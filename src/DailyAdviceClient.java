import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DailyAdviceClient {
    public static void main(String[] args) {
        DailyAdviceClient dac = new DailyAdviceClient();
        dac.go();
    }
    public void go() {
        try {
            Socket socket = new Socket("127.0.0.1", 4242);
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String advice = br.readLine();
            System.out.println("Совет дня: " + advice);
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
