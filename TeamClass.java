import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;

public class TeamClass implements Team{
    
    private String name, bunker;
    private Array<Player> players;

    public TeamClass(String name, String bunker){
        this.bunker = bunker;
        this.name = name;
        this.players = new ArrayClass<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBunker() {
        return bunker;
    }

    public Iterator<Player> getPlayers(){
        return players.iterator();
    }

}
