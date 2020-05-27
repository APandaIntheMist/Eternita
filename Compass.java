import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Compass extends BaseActor
{
    
    public Compass(float x, float y, Stage stage)
    {
        super (x,y,stage);
        
        this.setAnimator( new Animator("assets/compass.png"));

    }


}
