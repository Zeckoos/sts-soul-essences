package soulessences.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.PoisonedStab;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static soulessences.SoulEssences.makeID;

public class ReptomancerSoul extends BaseRelic {
    public static final String ID = makeID("ReptomancerSoul");

    private static final RelicTier RARITY = RelicTier.UNCOMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int CARDS_AMOUNT = 6;

    public ReptomancerSoul() {
        super(ID, RARITY, SOUND);

        ENEMY_KEYS.add("Reptomancer");
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractCard card = new PoisonedStab();
        card.modifyCostForCombat(-card.cost);
        for (int i = 0; i < CARDS_AMOUNT; i++) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, true));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ReptomancerSoul();
    }

}
