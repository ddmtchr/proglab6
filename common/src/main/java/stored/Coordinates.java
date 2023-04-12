package stored;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates implements Serializable {
    @XmlElement(name="x")
    private int x; //Значение поля должно быть больше -934
    @XmlElement(name="y")
    private Float y; //Максимальное значение поля: 946, Поле не может быть null

    private Coordinates() {}
    public Coordinates(int x, Float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the X coordinate.
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y coordinate.
     * @return Y coordinate
     */
    public Float getY() {
        return y;
    }

    /**
     * Gets string representation of Coordinates.
     * @return string representation of Coordinates
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
