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
                }
                BufferedImage buffResult = image;
                for (int x = 0; x < buffResult.getWidth(); x++){
                    for (int y = 0; y < buffResult.getHeight(); y++){
                        if ((buffResult.getWidth() % 4 != 0) && x == (buffResult.getWidth() - 1)){

                        }
                    }
                }*/
                BufferedImage buffImage = new BufferedImage(
                        image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB
                );
                while (buffImage.getWidth() > newWidth) {
                    int width = buffImage.getWidth();
                    int height = buffImage.getHeight();
                    for (int i = 0; i < width; i++){
                        for (int j = 0; j < height; j++){
                            int x = i * 2;
                            int y = j * 2;
                            Color color1 = new Color(image.getRGB(x, y));
                            Color color2 = new Color(image.getRGB(x, y + 1));
                            Color color3 = new Color(image.getRGB(x + 1, y));
                            Color color4 = new Color(image.getRGB(x + 1, y + 1));

                            int redResult = Math.round((color1.getRed() + color2.getRed() + color3.getRed() + color4.getRed()) / 4);
                            int greenResult = Math.round((color1.getGreen() + color2.getGreen() + color3.getGreen() + color4.getGreen()) / 4);
                            int blueResult = Math.round((color1.getBlue() + color2.getBlue() + color3.getBlue() + color4.getBlue()) / 4);
                            int alphaResult = Math.round((color1.getAlpha() + color2.getAlpha() + color3.getAlpha() + color4.getAlpha()) / 4);
                            Color color = new Color(redResult, greenResult, blueResult, alphaResult);
                            buffImage.setRGB(i, j, color.getRGB());
                        }
                    }
                    width = width / 4;
                    height = height / 4;
                    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    bufferedImage = buffImage;
                }
                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
                System.out.println("Успешно сжат файл " + file.getName() + " в потоке №" + iter);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Поток №" + iter + " Finished after start: " + (System.currentTimeMillis() - start) + "ms");
    }
}
