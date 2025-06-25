package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.TestDataProvider;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BookingApiTests extends BaseTest {

    @Epic("Booking API")
    @Feature("Create Booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifying that a new booking is created in /booking endpoint")
    @Test(dataProvider = "bookingData", dataProviderClass = TestDataProvider.class)
    public void createBooking(Map<String, Object> booking) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));
        }catch (IOException ioException){
            System.err.println("Error while printing map" + ioException.getMessage());
        }

        Response response =
        given()
                .contentType(ContentType.JSON)
                .body(booking)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .log().body()
                .body("booking.firstname", equalTo(booking.get("firstname")))
                .body("booking.bookingdates.checkin",equalTo(((Map<String,Object>) booking.get("bookingdates")).get("checkin").toString()))
                .extract().response();

        int bookingid = 0;

        try {
            bookingid = response.path("bookingid");
            Files.write(Paths.get("bookingid.txt"), String.valueOf(bookingid).getBytes());
        }catch (IOException ioException){
            System.err.println("Error while saving the bookingid" + ioException.getMessage());
        }
    }

    @Epic("Booking API")
    @Feature("Get Booking by Id")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifying that /booking{id} endpoint returns a specific booking id")
    @Test
    public void getBookingById() {

        int bookingid = 0;
        try {
            String content = Files.readString(Paths.get("bookingid.txt"));
            bookingid = Integer.parseInt(content);
        }catch (IOException ioException){
            System.err.println("Error while reading bookingid.txt" + ioException.getMessage());
        }

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("booking/" + bookingid)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
                //.body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
    }

}
