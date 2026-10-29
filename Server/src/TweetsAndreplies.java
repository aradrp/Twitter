import java.io.Serializable;
import java.util.ArrayList;

public class TweetsAndreplies implements Serializable {

    private String tweet;
    private String reply;
    private Client client;
    private String time;
    private boolean privateTweet;
    private boolean saveable;
    private boolean tweetOrReply;

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isPrivateTweet() {
        return privateTweet;
    }

    public void setPrivateTweet(boolean privateTweet) {
        this.privateTweet = privateTweet;
    }

    public boolean isSaveable() {
        return saveable;
    }

    public void setSaveable(boolean saveable) {
        this.saveable = saveable;
    }

    public boolean isTweetOrReply() {
        return tweetOrReply;
    }

    public void setTweetOrReply(boolean tweetOrReply) {
        this.tweetOrReply = tweetOrReply;
    }

    // if tweetOrReply is true, it's a tweet and if false it's a reply.
    TweetsAndreplies(boolean tweetOrReply, Client client, String tweet, String reply, String time, boolean privateTweet, boolean saveable) {
        this.tweetOrReply = tweetOrReply;
        this.client = client;
        this.tweet = tweet;
        this.reply = reply;
        this.privateTweet = privateTweet;
        this.saveable = saveable;
        this.time = time;
    }
}
