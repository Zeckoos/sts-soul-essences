package soulessences.relics.bosses;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.powers.MitosisPower;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class SlimeBossSoul extends BaseRelic {
    public static final String ID = makeID("SlimeBossSoul");

    private static final RelicTier RARITY = RelicTier.BOSS;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int MITOSIS_AMOUNT = 1;

    private static final String[] ENEMY_ID = {"SlimeBoss"};

    public SlimeBossSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);

        setDescriptionWithCard();
    }

    public void atBattleStart() {
        this.flash();

        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MitosisPower(p, MITOSIS_AMOUNT), MITOSIS_AMOUNT));

        this.grayscale = true;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    public void setDescriptionWithCard() {
        this.tips.clear();
        this.description = this.DESCRIPTIONS[0];
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(BaseMod.getKeywordTitle(this.DESCRIPTIONS[1]), BaseMod.getKeywordDescription(this.DESCRIPTIONS[1])));
        initializeTips();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SlimeBossSoul();
    }
}
