import dataStructures.Array;
import dataStructures.ArrayClass;

class GreenPlayer extends AbstractPlayer{
    static final int COST = 2;
    final String TYPE = "green";

    public GreenPlayer(String team, int x, int y){
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
    public Array<Integer> getAttackCoord(int width, int height) {
         Array<Integer> coords = new ArrayClass<>();

        //parar certa operação quando x || y == 1 OU x || y == height
        int x1 = x,x2 = x,x3 = x,x4 = x;
        int y1 = y,y2 = y,y3 = y,y4 = y;
        
        boolean finished1 = false,finished2 = false,finished3 = false,finished4 = false;

        while (!finished1 | !finished2 | !finished3 | !finished4) {
            if(x1 != 1 && y1 != 1){
                x1--; y1--;
                coords.insertLast(x1);
                coords.insertLast(y1);
            }else finished1 = true;
            if(x2 != 1 && y2 != height){ 
                x2--; y2++;
                coords.insertLast(x2);
                coords.insertLast(y2);
            }else finished2 = true;
            if(y3 != 1 && x3 != width){ 
                x3++; y3--;
                coords.insertLast(x3);
                coords.insertLast(y3);
            }else finished3 = true;
            if(x4 < width-1 && y4 < height-1){
                x4++; y4++;
                coords.insertLast(x4);
                coords.insertLast(y4);
            }else finished4 = true;

        }

        return coords;
    }
}
