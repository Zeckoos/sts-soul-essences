package soulessences.relics.elites;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.actions.ApplyEffectToAllEnemiesAction;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class TaskMasterSoul extends BaseRelic {
    public static final String ID = makeID("TaskMasterSoul");

    private static final RelicTier RARITY = RelicTier.UNCOMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int WEAK_AMOUNT = 3;

    private static final int STR_AMOUNT = 3;

    public TaskMasterSoul() {
        super(ID, RARITY, SOUND);

        ENEMY_KEYS.add("Slavers");
        ENEMY_KEYS.add("Colosseum Slavers");
    }

    @Override
    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        }

        else {
            ++this.counter;
        }

        if (this.counter % 3 == 0) {
            this.counter = 0;
            this.flash();

            AbstractDungeon.actionManager.addToBottom(new ApplyEffectToAllEnemiesAction(WeakPower.class, WEAK_AMOUNT, true));
            AbstractDungeon.actionManager.addToBottom(new ApplyEffectToAllEnemiesAction(VulnerablePower.class, STR_AMOUNT, true));
        }
    }

    @Override
    public void onVictory() {
        this.counter = 0;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TaskMasterSoul();
    }
}
