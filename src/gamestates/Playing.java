package gamestates;

import static main.Game.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.LevelCompletedOverlay;
import ui.PauseOverLay;
import utils.LoadSave;

import static utils.Constants.Environment.*;

public class Playing extends State implements StateMethods {

    private LevelManager levelManager;
    private Player player;
    private PauseOverLay pauseOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused = false;
    private boolean completed = false;

    private int yLevelOffset;
    private int bottomBorder = (int) (0.5 * Game.GAME_HEIGHT);
    private int topBorder = (int) (0.8 * Game.GAME_HEIGHT);
    private int maxLevelOffsetY;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudPos;
    private Random random = new Random();

    public Playing(Game game) {
        super(game);
        initClasses();
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMAGE);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudPos = new int[8];
        for (int i = 0; i < smallCloudPos.length; i++) {
            smallCloudPos[i] = (int) (90 * SCALE) + random.nextInt((int) (100 * SCALE));
        }
        calcLevelOffset();
    }

    private void calcLevelOffset() {
        maxLevelOffsetY = levelManager.getCurrentLevel().getLevelOffset();
    }

    public void setMaxLevelOffset(int offset) {
        this.maxLevelOffsetY = offset;
    }

    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
    }

    public void resetAll() {
        paused = false;
        completed = false;
        levelManager.setAnimationCompleted(false);
        player.resetAll();
    }

    private void initClasses() {
        levelManager = new LevelManager(game, this);
        int initialX = GAME_WIDTH / 2;
        int initialY = (levelManager.getCurrentLevel().getLevelData().length - 1) * Game.TILES_SIZE - Game.TILES_SIZE;
        player = new Player(initialX, initialY - Game.TILES_SIZE * 2, (int) (50 * Game.SCALE), (int) (36 * Game.SCALE),
                this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverLay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }

    public void windowLostFocus() {
        player.resetBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
        } else if (completed) {
            levelCompletedOverlay.update();
        } else {
            levelManager.update();
            player.update();
            checkViewport();
        }
    }

    private void checkViewport() {
        int playerY = (int) player.getHitBox().y;
        int diff = playerY - yLevelOffset;

        if (diff < bottomBorder) {
            yLevelOffset += diff - bottomBorder;
        } else if (diff > topBorder) {
            yLevelOffset += diff - topBorder;
        }

        if (yLevelOffset > maxLevelOffsetY) {
            yLevelOffset = maxLevelOffsetY;
        } else if (yLevelOffset < 0) {
            yLevelOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, TILES_SIZE, 0 - yLevelOffset,
                (int) (backgroundImg.getWidth() * Game.SCALE) - 2 * TILES_SIZE,
                (int) (backgroundImg.getHeight() * Game.SCALE) - TILES_SIZE, null);

        drawClouds(g);
        levelManager.draw(g, yLevelOffset);
        if (!completed) {
            player.render(g, yLevelOffset);
        }
        if (paused) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (completed) {
            levelCompletedOverlay.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigCloud, (int) (25 * SCALE),
                    BIG_CLOUD_HEIGHT + (int) (500 * Game.SCALE) - (int) (yLevelOffset * 0.5),
                    BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT,
                    null);
        }
        for (int i = 0; i < smallCloudPos.length; i++) {
            g.drawImage(smallCloud, smallCloudPos[i], SMALL_CLOUD_WIDTH * 4 * i - (int) (yLevelOffset
                    * 0.7), SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT,
                    null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pauseOverlay.mousePressed(e);
        } else if (completed) {
            levelCompletedOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseReleased(e);
        } else if (completed) {
            levelCompletedOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseMoved(e);
        } else if (completed) {
            levelCompletedOverlay.mouseMoved(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_S:
                player.setCrouching(true);
                break;
            case KeyEvent.VK_SPACE:
                player.jump();
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_S:
                player.setCrouching(false);
                break;
        }
    }

    public void unPauseGame() {
        paused = false;
    }

    public void unCompleteGame() {
        completed = false;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed) {
            game.getAudioPlayer().levelCompleted();
        }
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }
}
