package by.epamtc.periodical_edition.repository.impl;

import by.epamtc.periodical_edition.entity.Review;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.ReviewRepository;
import by.epamtc.periodical_edition.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class ReviewRepositoryImpl implements ReviewRepository {
    private static final String ID_COLUMN = "id";
    private static final String RATING_COLUMN = "rating";
    private static final String USER_COMMENT_COLUMN = "userComment";

    private static final String SELECT_ALL_QUERY = "from Review";
    private static final String UPDATE_QUERY = ""
            + " update Review set "
            + " userComment = :userComment, rating = :rating where id = : id";

    private final SessionFactory sessionFactory;

    public ReviewRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Review findById(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Review.class, id);
        } catch (Exception ex) {
            throw new RepositoryException("Review not found: " + ex.getMessage());
        }
    }

    @Override
    public List<Review> findAll() throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Review.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Reviews not found: " + ex.getMessage());
        }
    }

    @Override
    public boolean add(Review review) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            session.save(review);
            return true;
        } catch (Exception ex) {
            throw new RepositoryException("Review not adding: " + ex.getMessage());
        }
    }

    @Override
    public boolean update(Review review) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, review);
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

    private void constructQuery(Query query, Review review) {
        query.setParameter(RATING_COLUMN, review.getRating());
        query.setParameter(ID_COLUMN, review.getId());
        query.setParameter(USER_COMMENT_COLUMN, review.getUserComment());
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Review review = session.get(Review.class, id);
                session.delete(review);
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
    public List<Review> findReviewByPeriodicalEditionId(Long periodicalEditionId) {
        return null;
    }

    @Override
    public List<Review> findReviewByUserId(Long userId) {
        return null;
    }
}