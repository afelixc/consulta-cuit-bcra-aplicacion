# Consulta CUIT BCRA · Aplicación Android

App para el teléfono (APK) de [Consulta CUIT BCRA](https://github.com/afelixc/consulta-cuit-bcra):

- **Informe por CUIT:** nombre del titular, cheques rechazados, situación en los bancos e historial de 24 meses.
- **Cheques denunciados:** banco + número de cheque (+ sucursal y cuenta).
- Guardar CUIT y cheques con una nota, compartir el informe, copiarlo o sacarlo en PDF.

## 📱 Instalar

1. Desde el teléfono, descargá **[Consulta-CUIT-BCRA.apk](../../releases/latest/download/Consulta-CUIT-BCRA.apk)**.
2. Abrí el archivo descargado y tocá **Instalar**.
3. La primera vez Android pide permiso para "instalar apps de origen desconocido": tocá **Configuración**, activá **Permitir de esta fuente** y volvé.
4. Si aparece **Play Protect** ("app desconocida"), tocá **Más detalles** → **Instalar de todas formas**.

Para actualizar: descargá el APK nuevo e instalalo encima. Los guardados no se pierden.

## Cómo está hecha

- `www/` es el programa (el mismo de la versión celular, adaptado a la app).
- `android/` es el proyecto Android, hecho con [Capacitor](https://capacitorjs.com).
- Cada vez que se suben cambios, GitHub compila el APK solo (`.github/workflows/apk.yml`) y lo publica en **Releases**.
- La firma de la app se guarda en los *secrets* del repositorio (`FIRMA_BASE64` y `FIRMA_CLAVE`), nunca en el código.

Lo guardado (historial, CUIT y cheques) queda solo en el teléfono.
