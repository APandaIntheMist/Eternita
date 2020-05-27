import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Axe for the Player
 */
public class Axe extends BaseActor
{
    boolean isAttacking;
    boolean isComboing;
    boolean isIdle;
    double comboWindow;
    Animator attackAnimator;
    Animator comboAnimator;
    Animator idleAnimator;

    /**
     * Constructor for objects of class Axe
     */
    public Axe(float x, float y, Stage stage)
    {
        super(x,y,stage);
        
        idleAnimator = new Animator("assets/Axe Idle.png", 2,1,1.0f, true);
        attackAnimator = new Animator("assets/Axe Attack(R-L).png", 3, 3, 0.05f, false);
        comboAnimator = new Animator("assets/Axe Attack(L-R).png",3,3,0.05f,false);
        
        this.setAnimator( idleAnimator );
        
        setBoundaryPolygon(8);
        
        isAttacking = false;
        isComboing = false;
        isIdle = true;
        comboWindow = 0;
    }
    
    public void act(float deltaTime)
    {
        super.act(deltaTime);
        
        //switches animation back to normal after attack is done and window to combo is over
        if ((animator.isAnimationFinished() && comboWindow > 2)) //|| (animator.isAnimationFinished() && comboWindow > 2))
        {
            this.idle();
        }
    }
    
    public boolean animationDone()
    {
        return this.animator.isAnimationFinished();
    }

    public boolean isAttacking()
    {
        return isAttacking;
    }

    public boolean isComboing()
    {
        return isComboing;
    }

    public boolean isIdle()
    {
        return isIdle;
    }

    /**
     * Attack animation method
     */
    public void attack()
    {
        attackAnimator.elapsedTime = 0;
        setAnimator( attackAnimator );
        isAttacking = true;
        isComboing = false;
        isIdle = false;
    }

    /**
     * Combo animation method
     */
    public void combo()
    {  
        comboAnimator.elapsedTime = 0;
        setAnimator( comboAnimator );
        isComboing = true;
        isAttacking = false;
        isIdle = false;
    }

    /**
     * return comboWindow
     * comboWindow is the amount of time the player has spent not attacking again
     */
    public double getComboWindow()
    {
        return comboWindow;
    }

    /**
     * idle animation method
     */
    public void idle()
    {
        setAnimator( idleAnimator );
        isAttacking = false;
        isComboing = false;
        isIdle = true;
    }
}
