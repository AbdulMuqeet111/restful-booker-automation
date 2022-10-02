package modelBuilder;

import model.Booking;
import model.BookingDates;
import model.Token;
import net.datafaker.Faker;

import java.time.LocalDate;

public class TokenBuilder {
    public static Token createToken(String userName, String password)
    {
        Token token = new Token();
        token.setPassword(password);
        token.setUserName(userName);
        return token;
    }
}
