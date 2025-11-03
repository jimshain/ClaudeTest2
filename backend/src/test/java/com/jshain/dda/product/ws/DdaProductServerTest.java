package com.jshain.dda.product.ws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jshain.dda.product.message.DdaProductAddRq;
import com.jshain.dda.product.message.DdaProductAddRs;
import com.jshain.dda.product.message.DdaProductInqRq;
import com.jshain.dda.product.message.DdaProductInqRs;
import com.jshain.dda.product.message.DdaProductKey;
import com.jshain.dda.product.message.DdaProductMo;
import com.jshain.gson.LocalDateAdapter;
import com.jshain.gson.LocalDateTimeAdapter;

/**
 * Integration test for DdaProductServer endpoints.
 *
 * Prerequisites:
 * 1. The DdaProductServer must be running on localhost:8080
 * 2. The database must be initialized with the dda_product table
 *
 * This test:
 * 1. Adds a product via POST /ddaproductadd
 * 2. Retrieves the same product via POST /ddaproductinq
 * 3. Verifies the retrieved data matches what was added
 */
public class DdaProductServerTest {

	private static final String BASE_URL = "http://localhost:8080";
	private static HttpClient httpClient;
	private static Gson gson;

	@BeforeAll
	public static void setup() {
		httpClient = HttpClient.newHttpClient();

		gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
			.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
			.create();
	}

	@Test
	public void testAddAndInquireProduct() throws Exception {
		// Create a unique product for testing
		String testRquid = "TEST-" + System.currentTimeMillis();

		// Create the product key
		DdaProductKey key = new DdaProductKey();
		key.setHoldingCompanyId(1);
		key.setBankId(1);
		key.setBranchId(1);
		key.setProductId("CHK1");

		// Create the product message object
		DdaProductMo productMo = new DdaProductMo();
		productMo.setDdaProductKey(key);
		productMo.setDescription("Test Checking Account");
		productMo.setMinimumOpeningDeposit(Integer.valueOf("100"));
		productMo.setMinimumBalance(Integer.valueOf("25"));
		productMo.setOverdraftLimit(Integer.valueOf("500"));
		productMo.setApy(new BigDecimal("0.0125"));

		// Create the add request
		DdaProductAddRq addRequest = new DdaProductAddRq();
		addRequest.setRquid(testRquid);
		addRequest.setDdaProduct(productMo);

		// Step 1: Add the product
		String addRequestJson = gson.toJson(addRequest);

		HttpRequest addHttpRequest = HttpRequest.newBuilder()
			.uri(URI.create(BASE_URL + "/ddaproductadd"))
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(addRequestJson))
			.build();

		HttpResponse<String> addResponse = httpClient.send(addHttpRequest, HttpResponse.BodyHandlers.ofString());

		// Verify add was successful
		assertEquals(200, addResponse.statusCode(), "Add request should return 200");

		DdaProductAddRs addRs = gson.fromJson(addResponse.body(), DdaProductAddRs.class);
		assertNotNull(addRs, "Add response should not be null");
		assertEquals(testRquid, addRs.getRquid(), "Response rquid should match request");
		assertNotNull(addRs.getStatus(), "Status should not be null");
		assertEquals("100", addRs.getStatus().get(0).getCode(), "Status code should be 100 (success)");

		// Step 2: Inquire for the product
		DdaProductInqRq inqRequest = new DdaProductInqRq();
		inqRequest.setRquid(testRquid + "-INQ");
		inqRequest.setDdaProductKey(key);

		String inqRequestJson = gson.toJson(inqRequest);

		HttpRequest inqHttpRequest = HttpRequest.newBuilder()
			.uri(URI.create(BASE_URL + "/ddaproductinq"))
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(inqRequestJson))
			.build();

		HttpResponse<String> inqResponse = httpClient.send(inqHttpRequest, HttpResponse.BodyHandlers.ofString());

		// Verify inquiry was successful
		assertEquals(200, inqResponse.statusCode(), "Inquiry request should return 200");

		DdaProductInqRs inqRs = gson.fromJson(inqResponse.body(), DdaProductInqRs.class);
		assertNotNull(inqRs, "Inquiry response should not be null");
		assertEquals(testRquid + "-INQ", inqRs.getRquid(), "Response rquid should match request");
		assertNotNull(inqRs.getStatus(), "Status should not be null");
		assertEquals("100", inqRs.getStatus().get(0).getCode(), "Status code should be 100 (success)");

		// Step 3: Verify the retrieved product matches what was added
		DdaProductMo retrievedProduct = inqRs.getDdaProduct();
		assertNotNull(retrievedProduct, "Retrieved product should not be null");

		// Verify key fields
		assertNotNull(retrievedProduct.getDdaProductKey(), "Product key should not be null");
		assertEquals(key.getHoldingCompanyId(), retrievedProduct.getDdaProductKey().getHoldingCompanyId(),
			"Holding company ID should match");
		assertEquals(key.getBankId(), retrievedProduct.getDdaProductKey().getBankId(),
			"Bank ID should match");
		assertEquals(key.getBranchId(), retrievedProduct.getDdaProductKey().getBranchId(),
			"Branch ID should match");
		assertEquals(key.getProductId(), retrievedProduct.getDdaProductKey().getProductId(),
			"Product ID should match");

		// Verify non-key fields
		assertEquals("Test Checking Account", retrievedProduct.getDescription(),
			"Description should match");
		assertEquals(0, Integer.valueOf("100").compareTo(retrievedProduct.getMinimumOpeningDeposit()),
			"Minimum opening deposit should match");
		assertEquals(0, Integer.valueOf("25").compareTo(retrievedProduct.getMinimumBalance()),
			"Minimum balance should match");
		assertEquals(0, Integer.valueOf("500").compareTo(retrievedProduct.getOverdraftLimit()),
			"Overdraft limit should match");
		assertEquals(0, new BigDecimal("0.0125").compareTo(retrievedProduct.getApy()),
			"APY should match");
	}
}
