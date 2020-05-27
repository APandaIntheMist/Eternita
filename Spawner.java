import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

/**
 * Spawns enemies for Eternita
 */
public class Spawner extends BaseActor
{
    private double spawnTimer;
    SpawnerHealthbar healthbar;
    private double iFrames;
    private Stage savedStage;
    Sound spawnerHit;
    Animator staticSpawner;
    Animator sadSpawner;
    Animator spawning;

    /**
     * Constructor for objects of class Spawner
     */
    public Spawner(float x, float y, Stage stage)
    {
        super(x,y,stage);

        staticSpawner =  new Animator("assets/Spawner.png");
        sadSpawner = new Animator("assets/Spawner Sad.png");
        spawning = new Animator("assets/Spawner Spawn.png",4,3,0.09f,false);
        
        setAnimator( staticSpawner);
        this.setScale(0.60f);

        this.setBoundaryRectangle();

        spawnTimer = 0.0;
        iFrames = 1.0;

        savedStage = stage;

        healthbar = new SpawnerHealthbar(x+110,y+225,stage);

        spawnerHit = Gdx.audio.newSound( Gdx.files.internal("Sounds/SpawnerHit.wav") );
    }

    public void act(float deltaTime)
    {
        super.act(deltaTime);
        spawnTimer += deltaTime;
        iFrames += deltaTime;

        //on spawner death
        if (healthbar.getHP() == 0)
        {
            addAction(
                Actions.sequence(
                    Actions.delay(0.4f),
                    Actions.removeActor()
                )
            );
            if(healthbar.animator.isAnimationFinished())
            {
                healthbar.remove(); //remove healthbar after final animation
                Explosion boom = new Explosion(0,0,savedStage);
                boom.setPosition(this.getX(),this.getY());
                boom.setSize(this.getWidth(),this.getHeight());
            }
        }

        if (iFrames < 1.0)
            this.setAnimator( sadSpawner ); //make spawner sad while it is invincible
        else if( this.animator.isAnimationFinished() )
            this.setAnimator( staticSpawner ); //reset spawner image is losing health animation is done
    }

    public void hit()
    {
        if (iFrames > 1.0)
        {
            healthbar.loseHP();
            iFrames = 0.0;
            spawnerHit.play(0.5f);
        }
    }

    public double getSpawnTimer()
    {
        return spawnTimer;
    }

    /**
     * resets spawnTimer
     * should be used right after enemies have been spawned
     */
    public void resetTimer()
    {
        spawnTimer = 0.0;
        spawning.elapsedTime = 0;
        setAnimator( spawning );
    }

}
