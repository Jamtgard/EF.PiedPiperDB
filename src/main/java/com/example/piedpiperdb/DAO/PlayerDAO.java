package com.example.piedpiperdb.DAO;

import com.example.piedpiperdb.Entities.Player;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//GEFP-12-AA
public class PlayerDAO {
    //CRUD-opperationer (Create - Read - Update - Delete)

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //Create
    public boolean savePlayer(Player player) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(player);
            transaction.commit();
            System.out.println("Player saved to the DB");
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

    //Read one/ Read all
    public Player getPlayer(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Player playerToReturn = entityManager.find(Player.class, id);
        entityManager.close();
        System.out.println(playerToReturn);
        return playerToReturn;
    }

    public List<Player> getAllPlayers() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        transaction = entityManager.getTransaction();
        transaction.begin();
        List<Player> listToReturn = new ArrayList<>();
        TypedQuery<Player> query = entityManager.createQuery("FROM Player", Player.class);
        listToReturn.addAll(query.getResultList());
        return listToReturn;
    }

    //GEFP-19-AA
    public List<Player> getAllPlayersFromSelectedGame(List<Integer> gameIds) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Player> listToReturn = new ArrayList<>();

        transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            TypedQuery<Player> query = entityManager.createQuery(
                    "SELECT p FROM Player p WHERE p.gameId.game_id IN :gameIds", Player.class);
            query.setParameter("gameIds", gameIds);
            listToReturn.addAll(query.getResultList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
        return listToReturn;
    }

    //Update
    public void updatePlayer(Player playerToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            if(entityManager.contains(playerToUpdate)) {
                System.out.println("Player to update found in DB");
                entityManager.persist(playerToUpdate);
            } else {
                System.out.println("Player to update not found in DB");
                Player revivedPlayer = entityManager.merge(playerToUpdate);
                System.out.println(revivedPlayer.getId() + " is alive");
            }
            entityManager.merge(playerToUpdate);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    //Delete
    public void deletePlayer(Player player) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entityManager.contains(player) ? player : entityManager.merge(player));
            transaction.commit();
            System.out.println("Player deleted from DB");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public boolean deletePlayerById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Player playerToDelete = entityManager.find(Player.class, id);
            entityManager.remove(entityManager.contains(playerToDelete) ? playerToDelete : entityManager.merge(playerToDelete));
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
