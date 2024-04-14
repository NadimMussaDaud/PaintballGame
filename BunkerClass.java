public class BunkerClass implements Bunker {

    private String name;
    private int x, y;
    private int treasure;
    private String team;

    public BunkerClass(int x, int y, int treasure, String name){
        this.x = x;
        this.y = y;
        this.treasure = treasure;
        this.name = name;
    }


    public void addTeam(String team){
        this.team = team;
    }

    public String getName(){
        return name;
    }
}
