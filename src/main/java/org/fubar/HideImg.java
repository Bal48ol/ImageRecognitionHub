package org.fubar;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class HideImg {

    public static void hideImage(String sourceImagePath, String targetImagePath, String outputImagePath) {
        try {
            // Загрузка изображений
            BufferedImage sourceImage = ImageIO.read(new File(sourceImagePath));
            BufferedImage targetImage = ImageIO.read(new File(targetImagePath));

            // Получение размеров изображений
            int sourceWidth = sourceImage.getWidth();
            int sourceHeight = sourceImage.getHeight();
            int targetWidth = targetImage.getWidth();
            int targetHeight = targetImage.getHeight();

            // Проверка, вмещается ли изображение
            if (sourceWidth * sourceHeight > targetWidth * targetHeight) {
                System.out.println("The source image does not fit into the target. Please choose a different target image.");
                return;
            }

            // Скрытие изображения
            for (int y = 0; y < sourceHeight; y++) {
                for (int x = 0; x < sourceWidth; x++) {
                    // Получаем RGB значения пикселей
                    int sourceRGB = sourceImage.getRGB(x, y);
                    int targetRGB = targetImage.getRGB(x, y);

                    // Получаем значения красного, зеленого и синего цветов для каждого пикселя
                    int newRGB = getNewRGB(sourceRGB, targetRGB);
                    targetImage.setRGB(x, y, newRGB);
                }
            }

            // Сохранение нового изображения
            File outputFile = new File(outputImagePath);
            ImageIO.write(targetImage, "png", outputFile);

            System.out.println("The image has been successfully hidden in " + outputImagePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getNewRGB(int sourceRGB, int targetRGB) {
        int sourceRed = (sourceRGB >> 16) & 0xFF;
        int sourceGreen = (sourceRGB >> 8) & 0xFF;
        int sourceBlue = sourceRGB & 0xFF;

        int targetRed = (targetRGB >> 16) & 0xFF;
        int targetGreen = (targetRGB >> 8) & 0xFF;
        int targetBlue = targetRGB & 0xFF;

        // Младшие биты заменяются значениями изображения-источника
        targetRed = (targetRed & 0xFE) | ((sourceRed >> 7) & 1);
        targetGreen = (targetGreen & 0xFE) | ((sourceGreen >> 7) & 1);
        targetBlue = (targetBlue & 0xFE) | ((sourceBlue >> 7) & 1);

        // Обновляем цвета пикселей
        return (targetRed << 16) | (targetGreen << 8) | targetBlue;
    }

    public static void main(String[] args) {
        // Путь к изображению, которое нужно спрятать
        String sourceImagePath = "C:/Users/dayof/Desktop/hideImage/white_cat.jpg";
        // Путь к целевому изображению, в котором нужно спрятать другое изображение
        String targetImagePath = "C:/Users/dayof/Desktop/hideImage/black_cat.jpg";
        // Путь к выходному изображению с закрытым изображением
        String outputImagePath = "C:/Users/dayof/Desktop/hideImage/hide_cat.jpg";

        // Вызов метода для скрытия изображения
        hideImage(sourceImagePath, targetImagePath, outputImagePath);
    }
}

