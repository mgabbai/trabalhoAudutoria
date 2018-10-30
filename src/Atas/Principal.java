package Atas;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import telas.Login;

public class Principal {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                   Login inicia = new Login();
                   inicia.setPreferredSize(new Dimension(450, 330));
                   inicia.pack();
                   inicia.setLocationRelativeTo(null);
                   inicia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   inicia.setVisible(true);
                  } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}

}
