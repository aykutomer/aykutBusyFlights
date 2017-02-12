package com.busyflights.service;


public class ToughJetFlightsResponse {

        private String carrier;
        private Double basePrice;
        private Double tax;
        private Double discount;
        private String departureAirportName;
        private String arrivalAirportName;
        private int departureDay;
        private int departureMonth;
        private int departureYear;

        public static String getSupplier() {
                return supplier;
        }

        private static final String supplier = "ToughJet";


        public ToughJetFlightsResponse(String carrier, Double basePrice, Double tax, Double discount, String departureAirportName, String arrivalAirportName, int departureDay, int departureMonth, int departureYear, int returnDay, int returnMonth, int returnYear) {
                this.carrier = carrier;
                this.basePrice = basePrice;
                this.tax = tax;
                this.discount = discount;
                this.departureAirportName = departureAirportName;
                this.arrivalAirportName = arrivalAirportName;
                this.departureDay = departureDay;
                this.departureMonth = departureMonth;
                this.departureYear = departureYear;
                this.returnDay = returnDay;
                this.returnMonth = returnMonth;
                this.returnYear = returnYear;
        }

        private int returnDay;
        private int returnMonth;
        private int returnYear;

        public ToughJetFlightsResponse() {


        }

        public String getCarrier() {
                return carrier;
        }

        public void setCarrier(String carrier) {
                this.carrier = carrier;
        }

        public Double getBasePrice() {
                return basePrice;
        }

        public void setBasePrice(Double basePrice) {
                this.basePrice = basePrice;
        }

        public Double getTax() {
                return tax;
        }

        public void setTax(Double tax) {
                this.tax = tax;
        }

        public Double getDiscount() {
                return discount;
        }

        public void setDiscount(Double discount) {
                this.discount = discount;
        }

        public String getDepartureAirportName() {
                return departureAirportName;
        }

        public void setDepartureAirportName(String departureAirportName) {
                this.departureAirportName = departureAirportName;
        }

        public String getArrivalAirportName() {
                return arrivalAirportName;
        }

        public void setArrivalAirportName(String arrivalAirportName) {
                this.arrivalAirportName = arrivalAirportName;
        }

        public int getDepartureDay() {
                return departureDay;
        }

        public void setDepartureDay(int departureDay) {
                this.departureDay = departureDay;
        }

        public int getDepartureMonth() {
                return departureMonth;
        }

        public void setDepartureMonth(int departureMonth) {
                this.departureMonth = departureMonth;
        }

        public int getDepartureYear() {
                return departureYear;
        }

        public void setDepartureYear(int departureYear) {
                this.departureYear = departureYear;
        }

        public int getReturnDay() {
                return returnDay;
        }

        public void setReturnDay(int returnDay) {
                this.returnDay = returnDay;
        }

        public int getReturnMonth() {
                return returnMonth;
        }

        public void setReturnMonth(int returnMonth) {
                this.returnMonth = returnMonth;
        }

        public int getReturnYear() {
                return returnYear;
        }

        public void setReturnYear(int returnYear) {
                this.returnYear = returnYear;
        }

        @Override
        public String toString() {
                return "ToughJetFlightsResponse{" +
                        "carrier='" + carrier + '\'' +
                        ", basePrice=" + basePrice +
                        ", tax=" + tax +
                        ", discount=" + discount +
                        ", departureAirportName='" + departureAirportName + '\'' +
                        ", arrivalAirportName='" + arrivalAirportName + '\'' +
                        ", departureDay=" + departureDay +
                        ", departureMonth=" + departureMonth +
                        ", departureYear=" + departureYear +
                        ", returnDay=" + returnDay +
                        ", returnMonth=" + returnMonth +
                        ", returnYear=" + returnYear +
                        '}';
        }
}

