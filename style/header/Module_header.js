async function loadHeader() {
    // 1) chèn HTML fragment
    const res = await fetch('/style/header/header.html');
    const html = await res.text();
    const mount = document.querySelector('#header');
    mount.innerHTML = html;

    // 2) nạp CSS (chỉ 1 lần)
    const cssPath = '/style/header/header.css';
    if (!document.querySelector(`link[href="${cssPath}"]`)) {
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = cssPath;
        document.head.appendChild(link);
    }

    // 3) nạp JS và gọi init (chỉ 1 lần)
    await loadScriptOnce('/style/header/header.js');
    if (window.setupStickyHeader) window.setupStickyHeader();
}

function loadScriptOnce(src) {
    return new Promise((resolve, reject) => {
        if (document.querySelector(`script[src="${src}"]`)) return resolve();
        const s = document.createElement('script');
        s.src = src;
        s.onload = () => resolve();
        s.onerror = reject;
        document.body.appendChild(s);
    });
}

loadHeader();