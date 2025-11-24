package com.david.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.david.frontend.GameManager;

public class MenuState implements GameState {
    private GameStateManager gsm;
    private Stage stage;
    private Skin skin;
    private TextField nameField;
    private TextButton startButton;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createBasicSkin();
        buildUI();
    }

    private void createBasicSkin() {
        skin = new Skin();

        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Pixmap pixmapWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapWhite.setColor(Color.WHITE);
        pixmapWhite.fill();
        skin.add("white", new Texture(pixmapWhite));
        pixmapWhite.dispose();

        Pixmap pixmapGray = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapGray.setColor(Color.GRAY);
        pixmapGray.fill();
        skin.add("gray", new Texture(pixmapGray));
        pixmapGray.dispose();

        Pixmap pixmapDarkGray = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapDarkGray.setColor(Color.DARK_GRAY);
        pixmapDarkGray.fill();
        skin.add("dark_gray", new Texture(pixmapDarkGray));
        pixmapDarkGray.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = skin.newDrawable("dark_gray");
        textFieldStyle.cursor = skin.newDrawable("white");
        textFieldStyle.selection = skin.newDrawable("gray");
        skin.add("default", textFieldStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.newDrawable("gray");
        textButtonStyle.down = skin.newDrawable("white");
        textButtonStyle.over = skin.newDrawable("dark_gray");
        skin.add("default", textButtonStyle);
    }

    private void buildUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("NETLAB JOYRIDE", skin);
        titleLabel.setFontScale(2f);

        Label promptLabel = new Label("Enter Your Name:", skin);

        nameField = new TextField("", skin);
        nameField.setMessageText("Username...");
        nameField.setAlignment(Align.center);

        startButton = new TextButton("START GAME", skin);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String inputName = nameField.getText();
                if (inputName.isEmpty()) {
                    inputName = "Guest";
                }
                GameManager.getInstance().registerPlayer(inputName);
                gsm.set(new PlayingState(gsm));
            }
        });

        table.add(titleLabel).padBottom(40);
        table.row();
        table.add(promptLabel).padBottom(20);
        table.row();
        table.add(nameField).width(300).height(40).padBottom(20);
        table.row();
        table.add(startButton).width(200).height(50);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
