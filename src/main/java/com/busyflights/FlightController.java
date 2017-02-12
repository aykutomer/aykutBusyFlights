package com.busyflights;

import com.busyflights.service.FlightSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@EnableAutoConfiguration
@ComponentScan
public class FlightController {



    @Autowired
    FlightSearch flightSearch;

    @RequestMapping("/flights")
    public String flightSearchService(@RequestParam("origin") String origin,
                                                       @RequestParam("destination") String destination,
                                                       @RequestParam("departureDate") Date departureDate,
                                                       @RequestParam("returnDate") Date returnDate,
                                                       @RequestParam("numberOfPassengers") int numberOfPassengers) {

        String results;

        results = flightSearch.searchFlights(origin, destination,departureDate,returnDate,numberOfPassengers);

        return results;

        }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FlightController.class, args);
    }


}
