\*FileManager クラス（CSV入出力）

役割: ファイル保存・読み込み機能を担当。*\

import java.io.*;
import java.util.*;

public class FileManager {
    private String filePath = "inventory.csv";

    public List<Product> loadProducts() { ... }
    public void saveProducts(List<Product> products) { ... }
}
