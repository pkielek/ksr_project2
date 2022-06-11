package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NumericVariable {
    undefined(null,"","","","",false),
    relativeQuantifier(true,"","","","Relative quantifier",false),
    absoluteQuantifier(true,"","","","Absolute quantifier",false),
    leadTime(false,"lead time","have","having","Lead time",false),
//    arrivalDateWeekNumber(false,"made","are","being",true),
    arrivalDateDayOfMonth(false,"made","are","being","Arrival date as day of the month",true),
    staysInWeekendNights(false,"stays in weekend nights","have","having","Stays in weekend nights",false),
    staysInWeekNights(false,"stays in week nights","have","having","Stays in week nights",false),
    numberOfAdults(false,"adults","have","having","Number of adults",false),
    numberOfChildren(false,"children","have","having","Number of children",false),
    requiredCarParkingSpaces(false,"required car parking spaces","have","having","Required car parking spaces",false),
    bookingChanges(false,"booking changes","have","having","Booking changes",false),
    daysInWaitingList(false,"waiting time for booking","have","having","Waiting time for booking",false),
    totalOfSpecialRequests(false,"special requests","have","having","Special requests",false),
    adr(false,"average daily rate","have","having","Average daily rate", false);

    @Getter
    private final Boolean isQuantifier;
    @Getter
    private final String summarizerTitle;
    @Getter
    private final String summarizerPrefix;
    @Getter
    private final String qualifierPrefix;
    @Getter
    private final String name;
    @Getter
    private final Boolean titleBeforeLabel;

    public static NumericVariable findByName(String name) {
        for(NumericVariable variable : NumericVariable.values()) {
            if(variable.getName().equals(name)) {
                return variable;
            }
        }
        return null;
    }

}
