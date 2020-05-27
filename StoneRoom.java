import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;

/**
 * Paul thoughts:
 * axe swing destroys fireballs
 * enemies knocked further/stunned
 * 
 * Daniel thoughts:
 * add powerup: size increase, dmg increase (fireaxe lower opacity)
 * 
 * Ask Stemkoski:
 * 
 * 
 * TO DO:
 * rework barb hit box*
 * add barriers to doors that go down when room is clear*
 * room cleared message*
 * info menu*
 * win the game*
 * sound/music*
 * update button instructions in menus*
 * fading level names (new class similar to fading of healthbars)
 * credits menu
 * 
 * Music = 8bit Dungeon Boss
 * '8bit Dungeon Boss' Kevin MacLeod (incompetech.com)
Licensed under Creative Commons: By Attribution 3.0 License
http://creativecommons.org/licenses/by/3.0/
 */

public class StoneRoom extends BaseScreen
{
    BaseActor BG;
    BaseActor clear;

    Barbarian barb;
    PlayerHP barbHP;
    public static boolean fury = false;

    Floor floor;
    int currentRoom;
    int version;

    Label scoreText;
    public static int score = 0;
    
    Label furyStatus;
    double furyTimer;

    public static Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/8bit Dungeon Boss.mp3"));
    Sound oof;
    Sound skeleHit;
    Sound swing1;
    Sound swing2;
    Sound getHam;

    public static double barbiFrames;

    public StoneRoom(boolean newGame, int index)
    {
        super(newGame, index);       
    }

    public void initialize(boolean newGame, int index)
    {
        BG = new BaseActor(0,0, mainStage);
        BG.animator = new Animator("assets/dungeonBG.jpg");
        BG.setSize(1600,1200);

        barb = new Barbarian(800,600, mainStage);
        barbHP = new PlayerHP(0,0,mainStage);

        barbiFrames = 0.3;

        scoreText = new Label("Score: " + score, BaseGame.labelStyle); //initialize text
        
        furyStatus = new Label("FURY READY!", BaseGame.labelStyle);
        uiStage.addActor(furyStatus);
        furyTimer = 30.0;
        fury = false;
        
        //room cleared message
        clear = new BaseActor(200,375,uiStage);
        clear.setAnimator( new Animator("assets/clear.png") );
        clear.setVisible(false);

        //initialize sounds
        oof = Gdx.audio.newSound( Gdx.files.internal("Sounds/Oof.wav") );
        skeleHit = Gdx.audio.newSound( Gdx.files.internal("Sounds/Skeleton Hit.wav") );
        swing1 = Gdx.audio.newSound( Gdx.files.internal("Sounds/AxeAttack.wav") );
        swing2 = Gdx.audio.newSound( Gdx.files.internal("Sounds/AxeCombo.wav") );
        getHam = Gdx.audio.newSound( Gdx.files.internal("Sounds/getHam.wav") );

        //where player is in arraylist
        currentRoom = index;
        if (newGame)
        {
            score = 0;
            Skeleton.skelesDead = 0;
            Sage.sagesDead = 0;
            Ham.hamsCollected = 0;
            barbHP.HP = 4;
            floor = new Floor(newGame, new ArrayList<Integer>()); //2nd param arraylist doesn't matter, isn't used
            helperClass.main(floor.getFloor()); //takes the randomly generated floor and stores it in the helper class

            bgMusic.setLooping(true);
            bgMusic.setVolume(0.25f);
            bgMusic.play();
        }
        else
        {
            floor = new Floor(newGame, helperClass.getList());
        }
        version = floor.getRoomVersion(currentRoom);

        //version = 9;
        //basically a switch that creates a new room
        //new room that is created is dependent on player's position on the floor
        //and what room the player is going to
        if (version == 1)
        {   //SURROUNDED BY SKELES
            uiStage.addActor(barbHP);
            barbHP.setPosition(350,0);
            scoreText.setFontScale(0.7f);
            uiStage.addActor(scoreText);
            scoreText.setPosition(360,5);
            // Stack HPScore = new Stack(barbHP,scoreText);

            for (int i = 0; i<10; i++)
            {
                float radius = 600.0f; 
                float angle = (float)Math.random()*(float)Math.PI*2.0f; //generate random angle
                float circleX = (float)Math.cos(angle)*radius; //random x position of circle
                float circleY = (float)Math.sin(angle)*radius; //random y position of circle
                float trueX = circleX + 800; 
                float trueY = circleY + 600;

                Skeleton newSkele = new Skeleton(trueX, trueY, mainStage); // position of newly spawned skeleton
            }

            //uiTable.debug();
            uiTable.add().expandX();
            //uiTable.add(level1).expandX().expandY();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            //uiTable.add(HPScore).bottom().expandX().expand();
            uiTable.add().expandX();
            this.addDoors(floor.getRoomIndex(version));
        }
        else if (version == 2)
        {   //DOUBLE ARMY
            uiStage.addActor(barbHP);
            barbHP.setPosition(350,0);
            scoreText.setFontScale(0.7f);
            uiStage.addActor(scoreText);
            scoreText.setPosition(360,5);
            //Stack HPScore = new Stack(barbHP,scoreText);

            for (int y = 900; y >= 400; y -= 100)
            {
                Skeleton newSkele = new Skeleton(200,y,mainStage);
                Skeleton newSkele2 = new Skeleton(1400,y,mainStage);
            }

            Sage sage1 = new Sage(100,600,mainStage);
            Sage sage2 = new Sage(1500,600,mainStage);

            //uiTable.debug();
            uiTable.add().expandX();
            //uiTable.add(level2).expandX().expandY();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            //uiTable.add(HPScore).bottom().expandX().expand();
            uiTable.add().expandX();
            this.addDoors(floor.getRoomIndex(version));
        }
        else if (version == 3)
        {   //SEMICIRCLE W/ SPAWNER
            uiStage.addActor(barbHP);
            barbHP.setPosition(350,0);
            scoreText.setFontScale(0.7f);
            uiStage.addActor(scoreText);
            scoreText.setPosition(360,5);
            //Stack HPScore = new Stack(barbHP,scoreText);

            for (float x = -500; x <= 500; x += 83.33)
            {
                float y = (float)Math.sqrt(250000-(x*x)) +400; 
                float trueX = x +800; 
                float trueY = y;

                Rock newRock = new Rock(trueX, trueY, mainStage);
            }

            Rock r1 = new Rock(318.374f,515.2716f,mainStage);
            Rock r2 = new Rock(1254.6086f,515.2716f,mainStage);

            Spawner s1 = new Spawner(700,200,mainStage);

            //uiTable.debug();
            uiTable.add().expandX();
            //uiTable.add(level3).expandX().expandY();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            //uiTable.add(HPScore).bottom().expandX().expand();
            uiTable.add().expandX();
            this.addDoors(floor.getRoomIndex(version));
        }
        else if (version == 4)
        {   //ROCK VERTICAL DIVIDE
            uiStage.addActor(barbHP);
            barbHP.setPosition(350,0);
            scoreText.setFontScale(0.7f);
            uiStage.addActor(scoreText);
            scoreText.setPosition(360,5);
            //Stack HPScore = new Stack(barbHP,scoreText);

            for (int y = 1000; y >= 200; y -= 100)
            {
                new Rock(800,y,mainStage);
            }

            new Spawner(1100,600,mainStage);

            for (int n = 0; n < 6; n++)
            {
                Random rand = new Random();
                float randX = (float)rand.nextInt(500)+100;
                float randY = (float)rand.nextInt(800)+200;
                new Skeleton(randX,randY,mainStage);
            }

            //uiTable.debug();
            uiTable.add().expandX();
            //uiTable.add(level4).expandX().expandY();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            //uiTable.add(HPScore).bottom().expandX().expandY();
            uiTable.add().expandX();
            this.addDoors(floor.getRoomIndex(version));
        }
        else if (version == 5)
        {   //SAGE ARENA
            uiStage.addActor(barbHP);
            barbHP.setPosition(350,0);
            scoreText.setFontScale(0.7f);
            uiStage.addActor(scoreText);
            scoreText.setPosition(360,5);
            //Stack HPScore = new Stack(barbHP,scoreText);

            for (float degrees = 0; degrees <= 1.0; degrees += 0.05)
            {
                float radius = 500.0f; 
                float angle = (float)degrees*(float)Math.PI*2.0f; 
                float circleX = (float)Math.cos(angle)*radius; 
                float circleY = (float)Math.sin(angle)*radius; 
                float trueX = circleX + 800; 
                float trueY = circleY + 600;

                new Rock(trueX, trueY, mainStage);
            }

            new Sage(800,1000,mainStage);
            new Sage(400,600,mainStage);
            new Sage(800,200,mainStage);
            new Sage(1200,600,mainStage);

            //uiTable.debug();
            uiTable.add().expandX();
            //uiTable.add(level5).expandX().expandY();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            //uiTable.add(HPScore).bottom().expandX().expand();
            uiTable.add().expandX();
            this.addDoors(floor.getRoomIndex(version));
        }
        else if (version == 6)
        {   //ROCK HORIZONTAL DIVIDE
            uiStage.addActor(barbHP);
            barbHP.setPosition(350,0);
            scoreText.setFontScale(0.7f);
            uiStage.addActor(scoreText);
            scoreText.setPosition(360,5);
            //Stack HPScore = new Stack(barbHP,scoreText);

            for (int x = 200; x <= 1400; x += 60)
            {
                new Rock(x, 550, mainStage);
            }

            new Spawner(700,900,mainStage);

            new Sage(600,400,mainStage);
            new Sage(1000,400,mainStage);

            //uiTable.debug();
            uiTable.add().expandX();
            //uiTable.add(level6).expandX().expandY();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            //uiTable.add(HPScore).bottom().expandX().expand();
            uiTable.add().expandX();
            this.addDoors(floor.getRoomIndex(version));
        }
        else if (version == 7)
        {   //TWO SPAWNERS
            uiStage.addActor(barbHP);
            barbHP.setPosition(350,0);
            scoreText.setFontScale(0.7f);
            uiStage.addActor(scoreText);
            scoreText.setPosition(360,5);
            //Stack HPScore = new Stack(barbHP,scoreText);

            new Spawner(300,600,mainStage);
            new Spawner(1300,600,mainStage);

            //uiTable.debug();
            uiTable.add().expandX();
            //uiTable.add(level7).expandX().expandY();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            //uiTable.add(HPScore).bottom().expandX().expand();
            uiTable.add().expandX();
            this.addDoors(floor.getRoomIndex(version));
        }
        else if (version == 8)
        {   //TWO HORIZONTAL WALLS,2 SAGES, 2 SPAWNERS
            uiStage.addActor(barbHP);
            barbHP.setPosition(350,0);
            scoreText.setFontScale(0.7f);
            uiStage.addActor(scoreText);
            scoreText.setPosition(360,5);
            //Stack HPScore = new Stack(barbHP,scoreText);

            for (int y = 400; y <= 800; y += 80)
            {
                new Rock(400,y,mainStage);
                new Rock(1200,y,mainStage);
            }

            new Sage(300,600,mainStage);
            new Sage(1300,600,mainStage);

            new Spawner(700,200,mainStage);
            new Spawner(700,950,mainStage);

            //uiTable.debug();
            uiTable.add().expandX();
            //uiTable.add(level8).expandX().expandY();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            //uiTable.add(HPScore).bottom().expandX().expand();
            uiTable.add().expandX();
            this.addDoors(floor.getRoomIndex(version));
        }
        else if (version == 9)
        {   // CROSS SHAPED LEVEL W/ 4 SPAWNERS
            uiStage.addActor(barbHP);
            barbHP.setPosition(350,0);
            scoreText.setFontScale(0.7f);
            uiStage.addActor(scoreText);
            scoreText.setPosition(360,5);
            //Stack HPScore = new Stack(barbHP,scoreText);

            for (int x = 200; x <= 600; x += 50)
            {
                new Rock(x, 600, mainStage);
            }

            for (int x = 1000; x <= 1400; x += 50)
            {
                new Rock(x, 600, mainStage);
            }

            for (int y = 800; y <= 1100; y += 50)
            {
                new Rock(800,y, mainStage);
            }

            for (int y = 100; y <= 400; y += 50)
            {
                new Rock(800,y, mainStage);
            }

            new Spawner(100,100,mainStage); //bottom left spawner
            new Spawner(100,900,mainStage); //top left spawner
            new Spawner(1200,100,mainStage);//bottom right spawner
            new Spawner(1200,900,mainStage);//top right spawner

            //uiTable.debug();
            uiTable.add().expandX();
            //uiTable.add(level9).expandX().expandY();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.add().expandX();
            uiTable.row();
            uiTable.add().expandX();
            //uiTable.add(HPScore).bottom().expandX().expand();
            uiTable.add().expandX();
            this.addDoors(floor.getRoomIndex(version));
        }

    }

    /**
     * This method determines how many doors a room should have
     * @param: roomIndex is the index in the arraylist of rooms (0-8)
     * 
     */
    public void addDoors(int roomIndex)
    {
        if (roomIndex == 0)
        {
            new Door(1525, 500, mainStage); //right door
            Barrier b1 = new Barrier(1495, 455, mainStage);
            b1.setRotation(90);

            new Door(800,0,mainStage); //bottom door
            new Barrier(750,-50,mainStage);
        }
        else if (roomIndex == 1)
        {
            new Door(1525, 500, mainStage); //right door
            Barrier b1 = new Barrier(1495, 455, mainStage);
            b1.setRotation(90);

            new Door(800,0,mainStage); //bottom door
            new Barrier(750,-50,mainStage);

            new Door(0,500,mainStage); //left door
            Barrier b2 = new Barrier(-50, 455, mainStage);
            b2.setRotation(270);
        }
        else if (roomIndex == 2)
        {
            new Door(800,0,mainStage); //bottom door
            new Barrier(750,-50,mainStage);

            new Door(0,500,mainStage); //left door
            Barrier b2 = new Barrier(-50, 455, mainStage);
            b2.setRotation(270);
        }
        else if (roomIndex == 3)
        {
            new Door(1525, 500, mainStage); //right door
            Barrier b1 = new Barrier(1495, 455, mainStage);
            b1.setRotation(90);

            new Door(800,0,mainStage); //bottom door
            new Barrier(750,-50,mainStage);

            new Door(800,1120,mainStage); //top door
            Barrier b3 = new Barrier(752, 1065, mainStage);
            b3.setRotation(180);
        }
        else if (roomIndex == 4)
        {
            new Door(1525, 500, mainStage); //right door
            Barrier b1 = new Barrier(1495, 455, mainStage);
            b1.setRotation(90);

            new Door(800,0,mainStage); //bottom door
            new Barrier(750,-50,mainStage);

            new Door(800,1120,mainStage); //top door
            Barrier b3 = new Barrier(752, 1065, mainStage);
            b3.setRotation(180);

            new Door(0,500,mainStage); //left door
            Barrier b2 = new Barrier(-50, 455, mainStage);
            b2.setRotation(270);
        }
        else if (roomIndex == 5)
        {
            new Door(800,0,mainStage); //bottom door
            new Barrier(750,-50,mainStage);

            new Door(800,1120,mainStage); //top door
            Barrier b3 = new Barrier(752, 1065, mainStage);
            b3.setRotation(180);

            new Door(0,500,mainStage); //left door
            Barrier b2 = new Barrier(-50, 455, mainStage);
            b2.setRotation(270);
        }
        else if (roomIndex == 6)
        {
            new Door(800,1120,mainStage); //top door
            Barrier b3 = new Barrier(752, 1065, mainStage);
            b3.setRotation(180);

            new Door(1525, 500, mainStage); //right door
            Barrier b1 = new Barrier(1495, 455, mainStage);
            b1.setRotation(90);
        }
        else if (roomIndex == 7)
        {
            new Door(800,1120,mainStage); //top door
            Barrier b3 = new Barrier(752, 1065, mainStage);
            b3.setRotation(180);

            new Door(1525, 500, mainStage); //right door
            Barrier b1 = new Barrier(1495, 455, mainStage);
            b1.setRotation(90);

            new Door(0,500,mainStage); //left door
            Barrier b2 = new Barrier(-50, 455, mainStage);
            b2.setRotation(270);
        }
        else if (roomIndex == 8)
        {
            new Door(0,500,mainStage); //left door
            Barrier b2 = new Barrier(-50, 455, mainStage);
            b2.setRotation(270);

            new Door(800,1120,mainStage); //top door
            Barrier b3 = new Barrier(752, 1065, mainStage);
            b3.setRotation(180);
        }
    }

    @Override
    public void update(float deltaTime)
    {
        barbiFrames += deltaTime;
        furyTimer += deltaTime;

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
            BaseGame.setActiveScreen( new StoneRoom(true,0) );
        }

        //back to main menu
        if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
        {
            bgMusic.stop();

            BaseGame.setActiveScreen( new MenuScreen(false,0) );  
        }
        
        if ( Gdx.input.isKeyJustPressed(Keys.X) && furyTimer > 30.0 )
        {
            furyTimer = 0.0;
            fury = true;
        }
        
        // update furyStatus
        if (furyTimer <= 6.0 )
        {
            furyStatus.setText("SMASH!!");
            furyStatus.setColor( Color.RED );
        }
        else if (furyTimer <= 30.0)
        {   
            furyStatus.setText("Building rage...");
            furyStatus.setColor( Color.YELLOW );
            fury = false;
        }
        else // fury is ready
        {
            furyStatus.setText("FURY READY!");
            furyStatus.setColor( Color.GREEN );
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
        if ((Gdx.input.isKeyJustPressed(Keys.SPACE) && !barb.isAttacking()) || 
        (Gdx.input.isKeyJustPressed(Keys.SPACE) && barb.isComboing() && barb.axeDone()))
        {
            barb.attack();
            swing1.play(0.5f);
        }

        //lets the barbarian combo
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && barb.getComboWindow() <= 2 
        && barb.isAttacking() && !barb.isIdle() && barb.getComboWindow() > 0 
        && !barb.isComboing() && barb.axeDone())
        {
            barb.combo();
            swing2.play(0.5f);
        }

        //control all barriers
        for (BaseActor barrier : BaseActor.getList(mainStage,"Barrier") )
        {
            barb.preventOverlap(barrier);

            if ( BaseActor.getList(mainStage, "Skeleton").size() == 0 
            && BaseActor.getList(mainStage, "Sage").size() == 0 
            && BaseActor.getList(mainStage, "Spawner").size() == 0)
            {
                Explosion boom = new Explosion(0,0,mainStage);
                boom.setPosition(barrier.getX(),barrier.getY());
                boom.setSize(barrier.getWidth(),barrier.getHeight());
                barrier.remove();
                clear.setVisible(true);
            }

            for (BaseActor skele : BaseActor.getList(mainStage,"Skeleton") )
            {
                skele.preventOverlap(barrier);
            }
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
                    Explosion boom = new Explosion(0,0,mainStage);
                    boom.setPosition(rock.getX(),rock.getY());
                    boom.setSize(rock.getWidth(),rock.getHeight());
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
                getHam.play(0.5f);
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
        for (BaseActor actor : BaseActor.getList(mainStage, "Spawner"))
        {
            Spawner spawner = (Spawner)actor;
            barb.preventOverlap(spawner);

            //System.out.println(barb.getPosition().dst( spawner.getX()+spawner.getWidth()/2,spawner.getY()+spawner.getWidth()/2 ));
            float dist = barb.getPosition().dst( spawner.getX()+spawner.getWidth()/2,spawner.getY()+spawner.getWidth()/2 );

            if ( (fury) && ( ( Gdx.input.isKeyJustPressed(Keys.SPACE) && ( dist < 500) && barb.isAttacking() && !barb.axeDone() ) 
            || ( Gdx.input.isKeyJustPressed(Keys.SPACE) && ( dist < 600) && barb.isComboing() && !barb.axeDone() ) ) )
            {
                spawner.hit();
            }
            else if( ( Gdx.input.isKeyJustPressed(Keys.SPACE) && (dist < 200) && barb.axe.isAttacking && !barb.axeDone() ) 
            || ( Gdx.input.isKeyJustPressed(Keys.SPACE) && (dist < 200) && barb.axe.isComboing && !barb.axeDone() ) )
            {
                spawner.hit(); //have it be hit and lose hp
            }

            if ( spawner.getSpawnTimer() > 8.0 && BaseActor.getList(mainStage, "Skeleton").size() < 40 )
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

                    new Skeleton(trueX, trueY, mainStage); // spawn new skeleton
                }

                spawner.resetTimer();
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
        for (BaseActor actor : BaseActor.getList(mainStage, "Sage"))
        {
            Sage sage = (Sage)actor;
            float angleToPlayer = barb.getPosition().sub( sage.getPosition() ).angle(); //angle from sage to barb
            sage.setRotation(angleToPlayer); //make sages face barb always
            float hitAngle = barb.getRotation();

            float dist = barb.getPosition().dst( sage.getX()+sage.getWidth()/2,sage.getY()+sage.getWidth()/2 );

            //while sage is invincible
            if( sage.getIFrames() < 0.5 )
            {
                sage.physics.setMotionAngle( hitAngle );
            }
            //if sage is too far
            else if ( dist > 500 )
            {
                //chase player
                sage.physics.setSpeed(100); 
                sage.physics.setMotionAngle( angleToPlayer );
            }
            else if ( (fury) && ( ( Gdx.input.isKeyJustPressed(Keys.SPACE) && ( dist < 285) && barb.isAttacking() && !barb.axeDone() && sage.iFrames >= 0.5 ) 
            || ( Gdx.input.isKeyJustPressed(Keys.SPACE) && ( dist < 285) && barb.isComboing() && !barb.axeDone() && sage.iFrames >= 0.5 ) ) )
            {
                sage.hit();
                skeleHit.play(0.5f);
            }
            //if sage has been attacked when not invincible
            else if( ( Gdx.input.isKeyJustPressed(Keys.SPACE) && (dist < 95) && barb.axe.isAttacking && !barb.axeDone() && sage.getIFrames() >= 0.5 ) 
            || ( Gdx.input.isKeyJustPressed(Keys.SPACE) && (dist < 95) && barb.axe.isComboing && !barb.axeDone() && sage.getIFrames() >= 0.5) )
            {
                sage.hit(); //have it be hit and lose hp
                skeleHit.play(0.5f);
            }
            //remain stationary
            else
            {
                sage.physics.setSpeed(0);
            }

            //might need more testing, problem *seems* to be solved
            //System.out.println("Attackspeed: "+((Sage)sage).attackSpeed);
            //spawns firebal 
            if ( sage.attackSpeed >= 1.98 && sage.getIFrames() >= 0.5) //spawn fireball in sync with attack speed if not in iFrames
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
        for (BaseActor fireball: BaseActor.getList(mainStage, "Sage_Fireball"))
        {
            //barb gets hit if are frames are over
            if( fireball.overlaps(barb) && barbiFrames > 1.0 && !fury )
            {
                fireball.remove();
                barbHP.loseHP();
                barbiFrames = 0.0; //make barb invulnerable
                oof.play();
            }

            //makes barb knockback
            if (barbiFrames < 0.1)
            {
                barb.physics.setSpeed(400);
                barb.physics.setMotionAngle( fireball.getRotation() );
            }
        }

        //control all skeletons
        for (BaseActor actor : BaseActor.getList(mainStage, "Skeleton"))
        {
            Skeleton skele = (Skeleton)actor;
            float angleToPlayer = barb.getPosition().sub( skele.getPosition() ).angle();
            float hitAngle = barb.getRotation();
            //System.out.println("barb's angle: "+(hitAngle-90));
            //System.out.println("angleToPlayer: "+angleToPlayer);
            //System.out.println("barb's angle: "+(hitAngle+90));
            //System.out.println(angleToPlayer);
            //System.out.println(skele.physics.getMotionAngle());

            float dist = barb.getPosition().dst( skele.getX()+skele.getWidth()/2,skele.getY()+skele.getWidth()/2 );

            //barb gets hit if are iframes are over
            if( ( (dist < 60) && !skele.isAttacking() && barbiFrames > 1.0 && barb.axeDone() && skele.stunFrames > 0.9 && !fury) )
            {

                skele.attack(); //skeleton attacks
                barbHP.loseHP(); //barb loses health
                barbiFrames = 0.0; //barb becomes invulnerable
                oof.play();
            }

            //makes barb knockback
            if (barbiFrames < 0.1)
            {
                barb.physics.setSpeed(400);
                barb.physics.setMotionAngle( skele.getRotation() );
            }

            //while skele is invincible
            if( skele.iFrames < 0.5 )
            {
                skele.physics.setMotionAngle( hitAngle );
            }
            else if ( (fury) && ( ( Gdx.input.isKeyJustPressed(Keys.SPACE) && ( dist < 285) && barb.isAttacking() && !barb.axeDone() && skele.iFrames >= 0.5 && (hitAngle-(360+90) < angleToPlayer) && (hitAngle+(360+90) > angleToPlayer)) 
            || ( Gdx.input.isKeyJustPressed(Keys.SPACE) && ( dist < 285) && barb.isComboing() && !barb.axeDone() && skele.iFrames >= 0.5) ) && (hitAngle-(360+90) < angleToPlayer) && (hitAngle+(360+90) > angleToPlayer))
            {
                skele.hit();
            }
            //if skeleton has been attacked when not invincible
            else if( (  ( dist < 95) && barb.isAttacking() && !barb.axeDone() && skele.iFrames >= 0.5 && (hitAngle-(360+90) < angleToPlayer) && (hitAngle+(360+90) > angleToPlayer) ) 
            || (  ( dist < 95) && barb.isComboing() && !barb.axeDone() && skele.iFrames >= 0.5) && (hitAngle-(360+90) < angleToPlayer) && (hitAngle+(360+90) > angleToPlayer))
            {
                skele.hit();
            }
            //move skele normally towards player if not stunned
            else if ( skele.stunFrames > 0.9 )
            {
                skele.setRotation( angleToPlayer );
                skele.physics.setMotionAngle( angleToPlayer );
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