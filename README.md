# できること
- データファイルをダウンロードする
- 特徴量データを読み込む
- 正解ラベルデータを読み込む
- 文字の概形をテキストで表示する
- 画像を表示する
- 画像を保存する

# 使い方
## クローン
$ git clone https://github.com/kinmojr/mnist_java.git
$ cd mnist_java.git

## ビルド
$ mvn clean package

## サンプル実行
$ java -cp target/mnist_java-0.1.jar to.kishimo.minist.Main
コンソールに訓練画像とテスト画像のそれぞれ1枚目がテキストで表示される
訓練画像とテスト画像のそれぞれ2枚目がウィンドウに表示される
訓練画像とテスト画像のそれぞれ3枚目が、./dataset/mnist/(train|test)_[インデックス]_[正解ラベル].gifとして保存される

# ライセンス
MIT