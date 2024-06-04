package utils;

import main.Game;

public class HelpMethods {
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] data) {
        if (!isSolid(x, y, data)) {
            if (!isSolid(x + width, y + height, data)) {
                if (!isSolid(x + width, y, data)) {
                    if (!isSolid(x, y + height, data)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isSolid(float x, float y, int[][] data) {
        if (x < 0 || x >= Game.GAME_WIDTH) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = data[(int) yIndex][(int) xIndex];

        if (value >= 96 || value < 0 || value != 11) {
            return true;
        }
        return false;
    }
}
