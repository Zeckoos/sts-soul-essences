package soulessences.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import soulessences.helpers.PowerFactory;


public class ApplyEffectToAllEnemiesAction extends AbstractGameAction {
    private final Class<? extends AbstractPower> powerClass;

    private final int amount;

    public ApplyEffectToAllEnemiesAction(Class<? extends AbstractPower> powerClass, int amount, boolean isDebuff) {
        this.powerClass = powerClass;
        this.amount = amount;
        this.actionType = isDebuff ? ActionType.DEBUFF : ActionType.POWER;
    }

    @Override
    public void update() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractPower powerInstance = PowerFactory.createPower(powerClass, m, this.amount, false);

            if (powerInstance != null) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, powerInstance, this.amount));
            }
        }
        this.isDone = true;
    }
}
