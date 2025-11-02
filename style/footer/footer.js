async function loadFooter() {
    const res = await fetch('/style/footer/footer.html');
    const html = await res.text();
    document.querySelector('.footer').innerHTML = html;
    const cssPath ='/style/footer/footer.css';
    if (!document.querySelector(`link[href="${cssPath}"]`)) {
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = cssPath;
        document.head.appendChild(link);
    }

}
loadFooter();
