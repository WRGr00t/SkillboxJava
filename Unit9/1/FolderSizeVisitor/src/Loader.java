import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Loader {
    private static Scanner scanner;

    public static void main(String[] args) throws IOException {
        String path = "";
        String pathDir;
        scanner = new Scanner(System.in);
        for (;;){
            System.out.println("Введите путь к папке");
            pathDir = scanner.nextLine().trim();
            if (!pathDir.equals("")) {
                File folder = new File(pathDir);
                if (folder.exists()) {
                    path = pathDir;
                } else {
                    System.out.println("Папка не найдена");
                }
            } else {
                System.out.println("Пустое имя");
            }
            if (!path.equals("")){
                break;
            }
        }
        Path pathFolder = Paths.get(pathDir);
        CounterFileVisitor visitor = new CounterFileVisitor();
        Files.walkFileTree(pathFolder, visitor);
        double size = visitor.getSizeFolder() / (1024.0 * 1024.0);
        System.out.printf("Размер папки %.2f Мб", size);
    }
}
