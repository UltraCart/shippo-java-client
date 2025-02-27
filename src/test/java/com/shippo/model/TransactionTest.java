package com.shippo.model;

import com.shippo.Shippo;
import com.shippo.exception.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionTest extends ShippoTest {

	@Test
	public void testValidCreate() {
		Transaction testObject = createTransactionFixture();
		assertEquals("SUCCESS", testObject.getStatus());
		assertEquals(Shippo.apiKeyIsTest.get(), testObject.isTest());
	}

	@Test(expected = InvalidRequestException.class)
	public void testInvalidCreate() throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		Transaction.create(getInvalidObjectMap());
	}

	@Test
	public void testRetrieve() throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		Transaction testObject = createTransactionFixture();
		Transaction retrievedObject;

		retrievedObject = Transaction.retrieve((String) testObject.objectId);
		assertEquals(testObject.objectId, retrievedObject.objectId);

	}

	@Test(expected = InvalidRequestException.class)
	public void testInvalidRetrieve() throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		Transaction.retrieve("invalid_id");
	}

	@Test
	public void testListAll() throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		TransactionCollection objectCollection = Transaction.all(null);
		assertNotNull(objectCollection.getData());
	}

	@Test
	public void testListPageSize() throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		Map<String, Object> objectMap = new HashMap<String, Object>();
		objectMap.put("results", "1"); // one result per page
		objectMap.put("page", "1"); // the first page of results
		TransactionCollection TransactionCollection = Transaction
				.all(objectMap);
		assertEquals(TransactionCollection.getData().size(), 1);
	}

	public static Transaction createTransactionFixture() {
		Map<String, Object> objectMap = new HashMap<String, Object>();
		RateCollection rateCollection = RateTest.createRateCollectionFixture();
		List<Rate> rateList = rateCollection.getData();

		Rate selectedRate = selectTestRate(rateList);

		objectMap.put("rate", selectedRate.getObjectId());
		objectMap.put("metadata", "Customer ID 123456");

		try {
			return Transaction.createSync(objectMap);
		} catch (ShippoException e) {
			e.printStackTrace();
		}
		return null;
	}
}
