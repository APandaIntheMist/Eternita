import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Healthbar extends BaseActor
{
    private int HP;

    public Healthbar(float x, float y, Stage stage)
    {
        super(x,y,stage);

        HP = 3;

        this.setAnimator( new Animator("assets/healthbar.png"));
        setVisible(false);

    }
    
    public void act(float deltaTime)
    {
        super.act(deltaTime);
      
    }

    public int getHP()
    {
        int hpCopy = HP;
        return hpCopy;
    }

    public void loseHP()
    {
        if (HP == 3)
        {
            HP = 2;
            setVisible(true);
            this.setAnimator( new Animator("assets/Healthbar3-2_3.png",6,1,0.1f,false) );
            addAction(
                Actions.sequence(
                    Actions.alpha(1.0f),
                    Actions.delay(1.0f),
                    Actions.fadeOut(0.25f)
                )
            );
        }
        else if (HP == 2)
        {
            HP = 1;
            setVisible(true);
            this.setAnimator( new Animator("assets/Healthbar 2-1_3.png",6,1,0.1f,false));
            addAction(
                Actions.sequence(
                    Actions.alpha(1.0f),
                    Actions.delay(1.0f),
                    Actions.fadeOut(0.25f)
                )
            );
        }
        else if (HP == 1)
        {
            HP = 0;
            setVisible(true);
            this.setAnimator( new Animator("assets/Healthbar 1-0_3.png",6,1,0.1f,false));
            addAction(
                Actions.sequence(
                    Actions.alpha(1.0f),
                    Actions.delay(0.5f),
                    Actions.fadeOut(0.1f),
                    Actions.removeActor()
                )
            );
        }
    }
}
