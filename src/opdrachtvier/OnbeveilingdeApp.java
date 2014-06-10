package opdrachtvier;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class OnbeveilingdeApp {

    JFrame loginScreen;
    JFrame funOneScreen;
    JFrame funTwoScreen;
    JTextField username;
    JTextField password;
    JTextField klasname;

    public OnbeveilingdeApp() {
    	// make a login screen of a unsecured application
        loginScreen = new JFrame("Onbeveiligde app");
        loginScreen.setLayout(new GridLayout(0, 1));

        loginScreen.setVisible(true);
        loginScreen.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        paintLogin();
        loginScreen.pack();
        loginScreen.repaint();

    }

    public void paintLogin() {
    	// paint some textfields on the screen and a log in button
        username = new JTextField(10);
        password = new JTextField(10);
        JButton btnlogin = new JButton();

        loginScreen.add(new JLabel("Username:"));
        loginScreen.add(username);
        loginScreen.add(new JLabel("Password:"));
        loginScreen.add(password);
        loginScreen.add(btnlogin);

        loginScreen.add(new JLabel());

        btnlogin.setText("Log in");

        btnlogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // if pressed on the button call the checklogin function
                checkLogin();
            }
        });
    }

    protected void checkLogin() {
        // TODO Auto-generated method stub
        DatabaseMy db = new DatabaseMy();
// make connection to the mysql database
        try {
        	// sql that checks the username and password of the user
            String sql = "SELECT * FROM studenten INNER JOIN klassen ON stu_klas_id=klas_id WHERE stu_id = '"
                    + username.getText() + "' AND stu_ww = '" + password.getText() + "'";
            System.out.println(sql);
            ResultSet rs = db.stmt.executeQuery(sql);
            int rows = 0;
            while (rs.next()) {
                if (rows == 0) {
                	// if found a correct username password combination start functionality one and two
                    funOneScreen = new JFrame("Functionaliteit een");
                    funOneScreen.setLayout(new GridLayout(0, 1));
                    funOneScreen.setVisible(true);
                    funOneScreen.add(new JLabel("ID, Naam, Klas, Ingeschreven"));

                    funTwoScreen = new JFrame("Functionaliteit twee");
                    funTwoScreen.setLayout(new GridLayout(0, 1));
                    funTwoScreen.setVisible(true);
                    funTwoScreen.add(new JLabel("Klasnaam: "));
                    klasname = new JTextField(10);
                    funTwoScreen.add(klasname);
                    JButton btnklas = new JButton();
                    btnklas.setText("Zoek klassen op");
                    funTwoScreen.add(btnklas);
                    btnklas.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            // TODO Auto-generated method stub
                            getKlassen();
                        }
                    });
                }
                rows++;

                funOneScreen.add(new JLabel(rs.getString("stu_id") + ", " + rs.getString("stu_naam") + ", "
                        + rs.getString("klas_naam") + ", " + rs.getString("stu_ingeschreven")));

                funOneScreen.pack();
                funOneScreen.repaint();
                funTwoScreen.pack();
                funTwoScreen.repaint();

            }
            System.out.println("Number of rows found: " + rows);
            if (rows == 0) {
                loginScreen.add(new JLabel("Username and password is not correct."));
                loginScreen.pack();
                loginScreen.repaint();
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getKlassen() {
        DatabaseMy db = new DatabaseMy();
        // show all students of the inserted class
        try {
            String sql = "SELECT * FROM klassen INNER JOIN studenten ON stu_klas_id=klas_id WHERE stu_ingeschreven='Ja' AND klas_naam = '"
                    + klasname.getText() + "'";
            System.out.println(sql);
            ResultSet rs;

            rs = db.stmt.executeQuery(sql);
            while (rs.next()) {
                funTwoScreen.add(new JLabel(rs.getString("stu_id") + ", " + rs.getString("stu_naam") + ", "
                        + rs.getString("klas_naam") + ", " + rs.getString("stu_ingeschreven")));

                funTwoScreen.pack();
                funTwoScreen.repaint();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
