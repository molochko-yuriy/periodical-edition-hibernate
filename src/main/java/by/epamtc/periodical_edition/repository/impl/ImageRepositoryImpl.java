package by.epamtc.periodical_edition.repository.impl;

import by.epamtc.periodical_edition.entity.Image;
import by.epamtc.periodical_edition.entity.PeriodicalEdition;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.ImageRepository;
import by.epamtc.periodical_edition.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ImageRepositoryImpl implements BaseRepository<Image>, ImageRepository {
    private static final String ID_COLUMN = "id";
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String SELECT_ALL_QUERY = "from Image";
    private static final String UPDATE_QUERY = ""
            + "update Image set "
            + " imagePath = : imagePath where id = : id";


    private final SessionFactory sessionFactory;

    public ImageRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Image findById(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Image.class, id);
        } catch (Exception ex) {
            throw new RepositoryException("Image not found: " + ex.getMessage());
        }
    }

    @Override
    public List<Image> findAll() throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Image.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Images not found: " + ex.getMessage());
        }
    }

    @Override
    public boolean add(Image image) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            session.save(image);
            return true;
        } catch (Exception ex) {
            throw new RepositoryException("Image not adding: " + ex.getMessage());
        }
    }

    @Override
    public boolean update(Image image) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, image);
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

    private void constructQuery(Query query, Image image) {
        query.setParameter(IMAGE_PATH_COLUMN, image.getImagePath());
        query.setParameter(ID_COLUMN, image.getId());
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Image image = session.get(Image.class, id);
                session.delete(image);
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
    public List<Image> findImageByPeriodicalEditionId(Long periodicalEditionId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            PeriodicalEdition periodicalEdition = session.get(PeriodicalEdition.class, periodicalEditionId);
            if(periodicalEdition != null) {
                List<Image> images = periodicalEdition.getImages();
                Hibernate.initialize(images);
                return images;
            }
            return new ArrayList<>();
        }
    }
}