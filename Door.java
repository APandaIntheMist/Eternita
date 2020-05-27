import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Class that represents the portals between rooms
 */
public class Door extends BaseActor
{


    public Door(float x, float y, Stage stage)
    {
        super (x,y,stage);
        
        setAnimator( new Animator("assets/warp.png",4,8,0.1f,true) );
        setScale(1.2f);
        
        setBoundaryPolygon(4);
    }

    public void act(float deltaTime)
    {
        super.act(deltaTime);
        
        boundToWorld(1600,1200);
    }
}
