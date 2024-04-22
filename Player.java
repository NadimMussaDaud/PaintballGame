public interface Player {

    String getTeam();
    int getX();
    int getY();
    void move(String dir);
    abstract int cost();
    abstract String getType();
}
