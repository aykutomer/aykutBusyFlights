package com.busyflights;


import com.busyflights.service.CrazyAirFlightsResponse;
import com.busyflights.service.FlightSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@EnableAutoConfiguration
@ComponentScan

public class CrazyAirController  {
    @Autowired
    FlightSearch flightSearch;

    @RequestMapping("/crazyAir")
    public ArrayList<CrazyAirFlightsResponse> crazyAirSearchService(@RequestParam("origin") String origin,
                                                                    @RequestParam("destination") String destination,
                                                                    @RequestParam("departureDate") String departureDate,
                                                                    @RequestParam("arrivalDate") String arrivalDate,
                                                                    @RequestParam("numberOfPassengers") int numberOfPassengers) {



        return flightSearch.crazyAirSearchResults(origin,destination,departureDate,arrivalDate,numberOfPassengers);


        }


}
