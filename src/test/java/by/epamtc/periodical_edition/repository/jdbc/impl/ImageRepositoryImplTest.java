package by.epamtc.periodical_edition.repository.jdbc.impl;

import by.epamtc.periodical_edition.entity.Image;
import by.epamtc.periodical_edition.entity.PeriodicalEdition;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.BaseRepositoryTest;
import by.epamtc.periodical_edition.repository.ImageRepository;
import by.epamtc.periodical_edition.repository.impl.ImageRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImageRepositoryImplTest extends BaseRepositoryTest {
    private final List<Image> images;
    private final ImageRepository imageRepository;
//    private final BaseRepository<Image> imageRepository;

    public ImageRepositoryImplTest() {
        images = new ArrayList<>();
        imageRepository = new ImageRepositoryImpl();
//        imageRepository = new ImageRepositoryImpl(getConnectionPool());
        images.add(new Image(1L, PeriodicalEdition.builder().id(1L).build(), "D/im/cont"));
        images.add(new Image(2L, PeriodicalEdition.builder().id(2L).build(), "D/if/nok"));
        images.add(new Image(3L, PeriodicalEdition.builder().id(1L).build(), "A/im/cont"));
        images.add(new Image(4L, PeriodicalEdition.builder().id(2L).build(), "A/if/nok"));
        images.add(new Image(5L, PeriodicalEdition.builder().id(1L).build(), "B/im/cont"));
        images.add(new Image(6L, PeriodicalEdition.builder().id(2L).build(), "B/if/nok"));
    }

    @Test
    public void findById_validData_shouldReturnImage() throws RepositoryException {
        //given
        Image expected = images.get(0);

        //when
        Image actual = imageRepository.findById(1L);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll_validData_shouldReturnImages() throws RepositoryException {
        //given && when
        final List<Image> actual = imageRepository.findAll();

        //then
        Assert.assertEquals(images, actual);
    }

    @Test
    public void add_validData_shouldAddNewImage() throws RepositoryException {
        //given
        Image expected = new Image(7L, PeriodicalEdition.builder().id(1L).build(), "D/if/nok/k");
        Image actual = new Image(null, PeriodicalEdition.builder().id(1L).build(), "D/if/nok/k");

        //when
        boolean isAdded = imageRepository.add(actual);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, imageRepository.findById(actual.getId()));
    }

    @Test
    public void update_validData_ShouldReturnUpdateImage() throws RepositoryException {
        //given
        Image expected = new Image(2L, PeriodicalEdition.builder().id(1L).build(), "D/inf/nok/k");
        Image actual = imageRepository.findById(2L);

        //when
        actual.setId(2L);
        actual.setImagePath("D/inf/nok/k");
        boolean isUpdated = imageRepository.update(actual);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, imageRepository.findById(actual.getId()));
    }

    @Test
    public void delete_validData_shouldDeleteImage() throws RepositoryException {
        //given
        Image expected = images.get(0);
        Image actual = imageRepository.findById(1L);
        Assert.assertEquals(expected, actual);

        //when
        boolean isDeleted = imageRepository.delete(1L);

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(imageRepository.findById(1L));
    }
//
//    @Test
//    public void findImageByPeriodicalEditionId_validData_shouldReturnImagesOfCertainPeriodicalEdition() {
//        //given
//        List<Image> expected = images.stream()
//                .filter(image -> image.getPeriodicalEditionId() == 1L)
//                .collect(Collectors.toList());
//
//        //when
//        List<Image> actual = imageRepository.findImageByPeriodicalEditionId(1L);
//
//        //then
//        Assert.assertEquals(expected, actual);
//    }
}