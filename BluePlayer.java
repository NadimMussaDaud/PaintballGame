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
     
}
