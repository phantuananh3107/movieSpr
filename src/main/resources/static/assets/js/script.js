import {TMDB_API,auth} from "./config.js"
import {handleSignOut} from "./auth.js"
import {onAuthStateChanged} from "https://www.gstatic.com/firebasejs/11.5.0/firebase-auth.js"


const urlParams = new URLSearchParams(window.location.search)
const movieId = urlParams.get('id')

// if(!movieId){
//     window.location.href='/index';
// }


// ham hien acc sau khi dang nhap
const displayUser = (user)=>{
    const ava=document.getElementById('avatar')

    if(user){
        const avaUrl=`https://api.dicebear.com/9.x/adventurer-neutral/svg?seed=${user.email}`
        ava.innerHTML=`
            <div class="ava-info">
                <img src="${avaUrl}" class="ava-img" />
                <p>${user.email} </p>
                <button class="action-button" id="logOut-btn" href="/login" >LogOut</button>
            </div>
        `
        document.getElementById('logOut-btn').addEventListener('click',handleSignOut)
    }
    else{
        ava.innerHTML=`
              <i class='bx bx-user-circle id="login-icon"'></i>
        `

        document.getElementById('login-icon').addEventListener('click',()=>{
            window.location.href='/login'
        })
    }
}

auth.onAuthStateChanged(user =>{
    displayUser(user )
})



//ham lay id phim
const fetchMovieDetail = async ()=>{
    const res = await fetch(`https://api.themoviedb.org/3/movie/${movieId}?api_key=${TMDB_API}`);

    const data= await res.json();
    return data;
}

const displayDetailMovie =async(movie)=>{ 
   

    document.getElementById('poster_path').src=`https://image.tmdb.org/t/p/w200/${movie.poster_path}`
    document.getElementById('name').innerText=movie.title;
    document.getElementById('release_date').innerText=movie.release_date
    document.getElementById('overview').innerText=movie.overview;
    document.getElementById('vote_average').innerText=movie.vote_average;
    document.getElementById('trailer').href=`https://www.youtube.com/results?search_query=${movie.title}+trailer`

    //background
    const backdropUrl = `https://image.tmdb.org/t/p/original/${movie.backdrop_path}`;
    document.querySelector('.background-blur').style.backgroundImage = `url(${backdropUrl})`;
}   

fetchMovieDetail().then(displayDetailMovie)


// swiper-js
const swiper = new Swiper('.swiper', {
    spaceBetween:30,
    autoplay:{delay: 5000,disableOnInteraction: true},
    slidesPerView: "auto",
    loop:true,
    slidesPerGroupAuto: true,
   
    // Navigation arrows
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
  
  
  });

//list trendingg movie
const fetchTrending = async (timeWindow) => {
    const url=`https://api.themoviedb.org/3/trending/movie/${timeWindow}?api_key=${TMDB_API}`

    try {
        const res=await fetch(url);
        if(!res){
            //neu duong truyen loi
            throw Error("Network was not ok!",res.status)
        }
        const data=await res.json();
        console.log(data.results)
        return data.results;

    } catch (e) {
        console.log("Error ",e)

    }
}

//display trending movie
const displayTrendingMovie = (arr)=>{
    const movieTrend=document.getElementById('trend-movie');
    movieTrend.innerHTML='';

    movieTrend.innerHTML = arr.map(movie=>{
       return  `
            <a href="/detail?id=${movie.id}" class="swiper-slide">
                <img src="https://image.tmdb.org/t/p/w300/${movie.poster_path}" alt="${movie.title}">
                <p>${movie.title}</p>
                 
                        
            </a>
        `
    }).join("");
}



//list popular movie
const fetchPopular = async () => {
    const url=` https://api.themoviedb.org/3/movie/popular?api_key=${TMDB_API}`
   
    try {
        const res=await fetch(url);
        if(!res){
            //neu duong truyen loi
            throw Error("Network was not ok!",res.status)
        }
        const data=await res.json();
        // console.log(data.results)
        return data.results;

    } catch (e) {
        console.log("Error ",e)

    }
}


//display popular movie
const displayPopularMovie = (arr)=>{
    const movieTrend=document.getElementById('Popular-movie');
    movieTrend.innerHTML='';

   
    movieTrend.innerHTML = arr.map(movie=>{
       return  `
            <a href="/detail?id=${movie.id}" class="swiper-slide">
                <img src="https://image.tmdb.org/t/p/w300/${movie.poster_path}" alt="${movie.title}">
                    <p>${movie.title}</p>
                 
            </a>
        `
    }).join("");
}

fetchTrending('day').then(displayTrendingMovie)
fetchPopular().then(displayPopularMovie)







  //page home

  const fetchHome = async () => {
    const url=` https://api.themoviedb.org/3/movie/top_rated?api_key=${TMDB_API}`
   
    try {
        const res=await fetch(url);
        if(!res){
            //neu duong truyen loi
            throw Error("Network was not ok!",res.status)
        }
        const data=await res.json();
        // console.log(data.results)
        return data.results;

    } catch (e) {
        console.log("Error ",e)

    }
}


//display home movie
const displayHome = (arr)=>{
    const movieTrend=document.getElementById('homeMovie');
    movieTrend.innerHTML='';

   
    movieTrend.innerHTML = arr.map(movie=>{
       return  `
            <a href="/detail?id=${movie.id}" class="swiper-slide">
                <img src="https://image.tmdb.org/t/p/w300/${movie.poster_path}" alt="${movie.title}">
                    <p>${movie.title}</p>
                 
            </a>
        `
    }).join("");
}
fetchHome().then(displayHome)





