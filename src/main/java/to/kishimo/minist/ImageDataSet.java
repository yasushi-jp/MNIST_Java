package to.kishimo.minist;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * MNISTの手書き文字の画像データを扱うクラス.
 */
public class ImageDataSet {
    private String fileName = "";
    private int numImages;
    private int numDimensions;
    private double[][] features;

    /**
     * MNISTの手書き文字の画像データファイルを読み込んで、特徴量データに変換する.
     *
     * @param fileName 画像データのファイル名
     */
    public ImageDataSet(String fileName) throws IOException {
        this.fileName = fileName;

        File baseDir = new File(Const.BASE_PATH);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        Util.download(Const.BASE_URL, Const.BASE_PATH, fileName);

        loadFeatures();
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
     * 特徴量データを読み込む.
     */
    private void loadFeatures() throws IOException {
        System.out.println("Loading feature data from " + fileName + " ...");
        DataInputStream is = new DataInputStream(new GZIPInputStream(new FileInputStream(Const.BASE_PATH + fileName)));
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
}
