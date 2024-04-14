import dataStructures.Array;
import dataStructures.Iterator;

public interface Game {
    void addBunker(int x, int y, int treasure, String name);
    void addTeam(String name, String bunker);
    public boolean hasBunker(String name);
    public boolean isEmpty(int x, int y);
    boolean hasTeam(String team);
    boolean isOccupiedBunker(String bunker);
    boolean canPlay();
    String getTurnTeamName();
    Array<Array<String>> status();
}
