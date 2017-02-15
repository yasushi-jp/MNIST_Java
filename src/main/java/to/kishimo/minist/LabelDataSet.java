package to.kishimo.minist;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * MNISTの手書き文字の正解ラベルデータを扱うクラス.
 */
public class LabelDataSet implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName = "";
    private int numLabels;
    private int[] labels;

    /**
     * MNISTの手書き文字の正解ラベルファイルを読み込んで、正解ラベルデータに変換する.
     *
     * @param fileName 正解ラベルデータのファイル名
     */
    private LabelDataSet(String fileName) throws IOException {
        this.fileName = fileName;

        File baseDir = new File(Const.BASE_PATH);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        Util.download(Const.BASE_URL, Const.BASE_PATH, fileName);
    }

    /**
     * 正解ラベルデータセットのインスタンスを作成する.
     *
     * @param fileName 正解ラベルデータのファイル名
     * @return 正解ラベルデータセットのインスタンス
     */
    public static LabelDataSet create(String fileName) throws IOException, ClassNotFoundException {
        if (new File(Const.BASE_PATH + fileName + ".ser").exists()) {
            System.out.println("Deserializing label data from " + fileName + ".ser ...");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Const.BASE_PATH + fileName + ".ser"));
            return (LabelDataSet) ois.readObject();
        } else {
            System.out.println("Loading label data from " + fileName + " ...");
            LabelDataSet labelDataSet = new LabelDataSet(fileName);
            labelDataSet.loadLabels();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Const.BASE_PATH + fileName + ".ser"));
            oos.writeObject(labelDataSet);
            return labelDataSet;
        }
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
        DataInputStream is = new DataInputStream(new GZIPInputStream(new FileInputStream(Const.BASE_PATH + fileName)));

        is.readInt();
        numLabels = is.readInt();

        labels = new int[numLabels];
        for (int i = 0; i < numLabels; i++) {
            labels[i] = is.readUnsignedByte();
        }
    }
}
