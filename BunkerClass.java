public class BunkerClass implements Bunker {


    private static final String NO_OWNER = "without owner";

    private String name;
    private int x, y;
    private int treasure;
    private String team;
    

    public BunkerClass(int x, int y, int treasure, String name){
        this.team = NO_OWNER;
        this.x = x;
        this.y = y;
        this.treasure = treasure;
        this.name = name;
    }


    public void addTeam(String team){
        this.team = team;
    }

    public String getTeam(){
        return team;
    }


    public String getName(){
        return name;
    }

    public int getTreasure(){
        return treasure;
    }

    @Override
    public boolean isFree(){
        return team.equals(NO_OWNER);
    }


    @Override
    public int getX() {
        return x;
    }


    @Override
    public int getY() {
        return y;
    }


    @Override
    public void decreaseTreasure(int cost) {
        treasure -= cost;
        if(treasure < 0) treasure = 0;
    }


    @Override
    public void increaseTreasure() {
        treasure++;
    }
}
