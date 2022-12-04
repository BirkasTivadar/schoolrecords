package schoolrecords;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ClassRecords {

    private final String className;
    private final Random rnd;
    private final List<Student> students = new ArrayList<>();

    public ClassRecords(String className, Random rnd) {
        this.className = className;
        this.rnd = rnd;
    }

    public String getClassName() {
        return className;
    }

    public boolean addStudent(Student student) {
        for (Student st : students) {
            if (st.getName().equals(student.getName())) {
                return false;
            }
        }
        students.add(student);
        return true;
    }

    public boolean removeStudent(Student student) {
        Iterator<Student> studentIterator = students.iterator();
        while (studentIterator.hasNext()) {
            Student st = studentIterator.next();
            if (st.getName().equals(student.getName())) {
                studentIterator.remove();
                return true;
            }
        }
        return false;
    }

    public double calculateClassAverage() {
        if (students.isEmpty()) {
            throw new ArithmeticException("No student in the class, average calculation aborted!");
        }
        return students.stream()
                .filter(student -> !student.getMarks().isEmpty())
                .mapToDouble(Student::calculateAverage)
                .average()
                .orElseThrow(() -> new ArithmeticException("No marks present, average calculation aborted!"));
    }

    public double calculateClassAverageBySubject(Subject subject) {
        return students.stream()
                .filter(student -> student.calculateSubjectAverage(subject) != 0)
                .mapToDouble(student -> student.calculateSubjectAverage(subject))
                .average()
                .orElse(0);
    }


    public Student findStudentByName(String name) {
        if (isEmpty(name)) {
            throw new IllegalArgumentException("Student name must not be empty!");
        }
        if (students.isEmpty()) {
            throw new IllegalStateException("No students to search!");
        }
        return students.stream()
                .filter(student -> student.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Student by this name cannot be found! %s", name)));
    }

    public Student repetition() {
        if (students.isEmpty()) {
            throw new IllegalStateException("No students to select for repetition!");
        }
        int i = rnd.nextInt(students.size());
        return students.get(i);
    }

    public List<StudyResultByName> listStudyResults() {
        List<StudyResultByName> studyResultByNames = new ArrayList<>();
        students.forEach(student -> studyResultByNames.add(new StudyResultByName(student.getName(), student.calculateAverage())));
        return studyResultByNames;
    }

    public String listStudentNames() {
        List<String> studentNames = students.stream().map(Student::getName).toList();
        return String.join(", ", studentNames);
    }

    private boolean isEmpty(String str) {
        return str == null || str.isBlank();
    }
}
