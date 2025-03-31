import {TMDB_API} from './config.js'


document.getElementById('search-form').addEventListener('submit',(even)=>{
    even.preventDefault();
    
    const query= document.getElementById('search-input').value.trim();

    if(query){
        fetchSearch(query).then(displaySearch)
    }

})


const fetchSearch = async(query)=>{
    const res=await fetch(`https://api.themoviedb.org/3/search/movie?query=${encodeURIComponent(query)}&api_key=${TMDB_API}`)

    const data=await res.json();
    return data.results;


}
const displaySearch = (movies)=>{
    const movieList = document.getElementById('movies');
    movieList.innerHTML='';

    if(movies.length==0){
        movieList.innerHTML='<h2>No movie found </h2>';
        return ;
    }
    movies.forEach(movie =>{
        const movieCard = document.createElement('a')
        movieCard.classList.add('movie-item')
        movieCard.setAttribute('href',`../datail.html?id=${movie.id}`)
        movieCard.innerHTML=`
           
          
              <a href="../datail.html?id=${movie.id}" class="search-item ">
                 <img src=" https://image.tmdb.org/t/p/w300/${movie.poster_path}">
                 <p>${movie.title}</p>
                
            </a> 
         `;
         movieList.appendChild(movieCard);
    })
    
}