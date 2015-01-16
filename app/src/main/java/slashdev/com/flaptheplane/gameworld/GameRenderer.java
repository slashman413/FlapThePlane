package slashdev.com.flaptheplane.gameworld;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import slashdev.com.flaptheplane.gamehelpers.AssetLoader;
import slashdev.com.flaptheplane.gameobjects.Boss;
import slashdev.com.flaptheplane.gameobjects.Grass;
import slashdev.com.flaptheplane.gameobjects.Pipe;
import slashdev.com.flaptheplane.gameobjects.ScrollHandler;
import slashdev.com.flaptheplane.inputhandlers.GameScreenInputHandler;
import slashdev.com.flaptheplane.tweenaccessors.Value;
import slashdev.com.flaptheplane.tweenaccessors.ValueAccessor;

public class GameRenderer
{
    private Value alpha = new Value();
    private Grass backGrass;
    private TextureRegion bar;
    private SpriteBatch batcher;
    private TextureRegion bg;
    private Boss boss;
    private Animation bossAnimation;
    private TextureRegion bossMid;
    private OrthographicCamera cam;
    private Grass frontGrass;
    private TextureRegion gameOver;
    private TextureRegion grass;
    private TweenManager manager;
    private GameScreenInputHandler gameStage;
    private int midPointY;
    private GameWorld myWorld;
    private TextureRegion noStar;
    private Pipe pipe1;
    private Pipe pipe2;
    private Pipe pipe3;
    private TextureRegion ready;
    private TextureRegion retry;
    private TextureRegion scoreboard;
    private ScrollHandler scroller;
    private ShapeRenderer shapeRenderer;
    private TextureRegion skullDown;
    private TextureRegion skullUp;
    private TextureRegion star;
    private Color transitionColor;
    private TextureRegion gameLogo;

    public GameRenderer(GameWorld paramGameWorld, int paramInt1, int paramInt2)
    {
        this.myWorld = paramGameWorld;
        this.midPointY = paramInt2;
        this.gameStage = (GameScreenInputHandler)Gdx.input.getInputProcessor();
        this.cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.cam.setToOrtho(true, 136.0F, paramInt1);
        this.batcher = new SpriteBatch();
        this.batcher.setProjectionMatrix(this.cam.combined);
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setProjectionMatrix(this.cam.combined);
        initGameObjects();
        initAssets();
        this.transitionColor = new Color();
        prepareTransition(255, 255, 255, 0.5F);
    }

    private void drawBird(float paramFloat)
    {
        if (this.boss.shouldntFlap())
        {
            this.batcher.draw(this.bossMid, this.boss.getX(), this.boss.getY(), this.boss.getWidth() / 2.0F, this.boss.getHeight() / 2.0F, this.boss.getWidth(), this.boss.getHeight(), 1.0F, 1.0F, this.boss.getRotation());
            return;
        }
        this.batcher.draw(this.bossAnimation.getKeyFrame(paramFloat), this.boss.getX(), this.boss.getY(), this.boss.getWidth() / 2.0F, this.boss.getHeight() / 2.0F, this.boss.getWidth(), this.boss.getHeight(), 1.0F, 1.0F, this.boss.getRotation());
    }

    private void drawBirdCentered(float paramFloat)
    {
        this.batcher.draw(this.bossAnimation.getKeyFrame(paramFloat), 59.0F, this.boss.getY() - 15.0F, this.boss.getWidth() / 2.0F, this.boss.getHeight() / 2.0F, this.boss.getWidth(), this.boss.getHeight(), 1.0F, 1.0F, this.boss.getRotation());
    }

    private void drawGameOver()
    {
        this.batcher.draw(this.gameOver, 24.0F, -50 + this.midPointY, 92.0F, 14.0F);
    }

    private void drawGrass()
    {
        this.batcher.draw(this.grass, this.frontGrass.getX(), this.frontGrass.getY(), this.frontGrass.getWidth(), this.frontGrass.getHeight());
        this.batcher.draw(this.grass, this.backGrass.getX(), this.backGrass.getY(), this.backGrass.getWidth(), this.backGrass.getHeight());
    }

    private void drawGameLogo()
    {
        this.batcher.draw(this.gameLogo, 12.0F, -80 + this.midPointY, this.gameLogo.getRegionWidth() / 1.2F, this.gameLogo.getRegionHeight() / 1.2F);
    }

    private void drawPipes()
    {
        this.batcher.draw(this.bar, this.pipe1.getX(), this.pipe1.getY(), this.pipe1.getWidth(), this.pipe1.getHeight());
        this.batcher.draw(this.bar, this.pipe1.getX(), 45.0F + (this.pipe1.getY() + this.pipe1.getHeight()), this.pipe1.getWidth(), 66 + this.midPointY - (45 + this.pipe1.getHeight()));
        this.batcher.draw(this.bar, this.pipe2.getX(), this.pipe2.getY(), this.pipe2.getWidth(), this.pipe2.getHeight());
        this.batcher.draw(this.bar, this.pipe2.getX(), 45.0F + (this.pipe2.getY() + this.pipe2.getHeight()), this.pipe2.getWidth(), 66 + this.midPointY - (45 + this.pipe2.getHeight()));
        this.batcher.draw(this.bar, this.pipe3.getX(), this.pipe3.getY(), this.pipe3.getWidth(), this.pipe3.getHeight());
        this.batcher.draw(this.bar, this.pipe3.getX(), 45.0F + (this.pipe3.getY() + this.pipe3.getHeight()), this.pipe3.getWidth(), 66 + this.midPointY - (45 + this.pipe3.getHeight()));
    }

    private void drawReady()
    {
        this.batcher.draw(this.ready, 36.0F, -50 + this.midPointY, 68.0F, 14.0F);
    }

    private void drawRetry()
    {
        this.batcher.draw(this.retry, 36.0F, 10 + this.midPointY, 66.0F, 14.0F);
    }

    private void drawScore()
    {
        int i = this.myWorld.getScoreCharSequence().length();
        AssetLoader.shadow.draw(this.batcher, this.myWorld.getScoreCharSequence(), 68 - i * 3, -82 + this.midPointY);
        AssetLoader.font.draw(this.batcher, this.myWorld.getScoreCharSequence(), 68 - i * 3, -83 + this.midPointY);
    }

    private void drawScoreboard()
    {
        this.batcher.draw(this.scoreboard, 22.0F, -30 + this.midPointY, 97.0F, 37.0F);
        this.batcher.draw(this.noStar, 25.0F, -15 + this.midPointY, 10.0F, 10.0F);
        this.batcher.draw(this.noStar, 37.0F, -15 + this.midPointY, 10.0F, 10.0F);
        this.batcher.draw(this.noStar, 49.0F, -15 + this.midPointY, 10.0F, 10.0F);
        this.batcher.draw(this.noStar, 61.0F, -15 + this.midPointY, 10.0F, 10.0F);
        this.batcher.draw(this.noStar, 73.0F, -15 + this.midPointY, 10.0F, 10.0F);
        if (this.myWorld.getScore() > 2) {
            this.batcher.draw(this.star, 73.0F, -15 + this.midPointY, 10.0F, 10.0F);
        }
        if (this.myWorld.getScore() > 17) {
            this.batcher.draw(this.star, 61.0F, -15 + this.midPointY, 10.0F, 10.0F);
        }
        if (this.myWorld.getScore() > 50) {
            this.batcher.draw(this.star, 49.0F, -15 + this.midPointY, 10.0F, 10.0F);
        }
        if (this.myWorld.getScore() > 80) {
            this.batcher.draw(this.star, 37.0F, -15 + this.midPointY, 10.0F, 10.0F);
        }
        if (this.myWorld.getScore() > 120) {
            this.batcher.draw(this.star, 25.0F, -15 + this.midPointY, 10.0F, 10.0F);
        }
        int i = this.myWorld.getScoreCharSequence().length();
        AssetLoader.blackFont.draw(this.batcher, this.myWorld.getScoreCharSequence(), 104 - i * 2, -20 + this.midPointY);
        int j = AssetLoader.getHighScoreCharSequence().length();
        AssetLoader.blackFont.draw(this.batcher, AssetLoader.getHighScoreCharSequence(), 104.0F - 2.5F * j, -3 + this.midPointY);
    }

    private void drawSkulls()
    {
        this.batcher.draw(this.skullUp, this.pipe1.getX() - 1.0F, this.pipe1.getY() + this.pipe1.getHeight() - 14.0F, 24.0F, 14.0F);
        this.batcher.draw(this.skullDown, this.pipe1.getX() - 1.0F, 45.0F + (this.pipe1.getY() + this.pipe1.getHeight()), 24.0F, 14.0F);
        this.batcher.draw(this.skullUp, this.pipe2.getX() - 1.0F, this.pipe2.getY() + this.pipe2.getHeight() - 14.0F, 24.0F, 14.0F);
        this.batcher.draw(this.skullDown, this.pipe2.getX() - 1.0F, 45.0F + (this.pipe2.getY() + this.pipe2.getHeight()), 24.0F, 14.0F);
        this.batcher.draw(this.skullUp, this.pipe3.getX() - 1.0F, this.pipe3.getY() + this.pipe3.getHeight() - 14.0F, 24.0F, 14.0F);
        this.batcher.draw(this.skullDown, this.pipe3.getX() - 1.0F, 45.0F + (this.pipe3.getY() + this.pipe3.getHeight()), 24.0F, 14.0F);
    }

    private void drawTransition(float paramFloat)
    {
        if (this.alpha.getValue() > 0.0F)
        {
            this.manager.update(paramFloat);
            Gdx.gl.glEnable(3042);
            Gdx.gl.glBlendFunc(770, 771);
            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            this.shapeRenderer.setColor(this.transitionColor.r, this.transitionColor.g, this.transitionColor.b, this.alpha.getValue());
            this.shapeRenderer.rect(0.0F, 0.0F, 136.0F, 300.0F);
            this.shapeRenderer.end();
            Gdx.gl.glDisable(3042);
        }
    }

    private void initAssets()
    {
        this.bg = AssetLoader.bg;
        this.grass = AssetLoader.grass;
        this.bossAnimation = AssetLoader.bossAnimation;
        this.bossMid = AssetLoader.boss;
        this.skullUp = AssetLoader.skullUp;
        this.skullDown = AssetLoader.skullDown;
        this.bar = AssetLoader.bar;
        this.ready = AssetLoader.ready;
        this.gameLogo = AssetLoader.gameLogo;
        this.gameOver = AssetLoader.gameOver;
        this.scoreboard = AssetLoader.scoreboard;
        this.retry = AssetLoader.retry;
        this.star = AssetLoader.star;
        this.noStar = AssetLoader.noStar;
    }

    private void initGameObjects()
    {
        this.boss = this.myWorld.getBoss();
        this.scroller = this.myWorld.getScroller();
        this.frontGrass = this.scroller.getFrontGrass();
        this.backGrass = this.scroller.getBackGrass();
        this.pipe1 = this.scroller.getPipe1();
        this.pipe2 = this.scroller.getPipe2();
        this.pipe3 = this.scroller.getPipe3();
    }

    public void reloadAssets() {
        AssetLoader.reloadCustomizables();
        this.bg = AssetLoader.bg;
        this.grass = AssetLoader.grass;
        this.bossAnimation = AssetLoader.bossAnimation;
        this.bossMid = AssetLoader.boss;
        this.skullUp = AssetLoader.skullUp;
        this.skullDown = AssetLoader.skullDown;
        this.bar = AssetLoader.bar;
        this.ready = AssetLoader.ready;
        this.gameLogo = AssetLoader.gameLogo;
        this.gameOver = AssetLoader.gameOver;
        this.scoreboard = AssetLoader.scoreboard;
        this.retry = AssetLoader.retry;
        this.star = AssetLoader.star;
        this.noStar = AssetLoader.noStar;
    }

    public void prepareTransition(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
    {
        this.transitionColor.set(paramInt1 / 255.0F, paramInt2 / 255.0F, paramInt3 / 255.0F, 1.0F);
        this.alpha.setValue(1.0F);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        this.manager = new TweenManager();
        Tween.to(this.alpha, -1, paramFloat).target(0.0F).ease(TweenEquations.easeOutQuad).start(this.manager);
    }

    public void render(float paramFloat1, float paramFloat2)
    {
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(0.9372549F, 0.9372549F, 0.6941176F, 1.0F);
        this.shapeRenderer.rect(0.0F, 0.0F, 136.0F, 66 + this.midPointY);
        this.shapeRenderer.setColor(0.4352941F, 0.7294118F, 0.1764706F, 1.0F);
        this.shapeRenderer.rect(0.0F, 66 + this.midPointY, 136.0F, 11.0F);
        this.shapeRenderer.setColor(0.8117647F, 0.5921569F, 0.3058824F, 1.0F);
        this.shapeRenderer.rect(0.0F, 77 + this.midPointY, 136.0F, 52.0F);
        this.shapeRenderer.end();
        this.batcher.begin();
        this.batcher.disableBlending();
        this.batcher.draw(this.bg, 0.0F, 23 + this.midPointY, 136.0F, 43.0F);
        this.batcher.enableBlending();
        drawGrass();

        if (this.myWorld.isReady())
        {
            drawBird(paramFloat2);
            drawReady();
        }
        else if (this.myWorld.isRunning())
        {
            drawBird(paramFloat2);
            drawPipes();
            drawSkulls();
            drawScore();
        }
        else if (this.myWorld.isMenu())
        {
            drawBirdCentered(paramFloat2);
            drawGameLogo();
        }
        else if (this.myWorld.isGameOver())
        {
            drawScoreboard();
            drawGameOver();
            drawRetry();
        }
        else if (this.myWorld.isHighScore())
        {
            drawScoreboard();
            drawRetry();
        }
        this.batcher.end();

        if (this.myWorld.isMenu()) {
            this.gameStage.draw();
        }
        drawTransition(paramFloat1);
    }
}
