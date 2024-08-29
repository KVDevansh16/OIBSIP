
import java.util.Random;
import java.util.Scanner;


public class NumberGuess {
  private static final int MIN_RANGE=1;
  private static final int MAX_RANGE=100;
  private static final int MAX_ATTEMPTS=6;
  private static final int MAX_ROUNDS=5;
  
  public static void main(String[] args) {
    Random random=new Random();
    Scanner sc=new Scanner(System.in);
    int totalScore=0;

    System.out.println("*****NUMBER GUESSING GAME*****");
    System.out.println("Total Number of Rounds: 5");
    System.out.println("Attempts given to guess Number in each Round: 6\n");
    try{
     for(int i=1;i<=MAX_ROUNDS;i++){
        int number=random.nextInt(MAX_RANGE)+MIN_RANGE;
        int attempts=0;

        System.out.printf("Round %d: Guess the number between %d and %d in %d attempts.\n",i,MIN_RANGE,MAX_RANGE,MAX_ATTEMPTS);
        while(attempts<MAX_ATTEMPTS){
            System.out.print("Enter your guess: ");
            int guess_number=sc.nextInt();
            attempts+=1;

            if(guess_number==number){
                int score=MAX_ATTEMPTS-attempts;
                totalScore=totalScore+score;
                System.out.printf("Well Done!! Number Guessed Successfully.\nAttempts Left= %d. Round Score= %d\n",attempts,score);
                break;
            }
            else if(guess_number<number){
                System.out.printf("The number is greater than %d.\n Attempts Left: %d.\n",guess_number,MAX_ATTEMPTS-attempts);
                
            }
            else if(guess_number>number){
                System.out.printf("The number is less than %d.\nAttempts Left: %d.\n",guess_number,MAX_ATTEMPTS-attempts);
            }
        }

        if(attempts==MAX_ATTEMPTS){
            System.out.printf("\nRound: %d\n",i);
            System.out.println("Attempts= 0");
            System.out.printf("The Random Number is: %d\n\n",number);
        }
     }
    }catch(Exception e){
        System.out.println(e);
    }
     System.out.printf("Game Over.Total Score: %d\n",totalScore);
     sc.close();
  }
}
