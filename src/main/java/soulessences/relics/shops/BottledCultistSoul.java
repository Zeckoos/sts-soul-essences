package soulessences.relics.shops;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class BottledCultistSoul extends BaseRelic {
    public static final String ID = makeID("BottledCultistSoul");

    private static final RelicTier RARITY = RelicTier.SHOP;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int RITUAL_AMOUNT = 1;

    public BottledCultistSoul() {
        super(ID, RARITY, SOUND);
    }

    public void atBattleStart() {
        this.flash();

        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RitualPower(p, RITUAL_AMOUNT, true), RITUAL_AMOUNT));

        this.grayscale = true;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public boolean canSpawn() {
        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + RITUAL_AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledCultistSoul();
    }
}
