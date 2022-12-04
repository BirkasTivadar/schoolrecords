package schoolrecords;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TutorTest {

    @Test
    void testTutorTeachingSubject() {
        Tutor tutor = new Tutor("Nagy Csilla", Arrays.asList(new Subject("matematika"), new Subject("fizika")));//Then

        assertEquals("Nagy Csilla", tutor.getName());
        assertTrue(tutor.tutorTeachingSubject(new Subject("fizika")));
    }

    @Test
    void testTutorDoesNotTeachingSubject() {
        Tutor tutor = new Tutor("Nagy Csilla", Arrays.asList(new Subject("matematika"), new Subject("fizika")));

        assertFalse(tutor.tutorTeachingSubject(new Subject("biológia")));
    }
}
