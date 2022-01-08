package by.epamtc.periodical_edition.repository.impl;

import by.epamtc.periodical_edition.entity.Content;
import by.epamtc.periodical_edition.entity.PeriodicalEdition;
import by.epamtc.periodical_edition.entity.Subscription;
import by.epamtc.periodical_edition.entity.User;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.SubscriptionRepository;
import by.epamtc.periodical_edition.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionRepositoryImpl implements BaseRepository<Subscription>, SubscriptionRepository {
    private static final String ID_COLUMN = "id";
    private static final String PRICE_COLUMN = "price";
    private static final String PAYMENT_STATUS_COLUMN = "paymentStatus";

    private static final String UPDATE_QUERY = " update Subscription set "
            + " price = :price, paymentStatus = :paymentStatus where id = :id";

    private static final String SELECT_ALL_QUERY = "from Subscription";

    private final SessionFactory sessionFactory;

    public SubscriptionRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Subscription findById(Long id) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Subscription.class, id);
        } catch (Exception ex) {
            throw new RepositoryException("Subscription not found: " + ex.getMessage());
        }
    }

    @Override
    public List<Subscription> findAll() throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Subscription.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Subscriptions not found: " + ex.getMessage());
        }
    }

    @Override
    public boolean add(Subscription subscription) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            session.save(subscription);
            return true;
        } catch (Exception ex) {
            throw new RepositoryException("Subscription not adding: " + ex.getMessage());
        }
    }

    @Override
    public boolean update(Subscription subscription) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, subscription);
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

    protected void constructQuery(Query query, Subscription subscription) {
        query.setParameter(PRICE_COLUMN, subscription.getPrice());
        query.setParameter(PAYMENT_STATUS_COLUMN, subscription.getPaymentStatus());
        query.setParameter(ID_COLUMN, subscription.getId());
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Subscription subscription = session.get(Subscription.class, id);
                session.delete(subscription);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
            return false;
        }
    }

    @Override
    public List<Subscription> findSubscriptionsByUserId(Long userId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            if(user != null) {
                List<Subscription> subscriptions = user.getSubscriptions();
                Hibernate.initialize(subscriptions);
                return subscriptions;
            }
            return new ArrayList<>();
        }
    }

    @Override
    public List<Subscription> findSubscriptionsThatIncludePeriodicalEditionById(Long periodicalEditionId) {
//        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
//            PeriodicalEdition periodicalEdition = session.get(PeriodicalEdition.class, periodicalEditionId);
//            if(periodicalEdition != null) {
//                List<Subscription> subscriptions = periodicalEdition.getS;
//                Hibernate.initialize(subscriptions);
//                return subscriptions;
//            }
            return new ArrayList<>();
    }
}
