package soulessences.relics.bosses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import soulessences.actions.ApplyEffectToAllEnemiesAction;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class TheChampSoul extends BaseRelic {
    public static final String ID = makeID("TheChampSoul");

    private static final RelicTier RARITY = RelicTier.BOSS;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final String[] ENEMY_ID = {"Champ"};

    private boolean FIRST_CARD_PLAYED;

    public TheChampSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);
    }

    @Override
    public void atTurnStart() {
        FIRST_CARD_PLAYED = false; // Reset at the start of each turn
    }

    private void applyPowerEffect(AbstractCreature target, AbstractPower power, int amount) {
        if (amount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, power, amount));
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!FIRST_CARD_PLAYED) {
            FIRST_CARD_PLAYED = true;
            int cost = card.costForTurn; // Get the cost of the card played

            // Apply effects based on card type
            if (card.type == AbstractCard.CardType.POWER) {
                applyPowerEffect(AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, cost), cost);
            }

            else if (card.type == AbstractCard.CardType.SKILL) {
                applyPowerEffect(AbstractDungeon.player, new MetallicizePower(AbstractDungeon.player, cost), cost);
            }

            else if (card.type == AbstractCard.CardType.ATTACK) {
                AbstractDungeon.actionManager.addToBottom(new ApplyEffectToAllEnemiesAction(VulnerablePower.class, cost, true));
            }

            // Optionally, show the card briefly with an effect to indicate it's been triggered
            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TheChampSoul();
    }
}
