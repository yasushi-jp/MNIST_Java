package to.kishimo.minist;

import java.io.IOException;

/**
 * 使用例.
 */
public class Main {
    public static void main(String... args) throws IOException {
        // トレーニングデータセット
        ImageDataSet trainImages = new ImageDataSet(Const.TRAIN_IMAGE_FILE);
        LabelDataSet trainLabels = new LabelDataSet(Const.TRAIN_LABEL_FILE);
        ImageViewer trainViewer = new ImageViewer(trainImages.getFeatures(), trainLabels.getLabels());
        // 概形を表示する
        trainViewer.showImageAsText(0);
        // 画像を表示する
        trainViewer.showImage(1);
        // 画像を保存する
        trainViewer.saveImage(Const.BASE_PATH, "train", 2);

        // テストデータセット
        ImageDataSet testImages = new ImageDataSet(Const.TEST_IMAGE_FILE);
        LabelDataSet testLabels = new LabelDataSet(Const.TEST_LABEL_FILE);
        ImageViewer testViewer = new ImageViewer(testImages.getFeatures(), testLabels.getLabels());
        // 概形を表示する
        testViewer.showImageAsText(0);
        // 画像を表示する
        testViewer.showImage(1);
        // 画像を保存する
        testViewer.saveImage(Const.BASE_PATH, "test", 2);
    }
}
