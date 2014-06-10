/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdrachtvier;

import java.awt.*;
import java.awt.event.*;
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
        frm.setSize(400, 200);
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
                p.remove(stud);
                p.remove(klas);

                DatabaseSecure db = new DatabaseSecure("student");

            }
        });

        klas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DatabaseSecure db = new DatabaseSecure("guest");
                try {
                    String query = "SELECT klas_naam FROM klas";
                    ResultSet rs1 = db.stmt.executeQuery(query);

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

                                String query1 = "SELECT * FROM klasstud WHERE klas_naam = '" + classes.getSelectedItem().toString() + "'";
                                ResultSet rs2 = db2.stmt.executeQuery(query1);
                                
                                Panel p3 = new Panel();
                                
                                p3.setLayout(new GridLayout(0,1));

                                while (rs2.next()) {
                                    Label lbl1 = new Label("---------------------------------------------------------");
                                    Label lbl2 = new Label("Student id: " + rs2.getString("stu_id") + "\r\n");
                                    Label lbl3 = new Label("Naam: " + rs2.getString("stu_id") + "\r\n");
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
