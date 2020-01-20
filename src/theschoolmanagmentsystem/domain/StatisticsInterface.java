package theschoolmanagmentsystem.domain;

/**
 *
 * @author Fabian'
 */
public interface StatisticsInterface {
    /**
     * 
     * @return the average age of all students
     */
    double getAverageAgeOfStudents();
    
    /**
     * 
     * @return the average age of all teachers 
     */
    double getAverageAgeOfTeachers();
    
    /**
     * 
     * @return the average number of courses per education
     */
    double getAverageNrOfCoursesPerEducation();
    
    /**
     * 
     * @return the average number of students per education
     */
    double getAverageNrOfStudentsPerEducation();
    
    
}
