package soulessences.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.powers.ExhaustedPower;

import static soulessences.SoulEssences.makeID;

public class LagavulinSoul extends BaseRelic {
    public static final String ID = makeID("LagavulinSoul");

    private static final RelicTier RARITY = RelicTier.COMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int ASLEEP_AMOUNT = 3;

    public LagavulinSoul() {
        super(ID, RARITY, SOUND);

        ENEMY_KEYS.add("Lagavulin");
        ENEMY_KEYS.add("Lagavulin Event");

    }

    @Override
    public void atBattleStart() {
        this.flash();

        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ExhaustedPower(p, ASLEEP_AMOUNT), ASLEEP_AMOUNT));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LagavulinSoul();
    }
}