package RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertThat;

import org.testng.Reporter;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BookingDetails {
	private static final String BASE_URL = "https://restful-booker.herokuapp.com";
	private static final String API_PATH = "/booking";

	public void PositiveScenario() {
		// TODO Auto-generated method stub
		String firstName = "firstName";
		String lastName = "lastName";
		double totalPrice = 10.11;
		boolean depositPaid = true;
		String checkin = "2022-01-01";
		String checkout = "2024-01-01";
		String additionalNeeds = "testAdd";

		// Create booking request body
		String requestBody = String.format(
				"{\n" + "  \"firstname\" : \"%s\",\n" + "  \"lastname\" : \"%s\",\n" + "  \"totalprice\" : %.2f,\n"
						+ "  \"depositpaid\" : %b,\n" + "  \"bookingdates\" : {\n" + "    \"checkin\" : \"%s\",\n"
						+ "    \"checkout\" : \"%s\"\n" + "  },\n" + "  \"additionalneeds\" : \"%s\"\n" + "}",
				firstName, lastName, totalPrice, depositPaid, checkin, checkout, additionalNeeds);

		// Add booking and capture response
		Response response = given().contentType(ContentType.JSON).body(requestBody).post(API_PATH);

		// Verify response status code
		response.then().statusCode(is(200));

		// Extract booking ID from response
		int bookingId = response.then().extract().path("bookingid");

		// Validate added booking using list API
		Response listResponse = given().get(API_PATH + "/" + bookingId);

		listResponse.then().statusCode(is(200)).body("firstname", equalTo(firstName))
				.body("lastname", equalTo(lastName))
				// Allow for slight rounding difference in total price
				.body("totalprice", equalTo(10)).body("depositpaid", equalTo(depositPaid))
				.body("bookingdates.checkin", equalTo(checkin)).body("bookingdates.checkout", equalTo(checkout))
				.body("additionalneeds", equalTo(additionalNeeds));

		Reporter.log("testAddBooking_PositiveScenario: PASSED", true);

	}

	public void MissingFirstName() {
		// TODO Auto-generated method stub
		String lastName = "lastName";
		double totalPrice = 10.11;
		boolean depositPaid = true;
		String checkin = "2022-01-01";
		String checkout = "2024-01-01";
		String additionalNeeds = "testAdd";

		// Create booking request body
		String requestBody = String.format(
				"{\n" + "  \"lastname\" : \"%s\",\n" + "  \"totalprice\" : %.2f,\n" + "  \"depositpaid\" : %b,\n"
						+ "  \"bookingdates\" : {\n" + "    \"checkin\" : \"%s\",\n" + "    \"checkout\" : \"%s\"\n"
						+ "  },\n" + "  \"additionalneeds\" : \"%s\"\n" + "}",
				lastName, totalPrice, depositPaid, checkin, checkout, additionalNeeds);

		// Add booking and capture response
		Response response = given().contentType(ContentType.JSON).body(requestBody).post(API_PATH);

		// Verify response status code and extract plain text response
		String responseBody = response.then().statusCode(isOneOf(400, 500)).extract().asString();

		// Assert that the response contains an error message (adjust as needed)
		assertThat(responseBody, containsString("Error"));

		Reporter.log(
				"testAddBooking_MissingFirstName: "
						+ (response.getStatusCode() == 400 || response.getStatusCode() == 500 ? "PASSED" : "FAILED"),
				true);

	}

}
