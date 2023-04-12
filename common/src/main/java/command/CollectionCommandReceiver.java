package command;

import exceptions.EmptyCollectionException;
import exceptions.ErrorInScriptException;
import exceptions.NoSuchIDException;
import stored.*;
import managers___ch.CollectionManager;
import managers___ch.FileManager;
import utility.LabWorkStatic;
import utility.ResponseBuilder;

import java.io.Serializable;
import java.util.*;

/**
 * Class that contains implementations of commands.
 */
public class CollectionCommandReceiver implements Serializable {
    //private FieldInputReceiver fir;

    /**
     * Creater FileData instance with a specified InputReceiver.
     * @param ir
     */
//    public CollectionCommandReceiver(FieldInputReceiver fir) {
//        this.fir = fir;
//    }

    /**
     * Implements command help. Displays help on available commands.
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int help() {
        int execCode = 0;
        ResponseBuilder.appendln("- help : вывести справку по доступным командам\n" +
                "- info : вывести в стандартный поток вывода информацию о коллекции\n" +
                "- show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "- add [element] : добавить новый элемент в коллекцию\n" +
                "- update {id} [element] : обновить значение элемента коллекции, id которого равен заданному\n" +
                "- remove_by_id {id} : удалить элемент из коллекции по его id\n" +
                "- clear : очистить коллекцию\n" +
                "- save : сохранить коллекцию в файл\n" +
                "- execute_script {file_name} : считать и исполнить скрипт из указанного файла\n" +
                "- exit : завершить программу без сохранения в файл\n" +
                "- insert_at {index} [element] : добавить новый элемент в заданную позицию\n" +
                "- add_if_min [element] : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "- remove_greater [element] : удалить из коллекции все элементы, превышающие заданный\n" +
                "- average_of_minimal_point : вывести среднее значение поля minimalPoint для всех элементов коллекции\n" +
                "- min_by_id : вывести любой объект из коллекции, значение поля id которого является минимальным\n" +
                "- print_field_ascending_minimal_point : вывести значения поля minimalPoint всех элементов в порядке возрастания");
//        System.out.println("- help : вывести справку по доступным командам\n" +
//                "- info : вывести в стандартный поток вывода информацию о коллекции\n" +
//                "- show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
//                "- add [element] : добавить новый элемент в коллекцию\n" +
//                "- update {id} [element] : обновить значение элемента коллекции, id которого равен заданному\n" +
//                "- remove_by_id {id} : удалить элемент из коллекции по его id\n" +
//                "- clear : очистить коллекцию\n" +
//                "- save : сохранить коллекцию в файл\n" +
//                "- execute_script {file_name} : считать и исполнить скрипт из указанного файла\n" +
//                "- exit : завершить программу без сохранения в файл\n" +
//                "- insert_at {index} [element] : добавить новый элемент в заданную позицию\n" +
//                "- add_if_min [element] : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
//                "- remove_greater [element] : удалить из коллекции все элементы, превышающие заданный\n" +
//                "- average_of_minimal_point : вывести среднее значение поля minimalPoint для всех элементов коллекции\n" +
//                "- min_by_id : вывести любой объект из коллекции, значение поля id которого является минимальным\n" +
//                "- print_field_ascending_minimal_point : вывести значения поля minimalPoint всех элементов в порядке возрастания");
        return execCode;
    }

    /**
     * Implements command info. Prints information about the collection to standard output.
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int info() {
        int execCode = 0;
        String lastInitTimeString = (CollectionManager.getLastInitTime() == null) ?
                "в текущей сессии не инициализирована" :
                CollectionManager.getLastInitTime();
        String lastSaveTimeString = (CollectionManager.getLastSaveTime() == null) ?
                "в текущей сессии не сохранена" :
                CollectionManager.getLastSaveTime();
        ResponseBuilder.appendln("Информация о коллекции:");
        ResponseBuilder.appendln("\tТип коллекции: " + CollectionManager.getCollection().getClass().getName());
        ResponseBuilder.appendln("\tКоличество элементов: " + CollectionManager.size());
        ResponseBuilder.appendln("\tВремя последней инициализации: " + lastInitTimeString);
        ResponseBuilder.appendln("\tВремя последнего сохранения: " + lastSaveTimeString);
//        System.out.println("Информация о коллекции:");
//        System.out.println("\tТип коллекции: " + CollectionManager.getCollection().getClass().getName());
//        System.out.println("\tКоличество элементов: " + CollectionManager.size());
//        System.out.println("\tВремя последней инициализации: " + lastInitTimeString);
//        System.out.println("\tВремя последнего сохранения: " + lastSaveTimeString);
        return execCode;
    }

    /**
     * Implements command show. Prints to standard output all elements of the collection in string representation.
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int show() {
        int execCode = 0;
        try {
            if (CollectionManager.isEmpty()) throw new EmptyCollectionException();
            CollectionManager.show();
        } catch (EmptyCollectionException e) {
            ResponseBuilder.appendln("Коллекция пуста!");
//            System.out.println("Коллекция пуста!");
        }
        return execCode;
    }

    /**
     * Implements command add. Adds a new element to the collection.
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int add(Object objectArgs) {
        int execCode;
        try {
            LabWorkStatic lws = (LabWorkStatic) objectArgs;
            LabWork lw = new LabWork(lws);
            CollectionManager.add(lw);
            ResponseBuilder.appendln("Лабораторная работа успешно добавлена в коллекцию!");
//            System.out.println("Лабораторная работа успешно добавлена в коллекцию!");
            execCode = 0;
        } catch (Exception e) {
            e.printStackTrace();
            execCode = 1;
        }
        return execCode;
    }

    /**
     * Implements command update. Updates the value of the collection element whose id is equal to the given one.
     * @param args ID of the element to be updated
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int update(String args, Object objectArgs) {
        int execCode;
        try {
            long id = Long.parseLong(args);
            int index = CollectionManager.getIndexById(id);
            CollectionManager.remove(index);
            LabWorkStatic lws = (LabWorkStatic) objectArgs;
            LabWork lw = new LabWork(lws);
            CollectionManager.insertAt(lw, index);
            ResponseBuilder.appendln("Элемент с id=" + id + " успешно обновлен");
//            System.out.println("Элемент с id=" + id + " успешно обновлен");
            execCode = 0;
        } catch (NoSuchIDException e) {
            execCode = 1;
            ResponseBuilder.appendln("В коллекции нет элемента с указанным ID");
//            System.out.println("В коллекции нет элемента с указанным ID");
        } catch (NumberFormatException e) {
            execCode = 1;
            ResponseBuilder.appendln("Аргумент id - целое число");
//            System.out.println("Аргумент id - целое число");
        } catch (Exception e) {
            execCode = 1;
            e.printStackTrace();
        }
        return execCode;
    }

    /**
     * Implements command remove_by_id. Removes element from collection by its id.
     * @param args ID of the element to be removed
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int removeById(String args) {
        int execCode;
        try {
            if (CollectionManager.isEmpty()) throw new EmptyCollectionException();
            long id = Long.parseLong(args);
            CollectionManager.remove(CollectionManager.getIndexById(id));
            ResponseBuilder.appendln("Элемент с id=" + id + " успешно удален");
//            System.out.println("Элемент с id=" + id + " успешно удален");
            execCode = 0;
        } catch (NoSuchIDException e) {
            execCode = 1;
            ResponseBuilder.appendln("В коллекции нет элемента с указанным ID");
//            System.out.println("В коллекции нет элемента с указанным ID");
        } catch (NumberFormatException e) {
            execCode = 1;
            ResponseBuilder.appendln("Аргумент id - целое число");
//            System.out.println("Аргумент id - целое число");
        } catch (EmptyCollectionException e) {
            execCode = 0;
            ResponseBuilder.appendln("Ошибка: коллекция пуста!");
//            System.out.println("Ошибка: коллекция пуста!");
        }
        return execCode;
    }

    /**
     * Implements command clear. Clears the collection.
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int clear() {
        int execCode = 0;
        try {
            if (CollectionManager.isEmpty()) throw new EmptyCollectionException();
            CollectionManager.clear();
            ResponseBuilder.appendln("Коллекция очищена!");
//            System.out.println("Коллекция очищена!");
        } catch (EmptyCollectionException e) {
            ResponseBuilder.appendln("Коллекция пуста!");
//            System.out.println("Коллекция пуста!");
        }
        return execCode;
    }

    /**
     * Implements command insert_at. Adds a new element at a given position.
     * @param args Index at which to add the element
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int insertAt(String args, Object objectArgs) {
        int execCode;
        try {
            int index = Integer.parseInt(args);
            if (index < 0 || index > CollectionManager.size()) throw new ArrayIndexOutOfBoundsException();
            LabWorkStatic lws = (LabWorkStatic) objectArgs;
            LabWork lw = new LabWork(lws);
            CollectionManager.insertAt(lw, index);
            ResponseBuilder.appendln("Элемент успешно добавлен по индексу " + index);
//            System.out.println("Элемент успешно добавлен по индексу " + index);
            execCode = 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            execCode = 1;
            ResponseBuilder.appendln("Индекс должен быть от 0 до " + CollectionManager.size());
//            System.out.println("Индекс должен быть от 0 до " + CollectionManager.size());
        } catch (NumberFormatException e) {
            execCode = 1;
            ResponseBuilder.appendln("Аргумент index - целое число");
//            System.out.println("Аргумент index - целое число");
        } catch (Exception e) {
            execCode = 1;
        }
        return execCode;
    }

    /**
     * Implements command add_if_min. Adds a new element to the collection
     * if its value is less than the smallest element of this collection.
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int addIfMin(Object objectArgs) {
        int execCode;
        try {
            if (CollectionManager.isEmpty()) throw new EmptyCollectionException();
            LabWorkStatic lws = (LabWorkStatic) objectArgs;
            LabWork comparableLW = new LabWork(lws);

            boolean done = CollectionManager.getCollection().stream().noneMatch(lw -> comparableLW.compareTo(lw) >= 0);

            if (done) {
                CollectionManager.add(comparableLW);
                ResponseBuilder.appendln("Лабораторная работа успешно добавлена в коллекцию!");
//                System.out.println("Лабораторная работа успешно добавлена в коллекцию!");
            } else {
                ResponseBuilder.appendln("Значение элемента не является наименьшим, элемент не добавлен");
            }
            execCode = 0;

        } catch (EmptyCollectionException e) {
            execCode = 1;
            ResponseBuilder.appendln("Ошибка: коллекция пуста!");
//            System.out.println("Ошибка: коллекция пуста!");
        } catch (Exception e) {
            execCode = 1;
        }
        return execCode;
    }

    /**
     * Implements command remove_greater. Removes from the collection all elements greater than the given.
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int removeGreater(Object objectArgs) {
        int execCode;
        try {
            if (CollectionManager.isEmpty()) throw new EmptyCollectionException();
            LabWorkStatic lws = (LabWorkStatic) objectArgs;
            LabWork comparableLW = new LabWork(lws);
            long[] deletedIds = CollectionManager.getCollection().stream().filter(lw -> lw.compareTo(comparableLW) > 0)
                    .mapToLong(LabWork::getId).toArray();
            int deletedCounter = deletedIds.length;

            if (deletedCounter > 0) {
                for (long id: deletedIds) {
                    CollectionManager.remove(CollectionManager.getIndexById(id));
                }
                ResponseBuilder.appendln("Удалены элементы с id: " + Arrays.toString(deletedIds));
            } else {
                ResponseBuilder.appendln("Нет элементов, превышающих введенный");
            }

            execCode = 0;
        } catch (EmptyCollectionException e) {
            execCode = 0;
            ResponseBuilder.appendln("Ошибка: коллекция пуста!");
//            System.out.println("Ошибка: коллекция пуста!");
        } catch (Exception e) {
            execCode = 1;
        }
        return execCode;
    }

    /**
     * Implements command average_of_minimal_point. Displays the average value
     * of the field minimalPoint for all elements of the collection.
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int averageOfMinimalPoint() {
        int execCode = 0;
        try {
            if (CollectionManager.isEmpty()) throw new EmptyCollectionException();
            double average = CollectionManager.getCollection().stream().mapToDouble(LabWork::getMinimalPoint)
                            .average().orElseThrow(EmptyCollectionException::new);

            ResponseBuilder.appendln("Среднее значение поля minimalPoint: " + average);
//            System.out.println("Среднее значение поля minimalPoint: " + CollectionManager.getAverageMinimalPoint());
        } catch (EmptyCollectionException e) {
            ResponseBuilder.appendln("Ошибка: коллекция пуста!");
//            System.out.println("Ошибка: коллекция пуста!");
        }
        return execCode;
    }

    /**
     * Implements command min_by_id. Displays any object from the collection whose id field value is the minimum.
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int minById() {
        int execCode = 0;
        try {
            if (CollectionManager.isEmpty()) throw new EmptyCollectionException();
            LabWork minlw = CollectionManager.getCollection().stream().min(Comparator.comparingLong(LabWork::getId))
                    .orElseThrow(EmptyCollectionException::new);

            ResponseBuilder.appendln(minlw.toString());
//            System.out.println(CollectionManager.getMinById().toString());
        } catch (EmptyCollectionException e) {
            ResponseBuilder.appendln("Ошибка: коллекция пуста!");
//            System.out.println("Ошибка: коллекция пуста!");
        }
        return execCode;
    }

    /**
     * Implements command print_field_ascending_minimal_point. Displays the values of the
     * minimalPoint field of all elements in ascending order
     * @return 0 if command executed correctly;
     * 1 if an error occurred while executing
     */
    public int printFAMinimalPoint() {
        int execCode = 0;
        try {
            if (CollectionManager.isEmpty()) throw new EmptyCollectionException();
            Vector<LabWork> tempStorage = CollectionManager.getCollection();
            tempStorage.sort(Comparator.comparingInt(LabWork::getMinimalPoint));
            CollectionManager.showMinPoint(tempStorage);
        } catch (EmptyCollectionException e) {
            ResponseBuilder.appendln("Ошибка: коллекция пуста!");
//            System.out.println("Ошибка: коллекция пуста!");
        }
        return execCode;
    }
}
