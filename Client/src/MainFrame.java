import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainFrame extends JFrame implements ActionListener {

    private static final Color PRIMARY_COLOR = new Color(29, 161, 242); // Deep blue
    private static final Color SECONDARY_COLOR = new Color(51, 51, 51); // Dark gray
    private static final Color BACKGROUND_COLOR = new Color(33, 33, 33); // Dark blue-gray
    private static final Color LABEL_COLOR = new Color(170, 170, 170); // Light gray
    private static final Color PANEL_COLOR = new Color(25, 25, 25); // more Dark gray

    private boolean tweetPanelCreated = false;
    Client clientInApp;
    JButton settingsButton;
    JButton profileButton;
    JTextArea tweetArea2;
    JButton reloadButton;
    JButton forYouButton;
    JButton followingButton;
    JButton homeButton;
    JButton searchButton;
    JButton newTweetButton;
    JScrollPane scrollPane;
    JButton Unfollow;
    JButton notificationButton;
    JButton tweetSend;
    JPanel lowerPanel;
    JPanel upperPanel;
    JPanel timeLinePanel;
    JPanel newTweetPanel;
    JPanel tweetpanels;
    JCheckBox likeOthersTweetCheckBox;
    JTextArea tweetArea;
    JButton follow;
    JPanel contentPanel;
    JLabel usernameAndNameAndTimeLable;

    private static ArrayList<Client> followingUsers = new ArrayList<>();
    private static ArrayList<Client> followerUsers = new ArrayList<>();

    boolean tweetLimitation = false;
    ImageIcon xIcon = new ImageIcon("pics\\X.jpg");
    ImageIcon like = new ImageIcon("pics\\like.jpg");
    ImageIcon notlike = new ImageIcon("pics\\notlike.png");

    ImageIcon x2Icon = new ImageIcon("pics\\X2.jpg");
    ImageIcon profileIcon = new ImageIcon("pics\\prof.png");
    ImageIcon settingsIcon = new ImageIcon("pics\\settings.png");
    ImageIcon homeIcon = new ImageIcon("pics\\home.png");
    ImageIcon searchIcon = new ImageIcon("pics\\search.png");
    ImageIcon newTweetIcon = new ImageIcon("pics\\plus.png");
    ImageIcon notificationIcon = new ImageIcon("pics\\notif.png");
    ImageIcon lockIcon = new ImageIcon("pics\\lock.png");
    ImageIcon unlockIcon = new ImageIcon("pics\\unlock.png");
    ImageIcon saveIcon = new ImageIcon("pics\\save.jpeg");
    ImageIcon unsaveIcon = new ImageIcon("pics\\unsave.jpeg");
    JCheckBox privateTweetCheckbox;
    JCheckBox saveTweetCheckbox;
    JCheckBox saveOthersTweetCheckbox;
    private static boolean privateTweet;
    private static boolean saveableTweet;
    private static boolean saveOthersTweets = false;
    private static boolean likeOthersTweets = false;

    MainFrame() {
        showForYou();
    }

    public void resetFrame() {
        this.getContentPane().removeAll();
        this.setLayout(null);
        int frameHeight = Math.min(1000, Toolkit.getDefaultToolkit().getScreenSize().height - 60);
        this.setSize(600, frameHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(BACKGROUND_COLOR);

        //fix reload


        // Upper section
        upperPanel = new JPanel();
        upperPanel.setBackground(PANEL_COLOR);
        upperPanel.setBounds(0, 0, 600, 80);
        upperPanel.setLayout(null);

        timeLinePanel = new JPanel();
        timeLinePanel.setLayout(new GridLayout(1, 2));
        timeLinePanel.setBounds(0, 80, 600, 50);

        forYouButton = new JButton("For You");
        forYouButton.setFont(new Font(null, Font.BOLD, 20));
        forYouButton.setForeground(LABEL_COLOR);
        forYouButton.addActionListener(this);
        forYouButton.setFocusable(false);
        forYouButton.setBorder(BorderFactory.createRaisedBevelBorder());
        forYouButton.setVerticalAlignment(SwingConstants.CENTER);
        forYouButton.setHorizontalAlignment(SwingConstants.CENTER);
        forYouButton.setBackground(SECONDARY_COLOR);

        followingButton = new JButton("Following");
        followingButton.setFont(new Font(null, Font.BOLD, 20));
        followingButton.setForeground(LABEL_COLOR);
        followingButton.addActionListener(this);
        followingButton.setEnabled(false);
        followingButton.setToolTipText("Following is not available in this coursework demo");
        followingButton.setFocusable(false);
        followingButton.setBorder(BorderFactory.createRaisedBevelBorder());
        followingButton.setHorizontalAlignment(SwingConstants.CENTER);
        followingButton.setVerticalAlignment(SwingConstants.CENTER);
        followingButton.setBackground(SECONDARY_COLOR);

        profileButton = new JButton();
        profileButton.setIcon(profileIcon);
        profileButton.setBounds(10, 18, 55, 55);
        profileButton.addActionListener(this);
        profileButton.setFocusable(false);
        profileButton.setBorder(BorderFactory.createRaisedBevelBorder());

        settingsButton = new JButton();
        settingsButton.setIcon(settingsIcon);
        settingsButton.setBounds(518, 18, 55, 55);
        settingsButton.addActionListener(this);
        settingsButton.setFocusable(false);
        settingsButton.setBorder(BorderFactory.createRaisedBevelBorder());
        settingsButton.setEnabled(false);

        reloadButton = new JButton();
        reloadButton.setIcon(x2Icon);
        reloadButton.setBounds(277, 23, 45, 45);
        reloadButton.addActionListener(this);
        reloadButton.setFocusable(false);
        reloadButton.setBorder(BorderFactory.createRaisedBevelBorder());

        timeLinePanel.add(followingButton);
        timeLinePanel.add(forYouButton);
        upperPanel.add(reloadButton);
        upperPanel.add(profileButton);
        upperPanel.add(settingsButton);
        this.add(timeLinePanel);
        this.add(upperPanel);

        // Lower section
        lowerPanel = new JPanel();
        lowerPanel.setBackground(PANEL_COLOR);
        int lowerPanelY = frameHeight - 110;
        lowerPanel.setBounds(0, lowerPanelY, 600, 80);
        lowerPanel.setLayout(null);

        homeButton = new JButton();
        homeButton.setIcon(homeIcon);
        homeButton.setBounds(10, 14, 45, 45);
        homeButton.addActionListener(this);
        homeButton.setFocusable(false);
        homeButton.setBorder(BorderFactory.createRaisedBevelBorder());

        notificationButton = new JButton();
        notificationButton.setIcon(notificationIcon);
        notificationButton.setBounds(528, 14, 45, 45);
        notificationButton.addActionListener(this);
        notificationButton.setFocusable(false);
        notificationButton.setBorder(BorderFactory.createRaisedBevelBorder());
        notificationButton.setEnabled(false);

        searchButton = new JButton();
        searchButton.setIcon(searchIcon);
        searchButton.setBounds(180, 14, 45, 45);
        searchButton.addActionListener(this);
        searchButton.setFocusable(false);
        searchButton.setBorder(BorderFactory.createRaisedBevelBorder());
        searchButton.setEnabled(false);

        newTweetButton = new JButton();
        newTweetButton.setIcon(newTweetIcon);
        newTweetButton.setBounds(365, 14, 45, 45);
        newTweetButton.addActionListener(this);
        newTweetButton.setFocusable(false);
        newTweetButton.setBorder(BorderFactory.createRaisedBevelBorder());

        lowerPanel.add(searchButton);
        lowerPanel.add(homeButton);
        lowerPanel.add(notificationButton);
        lowerPanel.add(newTweetButton);
        this.add(lowerPanel);


        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);


        // Add contentPanel to scrollPane and then add scrollPane to the main frame
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBounds(0, 130, 600, lowerPanelY - 130);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Increase scroll speed
        scrollPane.setWheelScrollingEnabled(true);

        this.add(scrollPane);
        this.setIconImage(xIcon.getImage());
        this.setTitle("Tweeter (X)");
        this.setResizable(false);
        this.setVisible(true);
    }

    public void newTweetGUI() {

        privateTweet = false;
        saveableTweet = false;

        newTweetPanel = new JPanel();
        newTweetPanel.setBackground(PANEL_COLOR);
        newTweetPanel.setSize(new Dimension(600, 210));
        newTweetPanel.setLayout(null);


        privateTweetCheckbox = new JCheckBox("Private Tweet");
        privateTweetCheckbox.setFocusable(false);
        privateTweetCheckbox.setBackground(PRIMARY_COLOR);
        privateTweetCheckbox.setForeground(LABEL_COLOR);
        privateTweetCheckbox.setFont(new Font("Consolas", Font.PLAIN, 15));
        privateTweetCheckbox.setIcon(unlockIcon);
        privateTweetCheckbox.setSelectedIcon(lockIcon);


        privateTweetCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                privateTweet = privateTweetCheckbox.isSelected();
            }
        });
        privateTweetCheckbox.setBounds(40, 166, 165, 35);

        saveTweetCheckbox = new JCheckBox("Allow saving");
        saveTweetCheckbox.setFocusable(false);
        saveTweetCheckbox.setBackground(PRIMARY_COLOR);
        saveTweetCheckbox.setForeground(LABEL_COLOR);
        saveTweetCheckbox.setFont(new Font("Consolas", Font.PLAIN, 15));
        saveTweetCheckbox.setIcon(saveIcon);

        saveTweetCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (saveTweetCheckbox.isSelected()) {
                    saveTweetCheckbox.setText("Allow saving");
                    saveTweetCheckbox.setSelectedIcon(unsaveIcon);
                    saveableTweet = true;
                } else {
                    saveTweetCheckbox.setText("Allow saving");
                    saveTweetCheckbox.setSelectedIcon(saveIcon);
                    saveableTweet = false;
                }
            }
        });
        saveTweetCheckbox.setBounds(225, 166, 185, 35);

        tweetSend = new JButton("Tweet");
        tweetSend.setFocusable(false);
        tweetSend.setFont(new Font("Consolas", Font.PLAIN, 18));
        tweetSend.setForeground(LABEL_COLOR);
        tweetSend.setBackground(PRIMARY_COLOR);
        tweetSend.addActionListener(this);
        tweetSend.setBounds(460, 166, 85, 35);
        tweetSend.setBorder(BorderFactory.createRaisedBevelBorder());

        tweetArea = new JTextArea();
        tweetArea.setFont(new Font("Arial", Font.PLAIN, 20));
        tweetArea.setForeground(Color.lightGray);
        tweetArea.setBackground(SECONDARY_COLOR);
        tweetArea.setCaretColor(Color.lightGray);
        tweetArea.setLineWrap(true);
        tweetArea.setWrapStyleWord(true);
        tweetArea.setBounds(40, 10, 500, 135);
        tweetArea.setToolTipText("Write up to 280 characters");



        newTweetPanel.add(tweetArea);
        newTweetPanel.add(privateTweetCheckbox);
        newTweetPanel.add(saveTweetCheckbox);
        newTweetPanel.add(tweetSend);

        contentPanel.add(newTweetPanel, BorderLayout.CENTER);
        tweetPanelCreated = true;

        contentPanel.repaint();
        contentPanel.revalidate();
        this.repaint();
        this.revalidate();
    }

    public void forYouTL() {
        try {
            clientInApp = ((Client) Main.objectInputStream.readObject());
            ArrayList<TweetsAndreplies> likesArraylist = clientInApp.getLikedArraylist();
            ArrayList<TweetsAndreplies> savedArraylist = clientInApp.getSavedArraylist();


            ArrayList<TweetsAndreplies> allTweets = new ArrayList<>();

            // Ensure you use the existing contentPanel
            contentPanel.removeAll(); // Clear existing content

            // Ensure contentPanel has the correct layout
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            // Read from the stream to get the tweets
            if (Main.objectInputStream.readBoolean()) {
                if (Main.objectInputStream.readBoolean()) {
                    while (true) {
                        try {
                            allTweets.add((TweetsAndreplies) Main.objectInputStream.readObject());
                            if (Main.objectInputStream.readBoolean()) {
                                break;
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    // Add each tweet to the content panel
                    for (TweetsAndreplies tweetsAndreplies : allTweets) {
                        JPanel tweetPanel = new JPanel();
                        tweetPanel.setBackground(BACKGROUND_COLOR);
                        tweetPanel.setPreferredSize(new Dimension(600, 170));
                        tweetPanel.setLayout(null);
                        tweetPanel.setBorder(BorderFactory.createEtchedBorder());

                        if (tweetsAndreplies.isSaveable()) {
                            saveOthersTweetCheckbox = new JCheckBox("Save this TWEET");
                            saveOthersTweetCheckbox.setFocusable(false);
                            saveOthersTweetCheckbox.setBackground(BACKGROUND_COLOR);
                            saveOthersTweetCheckbox.setForeground(LABEL_COLOR);
                            saveOthersTweetCheckbox.setFont(new Font("Consolas", Font.PLAIN, 15));
                            
                            saveOthersTweetCheckbox.setBounds(325, 125, 200, 35);

                            if (!savedArraylist.isEmpty()) {
                                for (TweetsAndreplies tweetsAndreplies1 : clientInApp.getSavedArraylist()) {
                                    if (tweetsAndreplies == tweetsAndreplies1) {
                                        saveOthersTweetCheckbox.setIcon(unsaveIcon);
                                    } else {
                                        saveOthersTweetCheckbox.setIcon(saveIcon);
                                    }
                                }
                            } else {
                                saveOthersTweetCheckbox.setIcon(saveIcon);
                            }

                            attachSaveHandler(saveOthersTweetCheckbox, tweetsAndreplies, savedArraylist);
                            tweetPanel.add(saveOthersTweetCheckbox);
                        }

                        likeOthersTweetCheckBox = new JCheckBox();
                        likeOthersTweetCheckBox.setFocusable(false);
                        likeOthersTweetCheckBox.setBackground(BACKGROUND_COLOR);
                        likeOthersTweetCheckBox.setBounds(50, 125, 200, 35);

                        if (!likesArraylist.isEmpty()) {
                            for (TweetsAndreplies tweetsAndreplies2 : clientInApp.getLikedArraylist()) {
                                if (tweetsAndreplies == tweetsAndreplies2) {
                                    likeOthersTweetCheckBox.setIcon(like);
                                } else {
                                    likeOthersTweetCheckBox.setIcon(notlike);
                                }
                            }
                        } else {
                            likeOthersTweetCheckBox.setIcon(notlike);
                        }

                        attachLikeHandler(likeOthersTweetCheckBox, tweetsAndreplies, likesArraylist);
                        tweetPanel.add(likeOthersTweetCheckBox);


                        JTextArea tweetArea2 = new JTextArea();
                        tweetArea2.setBackground(BACKGROUND_COLOR);
                        tweetArea2.setText(tweetsAndreplies.getTweet());
                        tweetArea2.setForeground(Color.lightGray);
                        tweetArea2.setFont(new Font("Arial", Font.PLAIN, 20));
                        tweetArea2.setLineWrap(true);
                        tweetArea2.setWrapStyleWord(true);
                        tweetArea2.enableInputMethods(false);
                        tweetArea2.setBounds(40, 55, 500, 100);
                        tweetPanel.add(tweetArea2);

                        JLabel usernameAndNameAndTimeLabel = new JLabel(tweetsAndreplies.getClient().getName() + " @" + tweetsAndreplies.getClient().getUsername() + "   " + tweetsAndreplies.getTime());
                        usernameAndNameAndTimeLabel.setBackground(BACKGROUND_COLOR);
                        usernameAndNameAndTimeLabel.setForeground(PRIMARY_COLOR);
                        usernameAndNameAndTimeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                        usernameAndNameAndTimeLabel.setBounds(10, 10, 250, 30);

                        
                        tweetPanel.add(usernameAndNameAndTimeLabel);

                        contentPanel.add(tweetPanel);
                    }

                    contentPanel.repaint();
                    contentPanel.revalidate();
                } else {
                    System.out.println("No tweets found.");
                }
            } else {
                System.out.println("No users found.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void followingTL() {
        try {
            clientInApp = ((Client) Main.objectInputStream.readObject());
            ArrayList<TweetsAndreplies> likesArraylist = clientInApp.getLikedArraylist();
            ArrayList<TweetsAndreplies> savedArraylist = clientInApp.getSavedArraylist();


            ArrayList<TweetsAndreplies> allTweets = new ArrayList<>();

            // Ensure you use the existing contentPanel
            contentPanel.removeAll(); // Clear existing content

            // Ensure contentPanel has the correct layout
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            // Read from the stream to get the tweets
            if (Main.objectInputStream.readBoolean()) {
                if (Main.objectInputStream.readBoolean()) {
                    while (true) {
                        try {
                            allTweets.add((TweetsAndreplies) Main.objectInputStream.readObject());
                            if (Main.objectInputStream.readBoolean()) {
                                break;
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    // Add each tweet to the content panel
                    for (TweetsAndreplies tweetsAndreplies : allTweets) {
                        JPanel tweetPanel = new JPanel();
                        tweetPanel.setBackground(BACKGROUND_COLOR);
                        tweetPanel.setPreferredSize(new Dimension(600, 170));
                        tweetPanel.setLayout(null);
                        tweetPanel.setBorder(BorderFactory.createEtchedBorder());

                        if (tweetsAndreplies.isSaveable()) {
                            saveOthersTweetCheckbox = new JCheckBox("Save this TWEET");
                            saveOthersTweetCheckbox.setFocusable(false);
                            saveOthersTweetCheckbox.setBackground(BACKGROUND_COLOR);
                            saveOthersTweetCheckbox.setForeground(LABEL_COLOR);
                            saveOthersTweetCheckbox.setFont(new Font("Consolas", Font.PLAIN, 15));
                            
                            saveOthersTweetCheckbox.setBounds(325, 125, 200, 35);

                            if (!savedArraylist.isEmpty()) {
                                for (TweetsAndreplies tweetsAndreplies1 : clientInApp.getSavedArraylist()) {
                                    if (tweetsAndreplies == tweetsAndreplies1) {
                                        saveOthersTweetCheckbox.setIcon(unsaveIcon);
                                    } else {
                                        saveOthersTweetCheckbox.setIcon(saveIcon);
                                    }
                                }
                            } else {
                                saveOthersTweetCheckbox.setIcon(saveIcon);
                            }

                            attachSaveHandler(saveOthersTweetCheckbox, tweetsAndreplies, savedArraylist);
                            tweetPanel.add(saveOthersTweetCheckbox);
                        }

                        likeOthersTweetCheckBox = new JCheckBox();
                        likeOthersTweetCheckBox.setFocusable(false);
                        likeOthersTweetCheckBox.setBackground(BACKGROUND_COLOR);
                        likeOthersTweetCheckBox.setBounds(50, 125, 200, 35);

                        if (!likesArraylist.isEmpty()) {
                            for (TweetsAndreplies tweetsAndreplies2 : clientInApp.getLikedArraylist()) {
                                if (tweetsAndreplies == tweetsAndreplies2) {
                                    likeOthersTweetCheckBox.setIcon(like);
                                } else {
                                    likeOthersTweetCheckBox.setIcon(notlike);
                                }
                            }
                        } else {
                            likeOthersTweetCheckBox.setIcon(notlike);
                        }

                        attachLikeHandler(likeOthersTweetCheckBox, tweetsAndreplies, likesArraylist);
                        tweetPanel.add(likeOthersTweetCheckBox);


                        JTextArea tweetArea2 = new JTextArea();
                        tweetArea2.setBackground(BACKGROUND_COLOR);
                        tweetArea2.setText(tweetsAndreplies.getTweet());
                        tweetArea2.setForeground(Color.lightGray);
                        tweetArea2.setFont(new Font("Arial", Font.PLAIN, 20));
                        tweetArea2.setLineWrap(true);
                        tweetArea2.setWrapStyleWord(true);
                        tweetArea2.enableInputMethods(false);
                        tweetArea2.setBounds(40, 55, 500, 100);
                        tweetPanel.add(tweetArea2);

                        JLabel usernameAndNameAndTimeLabel = new JLabel(tweetsAndreplies.getClient().getName() + " @" + tweetsAndreplies.getClient().getUsername() + "   " + tweetsAndreplies.getTime());
                        usernameAndNameAndTimeLabel.setBackground(BACKGROUND_COLOR);
                        usernameAndNameAndTimeLabel.setForeground(PRIMARY_COLOR);
                        usernameAndNameAndTimeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                        usernameAndNameAndTimeLabel.setBounds(10, 10, 250, 30);

                        
                        contentPanel.add(tweetPanel);
                        tweetPanel.add(usernameAndNameAndTimeLabel);
                    }

                    contentPanel.repaint();
                    contentPanel.revalidate();
                } else {
                    System.out.println("No tweets found.");
                }
            } else {
                System.out.println("No users found.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void profile(Client client) {
        try {
            Main.objectOutputStream.writeUTF("profile");
            Main.objectOutputStream.flush();
            clientInApp = ((Client) Main.objectInputStream.readObject());
            ArrayList<TweetsAndreplies> likesArraylist = clientInApp.getLikedArraylist();
            ArrayList<TweetsAndreplies> savedArraylist = clientInApp.getSavedArraylist();


            ArrayList<TweetsAndreplies> allTweets = new ArrayList<>();

            // Ensure you use the existing contentPanel
            contentPanel.removeAll(); // Clear existing content

            // Ensure contentPanel has the correct layout
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            // Read from the stream to get the tweets
            if (Main.objectInputStream.readBoolean()) {
                while (true) {
                    allTweets.add((TweetsAndreplies) Main.objectInputStream.readObject());
                    if (Main.objectInputStream.readBoolean()) {
                        break;
                    }
                }
            }

            ArrayList<Client> followingUsersList = new ArrayList<>();
            if (Main.objectInputStream.readBoolean()) {
                while (true) {
                    followingUsersList.add((Client) Main.objectInputStream.readObject());
                    if (Main.objectInputStream.readBoolean()) {
                        break;
                    }
                }
                clientInApp.followingUsers = followingUsersList;
            } else {
                clientInApp.followingUsers = followingUsersList;
            }

            ArrayList<Client> followersUsersList = new ArrayList<>();
            if (Main.objectInputStream.readBoolean()) {
                while (true) {
                    followersUsersList.add((Client) Main.objectInputStream.readObject());
                    if (Main.objectInputStream.readBoolean()) {
                        break;
                    }
                }
                clientInApp.followerUsers = followersUsersList;
            } else {
                clientInApp.followerUsers = followersUsersList;
            }
            //upper section of profiles
            JPanel profilepanel = new JPanel();
            profilepanel.setBackground(PANEL_COLOR);
            profilepanel.setPreferredSize(new Dimension(600, 400));
            profilepanel.setLayout(null);
            profilepanel.setBorder(BorderFactory.createEtchedBorder());

            JLabel NameLabel = new JLabel(clientInApp.getName());
            NameLabel.setBackground(PANEL_COLOR);
            NameLabel.setForeground(PRIMARY_COLOR);
            NameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
            NameLabel.setBounds(10, 10, 520, 35);
            profilepanel.add(NameLabel);

            JLabel userNameLabel = new JLabel("@" + clientInApp.getUsername());
            userNameLabel.setBackground(PANEL_COLOR);
            userNameLabel.setForeground(PRIMARY_COLOR);
            userNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            userNameLabel.setBounds(10, 50, 520, 35);
            profilepanel.add(userNameLabel);

            JTextArea bio = new JTextArea();
            bio.setBackground(PANEL_COLOR);
            bio.setText(clientInApp.getBio());
            bio.setForeground(Color.lightGray);
            bio.setFont(new Font("Arial", Font.PLAIN, 20));
            bio.setLineWrap(true);
            bio.setWrapStyleWord(true);
            bio.enableInputMethods(false);
            bio.setBounds(40, 100, 500, 120);
            profilepanel.add(bio);

            JLabel birthDAteLabel = new JLabel(clientInApp.getDateOfBirth());
            birthDAteLabel.setBackground(PANEL_COLOR);
            birthDAteLabel.setForeground(PRIMARY_COLOR);
            birthDAteLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            birthDAteLabel.setBounds(10, 245, 150, 35);
            profilepanel.add(birthDAteLabel);


            int followingCount = 0;
            int followersCount = 0;

            followersCount = followersUsersList.size();
            followingCount = followingUsersList.size();

            JButton followersButton = new JButton("Followers: " + followersCount);
            followersButton.setBounds(120, 300, 120, 30);
            profilepanel.add(followersButton);

            JButton followingButton = new JButton("Following: " + followingCount);
            followingButton.setBounds(400, 300, 120, 30);
            profilepanel.add(followingButton);

            followersButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createScrollablePanel(clientInApp.followerUsers);
                }
            });

            followingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createScrollablePanel(clientInApp.followingUsers);
                }
            });

            contentPanel.add(profilepanel);


            // Add each tweet to the content panel
            for (TweetsAndreplies tweetsAndreplies : allTweets) {
                JPanel tweetPanel = new JPanel();
                tweetPanel.setBackground(BACKGROUND_COLOR);
                tweetPanel.setPreferredSize(new Dimension(600, 170));
                tweetPanel.setLayout(null);
                tweetPanel.setBorder(BorderFactory.createEtchedBorder());

                if (tweetsAndreplies.isSaveable()) {
                    saveOthersTweetCheckbox = new JCheckBox("Save this TWEET");
                    saveOthersTweetCheckbox.setFocusable(false);
                    saveOthersTweetCheckbox.setBackground(BACKGROUND_COLOR);
                    saveOthersTweetCheckbox.setForeground(LABEL_COLOR);
                    saveOthersTweetCheckbox.setFont(new Font("Consolas", Font.PLAIN, 15));
                    
                    saveOthersTweetCheckbox.setBounds(325, 125, 200, 35);

                    if (!savedArraylist.isEmpty()) {
                        for (TweetsAndreplies tweetsAndreplies1 : clientInApp.getSavedArraylist()) {
                            if (tweetsAndreplies == tweetsAndreplies1) {
                                saveOthersTweetCheckbox.setIcon(unsaveIcon);
                            } else {
                                saveOthersTweetCheckbox.setIcon(saveIcon);
                            }
                        }
                    } else {
                        saveOthersTweetCheckbox.setIcon(saveIcon);
                    }

                    attachSaveHandler(saveOthersTweetCheckbox, tweetsAndreplies, savedArraylist);
                    tweetPanel.add(saveOthersTweetCheckbox);
                }

                likeOthersTweetCheckBox = new JCheckBox();
                likeOthersTweetCheckBox.setFocusable(false);
                likeOthersTweetCheckBox.setBackground(BACKGROUND_COLOR);
                likeOthersTweetCheckBox.setBounds(50, 125, 200, 35);

                if (!likesArraylist.isEmpty()) {
                    for (TweetsAndreplies tweetsAndreplies2 : clientInApp.getLikedArraylist()) {
                        if (tweetsAndreplies == tweetsAndreplies2) {
                            likeOthersTweetCheckBox.setIcon(like);
                        } else {
                            likeOthersTweetCheckBox.setIcon(notlike);
                        }
                    }
                } else {
                    likeOthersTweetCheckBox.setIcon(notlike);
                }

                attachLikeHandler(likeOthersTweetCheckBox, tweetsAndreplies, likesArraylist);
                tweetPanel.add(likeOthersTweetCheckBox);


                JTextArea tweetArea2 = new JTextArea();
                tweetArea2.setBackground(BACKGROUND_COLOR);
                tweetArea2.setText(tweetsAndreplies.getTweet());
                tweetArea2.setForeground(Color.lightGray);
                tweetArea2.setFont(new Font("Arial", Font.PLAIN, 20));
                tweetArea2.setLineWrap(true);
                tweetArea2.setWrapStyleWord(true);
                tweetArea2.enableInputMethods(false);
                tweetArea2.setBounds(40, 55, 500, 100);
                tweetPanel.add(tweetArea2);

                JLabel usernameAndNameAndTimeLabel = new JLabel(tweetsAndreplies.getClient().getName() + " @" + tweetsAndreplies.getClient().getUsername() + "   " + tweetsAndreplies.getTime());
                usernameAndNameAndTimeLabel.setBackground(BACKGROUND_COLOR);
                usernameAndNameAndTimeLabel.setForeground(PRIMARY_COLOR);
                usernameAndNameAndTimeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                usernameAndNameAndTimeLabel.setBounds(10, 10, 250, 30);
                tweetPanel.add(usernameAndNameAndTimeLabel);

                contentPanel.add(tweetPanel);
            }

            contentPanel.repaint();
            contentPanel.revalidate();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            showForYou();
        } else if (e.getSource() == reloadButton) {
            showForYou();
        } else if (e.getSource() == settingsButton) {

        } else if (e.getSource() == profileButton) {
            profile(clientInApp);
        } else if (e.getSource() == notificationButton) {

        } else if (e.getSource() == searchButton) {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        } else if (e.getSource() == newTweetButton) {
            //fix
            resetFrame();
            if (!tweetPanelCreated) {
                newTweetGUI();
                tweetPanelCreated = true;
            } else {
                contentPanel.remove(newTweetPanel);
                tweetPanelCreated = false;
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        } else if (e.getSource() == tweetSend) {
            try {
                String post = tweetArea.getText().trim();
                if (post.isEmpty() || post.length() > 280) {
                    JOptionPane.showMessageDialog(this, "Write a post of 1 to 280 characters.");
                    return;
                }
                Main.objectOutputStream.writeUTF("newTweet");
                Main.objectOutputStream.flush();
                Main.objectOutputStream.writeUTF(post);
                Main.objectOutputStream.flush();
                Main.objectOutputStream.writeBoolean(saveableTweet);
                Main.objectOutputStream.flush();
                Main.objectOutputStream.writeBoolean(privateTweet);
                Main.objectOutputStream.flush();

                if (!Main.objectInputStream.readBoolean()) {
                    JOptionPane.showMessageDialog(this, "The post could not be published.");
                    return;
                }

                contentPanel.remove(newTweetPanel);
                tweetPanelCreated = false;
                contentPanel.revalidate();
                contentPanel.repaint();
                profile(clientInApp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == followingButton) {
            resetFrame();
            try {
                Main.objectOutputStream.writeUTF("followingTL");
                Main.objectOutputStream.flush();
                followingTL();

            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } else if (e.getSource() == forYouButton) {
            showForYou();
        }
    }

    private void showForYou() {
        resetFrame();
        try {
            Main.objectOutputStream.writeUTF("forYouTL");
            Main.objectOutputStream.flush();
            forYouTL();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not load the timeline.");
        }
    }


    private static boolean samePost(TweetsAndreplies a, TweetsAndreplies b) {
        return Objects.equals(a.getClient().getUsername(), b.getClient().getUsername()) &&
                Objects.equals(a.getTweet(), b.getTweet()) && Objects.equals(a.getTime(), b.getTime());
    }

    private static boolean containsPost(ArrayList<TweetsAndreplies> posts, TweetsAndreplies target) {
        return posts.stream().anyMatch(post -> samePost(post, target));
    }

    private void sendListUpdate(String kind, int count, ArrayList<TweetsAndreplies> posts) throws IOException {
        Main.objectOutputStream.reset();
        Main.objectOutputStream.writeUTF("UpdateClient");
        Main.objectOutputStream.writeUTF(kind);
        Main.objectOutputStream.writeInt(count);
        for (TweetsAndreplies post : posts) {
            Main.objectOutputStream.writeBoolean(true);
            Main.objectOutputStream.writeObject(post);
        }
        Main.objectOutputStream.writeBoolean(false);
        Main.objectOutputStream.flush();
    }

    private void attachSaveHandler(JCheckBox checkBox, TweetsAndreplies post,
                                   ArrayList<TweetsAndreplies> savedPosts) {
        boolean saved = containsPost(savedPosts, post);
        checkBox.setSelected(saved);
        checkBox.setIcon(saved ? unsaveIcon : saveIcon);
        checkBox.setText(saved ? "Unsave this TWEET" : "Save this TWEET");
        checkBox.addActionListener(e -> {
            boolean nowSaved = checkBox.isSelected();
            if (nowSaved && !containsPost(savedPosts, post)) savedPosts.add(post);
            if (!nowSaved) savedPosts.removeIf(item -> samePost(item, post));
            checkBox.setIcon(nowSaved ? unsaveIcon : saveIcon);
            checkBox.setText(nowSaved ? "Unsave this TWEET" : "Save this TWEET");
            clientInApp.setSavedArraylist(savedPosts);
            clientInApp.setSaveCounts(savedPosts.size());
            try {
                sendListUpdate("save", savedPosts.size(), savedPosts);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not update saved posts.");
            }
        });
    }

    private void attachLikeHandler(JCheckBox checkBox, TweetsAndreplies post,
                                   ArrayList<TweetsAndreplies> likedPosts) {
        boolean liked = containsPost(likedPosts, post);
        checkBox.setSelected(liked);
        checkBox.setIcon(liked ? like : notlike);
        checkBox.addActionListener(e -> {
            boolean nowLiked = checkBox.isSelected();
            if (nowLiked && !containsPost(likedPosts, post)) likedPosts.add(post);
            if (!nowLiked) likedPosts.removeIf(item -> samePost(item, post));
            checkBox.setIcon(nowLiked ? like : notlike);
            clientInApp.setLikedArraylist(likedPosts);
            clientInApp.setLikeCounts(likedPosts.size());
            try {
                sendListUpdate("like", likedPosts.size(), likedPosts);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not update liked posts.");
            }
        });
    }

    private void createScrollablePanel(ArrayList<Client> clients) {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));

        for (Client client : clients) {
            JLabel clientLabel = new JLabel(client.getName() + " @" + client.getUsername());
            clientLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            scrollPanel.add(clientLabel);
        }

        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        contentPanel.add(scrollPane);
        contentPanel.setVisible(true);
    }

}
