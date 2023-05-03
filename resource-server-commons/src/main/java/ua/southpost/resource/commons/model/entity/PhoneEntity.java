package ua.southpost.resource.commons.model.entity;

public interface PhoneEntity<I> extends Identity<I> {
    String getDisplayNumber();

    void setDisplayNumber(String value);

    String getSearchNumber();

    void setSearchNumber(String value);

    String getDescription();

    void setDescription();
}
