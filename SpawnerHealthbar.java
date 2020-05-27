import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class SpawnerHealthbar extends BaseActor
{
    private int HP;

    public SpawnerHealthbar(float x, float y, Stage stage)
    {
        super(x,y,stage);

        HP = 6;

        this.setAnimator( new Animator("assets/healthbar.png"));
        this.setScale(2.2f);
        
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
        if (HP == 6)
        {
            HP = 5;
            setVisible(true);
            this.setAnimator( new Animator("assets/Spawner Healthbar 1.png",4,1,0.1f,false) );
        }
        else if (HP == 5)
        {
            HP = 4;
            this.setAnimator( new Animator("assets/Spawner Healthbar 2.png",4,1,0.1f,false) );
        }
        else if (HP == 4)
        {
            HP = 3;
            this.setAnimator( new Animator("assets/Spawner Healthbar 3.png",4,1,0.1f,false) );
        }
        else if (HP == 3)
        {
            HP = 2;
            this.setAnimator( new Animator("assets/Spawner Healthbar 4.png",4,1,0.1f,false) );
        }
        else if (HP == 2)
        {
            HP = 1;
            this.setAnimator( new Animator("assets/Spawner Healthbar 5.png",4,1,0.1f,false));
        }
        else if (HP == 1)
        {
            HP = 0;
            this.setAnimator( new Animator("assets/Spawner Healthbar 6.png",4,1,0.1f,false));
        }
    }
}
