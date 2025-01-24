package RestAssured;

//import static org.testng.AssertJUnit.assertThat;
//import static org.testng.AssertJUnit.assertThat;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class IATest {

	private static final String BASE_URL = "https://restful-booker.herokuapp.com";
	private static final String API_PATH = "/booking";
	BookingDetails bd = new BookingDetails();

	@BeforeTest
	public void beforeTest() {
		// Reset base URL before all tests
		RestAssured.baseURI = BASE_URL;
	}

	@Test
	public void testAddBooking_PositiveScenario() throws Exception {
		// Booking details
		bd.PositiveScenario();

	}

	@Test
	public void testAddBooking_MissingFirstName() throws Exception {
		// Booking details with missing first name
		bd.MissingFirstName();
	}
}