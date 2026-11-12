package ar.afelixc.consultacuit;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

/**
 * Abre una página en Chrome (si el teléfono no tiene Chrome, la abre con el navegador de siempre).
 * Desde la pantalla: AbrirWeb.abrir({ url: "https://..." }) → { chrome: true/false }
 */
@CapacitorPlugin(name = "AbrirWeb")
public class AbrirWebPlugin extends Plugin {

    @PluginMethod
    public void abrir(PluginCall call) {
        String url = call.getString("url");
        if (url == null || !url.startsWith("https://")) {
            call.reject("Dirección no válida");
            return;
        }
        Uri direccion = Uri.parse(url);
        JSObject r = new JSObject();
        try {
            Intent chrome = new Intent(Intent.ACTION_VIEW, direccion);
            chrome.setPackage("com.android.chrome");
            chrome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(chrome);
            r.put("chrome", true);
            call.resolve(r);
        } catch (ActivityNotFoundException sinChrome) {
            try {
                Intent otro = new Intent(Intent.ACTION_VIEW, direccion);
                otro.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(otro);
                r.put("chrome", false);
                call.resolve(r);
            } catch (Exception e) {
                call.reject("No se pudo abrir la página");
            }
        }
    }
}
