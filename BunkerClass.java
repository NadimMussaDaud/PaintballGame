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


    @Override
    public boolean isFree(){
        return team.equals(NO_OWNER);
    }
}
