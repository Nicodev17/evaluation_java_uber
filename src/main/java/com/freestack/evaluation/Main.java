package com.freestack.evaluation;

import com.freestack.evaluation.apis.UberApi;
import com.freestack.evaluation.models.Booking;
import com.freestack.evaluation.models.UberDriver;
import com.freestack.evaluation.models.UberUser;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        UberUser uberUser = new UberUser("joe", "bah");
        UberApi.enrollUser(uberUser);

        UberUser uberUser2 = new UberUser("joe", "bee");
        UberApi.enrollUser(uberUser2);

        UberDriver uberDriver = new UberDriver("jane", "dee", true);
        UberApi.enrollDriver(uberDriver);

        Booking booking1 = UberApi.bookOneDriver(uberUser);
        if (booking1 == null) throw new AssertionError("uberDriver should be found available");

        Booking booking2 = UberApi.bookOneDriver(uberUser2);
        if (booking2 != null) throw new AssertionError("the only one driver is already booked, " +
            "we should not found any free");

        UberApi.finishBooking(booking1);
        int evaluationOfTheUser = 5;
        UberApi.evaluateDriver(booking1, evaluationOfTheUser);

        List<Booking> bookings = UberApi.listDriverBookings(uberDriver);
        if (bookings.size() != 1) throw new AssertionError();
        if (!bookings.get(0).getEvalutation().equals(evaluationOfTheUser)) throw new AssertionError();

        Booking booking3 = UberApi.bookOneDriver(uberUser2);
        if (booking3 == null) throw new AssertionError("uberDriver should be now available");

        List<Booking> unfinishedBookings = UberApi.listUnfinishedBookings();
        if (unfinishedBookings.size() != 1) throw new AssertionError("only booking3 should be unfinished");

        float averageScore = UberApi.averageScore(uberDriver);
        if (averageScore != 5) throw new AssertionError("one eval of 5 should give a mean of 5");
    }
}
