package model;

public class CartItem {

    private Book book;
    private int quantity;
    private String status; // "chưa thanh toán", "đang xử lý"
    private Accessory accessory;

    public CartItem() {
    }

    public CartItem(Book book, int quantity, String status) {
        this.book = book;
        this.quantity = quantity;
        this.status = status;
    }

    public CartItem(Book book, int quantity, String status, Accessory accessory) {
        this.book = book;
        this.quantity = quantity;
        this.status = status;
        this.accessory = accessory;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }
    
    

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
