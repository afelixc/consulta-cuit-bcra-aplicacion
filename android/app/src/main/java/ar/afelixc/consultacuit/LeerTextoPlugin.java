package ar.afelixc.consultacuit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Base64;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

/**
 * Lee el texto impreso de una foto (por ejemplo el recuadro de números del cheque) con el lector de Google
 * (ML Kit, por los Servicios de Google del teléfono; funciona sin internet una vez bajado).
 * Desde la pantalla: LeerTexto.leer({ imagen: "<jpeg en base64>" }) → { texto, lineas: [{ texto, x, y, ancho, alto }] }
 */
@CapacitorPlugin(name = "LeerTexto")
public class LeerTextoPlugin extends Plugin {

    /** Pide a los Servicios de Google que bajen el lector de texto (si ya está, no hace nada). */
    private void pedirDescarga(TextRecognizer lector) {
        try {
            ModuleInstall.getClient(getContext()).installModules(ModuleInstallRequest.newBuilder().addApi(lector).build());
        } catch (Exception ignorado) { }
    }

    @Override
    public void load() {
        TextRecognizer lector = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        ModuleInstall.getClient(getContext()).areModulesAvailable(lector)
            .addOnSuccessListener(r -> { if (!r.areModulesAvailable()) pedirDescarga(lector); })
            .addOnFailureListener(e -> pedirDescarga(lector));
    }

    /** ¿El teléfono ya tiene el lector de texto? → { listo: true/false } */
    @PluginMethod
    public void estado(PluginCall call) {
        TextRecognizer lector = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        ModuleInstall.getClient(getContext()).areModulesAvailable(lector)
            .addOnSuccessListener(r -> {
                JSObject o = new JSObject();
                o.put("listo", r.areModulesAvailable());
                call.resolve(o);
            })
            .addOnFailureListener(e -> call.reject("No se pudo consultar a los Servicios de Google: " + e.getMessage()));
    }

    /** Baja el lector de texto y avisa cuando terminó → { listo: true } */
    @PluginMethod
    public void descargar(PluginCall call) {
        TextRecognizer lector = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        final ModuleInstallClient cliente = ModuleInstall.getClient(getContext());
        final InstallStatusListener[] escucha = new InstallStatusListener[1];
        final boolean[] terminado = { false };
        escucha[0] = actualizacion -> {
            int estado = actualizacion.getInstallState();
            if (terminado[0]) return;
            if (estado == ModuleInstallStatusUpdate.InstallState.STATE_COMPLETED) {
                terminado[0] = true;
                cliente.unregisterListener(escucha[0]);
                JSObject o = new JSObject();
                o.put("listo", true);
                call.resolve(o);
            } else if (estado == ModuleInstallStatusUpdate.InstallState.STATE_FAILED || estado == ModuleInstallStatusUpdate.InstallState.STATE_CANCELED) {
                terminado[0] = true;
                cliente.unregisterListener(escucha[0]);
                call.reject("La descarga no se pudo completar (código " + actualizacion.getErrorCode() + ")");
            }
        };
        ModuleInstallRequest pedido = ModuleInstallRequest.newBuilder().addApi(lector).setListener(escucha[0]).build();
        cliente.installModules(pedido)
            .addOnSuccessListener(r -> {
                if (r.areModulesAlreadyInstalled() && !terminado[0]) {
                    terminado[0] = true;
                    cliente.unregisterListener(escucha[0]);
                    JSObject o = new JSObject();
                    o.put("listo", true);
                    call.resolve(o);
                }
            })
            .addOnFailureListener(e -> {
                if (terminado[0]) return;
                terminado[0] = true;
                call.reject("No se pudo pedir la descarga: " + e.getMessage());
            });
    }

    @PluginMethod
    public void leer(PluginCall call) {
        String datos = call.getString("imagen");
        if (datos == null || datos.isEmpty()) {
            call.reject("Falta la imagen");
            return;
        }
        Bitmap bmp;
        try {
            byte[] bytes = Base64.decode(datos, Base64.DEFAULT);
            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {
            call.reject("No se pudo abrir la imagen");
            return;
        }
        if (bmp == null) {
            call.reject("No se pudo abrir la imagen");
            return;
        }
        final TextRecognizer lector = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        lector.process(InputImage.fromBitmap(bmp, 0))
            .addOnSuccessListener(texto -> {
                JSArray lineas = new JSArray();
                for (Text.TextBlock bloque : texto.getTextBlocks()) {
                    for (Text.Line linea : bloque.getLines()) {
                        JSObject l = new JSObject();
                        l.put("texto", linea.getText());
                        Rect r = linea.getBoundingBox();
                        if (r != null) {
                            l.put("x", r.left);
                            l.put("y", r.top);
                            l.put("ancho", r.width());
                            l.put("alto", r.height());
                        }
                        lineas.put(l);
                    }
                }
                JSObject res = new JSObject();
                res.put("texto", texto.getText());
                res.put("lineas", lineas);
                call.resolve(res);
                lector.close();
            })
            .addOnFailureListener(e -> {
                // Lo más común: el teléfono todavía no bajó el lector de texto de Google. Se lo pide y se avisa.
                pedirDescarga(lector);
                call.reject("No se pudo leer: " + e.getMessage() + " (si dice que falta descargar, conectate a internet, esperá un par de minutos y probá de nuevo)");
            });
    }
}
