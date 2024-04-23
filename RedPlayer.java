import dataStructures.Array;
import dataStructures.ArrayClass;

class RedPlayer extends AbstractPlayer {
    static final int COST = 4;
    final String TYPE = "red";

    public RedPlayer(String team, int x, int y){
        super(team, x,y);
    }

    @Override
    public int cost() {
        return COST;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Array<Integer> getAttackCoord(int width, int height){
        Array<Integer> coords = new ArrayClass<>();
        for(int x = this.x; x <= height; x++){
            for(int y = this.y; y <= width; y++){
                if(x != this.x || y != this.y){
                    coords.insertLast(x);
                    coords.insertLast(y);
                }
            }
        }
        return coords;
    }
}
