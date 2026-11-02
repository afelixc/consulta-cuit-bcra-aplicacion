package ar.afelixc.consultacuit;

import android.os.Bundle;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Lector de texto propio de la app (para el recuadro de números del cheque)
        registerPlugin(LeerTextoPlugin.class);
        super.onCreate(savedInstanceState);
    }
}
