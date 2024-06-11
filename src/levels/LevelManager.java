package levels;

import static main.Game.TILES_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        levelOne = new Level(LoadSave.GetLevelData());
        importOutsideSprites();
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[96];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i * 12 + j;
                levelSprite[index] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void update() {

    }

    public void draw(Graphics g, int levelOffset) {
        int levelHeight = levelOne.getLevelData().length;
        int tileSize = TILES_SIZE;

        int startTileY = levelOffset / tileSize;
        if (startTileY < 0) {
            startTileY = 0;
        }

        int endTileY = (levelOffset + Game.GAME_HEIGHT) / tileSize + 1;
        if (endTileY > levelHeight) {
            endTileY = levelHeight;
        }

        for (int i = startTileY; i < endTileY; i++) {
            for (int j = 0; j < levelOne.getLevelData()[0].length; j++) {
                int index = levelOne.getSpriteIndex(j, i);
                g.drawImage(levelSprite[index], j * tileSize, i * tileSize - levelOffset,
                        tileSize, tileSize, null);
            }
        }
    }

    public Level getCurrentLevel() {
        return levelOne;
    }

    public Game getGame() {
        return game;
    }
}
