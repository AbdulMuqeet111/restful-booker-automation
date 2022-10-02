package helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Booking;
import model.Token;
import modelBuilder.TokenBuilder;

import static io.restassured.RestAssured.given;

public class AuthHelper {
    private static Token token = TokenBuilder.createToken("admin", "password123");
    public static Response getAuthToken()
    {
        return given(RestAssured.requestSpecification)
                .body(token)
                .when()
                .post("/auth");
    }
}
