import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.audio.Sound;

/**
 * Game Over screen
 */
public class GameOver extends BaseScreen
{
    BaseActor bg;
    BaseActor go;
    Sound gameover;
    
    public GameOver(boolean newGame, int index)
    {
        super(newGame,index);
    }

    public void initialize(boolean newGame, int index)
    {
        bg = new BaseActor(0,0,mainStage);
        bg.setAnimator( new Animator("assets/dungeonFloor.png") );
        bg.setSize(800,600);
        
        go = new BaseActor(0,0,mainStage);
        go.setAnimator( new Animator("assets/gameOver.png") );
        
        //Label author = new Label("By Ryan Deisler",BaseGame.labelStyle);
        Label pressS = new Label("Press 'CONTROL' to Return to Main Menu",BaseGame.labelStyle);
        //Label info = new Label("Press 'BUTTON' for Info",BaseGame.labelStyle);
        
        Label scoreText = new Label("You scored " + StoneRoom.score + " points!", BaseGame.labelStyle);
        
        //uiTable.debug();
        //uiTable.padCell(64).left();
        uiTable.add(go);//.pad(64).left();
        uiTable.row();
        uiTable.add(pressS);
        uiTable.row();
        uiTable.add(scoreText);
        
        gameover = Gdx.audio.newSound( Gdx.files.internal("Sounds/Game Over.wav") );
        gameover.play();
    }
    
    public void update(float deltaTime)
    {
        if ( Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
            BaseGame.setActiveScreen( new MenuScreen(false,0) );
    }
}
