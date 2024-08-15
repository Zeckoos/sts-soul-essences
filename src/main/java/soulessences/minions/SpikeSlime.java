package soulessences.minions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import soulessences.SoulEssences;
import soulessences.utils.MinionStrings;
import soulessences.utils.PathManager;

import static soulessences.SoulEssences.makeID;

public class SpikeSlime extends BaseMinionAnimation {
    private static final String ID = makeID("SpikeSlime");

    private static final MinionStrings minionStrings = SoulEssences.getMinionString(ID);

    public static final String NAME = minionStrings.NAME;

    public static final String[] DESCRIPTIONS = minionStrings.DESCRIPTION;

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
    public void playChannelSFX() {
        CardCrawlGame.sound.play("MONSTER_SLIME_ATTACK", 0.1F);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.passiveAmount) +
                String.format(DESCRIPTIONS[1], this.evokeAmount);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new SpikeSlime();
    }
}
