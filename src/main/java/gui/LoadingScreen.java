package gui;

import fuzzy.LinguisticVariableRepository;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import model.HotelBookingRepository;
import model.NumericVariable;

import java.io.IOException;

public class LoadingScreen {

    @FXML
    private ProgressIndicator loginProgress;
    @FXML
    private Text introText;
    @FXML
    private Button loadAllDataButton;


    @FXML
    public void loadAllData() {
        loadAllDataButton.setVisible(false);
        introText.setVisible(true);


        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
                HotelBookingRepository HBR = HotelBookingRepository.getInstance();
                int linguisticVariableAllCount = NumericVariable.values().length;
                int hotelTupleAllCount = HotelBookingRepository.MAX_INDEX_DATABASE-HotelBookingRepository.MIN_INDEX_DATABASE+1;
                int linguisticVariableCurrentCount = 0;
                int hotelTupleCurrentCount = 0;
                updateMessage(introText.getText());
                for (NumericVariable variable : NumericVariable.values()) {
                    if(variable!=NumericVariable.undefined) {
                        LBR.loadLinguisticVariable(variable.toString());
                        updateProgress(hotelTupleCurrentCount+linguisticVariableCurrentCount,
                                hotelTupleAllCount+linguisticVariableAllCount);
                    }
                    linguisticVariableCurrentCount++;
                }
                updateMessage("Wczytywanie danych z bazy danych");
                for(int i=HotelBookingRepository.MIN_INDEX_DATABASE;i<HotelBookingRepository.MAX_INDEX_DATABASE;i++) {
                    HBR.getBooking(i);
                    hotelTupleCurrentCount++;
                    updateProgress(hotelTupleCurrentCount+linguisticVariableCurrentCount,
                            hotelTupleAllCount+linguisticVariableAllCount);
                }
                return null;
            }
        };
        loginProgress.progressProperty().bind(task.progressProperty());
        introText.textProperty().bind(task.messageProperty());
        task.setOnSucceeded(workerStateEvent -> {
            try {
                MainApp.setRoot("mainView");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        new Thread(task).start();


    }

}
