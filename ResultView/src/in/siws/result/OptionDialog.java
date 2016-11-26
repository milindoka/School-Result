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
import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class OptionDialog extends JDialog implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnModList = null;
	private JButton btnStatistics = null;
	private JButton btnMeritList = null;
	private JButton btnSubMerit = null;
	private JButton btnConsolidated = null;
	private JButton btnMTnames = null;
	private JButton btnDelVac = null;
	
	private JButton btnFList = null;
	private JButton btnCardsPdf = null;
	private JButton btnSaral = null;
	private JButton btnReserved3 = null;
	private JButton btnReserved4 = null;
	private JButton btnReserved5 = null;
	private JButton btnReserved6 = null;
	private JButton btnReserved7 = null;
	private JButton btnReserved8 = null;
	
	
	
    private String answer = "?";
    public String getAnswer() { return answer; }

    JPanel btnPanel = new JPanel(new GridLayout(4,16));
    final Font BTN_FONT = new Font("Times New Roman", Font.PLAIN, 16);
////button matrix


    
    
    public OptionDialog(JFrame frame, boolean modal, String myMessage) 
  {
   	 super(frame, modal);
    	this.setTitle("Select Option");
    btnModList = new JButton("Mod List"); btnModList.setFont(BTN_FONT); btnPanel.add(btnModList);	btnModList.addActionListener(this);
    btnStatistics = new JButton("Statistics"); btnStatistics.setFont(BTN_FONT); btnPanel.add(btnStatistics);btnStatistics.addActionListener(this);
    btnMeritList = new JButton("Merit List"); btnMeritList.setFont(BTN_FONT); btnPanel.add(btnMeritList);btnMeritList.addActionListener(this);
    btnSubMerit = new JButton("Sub Merit"); btnSubMerit.setFont(BTN_FONT); btnPanel.add(btnSubMerit);btnSubMerit.addActionListener(this);
    btnConsolidated = new JButton("Consolidated"); btnConsolidated.setFont(BTN_FONT); btnPanel.add(btnConsolidated);btnConsolidated.addActionListener(this);	
    btnMTnames = new JButton("Empty Names"); btnMTnames.setFont(BTN_FONT); btnPanel.add(btnMTnames);btnMTnames.addActionListener(this);
    btnDelVac = new JButton("Del Vacants"); btnDelVac.setFont(BTN_FONT); btnPanel.add(btnDelVac);btnDelVac.addActionListener(this);
    	
    btnFList = new JButton("Failures"); btnFList.setFont(BTN_FONT); btnPanel.add(btnFList);btnFList.addActionListener(this);
    btnCardsPdf = new JButton("Cards Pdf"); btnCardsPdf.setFont(BTN_FONT); btnPanel.add(btnCardsPdf);btnCardsPdf.addActionListener(this);
    btnSaral = new JButton("Saral"); btnSaral.setFont(BTN_FONT); btnPanel.add(btnSaral);btnSaral.addActionListener(this);
    btnReserved3 = new JButton("Reserved3"); btnReserved3.setFont(BTN_FONT); btnPanel.add(btnReserved3);btnReserved3.addActionListener(this);
    btnReserved4 = new JButton("Reserved4"); btnReserved4.setFont(BTN_FONT); btnPanel.add(btnReserved4);btnReserved4.addActionListener(this);
    btnReserved5 = new JButton("Reserved5"); btnReserved5.setFont(BTN_FONT); btnPanel.add(btnReserved5);btnReserved5.addActionListener(this);
    btnReserved6 = new JButton("Reserved6"); btnReserved6.setFont(BTN_FONT); btnPanel.add(btnReserved6);btnReserved6.addActionListener(this);
    btnReserved7 = new JButton("Reserved7"); btnReserved7.setFont(BTN_FONT); btnPanel.add(btnReserved7);btnReserved7.addActionListener(this);
    btnReserved8 = new JButton("Reserved8"); btnReserved8.setFont(BTN_FONT); btnPanel.add(btnReserved8);btnReserved8.addActionListener(this);
    
    	getContentPane().add(btnPanel, BorderLayout.CENTER);
    	
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {	if(btnModList == e.getSource()) { answer = "ModList"; setVisible(false); }
    	if(btnStatistics == e.getSource()) { answer = "Statistics"; setVisible(false); }
    	if(btnMeritList == e.getSource()) { answer = "MeritList"; setVisible(false); }
    	if(btnSubMerit == e.getSource()) { answer = "SubMerit"; setVisible(false); }
    	if(btnConsolidated == e.getSource()) { answer = "Consolidated"; setVisible(false); }
    	if(btnMTnames == e.getSource()) { answer = "MTnames"; setVisible(false); }
    	if(btnDelVac == e.getSource()) { answer = "DelVac"; setVisible(false); }
    	if(btnFList == e.getSource()) { answer = "FList"; setVisible(false); }
    	if(btnCardsPdf == e.getSource()) { answer = "CardsPdf"; setVisible(false); }
    	if(btnSaral == e.getSource()) { answer = "Saral"; setVisible(false); }
    	if(btnReserved3 == e.getSource()) { answer = "Reserved3"; setVisible(false); }
    	if(btnReserved4 == e.getSource()) { answer = "Reserved4"; setVisible(false); }
    	if(btnReserved5 == e.getSource()) { answer = "Reserved5"; setVisible(false); }
    	if(btnReserved6 == e.getSource()) { answer = "Reserved6"; setVisible(false); }
    	if(btnReserved7 == e.getSource()) { answer = "Reserved7"; setVisible(false); }
    	if(btnReserved8 == e.getSource()) { answer = "Reserved8"; setVisible(false); }
     }
    
}