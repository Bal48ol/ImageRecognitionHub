import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FindLines {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat image = Imgcodecs.imread("C:/Users/dayof/Desktop/lineImg/lines.png");

        // Преобразование в оттенки серого
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Поиск границ на изображении
        Mat edges = new Mat();
        Imgproc.Canny(grayImage, edges, 50, 150, 3, false);

        // Применение преобразования Хафа для поиска прямых
        Mat lines = new Mat();
        Imgproc.HoughLines(edges, lines, 1, Math.PI / 180, 150);

        // Отрисовка найденных прямых на исходном изображении
        for (int i = 0; i < lines.rows(); i++) {
            double rho = lines.get(i, 0)[0];
            double theta = lines.get(i, 0)[1];
            double a = Math.cos(theta);
            double b = Math.sin(theta);
            double x0 = a * rho;
            double y0 = b * rho;
            Point pt1 = new Point(Math.round(x0 + 1000 * (-b)), Math.round(y0 + 1000 * (a)));
            Point pt2 = new Point(Math.round(x0 - 1000 * (-b)), Math.round(y0 - 1000 * (a)));
            Imgproc.line(image, pt1, pt2, new Scalar(0, 0, 255), 2, Imgproc.LINE_AA, 0);
        }

        Imgcodecs.imwrite("C:/Users/dayof/Desktop/lineImg/lines_output.jpg", image);
        System.out.println("Lines have been detected and marked on the image.");
    }
}
