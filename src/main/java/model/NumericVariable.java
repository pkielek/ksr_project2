package model;

import lombok.Getter;

public enum NumericVariable {
    undefined(null,"","",false),
    relativeQuantifier(true,"","",false),
    absoluteQuantifier(true,"","",false),
    leadTime(false,"lead time","have",false),
    arrivalDateWeekNumber(false,"made","are",true),
    arrivalDateDayOfMonth(false,"made","are",true),
    staysInWeekendNights(false,"stays in weekend nights","have",false),
    staysInWeekNights(false,"stays in week nights","have",false),
    numberOfAdults(false,"adults","have",false),
    numberOfChildren(false,"children","have",false),
    requiredCarParkingSpaces(false,"required car parking spaces","have",false),
    bookingChanges(false,"booking changes","have",false),
    daysInWaitingList(false,"waiting time for booking","have",false),
    totalOfSpecialRequests(false,"special requests","have",false),
    adr(false,"average daily rate","have",false);

    @Getter
    private final Boolean isQuantifier;
    @Getter
    private final String summarizerTitle;
    @Getter
    private final String summarizerPrefix;
    @Getter
    private final Boolean titleBeforeLabel;

    NumericVariable(Boolean isQuantifier,String summarizerTitle, String summarizerPrefix, Boolean titleBeforeLabel) {
        this.isQuantifier = isQuantifier;
        this.summarizerTitle = summarizerTitle;
        this.summarizerPrefix = summarizerPrefix;
        this.titleBeforeLabel = titleBeforeLabel;
    }
}
