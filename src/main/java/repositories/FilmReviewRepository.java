package repositories;


import models.Film;
import models.FilmReview;

import java.util.List;

public interface FilmReviewRepository {
    List<FilmReview> findAll(Film film);
    FilmReview save(FilmReview filmsReview);
}
