package ua.southpost.resource.commons.model.entity;

public interface AddressEntity<I> extends Identity<I> {

    String getAddressLine1();

    void setAddressLine1(String value);

    String getAddressLine2();

    void setAddressLine2(String value);

    String getAddressLine3();

    void setAddressLine3(String value);

    String getSubRegion();

    void setSubRegion(String value);

    String getRegion();

    void setRegion(String value);

    String getCountryCode();

    void setCountryCode();
    String getPostalCode();
}
