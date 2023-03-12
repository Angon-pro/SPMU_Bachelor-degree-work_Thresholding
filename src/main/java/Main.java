import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class Main {

    static int width;
    static int height;
    static int amount_of_fire_files;
    static int amount_of_no_fire_files;
    static double min_detected_temp;
    static double max_detected_temp;
    static double mean_detected_temp;
    static double anomaly_part;
    static final double MAX_TEMP = 500.0;
    static final double MIN_TEMP = 0.0;
    static final double DELTA_TEMP = MAX_TEMP - MIN_TEMP;
    static final double FIRE_TEMP = 300.0;
    static final String FILE_FIRE_PATH = "D:/Projects/Programming-projects/IntelliJ_IDEA_Projects/" +
            "SPMU_BDW_Thresholding/src/main/resources/fire/";
    static final String FILE_NO_FIRE_PATH = "D:/Projects/Programming-projects/IntelliJ_IDEA_Projects/" +
            "SPMU_BDW_Thresholding/src/main/resources/no_fire/";
    static final String FILE_RESULTS_FIRE_BASED_PATH = "D:/Projects/Programming-projects/IntelliJ_IDEA_Projects/" +
            "SPMU_BDW_Thresholding/src/main/resources/results_fire_based/";
    static final String FILE_RESULTS_NO_FIRE_BASED_PATH = "D:/Projects/Programming-projects/IntelliJ_IDEA_Projects/" +
            "SPMU_BDW_Thresholding/src/main/resources/results_no_fire_based/";
    static final String[] FILE_FIRE_NAMES = {"dcags1.png", "dcags2.png",
            "g1ds1.png", "g1ds2.png", "g1ds3.png",
            "g1dw1.png", "g1dw2.png", "g1dw3.png", "g1dw4.png",
            "g1gg1.png", "g1gg2.png", "g1gg3.png", "g1gg4.png", "g1gg5.png",
            "g1pg1.png", "g1pg2.png", "g1pg3.png",
            "g2ds1.png", "g2ds2.png", "g2ds3.png",
            "g2dw1.png", "g2dw2.png", "g2dw3.png", "g2dw4.png",
            "g2gg1.png", "g2gg2.png", "g2gg3.png", "g2gg4.png", "g2gg5.png",
            "g2pg1.png", "g2pg2.png", "g2pg3.png"};
    static final String[] FILE_NO_FIRE_NAMES = {"1ggg.png", "2ggg.png",
            "gg1ds1.png", "gg1ds2.png", "gg1ds3.png",
            "gg1dw1.png", "gg1dw2.png", "gg1dw3.png", "gg1dw4.png",
            "gg1gg1.png", "gg1gg2.png", "gg1gg3.png", "gg1gg4.png", "gg1gg5.png",
            "gg1pg1.png", "gg1pg2.png", "gg1pg3.png",
            "gg2ds1.png", "gg2ds2.png", "gg2ds3.png",
            "gg2dw1.png", "gg2dw2.png", "gg2dw3.png", "gg2dw4.png",
            "gg2gg1.png", "gg2gg2.png", "gg2gg3.png", "gg2gg4.png", "gg2gg5.png",
            "gg2pg1.png", "gg2pg2.png", "gg2pg3.png"};


    public static void main(String[] args) throws IOException {
        execute();
    }

    private static void execute() throws IOException {
        initialize();
        compute();
    }

    private static void initialize() {
        amount_of_fire_files = FILE_FIRE_NAMES.length;
        amount_of_no_fire_files = FILE_NO_FIRE_NAMES.length;
        System.out.println("\nmin detectable temp:\t\t" + MIN_TEMP);
        System.out.println("max detectable temp:\t\t" + MAX_TEMP);
        System.out.println("anomaly temperature:\t\t" + FIRE_TEMP);
        System.out.println("thermograms with fire:\t\t" + amount_of_fire_files);
        System.out.println("thermograms with no fire:\t" + amount_of_no_fire_files);
    }

    private static void compute() throws IOException {
        System.out.println("\n\nTHERMOGRAMS WITH FIRE");
        for (int i = 0; i < amount_of_fire_files; i++) {
            System.out.println("\nnumber " + (i + 1));
            String imagePath = FILE_FIRE_PATH + FILE_FIRE_NAMES[i];
            BufferedImage thermogram = ImageIO.read(new File(imagePath));
            double[][] matrix = convertTo2D(thermogram);
            double[][] temps = getTemps(matrix);
            min_detected_temp = getMinDetectedTemp(temps);
            max_detected_temp = getMaxDetectedTemp(temps);
            mean_detected_temp = getMeanDetectedTemp(temps);
            System.out.println("min detected temp:\t\t" + min_detected_temp);
            System.out.println("max detected temp:\t\t" + max_detected_temp);
            System.out.println("mean detected temp:\t\t" + mean_detected_temp);
            if (max_detected_temp > FIRE_TEMP) {
                System.out.println("—> ! anomaly temperature detected !");
            } else {
                System.out.println("—> no anomaly temperatures");
            }
            int[][] thresholdMatrix = threshold(temps);
            anomaly_part = getBinaryImage(thresholdMatrix,
                    FILE_RESULTS_FIRE_BASED_PATH + "r" + FILE_FIRE_NAMES[i]);
            if (anomaly_part > 1.0) {
                System.out.println("anomaly part:\t\t\t" + anomaly_part + "%\t\t<— more than 1%!");
            } else {
                System.out.println("anomaly part:\t\t\t" + anomaly_part + "%");
            }
        }
        System.out.println("\n\nTHERMOGRAMS WITH NO FIRE");
        for (int i = 0; i < amount_of_no_fire_files; i++) {
            System.out.println("\nnumber " + (i + 1));
            String imagePath = FILE_NO_FIRE_PATH + FILE_NO_FIRE_NAMES[i];
            BufferedImage thermogram = ImageIO.read(new File(imagePath));
            double[][] matrix = convertTo2D(thermogram);
            double[][] temps = getTemps(matrix);
            min_detected_temp = getMinDetectedTemp(temps);
            max_detected_temp = getMaxDetectedTemp(temps);
            mean_detected_temp = getMeanDetectedTemp(temps);
            System.out.println("min detected temp:\t\t" + min_detected_temp);
            System.out.println("max detected temp:\t\t" + max_detected_temp);
            System.out.println("mean detected temp:\t\t" + mean_detected_temp);
            if (max_detected_temp > FIRE_TEMP) {
                System.out.println("—> ! anomaly temperature detected !");
            } else {
                System.out.println("—> no anomaly temperatures");
            }
            int[][] thresholdMatrix = threshold(temps);
            anomaly_part = getBinaryImage(thresholdMatrix,
                    FILE_RESULTS_NO_FIRE_BASED_PATH + "r" + FILE_NO_FIRE_NAMES[i]);
            if (anomaly_part > 1.0) {
                System.out.println("anomaly part:\t\t\t" + anomaly_part + "%\t\t<— more than 1%!");
            } else {
                System.out.println("anomaly part:\t\t\t" + anomaly_part + "%");
            }
        }
    }

    private static double[][] convertTo2D(BufferedImage image) {
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        width = image.getWidth();
        height = image.getHeight();
        double[][] result = new double[height][width];
        final int pixelLength = 3;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int rgb = 0;
            rgb += ((int) pixels[pixel] & 0xff);
            rgb += (((int) pixels[pixel + 1] & 0xff) << 8);
            rgb += (((int) pixels[pixel + 2] & 0xff) << 16);
            result[row][col] = (double) rgb / 16777216;
            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }
        return result;
    }

    private static double[][] getTemps(double[][] matrix) {
        double[][] result = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = MIN_TEMP + matrix[i][j] * DELTA_TEMP;
            }
        }
        return result;
    }

    private static double getMeanDetectedTemp(double[][] temps) {
        double result = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result += temps[i][j];
            }
        }
        return result / (width * height);
    }

    private static double getMinDetectedTemp(double[][] temps) {
        double result = MAX_TEMP;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (temps[i][j] < result) {
                    result = temps[i][j];
                }
            }
        }
        return result;
    }

    private static double getMaxDetectedTemp(double[][] temps) {
        double result = MIN_TEMP;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (temps[i][j] > result) {
                    result = temps[i][j];
                }
            }
        }
        return result;
    }

    private static int[][] threshold(double[][] temps) {
        int[][] result = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (temps[i][j] >= FIRE_TEMP) {
                    result[i][j] = 1;
                } else {
                    result[i][j] = 0;
                }
            }
        }
        return result;
    }

    private static double getBinaryImage(int[][] matrix, String filePath) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color color;
        double anomalyPercentage = 0.0;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if (matrix[i][j] == 0) {
                    color = new Color(0, 0, 0);
                } else {
                    color = new Color(255, 0, 0);
                    anomalyPercentage++;
                }
                image.setRGB(j, i, color.getRGB());
            }
        }
        File output = new File(filePath);
        ImageIO.write(image, "png", output);
        anomalyPercentage = 100 * anomalyPercentage / (width * height);
        return anomalyPercentage;
    }
}