class GreenPlayer extends AbstractPlayer{
    static final int COST = 2;

    public GreenPlayer(String team, int x, int y){
        super(team,x,y);
    }

    @Override
    public int cost() {
        return COST;
    }
}