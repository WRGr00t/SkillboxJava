import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyDir extends SimpleFileVisitor<Path> {
    private Path sourceDir;
    private Path targetDir;
    private int countCopy, countExist = 0;

    public CopyDir(Path sourceDir, Path targetDir) {
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
    }

    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attributes) {

        try {
            Path targetFile = targetDir.resolve(sourceDir.relativize(file));
            Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
            countCopy++;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir,
                                             BasicFileAttributes attributes) {
        try {
            Path newDir = targetDir.resolve(sourceDir.relativize(dir));
            Files.createDirectory(newDir);
        } catch (FileAlreadyExistsException ex) {
            countExist++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FileVisitResult.CONTINUE;
    }

    public int getCountCopy() {
        return countCopy;
    }

    public int getCountExist() {
        return countExist;
    }
}
