package code;

import image.APImage;
import image.Pixel;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        APImage APImage1= new APImage ("cyberpunk2077.jpg");
        APImage1.draw();
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage APImage1 = new APImage("cyberpunk2077.jpg");
        int count = 0;
        for (int i = 0; i < APImage1.getWidth(); i++) {
            for (int j = 0; j < APImage1.getHeight(); j++) {
                count++;
                Pixel currentPixel = APImage1.getPixel(j, i);
                int b = APImage1.getPixel(i, j).getBlue();
                int r = APImage1.getPixel(i, j).getRed();
                int g = APImage1.getPixel(i, j).getGreen();
                int all = (b + r + g) / 3;
                APImage1.getPixel(i, j).setBlue(all);
                APImage1.getPixel(i, j).setRed(all);
                APImage1.getPixel(i, j).setGreen(all);
                APImage1.setPixel(j,i,currentPixel);
            }
        }
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
            int avg = pixel.getBlue() + pixel.getGreen() + pixel.getRed();
            return avg / 3;
        }


    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Pixel currentPixel = image.getPixel(i, j);
                int avg = ImageManipulation.getAverageColour(currentPixel);
                if (avg < 128) {
                    currentPixel.setBlue(0);
                    currentPixel.setRed(0);
                    currentPixel.setGreen(0);
                } else {
                    currentPixel.setBlue(255);
                    currentPixel.setRed(255);
                    currentPixel.setGreen(255);
                    image.setPixel(j, i, currentPixel);
                }
            }
        }
    }


    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        for(int y = 0; y <image.getHeight()-1; y++) {
            for (int x = image.getWidth()-1; x > 1; x--) {
                Pixel currentPixel = image.getPixel(x, y);
                int currentAvg = ImageManipulation.getAverageColour(currentPixel);

                Pixel leftCurrentPixel = image.getPixel(x-1, y);
                int leftAvg = ImageManipulation.getAverageColour(leftCurrentPixel);

                Pixel bottomPixel = image.getPixel(x, y + 1);
                int bottomAvg = ImageManipulation.getAverageColour(bottomPixel);

                if(Math.abs(currentAvg - leftAvg) > threshold || Math.abs(currentAvg - bottomAvg) > threshold){
                    currentPixel.setBlue(0);
                    currentPixel.setRed(0);
                    currentPixel.setGreen(0);}
                else{
                    currentPixel.setBlue(255);
                    currentPixel.setRed(255);
                    currentPixel.setGreen(255);

                }
            }
        }

        image.draw();

    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
            APImage image = new APImage(pathToFile);
            int width = image.getWidth();
            int middle = width / 2;
            for(int row = 0; row <image.getHeight(); row++){
                for(int column = 0; column<middle; column++){
                    Pixel leftPixel = image.getPixel(middle-column, row);
                    Pixel rightPixel = image.getPixel(middle+column, row);
                    image.setPixel(middle-column, row, rightPixel);
                    image.setPixel(middle+column, row, leftPixel);
                }
            }
            image.draw();
        }


    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        APImage image2 = new APImage(image.getHeight(),image.getWidth());
        for (int originalx=0; originalx<image.getWidth(); originalx++) {
            for (int originaly = 0; originaly < image.getHeight(); originaly++) {
                Pixel currentPixel = image.getPixel(originalx, originaly);

                image2.setPixel(image.getHeight() - originalx - 1, originalx, currentPixel);
            }
        }
        image = image2;
        image.draw();
    }

}



