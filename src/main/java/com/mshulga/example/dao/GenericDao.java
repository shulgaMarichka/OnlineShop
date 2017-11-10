package com.mshulga.example.dao;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public abstract class GenericDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    public abstract Class<T> getPersistentClass();

    public T getObjectById(Long id) {
       return entityManager.find(getPersistentClass(), id);
    }

    public List<T> getAll() {
        return entityManager.createQuery("from " + getPersistentClass().getName()).getResultList();
    }

    public T create(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public boolean remove(T entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
            return true;
        }
        return false;
    }

}
