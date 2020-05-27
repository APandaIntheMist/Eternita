import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Barrier extends BaseActor
{
    public Barrier(float x, float y, Stage stage)
    {
        super(x,y, stage);
        
        setAnimator( new Animator("assets/RockBarrier.png",4,3,0.09f,true) );
        
        setBoundaryPolygon(8);
        
    }
    
    
}