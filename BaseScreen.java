import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class BaseScreen implements Screen
{
    protected Stage mainStage;
    protected Stage uiStage;
    protected Table uiTable;

    public BaseScreen(boolean newGame, int index)
    {
        mainStage = new Stage();
        uiStage = new Stage();
        
        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);

        initialize(newGame, index);
    }

    public abstract void initialize(boolean newGame, int index);

    public abstract void update(float deltaTime);

    // Gameloop:
    // (1) process input (discrete handled by listener; continuous in update)
    // (2) update game logic
    // (3) render the graphics
    public void render(float dt) 
    {
        // act methods
        uiStage.act(dt);
        mainStage.act(dt);

        // defined by user
        update(dt);

        // clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw the graphics
        mainStage.draw();
        uiStage.draw();
    }
    
    public void addDoors()
    {
        for (int n = 0; n<4; n++)
        {
            Door door = new Door(0,0,mainStage);
            //door.setAnimator(new Animator("assets/door.jpg"));
        }
        
        BaseActor.getList( mainStage, "Door" ).get(0).setPosition(0,500); //left door
        BaseActor.getList( mainStage, "Door" ).get(1).setPosition(525,1120); //top door?
        BaseActor.getList( mainStage, "Door" ).get(2).setPosition(1525,500); //right door
        BaseActor.getList( mainStage, "Door" ).get(3).setPosition(525,0); //bottom door
    }

    // methods required by Screen interface
    public void resize(int width, int height) {  }

    public void pause()   {  }

    public void resume()  {  }

    public void dispose() {  }

    public void show()    {  }

    public void hide()    {  }
}