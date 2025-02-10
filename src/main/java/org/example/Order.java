package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderNumber;

    @OneToMany(mappedBy = "order")
    private List<Product> productList = new ArrayList<>();

    public Order() {
    }

    public Order(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void addProduct(Product product) {
        product.setOrder(this);
        productList.add(product);
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", productList=" + productList +
                '}';
    }
}
