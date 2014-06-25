
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author naise.reginatto
 */
public class Janela extends JPanel implements ItemListener {

    private JCheckBox atendimentoButton;
    private JCheckBox criarContaButton;
    private JCheckBox pagamentoButton;
    private StringBuffer choices;
    private JLabel pictureLabel;

    public Janela() {
        super(new BorderLayout());

        //Create the check boxes.
        atendimentoButton = new JCheckBox("Atendimento");
        atendimentoButton.setMnemonic(KeyEvent.VK_C);
        atendimentoButton.setSelected(true);

        criarContaButton = new JCheckBox("Cadastro de nova conta");
        criarContaButton.setMnemonic(KeyEvent.VK_G);
        criarContaButton.setSelected(true);

        pagamentoButton = new JCheckBox("Pagamento de faturas");
        pagamentoButton.setMnemonic(KeyEvent.VK_H);
        pagamentoButton.setSelected(true);


        //Register a listener for the check boxes.
        atendimentoButton.addItemListener(this);
        criarContaButton.addItemListener(this);
        pagamentoButton.addItemListener(this);

        //Indicates what's on the geek.
        choices = new StringBuffer("cght");




        //Put the check boxes in a column in a panel
        JPanel checkPanel = new JPanel(new GridLayout(0, 1));
        checkPanel.add(atendimentoButton);
        checkPanel.add(criarContaButton);
        checkPanel.add(pagamentoButton);

        add(checkPanel, BorderLayout.LINE_START);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    /**
     * Listens to the check boxes.
     */
    public void itemStateChanged(ItemEvent e) {
        int index = 0;
        char c = '-';
        Object source = e.getItemSelectable();

        if (source == atendimentoButton) {
            index = 0;
            c = 'c';
        } else if (source == criarContaButton) {
            index = 1;
            c = 'g';
        } else if (source == pagamentoButton) {
            index = 2;
            c = 'h';
        } 
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Informe o serviço que deseja");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new Janela();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }

    
    
}
