package tests;

import base.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.TestDataProvider;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class BookingApiTests extends BaseTest {

    @Epic("Booking API")
    @Feature("Get Booking by Id")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifying that /booking{id} endpoint returns a specific booking id")
    @Test
    public void getBookingIds(){
        given()
              .contentType(ContentType.JSON)
        .when()
              .get("booking/4187")
        .then()
              .statusCode(200)
              .body("size()", greaterThan(0))
                .body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
    }

    @Epic("Booking API")
    @Feature("Create Booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifying that a new booking is created in /booking endpoint")
    @Test(dataProvider = "bookingData", dataProviderClass = TestDataProvider.class)
    public void createBooking(Map<String, Object> booking) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking));

        given()
                .contentType(ContentType.JSON)
                .body(booking)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .log().body()
                .body("booking.firstname", equalTo(booking.get("firstname")))
                .body("booking.bookingdates.checkin",equalTo(((Map<String,Object>) booking.get("bookingdates")).get("checkin").toString()));
    }

}
