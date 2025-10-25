// JS: ảnh + render + logic slider
const images = [
    "https://st.meta.vn/img/thumb.ashx/Data/2025/Thang10/chao-thu/LDP-chao-thu-990x280.png",
    "https://st.meta.vn/img/thumb.ashx/Data/2025/Thang04/Banner-tivi-thong-minh-990x280-a.png",
    "https://st.meta.vn/img/thumb.ashx/Data/2025/Thang10/Banner-Halloween-990x280.png"];

function createSlider(containerId, imgs, {interval=4000} = {}){
    const root = document.getElementById(containerId);
    root.classList.add('slider');

    // Khung
    const track = document.createElement('div');
    track.className = 'slides';
    const prev = document.createElement('button');
    prev.className = 'nav prev'; prev.setAttribute('aria-label', 'Prev'); prev.textContent = '‹';
    const next = document.createElement('button');
    next.className = 'nav next'; next.setAttribute('aria-label', 'Next'); next.textContent = '›';
    const dots = document.createElement('div'); dots.className = 'dots';

    // Render ảnh từ JS
    imgs.forEach(src => {
        const img = document.createElement('img');
        img.src = src; img.alt = '';
        track.appendChild(img);
    });

    // Render dots theo số ảnh
    imgs.forEach((_, i) => {
        const b = document.createElement('button');
        b.addEventListener('click', () => go(i));
        dots.appendChild(b);
    });

    root.append(track, prev, next, dots);

    // Logic
    let index = 0, timer;
    function update(){
        track.style.transform = `translateX(-${index*100}%)`;
        [...dots.children].forEach((d,i)=>d.classList.toggle('active', i===index));
    }
    function go(i){ index = (i+imgs.length)%imgs.length; update(); }
    const nextSlide = () => go(index+1);
    const prevSlide = () => go(index-1);

    next.addEventListener('click', nextSlide);
    prev.addEventListener('click', prevSlide);

    function start(){ stop(); timer = setInterval(nextSlide, interval); }
    function stop(){ clearInterval(timer); }

    root.addEventListener('mouseenter', stop);
    root.addEventListener('mouseleave', start);

    update(); start();
}

// Dùng:
createSlider('slider', images, { interval: 4000 });
