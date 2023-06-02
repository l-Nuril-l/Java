import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.InputStream;

public class CallReceiver extends Thread {

    InputStream in;
    CallReceiver(InputStream in){
        this.in = in;
    }
    public void run(){
        AudioFormat format = new AudioFormat(48000.0f, 16, 2, true, false);
        SourceDataLine speakers;
        try {
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();

            int numBytesRead;

            byte[] data = new byte[204800];

            while (true) {
                numBytesRead = in.read(data,0,102400);
                speakers.write(data, 0, numBytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
