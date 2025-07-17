/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class OrderDetail {

    private int id;
    private int orderId;
    private int bookId;
    private int quantity;
    private double price;
    private String bookTitle;
    private String bookImage;

    public OrderDetail() {
    }

    public OrderDetail(int id, int orderId, int bookId, int quantity, double price, String bookTitle, String bookImage) {
        this.id = id;
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "id=" + id + ", orderId=" + orderId + ", bookId=" + bookId + ", quantity=" + quantity + ", price=" + price + ", bookTitle=" + bookTitle + ", bookImage=" + bookImage + '}';
    }

}
