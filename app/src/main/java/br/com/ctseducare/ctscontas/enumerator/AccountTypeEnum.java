package br.com.ctseducare.ctscontas.enumerator;

public enum AccountTypeEnum {

    FIX("Fixa"),
    VARIABLE("Variável");

    private String description;

    AccountTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
