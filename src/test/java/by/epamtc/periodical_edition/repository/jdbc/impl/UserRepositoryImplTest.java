package by.epamtc.periodical_edition.repository.jdbc.impl;

import by.epamtc.periodical_edition.entity.User;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.*;
import by.epamtc.periodical_edition.repository.impl.RoleRepositoryImpl;
import by.epamtc.periodical_edition.repository.impl.SubscriptionRepositoryImpl;
import by.epamtc.periodical_edition.repository.impl.UserRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImplTest extends BaseRepositoryTest {
    private final UserRepositoryImpl userRepository;
    private final List<User> users;
    private final SubscriptionRepositoryImpl subscriptionRepository;
    private final RoleRepositoryImpl roleRepository;

    public UserRepositoryImplTest() {
        users = new ArrayList<>();
        userRepository = new UserRepositoryImpl();
        subscriptionRepository = new SubscriptionRepositoryImpl();
        roleRepository = new RoleRepositoryImpl();
        users.add(new User(1L, "Степанов", "Александр", "stepanow.a@mail.ru", "1111",
                "8684758965", "stepanow.a@mail.ru", 235));
        users.add(new User(2L, "Александров", "Виктор", "alecsandrow.a@mail.ru", "2222",
                "985214", "alecsandrow.a@mail.ru", 25));
        users.add(new User(3L, "Степанов", "Степан", "stepan.a@mail.ru", "3333",
                "9522585", "stepan.a@mail.ru", 235));
        users.add(new User(4L, "Александров", "Андрей", "alecsandr.a@mail.ru", "4444",
                "7892152", "alecsandr.a@mail.ru", 25));
    }

    @Test
    public void findById_validData_shouldReturnUser() throws RepositoryException {
        //given
        User expected = users.get(0);

        //when
        User actual = userRepository.findById(1L);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll_validData_shouldReturnExistUsers() throws RepositoryException {
        //given && when
        final List<User> actual = userRepository.findAll();

        //then
        Assert.assertEquals(users, actual);
    }


    @Test
    public void add_validData_shouldAddNewUser() throws RepositoryException {
        //given
        User expected = new User(5L, "Oleg", "Petrov", "rider", "580236",
                "+375295899663", "oleg.p@mail.ru", 85);
        User actual = new User( null, "Oleg", "Petrov", "rider", "580236",
                "+375295899663", "oleg.p@mail.ru", 85);

        // when
        boolean isAdded = userRepository.add(actual);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, userRepository.findById(actual.getId()));
    }

    @Test
    public void update_validData_shouldUpdateUser() throws RepositoryException {
        //given
        User expected = User.builder().id(2L).lastName("Petrov").firstName("Oleg").login("rider").password("158963").mobilePhone("+375291548544").email("petrov.a@mail.ru").balance(85).build();
        User actual = userRepository.findById(2L);
        Assert.assertEquals(users.get(1), actual);

        //when
        actual.setId(2L);
        actual.setLastName("Petrov");
        actual.setFirstName("Oleg");
        actual.setLogin("rider");
        actual.setPassword("158963");
        actual.setMobilePhone("+375291548544");
        actual.setEmail("petrov.a@mail.ru");
        actual.setBalance(85);
        boolean isUpdated = userRepository.update(actual);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(actual, userRepository.findById(actual.getId()));
    }


    @Test
    public void delete_validData_shouldDeleteUser() throws RepositoryException {
        //given
        User expected = users.get(0);
        User actual = userRepository.findById(1L);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(2, roleRepository.findRolesByUserId(actual.getId()).size());
        Assert.assertEquals(4, subscriptionRepository.findSubscriptionsByUserId(actual.getId()).size());

        // when
        boolean isDeleted = userRepository.delete(actual.getId());

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(userRepository.findById(1L));
        Assert.assertEquals(0, roleRepository.findRolesByUserId(actual.getId()).size());
        Assert.assertEquals(0, subscriptionRepository.findSubscriptionsByUserId(actual.getId()).size());
    }
}