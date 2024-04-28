import dataStructures.Array;
import dataStructures.Iterator;

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
    boolean belongsTo(String bunker);
    boolean hasFunds(String bunker, String type);
    void create(String bunker, String type);
    void changeTurns();
    Array<Player> players();
    boolean isPosition(int x, int y);
    boolean hasPlayer(int x, int y);
    boolean isMovingOff(int x, int y, String direction);
    boolean isFreePosition(int x, int y,String direction);
    Player getPlayer(int x,int y);
    Iterator<String> move(Player p, Array<String> dir);
    String[][] attack();
    String getWinner();
    boolean bunkerWithTeam(String bunker);
}
