package soulessences.relics.shops;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.actions.ApplyEffectToAllEnemiesAction;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class BottledBlueSlaverSoul extends BaseRelic {
    public static final String ID = makeID("BottledBlueSlaverSoul");

    private static final RelicTier RARITY = RelicTier.SHOP;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int WEAK_AMOUNT = 1;

    public BottledBlueSlaverSoul() {
        super(ID, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        this.flash();

        AbstractDungeon.actionManager.addToBottom(new ApplyEffectToAllEnemiesAction(WeakPower.class, WEAK_AMOUNT, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + WEAK_AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledBlueSlaverSoul();
    }
}
