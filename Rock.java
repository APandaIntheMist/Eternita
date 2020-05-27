import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Rock extends BaseActor
{
    public Rock(float x, float y, Stage stage)
    {
        super(x,y, stage);
        
        setAnimator( new Animator("assets/rock.png") );
        
        setBoundaryPolygon(8);
        
    }
    
    
}