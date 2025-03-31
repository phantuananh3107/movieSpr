export const TMDB_API="4019753f9031b36e438ed91336ead576"



 // Import the functions you need from the SDKs you need
 import { initializeApp } from "https://www.gstatic.com/firebasejs/11.5.0/firebase-app.js";
 import { getAnalytics } from "https://www.gstatic.com/firebasejs/11.5.0/firebase-analytics.js";

 import { getAuth } from "https://www.gstatic.com/firebasejs/11.5.0/firebase-auth.js";
 // TODO: Add SDKs for Firebase products that you want to use
 // https://firebase.google.com/docs/web/setup#available-libraries

 // Your web app's Firebase configuration
 // For Firebase JS SDK v7.20.0 and later, measurementId is optional
 const firebaseConfig = {
   apiKey: "AIzaSyAHdkTphgzNxrytm09ECiq0t51u5DfzFiM",
   authDomain: "movieproject-8b0bb.firebaseapp.com",
   projectId: "movieproject-8b0bb",
   storageBucket: "movieproject-8b0bb.firebasestorage.app",
   messagingSenderId: "1050620591355",
   appId: "1:1050620591355:web:43f17acc2e621bfac8c45e",
   measurementId: "G-FRGN6VZ58G"
 };

 // Initialize Firebase
 const app = initializeApp(firebaseConfig);
 const analytics = getAnalytics(app);

// Initialize Firebase Authentication and get a reference to the service
const auth = getAuth(app);


export {auth}