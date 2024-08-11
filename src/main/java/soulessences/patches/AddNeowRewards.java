package soulessences.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.neow.NeowReward;

import java.util.ArrayList;


@SpirePatch(clz = NeowReward.class, method = "getRewardOptions")
public class AddNeowRewards {
    @SpireEnum
    public static NeowReward.NeowRewardType LOSE_STARTER_GAIN_SOUL;

    @SpirePostfixPatch
    public static ArrayList<NeowReward.NeowRewardDef> patch(ArrayList<NeowReward.NeowRewardDef> __result, NeowReward __instance, int category) {
        if (category == 3) { // Use the appropriate category for Boss relics
            String description = FontHelper.colorString("Lose your starter relic and gain a random character Soul relic", "g");
            __result.add(new NeowReward.NeowRewardDef(LOSE_STARTER_GAIN_SOUL, "[ " + description + " ]"));
        }
        return __result;
    }
}

