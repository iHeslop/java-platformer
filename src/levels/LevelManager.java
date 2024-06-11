package levels;

import static main.Game.TILES_SIZE;
import static utils.HelpMethods.isTouchingDoor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    private BufferedImage[][] door;

    private int aniTick, aniSpeed = 30, aniIndex;
    private static final int DOOR_TILE_VALUE = 74;
    private boolean animationCompleted = false;

    public LevelManager(Game game) {
        this.game = game;
        this.animationCompleted = false;
        levelOne = new Level(LoadSave.GetLevelData());
        importOutsideSprites();
        loadAnimations();
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

        int startTileY = levelOffset / TILES_SIZE;
        if (startTileY < 0) {
            startTileY = 0;
        }

        int endTileY = (levelOffset + Game.GAME_HEIGHT) / TILES_SIZE + 1;
        if (endTileY > levelHeight) {
            endTileY = levelHeight;
        }
        float playerX = game.getPlaying().getPlayer().getHitBox().x;
        float playerY = game.getPlaying().getPlayer().getHitBox().y;
        for (int i = startTileY; i < endTileY; i++) {
            for (int j = 0; j < levelOne.getLevelData()[0].length; j++) {
                int index = levelOne.getSpriteIndex(j, i);
                int tileX = j * TILES_SIZE;
                int tileY = i * TILES_SIZE - levelOffset;
                if (levelOne.getLevelData()[i][j] == DOOR_TILE_VALUE) {
                    if (isTouchingDoor(playerX, playerY, levelOne.getLevelData())) {
                        g.drawImage(door[0][aniIndex], tileX, tileY, TILES_SIZE, TILES_SIZE, null);
                        if (!animationCompleted) {
                            updateAnimationTick();
                        } else {
                            game.gameComplete();
                        }
                    } else {
                        g.drawImage(door[0][0], tileX, tileY, TILES_SIZE, TILES_SIZE, null);
                    }
                } else {
                    g.drawImage(levelSprite[index], tileX, tileY, TILES_SIZE, TILES_SIZE, null);
                }
            }
        }
    }

    public void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= door[0].length) {
                aniIndex = 0;
                animationCompleted = true;
            }
        }
    }

    public void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.DOOR);
        door = new BufferedImage[1][5];
        for (int j = 0; j < door.length; j++) {
            for (int i = 0; i < door[j].length; i++) {
                door[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    public Level getCurrentLevel() {
        return levelOne;
    }

    public Game getGame() {
        return game;
    }

    public void setAnimationCompleted(boolean completed) {
        this.animationCompleted = completed;
    }
}
