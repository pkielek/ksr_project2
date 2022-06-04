package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NumericVariable {
    undefined(null,"","","",false),
    relativeQuantifier(true,"","","",false),
    absoluteQuantifier(true,"","","",false),
    leadTime(false,"lead time","have","having",false),
//    arrivalDateWeekNumber(false,"made","are","being",true),
    arrivalDateDayOfMonth(false,"made","are","being",true),
    staysInWeekendNights(false,"stays in weekend nights","have","having",false),
    staysInWeekNights(false,"stays in week nights","have","having",false),
    numberOfAdults(false,"adults","have","having",false),
    numberOfChildren(false,"children","have","having",false),
    requiredCarParkingSpaces(false,"required car parking spaces","have","having",false),
    bookingChanges(false,"booking changes","have","having",false),
    daysInWaitingList(false,"waiting time for booking","have","having",false),
    totalOfSpecialRequests(false,"special requests","have","having",false),
    adr(false,"average daily rate","have","having",false);

    @Getter
    private final Boolean isQuantifier;
    @Getter
    private final String summarizerTitle;
    @Getter
    private final String summarizerPrefix;
    @Getter
    private final String qualifierPrefix;
    @Getter
    private final Boolean titleBeforeLabel;

}
