package soulessences.relics.starters;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.HeatsinkPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class DefectSoul extends BaseRelic {
    public static final String ID = makeID("DefectSoul");

    private static final RelicTier RARITY = RelicTier.RARE;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int ORB_SLOT = 1;

    private static final int DRAW_AMOUNT = 2;

    private static final int PLASMA_AMOUNT = 1;

    public DefectSoul() {
        super(ID, RARITY, SOUND);

        setDescriptionWithCard();
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(ORB_SLOT));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Plasma()));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HeatsinkPower(AbstractDungeon.player, DRAW_AMOUNT), DRAW_AMOUNT));
    }

    public void setDescriptionWithCard() {
        this.tips.clear();
        this.description = getUpdatedDescription();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(BaseMod.getKeywordTitle(this.DESCRIPTIONS[4]), BaseMod.getKeywordDescription(this.DESCRIPTIONS[4])));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + ORB_SLOT + DESCRIPTIONS[1] + DRAW_AMOUNT + DESCRIPTIONS[2] + PLASMA_AMOUNT + DESCRIPTIONS[3];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DefectSoul();
    }
}
