import java.util.Random;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Set;

import java.lang.Math;
import java.lang.StringBuilder;

class Code {
  private static HashMap<String,String> PEGS;
  private static ArrayList<String> pegStrings;
  protected String secretCodeString;


  public static void main(String[] args) {

  }

  public Code(String input){
    this.secretCodeString = input;
  }

  public Code(){
    randomize();
  }


  //literally just creates the peghash
  public static void setPegs(){
    PEGS = new HashMap<String,String>();

    PEGS.put("C","c");
    PEGS.put("Y","y");
    PEGS.put("R","r");
    PEGS.put("P","p");
    PEGS.put("O","o");
    PEGS.put("G","g");
  }

  //turns the pegs ito something randomize can use
 public static ArrayList<String> makePegArray(){
    setPegs();

    pegStrings = new ArrayList<String>();

    Collection<String> pegValues = PEGS.values();
    Object[] pegObjects = pegValues.toArray();

      for (int i = 0; i < pegObjects.length; i++){
        pegStrings.add(pegObjects[i].toString());
      }

    return pegStrings;
  }

  // sets Class Variable secretCode to a four letter combination
  public Code randomize(){
    secretCodeString = new String();

    Random rand = new Random();
    int randIndex = rand.nextInt(makePegArray().size());

    for (int i = 0; i < 4; i++){
      randIndex = rand.nextInt(makePegArray().size());
      secretCodeString = secretCodeString.concat(makePegArray().get(randIndex));
    }

      Code secretCode = parse(secretCodeString);
      return secretCode;
  }

  public static Code parse(String input) {
    setPegs();
    makePegArray();

    String[] letters = input.split("");
    StringBuilder sb = new StringBuilder();

    for (String letter : letters) {
      if (pegStrings.contains(letter)) {
        sb.append(letter);
      } else {
        System.out.println(letter);
        throw new RuntimeException();

      }
    }

    String pegListString = sb.toString();
    Code parsedCode = new Code(pegListString);
    //System.out.println(parsedCode);
    return parsedCode;

  }

  public int countExactMatches(Code guess){
    String guessString = guess.secretCodeString;

    int exactMatches = 0;

    String[] guessArray = guessString.split("");

    String[] winningCodeArray = (this.secretCodeString).split("");

    for(int i = 0; i < 4; i++){

      if(guessArray[i] == winningCodeArray[i]){
        exactMatches++;
      }
    }
    return exactMatches;
  }

  public int countNearMatches(Code guess) {

    String guessString= guess.secretCodeString;

    HashMap<String,Integer> guessCount = new HashMap<String,Integer>();
    HashMap<String,Integer> secretCodeCount = new HashMap<String,Integer>();

    Set<String> codeKeys = guessCount.keySet();

    int matches = 0;
    int keys = guessCount.keySet().size();


    String[] keyArray = new String[keys];



    for(int i = 0; i < guessString.length(); i++) {
      //removes character from string
      String codeCharacter = String.valueOf(guessString.charAt(i));
      String guessShort = guessString.replace(codeCharacter,"");

      //counts instances of said character
      int count = guessString.length() - guessShort.length();

      guessCount.put(codeCharacter, count);
    }

    for(int i = 0; i < secretCodeString.length(); i++) {
      //removes character from string
      String winningString = this.secretCodeString;

      String winningCodeCharacter = String.valueOf(winningString.charAt(i));
      String winningCodeShort = guessString.replace(winningCodeCharacter,"");

      //counts instances of said character
      int count = winningString.length() - winningCodeShort.length();

      secretCodeCount.put(winningCodeCharacter, count);
    }

    for (int i = 0; i < keys; i++) {
      codeKeys.toArray(keyArray);
      String keyString = keyArray[i];

      if (secretCodeCount.containsKey(keyString)) {
        matches += Math.min(secretCodeCount.get(keyString), guessCount.get(keyString));
      }
    }

    int nearMatches = matches - countExactMatches(guess);

    return nearMatches;
  }
}
