// Hàm xử lý tìm kiếm
const handleSearch = (event) => {
    event.preventDefault();
    
    const query = event.target.querySelector('input').value.trim();
    const movieList = document.getElementById('movies');

    if (query) {
        movieList.innerHTML = '<p class="loading text-white">Đang tìm kiếm...</p>';
        
        fetchSearch(query)
            .then(displaySearch)
            .catch(error => {
                console.error('Error fetching search results:', error);
                movieList.innerHTML = '<h2 class="text-white">Đã có lỗi xảy ra. Vui lòng thử lại.</h2>';
            });
    } else {
        movieList.innerHTML = '<h2 class="text-white">Vui lòng nhập từ khóa tìm kiếm.</h2>';
    }
};

// Gắn sự kiện cho form trong navbar
document.getElementById('navbar-search-form').addEventListener('submit', handleSearch);

// Gọi endpoint /api/search để tìm kiếm phim từ database
const fetchSearch = async (query) => {
    const res = await fetch(`/api/search?query=${encodeURIComponent(query)}`);
    if (!res.ok) {
        throw new Error('Failed to fetch movies');
    }
    const data = await res.json();
    return data;
};

// Hiển thị kết quả tìm kiếm
const displaySearch = (movies) => {
    const movieList = document.getElementById('movies');
    movieList.innerHTML = '';

    if (movies.length === 0) {
        movieList.innerHTML = '<h2 class="text-white">Không có kết quả tìm kiếm.</h2>';
        return;
    }

    const title = document.createElement('h2');
    title.textContent = 'Kết quả tìm kiếm';
    title.classList.add('text-white', 'mb-3', 'text-center');
    movieList.appendChild(title);

    const resultsContainer = document.createElement('div');
    resultsContainer.classList.add('search-results');
    movieList.appendChild(resultsContainer);

    movies.forEach(movie => {
        const posterPath = movie.posterPath 
            ? movie.posterPath
            : 'https://via.placeholder.com/300x450?text=No+Image';

        const movieCard = document.createElement('a');
        movieCard.classList.add('search-item');
        movieCard.href = `/movie/${movie.id}`;

        const img = document.createElement('img');
        img.src = posterPath;
        img.alt = movie.title;

        const titleElement = document.createElement('p');
        titleElement.textContent = movie.title;
        titleElement.classList.add('text-white');

        movieCard.appendChild(img);
        movieCard.appendChild(titleElement);
        resultsContainer.appendChild(movieCard);
    });
};