package schoolrecords;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MarkTest {

    public static final Subject SUBJECT = new Subject("matematika");
    public static final Tutor TUTOR = new Tutor("Nagy Lilla", List.of(SUBJECT));


    @Test
    void nullParameterShouldThrowException() throws NullPointerException {
        Exception ex = assertThrows(NullPointerException.class, () -> new Mark(MarkType.A, null, TUTOR));
        assertEquals("Both subject and tutor must be provided!", ex.getMessage());
    }

    @Test
    void testCreate() {
        Mark mark = new Mark(MarkType.A, SUBJECT, TUTOR);
        assertEquals(MarkType.A, mark.getMarkType());
        assertEquals(SUBJECT, mark.getSubject());
        assertEquals(TUTOR, mark.getTutor());
        assertEquals("excellent(5)", mark.toString());
    }
}
