
/*
 * Author:  Milind Oka (oak444@gmail.com)
 * License: Public Domain
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS, IMPLIED OR OTHERWISE, INCLUDING WITHOUT LIMITATION, ANY
 * WARRANTY OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import javax.swing.text.MaskFormatter;
import javax.swing.ImageIcon;

////for toast

/*
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

*/

public class MarkList
{	
    public static void main(String[] args)
    {   SwingUtilities.invokeLater(new Runnable()
        {
            public void run() 
            {   
                new ProgrammaticTableScrollFrame().setVisible(true);
              
                
            }
        });
    }
}


/*
class AndroidLikeToast extends JDialog {

    String msg;
    JFrame frame;
    // you can set the time for how long the dialog will be displayed
    public static final int LENGTH_LONG = 5000;
    public static final int LENGTH_SHORT = 2000;

    public AndroidLikeToast(JFrame frame, boolean modal, String msg) {
        super(frame, modal);
        this.msg = msg;
        this.frame=frame;
        initComponents();        
    }

    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        addComponentListener(new ComponentAdapter() {
            // Give the window an rounded rect shape.

            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
            }
        });
        setUndecorated(true);
        setSize(300, 50);
        setLocationRelativeTo(frame);// adding toast to frame or use null
        getContentPane().setBackground(Color.BLACK);
        
        // Determine what the GraphicsDevice can support.
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        final boolean isTranslucencySupported =
                gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT);

        //If shaped windows aren't supported, exit.
        if (!gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT)) {
            System.err.println("Shaped windows are not supported");
        }

        //If translucent windows are supported, 
        //create an opaque window.
        // Set the window to 50% translucency, if supported.
        if (isTranslucencySupported) {
            setOpacity(0.5f);
        } else {
            System.out.println("Translucency is not supported.");
        }

        JLabel label = new JLabel();
        label.setForeground(Color.WHITE);
        label.setText(msg);
        add(label);
    }
}



*/

//////////////////end of toast class






class ProgrammaticTableScrollFrame extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField FrollField;
    private TextField FClas = new TextField("");
    private TextField FDivision = new TextField("");
    private TextField FStream = new TextField("");    
    private TextField FSubject = new TextField("");
    private TextField FMaxMarks = new TextField("100");
    private TextField FExamination = new TextField("");
    private TextField FExaminer = new TextField("");
    private TextField FDate = new TextField("");
    
    private JTextField LrollField;
    private JButton createButton;
    private JButton scrollButton;
    private JButton addButton;
    private JButton openButton;
    private JButton insertAboveButton;
    private JButton saveButton;
    private JButton saveAsButton;
    private JButton help1Button;
    private JButton printButton;
    private JButton insertBelowButton;
    private JButton setCollegeButton;
    private JButton statButton;
    private JButton CleanButton;
    private JButton delrowButton;
    
    
    
    
    private String CollegeName1="SIWS College, Wadala ";
    private String CollegeName2=" ";
    private String CollegeName3=" ";
    private String setNObuff="";
    final JTextField Line1 = new JTextField(10);
    final JTextField Line2 = new JTextField(10);
    final JTextField Line3 = new JTextField(10);
    
    private boolean KeyPadDisplayed=false;
   
    private int KeyPadMode=0;  ///0-Normal Marks Entry Mode, 1-MCQ Mode, 2-AnsKey Entry Mode
    private int rowheight;
    String inn="";
    static int curRow=1;
	static int curCol=0;
	boolean dialogcreated=false;
	JDialog dialog = new JDialog();
    int MCQmarks=0;
	private static final int TL = 20;
	int CurLis=0;
	int CurSet=0;
	///int LoadedListCount=1;
	/////MarkList Details
	public static String[] Examiner = new String[TL];
	public static String[] Clas = new String[TL];
	public static String[] Division = new String[TL];
	public static String[] Stream = new String[TL];
	public static String[] Subject = new String[TL];
	public static String[] Examination = new String[TL];
	public static String[] MaxMarks = new String[TL];
	public static String[] Date = new String[TL];
	public static String[] FilePath = new String[TL];
	public static String[] FileTitle = new String[TL];
	public static int[] TotalSets = new int[TL];
	
	public static boolean[] bModified = new boolean[TL];
	public static boolean[] bSaveDirect = new boolean[TL];
	public static boolean[] bFileLoaded = new boolean[TL];
	public String FirstRoll,LastRoll;
	public String[][] Set = new String[TL][100];
	public String[][] Key = new String[TL][100];
    
	public void DisplayNumPad() 
	{KeyPadDisplayed=true;
if(!dialogcreated)  DInit();		
Line2.setText(Set[CurLis][CurSet]);
Line3.setText(Key[CurLis][CurSet]);

int temprow=table.getSelectedRow();
if(temprow!=-1) curRow=temprow;
int tempcol=table.getSelectedColumn();
if(tempcol!=-1) curCol=tempcol;

Rectangle r = table.getCellRect(curRow, curCol, true);  
Point p = new Point(r.x+ r.width, r.y-r.height );  // lower left of cell  
SwingUtilities.convertPointToScreen(p, table);  

dialog.setLocation(p);
dialog.setVisible(true);
}
	
    public ProgrammaticTableScrollFrame() {
        super("MarkList 01 - Untitled");
       /* 
        BufferedImage image = null;
        try {
            image = ImageIO.read(
                this.getClass().getResource("MarkList.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setIconImage(image);
        
        */
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
       
        //setSize(550, 350);
        final String[][] data = new String[120][2];
        String[] col= new String[2];
        
        String det = new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime());
        
        for(int i=0;i<TL;i++)
        {bModified[i]=false;
         bSaveDirect[i]=false;
         TotalSets[i]=1;
         FirstRoll="121";
         LastRoll="250";
         String ttt=String.format("MarkList - %02d - Untitled",i+1);
         FilePath[i]=ttt;
         
         Clas[i]=Examiner[i]=Division[i]=Stream[i]=Examination[i]=MaxMarks[i]="100";
         String tempSub=String.format("SUB-%02d",i+1);
         Subject[i]=tempSub; FSubject.setText(Subject[CurLis]);
         Date[CurLis]=det;
        }
        
        for(int i=0;i<100;i++)
        	Set[CurLis][i]=Key[CurLis][i]="";
    
        
        // Read Default College Name
    	String appDir = getClass().getProtectionDomain().getCodeSource().getLocation().getPath(); 
      
        if(appDir.contains("MarkList.jar"))
  	     { int templen=appDir.length();  appDir=appDir.substring(0, templen-12);}
  	       appDir+="/CollegeName.txt";
      
  	       
  	       
  	     try{
  	    	  // Open the file that is the first 
  	    	  // command line parameter
  	    	  FileInputStream fstream = new FileInputStream(appDir);  ///full path
  	    	  // Get the object of DataInputStream
  	    	  DataInputStream in = new DataInputStream(fstream);
  	    	  BufferedReader br = new BufferedReader(new InputStreamReader(in));
  	    	  String strLine;
  	    	  //Read File Line By Line
  	    	  while ((strLine = br.readLine()) != null)   {
  	    	  // Print the content on the console
  	    	  //show(strLine);
  	    	  
  	    	  CollegeName1=strLine;
  	    	  }
  	    	  //Close the input stream
  	    	  in.close();
  	    	    }catch (Exception e){//Catch exception if any
  	    	  System.err.println("Error: " + e.getMessage());
  	    	  }
  	    	  
  	       
  	       
  	       
        
        
        
        this.addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {   
            	if(bModified[CurLis]==true)
            	{
                JFrame frame = (JFrame)e.getSource();
                int result = JOptionPane.showConfirmDialog(
                    frame,
                    "Marklist Modified, Exit Application Without Saving?",
                    "Exit Application",
                    JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION)
                	System.exit(0);
                else
                	//System.exit(DO_NOTHING_ON_CLOSE);
                 frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
               }
            	else
            	System.exit(0);
            		
        }
        });
        
        
        final DefaultTableModel model = new DefaultTableModel(data, col)
        {

            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        
        table = new JTable(model);
        //table.setAutoCreateColumnsFromModel(false);
    
        /*
        
        DefaultTableCellRenderer centerRenderer1 = new DefaultTableCellRenderer();
        centerRenderer1.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer1 );
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer1 );
        */
        
        table.setCellSelectionEnabled(true);
              
     for(int i=0;i<120;i++) { String temp=String.format("%d",i+121); 
                              SetData(temp,i,0);
                              SetData(" ",i,1);
                             } 
        
     
     
     /*
     
     for(int i=0;i<table.getColumnCount();i++)  
     {  
     TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(i);  
        
     column1.setHeaderValue(header[i]);  
     }   
        		  
       */ 		  
        		  
     TableColumn column0 = table.getTableHeader().getColumnModel().getColumn(0); 		  
     column0.setHeaderValue("ROLL");
        		  
     TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(1); 		  
     column1.setHeaderValue("SUB-01");     
     
     
     
     
     scrollPane = new JScrollPane(table);
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(8,8));
        table.getTableHeader().setResizingAllowed(false);
        table.setFont(new Font("COURIER", Font.BOLD, 24));
/*
        for(int i=1;i<table.getColumnCount();i++)
        {
        table.getColumnModel().getColumn(i).setMinWidth(40);
        table.getColumnModel().getColumn(i).setMaxWidth(40);
        }
        
  */      
        FrollField = new JTextField(4);
        FrollField.setText("121");

        LrollField = new JTextField(4);
        LrollField.setText("240");

        
        FrollField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        
        LrollField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        
        
        
        //JPanel topmostPanel = new JPanel(new GridLayout(2,1,1,1));
        //topmostPanel.setBorder(new EmptyBorder( 8, 8, 8, 8 ));
        //topmostPanel.add(new JLabel("School/College :"));
        //topmostPanel.add(new JLabel("School/College :"));
        //topmostPanel.add(FCollegeNameField);
        
        
        JPanel topPanel = new JPanel(new GridLayout(4,1,8,5));
        topPanel.setBorder(new EmptyBorder( 8, 8, 8, 8 ));
        
        topPanel.add(new JLabel("First Roll"));
        topPanel.add(FrollField);
        
        
        topPanel.add(new JLabel("Class :"));
        FClas.addKeyListener(new KeyAdapter() {
	      public void keyPressed(KeyEvent e) {
	        if (Character.isLetter(e.getKeyChar()))
	        {
	          e.setModifiers(Event.SHIFT_MASK);
	        }
	      }
	    });
    	topPanel.add(FClas);

    	
    	topPanel.add(new JLabel("Div :"));
    	FDivision.addKeyListener(new KeyAdapter() {
	      public void keyPressed(KeyEvent e) {
	        if (Character.isLetter(e.getKeyChar()))
	        {
	          e.setModifiers(Event.SHIFT_MASK);
	        }
	      }
	    });
    	topPanel.add(FDivision);
        
    	topPanel.add(new JLabel("Last Roll"));
        topPanel.add(LrollField);
        
        topPanel.add(new JLabel("Stream :"));
        FStream.addKeyListener(new KeyAdapter() {
	      public void keyPressed(KeyEvent e) {
	        if (Character.isLetter(e.getKeyChar()))
	        {
	          e.setModifiers(Event.SHIFT_MASK);
	        }
	      }
	    });
        topPanel.add(FStream);
        
    	        
        topPanel.add(new JLabel("Subject :"));
        FSubject.addKeyListener(new KeyAdapter()
        {public void keyPressed(KeyEvent e) 
	      {char ch=e.getKeyChar();
        	if (Character.isLetter(ch))
        	{
	          e.setModifiers(Event.SHIFT_MASK);
	        }
        	
	      }
	    	public void keyReleased(KeyEvent e) 
	      { TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(CurLis+1);
	        char ch=e.getKeyChar();
	        String sch=String.valueOf(ch);
	       if( ch == KeyEvent.VK_BACK_SPACE) 
	       { column1.setHeaderValue(FSubject.getText().toUpperCase());
	         FSubject.validate();
	         Subject[CurLis]=FSubject.getText();
	         table.updateUI();
	       }
           if(ch>=32 && ch<=126) 
	       	column1.setHeaderValue(FSubject.getText());
            Subject[CurLis]=FSubject.getText();
	        table.updateUI();
	      }
	    });
        topPanel.add(FSubject);
        
        
        topPanel.add(new JLabel("Maximum Marks :"));
        FMaxMarks.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) 
                {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE)))
                     {
                       e.consume();
                     }
                
                
            }
        });

        topPanel.add(FMaxMarks);
        
        topPanel.add(new JLabel("Examination :"));
        
	    FExamination.addKeyListener(new KeyAdapter() {
	      public void keyPressed(KeyEvent e) {
	        if (Character.isLetter(e.getKeyChar())) 
	        {
	          e.setModifiers(Event.SHIFT_MASK);
	        }
	        
	      }
	    });
        topPanel.add(FExamination);
                

        topPanel.add(new JLabel("Examiner :"));
        
	    FExaminer.addKeyListener(new KeyAdapter() {
	      public void keyPressed(KeyEvent e) {
	        if (Character.isLetter(e.getKeyChar())) {
	          e.setModifiers(Event.SHIFT_MASK);
	        }
	      }
	    });
        topPanel.add(FExaminer);
        
        
        
        createButton = new JButton("Create List");
        topPanel.add(createButton);
        
        openButton = new JButton("Open List");
        topPanel.add(openButton);
        
        saveButton = new JButton("Save List");
        topPanel.add(saveButton);
        
        printButton = new JButton("Print List");
        topPanel.add(printButton);

        topPanel.add(new JLabel("Date :"));
        
	    FDate.addKeyListener(new KeyAdapter() {
	      public void keyPressed(KeyEvent e) {
	        if (Character.isLetter(e.getKeyChar())) {
	          e.setModifiers(Event.SHIFT_MASK);
	        }
	      }
	    });
        topPanel.add(FDate);

        
        
        scrollButton = new JButton("Scroll");
        addButton = new JButton("Add");
        
        insertAboveButton = new JButton("Ins Above");
        
        saveAsButton = new JButton("SaveAs");
        help1Button = new JButton("Help");
       
        insertBelowButton = new JButton("Ins Below");
        setCollegeButton = new JButton("School/College");
        statButton = new JButton("Stat");
        CleanButton = new JButton("Clean Marks");
        delrowButton = new JButton("Del Row");
        
        
        JPanel controlPanel = new JPanel(new GridLayout(2,1));
        controlPanel.setBorder(new EmptyBorder( 8, 8, 8, 8 ));
        
       
        controlPanel.add(scrollButton);
        controlPanel.add(addButton);
        
        controlPanel.add(insertAboveButton);
        
       
        controlPanel.add(saveAsButton);
        controlPanel.add(help1Button);
        controlPanel.add(insertBelowButton);
        
        
        controlPanel.add(setCollegeButton);
        controlPanel.add(statButton);
        controlPanel.add(CleanButton);
        controlPanel.add(delrowButton);
        
   //     getContentPane().add(topmostPanel, BorderLayout.EAST);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        pack();
        
        rowheight=table.getRowHeight();
        createButton.addActionListener(new ActionListener()
        { 
        	
            public void actionPerformed(ActionEvent e)
            { 
              int  froll = Integer.parseInt(FrollField.getText());
              int  lroll = Integer.parseInt(LrollField.getText());
             
              if(lroll<froll)
              { JOptionPane.showMessageDialog(null, "First roll exceeds last roll");return;}
              int strength=lroll-froll+1;
              if(strength<8) 
              { JOptionPane.showMessageDialog(null, "At least 8 rolls required");return;}
              if(strength>5000) 
              { JOptionPane.showMessageDialog(null, "At most 5000 rolls are allowed");return;}
              int totalroll=table.getRowCount();
              int difference=strength-totalroll;
              if(difference>0)
            	   for(int i=0;i<difference;i++) model.addRow(new Object[]{" "});
            	
            	  
              if(difference<0)
        	  { difference=-difference;
            	for(int i=0;i<difference;i++) model.removeRow(0);
        	   }
            	
              for(int i=0;i<strength;i++) 
    		  {String tt=String.format("%d",froll+i);
    		   SetData(tt,i,0);
    		   SetData(" ",i,1);
    		   bModified[CurLis]=true;
    		  }
            
              FirstRoll=String.format("%d", froll);
              LastRoll=String.format("%d", lroll);
              
          //    UpdateHeader();  
              
            }
            
          
            
        });
        
        table.getColumnModel().addColumnModelListener(new TableColumnModelListener()
        //table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {  
            
			@Override
			public void columnAdded(TableColumnModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void columnMarginChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void columnMoved(TableColumnModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void columnRemoved(TableColumnModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void columnSelectionChanged(ListSelectionEvent arg0) {

	              UpdateFromUser();
				   int cc=table.getSelectedColumn(); 
				   if(cc>0) CurLis=cc-1; else CurLis=0;
				
				   UpdateHeader();	
				
			}  
            });  
        //@th
        table.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e)
            {   int cc=table.getColumnCount();
            	curRow=table.getSelectedRow();
            	curCol=table.getSelectedColumn();
            	if(curCol==0) return;
            	      
            	if(cc>3 && curCol==cc-1) return;
            	String temp=(String) GetData(table,curRow,curCol);
            	if(temp.charAt(0)==' ') temp="";
            if(temp.length()>=2) temp="";
        if(e.getKeyChar()=='a') {temp="AB"; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='A') {temp="AB"; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='v') {temp="VA"; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='V') {temp="VA"; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='0') {temp+='0'; SetData(temp,curRow,curCol);}    	
        if(e.getKeyChar()=='1') {temp+='1'; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='2') {temp+='2'; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='3') {temp+='3'; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='4') {temp+='4'; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='5') {temp+='5'; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='6') {temp+='6'; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='7') {temp+='7'; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='8') {temp+='8'; SetData(temp,curRow,curCol);}
        if(e.getKeyChar()=='9') {temp+='9'; SetData(temp,curRow,curCol);}
        sum();
        if(temp.length()>=2) {  String Max=(String) FMaxMarks.getText();
                                int border;
                                if (Max.matches("[0-9]+") && Max.length() > 0) 
 		                        border=Integer.parseInt(Max); else border=0;
                                String tempmark=(String) GetData(table,curRow,curCol);
                                int imark=atoi(tempmark);
                                if(imark>border) { show("Marks Exceed Max marks !");
                                                   temp=" "; SetData(temp,curRow,curCol);
                                                  }
                                else
                                
        	                         slide();
                              }
            	
            }
        });
        
        
        
        
        table.addMouseListener(new MouseAdapter() {
        	  public void mouseClicked(MouseEvent e) 
        	  {if (e.getClickCount() == 1)
        	  {
        		   curRow = table.getSelectedRow();
       	           curCol = table.getSelectedColumn();
       	          
        	  }  
        	    if (e.getClickCount() == 2) 
        	    {
        	     int cc=table.getColumnCount();
        	     
        	      curRow = table.getSelectedRow();
        	       curCol = table.getSelectedColumn();
        	     
        	       if(cc>3 && curCol==cc-1) return;
        	       if(curCol==0)
        	       { String input = JOptionPane.showInputDialog(null, "Enter Roll:", "Edit Roll Dialog",
        	    	        JOptionPane.PLAIN_MESSAGE);
        	      
        	       if(input!=null) input = input.replaceAll("\\D+","");
        	       if(input.length()==0) return;
        	       int ttt=Integer.parseInt(input.replaceAll("[^0-9.]",""));
        	       input=String.format("%d", ttt);
        	       SetData(input,curRow,curCol);bModified[CurLis]=true;
        	        return;
        	       }
        	     
        	      if(KeyPadDisplayed) 
        			 {int temprow=table.getSelectedRow();
        				if(temprow!=-1) curRow=temprow;
        				int tempcol=table.getSelectedColumn();
        				if(tempcol!=-1) curCol=tempcol;
        	         Rectangle r = table.getCellRect(curRow, curCol, true);  
        	         Point p = new Point(r.x+ r.width, r.y-r.height );  // lower left of cell  
        	         SwingUtilities.convertPointToScreen(p, table);
        	         
        	         dialog.setLocation(p);
        	         	return;
        	        }
        	      
        	     DisplayNumPad(); 
        	      
        	    }
        	    
        	  }
        	});        
        
        openButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {  int cc=table.getColumnCount();
               if(cc>3 && CurLis==cc-2) return; ///DO NOT OPEN IN TOTAL COLUMN
               
               
               File f = new File(System.getProperty("java.class.path"));
           	File dir = f.getAbsoluteFile().getParentFile();
           	String path = dir.toString();
              
            	JFileChooser chooser = new JFileChooser();
                chooser.setMultiSelectionEnabled(true);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Mark List Files", "mrk");
                    chooser.setFileFilter(filter);
                    chooser.setCurrentDirectory(new File(path));
                
                int option = chooser.showOpenDialog(ProgrammaticTableScrollFrame.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                  File[] sf = chooser.getSelectedFiles();
                  String filelist = "nothing";
                  if (sf.length > 0) filelist = sf[0].getName();
                  for (int i = 1; i < sf.length; i++) 
                  {
                    filelist += ", " + sf[i].getName();
                  }
                  FilePath[CurLis]=sf[0].getPath();
                  bSaveDirect[CurLis]=true;
                 // if (!FilePath[CurLis].endsWith(".mrk")) FilePath[CurLis]+= ".mrk";
                  if(CurLis==0) ReadFromDisk(sf[0].getPath());
                  else ReadMarksOnly(sf[0].getPath());
                  AutoCloseMsg("File Loaded");
                }
                else {
                  show("You canceled.");
                }
            	
            	
            }
        });
        
        
        
        
        
        
        saveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                 if(bSaveDirect[CurLis])
                 {
                  WriteToDisk(FilePath[CurLis]);
                  bModified[CurLis]=false;
                }
                 else 
                	 SaveAsRoutine();
            }
        });
        
        insertAboveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	int row=table.getSelectedRow();
            	int col=table.getSelectedColumn();
            if(row>=0)
            	{  String temp=(String) GetData(table,row,0);
            	   int temproll=Integer.parseInt(temp.replaceAll("[^0-9.]",""));
            	   if(temproll>0)temproll--;
            	   temp=String.format("%d",temproll);
            	   if(row==0) FrollField.setText(temp);
            	   
            	   model.insertRow(row,new Object [] {
            	  temp," "," "," "," "," "," "," "," "," "," ",
            	  " "," "," "," "," "," "," "," "," "," "});
            	   table.changeSelection(row, col,false,false);
            	   bModified[CurLis]=true;
            	 }
            }
        });



        insertBelowButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	int row=table.getSelectedRow();
            	int col=table.getSelectedColumn();
            if(row>=0)
            	{  String temp=(String) GetData(table,row,0);
            	   int temproll=Integer.parseInt(temp.replaceAll("[^0-9.]",""));
            	   if(temproll>0)temproll++;
            	   
            	   temp=String.format("%d",temproll);
            	   
            	   if(row==table.getRowCount()-1) LrollField.setText(temp);
            	   
            	   model.insertRow(row+1,new Object [] {
                     	  temp," "," "," "," "," "," "," "," "," "," ",
                     	  " "," "," "," "," "," "," "," "," "," "});
            	   
            	   table.changeSelection(row+1, col,false,false);
            	   bModified[CurLis]=true;
            	 }
            }
        });

        
        
        
        saveAsButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	SaveAsRoutine();            	
            }
        });
        
        
        setCollegeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	
            	
            	CollegeName1 = JOptionPane.showInputDialog("Enter School/College Name:",CollegeName1).toUpperCase();
            	
            
            	String appDir = getClass().getProtectionDomain().getCodeSource().getLocation().getPath(); 
          	    
          	  FileWriter f0=null;
              
          	  if(appDir.contains("MarkList.jar"))
          	  {int templen=appDir.length();  appDir=appDir.substring(0, templen-12);}
          	  appDir+="/CollegeName.txt";
              //show(appDir);
             
              
          	 try {f0 = new FileWriter(appDir);	} catch (IOException e1) {e1.printStackTrace();	}
               String newLine = System.getProperty("line.separator");
         //      try { f0.write(newLine);	} catch (IOException e1) {e1.printStackTrace();	}
               try { f0.write(CollegeName1+ newLine);	} catch (IOException e1) {e1.printStackTrace();	}

          try {f0.close();} catch (IOException e1) {e1.printStackTrace();}

            }    
          	    
          	    
            
        });
        
        
        
        addButton.addActionListener(new ActionListener()
        {   public void actionPerformed(ActionEvent e)
            { 
        	
        	int cc=table.getColumnCount();
        	
        	if(cc==2)     /// No TOTAL Column yet
        	{
        ///	String ttt=String.format("TOTAL",cc);
        	
                	model.addColumn("TOTAL");
        	
        	cc=table.getColumnCount();
        	for(int i=0;i<table.getRowCount();i++)
            { 
            SetData(" ",i,cc-1);
           }
        	
          }
        	
        	model.addColumn("TOTAL");
           
           cc=table.getColumnCount();
           
           TableColumn column0 = table.getTableHeader().getColumnModel().getColumn(0); 		  
              column0.setHeaderValue("ROLL");
          
              
              for(int i=1;i<cc-1;i++)
               { TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(i); 		  
                 column1.setHeaderValue(Subject[i-1]);
               }
              String ttt=String.format("SUB-%02d",cc-2);
              
              Subject[cc-3]=ttt;
              Subject[cc-2]="TOTAL";
              column0 = table.getTableHeader().getColumnModel().getColumn(cc-2); 		  
              column0.setHeaderValue(ttt);
              
              for(int i=0;i<table.getRowCount();i++)
              { 
            	  String temp=(String) GetData(table,i,cc-2);
                  SetData(temp,i,cc-1);
                  SetData(" ",i,cc-2);
                  
             }
              
            
              
              cc=table.getColumnCount();
              if(cc==2) return;
              int rr=table.getRowCount();
              for(curRow=0;curRow<rr;curRow++)    sum();
             
            }
        
        
        });
        
        scrollButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {  ScrollToLine(scrollPane, table);
               
             }
        });
        
        /////help1Button
        help1Button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {     		 
            	String multiLineMsg[] = { "This free application creates and prints marklists for a class of students.",
            			                  "It is compatible with associated application on Android Mobile Phone.",
            			                  " ",
            			                  " 1. Fill First and Last Roll and other info in the top header fields. Then press [create].",
            			                  " 2. Click empty cell and enter marks or double click and enter via mouse touch mode.",
            			                  " 3. Enter School/College Name (only first time. It will be saved).",
            			                  " 4. Save Marklist. Then use [print] button to print it neatly in table form.",
            			                  " 5. Press [Add] to create additional column.",
            			                  " 6. Select any cell from additional column. Then [open] to load in this column.",
            			                  " 7. You can add several marklists and print them with rowwise totals.",
            			                  " 8. Double click on empty cell. Then switch to Ans Edit mode.",
            			                  " 9. Enter MCQ keys with marks. You can do it for multiple sets also.",
            			                  "10. Switch to MCQ assessment mode and assess your paper with ease and accuracy.",
            			                  "11. All these features are available on Marklist.apk (Andoid), except multiple",
            			                  "    file support and printing. Files created by Marklist (android) can be loaded",
            			                  "    and printed in this desktop application.",
            			                  " ",
            			                  
            			                  } ;
                JOptionPane.showMessageDialog (null, multiLineMsg,"Help",JOptionPane.PLAIN_MESSAGE);   
           
            }
                });
            	
       statButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            { 
            	
            	
                final Dialog d;

        		Frame window = new Frame();

        		// Create a modal dialog
        		d = new Dialog(window, "Alert", true);

        		// Use a flow layout
        		d.setLayout( new GridLayout(11,1) );

        		// Create an OK button
        		Button ok = new Button ("OK");
        		//Label ABEES= new Label("Absentees");
        		ok.addActionListener ( new ActionListener()
        		{
        			public void actionPerformed( ActionEvent e )
        			{
        				// Hide dialog
        				d.dispose();
        			}
        		});

        		
        		int ab=0,va=0,blank=0,net=0, pass=0,fail=0,zeros=0,border=0;
               	
        		
        		
        		
        		 String Max=(String) FMaxMarks.getText();
        		
        		 if (Max.matches("[0-9]+") && Max.length() > 0) 
        		   border=Integer.parseInt(Max); else border=0;
        		 
        	     border=border*35;
        	     int r=border%100;
        	     if(r>0)
        		 border=border/100 +1; else border=border/100;
  
        		int rowcount=table.getRowCount();
        		for(int i = 0;i<rowcount;i++)
        		{   
        		   String tempmark=(String) GetData(table,i,CurLis+1);
        		   if(tempmark.contains("AB")) { ab++; fail++;continue;}
        		   if(tempmark.contains("VA")) { va++; continue;}
        		   if(tempmark==" ") {blank++;fail++;continue;} 
        		   if(tempmark!=null) tempmark = tempmark.replaceAll("\\D+","");
        		   if(tempmark.length()==0) {blank++; fail++;continue;}
        		   int imark=Integer.parseInt(tempmark.replaceAll("[^0-9.]",""));
                   if(imark==0) zeros++;
        		   if(imark<border) fail++; else pass++;
        		   
                }
        		String GROSS=String.format("Gross     : %3d",rowcount);
        		String BLANK=String.format("Blanks    : %3d",blank);
        		String AB=String.format   ("Absentees : %3d",ab);
        	   	String VA=String.format   ("Vacants   : %3d",va);
        	   	String ZEROS=String.format("Zeroes    : %3d",zeros);
        	   	String PASS=String.format ("Passes    : %3d",pass);
        		String FAIL=String.format ("Failures  : %3d",fail);
        		String NET=String.format  ("Net Total : %3d",rowcount-va);
        		
        		d.add( new Label (GROSS));
        		d.add( new Label (BLANK));
        		d.add( new Label (AB));
        		d.add( new Label (VA));
        		d.add( new Label (ZEROS));
        		d.add( new Label (PASS));
        		d.add( new Label (FAIL));
        		d.add( new Label (NET));
        		d.add( new Label ("  "));
        		
        		d.add( ok );
        		// Show dialog
        		d.pack();
        		d.setVisible(true);
             }
            
        });
            //	??
            	
        CleanButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {     CleanMarks();
                                
             }
                });

            	
  
       delrowButton.addActionListener(new ActionListener()
        {   public void actionPerformed(ActionEvent e)
            {int rc=table.getRowCount();
            if (rc<9) return;
             int temprow=table.getSelectedRow();
             int tempcol=table.getSelectedColumn();
              if(temprow>=0) ((DefaultTableModel) table.getModel()).removeRow(temprow);
             
              String temp=(String) GetData(table,0,0);
              FrollField.setText(temp);
              temp=(String) GetData(table,table.getRowCount()-1,0);
              LrollField.setText(temp);
              if(temprow==table.getRowCount()) temprow--;
              table.changeSelection(temprow, tempcol,false,false); 
              table.updateUI();
            }
        });


        
         
        
        
        
              printButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
             {      
            	javax.swing.SwingUtilities.invokeLater(new Runnable()
             	{   public void run() 
             	    {
             		 int cc=table.getColumnCount();
             		 
             		 switch(cc)
             		 {case 4 : print2();break;
             		  case 5 : print2();break;
             		  case 6 : print2();break;
             		  default : print();
             		 }
             		 
             	     }
             	});
            	
             }
             
        });
    }
    /*
    private boolean convertRowAndColumn()
     {
        return true;
    }
*/
///111
    
    private void UpdateFromUser()
    {    	
    	Clas[CurLis]=FClas.getText();
    	Examiner[CurLis]=FExaminer.getText();
    	Division[CurLis]=FDivision.getText();
        Stream[CurLis]=FStream.getText();	
        Subject[CurLis]=FSubject.getText();
        Examination[CurLis]=FExamination.getText();
        MaxMarks[CurLis]=FMaxMarks.getText();
        Date[CurLis]=FDate.getText();
     }
    
    
    
    
    
    
    private void UpdateHeader()
    {    	
    	FClas.setText(Clas[CurLis]);
    	FExaminer.setText(Examiner[CurLis]);
        FDivision.setText(Division[CurLis]);
        FStream.setText(Stream[CurLis]);
        FSubject.setText(Subject[CurLis]);
        FExamination.setText(Examination[CurLis]);
        FMaxMarks.setText(MaxMarks[CurLis]);
        FDate.setText(Date[CurLis]);
        this.setTitle(FilePath[CurLis]);
        TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(CurLis+1);  
        column1.setHeaderValue(Subject[CurLis]);
        table.updateUI();
    }
    
    
    
    private void slide()
    { 
    
    if(curRow>=table.getRowCount()-1) {Line1.setText(""); return;}
	 curRow++;
	table.setRowSelectionInterval(curRow,curRow);
  	Rectangle vr = table.getVisibleRect ();
	int first = table.rowAtPoint(vr.getLocation());
	vr.translate(0, vr.height);
	int last=table.rowAtPoint(vr.getLocation());
	int visibleRows = last - first;
	int mid=visibleRows/2;

 	if(curRow>first+mid)
 	javax.swing.SwingUtilities.invokeLater(new Runnable()
 	{   public void run() 
 	    {ScrollToLine(scrollPane, table);
 	     Line1.setText(""); /* execute logic to update panel */ 
 	     }
 	});
 	else
	    {Point pt = MouseInfo.getPointerInfo().getLocation();
		Rectangle r = table.getCellRect(curRow, curCol, true);  
	    Point p = new Point(r.x+ r.width, r.y-r.height );  // lower left of cell  
	    SwingUtilities.convertPointToScreen(p, table);  
	    dialog.setLocation(p);
	    
	    try { new Robot().mouseMove( pt.x, pt.y+r.height);
        } 
	    catch (AWTException e1) 
      {
	e1.printStackTrace();
      }  
	    
	    }///else end
    	
    } ///function slide end
    
    
    private void Centre(String s, int width, int XPos, int YPos,Graphics g2d){  
        int stringLen = (int)  g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();  
        int start = width/2 - stringLen/2;  
        g2d.drawString(s, start + XPos, YPos);  
 }  
    
    private void RightJustify(String s, int xPos, int YPos,Graphics g2d){  
        int stringLen = (int)  g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();  
        g2d.drawString(s, xPos-stringLen, YPos);  
 }  
    
    private int atoi(String number)
    {  String input = number.replaceAll("\\D+","");
    
    	if(input.length()==0) return 0;
    	  try {
    	    return Integer.parseInt(input.replaceAll("[^0-9.]",""));
    	  } catch (NumberFormatException e)
    	  {
    	    return 0;
    	  }
    }
    
    
    private void SaveAsRoutine()
    {JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Mark List Files", "mrk");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File("D:\\Proje\\Java\\Test Lists"));
    
    chooser.setMultiSelectionEnabled(true);
    int option = chooser.showSaveDialog(ProgrammaticTableScrollFrame.this);
    if (option == JFileChooser.APPROVE_OPTION) {
      File sf = chooser.getSelectedFile();
      
      FilePath[CurLis] = sf.getPath();
      String temp=FilePath[CurLis].toLowerCase();
      if (!temp.endsWith(".mrk")) FilePath[CurLis] += ".mrk";
      
      WriteToDisk(FilePath[CurLis]);
      bModified[CurLis]=false;
      bSaveDirect[CurLis]=true;
    }
    else {
      show("You canceled.");
    }

    	
    }
    
    public void AutoCloseMsg(String quickmsg)
    {
    	JLabel label=new JLabel(quickmsg,JLabel.CENTER);
    	label.setBackground(Color.yellow);
    	
    	JOptionPane optionPane = new JOptionPane(label,JOptionPane.PLAIN_MESSAGE,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null); 
    	//final JOptionPane optionPane = new JOptionPane(" Saved ", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);

    	
    	final JDialog dialog = new JDialog();
    	
//       dialog.setTitle("Message");
    	//dialog.setModal(true);
    	dialog.setSize(200,200);
    	dialog.setAlwaysOnTop(true);   	
    	dialog.setLocationRelativeTo(this);
    	dialog.setContentPane(optionPane);
    	
    	dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    	dialog.setUndecorated(true);
    	
    	dialog.pack();

    	//create timer to dispose of dialog after 5 seconds
    	Timer timer = new Timer(2000, new AbstractAction() {
    	    @Override
    	    public void actionPerformed(ActionEvent ae) {
    	        dialog.dispose();
    	    }
    	});
    	timer.setRepeats(false);//the timer should only go off once

    	//start timer to close JDialog as dialog modal we must start the timer before its visible
    	timer.start();

    	  dialog.getRootPane().setBackground(Color.yellow);
    	    dialog.getContentPane().setBackground(Color.yellow);
    	    dialog.setBackground (Color.yellow);
    	    
    	
    	dialog.setVisible(true);
    	
    }
    
    
   
    
    
    public void sum()
    {   bModified[CurLis]=true;
    	int cc=table.getColumnCount();
    	if(cc<4) return;
    	int total=0;
    	int ABcount=0;
    	int VAcount=0;
    	int BLcount=0;
    	
    
    	for(int i=1;i<cc-1;i++)
    	{ String temp= (String) GetData(table,curRow,i);
    	  if(temp.contains("AB")) { ABcount++; continue; }
    	  if(temp.contains("VA")) { VAcount++; continue; }
    	  if(temp!=null) temp = temp.replaceAll("\\D+","");
	      if(temp.length()==0) {BLcount++;continue;}
	      
	      
	      int ttt=Integer.parseInt(temp.replaceAll("[^0-9.]",""));
	      total=total+ttt;   
	       		
    	}
    	if(VAcount>0) { SetData("VA",curRow,cc-1);return;}
    	if(ABcount==cc-2){ SetData("AB",curRow,cc-1);return;}
    	if(BLcount==cc-2){ SetData(" ",curRow,cc-1);return;}
    	String temp=String.format("%02d",total);
	    SetData(temp,curRow,cc-1);
    	
    }
    
 public void PrintGrid(int px,int py, int height, int width,int rows,int cols,Graphics gr)
 {   int rc=table.getRowCount();
     int cc=table.getColumnCount();
     int Sections=3;
     if(cc==4) Sections=4;
     int RollsPerSection=40;
     
     int Gap=5; 
     int MrkCellWidth=25;
     int celltextshiftx=5,celltextshifty=12;
	 int rollindex=0;
	 String tempmark1="";
	 long pagetotal=0;
     int tempc=0;
     for(int s=0;s<Sections;s++)
     {int shift=(width+MrkCellWidth*(cc-1)+Gap)*s;
       ///col title
          int i=0,j=0;
         gr.drawRect(px+shift,py+i*height,width, height);
         Centre("ROLL",width,px+shift,py+i*height+celltextshifty,gr);
         for(j=0;j<cc-1;j++)
         {
        	 gr.drawRect(px+width+j*MrkCellWidth+shift,py+i*height,MrkCellWidth, height);
        	 String str3=(Subject[j].length()>3 ? Subject[j].substring(0,3):Subject[j]);
        	 
             Centre(str3,MrkCellWidth,px+width+j*MrkCellWidth+shift,py+i*height+celltextshifty,gr);
         }
	     for(i=1;i<=RollsPerSection;i++)
	      {  
		     gr.drawRect(px+shift,py+i*height,width, height);
		     if(rollindex<rc) { String temproll=(String) GetData(table,rollindex,0);
		     					
		                        Centre(temproll,width,px+shift,py+i*height+celltextshifty,gr);
		                        
		     				  }
	         for(j=0;j<cc-1;j++)
	         {  if(rollindex<rc){	 tempmark1=(String) GetData(table,rollindex,j+1);
	                             Centre(tempmark1,MrkCellWidth,px+width+j*MrkCellWidth+shift,py+i*height+celltextshifty,gr);
	         				}
	         /* String tempmark2=(String) GetData(table,rollindex,2);
	          String tempmark3=(String) GetData(table,rollindex,3);
	         */
	    	 gr.drawRect(px+width+j*MrkCellWidth+shift,py+i*height,MrkCellWidth, height);
	    	 
	         }
	         tempc=atoi(tempmark1);
	    	 pagetotal=pagetotal+tempc;
	     rollindex++;
	   }
	     

     }
     String pt=String.format("Page Total : %d", pagetotal);
     gr.drawString(pt,px, py+700);
     RightJustify("Examiner's Sign : ____________",px+500, py+700,gr);
     
 }
    
    
    
    public void print2()
    {  try {
            PrinterJob pjob = PrinterJob.getPrinterJob();
            pjob.setJobName("MarkList Printout");
            pjob.setCopies(1);
            pjob.setPrintable(new Printable() {
              public int print(Graphics pg, PageFormat pf, int pageNum) {
                if (pageNum > 0) // we only print one page
                  return Printable.NO_SUCH_PAGE; // ie., end of job
               
               
                pg.setFont(new Font("TimesRoman", Font.PLAIN,10)); 
                           
               int tlx=70,tly=100,cellheight=16,cellwidth=40,colcount=10,rowcount=31;
               int celltextshiftx=5,celltextshifty=12;
    
               
               Centre(CollegeName1,colcount*cellwidth,tlx,tly-72,pg);
               pg.drawString("Class & Div : "+Clas[CurLis]+" "+Division[CurLis],tlx, tly-38);
               pg.drawString("Examination : "+Examination[CurLis],tlx, tly-24);
               pg.drawString("Subject :"+Subject[CurLis],tlx+200, tly-38);
               pg.drawString("Total Marks : "+MaxMarks[CurLis],tlx+200, tly-24);
               pg.drawString("Examiner : "+Examiner[CurLis],tlx+400, tly-38);
               pg.drawString("Date : "+Date[CurLis],tlx+400, tly-24);
               
               PrintGrid(tlx,tly,cellheight,cellwidth,10,5,pg);
             
                return Printable.PAGE_EXISTS;
              }
            });

            if (pjob.printDialog() == false) // choose printer
              return; 
            pjob.print(); 
          } catch (PrinterException pe) {
            pe.printStackTrace();
          }
    }    

    
    
    
    
    public void print()
    {
    	try {
    		    		
    		
            PrinterJob pjob = PrinterJob.getPrinterJob();
            //PageFormat pf = pjob.defaultPage();
            //pjob.setPrintable(null, pf);
            
            pjob.setJobName("MarkList Printout");
            pjob.setCopies(1);
           pjob.setPrintable(new Printable()
           {
              public int print(Graphics pg, PageFormat pf, int pageNum) {
                if (pageNum > 0) // we only print one page
                  return Printable.NO_SUCH_PAGE; // ie., end of job
               
               // Stroke drawingStroke = new BasicStroke(0.01f);
                pg.setFont(new Font("TimesRoman", Font.PLAIN,10));
                FontMetrics fm=pg.getFontMetrics();
                
               /*
                Graphics2D g2 = (Graphics2D) 
                g2.setStroke(new BasicStroke(1));
                g2.draw(new Line2D.Float(30, 20, 80, 90));
                */
    /*
    	        Paper paper = pf.getPaper();
    	        final double MM_TO_PAPER_UNITS = 72.0/25.4;
    	        double widthA4 = 210*MM_TO_PAPER_UNITS;
    	        double heightA4 = 297*MM_TO_PAPER_UNITS;
    	        paper.setSize(widthA4, heightA4);
*/
                
                              
                
               int tlx=(int) pf.getImageableX()+10,tly=(int) pf.getImageableY()+fm.getHeight(),
               cellheight=16,cellwidth=45,colcount=10,rowcount=31;
               int celltextshiftx=5,celltextshifty=12;
    
               
               Centre(CollegeName1,colcount*cellwidth,tlx,tly,pg);
               pg.drawString("Class & Div : "+Clas[CurLis]+" "+Division[CurLis],tlx, tly+38);
               pg.drawString("Examination : "+Examination[CurLis],tlx, tly+24);
               pg.drawString("Subject :"+Subject[CurLis],tlx+200, tly+38);
               pg.drawString("Total Marks : "+MaxMarks[CurLis],tlx+200, tly+24);
               pg.drawString("Examiner : "+Examiner[CurLis],tlx+360, tly+38);
               pg.drawString("Date : "+Date[CurLis],tlx+360, tly+24);
               
               tly=tly+60;//skip headerspace
                int i=0;
                for(i=0;i<rowcount;i++)
                  { pg.drawLine(tlx, tly+cellheight*i, tlx+colcount*cellwidth,tly+cellheight*i);
                  pg.drawLine(tlx+cellwidth*i, tly, tlx+cellwidth*i,tly+cellheight*rowcount);
                  //pg.drawString(String.valueOf(i+1),tlx+celltextshiftx, tly+celltextshifty+cellheight*i);
                 
                  }
                
                
                pg.drawLine(tlx, tly+cellheight*i, tlx+colcount*cellwidth,tly+cellheight*i);
               
                for(i=0;i<5;i++)
                { 	Centre("Roll",cellwidth,tlx+2*i*cellwidth,tly+celltextshifty,pg);
                    Centre("Marks",cellwidth,tlx+(2*i+1)*cellwidth,tly+celltextshifty,pg);
                }
                int pagetotal=0;
                int tempc=0;
                boolean err=false;
                int totalroll=table.getRowCount();
                i=0;
                int j=0;
                int rows=rowcount-1; //total rows without title
                for(int k = 0;k<totalroll;k++)
                {  j=k%rows;
                   i=k/rows;
                   String temproll=(String) GetData(table,k,0);
                   String tempmark=(String) GetData(table,k,1);
                   if(temproll==null) temproll=" ";
                   if(tempmark==null) tempmark=" ";
                   Centre(temproll,cellwidth,tlx+2*i*cellwidth,tly+cellheight*(j+1)+celltextshifty,pg);
                   Centre(tempmark,cellwidth,tlx+(2*i+1)*cellwidth,tly+cellheight*(j+1)+celltextshifty,pg);
                   tempc=atoi(tempmark);
                   //if(tempc<0) show("Error In Page Toatal : " + temproll);
                   pagetotal=pagetotal+tempc;
                   
                }

                pg.drawString("Page Total : "+String.valueOf(pagetotal),tlx, tly+550);
                RightJustify("Examiner's Sign : ____________",tlx+cellwidth*colcount, tly+560,pg);
                
                //pg.dispose();

                return Printable.PAGE_EXISTS;
              }
            });

        if (pjob.printDialog() == false) return; // choose printer comment out to remove dlg
               
            pjob.print(); 
          } catch (PrinterException pe) {
            pe.printStackTrace();
          }
    }    
    public void WriteToDisk(String fyle)
    {FileWriter f0=null;
    
    Clas[CurLis]=FClas.getText();
    Examiner[CurLis]=FExaminer.getText();
    Division[CurLis]=FDivision.getText();
    Stream[CurLis]=FStream.getText();
    Subject[CurLis]=FSubject.getText();
    Examination[CurLis]=FExamination.getText();
    MaxMarks[CurLis]=FMaxMarks.getText();
    Date[CurLis]=FDate.getText();
    
	 try {f0 = new FileWriter(fyle);	} catch (IOException e1) {e1.printStackTrace();	}
     String newLine = System.getProperty("line.separator");
     try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}
     try { f0.write(CollegeName1+ newLine);	} catch (IOException e) {e.printStackTrace();	}
     try { f0.write(CollegeName2+ newLine);	} catch (IOException e) {e.printStackTrace();	}
     try { f0.write(CollegeName3+ newLine);	} catch (IOException e) {e.printStackTrace();	}

     try { f0.write(newLine+"======== MarkList Ver 177 ======="+newLine+newLine);	} catch (IOException e) {e.printStackTrace();	}     
     
     try { f0.write("Total Sets : "+TotalSets[CurLis]);	} catch (IOException e) {e.printStackTrace();	}
     try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}
     try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}
     
for(int i=0;i<TotalSets[CurLis];i++)
{   try { f0.write("Set "+i+": "+Set[CurLis][i]+newLine);	} catch (IOException e) {e.printStackTrace();	}
    try { f0.write("Set "+i+": "+Key[CurLis][i]+newLine);	} catch (IOException e) {e.printStackTrace();	}
    try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}

}

try { f0.write("======== MarkList Ver 177 ======="+newLine+newLine);	} catch (IOException e) {e.printStackTrace();	}

try { f0.write("First Roll  : "+FirstRoll+ newLine);	} catch (IOException e) {e.printStackTrace();	}
try { f0.write("Last  Roll  : "+LastRoll+ newLine);	} catch (IOException e) {e.printStackTrace();	}
try { f0.write("Examiner    : "+Examiner[CurLis]+ newLine);	} catch (IOException e) {e.printStackTrace();	}
try { f0.write("Class       : "+Clas[CurLis]    + newLine);	} catch (IOException e) {e.printStackTrace();	}
try { f0.write("Division    : "+Division[CurLis]+ newLine);	} catch (IOException e) {e.printStackTrace();	}
try { f0.write("Stream      : "+Stream[CurLis]  + newLine);	} catch (IOException e) {e.printStackTrace();	}
try { f0.write("Subject     : "+Subject[CurLis] + newLine);	} catch (IOException e) {e.printStackTrace();	}
try { f0.write("Examination : "+Examination[CurLis]+ newLine);	} catch (IOException e) {e.printStackTrace();	}
try { f0.write("Max Marks   : "+MaxMarks[CurLis]+ newLine);	} catch (IOException e) {e.printStackTrace();	}
try { f0.write("Date        : "+Date[CurLis]+ newLine);	} catch (IOException e) {e.printStackTrace();	}


try { f0.write(newLine+"======== MarkList Ver 177 ======="+newLine+newLine);	} catch (IOException e) {e.printStackTrace();	}
try { f0.write(" Roll   Marks");	} catch (IOException e) {e.printStackTrace();	}
try { f0.write(newLine+newLine+newLine+newLine);	} catch (IOException e) {e.printStackTrace();	}

int rowcount=table.getRowCount();

for(int i = 0;i<rowcount;i++)
{   
   String temproll=(String) GetData(table,i,0);
   String tempmark=(String) GetData(table,i,CurLis+1);
   try { f0.write(temproll+": "+tempmark+newLine);	} catch (IOException e) {e.printStackTrace();	}
}

try {f0.close();} catch (IOException e) {e.printStackTrace();}
    
this.setTitle(fyle);
AutoCloseMsg("File Saved");
   }
    
    
    public void ReadFromDisk(String fnem)
    {
    	BufferedReader reader=null;
		try {
			reader = new BufferedReader(new FileReader(fnem));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		
		ArrayList<String> strArray = new ArrayList<String>();
				
		String line = null;
    	try { while ((line = reader.readLine()) != null) 
			{
			 
			 strArray.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    //	show(strArray.size());
    
    	CollegeName1=strArray.get(1);
    	CollegeName2=strArray.get(2);
    	CollegeName3=strArray.get(3);
    	String temp[],stemp;
    	stemp=strArray.get(7);
    	temp=stemp.split(":");
    	
    	TotalSets[CurLis]=Integer.parseInt(temp[1].replaceAll("[^0-9.]",""));
    	
        for(int i=0;i<TotalSets[CurLis];i++) 
        {   stemp=strArray.get(9+3*i);temp=stemp.split(":");
        	Set[CurLis][i]=temp[1].trim();
        	stemp=strArray.get(9+3*i+1);temp=stemp.split(":");
        	Key[CurLis][i]=temp[1].trim();
        }
        //show(Set[CurLis][CurSet]);
        stemp=strArray.get(11+3*TotalSets[CurLis]); temp=stemp.split(":");
    	FirstRoll=temp[1].trim();
    
    	stemp=strArray.get(12+3*TotalSets[CurLis]); temp=stemp.split(":");
    	LastRoll=temp[1].trim();
    	stemp=strArray.get(13+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Examiner[CurLis]=temp[1].trim();
    	stemp=strArray.get(14+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Clas[CurLis]=temp[1].trim();
    	stemp=strArray.get(15+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Division[CurLis]=temp[1].trim();
    	stemp=strArray.get(16+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Stream[CurLis]=temp[1].trim();
    	stemp=strArray.get(17+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Subject[CurLis]=temp[1].trim();
    	stemp=strArray.get(18+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Examination[CurLis]=temp[1].trim();
    	stemp=strArray.get(19+3*TotalSets[CurLis]); temp=stemp.split(":");
    	MaxMarks[CurLis]=temp[1].trim();
    	stemp=strArray.get(20+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Date[CurLis]=temp[1].trim();
    	
        UpdateHeader();    	
    	
    	
    	int rollstart=0;
    	int totalroll=table.getRowCount();
    	int strength=strArray.size()-(28+3*TotalSets[CurLis]);
    	int difference=strength-totalroll;
        if(difference>0)
      	   for(int i=0;i<difference;i++) ((DefaultTableModel) table.getModel()).addRow(new Object[]{" "});
      	
    	  
        if(difference<0)
  	  { difference=-difference;
      	for(int i=0;i<difference;i++) ((DefaultTableModel) table.getModel()).removeRow(0);
  	   }
   	
    	for(int i=28+3*TotalSets[CurLis];i<strArray.size();i++) 
    		{
    		stemp=strArray.get(i); temp=stemp.split(":");
        	SetData(temp[0].trim(),rollstart,0);SetData(temp[1].trim(),rollstart,CurLis+1);
    		rollstart++;
    		}
    this.setTitle(fnem);
    FrollField.setText(FirstRoll);
    LrollField.setText(LastRoll);
    
    TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(CurLis+1); 		  
    if(Subject[CurLis].trim().length()!=0)
     column1.setHeaderValue(Subject[CurLis]);
  
    
    int buffer=curRow;
    int cc=table.getColumnCount();
    int rr=table.getRowCount();
    if(cc==2) return;
    for(curRow=0;curRow<rr;curRow++)    sum();
    curRow=buffer;
    table.updateUI();

    	
     }
    

    public void ReadMarksOnly(String fnem)
    {
    	BufferedReader reader=null;
		try {
			reader = new BufferedReader(new FileReader(fnem));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		
		ArrayList<String> strArray = new ArrayList<String>();
				
		String line = null;
    	try { while ((line = reader.readLine()) != null) 
			{
			 
			 strArray.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    //	show(strArray.size());
    
    	CollegeName1=strArray.get(1);
    	CollegeName2=strArray.get(2);
    	CollegeName3=strArray.get(3);
    	String temp[],stemp;
    	stemp=strArray.get(7);
    	temp=stemp.split(":");
    	
    	TotalSets[CurLis]=Integer.parseInt(temp[1].replaceAll("[^0-9.]",""));
    	
        for(int i=0;i<TotalSets[CurLis];i++) 
        {   stemp=strArray.get(9+3*i);temp=stemp.split(":");
        	Set[CurLis][i]=temp[1].trim();
        	stemp=strArray.get(9+3*i+1);temp=stemp.split(":");
        	Key[CurLis][i]=temp[1].trim();
        }
        //show(Set[CurLis][CurSet]);
        stemp=strArray.get(11+3*TotalSets[CurLis]); temp=stemp.split(":");
    	FirstRoll=temp[1].trim();
    
    	stemp=strArray.get(12+3*TotalSets[CurLis]); temp=stemp.split(":");
    	LastRoll=temp[1].trim();
    	stemp=strArray.get(13+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Examiner[CurLis]=temp[1].trim();
    	stemp=strArray.get(14+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Clas[CurLis]=temp[1].trim();
    	stemp=strArray.get(15+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Division[CurLis]=temp[1].trim();
    	stemp=strArray.get(16+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Stream[CurLis]=temp[1].trim();
    	stemp=strArray.get(17+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Subject[CurLis]=temp[1].trim();
    	stemp=strArray.get(18+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Examination[CurLis]=temp[1].trim();
    	stemp=strArray.get(19+3*TotalSets[CurLis]); temp=stemp.split(":");
    	MaxMarks[CurLis]=temp[1].trim();
    	stemp=strArray.get(20+3*TotalSets[CurLis]); temp=stemp.split(":");
    	Date[CurLis]=temp[1].trim();
    	
        UpdateHeader();    	
    	
    	int totalroll=table.getRowCount();
    	int strength=strArray.size()-(28+3*TotalSets[CurLis]);
    	boolean bMismatch=false;
       if(strength != totalroll) bMismatch=true; 
  	   
   	    String temproll="";
    	for(int i=28+3*TotalSets[CurLis];i<strArray.size();i++) 
    		{
    		stemp=strArray.get(i); temp=stemp.split(":");
    		temp[0]=temp[0].replaceAll("\\D+","");
    		boolean bFound=false;
    		for (int j=0;j<totalroll;j++)
    		 {  temproll=(String) GetData(table,j,0);
    		    temproll= temproll.replaceAll("\\D+","");
    		    if(Integer.parseInt(temp[0])==Integer.parseInt(temproll))
    			{
    				SetData(temp[1].trim(),j,CurLis+1);		
    				bFound=true;
    				break;
    			}
    			
    		 }
    		if(!bFound) bMismatch=true;
    		
    		
    		}
    	if(bMismatch) show("Mismatch");
    this.setTitle(fnem);
    FrollField.setText(FirstRoll);
    LrollField.setText(LastRoll);
    
    TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(CurLis+1); 		  
    if(Subject[CurLis].trim().length()!=0)
     column1.setHeaderValue(Subject[CurLis]);
    
    int buffer=curRow;
    int rr=table.getRowCount();
    
    for(curRow=0;curRow<rr;curRow++)    sum();
    curRow=buffer;
    table.updateUI();

    	
     }

    
    // This method ensures that a cell is visible. It does NOT try to
    // put the cell always at a specific position in the viewport!!
    // 1) If the cell is already visible, nothing changes.
    // 2) If the cell is not visible, scrolls the table to make it
    //    visible but the effective position depends on where was the
    //    scroll position.

    public static void ensureJTableCellIsVisible(JTable table, int row, int column) {
        Rectangle cellRect = table.getCellRect(row, column, true);

        table.scrollRectToVisible(cellRect);
    }

    // This method scrolls the table to show the cell always at the
    // top-left corner of the viewport. If it's not possibile (because
    // there are no sufficient rows/columns, it is not positioned at
    // the top/left corner but is guaranteed to be visible.

    public static void scrollJTableToCell(JScrollPane scrollPane, JTable table, int row, int column) 
    {   Rectangle cellRect = table.getCellRect(row, column, true);
        JViewport viewport = scrollPane.getViewport();
        viewport.setViewPosition(cellRect.getLocation());
    }
    
    
    public Object GetData(JTable table, int row_index, int col_index){
    	  return table.getModel().getValueAt(row_index, col_index);
    	  }  
    
    
    public void CleanMarks()
    {         	int result = JOptionPane.showConfirmDialog (null, "This will make all mark cells blank for Re-Entry. Proceed ?","Warning", JOptionPane.YES_NO_OPTION);
	if(result == JOptionPane.YES_OPTION)
	{int rowcount=table.getRowCount();
	 for(int i = 0;i<rowcount;i++)    SetData(" ",i,1);
	 bModified[CurLis]=true;
	 String ttt=String.format("MarkList - %02d - Untitled",CurLis+1);
     FilePath[CurLis]=ttt;
     this.setTitle(FilePath[CurLis]);

	  }
 
    }
    
    
    
    public void SetData(Object obj, int row_index, int col_index)
         {
    	  table.getModel().setValueAt(obj,row_index,col_index);
    	  
    	  // System.out.println("Value is added");
    	  }

    // This method scrolls the table to show the cell always at the
    // top-left corner of the viewport. If it's not possibile (because
    // there are no sufficient rows/columns, it is not positioned at
    // the top/left corner but is guaranteed to be visible.

    public static void ScrollToLine(JScrollPane scrollPane, JTable table) 
    {	Rectangle vr = table.getVisibleRect ();
    	int first = table.rowAtPoint(vr.getLocation());
     	Rectangle cellRect = table.getCellRect(first+1, 0, true);
        JViewport viewport = scrollPane.getViewport();
        Rectangle rr=viewport.getViewRect();
        int shift=cellRect.y - rr.y;
       // String tt;
        //tt = String.format("%d %d %d",cellRect.y, rr.y, shift);
        for(int i=0; i<shift;i++)
   	  {
   	   int cur=scrollPane.getVerticalScrollBar().getValue();
          scrollPane.getVerticalScrollBar().setValue(cur+1);
          try { Thread.sleep(45);
            	} catch (InterruptedException e1)
            	{
		       // TODO Auto-generated catch block
		       e1.printStackTrace();
	             }
   	   }
///        JOptionPane.showMessageDialog(null, tt);
        //viewport.setViewPosition(cellRect.getLocation());
        
    }
    
    public static void ScrollUP(JScrollPane scrollPane, JTable table) 
    {	    	
    	Rectangle vr = table.getVisibleRect ();
    	int first = table.rowAtPoint(vr.getLocation());
     	if(first<1) return;
     	curRow--; table.setRowSelectionInterval(curRow,curRow);
     	Rectangle cellRect = table.getCellRect(first+1, 0, true);
        JViewport viewport = scrollPane.getViewport();
        Rectangle rr=viewport.getViewRect();
        int shift=cellRect.y - rr.y;
       
        for(int i=0; i<shift;i++)
   	  {
   	   int cur=scrollPane.getVerticalScrollBar().getValue();
          scrollPane.getVerticalScrollBar().setValue(cur-1);
          try { Thread.sleep(45);
            	} catch (InterruptedException e1)
            	{
		       // TODO Auto-generated catch block
		       e1.printStackTrace();
	             }
   	   }
///        JOptionPane.showMessageDialog(null, tt);
        //viewport.setViewPosition(cellRect.getLocation());
        
    }
    
    
  public void DisplayHelp(int mode)
  { switch(mode)
	  { case 0: 
		 
		 String multiLineMsg[] = 
	     { "1. Just hover the mouse on digit and pause.",
           "2. To repeat the digit go out and enter again.",
           "3. Marks up to 100 are supported per subject.",
           "4. Enter single digit marks with prefix 0.",
           "5. AB - Absent, VA - Vacant, X - Backspace.",
           "6. Click on UP and DN to Slide UP and Down.",
           "7. For Keyboard entry, close mouse entry box,",
           "   single click on cell and start keyboard entry.",
           " "
          } ;
		
		 
		 
          JOptionPane.showMessageDialog (dialog, multiLineMsg,"Help",JOptionPane.PLAIN_MESSAGE);
               break;
	      case 1:
	    	  String multiLineMsg1[] = 
		     { "1. If you have not entered MCQ Key, click mode button.",
	           "2. Complete the MCQ Key and then come back here again.",
	           "3. Just touch the mouse to MCQ option buttons A,B,C,D.",
	           "4. Use spacebar for empty option or invalid option.",
	           "5. After completing all qns, press [Enter] to post marks.",
	           "6. AB - Absent, X - Backspace and R - Reset all options.",
	           "7. Use can use keyboard for Absent(A) and Vacant (V).",
	           "8. For mutltiset, enter two digit set number from keyboard.",
	           "9. Altenatively just touch the Set button to flip next set.",
	           " "
	          } ;
	          JOptionPane.showMessageDialog (dialog, multiLineMsg1,"Help",JOptionPane.PLAIN_MESSAGE);
	    	  
	    	  
	    	  break;
	      case 2:
	    	  String multiLineMsg2[] = 
		     { "1. Enter Answer Key for your paper. X-Backspace.",
	           "2. For Multiset Examination click >> for next Set.",
	           "3. Click << for previous set to edit previous set.",
	           "4. >> will not work for Invalid or Blank Ans Keys.",
	           "5. After you finish, switch to MCQ Assessment Mode.",
	           "6. Assessment will work up to the last valid ans key.",
	           " "
	          } ;
	          JOptionPane.showMessageDialog (dialog, multiLineMsg2,"Help",JOptionPane.PLAIN_MESSAGE);
	    	  break;
 	  }

  }
    
    
    
public static void show(String msg)
{JOptionPane.showMessageDialog(null, msg);}
public void show(int msg)
{JOptionPane.showMessageDialog(null, msg);}

	public void DInit()
	{   dialogcreated=true;
		KeyPadDisplayed=true;
// 	int sui = table.getScrollableUnitIncrement(scrollPane.getVisibleRect(), 
//  SwingConstants.VERTICAL, 1);

final Font BTN_FONT = new Font(Font.MONOSPACED, Font.BOLD, 20);
final Font BTN_FONT_SMALL = new Font(Font.MONOSPACED, Font.BOLD, 15);


Line1.setEditable(false);
Line2.setEditable(false);
Line3.setEditable(false);

Line2.setVisible(false);
Line3.setVisible(false);

Line1.setFont(BTN_FONT.deriveFont(Font.PLAIN));
Line2.setFont(BTN_FONT.deriveFont(Font.PLAIN));
Line3.setFont(BTN_FONT.deriveFont(Font.PLAIN));



JPanel fieldPanel = new JPanel(new GridLayout(4,1));

final JButton btnPE = new JButton("Marks Entry (Touch Mode)"); btnPE.setFont(BTN_FONT);

fieldPanel.add(Line1);
fieldPanel.add(Line2);
fieldPanel.add(Line3);
fieldPanel.add(btnPE);

JPanel btnPanel = new JPanel(new GridLayout(4,25));

//// button matrix

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
///final JButton btnT = new JButton("T"); btnT.setFont(BTN_FONT);




//btnPanel.add(btnT);FStream
//btnT.setVisible(false);
final Timer tiempo= new Timer(850, new ActionListener() 
{ 
public void actionPerformed(ActionEvent e) 
{   
if(!inn.isEmpty())
   {   String temp=Line1.getText();
       if(inn=="AB" || inn=="100") temp=inn; else  temp+=inn;
        Line1.setText(temp);
        int temprow=table.getSelectedRow();
        if(temprow!=-1) curRow=temprow;
        int tempcol=table.getSelectedColumn();
        if(tempcol!=-1) curCol=tempcol;
        
        SetData(temp,curRow,curCol);
        sum();
        ((Timer)e.getSource()).stop();
        inn="";
        
        
     int len=temp.length();
     if(len>=2)
     {	 if(curRow>=table.getRowCount()-1) {Line1.setText(""); return;}
    	 curRow++;
     	table.setRowSelectionInterval(curRow,curRow);
       	Rectangle vr = table.getVisibleRect ();
     	int first = table.rowAtPoint(vr.getLocation());
     	vr.translate(0, vr.height);
     	int last=table.rowAtPoint(vr.getLocation());
     	int visibleRows = last - first;
     	int mid=visibleRows/2;
     	
     	if(curRow>first+mid)
     	javax.swing.SwingUtilities.invokeLater(new Runnable()
     	{   public void run() 
     	    {ScrollToLine(scrollPane, table);
     	     Line1.setText(""); /* execute logic to update panel */ 
     	     }
     	});
     	else
     	{	Point pt = MouseInfo.getPointerInfo().getLocation();
     		Rectangle r = table.getCellRect(curRow, curCol, true);  
     	    Point p = new Point(r.x+ r.width, r.y-r.height );  // lower left of cell  
     	    SwingUtilities.convertPointToScreen(p, table);  
     	    dialog.setLocation(p);
     	    try { new Robot().mouseMove( pt.x, pt.y+r.height);
		        } 
     	    catch (AWTException e1) 
		      {
			e1.printStackTrace();
		      }
     	     javax.swing.SwingUtilities.invokeLater(new Runnable()
     	     { public void run() 
      	       {try
      	          { Thread.sleep(850);	Line1.setText("");
			      } catch (InterruptedException e) 
			      {e.printStackTrace();
		          }
      	     }  ///end of if len>2 
      	
     	     
     	     });
     	}
     }  
   }
    }
});  	




btnUP.addMouseListener(new MouseListener()  ////Used For - in MCQ
{	@Override
public void mouseClicked(MouseEvent arg0) 
{   if(KeyPadMode==2)
    {String l2=Line2.getText(); String l3=Line3.getText();
	 //if(!Line2.getText().isEmpty() && !Line3.getText().isEmpty() && Line2.get
	 if(l2.length()==l3.length() && l3.length()>0) 
	 {   Set[CurLis][CurSet]=l2;
	     Key[CurLis][CurSet]=l3;
		 CurSet++;if(TotalSets[CurLis]==CurSet) TotalSets[CurLis]++;
	     Line2.setText(Set[CurLis][CurSet]);Line3.setText(Key[CurLis][CurSet]);
		 String temp=String.format("%02d",CurSet+1);
		 btnAB.setText(temp);	 
	 }
    }
else
	ScrollUP(scrollPane, table); 
}

@Override
public void mouseEntered(MouseEvent arg0)
{ }


@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});


btnPE.addMouseListener(new MouseListener()  ////Entry Mode Change Button
{	@Override
public void mouseClicked(MouseEvent arg0) 
{ 

if(KeyPadMode==2)
{String l2=Line2.getText(); String l3=Line3.getText();
 Set[CurLis][CurSet]=l2;
 Key[CurLis][CurSet]=l3;
 }

CurSet=0;
  
/*
//delete last invalid set
if(TotalSets[CurLis]>1 && KeyPadMode==2) 
	{
		
		
		String l2=Set[CurLis][TotalSets[CurLis]-1];
		String l3=Key[CurLis][TotalSets[CurLis]-1];
		
		 if(l2.length()!=l3.length() || l3.length()==0 || l2.length()==0) 
		 {   Set[CurLis][TotalSets[CurLis]-1]="";
			 Key[CurLis][TotalSets[CurLis]-1]="";
			 TotalSets[CurLis]--;
			 
		 }
		     
		
	}
	
	*/
	
	KeyPadMode=(KeyPadMode+1)%3;
	 if(KeyPadMode==0) btnPE.setText("Marks Entry (Touch Mode)");
	 if(KeyPadMode==1) btnPE.setText("MCQ Assessment (Touch Mode)");
	 if(KeyPadMode==2) btnPE.setText("Ans Key Edit (Click Mode)");
	
	 Line1.setText("");

	
	if(KeyPadMode==0)
    {btnP.setText("Help");btnS.setText(" ");	 btn7.setText("7"); btn9.setText("9");
     btn3.setText("3");btn1.setText("1");
     btnAB.setText("AB");
     btn0.setText("0"); btn2.setText("2");
     btn4.setText("4");btn5.setText("5");
     btn6.setText("6"); btn8.setText("8");
     btn100.setText("100");  btnQN.setText("VA");
     btnDN.setText("DN"); btnQ.setText(" ");
     btnUP.setText("UP");
     
     
     Line2.setVisible(false);
	 Line3.setVisible(false);
     
     }
if(KeyPadMode==1)
  {btnP.setText("Help");btnS.setText("01");
  btnR.setText("Set");
	btn7.setText("A"); btn9.setText("B");
   btn3.setText("D");btn1.setText("C");
   
   btn0.setText(" "); btn2.setText(" ");
   btn4.setText(" ");btn5.setText(" ");
   btn6.setText(" "); btn8.setText(" ");
   btn100.setText(" ");  //btn1.setText("1");
   btnQN.setText("00");
   btnDN.setText("DN");
   btnUP.setText("UP");
   btnQ.setText("R");
  
   Line2.setText(Set[CurLis][CurSet]);Line3.setText(Key[CurLis][CurSet]);
   
   Line2.setVisible(true);
   Line3.setVisible(true);
  
  }

   if(KeyPadMode==2)
   { btnP.setText("A"); btn7.setText("B");
    btn8.setText("C");btn9.setText("D");
    
    btnQ.setText("1"); btn4.setText("2");
    btn5.setText("3");btn6.setText("4");
    btnQN.setText("X"); //btn8.setText(" ");
    btn1.setText(" ");  btn3.setText(" ");
    btn0.setText("Set");
    btnAB.setText("01");
    btnUP.setText(">>");  btnDN.setText("<<");
    btnS.setText("Help");
    btnR.setText(" ");
    
    
    Line2.setVisible(true);
    Line3.setVisible(true);

   }

 }

@Override
public void mouseEntered(MouseEvent arg0)
{  }

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});



btnQ.addMouseListener(new MouseListener()  ////Used For Reset in MCQ
{	@Override
public void mouseClicked(MouseEvent arg0)
{ if(KeyPadMode==2) Line3.setText(Line3.getText()+"1");  }

@Override
public void mouseEntered(MouseEvent arg0)
{  if(KeyPadMode==1) { Line1.setText("");btnQN.setText("00");} 
                       MCQmarks=0;
}

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});


btnDN.addMouseListener(new MouseListener()  ////Used For Reset in MCQ
{	@Override
public void mouseClicked(MouseEvent arg0) 
     {
	
	if(KeyPadMode==2)  ////MCQ ANSWER EDIT MODE
    {String l2=Line2.getText(); String l3=Line3.getText();
    Set[CurLis][CurSet]=l2;
    Key[CurLis][CurSet]=l3;
	if(CurSet>0)
	     {CurSet--;
	      Line2.setText(Set[CurLis][CurSet]);Line3.setText(Key[CurLis][CurSet]);
		 String temp=String.format("%02d",CurSet+1);
		 btnAB.setText(temp);
	     }
	}
else
	
	  slide(); 
	}

@Override
public void mouseEntered(MouseEvent arg0)
{  }

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});


btnP.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) 
{   if(KeyPadMode==0) DisplayHelp(KeyPadMode);
    if(KeyPadMode==1) DisplayHelp(KeyPadMode);
	if(KeyPadMode==2) Line2.setText(Line2.getText()+"A"); }

@Override
public void mouseEntered(MouseEvent arg0) 
{ }

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});



btnR.addMouseListener(new MouseListener()
{	
@Override
public void mouseClicked(MouseEvent arg0) 
{ 
    if(KeyPadMode==1)
    {
    	
    	 {    ///FLIP NEXT SET BLOck
    			CurSet++;
    			String l2=Set[CurLis][CurSet];
    			String l3=Key[CurLis][CurSet];
    		       
    		 if(l3.length()==0 || l2.length()==0 || l2.length()!=l3.length())CurSet=0;
    		 
    		 {   
    			 Line2.setText(Set[CurLis][CurSet]);Line3.setText(Key[CurLis][CurSet]);
    			 String temp=String.format("%02d",CurSet+1);
    			 btnS.setText(temp);	 
    		 }
    		  }////////END OF FLIP NEXT SET BLOck
    	
    }
	 
}

@Override
public void mouseEntered(MouseEvent arg0) 
{
    if(KeyPadMode==1)
    {
    	
    	 {    ///FLIP NEXT SET BLOck
    			CurSet++;
    			String l2=Set[CurLis][CurSet];
    			String l3=Key[CurLis][CurSet];
    		       
    		 if(l3.length()==0 || l2.length()==0 || l2.length()!=l3.length())CurSet=0;
    		 
    		 {   
    			 Line2.setText(Set[CurLis][CurSet]);Line3.setText(Key[CurLis][CurSet]);
    			 String temp=String.format("%02d",CurSet+1);
    			 btnS.setText(temp);	 
    		 }
    		  }////////END OF FLIP NEXT SET BLOck
    	
    }
	

}

@Override
public void mouseExited(MouseEvent arg0){}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});




btnS.addMouseListener(new MouseListener()
{	
@Override
public void mouseClicked(MouseEvent arg0) 
{ 
    if(KeyPadMode==2)
    {DisplayHelp(2);	
    }
	 
}

@Override
public void mouseEntered(MouseEvent arg0) 
{}

@Override
public void mouseExited(MouseEvent arg0){}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});











btn7.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) 
{if(KeyPadMode==2) Line2.setText(Line2.getText()+"B");  }

@Override
public void mouseEntered(MouseEvent arg0)
{ if(KeyPadMode==1){ String corkey=Line2.getText();
	                 int corkeylen=corkey.length();
	                 if(corkeylen<1) return;
                     
	                 String markey=Line3.getText();
	                 int markeylen=markey.length();
	                 if(corkeylen!=markeylen) return;
	                 //if(nmar<1)
	                 String studkey=Line1.getText();
	                 int studkeylen=Line1.getText().length();
	                 
	                 if(studkeylen<corkeylen)
	                 {Line1.setText(Line1.getText()+"A");
	                  studkey=Line1.getText();
	                  studkeylen=Line1.getText().length();
	                  MCQmarks=0;
	                  for(int i=0;i<studkeylen;i++)
	                	  if(studkey.charAt(i)==corkey.charAt(i)) MCQmarks=MCQmarks+markey.charAt(i)-'0';
	                  
	                  String fmtstr=String.format("%02d",MCQmarks);
	                  btnQN.setText(fmtstr);
	                 }
	             }


  if(KeyPadMode==0) {inn="7"; tiempo.start();}
}


@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});

btn8.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0)
{if(KeyPadMode==2) Line2.setText(Line2.getText()+"C"); }

@Override
public void mouseEntered(MouseEvent arg0) 
{ if(KeyPadMode==0) { inn="8"; tiempo.start();}}

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});


btn9.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) 
{
	
 if(KeyPadMode==2) Line2.setText(Line2.getText()+"D"); }

@Override
public void mouseEntered(MouseEvent arg0)
{  
	if(KeyPadMode==1){ String corkey=Line2.getText();
    int corkeylen=corkey.length();
    if(corkeylen<1) return;
    
    String markey=Line3.getText();
    int markeylen=markey.length();
    
    //if(nmar<1)
    String studkey=Line1.getText();
    int studkeylen=Line1.getText().length();
    
    if(corkeylen!=markeylen) return;
    
    if(studkeylen<corkeylen)
    {Line1.setText(Line1.getText()+"B");
     studkey=Line1.getText();
     studkeylen=Line1.getText().length();
     MCQmarks=0;
     for(int i=0;i<studkeylen;i++)
    	 if(studkey.charAt(i)==corkey.charAt(i)) MCQmarks=MCQmarks+markey.charAt(i)-'0';
     
     String fmtstr=String.format("%02d",MCQmarks);
     btnQN.setText(fmtstr);
    }
}

if(KeyPadMode==0) {inn="9"; tiempo.start();}
}
@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});

btn4.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) 
{if(KeyPadMode==2) Line3.setText(Line3.getText()+"2"); }

@Override
public void mouseEntered(MouseEvent arg0) { if(KeyPadMode==0) { inn="4"; tiempo.start();}}

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});


btn5.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) 
{ if(KeyPadMode==2) Line3.setText(Line3.getText()+"3");  }

@Override
public void mouseEntered(MouseEvent arg0) {if(KeyPadMode==0) { inn="5"; tiempo.start();} }

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});

btn6.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) 
{ if(KeyPadMode==2) Line3.setText(Line3.getText()+"4"); }

@Override
public void mouseEntered(MouseEvent arg0) { if(KeyPadMode==0) { inn="6"; tiempo.start();}}

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});

btnBS.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) 
{ 
	   String ttt=Line3.getText();
	   int len=ttt.length();
	   if (len<1) return;
	   if(KeyPadMode==2) Line3.setText(ttt.substring(0, len-1));	
}

@Override
public void mouseEntered(MouseEvent arg0) 
{ 
	if(KeyPadMode==0) Line1.setText("");
	
if(KeyPadMode==1)
{ String ttt=Line1.getText();
  int len=ttt.length();
  if (len<1) return;
   Line1.setText(ttt.substring(0, len-1));
    String corkey=Line2.getText();
   int corkeylen=corkey.length();
    if(corkeylen<1) return;
  String markey=Line3.getText();
  int markeylen=markey.length();
 if(corkeylen!=markeylen) return;
String studkey=Line1.getText();
int studkeylen=Line1.getText().length();
if(studkeylen<corkeylen)
{//Line1.setText(Line1.getText()+"A");
 studkey=Line1.getText();
 studkeylen=Line1.getText().length();
 MCQmarks=0;
 for(int i=0;i<studkeylen;i++)
	 if(studkey.charAt(i)==corkey.charAt(i))MCQmarks=MCQmarks+markey.charAt(i)-'0';
 
 String fmtstr=String.format("%02d",MCQmarks);
 btnQN.setText(fmtstr);
}  ///end of if keypadmode==1


}



}

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});

btn1.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) { }

@Override
public void mouseEntered(MouseEvent arg0)
{ if(KeyPadMode==1){ String corkey=Line2.getText();
int corkeylen=corkey.length();
if(corkeylen<1) return;

String markey=Line3.getText();
int markeylen=markey.length();

//if(nmar<1)
String studkey=Line1.getText();
int studkeylen=Line1.getText().length();

if(corkeylen!=markeylen) return;
if(studkeylen<corkeylen)
{Line1.setText(Line1.getText()+"C");
 studkey=Line1.getText();
 studkeylen=Line1.getText().length();
 MCQmarks=0;
 for(int i=0;i<studkeylen;i++)
	 if(studkey.charAt(i)==corkey.charAt(i)) MCQmarks=MCQmarks+markey.charAt(i)-'0';
 
 
 String fmtstr=String.format("%02d",MCQmarks);
 btnQN.setText(fmtstr);
}
}

if(KeyPadMode==0) {inn="1"; tiempo.start();}
}


@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});

btn2.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) { }

@Override
public void mouseEntered(MouseEvent arg0) { if(KeyPadMode==0) { inn="2"; tiempo.start();}}

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});

btn3.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) { }

@Override
public void mouseEntered(MouseEvent arg0)
{ if(KeyPadMode==1){ String corkey=Line2.getText();
int corkeylen=corkey.length();
if(corkeylen<1) return;

String markey=Line3.getText();
int markeylen=markey.length();

//if(nmar<1)
String studkey=Line1.getText();
int studkeylen=Line1.getText().length();

if(corkeylen!=markeylen) return;

if(studkeylen<corkeylen)
{Line1.setText(Line1.getText()+"D");
 studkey=Line1.getText();
 studkeylen=Line1.getText().length();
 MCQmarks=0;
 for(int i=0;i<studkeylen;i++)
	  if(studkey.charAt(i)==corkey.charAt(i)) MCQmarks=MCQmarks+markey.charAt(i)-'0';
 
 String fmtstr=String.format("%02d",MCQmarks);
 btnQN.setText(fmtstr);
}
}

if(KeyPadMode==0) {inn="3"; tiempo.start();}
}
@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});


btn0.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) { }

@Override
public void mouseEntered(MouseEvent arg0) { if(KeyPadMode==0) { inn="0"; tiempo.start();}}

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});

btnAB.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) { }

@Override
public void mouseEntered(MouseEvent arg0)
{ if(KeyPadMode<2){inn="AB"; tiempo.start();} ////In mode 0 &1 put AB directly in the List

}

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});

btn100.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) { }

@Override
public void mouseEntered(MouseEvent arg0) { if(KeyPadMode==0) { inn="100"; tiempo.start();}}

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
});


btnQN.addMouseListener(new MouseListener()
{	@Override
public void mouseClicked(MouseEvent arg0) 
{  String ttt=Line2.getText();
   int len=ttt.length();
   if (len<1) return;
   if(KeyPadMode==2) Line2.setText(ttt.substring(0, len-1)); 
 }

@Override
public void mouseEntered(MouseEvent arg0) 
{if(KeyPadMode==0){inn="VA"; tiempo.start(); ////In mode 0 &1 put VA directly in the List
                 }
}

@Override
public void mouseExited(MouseEvent arg0){inn="";}

@Override
public void mousePressed(MouseEvent arg0) 
{}

@Override
public void mouseReleased(MouseEvent arg0) {}
});


JPanel mainPanel = new JPanel(new BorderLayout());
//mainPanel.setBorder(new EmptyBorder( 8, 8, 8, 8 ));
mainPanel.add(fieldPanel, BorderLayout.NORTH);
mainPanel.add(btnPanel, BorderLayout.CENTER);

//The dialog box is made up of the JDialog containing
//two JLabels (one for the icon, one for message) and
//two JButtons to make an OK/CANCEL selection

// dialog.setSize(300,130);

dialog.addKeyListener(new KeyAdapter() {
    public void keyPressed(KeyEvent e)
    {
        show("pressed");
    }
});

KeyboardFocusManager.getCurrentKeyboardFocusManager()  
.addKeyEventDispatcher(new KeyEventDispatcher() {  
public boolean dispatchKeyEvent(KeyEvent e) {  
    boolean keyHandled = false;  
    if (e.getID() == KeyEvent.KEY_PRESSED)
    { 
    	/*
    	if (e.getKeyCode() == KeyEvent.VK_1)
    	{if(!KeyPadDisplayed)
    		show("1");
    		//curRow=table.getSelectedRow();
    	   //curRow=table.getSelectedColumn();
    		SetData("1",curRow,curCol);
    	    slide();
    	    table.updateUI();
    	    
    	}
    	*/
    	
    	if (e.getKeyCode() == KeyEvent.VK_A)
    	{  if(KeyPadDisplayed && KeyPadMode<2)
    		{
    	    SetData("AB",curRow,curCol);
    		bModified[CurLis]=true;; 
    		slide();
    		//show("A");
    		table.updateUI();
    		return true;
    		}
    	}
    
    	if (e.getKeyCode()>='0' && e.getKeyCode()<='9')
    	{  if(KeyPadMode==1)
    		{if(setNObuff.length()==0) {setNObuff+=(char) e.getKeyCode();}
    		else 
    			{setNObuff+=(char) e.getKeyCode();btnS.setText(setNObuff);
    			int setno=Integer.parseInt(setNObuff); setNObuff="";
    			if(setno==0) setno=1;
    			int LastValidIndex=0;
    			 for(CurSet=0;CurSet<setno;CurSet++)
    			 {String l2=Set[CurLis][CurSet];
    			  String l3=Key[CurLis][CurSet];
    			  if(l3.length()==0 || l2.length()==0 || l2.length()!=l3.length()) 
    				  break;   //// This is invalid CurSet
    				 LastValidIndex=CurSet;
     			 }
    			CurSet=LastValidIndex;     ///in case unexpected invalid set found early
    			
    			Line2.setText(Set[CurLis][CurSet]);Line3.setText(Key[CurLis][CurSet]);
    			String temp=String.format("%02d",CurSet+1);
    			btnS.setText(temp);        ///  Set New Set Number on btn
    	 		
    			
    			}
    	    
    		}
    	
    	}
    	
    	
    	
    if (e.getKeyCode() == KeyEvent.VK_V)
	{  if(KeyPadDisplayed && KeyPadMode<2)
		{
	    SetData("VA",curRow,curCol);
		bModified[CurLis]=true;; 
		slide();
		//show("A");
		table.updateUI();
		return true;
		}
	}
    
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {   if(KeyPadMode==1)
        {
        	Line1.setText("");btnQN.setText("00");
        	String fmtstr=String.format("%02d",MCQmarks);
        	MCQmarks=0;
            SetData(fmtstr,curRow,curCol);bModified[CurLis]=true;
        	slide();
        
            
	   {    ///FLIP NEXT SET BLOck
		CurSet++;
		String l2=Set[CurLis][CurSet];
		String l3=Key[CurLis][CurSet];
	       
	 if(l3.length()==0 || l2.length()==0 || l2.length()!=l3.length())CurSet=0;
	 
	 {   
		 Line2.setText(Set[CurLis][CurSet]);Line3.setText(Key[CurLis][CurSet]);
		 String temp=String.format("%02d",CurSet+1);
		 btnS.setText(temp);	 
	 }
	  }////////END OF FLIP NEXT SET BLOck
	   
        }   
            keyHandled = true;  
        } 
        
        
        if (e.getKeyCode()==32)
            {  
        	if(KeyPadMode==1)
        	{ String corkey=Line2.getText();
            int corkeylen=corkey.length();
            if(corkeylen<1) return true;
            
            String markey=Line3.getText();
            int markeylen=markey.length();
            if(corkeylen!=markeylen) return true;
            String studkey=Line1.getText();
            int studkeylen=Line1.getText().length();
            
            if(studkeylen<corkeylen)
            {Line1.setText(Line1.getText()+"-");
             /*
             studkey=Line1.getText();
             studkeylen=Line1.getText().length();

             MCQmarks=0;
             for(int i=0;i<studkeylen;i++)
            	 if(studkey.charAt(i)==corkey.charAt(i)) MCQmarks=MCQmarks+markey.charAt(i)-'0';
             
             String fmtstr=String.format("%02d",MCQmarks);
             btnQN.setText(fmtstr);
            */
            }
        }
        	
        	keyHandled = true;  
        }  
    }  
    return keyHandled;  
}  
});


dialog.getContentPane().add(mainPanel);

dialog.pack();
dialog.setAlwaysOnTop(true);
//dialog.setVisible(true);
dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
dialog.addWindowListener( new WindowAdapter()
{
public void windowClosing(WindowEvent e)
{
	//dialog.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
Set[CurLis][CurSet]=Line2.getText();	
Key[CurLis][CurSet]=Line3.getText();
KeyPadDisplayed=false;
//dialogcreated=false;
}
});
}
	
	
}









