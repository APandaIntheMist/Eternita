import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

/**
 * Main Menu
 * menu screen music = virtues vocis?
 * "Virtutes Vocis" Kevin MacLeod (incompetech.com)
Licensed under Creative Commons: By Attribution 3.0 License
http://creativecommons.org/licenses/by/3.0/
 */
public class MenuScreen extends BaseScreen
{
    BaseActor bg;
    BaseActor title;
    public static Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/8bit Dungeon Boss.mp3"));
    Sound titleScreen;
    double wait;
    Sound select;
    
    public MenuScreen(boolean newGame, int index)
    {
        super(newGame,index);
    }

    public void initialize(boolean newGame, int index)
    {
        bg = new BaseActor(0,0,mainStage);
        bg.setAnimator( new Animator("assets/dungeonFloor.png") );
        bg.setSize(800,600);
        
        title = new BaseActor(0,0,mainStage);
        title.setAnimator( new Animator("assets/logo.png") );
        
        Label author = new Label("By Ryan Deisler",BaseGame.labelStyle);
        Label pressS = new Label("Press 'SPACE' to Begin",BaseGame.labelStyle);
        Label info = new Label("Press 'I' for Info",BaseGame.labelStyle);
        Label controls = new Label("Press 'SHIFT' for Controls", BaseGame.labelStyle);
        Label credits = new Label("Press 'C' for Credits", BaseGame.labelStyle);
        
        
        uiTable.add(title);
        uiTable.row();
        uiTable.add(author);
        uiTable.row();
        uiTable.add(pressS);
        uiTable.row();
        uiTable.add(info);
        uiTable.row();
        uiTable.add(controls);
        uiTable.row();
        uiTable.add(credits);
        
        titleScreen = Gdx.audio.newSound( Gdx.files.internal("Sounds/Title Screen.wav") );
        titleScreen.play();
        select = Gdx.audio.newSound( Gdx.files.internal("Sounds/select.wav") );
        
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.25f);
        wait = 0.0;
    }
    
    public void update(float deltaTime)
    {
        wait += deltaTime;
        
        if (wait > 1.7)
            bgMusic.play();
        
        if ( Gdx.input.isKeyJustPressed(Keys.SPACE) )
        {
            bgMusic.stop();
            select.play(0.5f);
            BaseGame.setActiveScreen( new StoneRoom(true,0) );
        }
        
        if ( Gdx.input.isKeyJustPressed(Keys.I) )
        {
            select.play(0.5f);
            BaseGame.setActiveScreen( new InfoScreen(false,0) );
        }
        
        if ( Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyJustPressed(Keys.SHIFT_RIGHT) )
        {
            select.play(0.5f);
            BaseGame.setActiveScreen( new ControlsScreen(false,0) );
        }
        
        if ( Gdx.input.isKeyJustPressed(Keys.C) )
        {
            select.play(0.5f);
            BaseGame.setActiveScreen( new Credits(false,0) );
        }
    }
}
