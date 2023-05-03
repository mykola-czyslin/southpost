package ua.southpost.resource.commons.model.entity;

import java.util.List;

public interface ContactEntity<IA, A extends AddressEntity<IA>, IP, P extends PhoneEntity<IP>, IM, M extends EmailEntity<IM>> {
	A getAddress();

	void setAddress(A location);

	List<P> getPhones();

	void setPhones(List<P> phones);

	List<M> getEmails();

	void setEmails(List<M> emails);
}
