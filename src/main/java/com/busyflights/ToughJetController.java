package com.busyflights;

import com.busyflights.service.FlightSearch;
import com.busyflights.service.ToughJetFlightsResponse;
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
public class ToughJetController {
    @Autowired
    FlightSearch flightSearch;
    @RequestMapping("/toughJet")
    public ArrayList<ToughJetFlightsResponse> toughJetSearchService(@RequestParam("from") String from,
                                                                    @RequestParam("to") String to,
                                                                    @RequestParam("departureDay") int departureDay,
                                                                    @RequestParam("departureMonth") int departureMonth,
                                                                    @RequestParam("departureYear") int departureYear,
                                                                    @RequestParam("returnDay") int returnDay,
                                                                    @RequestParam("returnMonth") int returnMonth,
                                                                    @RequestParam("returnYear") int returnYear,
                                                                    @RequestParam("numberOfAdults") int numberOfAdults) {


        return flightSearch.toughJetSearchResults(from, to, departureDay, departureMonth, departureYear, returnDay, returnMonth, returnYear, numberOfAdults);

    }

}
