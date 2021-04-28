package br.com.ctseducare.ctscontas.model;

public class Account {

    private int code;
    private String type;
    private int category;
    private int subcategory;
    private String dueDate;
    private double dueValue;
    private String paymentDate;
    private double paymentValue;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(int subcategory) {
        this.subcategory = subcategory;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public double getDueValue() {
        return dueValue;
    }

    public void setDueValue(double dueValue) {
        this.dueValue = dueValue;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(double paymentValue) {
        this.paymentValue = paymentValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
