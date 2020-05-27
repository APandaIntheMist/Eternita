import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Barbarian extends BaseActor
{
    double comboWindow;
    Compass compass;
    public Axe axe; 

    public Barbarian(float x, float y, Stage stage)
    {
        super(x,y,stage);

        comboWindow = 0;

        compass = new Compass(x,y,stage);
        compass.setScale(0.15f);
        
        setAnimator( new Animator("assets/Barbarian.png") );

        // parameters: acceleration rate, maximum speed, deceleration rate
        physics = new Physics(700, 200, 700);

        setBoundaryPolygon(4);
        
        axe = new Axe(0,0,stage);
        axe.setPosition(7,-47); // adjust position of axe to line up with barb
        this.addActor(axe); //attach axe to barb
    }

    public boolean isAttacking()
    {
        return axe.isAttacking();
    }

    public boolean isComboing()
    {
        return axe.isComboing();
    }

    public boolean isIdle()
    {
        return axe.isIdle();
    }
    
    public boolean axeDone()
    {
        return axe.animationDone();
    }

    /**
     * Attack animation method
     */
    public void attack()
    {
        axe.attack();
    }

    /**
     * Combo animation method
     */
    public void combo()
    {  
        axe.combo();
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
        axe.idle();
    }

    public void act(float deltaTime)
    {
        super.act(deltaTime);
        
        //centers and rotates compass to keep up with barb
        compass.centerAt(this);
        compass.setRotation(this.getRotation());
        
        if (StoneRoom.fury)
        {
            this.setScale(3.0f);
            compass.setScale(0.45f);
        }
        else
        {
            this.setScale(1.0f);
            compass.setScale(0.15f);
        }
        
        
        if ( axe.isAttacking() || axe.isComboing() )
            axe.setPosition(-45,-68);
        else
            axe.setPosition(7,-47);

        // rotate barb to equal the angle of motion
        if ( (physics.isMoving() && StoneRoom.barbiFrames > 1.0) || (physics.isMoving() && TestRoom.barbiFrames > 1.0) )
        {
            this.physics.maximumSpeed = 205;
            setRotation( physics.getMotionAngle() );
        }
        else
        {
            this.physics.maximumSpeed = 400; //max speed increases when barb is hit by enemy
        }

        //if barb is attacking (or comboing) and the attack animation is finished, start counting the amount of time before barb should idle (2 sec window)
        if ( axe.isAttacking && axeDone() && comboWindow < 2)
        {
            comboWindow += 1 * deltaTime;
            //isAttacking = false;
        }
        else if ( axe.isComboing && axeDone() && comboWindow < 2 )
        {
            comboWindow += 1 * deltaTime;
            //isComboing = false;
        }
        else
            comboWindow = 0.0;

        //moves the barb while he is attacking
        if ( (axe.isAttacking() && !axeDone()) 
        || (axe.isComboing() && !axeDone()))
        {
            physics.accelerateAtAngle(getRotation());
        }

        //switches animation back to normal after attack is done and window to combo is over
        if ((axeDone() && comboWindow > 2)) //|| (animator.isAnimationFinished() && comboWindow > 2))
        {
            this.idle();
        }

        boundToWorld(1600,1200);

        alignCamera(1600,1200);

    }
}