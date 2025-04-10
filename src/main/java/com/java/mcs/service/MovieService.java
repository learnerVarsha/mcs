package com.java.mcs.service;

import com.java.mcs.entity.FavoriteMovie;
import com.java.mcs.model.Movie;
import com.java.mcs.repository.FavoriteMovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final FavoriteMovieRepository repository;

    public MovieService(RestTemplate restTemplate, FavoriteMovieRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public List<Movie> getTrendingMovies() {
        String url = apiUrl + "/trending/movie/week?api_key=" + apiKey;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.getBody().get("results");
        return results.stream().map(this::mapToMovie).collect(Collectors.toList());
    }

    private Movie mapToMovie(Map<String, Object> data) {
        Movie movie = new Movie();
        movie.setId(Long.valueOf(data.get("id").toString()));
        movie.setTitle((String) data.get("title"));
        movie.setOverview((String) data.get("overview"));
        movie.setRelease_date((String) data.get("release_date"));
        movie.setVote_average(Double.valueOf(data.get("vote_average").toString()));
        movie.setPoster_path((String) data.get("poster_path"));
        return movie;
    }

    public void addToFavorites(Movie movie) {
        repository.save(new FavoriteMovie(movie.getId(), movie.getTitle(), movie.getPoster_path()));
    }

    public void removeFromFavorites(Long id) {
        repository.deleteById(id);
    }

    public List<FavoriteMovie> getFavoriteMovies() {
        return repository.findAll();
    }
}

