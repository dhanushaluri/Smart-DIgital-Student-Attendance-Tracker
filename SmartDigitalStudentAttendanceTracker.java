import java.util.*;

class AttendanceRecord {
    String date;
    boolean present;

    AttendanceRecord(String date, boolean present) {
        this.date = date;
        this.present = present;
    }
}

class Student {
    int rollNo;
    String name;
    String section;
    ArrayList<AttendanceRecord> attendanceList;

    Student(int rollNo, String name, String section) {
        this.rollNo = rollNo;
        this.name = name;
        this.section = section;
        this.attendanceList = new ArrayList<>();
    }

    void markAttendance(String date, boolean present) {
        attendanceList.add(new AttendanceRecord(date, present));
    }

    double getAttendancePercentage() {
        if (attendanceList.isEmpty()) {
            return 0.0;
        }

        int presentCount = 0;
        for (AttendanceRecord record : attendanceList) {
            if (record.present) {
                presentCount++;
            }
        }

        return (presentCount * 100.0) / attendanceList.size();
    }

    void displayStudentReport() {
        System.out.println("\n----- Student Attendance Report -----");
        System.out.println("Roll No   : " + rollNo);
        System.out.println("Name      : " + name);
        System.out.println("Section   : " + section);
        System.out.println("Total Days: " + attendanceList.size());

        int presentCount = 0;
        int absentCount = 0;

        for (AttendanceRecord record : attendanceList) {
            if (record.present) {
                presentCount++;
            } else {
                absentCount++;
            }
        }

        System.out.println("Present   : " + presentCount);
        System.out.println("Absent    : " + absentCount);
        System.out.printf("Percentage: %.2f%%\n", getAttendancePercentage());

        System.out.println("Attendance Details:");
        for (AttendanceRecord record : attendanceList) {
            System.out.println(record.date + " --> " + (record.present ? "Present" : "Absent"));
        }
    }
}

public class SmartDigitalStudentAttendanceTracker {
    static HashMap<Integer, Student> studentMap = new HashMap<>();
    static Queue<String> updateQueue = new LinkedList<>();
    static Scanner sc = new Scanner(System.in);

    static void addStudent() {
        System.out.print("Enter Roll Number: ");
        int rollNo = sc.nextInt();
        sc.nextLine();

        if (studentMap.containsKey(rollNo)) {
            System.out.println("Student already exists.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Section: ");
        String section = sc.nextLine();

        Student student = new Student(rollNo, name, section);
        studentMap.put(rollNo, student);

        System.out.println("Student added successfully.");
    }

    static void markAttendance() {
        System.out.print("Enter Roll Number: ");
        int rollNo = sc.nextInt();
        sc.nextLine();

        Student student = studentMap.get(rollNo);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = sc.nextLine();

        System.out.print("Enter Attendance (P for Present / A for Absent): ");
        char status = sc.next().toUpperCase().charAt(0);

        boolean present = (status == 'P');
        student.markAttendance(date, present);

        updateQueue.add("Attendance marked for Roll No " + rollNo + " on " + date);
        System.out.println("Attendance recorded successfully.");
    }

    static void searchStudent() {
        System.out.print("Enter Roll Number to search: ");
        int rollNo = sc.nextInt();

        Student student = studentMap.get(rollNo);

        if (student == null) {
            System.out.println("Student not found.");
        } else {
            student.displayStudentReport();
        }
    }

    static void displayAllStudents() {
        if (studentMap.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        System.out.println("\n----- All Students -----");
        for (Student student : studentMap.values()) {
            System.out.println("Roll No: " + student.rollNo +
                               ", Name: " + student.name +
                               ", Section: " + student.section +
                               ", Attendance: " + String.format("%.2f", student.getAttendancePercentage()) + "%");
        }
    }

    static void showUpdateHistory() {
        if (updateQueue.isEmpty()) {
            System.out.println("No attendance updates available.");
            return;
        }

        System.out.println("\n----- Attendance Update History -----");
        for (String update : updateQueue) {
            System.out.println(update);
        }
    }

    static void sortStudentsByAttendance() {
        if (studentMap.isEmpty()) {
            System.out.println("No student data available.");
            return;
        }

        ArrayList<Student> studentList = new ArrayList<>(studentMap.values());

        studentList.sort((s1, s2) -> Double.compare(s2.getAttendancePercentage(), s1.getAttendancePercentage()));

        System.out.println("\n----- Students Sorted by Attendance Percentage -----");
        for (Student student : studentList) {
            System.out.println("Roll No: " + student.rollNo +
                               ", Name: " + student.name +
                               ", Percentage: " + String.format("%.2f", student.getAttendancePercentage()) + "%");
        }
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== Smart Digital Student Attendance Tracker =====");
            System.out.println("1. Add Student");
            System.out.println("2. Mark Attendance");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Show Attendance Update History");
            System.out.println("6. Sort Students by Attendance Percentage");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    markAttendance();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    displayAllStudents();
                    break;
                case 5:
                    showUpdateHistory();
                    break;
                case 6:
                    sortStudentsByAttendance();
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 7);
    }
}