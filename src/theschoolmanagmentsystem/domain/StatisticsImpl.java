package theschoolmanagmentsystem.domain;

import java.time.LocalDate;
import static java.util.stream.Collectors.averagingDouble;
import theschoolmanagmentsystem.databaseControl.dbControlJpaImpl;

public class StatisticsImpl implements StatisticsInterface {

    private final dbControlJpaImpl dbC = new dbControlJpaImpl();

    @Override
    public double getAverageAgeOfStudents() {
        return LocalDate.now().getYear() - dbC.getAllStudents().stream()
                .collect(averagingDouble(s -> (int) (s.getPn() / Math.pow(10,8))));
    }

    @Override
    public double getAverageAgeOfTeachers() {
        return LocalDate.now().getYear() - dbC.getAllTeachers().stream()
                .collect(averagingDouble(t -> (int) (t.getPn() / Math.pow(10,8))));
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
