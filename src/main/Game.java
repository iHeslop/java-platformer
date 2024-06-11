package main;

import java.awt.Graphics;

import gamestates.GameOptions;
import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;
import ui.AudioOptions;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    private GameOptions gameOptions;
    private AudioOptions audioOptions;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 2f;
    public final static int TILES_IN_WIDTH = 10;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        audioOptions = new AudioOptions();
        menu = new Menu(this);
        playing = new Playing(this);
        gameOptions = new GameOptions(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (GameState.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
                gameOptions.update();
                break;
            case QUIT:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case OPTIONS:
                gameOptions.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();

        long lastCheck = System.nanoTime();
        double deltaU = 0;
        double deltaF = 0;

        int frames = 0;
        int updates = 0;

        while (true) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - previousTime;
            previousTime = currentTime;

            deltaU += elapsedTime / timePerUpdate;
            deltaF += elapsedTime / timePerFrame;

            while (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            long currentTimeMillis = System.nanoTime();
            if (currentTimeMillis - lastCheck >= 1000000000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastCheck = currentTimeMillis;
            }
        }
    }

    public void windowLostFocus() {
        if (GameState.state == GameState.PLAYING) {
            playing.getPlayer().resetBooleans();
        }
    }

    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }

    public AudioOptions getAudioOptions() {
        return audioOptions;
    }

    public GameOptions getGameOptions() {
        return gameOptions;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void gameComplete() {
        System.out.println("GAME COMPLETE");
        System.exit(0);
    }
}
