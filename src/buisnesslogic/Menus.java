package buisnesslogic;

import static buisnesslogic.MenuTexts.coursesMenuText;
import static buisnesslogic.MenuTexts.editMenuText;
import static buisnesslogic.MenuTexts.enterIdToAddMenuText;
import static buisnesslogic.MenuTexts.enterPnToAddMenuText;
import static buisnesslogic.MenuTexts.mainMenuText;
import static buisnesslogic.MenuTexts.setCourseToEducationMenuText;
import static buisnesslogic.MenuTexts.setEducationToCourseMenuText;
import static buisnesslogic.MenuTexts.setStudentToEducationMenuText;
import static buisnesslogic.MenuTexts.studentsMenuText;
import static buisnesslogic.MenuTexts.teachersMenuText;
import buisnesslogic.userEnvironmentAccess.UserEnviromentAccess;
import java.util.ArrayList;
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

    //Instantiates utility class
    Utilities utility = new Utilities();

    /**
     * Main menu. Users switches between 5 choices: 1. Course menu. 2. Education
     * menu. 3. Student menu. 4. Teacher menu 0. Exit application.
     */
    public void mainMenu() {
        boolean mainLoop = true;
        while (mainLoop) {
            int mainChoice = intInRangeFromUser(0, 4, ue.getIntegerInputFromUser(mainMenuText));
            switch (mainChoice) {
                case 1:
                    courseMenu();
                    break;
                case 2:
                    educationMenu();
                    break;
                case 3:
                    studentMenu();
                    break;
                case 4:
                    teacherMenu();
                    break;
                case 0:
                    mainLoop = false;
                    break;
            }
        }
    }

    /**
     * Education Menu. Switches between 3 choices: 1. Calls
     * addNewEducation()-method 2. Calls educationEditMenu()-method 3. Back to
     * Main Menu
     */
    public void educationMenu() {
        boolean educationMenuLoop = true;
        while (educationMenuLoop) {
            List<Education> listOfEducations = aListOfEducationsFromDataBase();
            ue.printList(listOfEducations);
            int educationMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(editMenuText));
            switch (educationMenuChoice) {
                case 1:
                    // ADD NEW EDUCATION
                    addNewEducation();
                    break;
                case 2:
                    // EDIT EDUCATION INFORMATION MENU
                    educationEditMenu();
                    break;
                case 0:
                    educationMenuLoop = false;
                    break;
            }
        }
    }

    /**
     * Deletes Education from database. Needs a object of Education to delete
     *
     * @param educationToEdit
     */
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

    /**
     * Edit Education information. Needs an object of Education to edit. Calls
     * editEducation()-method from database controller
     *
     * @param educationToEdit an object of Education
     */
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
                Course courseToEducation = printCourseListAndSearchAndCourse();
                if (courseToEducation == null) {
                    educationToEdit.addCourse(null);
                } else {
                    educationToEdit.addCourse(courseToEducation);
                }
                Student studentToEducation = printStudentListAndSearchAndFindStudent();
                if (studentToEducation == null) {
                    educationToEdit.addStudent(null);
                } else {
                    educationToEdit.addStudent(studentToEducation);
                }
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

    /**
     * Prints a full Student list from database. Search and find Student.
     *
     * @return an object of Student.
     */
    public Student printStudentListAndSearchAndFindStudent() {
        ue.printText(enterPnToAddMenuText);
        List<Student> studentList = aListOFStudentFromDataBase();
        ue.printList(studentList);
        Student studentToEducation = searchAndFindStudent();
        return studentToEducation;
    }

    /**
     * Prints a full Course list from database Search and find Course
     *
     * @return an object of Course
     */
    public Course printCourseListAndSearchAndCourse() {
        ue.printText(enterIdToAddMenuText);
        List<Course> courseList = aListOfCoursesFromDataBase();
        ue.printList(courseList);
        Course courseToEducation = searchAndFindCourse();
        return courseToEducation;
    }

    /**
     * Education edit menu. User enters id to a Education to edit. Calls the
     * thereIsAEducation()-method with id from user. Reaturns a object of
     * Education. Sends back a message if the Education is in the system or not.
     * If there is a Education, switches between 3 choices: 1. Calls
     * editEducation()-method with the found Education object as parameter. 2.
     * Calls deleteCourse()-method with the found Education object as parameter.
     * 0. Back to Education menu.
     */
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
                        // EDIT EDUCATION INFORMATION
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

    /**
     * Add new Education. Creates an Object of Education. Calls
     * db.createEducation()-method from db-controller.
     *
     */
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
            Course courseToEducation = printCourseListAndSearchAndCourse();
            if (courseToEducation == null) {
                newEducation.addCourse(null);
            } else {
                newEducation.addCourse(courseToEducation);
            }
            Student studentToEducation = printStudentListAndSearchAndFindStudent();
            if (studentToEducation == null) {
                newEducation.addStudent(null);
            } else {
                newEducation.addStudent(studentToEducation);
            }

            db.createEducation(newEducation);
        } else {
            ue.printText("Course already in system");
        }
    }

//////////////////////////////////////////////////
    /**
     * Course Menu. Switches between 3 choices: 1. Calls addNewCourse()-method
     * 2. Calls courseEditMenu()-method 3. Back to Main Menu
     */
    public void courseMenu() {
        boolean courseMenuLoop = true;
        while (courseMenuLoop) {
            List<Course> listOfCourses = aListOfCoursesFromDataBase();
            ue.printList(listOfCourses);
            int courseMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(coursesMenuText));
            switch (courseMenuChoice) {
                case 1:
                    // ADD NEW COURSE
                    addNewCourse();
                    break;
                case 2:
                    // EDIT COURSE INFORMATION MENU
                    courseEditMenu();
                    break;
                case 0:
                    courseMenuLoop = false;
                    break;
            }
        }
    }

    /**
     * Course edit menu. User enters id to a Course to edit. Calls the
     * thereIsACourse()-method with id from user. Reaturns a object of Course.
     * Sends back a message if the Course is in the system or not. If there is a
     * Course, switches between 3 choices: 1. Calls editCourse()-method with the
     * found Course object as parameter. 2. Calls deleteCourse()-method with the
     * found Course object as parameter. 0. Back to Course menu.
     */
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
                        // EDIT COURSE INFORMATION
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

    /**
     * Deletes course from database. Needs a object of Course to delete
     *
     * @param courseToEdit An Object of Course
     */
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

    /**
     * Prints a full Education list from database. Search and find Education.
     *
     * @return an object of Education.
     */
    public Education printEducationListAndSearchAndFindEducation() {
        ue.printText(enterIdToAddMenuText);
        List<Education> educationList = aListOfEducationsFromDataBase();
        ue.printList(educationList);
        Education educationToCourse = searchAndFindEducation();
        return educationToCourse;
    }

    /**
     * Prints a full Teacher list from database. Search and find Teacher.
     *
     * @return an object of Teacher.
     */
    public Teacher printTeacherListAndSearchAndFindTeacher() {
        ue.printText(enterPnToAddMenuText);
        List<Teacher> teacherList = aListOfTeachersFromDataBase();
        ue.printList(teacherList);
        Teacher teacherToCourse = searchAndFindTeacher();
        return teacherToCourse;
    }

    /**
     * Edit Course information. Needs an object of Course to edit. Calls
     * editCourse()-method from database controller.
     *
     * @param courseToEdit an object of Course.
     */
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

                Education educationToCourse = printEducationListAndSearchAndFindEducation();
                if (educationToCourse == null) {
                    courseToEdit.addEducation(null);
                } else {
                    courseToEdit.addEducation(educationToCourse);
                }

                Teacher teacherToCourse = printTeacherListAndSearchAndFindTeacher();
                if (teacherToCourse == null) {
                    courseToEdit.addTeacher(null);
                }
                courseToEdit.addTeacher(teacherToCourse);

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

    /**
     * Add new Course. Creates an Object of Course. Calls
     * db.createCourse()-method from db-controller.
     *
     */
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
            Education educationToCourse = printEducationListAndSearchAndFindEducation();
            if (educationToCourse == null) {
                newCourse.addEducation(null);
            } else {
                newCourse.addEducation(educationToCourse);
            }
            Teacher teacherToCourse = printTeacherListAndSearchAndFindTeacher();
            if (teacherToCourse == null) {
                newCourse.addTeacher(null);
            }
            newCourse.addTeacher(teacherToCourse);
            db.createCourse(newCourse);
        } else {
            ue.printText("Course already in system");
        }
    }

//////////////////////////////////////////////////
    /**
     * Teacher Menu. Switches between 3 choices: 1. Calls addNewTeacher()-method
     * 2. Calls teacherEditMenu()-method 3. Back to Main Menu
     */
    public void teacherMenu() {
        boolean teacherMenuLoop = true;
        while (teacherMenuLoop) {
            List<Teacher> teacherList = aListOfTeachersFromDataBase();
            ue.printList(teacherList);
            int teacherMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(teachersMenuText));
            switch (teacherMenuChoice) {
                case 1:
                    // ADD NEW TEACHER
                    addNewTeacher();
                    break;
                case 2:
                    // EDIT TEACHER INFORMATION MENU
                    teacherEditMenu();
                    break;
                case 0:
                    teacherMenuLoop = false;
                    break;
            }
        }
    }

    /**
     * Teacher edit menu. User enters id to a Teacher to edit. Calls the
     * thereIsATeacher()-method with id from user. Reaturns a object of Teacher.
     * Sends back a message if the Teacher is in the system or not. If there is
     * a Teacher, switches between 3 choices: 1. Calls editTeacher()-method with
     * the found Teacher object as parameter. 2. Calls deleteTeacher()-method
     * with the found Teacher object as parameter. 0. Back to Teacher menu.
     */
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
                        // EDIT TEACHER INFORMATION
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

    /**
     * Deletes Teacher from database. Needs a object of Teacher to delete
     *
     * @param teacherToEdit
     */
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

    /**
     * Edit Teacher information. Needs an object of Teacher to edit. Calls
     * editTeacher()-method from database controller.
     *
     * @param teacherToEdit an object of Teacher.
     */
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

                Course courseToTeacher = printCourseListAndSearchAndCourse();
                if (courseToTeacher == null) {
                    teacherToEdit.addCourse(null);
                } else {
                    teacherToEdit.addCourse(courseToTeacher);
                }
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

    /**
     * Add new Teacher. Creates an Object of Teacher. Calls
     * db.createTeacher()-method from db-controller.
     *
     */
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
            Course courseToTeacher = printCourseListAndSearchAndCourse();
            if (courseToTeacher == null) {
                newTeacher.addCourse(null);
            } else {
                newTeacher.addCourse(courseToTeacher);
            }
            db.createTeacher(newTeacher);
        } else {
            ue.printText("Teacher already in system");
        }
    }
//////////////////////////////////////////////////

    /**
     * Student Menu. Switches between 3 choices: 1. Calls addNewStudent()-method
     * 2. Calls studentEditMenu()-method 3. Back to Main Menu
     */
    public void studentMenu() {
        boolean studentMenuLoop = true;
        while (studentMenuLoop) {
            List<Student> studentList = aListOFStudentFromDataBase();
            ue.printList(studentList);
            int studentMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(studentsMenuText));
            switch (studentMenuChoice) {
                case 1:
                    // ADD NEW STUDENT
                    addNewStudent();
                    break;
                case 2:
                    // EDIT STUDENT INFORMATION MENU
                    studentEditMenu();
                    break;
                case 0:
                    studentMenuLoop = false;
                    break;
            }
        }
    }

    /**
     * Student edit menu. User enters id to a Student to edit. Calls the
     * thereIsAStudent()-method with id from user. Reaturns a object of Student.
     * Sends back a message if the Student is in the system or not. If there is
     * a Student, switches between 3 choices: 1. Calls editStudent()-method with
     * the found Student object as parameter. 2. Calls deleteStudent()-method
     * with the found Student object as parameter. 0. Back to Student menu.
     */
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
                        // EDIT STUDENT INFORMATION
                        editStudent(studentToEdit);
                        break;
                    case 2:
                        // DELETE STUDENT
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

    /**
     * Deletes Student from database. Needs a object of Student to delete
     *
     * @param studentToEdit
     */
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

    /**
     * Edit Student information. Needs an object of Student to edit. Calls
     * editStudent()-method from database controller.
     *
     * @param studentToEdit an object of Student.
     */
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

                Education educationToStudent = printEducationListAndSearchAndFindEducation();
                if (educationToStudent == null) {
                    studentToEdit.setEducation(null);
                } else {
                    studentToEdit.setEducation(educationToStudent);
                }
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

    /**
     * Add new Student. Creates an Object of Student. Calls
     * db.createStudent()-method from db-controller.
     *
     */
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
            Education educationToStudent = printEducationListAndSearchAndFindEducation();
            if (educationToStudent == null) {
                newStudent.setEducation(null);
            } else {
                newStudent.setEducation(educationToStudent);
            }
            db.createStudent(newStudent);
        } else {
            ue.printText("Student already in system");
        }
    }
/////////////////////////////////////////////////

    /**
     * Checks to see if user input is in range of menu choice. Takes an integer
     * from user and 2 integers to narrow the choices.
     *
     * @param min is the minimum integer input.
     * @param max is the maximum integer input.
     * @param userInPut takes an integer from user.
     * @return an integer in range of menu choices.
     */
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
/////////////////////////////////////////////////

    /**
     * Searching after a course in database
     *
     * @return an object of Course
     */
    public Course searchAndFindCourse() {
        Long courseId = utility.isLongValid(ue.getStringInputFromUser("Enter Course id number: "));
        Course courseFromDb = db.findCourseById(courseId);
        Course educationToCourse = thereIsAlreadyACourse(courseId);
        return educationToCourse;
    }

    /**
     * Searching after a student in database
     *
     * @return an object of Student
     */
    public Student searchAndFindStudent() {
        Long pn = utility.isLongValid(ue.getStringInputFromUser("Enter Student person number: "));
        Student studentFromDb = db.findStudentById(pn);
        Student student = thereIsAlreadyAStudent(pn);
        return student;
    }

    /**
     * Searching a education in database
     *
     * @return an object of Education
     */
    public Education searchAndFindEducation() {
        Long educationId = utility.isLongValid(ue.getStringInputFromUser("Enter Education id number: "));
        Education educationFromDb = db.findEducationById(educationId);
        Education setEducationToCourse = thereIsAlreadyAEducation(educationId);
        return setEducationToCourse;
    }

    /**
     * Searching a teacher in database
     *
     * @return an object of Teacher
     */
    public Teacher searchAndFindTeacher() {
        Long pn = utility.isLongValid(ue.getStringInputFromUser("Enter Teacher person number: "));
        Teacher teacherFromDb = db.findTeacherById(pn);
        Teacher teacher = thereIsAlreadyATeacher(pn);
        return teacher;
    }

    /**
     * Checks to see if there already is a Education in the system. Takes an
     * Long as id search in database. Calls the findEducationById()-method from
     * dbController.
     *
     * @param id Long from user input.
     * @return if there is a Education, it returns a full object of Education,
     * else a null object.
     */
    public Education thereIsAlreadyAEducation(Long id) {
        Education foundEducation = db.findEducationById(id);
        if (foundEducation == null) {
            ue.printText("No such education");
        } else {
            return foundEducation;
        }
        return null;
    }

    /**
     * Checks to see if there already is a Course in the system. Takes an Long
     * as id search in database. Calls the findCourseById()-method from
     * dbController.
     *
     * @param id Long from user input.
     * @return if there is a Course, it returns a full object of Course, else a
     * null object.
     */
    public Course thereIsAlreadyACourse(Long id) {
        Course foundCourse = db.findCourseById(id);
        if (foundCourse == null) {
            ue.printText("No such course");
        } else {
            return foundCourse;
        }
        return null;
    }

    /**
     * Checks to see if there already is a Teacher in the system. Takes an Long
     * as id search in database. Calls the findTeacherById()-method from
     * dbController.
     *
     * @param pn Long from user input.
     * @return if there is a Teacher, it returns a full object of Teacher, else
     * a null object.
     */
    public Teacher thereIsAlreadyATeacher(Long pn) {
        Teacher foundTeacher = db.findTeacherById(pn);
        if (foundTeacher == null) {
            ue.printText("No such teacher");
        } else {
            return foundTeacher;
        }
        return null;
    }

    /**
     * Checks to see if there already is a Student in the system. Takes an Long
     * as id search in database. Calls the findStudentById()-method from
     * dbController.
     *
     * @param pn an Long as id from user input
     * @return if there is a Student, it returns a full object of Student, else
     * a null object.
     */
    public Student thereIsAlreadyAStudent(Long pn) {
        Student foundStudent = db.findStudentById(pn);
        if (foundStudent == null) {
            ue.printText("No such student");
        } else {
            return foundStudent;
        }
        return null;
    }

    /**
     * Creates a list of Students from all students in database
     *
     * @return a list of Students
     */
    public List<Student> aListOFStudentFromDataBase() {
        List<Student> listOfStudents = db.getAllStudents();
        return listOfStudents;
    }

    /**
     * Creates a list of EDUCATIONS from all educations in database
     *
     * @return a list of Educations
     */
    public List<Education> aListOfEducationsFromDataBase() {
        List<Education> listOfEducations = db.getAllEducations();
        return listOfEducations;
    }

    /**
     * Creates a list of Teachers from all teachers in database
     *
     * @return a list of Teachers
     */
    public List<Teacher> aListOfTeachersFromDataBase() {
        List<Teacher> listOfTeachers = db.getAllTeachers();
        return listOfTeachers;
    }

    /**
     * Creates a list of Courses from all courses in database
     *
     * @return a list of Courses
     */
    public List<Course> aListOfCoursesFromDataBase() {
        List<Course> listOfCourses = db.getAllCourses();
        return listOfCourses;
    }


}
