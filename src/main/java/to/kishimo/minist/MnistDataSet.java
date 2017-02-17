package to.kishimo.minist;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;


/**
 * MNISTの手書き文字の画像データを扱うクラス.
 */
public class MnistDataSet implements Serializable {
    public static final String TRAIN_IMAGE_FILE = "train-images-idx3-ubyte.gz";
    public static final String TRAIN_LABEL_FILE = "train-labels-idx1-ubyte.gz";
    public static final String TEST_IMAGE_FILE = "t10k-images-idx3-ubyte.gz";
    public static final String TEST_LABEL_FILE = "t10k-labels-idx1-ubyte.gz";
    public static final String BASE_PATH = "./dataset/mnist/";

    private static final String BASE_URL = "http://yann.lecun.com/exdb/mnist/";
    private static final String SERIALIZED_FILE = "_mnist.ser";
    private static final long serialVersionUID = 1L;

    private int numImages;
    private int numDimensions;
    private double[][] features;
    private int[] labels;

    /**
     * Mnistデータセットのインスタンスを作成する.
     *
     * @param prefix    プレフィックス
     * @param imageFile 画像データのファイル名
     * @param labelFile 正解ラベルデータのファイル名
     * @return Mnistデータセットのインスタンス
     */
    public static MnistDataSet createInstance(String prefix, String imageFile, String labelFile) throws IOException, ClassNotFoundException {
        File baseDir = new File(BASE_PATH);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        download(BASE_URL, BASE_PATH, imageFile);
        download(BASE_URL, BASE_PATH, labelFile);

        String serFilePath = BASE_PATH + prefix + SERIALIZED_FILE;
        if (new File(serFilePath).exists()) {
            System.out.println("Deserializing object from " + prefix + SERIALIZED_FILE + " ...");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serFilePath));
            return (MnistDataSet) ois.readObject();
        } else {
            MnistDataSet dataSet = new MnistDataSet();
            System.out.println("Loading feature data from " + imageFile + " ...");
            dataSet.loadFeatures(imageFile);
            System.out.println("Loading label data from " + labelFile + " ...");
            dataSet.loadLabels(labelFile);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serFilePath));
            oos.writeObject(dataSet);

            return dataSet;
        }
    }

    /**
     * 画像数を取得する.
     *
     * @return 画像数
     */

    public int getNumImages() {
        return numImages;
    }

    /**
     * 特徴量の次元数を取得する.
     *
     * @return 特徴量の次元数
     */
    public int getNumDimensions() {
        return numDimensions;
    }

    /**
     * 特徴量データを取得する.
     *
     * @return 特徴量データ
     */
    public double[][] getFeatures() {
        return features;
    }

    /**
     * 正解ラベルデータを取得する.
     *
     * @return 正解ラベルデータ
     */
    public int[] getLabels() {
        return labels;
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
                double value = features[index][i * 28 + j];
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
                int value = (int) (features[index][i * 28 + j] * 255.0);
                image.setRGB(j, i, 0xff000000 | value << 16 | value << 8 | value);
            }
        }

        return image;
    }

    /**
     * 特徴量データを読み込む.
     *
     * @param imageFile 画像データのファイル名
     */
    private void loadFeatures(String imageFile) throws IOException {
        DataInputStream is = new DataInputStream(new GZIPInputStream(new FileInputStream(BASE_PATH + imageFile)));
        is.readInt();
        numImages = is.readInt();
        numDimensions = is.readInt() * is.readInt();

        features = new double[numImages][numDimensions];
        for (int i = 0; i < numImages; i++) {
            for (int j = 0; j < numDimensions; j++) {
                features[i][j] = (double) is.readUnsignedByte() / 255.0;
            }
        }
    }

    /**
     * 正解ラベルデータを読み込む.
     *
     * @param labelFile 正解ラベルデータのファイル名
     */
    private void loadLabels(String labelFile) throws IOException {
        DataInputStream is = new DataInputStream(new GZIPInputStream(new FileInputStream(BASE_PATH + labelFile)));

        is.readInt();
        int numLabels = is.readInt();

        labels = new int[numLabels];
        for (int i = 0; i < numLabels; i++) {
            labels[i] = is.readUnsignedByte();
        }
    }

    /**
     * ファイルをダウンロードする.
     *
     * @param baseUrl  ダウンロード元のベースURL
     * @param basePath ダウンロード先のベースパス
     * @param fileName ファイル名
     */
    private static void download(String baseUrl, String basePath, String fileName) throws IOException {
        if (!new File(basePath + fileName).exists()) {
            System.out.println("Downloading " + baseUrl + fileName + " ...");
            URL url = new URL(baseUrl + fileName);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();

            File file = new File(basePath + fileName);
            FileOutputStream out = new FileOutputStream(file, false);
            byte[] data = new byte[1024];
            while (true) {
                int ret = in.read(data);
                if (ret == -1) {
                    break;
                }

                out.write(data, 0, ret);
            }
            out.close();
            in.close();
        }
    }
}
