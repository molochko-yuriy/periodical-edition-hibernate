package by.epamtc.periodical_edition.repository.impl;

import by.epamtc.periodical_edition.entity.*;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.PeriodicalEditionRepository;
import by.epamtc.periodical_edition.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalEditionRepositoryImpl implements BaseRepository<PeriodicalEdition>, PeriodicalEditionRepository {
    private static final String ID_COLUMN = "id";
    private static final String PRICE_COLUMN = "price";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String TITLE_COLUMN = "title";
    private static final String PERIODICAL_EDITION_TYPE_COLUMN = "periodicalEditionType";
    private static final String PERIODICITY_COLUMN = "periodicity";

    private static final String SELECT_ALL_QUERY = " from PeriodicalEdition ";
    private static final String UPDATE_QUERY = ""
            + " update PeriodicalEdition set "
            + " price = :price, title = :title, description = :description, "
            + "periodicalEditionType = :periodicalEditionType, periodicity = :periodicity"
            + " where id = :id";

    private final SessionFactory sessionFactory;

    public PeriodicalEditionRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public PeriodicalEdition findById(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.get(PeriodicalEdition.class, id);
        } catch (Exception ex) {
            throw new RepositoryException("Periodical Edition not found: " + ex.getMessage());
        }
    }

    @Override
    public List<PeriodicalEdition> findAll() throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, PeriodicalEdition.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Periodical Editions not found: " + ex.getMessage());
        }
    }

    @Override
    public boolean add(PeriodicalEdition periodicalEdition) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            session.save(periodicalEdition);
            return true;
        } catch (Exception ex) {
            throw new RepositoryException("Periodical Edition not adding: " + ex.getMessage());
        }
    }

    @Override
    public boolean update(PeriodicalEdition periodicalEdition) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, periodicalEdition);
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

    private void constructQuery(Query query, PeriodicalEdition periodicalEdition) {
        query.setParameter(PERIODICITY_COLUMN, periodicalEdition.getPeriodicity());
        query.setParameter(ID_COLUMN, periodicalEdition.getId());
        query.setParameter(TITLE_COLUMN, periodicalEdition.getTitle());
        query.setParameter(DESCRIPTION_COLUMN, periodicalEdition.getDescription());
        query.setParameter(PRICE_COLUMN, periodicalEdition.getPrice());
        query.setParameter(PERIODICAL_EDITION_TYPE_COLUMN, periodicalEdition.getPeriodicalEditionType());
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                PeriodicalEdition periodicalEdition = session.get(PeriodicalEdition.class, id);
                deleteReviewLinks(session, periodicalEdition.getReviews());
                deleteImageLinks(session, periodicalEdition.getImages());
                deleteContentLinks(session, periodicalEdition.getContents());
                session.delete(periodicalEdition);
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

    private void deleteImageLinks(Session session, List<Image> images){
        for (Image image : images) {
            session.delete(image);
        }
    }

    private void deleteContentLinks(Session session, List<Content> contents){
        for (Content content : contents) {
            session.delete(content);
        }
    }

    @Override
    public List<PeriodicalEdition> findPeriodicalEditionsBySubscriptionId(Long subscriptionId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Subscription subscription = session.get(Subscription.class, subscriptionId);
//            if (subscription != null) {
//                List<PeriodicalEdition> periodicalEditions = subscription.getP;
//                Hibernate.initialize(periodicalEditions);
//                return periodicalEditions;
//            }
            return new ArrayList<>();
    }
}