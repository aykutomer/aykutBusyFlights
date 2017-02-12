package com.busyflights.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;


public class FlightResults
{
    private String airline;
    private String supplier;
    private Double fare;
    private String departureAirCode;
    private String destinationAirCode;
    private Date departureDate;
    private Date arrivalDate;


    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Double getFare() {

        return BigDecimal.valueOf(fare).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getDepartureAirCode() {
        return departureAirCode;
    }

    public void setDepartureAirCode(String departureAirCode) {
        this.departureAirCode = departureAirCode;
    }

    public String getDestinationAirCode() {
        return destinationAirCode;
    }

    public void setDestinationAirCode(String destinationAirCode) {
        this.destinationAirCode = destinationAirCode;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
