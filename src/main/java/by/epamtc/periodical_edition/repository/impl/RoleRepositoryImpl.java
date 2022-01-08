package by.epamtc.periodical_edition.repository.impl;

import by.epamtc.periodical_edition.entity.Role;
import by.epamtc.periodical_edition.entity.User;
import by.epamtc.periodical_edition.exception.RepositoryException;
import by.epamtc.periodical_edition.repository.BaseRepository;
import by.epamtc.periodical_edition.repository.RoleRepository;
import by.epamtc.periodical_edition.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImpl implements BaseRepository<Role>, RoleRepository {
    private static final String ID_COLUMN = "id";
    private static final String ROLE_NAME_COLUMN = "roleName";

    private static final String SELECT_ALL_QUERY = "from Role";

    private static final String UPDATE_QUERY = ""
            +" update Role set "
            +" roleName= :roleName where id = : id ";



    private final SessionFactory sessionFactory;

    public RoleRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Role findById(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Role.class, id);
        } catch (Exception ex) {
            throw new RepositoryException("Role not found: " + ex.getMessage());
        }
    }

    @Override
    public List<Role> findAll() throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Role.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Roles not found: " + ex.getMessage());
        }
    }

    @Override
    public boolean add(Role role) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            session.save(role);
            return true;
        } catch (Exception ex) {
            throw new RepositoryException("Role not adding: " + ex.getMessage());
        }
    }

    @Override
    public boolean update(Role role) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, role);
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

    private void constructQuery(Query query, Role role) {
        query.setParameter(ROLE_NAME_COLUMN, role.getRoleName());
        query.setParameter(ID_COLUMN, role.getId());
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Role role = session.get(Role.class, id);
                deleteRoleLinks(role.getUsers(), role );
                session.delete(role);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
            return false;
        }
    }

    private void deleteRoleLinks(List<User> users, Role deletionRole) {
        for (User user : users) {
            List<Role> roles = user.getRoles();
            roles.remove(deletionRole);
        }
    }

    @Override
    public boolean addRoleToUser(Long userId, Long roleId) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                User user = session.get(User.class, userId);
                Role role = session.get(Role.class, roleId);
                List<Role> roles = user.getRoles();
                roles.add(role);
                session.save(user);
                session.getTransaction().commit();
            } catch (Exception ex){
                session.getTransaction().rollback();
            }
            return true;
        } catch (Exception ex) {
            throw new RepositoryException("Role not adding: " + ex.getMessage());
        }
    }

    @Override
    public boolean deleteRoleFromUser(Long userId, Long roleId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try{
                User user = session.get(User.class, userId);
                Role role = session.get(Role.class, roleId);
                List<Role> roles = user.getRoles();
                if(roles.contains(role)) {
                    roles.remove(role);
                    session.getTransaction().commit();
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return false;
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            if(user != null) {
                List<Role> roles =  user.getRoles();
                Hibernate.initialize(roles);
                return roles;
            }
            return new ArrayList<>();
        }
    }
}