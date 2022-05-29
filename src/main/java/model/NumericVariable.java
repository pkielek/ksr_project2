package model;

import lombok.Getter;

public enum NumericVariable {
    undefined(null),
    relativeQuantifier(true),
    absoluteQuantifier(true),
    leadTime(false),
    arrivalDateWeekNumber(false),
    arrivalDateDayOfMonth(false),
    staysInWeekendNights(false),
    staysInWeekNights(false),
    numberOfAdults(false),
    numberOfChildren(false),
    requiredCarParkingSpaces(false),
    bookingChanges(false),
    daysInWaitingList(false),
    totalOfSpecialRequests(false),
    adr(false);

    @Getter
    private final Boolean isQuantifier;
    @Getter
    private final String summarizerText;
    @Getter
    private final String qualifierText;

    NumericVariable(Boolean isQuantifier) {
        this.isQuantifier = isQuantifier;
        this.summarizerText = name();
        this.qualifierText = name();
    }
}
