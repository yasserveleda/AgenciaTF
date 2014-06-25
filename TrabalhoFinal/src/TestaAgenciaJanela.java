/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import javax.swing.*;


public class TestaAgenciaJanela extends JFrame{

    public TestaAgenciaJanela() {
        super("Agência Bancária");

        this.setBounds(10, 100, 200, 20);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(2, 2);
        JPanel painel = new JPanel();
        painel.setLayout(grid);

        painel.add(new JLabel(""));
        painel.add(new JTextField(10));

        this.setContentPane(painel);
        this.setVisible(true);  // Exibe a janela
    }

    public static void main(String args[]) {
        TestaAgenciaJanela t = new TestaAgenciaJanela();
    }
    
}
