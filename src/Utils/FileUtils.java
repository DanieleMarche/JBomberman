package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    /**
     * This function returns a List of all the PNG files names inside the directory passend in input.
     * @param directoryPath
     * @return List
     */
    public static List<String> getAllPNGFileNamesInDirectory(String directoryPath) {

        File folder = new File(directoryPath);

        if (!folder.isDirectory()) {
            return new ArrayList<>();
        }

        File[] files = folder.listFiles();

        if (files == null) {
            return new ArrayList<>();
        }

        return Stream.of(files)
                .filter(File::isFile)
                .map(file -> directoryPath + File.separator + file.getName())
                .filter(path -> path.endsWith(".png"))
                .sorted()
                .collect(Collectors.toList());
    }


}