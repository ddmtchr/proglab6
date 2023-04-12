package stored;


import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Class whose objects are being used as field of LabWork objects.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Discipline implements Serializable {
    @XmlElement(name="name")
    private String name;
    @XmlElement(name="lectureHours")
    private long lectureHours;
    @XmlElement(name="practiceHours")
    private Integer practiceHours;
    @XmlElement(name="selfStudyHours")
    private long selfStudyHours;

    private Discipline() {}

    /**
     * Creates the Discipline instance.
     * @param name Name of discipline.
     * @param lectureHours Number of lecture hours of discipline.
     * @param practiceHours Number of practice hours of discipline.
     * @param selfStudyHours Number of self study hours of discipline.
     */
    public Discipline(String name, long lectureHours, Integer practiceHours, long selfStudyHours) {
        this.name = name;
        this.lectureHours = lectureHours;
        this.practiceHours = practiceHours;
        this.selfStudyHours = selfStudyHours;
    }

    /**
     * Gets the name of discipline.
     * @return Name of Discipline
     */
    public String getName() {
        return name;
    }
    /**
     * Gets string representation of Discipline object.
     * @return String representation of Discipline object
     */
    @Override
    public String toString() {
        return "Discipline(name = " + name + " lectureHours = " + lectureHours + " practiceHours = " + practiceHours +
                " selfStudyHours = " + selfStudyHours + ")";
    }
}
