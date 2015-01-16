package slashdev.com.flaptheplane.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.Map;

public class AssetLoader
{
    public static TextureRegion bar;
    public static TextureRegion bg;
    public static TextureRegion boss;
    public static Animation bossAnimation;
    public static TextureRegion bossDown;
    public static TextureRegion bossUp;

    public static Map<String, Sound> customSoundMap = new HashMap<String, Sound>();
    public static Sound customFlapSound;
    public static Sound customScoredSound;
    public static Sound customFallSound;

    public static Sound defaultFlapSound;
    public static Sound defaultScoredSound;
    public static Sound defaultFallSound;

    public static Sound flapSound;
    public static Sound scoredSound;
    public static Sound fallSound;

    public static Sound deadSound;
    public static BitmapFont font;
    public static TextureRegion gameOver;
    public static TextureRegion grass;
    public static TextureRegion logo;
    public static Texture logoTexture;
    public static TextureRegion noStar;
    public static TextureRegion startButtonDown;
    public static TextureRegion startButtonUp;
    private static Preferences prefs;
    public static Texture bossTexture;
    public static TextureRegion ready;
    public static TextureRegion retry;
    public static TextureRegion settingsButtonDown;
    public static TextureRegion settingsButtonUp;
    public static TextureRegion scoreboard;
    public static BitmapFont shadow;
    public static TextureRegion skullDown;
    public static TextureRegion skullUp;
    public static TextureRegion star;
    public static Texture texture;
    public static Texture bgTexture;
    public static BitmapFont blackFont;
    public static Texture gameLogoTexture;
    public static TextureRegion gameLogo;
    public static Skin skin;

    public static void dispose()
    {
        logoTexture.dispose();
        texture.dispose();
        gameLogoTexture.dispose();
        bossTexture.dispose();
        deadSound.dispose();
        customFlapSound.dispose();
        customScoredSound.dispose();
        customFallSound.dispose();
        defaultFlapSound.dispose();
        defaultScoredSound.dispose();
        defaultFallSound.dispose();
        font.dispose();
        shadow.dispose();
        skin.dispose();
    }

    public static int getHighScore()
    {
        return prefs.getInteger(GamePreferences.KEY_HIGH_SCORE);
    }

    public static CharSequence getHighScoreCharSequence()
    {
        return Integer.toString(prefs.getInteger(GamePreferences.KEY_HIGH_SCORE));
    }

    public static void load()
    {
        prefs = Gdx.app.getPreferences(GamePreferences.KEY_APPLICATION_ROOT);
        if (!prefs.contains(GamePreferences.KEY_HIGH_SCORE)) {
            prefs.putInteger(GamePreferences.KEY_HIGH_SCORE, 0);
        }
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        skin.getFont("default-font").scale(1.5F);

        logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logo = new TextureRegion(logoTexture, 0, 0, 512, 128);
        bossTexture = new Texture(Gdx.files.internal("data/boss.png"));
        bossTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        texture = new Texture(Gdx.files.internal("data/gameskin.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        bgTexture = new Texture(Gdx.files.internal("data/bg.png"));
        bgTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        gameLogoTexture = new Texture(Gdx.files.internal("data/gamelogo.png"));
        gameLogoTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        settingsButtonUp = new TextureRegion(texture, 137, 62, 40, 20);
        settingsButtonDown = new TextureRegion(texture, 177, 62, 40, 20);
        settingsButtonUp.flip(false, true);
        settingsButtonDown.flip(false, true);
        startButtonUp = new TextureRegion(texture, 0, 0, 40, 14);
        startButtonDown = new TextureRegion(texture, 97, 86, 40, 14);
        startButtonUp.flip(false, true);
        startButtonDown.flip(false, true);
        ready = new TextureRegion(texture, 40, 0, 87, 22);
        ready.flip(false, true);
        retry = new TextureRegion(texture, 0, 14, 40, 14);
        retry.flip(false, true);
        gameOver = new TextureRegion(texture, 127, 0, 94, 19);
        gameOver.flip(false, true);
        scoreboard = new TextureRegion(texture, 0, 63, 97, 37);
        scoreboard.flip(false, true);
        star = new TextureRegion(texture, 0, 49, 10, 10);
        noStar = new TextureRegion(texture, 13, 49, 10, 10);
        star.flip(false, true);
        noStar.flip(false, true);
        gameLogo = new TextureRegion(gameLogoTexture);
        gameLogo.flip(false, true);
        bg = new TextureRegion(bgTexture, 0, 0, 256, 128);
        bg.flip(false, true);
        grass = new TextureRegion(texture, 0, 100, 255, 28);
        grass.flip(false, true);
        bossDown = new TextureRegion(bossTexture, 4, 6, 74, 51);
        bossDown.flip(false, true);
        boss = new TextureRegion(bossTexture, 87, 6, 74, 51);
        boss.flip(false, true);
        bossUp = new TextureRegion(bossTexture, 174, 6, 74, 51);
        bossUp.flip(false, true);
        TextureRegion[] arrayOfTextureRegion = new TextureRegion[3];
        arrayOfTextureRegion[0] = bossDown;
        arrayOfTextureRegion[1] = boss;
        arrayOfTextureRegion[2] = bossUp;
        bossAnimation = new Animation(0.06F, arrayOfTextureRegion);
        bossAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        skullUp = new TextureRegion(texture, 0, 28, 24, 14);
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);
        bar = new TextureRegion(texture, 0, 43, 22, 3);
        bar.flip(false, true);

        defaultFlapSound = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
        defaultScoredSound = Gdx.audio.newSound(Gdx.files.internal("data/scored.wav"));
        defaultFallSound = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));

        reloadCustomizables();
        deadSound = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        font = new BitmapFont(Gdx.files.internal("data/blacktext.fnt"));
        font.setScale(0.25F, -0.25F);
        blackFont = new BitmapFont(Gdx.files.internal("data/blacktext.fnt"));
        blackFont.setScale(0.1F, -0.1F);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(0.25F, -0.25F);
    }

    public static void reloadCustomizables() {
        if (customFlapSound != null) {
            customFlapSound.dispose();
            customFlapSound = null;
        }
        if (customScoredSound != null) {
            customScoredSound.dispose();
            customScoredSound = null;
        }
        if (customFallSound != null) {
            customFallSound.dispose();
            customFallSound = null;
        }
        try {
            customFlapSound = Gdx.audio.newSound(Gdx.files.local(GamePreferences.CUSTOM_FLAP_SOUND_FILENAME));
            customSoundMap.put(GamePreferences.CUSTOM_FLAP_SOUND_FILENAME, customFlapSound);
        } catch (Exception e) {
            customFlapSound = null;
            e.printStackTrace();
        }
        try {
            customScoredSound = Gdx.audio.newSound(Gdx.files.local(GamePreferences.CUSTOM_SCORED_SOUND_FILENAME));
            customSoundMap.put(GamePreferences.CUSTOM_SCORED_SOUND_FILENAME, customScoredSound);
        } catch (Exception e) {
            customScoredSound = null;
            e.printStackTrace();
        }
        try {
            customFallSound = Gdx.audio.newSound(Gdx.files.local(GamePreferences.CUSTOM_FALL_SOUND_FILENAME));
            customSoundMap.put(GamePreferences.CUSTOM_FALL_SOUND_FILENAME, customFallSound);
        } catch (Exception e) {
            customFallSound = null;
            e.printStackTrace();
        }

        if (GamePreferences.useCustomFlapSound() && customFlapSound != null) {
            flapSound = customFlapSound;
        } else {
            flapSound = defaultFlapSound;
        }
        if (GamePreferences.useCustomScoredSound() && customScoredSound != null) {
            scoredSound = customScoredSound;
        } else {
            scoredSound = defaultScoredSound;
        }
        if (GamePreferences.useCustomFallSound() && customFallSound != null) {
            fallSound = customFallSound;
        } else {
            fallSound = defaultFallSound;
        }
    }

    public static void setHighScore(int paramInt)
    {
        prefs.putInteger(GamePreferences.KEY_HIGH_SCORE, paramInt);
        prefs.flush();
    }

    public static void playFlapSound() {
        if (GamePreferences.useCustomFlapSound()) {
            flapSound.play(1.0F);
        } else {
            flapSound.play(0.7F);
        }
    }

    public static void playScoredSound() {
        if (GamePreferences.useCustomScoredSound()) {
            scoredSound.play(1.0F);
        } else {
            scoredSound.play(0.7F);
        }
    }

    public static void playFallSound() {
        if (GamePreferences.useCustomFallSound()) {
            fallSound.play(1.0F);
        } else {
            fallSound.play(0.7F);
        }
    }
}

