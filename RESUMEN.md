# Resumen de sesión
_Última actualización: 2026-11-24_

## Objetivo
App Android **INVESTI-CUIT** (Informe BCRA, Cheques y Datos ARCA), hoy en versión 1.8. Ahora: **subirla a Google Play Store**.

## Contexto clave
- Repo: afelixc/consulta-cuit-bcra-aplicacion (local: /home/user/consulta-cuit-bcra-aplicacion). Capacitor 8. Pantalla: `www/index.html`. Workflow: `.github/workflows/apk.yml` (hoy solo `assembleRelease`, APK).
- Cada push a `main` compila el APK firmado y publica el release `vX.Y.N` (N = compilación; la app lo usa para avisar que hay versión nueva). Commits con `[skip ci]` no compilan.
- Versión visible: archivo `VERSION` + `VERSION_APP` en index.html (tienen que coincidir). Actual: **1.8** (release v1.8.50).
- appId `ar.afelixc.consultacuit`; minSdk 24, target/compileSdk 36. Permisos: INTERNET, CAMERA, REQUEST_INSTALL_PACKAGES (este último es para el auto-actualizar).
- Última versión: https://github.com/afelixc/consulta-cuit-bcra-aplicacion/releases/latest/download/INVESTI-CUIT.apk
- Entorno cloud "INVESTI-CUIT y servidor AFC" permite api.bcra.gob.ar, www.boletinoficial.gob.ar y servicioscf.afip.gob.ar.
- Pruebas: Playwright con respuestas guardadas del BCRA (PONY SA 30-70861358-0). El usuario usa letra grande: revisar en ancho ~280 px.
- Windows (afelixc/consulta-cuit-bcra-windows) NO tiene la 1.5–1.8 ("solo la aplicación por ahora").
- Play Console: el usuario ya tiene cuenta personal (la usó para "Server AFC", otro repo, en Prueba interna). Falta terminar la verificación (DNI, teléfono, celu Android).

## Restricciones
- Usuario no programador, castellano rioplatense: pasos simples, de a uno.
- No cambiar colores, `appId` ni la firma (secrets FIRMA_BASE64 / FIRMA_CLAVE).
- Si se cambia `www/`, preguntar si se pasa a Windows. Simular primero; subir solo cuando dice "subila/dale".
- Cada actualización sube la versión (1.9, 2.0…).

## Decisiones tomadas
- Respaldo de la versión vieja: rama `respaldo-v1.0.46` y release v1.0.46.
- Números siempre en su propio renglón; "Bancos en juicio" solo con datos del BCRA; cheques con "Entidad N".
- Se descartó lo pago (Nosis/Veraz, IA).
- Play Store (recomendado, falta que el usuario confirme): mantener appId `ar.afelixc.consultacuit` y la misma firma para que los que ya la tienen pasen a la de Play sin perder datos (a diferencia de Server AFC, que usó `aplicaciones.afc.serverafc`).

## Hecho
- v1.5–v1.8 publicadas (informe en ventanitas, semáforo, gráficos, PDF, solapitas de cheques, arreglo de números).
- Explicado al usuario qué hace falta para Play Store (sin cambios de código todavía).

## En curso / Problemas abiertos
- Esperando que el usuario responda: ¿Play para todos (requiere prueba cerrada con 12 testers × 14 días) o solo para él (Prueba interna)? ¿Arranco con los cambios?
- Falta que el usuario confirme la v1.8 en el teléfono.
- Datos de ARCA dentro del informe no probados con ARCA real.
- Pendientes viejos: cheque guardado en modo foto deja el formulario oculto; con Guardados abierto el fondo se mueve.

## Próximos pasos
1. Con el "dale": que GitHub arme también un `.aab` firmado (además del APK).
2. Versión de Play sin auto-actualizar ni permiso REQUEST_INSTALL_PACKAGES (usar una marca de compilación, sin copias distintas de `www/`).
3. Política de privacidad publicada gratis (datos solo en el teléfono; cámara; certificado ARCA).
4. Ficha: descripción aclarando que NO es oficial del BCRA ni de ARCA, ícono 512, imagen 1024×500, capturas (a1/a2/a3 o nuevas).
5. Guiar al usuario en Play Console: crear app, Play App Signing con la firma actual, subir AAB a Prueba interna/cerrada.
