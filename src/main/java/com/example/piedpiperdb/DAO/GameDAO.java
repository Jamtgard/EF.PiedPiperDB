package com.example.piedpiperdb.DAO;

import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.Entities.Team;
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

    //GEFP-22-SA, ändra inparameter så String är med
    //Update
    public void updateGame(Game gameToUpdate,String newName){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        System.out.println("c-------------------------------------------------------------------");
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            if(entityManager.contains(gameToUpdate)){
                System.out.println("Spelet finns i poolen");
                //gameToUpdate.setGameName(newName);
                entityManager.merge(gameToUpdate);//Helt nya saker som ska sparas


            } else {
                System.out.println("Finns inte i poolen");
                gameToUpdate.setGameName(newName);
                Game revivedGame = entityManager.merge(gameToUpdate);
                System.out.println(revivedGame.getGameId() + " is alive");

                System.out.println(revivedGame.getGameName() + " is alive");
                System.out.println("g---------------------------------------------------------------------------------------------------------------");

            }
            entityManager.merge(gameToUpdate);
            transaction.commit();
        } catch (Exception e){
            System.out.println("d----------------------------------------------------------------------------------------------------");
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    //GEFP-22-SA
    public void updatePlayersBeforeDelete(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        System.out.println("updatePlayersBeforeDelete id: " + id);

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Game gameToDelete = entityManager.find(Game.class, id);

            System.out.println(gameToDelete.getGameId() + " is alive");
            System.out.println(gameToDelete.getGameName() + " is alive");
            System.out.println(gameToDelete.getPlayers().size() + " antal spelare för spelet");

            List<Player>playersToRemove2 = new ArrayList<>();

            for (int i = 0; i < gameToDelete.getPlayers().size(); i++) {
                System.out.println(gameToDelete.getPlayers().get(i).getFirstName() + " in for-loop");
                System.out.println(gameToDelete.getPlayers().get(i).getId() + " in for-loop");
                playersToRemove2.add(entityManager.find(Player.class, gameToDelete.getPlayers().get(i).getId()));
            }

            for(Player player : playersToRemove2){
                System.out.println(player.getFirstName());
                gameToDelete.getPlayers().remove(player);
                player.setGameId(null);
                entityManager.merge(player);
            }


            entityManager.merge(gameToDelete);
            System.out.println("Efter merges");

            transaction.commit();


        }catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        }finally {
            entityManager.close();
        }

    }

    public void updateMatchesBeforeDelete(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        System.out.println("updateMatchesBeforeDelete id: " + id);

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Game gameToDelete = entityManager.find(Game.class, id);

            List<Match> matchesToRemove = new ArrayList<>();

            for (int i = 0; i < gameToDelete.getMatches().size(); i++) {
                System.out.println(gameToDelete.getMatches().get(i).getMatchId() + " in for-loop");
                System.out.println(gameToDelete.getMatches().get(i).getMatchName() + " in for-loop");
                matchesToRemove.add(entityManager.find(Match.class, gameToDelete.getMatches().get(i).getMatchId()));
            }


            for(Match match : matchesToRemove){
                gameToDelete.getMatches().remove(match);
                match.setGameId(null);
                entityManager.merge(match);
            }


            entityManager.merge(gameToDelete);
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        }finally {
            entityManager.close();
        }

    }
    public void updateTeamsBeforeDelete(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        System.out.println("updateTeamsBeforeDelete id: " + id);

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Game gameToDelete = entityManager.find(Game.class, id);

            List<Team> teamsToRemove = new ArrayList<>();

            for (int i = 0; i < gameToDelete.getTeams().size(); i++) {
                System.out.println(gameToDelete.getTeams().get(i).getTeamId() + " in for-loop");
                System.out.println(gameToDelete.getTeams().get(i).getTeamName() + " in for-loop");
                teamsToRemove.add(entityManager.find(Team.class, gameToDelete.getTeams().get(i).getTeamId()));
            }


            for(Team team : teamsToRemove){
                gameToDelete.getMatches().remove(team);
                team.setGameId(null);
                entityManager.merge(team);
            }


            entityManager.merge(gameToDelete);
            transaction.commit();

        }catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        }finally {
            entityManager.close();
        }

    }


    //Delete
    //GEFP-22-SA, ändra mellan begin och commit, det gamla ligger inom /**/
    public boolean deleteGameById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        System.out.println("deleteGameById id: " + id);
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Game gameToDelete = entityManager.find(Game.class, id);

            entityManager.remove(entityManager.contains(gameToDelete) ? gameToDelete : entityManager.merge(gameToDelete));


            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("b-----------------------------------------------------------------------------------------------------------------------");
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
