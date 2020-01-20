package theschoolmanagmentsystem.domain;

import static java.util.stream.Collectors.averagingDouble;
import theschoolmanagmentsystem.databaseControl.dbControlJpaImpl;

public class StatisticsImpl implements StatisticsInterface {

    private final dbControlJpaImpl dbC = new dbControlJpaImpl();

    @Override
    public double getAverageAgeOfStudents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getAverageAgeOfTeachers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getAverageNrOfCoursesPerEducation() {
        return dbC.getAllEducations().stream()
                .collect(averagingDouble(ed -> ed.getCourses().size()));
    }

    @Override
    public double getAverageNrOfStudentsPerEducation() {
        return dbC.getAllEducations().stream()
                .collect(averagingDouble(ed -> ed.getStudents().size()));
    }

}
