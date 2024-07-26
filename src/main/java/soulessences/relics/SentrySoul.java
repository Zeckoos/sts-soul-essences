package soulessences.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static soulessences.SoulEssences.logger;
import static soulessences.SoulEssences.makeID;

public class SentrySoul extends BaseRelic {
    public static final String ID = makeID("SentrySoul");

    private static final RelicTier RARITY = RelicTier.COMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    public SentrySoul() {
        super(ID, RARITY, SOUND);

        ENEMY_KEYS.add("3 Sentries");

        logger.info("SentrySoul spawn condition:{}", this.canSpawn());
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
    public AbstractRelic makeCopy() {
        return new SentrySoul();
    }
}