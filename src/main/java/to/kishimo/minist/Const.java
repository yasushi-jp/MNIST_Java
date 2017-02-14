package to.kishimo.minist;

/**
 * 定数.
 */
public interface Const {
    /**
     * MNISTの手書き文字データのベースURL.
     */
    public static final String BASE_URL = "http://yann.lecun.com/exdb/mnist/";

    /**
     * MNISTの手書き文字データを保存するベースパス.
     */
    public static final String BASE_PATH = "./dataset/mnist/";

    /**
     * トレーニング用の画像データのファイル名.
     */
    public static final String TRAIN_IMAGE_FILE = "train-images-idx3-ubyte.gz";

    /**
     * トレーニング用のラベルデータのファイル名.
     */
    public static final String TRAIN_LABEL_FILE = "train-labels-idx1-ubyte.gz";

    /**
     * テスト用の画像データのファイル名.
     */
    public static final String TEST_IMAGE_FILE = "t10k-images-idx3-ubyte.gz";

    /**
     * テスト用のラベルデータのファイル名.
     */
    public static final String TEST_LABEL_FILE = "t10k-labels-idx1-ubyte.gz";
}
