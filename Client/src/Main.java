import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    static Socket socket;
    static ObjectInputStream objectInputStream;
    static ObjectOutputStream objectOutputStream;
    public static String name;
    public static String username;
    public static String dateOfBirth;
    public static String phoneNumber;
    public static String email;
    public static String bio;
    public static String joinedTime;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 12345);
            new Main(socket);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Start the server before opening Twitter.",
                    "Connection failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    Main(Socket socket) {
        try {

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            SwingUtilities.invokeLater(MenuFrame::new);



        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
