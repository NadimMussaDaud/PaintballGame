public abstract class AbstractPlayer {

    private String team;
    private int x, y;

    AbstractPlayer(String team, int x, int y){
        this.team = team;
        this.x = x;
        this.y = y;
    }

    public String getTeam(){
        return team;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
