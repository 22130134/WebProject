async function loadFooter() {
    const res = await fetch('/style/footer/footer.html');
    const html = await res.text();
    document.querySelector('.footer').innerHTML = html;
}
loadFooter();
