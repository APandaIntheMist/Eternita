import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import java.util.Random;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

/**
 * Enemy for Eternita
 */
public class Skeleton extends BaseActor
{
    Healthbar skeleHealth;
    public double iFrames;
    SkeleSword sword;
    public static int skelesDead = 0;
    public boolean dead;
    private Stage savedStage;
    public double stunFrames;
    Sound death;
    Animator regSkele;
    Animator sadSkele;

    /**
     * Constructor for objects of class Gargant
     */
    public Skeleton(float x, float y, Stage stage)
    {
        super(x,y,stage);

        savedStage = stage;
        
        regSkele = new Animator("assets/Skeleton.png");
        sadSkele = new Animator("assets/Sad Skeleton.png");

        setAnimator( regSkele );

        physics = new Physics(0,300,0);

        physics.setSpeed(100);

        setBoundaryPolygon(4);

        iFrames = 0.5;
        stunFrames = 1.0;

        skeleHealth = new Healthbar(x,y,stage);

        sword = new SkeleSword(0,0,stage);
        sword.setPosition(7,-33); // adjust position of sword to line up with skele
        this.addActor(sword); //attach sword to skele

        death = Gdx.audio.newSound( Gdx.files.internal("Sounds/Skeleton Death.wav") );

        dead = false;
    }

    public void act(float deltaTime)
    {
        super.act(deltaTime);

        iFrames += 1 * deltaTime;

        skeleHealth.setPosition(this.getX()-2,this.getY()+45);

        //while being knocked back or stunned make the skele sad
        if (iFrames < 0.5)
        {
            this.setAnimator( sadSkele );
            physics.setSpeed(300);
        }
        else //skele is angry rest of the time
        {
            this.setAnimator( regSkele );
            physics.setSpeed(100);
        }

        //reset stunframes as soon as skele has stopped being knocked back
        if (iFrames == 0.5)
            stunFrames = 0.0;

        //right after skele knock back is over, start counting stun frames
        if ( iFrames > 0.5 )
        {
            stunFrames += 1 * deltaTime;
            if (stunFrames < 1.0) //if skele is stunned, have it not move
            {
                physics.setSpeed(0);
                this.setAnimator( sadSkele );
            }
        }

        if (stunFrames > 1.0) //keep stunframes at about 1
            stunFrames = 1.0;

        //on skele death
        if (skeleHealth.getHP() == 0 && !dead)
        {
            physics.setSpeed(0);
            dead = true;
            skelesDead += 1;
            addAction(
                Actions.sequence(
                    Actions.delay(0.6f),
                    Actions.removeActor()
                )
            );

            Random rand = new Random();
            double chance = rand.nextDouble();
            if ( chance <= 0.05 )
            {
                Ham ham = new Ham(this.getX(), this.getY(), savedStage);

                ham.centerAt(this);
            }
            death.play();

        }

        if ( sword.isAttacking() )
            sword.setPosition(-15,-32);
        else
            sword.setPosition(7,-33);

        boundToWorld(1600,1200);
    }

    public int numDead()
    {
        int copy = skelesDead;
        return copy;
    }

    public void attack()
    {
        sword.attack();
    }

    public boolean isAttacking()
    {
        return sword.isAttacking();
    }

    public SkeleSword getSword()
    {
        return sword;
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
            skeleHealth.loseHP();
            iFrames = 0.0;
            stunFrames = 0.0;
        }
    }

}
