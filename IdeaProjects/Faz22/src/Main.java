import java.io.*;

/**
 *  URL
 *  This class is for use all the classes
 *  <h2>Library  simulation  class</h2>
 *  @author Sara Tajernia
 *  @version 1.00
 *  @since 1398-4-21
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String str = new String();
        for (String s: args){
            str += s +" ";
        }

        System.out.println("CODE: " +str);

        try{
            Url url = new Url();
            url.Request(str);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
