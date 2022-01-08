package by.epamtc.periodical_edition.repository.jdbc.impl;

import by.epamtc.periodical_edition.entity.PeriodicalEdition;
import by.epamtc.periodical_edition.entity.Review;
import by.epamtc.periodical_edition.entity.User;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.BaseRepositoryTest;
import by.epamtc.periodical_edition.repository.impl.ReviewRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryImplTest extends BaseRepositoryTest {
//    private final ReviewRepository reviewRepository;
    private final List<Review> reviews;
    private final BaseRepository<Review> reviewRepository;

    public ReviewRepositoryImplTest() {
//        reviewRepository = new ReviewRepositoryImpl(getConnectionPool());
        reviewRepository = new ReviewRepositoryImpl();
        reviews = new ArrayList<>();
        reviews.add(new Review(1L, "good", 5, User.builder().id(1L).build(), PeriodicalEdition.builder().id(1L).build()));
        reviews.add(new Review(2L, "bad", 4, User.builder().id(2L).build(), PeriodicalEdition.builder().id(2L).build()));
        reviews.add(new Review(3L, "bad", 1, User.builder().id(3L).build(), PeriodicalEdition.builder().id(1L).build()));
        reviews.add(new Review(4L, "bad", 2, User.builder().id(4L).build(), PeriodicalEdition.builder().id(2L).build()));
        reviews.add(new Review(5L, "bad", 4,User.builder().id(1L).build(), PeriodicalEdition.builder().id(1L).build()));
        reviews.add(new Review(6L, "bad", 3, User.builder().id(2L).build(), PeriodicalEdition.builder().id(2L).build()));
    }

    @Test
    public void findById_validData_shouldReturnReview() throws RepositoryException {
        //given
        Review expected = reviews.get(0);

        //when
        Review actual = reviewRepository.findById(1L);

        //then
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findAll_validData_shouldReturnReviews() throws RepositoryException {
        //given && when
        final List<Review> actual = reviewRepository.findAll();

        // then
        Assert.assertEquals(actual, reviews);
    }

    @Test
    public void add_validData_shouldAddNewReview() throws RepositoryException {
        //given
        Review expected = new Review(7L, "good", 5, User.builder().id(1L).build(), PeriodicalEdition.builder().id(1L).build());
        Review actual = new Review(null, "good", 5, User.builder().id(1L).build(), PeriodicalEdition.builder().id(1L).build());

        //when
        boolean isAdded = reviewRepository.add(actual);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, reviewRepository.findById(actual.getId()));
    }

    @Test
    public void update_validData_shouldUpdateReview() throws RepositoryException {
        //given
        Review expected = new Review(2L, "good", 5, User.builder().id(1L).build(), PeriodicalEdition.builder().id(1L).build());
        Review actual = reviewRepository.findById(2L);
        Assert.assertEquals(reviews.get(1), actual);

        //when
        actual.setUserComment("good");
        actual.setRating(5);
        boolean isUpdated = reviewRepository.update(expected);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, reviewRepository.findById(actual.getId()));
    }

    @Test
    public void delete_validData_shouldDeleteReview() throws RepositoryException {
        //given
        Review expected = reviews.get(0);
        Review actual = reviewRepository.findById(1L);
        Assert.assertEquals(expected, actual);

        //when
        boolean isDeleted = reviewRepository.delete(1L);

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(reviewRepository.findById(1L));
    }
}