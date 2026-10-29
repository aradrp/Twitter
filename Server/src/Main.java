import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import java.io.FileWriter;
import java.io.IOException;


public class Main {
    Client clientInApp = null;
//    private static ArrayList<TweetsAndreplies> allTweets = Client.getAllTweets();


    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345, 50,
                java.net.InetAddress.getLoopbackAddress())) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(() -> new Main(clientSocket), "twitter-client");
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Main(Socket clientSocket) {

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream()); ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            while (clientSocket.isConnected()) {


                String ClientRequest;
                Switch:
                switch (ClientRequest = objectInputStream.readUTF()) {
                    case "logIn":

                        String loginEmail = objectInputStream.readUTF();
                        String loginPassword = objectInputStream.readUTF();
                        boolean passwordLoginStatus = false;
                        boolean emailLoginStatus = false;
                        Load(null, "accounts", null, null);
                        if (Client.accounts.isEmpty()) {
                            objectOutputStream.writeBoolean(false);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeBoolean(true);
                            objectOutputStream.flush();
                            for (Client client : Client.accounts) {
                                if (client.getEmail().equals(loginEmail)) {
                                    emailLoginStatus = true;
                                    if (CredentialStore.verify(client.getUsername(), loginPassword)) {
                                        passwordLoginStatus = true;
                                        clientInApp = client;
                                        break;
                                    }
                                }
                            }

                            objectOutputStream.writeBoolean(passwordLoginStatus);
                            objectOutputStream.flush();
                            objectOutputStream.writeBoolean(emailLoginStatus);
                            objectOutputStream.flush();
                        }
                        break;

                    case "signUp":

                        String name = objectInputStream.readUTF();
                        String username = objectInputStream.readUTF();
                        String emailSignUp = objectInputStream.readUTF();
                        String phoneNumber = objectInputStream.readUTF();
                        String birthDate = objectInputStream.readUTF();
                        String password = objectInputStream.readUTF();
                        String bio = objectInputStream.readUTF();

                        Load(null, "accounts", null, null);


                        boolean usernameStatus = username.matches("[A-Za-z0-9_]{3,30}");
                        boolean emailStatus = emailSignUp.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
                        for (Client client : Client.accounts) {
                            if (client.getEmail().equalsIgnoreCase(emailSignUp)) {
                                emailStatus = false;
                            }
                            if (client.getUsername().equalsIgnoreCase(username)) {
                                usernameStatus = false;
                            }
                        }

                        boolean phoneNumberStatus = phoneNumber.matches("\\d{11}");
                        boolean passwordStatus = password.length() >= 8 &&
                                Pattern.compile("[a-zA-Z]").matcher(password).find() &&
                                Pattern.compile("\\d").matcher(password).find();
                        boolean birthdateStatus = birthDate.matches("\\d{2}/\\d{2}/\\d{4}");

                        if (emailStatus && usernameStatus && phoneNumberStatus && passwordStatus && birthdateStatus) {
                            usernameStatus = CredentialStore.register(username, password);
                            if (usernameStatus) {
                                LocalDate myDateObj = LocalDate.now();
                                DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                String formattedDate = myDateObj.format(myFormatObj1);
                                Client client = new Client(name, username, formattedDate, birthDate,
                                        phoneNumber, emailSignUp, 0, 0, 0, 0, bio, false);
                                Save(client, "accounts", null);
                            }
                        }

                        objectOutputStream.writeBoolean(emailStatus);
                        objectOutputStream.flush();
                        objectOutputStream.writeBoolean(usernameStatus);
                        objectOutputStream.flush();
                        objectOutputStream.writeBoolean(phoneNumberStatus);
                        objectOutputStream.flush();
                        objectOutputStream.writeBoolean(passwordStatus);
                        objectOutputStream.flush();
                        objectOutputStream.writeBoolean(birthdateStatus);
                        objectOutputStream.flush();


                        break;

                    case "newTweet":
                        try {

                            String tweet = objectInputStream.readUTF();
                            boolean saveable = objectInputStream.readBoolean();
                            boolean privateOrPublic = objectInputStream.readBoolean();
                            if (clientInApp == null || tweet.isBlank() || tweet.length() > 280) {
                                objectOutputStream.writeBoolean(false);
                                objectOutputStream.flush();
                                break;
                            }
                            LocalDateTime myDateObj = LocalDateTime.now();
                            DateTimeFormatter myFormatObj2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                            String formattedDate2 = myDateObj.format(myFormatObj2);
                            Load(clientInApp, "tweet", null, null);
                            ArrayList<TweetsAndreplies> tweetsAndreplies = clientInApp.getTweetArraylist();
                            TweetsAndreplies newPost = new TweetsAndreplies(true, clientInApp, tweet, null,
                                    formattedDate2, privateOrPublic, saveable);
                            tweetsAndreplies.add(newPost);
                            clientInApp.setTweetArraylist(tweetsAndreplies);
                            ArrayList<TweetsAndreplies> alltweets = Client.getAllTweets();
                            alltweets.add(newPost);
                            Client.setAllTweets(alltweets);
                            Save(clientInApp, "tweet", null);
                            objectOutputStream.writeBoolean(true);
                            objectOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "forYouTL":
                        objectOutputStream.reset();
                        objectOutputStream.writeObject(clientInApp);
                        objectOutputStream.flush();
                        Load(clientInApp, "followings", null, null);
                        ArrayList<TweetsAndreplies> recommendedPosts = new ArrayList<>();
                        boolean hasRecommendedAuthors = false;
                        for (Client author : Client.accounts) {
                            if (author.getUsername().equals(clientInApp.getUsername())) continue;
                            boolean following = isFollowing(clientInApp, author.getUsername());
                            if (author.isPrivateAccount() && !following) continue;
                            hasRecommendedAuthors = true;
                            Load(author, "tweet", null, null);
                            for (TweetsAndreplies post : author.getTweetArraylist()) {
                                if (!post.isPrivateTweet() || following) recommendedPosts.add(post);
                            }
                        }
                        writeTimeline(objectOutputStream, recommendedPosts, hasRecommendedAuthors);
                        break;

                    case "followingTL":
                        objectOutputStream.reset();
                        objectOutputStream.writeObject(clientInApp);
                        objectOutputStream.flush();
                        Load(clientInApp, "followings", null, null);
                        ArrayList<TweetsAndreplies> followedPosts = new ArrayList<>();
                        boolean hasFollowedAuthors = clientInApp.followingUsers != null &&
                                !clientInApp.followingUsers.isEmpty();
                        if (hasFollowedAuthors) {
                            for (Client author : clientInApp.followingUsers) {
                                Load(author, "tweet", null, null);
                                followedPosts.addAll(author.getTweetArraylist());
                            }
                        }
                        writeTimeline(objectOutputStream, followedPosts, hasFollowedAuthors);
                        break;
                    case "follow":
                        Load(clientInApp, "followings", null, null);
                        String usernameToFollow = objectInputStream.readUTF();

                        for (Client client : Client.accounts) {
                            if (client.getUsername() == usernameToFollow) {
                                Save(clientInApp, "follow", client);
                            }
                        }
                        break;

                    case "Unfollow":
                        Load(clientInApp, "followings", null, null);
                        String usernameToFollow2 = objectInputStream.readUTF();

                        for (Client client : Client.accounts) {
                            if (client.getUsername() == usernameToFollow2) {
                                Save(clientInApp, "Unfollow", client);
                            }
                        }
                        break;

                    case "profile":
                        Load(clientInApp, "tweet", null, null);
                        Load(clientInApp, "followings", null, null);
                        Load(clientInApp, "tweetsavedandliked", null, null);
                        Load(clientInApp, "accounts", null, null);
                        objectOutputStream.reset();
                        objectOutputStream.writeObject(clientInApp);
                        objectOutputStream.flush();

                        ArrayList<TweetsAndreplies> tweets = clientInApp.getTweetArraylist();
                        objectOutputStream.writeBoolean(!tweets.isEmpty());

                        for (int i = 0; i < clientInApp.getTweetArraylist().size(); i++) {
                            objectOutputStream.writeObject(tweets.get(i));
                            objectOutputStream.flush();
                            if (i < clientInApp.getTweetArraylist().size() - 1) {
                                objectOutputStream.writeBoolean(false);
                                objectOutputStream.flush();
                            } else if (i == clientInApp.getTweetArraylist().size() - 1) {
                                objectOutputStream.writeBoolean(true);
                                objectOutputStream.flush();
                            }
                        }
                        if (clientInApp.followingUsers == null || clientInApp.followingUsers.isEmpty()) {
                            objectOutputStream.writeBoolean(false);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeBoolean(true);
                            objectOutputStream.flush();
                            for (int i = 0; i < clientInApp.followingUsers.size(); i++) {
                                objectOutputStream.writeObject(clientInApp.followingUsers.get(i));
                                objectOutputStream.flush();
                                if (i < clientInApp.followingUsers.size() - 1) {
                                    objectOutputStream.writeBoolean(false);
                                    objectOutputStream.flush();
                                } else if (i == clientInApp.followingUsers.size() - 1) {
                                    objectOutputStream.writeBoolean(true);
                                    objectOutputStream.flush();
                                }
                            }
                        }
                        if (clientInApp.followerUsers == null || clientInApp.followerUsers.isEmpty()) {
                            objectOutputStream.writeBoolean(false);
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeBoolean(true);
                            objectOutputStream.flush();
                            for (int i = 0; i < clientInApp.followerUsers.size(); i++) {
                                objectOutputStream.writeObject(clientInApp.followerUsers.get(i));
                                objectOutputStream.flush();
                                if (i < clientInApp.followerUsers.size() - 1) {
                                    objectOutputStream.writeBoolean(false);
                                    objectOutputStream.flush();
                                } else if (i == clientInApp.followerUsers.size() - 1) {
                                    objectOutputStream.writeBoolean(true);
                                    objectOutputStream.flush();
                                }
                            }
                        }
                        break;

                    case "UpdateClient":


                        if (objectInputStream.readUTF().equals("save")) {
                            //fix
                            clientInApp.setSaveCounts(objectInputStream.readInt());
                            ArrayList<TweetsAndreplies> savedList = new ArrayList<>();
                            while (objectInputStream.readBoolean()) {
                                savedList.add((TweetsAndreplies) objectInputStream.readObject());
                            }
                            clientInApp.setSavedArraylist(savedList);
                        } else {
                            //fix
                            clientInApp.setLikeCounts(objectInputStream.readInt());
                            ArrayList<TweetsAndreplies> likedList = new ArrayList<>();
                            while (objectInputStream.readBoolean()) {
                                likedList.add((TweetsAndreplies) objectInputStream.readObject());
                            }
                            clientInApp.setLikedArraylist(likedList);
                        }
                        Save(clientInApp, "accounts", null);
                        Save(clientInApp, "tweetsavedandliked", null);

                        break;

                    default:
                        System.out.println(ClientRequest);
                        break;
                }
            }


        } catch (EOFException ignored) {
            // A client closing its connection is normal.
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean isFollowing(Client viewer, String username) {
        if (viewer.followingUsers == null) return false;
        for (Client followed : viewer.followingUsers) {
            if (followed.getUsername().equals(username)) return true;
        }
        return false;
    }

    private static void writeTimeline(ObjectOutputStream output, ArrayList<TweetsAndreplies> posts,
                                      boolean hasAuthors) throws IOException {
        output.writeBoolean(hasAuthors);
        if (hasAuthors) {
            output.writeBoolean(!posts.isEmpty());
            posts.sort((a, b) -> b.getTime().compareTo(a.getTime()));
            for (int i = 0; i < posts.size(); i++) {
                output.writeObject(posts.get(i));
                output.writeBoolean(i == posts.size() - 1);
            }
        }
        output.flush();
    }

    public static void Save(Client client, String saveWhat, Client clientToDoActionOn) {
        try {
            // Create main save folder if it doesn't exist
            File mainSaveFolder = new File("SAVES");
            if (!mainSaveFolder.exists()) {
                mainSaveFolder.mkdir();
            }

            // Create client-specific folder if it doesn't exist
            File clientFolder = new File(mainSaveFolder, client.getUsername());
            if (!clientFolder.exists()) {
                clientFolder.mkdir();
            }

            // File for storing followings and accounts
            File followings = new File(clientFolder, "Followings.txt");
            if (!followings.exists()) {
                followings.createNewFile();
            }

            File accounts = new File(mainSaveFolder, "AccountsStatus.txt");
            if (!accounts.exists()) {
                accounts.createNewFile();
            }

            File likedTweets = new File(clientFolder, "likedTweets.txt");
            if (!likedTweets.exists()) {
                likedTweets.createNewFile();
            }

            File savedTweets = new File(clientFolder, "savedTweets.txt");
            if (!savedTweets.exists()) {
                savedTweets.createNewFile();
            }


            switch (saveWhat.toLowerCase()) {
                case "accounts":
                    saveAccounts(client, accounts);
                    break;
                case "tweet":
                    saveTweets(client, clientFolder, mainSaveFolder);
                    break;
                case "reply":
                    // Add implementation for saving replies
                    break;
                case "follow":
                    saveFollow(client, clientToDoActionOn, followings);
                    break;
                case "unfollow":
                    saveUnfollow(client, clientToDoActionOn, followings);
                    break;
                //saved and liked tweets
                case "tweetsavedandliked":
                    saveLikesAndSaves(client, savedTweets, likedTweets);
                    break;
                default:
                    System.err.println("Invalid saveWhat value: " + saveWhat);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void saveLikesAndSaves(Client client, File savedTweets, File likedTweets) {
        if (client == null || savedTweets == null || likedTweets == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        try (FileOutputStream fos5 = new FileOutputStream(savedTweets); ObjectOutputStream oos5 = new ObjectOutputStream(fos5); FileOutputStream fos6 = new FileOutputStream(likedTweets); ObjectOutputStream oos6 = new ObjectOutputStream(fos6)) {
            oos5.writeObject(client.getSavedArraylist());
            oos6.writeObject(client.getLikedArraylist());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void saveAccounts(Client client, File file) {
        try {
            Client.accounts.removeIf(existing ->
                    existing.getUsername().equalsIgnoreCase(client.getUsername()));
            Client.accounts.add(client);

            // Serialize the updated list to file
            try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(Client.accounts);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void saveTweets(Client client, File clientFolder, File mainSaveFolder) {
        try {
            File tweetFile = new File(clientFolder, "Tweets.txt");
            File allTweets = new File(mainSaveFolder, "AllTweets.txt");
            if (!tweetFile.exists()) {
                tweetFile.createNewFile();
            }
            if (!allTweets.exists()) {
                allTweets.createNewFile();
            }

            // Write the entire list to the file
            FileOutputStream fos2 = new FileOutputStream(tweetFile);
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos2.writeObject(client.getTweetArraylist());
            oos2.close();

            FileOutputStream fos3 = new FileOutputStream(allTweets);
            ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
            oos3.writeObject(Client.getAllTweets());
            oos3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveFollow(Client client, Client clientToDoActionOn, File followings) {
        try {
            client.followingUsers.add(clientToDoActionOn);

            // Append to the files
            FileOutputStream fos4 = new FileOutputStream(followings, true);
            ObjectOutputStream oos4 = new ObjectOutputStream(fos4) {
                @Override
                protected void writeStreamHeader() throws IOException {
                    // Don't write the header
                }
            };
            oos4.writeObject(client.followingUsers);
            oos4.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveUnfollow(Client client, Client clientToDoActionOn, File followings) {
        try {
            for (Client client1 : client.followingUsers) {
                if (client1 == clientToDoActionOn) {
                    client.followingUsers.remove(client1);
                    break;
                }
            }

            // Append to the files
            FileOutputStream fos4 = new FileOutputStream(followings, true);
            ObjectOutputStream oos4 = new ObjectOutputStream(fos4) {
                @Override
                protected void writeStreamHeader() throws IOException {
                    // Don't write the header
                }
            };
            oos4.writeObject(client.followingUsers);
            oos4.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Load(Client client, String loadWhat, Client client2, ArrayList<TweetsAndreplies> followedTweetsAndReplies) {
        File mainSaveFolder = new File("SAVES");
        File allTweets = new File(mainSaveFolder, "AllTweets.txt");
        File AccountsStatus = new File(mainSaveFolder, "AccountsStatus.txt");

        File clientFolder = null;
        if (client != null) {
            clientFolder = new File(mainSaveFolder, client.getUsername());
        }

        File likedTweets = null;
        File savedTweets = null;
        File tweetFile = null;
        File followings = null;
        if (clientFolder != null) {
            likedTweets = new File(clientFolder, "likedTweets.txt");
            tweetFile = new File(clientFolder, "Tweets.txt");
            followings = new File(clientFolder, "Followings.txt");
            savedTweets = new File(clientFolder, "savedTweets.txt");
        }

        File clientForActionFolder = null;
        if (client2 != null) {
            clientForActionFolder = (client2 != null) ? new File(mainSaveFolder, client2.getUsername()) : null;
        }

        File tweetFile2 = null;
        if (clientForActionFolder != null) {
            tweetFile2 = (clientForActionFolder != null) ? new File(clientForActionFolder, "Tweets.txt") : null;
        }
        if (mainSaveFolder.exists()) {
            switch (loadWhat.toLowerCase()) {
                case "accounts":
                    loadAccounts(AccountsStatus);
                    break;
                case "tweet":
                    loadTweets(client, tweetFile, allTweets);
                    break;
                case "followings":
                    loadFollowings(client, followings);
                    break;
                case "followedtweets":
                    if (tweetFile2 != null) {
                        loadFollowedTweets(tweetFile2, followedTweetsAndReplies);
                    }
                    break;
                case "tweetsavedandliked":
                    loadLikesAndSaves(likedTweets, savedTweets, client);
                    break;
                default:
                    System.err.println("Invalid loadWhat value: " + loadWhat);
            }
        } else {
            System.err.println("No save folder found");
        }
    }


    private static void loadLikesAndSaves(File likedTweets, File savedTweets, Client client) {
        try {
            // Load liked tweets
            if (likedTweets.exists() && likedTweets.length() > 0) {
                try (FileInputStream fis = new FileInputStream(likedTweets); ObjectInputStream ois = new ObjectInputStream(fis)) {
                    ArrayList<TweetsAndreplies> likedTweets2 = (ArrayList<TweetsAndreplies>) ois.readObject();
                    client.setLikedArraylist(likedTweets2);
                }
            } else {
                client.setLikedArraylist(new ArrayList<>());
            }

            // Load saved tweets
            if (savedTweets.exists() && savedTweets.length() > 0) {
                try (FileInputStream fis1 = new FileInputStream(savedTweets); ObjectInputStream ois1 = new ObjectInputStream(fis1)) {
                    ArrayList<TweetsAndreplies> savedTweets2 = (ArrayList<TweetsAndreplies>) ois1.readObject();
                    client.setSavedArraylist(savedTweets2);
                }
            } else {
                client.setSavedArraylist(new ArrayList<>());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            client.setSavedArraylist(new ArrayList<>());
            client.setLikedArraylist(new ArrayList<>());
        }
    }

    private static void loadAccounts(File file) {
        if (file.exists() && file.length() > 0) {
            try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
                Client.accounts = (ArrayList<Client>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Client.accounts = new ArrayList<>();
        }
    }

    private static void loadTweets(Client client, File tweetFile, File allTweets) {
        try {
            if (tweetFile.exists() && tweetFile.length() > 0) {
                FileInputStream fis = new FileInputStream(tweetFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList<TweetsAndreplies> tweetList = (ArrayList<TweetsAndreplies>) ois.readObject();
                client.setTweetArraylist(tweetList);
                ois.close();

                if (allTweets.exists() && allTweets.length() > 0) {
                    FileInputStream fis1 = new FileInputStream(allTweets);
                    ObjectInputStream ois1 = new ObjectInputStream(fis1);
                    ArrayList<TweetsAndreplies> allTweetsList = (ArrayList<TweetsAndreplies>) ois1.readObject();
                    Client.setAllTweets(allTweetsList);
                    ois1.close();
                } else {
                    Client.setAllTweets(new ArrayList<>());
                }
            } else {
                client.setTweetArraylist(new ArrayList<>());
                Client.setAllTweets(new ArrayList<>());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loadFollowings(Client client, File followings) {
        try {
            if (followings.exists() && followings.length() > 0) {
                try (FileInputStream fis2 = new FileInputStream(followings); ObjectInputStream ois = new ObjectInputStream(fis2)) {
                    ArrayList<Client> followingUsers2 = (ArrayList<Client>) ois.readObject();
                    client.followingUsers = followingUsers2;
                }
            } else {
                client.followingUsers = null;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loadFollowedTweets(File tweetFile2, ArrayList<TweetsAndreplies> followedTweetsAndReplies) {
        try {
            if (tweetFile2.exists() && tweetFile2.length() > 0) {
                try (FileInputStream fis2 = new FileInputStream(tweetFile2); ObjectInputStream ois = new ObjectInputStream(fis2)) {
                    followedTweetsAndReplies = (ArrayList<TweetsAndreplies>) ois.readObject();
                }
            } else {
                followedTweetsAndReplies = null;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}//fix save va load follower
