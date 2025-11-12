\*FileManager クラス（CSV入出力）

役割: ファイル保存・読み込み機能を担当。*\

import java.io.*;
import java.util.*;

public class FileManager {
    private String filePath = "inventory.csv";

    public List<Product> loadProducts() { ... }
    public void saveProducts(List<Product> products) { ... }
}

/*ポイント：

    loadProducts() … 起動時にCSVを読み込んでList<Product>を返す
    saveProducts() … 終了時に商品リストをCSVに書き込む          */