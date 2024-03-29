package by.epamtc.periodical_edition.repository.jdbc.impl;

import by.epamtc.periodical_edition.entity.PeriodicalEdition;
import by.epamtc.periodical_edition.enums.PeriodicalEditionType;
import by.epamtc.periodical_edition.enums.Periodicity;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.*;

import by.epamtc.periodical_edition.repository.impl.ContentRepositoryImpl;
import by.epamtc.periodical_edition.repository.impl.ImageRepositoryImpl;
import by.epamtc.periodical_edition.repository.impl.PeriodicalEditionRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalEditionRepositoryImplTest extends BaseRepositoryTest {
//    private final PeriodicalEditionRepository periodicalEditionRepository;
    private final List<PeriodicalEdition> periodicalEditions;
    private final ContentRepositoryImpl contentRepository;
    private final ImageRepositoryImpl imageRepository;
    private  final PeriodicalEditionRepositoryImpl periodicalEditionRepository;
    public PeriodicalEditionRepositoryImplTest() {
        periodicalEditionRepository = new PeriodicalEditionRepositoryImpl();
        periodicalEditions = new ArrayList<>();
        contentRepository = new ContentRepositoryImpl();
        imageRepository = new ImageRepositoryImpl();
//        periodicalEditionRepository = new PeriodicalEditionRepositoryImpl(getConnectionPool());
        periodicalEditions.add(new PeriodicalEdition(1L, 20, "very good", "The Guardian", PeriodicalEditionType.MAGAZINE, Periodicity.WEEKLY));
        periodicalEditions.add(new PeriodicalEdition(2L, 30, "good", "The NY Times", PeriodicalEditionType.NEWSPAPER, Periodicity.MONTHLY));
    }

    @Test
    public void findById_validData_shouldReturn_periodicalEdition() throws RepositoryException {
        //given
        PeriodicalEdition expected = periodicalEditions.get(0);

        //when
        PeriodicalEdition actual = periodicalEditionRepository.findById(1L);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll_validData_shouldReturnPeriodicalEditions() throws RepositoryException {
        //given && when
        final List<PeriodicalEdition> actual = periodicalEditionRepository.findAll();

        //then
        Assert.assertEquals(periodicalEditions, actual);
    }

    @Test
    public void add_validData_shouldAddNewPeriodicalEdition() throws RepositoryException {
        //given
        PeriodicalEdition expected = new PeriodicalEdition(3L, 20, "some description",
                "The Guardian", PeriodicalEditionType.MAGAZINE, Periodicity.WEEKLY);
        PeriodicalEdition actual = new PeriodicalEdition(null, 20, "some description",
                "The Guardian", PeriodicalEditionType.MAGAZINE, Periodicity.WEEKLY);

        //when
        boolean isAdded = periodicalEditionRepository.add(actual);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, periodicalEditionRepository.findById(actual.getId()));
    }

    @Test
    public void update_validData_shouldUpdatePeriodicalEdition() throws RepositoryException {
        //given
        PeriodicalEdition expected = new PeriodicalEdition(2L, 20, "some description", "The Guardian",
                PeriodicalEditionType.MAGAZINE, Periodicity.WEEKLY);
        PeriodicalEdition actual = periodicalEditionRepository.findById(2L);

        //when
        actual.setId(2L);
        actual.setPrice(20);
        actual.setDescription("some description");
        actual.setTitle("The Guardian");
        actual.setPeriodicalEditionType(PeriodicalEditionType.MAGAZINE);
        actual.setPeriodicity(Periodicity.WEEKLY);
        boolean isUpdated = periodicalEditionRepository.update(actual);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, periodicalEditionRepository.findById(actual.getId()));
    }

    @Test
    public void delete_validData_shouldDeletePeriodicalEdition() throws RepositoryException {
        //given
        PeriodicalEdition expected = periodicalEditions.get(0);
        PeriodicalEdition actual = periodicalEditionRepository.findById(1L);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(2, contentRepository.findContentByPeriodicalEditionId(actual.getId()).size());
        Assert.assertEquals(3, imageRepository.findImageByPeriodicalEditionId(actual.getId()).size());

        //when
        boolean isDeleted = periodicalEditionRepository.delete(1L);

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertEquals(0, contentRepository.findContentByPeriodicalEditionId(actual.getId()).size());
        Assert.assertEquals(0, imageRepository.findImageByPeriodicalEditionId(actual.getId()).size());
        Assert.assertNull(periodicalEditionRepository.findById(1L));
    }

//    @Test
//    public void findPeriodicalEditionsBySubscriptionId_validData_shouldReturnPeriodicalEditions(){
//        //given && when
//        List<PeriodicalEdition> actual  = periodicalEditionRepository.findPeriodicalEditionsBySubscriptionId(1L);
//
//        //then
//        Assert.assertEquals(1, actual.size());
//    }
}