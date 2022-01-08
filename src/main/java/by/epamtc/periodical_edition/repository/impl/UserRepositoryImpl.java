package by.epamtc.periodical_edition.repository.impl;

import by.epamtc.periodical_edition.entity.Review;
import by.epamtc.periodical_edition.entity.Subscription;
import by.epamtc.periodical_edition.entity.User;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.UserRepository;
import by.epamtc.periodical_edition.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final String ID_COLUMN = "id";
    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LOGIN_COLUMN = "login";
    private static final String PASSWORD_COLUMN = "password";
    private static final String MOBILE_PHONE_COLUMN = "mobilePhone";
    private static final String EMAIL_COLUMN = "email";
    private static final String BALANCE_COLUMN = "balance";

    private static final String SELECT_ALL_QUERY = "from User";

    private static final String UPDATE_QUERY = ""
            + " update User set "
            + " lastName = :lastName, firstName = :firstName, login = :login, "
            + " password = :password, mobilePhone = :mobilePhone, "
            + " email = :email, balance = :balance where id = :id ";

    private final SessionFactory sessionFactory;

    public UserRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public User findById(Long id) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception ex) {
            throw new RepositoryException("User not found: " + ex.getMessage());
        }
    }

    @Override
    public List<User> findAll() throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, User.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Users not found: " + ex.getMessage());
        }
    }

    @Override
    public boolean add(User user) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            session.save(user);
            return true;
        } catch (Exception ex) {
            throw new RepositoryException("User not adding: " + ex.getMessage());
        }
    }

    @Override
    public boolean update(User user) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, user);
                query.executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
            return false;
        }
    }

    protected void constructQuery(Query query, User user) {
        query.setParameter(LAST_NAME_COLUMN, user.getLastName());
        query.setParameter(FIRST_NAME_COLUMN, user.getFirstName());
        query.setParameter(LOGIN_COLUMN, user.getLogin());
        query.setParameter(PASSWORD_COLUMN, user.getPassword());
        query.setParameter(EMAIL_COLUMN, user.getEmail());
        query.setParameter(BALANCE_COLUMN, user.getBalance());
        query.setParameter(MOBILE_PHONE_COLUMN, user.getMobilePhone());
        query.setParameter(ID_COLUMN, user.getId());
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                User user = session.get(User.class, id);
                deleteSubscriptionLinks(session, user.getSubscriptions());
                deleteReviewLinks(session, user.getReviews());
                session.delete(user);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
            return false;
        }
    }

    private void deleteReviewLinks(Session session, List<Review> reviews){
        for(Review review : reviews) {
            session.delete(review);
        }
    }

    private void deleteSubscriptionLinks(Session session, List<Subscription> subscriptions){
        for(Subscription subscription : subscriptions) {
            session.delete(subscription);
        }
    }
}