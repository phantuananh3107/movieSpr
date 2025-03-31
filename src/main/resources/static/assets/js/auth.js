import {auth} from "./config.js"

import { createUserWithEmailAndPassword, signInWithEmailAndPassword,signOut } from "https://www.gstatic.com/firebasejs/11.5.0/firebase-auth.js";


// xu ly  dang xuat
export const handleSignOut = async ()=>{
    try {
        await signOut(auth);
        document.getElementById ('avatar').innerHTML=`
            <i class='bx bx-user-circle' id="login-icon"></i>
        `
        window.location.href='/login'

    } catch (e) {
            console.log("Error: ",e.message)
    }
}


// Hàm xử lý đăng nhập
const signIn = async () => {
    const email = document.getElementById('emailLogin').value.trim();
    const password = document.getElementById('passwordLogin').value.trim();
    const resMess = document.getElementById('resMessageLogin');
    resMess.innerText = '';
    console.log(resMess)

    if (!email || !password) {
        resMess.innerText = 'Vui lòng điền đầy đủ thông tin.';
        return;
    }
    try {
        const results = await signInWithEmailAndPassword(auth, email, password);
        const userEmail = results.user.email;
        // console.log('Đăng nhập thành công:', userEmail);
        resMess.style.color='green'
        resMess.innerText = 'Đăng nhập thành công!';
        window.location.href = '/index';
    } catch (e) {
        console.error("Lỗi đăng nhập:", e.message);
        alert('Đăng nhập thất bại: '+e.message)
    }
};


// Hàm xử lý đăng ký
const signUp = async () => {
    const email = document.getElementById('emailReg').value.trim();
    const password = document.getElementById('passwordReg').value.trim();
    const confpass = document.getElementById('ConfpasswordReg').value.trim();
    const resMess = document.getElementById('resMessageLogin');
    resMess.innerText = '';
    if (!email || !password || !confpass) {
        resMess.innerText = 'Vui lòng điền đầy đủ thông tin.';
        return;
    }

    if (password !== confpass) {
        resMess.innerText = 'Mật khẩu không khớp!';
        return;
    }
    try {
        const result = await createUserWithEmailAndPassword(auth, email, password);
        const userEmail = result.user.email;
        // console.log('Đăng ký thành công:', userEmail);
        resMess.style.color='green'
        resMess.innerText = 'Tạo tài khoản thành công!';
        window.location.href = '/login';

    } catch (e) {
        console.error("Lỗi đăng ký:", e.message);
        resMess.innerText = 'Đăng ký thất bại: ' + e.message;
    }
};


// Lắng nghe sự kiện submit

if(document.getElementById('register-form')) {
    document.getElementById('register-form').addEventListener('submit',(event)=>{
        event.preventDefault();
        signUp()
    })
}
   
if(document.getElementById('login-form')) {
    document.getElementById('login-form').addEventListener('submit',(event)=>{
        event.preventDefault();
        signIn()
    })
}
   

