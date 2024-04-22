abstract class AbstractPlayer implements Player{

    protected String team;
    protected int x, y;


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
    

    
    public void move(String dir){
        switch (dir) {
            case GameClass.NORTH -> this.y --;
            case GameClass.SOUTH -> this.y ++;
            case GameClass.WEST -> this.x --;
            case GameClass.EAST -> this.x ++;
        }
    }

    public abstract int cost();
    public abstract String getType();
}
