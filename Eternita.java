
public class Eternita extends BaseGame
{

    public void create() 
    {     
        super.create();
        
        //setActiveScreen( new TestRoom(false,0) );
        setActiveScreen( new MenuScreen(false,0) );
        //setActiveScreen( new StoneRoom(true,0) );
    }
}