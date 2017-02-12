package com.busyflights.service;


import com.oracle.javafx.jmx.json.JSONException;
import net.minidev.json.JSONArray;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

@Service
public class FlightSearchService implements FlightSearch {

    private final static String urlCrazyAir = "http://localhost:8080/crazyAir";
    private final static String urlToughJet = "http://localhost:8080/toughJet";
    final static Logger logger = Logger.getLogger(FlightSearchService.class);
    final static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");


    public String searchFlights(String origin,
                                String destination,
                                Date departureDate,
                                Date returnDate,
                                int numberOfPassengers) {



        LocalDate localDate = departureDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int departureYear = localDate.getYear();
        int departureMonth = localDate.getMonthValue();
        int departureDay = localDate.getDayOfMonth();
        localDate = returnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int returnYear = localDate.getYear();
        int returnMonth = localDate.getMonthValue();
        int returnDay = localDate.getDayOfMonth();


        MultiValueMap<String, String> paramsToughJet = new LinkedMultiValueMap<String, String>();
        paramsToughJet.add("from", origin);
        paramsToughJet.add("to", destination);
        paramsToughJet.add("departureDay", String.valueOf(departureDay));
        paramsToughJet.add("departureMonth", String.valueOf(departureMonth));
        paramsToughJet.add("departureYear", String.valueOf(departureYear));
        paramsToughJet.add("returnDay", String.valueOf(returnDay));
        paramsToughJet.add("returnMonth", String.valueOf(returnMonth));
        paramsToughJet.add("returnYear", String.valueOf(returnYear));
        paramsToughJet.add("numberOfAdults", String.valueOf(numberOfPassengers));

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(urlToughJet).queryParams(paramsToughJet).build();

        RestTemplate restTemplate = new RestTemplate();
        ArrayList<ToughJetFlightsResponse> toughJetFlightsResponseList = new ArrayList<ToughJetFlightsResponse>();
        ParameterizedTypeReference<ArrayList<ToughJetFlightsResponse>> typeRefToughJet = new ParameterizedTypeReference<ArrayList<ToughJetFlightsResponse>>() {
        };
        logger.info("ThoughJetFlightRequestURL : " + uriComponents.toUri());
        toughJetFlightsResponseList = restTemplate.exchange(uriComponents.toUri(), org.springframework.http.HttpMethod.GET, null, typeRefToughJet).getBody();
        logResponses(null,toughJetFlightsResponseList);



        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("origin", origin);
        params.add("destination", destination);
        params.add("departureDate", formatDate.format(departureDate));
        params.add("arrivalDate", formatDate.format(returnDate));
        params.add("numberOfPassengers", String.valueOf(numberOfPassengers));

        restTemplate = new RestTemplate();
        UriComponents uriComponentsCrazyAir = UriComponentsBuilder.fromHttpUrl(urlCrazyAir).queryParams(params).build();

        ArrayList<CrazyAirFlightsResponse> crazyAirFlightsResponseList = new ArrayList<CrazyAirFlightsResponse>();

        ParameterizedTypeReference<ArrayList<CrazyAirFlightsResponse>> typeRefCrazyAir = new ParameterizedTypeReference<ArrayList<CrazyAirFlightsResponse>>() {};
        logger.info("CrazyAirFlightRequestURL : " + uriComponentsCrazyAir.toUri());
        crazyAirFlightsResponseList = restTemplate.exchange(uriComponentsCrazyAir.toUri(), org.springframework.http.HttpMethod.GET, null, typeRefCrazyAir).getBody();
        logResponses(crazyAirFlightsResponseList,null);


        JSONObject jsonResult = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        ArrayList<FlightResults> flightResultsArrayList;
        flightResultsArrayList = new ArrayList<FlightResults>();


        flightResultsArrayList = gatherFlighResults(crazyAirFlightsResponseList, toughJetFlightsResponseList);


        Collections.sort(flightResultsArrayList, new Comparator<FlightResults>() {
            @Override
            public int compare(FlightResults p1, FlightResults p2) {
                return p1.getFare().intValue() - p2.getFare().intValue();
            }

        });

        for (FlightResults results : flightResultsArrayList) {

            try {


                jsonResult.put("airline", results.getAirline());
                jsonResult.put("supplier", results.getSupplier());
                jsonResult.put("fare", results.getFare());
                jsonResult.put("departureAirCode", results.getDepartureAirCode());
                jsonResult.put("destinationAirCode", results.getDestinationAirCode());
                jsonResult.put("departureDate", results.getDepartureDate());
                jsonResult.put("arrivalDate", results.getArrivalDate());


            } catch (JSONException ex) {
                logger.error(ex.getMessage());

            }
            jsonArray.add(jsonResult);
            jsonResult = new JSONObject();

        }

        return jsonArray.toString();

    }

    public ArrayList<FlightResults> gatherFlighResults(ArrayList<CrazyAirFlightsResponse> crazyAirFlightsResponseList, ArrayList<ToughJetFlightsResponse> toughJetFlightsResponseList) {

        ArrayList<FlightResults> FlightResultsList = new ArrayList<FlightResults>();

        FlightResults flightResults;

        for (CrazyAirFlightsResponse crazyAirFlightsResponse : crazyAirFlightsResponseList) {
            flightResults = new FlightResults();

            flightResults.setAirline(crazyAirFlightsResponse.getAirline());
            flightResults.setSupplier(crazyAirFlightsResponse.getSupplier());
            flightResults.setFare(BigDecimal.valueOf(crazyAirFlightsResponse.getTotalPrice()).setScale(3, RoundingMode.HALF_UP).doubleValue());
            flightResults.setDepartureAirCode(crazyAirFlightsResponse.getDepartureAirCode());
            flightResults.setDestinationAirCode(crazyAirFlightsResponse.getDestinationAirCode());
            flightResults.setDepartureDate(crazyAirFlightsResponse.getDepartureDate());
            flightResults.setArrivalDate(crazyAirFlightsResponse.getArrivalDate());


            FlightResultsList.add(flightResults);

        }

        for (ToughJetFlightsResponse toughJetFlightsResponse : toughJetFlightsResponseList) {
            flightResults = new FlightResults();

            flightResults.setAirline(toughJetFlightsResponse.getCarrier());
            flightResults.setSupplier(toughJetFlightsResponse.getSupplier());
            flightResults.setFare(calculatePrice(toughJetFlightsResponse.getBasePrice(), toughJetFlightsResponse.getTax(), toughJetFlightsResponse.getDiscount()));
            flightResults.setDepartureAirCode(toughJetFlightsResponse.getDepartureAirportName());
            flightResults.setDestinationAirCode(toughJetFlightsResponse.getArrivalAirportName());
            flightResults.setDepartureDate(yearMonthDayToDate(toughJetFlightsResponse.getDepartureYear(), toughJetFlightsResponse.getDepartureMonth(), toughJetFlightsResponse.getDepartureDay()));
            flightResults.setArrivalDate(yearMonthDayToDate(toughJetFlightsResponse.getReturnYear(), toughJetFlightsResponse.getReturnMonth(), toughJetFlightsResponse.getReturnDay()));

            FlightResultsList.add(flightResults);

        }

        return FlightResultsList;

    }

    public double calculatePrice(Double basePrice, Double tax, Double discount) {

        Double totalPrice;
        totalPrice = ((basePrice + tax) * (100 - discount)) / 100;

        return BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public Date yearMonthDayToDate(int year, int month, int day) {

        String sDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
        } catch (ParseException e) {
            logger.error("Error while parsing date " + e.getMessage());
        }

        return date;
    }


    public ArrayList<CrazyAirFlightsResponse> crazyAirSearchResults(String origin,
                                                                    String destination,
                                                                    String departureDate,
                                                                    String arrivalDate,
                                                                    int numberOfPassengers) {


        CrazyAirFlightsRequest myFlight = new CrazyAirFlightsRequest(origin, destination, getDateFromString(departureDate), getDateFromString(arrivalDate), numberOfPassengers);

        CrazyAirFlightsResponse availableCrazyFlights = new CrazyAirFlightsResponse("THY", 1000f, "E", "SAW", "ESB", getDateFromString("2017-01-01"), getDateFromString("2017-01-01"));
        CrazyAirFlightsResponse availableCrazyFlights2 = new CrazyAirFlightsResponse("THY", 1200f, "E", "SAW", "ESB", getDateFromString("2017-01-01"), getDateFromString("2017-01-02"));
        CrazyAirFlightsResponse availableCrazyFlights3 = new CrazyAirFlightsResponse("KLM", 1500f, "E", "SAW", "ESB", getDateFromString("2017-01-01"), getDateFromString("2017-01-01"));



        ArrayList<CrazyAirFlightsResponse> crazyAirFlightsResponseList = new ArrayList<CrazyAirFlightsResponse>();
        crazyAirFlightsResponseList.add(availableCrazyFlights);
        crazyAirFlightsResponseList.add(availableCrazyFlights2);
        crazyAirFlightsResponseList.add(availableCrazyFlights3);


        ArrayList<CrazyAirFlightsResponse> result = new ArrayList<CrazyAirFlightsResponse>();
        result = findCrazyAirFlights(crazyAirFlightsResponseList, myFlight);

        return result;


    }

    public ArrayList<ToughJetFlightsResponse> toughJetSearchResults(String from, String to, int departureDay, int departureMonth, int departureYear, int returnDay, int returnMonth, int returnYear, int numberOfAdults) {


        ToughJetFlightsRequest myFlight = new ToughJetFlightsRequest(from,to,departureDay,departureMonth,departureYear,returnDay,returnMonth,returnYear,numberOfAdults);

        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-config.xml");

        ToughJetFlightsResponse toughJetFlightsResponse = (ToughJetFlightsResponse) context.getBean("toughJet1");
        ToughJetFlightsResponse toughJetFlightsResponse2 = (ToughJetFlightsResponse) context.getBean("toughJet2");
        ToughJetFlightsResponse toughJetFlightsResponse3 = (ToughJetFlightsResponse) context.getBean("toughJet3");


        ArrayList <ToughJetFlightsResponse> ToughJetFlightsResponseList = new ArrayList<ToughJetFlightsResponse>();

        ToughJetFlightsResponseList.add(toughJetFlightsResponse);
        ToughJetFlightsResponseList.add(toughJetFlightsResponse2);
        ToughJetFlightsResponseList.add(toughJetFlightsResponse3);

        ArrayList<ToughJetFlightsResponse> result = new ArrayList<ToughJetFlightsResponse>();
        result = findToughJetFlights(ToughJetFlightsResponseList, myFlight);

        return result;
    }

    public static Date getDateFromString(String dateString) {
        Date date = null;
        try {
            date = formatDate.parse(dateString);
            return date;
        } catch (ParseException e) {
            logger.error("Error while parsing date " + e.getMessage());
        }
        return date;
    }

    public ArrayList<CrazyAirFlightsResponse> findCrazyAirFlights(ArrayList<CrazyAirFlightsResponse> availableFlights, CrazyAirFlightsRequest searchFlight) {

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<CrazyAirFlightsResponse> result = new ArrayList<CrazyAirFlightsResponse>();


        for (CrazyAirFlightsResponse availableFlight : availableFlights) {


            if (
                            searchFlight.getOrigin().equalsIgnoreCase(availableFlight.getDepartureAirCode()) &&
                            searchFlight.getDestination().equalsIgnoreCase(availableFlight.getDestinationAirCode()) &&
                            formatDate.format(searchFlight.getArrivalDate()).equals(formatDate.format(availableFlight.getArrivalDate())) &&
                            formatDate.format(searchFlight.getDepartureDate()).equals(formatDate.format(availableFlight.getDepartureDate()))

                    ) {
                result.add(availableFlight);

            }
        }

        return result;

    }

    public ArrayList<ToughJetFlightsResponse> findToughJetFlights(ArrayList<ToughJetFlightsResponse> availableFlights, ToughJetFlightsRequest searchFlight) {

        ArrayList<ToughJetFlightsResponse> result = new ArrayList<ToughJetFlightsResponse>();


        for (ToughJetFlightsResponse availableFlight : availableFlights) {


            if (

                            searchFlight.getFrom().equalsIgnoreCase(availableFlight.getDepartureAirportName())
                                    &&
                            searchFlight.getTo().equalsIgnoreCase(availableFlight.getArrivalAirportName())
                                    &&
                            (searchFlight.getDepartureDay()==availableFlight.getDepartureDay() &&
                             searchFlight.getDepartureMonth() == availableFlight.getDepartureMonth() &&
                             searchFlight.getDepartureYear()== availableFlight.getDepartureYear())
                                    &&
                            (searchFlight.getReturnDay()==availableFlight.getReturnDay()&&
                            searchFlight.getReturnMonth()==availableFlight.getReturnMonth()&&
                            searchFlight.getReturnYear()==availableFlight.getReturnYear())
               )
            {
                result.add(availableFlight);

            }
        }

        return result;

    }

    public void logResponses(ArrayList<CrazyAirFlightsResponse> crazyAirFlightsResponseArrayList,
                             ArrayList<ToughJetFlightsResponse> toughJetFlightsResponseArrayList){

     if(crazyAirFlightsResponseArrayList!=null){

         for (CrazyAirFlightsResponse crazyAirFlightsResponse : crazyAirFlightsResponseArrayList) {

             logger.info("CrazyAirFlightsResponse :" + crazyAirFlightsResponse.toString());

         }
     }


        if(toughJetFlightsResponseArrayList!=null){

            for (ToughJetFlightsResponse toughJetFlightsResponse : toughJetFlightsResponseArrayList) {

                logger.info("ToughJetFlightsResponse :" + toughJetFlightsResponse.toString());

            }
        }


    }


}
