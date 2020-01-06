
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        int middleWidth = newWidth * 2;
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    System.out.println("Пусто");
                    continue;
                }
                int newHeight = (int) Math.round(image.getHeight() / (image.getWidth() / (double) newWidth));
                int middleHeight = (int) Math.round(image.getHeight() / (image.getWidth() / (double) middleWidth));
                BufferedImage middleImage = getScaledImage(image, middleWidth, middleHeight,
                        AffineTransformOp.TYPE_BICUBIC);
                BufferedImage newImage = getScaledImage(middleImage, newWidth, newHeight,
                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                File newFile = new File(dstFolder + "/" + file.getName());
                if (ImageIO.write(newImage, "jpg", newFile)) {
                    System.out.println("Успешно сжат файл " + newFile.getName() + " в потоке " +
                            Thread.currentThread().getName());
                } else {
                    System.out.println("Не записано");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " Finished after start: " +
                (System.currentTimeMillis() - start) + "ms");
    }

    public static BufferedImage getScaledImage(BufferedImage image, double width, double height,
                                               int typeAffineTransformOp) throws IOException {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = width / imageWidth;
        double scaleY = height / imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, typeAffineTransformOp);

        return bilinearScaleOp.filter(
                image,
                new BufferedImage((int) width, (int) height, image.getType()));
    }
}
