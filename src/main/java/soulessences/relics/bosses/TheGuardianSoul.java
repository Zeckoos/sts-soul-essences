package soulessences.relics.bosses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import soulessences.powers.ModeshiftPower;
import soulessences.relics.BaseRelic;

import static soulessences.SoulEssences.makeID;

public class TheGuardianSoul extends BaseRelic {
    public static final String ID = makeID("TheGuardianSoul");

    private static final RelicTier RARITY = RelicTier.BOSS;

    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final String[] ENEMY_ID = {"TheGuardian"};

    private static final int DMG_THRESHOLD = 25;

    public TheGuardianSoul() {
        super(ID, RARITY, SOUND, ENEMY_ID);
    }

    public void atBattleStart() {
        this.flash();

        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ModeshiftPower(AbstractDungeon.player, DMG_THRESHOLD)));

        this.grayscale = true;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TheGuardianSoul();
    }

}
