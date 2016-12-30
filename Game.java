import java.util.Scanner;

class Game {

  protected static Code winningCode;

  public static void main(String[] args){

  }

  public Game(){
    winningCode = new Code();
  }

  protected static Code getGuess() {

    Scanner userInput = new Scanner(System.in);

    int count = 0;
    int maxTries = 5;
    while(true){
      try {
        String codeToParse = userInput.next();
        Code guess = Code.parse(codeToParse);
        return guess;

      } catch(RuntimeException notACode) {
        System.out.println("That's not a valid peg. You have " + (maxTries - count) + " tries left.");
        if (++count == maxTries) throw notACode;
      }
    }


  }

  protected static void displayMatches(Code guess){

    int nearMatches = winningCode.countNearMatches(guess);
    int exactMatches = winningCode.countExactMatches(guess);

    System.out.println("You have " + exactMatches + " exact matches and " + nearMatches + " near matches.");
  }

  protected static void play(){
    int turnCount = 0;
    int maxTurns = 10;

    System.out.println("Greetings. Pick your code of four from Y,O,G,P,C,R.");

    while(true){
      System.out.println(winningCode.secretCodeString);
      Code guess = getGuess();
      System.out.println(winningCode.secretCodeString);
      displayMatches(guess);

      if (guess == winningCode) {
        System.out.print("You win!!");
        break;
      } else if (++turnCount == maxTurns) {
        System.out.print("You lose!!");
        break;
      }
    }
  }
}
