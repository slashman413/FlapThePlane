package slashdev.com.flaptheplane.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Slimer on 2015/1/10.
 */
public class GamePreferences {

    public static final String KEY_APPLICATION_ROOT = "flappy_boss";
    public static final String KEY_HIGH_SCORE = "high_score";
    public static final String KEY_USE_CUSTOM_FLAP_SOUND = "use_custom_flap_sound";
    public static final String KEY_USE_CUSTOM_SCORED_SOUND = "use_custom_scored_sound";
    public static final String KEY_USE_CUSTOM_FALL_SOUND = "use_custom_fall_sound";

    public static final String CUSTOM_FLAP_SOUND_FILENAME = "custom_flap_sound.mp3";
    public static final String CUSTOM_SCORED_SOUND_FILENAME = "custom_scored_sound.mp3";
    public static final String CUSTOM_FALL_SOUND_FILENAME = "custom_fall_sound.mp3";

    public static final String KEY_TUTORIAL_SHOWN = "tutorial_shown";

    public static boolean isTutorialShown() {
        Preferences prefs = Gdx.app.getPreferences(GamePreferences.KEY_APPLICATION_ROOT);
        if (prefs.contains(GamePreferences.KEY_TUTORIAL_SHOWN)
                && true == prefs.getBoolean(GamePreferences.KEY_TUTORIAL_SHOWN)) {
            return true;
        }
        return false;
    }

    public static void updateTutorialShown(final boolean shown) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Preferences prefs = Gdx.app.getPreferences(GamePreferences.KEY_APPLICATION_ROOT);

                prefs.putBoolean(KEY_TUTORIAL_SHOWN, shown);
                prefs.flush();
            }
        }).start();
    }

    public static boolean useCustomFlapSound() {
        Preferences prefs = Gdx.app.getPreferences(GamePreferences.KEY_APPLICATION_ROOT);
        if (prefs.contains(GamePreferences.KEY_USE_CUSTOM_FLAP_SOUND)
                && true == prefs.getBoolean(GamePreferences.KEY_USE_CUSTOM_FLAP_SOUND)) {
            return true;
        }
        return false;
    }

    public static boolean useCustomScoredSound() {
        Preferences prefs = Gdx.app.getPreferences(GamePreferences.KEY_APPLICATION_ROOT);
        if (prefs.contains(GamePreferences.KEY_USE_CUSTOM_SCORED_SOUND)
                && true == prefs.getBoolean(GamePreferences.KEY_USE_CUSTOM_SCORED_SOUND)) {
            return true;
        }
        return false;
    }

    public static boolean useCustomFallSound() {
        Preferences prefs = Gdx.app.getPreferences(GamePreferences.KEY_APPLICATION_ROOT);
        if (prefs.contains(GamePreferences.KEY_USE_CUSTOM_FALL_SOUND)
                && true == prefs.getBoolean(GamePreferences.KEY_USE_CUSTOM_FALL_SOUND)) {
            return true;
        }
        return false;
    }

    // Usage:
    // getCustomSoundPath(GamePreferences.CUSTOM_FLAP_SOUND_FILENAME)
    // getCustomSoundPath(GamePreferences.CUSTOM_SCORED_SOUND_FILENAME)
    // getCustomSoundPath(GamePreferences.CUSTOM_FALL_SOUND_FILENAME)
    public static String getCustomSoundPath(String filename) {
        FileHandle outputFile = Gdx.files.local(filename);

        return outputFile.file().getPath();
    }
}
