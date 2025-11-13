//package inventory;  // パッケージを使わない場合は削除してOK

import java.io.Console;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * InventoryAppクラス
 * 在庫管理システムのメインクラス（CLI操作を担当）
 */
public class InventoryApp {

    private static final Charset CONSOLE_CHARSET = detectConsoleCharset();

    private Inventory inventory = new Inventory();
    private FileManager fileManager = new FileManager();
    private Scanner scanner = createScanner();

    // ==========================
    // メインメソッド
    // ==========================
    public static void main(String[] args) {
        InventoryApp app = new InventoryApp();
        app.run();
    }

    // ==========================
    // アプリ実行処理
    // ==========================
    public void run() {
        // 起動時にデータ読み込み
        inventory.getAllProducts().addAll(fileManager.loadProducts());

        System.out.println("\n=== 在庫管理システムへようこそ ===");

        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("番号を入力してください > ");
            int choice = inputInt();
            System.out.println();

            switch (choice) {
                case 1 -> inventory.showAllProducts();
                case 2 -> addProductMenu();
                case 3 -> searchProductMenu();
                case 4 -> updateProductMenu();
                case 5 -> removeProductMenu();
                case 6 -> {
                    System.out.println("💾 終了前にデータを保存します...");
                    fileManager.saveProducts(inventory.getAllProducts());
                    System.out.println("👋 システムを終了します。");
                    running = false;
                }
                default -> System.out.println("⚠️ 無効な選択です。1〜6を入力してください。");
            }
        }

        scanner.close();
    }
    // ==========================
    // コンソールの文字コード検出処理
    // ==========================    
    private static Charset detectConsoleCharset() {
        Console console = System.console();
        if (console != null) {
            return console.charset();
        }
        return StandardCharsets.UTF_8;
    }

    private static Scanner createScanner() {
        try {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, CONSOLE_CHARSET.name()));
            System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err), true, CONSOLE_CHARSET.name()));
        } catch (UnsupportedEncodingException e) {
            System.err.println("⚠️ コンソール出力の文字コード設定に失敗しました: " + e.getMessage());
        }
        return new Scanner(new InputStreamReader(System.in, CONSOLE_CHARSET));
    }

    // ==========================
    // メニュー表示
    // ==========================
    private void showMenu() {
        System.out.println();
        System.out.println("====================================");
        System.out.println(" 1. 商品一覧表示");
        System.out.println(" 2. 商品登録");
        System.out.println(" 3. 商品検索");
        System.out.println(" 4. 商品更新");
        System.out.println(" 5. 商品削除");
        System.out.println(" 6. 終了");
        System.out.println("====================================");
    }

    // ==========================
    // 商品登録
    // ==========================
    private void addProductMenu() {
        System.out.println("=== 商品登録 ===");
        System.out.print("商品ID: ");
        int id = inputInt();
        System.out.print("商品名: ");
        String name = scanner.nextLine();
        System.out.print("在庫数: ");
        int quantity = inputInt();
        System.out.print("単価（円）: ");
        int price = inputInt();

        Product p = new Product(id, name, quantity, price);
        inventory.addProduct(p);
    }

    // ==========================
    // 商品検索
    // ==========================
    private void searchProductMenu() {
        System.out.println("=== 商品検索 ===");
        System.out.print("検索方法を選択（1: ID, 2: 名前）> ");
        int type = inputInt();

        Product found = null;
        if (type == 1) {
            System.out.print("商品IDを入力: ");
            int id = inputInt();
            found = inventory.findProductById(id);
        } else if (type == 2) {
            System.out.print("商品名を入力（部分一致可）: ");
            String name = scanner.nextLine();
            found = inventory.findProductByName(name);
        } else {
            System.out.println("⚠️ 無効な選択です。");
            return;
        }

        if (found != null) {
            System.out.println("\n検索結果:");
            System.out.println("------------------------------------");
            System.out.printf("%-5s %-15s %-8s %-8s\n", "ID", "商品名", "在庫数", "単価");
            System.out.println(found);
            System.out.println("------------------------------------");
        } else {
            System.out.println("📭 該当する商品は見つかりませんでした。");
        }
    }

    // ==========================
    // 商品更新
    // ==========================
    private void updateProductMenu() {
        System.out.println("=== 商品更新 ===");
        System.out.print("更新対象のIDを入力: ");
        int id = inputInt();
        Product existing = inventory.findProductById(id);

        if (existing == null) {
            System.out.println("⚠️ 指定されたIDの商品は存在しません。");
            return;
        }

        System.out.println("現在の情報: " + existing);
        System.out.print("新しい商品名（空欄なら変更なし）: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) name = existing.getName();

        System.out.print("新しい在庫数（現在 " + existing.getQuantity() + "）: ");
        int quantity = inputOptionalInt(existing.getQuantity());

        System.out.print("新しい単価（現在 " + existing.getPrice() + "円）: ");
        int price = inputOptionalInt(existing.getPrice());

        Product updated = new Product(id, name, quantity, price);
        inventory.updateProduct(id, updated);
    }

    // ==========================
    // 商品削除
    // ==========================
    private void removeProductMenu() {
        System.out.println("=== 商品削除 ===");
        System.out.print("削除する商品のIDを入力: ");
        int id = inputInt();
        inventory.removeProduct(id);
    }

    // ==========================
    // 入力補助メソッド
    // ==========================
    private int inputInt() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.print("⚠️ 数値を入力してください > ");
            }
        }
    }

    private int inputOptionalInt(int defaultValue) {
        String line = scanner.nextLine();
        if (line.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ 無効な入力のため変更しません。");
            return defaultValue;
        }
    }
}