# INVESTI-CUIT (antes Consulta CUIT BCRA) · App Android

## Regla: hay dos versiones de la misma app

Este proyecto (Android) tiene una versión hermana para Windows: **afelixc/consulta-cuit-bcra-windows**.
La carpeta `www/` (la pantalla y todas sus funciones) es **idéntica** en los dos proyectos.

- Cada vez que se cambie algo de `www/` en este proyecto, **antes de terminar preguntale al usuario si quiere el mismo cambio en la versión de Windows**.
- Si dice que sí: copiar `www/` completa al otro proyecto, probar y publicar allá también. Si dice que no, avisar que las dos versiones quedan distintas.
- Lo propio de cada plataforma va **fuera** de `www/`: acá en `android/`; en Windows en `main.js` / `preload.js`. Si algo tiene que comportarse distinto, usar `window.CONFIG_APP` (en Windows lo define `preload.js`; acá no existe y se usan los valores por defecto), no copias distintas de `www/`.
- La versión web (**afelixc/consulta-cuit-bcra**, carpeta `docs/`) es otra cosa: solo Informe por CUIT y Cheques denunciados. No se sincroniza salvo que el usuario lo pida.

## Otros datos

- El usuario habla en castellano rioplatense y no es programador: explicar todo en pasos simples.
- Cada push a `main` compila el APK firmado en GitHub Actions y crea un release `vX.Y.N` (la firma está en los secrets `FIRMA_BASE64` y `FIRMA_CLAVE`). N es el número de compilación: la app lo usa para avisar que hay versión nueva.
- **Versión que ve el usuario (1.5, 1.6…):** cuando el usuario pide una versión nueva, cambiar el archivo `VERSION` (raíz) y la constante `VERSION_APP` en `www/index.html`. Las dos tienen que decir lo mismo.
- Respaldo de la versión anterior a la 1.5: rama `respaldo-v1.0.46` y el release v1.0.46.
- Los datos del usuario (guardados, certificado de ARCA) están en `localStorage` del teléfono: no cambiar el `appId` ni la firma, o se pierden al actualizar.
- **Play Store:** el `.aab` se arma con el nombre interno `aplicaciones.afc.investicuit` (variable `ID_APP` en el workflow) y sin auto-actualizar. El APK de GitHub sigue con `ar.afelixc.consultacuit`. Textos, imágenes y paso a paso en `play-store/`.
