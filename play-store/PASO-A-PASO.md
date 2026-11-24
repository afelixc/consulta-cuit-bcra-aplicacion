# Subir INVESTI-CUIT a Play Store: paso a paso

Todo se hace en la compu, en **play.google.com/console**, con tu cuenta de Google.
Los textos para copiar están en `TEXTOS-PARA-COPIAR.md`. Las imágenes están en esta misma carpeta.
(Google a veces cambia un poco los nombres de los botones: si alguno no coincide, buscá el más parecido.)

## 1. Crear la app
1. Tocá **Crear app**.
2. Completá:
   - Nombre de la app: `INVESTI-CUIT`
   - Idioma predeterminado: **Español (Latinoamérica) – es-419**
   - App o juego: **App**
   - Gratuita o pagada: **Gratuita**
3. Tildá las casillas de las declaraciones y tocá **Crear app**.

## 2. Ficha de la tienda
Menú de la izquierda: **Crecimiento › Presencia en Play Store › Ficha de Play Store principal**.
1. Descripción breve y descripción completa: copiarlas de `TEXTOS-PARA-COPIAR.md`.
2. Ícono de la app: `icono-512.png`
3. Gráfico de funciones: `imagen-1024x500.png`
4. Capturas de pantalla del teléfono: `captura-1.png`, `captura-3.png`, `captura-4.png` y `captura-5.png` (la del semáforo está en `no-usar/`: la versión Play no lo tiene)
5. Tocá **Guardar**.

En **Configuración de la tienda** (mismo menú): Categoría **Finanzas** y tu mail de contacto. **Guardar**.

## 3. Contenido de la app
Menú: **Política › Contenido de la app**. Completá cada cuadro con las respuestas de `TEXTOS-PARA-COPIAR.md`
(política de privacidad, anuncios, acceso a la app, clasificación, público objetivo, seguridad de los datos, apps de gobierno, funciones financieras).

## 4. Prueba cerrada (los 12 testers)
Menú: **Prueba y lanzamiento › Prueba › Prueba cerrada**.
1. En el carril **Alpha** (o "Prueba cerrada"), tocá **Administrar carril**.
2. Pestaña **Países o regiones**: agregá **Argentina**.
3. Pestaña **Verificadores**: **Crear lista de correo electrónico**, nombre `Probadores`, pegá los mails (uno por renglón). **Guardar** y tildá la lista.
4. En esa misma pestaña está el **link para unirse a la prueba**: copialo, es el que les mandás a los testers.
5. Tocá **Crear versión**.
   - Si pregunta por la firma: elegí **que Google genere y administre la clave** (es lo recomendado).
   - Subí el archivo `INVESTI-CUIT-PlayStore.aab`.
   - Notas de la versión: `Primera versión en Play Store.`
6. **Siguiente** › **Guardar** › **Enviar a revisión** (o "Iniciar lanzamiento").

Google revisa la app (puede tardar de unas horas a unos días).

## 5. Lo que hacen los testers (vos incluido)
1. Abrir el link de la prueba desde el celu, con la cuenta de Google del mail que diste.
2. Tocar **Convertirse en verificador**.
3. Tocar el link para ir a Play Store e **Instalar**.
4. Dejarla instalada **14 días**.

Para pasar los datos de la app vieja a la nueva: en la vieja **Guardados › Hacer copia**; en la nueva **Guardados › Restaurar**. Recién después borrar la vieja.

## 6. Después de los 14 días con 12 testers
En el **Panel**, aparece **Solicitar acceso a producción**. Responder las preguntas (las armamos juntos) y esperar la aprobación de Google. Ahí queda pública.
