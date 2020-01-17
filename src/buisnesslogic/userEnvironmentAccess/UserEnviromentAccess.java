
package buisnesslogic.userEnvironmentAccess;

import java.util.List;
import theschoolmanagmentsystem.domain.Course;
import theschoolmanagmentsystem.domain.Education;
import theschoolmanagmentsystem.domain.Student;
import theschoolmanagmentsystem.domain.Teacher;

/**
 *
 * @author I.S.op187
 */
public interface UserEnviromentAccess {
    
    /**
     * Simple Student print. 
     * @param studentToPrint 
     */
    public void print(Student studentToPrint);
    /**
     * Simple Teacher print. 
     * @param teacherToPrint
     */
    public void print(Teacher teacherToPrint);
    /**
     * Simple Course print. 
     * @param courseToPrint
     */
    public void print(Course courseToPrint);
    /**
     * Simple Education print. 
     * @param educationToPrint
     */
    public void print(Education educationToPrint);
    
    /**
     * Detailed Student print. Be sure to use print Education, Courses and 
     * Teachers
     * @param studentToPrint 
     */
    public void printFull(Student studentToPrint);
    
    /**
     * Detailed Teacher print. Be sure to print Courses, Educations and Students
     * @param teacherToPrint 
     */
    public void printFull(Teacher teacherToPrint);
    
    /**
     * Detailed Course print. Be sure to print Teachers, Students and 
     * Educatipons
     * @param courseToPrint 
     */
    public void printFull(Course courseToPrint);
    
    /**
     * Detailed Education print. Be sure to print Teachers, Students and 
     * Courses 
     * @param educationToPrint
     */
    public void printFull(Education educationToPrint);
    
    /**
     * Generic list print. It takes any object, so be sure to implement
     * toString in entity class as field will not be reachable.
     * 
     * @param listToPrint 
     */
    public void printList(List<?> listToPrint);
    
    
    /**
     * This is a main input from user. 
     * Use parsing for turning into Numbers.
     * All inputs are to happen in the same line as textBeforeInput;
     * F.ex. Name: ________
     * Method getIntFromUser or similar are for JFrame implementations.
     * @param textBeforeInput - Any text to explain the input
     * @return String
     */
    public String getStringInputFromUser(String textBeforeInput);
    
    /**
     * Input method for fetching Integers. Use with JFrame implemetations.
     * @param textBeforeInput - Any text to explain the input
     * @return Integer
     */
    public Integer getIntegerInputFromUser(String textBeforeInput);
    
    /**
     * Method for showing text to the user.
     * @param text 
     */
    public void printText(String text);
    
}
