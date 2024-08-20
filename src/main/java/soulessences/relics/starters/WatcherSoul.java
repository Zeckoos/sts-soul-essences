package soulessences.relics.starters;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.cards.ModdedSafety;
import soulessences.cards.ModdedSmite;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class WatcherSoul extends BaseRelic implements ClickableRelic {
    public static final String ID = makeID("WatcherSoul");

    private static final RelicTier RARITY = RelicTier.RARE;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int CARD_AMOUNT = 1;

    public WatcherSoul() {
        super(ID, RARITY, SOUND);

        setDescriptionWithCard();
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void onRightClick() {
        if (this.counter % 2 == 0) {
            this.flash();

            // Create the custom ModdedSmite and ModdedSafety cards
            AbstractCard smite = new ModdedSmite();
            AbstractCard safety = new ModdedSafety();

            // Add the ethereal cards to the player's hand
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(smite, true));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(safety, true));

            this.counter = 0;
        }
    }

    @Override
    public void atTurnStart() {
        ++this.counter;
    }

    @Override
    public void onVictory() {
        this.counter = 0;
    }

    public void setDescriptionWithCard() {
        this.tips.clear();
        this.description = getUpdatedDescription();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(BaseMod.getKeywordTitle(this.DESCRIPTIONS[2]), BaseMod.getKeywordDescription(this.DESCRIPTIONS[2])));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARD_AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new WatcherSoul();
    }
}
