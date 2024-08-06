package soulessences.relics.elites;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.actions.ApplyEffectToAllEnemiesAction;
import soulessences.powers.AngryPower;
import soulessences.powers.ChargingPower;
import soulessences.relics.BaseRelic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static soulessences.SoulEssences.makeID;

public class GremlinLeaderSoul extends BaseRelic {
    public static final String ID = makeID("GremlinLeaderSoul");

    private static final RelicTier RARITY = RelicTier.RARE;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final String[] ENEMY_ID = {"GremlinLeader"};

    private static final int EFFECT_AMOUNT = 2;

    private static final int MAX_TURNS = 2;

    private static final int BLOCK_AMOUNT = 11;

    private static final int ANGRY_AMOUNT = 2;

    private static final int WEAK_AMOUNT = 2;

    private static final int CHARGING_AMOUNT = 3;

    private static final int DAMAGE_AMOUNT = 11;

    public GremlinLeaderSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);
    }

    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart()  {
        if (this.counter == -1) {
            this.counter += 2;
        }

        else {
            ++this.counter;
        }

        AbstractPlayer p = AbstractDungeon.player;
        List<Runnable> actions = new ArrayList<>();

        actions.add(() -> AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, BLOCK_AMOUNT))); // Shield Gremlin
        actions.add(() -> AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AngryPower(p, ANGRY_AMOUNT), ANGRY_AMOUNT))); // Mad Gremlin
        actions.add(() -> AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ChargingPower(p, CHARGING_AMOUNT), CHARGING_AMOUNT))); // Gremlin Wizard
        actions.add(() -> AbstractDungeon.actionManager.addToBottom(new ApplyEffectToAllEnemiesAction(WeakPower.class, WEAK_AMOUNT, true))); // Fat Gremlin
        actions.add(() -> AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(DAMAGE_AMOUNT, true),
                DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL))); // Sneaky Gremlin

        if (this.counter % 2 == 0) {

            this.counter = 0;

            Collections.shuffle(actions);
            this.flash();

            actions.get(0).run();
            actions.get(1).run();
        }
    }

    // Reset relic's counter
    @Override
    public void onVictory() {
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MAX_TURNS + DESCRIPTIONS[1] + EFFECT_AMOUNT + DESCRIPTIONS[2];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GremlinLeaderSoul();
    }
}
