package utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "level_one.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMAGE = "background_menu.png";
    public static final String PLAYING_BACKGROUND_IMAGE = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "big_clouds.png";
    public static final String SMALL_CLOUDS = "small_clouds.png";
    public static final String OPTIONS_MENU = "options_background.png";
    public static final String DOOR = "door-opening.png";
    public static final String COMPLETED_IMAGE = "completed_sprite.png";

    public static final String STARS = "stars.png";
    public static final String SUN = "sun.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] getAllLevels() {
        InputStream is = LoadSave.class.getResourceAsStream("/gamelevels/index.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<BufferedImage> images = new ArrayList<>();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                InputStream imgStream = LoadSave.class.getResourceAsStream("/gamelevels/" + line);
                BufferedImage img = ImageIO.read(imgStream);
                images.add(img);
                imgStream.close();
            }
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return images.toArray(new BufferedImage[0]);
    }
}
