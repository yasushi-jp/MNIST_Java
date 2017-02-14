package to.kishimo.minist;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * MNISTの手書き文字の正解ラベルデータを扱うクラス.
 */
public class LabelDataSet {
    private String fileName = "";
    private int numLabels;
    private int[] labels;

    /**
     * MNISTの手書き文字の正解ラベルファイルを読み込んで、正解ラベルデータに変換する.
     *
     * @param fileName 正解ラベルデータのファイル名
     */
    public LabelDataSet(String fileName) throws IOException {
        this.fileName = fileName;

        File baseDir = new File(Const.BASE_PATH);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        Util.download(Const.BASE_URL, Const.BASE_PATH, fileName);

        loadLabels();
    }

    /**
     * 正解ラベル数を取得する.
     *
     * @return 正解ラベル数
     */
    public int getNumLabels() {
        return numLabels;
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
     * 正解ラベルデータを読み込む.
     */
    private void loadLabels() throws IOException {
        System.out.println("Loading label data from " + fileName + " ...");
        DataInputStream is = new DataInputStream(new GZIPInputStream(new FileInputStream(Const.BASE_PATH + fileName)));

        is.readInt();
        numLabels = is.readInt();

        labels = new int[numLabels];
        for (int i = 0; i < numLabels; i++) {
            labels[i] = is.readUnsignedByte();
        }
    }
}
