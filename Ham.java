import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
public class Ham extends BaseActor
{
    public static int hamsCollected = 0;

    /**
     * Constructor for objects of class Ham
     */
    public Ham(float x, float y, Stage stage)
    {
        super(x,y,stage);

        setAnimator( new Animator("assets/ham.png") );

        setScale(0.20f);
        setBoundaryRectangle();

        addAction( 
            Actions.forever(
                Actions.sequence(
                    Actions.scaleTo(0.25f,0.25f, 0.5f),
                    Actions.scaleTo(0.20f,0.20f, 0.5f) 
                )
            )
        );

    }
}
