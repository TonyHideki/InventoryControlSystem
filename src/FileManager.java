//package inventory;  // パッケージを使わない場合は削除してOK

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * FileManagerクラス
 * 在庫データをCSVファイルとして読み書きする。
 */
public class FileManager {

    // ==========================
    // フィールド
    // ==========================
    private String filePath = "inventory.csv";  // 保存先ファイル名

    // ==========================
    // コンストラクタ
    // ==========================
    public FileManager() {}

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    // ==========================
    // 商品リストをCSVファイルに保存
    // ==========================
    /**
     * 商品リストをCSVファイルに保存する。
     * @param products 保存対象の商品リスト
     */
    public void saveProducts(List<Product> products) {
        Path path = Paths.get(filePath);
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            // ヘッダ行（任意）
            bw.write("id,name,quantity,price");
            bw.newLine();

            // 各商品を書き込み
            for (Product p : products) {
                bw.write(String.format("%d,%s,%d,%d",
                        p.getId(),
                        escapeCsv(p.getName()),
                        p.getQuantity(),
                        p.getPrice()));
                bw.newLine();
            }

            System.out.println("💾 データをファイルに保存しました。");

        } catch (IOException e) {
            System.out.println("⚠️ ファイル保存中にエラーが発生しました。");
            e.printStackTrace();
        }
    }

    // ==========================
    // CSVファイルから商品リストを読み込み
    // ==========================
    /**
     * CSVファイルから商品情報を読み込み、List<Product>を返す。
     * ファイルが存在しない場合は空リストを返す。
     * @return 商品リスト
     */
    public List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("📄 データファイルが存在しません（新規作成予定）。");
            return products;
        }

        Path path = Paths.get(filePath);
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                // 1行目（ヘッダ）はスキップ
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",", -1);
                if (data.length != 4) continue; // 不正行はスキップ

                try {
                    int id = Integer.parseInt(data[0]);
                    String name = unescapeCsv(data[1]);
                    int quantity = Integer.parseInt(data[2]);
                    int price = Integer.parseInt(data[3]);
                    products.add(new Product(id, name, quantity, price));
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ データ形式が不正な行をスキップしました: " + line);
                }
            }

            System.out.println("📂 データを読み込みました。(" + products.size() + "件)");

        } catch (IOException e) {
            System.out.println("⚠️ ファイル読み込み中にエラーが発生しました。");
            e.printStackTrace();
        }

        return products;
    }

    // ==========================
    // CSVエスケープ処理（カンマ対策）
    // ==========================
    private String escapeCsv(String value) {
        if (value.contains(",")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private String unescapeCsv(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1).replace("\"\"", "\"");
        }
        return value;
    }
}

