import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Properties;

final class CredentialStore {
    private static final Path FILE = Path.of("SAVES", "credentials.properties");
    private static final int ITERATIONS = 210_000;
    private static final SecureRandom RANDOM = new SecureRandom();

    private CredentialStore() {}

    static synchronized boolean register(String username, String password) throws IOException {
        Properties credentials = load();
        if (credentials.containsKey(username)) return false;
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        byte[] hash = hash(password, salt, ITERATIONS);
        credentials.setProperty(username, ITERATIONS + ":" +
                Base64.getEncoder().encodeToString(salt) + ":" +
                Base64.getEncoder().encodeToString(hash));
        Files.createDirectories(FILE.getParent());
        try (OutputStream out = Files.newOutputStream(FILE)) {
            credentials.store(out, "Local coursework accounts; do not commit this file");
        }
        return true;
    }

    static synchronized boolean verify(String username, String password) throws IOException {
        String value = load().getProperty(username);
        if (value == null) return false;
        String[] parts = value.split(":", 3);
        if (parts.length != 3) return false;
        try {
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] expected = Base64.getDecoder().decode(parts[2]);
            return MessageDigest.isEqual(expected, hash(password, salt, iterations));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static Properties load() throws IOException {
        Properties credentials = new Properties();
        if (Files.exists(FILE)) {
            try (InputStream in = Files.newInputStream(FILE)) {
                credentials.load(in);
            }
        }
        return credentials;
    }

    private static byte[] hash(String password, byte[] salt, int iterations) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, 256);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Password hashing is unavailable", e);
        } finally {
            spec.clearPassword();
        }
    }
}
