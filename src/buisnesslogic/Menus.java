package buisnesslogic;

import buisnesslogic.userEnvironmentAccess.UserEnviromentAccess;
import theschoolmanagmentsystem.databaseControl.dbControlJpaImpl;
import theschoolmanagmentsystem.userEnvironment.UserEnviromentCommandPrImpl;
import theschoolmanagmentsystem.databaseControl.dbDAO;

/**
 * Implements menus for running the aplication as stand-alone program. 
 * Menus are to be called from the main method
 * For server aplication menus are not needed.
 * @author I.S.op187
 */
public class Menus {
    
    //Instatiate Command Prompt Environment
    UserEnviromentAccess ue = new UserEnviromentCommandPrImpl();
    //To access user environment use 'ue' variable.
    
    //Instantiates database. In this case it uses local mysql database 
    dbDAO db = new dbControlJpaImpl();
    //To access databes use 'db' variable
    
    
    // TODO implement menus starting from main etc. Main should be
    // in another class
    
}
