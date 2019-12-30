
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
            System.out.println(files.length);
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    System.out.println("Пусто");
                    continue;
                }
                int step = 4;
                BufferedImage newImage = createNewImage(newWidth, image, step);
                System.out.println(newImage);
                File newFile = new File(dstFolder + "/" + file.getName());
                if (ImageIO.write(newImage, "jpg", newFile)) {
                    System.out.println("Успешно сжат файл " + newFile.getName() + " в потоке №" + iter);
                } else {
                    System.out.println("Не записано");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Поток №" + iter + " Finished after start: " + (System.currentTimeMillis() - start) + "ms");
    }

    private BufferedImage createNewImage(int newWidth, BufferedImage image, int step) {
        int newHeight = (int)Math.round(
                image.getHeight() / (image.getWidth() / (double) newWidth)
        );
        BufferedImage buffImage = new BufferedImage(
                newWidth, newHeight, BufferedImage.TYPE_INT_ARGB
        );
        convolution(buffImage, image, step);

        return buffImage;
    }

    private void convolution(BufferedImage buffImage, BufferedImage image, int step) {
        while (buffImage.getWidth() > newWidth) {
            int width = buffImage.getWidth();
            int height = buffImage.getHeight();
            int newW = width / step;
            int newH = height / step;
            BufferedImage bufferedImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < newW; i++){
                for (int j = 0; j < newH; j++){
                    HashSet<Color> colors = new HashSet<>();
                    for (int x = i * step; x < (i + 1) * step; x++) {
                        for (int y = j * step; y < (j + 1) * step; y++) {
                            colors.add(new Color(image.getRGB(x, y)));
                        }
                    }
                    int sumRed = 0;
                    int sumGreen = 0;
                    int sumBlue = 0;
                    int sumAlpha = 0;
                    for (Color color : colors) {
                        sumRed =+ color.getRed();
                        sumGreen =+ color.getGreen();
                        sumBlue =+ color.getBlue();
                        sumAlpha =+ color.getAlpha();
                    }
                    int redResult = sumRed / colors.size();
                    int greenResult = sumGreen / colors.size();
                    int blueResult = sumBlue / colors.size();
                    int alphaResult = sumAlpha / colors.size();

                    Color color = new Color(redResult, greenResult, blueResult, alphaResult);
                    try {
                        bufferedImage.setRGB(i, j, color.getRGB());
                    } catch (Exception e) {
                        System.out.println("Ошибка на i = " + i + " j = " + j);
                    }
                }
            }
            buffImage = bufferedImage;
        }
    }
}
