(() => {
    const header = document.querySelector('.site-header');
    if (!header) return;

    const updateHeader = () => {
        // Khi ở đầu trang (scrollY <= 0) → thêm class .no-padding
        const removePadding = (window.scrollY || document.documentElement.scrollTop) > 50;
        header.classList.toggle('no-padding', removePadding);
    };

    window.addEventListener('scroll', updateHeader, { passive: true });
    // chạy 1 lần khi load trang
    updateHeader();
})();