package schoolrecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SchoolRecordsController {
    private static final String JAVA = "Java";
    private static final String PYTHON = "Python";
    private static final String CPP = "c++";
    private static final String KEREM_A_DIAK_NEVET = "Kérem a diák nevét: ";
    private static final String KEREM_A_TANTARGY_NEVET = "Kérem a tantárgy nevét: ";
    private static final String KEREM_A_TANAR_NEVET = "Kérem a tanár nevét: ";

    private final ClassRecords classRecords;

    private final List<Subject> subjectList = new ArrayList<>();

    private final List<Tutor> tutorList = new ArrayList<>();

    Scanner scanner;

    public SchoolRecordsController(ClassRecords classRecords, Scanner scanner) {
        this.classRecords = classRecords;
        this.scanner = scanner;
    }

    public void initSchool() {
        subjectList.add(new Subject(JAVA));
        subjectList.add(new Subject(PYTHON));
        subjectList.add(new Subject(CPP));

        tutorList.add(new Tutor("Kovács Béla", List.of(new Subject(JAVA), new Subject(CPP))));
        tutorList.add(new Tutor("Varga Mariann", List.of(new Subject(JAVA), new Subject(PYTHON))));
        tutorList.add(new Tutor("dr. Kotász Antal", List.of(new Subject(CPP), new Subject(PYTHON))));
    }

    public void printMenu() {
        System.out.printf("1. Diákok nevének listázása%s" +
                        "2. Diák név alapján keresése%s" +
                        "3. Diák létrehozása%s" +
                        "4. Diák név alapján törlése%s" +
                        "5. Diák feleltetése%s" +
                        "6. Osztályátlag kiszámolása%s" +
                        "7. Tantárgyi átlag kiszámolása%s" +
                        "8. Diákok átlagának megjelenítése%s" +
                        "9. Diák átlagának kiírása%s" +
                        "10. Diák tantárgyhoz tartozó átlagának kiírása%s" +
                        "11. Kilépés%s",
                System.lineSeparator(), System.lineSeparator(), System.lineSeparator(), System.lineSeparator(), System.lineSeparator(), System.lineSeparator(), System.lineSeparator(), System.lineSeparator(), System.lineSeparator(), System.lineSeparator(), System.lineSeparator());
    }

    public void runMenu(int menuPoint) {
        switch (menuPoint) {
            case 1 -> printStudentNames();
            case 2 -> findStudentByName();
            case 3 -> createStudent();
            case 4 -> removeStudent();
            case 5 -> repetitionStudent();
            case 6 -> printClassAverage();
            case 7 -> printSubjectAverage();
            case 8 -> printStudentsAverage();
            case 9 -> printStudentAverage();
            case 10 -> printStudentSubjectAverage();
        }
    }

    private void printStudentSubjectAverage() {
        System.out.println(KEREM_A_DIAK_NEVET);
        String studentName = scanner.nextLine();
        Student student = classRecords.findStudentByName(studentName);

        System.out.println(KEREM_A_TANTARGY_NEVET);
        String subjectName = scanner.nextLine();
        Subject subject = getSubject(subjectName);

        System.out.printf("A %s diák átlaga a %s tantárgyból: %.2f%s", student.getName(), subject.subjectName(), student.calculateSubjectAverage(subject), System.lineSeparator());
    }

    private Subject getSubject(String subjectName) {
        return subjectList.stream()
                .filter(s -> s.subjectName().equals(subjectName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Nincs %s nevű tárgy.", subjectName)));
    }

    private void printStudentsAverage() {
        classRecords.listStudyResults()
                .forEach(studyResultByName -> System.out.printf("A %s diák átlaga: %.2f%s", studyResultByName.studentName(), studyResultByName.studyAverage(), System.lineSeparator()));
    }

    private void printStudentAverage() {
        System.out.println(KEREM_A_DIAK_NEVET);
        String studentName = scanner.nextLine();
        Student student = classRecords.findStudentByName(studentName);
        System.out.printf("a %s diák átlaga: %.2f", student.getName(), student.calculateAverage());
    }

    private void printSubjectAverage() {
        System.out.println(KEREM_A_TANTARGY_NEVET);
        String subjectName = scanner.nextLine();
        Subject subject = getSubject(subjectName);
        System.out.printf("a %s tantárgy átlaga: %.2f%s", subject.subjectName(), classRecords.calculateClassAverageBySubject(subject), System.lineSeparator());
    }

    private void printClassAverage() {
        System.out.printf("A %s osztály átlaga: %.2f%s", classRecords.getClassName(), classRecords.calculateClassAverage(), System.lineSeparator());
    }

    private void repetitionStudent() {
        Student student = classRecords.repetition();
        System.out.printf("Felel %s%s", student.getName(), System.lineSeparator());

        Mark mark = createMark();
        student.grading(mark);
    }

    private Mark createMark() {
        System.out.println(KEREM_A_TANAR_NEVET);
        String tutorName = scanner.nextLine();
        Tutor tutor = getTutor(tutorName);

        Subject subject = getSubject(tutorName, tutor);
        System.out.println("Kérem a jegyet: ");
        String markType = scanner.nextLine();
        return new Mark(markType, subject, tutor);
    }

    private Tutor getTutor(String tutorName) {
        return tutorList.stream()
                .filter(t -> t.getName().equals(tutorName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nincs ilyen nevű tanár."));
    }

    private Subject getSubject(String tutorName, Tutor tutor) {
        System.out.println(KEREM_A_TANTARGY_NEVET);
        String subjectName = scanner.nextLine();
        return subjectList.stream()
                .filter(s -> s.subjectName().equals(subjectName) && tutor.tutorTeachingSubject(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Nincs %s nevű tárgy, vagy a %s tanár nem oktatja.", subjectName, tutorName)));
    }

    private void removeStudent() {
        System.out.println(KEREM_A_DIAK_NEVET);
        String name = scanner.nextLine();
        classRecords.removeStudent(new Student(name));
    }

    private void createStudent() {
        System.out.println(KEREM_A_DIAK_NEVET);
        String name = scanner.nextLine();
        classRecords.addStudent(new Student(name));
    }

    private void findStudentByName() {
        System.out.println(KEREM_A_DIAK_NEVET);
        String name = scanner.nextLine();
        System.out.println(classRecords.findStudentByName(name).toString());
    }

    private void printStudentNames() {
        System.out.println(classRecords.listStudentNames());
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SchoolRecordsController schoolRecordsController = new SchoolRecordsController(new ClassRecords("3.c", new Random()), scanner);

        schoolRecordsController.initSchool();

        int menu = 0;

        while (menu != 11) {
            schoolRecordsController.printMenu();
            System.out.println("Melyik menupontot választja?");
            menu = scanner.nextInt();
            scanner.nextLine();

            schoolRecordsController.runMenu(menu);
        }
    }
}
