package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import static utils.Constants.UI.UrmButtons.*;

public class LevelCompletedOverlay {
    private Playing playing;
    private UrmButton menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgWidth, bgHeight;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (110 * Game.SCALE);
        int nextX = (int) (175 * Game.SCALE);
        int bY = (int) (230 * Game.SCALE);
        menu = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        next = new UrmButton(nextX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMAGE);
        bgWidth = (int) (img.getWidth() * Game.SCALE / 1.5);
        bgHeight = (int) (img.getHeight() * Game.SCALE / 1.5);
        bgX = Game.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int) (150 * Game.SCALE);
    }

    public void update() {
        menu.update();
        next.update();
    }

    public void draw(Graphics g) {
        g.drawImage(img, bgX, bgY, bgWidth, bgHeight, null);
        next.draw(g);
        menu.draw(g);
    }

    public void mouseMoved(MouseEvent e) {
        menu.setMouseOver(false);
        next.setMouseOver(false);
        if (isIn(e, menu)) {
            menu.setMouseOver(true);
        } else if (isIn(e, next)) {
            next.setMouseOver(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menu)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                GameState.state = GameState.MENU;
            }
        } else if (isIn(e, next)) {
            if (next.isMousePressed()) {
                playing.loadNextLevel();
            }
        }

        menu.resetBools();
        next.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, menu)) {
            menu.setMousePressed(true);
        } else if (isIn(e, next)) {
            next.setMousePressed(true);
        }
    }

    private boolean isIn(MouseEvent e, UrmButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
