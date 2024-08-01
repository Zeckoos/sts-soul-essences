package soulessences.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import soulessences.helpers.RelicDropManager;

@SpirePatch(clz = MonsterRoomElite.class, method = "dropReward")
public class CustomElitesRelicDropPatch {
    @SpirePostfixPatch
    public static void addCustomRelicDrop(MonsterRoomElite __instance) {
        if (!AbstractDungeon.getCurrRoom().rewardAllowed) {
            return; // Ensure rewards are allowed in the current room
        }

        for (AbstractMonster monster : __instance.monsters.monsters) {
            RelicDropManager.tryDropRelic(monster);
        }
    }
}
