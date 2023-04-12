package stored;

import managers___ch.CollectionManager;
import utility.LabWorkStatic;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Class to be contained in the collection.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class LabWork implements Comparable<LabWork> {
    private static long commonId = 1;
    @XmlElement(name="id")
    private long id;
    @XmlElement(name="name")
    private String name;
    @XmlElement(name="coordinates")
    private Coordinates coordinates;
    @XmlElement(name="creationDate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private java.time.ZonedDateTime creationDate;
    @XmlElement(name="minimalPoint")
    private Integer minimalPoint;
    @XmlElement(name="averagePoint")
    private long averagePoint;
    @XmlElement(name="difficulty")
    private Difficulty difficulty;
    @XmlElement(name="discipline")
    private Discipline discipline;

    private LabWork() {}

    /**
     * Creates the LabWork instance.
     * @param name Name of LabWork
     * @param coordinates Coordinates of LabWork
     * @param minimalPoint Minimal point of LabWork
     * @param averagePoint Average point of LabWork
     * @param difficulty Difficulty of LabWork
     * @param discipline Discipline of LabWork
     */
    public LabWork(String name, Coordinates coordinates, Integer minimalPoint, long averagePoint, Difficulty difficulty, Discipline discipline) {
        id = generateId();
        this.name = name;
        this.coordinates = coordinates;
        creationDate = java.time.ZonedDateTime.now();
        this.minimalPoint = minimalPoint;
        this.averagePoint = averagePoint;
        this.difficulty = difficulty;
        this.discipline = discipline;
        System.out.println();
    }

    public LabWork(LabWorkStatic lws) {
        id = generateId();
        this.name = lws.getName();
        this.coordinates = lws.getCoordinates();
        creationDate = java.time.ZonedDateTime.now();
        this.minimalPoint = lws.getMinimalPoint();
        this.averagePoint = lws.getAveragePoint();
        this.difficulty = lws.getDifficulty();
        this.discipline = lws.getDiscipline();
    }

    /**
     * Generates ID for next created LabWork.
     * @return Generated ID
     */
    private long generateId() {
        long[] idList = CollectionManager.getAllIds();
        boolean unique = false;
        while (!unique) {
            commonId++;
            unique = true;
            for (long id: idList) {
                if (commonId == id) {
                    unique = false;
                    break;
                }
            }
        }
        return commonId;
    }

    /**
     * Gets the ID of LabWork.
     * @return ID of LabWork
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the name of LabWork.
     * @return Name of LabWork
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the coordinates of LabWork.
     * @return Coordinates of LabWork
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Gets the creation date of LabWork.
     * @return Creation date of LabWork
     */
    public java.time.ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the minimal point of LabWork.
     * @return Minimal point of LabWork
     */
    public int getMinimalPoint() {
        return minimalPoint;
    }

    /**
     * Gets the average point of LabWork.
     * @return Average point of LabWork
     */
    public long getAveragePoint() {
        return averagePoint;
    }

    /**
     * Gets the difficulty of LabWork.
     * @return Difficulty of LabWork
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the discipline of LabWork.
     * @return Discipline of LabWork
     */
    public Discipline getDiscipline() {
        return discipline;
    }

    /**
     * Sets the discipline of LabWork.
     * @param d Discipline to be set
     */
    public void setDiscipline(Discipline d) {
        this.discipline = d;
    }
    /**
     * Compares two names of LabWorks lexicographically.
     * @param lwToCompare the object to be compared.
     * @return the value 0 if the argument string is equal to this string;
     * a value less than 0 if this string is lexicographically less than the string argument;
     * and a value greater than 0 if this string is lexicographically greater than the string argument.
     */
    @Override
    public int compareTo(LabWork lwToCompare) {
        return getName().compareTo(lwToCompare.getName());
    }

    /**
     * Gets string representation of LabWork.
     * @return string representation of LabWork
     */
    @Override
    public String toString() {
        return "LabWork " + "id = " + id + "\n\t\tname = " + name + "\n\t\tcoordinates = " + coordinates.toString() +
                "\n\t\tcreationDate = " + creationDate.toString() + "\n\t\tminimalPoint = " + minimalPoint +
                "\n\t\taveragePoint = " + averagePoint + "\n\t\tdifficulty = " + difficulty.name() +
                "\n\t\tdiscipline = " + (discipline == null ? "null" : discipline.toString());
    }

}
