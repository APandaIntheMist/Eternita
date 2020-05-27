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
public class ControlsScreen extends BaseScreen
{
    BaseActor bg;
    BaseActor scheme;
    Sound select;
    
    public ControlsScreen(boolean newGame, int index)
    {
        super(newGame,index);
    }

    public void initialize(boolean newGame, int index)
    {
        bg = new BaseActor(0,0,mainStage);
        bg.setAnimator( new Animator("assets/dungeonFloor.png") );
        bg.setSize(800,600);
        
        select = Gdx.audio.newSound( Gdx.files.internal("Sounds/select.wav") );
        
        Label controls = new Label ("Use the arrow keys to move Gordox.\n\n'SPACE' = Attack\n\n'X' = Fury mode\n\n'CTRL' = Return to menu", BaseGame.labelStyle);
        uiTable.add(controls);
        //scheme = new BaseActor(45,45,mainStage);
        //scheme.setAnimator( new Animator("assets/eternitaControls.jpg") );
        //scheme.setSize(700,500);
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
