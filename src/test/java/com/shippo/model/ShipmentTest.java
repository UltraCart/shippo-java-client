package com.shippo.model;

import com.shippo.Shippo;
import com.shippo.exception.*;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class ShipmentTest extends ShippoTest {

    @Test
    public void testValidCreate() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Shipment testObject = createShipmentFixture();
        assertEquals("SUCCESS", testObject.getStatus());
        assertEquals(Shippo.apiKeyIsTest.get(), testObject.isTest());
        Address addressTo = (Address) testObject.getAddressTo();
        assertTrue(addressTo.getIsComplete());
    }

    @Test(expected = InvalidRequestException.class)
    public void testInvalidCreate() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Shipment.create(getInvalidObjectMap());
    }

    @Test
    public void testRetrieve() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Shipment testObject = createShipmentFixture();
        Shipment retrievedObject;

        retrievedObject = Shipment.retrieve((String) testObject.objectId);
        assertEquals(testObject.objectId, retrievedObject.objectId);

    }

    @Test(expected = InvalidRequestException.class)
    public void testInvalidRetrieve() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Shipment.retrieve("invalid_id");
    }

    @Test
    public void testListAll() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        ShipmentCollection objectCollection = Shipment.all(null);
        assertNotNull(objectCollection.getData());
    }

    @Test
    public void testListPageSize() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            APIException {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("results", "1"); // one result per page
        objectMap.put("page", "1"); // the first page of results
        ShipmentCollection ShipmentCollection = Shipment.all(objectMap);
        assertEquals(ShipmentCollection.getData().size(), 1);
    }

    public static Shipment createShipmentFixture() {
        // Create prerequisite helper objects
        Address addressFrom = AddressTest.createAddressFixture1();
        Address addressTo = AddressTest.createAddressFixture2();
        Parcel parcel = ParcelTest.createParcelFixture();

        TimeZone tz = TimeZone.getTimeZone("PST");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        List<Parcel> parcels = new ArrayList<Parcel>();
        parcels.add(parcel);
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("address_from", addressFrom.getObjectId());
        objectMap.put("address_to", addressTo.getObjectId());
        objectMap.put("parcels", parcels);
        objectMap.put("extra", "{\"signature_confirmation\": true}");
        objectMap.put("customs_declaration", null);
        objectMap.put("shipment_date", df.format(new Date()));
        objectMap.put("metadata", "Customer ID 123456");
        objectMap.put("async", false);

        try {
            return Shipment.create(objectMap);
        } catch (ShippoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
