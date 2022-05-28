import model.DatabaseInitialize;
import model.HotelBookingRepository;

public class MainApp {

    public static void main(String[] args) {
        DatabaseInitialize.databaseInitialize();
    }
}
