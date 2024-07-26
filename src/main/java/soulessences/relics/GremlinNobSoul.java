package soulessences.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import soulessences.powers.EnragedPower;

import static soulessences.SoulEssences.logger;
import static soulessences.SoulEssences.makeID;

public class GremlinNobSoul extends BaseRelic {
    public static final String ID = makeID("GremlinNobSoul");

    private static final RelicTier RARITY = RelicTier.COMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int ENRAGE_AMOUNT = 1;

    public GremlinNobSoul() {
        super(ID, RARITY, SOUND);

        ENEMY_KEYS.add("Gremlin Nob");
        ENEMY_KEYS.add("Colosseum Nobs");

        logger.info("GremlinNobSoul spawn condition:{}", this.canSpawn());
    }

    @Override
    public void atBattleStart() {
        this.flash();

        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.effectList.add(new SpeechBubble(p.dialogX,
                p.dialogY,3.0F, "ARGHHH!!!", true));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnragedPower(p, ENRAGE_AMOUNT), ENRAGE_AMOUNT));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GremlinNobSoul();
    }
}