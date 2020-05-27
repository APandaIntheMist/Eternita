import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

/**
 * Ranged enemy for Eternita
 */
public class Sage extends BaseActor
{
    Healthbar sageHealth;
    double iFrames;
    public double attackSpeed;
    SageStaff staff;
    public static int sagesDead = 0;
    public boolean dead;
    Sound death;
    Animator regSage;
    Animator sadSage;

    /**
     * Constructor for objects of class Sage
     */
    public Sage(float x, float y, Stage stage)
    {
        super(x,y,stage);

        regSage = new Animator("assets/Sage.png");
        sadSage = new Animator("assets/Sad Sage.png");
        
        setAnimator( regSage );

        physics = new Physics(0,300,0);

        physics.setSpeed(100);

        setBoundaryPolygon(16);

        iFrames = 0.5;
        attackSpeed = 0.0;

        sageHealth = new Healthbar(0,0,stage);
        //sageHealth.setPosition(0,30); //alternate way to link healthbar
        //this.addActor(sageHealth);
        
        staff = new SageStaff(0,0,stage);
        staff.setPosition(37,-57); // adjust position of staff to line up with sage
        this.addActor(staff); //attach staff to Sage
        
        dead = false;
        
        death = Gdx.audio.newSound( Gdx.files.internal("Sounds/Skeleton Death.wav") );
    }

    public void act(float deltaTime)
    {
        super.act(deltaTime);
        iFrames += 1 * deltaTime;
        attackSpeed += 1 * deltaTime;
        
        sageHealth.setPosition(this.getX()+5,this.getY()+40); //position healthbar
        
        //on sage death
        if (sageHealth.getHP() == 0 && !dead)
        {
            physics.setSpeed(0);
            dead = true;
            sagesDead += 1;
            addAction(
                Actions.sequence(
                    Actions.delay(0.6f),
                    Actions.removeActor()
                )
            );
            death.play();
            
        }
        
        //make sage sad while invincible
        if (iFrames < 0.5)
        {
            this.setAnimator( sadSage );
            physics.setSpeed(300);
            //attackSpeed -= 2.0;
        }
        else
        {
            this.setAnimator( regSage );
        }
        
        if (attackSpeed >= 2.0 && iFrames >= 0.5)
        {
            //attack every 2 secs if not invincible
            staff.attack();
            attackSpeed -= 2.0;
        }
        else if ( staff.isAttacking() && staff.animator.isAnimationFinished() )
            staff.idle(); //idle animation
            
        //make sure staff is lined up with sage
        if ( staff.isAttacking() )
            staff.setPosition(37,-25);
        else
            staff.setPosition(37,-57);
        
        boundToWorld(1600,1200);
    }
    
    public int numDead()
    {
        int copy = sagesDead;
        return copy;
    }
    
    /**
     * return staff object
     * doesn't really work
     */
    public SageStaff getSageStaff()
    {
        return staff;
    }
    
    public double getAttackSpeed()
    {
        double copy = attackSpeed;
        return copy;
    }
    
    public double getIFrames()
    {
        double copyOfIFrames = iFrames;
        return copyOfIFrames;
    }
    
    public void hit()
    {
        if(iFrames >= 0.5)
        {
            sageHealth.loseHP();
            iFrames = 0.0;
        }
    }
}
