package com.busyflights.service;


import java.util.ArrayList;
import java.util.Date;

public interface FlightSearch {


    public ArrayList<CrazyAirFlightsResponse> crazyAirSearchResults(String origin,
                                                                    String destination,
                                                                    String departureDate,
                                                                    String arrivalDate,
                                                                    int numberOfPassengers);

    public ArrayList<ToughJetFlightsResponse> toughJetSearchResults(String from,
                                                                    String to,
                                                                    int departureDay,
                                                                    int departureMonth,
                                                                    int departureYear,
                                                                    int returnDay,
                                                                    int returnMonth,
                                                                    int returnYear,
                                                                    int numberOfAdults);


    public String searchFlights(String origin,
                                String destination,
                                Date departureDate,
                                Date returnDate,
                                int numberOfPassengers);

    public void logResponses(ArrayList<CrazyAirFlightsResponse>  crazyAirFlightsResponse,
                             ArrayList<ToughJetFlightsResponse>  toughJetFlightsResponse);

}
