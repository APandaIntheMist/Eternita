import java.util.ArrayList;
/**
 * The purpose of this class is to remember the layout of
 * the randomly generated floor that was created in the
 * Floor class
 */
public class helperClass
{
    static ArrayList<Integer> theList;
    public static void main(ArrayList<Integer> list)
    {
        theList = list;
    }
    
    public static ArrayList getList()
    {
        ArrayList<Integer> copy = theList;
        return copy;
    }
}
