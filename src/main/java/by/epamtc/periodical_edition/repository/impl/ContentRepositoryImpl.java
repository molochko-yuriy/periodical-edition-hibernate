package by.epamtc.periodical_edition.repository.impl;

import by.epamtc.periodical_edition.entity.*;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.ContentRepository;
import by.epamtc.periodical_edition.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ContentRepositoryImpl implements BaseRepository<Content>, ContentRepository {
    private static final String START_DATE_COLUMN = "startDate";
    private static final String EXPIRATION_DATE_COLUMN = "expirationDate";
    private static final String ID_COLUMN = "id";
    private static final String PRICE_COLUMN = "price";

    private static final String SELECT_ALL_QUERY = "from Content";
    private static final String UPDATE_QUERY = ""
            + "update Content set"
            + " startDate = :startDate, expirationDate = :expirationDate,"
            + " price = :price where id = :id ";

    private final SessionFactory sessionFactory;

    public ContentRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Content findById(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Content.class, id);
        } catch (Exception ex) {
            throw new RepositoryException("Content not found: " + ex.getMessage());
        }
    }

    @Override
    public List<Content> findAll() throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Content.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Contents not found: " + ex.getMessage());
        }
    }

    @Override
    public boolean add(Content content) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            session.save(content);
            return true;
        } catch (Exception ex) {
            throw new RepositoryException("Content not adding: " + ex.getMessage());
        }
    }

    @Override
    public boolean update(Content content) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, content);
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

    private void constructQuery(Query query, Content content) {
        query.setParameter(START_DATE_COLUMN, content.getStartDate());
        query.setParameter(ID_COLUMN, content.getId());
        query.setParameter(EXPIRATION_DATE_COLUMN, content.getExpirationDate());
        query.setParameter(PRICE_COLUMN, content.getPrice());
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Content content = session.get(Content.class, id);
                session.delete(content);
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
    public List<Content> findContentBySubscriptionId(Long subscriptionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Subscription subscription = session.get(Subscription.class, subscriptionId);
            if (subscription != null) {
                List<Content> contents = subscription.getContents();
                Hibernate.initialize(contents);
                return contents;
            }
            return new ArrayList<>();
        }
    }

    @Override
    public List<Content> findContentByPeriodicalEditionId(Long periodicalEditionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            PeriodicalEdition periodicalEdition = session.get(PeriodicalEdition.class, periodicalEditionId);
            if (periodicalEdition != null) {
                List<Content> contents = periodicalEdition.getContents();
                Hibernate.initialize(contents);
                return contents;
            }
            return new ArrayList<>();
        }
    }

    @Override
    public boolean deletePeriodicalEditionFromSubscription(Long subscriptionId, Long periodicalEditionId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            session.getTransaction().begin();
//            try {
//                Subscription subscription = session.get(Subscription.class, subscriptionId);
//                PeriodicalEdition periodicalEdition = session.get(PeriodicalEdition.class, periodicalEditionId);
//                List<PeriodicalEdition> periodicalEditions = subscription.getP;
//                if (periodicalEditions.contains(periodicalEdition)) {
//                    periodicalEditions.remove(periodicalEdition);
//                    session.getTransaction().commit();
//                    return true;
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                session.getTransaction().rollback();
//            }
//        }
        return false;
    }
}