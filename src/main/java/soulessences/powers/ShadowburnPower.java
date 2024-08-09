package soulessences.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import soulessences.utils.PathManager;
import soulessences.utils.TextureLoader;

import static soulessences.SoulEssences.makeID;

public class ShadowburnPower extends AbstractPower {
    public static final String POWER_ID = makeID("ShadowburnPower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture hiDefImage = TextureLoader.getTexture(PathManager.makePowerPath84("ShadowBurn.png"));

    private static final Texture normalImage = TextureLoader.getTexture(PathManager.makePowerPath32("ShadowBurn.png"));

    public ShadowburnPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;

        region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
        region48 = new TextureAtlas.AtlasRegion(normalImage, 0, 0, normalImage.getWidth(), normalImage.getHeight());

        this.updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.type != DamageInfo.DamageType.THORNS && info.owner != this.owner) {
            this.updateDescription();
        }
        return damageAmount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) {
            int damage = calculateDamage();
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(owner, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE)
            );

            if (this.amount == 1) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }

            else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            }
        }
    }

    private int calculateDamage() {
        int currentHp = owner.currentHealth;
        int H = (currentHp / 2) / 12;
        return (H + 1) * 3;
    }

    @Override
    public void updateDescription() {
        int damage = calculateDamage();
        this.description = DESCRIPTIONS[0] + damage + DESCRIPTIONS[1];
    }

}
