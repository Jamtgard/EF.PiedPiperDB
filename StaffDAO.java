package com.example.piedpiperdb.DAO;

import jakarta.persistence.*;
import java.util.List;
import java.util.logging.Logger;
import com.example.piedpiperdb.Entities.Game;
import java.util.ArrayList;
import com.example.piedpiperdb.Entities.Player;



//Anna R
public class StaffDAO {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("piedpiperdb");
    private static final EntityManager Logger LOGGER = Logger.getLogger(StaffDAO.class.getName());

    //lägger till ny personal
    public boolean savePersonal(Staff staff) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(staff);
            transaction.commit();
            LOGGER.info("Staff saved to the date base: " + staff.getName());
            return true;
        } catch (Exception e) {
            LOGGER.severe("Error");
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
    //Hämta en lista över personal
    public List<Staff> getAllStaff() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Staff> staff = null;

        try {
            TypedQuery<Staff> query = entityManager.createQuery("FROM Staff", Staff.class);
            staff = query.getResultList();
        } catch (Exception e) {
            LOGGER.severe("Error");
        } finally {
            entityManager.close();
        }
        return staff;
    }
    //Kontrollera behörighet
    public boolean harPermissionToStaff(String staffName) {
        return "admin".equalsIgnoreCase(staffName);
    }
    private com.example.piedpiperdb.DAO.TeamDAO teamDAO = new com.example.piedpiperdb.DAO.TeamDAO();
    //skapar, visa, tar bort, ändra team
    public boolean createTeam(com.example.piedpiperdb.Entities.Team team) {
        return teamDAO.createTeam(team);
    }
    public List<com.example.piedpiperdb.Entities.Team> getAllTeams() {
        return teamDAO.getAllTeams();
    }
    public boolean deleteTeam(com.example.piedpiperdb.Entities.Team team) {
        return teamDAO.deleteTeamById(team.getTeamId);
    }
    public boolean updateTeam(com.example.piedpiperdb.Entities.Team team) {
        return teamDAO.updateTeam(team);
    }
    //visa, lägga till, uppdatera, ta bort ett spel
    public List<Game> getAllGames() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Game> games = new ArrayList<>();
        try {
            TypedQuery<Game> query = entityManager.createQuery("FROM Game", Game.class);
            games = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            entityManager.close();
        }
        return games;
    }
    public boolean saveGame(Game game) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(game);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error");
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
    public boolean updateGame(int gameId, String newGameName) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Game gameToUpdate = entityManager.find(Game.class, gameId);
            if (gameToUpdate != null) {
                gameToUpdate.setGameName(newGameName);
                entityManager.merge(gameToUpdate);
                transaction.commit();
                return true;
            } else {
                System.out.println("Error");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error");
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
    public boolean deleteGame(int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Game gameToDelete = entityManager.find(Game.class, gameId);
            if (gameToDelete != null) {
                entityManager.remove(gameToDelete);
                transaction.commit();
                return true;
            } else {
                System.out.println("Error");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error");
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
    // Visa, lägga till, uppdatera och ta bort spelare
    public List<Player> getAllPlayers() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Player> players = new ArrayList<>();
        try {
            TypedQuery<Player> query = entityManager.createQuery("FROM Player", Player.class);
            players = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            entityManager.close();
        }
        return players;
    }
    public boolean savePlayer(Player player) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(player);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error");
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
    public boolean updatePlayer(Player player) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Player updatedPlayer = entityManager.find(Player.class, player.getId());
            if (updatedPlayer != null) {
                updatedPlayer.setFirstName(player.getFirstName());
                updatedPlayer.setLastName(player.getLastName());
                updatedPlayer.setEmail(player.getEmail());
                entityManager.merge(updatedPlayer);
                transaction.commit();
                return true;
            } else {
                System.out.println("Error");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error");
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
    public boolean deletePlayer(int playerId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Player playerToDelete = entityManager.find(Player.class, playerId);
            if (playerToDelete != null) {
                entityManager.remove(playerToDelete);
                transaction.commit();
                return true;
            } else {
                System.out.println("Error");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error");
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
}


