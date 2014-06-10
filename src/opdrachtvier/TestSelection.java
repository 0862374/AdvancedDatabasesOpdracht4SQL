/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package opdrachtvier;


import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author AbsentCrisisX
 */
public class TestSelection {
    
    public TestSelection(){
        Frame frm = new Frame("Select Database form"); // Create the frame for the form
        frm.setSize(150,200); // Set the size of the frame
        frm.setVisible(true); // Define if the frame should be visible
        frm.addWindowListener(new WindowAdapter(){ // Make sure the window can be closed by the cross button
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        
        Panel p = new Panel();
        
        Button secBut = new Button();
        secBut.setLabel("Beveiligde database");
        
        secBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e ){
                new BeveiligdeApp();
                frm.setVisible(false);
            }
        });
        
        Button unsBut = new Button();
        unsBut.setLabel("Onbeveiligde database");
        
        unsBut.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                new OnbeveilingdeApp();
                frm.setVisible(false);
            }
        });
        
        p.setLayout(new GridLayout(0,1));
        
        p.add(secBut);
        p.add(unsBut);
        
        frm.add(p, BorderLayout.NORTH);
    }
    
}
