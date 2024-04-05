import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FindCircle {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat image = Imgcodecs.imread("C:/Users/dayof/Desktop/circleImg/circles.png");

        // Преобразование в оттенки серого
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Уменьшения шума и улучшения обнаружения окружностей
        Imgproc.GaussianBlur(grayImage, grayImage, new org.opencv.core.Size(9, 9), 2, 2);

        // Обнаружение окружностей с помощью HoughCircles
        Mat circles = new Mat();
        Imgproc.HoughCircles(grayImage, circles, Imgproc.HOUGH_GRADIENT, 1.0,
                (double) grayImage.rows() / 14, // Обнаружения окружностей с разными расстояниями между ними
                100.0, 30.0, 10, 65); // Установки минимального и максимального радиуса для обнаружения

        // Нарисовать обнаруженные окружности
        for (int i = 0; i < circles.cols(); i++) {
            double[] circle = circles.get(0, i);
            Point center = new Point(Math.round(circle[0]), Math.round(circle[1]));
            // Нарисовать центр окружности
            Imgproc.circle(image, center, 3, new Scalar(0, 255, 0), -1, 8, 0);
            // Нарисовать контур окружности
            int radius = (int) Math.round(circle[2]);
            Imgproc.circle(image, center, radius, new Scalar(0, 0, 255), 3, 8, 0);
        }

        Imgcodecs.imwrite("C:/Users/dayof/Desktop/circleImg/circles_output.jpg", image);
        System.out.println("Circles have been detected and marked on the image.");
    }
}
