package stored;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Class used to format the date and time to write and read from XML.
 */
public class DateAdapter extends XmlAdapter<String, java.time.ZonedDateTime> {

    /**
     * Converts ZonedDateTime to String to write in XML.
     * @param t ZonedDateTime to be converted
     * @return String representation of DateTime
     */
    @Override
    public String marshal(java.time.ZonedDateTime t) {
        return t.toString();
    }

    /**
     * Converts ZonedDateTime from String to read from XML.
     * @param s String to be converted
     * @return ZonedDateTime object
     */
    @Override
    public java.time.ZonedDateTime unmarshal(String s) {
        return java.time.ZonedDateTime.parse(s);
    }
}
