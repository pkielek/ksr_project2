package model;

import com.neovisionaries.i18n.CountryCode;

import java.sql.*;
import java.util.Locale;
import java.util.TreeMap;

import static model.DatabaseInitialize.DATABASE_URL;

public class HotelBookingRepository {
    private static HotelBookingRepository instance;
    private TreeMap<Integer,HotelBooking> loadedData;

    private final String GET_BOOKING = "SELECT * FROM hotel_bookings WHERE id=?";

    private Hotel processHotel(String hotel) {
        return Hotel.valueOf(hotel.toUpperCase(Locale.ROOT).replace(' ','_'));
    }

    private ReservationStatus processReservationStatus(String status) {
        return ReservationStatus.valueOf(status.toUpperCase().replace('-','_'));
    }

    private CustomerType processCustomerType(String customerType) {
        return CustomerType.valueOf(customerType.toUpperCase().replace('-','_'));
    }

    public HotelBooking getBooking(Integer index) {
        if(!loadedData.containsKey(index)) {
            try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
                PreparedStatement s2 = conn.prepareStatement(GET_BOOKING);
                s2.setInt(1,index);
                ResultSet result = s2.executeQuery();
                loadedData.put(index,new HotelBooking(
                        processHotel(result.getString("hotel")),
                        processReservationStatus(result.getString("reservation_status")),
                        processCustomerType(result.getString("customer_type")),
                        CountryCode.findByName(result.getString("country")).get(0),
                        result.getInt("lead_time"),
                        result.getInt("arrival_date_week_number"),
                        result.getInt("arrival_date_day_of_month"),
                        result.getInt("stays_in_weekend_nights"),
                        result.getInt("stays_in_week_nights"),
                        result.getInt("adults"),
                        result.getInt("children"),
                        result.getInt("booking_changes"),
                        result.getInt("days_in_waiting_list"),
                        result.getInt("required_car_parking_spaces"),
                        result.getInt("total_of_special_requests"),
                        result.getDouble("adr")
                        ));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return loadedData.get(index);
    }

    private HotelBookingRepository() {
        loadedData = new TreeMap<>();
    }
    public static HotelBookingRepository getInstance() {
        if (instance == null) {
            instance = new HotelBookingRepository();
        }
        return instance;
    }
}
