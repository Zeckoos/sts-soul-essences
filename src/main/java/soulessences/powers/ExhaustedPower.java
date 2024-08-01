package soulessences.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import soulessences.utils.PathManager;
import soulessences.utils.TextureLoader;
import static soulessences.SoulEssences.makeID;

public class ExhaustedPower extends AbstractPower {
    public static final String POWER_ID = makeID("ExhaustedPower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture hiDefImage = TextureLoader.getTexture(PathManager.makePowerPath84("Exhausted.png"));

    private static final Texture normalImage = TextureLoader.getTexture(PathManager.makePowerPath32("Exhausted.png"));

    private static final int METALLICIZE_AMOUNT = 8;

    private static final int STRENGTH_AMOUNT = 3;

    private static final int DEXTERITY_AMOUNT = 3;

    private int maxEnergy;

    private static final int BLOCK_RATIO = 15;

    private static final int MIN_ENERGY = 1;

    public ExhaustedPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
        region48 = new TextureAtlas.AtlasRegion(normalImage, 0, 0, normalImage.getWidth(), normalImage.getHeight());

        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.effectList.add(new SpeechBubble(this.owner.dialogX, this.owner.dialogY, 3.0F, "My body is exhausted...", true));

        maxEnergy = AbstractDungeon.player.energy.energy; // Set player's max energy
        setEnergyToMin();
        gainBlock();
    }

    private void gainBlock() {
        int blockAmount = Math.round(this.owner.maxHealth * 0.15f);

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, blockAmount));
    }

    private void setEnergyToMin() {
        AbstractDungeon.actionManager.addToTop(new LoseEnergyAction(maxEnergy - 1));
    }

    @Override
    public void atStartOfTurn() {
        if (this.amount >= 1) {
            this.flash();

            setEnergyToMin();
            gainBlock();
        }
    }

    private void applyBuffs() {
        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, METALLICIZE_AMOUNT), METALLICIZE_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTH_AMOUNT), STRENGTH_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, DEXTERITY_AMOUNT), DEXTERITY_AMOUNT));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            if (this.amount == 1) {
                AbstractDungeon.effectList.add(new SpeechBubble(this.owner.dialogX, this.owner.dialogY, 3.0F, "Feel my power!", true));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));

                applyBuffs();
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            }

        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + BLOCK_RATIO + DESCRIPTIONS[1] + MIN_ENERGY + DESCRIPTIONS[2];
    }
}
