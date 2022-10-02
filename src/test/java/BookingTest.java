import helpers.BookingHelper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;

import static modelBuilder.BookingBuilder.createPartialBooking;
import static org.assertj.core.api.Assertions.*;
import model.Booking;
import model.BookingResponse;

import model.PartialBooking;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import static modelBuilder.BookingBuilder.createBookingData;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class BookingTest {
    private static BookingResponse bookingResponse;
    @BeforeAll
    public static void setup()
    {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .addHeader("Content-Type", "application/json")
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().expectResponseTime(lessThan(5000L)).build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }
    @Order(0)
    @Test
    public void CreateBookingTest()
    {
        Booking booking = createBookingData();

        Response response = BookingHelper.createBooking(booking);
        response.then().assertThat().statusCode(200);
        bookingResponse = response.jsonPath().getObject("", BookingResponse.class);
    }

    @Test
    @Order(2)
    public void GetBookingTestValidID() {
        Response response = BookingHelper.getBookingByID(bookingResponse.getBookingId());
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(response.jsonPath().getString("firstname"), bookingResponse.getBooking().getFirstName());
        Assert.assertEquals(response.jsonPath().getString("lastname"), bookingResponse.getBooking().getLastName());
        Assert.assertEquals(response.jsonPath().getBoolean("depositpaid"), bookingResponse.getBooking().getDepositPaid());
        Assert.assertEquals(response.jsonPath().getInt("totalprice"), bookingResponse.getBooking().getTotalPrice());
        Assert.assertEquals(response.jsonPath().get("bookingdates.checkin"), bookingResponse.getBooking().getBookingDates().getCheckIn());
        Assert.assertEquals(response.jsonPath().get("bookingdates.checkout"), bookingResponse.getBooking().getBookingDates().getCheckOut());
    }
    @Test
    @Order(2)
    public void GetBookingTestInvalidID()
    {
        Response response = BookingHelper.getBookingByID(921812931);
        response.then().assertThat().statusCode(404);
    }
    @Test
    @Order(2)
    public void GetBookingByFirstName()
    {
        Response response = BookingHelper.getBookingByFirstName(bookingResponse.getBooking().getFirstName());
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("bookingid", hasSize(greaterThan(0)));
    }
    @Test
    @Order(2)
    public void GetBookingByLastName()
    {
        Response response = BookingHelper.getBookingByLastName(bookingResponse.getBooking().getLastName());
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("bookingid", hasSize(greaterThan(0)));
    }
    @Test
    @Order(2)
    public void GetBookingByFullName()
    {
        Response response = BookingHelper.getBookingByFullName(bookingResponse.getBooking().getFirstName(), bookingResponse.getBooking().getLastName());
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("bookingid", hasSize(greaterThan(0)));
    }
    @Test
    @Order(2)
    public void GetBookingsByCheckInDate()
    {
        Response response = BookingHelper.getBookingByCheckInDate(bookingResponse.getBooking().getBookingDates().getCheckIn());
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("bookingid", hasSize(greaterThan(0)));
    }
    @Test
    @Order(2)
    public void GetBookingsByCheckOutDate()
    {
        Response response = BookingHelper.getBookingByCheckOutDate(bookingResponse.getBooking().getBookingDates().getCheckOut());
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("bookingid", hasSize(greaterThan(0)));
        response.prettyPrint();
    }
    @Test
    @Order(2)
    public void GetBookingByBookingDates()
    {
        Response response = BookingHelper.getBookingByBookingDates(bookingResponse.getBooking().getBookingDates().getCheckIn(), bookingResponse.getBooking().getBookingDates().getCheckOut());
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("bookingid", hasSize(greaterThan(0)));
    }
    @Test
    @Order(3)
    public void PartialUpdateBooking()
    {
        PartialBooking booking = createPartialBooking();
        Response response = BookingHelper.updatePartialBooking(booking, bookingResponse.getBookingId());
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(response.jsonPath().getString("firstname"), booking.getFirstName());
        Assert.assertEquals(response.jsonPath().getString("lastname"), booking.getLastName());
        Assert.assertEquals(response.jsonPath().getString("additionalneeds"), booking.getAdditionalNeeds());

    }
    @Test
    @Order(4)
    public void DeleteBooking()
    {
        Response response = BookingHelper.deleteBooking(bookingResponse.getBookingId());
        response.then().assertThat().statusCode(201);
    }

}
