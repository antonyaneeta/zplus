package com.zooplus.aneeta.salestax.dto;

public class Item {

    private String itemName;
    private String itemId;
    private Integer quantity;
    private String category;
    private Boolean imported;
    private Double price;
    private Double salesTax;
    private Boolean exemptFrmTax;
    private Double importSalesTax;
    private Double totalPrice;

    public Item(String itemName, String itemId, String category, Boolean imported, Double price, Double salesTax, Boolean exemptFrmTax, Double importSalesTax, Double totalPrice) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.category = category;
        this.imported = imported;
        this.price = price;
        this.salesTax = salesTax;
        this.exemptFrmTax = exemptFrmTax;
        this.importSalesTax = importSalesTax;
        this.totalPrice = totalPrice;
    }

    public Item() {

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    public Boolean getExemptFrmTax() {
        return exemptFrmTax;
    }

    public void setExemptFrmTax(Boolean exemptFrmTax) {
        this.exemptFrmTax = exemptFrmTax;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(Double salesTax) {
        this.salesTax = salesTax;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getImportSalesTax() {
        return importSalesTax;
    }

    public void setImportSalesTax(Double importSalesTax) {
        this.importSalesTax = importSalesTax;
    }


    @Override
    public String toString() {
        return "Item{" +
                "quantity=" + quantity +
                ", itemName='" + itemName + '\'' +
                ", itemId='" + itemId + '\'' +
                ", imported=" + imported +
                ", price=" + price +
                ", salesTax=" + salesTax +
                ", totalPrice=" + totalPrice +
                ", importSalesTax=" + importSalesTax +
                ", category='" + category + '\'' +
                ", exemptFrmTax=" + exemptFrmTax +
                '}';
    }

    public Item newItem() {
        return new Item(itemName, itemId, category, imported, price, salesTax, exemptFrmTax, importSalesTax, totalPrice);
    }
}
