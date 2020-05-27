import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Fireball from Sage's Staff
 */
public class Sage_Fireball extends BaseActor
{
    
    public Sage_Fireball(float x, float y, Stage stage)
    {
        super(x,y, stage);
        
        setAnimator( new Animator("assets/Better Green Fireball.png",1,5,0.1f,true) );
        
        setScale(2.0f);
        
        physics = new Physics(0,250,0);
        
        physics.setSpeed(250);
        
        setBoundaryPolygon(8);
        
    }
    
    public void act(float deltaTime)
    {
        super.act(deltaTime);
        
        if ( isOffScreen(1600,1200) )
            this.remove();
    }
}
