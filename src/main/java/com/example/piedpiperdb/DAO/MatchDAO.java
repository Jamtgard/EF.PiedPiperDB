package com.example.piedpiperdb.DAO;

import com.example.piedpiperdb.Entities.Match;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


//AWS GEFP-3
public class MatchDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //Create
        public boolean saveMatch(Match match) {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            try{
                transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.persist(match);
                transaction.commit();
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                if (entityManager != null && transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                return false;
            } finally {
                entityManager.close();
            }
        }









}
