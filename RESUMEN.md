# Resumen de sesión
_Última actualización: 2026-11-24 (tarde, PC laburo)_

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
- App INVESTI-CUIT CREADA en Play Console (cuenta "Alejandro Felix Cardenas").
- HECHO y guardado: ficha (textos, ícono, gráfico 1024x500, 5 capturas), categoría Finanzas, mail de contacto aplicaciones.afc@gmail.com (sin teléfono), Contenido de la app: Política de privacidad, Anuncios (No), Detalles de acceso (sin restricciones), ID de publicidad (No), Apps gubernamentales (No), Funciones financieras ("Informes y control de crédito", sin documentación).
- FALTA en Contenido de la app: Apps de salud (No), Estado de comerciante UE (no comerciante), Público objetivo (18+), Seguridad de los datos (SÍ recopila: "Otra información financiera" = CUIT y datos de cheque que se mandan a BCRA/ARCA; no se comparte; cifrado; obligatorio; sin servidor propio; la foto del cheque NO sale del teléfono), Clasificación del contenido (mail aplicaciones.afc@gmail.com, "El resto de los tipos de app", No a todo) -> antes de aceptar las Condiciones de IARC pedir OK al usuario.
- Más adelante la app pedirá mail, teléfono y otros datos: actualizar Seguridad de los datos en esa versión.
- Imágenes copiadas en D: - CLAUDE HP LABURO\INVESTI-CUIT Play Store (PC laburo).

## Próximos pasos
1. Guiarlo con capturas: ficha (textos + imágenes), Configuración de la tienda (Finanzas), Contenido de la app.
2. Prueba cerrada: país Argentina, lista "Probadores" con los mails, subir `.aab`, enviar a revisión; pasar el link a testers.
3. Sumar mails hasta ≥12; a los 14 días, "Solicitar acceso a producción" (preparar respuestas).
4. Si Google rechaza algo: corregir, subir versión y armar `.aab` nuevo.
