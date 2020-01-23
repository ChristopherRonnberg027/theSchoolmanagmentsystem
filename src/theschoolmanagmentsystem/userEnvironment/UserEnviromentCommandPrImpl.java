package theschoolmanagmentsystem.userEnvironment;

import buisnesslogic.userEnvironmentAccess.UserEnviromentAccess;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import theschoolmanagmentsystem.domain.Course;
import theschoolmanagmentsystem.domain.Education;
import theschoolmanagmentsystem.domain.Student;
import theschoolmanagmentsystem.domain.Teacher;

public class UserEnviromentCommandPrImpl implements UserEnviromentAccess {
    Scanner sc = new Scanner(System.in);
    
    
    @Override
    public void print(Student studentToPrint) {
        System.out.printf("%s %s,\tEducation: %s,\t"
                + "Personal identification number: %d\n",
                studentToPrint.getFirstName(), studentToPrint.getSurName(),
                studentToPrint.getEducation().getName(),
                studentToPrint.getPn());
    }

    @Override
    public void print(Teacher teacherToPrint) {
        System.out.printf("%s %s,\t"
                + "Personal identification number: %d\n",
                teacherToPrint.getFirstName(), teacherToPrint.getSurName(),
                teacherToPrint.getPn());
    }

    @Override
    public void print(Course courseToPrint) {
        System.out.printf("%s,\tlasts from %s to %s, schoolbreak %s\n", courseToPrint.getName(),
                courseToPrint.getStart(), courseToPrint.getEnd(),
                courseToPrint.getSchoolBreak());
    }

    @Override
    public void print(Education educationToPrint) {
        System.out.printf("%s,\tlasts from %s to %s, schoolbreak %s\n", educationToPrint.getName(),
                educationToPrint.getStart(), educationToPrint.getEnd(),
                educationToPrint.getSchoolBreak());
    }

    @Override
    public void printFull(Student studentToPrint) {
        System.out.print("- Student: ");
        print(studentToPrint);

        System.out.println("Courses: ");
        for (Course course : studentToPrint.getEducation().getCourses()) {
            print(course);
        }
    }

    @Override
    public void printFull(Teacher teacherToPrint) {
        System.out.print("- Teacher: ");
        print(teacherToPrint);
        
        System.out.println("Courses: ");
        for (Course course : teacherToPrint.getCourses()) {
            print(course);
        }
    }

    @Override
    public void printFull(Course courseToPrint) {
        System.out.print("- Course: ");
        print(courseToPrint);
        
        System.out.println("Teachers: ");
        for (Teacher teacher : courseToPrint.getTeachers()) {
            print(teacher);
        }
    }

    @Override
    public void printFull(Education educationToPrint) {
        System.out.print("- Education: ");
        print(educationToPrint);
        
        System.out.println("Courses: ");
        for (Course course : educationToPrint.getCourses()) {
            print(course);
        }
        
        System.out.println("Students: ");
        for (Student student : educationToPrint.getStudents()) {
            print(student);
        }
    }

    @Override
    public void printList(List<?> listToPrint) {
        for (Object object : listToPrint) {
            printText(object.toString());
        }
    }

    @Override
    public String getStringInputFromUser(String textBeforeInput) {
        System.out.print(textBeforeInput);
        String retur = sc.nextLine();
        return retur;
    }


    @Override
    public void printText(String text) {
        System.out.println(text);
    }

    @Override
    public Integer getIntegerInputFromUser(String textBeforeInput) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
