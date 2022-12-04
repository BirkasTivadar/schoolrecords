package schoolrecords;


import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentTest {

    public static final Subject MATH = new Subject("matematika");
    public static final Tutor TUTOR = new Tutor("Nagy Lilla", Arrays.asList(MATH, new Subject("történelem")));

    @Test
    void emptyNameShouldThrowException() throws IllegalArgumentException {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Student(""));
        assertEquals("Student name must not be empty!", ex.getMessage());
    }

    @Test
    void nullMarkInGradingShouldThrowException() throws NullPointerException {
        Exception ex = assertThrows(NullPointerException.class, () -> new Student("Kovács").grading(null));
        assertEquals("Mark must not be null!", ex.getMessage());
    }

    @Test
    void testGrading() {
        Student student = new Student("Kovács");

        student.grading(new Mark(MarkType.A, MATH, TUTOR));

        assertEquals("Kovács marks: matematika: excellent(5)", student.toString());
    }

    @Test
    void testCalculateAverage() {
        Student student = new Student("Kovács");

        student.grading(new Mark(MarkType.A, MATH, TUTOR));
        student.grading(new Mark(MarkType.C, MATH, TUTOR));
        student.grading(new Mark(MarkType.D, MATH, TUTOR));

        assertEquals(3.33, student.calculateAverage());
    }

    @Test
    void testCalculateAverageIfMarksEmpty() {
        Student student = new Student("Kovács");

        assertEquals(0.0, student.calculateAverage());
    }

    @Test
    void testCalculateSubjectAverage() {
        Student student = new Student("Kovács");

        student.grading(new Mark(MarkType.A, MATH, TUTOR));
        student.grading(new Mark(MarkType.C, new Subject("történelem"), TUTOR));
        student.grading(new Mark(MarkType.D, MATH, TUTOR));

        assertEquals(3.50, student.calculateSubjectAverage(MATH));
    }

    @Test
    void testCalculateSubjectAverageIfMarksEmpty() {
        Student student = new Student("Kovács");

        assertEquals(0.0, student.calculateSubjectAverage(MATH));
    }

    @Test
    void testCalculateSubjectAverageIfNoMarkFromSubject() {
        Student student = new Student("Kovács");

        student.grading(new Mark(MarkType.A, MATH, TUTOR));
        student.grading(new Mark(MarkType.C, new Subject("történelem"), TUTOR));
        student.grading(new Mark(MarkType.D, MATH, TUTOR));

        assertEquals(0.0, student.calculateSubjectAverage(new Subject("földrajz")));
    }
}
