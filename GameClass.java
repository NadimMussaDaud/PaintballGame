import java.util.LinkedList;
import java.util.Queue;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;


public class GameClass implements Game{


    private int teamsNumber, width, height, bunkersNumber;
    private Bunker[][] map;
    private Array<Bunker> bunkers;
    private Array<Team> teams;
    private Queue<String> teamTurns;

    public GameClass(int width, int height, int teamsNumber, int bunkersNumber){
        this.width = width;
        this.height = height;
        this.teamsNumber = teamsNumber;
        this.bunkersNumber = bunkersNumber;
        bunkers = new ArrayClass<>(bunkersNumber);
        teams = new ArrayClass<>(teamsNumber);
        map = new BunkerClass[width+1][height+1];//(0,0) not used
        teamTurns = new LinkedList<>();
    }

    public void addBunker(int x, int y, int treasure, String name){
        Bunker b = new BunkerClass(x, y, treasure, name);
        map[x][y] = b;
        bunkers.insertLast(b);
    }

    public void addTeam(String name, String bunker){
        teams.insertLast(new TeamClass(name, bunker));
        teamTurns.add(name);
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

    public Array<Array<String>> status(){
        //Array containing all the arrays of dimensions, bunkers, active teams
        Array<Array<String>> status = new ArrayClass<>();
        Array<String> activeTeams = new ArrayClass<>();
        Array<String> dim = new ArrayClass<>();
        Array<String> bunkers = new ArrayClass<>();

        status.insertLast(dim); status.insertLast(bunkers); status.insertLast(activeTeams);

        //dimensions
        dim.insertLast(String.valueOf(width)); dim.insertLast(String.valueOf(height));

        //Bunkers
        //TODO: Ver addTeam() em bunker
        Iterator<Bunker> itB = this.bunkers.iterator(); 
        while(itB.hasNext()){
            String b = itB.next().getName();
            bunkers.insertLast(b);
            bunkers.insertLast(getBunkersOwner(b));
        }

        //teams
        Iterator<Team> itT = this.teams.iterator();
        while(itT.hasNext()){
            activeTeams.insertLast(itT.next().getName());
        }

        return status;

    }

    public String getTurnTeamName(){
        return teamTurns.peek();
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

    public String[][] map(){
        String[][] mapStrings = new String[width+1][height+1];
        for(int x = 1; x < width+1; x++ ){
            for(int y = 1; y < height+1; y++){
               Player p = getPlayer(x,y);
               if(isEmpty(x, y)){
                    mapStrings[x][y] = ".";
               }
               else if(bunkerIn(x,y)){
                    Bunker b = map[x][y];
                    if(getTeam(getTurnTeamName()).getBunker().equals(b.getName())){
                        if(b.isFree()){
                            mapStrings[x][y] = "B";
                        }else
                        {
                            mapStrings[x][y] = "O";
                        }
                    }else
                        {
                            mapStrings[x][y] = ".";
                        }
               }
               
               else if( p != null){
                    if(p.getTeam().equals(getTurnTeamName())){
                        mapStrings[x][y] = "P";
                    }
               }
            }
        }
        return mapStrings;
    }

    
    private Team getTeam(String team) {
        Iterator<Team> it = teams.iterator();
        while(it.hasNext()){
            Team t = it.next();
            if(t.getName().equals(team))
                return t;
        }
        return null;
    }

    private Player getPlayer(int x, int y) {
        Iterator<Team> itT = teams.iterator();

        while(itT.hasNext()){
            Iterator<Player> itP = itT.next().getPlayers();
            while(itP.hasNext()){
                Player p = itP.next();
                if(p.getX() == x && p.getY() == y)
                    return p;
            }
        }
        return null;
    }

    private boolean bunkerIn(int x, int y) {
        return !isEmpty(x, y);
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

    private void changeTurns(){
        String team = teamTurns.remove();
        teamTurns.add(team);
    }

    //TODO: Ver addTeam() em Bunker
    private String getBunkersOwner(String bunker){
        Iterator<Team> it = teams.iterator();
        String owner = "without owner";

        while(it.hasNext()){
            Team t = it.next();
            if(t.getBunker().equals(bunker))
                owner = t.getName();
        }
        return owner;
    }
}
