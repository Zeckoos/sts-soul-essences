package soulessences.relics.starters;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class SilentSoul extends BaseRelic {
    public static final String ID = makeID("SilentSoul");

    private static final RelicTier RARITY = RelicTier.RARE;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int DRAW_DISCARD_AMOUNT = 2;

    private static final int BONUS_DRAW_AMOUNT = 1;

    public SilentSoul() {
        super(ID, RARITY, SOUND);

        setDescriptionWithCard();
    }

    @Override
    public void atTurnStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ScryAction(DRAW_DISCARD_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, BONUS_DRAW_AMOUNT));
    }

    public void setDescriptionWithCard() {
        this.tips.clear();
        this.description = getUpdatedDescription();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(BaseMod.getKeywordTitle(this.DESCRIPTIONS[3]), BaseMod.getKeywordDescription(this.DESCRIPTIONS[3])));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DRAW_DISCARD_AMOUNT + DESCRIPTIONS[1] + BONUS_DRAW_AMOUNT + DESCRIPTIONS[2];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SilentSoul();
    }
}
