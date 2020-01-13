package theschoolmanagmentsystem.userEnvironment;

import java.util.Scanner;

public class userEnvironmentInterface {
    Scanner sc = new Scanner(System.in);
    
    public void displayText(String text){
        System.out.println(text);
    }
    
    public String getUserInput(){
        return sc.nextLine();
    }
    
    public userEnvironmentInterface(){
    }
}