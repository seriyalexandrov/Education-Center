package com.aleksandrov.examples.jpa;

import com.aleksandrov.examples.jpa.entities.TestEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.SQLDataException;
import java.util.List;

@Stateless
public class StatelessBean {

    @PersistenceContext(unitName = "NewPersistenceUnit")
    private EntityManager entityManager;

    public TestEntity getById(BigInteger id) throws SQLDataException {
        Query query = entityManager.createNamedQuery("getNameById");
        query.setParameter("id", id);
        List<TestEntity> result = query.getResultList();
        if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new SQLDataException();
        }
    }
}
