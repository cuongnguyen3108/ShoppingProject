package vn.shopping.project.models;

public class Product {
    private String name;
    private String desc;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(String name, String desc, double price, int quantity) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
