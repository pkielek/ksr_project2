package model;

import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelBooking {
    Hotel hotel;
    ReservationStatus status;
    CustomerType customerType;
    CountryCode countryCode;
    Integer leadTime;
    Integer arrivalDateWeekNumber;
    Integer arrivalDateDayOfMonth;
    Integer staysInWeekendNights;
    Integer staysInWeekNights;
    Integer adults;
    Integer children;
    Integer bookingChanges;
    Integer daysInWaitingList;
    Integer requiredCarParkingSpaces;
    Integer totalOfSpecialRequests;
    Double adr;

    public String getStringVariable(StringVariable variable) {
        switch(variable) {
            case hotel -> {
                return this.hotel.toString();
            }
            case status -> {
                return this.status.toString();
            }
            case customerType -> {
                return this.customerType.toString();
            }
            case countryCode -> {
                return this.countryCode.getName();
            }
        }
        throw new IllegalArgumentException("No string variable found");
    }
    public Double getNumericVariable(NumericVariable variable) {
        switch(variable) {
            case leadTime -> {
                return Double.valueOf(leadTime);
            }
//            case arrivalDateWeekNumber -> {
//                return Double.valueOf(arrivalDateWeekNumber);
//            }
            case arrivalDateDayOfMonth -> {
                return Double.valueOf(arrivalDateDayOfMonth);
            }
            case staysInWeekendNights -> {
                return Double.valueOf(staysInWeekendNights);
            }
            case staysInWeekNights -> {
                return Double.valueOf(staysInWeekNights);
            }
            case numberOfAdults -> {
                return Double.valueOf(adults);
            }
            case numberOfChildren -> {
                return Double.valueOf(children);
            }
            case requiredCarParkingSpaces -> {
                return Double.valueOf(requiredCarParkingSpaces);
            }
            case bookingChanges -> {
                return Double.valueOf(bookingChanges);
            }
            case daysInWaitingList -> {
                return Double.valueOf(daysInWaitingList);
            }
            case totalOfSpecialRequests -> {
                return Double.valueOf(totalOfSpecialRequests);
            }
            case adr -> {
                return adr;
            }
        }
        throw new IllegalArgumentException("No numeric variable found");

    }

}
