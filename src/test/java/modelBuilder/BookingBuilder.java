package modelBuilder;


import model.Booking;
import model.BookingDates;
import model.PartialBooking;
import net.datafaker.Book;
import net.datafaker.Faker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BookingBuilder {
    public static Booking createBookingData()
    {
        Faker faker = new Faker();
        Booking booking = new Booking();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        BookingDates dates = new BookingDates();
        dates.setCheckIn(date.format(faker.date().future(5, TimeUnit.DAYS)));
        dates.setCheckOut(date.format(faker.date().future(15, TimeUnit.DAYS)));


        booking.setFirstName(faker.name().firstName());
        booking.setLastName(faker.name().lastName());
        booking.setTotalPrice(faker.number().numberBetween(100, 3000));
        booking.setDepositPaid(true);
        booking.setBookingDates(dates);
        booking.setAdditionalNeeds("DoubleBed and Non Smoking Room");
        return booking;
    }
    public static PartialBooking createPartialBooking()
    {
        Faker faker = new Faker();
        PartialBooking booking = new PartialBooking();

        booking.setFirstName(faker.name().firstName());
        booking.setLastName(faker.name().lastName());
        booking.setTotalPrice(faker.number().numberBetween(100, 3000));
        booking.setAdditionalNeeds("Single bed and Non Smoking Room");
        return booking;
    }
}
