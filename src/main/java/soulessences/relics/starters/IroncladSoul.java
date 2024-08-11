package soulessences.relics.starters;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class IroncladSoul extends BaseRelic {
    public static final String ID = makeID("IroncladSoul");

    private static final RelicTier RARITY = RelicTier.RARE;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int HEAL_AMOUNT = 6;

    private static final int MAX_HP_GAIN = 2;

    public IroncladSoul() {
        super(ID, RARITY, SOUND);

        setDescriptionWithCard();
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (this.isEligible(m)) {
            flash();
            AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL_AMOUNT));
            AbstractDungeon.player.increaseMaxHp(MAX_HP_GAIN, true);
        }
    }

    private boolean isEligible(AbstractMonster m) {
        return m.maxHealth > AbstractDungeon.player.maxHealth / 3; // 1/3 of player's max hp
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
        return DESCRIPTIONS[0] + HEAL_AMOUNT + DESCRIPTIONS[1] + MAX_HP_GAIN + DESCRIPTIONS[2];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new IroncladSoul();
    }
}
