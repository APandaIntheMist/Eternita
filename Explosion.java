import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class Explosion extends BaseActor
{
    Sound boom;
    
    public Explosion(float x, float y, Stage stage)
    {
        super(x,y,stage);

        setAnimator( new Animator("assets/explosion.png", 6,6, 0.02f, false) );
        
        boom = Gdx.audio.newSound( Gdx.files.internal("Sounds/boom.ogg"));
        boom.play();
    }
    
    public void act(float deltaTime)
    {
        super.act(deltaTime);
        
        if ( this.animator.isAnimationFinished() )
            remove();
    }

}