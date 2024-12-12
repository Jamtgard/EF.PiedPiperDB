package com.example.piedpiperdb.DAO;

import com.example.piedpiperdb.Entities.Match;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


//AWS GEFP-3
public class MatchDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //Create
            // Skapa en match
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
                System.out.println("Error while saving match: " + e.getMessage());
                if (entityManager != null && transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                return false;
            } finally {
                entityManager.close();
            }
        }
    //AWS GEFP-13
            // hämta en match       Read One/All
        public Match getMatchById(int id) {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            Match matchToReturn = entityManager.find(Match.class, id);
            entityManager.close();
            return matchToReturn;
        }
            // hämta lista med matcher
        public static List<Match> getAllMatches() {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            List<Match> listToReturn = new ArrayList<>();
            TypedQuery<Match> result = entityManager.createQuery("FROM Match", Match.class);
            listToReturn.addAll(result.getResultList());
            return listToReturn;
        }

    //AWS GEFP-13

    // Update
        public void updateMatch(Match matchToUpdate) {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            try{
                transaction = entityManager.getTransaction();
                transaction.begin();
                if(entityManager.contains(matchToUpdate)) {
                    entityManager.persist(matchToUpdate);
                } else {
                    Match updatedMatch = entityManager.merge(matchToUpdate);
                }
                entityManager.merge(matchToUpdate);
                transaction.commit();
            } catch (Exception e) {
                System.out.println("Error while updating match: " + e.getMessage());
                if(entityManager != null && transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            } finally {
                entityManager.close();
            }
        }
    //AWS GEFP-13
    // Delete
        public void deleteMatch(Match matchToDelete) {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            try{
                transaction = entityManager.getTransaction();
                transaction.begin();
                if(!entityManager.contains(matchToDelete)) {
                    matchToDelete = entityManager.merge(matchToDelete);
                }
                entityManager.remove(matchToDelete);
                transaction.commit();
            } catch (Exception e) {
                System.out.println("Error while deleting match: " + e.getMessage());
                if(entityManager != null && transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            }
            finally {
                entityManager.close();
            }
        }
        //
        public boolean deleteMatchById(int id) {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            try{
                transaction = entityManager.getTransaction();
                transaction.begin();
                Match matchToDelete = entityManager.find(Match.class, id);
                entityManager.remove(entityManager.contains(matchToDelete) ? matchToDelete : entityManager.merge(matchToDelete));
                transaction.commit();
                return true;
            } catch (Exception e) {
                System.out.println("Error while deleting match: " + e.getMessage());
                if(entityManager != null && transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                return false;
            }
            finally {
                entityManager.close();
            }
        }
}
