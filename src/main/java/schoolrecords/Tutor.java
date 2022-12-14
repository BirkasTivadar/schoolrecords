package schoolrecords;

import java.util.ArrayList;
import java.util.List;

public class Tutor {

    private final String name;
    private final List<Subject> taughtSubjects;

    public Tutor(String name, List<Subject> taughtSubjects) {
        this.name = name;
        this.taughtSubjects = new ArrayList<>(taughtSubjects);
    }

    public String getName() {
        return name;
    }

    public boolean tutorTeachingSubject(Subject subject) {
        return taughtSubjects.contains(subject);
    }
}
