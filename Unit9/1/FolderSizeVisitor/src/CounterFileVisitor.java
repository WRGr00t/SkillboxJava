import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CounterFileVisitor extends SimpleFileVisitor<Path> {

    private double sizeFolder;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        sizeFolder = sizeFolder + file.toFile().length();
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
        return FileVisitResult.SKIP_SUBTREE;
    }

    public double getSizeFolder() {
        return sizeFolder;
    }
}
