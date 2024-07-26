package soulessences.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.actions.ApplyEffectToAllEnemiesAction;

import static soulessences.SoulEssences.makeID;

public class TaskMasterSoul extends BaseRelic {
    public static final String ID = makeID("TaskMasterSoul");

    private static final RelicTier RARITY = RelicTier.UNCOMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int WEAK_AMOUNT = 2;

    private static final int STR_AMOUNT = 2;

    public TaskMasterSoul() {
        super(ID, RARITY, SOUND);

        ENEMY_KEYS.add("Slavers");
        ENEMY_KEYS.add("Colosseum Slavers");
    }

    @Override
    public void atTurnStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyEffectToAllEnemiesAction(WeakPower.class, WEAK_AMOUNT, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyEffectToAllEnemiesAction(VulnerablePower.class, STR_AMOUNT, true));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TaskMasterSoul();
    }
}
