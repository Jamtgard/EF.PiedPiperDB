package com.example.piedpiperdb.DAO;

import jakarta.persistence.*;
import com.example.piedpiperdb.Entities.Game;

import java.util.ArrayList;
import java.util.List;

//GEFP-6-SA
public class GameDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //Create
    public boolean saveGame(Game game){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction tx = null;
        try{
            tx = em.getTransaction();
            tx.begin();
            em.persist(game);
            tx.commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(em != null && tx != null && tx.isActive()){
                tx.rollback();
            }
            return false;
        }
    }

    //Read
    public Game getGameById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Game gameToReturn = entityManager.find(Game.class, id);
            transaction.commit();
            return gameToReturn;
        }catch(Exception e){
            if(entityManager != null && transaction != null && transaction.isActive()){
                System.out.println(e.getMessage());
            }
            return null;
        }finally {
            entityManager.close();
        }
    }

    public List<Game> getAllGames(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Game> listToReturn = new ArrayList<>();
        TypedQuery<Game> result = entityManager.createQuery("FROM Game", Game.class);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    //Update
    public void updateGame(Game gameToUpdate){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if(entityManager.contains(gameToUpdate)){
                System.out.println("Spelet finns i poolen");
                entityManager.persist(gameToUpdate);//Helt nya saker som ska sparas

            } else {
                System.out.println("Finns inte i poolen");
                Game revivedGame = entityManager.merge(gameToUpdate);
                System.out.println(revivedGame.getGame_id() + " is alive");
            }
            entityManager.merge(gameToUpdate);
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    //Delete
    public boolean deleteGameById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Game gameToDelete = entityManager.find(Game.class, id);
            entityManager.remove(entityManager.contains(gameToDelete) ? gameToDelete : entityManager.merge(gameToDelete));
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        }
        finally {
            entityManager.close();
        }
    }

}
