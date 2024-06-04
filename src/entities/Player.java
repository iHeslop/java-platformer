package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.JUMP;
import static utils.Constants.PlayerConstants.RUNNING;
import static utils.Constants.PlayerConstants.getSpriteAmount;
import static utils.HelpMethods.canMoveHere;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick, aniSpeed = 30, jumpAniSpeed = 15, aniIndex;
    private int playerAction = IDLE;
    private boolean left, up, down, right, jumping;
    private boolean moving = false;
    private float playerSpeed = 2.0f;
    private int width, height;
    private int[][] data;

    private float xDrawOffset = 18 * Game.SCALE;
    private float yDrawOffset = 6 * Game.SCALE;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.width = width;
        this.height = height;
        initHitBox(x, y, 14 * Game.SCALE, 28 * Game.SCALE);
        loadAnimations();
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimations();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x - xDrawOffset), (int) (hitBox.y
                - yDrawOffset), width, height, null);
        drawHitBox(g);
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
        int currentAniSpeed = playerAction == JUMP ? jumpAniSpeed : aniSpeed;
        if (aniTick >= currentAniSpeed) {
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
        if (!left && !right && !up && !down) {
            return;
        }
        float xSpeed = 0;
        float ySpeed = 0;
        if (left && !right) {
            xSpeed = -playerSpeed;

        } else if (right && !left) {
            xSpeed = playerSpeed;
        }

        if (up && !down) {
            ySpeed = -playerSpeed;

        } else if (down && !up) {
            ySpeed = playerSpeed;
        }

        if (canMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, data)) {
            hitBox.x += xSpeed;
            hitBox.y += ySpeed;
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

    public void loadLevelData(int[][] data) {
        this.data = data;
    }
}
