package br.com.ctseducare.ctscontas.model;

import java.util.Objects;

public class Subcategory {

    private int code;
    private int codeCategory;
    private String description;

    public Subcategory() {

    }

    public Subcategory(int code, int codeCategory, String description) {
        this.code = code;
        this.codeCategory = codeCategory;
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
        Subcategory that = (Subcategory) object;
        return code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

}
