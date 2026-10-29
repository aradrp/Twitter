import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable {

    // one file from here...
    private String name;
    private String username;
    private String joinedTime;
    private String dateOfBirth;
    private String bio;
    private String phoneNumber;
    private String email;
    private int likeCounts;
    private int saveCounts;
    private int Replies;
    private int tweets;
    private boolean privateAccount;

    //... to here

    // one file for these tow
    ArrayList<Client> followingUsers = new ArrayList<>();
    ArrayList<Client> followerUsers = new ArrayList<>();

    //one file for each of these
    ArrayList<String> resentSearchArraylist = new ArrayList<>();
    ArrayList<Client> resentUserSearchedArraylist = new ArrayList<>();


    private  ArrayList<TweetsAndreplies> tweetArraylist = new ArrayList<>();
    private  ArrayList<TweetsAndreplies> replyArraylist = new ArrayList<>();
    private  ArrayList<TweetsAndreplies> savedArraylist = new ArrayList<>();
    private  ArrayList<TweetsAndreplies> likedArraylist = new ArrayList<>();
    private static ArrayList<TweetsAndreplies> allTweets = new ArrayList<>();

    public static ArrayList<TweetsAndreplies> getAllTweets() {
        return allTweets;
    }

    public static void setAllTweets(ArrayList<TweetsAndreplies> allTweets) {
        Client.allTweets = allTweets;
    }

    public static ArrayList<Client> accounts = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJoinedTime() {
        return joinedTime;
    }

    public void setJoinedTime(String joinedTime) {
        this.joinedTime = joinedTime;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(int likeCounts) {
        this.likeCounts = likeCounts;
    }

    public int getSaveCounts() {
        return saveCounts;
    }

    public void setSaveCounts(int saveCounts) {
        this.saveCounts = saveCounts;
    }

    public int getReplies() {
        return Replies;
    }

    public void setReplies(int replies) {
        Replies = replies;
    }

    public int getTweets() {
        return tweets;
    }

    public void setTweets(int tweets) {
        this.tweets = tweets;
    }

    public boolean isPrivateAccount() {
        return privateAccount;
    }

    public void setPrivateAccount(boolean privateAccount) {
        this.privateAccount = privateAccount;
    }

    Client(String name,
           String username,
           String joinedTime,
           String dateOfBirth,
           String phoneNumber,
           String email,
           int likeCounts,
           int saveCounts,
           int Replies,
           int tweets,
           String bio,
           boolean privateAccount) {

        this.privateAccount= privateAccount;
        this.name = name;
        this.username = username;
        this.joinedTime = joinedTime;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.likeCounts = likeCounts;
        this.saveCounts = saveCounts;
        this.Replies = Replies;
        this.tweets = tweets;
    }

    public static void setAccounts(Client client){
        accounts.add(client);
    }

    public ArrayList<TweetsAndreplies> getTweetArraylist() {
        return tweetArraylist;
    }

    public void setTweetArraylist(ArrayList<TweetsAndreplies> tweetArraylist) {
        this.tweetArraylist = tweetArraylist;
    }

    public ArrayList<TweetsAndreplies> getReplyArraylist() {
        return replyArraylist;
    }

    public void setReplyArraylist(ArrayList<TweetsAndreplies> replyArraylist) {
        this.replyArraylist = replyArraylist;
    }

    public ArrayList<TweetsAndreplies> getSavedArraylist() {
        return savedArraylist;
    }

    public void setSavedArraylist(ArrayList<TweetsAndreplies> savedArraylist) {
        this.savedArraylist = savedArraylist;
    }

    public ArrayList<TweetsAndreplies> getLikedArraylist() {
        return likedArraylist;
    }

    public void setLikedArraylist(ArrayList<TweetsAndreplies> likedArraylist) {
        this.likedArraylist = likedArraylist;
    }
}
