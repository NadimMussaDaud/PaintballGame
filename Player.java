import dataStructures.Array;

public interface Player {

    String getTeam();
    int getX();
    int getY();
    void move(String dir);
    abstract int cost();
    abstract String getType();
    abstract Array<Integer> getAttackCoord(int width, int height);
}
