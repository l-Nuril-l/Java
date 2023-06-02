package jAudioTransmitter;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Main {
    static List<Sender> senderList = new ArrayList<>();
    static MicrophoneReader mr;
    static volatile Integer sendersCreated = 0;
    static volatile Integer numBytesRead;
    static volatile Integer senderNotReady = 0;
    static volatile byte[] data;
    static final Object monitor = new Object();
    public static void main(String[] args) {
        try {
            mr = new MicrophoneReader();
            mr.start();

            ServerSocket ss = new ServerSocket(7373);

            while (true) {
                Socket s = ss.accept();

                Sender sndr = new Sender(s);
                senderList.add(sndr);
                sndr.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mr.setFinishFlag();
            for (Sender sndr: senderList) {
                sndr.setFinishFlag();
            }
        }
    }

    static class Sender extends Thread {
        Socket s;
        volatile boolean finishFlag;
        int position;
        int senderNumber;

        public Sender(Socket s) {
            this.s = s;
            finishFlag = false;
            System.out.print("Sender started: #");
            senderNumber = ++sendersCreated;
            System.out.println(senderNumber);
        }

        public void setFinishFlag() {
            finishFlag = true;
        }

        public void run() {
            try {
                OutputStream os = s.getOutputStream();

                while (!finishFlag) {

                    synchronized (monitor) {
                        senderNotReady++;
                        monitor.wait();
                        os.write(data, 0, numBytesRead);
                        os.flush();
                        senderNotReady--;
                    }
                    Thread.sleep(1);
                    System.out.print("Sender #");
                    System.out.print(senderNumber);
                    System.out.print(" ");
                    System.out.print(numBytesRead);
                    System.out.println(" bytes sent");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class MicrophoneReader extends Thread {
        volatile boolean finishFlag;

        AudioFormat format = new AudioFormat(48000.0f, 16, 2, true, false);
        int CHUNK_SIZE = 81920;
        TargetDataLine microphone;

        public MicrophoneReader() {
            finishFlag = false;
            System.out.println("Microphone reader started");
        }

        public void setFinishFlag() {
            finishFlag = true;
        }

        public void run() {
            try {
                microphone = AudioSystem.getTargetDataLine(format);

                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(format);


                data = new byte[CHUNK_SIZE];
                microphone.start();

                while (!finishFlag) {
                    synchronized (monitor) {
                        if (senderNotReady.equals(sendersCreated)) {
                            monitor.notifyAll();
                            continue;
                        }
                        numBytesRead = microphone.read(data, 0, CHUNK_SIZE);

                    }
                    Thread.sleep(1);
                    System.out.print("Microphone reader: ");
                    System.out.print(numBytesRead);
                    System.out.println(" bytes read");

                }
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}