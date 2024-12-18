package com.example.piedpiperdb.DAO;

import com.example.piedpiperdb.Entities.Game;
import com.example.piedpiperdb.Entities.Match;
import com.example.piedpiperdb.Entities.Player;
import com.example.piedpiperdb.Entities.Team;
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

                if(matchToDelete == null) {
                    System.out.println("Match not found with ID:" + id);
                    return false;
                }

                if(!matchToDelete.getTeams().isEmpty()) {
                    for(Team team : matchToDelete.getTeams()) {
                        team.getMatchesInTeam().remove(matchToDelete);
                        entityManager.merge(team);
                    }
                    matchToDelete.getTeams().clear();
                }

                boolean relationsRemoved = removeMatchRelations(id);
                if(!relationsRemoved) {
                    System.out.println("Failed to remove relations for match");
                    return false;
                }
                if (!matchToDelete.getPlayers().isEmpty()) {
                    for (Player player : matchToDelete.getPlayers()) {
                        player.setMatchId(null); // Nollställ spelarens match-relation
                        entityManager.merge(player); // Uppdatera spelaren
                    }
                }

                entityManager.remove(entityManager.contains(matchToDelete) ? matchToDelete : entityManager.merge(matchToDelete));
                transaction.commit();
                System.out.println("Match removed successfully");
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

        public boolean removeMatchRelations(int matchId) {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;

            try{
                transaction = entityManager.getTransaction();
                transaction.begin();

                Match match = entityManager.find(Match.class, matchId);

                if(match == null) {
                    System.out.println("Match not found wiht id: " + matchId);
                    return false;
                }
                System.out.println("Removing relations for match: " + match.getMatchName());

                if(match.getTeams() != null && !match.getTeams().isEmpty()) {
                    for(Team team : match.getTeams()) {
                        team.getMatchesInTeam().remove(match);
                        entityManager.merge(team);
                    }
                    match.getTeams().clear();
                }

                if(match.getGameId() != null){
                    Game game = match.getGameId();
                    game.getMatches().remove(match);
                    match.setGameId(null);
                    entityManager.merge(game);
                }

                if(match.getPlayers() != null && !match.getPlayers().isEmpty()) {
                    for(Player player : match.getPlayers()) {
                        player.setMatchId(null);
                        entityManager.merge(player);
                    }
                }

                entityManager.merge(match);
                transaction.commit();

                System.out.println("Relations removed for match: " + match.getMatchName());
                return true;

            } catch (Exception e) {
                System.out.println("Error while removing match: " + e.getMessage());
                if(entityManager != null && transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                return false;
            } finally {
                entityManager.close();
            }
        }
}
