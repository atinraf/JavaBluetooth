/*
 * @author Thomas de Lazzari
 * with the help of EMSI Casablanca
 * http://tdelazzari.blogspot.com
 *
 */
package bluetoothserver;

import java.io.DataInputStream;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class Server extends Thread {

    private String serviceURL;
    private static UUID SERVICE_UUID = new UUID("1234", true);
    private LocalDevice localDevice;
    private StreamConnectionNotifier streamNotifier;
    private StreamConnection streamConnection;
    private boolean serverRunning = false;

    public Server() {
        this.init();
    }

    private void init() {
        System.out.println("Starting server...");
        this.serviceURL = "btspp://localhost:" + SERVICE_UUID + ";name=" + "btserver" + ";authorize=true";
        try {
            // Init Bluetooth device
            localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            // Creating a Bluetooth service         
            System.out.println("Service started on address: " + localDevice.getBluetoothAddress());
            // Server is ready
            serverRunning = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server is running");
    }

    @Override
    public void run() {
        while (serverRunning) {
            try {
                streamNotifier = (StreamConnectionNotifier) Connector.open(serviceURL);
                System.out.println("Waiting for client connection...");
                streamConnection = streamNotifier.acceptAndOpen();
                System.out.println("Bluetooth client is connected");
                DataInputStream dataIn = streamConnection.openDataInputStream();
                String msg = dataIn.readUTF();
                System.out.println("Client sent: " + msg);
                streamConnection.close();
                streamNotifier.close();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public boolean isServerRunning() {
        return serverRunning;
    }
}
