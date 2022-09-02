package sample;

import java.util.TimerTask;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class TimerScheduleHandler extends TimerTask implements SerialPortDataListener {

    private final long timeStart;

    public TimerScheduleHandler(long timeStart) {
        this.timeStart = timeStart;
    }

    @Override
    public void run () {
        //System.out.println("Time elapsed: " + (System.currentTimeMillis() - this.timeStart) + " milliseconds");
    }

    @Override
    public int getListeningEvents(){
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        String messages = "";
        messages += new String(serialPortEvent.getReceivedData());
        while (messages.contains("\n")) {
            String[] message = messages.split("\n", 2);
            messages = (message.length > 1) ? message[1] : "";
            //System.out.println("Message: " + message[0]);
            System.out.println(messages);
    }
       /* if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
            System.out.println("Works");
        }*/

    }
}
