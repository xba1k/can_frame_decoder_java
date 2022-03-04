import java.util.Date;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FrameDecoder {

    public final static int DEFAULT_UDP_PORT = 1080;

    public final static int SI_CHARGE_PARAMS_FRAME = 0x351;
    public final static int SI_VOLTAGE_FRAME = 0x356;
    public final static int SI_SOC_FRAME = 0x355;
    public final static int SI_ID_FRAME = 0x35F;
    public final static int SI_NAME_FRAME = 0x35E;
    public final static int SI_FAULT_FRAME = 0x35A;

    DatagramSocket socket;
    DatagramPacket datagram;
    byte recvBuffer[] = new byte[64];

    public FrameDecoder() throws IOException {
        socket = new DatagramSocket(DEFAULT_UDP_PORT);
        datagram = new DatagramPacket(recvBuffer, 0, recvBuffer.length);
    }

    public void receive() throws IOException {
        socket.receive(datagram);
        System.out.print(new Date() + " " + datagram.getAddress().getHostAddress() + " ");
    }

    public void print_frame() {
        ByteBuffer buffer = ByteBuffer.wrap(recvBuffer).order(ByteOrder.LITTLE_ENDIAN);

        int frameId = buffer.getInt();

        switch (frameId) {

            case SI_CHARGE_PARAMS_FRAME: {
                System.out.print(String.format("Charge params: finalVoltage %.2f, maxChargeCurrent %.2f, maxDischargeCurrent %.2f, finalDischargeVoltage %.2f",
                        buffer.getShort() / 10.0, buffer.getShort() / 10.0, buffer.getShort() / 10.0, buffer.getShort() / 10.0));
                break;
            }
            case SI_VOLTAGE_FRAME: {
                System.out.print(String.format("Voltage params: batteryVoltage %.2f, batteryCurrent %.2f, batteryTemp %d",
                        buffer.getShort() / 10.0, buffer.getShort() / 10.0, buffer.getShort() / 10.0, buffer.getShort() / 10.0));
                break;
            }
            case SI_SOC_FRAME: {
                System.out.print(String.format("States: stateOfCharge %d, stateOfHealth %d, stateOfChargeHighPrecision %d",
                        buffer.getShort(), buffer.getShort(), buffer.getShort(), buffer.getShort()));
                break;
            }
            case SI_ID_FRAME: {
                System.out.print(String.format("ID: chemistry %c%c, hw_ver %d, capacity %.2f, sw_ver %d",
                        buffer.get(), buffer.get(), buffer.getShort(), buffer.getShort() / 10.0, buffer.getShort()));
                break;
            }
            case SI_NAME_FRAME: {
                byte[] name = new byte[8];
                buffer.get(name);
                System.out.print(String.format("BMS Name: %s", new String(name)));
                break;
            }
            case SI_FAULT_FRAME: {
                System.out.print(String.format("Faults: f0 %d, f1 %d, f2 %d, f3 %d",
                        buffer.get(), buffer.get(), buffer.get(), buffer.get()));
                break;
            }
            default: {
                System.out.print(String.format("unknown frame type %x", frameId));
            }

        }

        System.out.println();
    }

    public static void main(String[] args) {

        try {

            FrameDecoder decoder = new FrameDecoder();

            System.out.println("Waiting for packets...");

            while (true) {
                decoder.receive();
                decoder.print_frame();
            }

        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }

}
