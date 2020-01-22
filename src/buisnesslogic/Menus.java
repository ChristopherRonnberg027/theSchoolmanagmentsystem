package buisnesslogic;

import static buisnesslogic.MenuTexts.coursesMenuText;
import static buisnesslogic.MenuTexts.editMenuText;
import static buisnesslogic.MenuTexts.educationMenuText;
import static buisnesslogic.MenuTexts.enterIdToAddMenuText;
import static buisnesslogic.MenuTexts.enterPnToAddMenuText;
import static buisnesslogic.MenuTexts.mainMenuText;
import static buisnesslogic.MenuTexts.statMenuTexts;
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
//                case 5:
//                    // STAT MENU?
//                    break;
                case 0:
                    mainLoop = false;
                    break;
            }
        }
    }

    /**
     * Stat menu
     */
    public void statMenu() {
        boolean statMenuLoop = true;
        while (statMenuLoop) {
            int statMenuChoice = intInRangeFromUser(0, 4, ue.getIntegerInputFromUser(statMenuTexts));
            switch (statMenuChoice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    statMenuLoop = false;
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
            int educationMenuChoice = intInRangeFromUser(0, 2, ue.getIntegerInputFromUser(educationMenuText));
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
     * @param educationToDelete
     */
    public void deleteEducation(Education educationToDelete) {
        boolean isNotSure = true;
        while (isNotSure) {
            ue.printText("Delete " + educationToDelete.getName() + "?");
            int yesNoChoice = intInRangeFromUser(0, 1, ue.getIntegerInputFromUser("\n1. Yes\n0. No"));
            if (yesNoChoice == 1) {
                try {
                    db.destroyEducation(educationToDelete);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    ue.printText("Entity not found...");
                }
                isNotSure = false;
            } else if (yesNoChoice == 0) {
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
        Long id = educationToEdit.getId();
        boolean editEducationLoop = true;
        while (editEducationLoop) {
            ue.printText("Edit education?");
            int yesNoChoice = intInRangeFromUser(0, 1, ue.getIntegerInputFromUser("\n1. Yes\n0. No"));
            if (yesNoChoice == 0) {
                editEducationLoop = false;
            } else if (yesNoChoice == 1) {
                educationToEdit = editEducationInfo(id);
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
            Long id = idInput();
            Education educationToEdit = db.findEducationById(id);
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
        Long id = idInput();
        Education newOrNotEducation = thereIsAlreadyAEducation(id);

        Education newEducation = (newOrNotEducation == null) ? editEducationInfo(id) : newOrNotEducation;

        if (newEducation != null) {
            db.createEducation(newEducation);
            ue.printText("New Education added!");
        } else {
            ue.printText("No Education added..");
        }
    }

    /**
     * Edit Education information. Call the method with Education id number
     *
     * @param id
     * @return a object of Course
     */
    public Education editEducationInfo(Long id) {
        Education newEducation = new Education();
        newEducation.setId(id);
        String name = nameInput();
        String start = startDateInput();
        String end = endDateInput();
        String schoolBreak = schoolBreakInput();
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
        return newEducation;
    }

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
            Long id = idInput();
            Course courseToEdit = db.findCourseById(id);
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
     * @param courseToDelete An Object of Course
     */
    public void deleteCourse(Course courseToDelete) {
        boolean isNotSure = true;
        while (isNotSure) {
            ue.printText("Delete " + courseToDelete.getName() + "?");
            int yesNoChoice = intInRangeFromUser(0, 1, ue.getIntegerInputFromUser("\n1. Yes\n0. No"));
            if (yesNoChoice == 1) {
                try {
                    db.destroyCourse(courseToDelete);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    ue.printText("Entity not found...");
                }
                isNotSure = false;
            } else if (yesNoChoice == 0) {
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
        Long id = courseToEdit.getId();
        boolean editCourseLoop = true;
        while (editCourseLoop) {
            ue.printText("Edit Course?");
            int yesNoChoice = intInRangeFromUser(0, 1, ue.getIntegerInputFromUser("\n1. Yes\n0. No"));
            if (yesNoChoice == 0) {
                editCourseLoop = false;
            } else if (yesNoChoice == 1) {
                editCourseInfo(id);
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
        ue.printText("Add new Course");
        Long id = idInput();
        Course newOrNotCourse = thereIsAlreadyACourse(id);

        Course newCourse = (newOrNotCourse == null) ? editCourseInfo(id) : newOrNotCourse;

        if (newCourse != null) {
            db.createCourse(newCourse);
            ue.printText("New Course added!");
        } else {
            ue.printText("No Course added");
        }
    }

    /**
     * Edit Course information. Call the method with Course id number
     *
     * @param id
     * @return a object of Course
     */
    public Course editCourseInfo(Long id) {
        Course newCourse = new Course();
        newCourse.setId(id);
        String name = nameInput();
        String start = startDateInput();
        String end = endDateInput();
        String schoolBreak = schoolBreakInput();

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
        } else {
            newCourse.addTeacher(teacherToCourse);
        }
        return newCourse;
    }

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
            Long pn = idInput();
            Teacher teacherToEdit = db.findTeacherById(pn);
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
     * @param teacherToDelete
     */
    public void deleteTeacher(Teacher teacherToDelete) {
        boolean isNotSure = true;
        while (isNotSure) {
            ue.printText("Delete " + teacherToDelete.getFirstName() + " "
                    + teacherToDelete.getSurName() + "?");
            int yesNoChoice = intInRangeFromUser(0, 1, ue.getIntegerInputFromUser("\n1. Yes\n0. No"));
            if (yesNoChoice == 1) {
                try {
                    db.destroyTeacher(teacherToDelete);
                } catch (NonexistentEntityException ex) {
                    ue.printText("Entity not found");
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                }
                isNotSure = false;
            } else if (yesNoChoice == 0) {
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
        Long pn = teacherToEdit.getPn();
        boolean editTeacherLoop = true;
        while (editTeacherLoop) {
            ue.printText("Edit teacher?");
            int yesNoChoice = intInRangeFromUser(0, 1, ue.getIntegerInputFromUser("\n1. Yes\n0. No"));
            if (yesNoChoice == 0) {
                editTeacherLoop = false;
            } else if (yesNoChoice == 1) {
                teacherToEdit = editTeacherInfo(pn);
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
        Long pn = personNumberInput();
        Teacher newOrNotTeacher = thereIsAlreadyATeacher(pn);
        Teacher newTeacher = (newOrNotTeacher == null) ? editTeacherInfo(pn) : newOrNotTeacher;

        if (newTeacher != null) {
            db.createTeacher(newTeacher);
            ue.printText("New Teacher added!");
        } else {
            ue.printText("No Teacher added...");
        }

    }

    /**
     * Edit Teacher information. Call the method with Teacher person number
     *
     * @param pn
     * @return a object of Teacher
     */
    public Teacher editTeacherInfo(Long pn) {
        Teacher newTeacher = new Teacher();
        newTeacher.setPn(pn);
        String firstName = firstNameInput();
        String surName = surNameInput();
        newTeacher.setFirstName(firstName);
        newTeacher.setSurName(surName);
        Course courseToTeacher = printCourseListAndSearchAndCourse();
        if (courseToTeacher == null) {
            newTeacher.addCourse(null);
        } else {
            newTeacher.addCourse(courseToTeacher);
        }
        return newTeacher;
    }

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
            Long pn = personNumberInput();
            Student studentToEdit = db.findStudentById(pn);
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
     * @param studentToDelete
     */
    public void deleteStudent(Student studentToDelete) {
        boolean isNotSure = true;
        while (isNotSure) {
            ue.printText("Delete" + studentToDelete.getFirstName() + " " + studentToDelete.getSurName() + "?");
            int yesNoChoice = intInRangeFromUser(0, 1, ue.getIntegerInputFromUser("\n1. Yes\n0. No"));
            if (yesNoChoice == 1) {
                try {
                    db.destroyStudent(studentToDelete);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    ue.printText("Entity not found...");
                }
                isNotSure = false;
            } else if (yesNoChoice == 0) {
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
        Long pn = studentToEdit.getPn();
        boolean editStudentLoop = true;
        while (editStudentLoop) {
            ue.printText("Edit student?");
            int yesNoChoice = intInRangeFromUser(0, 1, ue.getIntegerInputFromUser("\n1. Yes\n0. No"));
            if (yesNoChoice == 0) {
                editStudentLoop = false;
            } else if (yesNoChoice == 1) {
                studentToEdit = editStudentInfo(pn);
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
        Long pn = personNumberInput();

        Student newOrNotStudent = thereIsAlreadyAStudent(pn);

        Student newStudent = (newOrNotStudent == null) ? editStudentInfo(pn) : newOrNotStudent;

        if (newStudent != null) {
            db.createStudent(newStudent);
            ue.printText("New student added!");
        } else {
            ue.printText("No student added...");
        }
    }

    /**
     * Edit Student information. Call the method with Student person number
     *
     * @param pn
     * @return a object of Student
     */
    public Student editStudentInfo(Long pn) {
        Student newStudent = new Student();
        newStudent.setPn(pn);

        String firstName = firstNameInput();
        String surName = surNameInput();

        newStudent.setFirstName(firstName);
        newStudent.setSurName(surName);

        Education educationToStudent = printEducationListAndSearchAndFindEducation();
        if (educationToStudent == null) {
            newStudent.setEducation(null);
        } else {
            newStudent.setEducation(educationToStudent);
        }
        return newStudent;
    }

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

    /**
     * String input for id.
     *
     * @return a String with validaded id number
     */
    public Long idInput() {
        boolean isValid = true;
        String inPut = "";
        while (isValid) {
            char first;
            inPut = ue.getStringInputFromUser("Id number: ");
            if (inPut.length() > 0) {
                first = inPut.charAt(0);
                isValid = false;
            } else {
                ue.printText("invalid input, try again");
            }
        }
        return Long.parseLong(inPut);
    }

    /**
     * Long input for person number.
     *
     * @return a Long with validaded person number number
     */
    public Long personNumberInput() {
        boolean isValid = false;
        String inPut = "";
        while (!isValid) {
            char first;
            inPut = ue.getStringInputFromUser("Person number: ");
            if (inPut.length() > 0) {
                first = inPut.charAt(0);
                isValid = true;
            } else {
                ue.printText("invalid input, try again");
            }
        }
        return Long.parseLong(inPut);
    }

    /**
     * String input for firstName.
     *
     * @return a String with validaded firstName
     */
    public String firstNameInput() {
        boolean isValid = true;
        String inPut = "";
        while (isValid) {
            char first;
            inPut = ue.getStringInputFromUser("First name: ");
            if (inPut.length() > 0) {
                first = inPut.charAt(0);
                isValid = false;
            } else {
                ue.printText("invalid input, try again");
            }
        }
        return inPut;
    }

    /**
     * String input for surName.
     *
     * @return a String with validaded surName
     */
    public String surNameInput() {
        boolean isValid = true;
        String inPut = "";
        while (isValid) {
            char first;
            inPut = ue.getStringInputFromUser("Sur name: ");
            if (inPut.length() > 0) {
                first = inPut.charAt(0);
                isValid = false;
            } else {
                ue.printText("invalid input, try again");
            }
        }
        return inPut;
    }

    /**
     * String input for name.
     *
     * @return a String with validaded name
     */
    public String nameInput() {
        boolean isValid = true;
        String inPut = "";
        while (isValid) {
            char first;
            inPut = ue.getStringInputFromUser("Name: ");
            if (inPut.length() > 0) {
                first = inPut.charAt(0);
                isValid = false;
            } else {
                ue.printText("invalid input, try again");
            }
        }
        return inPut;
    }

    /**
     * String input for start date.
     *
     * @return a String with validaded start date
     */
    public String startDateInput() {
        boolean isValid = true;
        String inPut = "";
        while (isValid) {
            char first;
            inPut = ue.getStringInputFromUser("Start date: ");
            if (inPut.length() > 0) {
                first = inPut.charAt(0);
                isValid = false;
            } else {
                ue.printText("invalid input, try again");
            }
        }
        return inPut;
    }

    /**
     * String input for end date.
     *
     * @return a String with validaded end date
     */
    public String endDateInput() {
        boolean isValid = true;
        String inPut = "";
        while (isValid) {
            char first;
            inPut = ue.getStringInputFromUser("End date: ");
            if (inPut.length() > 0) {
                first = inPut.charAt(0);
                isValid = false;
            } else {
                ue.printText("invalid input, try again");
            }
        }
        return inPut;
    }

    /**
     * String input for schoolBreak date.
     *
     * @return a String with validaded schoolBreak date
     */
    public String schoolBreakInput() {
        boolean isValid = true;
        String inPut = "";
        while (isValid) {
            char first;
            inPut = ue.getStringInputFromUser("School break: ");
            if (inPut.length() > 0) {
                first = inPut.charAt(0);
                isValid = false;
            } else {
                ue.printText("invalid input, try again");
            }
        }
        return inPut;
    }

    /**
     * Searching after a course in database
     *
     * @return an object of Course
     */
    public Course searchAndFindCourse() {
        ue.printText("Enter Course");
        Long courseId = idInput();
        Course courseFromDb = db.findCourseById(courseId);
        return courseFromDb;
    }

    /**
     * Searching after a student in database
     *
     * @return an object of Student
     */
    public Student searchAndFindStudent() {
        ue.printText("Enter Student ");
        Long pn = personNumberInput();
        Student studentFromDb = db.findStudentById(pn);
        return studentFromDb;
    }

    /**
     * Searching a education in database
     *
     * @return an object of Education
     */
    public Education searchAndFindEducation() {
        ue.printText("Enter Education ");
        Long educationId = idInput();
        Education educationFromDb = db.findEducationById(educationId);
        return educationFromDb;
    }

    /**
     * Searching a teacher in database
     *
     * @return an object of Teacher
     */
    public Teacher searchAndFindTeacher() {
        ue.printText("Enter Teacher");
        Long pn = personNumberInput();
        Teacher teacherFromDb = db.findTeacherById(pn);
        return teacherFromDb;
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
        } else {
            ue.printText("There is already a education with that id number in the system");
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
        } else {
            ue.printText("There is already a course with that id number in the system");
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
        } else {
            ue.printText("There is already a teacher with that person number in the system");
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
        } else {
            ue.printText("There is already a student with that person number in the system");
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
