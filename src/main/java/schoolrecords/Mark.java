package schoolrecords;

public class Mark {

    private MarkType markType;
    private final Subject subject;
    private final Tutor tutor;

    public Mark(MarkType markType, Subject subject, Tutor tutor) {
        if (subject == null || tutor == null) {
            throw new NullPointerException("Both subject and tutor must be provided!");
        }
        if (!tutor.tutorTeachingSubject(subject)) {
            throw new IllegalArgumentException(String.format("%s tutor do not teach this subject: %s", tutor, subject));
        }
        this.markType = markType;
        this.subject = subject;
        this.tutor = tutor;
    }

    public Mark(String markType, Subject subject, Tutor tutor) {
        if (subject == null || tutor == null) {
            throw new NullPointerException("Both subject and tutor must be provided!");
        }
        this.subject = subject;
        this.tutor = tutor;
        for (MarkType mt : MarkType.values()) {
            if (mt == MarkType.valueOf(markType)) {
                this.markType = mt;
            }
        }
    }

    public MarkType getMarkType() {
        return markType;
    }

    public Subject getSubject() {
        return subject;
    }

    public Tutor getTutor() {
        return tutor;
    }

    @Override
    public String toString() {
        return String.format("%s(%d)", markType.getDescription(), markType.getValue());
    }
}
