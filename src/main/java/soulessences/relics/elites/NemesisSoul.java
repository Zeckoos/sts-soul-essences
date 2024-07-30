package soulessences.relics.elites;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class NemesisSoul extends BaseRelic {
    public static final String ID = makeID("NemesisSoul");

    private static final RelicTier RARITY = RelicTier.UNCOMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int INTANGIBLE_LIMIT = 3;

    private static final int INTANGIBLE_AMOUNT = 1;

    private int INTANGIBLE_APPLIED = 0;

    public NemesisSoul() {
        super(ID, RARITY, SOUND);

        ENEMY_KEYS.add("Nemesis");
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

        if (this.counter % 2 == 0 && INTANGIBLE_APPLIED != INTANGIBLE_LIMIT) {
            INTANGIBLE_APPLIED++;
            this.counter = 0;

            this.flash();

            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePower(p, INTANGIBLE_AMOUNT)));
        }
        else {
            this.grayscale = true; // Mark the relic gray
        }
    }

    // Reset relic's counter
    @Override
    public void onVictory() {
        this.counter = 0;
        INTANGIBLE_APPLIED = 0;

        this.grayscale = false; // Revert the mark
    }

    @Override
    public AbstractRelic makeCopy() {
        return new NemesisSoul();
    }
}
