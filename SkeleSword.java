import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Sword for skeleton
 */
public class SkeleSword extends BaseActor
{
    boolean isAttacking;
    Animator idleAnimator;
    Animator attackAnimator;

    public SkeleSword(float x, float y, Stage stage)
    {
        super(x,y,stage);
        
        isAttacking = false;
        idleAnimator = new Animator("assets/Sword Idle.png",2,1,0.25f,true);
        attackAnimator = new Animator("assets/Sword Attack.png",2,3,0.1f,false);

        this.setAnimator( idleAnimator);
    }
    
    public void act(float deltaTime)
    {
        super.act(deltaTime);
        
        if (isAttacking() && this.animator.isAnimationFinished() )
        {
            idle();
        }
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
        this.setAnimator( attackAnimator);
    }
    
    public void idle()
    {
        isAttacking = false;
        this.setAnimator(idleAnimator);
    }
}
