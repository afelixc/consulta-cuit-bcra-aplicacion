// Lee la banda CMC-7 en segundo plano (para la lectura en vivo de la cámara sin trabar la pantalla).
self.window = self;
importScripts('cmc7.js');
onmessage = e => {
  const { id, px, w, h, emparejar } = e.data;
  let r = null;
  try {
    let g = new Uint8Array(w * h);
    for (let i = 0, j = 0; j < g.length; i += 4, j++) g[j] = (px[i] * 77 + px[i + 1] * 150 + px[i + 2] * 29) >> 8;
    if (emparejar) g = self.CMC7.emparejar(g, w, h);
    r = self.CMC7.leerGris(g, w, h);
  } catch (err) {}
  postMessage({ id, r });
};
