package slashdev.com.flaptheplane.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.uraroji.garage.android.mp3recvoice.RecMicToMp3;

import slashdev.com.flaptheplane.MainGame;
import slashdev.com.flaptheplane.gamehelpers.AssetLoader;
import slashdev.com.flaptheplane.gamehelpers.GamePreferences;
import slashdev.com.flaptheplane.gameobjects.Boss;

/**
 * Created by Slimer on 2015/1/11.
 */
public class SettingsScreen implements Screen {

    private MainGame game;
    private float runTime;
    private float screenWidth;
    private float screenHeight;
    private int midPointY;

    private OrthographicCamera cam;
    private SpriteBatch batcher;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Boss boss;

    private TextureRegion grass;
    private TextureRegion bossMid;
    private Animation bossAnimation;
    private TextureRegion gameLogo;

    private ButtonGroup flapSoundButtonGroup;
    private TextButton customFlapSoundButton;
    private TextButton defaultFlapSoundButton;

    private Skin skin;

    private ButtonGroup scoredSoundButtonGroup;
    private TextButton customScoredSoundButton;
    private TextButton customFallSoundButton;

    private ButtonGroup fallSoundButtonGroup;
    private TextButton defaultScoredSoundButton;
    private TextButton defaultFallSoundButton;


    public SettingsScreen(MainGame g) {
        this.game = g;
        this.game.settingsScreen = this;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        float ratio = screenHeight / (screenWidth / 136.0F);
        midPointY = (int)(ratio / 2.0F);

        initAssets();

        this.cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.cam.setToOrtho(true, 136.0F, ratio);
        this.batcher = new SpriteBatch();
        this.batcher.setProjectionMatrix(this.cam.combined);
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setProjectionMatrix(this.cam.combined);
        stage = new Stage() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    SettingsScreen.this.game.setScreen(SettingsScreen.this.game.gameScreen);
                    return true;
                }
                return false;
            }
        };


        customFlapSoundButton = createCustomizeButton(500.0F, 500.0F, 200.0F, 80.0F,
                GamePreferences.CUSTOM_FLAP_SOUND_FILENAME,
                GamePreferences.KEY_USE_CUSTOM_FLAP_SOUND,
                1.0F);

        customScoredSoundButton = createCustomizeButton(500.0F, 400.0F, 200.0F, 80.0F,
                GamePreferences.CUSTOM_SCORED_SOUND_FILENAME,
                GamePreferences.KEY_USE_CUSTOM_SCORED_SOUND,
                2.0F);

        customFallSoundButton = createCustomizeButton(500.0F, 300.0F, 200.0F, 80.0F,
                GamePreferences.CUSTOM_FALL_SOUND_FILENAME,
                GamePreferences.KEY_USE_CUSTOM_FALL_SOUND,
                2.0F);

        defaultFlapSoundButton = createDefaultButton(250.0F, 500.0F, 200.0F, 80.0F,
                GamePreferences.CUSTOM_FLAP_SOUND_FILENAME,
                GamePreferences.KEY_USE_CUSTOM_FLAP_SOUND,
                AssetLoader.defaultFlapSound);


        defaultScoredSoundButton = createDefaultButton(250.0F, 400.0F, 200.0F, 80.0F,
                GamePreferences.CUSTOM_SCORED_SOUND_FILENAME,
                GamePreferences.KEY_USE_CUSTOM_SCORED_SOUND,
                AssetLoader.defaultScoredSound);

        defaultFallSoundButton = createDefaultButton(250.0F, 300.0F, 200.0F, 80.0F,
                GamePreferences.CUSTOM_FALL_SOUND_FILENAME,
                GamePreferences.KEY_USE_CUSTOM_FALL_SOUND,
                AssetLoader.defaultFallSound);

        flapSoundButtonGroup = new ButtonGroup(defaultFlapSoundButton, customFlapSoundButton);
        flapSoundButtonGroup.setMaxCheckCount(1);
        flapSoundButtonGroup.setMinCheckCount(1);

        scoredSoundButtonGroup = new ButtonGroup(defaultScoredSoundButton, customScoredSoundButton);
        scoredSoundButtonGroup.setMaxCheckCount(1);
        scoredSoundButtonGroup.setMinCheckCount(1);

        fallSoundButtonGroup = new ButtonGroup(defaultFallSoundButton, customFallSoundButton);
        fallSoundButtonGroup.setMaxCheckCount(1);
        fallSoundButtonGroup.setMinCheckCount(1);

        stage.clear();
        stage.addActor(createLabel("Flap", Color.BLACK, 80.0F, 500.0F, 150.0F, 80.0F));
        stage.addActor(customFlapSoundButton);
        stage.addActor(defaultFlapSoundButton);

        stage.addActor(createLabel("Scored", Color.BLACK, 80.0F, 400.0F, 150.0F, 80.0F));
        stage.addActor(customScoredSoundButton);
        stage.addActor(defaultScoredSoundButton);

        stage.addActor(createLabel("Fall", Color.BLACK, 80.0F, 300.0F, 150.0F, 80.0F));
        stage.addActor(customFallSoundButton);
        stage.addActor(defaultFallSoundButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        if (GamePreferences.useCustomFlapSound()) {
            customFlapSoundButton.setChecked(true);
        } else {
            defaultFlapSoundButton.setChecked(true);
        }
        if (GamePreferences.useCustomScoredSound()) {
            customScoredSoundButton.setChecked(true);
        } else {
            defaultScoredSoundButton.setChecked(true);
        }
        if (GamePreferences.useCustomFallSound()) {
            customFallSoundButton.setChecked(true);
        } else {
            defaultFallSoundButton.setChecked(true);
        }
    }

    @Override
    public void render(float delta) {
        this.runTime = (delta + this.runTime);
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setProjectionMatrix(this.cam.combined);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(0.9372549F, 0.9372549F, 0.6941176F, 1.0F);
        this.shapeRenderer.rect(0.0F, 0.0F, this.screenWidth, this.screenHeight);
        this.shapeRenderer.end();
        batcher.begin();

        drawGameLogo();
        drawBirdCentered(this.runTime);
        batcher.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        batcher.dispose();
    }

    private void initAssets() {
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        skin.getFont("default-font").scale(1.2F);
        this.grass = AssetLoader.grass;
        this.bossAnimation = AssetLoader.bossAnimation;
        this.boss = new Boss(33.0F, this.midPointY - 5, 17, 12);
        this.bossMid = AssetLoader.boss;
        this.gameLogo = AssetLoader.gameLogo;
    }

    private void drawGameLogo()
    {
        this.batcher.draw(this.gameLogo, 12.0F, -80 + this.midPointY, this.gameLogo.getRegionWidth() / 1.2F, this.gameLogo.getRegionHeight() / 1.2F);
    }

    private void drawBirdCentered(float paramFloat)
    {
        this.batcher.draw(this.bossAnimation.getKeyFrame(paramFloat), 59.0F, this.boss.getY() - 15.0F, this.boss.getWidth() / 2.0F, this.boss.getHeight() / 2.0F, this.boss.getWidth(), this.boss.getHeight(), 1.0F, 1.0F, this.boss.getRotation());
    }

    private Label createLabel(String text, Color color, float posX, float posY, float width, float height) {
        Label label = new Label(text, skin);
        label.setBounds(posX, posY, width, height);
        label.getStyle().fontColor = color;
        label.addListener(new InputListener() {
        });
        return label;
    }

    private TextButton createCustomizeButton(float posX, float posY, float width, float height,
                                             final String filename, final String preferenceKey,
                                             final float seconds) {

        final TextButton button = new TextButton("Customize", skin);
        button.getStyle().checked = button.getStyle().down;
        button.setBounds(posX, posY, width, height);
        button.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttonId) {

                final Skin localSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
                localSkin.getFont("default-font").scale(2.0F);
                final Dialog recordingDialog = new Dialog("", localSkin) {
                    {
                        text("Recording...");
                        button("Confirm");
                    }
                };

                // Play sound.
                if (Gdx.files.local(filename).exists() && AssetLoader.customSoundMap.get(filename) != null) {
                    AssetLoader.customSoundMap.get(filename).play(1.0F);
                }

                // Update preferences.
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Preferences prefs = Gdx.app.getPreferences(GamePreferences.KEY_APPLICATION_ROOT);

                        prefs.putBoolean(preferenceKey, true);
                        prefs.flush();
                        AssetLoader.reloadCustomizables();
                    }
                }).start();

                new Dialog("", localSkin) {
                    {
                        text("Customize Sound").setSkin(localSkin);
                        button("Record", "Record").setSkin(localSkin);
                        button("Cancel", "Cancel").setSkin(localSkin);
                    }

                    @Override
                    public Dialog show(Stage stage) {
                        return super.show(stage);
                    }

                    @Override
                    protected void result(Object object) {
                        if (object.toString().equals("Record")) {
                            final int sampleRate = 44100;
                            final RecMicToMp3 recMicToMp3 = new RecMicToMp3(GamePreferences.getCustomSoundPath(filename),
                                    sampleRate);

                            recMicToMp3.setResultCallback(new RecMicToMp3.ResultCallback() {

                                @Override
                                public void onResult(int what) {
                                    switch (what) {
                                        case RecMicToMp3.MSG_REC_STARTED:
                                            recordingDialog.show(stage);
                                            break;
                                        case RecMicToMp3.MSG_REC_STOPPED:
                                            AssetLoader.reloadCustomizables();
                                            recordingDialog.hide();
                                            new Dialog("", localSkin) {
                                                {
                                                    text("Record succeed!").setSkin(localSkin);
                                                    button("Confirm").setSkin(localSkin);
                                                }
                                            }.show(stage);
                                            break;
                                        case RecMicToMp3.MSG_ERROR_GET_MIN_BUFFERSIZE:
                                        case RecMicToMp3.MSG_ERROR_CREATE_FILE:
                                        case RecMicToMp3.MSG_ERROR_REC_START:
                                        case RecMicToMp3.MSG_ERROR_AUDIO_RECORD:
                                        case RecMicToMp3.MSG_ERROR_AUDIO_ENCODE:
                                        case RecMicToMp3.MSG_ERROR_WRITE_FILE:
                                        case RecMicToMp3.MSG_ERROR_CLOSE_FILE:
                                            recordingDialog.hide();
                                            new Dialog("", localSkin) {
                                                {
                                                    text("Record failed!").setSkin(localSkin);
                                                    button("Confirm").setSkin(localSkin);
                                                }
                                            }.show(stage);
                                            break;
                                        default:
                                            recordingDialog.hide();
                                            break;
                                    }
                                }
                            });
                            recMicToMp3.start();

                            new Timer().scheduleTask(new Timer.Task() {

                                @Override
                                public void run() {
                                    recMicToMp3.stop();
                                }
                            }, seconds);
                        }
                        super.result(object);
                    }
                }.show(stage);

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int buttonId) {
                button.setChecked(true);
            }
        });

        return button;
    }

    private TextButton createDefaultButton(float posX, float posY, float width, float height, final String filename, final String preferenceKey, final Sound sound) {

        final TextButton button = new TextButton("Default", skin);

        button.getStyle().checked = button.getStyle().down;
        button.setBounds(posX, posY, width, height);
        button.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttonId) {

                sound.play(0.7F);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Update preferences to not using customized sound.
                        Preferences prefs = Gdx.app.getPreferences(GamePreferences.KEY_APPLICATION_ROOT);

                        prefs.putBoolean(preferenceKey, false);
                        prefs.flush();
                        AssetLoader.reloadCustomizables();
                    }
                }).start();

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int buttonId) {
                button.setChecked(true);
            }
        });

        return button;
    }
}
