package soulessences.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import soulessences.utils.PathManager;
import soulessences.utils.TextureLoader;

import static soulessences.SoulEssences.makeID;

public class ChargingPower extends AbstractPower {
    public static final String POWER_ID = makeID("ChargingPower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String SPEECH_TEXT = powerStrings.DESCRIPTIONS[5];

    private static final Texture hiDefImage = TextureLoader.getTexture(PathManager.makePowerPath84("Charging.png"));

    private static final Texture normalImage = TextureLoader.getTexture(PathManager.makePowerPath32("Charging.png"));

    private static final int DAMAGE_AMOUNT = 30;

    private static final int MAX_TURNS = 3;

    private static int CURRENT_TURN = 1;

    public ChargingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.isTurnBased = true;
        this.type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
        this.region48 = new TextureAtlas.AtlasRegion(normalImage, 0, 0, normalImage.getWidth(), normalImage.getHeight());

        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            if (CURRENT_TURN < MAX_TURNS) {
                this.flash();

                CURRENT_TURN++;
            }

            else {
                CURRENT_TURN = 0;

                AbstractDungeon.effectList.add(new SpeechBubble(this.owner.dialogX, this.owner.dialogY, 3.0F, SPEECH_TEXT, true));

                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(DAMAGE_AMOUNT, true),
                        DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, this, 1));
            }

            if (this.amount <= 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }

            this.updateDescription();
        }
    }

    // Reset turn counter for applying effect
    @Override
    public void onVictory() {
        CURRENT_TURN = 0;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + DAMAGE_AMOUNT + DESCRIPTIONS[1] + MAX_TURNS + DESCRIPTIONS[2] + DESCRIPTIONS[3] + CURRENT_TURN + DESCRIPTIONS[4];
    }
}
