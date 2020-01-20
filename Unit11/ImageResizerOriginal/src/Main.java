import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String srcFolder = "d:/src";
        String dstFolder = "d:/dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }

                long newWidth = 300;
                long newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                BufferedImage newImage = getScaledImage(
                        image, newWidth, newHeight
                );

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }

    public static BufferedImage getScaledImage(BufferedImage image, long width, long height) throws IOException {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double) width / imageWidth;
        double scaleY = (double) height / imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return bilinearScaleOp.filter(
                image,
                new BufferedImage((int) width, (int) height, image.getType()));
    }
}


