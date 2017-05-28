import com.lkubicek.model.Player;
import com.lkubicek.model.Players;
import com.lkubicek.model.Team;

import java.util.*;

public class LeagueManager {
    private static Scanner scanner = new Scanner(System.in);
    private static Set<Team> teams = new TreeSet<>();
    private static Set<Player> availablePlayers = new TreeSet<>();

    public static void main(String[] args) {

        Player[] players = Players.load();
        Collections.addAll(availablePlayers, players);
        System.out.printf("There are currently %d registered players.%n", players.length);

        //Start prompt loop
        boolean quitRequest = false;
        String choice;
        do {
            choice = promptMainMenu();

            switch (choice.trim().toLowerCase()) {
                case "create":
                    if (availablePlayers.size() - teams.size() > 1) {
                        System.out.println("What is the team name? ");
                        scanner.nextLine();
                        String name = scanner.nextLine();
                        System.out.println("What is the coach name? ");
                        String coachName = scanner.nextLine();
                        teams.add(new Team(name, coachName));
                        System.out.println("Added team, " + name + ". Coached by " + coachName);
                    } else {
                        System.out.println("There are not enough players!");
                    }
                    break;
                case "add":
                    if (teams.size() > 0) {
                        Team teamToAdd = selectTeam();
                        if (availablePlayers.size() > 0) {
                            System.out.println("");
                            System.out.println("");
                            Player playerToAdd = selectPlayerFrom(availablePlayers);

                            availablePlayers.remove(playerToAdd);
                            teamToAdd.addPlayer(playerToAdd);

                            System.out.printf("%n%n" + playerToAdd.getFirstName() + " " + playerToAdd.getLastName() + " added to team, " + teamToAdd.getName() + "%n%n");
                        } else {
                            System.out.println("");
                            System.out.println("");
                            System.out.println("There are no available players!!!");
                            System.out.println("");
                            System.out.println("");
                        }
                    } else {
                        System.out.println("");
                        System.out.println("");
                        System.out.println("There are no teams!");
                        System.out.println("");
                        System.out.println("");
                    }

                    break;
                case "remove":
                    if (teams.size() > 0) {
                        Team teamToRemove = selectTeam();
                        if (teamToRemove.getPlayerList().size() > 0) {
                            System.out.println("");
                            System.out.println("");
                            Set<Player> playerListForRemove = teamToRemove.getPlayerList();
                            Player playerToRemove = selectPlayerFrom(playerListForRemove);
                            teamToRemove.removePlayer(playerToRemove);
                            availablePlayers.add(playerToRemove);

                            System.out.printf("%n%n" + playerToRemove.getFirstName() + " " + playerToRemove.getLastName() + " removed from team, " + teamToRemove.getName() + " and added back to availabe player pool.%n%n");
                        } else {
                            System.out.printf("%n%nThere are no players on this team!%n%n");
                        }
                    } else {
                        System.out.println("");
                        System.out.println("");
                        System.out.println("There are no teams!");
                        System.out.println("");
                        System.out.println("");
                    }
                    break;
                case "report":
                    if (teams.size() > 0) {
                        Team teamToReport = selectTeam();
                        Map<String, Set<Player>> teamReport = teamToReport.getPlayerReport();
                        System.out.println("");
                        System.out.println("");
                        System.out.println("Report for team: " + teamToReport.toString());
                        System.out.println("=========================================================");
                        System.out.println();
                        Set<Player> shortPlayersToReport = teamReport.get("Short");
                        Set<Player> mediumPlayersToReport = teamReport.get("Medium");
                        Set<Player> tallPlayersToReport = teamReport.get("Tall");

                        System.out.println("Players of heigh range: 35-40 inches (" + shortPlayersToReport.size() + " players)");
                        for (Player player : shortPlayersToReport) {
                            System.out.println("     " + player.toString());
                        }
                        System.out.println();
                        System.out.println("Players of heigh range: 41-46 inches (" + mediumPlayersToReport.size() + " players)");
                        for (Player player : mediumPlayersToReport) {
                            System.out.println("     " + player.toString());
                        }
                        System.out.println();
                        System.out.println("Players of heigh range: 47-50 inches (" + tallPlayersToReport.size() + " players)");
                        for (Player player : tallPlayersToReport) {
                            System.out.println("     " + player.toString());
                        }
                    } else {
                        System.out.println("");
                        System.out.println("");
                        System.out.println("There are no teams!");
                        System.out.println("");
                        System.out.println("");
                    }

                    break;
                case "balance":
                    System.out.println();
                    for (Team team : teams) {
                        Set<Player> playersOnTeam = team.getPlayerList();
                        if (playersOnTeam.size() < 1) {
                            System.out.println("There are no players on the team, " + team.getName() + ".");
                        } else {
                            Map<String, Set<Player>> teamBalanceReport = team.getPlayerReport();
                            Set<Player> shortPlayersBalance = teamBalanceReport.get("Short");
                            Set<Player> mediumPlayersBalance = teamBalanceReport.get("Medium");
                            Set<Player> tallPlayersBalance = teamBalanceReport.get("Tall");
                            int exp = 0;
                            int inexp = 0;
                            for (Player player : playersOnTeam) {
                                if (player.isPreviousExperience()) {
                                    exp++;
                                } else {
                                    inexp++;
                                }
                            }
                            double percentage = (((double) exp) / (inexp + exp)) * 100;
                            System.out.println();
                            System.out.println("Team statistic report for team: " + team.toString());
                            System.out.println("Experienced players: " + Integer.toString(exp) + ".  Inexperienced players: " + Integer.toString(inexp) + ".  " + Double.toString(percentage) + "% experienced players.");
                            System.out.println("Count of players in heigh range -- 35-40 inches: " + Integer.toString(shortPlayersBalance.size()));
                            System.out.println("Count of players in heigh range -- 41-46 inches: " + Integer.toString(mediumPlayersBalance.size()));
                            System.out.println("Count of players in heigh range -- 47-50 inches: " + Integer.toString(tallPlayersBalance.size()));
                            System.out.println();
                        }
                    }
                    break;
                case "roster":
                    if (teams.size() > 0) {
                        Team teamSelectedForRoster = selectTeam();
                        System.out.println();
                        System.out.println("Team roster for " + teamSelectedForRoster.toString());
                        System.out.println("============================================");
                        Set<Player> playerListForRoster = teamSelectedForRoster.getPlayerList();
                        for (Player player : playerListForRoster) {
                            System.out.println("  " + player.toString());
                        }
                    } else {
                        System.out.println("");
                        System.out.println("");
                        System.out.println("There are no teams!");
                        System.out.println("");
                        System.out.println("");
                    }
                    break;
                case "quit":
                    System.out.printf("Thank you for using the application!%n");
                    quitRequest = true;
                    break;
                default:
                    System.out.printf("Please select an option from the list.");
            }
        } while (!quitRequest);
    }

    private static String promptMainMenu() {
        System.out.printf("%n%nMenu%nCreate - Create a new team%n");
        System.out.printf("Add - Add a player to a team%n");
        System.out.printf("Remove - Remove a player from a team%n");
        System.out.printf("Report - View a report of a team by height%n");
        System.out.printf("Balance - View the League Balance Report%n");
        System.out.printf("Roster - View roster%n");
        System.out.printf("Quit - Exits the program%n%n");
        System.out.printf("Select an option: ");
        return scanner.next();
    }

    private static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }


    private static int promptForIndexSelection(String promptMsg, Set set, boolean firstRun) {
        boolean validSelection = false;
        int selectionIndex = 0;
        do {
            System.out.printf(promptMsg);
            if (firstRun) {
                scanner.nextLine();
            }
            String selectionStrIndex = scanner.nextLine();
            if (isInteger(selectionStrIndex, 10)) {
                selectionIndex = Integer.parseInt(selectionStrIndex);
                if (selectionIndex <= set.size() && selectionIndex > 0) {
                    validSelection = true;
                }
            }
            if (!validSelection) {
                System.out.println("Please select a valid index!");
            }
            firstRun = false;
        } while (!validSelection);
        return selectionIndex;
    }

    private static Team selectTeam() {
        int i = 1;
        for (Team team : teams) {
            System.out.println(Integer.toString(i) + ".) " + team.toString());
            i++;
        }
        int teamIndex = promptForIndexSelection("%n%nPlease select a team(n): ", teams, true);
        i = 1;
        Team teamSelected = null;
        for (Team team : teams) {
            if (i == teamIndex) {
                teamSelected = team;
                break;
            }
            i++;
        }
        return teamSelected;
    }

    private static Player selectPlayerFrom(Set<Player> set) {
        int i = 1;
        for (Player player : set) {
            System.out.println(Integer.toString(i) + ".) " + player.toString());
            i++;
        }
        int playerIndex = promptForIndexSelection("%n%nPlease select a player from the list of available players(n): ", set, false);
        i = 1;
        Player playerSelected = null;
        for (Player player : set) {
            if (i == playerIndex) {
                playerSelected = player;
                break;
            }
            i++;
        }
        return playerSelected;
    }
}
