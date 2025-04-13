package com.java.mcs.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.mcs.model.Movie;
import com.java.mcs.service.MovieService;

@Controller
public class MovieController {

	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping("/")
	public String home(Model model) {
		List<Movie> movies = movieService.getTrendingMovies();
		List<Long> favoriteIds = movieService.getFavoriteMovies().stream().map(f -> f.getId())
				.collect(Collectors.toList());

		model.addAttribute("movies", movies);
		model.addAttribute("favoriteIds", favoriteIds);
		return "home";
	}

	@GetMapping("/favorites")
	public String favorites(Model model) {
		model.addAttribute("favorites", movieService.getFavoriteMovies());
		return "favorites";
	}

	@PostMapping("/favorite/add")
	public String addToFavorites(@ModelAttribute Movie movie, @RequestParam(required = false) String redirect) {
		movieService.addToFavorites(movie);

		// Redirect to original page if provided
		if (redirect != null && !redirect.isEmpty()) {
			return "redirect:" + redirect + "?success=true";
		}

		return "redirect:/";
	}

	@PostMapping("/favorite/remove/{id}")
	public String removeFromFavorites(@PathVariable Long id) {
		movieService.removeFromFavorites(id);
		return "redirect:/favorites";
	}

	@GetMapping("/movie/{id}")
	public String movieDetails(@PathVariable Long id, Model model) {
	    Movie movie = movieService.getMovieById(id);
	    boolean isFavorite = movieService.isFavorite(id);
	    model.addAttribute("movie", movie);
	    model.addAttribute("isFavorite", isFavorite);
	    return "details";
	}
}
