import java.sql.*;
import java.util.Scanner;


public class DATABASE1 {

    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "root name", "password");
                System.out.println("Connected to the database successfully");
            } catch (SQLException error) {
                System.out.println("Not Connected to the database successfully");
                return;  // Exit if there's a connection error
            }


            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\nUniversity Database Operations:");
                System.out.println("1. Insert Student");
                System.out.println("2. Delete Student");
                System.out.println("3. Update Student");
                System.out.println("4. Display Student");
                System.out.println("5. Insert Instructor");
                System.out.println("6. Delete Instructor");
                System.out.println("7. Update Instructor");
                System.out.println("8. Display Instructor");
                System.out.println("9. Insert Course");
                System.out.println("10.Delete Course");
                System.out.println("11.Update Course");
                System.out.println("12.Display Course");
                System.out.println("13.Insert Course_Offering");
                System.out.println("14.Delete Course_Offering");
                System.out.println("15.Update Course_Offering");
                System.out.println("16.Display Course_offerings");
                System.out.println("17.Enroll Student in Course");
                System.out.println("18.Withdraw Student from Course");
                System.out.println("19.Update Enrollment Grade");
                System.out.println("20.Display Enrollment");
                System.out.println("21.Assign Instructor to Course");
                System.out.println("22.Unassign Instructor from Course");
                System.out.println("23.Update Teaching Timing");
                System.out.println("24.Display Teached_by");
                System.out.println("25.Exit");
                System.out.print("Select an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        insertStudent(connection, scanner);
                        break;
                    case 2:
                        deleteStudent(connection, scanner);
                        break;
                    case 3:
                        updateStudent(connection, scanner);
                        break;
                    case 4:
                    	displayStudent(connection,scanner);
                    	break;
                    case 5:
                        insertInstructor(connection, scanner);
                        break;
                    case 6:
                        deleteInstructor(connection, scanner);
                        break;
                    case 7:
                        updateInstructor(connection, scanner);
                        break;
                    case 8:
                    	displayInstructor(connection);
                    	break;
                    case 9:
                        insertCourse(connection, scanner);
                        break;
                    case 10:
                        deleteCourse(connection, scanner);
                        break;
                    case 11:
                        updateCourse(connection, scanner);
                        break;
                    case 12:
                    	displayCourse(connection);
                    	break;
                    case 13:
                        insertCourseOffering(connection, scanner);
                        break;
                    case 14:
                        deleteCourseOffering(connection, scanner);
                        break;
                    case 15:
                        updateCourseOffering(connection, scanner);
                        break;
                    case 16:
                    	displayCourseOfferings(connection);
                    	break;
                    case 17:	
                        enrollStudent(connection, scanner);
                        break;
                    case 18:
                        withdrawStudent(connection, scanner);
                        break;
                    case 19:
                        updateEnrollmentGrade(connection, scanner);
                        break;
                    case 20:
                    	displayEnroll(connection);
                    	break;
                    case 21:
                        assignInstructorToCourse(connection, scanner);
                        break;
                    case 22:
                        unassignInstructorFromCourse(connection, scanner);
                        break;
                    case 23:
                        updateTeachingTiming(connection, scanner);
                        break;
                    case 24:
                    	displayTeached_by(connection);
                    	break;
                    case 25:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
               
                }
               }

            connection.close();
            System.out.println("Disconnected from the database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Program: ");
        String program = scanner.nextLine();

        String insertQuery = "INSERT INTO Student (Student_id, Name, Program) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, program);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
        
    }

    private static void deleteStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID to delete: ");
        String studentId = scanner.nextLine();

        String deleteQuery = "DELETE FROM Student WHERE Student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, studentId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }

    private static void updateStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID to update: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter new Student Name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new Program: ");
        String newProgram = scanner.nextLine();

        String updateQuery = "UPDATE Student SET Name = ?, Program = ? WHERE Student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newProgram);
            preparedStatement.setString(3, studentId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
            
        }
    }
    private static void displayStudent(Connection connection, Scanner scanner)throws SQLException{
    String selectStudentQuery = "SELECT * FROM Student";
    try (PreparedStatement selectStudentStmt = connection.prepareStatement(selectStudentQuery);
         ResultSet resultSet = selectStudentStmt.executeQuery()) {
        while (resultSet.next()) {
            String studentId = resultSet.getString("Student_id");
            String name = resultSet.getString("Name");
            String program = resultSet.getString("Program");
            System.out.println("Student ID: " + studentId + ", Name: " + name + ", Program: " + program);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    private static void insertInstructor(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Instructor ID: ");
        String instructorId = scanner.nextLine();
        System.out.print("Enter Instructor Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter Designation: ");
        String designation = scanner.nextLine();

        String insertQuery = "INSERT INTO Instructor (Instructor_id, Instructor_name, Department, Designation) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, instructorId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, designation);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }
    private static void deleteInstructor(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Instructor ID to delete: ");
        String instructorId = scanner.nextLine();

        String deleteQuery = "DELETE FROM Instructor WHERE Instructor_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, instructorId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
    private static void updateInstructor(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Instructor ID to update: ");
        String instructorId = scanner.nextLine();
        System.out.print("Enter new Instructor Name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new Department: ");
        String newDepartment = scanner.nextLine();
        System.out.print("Enter new Designation: ");
        String newDesignation = scanner.nextLine();

        String updateQuery = "UPDATE Instructor SET Instructor_name = ?, Department = ?, Designation = ? WHERE Instructor_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newDepartment);
            preparedStatement.setString(3, newDesignation);
            preparedStatement.setString(4, instructorId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }
    private static void displayInstructor(Connection connection) throws SQLException {
        String selectInstructorQuery = "SELECT * FROM Instructor";
        try (PreparedStatement selectInstructorStmt = connection.prepareStatement(selectInstructorQuery);
             ResultSet resultSet = selectInstructorStmt.executeQuery()) {
            while (resultSet.next()) {
                String instructorId = resultSet.getString("Instructor_id");
                String instructorName = resultSet.getString("Instructor_name");
                String department = resultSet.getString("Department");
                String designation = resultSet.getString("Designation");
                System.out.println("Instructor ID: " + instructorId + ", Name: " + instructorName + ", Department: " + department + ", Designation: " + designation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void insertCourse(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Course Number: ");
        String courseNo = scanner.nextLine();
        System.out.print("Enter Syllabus: ");
        String syllabus = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Prerequisites: ");
        String prerequisites = scanner.nextLine();

        String insertQuery = "INSERT INTO Course (Course_no, Syllabus, Title, Credits, Prequisites) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, courseNo);
            preparedStatement.setString(2, syllabus);
            preparedStatement.setString(3, title);
            preparedStatement.setInt(4, credits);
            preparedStatement.setString(5, prerequisites);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }
    private static void deleteCourse(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Course Number to delete: ");
        String courseNo = scanner.nextLine();

        String deleteQuery = "DELETE FROM Course WHERE Course_no = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, courseNo);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
    private static void updateCourse(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Course Number to update: ");
        String courseNo = scanner.nextLine();
        System.out.print("Enter new Syllabus: ");
        String newSyllabus = scanner.nextLine();
        System.out.print("Enter new Title: ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new Credits: ");
        int newCredits = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new Prerequisites: ");
        String newPrerequisites = scanner.nextLine();

        String updateQuery = "UPDATE Course SET Syllabus = ?, Title = ?, Credits = ?, Prequisites = ? WHERE Course_no = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newSyllabus);
            preparedStatement.setString(2, newTitle);
            preparedStatement.setInt(3, newCredits);
            preparedStatement.setString(4, newPrerequisites);
            preparedStatement.setString(5, courseNo);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }
    private static void displayCourse(Connection connection) throws SQLException {
        String selectCourseQuery = "SELECT * FROM Course";
        try (PreparedStatement selectCourseStmt = connection.prepareStatement(selectCourseQuery);
             ResultSet resultSet = selectCourseStmt.executeQuery()) {
            while (resultSet.next()) {
                String courseNumber = resultSet.getString("Course_no");
                String syllabus = resultSet.getString("Syllabus");
                String title = resultSet.getString("Title");
                int credits = resultSet.getInt("Credits");
                String prerequisites = resultSet.getString("Prequisites");
                System.out.println("Course Number: " + courseNumber + ", Syllabus: " + syllabus + ", Title: " + title + ", Credits: " + credits + ", Prerequisites: " + prerequisites);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void insertCourseOffering(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Classroom: ");
        String classroom = scanner.nextLine();
        System.out.print("Enter Semester (e.g., 04): ");
        String semester = scanner.nextLine();
        System.out.print("Enter Year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Section Number: ");
        String sectionNo = scanner.nextLine();
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();

        String insertQuery = "INSERT INTO Course_offerings (Classroom, Sem, Year, Section_no, Courseid) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, classroom);
            preparedStatement.setString(2, semester);
            preparedStatement.setInt(3, year);
            preparedStatement.setString(4, sectionNo);
            preparedStatement.setString(5, courseId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }
    private static void deleteCourseOffering(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Section Number to delete: ");
        String sectionNo = scanner.nextLine();
        System.out.print("Enter Course ID for the section: ");
        String courseId = scanner.nextLine();

        String deleteQuery = "DELETE FROM Course_offerings WHERE Section_no = ? AND Courseid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, sectionNo);
            preparedStatement.setString(2, courseId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
    private static void updateCourseOffering(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Section Number to update: ");
        String sectionNo = scanner.nextLine();
        System.out.print("Enter Course ID for the section: ");
        String courseId = scanner.nextLine();
        System.out.print("Enter new Classroom: ");
        String newClassroom = scanner.nextLine();
        System.out.print("Enter new Semester (e.g., 04): ");
        String newSemester = scanner.nextLine();
        System.out.print("Enter new Year: ");
        int newYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String updateQuery = "UPDATE Course_offerings SET Classroom = ?, Sem = ?, Year = ? WHERE Section_no = ? AND Courseid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newClassroom);
            preparedStatement.setString(2, newSemester);
            preparedStatement.setInt(3, newYear);
            preparedStatement.setString(4, sectionNo);
            preparedStatement.setString(5, courseId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }
    private static void displayCourseOfferings(Connection connection) throws SQLException {
        String selectCourseOfferingsQuery = "SELECT * FROM Course_offerings";
        try (PreparedStatement selectCourseOfferingsStmt = connection.prepareStatement(selectCourseOfferingsQuery);
             ResultSet resultSet = selectCourseOfferingsStmt.executeQuery()) {
            while (resultSet.next()) {
                String classroom = resultSet.getString("Classroom");
                int sem = resultSet.getInt("Sem");
                int year = resultSet.getInt("Year");
                String sectionNo = resultSet.getString("Section_no");
                String courseID = resultSet.getString("Courseid");
                System.out.println("Classroom: " + classroom + ", Semester: " + sem + ", Year: " + year + ", Section No: " + sectionNo + ", Course ID: " + courseID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void enrollStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();
        System.out.print("Enter Grade: ");
        String grade = scanner.nextLine();

        String insertQuery = "INSERT INTO Enroll (Stud_id, Course_id, Grade) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);
            preparedStatement.setString(3, grade);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }
    private static void withdrawStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID to withdraw: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course ID to withdraw from: ");
        String courseId = scanner.nextLine();

        String deleteQuery = "DELETE FROM Enroll WHERE Stud_id = ? AND Course_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
    private static void updateEnrollmentGrade(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();
        System.out.print("Enter new Grade: ");
        String newGrade = scanner.nextLine();

        String updateQuery = "UPDATE Enroll SET Grade = ? WHERE Stud_id = ? AND Course_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newGrade);
            preparedStatement.setString(2, studentId);
            preparedStatement.setString(3, courseId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }
    private static void displayEnroll(Connection connection) throws SQLException {
        String selectEnrollQuery = "SELECT * FROM Enroll";
        try (PreparedStatement selectEnrollStmt = connection.prepareStatement(selectEnrollQuery);
             ResultSet resultSet = selectEnrollStmt.executeQuery()) {
            while (resultSet.next()) {
                String studentId = resultSet.getString("Stud_id");
                String courseId = resultSet.getString("Course_id");
                String grade = resultSet.getString("Grade");
                System.out.println("Student ID: " + studentId + ", Course ID: " + courseId + ", Grade: " + grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void assignInstructorToCourse(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Instructor ID: ");
        String instructorId = scanner.nextLine();
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();
        System.out.print("Enter Teaching Timing: ");
        String timing = scanner.nextLine();

        String insertQuery = "INSERT INTO Teached_by (Instructor_no, Course_ID, timing) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, instructorId);
            preparedStatement.setString(2, courseId);
            preparedStatement.setString(3, timing);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }
    private static void unassignInstructorFromCourse(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Instructor ID to unassign: ");
        String instructorId = scanner.nextLine();
        System.out.print("Enter Course ID to unassign from: ");
        String courseId = scanner.nextLine();

        String deleteQuery = "DELETE FROM Teached_by WHERE Instructor_no = ? AND Course_ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, instructorId);
            preparedStatement.setString(2, courseId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
    private static void updateTeachingTiming(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Instructor ID: ");
        String instructorId = scanner.nextLine();
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();
        System.out.print("Enter new Teaching Timing: ");
        String newTiming = scanner.nextLine();

        String updateQuery = "UPDATE Teached_by SET timing = ? WHERE Instructor_no = ? AND Course_ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newTiming);
            preparedStatement.setString(2, instructorId);
            preparedStatement.setString(3, courseId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }
    private static void displayTeached_by(Connection connection) throws SQLException {
        String selectTeachedByQuery = "SELECT * FROM Teached_by";
        try (PreparedStatement selectTeachedByStmt = connection.prepareStatement(selectTeachedByQuery);
             ResultSet resultSet = selectTeachedByStmt.executeQuery()) {
            while (resultSet.next()) {
                String instructorId = resultSet.getString("Instructor_no");
                String courseId = resultSet.getString("Course_ID");
                String timing = resultSet.getString("timing");
                System.out.println("Instructor ID: " + instructorId + ", Course ID: " + courseId + ", Timing: " + timing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}