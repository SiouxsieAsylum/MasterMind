import java.util.Random;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import java.lang.Math;
import java.lang.StringBuilder;

class Code {
  private static String[] PEGS;
  protected String secretCodeString;


  public static void main(String[] args) {
     // setPegs();
  }

  public Code(String input){
    this.secretCodeString = input;
  }

  public Code(){
    randomize();
  }

  public static void setPegs(){
    PEGS = new String[]{"c","r","p","o","g"};
}

  public Code randomize(){
    setPegs();

    secretCodeString = new String();

    Random rand = new Random();

    for (int i = 0; i < 4; i++){
      int randIndex = rand.nextInt(6);
      secretCodeString = secretCodeString.concat(PEGS[randIndex]);
    }

      Code secretCode = parse(secretCodeString);
      return secretCode;
  }

  public static Code parse(String input) {
    //setPegs();

    String[] letters = input.split("");
    StringBuilder sb = new StringBuilder();

    for (String letter : letters) {
      if (Arrays.asList(PEGS).contains(letter)) {
        sb.append(letter);
      } else {
        System.out.println(letter);
        throw new RuntimeException();

      }
    }

    String stringToParse = sb.toString();
    Code parsedCode = new Code(stringToParse);
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
