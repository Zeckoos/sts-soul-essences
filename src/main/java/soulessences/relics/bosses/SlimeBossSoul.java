package soulessences.relics.bosses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.powers.MitosisPower;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class SlimeBossSoul extends BaseRelic {
    public static final String ID = makeID("SlimeBossSoul");

    private static final RelicTier RARITY = RelicTier.BOSS;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int MITOSIS_AMOUNT = 1;

    private static final int ORB_SLOTS_ADDED = 1;

    private static final String[] ENEMY_ID = {"SlimeBoss"};
    public SlimeBossSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);
    }

    public void atBattleStart() {
        this.flash();

        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MitosisPower(p, MITOSIS_AMOUNT), MITOSIS_AMOUNT));
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxOrbSlots(ORB_SLOTS_ADDED, true);
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.decreaseMaxOrbSlots(ORB_SLOTS_ADDED);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SlimeBossSoul();
    }
}
