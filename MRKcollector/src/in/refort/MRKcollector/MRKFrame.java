package in.refort.MRKcollector;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JViewport;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.Color;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MRKFrame extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JButton btnStart;
	private JButton btnAccept;
	private JButton btnReject;
	private JLabel lblDivision;
	private JLabel lblExamination;
	private JLabel lblSubject;
	private JTextField DivField;
	private JTextField ExamField;
	private JTextField SubField;
	private JCheckBox chckbxNewCheckBox;
	private JButton btnLoad;
	private JButton btnSave;
	private JButton btnSelectPrinter;
	private JButton btnCreateDb;
	private JButton btnSort;
	private JButton btnUpdatePT;
    private JLabel Submitted;
    private JButton btnDelete;
    private JButton btnSaveReport;
    private JButton btnPrintMrk;
	private JButton btnSqliteDB;
    private JButton btnHelp;
 	private JButton btnNames;
	private JButton btnRemNems;
	private JButton btnRes02;
	private JButton btnRes03;
	
	private JTable table;
	private JScrollPane scrollPane;
	final String[][] data = new String[20][2];
    String[] col= new String[2];
    int TotalFiles;////in one session
    int fileindex;/////current file in one session
    int TotalReceived; ///Total files received in all sessions
 
    public static void show(String msg) {JOptionPane.showMessageDialog(null, msg);}
    public Object GetData(JTable table, int row_index, int col_index){
  	  return table.getModel().getValueAt(row_index, col_index);
  	  }  

 
	  File folder; 
      File[] listOfFiles; 
    
   
    MRKaddtodb  mrkaddtodb;
   
       
    
    public void SetData(Object obj, int row_index, int col_index)
    {  table.getModel().setValueAt(obj,row_index,col_index);  }

    public void AddRow()
    {DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.addRow(new Object[]{"", "","","",""});
    mrkaddtodb.setTextComponent(table);
    	
    }
    
    public void SetReceivedCount()
    {JTableHeader th = table.getTableHeader();
    TableColumnModel tcm = th.getColumnModel();
    TableColumn tc = tcm.getColumn(1);
    String ttt=String.format("Recd : %d [GT : %d]",TotalReceived,MRKaddtodb.GrandTotal);
    tc.setHeaderValue(ttt);
    th.repaint();
    }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MRKFrame frame = new MRKFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

		
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
        //JOptionPane.showMessageDialog(null, tt);
        //viewport.setViewPosition(cellRect.getLocation());
        
    }
    
	public  void ProcessLists()
	{ if(fileindex>=TotalFiles) {     show("      Finish");        return;}
	
	  while(!listOfFiles[fileindex].isFile()) { fileindex++; if(fileindex>=TotalFiles) 
		                                        {show("over3");
	                                             return;
		                                        } 
		                                        }
	 
	  if(fileindex<TotalFiles)
	  {
	  String files=listOfFiles[fileindex].getAbsolutePath();
	     if (files.endsWith(".mrk") || files.endsWith(".MRK"))
	     { 	 //  strArray.removeAll(strArray);  
	         System.out.println(files);
	         String stemp=MRKaddtodb.ReadFromDisk(files);
	         String temp[]=stemp.split("=");
	         DivField.setText(temp[0].toUpperCase());
	         ExamField.setText(temp[1].toUpperCase());
	         SubField.setText(temp[2].toUpperCase());
	         Submitted.setText(temp[3].toUpperCase());
	         
	         fileindex++;
	         //show(stemp);
	        
	      }
	     else { fileindex++; ProcessLists(); }
	  }
	
	}
	
	public void GetAllFiles() ////in Array listofFiles
	{
	     		
		File f = new File(System.getProperty("java.class.path"));
		File dir = f.getAbsoluteFile().getParentFile();
		dir.toString();
		
				
		//File dir = new File(path);
		listOfFiles = dir.listFiles(new FilenameFilter()
		{
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".mrk");
		    }
		});
		
	   		 // folder = new File(path);
	   		  // = dir.listFiles();
	   		 System.out.println(listOfFiles.length);
	   		 
	}
	
	public void MoveToAccepted()
	{File f = new File(System.getProperty("java.class.path"));
	File dir = f.getAbsoluteFile().getParentFile();
	String path = dir.toString();
	//String fnem=path+"/Result.rlt";
		
	try{
		String fnem=listOfFiles[fileindex-1].getName();
		String oldname=path+"/"+fnem;

 	   File afile =new File(oldname);
 		//show(newname);
 	   if(afile.renameTo(new File(path+"/Accepted/"+fnem)))
 	   {
 		//System.out.println("File is moved successful!");
 	   }else{
 		show("Faild to move file into 'Accpeted' folder !");
 	   }
 	    
 	}catch(Exception e){
 		e.printStackTrace();
 	}
	}
	
public int ReplaceDialog()
{
	String[] buttons = { "Reject Marklist", "Replace all","Replace Empty"};

    int rc = JOptionPane.showOptionDialog(null, "Please Choose Carefully", "Confirmation",
        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[2]);

    return rc;
}	
	
	public void MoveToRejected()
	{File f = new File(System.getProperty("java.class.path"));
	File dir = f.getAbsoluteFile().getParentFile();
	String path = dir.toString();
	//String fnem=path+"/Result.rlt";
		
	try{
		String fnem=listOfFiles[fileindex-1].getName();
		String oldname=path+"/"+fnem;

 	   File afile =new File(oldname);
 		//show(newname);
 	   if(afile.renameTo(new File(path+"/Rejected/"+fnem)))
 	   {//fileindex++;
 		//System.out.println("File is moved successful!");
 	   }else{
 		  show("Faild to move file into 'Rejected' folder !");
 	   }
 	    
 	}catch(Exception e){
 		e.printStackTrace();
 	}
	}

	
	public void CreateFolder()
	{File f = new File(System.getProperty("java.class.path"));
	File dir = f.getAbsoluteFile().getParentFile();
	String path = dir.toString();
//	show(path);
	File d1file = new File(path+"/Accepted");
	if (!d1file.exists()) {
		if (d1file.mkdir()) {
			//System.out.println("Directory is created!");
		} else show("Dir Accepted cannot be created");
	}

	File d2file = new File(path+"/Rejected");
	if (!d2file.exists()) {
		if (d2file.mkdir()) {
			//System.out.println("Directory is created!");
		} else show("Dir Rejected cannot be created");
	}
		
	}
/////////////////////////////CONSTRUCTOR OF MRKFRAME/////////////////////////////	
	/**
	 * Create the frame.
	 */
	public MRKFrame() 
	{
		TotalReceived=0;
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 300);
		
		mrkaddtodb = MRKaddtodb.getInstance();
		
		
		mrkaddtodb.LoadPreferences();
		
		setTitle("MarkList Collector");
	    setSize(600, 409);
	    setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {102, 53, 102, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 28, 0, 0, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.3, 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		final DefaultTableModel model = new DefaultTableModel(data, col)
        { private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        
        CreateFolder();
        
        table = new JTable(model);
		TableColumn column0 = table.getTableHeader().getColumnModel().getColumn(0); 		  
	     column0.setHeaderValue("Pg Total");
	        		  
	     SetReceivedCount();    
	     //(DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.RIGHT);
	     TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
	     JLabel headerLabel = (JLabel)rendererFromHeader;
	     headerLabel.setHorizontalAlignment(JLabel.LEFT); // Here you can set the alignment you want.
	     
	     table.getColumnModel().getColumn(0).setMaxWidth(60);
	     table.getColumnModel().getColumn(1).setPreferredWidth(202);
	     scrollPane = new JScrollPane(table);
	     
	     
	        
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        //table.setRowHeight(30);
	        //table.setIntercellSpacing(new Dimension(8,8));
	        table.getTableHeader().setResizingAllowed(false
	        		);
	          
	        
	        
	        btnStart = new JButton("Start");
	        btnStart.setToolTipText("Start Process of Accepting Marklists");
	        btnStart.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent arg0)
	        	{  fileindex=0;
	        	   GetAllFiles(); 
	        	   TotalFiles=listOfFiles.length;
	        	   ProcessLists();
	        	}
	        });
	        GridBagConstraints gbc_btnStart = new GridBagConstraints();
	        gbc_btnStart.fill = GridBagConstraints.HORIZONTAL;
	        gbc_btnStart.insets = new Insets(0, 0, 5, 5);
	        gbc_btnStart.gridx = 0;
	        gbc_btnStart.gridy = 2;
	        contentPane.add(btnStart, gbc_btnStart);
	        
	        Submitted = new JLabel("Submitted By");
	        GridBagConstraints gbc_Submitted = new GridBagConstraints();
	        gbc_Submitted.insets = new Insets(0, 0, 5, 5);
	        gbc_Submitted.gridx = 2;
	        gbc_Submitted.gridy = 2;
	        contentPane.add(Submitted, gbc_Submitted);
	        
	        lblDivision = new JLabel("Division");
	        GridBagConstraints gbc_lblDivision = new GridBagConstraints();
	        gbc_lblDivision.insets = new Insets(0, 0, 5, 5);
	        gbc_lblDivision.gridx = 0;
	        gbc_lblDivision.gridy = 3;
	        contentPane.add(lblDivision, gbc_lblDivision);
	        
	        DivField = new JTextField();
	        GridBagConstraints gbc_DivField = new GridBagConstraints();
	        gbc_DivField.insets = new Insets(0, 0, 5, 5);
	        gbc_DivField.fill = GridBagConstraints.HORIZONTAL;
	        gbc_DivField.gridx = 2;
	        gbc_DivField.gridy = 3;
	        contentPane.add(DivField, gbc_DivField);
	        DivField.setColumns(5);
	        DivField.addKeyListener(new KeyAdapter()
        { public void keyTyped(KeyEvent e) {
	            char keyChar = e.getKeyChar();
	            if (Character.isLowerCase(keyChar)) {
	              e.setKeyChar(Character.toUpperCase(keyChar));
	            }
	          }
        });
	        table.setFont(new Font("COURIER", Font.PLAIN,12));
		
		table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_Entries = new GridBagConstraints();
		gbc_Entries.gridheight = 14;
		gbc_Entries.gridwidth = 8;
		gbc_Entries.fill = GridBagConstraints.BOTH;
		gbc_Entries.gridx = 4;
		gbc_Entries.gridy = 2;
		contentPane.add(scrollPane, gbc_Entries);
		
		lblExamination = new JLabel("Examination");
		GridBagConstraints gbc_lblExamination = new GridBagConstraints();
		gbc_lblExamination.insets = new Insets(0, 0, 5, 5);
		gbc_lblExamination.gridx = 0;
		gbc_lblExamination.gridy = 5;
		contentPane.add(lblExamination, gbc_lblExamination);
		
		ExamField = new JTextField();
		ExamField.addKeyListener(new KeyAdapter()
	        { public void keyTyped(KeyEvent e) {
			    char keyChar = e.getKeyChar();
			    if (Character.isLowerCase(keyChar)) {
			      e.setKeyChar(Character.toUpperCase(keyChar));
			    }
			  }
	        });
		
		GridBagConstraints gbc_ExamField = new GridBagConstraints();
		gbc_ExamField.insets = new Insets(0, 0, 5, 5);
		gbc_ExamField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ExamField.gridx = 2;
		gbc_ExamField.gridy = 5;
		contentPane.add(ExamField, gbc_ExamField);
		ExamField.setColumns(8);
		
		lblSubject = new JLabel("Subject");
		GridBagConstraints gbc_lblSubject = new GridBagConstraints();
		gbc_lblSubject.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubject.gridx = 0;
		gbc_lblSubject.gridy = 6;
		contentPane.add(lblSubject, gbc_lblSubject);
		
		SubField = new JTextField();
		SubField.addKeyListener(new KeyAdapter()
        { public void keyTyped(KeyEvent e) {
		    char keyChar = e.getKeyChar();
		    if (Character.isLowerCase(keyChar)) {
		      e.setKeyChar(Character.toUpperCase(keyChar));
		    }
		  }        });

		
		GridBagConstraints gbc_SubField = new GridBagConstraints();
		gbc_SubField.insets = new Insets(0, 0, 5, 5);
		gbc_SubField.fill = GridBagConstraints.HORIZONTAL;
		gbc_SubField.gridx = 2;
		gbc_SubField.gridy = 6;
		contentPane.add(SubField, gbc_SubField);
		SubField.setColumns(8);
		
		btnAccept = new JButton("Accept");
		btnAccept.setToolTipText("Accept Current Marklist");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{  //while (mrkaddtodb.printing) return;
				if(chckbxNewCheckBox.isSelected()) { 
					                                 Thread t = new Thread() {
					                                    public void run() { mrkaddtodb.PrintMarkSheet();
					                                                     //  System.out.println("text");
					                                                        // other complex code
					                                                       }
					                                                          };
					                                       t.start();
					                                       try {
															t.join();
														} catch (InterruptedException e1) {
															// TODO Auto-generated catch block
															e1.printStackTrace();
														}
					                                                                   
				                                    }
				
				String subcode=DivField.getText()+"="+ExamField.getText()+"="+SubField.getText();
			  int success=MRKaddtodb.FillMainList(subcode);
			  if(success==0) 
			  { if(fileindex>TotalFiles) {     show("Over1");   return;}
				
			  int option=ReplaceDialog();
			  if(option==0){ MoveToRejected(); ProcessLists();  }
			  if(option==1){ 
				  
				  			if(TotalReceived==1)
				  			{ MRKaddtodb.subLine="";//special case only one marklist
				  		      MRKaddtodb.rollArray.removeAll(MRKaddtodb.rollArray);
                              int nodupnow=MRKaddtodb.FillMainList(subcode);
	                          if(nodupnow==0) show("Error in Replace Routine");
	                          MRKaddtodb.CalculatePageTotal();
	                          MoveToAccepted();
	                          ProcessLists();
                             return;
                            }
				              MRKaddtodb.Remove(subcode); 
			                 int nodupnow=MRKaddtodb.FillMainList(subcode);
			                 if(nodupnow==0) show("Error in Replace Routine");
			                 MRKaddtodb.CalculatePageTotal();
			                 MoveToAccepted();
			                 ProcessLists();
			                 }
			     if(option==2)
			          { show("Replace Empty Only routine not added");  MoveToRejected(); fileindex++; ProcessLists();  
			           }
			  }
			  
			  else  ////Not Duplicate, so add to database
			   {
				  
			    SetData(subcode,TotalReceived,1 );
			    
			    TotalReceived++;
			    MoveToAccepted();
			     
			    if(TotalReceived>5) 
			      { //table.setRowSelectionInterval(curRow,curRow);
			       	Rectangle vr = table.getVisibleRect ();
			     	//int first = table.rowAtPoint(vr.getLocation());
			     	vr.translate(0, vr.height);
			     	int last=table.rowAtPoint(vr.getLocation());
			     	//int visibleRows = last - first;
			     	//int mid=visibleRows/2;

			    	if(last>TotalReceived)
			    	table.scrollRectToVisible(table.getCellRect(TotalReceived-5,0, true));
			    	else
			    		table.scrollRectToVisible(table.getCellRect(TotalReceived+5,0, true));
			           ScrollToLine(scrollPane,table);
			       }
			 
			    AddRow();
			    MRKaddtodb.CalculatePageTotal();
			    SetReceivedCount();
			    ProcessLists();
			  }
			}
		});
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccept.insets = new Insets(0, 0, 5, 5);
		gbc_btnAccept.gridx = 0;
		gbc_btnAccept.gridy = 7;
		contentPane.add(btnAccept, gbc_btnAccept);
		
		chckbxNewCheckBox = new JCheckBox(" Print ");
		chckbxNewCheckBox.setToolTipText("Tick Check Box to Print Marklists as you accept");
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 2;
		gbc_chckbxNewCheckBox.gridy = 7;
		contentPane.add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		chckbxNewCheckBox.setText(" Print ("+mrkaddtodb.PrinterName+")");
		
//////////////////////BUTTON SELECT	PRINTER//////////////////////////////
			
			
btnSelectPrinter = new JButton("Set Printer");
btnSelectPrinter.setToolTipText("Select Default Printer");
btnSelectPrinter.addMouseListener(new MouseAdapter() {
@Override
public void mouseClicked(MouseEvent arg0)
{
	mrkaddtodb.SelectPrinter();
	chckbxNewCheckBox.setText(" Print ("+mrkaddtodb.PrinterName+")");
}
});

btnReject = new JButton("Reject");
btnReject.setToolTipText("Reject Current Marklist");
btnReject.addMouseListener(new MouseAdapter() {
	@Override
	public void mouseClicked(MouseEvent e)
	{
		
		
	//	MoveToRejected();
	//    ProcessLists();
	}
});
GridBagConstraints gbc_btnReject = new GridBagConstraints();
gbc_btnReject.fill = GridBagConstraints.HORIZONTAL;
gbc_btnReject.insets = new Insets(0, 0, 5, 5);
gbc_btnReject.gridx = 0;
gbc_btnReject.gridy = 8;
contentPane.add(btnReject, gbc_btnReject);

GridBagConstraints gbc_btnSelectPrinter = new GridBagConstraints();
gbc_btnSelectPrinter.fill = GridBagConstraints.HORIZONTAL;
gbc_btnSelectPrinter.insets = new Insets(0, 0, 5, 5);
gbc_btnSelectPrinter.gridx = 2;
gbc_btnSelectPrinter.gridy = 8;
contentPane.add(btnSelectPrinter, gbc_btnSelectPrinter);
		
		///////////////////////END OF SELECT PRINTER///////////////////
				
				
				
				
				
				/////////////////////BUTTON DELETE ///////////////////////
				
				btnDelete = new JButton("   Delete   ");
				btnDelete.setToolTipText("Delete Selected Marklist");
				btnDelete.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0)
					{   int temprow=table.getSelectedRow();
					    if(temprow<0 || temprow>=TotalReceived) {show("No Marklist Selected"); return;}
					    
					    
					    if(TotalReceived==1) { MRKaddtodb.subLine="";  ///temprow must be 0 here
					                           ((DefaultTableModel) table.getModel()).removeRow(temprow);
					                           TotalReceived=0;
					                           MRKaddtodb.rollArray.removeAll(MRKaddtodb.rollArray);
					                           MRKaddtodb.CalculatePageTotal();
					   					       SetReceivedCount();
					                           return;
					                          }
					    
					     String temp=(String) GetData(table,temprow,1);
					    
					    if(temprow<TotalReceived) 
					    	{String temp1;
					    	 if(temprow==0) temp1=temp+"#";
					    	 else
					    	  temp1="#"+temp;
					    	 MRKaddtodb.subLine=MRKaddtodb.subLine.replaceAll(temp1,"");
					    	 MRKaddtodb.Remove(temp);
					    	 
					    	 ((DefaultTableModel) table.getModel()).removeRow(temprow);
					    	 TotalReceived--;
					    	 MRKaddtodb.CalculatePageTotal();
						     SetReceivedCount();
					    
					    	 show("Removed : "+temp);
					    	}
						
					}
				});
				
				btnLoad = new JButton("Load"); ///jump temporary
				btnLoad.setToolTipText("Load Last Saved Session");
				btnLoad.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) 
					{
						
						MRKaddtodb.LoadList();
					    String temp[];
						temp=MRKaddtodb.subLine.split("#");
						TotalReceived=temp.length;
					 	//TotalReceived=mrkaddtodb.TotalReceived;
						for(int i=0;i<TotalReceived;i++)
						{SetData(temp[i],i,1 );
						 AddRow();
						}
						
						MRKaddtodb.CalculatePageTotal();
						SetReceivedCount();
					}
				});
				GridBagConstraints gbc_btnLoad = new GridBagConstraints();
				gbc_btnLoad.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnLoad.insets = new Insets(0, 0, 5, 5);
				gbc_btnLoad.gridx = 0;
				gbc_btnLoad.gridy = 9;
				contentPane.add(btnLoad, gbc_btnLoad);
				GridBagConstraints gbc_btnDelete = new GridBagConstraints();
				gbc_btnDelete.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnDelete.insets = new Insets(0, 0, 5, 5);
				gbc_btnDelete.gridx = 2;
				gbc_btnDelete.gridy = 9;
				contentPane.add(btnDelete, gbc_btnDelete);

btnSave = new JButton("Save");
btnSave.setToolTipText("Save this Session");
btnSave.addMouseListener(new MouseAdapter() {
	@Override
	public void mouseClicked(MouseEvent arg0)
	{MRKaddtodb.WriteToDisk();
	 
	}
});

		///////////////////////END OF CREATE DB///////////////
		
		
		
		
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 10;
		contentPane.add(btnSave, gbc_btnSave);
					////////////////////////END OF BUTTON DELETE////////////////////////
					
					
//////////////////////BUTTON SORT//////////////////////////////
					
					
btnSort = new JButton("Sort");
btnSort.setToolTipText("Sorts Marklists in Ascending Order");
btnSort.addMouseListener(new MouseAdapter() {
@Override
public void mouseClicked(MouseEvent arg0)
{   if(TotalReceived==0) { show("No Mark Lists To Sort"); return;}
	mrkaddtodb.TableSort(TotalReceived);
	//mrkaddtodb.SortList(TotalReceived);
}
});

GridBagConstraints gbc_btnSort = new GridBagConstraints();
gbc_btnSort.fill = GridBagConstraints.HORIZONTAL;
gbc_btnSort.insets = new Insets(0, 0, 5, 5);
gbc_btnSort.gridx = 2;
gbc_btnSort.gridy = 10;
contentPane.add(btnSort, gbc_btnSort);
					
					btnUpdatePT = new JButton("Rectify");
					btnUpdatePT.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent arg0)
						{
							
						// MRKaddtodb.CalculatePageTotal();
							
							if(TotalReceived<=0) { show("No Marklist"); return; }
							SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, 100, 1);
					        JSpinner spinner = new JSpinner(sModel);
							
							int option = JOptionPane.showOptionDialog(null, spinner, "No. of Subjects To Rectify", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
							if (option == JOptionPane.CANCEL_OPTION)
							{
							    // user hit cancel
							} else if (option == JOptionPane.OK_OPTION)
							{
								 int sub=(Integer) spinner.getValue();
								 MRKaddtodb.Rectify(sub+1);
								 MRKaddtodb.CalculatePageTotal();
							}
							
					    
						}
					});
					GridBagConstraints gbc_btnUpdatePT = new GridBagConstraints();
					gbc_btnUpdatePT.fill = GridBagConstraints.HORIZONTAL;
					gbc_btnUpdatePT.insets = new Insets(0, 0, 5, 5);
					gbc_btnUpdatePT.gridx = 0;
					gbc_btnUpdatePT.gridy = 11;
					contentPane.add(btnUpdatePT, gbc_btnUpdatePT);
		

		
		///////////////////////END OF BUTTON SORT///////////////////
				
				
			//////////////////////CREATE DB	//////////////////////////////
				
				
				btnCreateDb = new JButton("MySql DB");
				btnCreateDb.setToolTipText("Create MySql Database (In Local MySql Server)");
				btnCreateDb.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0)
					{try {
						MRKaddtodb.CreateMysqlDB();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				});
				
					GridBagConstraints gbc_btnCreateDb = new GridBagConstraints();
					gbc_btnCreateDb.fill = GridBagConstraints.HORIZONTAL;
					gbc_btnCreateDb.insets = new Insets(0, 0, 5, 5);
					gbc_btnCreateDb.gridx = 2;
					gbc_btnCreateDb.gridy = 11;
					contentPane.add(btnCreateDb, gbc_btnCreateDb);
		
		btnSaveReport = new JButton("Save Report");
		btnSaveReport.setToolTipText("Print Report of All Marklists With Page Total");
		btnSaveReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				mrkaddtodb.SetReceivedCount(TotalReceived);
			//	mrkaddtodb.PrintReport();
				mrkaddtodb.SaveReport();
			}
		});
		GridBagConstraints gbc_btnSaveReport = new GridBagConstraints();
		gbc_btnSaveReport.insets = new Insets(0, 0, 5, 5);
		gbc_btnSaveReport.gridx = 0;
		gbc_btnSaveReport.gridy = 12;
		contentPane.add(btnSaveReport, gbc_btnSaveReport);
		
		btnPrintMrk = new JButton("Print MRK");
		btnPrintMrk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{int temprow=table.getSelectedRow();
		    if(temprow<0 || temprow>=TotalReceived) {show("No Marklist Selected"); return;}
		    String temp=(String) GetData(table,temprow,1);
		    String temp1=(String) GetData(table,temprow,0);
		   mrkaddtodb.SetSubjectCodeTriplet(temp,temp1);
		   // mrkaddtodb.RecreateAndPrintMarkSheet();
		   mrkaddtodb.PickAndPrintML();
				
			}
		});
		btnPrintMrk.setToolTipText("Print Selected Marklist");
		GridBagConstraints gbc_btnPrintMrk = new GridBagConstraints();
		gbc_btnPrintMrk.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPrintMrk.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrintMrk.gridx = 2;
		gbc_btnPrintMrk.gridy = 12;
		contentPane.add(btnPrintMrk, gbc_btnPrintMrk);
		
		
		btnSqliteDB = new JButton("Sqlite DB");
		btnSqliteDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{show("Sqlite DB routine to be added");
				
			}
		});
		btnSqliteDB.setToolTipText("Create Sqlite DB file");
		GridBagConstraints gbc_btnSqliteDB = new GridBagConstraints();
		gbc_btnSqliteDB.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSqliteDB.insets = new Insets(0, 0, 5, 5);
		gbc_btnSqliteDB.gridx = 0;
		gbc_btnSqliteDB.gridy = 13;
		contentPane.add(btnSqliteDB, gbc_btnSqliteDB);
		//////////////////////////END OF SQLITE DB///////////////
		
		/////////////////////////START OF GET NAMES///////////////////////
		btnNames = new JButton("Get Names");
		btnNames.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{mrkaddtodb.GetNames();
				
			}
		});
		btnNames.setToolTipText("Get Names From Text File in Roll#Name Format");
		GridBagConstraints gbc_btnNames = new GridBagConstraints();
		gbc_btnNames.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNames.insets = new Insets(0, 0, 5, 5);
		gbc_btnNames.gridx = 0;
		gbc_btnNames.gridy = 14;
		contentPane.add(btnNames, gbc_btnNames);
		//////////////////////END OF GET NAMES //////////////////
		
/////////////////////////START RMOVE NAMES///////////////////////
btnRemNems = new JButton("Del Names");
btnRemNems.addMouseListener(new MouseAdapter() {
@Override
public void mouseClicked(MouseEvent e) 
{MRKaddtodb.DeleteNames();
MRKaddtodb.CalculatePageTotal();
}
});
btnRemNems.setToolTipText("Delete All Names");
GridBagConstraints gbc_btnRemNems = new GridBagConstraints();
gbc_btnRemNems.fill = GridBagConstraints.HORIZONTAL;
gbc_btnRemNems.insets = new Insets(0, 0, 5, 5);
gbc_btnRemNems.gridx = 2;
gbc_btnRemNems.gridy = 14;
contentPane.add(btnRemNems, gbc_btnRemNems);
//////////////////////END OF GET RemNems //////////////////
		
		
/////////////////////////START OF GET Res02///////////////////////
btnRes02 = new JButton("res02");
btnRes02.addMouseListener(new MouseAdapter() {
@Override
public void mouseClicked(MouseEvent e) 
{show("Getres02 routine to be added");

}
});
btnRes02.setToolTipText("Reserved button Res02");
GridBagConstraints gbc_btnres02 = new GridBagConstraints();
gbc_btnres02.fill = GridBagConstraints.HORIZONTAL;
gbc_btnres02.insets = new Insets(0, 0, 5, 5);
gbc_btnres02.gridx = 0;
gbc_btnres02.gridy = 15;
contentPane.add(btnRes02, gbc_btnres02);
//////////////////////END OF GET RES02 //////////////////
		


/////////////////////////START OF GET Res03///////////////////////
btnRes03 = new JButton("Res03");
btnRes03.addMouseListener(new MouseAdapter() {
@Override
public void mouseClicked(MouseEvent e) 
{show("GetRes03 routine to be added");

}
});
btnRes03.setToolTipText("Reserved button Res03");
GridBagConstraints gbc_btnRes03 = new GridBagConstraints();
gbc_btnRes03.fill = GridBagConstraints.HORIZONTAL;
gbc_btnRes03.insets = new Insets(0, 0, 5, 5);
gbc_btnRes03.gridx = 2;
gbc_btnRes03.gridy = 15;
contentPane.add(btnRes03, gbc_btnRes03);
//////////////////////END OF GET RES03 //////////////////





		
		
		btnHelp = new JButton("Help !");
		btnHelp.setToolTipText("Display Help Screen");
		btnHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{String multiLineMsg[] = { "This free and open source application collects marklists created from Mobile App 'MarkList'",
	                  "which is available on playstore. It compiles them in to one simple text file - Result.rlt. You can ",
	                  "create MySql database, provided XAMPP or WAMP MySql Local Server installed and is running.",
	                  " ",
	                  " 1. Keep all your .mrk files in the same folder where MRKcollector.jar is kept.",
	                  " 2. Run the MRKcollector. Press [Start] once. This prepares set of all available marklists.",
	                  " 3. Now keep on accepting marklists by pressing [Accept] repeatedly till all the lists are over.",
	                  " 4. Save the compilation in the default file Result.rlt. It will overwrite earlier compilation.",
	                  " 5. In the next session [Load] the compilation from default Result.rlt and repeat the process.",
	                  " 6. Set the default printer and Tick the print checkbox, to print mark lists on-the-fly.",
	                  " 7. Delete the marklist from the compilation if you want by selecting list and pressing [Delete].",
	                  " 8. Accepted Mark Lists will be moved to Accepted folder and Rejected will go to Rejected folder.",
	                  " 9. Folders will be created automatically on first run if they do not exists.",
	                  "10. Press [sort] to sort list of files in order to locate them easily or before you print a report.",
	                  "11. Your IT department can prepare result directly from Result.rlt or by using mySql or Sqlite Database",
	                  "12. You can make single sqlite db file if you want to use Sqlite databse for result processing.",
	                  "13. A report of all collected marklists can be printed,with page total, for verification purpose.",
	                  "14. MySql database can be used to uload marks on your School/College website for students and parents.",
	                  "15. We recommend BOSS, 'Bharat Operating System Solution', India's Linux, if your institution is in India.",
	                  "16. For free assistance or suggestions, please email us at bossresult@gmail.com or bossresult@gmx.com.",
	                  "17. The sourcecode of this application is available at http://sourceforge.net/projects/marklist/",
	                  "18. Do not use pirated software, always use free and open source software. We are always there to help.",
	                  " "
	                  } ;
JOptionPane.showMessageDialog (null, multiLineMsg,"Help",JOptionPane.PLAIN_MESSAGE);   
			
			}
		});
		GridBagConstraints gbc_btnHelp = new GridBagConstraints();
		gbc_btnHelp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHelp.insets = new Insets(0, 0, 5, 5);
		gbc_btnHelp.gridx = 2;
		gbc_btnHelp.gridy = 13;
		contentPane.add(btnHelp, gbc_btnHelp);
		
			}
////////////////////////END OF CONSTRUCTOR/////////////////////////////
	public static boolean isCellVisible(JTable table, int rowIndex, int vColIndex) {
	    if (!(table.getParent() instanceof JViewport)) {
	      return false;
	    }
	    JViewport viewport = (JViewport) table.getParent();
	    Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);
	    Point pt = viewport.getViewPosition();
	    rect.setLocation(rect.x - pt.x, rect.y - pt.y);
	    return new Rectangle(viewport.getExtentSize()).contains(rect);
	  }
	

	
		
}
