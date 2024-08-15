package soulessences.minions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

import java.util.ArrayList;

public abstract class BaseMinionAnimation extends AbstractOrb {
    protected Skeleton skeleton;
    protected AnimationState state;
    private static final SkeletonMeshRenderer sr = new SkeletonMeshRenderer();
    private final TextureAtlas atlas;

    public BaseMinionAnimation(String id, String name, String atlasPath, String skeletonPath, float scale) {
        this.ID = id;
        this.name = name;
        this.atlas = new TextureAtlas(atlasPath);
        this.tips = new ArrayList<>();

        loadAnimation(skeletonPath, scale);

        this.applyFocus();
        this.updateDescription();
    }

    private void loadAnimation(String skeletonPath, float scale) {
        SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(Settings.scale * scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonPath));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.state = new AnimationState(new AnimationStateData(skeletonData));

        // Set initial animation, loop if needed
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addListener(new SlimeAnimListener());
    }

    @Override
    public void update() {
        super.update();
        state.update(Gdx.graphics.getDeltaTime());
        state.apply(skeleton);
        skeleton.updateWorldTransform();
    }

    @Override
    public void render(SpriteBatch sb) {
        // Set the position for the skeleton
        this.skeleton.setPosition(this.cX, this.cY);

        // Update the skeleton's world transform and state for animation
        this.skeleton.updateWorldTransform();
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);

        // End the current SpriteBatch, switch to using the PolygonSpriteBatch for rendering the skeleton
        sb.end();
        CardCrawlGame.psb.begin();
        CardCrawlGame.psb.setColor(sb.getColor());

        // Draw the skeleton
        sr.draw(CardCrawlGame.psb, this.skeleton);

        // End the PolygonSpriteBatch and begin the SpriteBatch again
        CardCrawlGame.psb.end();
        sb.begin();

        // Reset the blend function for the SpriteBatch
        sb.setBlendFunction(770, 771);
    }

    public void applyFocus() {
        AbstractPower focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if (focus != null) {
            this.passiveAmount = Math.max(0, this.basePassiveAmount + focus.amount);
            this.evokeAmount = Math.max(0, this.baseEvokeAmount + focus.amount);
        } else {
            this.passiveAmount = this.basePassiveAmount;
            this.evokeAmount = this.baseEvokeAmount;
        }
        updateDescription();
    }

    @Override
    public void playChannelSFX() {
    }

    @Override
    public void updateDescription() {
        this.description = "Passive: " + this.passiveAmount + " (Passive effect)\nEvoke: " + this.evokeAmount + " (Evoke effect)";
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
}
