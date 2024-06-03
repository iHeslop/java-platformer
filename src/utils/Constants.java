package utils;

public class Constants {
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
