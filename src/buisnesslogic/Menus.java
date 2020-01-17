package buisnesslogic;

import static buisnesslogic.MenuTexts.coursesMenuText;
import static buisnesslogic.MenuTexts.editMenuText;
import static buisnesslogic.MenuTexts.mainMenuText;
import static buisnesslogic.MenuTexts.studentsMenuText;
import static buisnesslogic.MenuTexts.teachersMenuText;
import buisnesslogic.userEnvironmentAccess.UserEnviromentAccess;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import theschoolmanagmentsystem.databaseControl.dbControlJpaImpl;
import theschoolmanagmentsystem.userEnvironment.UserEnviromentCommandPrImpl;
import theschoolmanagmentsystem.databaseControl.dbDAO;
import theschoolmanagmentsystem.domain.Course;
import theschoolmanagmentsystem.domain.Education;
import theschoolmanagmentsystem.domain.Student;
import theschoolmanagmentsystem.domain.Teacher;
import theschoolmanagmentsystem.domain.exceptions.NonexistentEntityException;

/**
 * Implements menus for running the aplication as stand-alone program. Menus are
 * to be called from the main method For server aplication menus are not needed.
 *
 * @author I.S.op187
 */
public class Menus {

    //Instatiate Command Prompt Environment
    UserEnviromentAccess ue = new UserEnviromentCommandPrImpl();
    //To access user environment use 'ue' variable.

    //Instantiates database. In this case it uses local mysql database 
    dbDAO db = new dbControlJpaImpl();
    //To access databes use 'db' variable

    //Instantiates
    Utilities utility = new Utilities();

    // TODO implement menus starting from main etc. Main should be
    // in another class
    // IN PROGRESS
    public void mainMenu() {
        boolean mainLoop = true;
        while (mainLoop) {
            int mainChoice = intInRangeFromUser(0, 4, ue.getIntegerInputFromUser(mainMenuText));
            switch (mainChoice) {
                case 1:
                    // X
                    courseMenu();
                    break;
                case 2:
                    // education domainMenu()
                    educationMenu();
                    break;
                case 3:
                    // X
                    studentMenu();
                    break;
                case 4:
                    // X
                    teacherMenu();
                    break;
                case 0:
                    mainLoop = false;
                    break;
            }
        }
    }

    // EDUCATION MENU IN PROGRESS
    public void educationMenu() {
        boolean educationMenuLoop = true;
        while (educationMenuLoop) {
            List<Education> listOfEducations = db.getAllEducations();
            ue.printList(listOfEducations);
            int educationMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(editMenuText));
            switch (educationMenuChoice) {
                case 1:
                    // ADD NEW EDUCATION
                    addNewEducation();
                    break;
                case 2:
                    // EDIT EDUCATION
                    educationEditMenu();
                    break;
                case 0:
                    educationMenuLoop = false;
                    break;
            }
        }
    }

    // DELETE EDUCATION
    public void deleteEducation(Education educationToEdit) {
        boolean isNotSure = true;
        while (isNotSure) {
            String answer = utility.isStringValid(ue.getStringInputFromUser("Delete "
                    + educationToEdit.getName() + "? yes/no?"));
            if (answer.equalsIgnoreCase("yes")) {
                try {
                    db.destroyEducation(educationToEdit);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    ue.printText("Entity not found...");
                }
                isNotSure = false;
            } else {
                isNotSure = false;
            }
        }
    }

    // X
    public void editEducation(Education educationToEdit) {
        boolean editEducationLoop = true;
        while (editEducationLoop) {
            String answer = utility.isStringValid("Would you like to edit? Yes/No");
            if (answer.equalsIgnoreCase("no")) {
                editEducationLoop = false;
            } else {
                String name = utility.isStringValid(ue.getStringInputFromUser("Education name: "));
                String start = utility.isStringValid(ue.getStringInputFromUser("Start date: "));
                String end = utility.isStringValid(ue.getStringInputFromUser("End date: "));
                String schoolBreak = utility.isStringValid(ue.getStringInputFromUser("School break dates: "));
                educationToEdit.setName(name);
                educationToEdit.setStart(start);
                educationToEdit.setEnd(end);
                educationToEdit.setSchoolBreak(schoolBreak);

                //TODO set course and students
                educationToEdit.setCourses(null);
                educationToEdit.setStudents(null);
                try {
                    db.editEducation(educationToEdit);
                } catch (Exception ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    ue.printText("Entity not saved...");
                }
                editEducationLoop = false;
            }
        }
    }

    // EDUCATION EDIT 
    public void educationEditMenu() {
        boolean educationEditMenuLoop = true;
        while (educationEditMenuLoop) {
            Long id = utility.isLongValid(ue.getStringInputFromUser("Enter education id number: "));
            Education educationToEdit = thereIsAlreadyAEducation(id);
            if (educationToEdit == null) {
                ue.printText("No such education with that id");
                educationEditMenuLoop = false;
            } else {
                ue.printFull(educationToEdit);
                int courseEditMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(editMenuText));
                switch (courseEditMenuChoice) {
                    case 1:
                        // EDIT EDUCATION
                        editEducation(educationToEdit);
                        break;
                    case 2:
                        // DELETE EDUCATION
                        deleteEducation(educationToEdit);
                        educationEditMenuLoop = false;
                        break;
                    case 0:
                        educationEditMenuLoop = false;
                        break;
                }
            }
        }
    }

    // X
    public void addNewEducation() {
        ue.printText("Add new education");
        Long id = utility.isLongValid(ue.getStringInputFromUser("Education id number: "));
        Education newEducation = thereIsAlreadyAEducation(id);
        if (newEducation == null) {
            newEducation.setId(id);
            String name = utility.isStringValid(ue.getStringInputFromUser("Education name: "));
            String start = utility.isStringValid(ue.getStringInputFromUser("Start date: "));
            String end = utility.isStringValid(ue.getStringInputFromUser("End date: "));
            String schoolBreak = utility.isStringValid(ue.getStringInputFromUser("School break dates: "));
            newEducation.setName(name);
            newEducation.setStart(start);
            newEducation.setEnd(end);
            newEducation.setSchoolBreak(schoolBreak);
            //TODO set course and students
            newEducation.setCourses(null);
            newEducation.setStudents(null);
            db.createEducation(newEducation);
        } else {
            ue.printText("Course already in system");
        }
    }

    // COURSE MENU
    public void courseMenu() {
        boolean courseMenuLoop = true;
        while (courseMenuLoop) {
            List<Course> listOfCourses = db.getAllCourses();
            ue.printList(listOfCourses);
            int courseMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(coursesMenuText));
            switch (courseMenuChoice) {
                case 1:
                    addNewCourse();
                    break;
                case 2:
                    courseEditMenu();
                    break;
                case 0:
                    courseMenuLoop = false;
                    break;
            }
        }
    }

    // EDIT COURSE MENU
    public void courseEditMenu() {
        boolean courseEditMenuLoop = true;
        while (courseEditMenuLoop) {
            Long id = utility.isLongValid(ue.getStringInputFromUser("Enter course id number: "));
            Course courseToEdit = thereIsAlreadyACourse(id);
            if (courseToEdit == null) {
                ue.printText("No such course with that id");
                courseEditMenuLoop = false;
            } else {
                ue.printFull(courseToEdit);
                int courseEditMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(editMenuText));
                switch (courseEditMenuChoice) {
                    case 1:
                        // EDIT COURSE
                        editCourse(courseToEdit);
                        break;
                    case 2:
                        // DELETE COURSE
                        deleteCourse(courseToEdit);
                        break;
                    case 0:
                        courseEditMenuLoop = false;
                        break;
                }
            }
        }
    }

    // DELETE COURSE
    public void deleteCourse(Course courseToEdit) {
        boolean isNotSure = true;
        while (isNotSure) {
            String answer = utility.isStringValid(ue.getStringInputFromUser("Delete "
                    + courseToEdit.getName() + "? yes/no?"));
            if (answer.equalsIgnoreCase("yes")) {
                try {
                    db.destroyCourse(courseToEdit);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    ue.printText("Entity not found...");
                }
                isNotSure = false;
            } else {
                isNotSure = false;
            }
        }
    }

    // EDIT COURSE
    public void editCourse(Course courseToEdit) {
        boolean editCourseLoop = true;
        while (editCourseLoop) {
            String answer = utility.isStringValid("Would you like to edit? Yes/No");
            if (answer.equalsIgnoreCase("no")) {
                editCourseLoop = false;
            } else {
                String name = utility.isStringValid(ue.getStringInputFromUser("Course name: "));
                String start = utility.isStringValid(ue.getStringInputFromUser("Start date: "));
                String end = utility.isStringValid(ue.getStringInputFromUser("End date: "));
                String schoolBreak = utility.isStringValid(ue.getStringInputFromUser("School break dates: "));
                courseToEdit.setName(name);
                courseToEdit.setStart(start);
                courseToEdit.setEnd(end);
                courseToEdit.setSchoolBreak(schoolBreak);

                // TODO 
                // search after educations and teachers? if found, continue, if not, what happens?
                courseToEdit.setEducations(null);
                courseToEdit.setTeachers(null);
                try {
                    db.editCourse(courseToEdit);
                } catch (Exception ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    ue.printText("Entity not saved...");
                }
                editCourseLoop = false;
            }
        }
    }

    // ADD NEW COURSE
    public void addNewCourse() {
        ue.printText("Add new course");
        Long id = utility.isLongValid(ue.getStringInputFromUser("Id number: "));
        Course newCourse = thereIsAlreadyACourse(id);
        if (newCourse == null) {
            newCourse.setId(id);
            String name = utility.isStringValid(ue.getStringInputFromUser("Course name: "));
            String start = utility.isStringValid(ue.getStringInputFromUser("Start date: "));
            String end = utility.isStringValid(ue.getStringInputFromUser("End date: "));
            String schoolBreak = utility.isStringValid(ue.getStringInputFromUser("School break dates: "));
            newCourse.setName(name);
            newCourse.setStart(start);
            newCourse.setEnd(end);
            newCourse.setSchoolBreak(schoolBreak);
            //TODO set educations and courses
            newCourse.setEducations(null);
            newCourse.setTeachers(null);
            db.createCourse(newCourse);
        } else {
            ue.printText("Course already in system");
        }
    }

    // TEACHER MENU
    public void teacherMenu() {
        boolean teacherMenuLoop = true;
        while (teacherMenuLoop) {
            List<Teacher> teacherList = db.getAllTeachers();
            ue.printList(teacherList);
            int teacherMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(teachersMenuText));
            switch (teacherMenuChoice) {
                case 1:
                    // ADD NEW TEACHER
                    addNewTeacher();
                    break;
                case 2:
                    // EDIT TEACHER MENU
                    teacherEditMenu();
                    break;
                case 0:
                    teacherMenuLoop = false;
                    break;
            }
        }
    }

    // EDIT TEACHER MENU X
    public void teacherEditMenu() {
        boolean teacherEditMenuLoop = true;
        while (teacherEditMenuLoop) {
            Long pn = utility.isLongValid(ue.getStringInputFromUser("Enter teacher person number to edit: "));
            Teacher teacherToEdit = thereIsAlreadyATeacher(pn);
            if (teacherToEdit == null) {
                ue.printText("No teacher whit that person number");
                teacherEditMenuLoop = false;
            } else {
                ue.printFull(teacherToEdit);
                int teacherEditMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(editMenuText));
                switch (teacherEditMenuChoice) {
                    case 1:
                        // EDIT TEACHER
                        editTeacher(teacherToEdit);
                        break;
                    case 2:
                        // DELETE TEACHER
                        deleteTeacher(teacherToEdit);
                        teacherEditMenuLoop = false;
                        break;
                    case 0:
                        teacherEditMenuLoop = false;
                        break;
                }
            }
        }
    }

    // DELETE TEACHER
    public void deleteTeacher(Teacher teacherToEdit) {
        boolean isNotSure = true;
        while (isNotSure) {
            String answer = utility.isStringValid(ue.getStringInputFromUser("Delete "
                    + teacherToEdit.getFirstName() + " "
                    + teacherToEdit.getSurName() + "? yes/no?"));
            if (answer.equalsIgnoreCase("yes")) {
                try {
                    db.destroyTeacher(teacherToEdit);
                } catch (NonexistentEntityException ex) {
                    ue.printText("Entity not found");
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                }
                isNotSure = false;
            } else {
                isNotSure = false;
            }
        }
    }

    // EDIT TEACHER
    public void editTeacher(Teacher teacherToEdit) {
        boolean editTeacherLoop = true;
        while (editTeacherLoop) {
            String answer = utility.isStringValid("Would you like to edit? Yes/No");
            if (answer.equalsIgnoreCase("no")) {
                editTeacherLoop = false;
            } else {
                String firstName = utility.isStringValid(ue.getStringInputFromUser("First name: "));
                String surName = utility.isStringValid(ue.getStringInputFromUser("Sur name: "));
                teacherToEdit.setFirstName(firstName);
                teacherToEdit.setSurName(surName);
                // TODO 
                // search after course? if found, continue, if not, what happens?
                teacherToEdit.setCourses(null);
                try {
                    db.editTeacher(teacherToEdit);
                } catch (Exception ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    ue.printText("Entity not saved...");
                }
                editTeacherLoop = false;
            }
        }
    }

    // ADD NEW TEACHER X
    public void addNewTeacher() {
        ue.printText("Add new Teacher");
        Long pn = utility.isLongValid(ue.getStringInputFromUser("Person number: "));
        Teacher newTeacher = thereIsAlreadyATeacher(pn);
        if (newTeacher == null) {
            newTeacher.setPn(pn);
            String firstName = utility.isStringValid(ue.getStringInputFromUser("First name: "));
            String surName = utility.isStringValid(ue.getStringInputFromUser("Sur name: "));
            newTeacher.setFirstName(firstName);
            newTeacher.setSurName(surName);
            //TODO set courses
            newTeacher.setCourses(null);
            db.createTeacher(newTeacher);
        } else {
            ue.printText("Teacher already in system");
        }
    }

    // STUDENT MENU X
    public void studentMenu() {
        boolean studentMenuLoop = true;
        while (studentMenuLoop) {
            List<Student> studentList = db.getAllStudents();
            ue.printList(studentList);
            int studentMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(studentsMenuText));
            switch (studentMenuChoice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    studentEditMenu();
                    break;
                case 0:
                    studentMenuLoop = false;
                    break;
            }
        }
    }

    // EDIT STUDENT MENU X
    public void studentEditMenu() {
        boolean studentEditMenuLoop = true;
        while (studentEditMenuLoop) {
            Long pn = utility.isLongValid(ue.getStringInputFromUser("Enter student person number: "));
            Student studentToEdit = thereIsAlreadyAStudent(pn);
            if (studentToEdit == null) {
                ue.printText("No student with that person number...");
                studentEditMenuLoop = false;
            } else {
                ue.printFull(studentToEdit);
                int studentEditMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(editMenuText));
                switch (studentEditMenuChoice) {
                    case 1:
                        // setMenu
                        editStudent(studentToEdit);
                        break;
                    case 2:
                        // deletemenu
                        deleteStudent(studentToEdit);
                        studentEditMenuLoop = false;
                        break;
                    case 0:
                        studentEditMenuLoop = false;
                        break;
                }

            }
        }
    }

    // DELETE STUDENT
    public void deleteStudent(Student studentToEdit) {
        boolean isNotSure = true;
        while (isNotSure) {
            String answer = utility.isStringValid(ue.getStringInputFromUser("Delete "
                    + studentToEdit.getFirstName() + " "
                    + studentToEdit.getSurName() + "? yes/no?"));
            if (answer.equalsIgnoreCase("yes")) {
                try {
                    db.destroyStudent(studentToEdit);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    ue.printText("Entity not found...");
                }
                isNotSure = false;
            } else {
                isNotSure = false;
            }
        }
    }

    // EDIT STUDENT
    public void editStudent(Student studentToEdit) {
        boolean editStudentLoop = true;
        while (editStudentLoop) {
            String answer = utility.isStringValid("Would you like to edit? Yes/No");
            if (answer.equalsIgnoreCase("no")) {
                editStudentLoop = false;
            } else {
                String firstName = utility.isStringValid(ue.getStringInputFromUser("First name: "));
                String surName = utility.isStringValid(ue.getStringInputFromUser("Sur name: "));
                studentToEdit.setFirstName(firstName);
                studentToEdit.setSurName(surName);
                // TODO 
                // search after education? if found, continue, if not, what happens?
                studentToEdit.setEducation(null);
                try {
                    db.editStudent(studentToEdit);
                } catch (Exception ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    ue.printText("Entity not saved...");
                }
                editStudentLoop = false;
            }
        }
    }

    // ADD NEW STUDENT X
    public void addNewStudent() {
        ue.printText("Add new Student");
        Long pn = utility.isLongValid(ue.getStringInputFromUser("Person number: "));
        Student newStudent = thereIsAlreadyAStudent(pn);
        if (newStudent == null) {
            newStudent.setPn(pn);
            String firstName = utility.isStringValid(ue.getStringInputFromUser("First name: "));
            String surName = utility.isStringValid(ue.getStringInputFromUser("Sur name: "));
            newStudent.setFirstName(firstName);
            newStudent.setSurName(surName);
            newStudent.setEducation(null);
            db.createStudent(newStudent);
        } else {
            ue.printText("Student already in system");
        }
    }

    // X
    public Education thereIsAlreadyAEducation(Long id) {
        Education foundEducation = db.findEducationById(id);
        if (foundEducation == null) {
            ue.printText("No such education");
        } else {
            return foundEducation;
        }
        return null;
    }

    // X
    public Course thereIsAlreadyACourse(Long id) {
        Course foundCourse = db.findCourseById(id);
        if (foundCourse == null) {
            ue.printText("No such course");
        } else {
            return foundCourse;
        }
        return null;
    }

    // X
    public Teacher thereIsAlreadyATeacher(Long pn) {
        Teacher foundTeacher = db.findTeacherById(pn);
        if (foundTeacher == null) {
            ue.printText("No such teacher");
        } else {
            return foundTeacher;
        }
        return null;
    }

    // X
    public Student thereIsAlreadyAStudent(Long pn) {
        Student foundStudent = db.findStudentById(pn);
        if (foundStudent == null) {
            ue.printText("No such student");
        } else {
            return foundStudent;
        }
        return null;
    }

    // X
    public int intInRangeFromUser(int min, int max, int userInPut) {
        int inRange = - 1;
        try {
            while (inRange < min || inRange > max) {
                try {
                    inRange = userInPut;
                } catch (NumberFormatException e) {
                    ue.printText("Invalid selection");
                }
            }
        } catch (Exception e) {
            ue.printText("Out of meny range");
        }
        return inRange;
    }

}
