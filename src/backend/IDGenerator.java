package backend;

public class IDGenerator {
    private static final String TEAM_PREFIX = "T";
    private static final String SHARE_PREFIX = "S";
    private static final int STARTING_NUMBER = 101;
    
    private int teamCounter;
    private int shareCounter;
    
    public IDGenerator() {
        teamCounter = STARTING_NUMBER;
        shareCounter = STARTING_NUMBER;
    }

    public String generateTeamID() {
        String teamID = TEAM_PREFIX + teamCounter;
        teamCounter++;
        return teamID;
    }

    public String generateShareID() {
        String shareID = SHARE_PREFIX + shareCounter;
        shareCounter++;
        return shareID;
    }
}