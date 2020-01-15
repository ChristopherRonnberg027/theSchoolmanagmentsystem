package theschoolmanagmentsystem.userEnvironment;

import theschoolmanagmentsystem.databaseControl.dbDAO;

public class userEnvironmentMenyLoop {

    userEnvironmentInterface uInter = new userEnvironmentInterface();
    //dbDAO dbEntity = dbControlJpaImpl();

    private boolean exit;

    public userEnvironmentMenyLoop() {

    }

    public void mainMenu(dbDAO dbEntity) {
        while (!this.exit) {
            switchMainMenuChoice(showMainMenu(), dbEntity);
        }
    }

    /** Takes two integers int min and int max
     *
     * @param min int minum range in meny
     * @param max int maxium range in meny
     * @return int in range in meny
     */
    public int intInRangeFromUser(int min, int max) {
        int inRange = - 1;
        try {
            while (inRange < min || inRange > max) {
                try {
                    inRange = Integer.parseInt(uInter.getUserInput());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid selection");
                }
            }
        } catch (Exception e) {
            System.out.println("Out of meny range");
        }
        return inRange;
    }

    /**
     * Display main menu text
     *
     * @return int from the intInRangeFromUser() method
     */
    public int showMainMenu() {
        System.out.println(userEnvironmentTexts.mainMenuText);
        int choice = intInRangeFromUser(0, 4);
        return choice;
    }

    /**
     * 
     * @param choice 
     */
    public void switchMainMenuChoice(int choice, dbDAO dbEntity) {
        switch (choice) {
            case 1:
                System.out.println("Course");
                break;
            case 2:
                System.out.println("Education");
                break;
            case 3:
                System.out.println("Student");
                break;
            case 4:
                System.out.println("Teacher");
                break;
            case 0:
                this.exit = true;
                break;

        }
    }

}
