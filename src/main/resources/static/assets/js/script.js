import { handleSignOut } from "./auth.js";

// Hàm hiển thị thông tin người dùng sau khi đăng nhập
const displayUser = (user) => {
    const ava = document.getElementById('avatar');

    if (user) {
        const avaUrl = `https://api.dicebear.com/9.x/adventurer-neutral/svg?seed=${user.email}`;
        ava.innerHTML = `
            <div class="ava-info">
                <img src="${avaUrl}" class="ava-img" />
                <p>${user.email}</p>
                <button class="action-button" id="logOut-btn" href="/login">LogOut</button>
            </div>
        `;
        document.getElementById('logOut-btn').addEventListener('click', handleSignOut);
    } else {
        ava.innerHTML = `
            <i class='bx bx-user-circle' id="login-icon"></i>
        `;
        document.getElementById('login-icon').addEventListener('click', () => {
            window.location.href = '/login';
        });
    }
};

// Giả lập kiểm tra trạng thái đăng nhập (thay thế cho onAuthStateChanged)
document.addEventListener('DOMContentLoaded', () => {
    // Giả sử bạn lưu thông tin người dùng trong localStorage sau khi đăng nhập
    const user = JSON.parse(localStorage.getItem('user'));
    displayUser(user);
});

// Khởi tạo Swiper (dành cho trang /index và các trang khác nếu cần)
const swiper = new Swiper('.swiper', {
    spaceBetween: 30,
    autoplay: { delay: 5000, disableOnInteraction: true },
    slidesPerView: "auto",
    loop: true,
    slidesPerGroupAuto: true,
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
});