import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Survey {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            System.out.println("Welcome to our survey. you may enter\"quit\" at any time to cancel the survey.");
            int age = getAge();
            String[][] states = readStateFiles();
            String state = getState(states);
            int ZIPCode = getZipCode();
            System.out.printf("\nAge:\t\t%d\n", age);
            System.out.printf("Address:\t%s %s\n\n", ZIPCode, state);
//
            System.out.println("Your survey is complete!");

        } catch (CancelledSurveyException e) {
            System.out.println(e.getMessage());

        }
        finally {
            System.out.println("Thank you for your time.");
            scanner.close();
        }
    }

    private static int getZipCode() throws CancelledSurveyException {
        String str;
        do{
            System.out.println("Please enter your zip code: ");
            str = scanner.next();
        } while(!isZipCode(str));
        return Integer.parseInt(str);
    }

    private static String getState(String[][] states) throws CancelledSurveyException {
        String str;
        do{
            System.out.print("Please enter the 2-letter state abbreviation: ");
            str = scanner.next();
            if (str.compareToIgnoreCase("quit") == 0){
                throw new CancelledSurveyException();
            }
            for (String[] state : states) {
                if (state[0].compareToIgnoreCase(str) == 0){
                    System.out.println(state[1]);
                    return state[1];
                }
            }
            System.out.println("The state abbreviation was not found.");
        }while(true);
    }

    private static String[][] readStateFiles() {
        try {
            RandomAccessFile file = new RandomAccessFile("states.bin", "r");
            String[][] states = new String[50][2];
            for (int i = 0; i < 50; i++) {
                states[i][0] = file.readUTF().strip();
                states[i][1] = file.readUTF().strip();
            }
            file.close();
            return states.clone();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new String[0][];
    }

    private static int getAge() throws CancelledSurveyException {

        String age;
        try{
            do{
                System.out.print("Please enter your age: ");
                age = scanner.next();
            }while(!isNumeric(age));
            if (Integer.parseInt(age) < 18){
                System.out.println("You are too young to complete the survey.");
                throw new CancelledSurveyException();
            }
            return Integer.parseInt(age);
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static boolean isNumeric(String str) throws CancelledSurveyException{
        try {
            if(str.compareToIgnoreCase("quit") == 0){
                throw new CancelledSurveyException();
            }
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            System.out.println("You've entered an invalid age.");
            return false;
        }
    }
    public static boolean isZipCode(String str) throws CancelledSurveyException{
        try {
            if(str.compareToIgnoreCase("quit") == 0){
                throw new CancelledSurveyException();
            }
            int zip = Integer.parseInt(str);
            if (zip > 0 && str.length() == 5){
                return true;
            }
            System.out.println("Invalid zip code.");
            return false;
        } catch(NumberFormatException e){
            System.out.println("Invalid Input.");
            return false;
        }
    }
}
