# Consulta CUIT BCRA · App Android

## Regla: hay dos versiones de la misma app

Este proyecto (Android) tiene una versión hermana para Windows: **afelixc/consulta-cuit-bcra-windows**.
La carpeta `www/` (la pantalla y todas sus funciones) es **idéntica** en los dos proyectos.

- Cada vez que se cambie algo de `www/` en este proyecto, **antes de terminar preguntale al usuario si quiere el mismo cambio en la versión de Windows**.
- Si dice que sí: copiar `www/` completa al otro proyecto, probar y publicar allá también. Si dice que no, avisar que las dos versiones quedan distintas.
- Lo propio de cada plataforma va **fuera** de `www/`: acá en `android/`; en Windows en `main.js` / `preload.js`. Si algo tiene que comportarse distinto, usar `window.CONFIG_APP` (en Windows lo define `preload.js`; acá no existe y se usan los valores por defecto), no copias distintas de `www/`.
- La versión web (**afelixc/consulta-cuit-bcra**, carpeta `docs/`) es otra cosa: solo Informe por CUIT y Cheques denunciados. No se sincroniza salvo que el usuario lo pida.

## Otros datos

- El usuario habla en castellano rioplatense y no es programador: explicar todo en pasos simples.
- Cada push a `main` compila el APK firmado en GitHub Actions y crea un release `v1.0.N` (la firma está en los secrets `FIRMA_BASE64` y `FIRMA_CLAVE`).
- Los datos del usuario (guardados, certificado de ARCA) están en `localStorage` del teléfono: no cambiar el `appId` ni la firma, o se pierden al actualizar.
