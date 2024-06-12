package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import static utils.Constants.PlayerConstants.CROUCH;
import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.JUMP;
import static utils.Constants.PlayerConstants.RUNNING;
import static utils.Constants.PlayerConstants.getSpriteAmount;
import static utils.HelpMethods.*;

public class Player extends Entity {
    private Playing playing;
    private BufferedImage[][] animations;
    private int aniTick, aniSpeed = 30, jumpAniSpeed = 15, aniIndex;
    private int playerAction = IDLE;
    private boolean left, right;
    private boolean moving = false;
    private boolean crouching = false;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int width, height;
    private int[][] data;

    private float xDrawOffset = 18 * Game.SCALE;
    private float yDrawOffset = 6 * Game.SCALE;

    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    private int maxJumps = 2;
    private int jumpCount = 0;

    private int flipX = 0;
    private int flipW = 1;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.width = width;
        this.height = height;
        initHitBox(x, y, (int) (14 * Game.SCALE), (int) (28 * Game.SCALE));
        loadAnimations();
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimations();
    }

    public void render(Graphics g, int levelOffset) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x - xDrawOffset) + flipX,
                (int) (hitBox.y
                        - yDrawOffset)
                        - levelOffset,
                width * flipW, height, null);
    }

    public void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[5][9];
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
            }
        }
    }

    public void setAnimations() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else if (crouching) {
            playerAction = CROUCH;
        } else {
            playerAction = IDLE;
        }
        if (inAir) {
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

        if (!inAir) {
            jumpCount = 0;
            if ((!left && !right) || (right && left)) {
                return;
            }
        }
        float xSpeed = 0;
        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (!isEntityOnFloor(hitBox, data)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, data)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = getEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    public void jump() {
        if (!inAir || jumpCount < maxJumps) {
            if (jumpCount == 0) {
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
            }
            inAir = true;
            airSpeed = jumpSpeed;
            jumpCount++;
        }
    }

    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width,
                hitBox.height, data)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = getEntityXPosNextToWall(hitBox, xSpeed);
        }
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setCrouching(boolean crouch) {
        this.crouching = crouch;
    }

    public void resetBooleans() {
        right = false;
        left = false;
    }

    public void loadLevelData(int[][] data) {
        this.data = data;
        if (!isEntityOnFloor(hitBox, data)) {
            inAir = true;
        }
    }

    private void resetInAir() {
        jumpCount = 0;
        inAir = false;
        airSpeed = 0;
    }

    public void resetAll() {
        resetBooleans();
        inAir = false;
        moving = false;
        jumpCount = 0;
        playerAction = IDLE;
        hitBox.x = x;
        hitBox.y = y;
        playing.getLevelManager().setLevelCompleted(false);
        if (!isEntityOnFloor(hitBox, data)) {
            inAir = true;
        }
    }
}
