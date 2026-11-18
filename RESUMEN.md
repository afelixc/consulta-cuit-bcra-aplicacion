# Resumen de sesión
_Última actualización: 2026-11-18_

## Objetivo
Mantener y mejorar la app Android **INVESTI-CUIT** (antes "Consulta CUIT BCRA"). Tiene tres solapas: Informe BCRA, Cheques (denunciados, lectura por foto/cámara) y Datos ARCA.

## Contexto clave
- Repo: afelixc/consulta-cuit-bcra-aplicacion (copia local en /home/user/aplicacion). Hecha con Capacitor 8.
- Cada push a `main` hace que GitHub Actions compile el APK firmado y publique el release `v1.0.N` (archivo `INVESTI-CUIT.apk`).
- Link a la última versión: https://github.com/afelixc/consulta-cuit-bcra-aplicacion/releases/latest/download/INVESTI-CUIT.apk
- Pantalla: `www/index.html`. Código nativo: `android/app/src/main/java/ar/afelixc/consultacuit/` (MainActivity, LeerTextoPlugin, AbrirWebPlugin).
- Versión hermana para Windows: afelixc/consulta-cuit-bcra-windows. La web es afelixc/consulta-cuit-bcra (solo BCRA y Cheques, sin ARCA).
- La API del BCRA no responde desde el contenedor; para probar se usa un fetch simulado.
- No se puede compilar Android acá (no hay SDK); compila GitHub.

## Restricciones
- El usuario no es programador y habla castellano rioplatense: explicar en pasos simples.
- No cambiar los colores.
- No tocar `appId` ni la firma, porque se pierden los datos guardados.
- Nunca subir la clave de firma; vive en los secrets FIRMA_BASE64 y FIRMA_CLAVE.
- Si se cambia `www/`, preguntar si se pasa a Windows (ver CLAUDE.md).
- No tocar el repo web salvo pedido.
- Cuando el usuario dice "no toques nada", solo responder.

## Decisiones tomadas
- El trabajo de interfaz y la vista previa del 12/11 se descartaron por pedido del usuario. La vista privada sigue publicada en https://claude.ai/artifact/8G4Pvq155N5NfjfXGY226t y se puede borrar si la pide.
- El botón "buscar por nombre en CUIT Online" (Datos ARCA) ahora abre la página en Chrome. Si el teléfono no tiene Chrome, usa el navegador de siempre. Motivo: el bloqueador de anuncios de Samsung Internet hacía fallar la página.
- La v1.0.45 queda como respaldo (no se borra): https://github.com/afelixc/consulta-cuit-bcra-aplicacion/releases/download/v1.0.45/INVESTI-CUIT.apk

## Hecho
- v1.0.46 (commit 28b190a) publicada y compilada con éxito. Se agregó `AbrirWebPlugin.java`, se registró en MainActivity y en `index.html` se usa `abrirWeb.abrir({url})`.
- Revisé el APK v1.0.46 por dentro: dice INVESTI-CUIT, tiene las tres solapas y el cambio de Chrome.
- Link directo: https://github.com/afelixc/consulta-cuit-bcra-aplicacion/releases/download/v1.0.46/INVESTI-CUIT.apk
- Le di al usuario guías para apagar bloqueadores en Samsung Internet y para revisar el DNS privado del teléfono.
- Antes, solo charla, sin hacer nada: versión iPhone, costos de Apple y Play Store, posible clon pago con Mercado Pago y precios.

## En curso / Problemas abiertos
- Falta que el usuario confirme en el teléfono que la v1.0.46 abre Chrome. Una vez dijo que veía "una app con cosas de menos"; probablemente abrió un APK viejo de Descargas o la versión web.
- Quedó sin respuesta si este cambio de Chrome se pasa también a Windows. Allá no hace nada: `abrirWeb` es null y abre el navegador como siempre.
- Problemas chicos detectados en la app real, sin arreglar porque no se pidió:
  1. Al abrir un cheque guardado estando en modo foto, el formulario queda oculto (agregar `modoCheque(false)` en `abrirGuardado`).
  2. Con la hoja de Guardados abierta, el fondo se sigue moviendo.

## Próximos pasos
1. Esperar que el usuario confirme que la v1.0.46 abre Chrome y que tiene las tres solapas.
2. Preguntar si quiere el cambio también en Windows.
3. Ofrecer arreglar los dos problemas chicos (solo si dice que sí).
