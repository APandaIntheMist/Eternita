import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

/**
 * Information screen
 */
public class InfoScreen extends BaseScreen
{
    BaseActor bg;
    BaseActor barbEX;
    BaseActor skelEX;
    BaseActor sageEX;
    BaseActor hamEX;
    BaseActor spawnerEX;
    
    Sound select;

    public InfoScreen(boolean newGame, int index)
    {
        super(newGame,index);
    }

    public void initialize(boolean newGame, int index)
    {
        bg = new BaseActor(0,0,mainStage);
        bg.setAnimator( new Animator("assets/dungeonFloor.png") );
        bg.setSize(800,600);
        
        select = Gdx.audio.newSound( Gdx.files.internal("Sounds/select.wav") );
        
        barbEX = new BaseActor(0,0,mainStage);
        barbEX.setAnimator( new Animator("assets/Barbarian.png") );
        skelEX = new BaseActor(0,0,mainStage);
        skelEX.setAnimator( new Animator("assets/Skeleton.png") );
        sageEX = new BaseActor(0,0,mainStage);
        sageEX.setAnimator( new Animator("assets/Sage.png") );
        hamEX = new BaseActor(0,0,mainStage);
        hamEX.setScale(0.20f);
        hamEX.setAnimator( new Animator("assets/ham.png") );
        hamEX.setPosition(-100,150);
        spawnerEX = new BaseActor(0,0,mainStage);
        spawnerEX.setAnimator( new Animator("assets/Spawner.png") );
        spawnerEX.setScale(0.25f);
        spawnerEX.setPosition(-80,40);

        Label goal = new Label ("You are Gordox, the Barbarian.\nYou have come to slay all the wretched abominations that lurk in this dungeon.\nGoal: Clear all 9 rooms of enemies!",BaseGame.labelStyle);
        goal.setFontScale(0.65f);
        Label barbLabel = new Label("This is you.\nYou have 4 HP and therefore can take 4 hits from enemies before perishing.",BaseGame.labelStyle);
        barbLabel.setFontScale(0.5f);
        Label skeleLabel = new Label("A common Skeleton, they are the most numerous foe.\nIf they get too close they will hit you with a melee attack!",BaseGame.labelStyle);
        skeleLabel.setFontScale(0.5f);
        Label sageLabel = new Label("Sages, Skeletons that have mastery over the arcane arts.\nThey will constantly shoot fireballs at you!",BaseGame.labelStyle);
        sageLabel.setFontScale(0.5f);
        Label hamLabel = new Label("Delicious ham.\nHas a chance to spawn after killing a Skeleton.\nCollecting it heals you for 1 HP!", BaseGame.labelStyle);
        hamLabel.setFontScale(0.5f);
        Label spawnerLabel = new Label("A devious construct that spawns 5 Skeletons every 8 seconds.\nDestroy these quickly!", BaseGame.labelStyle);
        spawnerLabel.setFontScale(0.5f);

        //uiTable.debug();
        uiTable.add().expandX();
        uiTable.add(goal);
        uiTable.add().expandX();
        uiTable.row();
        uiTable.add().expandX();
        uiTable.row();
        uiTable.add(barbEX);
        uiTable.add(barbLabel);
        uiTable.row();
        uiTable.add(skelEX);
        uiTable.add(skeleLabel);
        uiTable.row();
        uiTable.add(sageEX);
        uiTable.add(sageLabel);
        uiTable.row();
        uiTable.add().expandX();
        uiTable.add(hamLabel);
        uiTable.row();
        uiTable.add().expandX();
        uiTable.add(spawnerLabel);

    }

    public void update(float deltaTime)
    {

        if ( Gdx.input.isKeyJustPressed(Keys.S))
        {
            MenuScreen.bgMusic.stop();
            select.play(0.5f);
            BaseGame.setActiveScreen( new StoneRoom(true,0) );
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT))
        {
            MenuScreen.bgMusic.stop();
            select.play(0.5f);
            BaseGame.setActiveScreen( new MenuScreen(false,0) );  
        }
    }
}
