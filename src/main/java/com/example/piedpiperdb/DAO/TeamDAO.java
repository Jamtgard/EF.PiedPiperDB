
package com.example.piedpiperdb.DAO;

import jakarta.persistence.*;
import com.example.piedpiperdb.Entities.Team;

import java.util.ArrayList;
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

    // Get a Team by ID

    public Team getTeamById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Team teamToReturn = entityManager.find(Team.class, id);
        entityManager.close();
        return teamToReturn;
    }

    // Get list of Teams

    public List<Team> getAllTeams(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Team> listToReturn = new ArrayList<>();
        TypedQuery<Team> result = entityManager.createQuery("FROM Team", Team.class);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    // Get Teams by Game

    public List<Team> getTeamsByGame (List<Integer> listOfGameIDs){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Team> listToReturn = new ArrayList<>();

        transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            TypedQuery<Team> query = entityManager.createQuery(
                    "SELECT t FROM Team t WHERE t.gameId.game_id IN :gameIDs", Team.class);
            query.setParameter("gameIDs", listOfGameIDs);
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

    // Update Team

    public void updateTeam(Team team){
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
        } catch (Exception e) {
            System.out.println("Error updating Team " + team.getTeamName() + " Message: " + e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    // Delete By Team

    public boolean deleteTeam (Team team){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        transaction = entityManager.getTransaction();
        transaction.begin();

        try {
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

    // Delete By ID

    public boolean deleteTeamById (int id){

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Team teamToDelete = entityManager.find(Team.class, id);
            entityManager.remove(entityManager.contains(teamToDelete) ? teamToDelete : entityManager.merge(teamToDelete));
            transaction.commit();
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

}

