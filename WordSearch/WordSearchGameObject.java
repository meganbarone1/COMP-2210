import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Game implements WordSearchGame interface.
 *
 * @author Megan Barone
 * @version 03/20/2019
 */
public class WordSearchGameObject implements WordSearchGame {


   private String[][] gameBoard;
   private Trie dictionary;
   private boolean isLoaded = false;
   public int total = 0;

   @Override
   public void loadLexicon(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      try {
         URL path = WordSearchGameObject.class.getResource(fileName);
         File f = new File(path.getFile());
         BufferedReader read = new BufferedReader(new FileReader(f));
         String input;
         input = read.readLine();
         while (input != null) {
            if (dictionary == null) {
               dictionary = new Trie();
            }
            if (input.contains(" ")) {
               input = input.substring(0, input.indexOf(" "));
            }
            input = input.replace(" ", "");
            dictionary.add(input.toUpperCase());
            total++;
            input = read.readLine();
         }
      } catch (Exception e) {
         throw new IllegalArgumentException();
      }
      isLoaded = true;
   }

   @Override
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      double number = Math.sqrt(letterArray.length);
      if (number != Math.floor(number)) {
         throw new IllegalArgumentException();
      }
      int m = (int) Math.floor(number);
      gameBoard = new String[m][m];
      int capacity = 0;
      int i = 0;
      while (i < m) {
         int j = 0;
         while (j < m) {
            gameBoard[i][j] = letterArray[capacity++];
            j++;
         }
         i++;
      }
   }

   @Override
   public String getBoard() {
      String board = "";
      int max1 = gameBoard.length + 2;
      int max2 = gameBoard.length * 2 + 3;
      int i = 0;
      int k = -1;
      while (i < max1) {
         int j = 0;
         int l = -1;
         while (j < max2) {
            if (i == 0 || i == max1 - 1) {
               if (j == 0 || j == max2 - 1) {
                  if (i > 0 && i != max1 - 1) {
                     board += "|";
                  }
                  else {
                     board += " ";
                  }
               }
               if (j > 0 && j < max2 - 1) {
                  board += "-";
               }
            }
            if (i > 0 && (j == 0 || j == max2 - 1)) {
               board += "|";
            }
            if ((j + 1) % 2 == 0) {
               board += " ";
               l += (l + 1 != gameBoard.length) ? 1 : 2 - gameBoard.length;
               if (l == 0) {
                  k++;
               }
            }
            else {
               board += gameBoard[k][l];
            }
            j++;
         }
         board += "\n";
         i++;
      }
      return board;
   }

   @Override
   public SortedSet<String> getAllValidWords(int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (!isLoaded) {
         throw new IllegalStateException();
      }
      SortedSet<String> validWords = new TreeSet<>();
      int i = 0;
      while (i < gameBoard.length) {
         int j = 0;
         while (j < gameBoard.length) {
            Searcher searcher = new Searcher(dictionary.getNode(gameBoard[i][j]), gameBoard, new Tuple(i, j), minimumWordLength);
            Stack<Node<String>> nodeStack = new Stack<>();
            nodeStack.push(searcher.getParent());
            searcher.search(searcher.getSource(), nodeStack, "", new Stack<>());
            validWords.addAll(searcher.getWords());
            j++;
         }
         i++;
      }
      return validWords;
   }

   @Override
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (!isLoaded) {
         throw new IllegalStateException();
      }
      int score = 0;
      for (String word : words) {
         if (minimumWordLength < word.length()) {
            score += word.length() - minimumWordLength;
         }
      }
      return score;
   }

   @Override
   public boolean isValidWord(String wordToCheck) {
      if (dictionary.contains(wordToCheck)) {
         return true;
      }
      return false;
   }

   @Override
   public boolean isValidPrefix(String prefixToCheck) {
      if (dictionary.hasPrefix(prefixToCheck)) {
         return true;
      }
      return false;
   }

   @Override
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (! isLoaded) {
         throw new IllegalStateException();
      }
      Searcher searcher = new Searcher(dictionary.getParentNode(wordToCheck), gameBoard, wordToCheck);
      Stack<Tuple> spots = searcher.getResult();
      List<Integer> result = new ArrayList<>();
      for ( Tuple tuple : spots) {
         result.add(tuple.getA() * gameBoard.length + tuple.getB());
      }
      return result;
   }

   private class Searcher {
      private String desired;
      private String[][] gameBoard;
      private SortedSet<String> words;
      private Stack<Node<String>> web;
      private Stack<Tuple> answer;
      private final Node<String> parent;
      private final Tuple source;
      private final int minLength;

      Searcher(Node<String> parent, String[][] board, String desired) {
         words = new TreeSet<>();
         answer = new Stack<>();
         this.parent = parent;
         this.gameBoard = board;
         this.desired = desired;
         this.minLength = 0;
         this.web = new Stack<>();
         this.web.add(parent);
         Tuple firstSpot = new Tuple(- 1, - 1);
         for (Tuple location : getLocations(parent)) {
            if (location != null) {
               firstSpot = location;
            }
            this.search(location, web, "", new Stack<>());
         }
         this.source = firstSpot;
      }

      Searcher(Node<String> parent, String[][] gameBoard, Tuple source, int minLength) {
         words = new TreeSet<>();
         this.parent = parent;
         this.gameBoard = gameBoard;
         this.source = source;
         this.minLength = minLength;
      }

      void search(Tuple position, Stack<Node<String>> nodeStack, String word, Stack<Tuple> visited) {
         int a = position.getA();
         int b = position.getB();
         if (a < 0 || b < 0) {
            throw new IllegalArgumentException();
         }
         word += gameBoard[a][b];
         visited.push(new Tuple(a, b));
         if (answer != null && ! answer.isEmpty()) {
            return;
         }
         if (desired != null && word.equals(desired)) {
            if (answer == null) {
               answer = new Stack<>();
            }
            answer.addAll(visited);
         }
         int[][] pathMatrix = {{0, 1}, {0, - 1}, {1, 0}, {- 1, 0}, {1, 1}, {- 1, - 1}, {- 1, 1}, {1, - 1}};
         addWord(nodeStack.peek(), word);
         for (int[] path : pathMatrix) {
            while (visited.size() > word.length()) {
               visited.pop();
            }
            while (nodeStack.size() > word.length()) {
               nodeStack.pop();
            }
            int da = path[0];
            int db = path[1];
            if (isValidMove(da + a, db + b, gameBoard.length, gameBoard.length)
                    && dictionary.hasPrefix(word + gameBoard[da + a][db + b])
                    && ! visited.contains(new Tuple(da + a, db + b))) {
               Node<String> n = dictionary.getNode(word + gameBoard[da + a][db + b]);
               nodeStack.push(n);
               search(new Tuple(a + da, b + db), nodeStack, word, visited);
            }
         }
      }

      private Vector<Tuple> getLocations(Node<String> parent) {
         Vector<Tuple> spots = new Vector<>();
         if (parent == null) {
            return spots;
         }
         int i = 0;
         while (i < gameBoard.length) {
            int j = 0;
            while (j < gameBoard.length) {
               if (gameBoard[i][j].equals(parent.getValue())) {
                  spots.add(new Tuple(i, j));
               }
               i++;
               j++;
            }
         }
         return spots;
      }

      boolean isValidMove(int a, int b, int maxA, int maxB) {
         boolean lessThanMax = a < maxA && b < maxB;
         boolean greaterThanMin = a >= 0 && b >= 0;

         return lessThanMax && greaterThanMin;
      }

      Node<String> getParent() {
         return parent;
      }

      Tuple getSource() {
         return source;
      }

      private void addWord(Node<String> node, String word) {
         if (node != null && node.isAtEdge() && word.length() >= minLength) {
            words.add(word);
         }
      }

      private SortedSet<String> getWords() {
         return words;
      }

      Stack<Tuple> getResult() {
         return answer;
      }
   }

   private class Tuple {
      private int a;
      private int b;

      Tuple(int a, int b) {
         this.a = a;
         this.b = b;
      }

      int getA() {
         return a;
      }

      int getB() {
         return b;
      }

      @Override
      public boolean equals(Object other) {
         return ! (other == null || ! (other instanceof Tuple)) && (this.a == ((Tuple) other).getA() && this.b == ((Tuple) other).getB());
      }
   }

   private class Trie {
      private Vector<Node<String>> network;
      private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

      public Trie() {
         network = new Vector<>();
         for (String s : alphabet.split("")) {
            network.add(new Node<>(s));
         }
      }

      boolean hasPrefix(String prefix) {
         prefix = prefix.toUpperCase();
         Node<String> parent = getParentNode(prefix);
         if (hasPrefix(parent, new ArrayList<>(Arrays.asList(prefix.split(""))))) {
            return true;
         }
         return false;
      }

      private boolean hasPrefix(Node<String> parent, ArrayList<String> remainingLetters) {
         if (parent == null) {
            return false;
         }
         remainingLetters.remove(0);
         if (remainingLetters.size() == 0 || hasPrefix(parent.getChild(remainingLetters.get(0)), remainingLetters)) {
            return true;
         }
         return false;
      }

      boolean contains(String word) {
         word = word.toUpperCase();
         Node<String> parent = getParentNode(word);
         if (contains(parent, new ArrayList<>(Arrays.asList(word.split(""))))) {
            return true;
         }
         return false;
      }

      private boolean contains(Node<String> parent, ArrayList<String> remainingLetters) {
         if (parent == null) {
            return false;
         }
         remainingLetters.remove(0);
         if (remainingLetters.size() == 0) {
            return parent.isAtEdge();
         }
         if (contains(parent.getChild(remainingLetters.get(0)), remainingLetters)) {
            return true;
         }
         return false;
      }

      boolean add(String word) {
         word = word.toUpperCase();
         Node<String> parent = getParentNode(word);
         if (add(parent, new ArrayList<>(Arrays.asList(word.split(""))))) {
            return true;
         }
         return false;
      }

      private boolean add(Node<String> parent, ArrayList<String> remainingLetters) {
         remainingLetters.remove(0);
         if (remainingLetters.size() == 0) {
            parent.setAsEdge();
            return true;
         }
         if (add(parent.addChild(remainingLetters.get(0)), remainingLetters)) {
            return true;
         }
         return false;
      }

      Node<String> getParentNode(String word) {
         if (word == null) {
            throw new IllegalArgumentException();
         }
         return network.get(alphabet.indexOf(word.charAt(0) + ""));
      }

      Node<String> getNode(String word) {
         if (word == null) {
            throw new IllegalArgumentException();
         }
         Node<String> node = getParentNode(word);
         word = word.substring(1, word.length());
         while (word.length() > 0) {
            if (node.getChild(word.charAt(0) + "") == null) {
               return node;
            }
            node = node.getChild(word.charAt(0) + "");
            word = word.substring(1, word.length());
         }
         return node;
      }
   }

   private class Node<T> {
      private T value;
      private Node<T> parent;
      private HashMap<T, Node<T>> children;
      private boolean isAtEdge;

      Node(T value) {
         this.value = value;
         this.children = new HashMap<>();
         isAtEdge = false;
      }

      Node(T value, Node<T> parent) {
         this.value = value;
         this.parent = parent;
         this.children = new HashMap<>();
         isAtEdge = false;
      }

      Node<T> addChild(T value) {
         Node<T> child = new Node<>(value, this);
         children.putIfAbsent(value, child);
         return children.get(value);
      }

      Node<T> getChild(T value) {
         return children.get(value);
      }

      Node<T> setAsEdge() {
         isAtEdge = true;
         return this;
      }

      boolean isAtEdge() {
         return isAtEdge;
      }

      @SuppressWarnings("unchecked")
      @Override
      public boolean equals(Object object) {

         if (object != null && object instanceof Node) {
            Node<T> other = (Node<T>) object;
            return value == other.value && parent == other.parent;
         }
         return false;
      }

      public T getValue() {
         return value;
      }
   }
}

