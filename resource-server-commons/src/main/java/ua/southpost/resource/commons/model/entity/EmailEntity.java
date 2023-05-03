package ua.southpost.resource.commons.model.entity;

public interface EmailEntity<I> extends Identity<I> {

    String getEmailAddress();

    void setEmailAddress(String value);

    String getDescription();

    void setDescription(String value);
}
