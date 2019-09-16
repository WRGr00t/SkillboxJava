import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Loader {
    public static void main(String[] args) throws IOException {
        Path sourceDir = Paths.get("e:\\111");
        Path targetDir = Paths.get("e:\\1111");
        CopyDir copyDir = new CopyDir(sourceDir, targetDir);
        try {
            Files.walkFileTree(sourceDir, copyDir);
        } catch (NoSuchFileException ex) {
            System.out.println("Файл/папка не найдены");
        }
        if (copyDir.getCountCopy() != 0) {
            System.out.println("Успешно скопировано объектов - " + copyDir.getCountCopy());
            System.out.println("Перезаписано каталогов - " + copyDir.getCountExist());
        }
    }
}
