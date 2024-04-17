public interface Player {

    String getTeam();
    int getX();
    int getY();
    abstract int cost();
    abstract String getType();
}
