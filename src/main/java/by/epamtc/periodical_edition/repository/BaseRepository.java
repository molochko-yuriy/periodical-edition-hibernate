package by.epamtc.periodical_edition.repository;

import by.epamtc.periodical_edition.entity.BaseEntity;
import by.epamtc.periodical_edition.exception.RepositoryException;

import java.util.List;

public interface BaseRepository<E extends BaseEntity<Long>> {
    E findById(Long id) throws RepositoryException;
    List<E> findAll() throws RepositoryException;
    boolean add(E e) throws RepositoryException;
    boolean update(E e) throws RepositoryException;
    boolean delete(Long id);
}