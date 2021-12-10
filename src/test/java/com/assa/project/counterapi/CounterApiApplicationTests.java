package com.assa.project.counterapi;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.assa.project.counterapi.entity.Counter;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.slf4j.LoggerFactory;

/* Launches a spring context in unit test */
@RunWith(SpringRunner.class)
/* The spring context that we would want to launch is a springBoot test */
@SpringBootTest
class CounterApiApplicationTests {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String API_ROOT = "http://localhost:8080/api/counters";

	@Test
	void contextLoads() {
		log.info("***Test is running***");
	}

	private Counter createRandomCounter() {

		Counter counter = new Counter();

		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk" + "lmnopqrstuvwxyz";
		int len = 10;
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));

		counter.setName(sb.toString());

		rnd = new Random();
		int low = 1;
		int high = 1000;
		int result = rnd.nextInt(high - low) + low;

		counter.setValue(result);

		return counter;
	}

	private void createCounterAsUri(Counter counter) {
		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(counter)
				.post(API_ROOT);

		// return API_ROOT + "/" + response.jsonPath();
	}

	@Test
	public void whenCreateNewCounter_thenCreated() {
		Counter counter = createRandomCounter();

		// Performs Post request with body as a counter
		// With REST Assured, you can deserialize this JSON object
		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(counter)
				.post(API_ROOT);

		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
	}

	@Test
	public void whenGetAllCounters_thenOK() {

		// Performs Get request to URI
		Response response = RestAssured.get(API_ROOT);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	@Test
	public void whenGetCounterByName_thenOK() {

		Counter counter = createRandomCounter();

		createCounterAsUri(counter);

		Response response = RestAssured.get(API_ROOT + "?name=" + counter.getName());

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());

		// Counter[] counters = RestAssured.given().when().get(API_ROOT + "?name=" +
		// counter.getName()).as(Counter[].class);
		// assertTrue(response.as(List.class).size() > 0);
	}

	@Test
	public void whenGetNotExistCounterByName_thenNotFound() {
		Counter counter = createRandomCounter();
		Response response = RestAssured.get(API_ROOT + "?name=" + counter.getName());

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}

	// Creating new counter with name=null
	@Test
	public void whenInvalidCounterNameNull_thenError() {
		Counter counter = createRandomCounter();
		counter.setName(null);
		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(counter)
				.post(API_ROOT);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode());
	}

	// Creating new counter with name=""
	/* http://localhost:8080/api/counters?name=...&increment */
	/* http://localhost:8080/api/counters?name=...&increment=true */
	@Test
	public void whenInvalidCounterName_thenError() {
		Counter counter = createRandomCounter();
		counter.setName("");
		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(counter)
				.post(API_ROOT);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode());
	}

	// Incrementing counter value OK
	@Test
	public void whenUpdateCreatedCounter_thenUpdated() {
		Counter counter = createRandomCounter();
		int counterValue = counter.getValue();

		createCounterAsUri(counter);

		Response response = RestAssured.put(API_ROOT + "?name=" + counter.getName() + "&increment");

		Response secondResponse = RestAssured.put(API_ROOT + "?name=" + counter.getName() + "&increment=true");

		JSONObject jsonObject;
		int val = 0;

		try {
			jsonObject = new JSONObject(response.asString());
			val = jsonObject.getInt("value");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertEquals(counterValue + 1, val);
		assertEquals(HttpStatus.OK.value(), secondResponse.getStatusCode());

	}

	// Incrementing counter value Not OK
	@Test
	public void whenUpdateCreatedCounter_thenNotUpdated() {
		Counter counter = createRandomCounter();
		createCounterAsUri(counter);

		Response response = RestAssured.put(API_ROOT + "?name=" + counter.getName() + "&increment=hello");

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

	}

}
