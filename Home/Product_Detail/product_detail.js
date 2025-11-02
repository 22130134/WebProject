const IMAGES = [
    // ví dụ ảnh mẫu: thay bằng ảnh của bạn
    'https://down-vn.img.susercontent.com/file/vn-11134207-7ras8-m58b9cf4fad241',
    'https://down-vn.img.susercontent.com/file/7107990071f7904a342c14b39d070ba0',
    'https://down-vn.img.susercontent.com/file/c28c65c2fcd9f6e3e6baa7b4cd621d51',

];

// Nếu muốn dùng ảnh minh hoạ bạn đã gửi (tệp trong dự án hiện tại),
// có thể tạm thời đặt vào đầu mảng:
// IMAGES.unshift('images/durex-cover.jpg');

const mainImg = document.getElementById('mainImg');
const thumbsEl = document.getElementById('thumbs');
const btnPrev = document.getElementById('btnPrev');
const btnNext = document.getElementById('btnNext');

// Render thumbnail từ mảng IMAGES
function renderThumbs(list){
    thumbsEl.innerHTML = '';
    list.forEach((src, idx) => {
        const item = document.createElement('div');
        item.className = 'thumb';
        item.setAttribute('role','listitem');
        item.dataset.index = idx;
        item.innerHTML = `<img src="${src}" alt="Ảnh ${idx+1}">`;

        // Hover (desktop): show ảnh chính
        item.addEventListener('mouseenter', () => setMain(idx));

        // Click (mobile + desktop): set ảnh chính và đánh dấu active
        item.addEventListener('click', () => setMain(idx));

        thumbsEl.appendChild(item);
    });
}

function setMain(index){
    const src = IMAGES[index];
    if (!src) return;
    mainImg.src = src;

    // set active border cho thumb đang chọn
    thumbsEl.querySelectorAll('.thumb').forEach((t,i)=>{
        t.classList.toggle('is-active', i===index);
    });
}

// Nút cuộn danh sách (kéo dải ảnh)
const SCROLL_STEP = 3; // số thumb mỗi lần cuộn
function scrollThumbs(dir){ // dir = -1 trái, 1 phải
    const thumbSize = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--thumb-size'));
    const gap = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--gap'));
    const delta = dir * (thumbSize + gap) * SCROLL_STEP;
    thumbsEl.scrollBy({left: delta, behavior: 'smooth'});
}
btnPrev.addEventListener('click', ()=>scrollThumbs(-1));
btnNext.addEventListener('click', ()=>scrollThumbs(1));

// Khởi tạo
renderThumbs(IMAGES);
setMain(0);