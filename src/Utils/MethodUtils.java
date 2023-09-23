package Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * This class provides some useful generic method fot the rest of the code.
 */
public class MethodUtils {

    private static final Random random = new Random();

    public static <T extends Enum<T>> T getRandomEnumValue(Class<T> enumClass) {
        int index = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[index];
    }

    public static <T> T pickRandomElement(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        int randomIndex = random.nextInt(array.length);
        return array[randomIndex];
    }

    public static int[] extractDigits(int number) {
        String numberStr = Integer.toString(number); // Converte il numero in una stringa
        int[] digits = new int[numberStr.length()]; // Crea un array per le cifre

        for (int i = 0; i < numberStr.length(); i++) {
            digits[i] = Character.getNumericValue(numberStr.charAt(i)); // Converte il carattere in un valore numerico
        }

        return digits;
    }

    public static void writeJsonObjectToFile(JSONObject jsonObject, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toString(4)); // Scrive il JSON con indentazione di 4 spazi
            System.out.println("JSON salvato nel file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> readJsonMapFromFile(String filePath) {
        Map<String, Object> jsonMap = new HashMap<>();

        try {
            FileReader fileReader = new FileReader(filePath);
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONObject jsonObject = new JSONObject(tokener);

            jsonMap = jsonObject.toMap();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonMap;
    }

}
