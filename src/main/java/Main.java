import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by dtrouillet on 11/08/2017.
 */
public class Main {
    public static void main(String... arg) throws IOException {
        for(SerialPort serialPort : SerialPort.getCommPorts()) {
            System.out.println("CommPorts = "+serialPort.getSystemPortName());
        }
        final SerialPort comPort = SerialPort.getCommPorts()[2];
        comPort.openPort();
        comPort.setBaudRate(57600);
        InputStream inputStream = comPort.getInputStream();
        OutputStream outputStream = comPort.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while (true){
            if(inputStream.available() > 0){
                String line = bufferedReader.readLine();
                if(line.contains("NewKaku;ID=00c5a71e;SWITCH=2;CMD=ON")){
                    System.out.println("Envoyer 1 ON");
                    bufferedWriter.write("10;NewKaku;00c5a71e;1;ON;\n");
                    bufferedWriter.flush();
                }else  if(line.contains("NewKaku;ID=00c5a71e;SWITCH=2;CMD=OFF")){
                    System.out.println("Envoyer 1 OFF");
                   // bufferedWriter.write("10;NewKaku;00c5a71e;1;OFF;\n");
                    //RTS;ID=f3d5fd;SWITCH=01;CMD=STOP;
                    bufferedWriter.write("10;RTS;f3d5fd;1;STOP;\n");
                    bufferedWriter.flush();
                }
            }
        }

    }
}
