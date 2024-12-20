
package com.example.piedpiperdb.DAO;

import com.example.piedpiperdb.DAO.JavaFXActions.TeamActions;
import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.View.AlertBox;
import jakarta.persistence.*;
import com.example.piedpiperdb.Entities.Team;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// GEFP-11-SJ
public class TeamDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // Create Team
    public boolean createTeam(Team team){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(team);
            transaction.commit();
            System.out.println("Team created successfully");
            return true;

        } catch (Exception e) {
            System.out.println("Error creating Team: " + team.getTeamName() + " Message: " + e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
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
    public boolean updateTeam(Team team){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (entityManager.contains(team)){
                entityManager.persist(team);
            } else {
                Team updatedTeam = entityManager.merge(team);
            }
            entityManager.merge(team);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error updating Team " + team.getTeamName() + " Message: " + e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Delete - By Team
    public boolean deleteTeam (Team team){
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
        }
    }

    // Delete - By ID
    public boolean deleteTeamById (int id){

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {

            transaction = entityManager.getTransaction();
            transaction.begin();

            Team teamToDelete = entityManager.find(Team.class, id);
            deleteTeamRelations(teamToDelete.getTeamId());

            entityManager.remove(entityManager.contains(teamToDelete) ? teamToDelete : entityManager.merge(teamToDelete));
            transaction.commit();
            System.out.println("Team " + teamToDelete.getTeamName() + " deleted successfully");
            return true;

        } catch (Exception e) {
            System.out.println("Error deleting team by ID. Message: " + e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Delete relations
    public boolean deleteTeamRelations(int teamId){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Team team = entityManager.find(Team.class, teamId);

            if (team == null){
                AlertBox.displayAlertBox("Team not found", "There is no Team registered with ID: " + teamId);
                return false;
            }

            if (team.getMatchesInTeam() != null){
                List<Match> matchesToRemove = new ArrayList<>(team.getMatchesInTeam());
                for (Match match : matchesToRemove) {

                    match.getTeams().remove(team);
                    team.getMatchesInTeam().remove(match);
                    team.setMatchId(null);
                    entityManager.merge(team);

                }
                team.getMatchesInTeam().clear();
            }

            if (team.getGameId() != null){

                Game game = team.getGameId();
                game.getTeams().remove(team);
                team.setGameId(null);
                entityManager.merge(team);

            }

            if (team.getListOfPlayersInTeam() != null) {
                List<Player> playersInTeam = team.getListOfPlayersInTeam();

                Iterator<Player> iterator = playersInTeam.iterator();
                while (iterator.hasNext()) {

                    Player player = iterator.next();
                    iterator.remove();
                    player.setTeamId(null);
                    entityManager.merge(player);

                }
            }

            entityManager.remove(team);
            //entityManager.merge(team);
            transaction.commit();
            return true;

        } catch (Exception e){
            AlertBox.displayAlertBox("Error", "Error while removing relations from Team.");
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        }finally {
            entityManager.close();
        }

    }


// Functional
//----------------------------------------------------------------------------------------------------------------------

    public boolean isTeamNameUnique(String teamName){

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        String query = "SELECT COUNT(*) FROM Team t WHERE t.teamName = :teamName";

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

    private static boolean validateInputTeamName (String teamName) {
        List<Team> allOtherTeams = allOtherTeams(teamName);

        for (Team team : allOtherTeams) {
            if (teamName == null || teamName.isEmpty()) {
                AlertBox.displayAlertBox("Error", "Team name is required.");
                return false;
            }
            if (!TeamActions.isTeamNameUnique(teamName)) {
                AlertBox.displayAlertBox("Error", "Team name already exists. Choose another.");
                return false;
            }
        }
        return true;
    }

    private static List<Team> allOtherTeams(String teamName){
        List<Team> allTeams = TeamActions.getAllTeams();


        List<Team> allOtherTeams = allTeams.stream()
                .filter(team -> !team.getTeamName().equalsIgnoreCase(teamName))
                .toList();

        return allOtherTeams;
    }
}

