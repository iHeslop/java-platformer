package ui;

import static utils.Constants.UI.UrmButtons.*;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

public class PauseOverLay {
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgWidth, bgHeight;
    private AudioOptions audioOptions;
    private UrmButton menuB, replayB, unpauseB;

    public PauseOverLay(Playing playing) {
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getGame().getAudioOptions();
        createUrmButtons();
    }

    private void createUrmButtons() {
        int menuX = (int) (90 * Game.SCALE);
        int replayX = (int) (141 * Game.SCALE);
        int unpauseX = (int) (192 * Game.SCALE);
        int bY = (int) (320 * Game.SCALE);
        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgWidth = (int) (backgroundImg.getWidth() * Game.SCALE / 1.5);
        bgHeight = (int) (backgroundImg.getHeight() * Game.SCALE / 1.5);
        bgX = Game.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int) (115 * Game.SCALE);
    }

    public void update() {

        menuB.update();
        replayB.update();
        unpauseB.update();
        audioOptions.update();

    }

    public void draw(Graphics g) {
        // Background
        g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);

        // URM Buttons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        audioOptions.draw(g);

    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e, replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                GameState.state = GameState.MENU;
                playing.unPauseGame();
            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                playing.resetAll();
            }
        } else if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed()) {
                playing.unPauseGame();
            }
        } else {
            audioOptions.mouseReleased(e);
        }

        menuB.resetBools();
        unpauseB.resetBools();
        replayB.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        replayB.setMouseOver(false);
        if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else if (isIn(e, replayB)) {
            replayB.setMouseOver(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
