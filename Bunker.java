
public interface Bunker {
    void addTeam(String team);
    String getName();
    String getTeam();
    boolean isFree();
    int getTreasure();
    int getX();
    int getY();
    void decreaseTreasure(int cost);
    void increaseTreasure();
}
