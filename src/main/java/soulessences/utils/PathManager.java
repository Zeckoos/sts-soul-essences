package soulessences.utils;

import static soulessences.SoulEssences.modID;

public class PathManager {
    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return makeImagePath("relics/") + resourcePath;
    }

    public static String makePowerPath32(String resourcePath) {
        return makeImagePath("powers/32/") + resourcePath;
    }

    public static String makePowerPath84(String resourcePath) {
        return makeImagePath("powers/84/") + resourcePath;
    }

    public static String makeMinionsAnimationPath(String resourcePath) {
        return makeImagePath("animations/") + resourcePath;
    }
}
