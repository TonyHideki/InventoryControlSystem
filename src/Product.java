//package inventory; //パッケージを使わない場合は削除してOK

public class Product {
    // ====================
    // フィールド（属性）
    // ====================
    private int id; // 商品ID
    private String name; // 商品名
    private int quantity; // 在庫数
    private int price; // 単価（円）

    // ====================
    // コンストラクタ
    // ====================
    /**
     * 商品情報を指定してProductを生成する。
     * 
     * @param id       商品ID
     * @param name     商品名
     * @param quantity 在庫数
     * @param price    単価
     */

    public Product(int id, String name, int quantity, int price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // ==========================
    // Getter / Setter
    // ==========================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // ==========================
    // 表示用メソッド
    // ==========================
    /**
     * 商品情報を整形して文字列として返す。
     */
    @Override
    public String toString() {
        return String.format("%-5d %-15s %-8d %-8d円", id, name, quantity, price);
    }

    // ==========================
    // 比較・等価判定（任意）
    // ==========================
    /**
     * 商品IDが同じなら同一商品とみなす。
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Product))
            return false;
        Product other = (Product) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}