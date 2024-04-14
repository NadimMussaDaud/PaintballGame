import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;


public class GameClass implements Game{


    private int teamsNumber, width, height, bunkersNumber;
    private Bunker[][] map;
    private Array<Bunker> bunkers;
    private Array<Team> teams;

    public GameClass(int width, int height, int teamsNumber, int bunkersNumber){
        this.width = width;
        this.height = height;
        this.teamsNumber = teamsNumber;
        this.bunkersNumber = bunkersNumber;
        bunkers = new ArrayClass<>(bunkersNumber);
        teams = new ArrayClass<>(teamsNumber);
        map = new BunkerClass[width+1][height+1];
    }

    public void addBunker(int x, int y, int treasure, String name){
        Bunker b = new BunkerClass(x, y, treasure, name);
        map[x][y] = b;
        bunkers.insertLast(b);
    }

    public void addTeam(String name, String bunker){
        teams.insertLast(new TeamClass(name, bunker));
    }

    // TODO: Refazer o metodo. Fazer com que receba posições e verifique no mapa
    public boolean hasBunker(String name) {
        Iterator<Bunker> it = bunkers.iterator();
        while(it.hasNext()){
            if (it.next().getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty(int x, int y) {
       return map[x][y] == null;
    }

    @Override
    public boolean hasTeam(String team) {
        Iterator<Team> it = teams.iterator();
        while(it.hasNext()){
            if (it.next().getName().equals(team)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isOccupiedBunker(String bunker) {
        Iterator<Team> it = teams.iterator();
        while(it.hasNext()){
            if (it.next().getBunker().equals(bunker)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canPlay() {
        return teamsCreated() > 2;
    }

    private int teamsCreated(){
        int count = 0;
        Iterator<Team> it = teams.iterator();
        while(it.hasNext()){
            if (!it.next().equals(null)) {
                count += 1;
            }
        }
        return count;
    }

    
}
