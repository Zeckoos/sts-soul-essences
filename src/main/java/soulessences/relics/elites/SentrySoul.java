package soulessences.relics.elites;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class SentrySoul extends BaseRelic {
    public static final String ID = makeID("SentrySoul");

    private static final RelicTier RARITY = RelicTier.COMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final String[] ENEMY_ID = {"Sentry", "FF_Sentry"};

    private static final int DRAW_AMOUNT = 2;

    private static final int MAX_TURNS = 2;

    public SentrySoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);
    }

    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        }

        else {
            ++this.counter;
        }

        if (this.counter % 2 == 0) {
            this.counter = 0;
            this.flash();

            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 2));
        }
    }

    @Override
    public void onVictory() {
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DRAW_AMOUNT + DESCRIPTIONS[1] + MAX_TURNS + DESCRIPTIONS[2];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SentrySoul();
    }
}
