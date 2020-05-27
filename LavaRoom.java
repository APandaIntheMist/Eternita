import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import java.util.Random;
import java.util.ArrayList;

/**
 * TO DO: add enemies and enemy spawners
 * levels more interesting
 * doors not blue squares
 */

public class LavaRoom extends BaseScreen
{
    BaseActor BG;

    Barbarian barb;
    
    Floor floor;
    int currentRoom;
    int version;

    public LavaRoom(boolean newGame, int index)
    {
        super(newGame, index);       
    }

    public void initialize(boolean newGame, int index)
    {
        //super.addDoors();
        BG = new BaseActor(0,0, mainStage);
        BG.animator = new Animator("assets/lavaFloor.png");
        BG.setSize(1600,1200);

        barb = new Barbarian(100,300, mainStage);
        
        //Gargant g1 = new Gargant(1000,300,mainStage);
        //Gargant g2 = new Gargant(1000,400,mainStage);
        //Gargant g3 = new Gargant(1000,500,mainStage);
        
        Spawner s1 = new Spawner(300,200,mainStage);

        // for (int n = 0; n<4; n++)
        // {
            // Door door = new Door(0,0,mainStage);
            // //door.setAnimator(new Animator("assets/door.jpg"));
        // }

        //(0,500); //left door
        //(525,1120); //top door
        //(1525,500); //right door
        //(525,0); //bottom door
        
        
        //determines what version of room should be made
        currentRoom = index;
        if (newGame)
        {
            floor = new Floor(newGame, new ArrayList<Integer>());
        }
        else
        {
            //floor.getFloor();
            floor = new Floor(newGame, floor.getFloor());
        }
        version = floor.getRoomVersion(currentRoom);
        
        if (version == 1)
        {
            Door door1 = new Door(1525, 500, mainStage); //right door
            Door door2 = new Door(525,0,mainStage); //bottom door
            
            Label level1 = new Label("Room 1", BaseGame.labelStyle);
            level1.setColor( Color.RED );
            uiTable.add(level1);
            uiTable.row();
            uiTable.add().pad(64);
            uiTable.row();
            uiTable.add().pad(64);
            uiTable.row();
            uiTable.add().pad(64);
        }
        else if (version == 2)
        {
            Door door1 = new Door(1525, 500, mainStage); //right door
            Door door2 = new Door(525,0,mainStage); //bottom door
            Door door3 = new Door(0,500,mainStage); //left door
            
            Label level2 = new Label("Room 2", BaseGame.labelStyle);
            level2.setColor( Color.RED );
            uiTable.add(level2);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
        }
        else if (version == 3)
        {
            Door door2 = new Door(525,0,mainStage); //bottom door
            Door door3 = new Door(0,500,mainStage); //left door
            
            Label level3 = new Label("Room 3", BaseGame.labelStyle);
            level3.setColor( Color.RED );
            uiTable.add(level3);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
        }
        else if (version == 4)
        {
            Door door1 = new Door(1525, 500, mainStage); //right door
            Door door2 = new Door(525,0,mainStage); //bottom door
            Door door3 = new Door(525,1120,mainStage); //top door
            
            Label level4 = new Label("Room 4", BaseGame.labelStyle);
            level4.setColor( Color.RED );
            uiTable.add(level4);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
        }
        else if (version == 5)
        {
            Door door1 = new Door(1525, 500, mainStage); //right door
            Door door2 = new Door(525,0,mainStage); //bottom door
            Door door3 = new Door(525,1120,mainStage); //top door
            Door door4 = new Door(0,500,mainStage); //left door
            
            Label level5 = new Label("Room 5", BaseGame.labelStyle);
            level5.setColor( Color.RED );
            uiTable.add(level5);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
        }
        else if (version == 6)
        {
            Door door2 = new Door(525,0,mainStage); //bottom door
            Door door3 = new Door(525,1120,mainStage); //top door
            Door door4 = new Door(0,500,mainStage); //left door
            
            Label level6 = new Label("Room 6", BaseGame.labelStyle);
            level6.setColor( Color.RED );
            uiTable.add(level6);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
        }
        else if (version == 7)
        {
            Door door3 = new Door(525,1120,mainStage); //top door
            Door door1 = new Door(1525, 500, mainStage); //right door
            
            Label level7 = new Label("Room 7", BaseGame.labelStyle);
            level7.setColor( Color.RED );
            uiTable.add(level7);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
        }
        else if (version == 8)
        {
            Door door3 = new Door(525,1120,mainStage); //top door
            Door door1 = new Door(1525, 500, mainStage); //right door
            Door door4 = new Door(0,500,mainStage); //left door
            
            Label level8 = new Label("Room 8", BaseGame.labelStyle);
            level8.setColor( Color.RED );
            uiTable.add(level8);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
        }
        else if (version == 9)
        {
            Door door4 = new Door(0,500,mainStage); //left door
            Door door3 = new Door(525,1120,mainStage); //top door
            
            Label level9 = new Label("Room 9", BaseGame.labelStyle);
            level9.setColor( Color.RED );
            uiTable.add(level9);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
            uiTable.row();
            uiTable.add().pad(32);
        }

    }

    //possibly make axe seperate entity
    //barb.addActor(axe);
    //axe.moveBy( barb.getWidth()/2, barb.getHeight()/2);
    //axe.moveBy( -axe.getWidth()/2, -axe.getHeight()/2);


    @Override
    public void update(float deltaTime)
    {
        //for testing position of player
        //System.out.println(barb.getX());
        
        
        // move barb based on keys pressed
        if ( Gdx.input.isKeyPressed(Keys.RIGHT) )
            barb.physics.accelerateAtAngle(0);

        if ( Gdx.input.isKeyPressed(Keys.LEFT) )
            barb.physics.accelerateAtAngle(180);

        if ( Gdx.input.isKeyPressed(Keys.UP) )
            barb.physics.accelerateAtAngle(90);

        if ( Gdx.input.isKeyPressed(Keys.DOWN) )
            barb.physics.accelerateAtAngle(270);
            
        if (Gdx.input.isKeyPressed(Keys.SPACE) && !barb.isAttacking() )
            barb.attack();

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
        
        for (BaseActor gargant : BaseActor.getList(mainStage, "Gargant"))
        {
            float angleToPlayer = barb.getPosition().sub( gargant.getPosition() ).angle();
            gargant.setRotation( angleToPlayer );
            gargant.physics.accelerateAtAngle( angleToPlayer );
            
            for (BaseActor otherGargant : BaseActor.getList(mainStage,"Gargant"))
            {
                if (gargant != otherGargant)
                    gargant.preventOverlap(otherGargant);
            }
        }
        
        for (BaseActor spawner : BaseActor.getList(mainStage, "Spawner"))
        {
            barb.preventOverlap(spawner);
            
            for (BaseActor gargant : BaseActor.getList(mainStage, "Gargant"))
            {
                gargant.preventOverlap(spawner);
            }
        }
    }
}