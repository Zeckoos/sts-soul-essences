package soulessences.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

public class EnragedAction extends AbstractGameAction {
    private final int STR_INCREASE;

    private static final int DEX_LOSS = 2; // Constant for dexterity loss

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("soulessences:EnragedPower");

    private static final String SPEECH = powerStrings.DESCRIPTIONS[3];

    public EnragedAction(int str) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.STR_INCREASE = str;
    }

    private boolean isAttackIntent(AbstractMonster.Intent intent) {
        return intent == AbstractMonster.Intent.ATTACK ||
                intent == AbstractMonster.Intent.ATTACK_BUFF ||
                intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                intent == AbstractMonster.Intent.ATTACK_DEFEND;
    }

    @Override
    public void update() {
        boolean shouldApplyStrength = false;

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster != null && !monster.isDeadOrEscaped() && !isAttackIntent(monster.intent)) {
                shouldApplyStrength = true;
                break;
            }
        }

        if (shouldApplyStrength) {
            AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX,
                    AbstractDungeon.player.dialogY,3.0F, SPEECH, true));

            // Apply Strength increase
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new StrengthPower(AbstractDungeon.player, this.STR_INCREASE), this.STR_INCREASE));

            // Apply Dexterity decrease
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new DexterityPower(AbstractDungeon.player, -DEX_LOSS), -DEX_LOSS));
        }

        this.isDone = true;
    }
}
