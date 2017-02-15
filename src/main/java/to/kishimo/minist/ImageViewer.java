package to.kishimo.minist;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 画像を表示する.
 */
public class ImageViewer {
    private double[][] images;
    private int[] labels;

    /**
     * ファイル名を指定するコンストラクタ.
     *
     * @param imageFile 画像ファイル名
     * @param labelFile ラベルファイル名
     */
    public ImageViewer(String imageFile, String labelFile) throws IOException, ClassNotFoundException {
        ImageDataSet imageData = ImageDataSet.create(imageFile);
        images = imageData.getFeatures();

        LabelDataSet labelData = LabelDataSet.create(labelFile);
        labels = labelData.getLabels();
    }

    /**
     * データを指定するコンストラクタ.
     *
     * @param images 画像データ
     * @param labels ラベルデータ
     */
    public ImageViewer(double[][] images, int[] labels) throws IOException {
        this.images = images;
        this.labels = labels;
    }

    /**
     * 標準出力に画像の概形を表示する.
     *
     * @param index 画像の番号.
     */
    public void showImageAsText(int index) {
        System.out.println("Label: " + labels[index]);
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                double value = images[index][i * 28 + j];
                if (value > 0.0) {
                    System.out.print("**");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    /**
     * 画像を表示する.
     *
     * @param index 画像の番号
     */
    public void showImage(int index) {
        BufferedImage image = makeImage(index);
        Icon icon = new ImageIcon(image);
        try {
            JOptionPane.showMessageDialog(null, labels[index], "MnistImageViewer", JOptionPane.PLAIN_MESSAGE, icon);
        } catch (HeadlessException e) {
            System.out.println("Image dialog can't be displayed on CUI environment.");
        }
    }

    /**
     * 画像をファイルに保存する.
     *
     * @param dir    ファイルを保存するディレクトリ
     * @param prefix ファイル名の先頭に付けるプレフィックス
     * @param index  画像の番号
     */
    public void saveImage(String dir, String prefix, int index) throws IOException {
        BufferedImage image = makeImage(index);
        File file = new File(dir + "/" + prefix + "_" + String.format("%05d", index) + "_" + labels[index] + ".gif");
        if (file.exists()) file.delete();
        ImageIO.write(image, "gif", file);
    }

    /**
     * 数値データから画像オブジェクトを生成する.
     *
     * @param index 画像の番号
     * @return 画像オブジェクト
     */
    private BufferedImage makeImage(int index) {
        BufferedImage image =
                new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                int value = (int) (images[index][i * 28 + j] * 255.0);
                image.setRGB(j, i, 0xff000000 | value << 16 | value << 8 | value);
            }
        }

        return image;
    }
}
