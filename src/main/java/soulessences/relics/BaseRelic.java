package soulessences.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import soulessences.utils.PathManager;
import soulessences.utils.TextureLoader;

import java.util.HashSet;
import java.util.Set;

import static soulessences.SoulEssences.modID;

public abstract class BaseRelic extends CustomRelic {
    public AbstractCard.CardColor pool = null;

    public RelicType relicType = RelicType.SHARED;

    protected Set<String> ENEMY_KEYS = new HashSet<>();

    public BaseRelic(String ID, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx) {
        super(ID, TextureLoader.getTexture(PathManager.makeRelicPath(ID.replace(modID + ":", "") + ".png")), tier, sfx);

        this.setPool(pool);
    }

    public BaseRelic(String ID, RelicTier tier, LandingSound sfx) {
        super(ID, TextureLoader.getTexture(PathManager.makeRelicPath(ID.replace(modID + ":", "") + ".png")), tier, sfx);
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
        return ENEMY_KEYS.contains(AbstractDungeon.lastCombatMetricKey);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
