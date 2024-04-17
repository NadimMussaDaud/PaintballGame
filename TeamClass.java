import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;

public class TeamClass implements Team{
    
    private String name;
    private Array<Player> players;
    private Array<Bunker> bunkers;
    public TeamClass(String name, Bunker bunker){
        this.name = name;
        this.players = new ArrayClass<>();
        this.bunkers = new ArrayClass<>();
        bunkers.insertLast(bunker);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBunker() {
        return bunkers.get(bunkers.size()-1).getName();
    }

    public Iterator<Player> getPlayers(){
        return players.iterator();
    }

    public void addBunker(Bunker b){
        bunkers.insertLast(b);
    }

    public Array<Bunker> getBunkers(){
        return bunkers;
    }

    public void addPlayer(Player p){
        players.insertLast(p);
    }

    @Override
    public boolean hasPlayers() {
        return players.size() > 0;
    }

}
