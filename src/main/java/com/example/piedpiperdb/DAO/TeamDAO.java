
package com.example.piedpiperdb.DAO;


import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.Player;
import jakarta.persistence.*;
import com.example.piedpiperdb.Entities.Team;

import java.util.ArrayList;
import java.util.List;

// GEFP-11-SJ
public class TeamDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // Create Team
    public boolean createTeam(Team team) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            List<Player> managedPlayers = new ArrayList<>();
            for (Player player : team.getListOfPlayersInTeam()) {
                Player managedPlayer = entityManager.find(Player.class, player.getId());
                /*
                if (managedPlayer == null) {
                    throw new IllegalArgumentException("Player with ID " + player.getId() + " not found in database.");
                }

                 */

                managedPlayer.setTeamId(team);
                entityManager.merge(managedPlayer);
                managedPlayers.add(managedPlayer);

            }
            team.setListOfPlayersInTeam(managedPlayers);

            entityManager.persist(team);
            transaction.commit();

            return true;

        } catch (Exception e) {
            System.out.println("Error creating Team: " + team.getTeamName() + " Message: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;

        } finally {
            entityManager.close();
        }
    }

    // Read - By ID
    public Team getTeamById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Team teamToReturn = entityManager.find(Team.class, id);
        entityManager.close();
        return teamToReturn;
    }

    // Read - Get All
    public List<Team> getAllTeams(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        transaction = entityManager.getTransaction();
        transaction.begin();
        List<Team> listToReturn = new ArrayList<>();
        TypedQuery<Team> result = entityManager.createQuery("FROM Team", Team.class);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    // Read - By Game
    public List<Team> getTeamsByGame (List<Integer> listOfGameIDs){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Team> listToReturn = new ArrayList<>();
        try {
            TypedQuery<Team> query = entityManager.createQuery(
                    "SELECT t FROM Team t WHERE t.gameId.gameId IN :gameIds", Team.class);
            query.setParameter("gameIds", listOfGameIDs);
            listToReturn.addAll(query.getResultList());
        } catch (Exception e) {

            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }

        } finally {
            entityManager.close();
        }
        return listToReturn;
    }

    // Update - By Team
    public boolean updateTeam(Team team) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Team managedTeam = entityManager.find(Team.class, team.getTeamId());
            if (managedTeam == null) {
                throw new IllegalArgumentException("Team with ID " + team.getTeamId() + " not found in database.");
            }

            // Update team details
            managedTeam.setTeamName(team.getTeamName());
            managedTeam.setGameId(team.getGameId());

            // Synchronize players
            List<Player> newPlayers = team.getListOfPlayersInTeam();
            List<Player> existingPlayers = new ArrayList<>(managedTeam.getListOfPlayersInTeam());

            for (Player existingPlayer : existingPlayers) {
                if (!newPlayers.contains(existingPlayer)) {
                    existingPlayer.setTeamId(null);
                    entityManager.merge(existingPlayer); // Update player in database
                }
            }

            for (Player newPlayer : newPlayers) {
                if (!existingPlayers.contains(newPlayer)) {
                    Player managedPlayer = entityManager.find(Player.class, newPlayer.getId());
                    managedPlayer.setTeamId(managedTeam);
                    managedTeam.getListOfPlayersInTeam().add(managedPlayer);
                    entityManager.merge(managedPlayer); // Update player in database
                }
            }

            entityManager.merge(managedTeam);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Delete - By Team
    public boolean deleteTeam(Team teamToDelete) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            deleteTeamRelations(entityManager, teamToDelete);

            entityManager.remove(entityManager.contains(teamToDelete) ? teamToDelete : entityManager.merge(teamToDelete));
            transaction.commit();

            System.out.println("Team " + teamToDelete.getTeamName() + " deleted successfully");
            return true;

        } catch (Exception e) {
            System.out.println("Error deleting team by ID. Message: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    /*    public boolean deleteTeam (Team team){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            deleteTeamRelations(team.getTeamId());
            entityManager.remove(entityManager.contains(team) ? team : entityManager.merge(team));
            transaction.commit();
            System.out.println("Team " + team.getTeamName() + " deleted successfully");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }*/

    // Delete - By ID
    public boolean deleteTeamById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Team teamToDelete = entityManager.find(Team.class, id);
            if (teamToDelete == null) {
                System.out.println("Team with ID " + id + " not found.");
                return false;
            }

            deleteTeamRelations(entityManager, teamToDelete);

            entityManager.remove(entityManager.contains(teamToDelete) ? teamToDelete : entityManager.merge(teamToDelete));
            transaction.commit();

            System.out.println("Team " + teamToDelete.getTeamName() + " deleted successfully");
            return true;

        } catch (Exception e) {
            System.out.println("Error deleting team by ID. Message: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Delete relations
    private void deleteTeamRelations(EntityManager entityManager, Team team) {
        if (team == null) {
            System.out.println("Team is null, no relations to delete.");
            return;
        }

        if (team.getMatchesInTeam() != null && !team.getMatchesInTeam().isEmpty()) {

            List<Match> matchesToRemove = new ArrayList<>(team.getMatchesInTeam());

            for (Match match : matchesToRemove) {
                match.getTeams().remove(team);
                team.getMatchesInTeam().remove(match);
                team.setMatchId(null);
                entityManager.merge(match);
            }
            team.getMatchesInTeam().clear();
        }

        if (team.getGameId() != null) {
            Game game = team.getGameId();
            game.getTeams().remove(team);
            team.setGameId(null);
            entityManager.merge(game);
        }

        if (team.getListOfPlayersInTeam() != null && !team.getListOfPlayersInTeam().isEmpty()) {

            List<Player> playersInTeam = new ArrayList<>(team.getListOfPlayersInTeam());

            for (Player player : playersInTeam) {
                player.setTeamId(null);
                team.getListOfPlayersInTeam().remove(player);
                entityManager.merge(player);
            }
            team.getListOfPlayersInTeam().clear();
        }
    }


// Functional
//----------------------------------------------------------------------------------------------------------------------

    public boolean isTeamNameUnique(String teamName){

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        String query = "SELECT COUNT(t) FROM Team t WHERE t.teamName = :teamName";

        try {
            TypedQuery<Long> result = entityManager.createQuery(query, Long.class);
            result.setParameter("teamName", teamName);
            Long count = result.getSingleResult();
            return count == 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
}

