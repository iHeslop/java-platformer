package utils;

import java.awt.geom.Rectangle2D;

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

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
            return tileXPos + xOffset - 1;
        } else {
            // Left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.y / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Falling
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitBox.height);
            return tileYPos + yOffset - 1;
        } else {
            // Left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] data) {
        if (!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, data))
            if (!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, data)) {
                return false;
            }
        return true;
    }
}
