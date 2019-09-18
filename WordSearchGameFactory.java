
/**
 * Provides a factory method for creating word search games. 
 *
 * @author Megan Barone (mab0126@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 03/20/2019
 */
public class WordSearchGameFactory {

   /**
    * Returns an instance of a class that implements the WordSearchGame
    * interface.
    */
   public static WordSearchGameObject createGame() {
      // You must return an instance of your solution class here instead of null.
      return new WordSearchGameObject();
   }

}
