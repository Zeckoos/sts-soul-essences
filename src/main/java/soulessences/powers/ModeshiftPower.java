package soulessences.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

import static soulessences.SoulEssences.makeID;

public class ModeshiftPower extends AbstractPower {
    public static final String POWER_ID = makeID("ModeShiftPower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int THORNS_AMOUNT = 12;

    private int damageAccumulated = 0;

    private final int threshold;

    public ModeshiftPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.threshold = amount;

        this.loadRegion("modeShift");
        this.updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.type != DamageInfo.DamageType.THORNS && info.owner != this.owner) {
            damageAccumulated += damageAmount;

            this.amount -= damageAmount;
            this.updateDescription();

            if (damageAccumulated >= threshold) {
                activateModeShift();
            }
        }
        return damageAmount;
    }

    private void activateModeShift() {
        AbstractDungeon.effectList.add(new SpeechBubble(this.owner.dialogX, this.owner.dialogY, 3.0F, "There are spikes all over my body?!!!.", true));

        this.flash();

        // Apply Thorns
        this.addToTop(new ApplyPowerAction(owner, owner, new ThornsPower(owner, THORNS_AMOUNT), THORNS_AMOUNT));

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + THORNS_AMOUNT + DESCRIPTIONS[2];
    }
}
