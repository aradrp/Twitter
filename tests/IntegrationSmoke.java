import java.io.*;
import java.net.Socket;

public class IntegrationSmoke {
    private record Connection(Socket socket, ObjectOutputStream out, ObjectInputStream in)
            implements AutoCloseable {
        static Connection open() throws IOException {
            Socket socket = new Socket("127.0.0.1", 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return new Connection(socket, out, in);
        }
        @Override public void close() throws IOException { socket.close(); }
    }

    private static void signUp(Connection c, String user) throws IOException {
        c.out.writeUTF("signUp");
        c.out.writeUTF("Test " + user);
        c.out.writeUTF(user);
        c.out.writeUTF(user + "@example.test");
        c.out.writeUTF("09123456789");
        c.out.writeUTF("01/01/2000");
        c.out.writeUTF("Testpass123");
        c.out.writeUTF("Demo account");
        c.out.flush();
        for (int i = 0; i < 5; i++) {
            if (!c.in.readBoolean()) throw new AssertionError("Registration flag " + i + " failed");
        }
    }

    private static void logIn(Connection c, String user) throws IOException {
        c.out.writeUTF("logIn");
        c.out.writeUTF(user + "@example.test");
        c.out.writeUTF("Testpass123");
        c.out.flush();
        if (!c.in.readBoolean() || !c.in.readBoolean() || !c.in.readBoolean()) {
            throw new AssertionError("Login failed for " + user);
        }
    }

    public static void main(String[] args) throws Exception {
        String suffix = Long.toString(System.currentTimeMillis());
        String alice = "alice" + suffix;
        String bob = "bob" + suffix;
        try (Connection a = Connection.open(); Connection b = Connection.open()) {
            signUp(a, alice);
            signUp(b, bob);
            logIn(a, alice);
            logIn(b, bob);
            a.out.writeUTF("newTweet");
            a.out.writeUTF("integration smoke post");
            a.out.writeBoolean(true);
            a.out.writeBoolean(false);
            a.out.flush();
            if (!a.in.readBoolean()) throw new AssertionError("Post was rejected");

            b.out.writeUTF("forYouTL");
            b.out.flush();
            Client profile = (Client) b.in.readObject();
            if (!bob.equals(profile.getUsername())) throw new AssertionError("Wrong profile");
            if (!b.in.readBoolean()) throw new AssertionError("No authors");
            if (!b.in.readBoolean()) throw new AssertionError("No posts");
            TweetsAndreplies post = null;
            boolean last;
            do {
                TweetsAndreplies candidate = (TweetsAndreplies) b.in.readObject();
                if (alice.equals(candidate.getClient().getUsername()) &&
                        "integration smoke post".equals(candidate.getTweet())) post = candidate;
                last = b.in.readBoolean();
            } while (!last);
            if (post == null) throw new AssertionError("Public post missing");

            updateList(b, "save", post);
            updateList(b, "like", post);
            b.out.writeUTF("profile");
            b.out.flush();
            Client updated = (Client) b.in.readObject();
            if (updated.getSavedArraylist().size() != 1 || updated.getLikedArraylist().size() != 1) {
                throw new AssertionError("Save/like did not persist");
            }
            if (b.in.readBoolean() || b.in.readBoolean() || b.in.readBoolean()) {
                throw new AssertionError("Unexpected posts or follows on new profile");
            }
            a.out.writeUTF("newTweet");
            a.out.writeUTF("private smoke post");
            a.out.writeBoolean(true);
            a.out.writeBoolean(true);
            a.out.flush();
            if (!a.in.readBoolean()) throw new AssertionError("Private post rejected");
            b.out.writeUTF("forYouTL");
            b.out.flush();
            b.in.readObject();
            if (!b.in.readBoolean() || !b.in.readBoolean()) throw new AssertionError("Public post missing");
            boolean foundPublic = false;
            do {
                TweetsAndreplies visible = (TweetsAndreplies) b.in.readObject();
                if (alice.equals(visible.getClient().getUsername())) {
                    if ("private smoke post".equals(visible.getTweet())) {
                        throw new AssertionError("Private post leaked into public feed");
                    }
                    if ("integration smoke post".equals(visible.getTweet())) foundPublic = true;
                }
                last = b.in.readBoolean();
            } while (!last);
            if (!foundPublic) throw new AssertionError("Public post missing after private post");
        }
        System.out.println("SIGNUP_LOGIN_POST_FEED_SAVE_LIKE_PRIVACY_OK");
    }

    private static void updateList(Connection c, String kind, TweetsAndreplies post) throws IOException {
        c.out.writeUTF("UpdateClient");
        c.out.writeUTF(kind);
        c.out.writeInt(1);
        c.out.writeBoolean(true);
        c.out.writeObject(post);
        c.out.writeBoolean(false);
        c.out.flush();
    }
}
