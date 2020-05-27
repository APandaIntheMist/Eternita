import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class PlayerHP extends BaseActor
{
    public static int HP = 4;
    Animator fullHealth;
    Animator threeFourths;
    Animator twoFourths;
    Animator oneFourth;
    Animator fourToThree;
    Animator threeToTwo;
    Animator twoToOne;
    Animator oneToZero;

    public PlayerHP(float x, float y, Stage stage)
    {
        super(x,y,stage);
        
        fullHealth = new Animator("assets/PlayerHPfull.png");
        threeFourths = new Animator("assets/PlayerHP3-4.png");
        twoFourths = new Animator("assets/PlayerHP2-4.png");
        oneFourth = new Animator("assets/PlayerHP1-4.png");
        
        fourToThree = new Animator("assets/PlayerHP4-_3.png",3,2,0.1f,false);
        threeToTwo = new Animator("assets/PlayerHP3-_2.png",3,2,0.1f,false);
        twoToOne = new Animator("assets/PlayerHP2-_1.png",3,2,0.1f,false);
        oneToZero = new Animator("assets/PlayerHP1-_0.png",3,2,0.1f,false);
        
        if (HP == 4)
            this.setAnimator( fullHealth );
        else if (HP == 3)
            this.setAnimator( threeFourths );
        else if (HP == 2)
            this.setAnimator( twoFourths );
        else if (HP == 1)
            this.setAnimator( oneFourth );
        else
        {
            this.setAnimator( fullHealth );
            resetHP();
        }
    }
    
    public void act(float deltaTime)
    {
        super.act(deltaTime);
        if (HP == 4)
            this.setAnimator( fullHealth );
    }
    
    public void resetHP()
    {
        HP = 4;
    }
    
    public void addHP()
    {
        if (HP == 4)
        {
            HP = 4;
        }
        else if (HP == 3)
        {
            this.setAnimator( fourToThree );
            this.animator.animation.setPlayMode(Animation.PlayMode.REVERSED);
            HP = 4;
        }
        else if (HP == 2)
        {
            this.setAnimator( threeToTwo );
            this.animator.animation.setPlayMode(Animation.PlayMode.REVERSED);
            HP = 3;
        }
        else if (HP == 1)
        {
            this.setAnimator( twoToOne );
            this.animator.animation.setPlayMode(Animation.PlayMode.REVERSED);
            HP = 2;
        }
    }
    
    public int getHP()
    {
        int copy = HP;
        return copy;
    }
    
    public void loseHP()
    {
        if (HP == 4)
        {
            HP = 3;
            this.setAnimator( fourToThree);
        }
        else if (HP == 3)
        {
            HP = 2;
            this.setAnimator( threeToTwo );
        }
        else if (HP == 2)
        {
            HP = 1;
            this.setAnimator( twoToOne );
        }
        else if (HP == 1)
        {
            HP = 0;
            this.setAnimator( oneToZero );
        }
    }
}
