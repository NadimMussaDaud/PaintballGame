import java.util.LinkedList;
import java.util.Queue;

import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;


public class GameClass implements Game{


    public static final String GREEN = "green";
    public static final String BLUE = "blue";
    public static final String RED = "red";
    public static final String NORTH = "north";
    public static final String SOUTH = "south";
    public static final String WEST = "west";
    public static final String EAST = "east";

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
        map = new BunkerClass[width+1][height+1]; //(0,0) not used
        teamTurns = new LinkedList<>();
    }

    public void addBunker(int x, int y, int treasure, String name){
        Bunker b = new BunkerClass(x, y, treasure, name);
        map[x][y] = b;
        bunkers.insertLast(b);
    }

    public void addTeam(String name, String bunker){
        teams.insertLast(new TeamClass(name, getBunker(bunker)));
        teamTurns.add(name);
        getBunker(bunker).addTeam(name);
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

    public Array<Bunker> teamBunkers(){
        return getTeam(getTurnTeamName()).getBunkers();
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

    /**
     * @Pre-conditions: @param type.equals(GREEN||BLUE||RED)
     */
    public void create(String bunker, String type){
        Bunker b = getBunker(bunker);
        Team team = getTeam(b.getTeam());
        Player p = null;
        
        switch (type) {
            case GREEN -> {
                p = new GreenPlayer(team.getName(), b.getX(), b.getY());
            }
            case BLUE -> {
                p = new BluePlayer(team.getName(), b.getX(), b.getY());
            }
            case RED -> {
                p = new RedPlayer(team.getName(), b.getX(), b.getY());
            }
        }
        team.addPlayer(p);
        b.decreaseTreasure(p.cost());  
    }


    @Override
    public boolean isOccupiedBunker(String bunker) {
        Bunker b = getBunker(bunker);
        Player p = getPlayer(b.getX(),b.getY());

        if(p != null){
            if(getTeam(p.getTeam()).hasBunker(b))
                return true;
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


    public boolean hasPlayer(int x, int y){
        return getPlayer(x, y) != null;
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

    public Player getPlayer(int x, int y) {
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
        return teamsCreated() > 1;
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

    public void changeTurns(){
        String team = teamTurns.remove();
        teamTurns.add(team);
        //adds 1 Coin to all bunkers
        addCoin();
    }

    private void addCoin() {
        Iterator<Bunker> it = bunkers.iterator();
        while(it.hasNext())
            it.next().increaseTreasure();
    }

    private Bunker getBunker(String name){
        Iterator<Bunker> it = bunkers.iterator();
        while(it.hasNext()){
            Bunker b = it.next();
            if(b.getName().equals(name))
                return b;
        }
        return null;
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

    @Override
    public boolean belongsTo(String bunker) {
        return getTeam(getTurnTeamName()).hasBunker(getBunker(bunker));
    }

    @Override
    public boolean hasFunds(String bunker, String type) {
        int price = 0;
        switch (type) {
            case RED -> price = RedPlayer.COST;
            case BLUE -> price = BluePlayer.COST;
            case GREEN -> price = GreenPlayer.COST;
        }
        return getBunker(bunker).getTreasure() >= price;
    }

    @Override
    public Array<Player> players() {
        Array<Player> array = new ArrayClass<>();
        Iterator<Player> it = getTeam(getTurnTeamName()).getPlayers();

        while(it.hasNext()){
            array.insertLast(it.next());
        }
        return array;
    }

    @Override
    public boolean isPosition(int x, int y) {
        return  (x > 0 && x <= width && y > 0 && y <= height);
    }

    
    @Override
    public boolean isMovingOff(int x, int y,String direction) {
        int totalX  = x, totalY = y;

            switch (direction) {
                case NORTH -> totalY --;
                case WEST -> totalX --;
                case EAST -> totalX ++;
                case SOUTH -> totalY ++;
            }
        
        return !isPosition(totalX, totalY);
    }

    @Override
    public boolean isFreePosition(int x, int y, String direction) {
        int totalX  = x, totalY = y;

            switch (direction) {
                case NORTH -> totalY --;
                case WEST -> totalX --;
                case EAST -> totalX ++;
                case SOUTH -> totalY ++;
            }
        Player p = getPlayer(totalX, totalY);
        if (p != null){
            return !getPlayer(totalX, totalY).getTeam().equals(getPlayer(x, y).getTeam());
        }
        return true;
    }

    @Override
    public String move(Player p,String dir) {
        int x = p.getX();
        int y = p.getY();
        
        int totalX  = x, totalY = y;

            switch (dir) {
                case NORTH -> totalY --;
                case WEST -> totalX --;
                case EAST -> totalX ++;
                case SOUTH -> totalY ++;
            }
        

        if(bunkerIn(totalX, totalY)){ // map position with bunker
            Bunker b = map[totalX][totalY];
            
            //TODO: VER o que é um bunker estar Free
            // se é sem pessoas ou sem team...
//se não for um bunker da equipa do jogador
            if(!isOccupiedBunker(b.getName()) && !getTeam(p.getTeam()).hasBunker(b) ){ // free bunker from another team
                //Bunker seized
                
                //TODO: (IMPORTANTE) Verificar bunker atraves da teams e não do bunker directamente
                
                Team oldTeam = getTeam(b.getTeam());
                
                getTeam(p.getTeam()).addBunker(b);
                b.addTeam(p.getTeam());
                
                if(oldTeam != null){
                    oldTeam.removeBunker(b);
                    if(!oldTeam.hasPlayers() && !oldTeam.hasBunkers()){
                        removeTeam(oldTeam);
                    }
                }

                changeTurns();
                p.move(dir);
                return String.format("Bunker seized.\n%s player in position (%d, %d)",p.getType(),p.getX(),p.getY());
            }
            else if(!getTeam(getTurnTeamName()).hasBunker(b)){ // occupied bunker
                Player defender = getPlayer(totalX, totalY);
                Player winner = fight(defender,p);
                // seize the opponents bunker
                if(winner.equals(p)){
                    getTeam(p.getTeam()).addBunker(b);
                    b.addTeam(p.getTeam());
                    changeTurns();
                    return "Won the fight and bunker seized.";
                }else{
                    changeTurns();
                    return "Player eliminated.";
                }
            } else { // free bunker from player's team
                p.move(dir); 
                changeTurns();
                return String.format("%s player in position (%d, %d)",p.getType(),p.getX(),p.getY());
            }
        }else if(hasPlayer(totalX, totalY)){ // map position with player
            Player winner = fight(getPlayer(totalX, totalY),p);
            if(winner.equals(p)){
                p.move(dir);
                changeTurns();
                return String.format("Won the fight.\n%s player in position (%d, %d)",p.getType(),p.getX(),p.getY());
            }else{
                changeTurns();
            return "Player eliminated";
            }
        } else {
        p.move(dir); // empty map position
        changeTurns();
        return String.format("%s player in position (%d, %d)",p.getType(),p.getX(),p.getY());
        }
    }

    /**
     * 
     * @param defender
     * @param attacker
     * @return the winner
     */
    private Player fight(Player defender, Player attacker) {
        String attackerType = attacker.getType();
        String defenderType = defender.getType();

        if(attackerType.equals(RED)){
            if(defenderType.equals(BLUE)){
                removePlayer(defender);
                return attacker;
            }else if(defenderType.equals(GREEN)){
                removePlayer(attacker);
                return defender;
            }
        }else if(attackerType.equals(GREEN)){
            if(defenderType.equals(RED)){
                removePlayer(defender);
                return attacker;
          } else if(defenderType.equals(BLUE)){
                removePlayer(attacker);
                return defender;
          } 
        }else if(attackerType.equals(BLUE)){
            if(defenderType.equals(RED)){
                removePlayer(attacker);
                return defender;
            } else if(defenderType.equals(GREEN)){
                removePlayer(defender);
                return attacker;
            } 
        }
        removePlayer(defender);
        return attacker;
    }


    private void removePlayer(Player loser) {
        Team team = getTeam(loser.getTeam());
        team.removeMember(loser);
        
        if(!team.hasPlayers() && !team.hasBunkers()){
            removeTeam(team);
        }  
    }

    private void removeTeam(Team team) {
        teamTurns.remove(team);
        teams.removeAt(teams.searchIndexOf(team));
    }
    //Uma equipa é ativa se tiver bunkers em seu nome OU se tiver jogadores

    private void won(){
        int activeTeams;
        Iterator<Team> it = teams.iterator();
        while(it.hasNext()){
            Team t = it.next();
        }
    }
        
}
