package buisnesslogic;

import static buisnesslogic.MenuTexts.*;
import buisnesslogic.userEnvironmentAccess.UserEnviromentAccess;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;
import theschoolmanagmentsystem.databaseControl.dbControlJpaImpl;
import theschoolmanagmentsystem.databaseControl.dbDAO;
import theschoolmanagmentsystem.domain.*;
import theschoolmanagmentsystem.domain.exceptions.*;
import theschoolmanagmentsystem.userEnvironment.UserEnviromentCommandPrImpl;

public class Menus {

    //Instatiate Command Prompt Environment
    UserEnviromentAccess ue = new UserEnviromentCommandPrImpl();
    //To access user environment use 'ue' variable.

    //Instantiates database. In this case it uses local mysql database 
    dbDAO db = new dbControlJpaImpl();
    //To access databes use 'db' variable

    StatisticsInterface stat = new StatisticsImpl();

    /**
     * Main menu. Users switches between 5 choices: 1. Course menu. 2. Education
     * menu. 3. Student menu. 4. Teacher menu 0. Exit application.
     */
    public void mainMenu() {
        while (true) {
            int mainChoice = intInRangeFromUser(0, 5, mainMenuText);
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
                case 5:
                    statMenu();
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }

    /**
     * Stat menu
     */
    public void statMenu() {
        boolean statMenuLoop = true;
        while (statMenuLoop) {
            int statMenuChoice = intInRangeFromUser(0, 3, statMenuTexts);
            switch (statMenuChoice) {
                case 1:
                    studentStat();
                    break;
                case 2:
                    teacherStat();
                    break;
                case 3:
                    coursesPerEducationStat();
                    break;
                case 0:
                    statMenuLoop = false;
                    break;
            }
        }
    }

    /**
     * Statistics about students, print the average age of all students in the
     * system.
     */
    public void studentStat() {
        boolean studentStatLoop = true;
        while (studentStatLoop) {
            ue.printText(averageAgeOfStudentsText);
            double averageAge = stat.getAverageAgeOfStudents();
            ue.printText("\n" + averageAge);
            int userChoice = intInRangeFromUser(0, 0, pressZeroForBackText);
            if (userChoice == 0) {
                studentStatLoop = false;
            }
        }
    }

    /**
     * Statistics about teachers, print the average age of all teachers in the
     * system.
     */
    public void teacherStat() {
        boolean teacherStatLoop = true;
        while (teacherStatLoop) {
            ue.printText(averageAgeOfTeachersText);
            double averageAge = stat.getAverageAgeOfTeachers();
            ue.printText("\n" + averageAge);
            int userChoice = intInRangeFromUser(0, 0, pressZeroForBackText);
            if (userChoice == 0) {
                teacherStatLoop = false;
            }
        }
    }

    /**
     * Statistics about educations, print the average number of courses and
     * students from the system.
     */
    public void coursesPerEducationStat() {
        boolean coursePerEducationStatLoop = true;
        while (coursePerEducationStatLoop) {
            double numberOFCoursesPerEducation = stat.getAverageNrOfCoursesPerEducation();
            double numberOFStudentsPerEducation = stat.getAverageNrOfStudentsPerEducation();
            ue.printText("\nAverage number of Courses per Education:" + "\n" + numberOFCoursesPerEducation);
            ue.printText("\nAverage number of Students per Education:" + "\n" + numberOFStudentsPerEducation);
            int userChoice = intInRangeFromUser(0, 0, pressZeroForBackText);
            if (userChoice == 0) {
                coursePerEducationStatLoop = false;
            }
        }
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
            int teacherMenuChoice = intInRangeFromUser(0, 2, teachersMenuText);
            switch (teacherMenuChoice) {
                case 1:
                    addNewTeacher();
                    break;
                case 2:
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
                int teacherEditMenuChoice = intInRangeFromUser(0, 2, teacherEditMenuText);
                switch (teacherEditMenuChoice) {
                    case 1:
                        editTeacher(teacherToEdit);
                        break;
                    case 2:
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
     * Deletes course from education. Needs a object of Education to edit list
     * of Course
     *
     * @param teacherToEdit An Object of Course
     * @return teacherToEdit
     */
    public Teacher deleteCourseFromTeacher(Teacher teacherToEdit) {
        boolean deleteCourseLoop = true;
        while (deleteCourseLoop) {
            List<Course> courseList = teacherToEdit.getCourses();
            if (courseList == null) {
                ue.printText("No course attached to this teacher...");
                deleteCourseLoop = false;
            } else {
                ue.printList(courseList);

                ue.printText("\nEnter course id to delete it from this teacher:" + "\n");
                Long id = idInput();
                for (Course next : courseList) {
                    if (id == next.getId()) {
                        ue.printText("\nRemoving " + next.getName() + " from " + teacherToEdit.getFirstName() + " " + teacherToEdit.getSurName());
                        courseList.remove(next);

                    }
                }
                deleteCourseLoop = false;
            }
        }
        return teacherToEdit;
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
            int yesNoChoice = intInRangeFromUser(0, 1, yesNoText);
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

            int editTeacherChoice = intInRangeFromUser(0, 3, editTeacherTexts);

            switch (editTeacherChoice) {
                case 1:
                    teacherToEdit = editTeacherInfo(pn);
                    editTeacherToDB(teacherToEdit);
                    break;
                case 2:
                    teacherToEdit = addCourseToTeacher(teacherToEdit);
                    editTeacherToDB(teacherToEdit);
                    break;
                case 3:
                    teacherToEdit = deleteCourseFromTeacher(teacherToEdit);
                    editTeacherToDB(teacherToEdit);
                    break;
                case 0:
                    editTeacherLoop = false;
                    break;
            }
        }
    }

    /**
     * Edit Teacher information. Call the method with Teacher person number
     *
     * @param pn
     * @return a object of Teacher
     */
    public Teacher editTeacherInfo(Long pn) {
        Teacher teacher = new Teacher();
        teacher.setPn(pn);
        String firstName = firstNameInput();
        String surName = surNameInput();

        teacher.setFirstName(firstName);
        teacher.setSurName(surName);

        teacher = addCourseToTeacher(teacher);

        return teacher;
    }

    /**
     * Set course to teacher. List of course is printed, user choose from list.
     *
     * @param teacher
     * @return teacher
     */
    public Teacher addCourseToTeacher(Teacher teacher) {
        Course courseToTeacher = printCourseListAndSearchAndCourse();
        if (courseToTeacher == null) {
            teacher.addCourse(null);
        } else {
            teacher.addCourse(courseToTeacher);
        }
        return teacher;
    }

    /**
     * Edit teacher to database
     *
     * @param teacher
     */
    public void editTeacherToDB(Teacher teacher) {

        try {
            db.editTeacher(teacher);
        } catch (Exception ex) {
            Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
            ue.printText("Entity not saved...");
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
            ue.printText("Teacher not added...");
        }

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
            int studentMenuChoice = intInRangeFromUser(0, 2, studentsMenuText);
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
                int studentEditMenuChoice = intInRangeFromUser(0, 2, studentEditMenuText);
                switch (studentEditMenuChoice) {
                    case 1:
                        editStudent(studentToEdit);
                        break;
                    case 2:
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
     * Deletes education from student. Needs a object of Student to delete
     * Education from Student
     *
     * @param studentToEdit An Object of Course
     * @return studentToEdit
     */
    public Student deleteEducationFromStudent(Student studentToEdit) {
        boolean deleteEducationLoop = true;
        while (deleteEducationLoop) {
            Education educationFromStudent = studentToEdit.getEducation();
            if (educationFromStudent == null) {
                ue.printText("No education attached to this student...");
                deleteEducationLoop = false;
            } else {
                ue.printText(studentToEdit.getEducation().getId() + " " + studentToEdit.getEducation().getName());

                ue.printText("\nEnter education id to delete it from this student:" + "\n");
                Long id = idInput();
                if (id == studentToEdit.getEducation().getId()) {
                    studentToEdit.setEducation(null);

                }
                deleteEducationLoop = false;
            }
        }
        return studentToEdit;
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
            int yesNoChoice = intInRangeFromUser(0, 1, yesNoText);
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

            int editStudentChoice = intInRangeFromUser(0, 3, editStudentTexts);

            switch (editStudentChoice) {
                case 1:
                    studentToEdit = editStudentInfo(pn);
                    editStudentToDB(studentToEdit);
                    break;
                case 2:
                    studentToEdit = addEducationToStudent(studentToEdit);
                    editStudentToDB(studentToEdit);
                    break;
                case 3:
                    studentToEdit = deleteEducationFromStudent(studentToEdit);
                    editStudentToDB(studentToEdit);
                    break;
                case 0:
                    editStudentLoop = false;
                    break;
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
            ue.printText("New Student added!");
        } else {
            ue.printText("Student not added...");
        }
    }

    /**
     * Edit Student information. Call the method with Student person number
     *
     * @param pn
     * @return a object of Student
     */
    public Student editStudentInfo(Long pn) {
        Student student = new Student();
        student.setPn(pn);

        String firstName = firstNameInput();
        String surName = surNameInput();

        student.setFirstName(firstName);
        student.setSurName(surName);

        student = addEducationToStudent(student);
        return student;
    }

    /**
     * Edit student to database
     *
     * @param student
     */
    public void editStudentToDB(Student student) {

        try {
            db.editStudent(student);
        } catch (Exception ex) {
            Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
            ue.printText("Entity not saved...");
        }

    }

    /**
     * Set education to student. List of education is printed, user choose from
     * list.
     *
     * @param student
     * @return student
     */
    public Student addEducationToStudent(Student student) {
        Education educationToStudent = printEducationListAndSearchAndFindEducation();
        if (educationToStudent == null) {
            student.setEducation(null);
        } else {
            student.setEducation(educationToStudent);
        }
        return student;
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
            int educationMenuChoice = intInRangeFromUser(0, 2, educationMenuText);
            switch (educationMenuChoice) {
                case 1:
                    addNewEducation();
                    break;
                case 2:
                    educationEditMenu();
                    break;
                case 0:
                    educationMenuLoop = false;
                    break;
            }
        }
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
                int courseEditMenuChoice = intInRangeFromUser(0, 2, educationEditMenuText);
                switch (courseEditMenuChoice) {
                    case 1:
                        editEducation(educationToEdit);
                        break;
                    case 2:
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
     * Deletes students from education. Needs a object of Education to edit list
     * of Student
     *
     * @param educationToEdit An Object of Course
     * @return educationToEdit
     */
    public Education deleteStudentsFromEducation(Education educationToEdit) {
        boolean deleteStudentLoop = true;
        while (deleteStudentLoop) {
            List<Student> studentList = educationToEdit.getStudents();
            if (studentList == null) {
                ue.printText("No students attached to this education...");
                deleteStudentLoop = false;
            } else {
                ue.printList(studentList);
                ue.printText("\nEnter student personal number to delete it from this Education:" + "\n");
                Long pn = personNumberInput();
                for (Student next : studentList) {
                    if (pn == next.getPn()) {
                        ue.printText("\nRemoving "
                                + next.getFirstName() + " "
                                + next.getSurName() + " from "
                                + educationToEdit.getName());
                        studentList.remove(next);
                    }
                }
                deleteStudentLoop = false;
            }
        }
        return educationToEdit;
    }

    /**
     * Deletes course from education. Needs a object of Education to edit list
     * of Course
     *
     * @param educationToEdit An Object of Course
     * @return educationToEdit
     */
    public Education deleteCourseFromEducation(Education educationToEdit) {
        boolean deleteCourseLoop = true;
        while (deleteCourseLoop) {
            List<Course> courseList = educationToEdit.getCourses();
            if (courseList == null) {
                ue.printText("No education attached to this course...");
                deleteCourseLoop = false;
            } else {
                ue.printList(courseList);

                ue.printText("\nEnter education id to delete it from this course:" + "\n");
                Long id = idInput();
                for (Course next : courseList) {
                    if (id == next.getId()) {
                        ue.printText("\nRemoving " + next.getName() + " from " + educationToEdit.getName());
                        courseList.remove(next);
                    }
                }
                deleteCourseLoop = false;
            }
        }
        return educationToEdit;
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
            int yesNoChoice = intInRangeFromUser(0, 1, yesNoText);
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

            int editEductionChoice = intInRangeFromUser(0, 5, editEducationTexts);

            switch (editEductionChoice) {
                case 1:
                    educationToEdit = editEducationInfo(id);
                    editEducationToDB(educationToEdit);
                    break;
                case 2:
                    educationToEdit = addStudentToEducation(educationToEdit);
                    editEducationToDB(educationToEdit);
                    break;
                case 3:
                    educationToEdit = addCourseToEducation(educationToEdit);
                    editEducationToDB(educationToEdit);
                    break;
                case 4:
                    educationToEdit = deleteStudentsFromEducation(educationToEdit);
                    editEducationToDB(educationToEdit);
                    break;
                case 5:
                    educationToEdit = deleteCourseFromEducation(educationToEdit);
                    editEducationToDB(educationToEdit);
                    break;
                case 0:
                    editEducationLoop = false;
                    break;
            }
        }
    }

    /**
     * Add new Education. Creates an Object of Education. Calls
     * db.createEducation()-method from db-controller.
     *
     */
    public void addNewEducation() {
        ue.printText("Add new Education");
        Long id = idInput();
        Education newOrNotEducation = thereIsAlreadyAEducation(id);

        Education newEducation = (newOrNotEducation == null) ? editEducationInfo(id) : newOrNotEducation;

        if (newEducation != null) {
            db.createEducation(newEducation);
            ue.printText("New Education added!");
        } else {
            ue.printText("Education not added..");
        }
    }

    /**
     * Edit Education information. Call the method with Education id number
     *
     * @param id
     * @return a object of Course
     */
    public Education editEducationInfo(Long id) {
        Education education = new Education();

        education.setId(id);
        String name = nameInput();
        String start = startDateInput();
        String end = endDateInput();
        String schoolBreak = schoolBreakInput();

        education.setName(name);
        education.setStart(start);
        education.setEnd(end);
        education.setSchoolBreak(schoolBreak);

        education = addCourseToEducation(education);

        education = addStudentToEducation(education);

        return education;
    }

    /**
     * Set student to education. List of student is printed, user choose from
     * list.
     *
     * @param education
     * @return education
     */
    public Education addStudentToEducation(Education education) {
        Student studentToEducation = printStudentListAndSearchAndFindStudent();
        if (studentToEducation == null) {
            education.addStudent(null);
        } else {
            education.addStudent(studentToEducation);
        }
        return education;
    }

    /**
     * Edit education to database
     *
     * @param education
     */
    public void editEducationToDB(Education education) {

        try {
            db.editEducation(education);
        } catch (Exception ex) {
            Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
            ue.printText("Entity not saved...");
        }

    }

    /**
     * Set course to education. List of course is printed, user choose from
     * list.
     *
     * @param education
     * @return education
     */
    public Education addCourseToEducation(Education education) {
        Course courseToEducation = printCourseListAndSearchAndCourse();
        if (courseToEducation == null) {
            education.addCourse(null);
        } else {
            education.addCourse(courseToEducation);
        }
        return education;
    }

    /**
     * Course Menu. Switches between 3 choices: 1. Calls addNewCourse()-method
     * 2. Calls courseEditMenu()-method 3. Back to Main Menu
     */
    public void courseMenu() {
        boolean courseMenuLoop = true;
        while (courseMenuLoop) {
            // List<Course> listOfCourses = aListOfCoursesFromDataBase();
            ue.printList(db.getAllCourses());
            int courseMenuChoice = intInRangeFromUser(0, 2, coursesMenuText);
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
                int courseEditMenuChoice = intInRangeFromUser(0, 2, courseEditMenuText);
                switch (courseEditMenuChoice) {
                    case 1:
                        editCourse(courseToEdit);
                        break;
                    case 2:
                        deleteCourse(courseToEdit);
                        courseEditMenuLoop = false;
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
            int yesNoChoice = intInRangeFromUser(0, 1, yesNoText);
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
     * Deletes teacher from course. Needs a object of Course to edit list of
     * Teacher
     *
     * @param courseToEdit An Object of Course
     * @return courseToEdit
     */
    public Course deleteTeachersFromCourse(Course courseToEdit) {
        boolean deleteEducationLoop = true;
        while (deleteEducationLoop) {
            List<Teacher> teacherList = courseToEdit.getTeachers();
            if (teacherList == null) {
                ue.printText("No teachers attached to this course...");
                deleteEducationLoop = false;
            } else {
                ue.printList(teacherList);
                ue.printText("\nEnter teacher personal number to delete it from this course:" + "\n");
                Long pn = personNumberInput();
                for (Teacher next : teacherList) {
                    if (pn == next.getPn()) {
                        ue.printText("\nRemoving "
                                + next.getFirstName() + " "
                                + next.getSurName() + " from "
                                + courseToEdit.getName());
                        teacherList.remove(next);
                    }
                }
                deleteEducationLoop = false;
            }
        }
        return courseToEdit;
    }

    /**
     * Deletes education from course. Needs a object of Course to edit list of
     * Education
     *
     * @param courseToEdit An Object of Course
     * @return courseToEdit
     */
    public Course deleteEducationFromCourse(Course courseToEdit) {
        boolean deleteEducationLoop = true;
        while (deleteEducationLoop) {
            List<Education> educationList = courseToEdit.getEducations();
            if (educationList == null) {
                ue.printText("No education attached to this course...");
                deleteEducationLoop = false;
            } else {
                ue.printList(educationList);

                ue.printText("\nEnter education id to delete it from this course:" + "\n");
                Long id = idInput();
                for (Education next : educationList) {
                    if (id == next.getId()) {
                        ue.printText("\nRemoving " + next.getName() + " from " + courseToEdit.getName());
                        educationList.remove(next);

                    }
                }
                deleteEducationLoop = false;
            }
        }
        return courseToEdit;
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

            int editCourseChoice = intInRangeFromUser(0, 5, editCourseTexts);

            switch (editCourseChoice) {
                case 1:
                    courseToEdit = editCourseInfo(id);
                    editCourseToDB(courseToEdit);
                    break;
                case 2:
                    courseToEdit = addEducationToCourse(courseToEdit);
                    editCourseToDB(courseToEdit);
                    break;
                case 3:
                    courseToEdit = addTeacherToCourse(courseToEdit);
                    editCourseToDB(courseToEdit);
                    break;
                case 4:
                    courseToEdit = deleteEducationFromCourse(courseToEdit);
                    editCourseToDB(courseToEdit);
                    break;
                case 5:
                    courseToEdit = deleteTeachersFromCourse(courseToEdit);
                    editCourseToDB(courseToEdit);
                    break;
                case 0:
                    editCourseLoop = false;
                    break;
            }
        }
    }

    /**
     * Edit course to database
     *
     * @param courseToEdit
     */
    public void editCourseToDB(Course courseToEdit) {
        try {
            db.editCourse(courseToEdit);
        } catch (Exception ex) {
            Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
            ue.printText("Entity not saved...");
        }
    }

    /**
     * Edit Course information. Call the method with Course id number
     *
     * @param id
     * @return a object of Course
     */
    public Course editCourseInfo(Long id) {
        Course course = new Course();
        course.setId(id);
        String name = ue.getStringInputFromUser("Name:  ");
        String start = ue.getStringInputFromUser("Start date:  ");
        String end = ue.getStringInputFromUser("End date:  ");
        String schoolBreak = ue.getStringInputFromUser("Schol break:  ");

        course.setName(name);
        course.setStart(start);
        course.setEnd(end);
        course.setSchoolBreak(schoolBreak);

        // course = addEducationToCourse(course);
        // course = addTeacherToCourse(course);
        return course;
    }

    /**
     * Set teacher to course. List of teacher is printed, user choose from list.
     *
     * @param course
     * @return course
     */
    public Course addTeacherToCourse(Course course) {
        Teacher teacherToCourse = printTeacherListAndSearchAndFindTeacher();
        if (teacherToCourse == null) {
            course.addTeacher(null);
        } else {
            course.addTeacher(teacherToCourse);
        }
        return course;
    }

    /**
     * Set education to course. List of eduaction is printed, user choose from
     * list.
     *
     * @param course
     * @return a object of Course
     */
    public Course addEducationToCourse(Course course) {
        Education educationToCourse = printEducationListAndSearchAndFindEducation();
        if (educationToCourse == null) {
            course.addEducation(null);
        } else {
            course.addEducation(educationToCourse);
        }
        return course;
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

        if (newCourse != null && newOrNotCourse == null) {
            db.createCourse(newCourse);
            ue.printText("New Course added!");
        } else {
            ue.printText("Course not added");
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
     * Creates a list of Students from all students in database
     *
     * @return a list of Students
     */
    public List<Student> aListOFStudentFromDataBase() {
        List<Student> listOfStudents = db.getAllStudents();
        return listOfStudents;
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
     * Checks to see if there already is a Education in the system. Takes an
     * Long as id search in database. Calls the findEducationById()-method from
     * dbController.
     *
     * @param id Long from user input.
     * @return if there is a Education, it returns a full object of Education,
     * else a null object.
     */
    public Education thereIsAlreadyAEducation(Long id) {
        Education foundEducation = null;
        try {
            foundEducation = db.findEducationById(id);
        } catch (EntityNotFoundException e) {
        }  // Education was not found
        if (foundEducation != null) { // There was such Education already
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
        Course foundCourse = null;
        try {
            foundCourse = db.findCourseById(id);

        } catch (EntityNotFoundException e) {  // Course was not found
        }
        if (foundCourse != null) {   // There was such Course already
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
        Teacher foundTeacher = null;
        try {
            foundTeacher = db.findTeacherById(pn);
        } catch (EntityNotFoundException e) {
        }  // Teacher not found

        if (foundTeacher != null) { // There was such Teacher already
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
        Student foundStudent = null;
        try {
            foundStudent = db.findStudentById(pn);
        } catch (EntityNotFoundException e) {
        }  // Student not found

        if (foundStudent != null) { // There was such Student already
            ue.printText("There is already a Student with that person number in the system");
            return foundStudent;
        }
        return null;
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
        ue.printText("Enter Teacher ");
        Long pn = personNumberInput();
        Teacher teacherFromDb = db.findTeacherById(pn);
        return teacherFromDb;
    }

    /**
     * Searching after a course in database
     *
     * @return an object of Course
     */
    public Course searchAndFindCourse() {
        ue.printText("Enter Course ");
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
                ue.printText("Invalid input, try again");
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
                ue.printText("Invalid input, try again");
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
                ue.printText("Invalid input, try again");
            }
        }
        return inPut;
    }

    /**
     * String input for id. Will loop while it is not correct
     *
     * @return a String with validaded id number
     */
    public Long idInput() {
        boolean isNotValid = true; // Must run at least once
        String inPut = ue.getStringInputFromUser("Course id? :  ");
        Long retur = null;
        while (isNotValid) {
            try {
                retur = Long.parseLong(inPut);
                isNotValid = false; // Parsing successfull
            } catch (NumberFormatException e) { // Parsing failed
                ue.printText("Invalid input.");
                inPut = ue.getStringInputFromUser("Course id? :");

            }
        }
        return retur;
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
                ue.printText("Invalid input, try again");
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
                ue.printText("Invalid input, try again");
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
                ue.printText("Invalid input, try again");
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
                ue.printText("Invalid input, try again");
            }
        }
        return inPut;
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
    public int intInRangeFromUser(int min, int max, String menyText) {
        String input = ue.getStringInputFromUser(menyText);
        int retur = -1;
        boolean wrongInput = true; // Run at least once
        while (wrongInput) {
            try {
                retur = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                ue.printText("Numbers only\n");
                input = ue.getStringInputFromUser(menyText);
            }
            if (retur < min || retur > max) {
                ue.printText("Not in range\n");
                input = ue.getStringInputFromUser(menyText);
            } else {
                wrongInput = false;
            }
                
        }
        return retur;
    }
}
