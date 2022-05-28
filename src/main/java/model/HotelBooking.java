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
}
