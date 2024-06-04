package levels;

public class Level {
    private int[][] data;

    public Level(int[][] data) {
        this.data = data;
    }

    public int getSpriteIndex(int x, int y) {
        return data[y][x];
    }
}
