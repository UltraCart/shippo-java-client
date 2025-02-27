package com.shippo.model;

import com.shippo.Shippo;
import com.shippo.exception.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AddressTest extends ShippoTest {

    @Test
    public void testValidCreate() {
        Address address = createAddressFixture1();
        assertTrue(address.getIsComplete());
        assertEquals(Shippo.apiKeyIsTest.get(), address.isTest());
    }

    @Test(expected = InvalidRequestException.class)
    public void testInvalidCreate() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Address.create(getInvalidObjectMap());
    }

    @Test
    public void testRetrieve() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Address testObject = createAddressFixture1();
        Address retrievedObject;

        retrievedObject = Address.retrieve((String) testObject.getObjectId());
        assertEquals(testObject.getObjectId(), retrievedObject.getObjectId());

    }

    @Test(expected = InvalidRequestException.class)
    public void testInvalidRetrieve() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Address.retrieve("invalid_id");
    }

    @Test
    public void testListAll() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        AddressCollection objectCollection = Address.all(null);
        assertNotNull(objectCollection.getData());
    }

    @Test
    public void testListPageSize() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("results", "1"); // one result per page
        objectMap.put("page", "1"); // the first page of results
        AddressCollection addressCollection = Address.all(objectMap);
        assertEquals(addressCollection.getData().size(), 1);
    }

    @Test
    public void testInvalidAddress() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Address testAddress = createInvalidAddressFixture();
        assertFalse(testAddress.getValidationResults().getIsValid());
        assertTrue(testAddress.getValidationResults().getValidationMessages().size() > 0);
        assertNotNull(testAddress.getValidationResults().getValidationMessages().get(0).getSource());
        assertNotNull(testAddress.getValidationResults().getValidationMessages().get(0).getCode());
        assertNotNull(testAddress.getValidationResults().getValidationMessages().get(0).getText());
    }

    @Test
    public void testValidAddress() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Address testAddress = createAddressFixture1();
        Address validatedAddress = Address.validate(testAddress.getObjectId());
        assertTrue(validatedAddress.getValidationResults().getIsValid());
        assertEquals(validatedAddress.getValidationResults().getValidationMessages().size(), 0);
    }

    public static Address createAddressFixture1() {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("name", "Undefault New Wu");
        objectMap.put("company", "Shippo");
        objectMap.put("street1", "Clayton St.");
        objectMap.put("street_no", "215");
        objectMap.put("street2", null);
        objectMap.put("city", "San Francisco");
        objectMap.put("state", "CA");
        objectMap.put("zip", "94117");
        objectMap.put("country", "US");
        objectMap.put("phone", "+1 555 341 9393");
        objectMap.put("email", "test@goshipppo.com");
        objectMap.put("is_residential", false);
        objectMap.put("metadata", "Customer ID 123456");

        try {
            return Address.create(objectMap);
        } catch (ShippoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Address createAddressFixture2() {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("name", "Second New Wu");
        objectMap.put("company", "Hippo");
        objectMap.put("street1", "965 Mission St");
        objectMap.put("street2", null);
        objectMap.put("city", "San Francisco");
        objectMap.put("state", "CA");
        objectMap.put("zip", "94103");
        objectMap.put("country", "US");
        objectMap.put("phone", "+1 415 111 1111");
        objectMap.put("email", "test@goshipppo.com");
        objectMap.put("is_residential", false);
        objectMap.put("metadata", "Customer ID 1234567");

        try {
            return Address.create(objectMap);
        } catch (ShippoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Address createInternationalAddressFixture() {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("name", "Ms Hippo");
        objectMap.put("company", "Regents Park");
        objectMap.put("street1", "Outer Cir");
        objectMap.put("street2", null);
        objectMap.put("city", "London");
        objectMap.put("state", null);
        objectMap.put("zip", "NW1 4RY");
        objectMap.put("country", "GB");
        objectMap.put("phone", "+1 555 341 9393");
        objectMap.put("email", "ms-hippo@goshipppo.com");
        objectMap.put("is_residential", false);
        objectMap.put("metadata", "For Order Number 123");

        try {
            return Address.create(objectMap);
        } catch (ShippoException e) {
            e.printStackTrace();
        }
        return null;
    }

    // this address does not exist
    public static Address createInvalidAddressFixture() {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("name", "Undefault New Wu");
        objectMap.put("street1", "Clayton St.");
        objectMap.put("street_no", "215215");
        objectMap.put("street2", null);
        objectMap.put("city", "San Francisco");
        objectMap.put("state", "CA");
        objectMap.put("zip", "94117");
        objectMap.put("country", "US");
        objectMap.put("phone", "+1 555 341 9393");
        objectMap.put("email", "test@goshipppo.com");
        objectMap.put("is_residential", false);
        objectMap.put("metadata", "Customer ID 123456");
        objectMap.put("validate", true);

        try {
            return Address.create(objectMap);
        } catch (ShippoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
