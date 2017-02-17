package to.kishimo.minist;

import java.io.IOException;

/**
 * 使用例.
 */
public class Main {
    public static void main(String... args) throws IOException, ClassNotFoundException {
        // トレーニングデータセット
        MnistDataSet trainDataSet = MnistDataSet.createInstance("train", MnistDataSet.TRAIN_IMAGE_FILE, MnistDataSet.TRAIN_LABEL_FILE);
        // 概形を表示する
        trainDataSet.showImageAsText(0);
        // 画像を表示する
        trainDataSet.showImage(1);
        // 画像を保存する
        trainDataSet.saveImage(MnistDataSet.BASE_PATH, "train", 2);

        // テストデータセット
        MnistDataSet testDataSet = MnistDataSet.createInstance("test", MnistDataSet.TEST_IMAGE_FILE, MnistDataSet.TEST_LABEL_FILE);
        // 概形を表示する
        testDataSet.showImageAsText(0);
        // 画像を表示する
        testDataSet.showImage(1);
        // 画像を保存する
        testDataSet.saveImage(MnistDataSet.BASE_PATH, "test", 2);
    }
}
