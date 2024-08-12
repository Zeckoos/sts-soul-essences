package soulessences.relics.elites;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class NemesisSoul extends BaseRelic {
    public static final String ID = makeID("NemesisSoul");

    private static final RelicTier RARITY = RelicTier.UNCOMMON;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final String[] ENEMY_ID = {"Nemesis"};

    private static final int BUFFER_LIMIT = 3; // The maximum number of Buffer applications
    private static final int BUFFER_AMOUNT = 1; // The amount of Buffer applied each time
    private static final int MAX_TURNS = 3; // The number of turns after which Buffer is applied

    private int bufferApplied = 0;

    public NemesisSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        }

        else {
            ++this.counter;
        }

        if (this.counter % MAX_TURNS == 0 && bufferApplied < BUFFER_LIMIT) {
            bufferApplied++;
            this.counter = 0;

            this.flash();

            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BufferPower(p, BUFFER_AMOUNT)));
        }

        else if (bufferApplied == BUFFER_LIMIT){
            this.grayscale = true; // Mark the relic gray
        }
    }

    @Override
    public void onVictory() {
        this.counter = 0;
        bufferApplied = 0;

        this.grayscale = false; // Revert the mark
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BUFFER_AMOUNT + DESCRIPTIONS[1] + MAX_TURNS + DESCRIPTIONS[2] + BUFFER_LIMIT + DESCRIPTIONS[3];
    }
    @Override
    public AbstractRelic makeCopy() {
        return new NemesisSoul();
    }
}
