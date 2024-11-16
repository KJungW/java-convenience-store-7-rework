package store.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtility {

    public static Scanner loadFile(String fileName) {
        try {
            return new Scanner(new File(fileName));
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static List<String> readFileBySpace(String fileName) {
        Scanner scanner = loadFile(fileName);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNext()) {
            inputs.add(scanner.next());
        }
        return inputs;
    }
}
