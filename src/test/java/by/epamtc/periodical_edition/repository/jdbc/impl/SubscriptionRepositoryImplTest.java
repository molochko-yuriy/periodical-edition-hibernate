package by.epamtc.periodical_edition.repository.jdbc.impl;

import by.epamtc.periodical_edition.entity.Subscription;
import by.epamtc.periodical_edition.entity.User;
import by.epamtc.periodical_edition.enums.PaymentStatus;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.BaseRepositoryTest;
import by.epamtc.periodical_edition.repository.ContentRepository;
import by.epamtc.periodical_edition.repository.impl.ContentRepositoryImpl;
import by.epamtc.periodical_edition.repository.impl.SubscriptionRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionRepositoryImplTest extends BaseRepositoryTest {
    private final SubscriptionRepositoryImpl subscriptionRepository;
//    private final SubscriptionRepository subscriptionRepository;
    private final List<Subscription> subscriptions;
    private final ContentRepository contentRepository;

    public SubscriptionRepositoryImplTest() {
        subscriptionRepository = new SubscriptionRepositoryImpl();
        subscriptions = new ArrayList<>();
        contentRepository = new ContentRepositoryImpl();
//        subscriptionRepository = new SubscriptionRepositoryImpl();
        subscriptions.add(new Subscription(1L, 28, PaymentStatus.PAID, User.builder().id(1L).build()));
        subscriptions.add(new Subscription(2L, 42,  PaymentStatus.UNPAID, User.builder().id(1L).build()));
        subscriptions.add(new Subscription(3L, 41,  PaymentStatus.PAID, User.builder().id(3L).build()));
        subscriptions.add(new Subscription(4L, 32,  PaymentStatus.UNPAID, User.builder().id(4L).build()));
        subscriptions.add(new Subscription(5L, 42, PaymentStatus.PAID, User.builder().id(1L).build()));
        subscriptions.add(new Subscription(6L, 52,  PaymentStatus.UNPAID, User.builder().id(2L).build()));
        subscriptions.add(new Subscription(7L, 72,  PaymentStatus.PAID, User.builder().id(3L).build()));
        subscriptions.add(new Subscription(8L, 92, PaymentStatus.UNPAID, User.builder().id(4L).build()));
        subscriptions.add(new Subscription(9L, 2,  PaymentStatus.PAID, User.builder().id(1L).build()));
        subscriptions.add(new Subscription(10L, 62,  PaymentStatus.UNPAID, User.builder().id(2L).build()));
        subscriptions.add(new Subscription(11L, 48,  PaymentStatus.PAID, User.builder().id(3L).build()));
    }

    @Test
    public void findById_validData_shouldReturnUser() throws RepositoryException {
        //given
        Subscription expected = subscriptions.get(0);

        //when
        Subscription actual = subscriptionRepository.findById(1L);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll_validData_shouldReturnUsers() throws RepositoryException {
        //given && when
        List<Subscription> actual = subscriptionRepository.findAll();

        //then
        Assert.assertEquals(subscriptions, actual);
    }

    @Test
    public void add_validData_shouldAddNewSubscription() throws RepositoryException {
        //given
        Subscription expected = new Subscription(12L, 30, PaymentStatus.PAID , User.builder().id(1L).build());
        Subscription actual = new Subscription(null, 30, PaymentStatus.PAID, User.builder().id(1L).build());

        // when
        boolean isAdded = subscriptionRepository.add(actual);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, subscriptionRepository.findById(actual.getId()));
    }

    @Test
    public void update_validData_shouldUpdateSubscription() throws RepositoryException {
        //given
        Subscription expected = new Subscription(2L, 30, PaymentStatus.PAID, User.builder().id(1L).build());
        Subscription actual = subscriptionRepository.findById(2L);
        Assert.assertEquals(subscriptions.get(1), actual);

        //when
        actual.setPrice(30);
        actual.setPaymentStatus(PaymentStatus.PAID);
        boolean isUpdated = subscriptionRepository.update(expected);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, subscriptionRepository.findById(actual.getId()));
    }

    @Test
    public void delete_validData_shouldDeleteSubscription() throws RepositoryException {
        //given
        Subscription expected = subscriptions.get(0);
        Subscription actual = subscriptionRepository.findById(1L);

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(1, contentRepository.findContentBySubscriptionId(actual.getId()).size());

        //when
        boolean isDeleted = subscriptionRepository.delete(actual.getId());

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(subscriptionRepository.findById(1L));
        Assert.assertEquals(0, contentRepository.findContentBySubscriptionId(actual.getId()).size());
    }

//    @Test
//    public void findSubscriptionsThatIncludePeriodicalEditionById() {
//        //given && when
//        List<Subscription> actual = subscriptionRepository.findSubscriptionsThatIncludePeriodicalEditionById(1L);
//
//        //then
//        Assert.assertEquals(2, actual.size());
//    }
}