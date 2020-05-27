import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Weapon for Sage
 */
public class SageStaff extends BaseActor
{
    private boolean isAttacking;
    Animator attackAnimator;
    Animator idleAnimator;

    public SageStaff(float x, float y, Stage stage)
    {
        super(x,y,stage);
        
        isAttacking = false;
        
        attackAnimator = new Animator("assets/Sage Staff Attack.png",4,3,0.05f,false);
        idleAnimator = new Animator("assets/Sage Staff.png",2,1,0.5f,true);

        this.setAnimator( idleAnimator );
    }
    
    public boolean isAttacking()
    {
        boolean copy = isAttacking;
        return copy;
    }
    
    public void attack()
    {
        isAttacking = true;
        attackAnimator.elapsedTime = 0;
        setAnimator( attackAnimator );
    }
    
    public void idle()
    {
        isAttacking = false;
        this.setAnimator( idleAnimator);
    }

}
