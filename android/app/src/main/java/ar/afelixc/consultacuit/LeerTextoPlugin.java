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
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

/**
 * Lee el texto impreso de una foto (por ejemplo el recuadro de números del cheque) con el lector de Google
 * (ML Kit), que viene dentro de la app y funciona sin internet.
 * Desde la pantalla: LeerTexto.leer({ imagen: "<jpeg en base64>" }) → { texto, lineas: [{ texto, x, y, ancho, alto }] }
 */
@CapacitorPlugin(name = "LeerTexto")
public class LeerTextoPlugin extends Plugin {

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
                call.reject("No se pudo leer: " + e.getMessage());
                lector.close();
            });
    }
}
