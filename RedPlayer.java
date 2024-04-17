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
}
