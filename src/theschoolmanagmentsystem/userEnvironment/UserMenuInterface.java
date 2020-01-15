
package theschoolmanagmentsystem.userEnvironment;

import java.util.List;
import theschoolmanagmentsystem.domain.Course;
import theschoolmanagmentsystem.domain.Education;
import theschoolmanagmentsystem.domain.Student;
import theschoolmanagmentsystem.domain.Teacher;

public interface UserMenuInterface {
    void printStudent(Student studentToprint);
    
    void printEducation(Education educationToPrint);
    
    void printDetailedTeacher(Teacher teacherToPrint);
    
    void printCoursesList(List<Course> coursesListToPrint);
}
