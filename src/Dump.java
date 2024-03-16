import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Dump {
    static String file = "C:\\Users\\User\\IdeaProjects\\OOP_MAP\\streamAPI_HW\\out\\production\\streamAPI_HW\\dump.txt";
    public static List<Patient> getDump() {
        try(Stream<String> strings = Files.lines(Paths.get(file))) {
            return strings
                    .map(e -> e
                            .replaceAll(",", "")
                            .replaceAll("'", "")
                            .replace("(", "")
                            .replace(")", ""))
                    .map(Patient::new)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
