import dataStructures.Array;
import dataStructures.ArrayClass;

class BluePlayer extends AbstractPlayer{
    static final int COST = 2;
    static String TYPE = "blue";

    public BluePlayer(String team, int x, int y){
        super(team,x,y);
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
    public Array<Integer> getAttackCoord(int width,int height) {
        Array<Integer> coord = new ArrayClass<>();
        for(int i = 1; i < width-1; i++){
            if(y-i > 0){
                coord.insertLast(x); 
                coord.insertLast(y-i); //insert left y
            }
            if(y+i < height){
                coord.insertLast(x); 
                coord.insertLast(y+i); //insert right y
            }
        }
        return coord;
    }
     
}
