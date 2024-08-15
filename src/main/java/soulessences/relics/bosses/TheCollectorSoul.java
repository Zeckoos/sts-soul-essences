package soulessences.relics.bosses;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import soulessences.relics.BaseRelic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static soulessences.SoulEssences.makeID;

public class TheCollectorSoul extends BaseRelic {
    public static final String ID = makeID("TheCollectorSoul");

    private static final RelicTier RARITY = RelicTier.BOSS;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final String[] ENEMY_ID = {"TheCollector"};

    private static final int TEMP_HP_GAIN = 1;

    private final List<String> SPEECH_TEXTS = new ArrayList<>();

    private static int TOTAL_TEMP_HP = 0;

    public TheCollectorSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);

        this.counter = TOTAL_TEMP_HP;

        SPEECH_TEXTS.add(this.DESCRIPTIONS[1]);
        SPEECH_TEXTS.add(this.DESCRIPTIONS[2]);
    }

    public void atBattleStart() {
        this.flash();

        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(p, p, TOTAL_TEMP_HP));
    }

    private void updateCounter() {
        this.counter = TOTAL_TEMP_HP;
    }

    public void atTurnStart() {
        this.updateCounter();
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        AbstractPlayer p = AbstractDungeon.player;

        this.flash();

        TOTAL_TEMP_HP += TEMP_HP_GAIN;

        Collections.shuffle(SPEECH_TEXTS);

        AbstractDungeon.effectList.add(new SpeechBubble(p.dialogX,
                p.dialogY,3.0F, SPEECH_TEXTS.get(0), true));

        this.updateCounter();
    }

    public static int getTotalTempHp() {
        return TOTAL_TEMP_HP;
    }

    public static void setTempHp(int tempHp) {
        TOTAL_TEMP_HP = tempHp;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TheCollectorSoul();
    }
}
