
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;
import java.util.HashSet;

public class ClosestWords {
  LinkedList<String> closestWords = null;
  static int[][] matrix = new int[64][64];
  static {
    for (int i = 0; i < 64; i++) {
      matrix[0][i] = i;
      matrix[i][0] = i;
    }
  }
  int closestDistance = 256;
  String lastWord = "";  


  int partDist(String w1, String w2, int w1len, int w2len, int min_dist) {
    int cols =Math.max(SameCount(w2), 1);
    int min_col = matrix[0][cols];
    for (int col = cols; w2len>=col; col++){
      for (int row = 1; w1len>=row; row++){
          matrix[row][col] = matrix[row-1][col-1] + (w1.charAt(row-1) == w2.charAt(col-1)? 0 : 1);
          if (matrix[row][col] > matrix[row][col-1] + 1){
            matrix[row][col] = matrix[row][col-1] + 1;
          }
          if (matrix[row][col] > matrix[row-1][col] + 1){
            matrix[row][col] = matrix[row-1][col] + 1;
          }
        
        min_col = Math.min(matrix[row][col], min_col); 
      }
      if (min_col > min_dist) {
        return 1024; // Ugly
      }
    }
    lastWord = w2;
    return matrix[w1len][w2len];

  }

  int SameCount(String word){

    int len = Math.min(word.length(), lastWord.length());
    int count = 0;
    for ( ; count < len; count++) {
      if (word.charAt(count) != lastWord.charAt(count)) {
        return count;
       }
    }
    return count;
  }

  int Distance(String w1, String w2, int min_dist) {
    return partDist(w1, w2, w1.length(), w2.length(), min_dist);
  }

  public ClosestWords(String w, List<String> wordList) {
    for (String s : wordList) {
      int lenDiff = Math.abs( w.length() - s.length() );
      if (lenDiff>closestDistance){continue;}
      int dist = Distance(w, s, closestDistance);
      if (dist < closestDistance || closestDistance == 256) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      }
      else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

//  HashSet<String> findClosest(String word){
//    //delete one letter
//    StringBuilder nw = new StringBuilder(word);
//    for (int i = word.length()-1; i>=0; i--){
//      nw.deleteCharAt(i);
//      returnSet = nw.toString(); 
//    }
//    return null;
//  }

  List<String> getClosestWords() {
    return closestWords;
  }
