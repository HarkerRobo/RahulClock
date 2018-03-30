package rahulclock;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector extends Thread {

    private Server serv;
    private Runnable onConnect;
    private Runnable onError;
    private static Logger logger = Logger.getLogger(Connector.class.getName());

    public Connector(Runnable onConnect, Runnable onError, Server serv) {
        setDaemon(true);
        this.onConnect = onConnect;
        this.onError = onError;
        this.serv = serv;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("10.10.72.2", 5808), 3000);
                logger.info("Connected to server");
                onConnect.run();
                logger.info("Changed screen color");
                serv.addSocket(sock, () -> {
                    onError.run();
                    run();
                });
                break;
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error connecting to robot", e);
                try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
            }
        }
    }

}
