package soulessences.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import soulessences.utils.PathManager;
import soulessences.utils.TextureLoader;

import static soulessences.SoulEssences.modID;

public abstract class BaseRelic extends CustomRelic {
    public AbstractCard.CardColor pool = null;

    public RelicType relicType = RelicType.SHARED;

    private final String monsterID; // Links to the respective relic

    public BaseRelic(String ID, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx, String monsterID) {
        super(ID, TextureLoader.getTexture(PathManager.makeRelicPath(ID.replace(modID + ":", "") + ".png")), tier, sfx);

        this.setPool(pool);

        this.monsterID = monsterID;
    }

    public BaseRelic(String ID, RelicTier tier, LandingSound sfx, String monsterID) {
        super(ID, TextureLoader.getTexture(PathManager.makeRelicPath(ID.replace(modID + ":", "") + ".png")), tier, sfx);

        this.monsterID = monsterID;
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
        if (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss
                || AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite)) {
            return false;
        }
        // Check all monsters in the room to see if one matches the expected Monster ID
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (monsterID.equals(monster.id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
