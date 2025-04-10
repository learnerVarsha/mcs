package com.java.mcs.model;

import lombok.Data;

@Data
public class Movie {
    private Long id;
    private String title;
    private String overview;
    private String release_date;
    private Double vote_average;
    private String poster_path;
}
