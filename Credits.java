import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.audio.Sound;

/**
 * Control scheme screen
 */
public class Credits extends BaseScreen
{
    BaseActor bg;
    BaseActor title;
    Sound select;

    public Credits(boolean newGame, int index)
    {
        super(newGame,index);
    }

    public void initialize(boolean newGame, int index)
    {
        bg = new BaseActor(0,0,mainStage);
        bg.setAnimator( new Animator("assets/dungeonFloor.png") );
        bg.setSize(800,600);

        select = Gdx.audio.newSound( Gdx.files.internal("Sounds/select.wav") );

        title = new BaseActor(0,0,mainStage);
        title.setAnimator( new Animator("assets/logo.png") );
        title.setScale(0.5f);

        Label author = new Label("Made by Ryan Deisler",BaseGame.labelStyle);
        //author.setFontScale(0.75f);
        Label music1 = new Label("Music",BaseGame.labelStyle);
        //music1.setFontScale(0.75f);
        Label music2 = new Label("'8bit Dungeon Boss' Kevin MacLeod (incompetech.com)",BaseGame.labelStyle);
        music2.setFontScale(0.75f);
        Label music3 = new Label("Licensed under Creative Commons:",BaseGame.labelStyle);
        music3.setFontScale(0.75f);
        Label music4 = new Label("By Attribution 3.0 License",BaseGame.labelStyle);
        music4.setFontScale(0.75f);
        Label music5 = new Label("http://creativecommons.org/licenses/by/3.0/",BaseGame.labelStyle);
        music5.setFontScale(0.75f);
        Label ideas1 = new Label("With Ideas From",BaseGame.labelStyle);
        Label ideas2 = new Label("Paul Maurantonio, Daniel Hickey, and Henry Posada",BaseGame.labelStyle);
        ideas2.setFontScale(0.75f);
        Label voice1 = new Label("With Voice From",BaseGame.labelStyle);
        Label voice2 = new Label("Paul Maurantonio", BaseGame.labelStyle);
        voice2.setFontScale(0.75f);
        Label memory = new Label("In Memory of Robert Deisler", BaseGame.labelStyle);
        //memory.setFontScale(0.5f);

        uiTable.add(title);
        uiTable.row();
        uiTable.add(author);
        uiTable.row();
        uiTable.add().pad(5);
        uiTable.row();
        uiTable.add(music1);
        uiTable.row();
        uiTable.add().pad(5);
        uiTable.row();
        uiTable.add(music2);
        uiTable.row();
        uiTable.add(music3);
        uiTable.row();
        uiTable.add(music4);
        uiTable.row();
        uiTable.add(music5);
        uiTable.row();
        uiTable.add().pad(5);
        uiTable.row();
        uiTable.add(ideas1);
        uiTable.row();
        uiTable.add().pad(5);
        uiTable.row();
        uiTable.add(ideas2);
        uiTable.row();
        uiTable.add().pad(5);
        uiTable.row();
        uiTable.add(voice1);
        uiTable.row();
        uiTable.add().pad(5);
        uiTable.row();
        uiTable.add(voice2);
        uiTable.row();
        uiTable.add().pad(5);
        uiTable.row();
        uiTable.add(memory);

    }

    public void update(float deltaTime)
    {
        if ( Gdx.input.isKeyJustPressed(Keys.S))
        {
            MenuScreen.bgMusic.stop();
            select.play(0.5f);
            BaseGame.setActiveScreen( new StoneRoom(true,0) );
        }

        if ( Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT))
        {
            MenuScreen.bgMusic.stop();
            select.play(0.5f);
            BaseGame.setActiveScreen( new MenuScreen(false,0) );
        }
    }
}
