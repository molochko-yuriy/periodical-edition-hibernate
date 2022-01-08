package by.epamtc.periodical_edition.repository.jdbc.impl;

import by.epamtc.periodical_edition.entity.Content;
import by.epamtc.periodical_edition.entity.PeriodicalEdition;
import by.epamtc.periodical_edition.entity.Subscription;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.BaseRepositoryTest;
import by.epamtc.periodical_edition.repository.impl.ContentRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContentRepositoryImplTest extends BaseRepositoryTest {
    private final List<Content> contents;
//    private final BaseRepository<Content> contentRepository;
    private final ContentRepositoryImpl contentRepository;

    public ContentRepositoryImplTest() {
        contents = new ArrayList<>();
        contentRepository = new ContentRepositoryImpl();
//        contentRepository = new ContentRepositoryImpl(getConnectionPool());
        contents.add(new Content(1L, LocalDate.of(2021, 6, 5), LocalDate.of(2021, 7, 5), 20, Subscription.builder().id(1L).build(),
                PeriodicalEdition.builder().id(1L).build()));
        contents.add(new Content(2L, LocalDate.of(2021, 7, 6), LocalDate.of(2021, 8, 6), 30, Subscription.builder().id(2L).build(),
                PeriodicalEdition.builder().id(2L).build()));
        contents.add(new Content(3L, LocalDate.of(2021, 8, 7), LocalDate.of(2021, 9, 7), 40, Subscription.builder().id(3L).build(),
                PeriodicalEdition.builder().id(1L).build()));
        contents.add(new Content(4L, LocalDate.of(2021, 9, 8), LocalDate.of(2021, 10, 8), 50, Subscription.builder().id(4L).build(),
                PeriodicalEdition.builder().id(2L).build()));
    }

    @Test
    public void findById_validData_shouldReturnContent() throws RepositoryException {
        //given
        Content expected = contents.get(0);

        //when
        Content actual = contentRepository.findById(1L);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll_validData_shouldReturnContents() throws RepositoryException {
        //given && when
        final List<Content> actual = contentRepository.findAll();

        //then
        Assert.assertEquals(contents, actual);
    }

    @Test
    public void add_validData_shouldAddNewContent() throws RepositoryException {
        // given
        Content expected = new Content(5L, LocalDate.of(2020, 10, 5),
                LocalDate.of(2020, 11, 5), 20, Subscription.builder().id(1L).build(),
                PeriodicalEdition.builder().id(1L).build());
        Content actual = new Content(null, LocalDate.of(2020, 10, 5),
                LocalDate.of(2020, 11, 5), 20, Subscription.builder().id(1L).build(),
                PeriodicalEdition.builder().id(1L).build());

        //when
        boolean isAdded = contentRepository.add(actual);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, contentRepository.findById(actual.getId()));
    }

    @Test
    public void update_validData_shouldUpdateContent() throws RepositoryException {
        //given
        Content expected = new Content(2L, LocalDate.of(2020, 10, 5),
                LocalDate.of(2020, 11, 5), 20, Subscription.builder().id(1L).build(),
                PeriodicalEdition.builder().id(1L).build());
        Content actual = contentRepository.findById(2L);

        //when
        actual.setId(2L);
        actual.setStartDate(LocalDate.of(2020, 10, 5));
        actual.setExpirationDate(LocalDate.of(2020, 11, 5));
        actual.setPrice(20);
        boolean isUpdated = contentRepository.update(actual);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, contentRepository.findById(actual.getId()));
    }

    @Test
    public void delete_validData_shouldDeleteContent() throws RepositoryException {
        //given
        Content expected = contents.get(0);
        Content actual = contentRepository.findById(1L);
        Assert.assertEquals(expected, actual);

        //when
        boolean isDeleted = contentRepository.delete(1L);

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(contentRepository.findById(1L));
    }
//
//    @Test
//    public void deletePeriodicalEditionFromSubscription_validData_shouldDeletePeriodicalEdition() {
//        //given
//        List<Content> contents = contentRepository.findContentBySubscriptionId(3L);
//        Assert.assertEquals(1, contents.size());
//
//        //when
//        boolean isDeleted = contentRepository.deletePeriodicalEditionFromSubscription(3L, 1L);
//
//        //then
//        Assert.assertTrue(isDeleted);
//        Assert.assertEquals(0, contentRepository.findContentBySubscriptionId(3L).size());
//    }
}