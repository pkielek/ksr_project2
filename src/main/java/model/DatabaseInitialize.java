package model;

import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.LanguageAlpha3Code;
import com.neovisionaries.i18n.LanguageCode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public class DatabaseInitialize {

    private static final String INSERT_BOOKING = "INSERT INTO HOTEL_BOOKINGS (id,hotel,lead_time,arrival_date_week_number," +
            "arrival_date_day_of_month,stays_in_weekend_nights,stays_in_week_nights,adults,children,country,booking_changes," +
            "days_in_waiting_list,customer_type,adr,required_car_parking_spaces,total_of_special_requests,reservation_status)" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String DATABASE_URL = "jdbc:sqlite:hotel_bookings";

    public static void databaseInitialize() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            conn.setAutoCommit(false);
            Statement s = conn.createStatement();
            s.execute("drop table if exists hotel_bookings");
            conn.commit();
            s = conn.createStatement();
            s.execute("create table hotel_bookings" +
                    "(id int primary key," +
                    "hotel varchar(40)," +
                    "lead_time int," +
                    "arrival_date_week_number int," +
                    "arrival_date_day_of_month int," +
                    "stays_in_weekend_nights int," +
                    "stays_in_week_nights int," +
                    "adults int," +
                    "children int," +
                    "country varchar(80)," +
                    "booking_changes int," +
                    "days_in_waiting_list int," +
                    "customer_type varchar(30)," +
                    "adr real," +
                    "required_car_parking_spaces int," +
                    "total_of_special_requests int," +
                    "reservation_status varchar(20))");
            conn.commit();
            try (BufferedReader br = new BufferedReader(new FileReader("hotel_bookings_v3.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {

                    PreparedStatement s2 = conn.prepareStatement(INSERT_BOOKING);
                    String[] values = line.split(",");
                    if(!values[0].equals("")) {
                        for(int i=0;i<17;i++) {
                            s2.setString(i+1,i!=9?values[i]: CountryCode.getByCode(values[i]).getName());
                        }
                        s2.executeUpdate();
                    }
                }
                conn.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
