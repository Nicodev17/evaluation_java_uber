package com.freestack.evaluation.apis;

import com.freestack.evaluation.models.Booking;
import com.freestack.evaluation.models.UberDriver;
import com.freestack.evaluation.models.UberUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UberApi {
    private static List<UberUser> users = new ArrayList<>();
    private static List<UberDriver> drivers = new ArrayList<>();

    private static final String PERSISTANCE_UNIT_NAME = "myPostGreSqlEntityManager";
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory(PERSISTANCE_UNIT_NAME);
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();

    //  Peristence du user créée en base et ajout à la liste des users + persitence de la liste
    public static void enrollUser(UberUser user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);

            users.add(user);
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    public static void enrollDriver(UberDriver driver) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(driver);

            drivers.add(driver);
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    // Réservation d'une course
    public static Booking bookOneDriver(UberUser uberUser) {
        try {
            entityManager.getTransaction().begin();

            Booking booking = new Booking();

            for(UberDriver driver : drivers) {
                if(driver.getAvailable()) {
                    driver.setAvailable(false);
                    booking.setDriverId(driver.getId());
                    booking.setUserId(uberUser.getId());
                    booking.setStartOfTheBooking(Instant.now());
                    booking.setEvaluation(null);
                    booking.setEndOfTheBooking(null);
                    entityManager.persist(booking);
                    return booking;
                }
            }
            return null;
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    public static void finishBooking(Booking booking) {
        try {
            entityManager.getTransaction().begin();

            if(booking.getStartOfTheBooking() != null) {
                booking.setEndOfTheBooking(Instant.now());
                entityManager.persist(booking);

                // Après fin de la course on repasse la disponibilité du driver à true.
                Query query = entityManager.createQuery("SELECT u from UberDriver u WHERE u.Id = :id");
                query.setParameter("id", booking.getDriverId());
                UberDriver driver = (UberDriver) query.getSingleResult();
                driver.setAvailable(true);
            }
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    public static void evaluateDriver(Booking booking, int evaluation) {
        try {
            entityManager.getTransaction().begin();
            booking.setEvaluation(evaluation);
            entityManager.persist(booking);
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    public static List listDriverBookings(UberDriver uberDriver) {
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT b from Booking b WHERE b.driverId = :id");
            query.setParameter("id", uberDriver.getId());
            List bookings = query.getResultList();

            return bookings;
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    public static List listUnfinishedBookings() {
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT b from Booking b WHERE b.endOfTheBooking = null");
            return query.getResultList();
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    public static Float averageScore(UberDriver uberDriver) {
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("Select AVG(b.evaluation) FROM Booking b");
            Double averageEval = (Double) query.getSingleResult();

            Float avg = averageEval.floatValue();
            return avg;
        } finally {
            entityManager.getTransaction().commit();
        }
    }
}
