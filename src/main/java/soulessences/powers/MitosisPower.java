package soulessences.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import soulessences.minions.AcidSlime;
import soulessences.minions.SpikeSlime;
import soulessences.utils.PathManager;
import soulessences.utils.TextureLoader;

import java.util.Random;

import static soulessences.SoulEssences.makeID;

public class MitosisPower extends AbstractPower {
    public static final String POWER_ID = makeID("MitosisPower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture hiDefImage = TextureLoader.getTexture(PathManager.makePowerPath84("Mitosis.png"));

    private static final Texture normalImage = TextureLoader.getTexture(PathManager.makePowerPath32("Mitosis.png"));

    public MitosisPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
        region48 = new TextureAtlas.AtlasRegion(normalImage, 0, 0, normalImage.getWidth(), normalImage.getHeight());

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (owner.currentHealth <= owner.maxHealth / 2 && owner.lastDamageTaken > 0) {
            activateMitosis();
        }
    }

    private void activateMitosis() {
            this.flash();

            Random random = new Random();
            AbstractOrb slime = random.nextBoolean() ? new AcidSlime() : new SpikeSlime();

            // Add the slime as a new orb
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(slime));

            this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }
}
