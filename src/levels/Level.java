package levels;

import static utils.HelpMethods.GetLevelData;

import java.awt.image.BufferedImage;

import main.Game;

public class Level {
    private int[][] data;
    private BufferedImage img;
    private int levelTilesHeight, maxTilesOffset, maxLevelOffsetY;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        calcOffsets();
    }

    private void calcOffsets() {
        levelTilesHeight = img.getHeight();
        maxTilesOffset = levelTilesHeight - Game.TILES_IN_HEIGHT;
        maxLevelOffsetY = maxTilesOffset * Game.TILES_SIZE;
    }

    private void createLevelData() {
        data = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y) {
        return data[y][x];
    }

    public int[][] getLevelData() {
        return data;
    }

    public int getLevelOffset() {
        return maxLevelOffsetY;
    }
}
