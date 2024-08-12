package soulessences.minions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.PoisonPower;
import soulessences.utils.PathManager;

import static soulessences.SoulEssences.makeID;

public class AcidSlime extends BaseSlime {
    private static final String ID = makeID("AcidSlime");
    private static final String NAME = "Acid Slime";
    private static final String ATLAS_PATH = PathManager.makeMinionsAnimationPath("slimeS/skeleton.atlas");
    private static final String SKELETON_PATH = PathManager.makeMinionsAnimationPath("slimeS/skeleton.json");
    private static final float SCALE = 1.0f;

    public AcidSlime() {
        super(ID, NAME, ATLAS_PATH, SKELETON_PATH, SCALE);

        this.baseEvokeAmount = 6;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 3;
        this.passiveAmount = this.basePassiveAmount;
    }

    protected void applyEffect() {
        AbstractCreature target = AbstractDungeon.getRandomMonster();
        if (target != null) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(target, AbstractDungeon.player, new PoisonPower(target, AbstractDungeon.player, passiveAmount), passiveAmount)
            );
        }
    }

    @Override
    public void onEndOfTurn() {
        applyEffect();
    }

    @Override
    public void updateDescription() {
        this.description = String.format("#yPassive: Applies %d #yPoison to a random enemy at the end of each turn. " +
                        "NL #yEvoke: Applies %d #yPoison to a random enemy.",
                this.passiveAmount, this.evokeAmount);
    }

    @Override
    public void onEvoke() {
        AbstractCreature target = AbstractDungeon.getRandomMonster();
        if (target != null) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(target, AbstractDungeon.player, new PoisonPower(target, AbstractDungeon.player,
                            evokeAmount), evokeAmount)
            );
        }
    }

    @Override
    public AbstractOrb makeCopy() {
        return new AcidSlime();
    }
}
