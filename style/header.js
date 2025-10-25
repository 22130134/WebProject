async function loadHeader() {
    const res = await fetch('/style/header.html');
    const html = await res.text();
    document.querySelector('#header').innerHTML = html;
}
loadHeader();
