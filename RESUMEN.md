# Resumen de sesión
_Última actualización: 2026-11-25_

## Objetivo
Publicar la app Android **INVESTI-CUIT** en Google Play Store, **pública y gratis**. Ahora: el usuario está cargando la app en Play Console (prueba cerrada).

## Contexto clave
- Repo: afelixc/consulta-cuit-bcra-aplicacion (público). Capacitor 8. Pantalla `www/index.html`. Workflow `.github/workflows/apk.yml`.
- Cada push a `main` arma el APK (GitHub) y el `.aab` (Play) y publica el release `vX.Y.N`. `[skip ci]` no compila. Versión actual **1.9** (release v1.9.52).
- `.aab` para Play: `INVESTI-CUIT-PlayStore.aab` en cada release. Nombre de paquete **`aplicaciones.afc.investicuit`** (por `ID_APP` en el workflow; regla del usuario `aplicaciones.afc.<app>`). Sin permiso REQUEST_INSTALL_PACKAGES y con `appendUserAgent: InvestiPlay` → la app no busca actualizaciones en GitHub (chequeo en `buscarActualizacion()` de index.html).
- El APK de GitHub sigue con `ar.afelixc.consultacuit` y su auto-actualizar (para no duplicar la app a los usuarios actuales).
- Carpeta `play-store/`: `PASO-A-PASO.md`, `TEXTOS-PARA-COPIAR.md`, `icono-512.png`, `imagen-1024x500.png`, `captura-1..5.png` (1080×2160, datos 100% inventados: "EMPRESA DE EJEMPLO SA", bancos ficticios, sin CUIT). Zip `INVESTI-CUIT-Play-Store.zip` mandado al usuario.
- Política de privacidad: https://github.com/afelixc/consulta-cuit-bcra-aplicacion/blob/main/docs/PRIVACIDAD.md (también `docs/privacidad.html`). GitHub Pages no se pudo activar desde acá (API bloqueada).
- Cuenta Play Console: personal, ya verificada (la misma de "Server AFC").
- La compu del usuario (DESKTOP-2VP81M4) figura en Remote Control pero "not_available": no se puede ver su pantalla; ayuda por capturas (Win+Shift+S, Ctrl+V en el chat).

## Restricciones
- Usuario no programador, castellano rioplatense: pasos simples, de a uno.
- No cambiar colores ni la firma (secrets FIRMA_BASE64 / FIRMA_CLAVE). Subir solo con "subila/dale". Cada actualización sube la versión.
- Windows (afelixc/consulta-cuit-bcra-windows): **no se toca** por ahora (no tiene 1.5–1.9).

## Decisiones tomadas
- Nombre de paquete nuevo `aplicaciones.afc.investicuit` → es otra app en el teléfono; datos se pasan con Guardados › Hacer copia (vieja) / Restaurar (nueva); después borrar la vieja.
- Firma en Play: que Google la genere (nuestra llave queda como llave de subida).
- Gratis; si más adelante se cobra, será dentro de la app (gratis no puede pasar a paga).
- Capturas sin ningún dato real (pedido del usuario).

## Hecho
- v1.9 publicada (APK + AAB) y revisada por dentro.
- Textos de ficha, respuestas de "Contenido de la app", política de privacidad, imágenes y paso a paso.
- Explicado: prueba cerrada ≥12 testers × 14 días seguidos (cuenta desde que hay 12 anotados), una vez por app; testers deben unirse por link e instalar desde Play.

## En curso / Problemas abiertos
- 24/11: Contenido de la app completo (público 18+, seguridad de datos, IARC). Prueba cerrada "Alpha": Argentina, lista "Probadores" (solo aplicaciones.afc@gmail.com), comentarios a aplicaciones.afc@gmail.com.
- **Rechazo de Google (v52):** "Play Console Requirements": declarar "Informes y control de crédito" exige cuenta de organización (D-U-N-S). La ayuda de Play Console (asistente IA, antes de crear el ticket) recomendó declarar "Mi app no ofrece ninguna función financiera" si la app solo muestra datos públicos. No se mandó ticket/apelación.
- **Arreglo:** la versión Play NO tiene semáforo de riesgo (`SIN_PUNTAJE` en `www/index.html`: se activa con el user agent `InvestiPlay` del `.aab` o con `CONFIG_APP.sinPuntaje`; oculta tarjeta, pantalla y partes del PDF). El APK de GitHub sigue con semáforo. Funciones financieras = ninguna. Descripción de la ficha sin semáforo; captura del semáforo sacada (`play-store/no-usar/`). Ficha con "No etiquetar recursos" de IA.
- **v1.9.53 enviada a revisión el 24/11 a la noche** (prueba cerrada). Esperar respuesta (hasta 7 días). Si la vuelven a rechazar por lo mismo: cuenta de organización.
- 25/11: Google todavía no contestó sobre la v53. El usuario vio en Centro de políticas el aviso "Infracción de los requisitos de Play Console" ("Se aplicó el 24 nov"): es el rechazo viejo de la v52, que queda visible hasta que revisen la v53. Se le pidió confirmar: "Ver correo electrónico" debe decir código 52; en "Resumen de la publicación" debe decir "Cambios en revisión" (si dice "no enviados", tocar Enviar cambios a revisión); Contenido de la app › Funciones financieras = ninguna. Pendiente su captura.
- 25/11: Explicado cómo sumar testers: Prueba cerrada › Alpha › Administrar carril › Verificadores › editar lista "Probadores" (un mail por renglón, cuentas Google de celus Android; juntar 14-15). No requiere nueva revisión. El link para unirse anda recién cuando aprueben; se le dio un mensaje de WhatsApp listo para mandar.
- Windows (afelixc/consulta-cuit-bcra-windows): no se copió este cambio (no hace falta, allá no hay Play Store). Las dos `www/` quedan distintas solo en `SIN_PUNTAJE`.

## Próximos pasos
1. Ver la captura del usuario (mail del rechazo: ¿código 52 o 53? / estado en Resumen de la publicación).
2. Esperar la aprobación de Google; mientras, el usuario carga los mails de testers en "Probadores".
3. Cuando aprueben: mandar el link de Verificadores a los testers (≥12, 14 días seguidos).
4. A los 14 días: "Solicitar acceso a producción".
5. Si se agregan funciones nuevas: no meter puntajes ni recomendaciones de crédito en la versión Play.
