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

                int newHeight = (int)Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                BufferedImage newImage = new BufferedImage(
                        newWidth, newHeight, BufferedImage.TYPE_INT_RGB
                );

                int widthStep = image.getWidth() / newWidth;
                int heightStep = image.getHeight() / newHeight;

                /*for (int x = 0; x < newWidth; x++)
                {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = image.getRGB(x * widthStep, y * heightStep);
                        newImage.setRGB(x, y, rgb);
                    }
                }*/
                BufferedImage buffResult = image;
                for (int x = 0; x < buffResult.getWidth(); x++){
                    for (int y = 0; y < buffResult.getHeight(); y++){
                        if ((buffResult.getWidth() % 4 != 0) && x == (buffResult.getWidth() - 1)){

                        }
                    }
                }
                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
                System.out.println("Успешно " + iter);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Поток №" + iter + " Finished after start: " + (System.currentTimeMillis() - start) + "ms");
    }
}
