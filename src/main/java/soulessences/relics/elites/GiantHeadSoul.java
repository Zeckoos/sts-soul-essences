package soulessences.relics.elites;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.actions.ApplyEffectToAllEnemiesAction;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class GiantHeadSoul extends BaseRelic {
    public static final String ID = makeID("GiantHeadSoul");

    private static final RelicTier RARITY = RelicTier.UNCOMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int SLOW_AMOUNT = 1;

    private static final String ENEMY_ID = "GiantHead";

    public GiantHeadSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);
    }

    @Override
    public void atBattleStart() {
        this.flash();

        AbstractDungeon.actionManager.addToBottom(new ApplyEffectToAllEnemiesAction(SlowPower.class, SLOW_AMOUNT, true));

        this.grayscale = true;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GiantHeadSoul();
    }
}
