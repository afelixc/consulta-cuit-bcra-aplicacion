// Lector de la banda CMC-7 de los cheques (la fila de números hechos con rayitas, abajo del cheque).
// Cada carácter son 7 rayas verticales con 6 espacios entre ellas: 4 cortos y 2 largos.
// Qué espacios son largos indica el número, como un código de barras. No necesita internet.
(function () {
  // 1 = espacio largo. Cualquier otra combinación es un símbolo separador.
  const TABLA = { '010001': '0', '100010': '1', '011000': '2', '101000': '3', '100100': '4',
                  '000110': '5', '001010': '6', '110000': '7', '010010': '8', '010100': '9' };

  // Umbral de Otsu: separa tinta de papel
  function otsu(g) {
    const hist = new Array(256).fill(0);
    for (let i = 0; i < g.length; i++) hist[g[i]]++;
    let sum = 0; for (let i = 0; i < 256; i++) sum += i * hist[i];
    let sB = 0, wB = 0, mejor = 0, umbral = 128;
    for (let t = 0; t < 256; t++) {
      wB += hist[t]; if (!wB) continue;
      const wF = g.length - wB; if (!wF) break;
      sB += t * hist[t];
      const mB = sB / wB, mF = (sum - sB) / wF, v = wB * wF * (mB - mF) ** 2;
      if (v > mejor) { mejor = v; umbral = t; }
    }
    return umbral;
  }

  // Lee una franja horizontal (centrada en yc, de alto "alto") girándola un poco por si el cheque está torcido:
  // suma la tinta de cada columna, encuentra las rayas y traduce cada grupo de 7.
  function perfilFranja(g, w, h, yc, alto, umbral, ang) {
    const perfil = new Float32Array(w), s = Math.sin(ang), c = Math.cos(ang), xc = w / 2;
    for (let k = -alto / 2; k < alto / 2; k++) {
      for (let x = 0; x < w; x++) {
        const xs = Math.round(xc + (x - xc) * c - k * s), ys = Math.round(yc + (x - xc) * s + k * c);
        if (xs < 0 || xs >= w || ys < 0 || ys >= h) continue;
        const v = g[ys * w + xs];
        if (v < umbral) perfil[x] += umbral - v;
      }
    }
    return perfil;
  }

  // Qué tan nítidas quedan las rayas con ese ángulo (si el ángulo es el justo, el perfil tiene picos bien marcados)
  function nitidez(p) {
    let d = 0, t = 0;
    for (let x = 1; x < p.length; x++) { d += (p[x] - p[x - 1]) ** 2; t += p[x]; }
    return t ? d / t : 0;
  }

  function leerPerfil(perfil, nivel) {
    const w = perfil.length;
    const orden = Array.from(perfil).filter(v => v > 0).sort((a, b) => a - b);
    if (orden.length < 50) return '';
    const corte = orden[Math.floor(orden.length * 0.9)] * nivel;
    // Cada raya es un pico del perfil; dos picos cuentan como rayas distintas solo si entre ellos baja bastante
    const centros = [], alturas = [];
    for (let x = 1; x < w - 1; x++) {
      const v = perfil[x];
      if (v <= corte || v < perfil[x - 1] || v <= perfil[x + 1]) continue;
      const n = centros.length;
      if (n) {
        let min = v;
        for (let i = Math.ceil(centros[n - 1]); i < x; i++) if (perfil[i] < min) min = perfil[i];
        if (min > Math.min(v, alturas[n - 1]) * 0.8) {
          if (v > alturas[n - 1]) { centros[n - 1] = x; alturas[n - 1] = v; }
          continue;
        }
      }
      const a = perfil[x - 1], b = perfil[x + 1], den = a - 2 * v + b;
      centros.push(x + (den ? (a - b) / (2 * den) : 0)); alturas.push(v);
    }
    if (centros.length < 70) return '';
    const d = [];
    for (let i = 1; i < centros.length; i++) d.push(centros[i] - centros[i - 1]);
    const dm = [...d].sort((a, b) => a - b)[Math.floor(d.length * 0.3)];
    // Separar en caracteres por los espacios grandes
    const grupos = [[]];
    d.forEach(v => { if (v > dm * 2.7) grupos.push([]); else grupos[grupos.length - 1].push(v); });
    return grupos.map(gr => {
      if (gr.length !== 6) return gr.length > 1 ? '?' : '';
      const o = [...gr].sort((a, b) => a - b);
      if (o[4] < o[3] * 1.25) return '?';
      const lim = (o[3] + o[4]) / 2;
      return TABLA[gr.map(v => (v > lim ? '1' : '0')).join('')] || '|';
    }).join('');
  }

  // Separa los 29 números en banco (3), sucursal (3), código postal (4), cheque (8) y cuenta (11)
  function separar(texto) {
    const m = texto.match(/\d[\d|]*\d/);
    if (!m || /\?/.test(m[0])) return null;
    const num = m[0].replace(/\|/g, '');
    if (num.length !== 29) return null;
    return { banco: num.slice(0, 3), sucursal: num.slice(3, 6), cp: num.slice(6, 10), cheque: num.slice(10, 18), cuenta: num.slice(18), todo: num };
  }

  const ANGULOS = [];
  for (let a = -5; a <= 5; a += 0.5) ANGULOS.push(a * Math.PI / 180);

  // g: escala de grises (Uint8Array) de w × h. Prueba franjas a distintas alturas y ángulos.
  // Devuelve los datos cuando dos lecturas coinciden (o la única lectura válida), o null.
  function leerGris(g, w, h) {
    const alto = Math.max(12, Math.round(w / 18));
    const votos = {};
    const ys = [];
    if (h <= alto * 1.8) ys.push(h / 2);
    else for (let y = alto / 2; y <= h - alto / 2; y += alto / 3) ys.push(y);
    for (const yc of ys) {
      const y0 = Math.max(0, Math.round(yc - alto)), y1 = Math.min(h, Math.round(yc + alto));
      const u = otsu(g.subarray(y0 * w, y1 * w));
      const perfiles = ANGULOS.map(a => perfilFranja(g, w, h, yc, alto, u, a)).map(p => ({ p, n: nitidez(p) })).sort((a, b) => b.n - a.n);
      for (const { p } of perfiles.slice(0, 3)) {
        for (const nivel of [0.15, 0.3]) {
          const r = separar(leerPerfil(p, nivel));
          if (r) {
            votos[r.todo] = votos[r.todo] || { r, n: 0 };
            if (++votos[r.todo].n >= 2) return r;
          }
        }
      }
    }
    const v = Object.values(votos).sort((a, b) => b.n - a.n)[0];
    return v ? v.r : null;
  }

  // Pasa una imagen (canvas) a escala de grises; si la franja es alta, la recorta en varias franjas horizontales
  function leerCanvas(canvas) {
    const { width: w, height: h } = canvas;
    const px = canvas.getContext('2d', { willReadFrequently: true }).getImageData(0, 0, w, h).data;
    const g = new Uint8Array(w * h);
    for (let i = 0, j = 0; j < g.length; i += 4, j++) g[j] = (px[i] * 77 + px[i + 1] * 150 + px[i + 2] * 29) >> 8;
    return leerGris(g, w, h);
  }

  window.CMC7 = { leerCanvas, leerGris, _interno: { otsu, perfilFranja, nitidez, leerPerfil, separar } };
})();
