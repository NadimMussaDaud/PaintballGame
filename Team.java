import dataStructures.Iterator;

public interface Team {

    String getName();
    Iterator<Player> getPlayers();
    String getBunker();

}
