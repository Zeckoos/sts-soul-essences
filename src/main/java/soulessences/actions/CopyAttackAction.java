package soulessences.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CopyAttackAction extends AbstractGameAction {
    protected final AbstractCard CARD;

    protected final int TIMES;

    private final AbstractMonster TARGET;

    public CopyAttackAction(AbstractCard card, int numberOfCopies, AbstractMonster target) {
        this.CARD = card;
        this.TIMES = numberOfCopies;
        this.TARGET = target;
    }
    @Override
    public void update() {
        for (int i = 0; i < TIMES; i++) {
            AbstractCard tmp = CARD.makeSameInstanceOf();

            AbstractDungeon.player.limbo.addToBottom(tmp);

            tmp.current_x = CARD.current_x;
            tmp.current_y = CARD.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            tmp.purgeOnUse = true;

            if (TARGET != null) {
                tmp.calculateCardDamage(TARGET);
            }

            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, TARGET, CARD.energyOnUse, true, true), true);
        }
        isDone = true;
    }
}
