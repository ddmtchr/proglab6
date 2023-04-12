package utility;

import stored.Coordinates;
import stored.DateAdapter;
import stored.Difficulty;
import stored.Discipline;

import java.io.Serializable;

public class LabWorkStatic implements Serializable {
    private String name;
    private Coordinates coordinates;
    private Integer minimalPoint;
    private long averagePoint;
    private Difficulty difficulty;
    private Discipline discipline;

    public LabWorkStatic(String name, Coordinates coordinates, Integer minimalPoint, long averagePoint, Difficulty difficulty, Discipline discipline) {
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.averagePoint = averagePoint;
        this.difficulty = difficulty;
        this.discipline = discipline;
        System.out.println();
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Integer getMinimalPoint() {
        return minimalPoint;
    }

    public long getAveragePoint() {
        return averagePoint;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Discipline getDiscipline() {
        return discipline;
    }
}
