package ua.southpost.resource.backup.web.model.dto.contact;

import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class ContactInfoTest {

	private ContactInfo testObj = new TestContactInfo();

	@Test
	public void getPhonesCSV() {
		assertNotNull(testObj.getPhonesCSV());
	}

	@Test
	public void getEmailsCSV() {
		assertNotNull(testObj.getEmailsCSV());
	}

	private static class TestContactInfo implements ContactInfo {

		@Override
		public LocationInfo getLocation() {
			return null;
		}

		@Override
		public void setLocation(LocationInfo location) {

		}

		@Override
		public List<PhoneInfo> getPhones() {
			return null;
		}

		@Override
		public void setPhones(List<PhoneInfo> phones) {

		}

		@Override
		public List<EmailInfo> getEmails() {
			return null;
		}

		@Override
		public void setEmails(List<EmailInfo> emails) {

		}
	}
}