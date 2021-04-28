package br.com.ctseducare.ctscontas.dto;

import java.util.Objects;

public class AccountDTO {

    public int code;
    public String type;
    public String category;
    public String subcategory;
    public String dueDate;
    public double dueValue;
    public String paymentDate;
    public double paymentValue;
    public String description;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AccountDTO accountVO = (AccountDTO)object;
        return code == accountVO.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

}
