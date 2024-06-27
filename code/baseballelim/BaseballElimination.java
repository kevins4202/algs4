import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.ArrayList;

public class BaseballElimination {
    private final int teams;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] gamesLeft;
    private final ArrayList<String> teamNames;
    private FordFulkerson fordFulkerson;
    private boolean trivial;
    private ArrayList<String> trivialCertificate;

    public BaseballElimination(String filename) // create a baseball division from given filename in format specified below
    {
        In in = new In(filename);
        teams = in.readInt();

        wins = new int[teams];
        losses = new int[teams];
        remaining = new int[teams];
        gamesLeft = new int[teams][teams];
        teamNames = new ArrayList<String>();

        for (int i = 0; i < teams; i++) {
            String name = in.readString();
            teamNames.add(name);
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < teams; j++) {
                gamesLeft[i][j] = in.readInt();
                gamesLeft[j][i] = gamesLeft[i][j];
            }
        }

        in.close();
    }
    public int numberOfTeams() // number of teams
    {
        return teams;
    }
    public Iterable<String> teams() // all teams
    {
        return teamNames;
    }
    public int wins(String team) // number of wins for given team
    {
        int index = teamNames.indexOf(team);
        if (index == -1) throw new IllegalArgumentException("This team does not exist");
        return wins[index];
    }

    public int losses(String team) // number of losses for given team
    {
        int index = teamNames.indexOf(team);
        if (index == -1) throw new IllegalArgumentException("This team does not exist");
        return losses[index];
    }
    public int remaining(String team)  // number of remaining games for given team
    {
        int index = teamNames.indexOf(team);
        if (index == -1) throw new IllegalArgumentException("This team does not exist");
        return remaining[index];
    }
    public int against(String team1, String team2) // number of remaining games between team1 and team2
    {
        int index1 = teamNames.indexOf(team1), index2 = teamNames.indexOf(team2);
        if (index1 == -1 || index2 == -1) throw new IllegalArgumentException("This team does not exist");
        return gamesLeft[index1][index2];
    }

    public boolean isEliminated(String team) // is given team eliminated?
    {
        int index = teamNames.indexOf(team);
        if (index == -1) throw new IllegalArgumentException("This team does not exist");

        FlowNetwork network = new FlowNetwork(2 + (teams) * (teams - 1) / 2 + teams);
        int teamToCalculate = teamNames.indexOf(team);

//        System.out.println("TEAM TO CALCULATE: " + teamToCalculate);

        // 0: source
        // 1 to (teams - 1) * teams / 2: matches
        // (teams - 1) * teams / 2 + 1 to (teams - 1) * teams / 2 + teams: teams
        // (teams - 1) * teams / 2 + teams + 1: sink

        trivial = false;
        trivialCertificate = new ArrayList<>();
        boolean eliminated = false;

        for (int team1 = 0; team1 < teams; team1++) {
            if (team1 == teamToCalculate) continue;

//            System.out.println("TEAM: " + team1);

            if (wins[teamToCalculate] + remaining[teamToCalculate] - wins[team1] < 0) {
                eliminated = true;
                trivial = true;
                trivialCertificate.add(teamNames.get(team1));
                continue;
            }

            for (int team2 = team1 + 1; team2 < teams; team2++) {
                if (team2 == teamToCalculate) continue;

//                System.out.println("TEAM2: " + team2);

                int sinkTo = 0;

                for (int i = 0; i < team1; i++) {
                    sinkTo += (teams - 1 - i);
                }

                sinkTo += team2 - team1;

                FlowEdge e1 = new FlowEdge(0, sinkTo, gamesLeft[team1][team2]);
                network.addEdge(e1);

                FlowEdge e21 = new FlowEdge(sinkTo, (teams - 1) * (teams) / 2 + 1 + team1, Double.POSITIVE_INFINITY);
                network.addEdge(e21);

                FlowEdge e22 = new FlowEdge(sinkTo, (teams - 1) * (teams) / 2 + 1 + team2, Double.POSITIVE_INFINITY);
                network.addEdge(e22);
            }
            // sink
            FlowEdge e3 = new FlowEdge((teams - 1) * (teams) / 2 + 1 + team1, (teams - 1) * teams / 2 + 1 + teams, wins[teamToCalculate] + remaining[teamToCalculate] - wins[team1]);
            network.addEdge(e3);
        }

        fordFulkerson = new FordFulkerson(network, 0, (teams - 1) * teams / 2 + teams + 1);

        for (FlowEdge e : network.adj(0)) {
            if (e.flow() != e.capacity()) {
                eliminated = true;
                break;
            }
        }

        return eliminated;
    }
    public Iterable<String> certificateOfElimination(String team) // subset R of teams that eliminates given team; null if not eliminated
    {
        int index = teamNames.indexOf(team);
        if (index == -1) throw new IllegalArgumentException("This team does not exist");

        if(!isEliminated(team)) return null;

        if (trivial) return trivialCertificate;

        ArrayList<String> certificate = new ArrayList<>();

        for (int i = 0; i < teams; i++) if (fordFulkerson.inCut(i)) {
            certificate.add(teamNames.get(i));
        }

        trivial = false;
        return certificate;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
