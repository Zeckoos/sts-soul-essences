package soulessences.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import soulessences.actions.EnragedAction;
import soulessences.utils.PathManager;
import soulessences.utils.TextureLoader;

import static soulessences.SoulEssences.makeID;

public class EnragedPower extends AbstractPower {
    public static final String POWER_ID = makeID("EnragedPower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture hiDefImage = TextureLoader.getTexture(PathManager.makePowerPath84("Enraged.png"));

    private static final Texture normalImage = TextureLoader.getTexture(PathManager.makePowerPath32("Enraged.png"));

    private static final int STR_DEX_AMOUNT = 3;

    public EnragedPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
        region48 = new TextureAtlas.AtlasRegion(normalImage, 0, 0, normalImage.getWidth(), normalImage.getHeight());
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        atStartOfTurn();
    }

    @Override
    public void atStartOfTurn() {
        this.flash();

        this.addToBot(new EnragedAction(STR_DEX_AMOUNT));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + STR_DEX_AMOUNT + DESCRIPTIONS[1] + STR_DEX_AMOUNT + DESCRIPTIONS[2];
    }
}
