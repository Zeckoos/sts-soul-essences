package soulessences.relics.bosses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.powers.ShadowburnPower;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class HexaghostSoul extends BaseRelic {
    public static final String ID = makeID("HexaghostSoul");

    private static final RelicTier RARITY = RelicTier.BOSS;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final String[] ENEMY_ID = {"Hexaghost"};

    private static final int CARDS_AMOUNT = 3;

    private static final int MAX_TURNS = 3;

    public HexaghostSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);
    }

    @Override
    public void atBattleStart() {
        AbstractCard card = new Burn();

        this.flash(); // Visual effect to indicate the relic's activation

        for (int i = 0; i < CARDS_AMOUNT; i++) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, true));
        }

        for (int i = 0; i < MAX_TURNS; i++) {
            // Apply ShadowBurnPower to a random enemy at the start of battle
            AbstractCreature target = AbstractDungeon.getRandomMonster();

            if (target != null) {
                this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new ShadowburnPower(target, 1), 1));
            }
        }


        this.grayscale = true;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MAX_TURNS + DESCRIPTIONS[1] + CARDS_AMOUNT + DESCRIPTIONS[2];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HexaghostSoul();
    }
}
