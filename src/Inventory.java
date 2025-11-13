//package inventory;  // パッケージを使わない場合は削除してOK

import java.util.*;

/**
 * Inventoryクラス
 * 商品（Product）の一覧を管理し、追加・削除・検索・更新などを行う。
 */

public class Inventory {

// ==========================
// フィールド
// ==========================
private List<Product> products;  // 商品のリスト

// ==========================
// コンストラクタ
// ==========================

public Inventory() {
    this.products = new ArrayList<>();
}

// ==========================
// 商品の追加
// ==========================
/**
 * 新しい商品を追加する。
 * 同じIDが存在する場合は追加しない。
 * @param p 追加する商品
 * @return 追加に成功したらtrue、失敗したらfalse
 */
public boolean addProduct(Product p) {
    if (findProductById(p.getId()) != null) {
        System.out.println("⚠️ 同じIDの商品が既に存在します。");
        return false;
    }
    products.add(p);
    System.out.println("✅ 商品を追加しました。");
    return true;
}

// ==========================
// 商品の削除
// ==========================
/**
 * 指定されたIDの商品を削除する。
 * @param id 削除する商品のID
 * @return 削除に成功したらtrue、失敗したらfalse
 */
public boolean removeProduct(int id) {
    Product target = findProductById(id);
    if (target != null) {
        products.remove(target);
        System.out.println("🗑️ 商品を削除しました。");
        return true;
    } else {
        System.out.println("⚠️ 指定されたIDの商品は存在しません。");
        return false;
    }
}

// ==========================
// 商品の検索（ID指定）
// ==========================
/**
 * IDで商品を検索する。
 * @param id 検索する商品ID
 * @return 該当する商品、存在しなければnull
 */
public Product findProductById(int id) {
    for (Product p : products) {
        if (p.getId() == id) {
            return p;
        }
    }
    return null;
}

// ==========================
// 商品の検索（名前指定）
// ==========================
/**
 * 名前で商品を検索する。
 * 部分一致で検索。
 * @param name 検索する商品名
 * @return 該当する商品（最初に見つかった1件）、存在しなければnull
 */
public Product findProductByName(String name) {
    for (Product p : products) {
        if (p.getName().contains(name)) {
            return p;
        }
    }
    return null;
}

// ==========================
// 商品情報の更新
// ==========================
/**
 * 指定されたIDの商品情報を更新する。
 * @param id 更新対象のID
 * @param newData 新しい商品情報
 * @return 更新に成功したらtrue、存在しなければfalse
 */
public boolean updateProduct(int id, Product newData) {
    Product target = findProductById(id);
    if (target != null) {
        target.setName(newData.getName());
        target.setQuantity(newData.getQuantity());
        target.setPrice(newData.getPrice());
        System.out.println("🔄 商品情報を更新しました。");
        return true;
    } else {
        System.out.println("⚠️ 指定されたIDの商品は存在しません。");
        return false;
    }
}

// ==========================
// 商品一覧取得
// ==========================
/**
 * 登録されている全商品のリストを返す。
 * @return 商品リスト
 */
public List<Product> getAllProducts() {
    return products;
}

// ==========================
// 商品一覧表示
// ==========================
/**
 * 商品リストをコンソールに表示する。
 */
public void showAllProducts() {
    if (products.isEmpty()) {
        System.out.println("📦 登録されている商品はありません。");
        return;
    }
    System.out.println("===========================================");
    System.out.printf("%-5s %-15s %-8s %-8s\n", "ID", "商品名", "在庫数", "単価");
    System.out.println("-------------------------------------------");
    for (Product p : products) {
        System.out.println(p);
    }
    System.out.println("===========================================");
}
}