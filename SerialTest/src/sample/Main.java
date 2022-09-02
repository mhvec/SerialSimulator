package sample;

import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.fazecast.jSerialComm.SerialPort;
import java.util.Timer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();



        var sp = SerialPort.getCommPort("COM6");
        sp.setComPortParameters(115200, Byte.SIZE, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        var hasOpened = sp.openPort();
        if(!hasOpened){
            throw new IllegalStateException("Failed to open serial port");
        }


        sp.addDataListener(new SerialPortDataListener() {
            private String messages = "";

            @Override
            public int getListeningEvents() {
                return sp.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                    messages += new String(serialPortEvent.getReceivedData());
                    while (messages.contains("\n")) {
                        String[] message = messages.replaceAll("ECT: |    Lambda:|    MAP:|    RPM:", "").split("\\n", 2);
                        messages = (message.length > 1) ? message[1] : "";
                        System.out.println( message[0]);
                }
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
