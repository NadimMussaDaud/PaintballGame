class BluePlayer extends AbstractPlayer{
    static final int COST = 2;

    public BluePlayer(String team, int x, int y){
        super(team,x,y);
    }

    @Override
    public int cost() {
        return COST;
    }
    
}
