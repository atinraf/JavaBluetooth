/*
 * @author Thomas de Lazzari
 * http://tdelazzari.blogspot.com
 *
 */
package bluetoothserver;

public class Main {

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
