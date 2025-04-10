package com.java.mcs.controller;

import com.java.mcs.model.Movie;
import com.java.mcs.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movies", movieService.getTrendingMovies());
        return "home";
    }

    @GetMapping("/favorites")
    public String favorites(Model model) {
        model.addAttribute("favorites", movieService.getFavoriteMovies());
        return "favorites";
    }

    @PostMapping("/favorite/add")
    public String addToFavorites(@ModelAttribute Movie movie) {
        movieService.addToFavorites(movie);
        return "redirect:/";
    }

    @PostMapping("/favorite/remove/{id}")
    public String removeFromFavorites(@PathVariable Long id) {
        movieService.removeFromFavorites(id);
        return "redirect:/favorites";
    }
}

