package soulessences.relics.elites;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.actions.CopyAttackAction;
import com.megacrit.cardcrawl.random.Random;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class BookOfStabbingSoul extends BaseRelic {
    public static final String ID = makeID("BookOfStabbingSoul");

    private static final RelicTier RARITY = RelicTier.RARE;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private boolean firstAttackPlayed = false;

    private static final int COPIES = 2;

    private final Random RANDOM;

    private static final String[] ENEMY_ID = {"BookOfStabbing"};

    public BookOfStabbingSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);
        this.RANDOM = new Random();

    }

    @Override
    public void atTurnStart() {
        firstAttackPlayed = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!firstAttackPlayed && card.type == AbstractCard.CardType.ATTACK) {
            firstAttackPlayed = true;

            if (RANDOM.randomBoolean(0.85f)) {
                this.flash();

                AbstractDungeon.actionManager.addToBottom(new CopyAttackAction(card, COPIES, (AbstractMonster) action.target));
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BookOfStabbingSoul();
    }

}
