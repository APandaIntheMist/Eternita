import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

/**
 * Main Menu
 * menu screen music = virtues vocis?
 * "Virtutes Vocis" Kevin MacLeod (incompetech.com)
Licensed under Creative Commons: By Attribution 3.0 License
http://creativecommons.org/licenses/by/3.0/
 */
public class TestRoom extends BaseScreen
{
    BaseActor bg;
    BaseActor title;
    Music bgMusic;

    Barbarian barb;
    PlayerHP barbHP;

    Floor floor;
    int currentRoom;
    int version;

    Label scoreText;
    public static int score = 0;

    public static double barbiFrames;

    public TestRoom(boolean newGame, int index)
    {
        super(newGame,index);
    }

    public void initialize(boolean newGame, int index)
    {
        bg = new BaseActor(0,0,mainStage);
        bg.setAnimator( new Animator("assets/dungeonFloor.png") );
        bg.setSize(800,600);

        bg = new BaseActor(0,0, mainStage);
        bg.animator = new Animator("assets/dungeonbg.jpg");
        bg.setSize(1600,1200);

        //attackCounter = 0;

        barb = new Barbarian(800,600, mainStage);
        barbHP = new PlayerHP(0,0,mainStage);

        barbiFrames = 1.0;

        Skeleton test = new Skeleton(400,300,mainStage);
        //Ham test3 = new Ham(400,300,mainStage);
        //Sage test2 = new Sage(400,200,mainStage);

        //Spawner s1 = new Spawner(600,200,mainStage);

        scoreText = new Label("Score: " + score, BaseGame.labelStyle);

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/8bit Dungeon Boss.mp3"));
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.50f);
        bgMusic.play();
    }

    @Override
    public void update(float deltaTime)
    {
        barbiFrames += deltaTime;

        score = (Skeleton.skelesDead * 100) + (Ham.hamsCollected * 100) + (Sage.sagesDead * 500);
        scoreText.setText( "Score: " + score );

        //end game
        if (barbHP.getHP() == 0 && barbHP.animator.isAnimationFinished() )
        {
            bgMusic.stop();
            BaseGame.setActiveScreen( new GameOver(false,0) ); 
        }

        //restart game
        if (Gdx.input.isKeyPressed(Keys.R))
        {
            bgMusic.stop();
            BaseGame.setActiveScreen( new TestRoom(false,0) );
        }

        //back to main menu
        if (Gdx.input.isKeyPressed(Keys.B))
        {
            bgMusic.stop();
            BaseGame.setActiveScreen( new MenuScreen(false,0) );  
        }

        // move barb based on keys pressed
        if ( ( Gdx.input.isKeyPressed(Keys.RIGHT) && barb.axeDone() && barb.isAttacking() && barbiFrames > 0.5 ) 
        || ( Gdx.input.isKeyPressed(Keys.RIGHT) && barb.axeDone() && barb.isComboing() && barbiFrames > 0.5 ) 
        || ( Gdx.input.isKeyPressed(Keys.RIGHT) && !barb.isAttacking() && !barb.isComboing() && barbiFrames > 0.5 ))
            barb.physics.accelerateAtAngle(0);

        if ( ( Gdx.input.isKeyPressed(Keys.LEFT) && barb.axeDone() && barb.isAttacking() && barbiFrames > 0.5 ) 
        || ( Gdx.input.isKeyPressed(Keys.LEFT) && barb.axeDone() && barb.isComboing() && barbiFrames > 0.5 ) 
        || ( Gdx.input.isKeyPressed(Keys.LEFT) && !barb.isAttacking() && !barb.isComboing() && barbiFrames > 0.5 ) )
            barb.physics.accelerateAtAngle(180);

        if ( (Gdx.input.isKeyPressed(Keys.UP) && barb.axeDone() && barb.isAttacking()) 
        || (Gdx.input.isKeyPressed(Keys.UP) && barb.axeDone() && barb.isComboing() && barbiFrames > 0.5 ) 
        || (Gdx.input.isKeyPressed(Keys.UP) && !barb.isAttacking() && !barb.isComboing() && barbiFrames > 0.5) )
            barb.physics.accelerateAtAngle(90);

        if ( ( Gdx.input.isKeyPressed(Keys.DOWN) && barb.axeDone() && barb.isAttacking() && barbiFrames > 0.5) 
        || ( Gdx.input.isKeyPressed(Keys.DOWN) && barb.axeDone() && barb.isComboing() && barbiFrames > 0.5) 
        || ( Gdx.input.isKeyPressed(Keys.DOWN) && !barb.isAttacking() && !barb.isComboing() && barbiFrames > 0.5 ))
            barb.physics.accelerateAtAngle(270);

        //lets the barbarian attack from idle or after a combo    
        if ((Gdx.input.isKeyJustPressed(Keys.Z) && !barb.isAttacking()) || 
        (Gdx.input.isKeyJustPressed(Keys.Z) && barb.isComboing() && barb.axeDone()))
        {
            barb.attack();
        }

        //lets the barbarian combo
        if (Gdx.input.isKeyJustPressed(Keys.Z) && barb.getComboWindow() <= 2 
        && barb.isAttacking() && !barb.isIdle() && barb.getComboWindow() > 0 
        && !barb.isComboing() && barb.axeDone())
        {
            barb.combo();
        }

        //control all rocks
        //prevent all things from walking through rocks
        for (BaseActor rock : BaseActor.getList(mainStage,"Rock") )
        {
            barb.preventOverlap(rock);

            for (BaseActor skele : BaseActor.getList(mainStage, "Skeleton") )
            {
                skele.preventOverlap(rock);
            }

            for (BaseActor sage : BaseActor.getList(mainStage, "Sage") )
            {
                sage.preventOverlap(rock);
            }

            for (BaseActor fireball : BaseActor.getList(mainStage, "Sage_Fireball") )
            {
                if (fireball.overlaps(rock))
                {
                    fireball.remove();
                    rock.remove();
                }

            }
        }

        //control all hams
        for (BaseActor ham : BaseActor.getList(mainStage,"Ham") )
        {
            //System.out.println("Ham X: "+ham.getX());
            //System.out.println("Ham Y: "+ham.getY());
            if (barb.overlaps(ham))
            {
                if (barbHP.getHP() < 4)
                {
                    ham.remove();
                    barbHP.addHP();
                }
                else
                {
                    ham.remove();
                    Ham.hamsCollected += 1;
                }
            }
        }

        //loop to get all doors on level
        for ( BaseActor actor : BaseActor.getList(mainStage,"Door") )
        {
            Door door = (Door)actor;

            if ( barb.overlaps(door)  )
            {
                //barb's x and y coords
                float barbX = barb.getX();
                float barbY = barb.getY();
                int newRoomIndex = currentRoom;

                //if barb is overlaps left door
                if (barbX < 300)
                {
                    newRoomIndex = floor.determineNextRoomIndex(currentRoom,"LEFT");                  //newRoomCoords[0] is X position of next room
                    BaseGame.setActiveScreen( new StoneRoom(false, newRoomIndex) ); //newRoomCoords[1] is Y position of next room
                }
                //if barb overlaps right door
                else if (barbX > 1100)
                {
                    newRoomIndex = floor.determineNextRoomIndex(currentRoom,"RIGHT");                  //newRoomCoords[0] is X position of next room
                    BaseGame.setActiveScreen( new StoneRoom(false, newRoomIndex) );
                }
                //if barb overlaps top door
                else if ( barbY > 800 )
                {
                    newRoomIndex = floor.determineNextRoomIndex(currentRoom,"UP");                  //newRoomCoords[0] is X position of next room
                    BaseGame.setActiveScreen( new StoneRoom(false, newRoomIndex) );
                }
                //if barb overlaps bottom door
                else if (barbY < 800 )
                {
                    newRoomIndex = floor.determineNextRoomIndex(currentRoom,"DOWN");                  //newRoomCoords[0] is X position of next room
                    BaseGame.setActiveScreen( new StoneRoom(false, newRoomIndex) );
                }
            }
        }

        //control all spawners
        for (BaseActor spawner : BaseActor.getList(mainStage, "Spawner"))
        {
            barb.preventOverlap(spawner);

            if (((Spawner)spawner).getSpawnTimer() > 8.0)
            {
                //spawns 5 skeletons in random positions in a circle around a spawner
                for (int i = 0; i < 5; i++)
                {
                    float radius = spawner.getWidth()*0.7f; //use width of spawner as radius
                    float angle = (float)Math.random()*(float)Math.PI*2.0f; //generate random angle
                    float circleX = (float)Math.cos(angle)*radius; //random x position of circle
                    float circleY = (float)Math.sin(angle)*radius; //random y position of circle
                    float trueX = circleX + ( spawner.getX() + (spawner.getWidth()/2) - 40.0f ); //adjust random position to be around spawner
                    float trueY = circleY + ( spawner.getY() + (spawner.getHeight()/2) - 40.0f );

                    Skeleton newSkele = new Skeleton(trueX, trueY, mainStage); // position of newly spawned skeleton
                }

                ((Spawner)spawner).resetTimer();
            }

            //prevent both types of enemies from overlapping spawner
            for (BaseActor skele : BaseActor.getList(mainStage, "Skeleton"))
            {
                skele.preventOverlap(spawner);
            }
            for (BaseActor sage : BaseActor.getList(mainStage, "Sage"))
            {
                sage.preventOverlap(spawner);
            }
        }

        //control all sages
        for (BaseActor sage : BaseActor.getList(mainStage, "Sage"))
        {
            float angleToPlayer = barb.getPosition().sub( sage.getPosition() ).angle(); //angle from sage to barb
            sage.setRotation(angleToPlayer); //make sages face barb always
            float hitAngle = barb.getRotation();

            float xDistance = Math.abs( barb.getX()-sage.getX() );
            float yDistance = Math.abs( barb.getY()-sage.getY() );

            //while sage is invincible
            if( ((Sage)sage).getIFrames() < 0.5 )
            {
                sage.physics.setSpeed(300);
                sage.physics.setMotionAngle( hitAngle );
            }
            //if sage is too far
            else if ( (xDistance > 500) || (yDistance > 350) )
            {
                //chase player
                sage.physics.setSpeed(100); 
                sage.physics.setMotionAngle( angleToPlayer );
            }
            //if sage has been attacked when not invincible
            else if( ( Gdx.input.isKeyJustPressed(Keys.Z) && barb.axe.overlaps(sage) && barb.axe.isAttacking && !barb.axeDone() && ((Sage)sage).getIFrames() >= 0.5 ) 
            || ( Gdx.input.isKeyJustPressed(Keys.Z) && barb.axe.overlaps(sage) && barb.axe.isComboing && !barb.axeDone() && ((Sage)sage).getIFrames() >= 0.5) )
            {
                ((Sage)sage).hit(); //have it be hit and lose hp
            }
            //remain stationary
            else
            {
                sage.physics.setSpeed(0);
            }

            //might need more testing, problem *seems* to be solved
            //System.out.println("Attackspeed: "+((Sage)sage).attackSpeed);
            //spawns firebal 
            if ( ((Sage)sage).attackSpeed >= 1.98 && ((Sage)sage).getIFrames() >= 0.5) //spawn fireball in sync with attack speed if not in iFrames
            {
                Sage_Fireball fireball = new Sage_Fireball(0,0,mainStage);
                //sage.addActor(fireball);
                //SageStaff aStaff = ((Sage)sage).getSageStaff();

                fireball.centerAt(sage);
                //fireball.setPosition( (sage.getX() + sage.getWidth()/2  - fireball.getWidth()/2)+25,
                //(sage.getY() + sage.getHeight()/2 - fireball.getHeight()/2)+25 );
                fireball.setRotation( sage.getRotation() );
                fireball.physics.setMotionAngle( sage.getRotation() );
            }

        }

        //controls all sages' fireballs
        //System.out.println(BaseActor.getList(mainStage, "Sage_Fireball").size());
        for (BaseActor fireball: BaseActor.getList(mainStage, "Sage_Fireball"))
        {
            if( fireball.overlaps(barb) && barbiFrames > 1.0 )
            {
                fireball.remove();
                barbHP.loseHP();
                barbiFrames = 0.0; //make barb invulnerable
            }

            //while barb is invincible
            if (barbiFrames < 1.0)
            {
                barb.physics.setSpeed(400);
                barb.physics.setMotionAngle( fireball.getRotation() );
            }
        }

        //control all skeletons
        for (BaseActor skele : BaseActor.getList(mainStage, "Skeleton"))
        {
            float angleToPlayer = barb.getPosition().sub( skele.getPosition() ).angle();
            float hitAngle = barb.getRotation();
            
            //System.out.println(angleToPlayer);
            //System.out.println(skele.physics.getMotionAngle());

            float dist = barb.getPosition().dst( skele.getX()+skele.getWidth()/2,skele.getY()+skele.getWidth()/2 );

            //barb gets hit if are frames are over
            if( ( (dist < 60) && !((Skeleton)skele).isAttacking() && barbiFrames > 1.0 && barb.axeDone() && ((Skeleton)skele).stunFrames > 0.9 ) )
            {

                ((Skeleton)skele).attack();
                barbHP.loseHP();
                barbiFrames = 0.0; //barb becomes invulnerable

            }

            //makes barb knockback
            if (barbiFrames < 0.1)
            {
                barb.physics.setSpeed(400);
                barb.physics.setMotionAngle( skele.getRotation() );
            }

            //while skele is invincible
            if( ((Skeleton)skele).iFrames < 0.5 )
            {
                skele.physics.setSpeed(300);
                skele.physics.setMotionAngle( hitAngle );
            }
            //if skeleton has been attacked when not invincible
            else if( ( Gdx.input.isKeyJustPressed(Keys.Z) && ( dist < 95) && barb.isAttacking() && !barb.axeDone() && ((Skeleton)skele).iFrames >= 0.5 ) 
            || ( Gdx.input.isKeyJustPressed(Keys.Z) && ( dist < 95) && barb.isComboing() && !barb.axeDone() && ((Skeleton)skele).iFrames >= 0.5) )
            {
                ((Skeleton)skele).hit();
                //((Skeleton)skele).stunFrames = 0.0;
            }
            //move skele normally towards player
            else if ( ((Skeleton)skele).stunFrames > 0.9 )
            {
                skele.setRotation( angleToPlayer );
                skele.physics.setMotionAngle( angleToPlayer );
                skele.physics.setSpeed(100);
            }
        

            //prevent skeles from overlapping each other
            for (BaseActor otherSkele : BaseActor.getList(mainStage,"Skeleton"))
            {
                if (skele != otherSkele)
                    skele.preventOverlap(otherSkele);
            }
        }

    }
}
