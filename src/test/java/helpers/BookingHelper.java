package helpers;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import model.Booking;
import model.PartialBooking;

import java.time.LocalDate;

public class BookingHelper {

 public static Response createBooking(Booking booking)
 {
     return given(RestAssured.requestSpecification)
             .body(booking)
             .when()
             .post("/booking");
 }

    public static Response getBookingByID(int bookingId)
    {
        return given(RestAssured.requestSpecification)
                .when()
                .get("/booking/" + bookingId);
    }
    public static Response getBookingByFirstName(String name)
    {
        return given(RestAssured.requestSpecification)
                .when().param("firstname", name)
                .get("/booking" );
    }
    public static Response getBookingByLastName(String name)
    {
        return given(RestAssured.requestSpecification)
                .when().param("lastname", name)
                .get("/booking" );
    }
    public static Response getBookingByFullName(String firstname, String lastname)
    {
        return given(RestAssured.requestSpecification)
                .when().param("lastname", lastname).param("firstname", firstname)
                .get("/booking" );
    }

    public static Response getBookingByCheckInDate(String checkIn) {
        return given(RestAssured.requestSpecification)
                .when().param("checkin", checkIn)
                .get("/booking" );
    }
    public static Response getBookingByCheckOutDate(String checkOut) {
        return given(RestAssured.requestSpecification)
                .when().param("checkout", checkOut)
                .get("/booking" );
    }

    public static Response getBookingByBookingDates(String checkIn, String checkOut) {
        return given(RestAssured.requestSpecification)
                .when().param("checkin", checkIn).param("checkOut", checkOut)
                .get("/booking" );
    }
    public static Response getBookingByDatesAndName(String checkIn, String checkOut, String firstname, String lastname) {
        return given(RestAssured.requestSpecification)
                .when().param("checkin", checkIn).param("checkOut", checkOut).param("firstname", firstname).param("lastname", lastname)
                .get("/booking" );
    }

    public static Response updatePartialBooking(PartialBooking booking, int bookingId) {
        return given(RestAssured.requestSpecification)
                .body(booking)
                .when()
                .header("Cookie", "token=" + AuthHelper.getAuthToken().jsonPath().get("token"))
                .patch("/booking/" + bookingId );
    }

    public static Response deleteBooking(int bookingId) {
        return given(RestAssured.requestSpecification)
                .when()
                .header("Cookie", "token=" + AuthHelper.getAuthToken().jsonPath().get("token"))
                .delete("/booking/" + bookingId);
    }
}
