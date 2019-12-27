import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable {
    private File[] files;
    private int newWidth;
    private String dstFolder;
    private long start;

    public ImageResizer(File[] files, int newWidth, String dstFolder, long start) {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
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

                int newHeight = (int)Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                AffineTransform transform = new AffineTransform(
                        ((double) newWidth) / image.getWidth(), 0, 0,
                        ((double) image.getHeight() / (image.getWidth() / (double) newWidth)), 0, 0);
                AffineTransformOp transformer = new AffineTransformOp(transform, new RenderingHints(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC));
                BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
                transformer.filter(image, newImage);

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
                System.out.println("Успешно");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Finished after start: " + (System.currentTimeMillis() - start) + "ms");
    }
}
