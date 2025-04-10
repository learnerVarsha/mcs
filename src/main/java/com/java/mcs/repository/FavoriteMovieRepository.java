package com.java.mcs.repository;

import com.java.mcs.entity.FavoriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {
}

