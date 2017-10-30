package org.word.game.util;

import org.word.game.vocabulary.Vocabulary;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class GameUtils {

    private static final String VOCABULARY_FILE_NAME_PROPERTY = "vocabulary.file.name";
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    private static final String LINE_REGEX_PROPERTY = "vocabulary.line.matches.regex";
    private static final String MIN_WORD_LENGTH_PROPERTY = "minimum.word.length";


    public static Vocabulary getLoadedVocabularyFromFile() {


        BufferedReader reader = null;
        Vocabulary vocabulary = null;

        try {

            reader = getFileReader(getVocabularyFileName());

            int sizeOfLongest = 0;
            String longest = "";

            String lineRegex = getVocabularyLineRegex();

            List<String> words = reader
                    .lines()
                    .map(line -> line.toLowerCase())
                    .filter(line -> line.matches(lineRegex))
                    .collect(Collectors.toList());

            longest = words
                    .stream()
                    .max(Comparator.comparingInt(String::length))
                    .get();

            reader.close();

            vocabulary = new Vocabulary(words);

        } catch (IOException e) {

            System.out.println("Error reading file or closing stream.");
            e.printStackTrace();

        } finally {
            return vocabulary;
        }


    }


    public static BufferedReader getFileReader(String fileName) throws FileNotFoundException {

        InputStream in = getInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        return reader;
    }

    public static String getVocabularyFileName() {

        return getConfigProperties().getProperty(VOCABULARY_FILE_NAME_PROPERTY);
    }

    public static String getVocabularyLineRegex() {

        return getConfigProperties().getProperty(LINE_REGEX_PROPERTY);
    }

    public static int getMinimumWordLength() {

        return Integer.valueOf(getConfigProperties().getProperty(MIN_WORD_LENGTH_PROPERTY));
    }

    public static Properties getConfigProperties() {

        if (PROPERTIES.isEmpty()) {

            try {

                InputStream in = getInputStream(CONFIG_FILE);
                PROPERTIES.load(in);
                in.close();

            } catch (IOException e) {

                System.out.println("Error loading properties");
                e.printStackTrace();
            }
        }

        return PROPERTIES;
    }


    private static InputStream getInputStream(String fileName) throws FileNotFoundException {

        ClassLoader classLoader = GameUtils.class.getClassLoader();

        File file = new File(classLoader.getResource(fileName) != null
                ? classLoader.getResource(fileName).getFile()
                : fileName);

        return new FileInputStream(file);
    }
}
