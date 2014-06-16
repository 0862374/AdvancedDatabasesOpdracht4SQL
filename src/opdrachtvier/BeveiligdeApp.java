/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdrachtvier;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

/**
 *
 * @author AbsentCrisisX
 */
public class BeveiligdeApp {

    public BeveiligdeApp() {
        Frame frm = new Frame("Choose action");
        frm.setVisible(true);
        frm.setSize(400, 100);
        frm.addWindowListener(new WindowAdapter() { // Make sure the window can be closed by the cross button
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Panel p = new Panel();

        Button stud = new Button();
        Button klas = new Button();

        stud.setLabel("Haal student gegevens op (met student login)");
        klas.setLabel("Haal alle studenten uit een klas op");

        stud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Panel p2 = new Panel();

                Label usr = new Label("Username: ");
                TextField usrnm = new TextField();

                Label pwd = new Label("Password: ");
                TextField pwdf = new TextField();
                pwdf.setEchoChar('*');

                Button search = new Button();
                search.setLabel("Zoek de gegevens");
                search.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent f) {
                        String user = usrnm.getText();
                        String pass = pwdf.getText();

                        DatabaseSecure db = new DatabaseSecure("student");
                        try {
                            String getUsr = "SELECT COUNT(*)  FROM usr WHERE un = ? AND ww = ?";
                            System.out.println(getUsr);
                            PreparedStatement ps = db.conn.prepareStatement(getUsr);
                            
                            ps.setString(1, user);
                            ps.setString(2, pass);
                            ResultSet rs = ps.executeQuery();

                            Panel p3 = new Panel();
                            p3.setLayout(new GridLayout(0, 1));

                            while (rs.next()) {
                                switch (rs.getInt("count")) {
                                    case 1:
                                        String getInfo = "SELECT s.stu_id, s.stu_naam, k.klas_naam, s.stu_ingeschreven FROM stud AS s LEFT JOIN klas AS k ON s.stu_klas_id = k.klas_id WHERE s.stu_naam = ? AND s.stu_ww = ?";
                                        System.out.println(getInfo);
                                        PreparedStatement ps2 = db.conn.prepareStatement(getInfo);

                                        ps2.setString(1, user);
                                        ps2.setString(2, pass);

                                        ResultSet rs2 = ps2.executeQuery();
                                        while (rs2.next()) {
                                            Label lbl1 = new Label("---------------------------------------------------------");
                                            Label lbl2 = new Label("Student id: " + rs2.getString("stu_id") + "\r\n");
                                            Label lbl3 = new Label("Naam: " + rs2.getString("stu_naam") + "\r\n");
                                            Label lbl4 = new Label("Klas: " + rs2.getString("klas_naam") + "\r\n");
                                            Label lbl5 = new Label("Ingeschreven: " + rs2.getString("stu_ingeschreven") + "\r\n");
                                            Label lbl6 = new Label("---------------------------------------------------------");

                                            p3.add(lbl1);
                                            p3.add(lbl2);
                                            p3.add(lbl3);
                                            p3.add(lbl4);
                                            p3.add(lbl5);
                                            p3.add(lbl6);
                                        }
                                        break;
                                    default:
                                        Label lbln = new Label("Student niet gevonden.");
                                        p3.add(lbln);
                                        frm.setSize(200, 100);
                                        break;
                                }
                            }

                            frm.remove(p2);
                            frm.add(p3);

                        } catch (SQLException o) {

                        }
                    }
                });

                p2.setLayout(new GridLayout(0, 2, 4, 4));

                p2.add(usr);
                p2.add(usrnm);
                p2.add(pwd);
                p2.add(pwdf);
                p2.add(search);

                frm.remove(p);
                frm.add(p2);
                frm.setSize(400, 150);

            }
        });

        klas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DatabaseSecure db = new DatabaseSecure("guest");
                try {
                    String query = "SELECT klas_naam FROM klas";
                    PreparedStatement ps1 = db.conn.prepareStatement(query);
                    ResultSet rs1 = ps1.executeQuery();

                    Panel p2 = new Panel();

                    JComboBox classes = new JComboBox();

                    while (rs1.next()) {
                        System.out.println("Er is een klas gevonden: " + rs1.getString("klas_naam"));
                        classes.addItem(rs1.getString("klas_naam"));
                    }

                    Button studclass = new Button();
                    studclass.setLabel("Haal studenten op");

                    studclass.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent f) {
                            try {
                                DatabaseSecure db2 = new DatabaseSecure("guest");

                                String query1 = "SELECT * FROM klasstud WHERE klas_naam = ?";
                                PreparedStatement ps2 = db2.conn.prepareStatement(query1);

                                ps2.setString(1, classes.getSelectedItem().toString());

                                ResultSet rs2 = ps2.executeQuery();

                                Panel p3 = new Panel();

                                p3.setLayout(new GridLayout(0, 1));

                                while (rs2.next()) {
                                    Label lbl1 = new Label("---------------------------------------------------------");
                                    Label lbl2 = new Label("Student id: " + rs2.getString("stu_id") + "\r\n");
                                    Label lbl3 = new Label("Naam: " + rs2.getString("stu_naam") + "\r\n");
                                    Label lbl4 = new Label("Klas: " + rs2.getString("klas_naam") + "\r\n");
                                    Label lbl5 = new Label("Ingeschreven: " + rs2.getString("stu_ingeschreven") + "\r\n");

                                    p3.add(lbl1);
                                    p3.add(lbl2);
                                    p3.add(lbl3);
                                    p3.add(lbl4);
                                    p3.add(lbl5);
                                }

                                frm.remove(p2);
                                frm.add(p3);
                            } catch (SQLException o) {
                                o.printStackTrace();
                            }
                        }
                    });

                    p2.setLayout(new GridLayout(0, 1));

                    p2.add(classes);
                    p2.add(studclass);

                    frm.remove(p);
                    frm.add(p2, BorderLayout.NORTH);
                    db.conn.close();
                } catch (SQLException o) {
                    o.printStackTrace();
                }
            }
        });

        p.setLayout(new GridLayout(0, 1));

        p.add(stud);
        p.add(klas);

        frm.add(p, BorderLayout.NORTH);

    }

}
