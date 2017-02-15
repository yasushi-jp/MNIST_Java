package to.kishimo.minist;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * ユーティリティメソッドを定義するクラス.
 */
public class Util {
    /**
     * ファイルをダウンロードする.
     *
     * @param baseUrl  ダウンロード元のベースURL
     * @param basePath ダウンロード先のベースパス
     * @param fileName ファイル名
     */
    public static void download(String baseUrl, String basePath, String fileName) throws IOException {
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
