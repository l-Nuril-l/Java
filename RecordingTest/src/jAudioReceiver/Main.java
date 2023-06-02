package jAudioReceiver;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Main {

    public static void main(String[] args) {
        String host = "localhost";

        if (args.length>0) {
            host = args[0];
        }

        AudioFormat format = new AudioFormat(48000.0f, 16, 2, true, false);
        SourceDataLine speakers;

        try {
            InetAddress ipAddr = InetAddress.getByName(host);

            Socket s = new Socket(ipAddr, 7373);
            InputStream is = s.getInputStream();

            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();

            int numBytesRead;

            byte[] data = new byte[81920];
            while (true) {
                numBytesRead = is.read(data,0,81920);
                speakers.write(data, 0, numBytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}