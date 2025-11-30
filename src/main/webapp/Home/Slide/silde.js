// JS: ảnh + render + logic slider
const images = [
    "https://sieuthiyte.com.vn/data/images/San-Pham/chuong-trinh-khuyen-mai-chuc-mung-phu-nu-viet-nam-14102025.jpg",
    "https://sieuthiyte.com.vn/data/images/San-Pham/banner-slide-may-tao-oxy-03072025.jpg",
    "https://sieuthiyte.com.vn/data/images/San-Pham/giai-phap-ho-tro-phuc-hoi-cot-song-co-alphay-slide-16052025.jpg"];

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
