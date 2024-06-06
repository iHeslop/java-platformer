package utils;

import main.Game;

public class Constants {

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class UrmButtons {
            public static final int URM_SIZE_DEFAULT = 56;
            public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * Game.SCALE);
        }

        public static class VolumeButtons {
            public static final int VOLUME_WIDTH_DEFAULT = 28;
            public static final int VOLUME_HEIGHT_DEFAULT = 44;
            public static final int SLIDER_WIDTH_DEFAULT = 215;

            public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT * Game.SCALE);
            public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_WIDTH_DEFAULT * Game.SCALE);

        }
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int SLIDE = 3;
        public static final int HOLD = 4;
        public static final int CROUCH = 5;

        public static int getSpriteAmount(int playerAction) {
            switch (playerAction) {
                case IDLE:
                case CROUCH:
                    return 4;
                case RUNNING:
                    return 6;
                case SLIDE:
                    return 5;
                case JUMP:
                case HOLD:
                    return 9;
                default:
                    return 1;
            }

        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;

    }
}
