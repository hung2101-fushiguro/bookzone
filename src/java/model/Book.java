package model;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {

    private int id;
    private String title;
    private String author;

    public Book(int id, String title, String author, String description, double price, int quantity, String imageURL, int categoryID, Date created_at, int discount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
        this.categoryID = categoryID;
        this.created_at = created_at;
        this.discount = discount;
    }
    private String description;
    private double price;
    private int quantity;
    private String imageURL;
    private int categoryID;
    private Date created_at;
    private String categoryName;
    private int discount;

    public Book(int id, String title, String author, String description, double price, int quantity, String imageURL, int categoryID, Date created_at, String categoryName, int discount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
        this.categoryID = categoryID;
        this.created_at = created_at;
        this.categoryName = categoryName;
        this.discount = discount;
    }

    public Book(int id, String title, String author, String description, double price, int quantity, String imageURL, int categoryID, Date created_at) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
        this.categoryID = categoryID;
        this.created_at = created_at;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title=" + title + ", author=" + author + ", description=" + description + ", price=" + price + ", quantity=" + quantity + ", imageURL=" + imageURL + ", categoryID=" + categoryID + ", created_at=" + created_at + '}';
    }

    public static Book fromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setDescription(rs.getString("description"));
        book.setPrice(rs.getDouble("price"));
        book.setQuantity(rs.getInt("quantity"));
        book.setImageURL(rs.getString("image_url"));
        book.setCategoryID(rs.getInt("category_id"));
        book.setCreated_at(rs.getTimestamp("created_at"));

        // optional if SELECT có join categories.name
        try {
            book.setCategoryName(rs.getString("category_name"));
        } catch (SQLException e) {
            // ignore nếu không có cột này
        }

        // optional nếu SELECT có discount
        try {
            book.setDiscount(rs.getInt("discount"));
        } catch (SQLException e) {
            book.setDiscount(0); // mặc định
        }

        return book;
    }

}
