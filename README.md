# できること
- データファイルをダウンロードする
- 特徴量データを読み込む
- 正解ラベルデータを読み込む
- オブジェクトのシリアライズ、デシリアライズ
- 文字の概形をテキストで表示する
- 画像をダイアログに表示する
- 画像をファイルに保存する

# 使い方
## クローン
$ git clone https://github.com/kinmojr/MNIST_Java.git  
$ cd MNIST_Java  

## ビルド
$ mvn clean package  

## サンプル実行
$ java -cp target/mnist_java-0.1.jar to.kishimo.minist.Main  
コンソールに訓練画像とテスト画像のそれぞれ1枚目がテキストで表示される  
訓練画像とテスト画像のそれぞれ2枚目がウィンドウに表示される  
訓練画像とテスト画像のそれぞれ3枚目が、./dataset/mnist/(train|test)_[インデックス]_[正解ラベル].gifとして保存される  

# ライセンス
MIT
