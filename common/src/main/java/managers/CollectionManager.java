package managers;

import exceptions.NoSuchIDException;
import stored.LabWork;
import utility.ResponseBuilder;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.xml.bind.annotation.*;;

/**
 * Class to manage the collection.
 */
@XmlRootElement(name = "collection")
@XmlAccessorType(XmlAccessType.NONE)
public class CollectionManager {
    @XmlElementWrapper(name="labWorks")
    @XmlElement(name="labWork")
    private static final Vector<LabWork> labStorage = new Vector<>();
    private static ZonedDateTime lastInitTime = null;
    private static ZonedDateTime lastSaveTime = null;

    /**
     * Gets last initialization time of collection for info command.
     * @return String representation of initialization time
     */
    public static String getLastInitTime() {
        return (lastInitTime == null) ? null :
                lastInitTime.toLocalDate() + " " +
                lastInitTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS) + " " +
                lastInitTime.getZone();
    }

    /**
     * Sets last initialization time of collection for info command when collection is read from file.
     */
    public static void setLastInitTime() {
        lastInitTime = ZonedDateTime.now();
    }

    /**
     * Gets last save time of collection for info command.
     * @return String representation of save time
     */
    public static String getLastSaveTime() {
        return (lastSaveTime == null) ? null : lastSaveTime.toLocalDate() + " " +
                lastSaveTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS) + " " +
                lastSaveTime.getZone();
    }

    /**
     * Sets last save time of collection for info command when collection is saved to file.
     */
    public static void setLastSaveTime() {
        lastSaveTime = ZonedDateTime.now();
    }

    /**
     * Gets the collection itself.
     * @return Collection
     */
    public static Vector<LabWork> getCollection() {
        return labStorage;
    }

    /**
     * Checks if the collection is empty.
     * @return true if collection is empty, false otherwise
     */
    public static boolean isEmpty() {
        return labStorage.isEmpty();
    }

    /**
     * Gets the number of elements in the collection.
     * @return Number of the elements
     */
    public static int size() {
        return labStorage.size();
    }

    /**
     * Adds the LabWork to the collection.
     * @param lw LabWork to be added
     */
    public static void add(LabWork lw) {
        labStorage.add(lw);
    }

    /**
     * Removes the LabWork from the collection by its index.
     * @param index Index of LabWork to be removed
     */
    public static void remove(int index) {
        labStorage.remove(index);
    }

    /**
     * Adds the LabWork to the collection at specified index.
     * @param lw LabWork to be added
     * @param index Index of LabWork to be added
     */
    public static void insertAt(LabWork lw, int index) {
        labStorage.insertElementAt(lw, index);
    }

    /**
     * Shows the collection in convenient format.
     */
    public static void show() {
        Vector<LabWork> tempStorage = labStorage.stream().sorted(Comparator.comparing(LabWork::getName))
                .collect(Collectors.toCollection(Vector::new));
        for (LabWork lw: tempStorage) {
            ResponseBuilder.appendln("- " + lw.toString());
//            System.out.println("- " + lw.toString());
        }
    }

    /**
     * Shows the minimal point field of each element from collection.
     * @param coll Collection to be shown
     */
    public static void showMinPoint(Vector<LabWork> coll) {
        for (LabWork lw: coll) {
            ResponseBuilder.appendln("- LabWork " + "id = " + lw.getId() + "\n\t\tminimalPoint = " + lw.getMinimalPoint());
//            System.out.println("- LabWork " + "id = " + lw.getId() + "\n\t\tminimalPoint = " + lw.getMinimalPoint());
        }
    }

    /**
     * Gets the array that contains IDs of each element from collection.
     * @return Array with IDs
     */
    public static long[] getAllIds() {
        long[] idList = new long[labStorage.size()];
        for (int i = 0; i < labStorage.size(); i++) {
            idList[i] = labStorage.get(i).getId();
        }
        return idList;
    }

    /**
     * Gets index of element by ID.
     * @param id ID of element
     * @return Index of element
     * @throws NoSuchIDException If there is no such ID in the collection
     */
    public static int getIndexById(long id) throws NoSuchIDException {
        int index = IntStream.range(0, labStorage.size()).filter(i -> labStorage.get(i).getId() == id)
                .findFirst().orElseThrow(NoSuchIDException::new);
        return index;
    }

    /**
     * Gets the average of minimal point fields of each element from the collection.
     * @return Average value
     */
    public static double getAverageMinimalPoint() {
        double sumMinimalPoint = 0;
        for (LabWork lw: labStorage) {
            sumMinimalPoint += lw.getMinimalPoint();
        }
        return sumMinimalPoint / labStorage.size();
    }

    /**
     * Gets the element with minimum ID.
     * @return LabWork object
     */
    public static LabWork getMinById() {
        int indexOfMin = 0;
        long minId = labStorage.get(0).getId();
        for (int i = 1; i < labStorage.size(); i++) {
            long currentId = labStorage.get(i).getId();
            if (currentId < minId) {
                minId = currentId;
                indexOfMin = i;
            }
        }
        return labStorage.get(indexOfMin);
    }

    /**
     * Clears the collection.
     */
    public static void clear() {
        labStorage.clear();
    }

    /**
     * Returns a string representation of this collection.
     * @return a string representation of this collection
     */
    @Override
    public String toString() {
        return labStorage.toString();
    }
}
