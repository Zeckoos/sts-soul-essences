package soulessences.helpers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.relics.elites.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RelicDropManager {
    private static final Map<String, Class<? extends AbstractRelic>> monsterRelicMap = new HashMap<>();

    private static final Set<String> droppedRelics = new HashSet<>();

    static {
        // Add mappings for monsters and their respective relics
        monsterRelicMap.put("GremlinNob", GremlinNobSoul.class);
        monsterRelicMap.put("Lagavulin", LagavulinSoul.class);
        monsterRelicMap.put("Sentry", SentrySoul.class);
        monsterRelicMap.put("BookOfStabbing", BookOfStabbingSoul.class);
        monsterRelicMap.put("GremlinLeader", GremlinLeaderSoul.class);
        monsterRelicMap.put("SlaverBoss", TaskMasterSoul.class);
        monsterRelicMap.put("GiantHead", GiantHeadSoul.class);
        monsterRelicMap.put("Nemesis", NemesisSoul.class);
        monsterRelicMap.put("Reptomancer", ReptomancerSoul.class);
    }

    public static void tryDropRelic(AbstractMonster monster) {
        String monsterID = monster.id;

        if (monsterRelicMap.containsKey(monsterID)) {
            Class<? extends AbstractRelic> relicClass = monsterRelicMap.get(monsterID);
            String relicID = relicClass.getName();

            // Check if this relic type hasn't been dropped yet in this encounter
            if (canDropRelic(relicID) && shouldDropBasedOnRarity(relicClass)) {
                try {
                    // Synchronized block to ensure atomic check and mark
                    synchronized (droppedRelics) {
                        if (!droppedRelics.contains(relicID)) {
                            AbstractRelic relic = relicClass.getDeclaredConstructor().newInstance();
                            AbstractDungeon.getCurrRoom().addRelicToRewards(relic);

                            // Mark the relic as dropped immediately
                            markSoulRelicDropped(relicID);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean canDropRelic(String relicID) {
        return AbstractDungeon.player.getRelic(relicID) == null;
    }

    private static boolean shouldDropBasedOnRarity(Class<? extends AbstractRelic> relicClass) {
        try {
            AbstractRelic relic = relicClass.getDeclaredConstructor().newInstance();
            switch (relic.tier) {
                case COMMON:
                    return true;
                    //return Math.random() < 0.50; // 50% chance
                case UNCOMMON:
                    return true;
                    //return Math.random() < 0.33; // 33% chance
                case RARE:
                    return true;
                    //return Math.random() < 0.17; // 17% chance
                case BOSS:
                    return true; // Boss relic will always drop
                default:
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void resetDropState() {
        droppedRelics.clear();
    }

    public static boolean canDropSoulRelic(String relicId) {
        return !droppedRelics.contains(relicId);
    }

    public static void markSoulRelicDropped(String relicId) {
        droppedRelics.add(relicId);
    }
}
