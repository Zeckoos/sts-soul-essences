package soulessences.minions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import soulessences.utils.PathManager;

import static soulessences.SoulEssences.makeID;

public class SpikeSlime extends BaseSlime {
    private static final String ID = makeID("SpikeSlime");
    private static final String NAME = "Spike Slime";
    private static final String ATLAS_PATH = PathManager.makeMinionsAnimationPath("slimeAltS/skeleton.atlas");
    private static final String SKELETON_PATH = PathManager.makeMinionsAnimationPath("slimeAltS/skeleton.json");
    private static final float SCALE = 1.0f;

    public SpikeSlime() {
        super(ID, NAME, ATLAS_PATH, SKELETON_PATH, SCALE);

        this.baseEvokeAmount = 3;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 1;
        this.passiveAmount = this.basePassiveAmount;
    }

    protected void applyEffect() {
        AbstractPlayer player  = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(player, AbstractDungeon.player, new PlatedArmorPower(player, passiveAmount), passiveAmount));
    }

    @Override
    public void onEndOfTurn() {
        applyEffect();
    }

    @Override
    public void onEvoke() {
        AbstractPlayer player  = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(player, AbstractDungeon.player, new PlatedArmorPower(player, evokeAmount), evokeAmount));
    }

    @Override
    public void updateDescription() {
        this.description = String.format("#yPassive: Grants %d #yPlated Armor to the player at the end of each turn. " +
                        "NL #yEvoke: Grants %d Plated Armor.",
                this.passiveAmount, this.evokeAmount);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new SpikeSlime();
    }
}
