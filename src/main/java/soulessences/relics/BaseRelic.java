package soulessences.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import soulessences.helpers.RelicDropManager;
import soulessences.utils.PathManager;
import soulessences.utils.TextureLoader;

import java.util.Arrays;

import static soulessences.SoulEssences.modID;

public abstract class BaseRelic extends CustomRelic {
    public AbstractCard.CardColor pool = null;

    public RelicType relicType = RelicType.SHARED;

    private final String[] monsterIDs; // Links to the respective relic

    public BaseRelic(String ID, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx, String[] monsterIDs) {
        super(ID, TextureLoader.getTexture(PathManager.makeRelicPath(ID.replace(modID + ":", "") + ".png")), tier, sfx);

        this.setPool(pool);

        this.monsterIDs = monsterIDs;
    }

    public BaseRelic(String ID, RelicTier tier, LandingSound sfx, String[] monsterIDs) {
        super(ID, TextureLoader.getTexture(PathManager.makeRelicPath(ID.replace(modID + ":", "") + ".png")), tier, sfx);

        this.monsterIDs = monsterIDs;
    }

    private void setPool(AbstractCard.CardColor pool) {
        switch (pool) {
            case RED:
                this.relicType = RelicType.RED;
                return;
            case GREEN:
                this.relicType = RelicType.GREEN;
                return;
            case BLUE:
                this.relicType = RelicType.BLUE;
                return;
            case PURPLE:
                this.relicType = RelicType.PURPLE;
                return;
        }
        this.pool = pool;
    }

    @Override
    public boolean canSpawn() {
        // Ensure the relic drops only if it matches the current enemy and no soul relic has been dropped yet
        if (monsterIDs == null || !isInCombat() || !RelicDropManager.canDropSoulRelic(this.relicId)) {
            return false;
        }

        // Check if the correct monster ID matches
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (Arrays.asList(monsterIDs).contains(monster.id)) {
                RelicDropManager.markSoulRelicDropped(this.relicId);
                return true;
            }
        }
        return false;
    }

    // Utility method to ensure the relic is not dropped during events or other non-combat scenarios
    private boolean isInCombat() {
        return AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
