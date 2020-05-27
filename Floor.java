import java.util.Random;
import java.util.ArrayList;
/**
 * Conceptual class for floor
 * Does all calculations in where the player is
 * and what floor the game should load
 */
public class Floor
{    
    Random rand;
    static ArrayList<Integer> allRooms;

    /**
     * Constructor for objects of class Floor
     * @param: freshFloor is true if and only if a new game has been created
     * in all other cases freshFloor is false
     * @param:
     */
    public Floor(boolean freshFloor, ArrayList<Integer> oldFloor)
    {
        rand = new Random();
        allRooms = new ArrayList<Integer>(9);
        
        //if making a new floor
        if (freshFloor)
        {
            //while there are not 9 elements in the arraylist
            while (allRooms.size() < 9)
            {
                //generate a random number between 1 and 9
                int n = rand.nextInt(9);
                n += 1;
                //number must be unique to be added
                if(!allRooms.contains(n))
                {
                    allRooms.add(n);
                }
            }
        }
        //take in old floor if not making a new one
        else
        {
            allRooms = oldFloor;
        }
        
    }
    
    /**
     * Returns a copy of this floor
     */
    public ArrayList<Integer> getFloor()
    {
        ArrayList<Integer> copyOfFloor = this.allRooms;
        return copyOfFloor;
    }
    
    /**
     * returns an element (an integer) from the floor arraylist
     * @param: roomIndex (0-8) aka position in arraylist allRooms
     * @return: an int, the number stored in position roomIndex
     */
    public int getRoomVersion(int roomIndex)
    {
        return allRooms.get(roomIndex);
    }
    
    /**
     * returns the index of an element (roomVersion) from the arraylist allRooms
     */
    public int getRoomIndex(int roomVersion)
    {
        return allRooms.indexOf(roomVersion);
    }

        /**
     * This method determines which room the game should load when the player selects a door
     * @param: x the x position of the player on the floor
     * @param: y the y position of the player on the floor
     * @param: direction the direction the player wants to move
     * @return: a int list of size 2 that contains the coordinates of the room being moved to
     */
    public int determineNextRoomIndex(int currentRoomIndex, String direction)
    {
        int nextRoom = currentRoomIndex;
        
        if (direction.equals("LEFT"))
        {
            if(currentRoomIndex == 0 || currentRoomIndex == 3 || currentRoomIndex == 6)
            {
                nextRoom = -1;
            }
            else
            {
                nextRoom = currentRoomIndex-1;
            }
        }
        else if (direction.equals("RIGHT"))
        {
            if(currentRoomIndex == 2 || currentRoomIndex == 5 || currentRoomIndex == 8)
            {
                nextRoom = -1;
            }
            else
            {
                nextRoom = currentRoomIndex+1;
            }
        }
        else if (direction.equals("UP"))
        {
            if(currentRoomIndex == 0 || currentRoomIndex == 1 || currentRoomIndex == 2)
            {
                nextRoom = -1;
            }
            else
            {
                nextRoom = currentRoomIndex-3;
            }
        }
        else if (direction.equals("DOWN"))
        {
            if(currentRoomIndex == 6 || currentRoomIndex == 7 || currentRoomIndex == 8)
            {
                nextRoom = -1;
            }
            else
            {
                nextRoom = currentRoomIndex+3;
            }
        }
        
        return nextRoom;
    }
}
