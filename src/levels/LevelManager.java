package levels;

import static main.Game.TILES_SIZE;
import static utils.HelpMethods.isTouchingDoor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import audio.AudioPlayer;
import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

public class LevelManager {
    private Game game;
    private Playing playing;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;

    private BufferedImage[][] door;

    private int aniTick, aniSpeed = 30, aniIndex;
    private static final int DOOR_TILE_VALUE = 74;
    private boolean animationCompleted = false;
    private boolean levelCompletedFlag = false;
    private boolean doorTouched = false;

    public LevelManager(Game game, Playing playing) {
        this.game = game;
        this.playing = playing;
        this.animationCompleted = false;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
        loadAnimations();

    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.getAllLevels();
        for (BufferedImage img : allLevels) {
            levels.add(new Level(img));
        }
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
        int levelHeight = levels.get(levelIndex).getLevelData().length;

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
            for (int j = 0; j < levels.get(levelIndex).getLevelData()[0].length; j++) {
                int index = levels.get(levelIndex).getSpriteIndex(j, i);
                int tileX = j * TILES_SIZE;
                int tileY = i * TILES_SIZE - levelOffset;
                if (levels.get(levelIndex).getLevelData()[i][j] == DOOR_TILE_VALUE) {
                    if (isTouchingDoor(playerX, playerY, levels.get(levelIndex).getLevelData())) {
                        g.drawImage(door[0][aniIndex], tileX, tileY, TILES_SIZE, TILES_SIZE, null);
                        doorSound();
                        if (!animationCompleted) {
                            updateAnimationTick();
                        } else {
                            completeLevel();
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

    private void doorSound() {
        if (!doorTouched) {
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DOOR);
            doorTouched = true;
        }
    }

    private void completeLevel() {
        if (!levelCompletedFlag) {
            playing.setCompleted(true);
            levelCompletedFlag = true;
        }
    }

    public void setLevelCompleted(boolean completed) {
        this.levelCompletedFlag = completed;
    }

    public void setDoorTouched(boolean door) {
        this.doorTouched = door;
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

    public void loadNextLevel() {
        levelIndex++;
        if (levelIndex >= levels.size()) {
            levelIndex = 0;
            GameState.state = GameState.MENU;
        }
        levelCompletedFlag = false;
        doorTouched = false;
        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLevelOffset(newLevel.getLevelOffset());
    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public Game getGame() {
        return game;
    }

    public void setAnimationCompleted(boolean completed) {
        this.animationCompleted = completed;
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

    public int getLevelIndex() {
        return levelIndex;
    }
}
