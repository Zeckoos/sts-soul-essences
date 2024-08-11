package soulessences.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.relics.starters.DefectSoul;
import soulessences.relics.starters.IroncladSoul;
import soulessences.relics.starters.SilentSoul;
import soulessences.relics.starters.WatcherSoul;

import java.util.Arrays;
import java.util.List;

import static soulessences.patches.AddNeowRewards.LOSE_STARTER_GAIN_SOUL;

@SpirePatch(clz = NeowReward.class, method = "activate")
public class ActivateNeowRewards {
    @SpirePrefixPatch
    public static void patch(NeowReward __instance) {
        if (__instance.type == LOSE_STARTER_GAIN_SOUL) {
            AbstractPlayer player = AbstractDungeon.player;

            // Lose the starter relic
            AbstractRelic starterRelic = player.getRelic(player.getStartingRelics().get(0));
            if (starterRelic != null) {
                player.loseRelic(starterRelic.relicId);
            }

            // Define the list of possible Soul relics
            List<AbstractRelic> soulRelics = Arrays.asList(
                    new IroncladSoul(),
                    new SilentSoul(),
                    new DefectSoul(),
                    new WatcherSoul()
            );

            // Randomly choose one of the Soul relics
            AbstractRelic soulRelic = soulRelics.get(AbstractDungeon.miscRng.random(soulRelics.size() - 1));

            // Grant the Soul relic to the player
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, soulRelic);
        }
    }
}
