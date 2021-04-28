package br.com.ctseducare.ctscontas.model;

import java.util.Objects;

public class Category {

    private int code;
    private String description;

    public Category() {

    }

    public Category(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Category category = (Category) object;
        return code == category.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
