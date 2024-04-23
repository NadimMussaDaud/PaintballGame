import dataStructures.Array;
import dataStructures.Iterator;

public interface Team {

    String getName();
    Iterator<Player> getPlayers();
    String getBunker();
    void addBunker(Bunker b);
    Array<Bunker> getBunkers();
    void addPlayer(Player p);
    boolean hasPlayers();
    void removeMember(Player loser);
    boolean hasBunkers();
    void removeBunker(Bunker b);
    boolean hasBunker(Bunker bunker);
    String initialBunker();
}
