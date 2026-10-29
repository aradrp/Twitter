import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MenuFrame extends JFrame implements ActionListener, KeyListener {
    JButton button1;
    JButton button2;
    JButton button3;
    JButton hiddenPassword;
    JButton hiddenPassword2;
    JButton logInbutton;
    JButton signUpButton;
    JButton returnButton;
    ImageIcon xIcon;
    static ImageIcon showPasswordpic = new ImageIcon("pics\\shown.png");
    static ImageIcon hidePasswordpic = new ImageIcon("pics\\hidden.jpg");
    JLabel lable1;
    JLabel emailLabel1;
    JLabel emailLabel2;
    JLabel passwordLable1;
    JLabel passwordLable2;
    JLabel Phonelable;
    JLabel birthLable;
    JLabel usernamelable;
    JLabel namelable;
    JLabel biolable;
    static JTextField emailfield2;
    static JTextField namefield;
    static JTextField usernamefield;
    static JTextField emailField;
    static JTextField birhtdateField;
    static JTextField phoneNumberField;
    static JTextField bioField;
    static JPasswordField passwordfieldSignUp;
    static JPasswordField passwordfield2;
    private static final Color PRIMARY_COLOR = new Color(29, 161, 242); // Deep blue for buttons
    private static final Color SECONDARY_COLOR = new Color(51, 51, 51); // Dark gray for text fields
    private static final Color BACKGROUND_COLOR = new Color(33, 33, 33); // Dark blue-gray
    private static final Color LABEL_COLOR = new Color(170, 170, 170); // Light gray for strings color


    MenuFrame(){
        resetFrame();
    }
    public void resetFrame() {
        this.getContentPane().removeAll();
        this.setLayout(null);
        this.setSize(600, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        xIcon = new ImageIcon("pics\\X.jpg");


        button1 = new JButton();
        button1.setBounds(235, 390, 100, 40);
        button1.addActionListener(this);
        button1.setText("Log in");
        button1.setFocusable(false);
        button1.setFont(new Font(null, Font.BOLD, 20));
        button1.setBackground(PRIMARY_COLOR);
        button1.setBorder(BorderFactory.createRaisedBevelBorder());

        button2 = new JButton();
        button2.setBounds(235, 450, 100, 40);
        button2.addActionListener(this);
        button2.setText("Sign up");
        button2.setFocusable(false);
        button2.setFont(new Font(null, Font.BOLD, 20));
        button2.setBackground(PRIMARY_COLOR);
        button2.setBorder(BorderFactory.createRaisedBevelBorder());

        button3 = new JButton();
        button3.setBounds(235, 510, 100, 40);
        button3.addActionListener(this);
        button3.setText("Exit");
        button3.setFocusable(false);
        button3.setFont(new Font(null, Font.BOLD, 20));
        button3.setBackground(PRIMARY_COLOR);
        button3.setBorder(BorderFactory.createRaisedBevelBorder());


        lable1 = new JLabel();
        lable1.setIcon(xIcon);
        lable1.setBounds(200, 200, 170, 170);


        this.add(lable1);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.setIconImage(xIcon.getImage());
        this.setTitle("Tweeter (X)");
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            button1.setVisible(false);
            button2.setVisible(false);
            button3.setVisible(false);
            logInMenu();

        } else if (e.getSource() == button2) {
            button1.setVisible(false);
            button2.setVisible(false);
            button3.setVisible(false);
            lable1.setVisible(false);
            signUpMenu();


        } else if (e.getSource() == hiddenPassword) {

         if (hiddenPassword.getIcon()==hidePasswordpic){
             hiddenPassword.setIcon(showPasswordpic);
             passwordfieldSignUp.setEchoChar((char)0); // make password visible

         }else {
             hiddenPassword.setIcon(hidePasswordpic);
             passwordfieldSignUp.setEchoChar('*'); // make password hidden

         }
        } else if (e.getSource() == hiddenPassword2) {
            if (hiddenPassword2.getIcon()==hidePasswordpic){
                hiddenPassword2.setIcon(showPasswordpic);
                passwordfield2.setEchoChar((char)0); // make password visible

            }else {
                hiddenPassword2.setIcon(hidePasswordpic);
                passwordfield2.setEchoChar('*'); // make password hidden

            }

        } else if (e.getSource() == button3) {
            System.exit(0);

        } else if (e.getSource() == logInbutton) {

            try {
                Main.objectOutputStream.writeUTF("logIn");
                Main.objectOutputStream.flush();
                //email
                Main.objectOutputStream.writeUTF(emailfield2.getText());
                Main.objectOutputStream.flush();
                //password
                Main.objectOutputStream.writeUTF(new String(passwordfield2.getPassword()));
                Main.objectOutputStream.flush();

                boolean accountsFound = Main.objectInputStream.readBoolean();
                if (accountsFound) {

                    boolean loginPasswordStatus = Main.objectInputStream.readBoolean();
                    boolean loginEmailStatus = Main.objectInputStream.readBoolean();

                    if (loginPasswordStatus && loginEmailStatus) {
                        this.dispose();
                        new MainFrame();
                    } else if (!loginEmailStatus) {
                        JOptionPane.showMessageDialog(null, "Email not found", "Email error", JOptionPane.ERROR_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(null, "Password is not correct", "Password error", JOptionPane.ERROR_MESSAGE);

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Email not found", "not found error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {

                ex.printStackTrace();
            }
        } else if (e.getSource() == signUpButton) {

            try {

                Main.objectOutputStream.writeUTF("signUp");
                Main.objectOutputStream.flush();
                Main.objectOutputStream.writeUTF(namefield.getText());
                Main.objectOutputStream.flush();
                Main.objectOutputStream.writeUTF(usernamefield.getText());
                Main.objectOutputStream.flush();
                Main.objectOutputStream.writeUTF(emailField.getText());
                Main.objectOutputStream.flush();
                Main.objectOutputStream.writeUTF(phoneNumberField.getText());
                Main.objectOutputStream.flush();
                Main.objectOutputStream.writeUTF(birhtdateField.getText());
                Main.objectOutputStream.flush();
                Main.objectOutputStream.writeUTF(new String(passwordfieldSignUp.getPassword()));
                Main.objectOutputStream.flush();
                Main.objectOutputStream.writeUTF(bioField.getText());
                Main.objectOutputStream.flush();

                boolean emailStatus = Main.objectInputStream.readBoolean();
                boolean usernameStatus = Main.objectInputStream.readBoolean();
                boolean phoneNumberStatus = Main.objectInputStream.readBoolean();
                boolean passwordStatus = Main.objectInputStream.readBoolean();
                boolean birthdateStatus = Main.objectInputStream.readBoolean();


                if (emailStatus && usernameStatus && phoneNumberStatus && passwordStatus && birthdateStatus) {
                    setVisible(false);
                    resetFrame();
                } else if (!emailStatus) {
                    JOptionPane.showMessageDialog(null, "This email is already in use or does not contain @", "email error", JOptionPane.ERROR_MESSAGE);

                } else if (!usernameStatus) {
                    JOptionPane.showMessageDialog(null, "This username is already in use", "username error", JOptionPane.ERROR_MESSAGE);

                } else if (!phoneNumberStatus) {
                    JOptionPane.showMessageDialog(null, "This phone number is either too short, too long or contains other characters", "phone number error", JOptionPane.ERROR_MESSAGE);

                } else if (!passwordStatus) {
                    JOptionPane.showMessageDialog(null, "This password is either too short,does not contain anu letter or does not contain any number", "password error", JOptionPane.ERROR_MESSAGE);

                } else if (!birthdateStatus) {
                    JOptionPane.showMessageDialog(null, "either you're entered birth date format is wrong or or your using other characters that are not allowed", "birth date error", JOptionPane.ERROR_MESSAGE);

                }
            } catch (IOException ex) {

                ex.printStackTrace();
            }

        } else if (e.getSource() == returnButton) {
            setVisible(false);
            resetFrame();

        }
    }

    public void logInMenu() {

        emailfield2 = new JTextField();
        emailfield2.setBounds(160, 400, 250, 50);
        emailfield2.setBackground(SECONDARY_COLOR);
        emailfield2.setToolTipText("Email");
        emailfield2.setFont(new Font(null, Font.ITALIC, 25));
        emailfield2.setForeground(LABEL_COLOR);

        passwordfield2 = new JPasswordField();
        passwordfield2.setBounds(160, 480, 250, 50);
        passwordfield2.setBackground(SECONDARY_COLOR);
        passwordfield2.setToolTipText("Password");
        passwordfield2.setFont(new Font(null, Font.ITALIC, 25));
        passwordfield2.setForeground(LABEL_COLOR);

        emailfield2.setBorder(BorderFactory.createRaisedBevelBorder());
        passwordfield2.setBorder(BorderFactory.createRaisedBevelBorder());

        emailfield2.setCaretColor(LABEL_COLOR);
        passwordfield2.setCaretColor(LABEL_COLOR);

        emailLabel1 = new JLabel("Email :");
        emailLabel1.setFont(new Font(null, Font.BOLD, 20));
        emailLabel1.setForeground(LABEL_COLOR);
        emailLabel1.setBounds(10, 370, 100, 100);

        passwordLable1 = new JLabel("Password :");
        passwordLable1.setFont(new Font(null, Font.BOLD, 20));
        passwordLable1.setForeground(LABEL_COLOR);
        passwordLable1.setBounds(10, 460, 130, 100);


        logInbutton = new JButton();
        logInbutton.addActionListener(this);
        logInbutton.setText("Log in");
        logInbutton.setFocusable(false);
        logInbutton.setFont(new Font(null, Font.BOLD, 20));
        logInbutton.setBackground(PRIMARY_COLOR);
        logInbutton.setBorder(BorderFactory.createRaisedBevelBorder());
        logInbutton.setBounds(230, 600, 100, 40);

        returnButton = new JButton();
        returnButton.addActionListener(this);
        returnButton.setText("Return");
        returnButton.setFocusable(false);
        returnButton.setFont(new Font(null, Font.BOLD, 20));
        returnButton.setBackground(PRIMARY_COLOR);
        returnButton.setBorder(BorderFactory.createRaisedBevelBorder());
        returnButton.setBounds(10, 700, 100, 50);



        hiddenPassword2 =new JButton();
        hiddenPassword2.addActionListener(this);
        hiddenPassword2.setIcon(hidePasswordpic);
        hiddenPassword2.setFocusable(false);
        hiddenPassword2.setBorder(BorderFactory.createRaisedBevelBorder());
        hiddenPassword2.setBounds(440, 493, 25, 25);

        this.add(returnButton);
        this.add(hiddenPassword2);
        this.add(logInbutton);
        this.add(emailLabel1);
        this.add(passwordLable1);
        this.add(passwordfield2);
        this.add(emailfield2);
        this.revalidate();
        this.repaint();
        this.setVisible(true);

    }

    public void signUpMenu() {

        namefield = new JTextField();
        namefield.setBounds(155, 20, 250, 50);
        namefield.setBackground(SECONDARY_COLOR);
        namefield.setToolTipText("Name");
        namefield.setFont(new Font(null, Font.ITALIC, 25));

        usernamefield = new JTextField();
        usernamefield.setBounds(155, 100, 250, 50);
        usernamefield.setBackground(SECONDARY_COLOR);
        usernamefield.setToolTipText("Username");
        usernamefield.setFont(new Font(null, Font.ITALIC, 25));

        //fix setline wrap va baghie chiza mesl send tweet.
        //ye panel baz she vaghti mos raft roosh va vaghti dar oomad
        //panel baste she text to field bashe

        bioField = new JTextField();
        bioField.setBounds(155, 180, 250, 50);
        bioField.setBackground(SECONDARY_COLOR);
        bioField.setToolTipText("CE, Gamer and etc");
        bioField.setFont(new Font(null, Font.ITALIC, 25));

        emailField = new JTextField();
        emailField.setBounds(155, 260, 250, 50);
        emailField.setBackground(SECONDARY_COLOR);
        emailField.setToolTipText("Email");
        emailField.setFont(new Font(null, Font.ITALIC, 25));

        birhtdateField = new JTextField();
        birhtdateField.setBounds(155, 340, 250, 50);
        birhtdateField.setBackground(SECONDARY_COLOR);
        birhtdateField.setToolTipText("04/10/1383");
        birhtdateField.setFont(new Font(null, Font.ITALIC, 25));

        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(155, 420, 250, 50);
        phoneNumberField.setBackground(SECONDARY_COLOR);
        phoneNumberField.setToolTipText("0933***6609");
        phoneNumberField.setFont(new Font(null, Font.ITALIC, 25));

        namefield.setBorder(BorderFactory.createRaisedBevelBorder());
        usernamefield.setBorder(BorderFactory.createRaisedBevelBorder());
        emailField.setBorder(BorderFactory.createRaisedBevelBorder());
        birhtdateField.setBorder(BorderFactory.createRaisedBevelBorder());
        phoneNumberField.setBorder(BorderFactory.createRaisedBevelBorder());
        bioField.setBorder(BorderFactory.createRaisedBevelBorder());

        namefield.setForeground(LABEL_COLOR);
        usernamefield.setForeground(LABEL_COLOR);
        emailField.setForeground(LABEL_COLOR);
        birhtdateField.setForeground(LABEL_COLOR);
        phoneNumberField.setForeground(LABEL_COLOR);
        bioField.setForeground(LABEL_COLOR);

        namefield.setCaretColor(LABEL_COLOR);
        usernamefield.setCaretColor(LABEL_COLOR);
        emailField.setCaretColor(LABEL_COLOR);
        birhtdateField.setCaretColor(LABEL_COLOR);
        phoneNumberField.setCaretColor(LABEL_COLOR);
        bioField.setCaretColor(LABEL_COLOR);


        passwordfieldSignUp = new JPasswordField();
        passwordfieldSignUp.setBounds(155, 500, 250, 50);
        passwordfieldSignUp.setBackground(SECONDARY_COLOR);
        passwordfieldSignUp.setToolTipText("Password");
        passwordfieldSignUp.setFont(new Font(null, Font.ITALIC, 25));
        passwordfieldSignUp.setBorder(BorderFactory.createRaisedBevelBorder());
        passwordfieldSignUp.setForeground(LABEL_COLOR);
        passwordfieldSignUp.setCaretColor(LABEL_COLOR);

        namelable = new JLabel("Name :");
        namelable.setFont(new Font(null, Font.BOLD, 20));
        namelable.setForeground(LABEL_COLOR);
        namelable.setBounds(10, 0, 100, 100);

        usernamelable = new JLabel("Username :");
        usernamelable.setFont(new Font(null, Font.BOLD, 20));
        usernamelable.setForeground(LABEL_COLOR);
        usernamelable.setBounds(10, 75, 150, 100);

        biolable = new JLabel("Bio :");
        biolable.setFont(new Font(null, Font.BOLD, 20));
        biolable.setForeground(LABEL_COLOR);
        biolable.setBounds(10, 155, 150, 100);

        emailLabel2 = new JLabel("Email :");
        emailLabel2.setFont(new Font(null, Font.BOLD, 20));
        emailLabel2.setForeground(LABEL_COLOR);
        emailLabel2.setBounds(10, 235, 100, 100);

        birthLable = new JLabel("Birth date :");
        birthLable.setFont(new Font(null, Font.BOLD, 20));
        birthLable.setForeground(LABEL_COLOR);
        birthLable.setBounds(10, 315, 150, 100);

        Phonelable = new JLabel("Phone number:");
        Phonelable.setFont(new Font(null, Font.BOLD, 20));
        Phonelable.setForeground(LABEL_COLOR);
        Phonelable.setBounds(3, 395, 170, 100);

        passwordLable2 = new JLabel("Password :");
        passwordLable2.setFont(new Font(null, Font.BOLD, 20));
        passwordLable2.setForeground(LABEL_COLOR);
        passwordLable2.setBounds(10, 475, 130, 100);

        signUpButton = new JButton();
        signUpButton.addActionListener(this);
        signUpButton.setText("Sign up");
        signUpButton.setFocusable(false);
        signUpButton.setFont(new Font(null, Font.BOLD, 20));
        signUpButton.setBackground(PRIMARY_COLOR);
        signUpButton.setBorder(BorderFactory.createRaisedBevelBorder());
        signUpButton.setBounds(230, 600, 100, 40);


        returnButton = new JButton();
        returnButton.addActionListener(this);
        returnButton.setText("Return");
        returnButton.setFocusable(false);
        returnButton.setFont(new Font(null, Font.BOLD, 20));
        returnButton.setBackground(PRIMARY_COLOR);
        returnButton.setBorder(BorderFactory.createRaisedBevelBorder());
        returnButton.setBounds(10, 700, 100, 50);


        hiddenPassword =new JButton();
        hiddenPassword.addActionListener(this);
        hiddenPassword.setIcon(hidePasswordpic);
        hiddenPassword.setFocusable(false);
        hiddenPassword.setBorder(BorderFactory.createRaisedBevelBorder());
        hiddenPassword.setBounds(435, 512, 25, 25);










        this.add(returnButton);
        this.add(hiddenPassword);
        this.add(signUpButton);
        this.add(emailLabel2);
        this.add(Phonelable);
        this.add(passwordLable2);
        this.add(birthLable);
        this.add(usernamelable);
        this.add(namelable);
        this.add(biolable);
        this.add(namefield);
        this.add(usernamefield);
        this.add(emailField);
        this.add(birhtdateField);
        this.add(phoneNumberField);
        this.add(bioField);
        this.add(passwordfieldSignUp);
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
