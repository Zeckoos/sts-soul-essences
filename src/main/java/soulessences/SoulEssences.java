package soulessences;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import soulessences.helpers.RelicDropManager;
import soulessences.relics.BaseRelic;
import soulessences.relics.bosses.TheCollectorSoul;
import soulessences.utils.MinionStrings;
import soulessences.utils.NeowRewardStrings;
import soulessences.utils.PathManager;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpireInitializer
public class SoulEssences implements EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, PostDungeonInitializeSubscriber, PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(SoulEssences.class.getName());

    public static final String modID = "soulessences";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    private static Map<String, MinionStrings> minionStringsMap = new HashMap<>();

    private static Map<String, NeowRewardStrings> neowRewardStringsMap = new HashMap<>();

    public SoulEssences() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");
        logger.info("Done creating the color");
    }

    public static void initialize() {
        new SoulEssences();
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd("SoulEssences")
                .packageFilter(BaseRelic.class)
                .any(BaseRelic.class, (info, relic) -> {
                    if (relic.pool == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    }

                    else {
                        BaseMod.addRelicToCustomPool(relic, relic.pool);
                    }

                    if (info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    public static MinionStrings getMinionString(String id) {
        return minionStringsMap.getOrDefault(id, new MinionStrings());
    }

    public static NeowRewardStrings getNeowRewardString(String id) {
        return neowRewardStringsMap.getOrDefault(id, new NeowRewardStrings());
    }

    private void loadMinionStrings(String lang) {
        String minionStringsPath = PathManager.makeLocalizationPath(lang, "/MinionStrings.json");
        Type type = new TypeToken<Map<String, MinionStrings>>() {}.getType();
        Gson gson = new Gson();
        String json = Gdx.files.internal(minionStringsPath).readString(String.valueOf(StandardCharsets.UTF_8));
        Map<String, MinionStrings> minionStrings = gson.fromJson(json, type);

        if (minionStrings != null) {
            minionStringsMap.putAll(minionStrings);
        }

        logger.info("Minion strings loaded from {}", minionStringsPath);
    }

    private void loadNeowRewardStrings(String lang) {
        String neowRewardStringsPath = PathManager.makeLocalizationPath(lang, "/NeowRewardStrings.json");
        Type type = new TypeToken<Map<String, NeowRewardStrings>>() {}.getType();
        Gson gson = new Gson();
        String json = Gdx.files.internal(neowRewardStringsPath).readString(String.valueOf(StandardCharsets.UTF_8));
        Map<String, NeowRewardStrings> neowStrings = gson.fromJson(json, type);

        if (neowStrings != null) {
            neowRewardStringsMap.putAll(neowStrings);
        }

        logger.info("NeowReward strings loaded from {}", neowRewardStringsPath);
    }

    private static String getLangString() {
        return Settings.language.name().toLowerCase();
    }

    private static final String defaultLanguage = "eng";

    public void receiveEditStrings() {
        loadLocalization(defaultLanguage);

        // Load minion strings
        loadMinionStrings(defaultLanguage);
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadMinionStrings(getLangString());
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }

        // Load neow reward strings
        loadNeowRewardStrings(defaultLanguage);
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadNeowRewardStrings(getLangString());
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }

        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        String relicPath = PathManager.makeLocalizationPath(lang, "RelicStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, relicPath);
        logger.info("Relic strings loaded from {}", relicPath);

        String powerPath = PathManager.makeLocalizationPath(lang, "/PowerStrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, powerPath);
        logger.info("Power strings loaded from {}", powerPath);
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("Begin editing keywords");
        loadCustomKeywordsForLanguage(getLangString());
        logger.info("Done editing keywords");
    }

    private void loadCustomKeywordsForLanguage(String languageFolder) {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/" + languageFolder + "/KeywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receivePostDungeonInitialize() {
        RelicDropManager.resetDropState();
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.addSaveField(TheCollectorSoul.ID, new TheCollectorSoul());
    }
}
