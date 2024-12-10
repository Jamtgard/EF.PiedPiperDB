package com.example.piedpiperdb.DAO;

import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.Entities.Team;
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
        if (!removePlayerRelations(id)){
            System.out.println("Could not remove player relations. Player is not deleted.");
            return false;
        }

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Player playerToDelete = entityManager.find(Player.class, id);
            if (playerToDelete == null) {
                System.out.println("Player not found in database.");
                transaction.rollback();
                return false;
            }

            entityManager.remove(entityManager.contains(playerToDelete) ? playerToDelete : entityManager.merge(playerToDelete));
            transaction.commit();
            playerToDelete = entityManager.find(Player.class, id);
            if(playerToDelete == null) {
                System.out.println("Player deleted from DB");
                return true;
            } else {
                System.out.println("Player still exist in DB" + playerToDelete.toString());
                return false;
            }

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

    //GBFP-19-AA
    public boolean removePlayerRelations(int playerId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Player player = entityManager.find(Player.class, playerId);
            if(player == null) {
                System.out.println("Player not found in the DB" + playerId);
                return false;
            } else {
                System.out.println("Removing relations...");
                if (player.getGameId() != null) {
                    Game game = player.getGameId();
                    System.out.println("Removing player from game" + game);
                    game.getPlayers().remove(player);
                    player.setGameId(null);
                    entityManager.merge(game);
                }

                if (player.getTeamId() != null) {
                    Team team = player.getTeamId();
                    System.out.println("Removing player from team" + team);
                    team.getListOfPlayersInTeam().remove(player);
                    player.setTeamId(null);
                    entityManager.merge(team);
                }

/*                if (player.getMatchId() != null) {
                    Match match = player.getMatchId();
                    System.out.println("Removing player from match" + match);
                    //match.getListOfPlayers().remove(player);
                    player.setMatchId(null);
                    entityManager.merge(match);
                }*/

                entityManager.merge(player);
                transaction.commit();
                System.out.println("Player relations removed from DB");
                return true;
            }
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
