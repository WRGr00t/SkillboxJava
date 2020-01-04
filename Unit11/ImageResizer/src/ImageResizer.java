
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class ImageResizer implements Runnable {
    private File[] files;
    private int newWidth;
    private String dstFolder;
    private long start;
    private int iter;

    public ImageResizer(File[] files, int newWidth, String dstFolder, long start, int iter) {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
        this.iter = iter;
    }

    @Override
    public void run() {
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    System.out.println("Пусто");
                    continue;
                }
                long newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                BufferedImage newImage = getScaledImage(image, newWidth, newHeight);
                File newFile = new File(dstFolder + "/" + file.getName());
                if (ImageIO.write(newImage, "jpg", newFile)) {
                    if (iter != 1000){
                        System.out.println("Успешно сжат файл " + newFile.getName() + " в потоке №" + iter);
                    } else {
                        System.out.println("Успешно сжат файл " + newFile.getName() + " в дополнительном потоке");
                    }
                } else {
                    System.out.println("Не записано");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (iter != 1000){
            System.out.println("Поток №" + iter + " Finished after start: " + (System.currentTimeMillis() - start) + "ms");
        } else {
            System.out.println("Дополнительный поток finished after start: " + (System.currentTimeMillis() - start) + "ms");
        }

    }

    public static BufferedImage getScaledImage(BufferedImage image, double width, double height) throws IOException {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = width / imageWidth;
        double scaleY = height / imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return bilinearScaleOp.filter(
                image,
                new BufferedImage((int) width, (int) height, image.getType()));
    }
}
