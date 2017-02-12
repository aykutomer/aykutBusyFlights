package com.busyflights.service;

import java.util.Date;


public class CrazyAirFlightsResponse {

    private String airline;
    private Float totalPrice;
    private String cabinClass;
    private String departureAirCode;
    private String destinationAirCode;
    private Date departureDate;
    private Date arrivalDate;

    public String getSupplier() {
        return supplier;
    }

    private static final String supplier = "CrazyAir";

    public CrazyAirFlightsResponse() {
    }

    public CrazyAirFlightsResponse(String airline, Float totalPrice, String cabinClass, String departureAirCode, String destinationAirCode, Date departureDate, Date arrivalDate) {
        this.airline = airline;
        this.totalPrice = totalPrice;
        this.cabinClass = cabinClass;
        this.departureAirCode = departureAirCode;
        this.destinationAirCode = destinationAirCode;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
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

    @Override
    public String toString() {
        return "CrazyAirFlightsResponse{" +
                "airline='" + airline + '\'' +
                ", totalPrice=" + totalPrice +
                ", cabinClass='" + cabinClass + '\'' +
                ", departureAirCode='" + departureAirCode + '\'' +
                ", destinationAirCode='" + destinationAirCode + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                '}';
    }
}
