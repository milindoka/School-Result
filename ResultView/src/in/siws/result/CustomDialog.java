package in.siws.result;
// Fri Oct 25 18:07:43 EST 2004
//
// Written by Sean R. Owens, sean at guild dot net, released to the
// public domain.  Share and enjoy.  Since some people argue that it is
// impossible to release software to the public domain, you are also free
// to use this code under any version of the GPL, LPGL, or BSD licenses,
// or contact me for use of another license.
// http://darksleep.com/player

// A very simple custom dialog that takes a string as a parameter,
// displays it in a JLabel, along with two Jbuttons, one labeled Yes,
// and one labeled No, and waits for the user to click one of them.

import javax.swing.JDialog; 

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class CustomDialog extends JDialog implements ActionListener {
    private JPanel myPanel = null;
    private JButton yesButton = null;
    private JButton noButton = null;
    private boolean answer = false;
    public boolean getAnswer() { return answer; }

    JPanel btnPanel = new JPanel(new GridLayout(4,25));
    final Font BTN_FONT = new Font(Font.MONOSPACED, Font.BOLD, 20);
////button matrix


    
    
    public CustomDialog(JFrame frame, boolean modal, String myMessage) 
    {
    	 super(frame, modal);
    	
    	
    	
    	final JButton btnP = new JButton("Help"); btnP.setFont(BTN_FONT); btnPanel.add(btnP);
    	final JButton btn7 = new JButton("7"); btn7.setFont(BTN_FONT); btnPanel.add(btn7);
    	final JButton btn8 = new JButton("8"); btn8.setFont(BTN_FONT); btnPanel.add(btn8);
    	final JButton btn9 = new JButton("9"); btn9.setFont(BTN_FONT); btnPanel.add(btn9);
    	//JButton btnBS = new JButton("<html>&nbsp;Back<p>Space</html>"); btnBS.setFont(BTN_FONT_SMALL); btnPanel.add(btnBS);
    	final JButton btnQN = new JButton("VA"); btnQN.setFont(BTN_FONT); btnPanel.add(btnQN);

    	final JButton btnQ = new JButton(" "); btnQ.setFont(BTN_FONT); btnPanel.add(btnQ);
    	final JButton btn4 = new JButton("4"); btn4.setFont(BTN_FONT); btnPanel.add(btn4);
    	final JButton btn5 = new JButton("5"); btn5.setFont(BTN_FONT); btnPanel.add(btn5);
    	final JButton btn6 = new JButton("6"); btn6.setFont(BTN_FONT); btnPanel.add(btn6);
    	JButton btnBS = new JButton("X"); btnBS.setFont(BTN_FONT); btnPanel.add(btnBS);


    	final JButton btnR = new JButton(" "); btnR.setFont(BTN_FONT); btnPanel.add(btnR);
    	final JButton btn1 = new JButton("1"); btn1.setFont(BTN_FONT); btnPanel.add(btn1);
    	final JButton btn2 = new JButton("2"); btn2.setFont(BTN_FONT); btnPanel.add(btn2);
    	final JButton btn3 = new JButton("3"); btn3.setFont(BTN_FONT); btnPanel.add(btn3);

    	final JButton btnUP = new JButton("UP"); btnUP.setFont(BTN_FONT); btnPanel.add(btnUP);

    	final JButton btnS = new JButton(" "); btnS.setFont(BTN_FONT); btnPanel.add(btnS);
    	final JButton btn0 = new JButton("0"); btn0.setFont(BTN_FONT); btnPanel.add(btn0);
    	final JButton btnAB = new JButton("AB"); btnAB.setFont(BTN_FONT); btnPanel.add(btnAB);
    	final JButton btn100 = new JButton("100"); btn100.setFont(BTN_FONT); btnPanel.add(btn100);
    	final JButton btnDN = new JButton("DN"); btnDN.setFont(BTN_FONT); btnPanel.add(btnDN);

    	
    	getContentPane().add(btnPanel, BorderLayout.CENTER);

    	
       /*
        myPanel = new JPanel();
        getContentPane().add(myPanel);
        myPanel.add(new JLabel(myMessage));
        yesButton = new JButton("Yes");
        yesButton.addActionListener(this);
        myPanel.add(yesButton); 
        noButton = new JButton("No");
        noButton.addActionListener(this);
        myPanel.add(noButton);  
        
        */
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(yesButton == e.getSource()) {
            System.err.println("User chose yes.");
            answer = true;
            setVisible(false);
        }
        else if(noButton == e.getSource()) {
            System.err.println("User chose no.");
            answer = false;
            setVisible(false);
        }
    }
    
}