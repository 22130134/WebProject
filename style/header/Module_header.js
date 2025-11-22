async function loadHeader() {

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