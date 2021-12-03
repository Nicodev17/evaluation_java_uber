package com.freestack.evaluation.models;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "booking")
@Entity
public class Booking {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "start_of_the_booking")
    private Instant startOfTheBooking;

    @Column(name = "evaluation")
    private Integer evaluation;

    @Column(name = "end_of_the_booking")
    private Instant endOfTheBooking;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getStartOfTheBooking() {
        return startOfTheBooking;
    }

    public void setStartOfTheBooking(Instant startOfTheBooking) {
        this.startOfTheBooking = startOfTheBooking;
    }

    public Integer getEvalutation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public Instant getEndOfTheBooking() {
        return endOfTheBooking;
    }

    public void setEndOfTheBooking(Instant endOfTheBooking) {
        this.endOfTheBooking = endOfTheBooking;


    }
}
