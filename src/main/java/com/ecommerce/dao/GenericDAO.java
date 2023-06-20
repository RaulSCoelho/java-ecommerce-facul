package com.ecommerce.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.ecommerce.dao.interfaces.IGenericDAO;

import java.util.List;

public class GenericDAO<T> implements IGenericDAO<T> {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
  private final Class<T> entityClass;

  public GenericDAO(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  protected EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public List<T> findAll() {
    EntityManager em = getEntityManager();
    CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
    criteriaQuery.from(entityClass);
    List<T> entities = em.createQuery(criteriaQuery).getResultList();
    em.close();
    return entities;
  }

  public T findById(Long id) {
    EntityManager em = getEntityManager();
    T entity = em.find(entityClass, id);
    em.close();
    return entity;
  }

  public <TT> List<TT> findByColumn(Class<TT> entityClass, String columnName, Object value) {
    EntityManager em = getEntityManager();
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<TT> criteriaQuery = cb.createQuery(entityClass);
    Root<TT> root = criteriaQuery.from(entityClass);
    criteriaQuery.select(root).where(cb.equal(root.get(columnName), value));
    List<TT> entities = em.createQuery(criteriaQuery).getResultList();
    em.close();
    return entities;
  }

  public List<T> findByColumn(String columnName, Object value) {
    EntityManager em = getEntityManager();
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = cb.createQuery(entityClass);
    Root<T> root = criteriaQuery.from(entityClass);
    criteriaQuery.select(root).where(cb.equal(root.get(columnName), value));
    List<T> entities = em.createQuery(criteriaQuery).getResultList();
    em.close();
    return entities;
  }

  public T findOneByColumn(String columnName, Object value) {
    List<T> entities = findByColumn(columnName, value);
    if (!entities.isEmpty()) {
      return entities.get(0);
    }
    return null;
  }

  public void create(T entity) {
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
    em.close();
  }

  public void update(T entity) {
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    em.merge(entity);
    em.getTransaction().commit();
    em.close();
  }

  public void delete(T entity) {
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    em.remove(em.merge(entity));
    em.getTransaction().commit();
    em.close();
  }
}
