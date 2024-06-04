package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;

import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.JUMP;
import static utils.Constants.PlayerConstants.RUNNING;
import static utils.Constants.PlayerConstants.getSpriteAmount;

public class Player extends Entity {

    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniSpeed = 30, aniIndex;
    private int playerAction = IDLE;
    private boolean left, up, down, right, jumping;
    private boolean moving = false;
    private float playerSpeed = 2.0f;
    private int width, height;

    public Player(float x, float y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        loadAnimations();
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimations();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, width, height, null);
    }

    public void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[6][9];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 50, j * 37, 50, 37);
            }
        }
    }

    public void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(playerAction)) {
                aniIndex = 0;
                jumping = false;
            }
        }
    }

    public void setAnimations() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
        if (jumping) {
            playerAction = JUMP;
        }

        if (startAni != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void updatePos() {
        moving = false;
        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void resetBooleans() {
        up = false;
        down = false;
        right = false;
        left = false;
    }
}
