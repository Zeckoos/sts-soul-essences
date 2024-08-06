package soulessences.relics.shops;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.actions.ApplyEffectToAllEnemiesAction;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class BottledRedSlaverSoul extends BaseRelic {
    public static final String ID = makeID("BottledRedSlaverSoul");

    private static final RelicTier RARITY = RelicTier.SHOP;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int VULNERABLE_AMOUNT = 1;

    public BottledRedSlaverSoul() {
        super(ID, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        this.flash();

        AbstractDungeon.actionManager.addToBottom(new ApplyEffectToAllEnemiesAction(VulnerablePower.class, VULNERABLE_AMOUNT, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + VULNERABLE_AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledRedSlaverSoul();
    }
}
