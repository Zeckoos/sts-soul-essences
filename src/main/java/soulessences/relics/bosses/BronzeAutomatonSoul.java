package soulessences.relics.bosses;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class BronzeAutomatonSoul extends BaseRelic {
    public static final String ID = makeID("BronzeAutomatonSoul");

    private static final RelicTier RARITY = RelicTier.BOSS;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final String[] ENEMY_ID = {"BronzeAutomaton"};

    private String CARD_NAME = "None";

    public BronzeAutomatonSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);

        updateDescription();
    }

    @Override
    public void atBattleStart() {
        this.CARD_NAME = "None";
    }

    public void moveRandomCardToHand() {
        AbstractPlayer player = AbstractDungeon.player;

        if (!player.discardPile.isEmpty()) {
            // Get a random card from the discard pile
            AbstractCard card = player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng);

            // Move the card from the discard pile to the hand
            if (player.hand.size() < Settings.MAX_HAND_SIZE) {
                player.discardPile.moveToHand(card, player.discardPile);
                CARD_NAME = card.name;
            }
            else {
                CARD_NAME = "None";
            }
            updateDescription();
        }
    }

    public void atTurnStart() {
        this.flash();

        moveRandomCardToHand();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + CARD_NAME + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();  // Force the UI to refresh and show the updated description
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BronzeAutomatonSoul();
    }
}
