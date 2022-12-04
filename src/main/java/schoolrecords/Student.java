package schoolrecords;

import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private final List<Mark> marks = new ArrayList<>();
    private final String name;

    public Student(String name) {
        if (isEmpty(name)) {
            throw new IllegalArgumentException("Student name must not be empty!");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Mark> getMarks() {
        return new ArrayList<>(marks);
    }

    public void grading(Mark mark) {
        if (mark == null) {
            throw new NullPointerException("Mark must not be null!");
        }
        marks.add(mark);
    }

    public double calculateAverage() {
        double result = marks.stream()
                .mapToDouble(mark -> mark.getMarkType().getValue())
                .average().orElse(0);
        return Precision.round(result, 2);
    }

    public double calculateSubjectAverage(Subject subject) {
        double result = marks.stream()
                .filter(mark -> mark.getSubject().equals(subject))
                .mapToDouble(mark -> mark.getMarkType().getValue())
                .average().orElse(0);
        return Precision.round(result, 2);
    }

    @Override
    public String toString() {
        if (marks.isEmpty()) {
            return String.format("%s nevű tanulónak nincs még jegye.", name);
        }
        return String.format("%s marks: %s", name, markListString());
    }

    private String markListString() {
        StringBuilder stringBuilder = new StringBuilder();
        marks.forEach(mark -> stringBuilder.append(mark.getSubject().subjectName()).append(": ").append(mark).append(", "));
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    private boolean isEmpty(String str) {
        return str == null || str.isBlank();
    }
}
