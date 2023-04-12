package managers___ch;

import exceptions.IncorrectFieldInputException;
import stored.LabWork;
import utility.ResponseBuilder;
import validation.*;

import javax.xml.bind.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for working with a file that stores a collection.
 */
public class FileManager {
    private final String ENV_VAR_NAME = "FILENAME_LAB";
    private File file;
    private InputStreamReader isr;
    private PrintWriter pw;

    /**
     * Create the FileManager to work with file specified in environment variable.
     */
    public FileManager() {
        try {
            file = new File(System.getenv(ENV_VAR_NAME));
        } catch (NullPointerException e) {
            System.out.println("Неверное имя переменной окружения");
        }
    }

    /**
     * Checks if file exists and can be written and read.
     * @return true if file exists and can be written and read, false otherwise
     */
    public boolean isFileValid() {
        boolean valid = false;
        try {
            if (!file.exists()) throw new FileNotFoundException();
            if (!file.canRead() || !file.canWrite()) throw new SecurityException();
            valid = true;
        } catch (SecurityException e) {
            System.out.println("Нет прав на чтение и запись файла");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (NullPointerException e) {
            System.out.println("Неверное имя переменной окружения");
        }
        return valid;
    }

    /**
     * Reads a file and initializes the collection from XML format.
     * @return true if initialized successfully, false otherwise
     */
    public boolean readFromFile() {
        boolean done = false;
        try {
            isr = new InputStreamReader(new FileInputStream(file));
            StringBuilder output = new StringBuilder("");
            while (isr.ready()) {
                output.append((char) isr.read());
            }
            if (output.toString().trim().isEmpty()) {
                output = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<collection>\n\t<labWorks/>\n </collection>");
            }
            StringReader sr = new StringReader(output.toString());
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Unmarshaller um = context.createUnmarshaller();
            um.unmarshal(sr);
            Set<Long> idSet = new HashSet<>();
            for (LabWork lw: CollectionManager.getCollection()) {
                if (!LabWorkValidator.isValid(lw)) throw new IncorrectFieldInputException();
                if (lw.getDiscipline() != null && lw.getDiscipline().getName().isBlank()) lw.setDiscipline(null);
                idSet.add(lw.getId());
            }
            if (idSet.size() < CollectionManager.size()) throw new IncorrectFieldInputException();
            done = true;
        } catch (JAXBException e) {
            System.out.println("Ошибка чтения XML");
        } catch (IOException e) {
            System.out.println("IOException");
        } catch (IncorrectFieldInputException e) {
            System.out.println("Ошибка ввода полей класса в XML");
        } finally {
            try {
                if (isr != null) isr.close();
            } catch (IOException e) {
                System.out.println("Ошибка закрытия потока ввода");
            }
        }
        return done;
    }

    /**
     * Writes the collection to file using XML format.
     * @return true if written successfully, false otherwise
     */
    public boolean writeToFile() {
        boolean done = false;
        try {
            pw = new PrintWriter(file);
            CollectionManager c = new CollectionManager();
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(c, pw);
            done = true;
        } catch (JAXBException e) {
            ResponseBuilder.appendln("Ошибка записи в XML");
//            System.out.println("Ошибка записи в XML");
        } catch (IOException e) {
            ResponseBuilder.appendln("IOException");
//            System.out.println("IOException");
        } finally {
            if (pw != null) pw.close();
        }
        return done;
    }
}
