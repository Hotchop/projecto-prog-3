package com.rogue.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rogue.game.objects.Player;
import com.rogue.game.objects.Weapon;

import javax.swing.*;

public class MainMenuScreen implements Screen {
    private final RogueliteGame game;
    private OrthographicCamera camera;
    private Texture backgroundTexture;
    private Music menuOst;
    private BitmapFont fontMenu;
    protected Player player;
    protected Weapon playerWeapon;
    private static final Color DEFAULT_COLOR = Color.WHITE; ///Atributos para dar color a la opcion seleccionada
    private static final Color HIGHLIGHT_COLOR = Color.YELLOW;
    private int selectedOption = 0;
    private String[] menuOptions = {"Nuevo juego", "Highscores", "Salir del juego"}; ///Lista opciones

    public MainMenuScreen(final RogueliteGame game) {
        this.game = game;
        backgroundTexture = new Texture("FondoMenu2.jpg");
        camera = new OrthographicCamera();
        fontMenu = new BitmapFont(Gdx.files.internal("FontTitulo.fnt"), Gdx.files.internal("FontTitulo.png"), false);
        game.font.getData().setScale(1);
        camera.setToOrtho(false,800,800);

        menuOst = Gdx.audio.newMusic(Gdx.files.internal("MainMenuOST.mp3"));
        menuOst.setVolume(0.1f);
        menuOst.setLooping(true);
        menuOst.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        GlyphLayout menuLayout = new GlyphLayout(fontMenu, "UTN Roguelite");
        fontMenu.draw(game.batch, menuLayout, (Gdx.graphics.getWidth() - menuLayout.width) / 2, Gdx.graphics.getHeight() - menuLayout.height - 50);

        float buttonY = (Gdx.graphics.getHeight() - menuLayout.height) / 2;
        for (int i = 0; i < menuOptions.length; i++) {
            Color fontColor = DEFAULT_COLOR;
            if (selectedOption == i) {
                fontColor = HIGHLIGHT_COLOR;
            }
            game.font.setColor(fontColor);
            GlyphLayout buttonLayout = new GlyphLayout(game.font, menuOptions[i]);
            float buttonX = (Gdx.graphics.getWidth() - buttonLayout.width) / 2;
            game.font.draw(game.batch, buttonLayout, buttonX, buttonY + buttonLayout.height);
            buttonY -= buttonLayout.height + 20;
        }

        game.batch.end();

        handleInput();
    }
///Interaccion con los botones
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedOption = Math.max(0, selectedOption -1 );
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedOption = Math.min(menuOptions.length - 1, selectedOption + 1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selectedOption) {
                case 0:
                    playerWeapon = new Weapon();
                    String name = JOptionPane.showInputDialog("Nombre de jugador");
                    if (name != null) {
                        menuOst.stop();
                        player = new Player(name, playerWeapon);
                        game.setScreen(new GameScreen(game, player));
                        dispose();
                        break;
                    }
                    //If cancel button is pressed
                    else{
                        game.setScreen(this);
                        break;
                    }
                case 1:
                    menuOst.stop();
                    game.setScreen(game.getHighscoreScreen());
                    break;
                case 2:
                    Gdx.app.exit(); // Salir del juego
                    break;
            }
        }
    }
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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

    }
}
