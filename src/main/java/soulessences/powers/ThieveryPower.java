package soulessences.powers;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static soulessences.SoulEssences.makeID;

public class ThieveryPower extends AbstractPower {
    public static final String POWER_ID = makeID("ThieveryPower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int MAX_ATTACKS = 3;

    private static int ATTACK_COUNTER;

    public ThieveryPower(AbstractCreature owner, int goldAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = goldAmount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        ATTACK_COUNTER = 0;

        this.loadRegion("thievery");
        this.updateDescription();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (ATTACK_COUNTER < MAX_ATTACKS) {
            this.flash();
            if (damageAmount > 0 && info.owner == this.owner && !target.isPlayer) {
                AbstractDungeon.actionManager.addToBottom(new GainGoldAction(this.amount));
            }
        }
        ATTACK_COUNTER++;

        if (ATTACK_COUNTER >= MAX_ATTACKS) {
            this.addToTop(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToTop(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    @Override
    public void onVictory() {
        ATTACK_COUNTER = 0;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + MAX_ATTACKS + DESCRIPTIONS[2];
    }
}
