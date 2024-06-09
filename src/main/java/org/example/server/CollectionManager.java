package org.example.server;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.example.common.studyGroupClasses.StudyGroup;
import org.example.common.studyGroupClasses.StudyGroupStudentCountComparator;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.logging.Logger;

/**
 * Класс, отвечающий за работу коллекции
 */

public class CollectionManager {

    /**
     * Дата инициализации коллекции
     */
    private final Date initDate = new Date();

    private TreeSet<StudyGroup> collection;

    private final Logger logger;

    private int maxId = 0;

    /**
     * Путь к файлу, в котором хранится коллекция и куда она сохраняется
     */
    private String dataFile = System.getenv("DATA_FILE");

    public CollectionManager(TreeSet<StudyGroup> collection, Logger logger) {
        this.collection = collection;
        this.logger = logger;
    }


    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(ZonedDateTime.class, new DateAdapter())
            .create();

    public StringBuilder readTextFromFile(String fileName) {
        StringBuilder result = new StringBuilder();
        try (FileReader fr = new FileReader(fileName)) {
            int i = fr.read();
            while (i != -1) {
                result.append((char) i);
                i = fr.read();
            }
        } catch (FileNotFoundException e) {
            logger.warning("File not found! Try again.");
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Метод, загружающий начальную коллекцию из файла в переменной окружения DATA_FILE
     */

    public void loadInitialCollection() {
        if (dataFile == null) {
            logger.warning("Не найдена переменная окружения DATA_FILE");
//            System.exit(1);
            dataFile="main.json";
        }
        int lastDotIndex = dataFile.lastIndexOf('.');
        if (lastDotIndex == -1 || !dataFile.substring(lastDotIndex + 1).equals("json")) {
            logger.warning("Файл должен иметь расширение .json");
            System.exit(1);
        }
        StringBuilder result = readTextFromFile(dataFile);
        var collectionType = new TypeToken<TreeSet<StudyGroup>>() {
        }.getType();
        TreeSet<StudyGroup> collection = gson.fromJson(result.toString(), collectionType);
        this.collection.clear();
        for (StudyGroup group : collection) {
            putToCollection(group, true);
        }
    }



    /**
     * Метод, возвращающий название класса коллекции.
     *
     * @return название класса коллекции
     */
    public String getCollectionClassName() {
        return collection.getClass().getName();
    }

    /**
     * Метод, возвращающий дату инициализации коллекции.
     *
     * @return дата инициализации коллекции
     */

    public Date getInitDate() {
        return initDate;
    }

    /**
     * Метод, возвращающий количество элементов в коллекции.
     *
     * @return количество элементов в коллекции
     */

    public int getCollectionSize() {
        return collection.size();
    }

    /**
     * Метод, добавляющий route в коллекцию и устанавливающий id элемента при необходимости.
     *
     * @param group   элемент, который нужно добавить
     * @param silence флаг, указывающий, нужно ли выводить сообщение о добавлении элемента
     */

    public String putToCollection(StudyGroup group, boolean silence) {
        if (group.getId() == 0) {
            long maxId = collection.stream().mapToLong(StudyGroup::getId).max().orElse(0) + 1;
            group.setId(maxId);
        }

        collection.add(group);
        if (!silence) return "Группа успешно добавлена в коллекцию";
        return "Silently added";
    }

    /**
     * Метод, очищающий коллекцию.
     */
    public void clearCollection() {
        collection.clear();
    }




    /**
     * Метод, выводящий поле distance элементов коллекции в порядке убывания.
     */
    public String printDescendingStudentsCount() {
        if (collection.isEmpty()) {
            return "Коллекция пуста";
        }
        StringBuilder result = new StringBuilder("Поля studentsCount в порядке убывания:\n");
        collection.stream().sorted(new StudyGroupStudentCountComparator())
                .forEach(r -> result.append("%s - %s%n".formatted(r.getId(), r.getStudentsCount())));
        return result.toString();
    }

    /**
     * Метод, удаляющий элемент коллекции по его id.
     *
     * @param id id элемента, который нужно удалить
     */

    public String removeById(long id) {
        if (collection.isEmpty()) {
            return "Коллекция пуста";
        }
        boolean removed = collection.removeIf(group -> group.getId() == id);
        if (removed) {
            return "Элемент успешно удален";
        }
        return "Элемент с таким id не найден";

    }

    /**
     * Метод, выводящий коллекцию.
     */

    public String showCollection() {
        if (collection.isEmpty()) {
            return "Коллекция пуста";
        }
        StringBuilder result = new StringBuilder("Содержимое коллекции: \n");
        collection.stream().forEach(r -> result.append(r.toString()).append('\n'));
        return result.toString();
    }

    /**
     * Метод, проверяющий, есть ли элемент с данным id в коллекции.
     *
     * @param id id искомого элемента
     * @return true, если элемент найден, иначе - false
     */

    public boolean findElementById(long id) {
        return collection.stream().anyMatch(r -> r.getId() == id);
    }


    /**
     * Метод, обновляющий элемент коллекции по его id, заменяя его другим элементом.
     *
     * @param id       id элемента, который нужно обновить
     * @param newStudyGroup новый элемент
     */
    public String updateElementById(long id, StudyGroup newStudyGroup) {
        for (StudyGroup group : collection) {
            if (group.getId() == id) {
                collection.remove(group);
                collection.add(newStudyGroup);
                return "Элемент с id " + id + " успешно обновлен.";
            }
        }
        return "Не найдено";
    }

    public void saveCollection() {
        if (collection.isEmpty()) {
            logger.info("Коллекция пуста, нечего сохранять.");
            return;
        }
        String text = gson.toJson(collection); // строка для записи
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            // перевод строки в байты
            byte[] buffer = text.getBytes();

            fos.write(buffer, 0, buffer.length);
            logger.info("The file has been written");
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

    }

    public String putIfMax(StudyGroup group) {
        if (collection.isEmpty()) {
            collection.add(group);
            return "Элемент успешно добавлен";
        }
        StudyGroup maxGroup = collection.stream().min(new StudyGroupStudentCountComparator()).get();
        if (group.getStudentsCount() > maxGroup.getStudentsCount()) {
            collection.add(group);
            return "Элемент успешно добавлен";
        }
        return "Элемент не добавлен, так как он не больше текущего максимума";

    }

    public String putIfMin(StudyGroup group) {
        if (collection.isEmpty()) {
            collection.add(group);
            return "Элемент успешно добавлен";
        }
        StudyGroup minGroup = collection.stream().max(new StudyGroupStudentCountComparator()).get();
        if (group.getStudentsCount() < minGroup.getStudentsCount()) {
            collection.add(group);
            return "Элемент успешно добавлен";
        }
        return "Элемент не добавлен, так как он не меньше текущего минимума";
    }

    public String removeIfLower(StudyGroup group) {
        if (collection.isEmpty()) {
            return "Коллекция пуста";
        }
        long count = collection.stream().filter(r -> r.getStudentsCount() < group.getStudentsCount()).count();
        collection.removeIf(r -> r.getStudentsCount() < group.getStudentsCount());
        return "Удалено " + count + " элементов";
    }

    public String getMinById() {
        if (collection.isEmpty()) {
            return "Коллекция пуста";
        }
        long minId = collection.stream().mapToLong(StudyGroup::getId).min().orElse(1);
        StudyGroup minGroup = collection.stream().filter(r -> r.getId() == minId).findFirst().get();
        return minGroup.toString();
    }

    public String countLessThanTransferredStudentsCommand(int transferredCount) {
        if (collection.isEmpty()) {
            return "Коллекция пуста";
        }
        long count = collection.stream().filter(r -> r.getTransferredStudents() < transferredCount).count();
        return "Количество элементов, значение поля transferredStudents которых меньше " + transferredCount + ": " + count;
    }
}
