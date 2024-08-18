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
        for(int i = 1; i < width; i++){
            if(x-i > 0){
                coord.insertLast(x-i); 
                coord.insertLast(y); //insert left y
            }
            if(x+i <= width){ 
                coord.insertLast(x+i); 
                coord.insertLast(y); //insert right y
            }
        }
        return coord;
    }
     
}
