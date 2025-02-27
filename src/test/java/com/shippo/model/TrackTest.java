package com.shippo.model;

import com.shippo.Shippo;
import com.shippo.exception.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrackTest extends ShippoTest {

    @Test
    public void testGet_TestMode() throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {

        // GIVEN a track requested with Test Mode (see
        // https://goshippo.com/docs/tracking/)
        String testModeCarrier = "shippo";
        String testModeNumber = "SHIPPO_DELIVERED";
        Track track = Track.getTrackingInfo(testModeCarrier, testModeNumber, null);

        // EXPECT
        assertEquals(Shippo.apiKeyIsTest.get(), track.isTest());
        assertEquals(track.getCarrier(), testModeCarrier);
        assertEquals(track.getTrackingNumber(), testModeNumber);
        assertEquals(track.getTrackingStatus().getStatus(), Track.TrackingStatus.DELIVERED);
    }

    @Test(expected = InvalidRequestException.class)
    public void testGetInvalidCarrier() throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        String testModeNumber = "SHIPPO_TRANSIT";
        Track.getTrackingInfo("bad", testModeNumber, null);
    }

    @Test(expected = InvalidRequestException.class)
    public void testGetInvalidCarrierNumber() throws ShippoException {
        String testModeCarrier = "shippo";
        Track.getTrackingInfo(testModeCarrier, "invalid", null);
    }
    /*
     * @Test
     * public void testRegisterWebhook() throws AuthenticationException,
     * InvalidRequestException,
     * APIConnectionException, APIException {
     * Track track = Track.registerTrackingWebhook(carrier, number, "meta", null);
     * checkTrack(track);
     * }
     */
}
