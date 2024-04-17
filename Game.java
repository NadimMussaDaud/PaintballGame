import dataStructures.Array;

public interface Game {
    void addBunker(int x, int y, int treasure, String name);
    void addTeam(String name, String bunker);
    boolean hasBunker(String name);
    boolean isEmpty(int x, int y);
    boolean hasTeam(String team);
    boolean isOccupiedBunker(String bunker);
    boolean canPlay();
    String getTurnTeamName();
    Array<Array<String>> status();
    String[][] map();
    Array<Bunker> teamBunkers();
    boolean belongsTo(String bunker, String turnTeamName);
    boolean hasFunds(String bunker, String type);
    void create(String bunker, String type);
    void changeTurns();
}
