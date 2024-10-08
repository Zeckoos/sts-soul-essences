package soulessences.relics.elites;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import soulessences.powers.EnragedPower;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class GremlinNobSoul extends BaseRelic {
    public static final String ID = makeID("GremlinNobSoul");

    private static final RelicTier RARITY = RelicTier.UNCOMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int ENRAGE_AMOUNT = 1;

    private static final String[] ENEMY_ID = {"GremlinNob", "FF_GremlinNob"};

    private final String SPEECH_TEXT = this.DESCRIPTIONS[1];

    public GremlinNobSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);
    }

    @Override
    public void atBattleStart() {
        this.flash();

        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.effectList.add(new SpeechBubble(p.dialogX,
                p.dialogY,3.0F, SPEECH_TEXT, true));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnragedPower(p, ENRAGE_AMOUNT), ENRAGE_AMOUNT));

        this.grayscale = true;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GremlinNobSoul();
    }
}