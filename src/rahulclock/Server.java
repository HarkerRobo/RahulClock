package rahulclock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The server that handles receiving timer times and sending timestamps
 */
public class Server extends Thread {

    public static final int PORT = 5801;

    private static Consumer<String> timeAccepter;
    private static Logger logger = Logger.getLogger(Server.class.getName());
    private Timer timer;

    public Server(Consumer<String> acc) {
        timeAccepter = acc;
        setDaemon(true);
    }

    @Override
    public void run() {
        try (ServerSocket sock = new ServerSocket(5801)) {
            while (true) {
	            new AcceptThread(sock.accept()).start();
	        }
        } catch (IOException e) {
            logger.severe("Could not start server");
            // System.exit(-1);
        }
    }

    public void addSocket(Socket sock) {
        new AcceptThread(sock).start();
    }


    public class AcceptThread extends Thread {

        private Socket sock;
        private OutputStreamWriter out;

        public AcceptThread(Socket sock) {
            this.sock = sock;
            setDaemon(true);
            try {
                out = new OutputStreamWriter(sock.getOutputStream());
            } catch (IOException e) {
                logger.log(Level.WARNING, "Could not open output stream", e);
            }
        }

        private void sendTime() {
            logger.info("sending time");
            try {
                out.write(new Date().getTime() + "\n");
                out.flush();
			} catch (IOException e) {
				logger.log(Level.WARNING, "Could not send time", e);
			}
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(
                new InputStreamReader(sock.getInputStream())
            )) {
                String line = null;
                while ((line = in.readLine()) != null) {
                    logger.info("Got line " + line);
                    try {
                        int minutes = Integer.parseInt(line);
                        if (timer != null) timer.end();
                        timer = new Timer(minutes, timeAccepter);
                        timer.start();
                    } catch (NumberFormatException e) {
                        sendTime();
                    }
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "error reading line", e);
            }
        }

    }

}
