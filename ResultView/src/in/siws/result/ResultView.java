package in.siws.result;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

public class ResultView extends JFrame implements Printable 
{ 
	JLabel label,GTlabel;
	static String fylename="";
    int grandtotal=0;	
    int GT1200=0;
	public static Object GetData(int row_index, int col_index)
	{ return table2.getModel().getValueAt(row_index, col_index); }  

	public static void show(String msg)
	{JOptionPane.showMessageDialog(null, msg);}
	public static void show(int msg)
	{JOptionPane.showMessageDialog(null, msg);}
	
	public static void show(long msg)
	{JOptionPane.showMessageDialog(null, msg);}
	
///////////////////START OF VARIABLES//////////////////////////////////////
	int leftmargin=80,topmargin=72;
	
	static ArrayList<String> strArray = new ArrayList<String>();
	static ArrayList<String> strMod = new ArrayList<String>();
	
	int ROWS=9;
    int COLS=12;
    int dist=0; ////moderation-distance : marks for passing the student
    String[][] Matrix = new String[ROWS][COLS];
String Stream="Commerce"; ///default stream. If found PHY || CHE || BIO change to Science
String Roll="0000";
int subtotal[]={0,0,0,0,0,0,0,0};
int grace[]   ={0,0,0,0,0,0,0,0};
int average[] ={0,0,0,0,0,0,0,0};
int grand[]=   {0,0,0,0,0,0,0,0};
int gracecount=0;
int gracetotal=0;
String Remark;

String StaticMatrix[][]={ 
                {"Terminal-I  (A)","50",""},
                {"Terminal-II (B)","100",""},
                {"Unit Test-I (C)","25",""},
                {"Unit Test-II(C)","25",""},                              
                {"Aggregate (A+B+C)","200","70"},
                {"Average (A+B+C)/2","100","35"},
                {"Grace","30",""}                             
               }; ////3 rows 8 columns



int subindex=0;
///This is sample data from mobile mark list application


String Order[]={"ENG","MAR","TAM","HIN","ITE","MAT","PHY","CHE","BIO","SEP","ECO","BKE","OCM","EVS","PTE","CS1","CS2","EL1","EL2"};
String Row[]={"T1","T2","U1","U2"};
public int currentindex=0;
boolean found;

private static final long serialVersionUID = 1L;
static JTable table2;
 private DefaultTableModel model2;
 private JScrollPane scrollPane2;
 
SetPrinter sp;



/////////////////////END OF VARIABLES //////////////////////

	
	
//////////////////// CONSTRUCTOR////////////////////////	
   
    public ResultView() 
    {  
    	
    	sp=SetPrinter.getInstance();
    	
    	
    //	String PrinterName="No Printer Set";
    	Object[][] data2 = 	{  {" "," ", " "," "," "," "," "," ", " "," "," "," " } }; /////define data types    
    	   
    	   String[] col2= new String[COLS];	
    	   model2 = new DefaultTableModel(data2, col2)
    	   { private static final long serialVersionUID = 2L;

    	       @Override
    	       public boolean isCellEditable(int row, int column)
    	       {
    	          //all cells false
    	          return true;
    	       }
    	   };
    	   
 table2 = new JTable(model2){
    		   
	private static final long serialVersionUID = 1L;

			@Override
    		    public Dimension getPreferredScrollableViewportSize() 
    		   {//Toolkit toolkit =  Toolkit.getDefaultToolkit ();
        	  //  Dimension dim = toolkit.getScreenSize();

    		       int h = Math.min(9, table2.getRowCount());
    		       return new Dimension(getPreferredSize().width, table2.getRowHeight() * h);
    		        
    		       // Dimension size = table2.getPreferredScrollableViewportSize();
    		       // return new Dimension(Math.min(getPreferredSize().width, size.width), size.height);
    		    }
    		    
    	   };

    	   scrollPane2 = new JScrollPane(table2);
    	   table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	   table2.setRowHeight(30);
    	   table2.setIntercellSpacing(new Dimension(8,8));
    	   table2.getTableHeader().setResizingAllowed(false);
    	   table2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
    	   table2.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 16));
    	   table2.getColumnModel().getColumn(0).setMinWidth(200);
    	   table2.getColumnModel().getColumn(1).setMinWidth(50);
    	   table2.getColumnModel().getColumn(2).setMinWidth(50);
    	   table2.getColumnModel().getColumn(3).setMinWidth(50);
    	   table2.getColumnModel().getColumn(4).setMinWidth(50);
    	   table2.getColumnModel().getColumn(5).setMinWidth(50);
    	   table2.getColumnModel().getColumn(6).setMinWidth(50);
    	   table2.getColumnModel().getColumn(7).setMinWidth(50);
    	   table2.getColumnModel().getColumn(8).setMinWidth(50);
    	   table2.getColumnModel().getColumn(9).setMinWidth(50);
    	   table2.getColumnModel().getColumn(10).setMinWidth(50);
    	   table2.getColumnModel().getColumn(11).setMinWidth(110);
    	  // table2.setVisibleColumnCount(10); 
    	   DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    	   centerRenderer.setHorizontalAlignment( JLabel.CENTER );
    	   for(int col=0;col<COLS;col++)
    	   table2.getColumnModel().getColumn(col).setCellRenderer( centerRenderer );
           
    	  
    	   ((DefaultTableCellRenderer)table2.getTableHeader().getDefaultRenderer())
    	    .setHorizontalAlignment(JLabel.CENTER);
    	   
    	   //JTableHeader header = table2.getTableHeader();
    	   //header.setRenderer(centerRenderer);
    	    
    	   table2.getColumnModel().getColumn(0).setHeaderValue("Examination");
    	   table2.getColumnModel().getColumn(1).setHeaderValue("Max");
    	   table2.getColumnModel().getColumn(2).setHeaderValue("Min");
    	   table2.getColumnModel().getColumn(3).setHeaderValue("Sub1");
    	   table2.getColumnModel().getColumn(4).setHeaderValue("Sub2");
    	   table2.getColumnModel().getColumn(5).setHeaderValue("Sub3");
    	   table2.getColumnModel().getColumn(6).setHeaderValue("Sub4");
    	   table2.getColumnModel().getColumn(7).setHeaderValue("Sub5");
    	   table2.getColumnModel().getColumn(8).setHeaderValue("Sub6");
    	   table2.getColumnModel().getColumn(9).setHeaderValue("Sub7");
    	   table2.getColumnModel().getColumn(10).setHeaderValue("Sub8");
    	   table2.getColumnModel().getColumn(11).setHeaderValue("Grand");
    	//   table2.setGridColor(Color.BLUE);
    	
    	   
    	  
    	   
    	   
    	   table2.setBorder(new EtchedBorder(EtchedBorder.RAISED));
    	   table2.setShowHorizontalLines(true);
    	   table2.setShowVerticalLines(true);
          scrollPane2 = new JScrollPane(table2);
         ////////Set Table Header height ////////////////// 
          scrollPane2.setColumnHeader(new JViewport() 
          {  
			private static final long serialVersionUID = 1L;

			@Override public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.height =30;
                return d;
              }
          });
          
          //////End Of Set Table Header height //////////////////
        
        JButton buttonLoad = new JButton("Load");
        buttonLoad.addActionListener(new ActionListener()
        {

			public void actionPerformed(ActionEvent arg0) 
			{
				ReadFromDisk();
				if(fylename=="") return;
				FillMatrix(currentindex);
				FillDistance();
				ShowMatrix();
				CalculateGT();
				
			}
        });
        JButton buttonNext = new JButton("Next");
        buttonNext.addActionListener(new ActionListener() 
        {
        	
            public void actionPerformed(ActionEvent arg0) 
            {
            
             if(currentindex<strArray.size()-1)
             {currentindex++;
         
             FillMatrix(currentindex);
             FillDistance();
             ShowMatrix();
             }
            
            }
        });

        JButton buttonPrev = new JButton("Prev");
        buttonPrev.addActionListener(new ActionListener() 
        {

            public void actionPerformed(ActionEvent arg0) 
            {if(currentindex>0)
            	{ currentindex--;  
            	  FillMatrix(currentindex);
            	  FillDistance();
            	  ShowMatrix();
            	}
               
            }
        });


        JButton buttonUpdate = new JButton("Update");
        buttonUpdate.addActionListener(new ActionListener() 
        {  

            public void actionPerformed(ActionEvent arg0) 
            { CalculateGT();
            }
        });


        JButton buttonPrint = new JButton("Print");
        buttonPrint.addActionListener(new ActionListener() 
        {

            public void actionPerformed(ActionEvent arg0) 
            {
              PrintResultCard();  
            }
        });

        
        JButton buttonPrintAll = new JButton("Print All");
        buttonPrintAll.addActionListener(new ActionListener() 
        {

            public void actionPerformed(ActionEvent arg0) 
            {
               
            }
        });
        
        JButton buttonJump = new JButton("Jump");
        buttonJump.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {  String temp[];
            	String name = JOptionPane.showInputDialog(null, "Roll Number To Jump");
            	for(int i=0;i<strArray.size();i++)
            	{
            		temp=strArray.get(i).split("#");
            		if(temp[0].contains(name)) 
            			{ currentindex=i; FillMatrix(currentindex);FillDistance();ShowMatrix(); return;}
            	}
               
            }
        });

       
        JButton buttonSetPrinter = new JButton("Set Printer");
        buttonSetPrinter.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
             sp.SelectPrinter();
             
            label.setText(sp.PrinterName);
          //  Dimension d = label.getPreferredSize();
            
            //label.setPreferredSize(new Dimension(d.width+30,d.height));
         //   label.setBorder(border);
               
            }
        });

       
        
        JButton buttonStat = new JButton("Stat");
        buttonStat.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0)
            {if(strArray.size()==0) { show("No Data"); return;}
             int scicount=0,scifailcount=0,scipasscount=0;
             int comcount=0,comfailcount=0,compasscount=0;
            	
             String temp;
             for(int i=0;i<strArray.size();i++)
             {  
            	temp=strArray.get(i);
            	FillMatrix(i);
            	if(temp.contains("PHY:"))
            	{   scicount++;
            		if(Remark=="FAIL") scifailcount++;
            		else scipasscount++;
            	}
            	else
            	{comcount++;
            	if(Remark=="FAIL") comfailcount++;
            	else compasscount++;
            	}
            	
             }
             
             String plate;
             plate=String.format("Science  : %d\nPass : %d\nFail : %d\n\n" +
             					 "Commrece : %d\nPass : %d\nFail : %d\n",
             					 scicount, scipasscount,scifailcount,
             					 comcount, compasscount,comfailcount);
             show(plate);
             
            }
        });


        JButton buttonMod = new JButton("Mod");
        buttonMod.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent arg0)
            {if(strArray.size()==0) { show("No Data"); return;}
             strMod.removeAll(strArray);
             String plate;
             int totalrecords=strArray.size();
             for(int i=0;i<totalrecords;i++) 
             { FillMatrix(i);
               if(!Remark.contains("FAIL")) continue;
               FillDistance();
               
              // ShowMatrix();
               plate=String.format("%4d : ",dist);
              plate=plate+"Roll :"+Roll+" = ";
              String tmpstr="";
              for(int j=3;j<9;j++)
                 { plate+="[";
                   plate=plate+table2.getColumnModel().getColumn(j).getHeaderValue();         ///subject name
                   tmpstr=(String) GetData(8,j);
                   if(tmpstr.length()==0) tmpstr="00";
                   if(tmpstr.length()==1) tmpstr+=" ";
                   plate=plate+"-"+tmpstr+"] "; /// moderation level per subject
                 }
              
              strMod.add(plate);
             }
            Collections.sort(strMod);
            SaveModReport();
         }
        });

        
        
       label= new JLabel("No Printer Set",JLabel.CENTER);
       GTlabel= new JLabel("GT=0",JLabel.CENTER);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
       
      //   border = label.getBorder();
        Border margin = new EmptyBorder(3,10,3,10);
        label.setBorder(new CompoundBorder(border, margin));
        GTlabel.setBorder(new CompoundBorder(border, margin));
        
      //  Dimension d = label.getPreferredSize();
      //  label.setPreferredSize(new Dimension(d.width+30,d.height+8));
     //   label.setBorder(border);
        
        
        
        ResizeTable2(table2,ROWS);
        JPanel northPanel=new JPanel();
        northPanel.add(GTlabel);
        northPanel.add(buttonSetPrinter);
        northPanel.add(label);
        add(northPanel,BorderLayout.NORTH);
        JPanel centrePanel=new JPanel();
        centrePanel.add(scrollPane2);
        add(centrePanel,BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(buttonLoad);
        southPanel.add(buttonNext);
        southPanel.add(buttonPrev);
        southPanel.add(buttonStat);
        southPanel.add(buttonMod);
        southPanel.add(buttonUpdate);
        southPanel.add(buttonPrint);
        southPanel.add(buttonPrintAll);
        southPanel.add(buttonJump);
        //southPanel.add(buttonSetPrinter);
        
        add(southPanel, BorderLayout.SOUTH);
        
       // FillMatrix(CurrentIndex);
       // ShowMatrix();
        
        for(int i=0;i<7;i++)
 		   for(int j=0;j<3;j++)
 		   {
 			   SetData(StaticMatrix[i][j],i,j);
 			   
 		   }
        
        
     sp.LoadPreferences();
     label.setText(sp.PrinterName);
     //GTlabel.setText("GT=");
        
    } /////END OF CONSTRUCTOR////////////////////

    public Action updateCol() {
        return new AbstractAction("text load action") {

            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e)
            {
              
            }
        };
    }

    
    
    public void ResizeTable2(JTable tablename,int numberofrows)
	 { DefaultTableModel model=(DefaultTableModel) tablename.getModel();
    int totalrows=tablename.getRowCount();
    int difference=numberofrows-totalrows;
    if(difference>0)
    {                               
       for(int i=0;i<difference;i++) model.addRow(new Object[]{""," ", " "," "," "," "," "," "});
     }  
    if(difference<0)
	   { difference=-difference;
       for(int i=0;i<difference;i++) model.removeRow(0);
	  }
		 
	 }
 public void CalculateGT()
 {  grandtotal=0;
    for(int i=0;i<strArray.size();i++)
    {FillMatrix(i);grandtotal=grandtotal+GT1200;
    }
    String ttt;
    ttt=String.format("%d",grandtotal);
    GTlabel.setText(ttt);
 }  
    public void FillMatrix(int currindex)
	 { for(int i=0;i<ROWS;i++)
			 for(int j=0;j<COLS;j++) Matrix[i][j]="";
		 //for(int i=0;i<8;i++)
		//	 for(int j=0;j<9;j++) Matrix[i][j]="";
		 
		 for(int i=0;i<8;i++) subtotal[i]=grace[i]=average[i]=grand[i]=0;
					 
		 
	     String temp[];
         String sub[];
         String OneStudent=strArray.get(currindex);
        // show(CurrentIndex);
         temp=OneStudent.split("#");  ///Make pieces of the record cutting at #
         Roll=temp[0]; ///Get Roll Number
         if(temp[1].length()>30) Roll+="       Name :     "+temp[1];
         
         this.setTitle("Roll No : "+ Roll);
         // now array temp is filled with pieces
         // temp[0]="101"
      	 // temp[1]="A=T1=TAM:42"
      	 // temp[2]="A=T1=ENG:23"
         
         subindex=0;
         gracecount=0;
         gracetotal=0;
         int evs=0;
	     
      for(int i=0;i<Order.length;i++)
	     {   found=false;
	    	 for(int j=1;j<temp.length;j++)
	    	 {  if(temp[j].length()>30) continue;
	    	    if(temp[j].contains(Order[i])) ///Got Subject
	    	      { found=true;
	    	    	Matrix[0][subindex]=Order[i];
	               // show(temp[j]);
	    	    	sub=temp[j].split("="); //Cut further at "="
	                String marks[];
	                marks=sub[2].split(":");
	               // show(marks[1]);
	                marks[1]=marks[1].trim();
	                int strtointeger=0;
	                
	                if(marks[1].length()!=0 && !marks[1].contains("AB")) 
	                
	                strtointeger=Integer.parseInt(marks[1].replaceAll("[^0-9.]",""));
	                subtotal[subindex]=subtotal[subindex]+strtointeger;
	                
	                //show(subtotal[subindex]);
	                
	                if(temp[j].contains("T1")) { Matrix[1][subindex]=marks[1]; grand[1]=grand[1]+strtointeger;} 
	                if(temp[j].contains("T2")) { 
	                	                        int original=0;
	                	                        if(Matrix[2][subindex]!="")
	                	                        original=Integer.parseInt(Matrix[2][subindex].replaceAll("[^0-9.]",""));
	                                            original=original+strtointeger;
	                                            if(!temp[j].contains("EVS") && !temp[j].contains("PTE"))
	                                            grand[2]=grand[2]+strtointeger;
	                                            if(temp[j].contains("EVS")) evs=strtointeger;     
	                                            String plate00=String.format("%02d",original);
	                	                        Matrix[2][subindex]=plate00;
	                                            }
	                if(temp[j].contains("U1")) { Matrix[3][subindex]=marks[1]; grand[3]=grand[3]+strtointeger;}
	                if(temp[j].contains("U2")) { Matrix[4][subindex]=marks[1]; grand[4]=grand[4]+strtointeger;}
	    	      }
	    	  }
	    	 
	    	 if(found) { String tempstr=String.format("%02d",subtotal[subindex]);
	    		         Matrix[5][subindex]=tempstr;
	    		         average[subindex]=subtotal[subindex]/2;
	    		         if(subtotal[subindex]%2==1) average[subindex]++;
	    		         tempstr=String.format("%02d",average[subindex]);
	    		         Matrix[6][subindex]=tempstr;
	    		         if(average[subindex]<35 && subindex<6) ///no grace for evs and pte
	    		        	 {grace[subindex]=35-average[subindex];
	    		        	  gracecount++;
	    		        	  gracetotal=gracetotal+grace[subindex];
	    		        	 }
	    		         else
	    		        	 grace[subindex]=0;
	    		         tempstr=String.format("%d",grace[subindex]);
	    		         Matrix[7][subindex]=tempstr;
	    	             subindex++;
	    	            }
	    	 
	    	 }
      
     ////Fill 8th column
      GT1200=0;
      String plate;
      Matrix[0][8]="Grand";
      for (int i=1; i<5;i++) { GT1200=GT1200+grand[i];
    	                       plate=String.format("%d",grand[i]);
                               Matrix[i][8]=plate; 
    	                      }
      
      plate=String.format("%d/1200",GT1200);
      Matrix[5][8]=plate;
      int avgtotal=0; 
      for (int i=0; i<6;i++)  avgtotal=avgtotal+average[i]; 
      
      plate=String.format("%d/650",avgtotal+evs);
      Matrix[6][8]=plate;
      
      if (gracecount>3 || gracetotal>30) { Remark="FAIL"; return; }
      
      for (int i=0; i<6;i++) if(grace[i]>10) { Remark="FAIL"; return; }
      
      plate=String.format("%d/30",gracetotal);
      Matrix[7][8]=plate;
      
      for (int i=0; i<6;i++) if(grace[i]> 0) { Remark="PROMOTED"; return; }
      
      
      int percentage=avgtotal/6;
      if(avgtotal%6!=0) percentage++;
      
      if(percentage>75) { Remark="Grade I"; return; }
      if(percentage>60) { Remark="Grade II"; return; }
      if(percentage>45) { Remark="Grade III"; return; }
      
      Remark="Grade Pass"; 
	  
	 }
    
    public void FillDistance()
    {String plate;
     dist=0;
     for(int i=0;i<6;i++) SetData(" ",8,3+i);
     
     if(!Remark.contains("FAIL")) { plate=String.format("%d",dist);  SetData(plate,8,2); return;}
     int sort[]={0,1,2,3,4,5};
     int temp;
     for(int i=0;i<5;i++)
    	 for(int j=i+1;j<6;j++)
    		 if(subtotal[sort[i]]>subtotal[sort[j]]) 
    		  {temp=sort[i];sort[i]=sort[j];sort[j]=temp; }
     
     for(int i=0;i<3;i++) if(subtotal[sort[i]]<49)        
      {  int gap=49-subtotal[sort[i]];
         plate=String.format("%d",gap);
    	 SetData(plate,8,3+sort[i]);
    	 dist=dist+gap;
      }
     
     for(int i=3;i<6;i++) if(subtotal[sort[i]]<69)
    	 { int gap=69-subtotal[sort[i]];
         plate=String.format("%d",gap);
    	 SetData(plate,8,3+sort[i]);
    	 dist=dist+gap;
    	 }
     
     plate=String.format("%d",dist);
     SetData(plate,8,2);
    }
    
    public static void SetData(Object obj, int row_index, int col_index)
    {  table2.getModel().setValueAt(obj,row_index,col_index);  }

    
    public void ShowMatrix()
	 {   
    	JTableHeader th = table2.getTableHeader();
    	TableColumnModel tcm = th.getColumnModel();
    	TableColumn tc ;
    	//tc.setHeaderValue( "???" );
    	
    	
		 for(int j=3;j<COLS;j++){tc= tcm.getColumn(j); tc.setHeaderValue(Matrix[0][j-3]);th.repaint();}
		
		 for(int i=1;i<ROWS-1;i++)
			 for(int j=0;j<COLS-3;j++) SetData(Matrix[i][j],i-1,j+3);
		 
		 SetData(Remark,8,0);
	 }
    
    /////////////////////////Load Result Data in Array/////////////

    
    public static void ReadFromDisk()
    {   //String fnme="";
		
    	File f = new File(System.getProperty("java.class.path"));
    	File dir = f.getAbsoluteFile().getParentFile();
    	String path = dir.toString();
    	
    	fylename=path+="/Result.rlt";
    	//show(fylename);
	    File varTmpDir = new File(fylename); 
	    boolean exists = varTmpDir.exists();
		
		if(!exists)
		{fylename="";
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "RLT", "rlt");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    {
	    	fylename=chooser.getSelectedFile().getPath();
	    
	    //   show("You chose to open this file: " + fnem);
	       
	    }
		if(fylename=="") return;
		
		}
		strArray.removeAll(strArray);
    	BufferedReader reader=null;
		try {
			reader = new BufferedReader(new FileReader(fylename));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
				
		String line = null;
    	try { while ((line = reader.readLine()) != null) 
			{
			 if(line.contains(":")) strArray.add(line);
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    
    }
 //////Save Moderation Report//////////////////////////////////////////
    public void SaveModReport()
    {
  	  
  		String fnem="/home/milind/Result-Moderation-Report.txt";
  			
  	     FileWriter f0=null;
  		 try {f0 = new FileWriter(fnem);	} catch (IOException e1) {e1.printStackTrace();	}
  	     String newLine = System.getProperty("line.separator");
  	     
  	     
  	     
  	     
  	     for(int i=0;i<strMod.size();i++)
  	     {   
  	    	
  	         try { f0.write(strMod.get(i));	} catch (IOException e) {e.printStackTrace();	}
  	        try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}  
           }

  	     try {f0.close();} catch (IOException e) {e.printStackTrace();}
  
}

///////////////////END OF SAVE MODERATION REPORT////////////////////////////////////
    
    
	
	int JobNem=0;
	
	
	
		 public PrintService findPrintService(String printerName)
	    {
	        for (PrintService service : PrinterJob.lookupPrintServices())
	        {
	            if (service.getName().equalsIgnoreCase(printerName))
	                return service;
	        }

	        return null;
	    }

		 
	
    public void PrintResultCard()
    {	
    	
    	
    	//if(TotalReceived==0) { show("No Marklists To Prints"); return; }
    	JobNem=0;
        //NumberOfReportPages=TotalReceived/EntriesPerPage;
        //if(TotalReceived%EntriesPerPage!=0) NumberOfReportPages++;
    
        PrintService ps = findPrintService("pdf");                                    
        //create a printerJob
        PrinterJob job = PrinterJob.getPrinterJob();            
        //set the printService found (should be tested)
        try {
			job.setPrintService(ps);
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
        //set the printable (an object with the print method that can be called by "job.print")
        job.setPrintable(this);       
        job.setJobName("ResultCard");
        //call je print method of the Printable object
        
        HashPrintRequestAttributeSet pattribs=new HashPrintRequestAttributeSet();
        pattribs.add(new MediaPrintableArea(2, 2, 210, 297, MediaPrintableArea.MM));
        // 210 x 297  A4 size paper
        
        
        
        
        try {
        	job.print(pattribs);
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

	
    
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException 
			{
				switch(JobNem)
					{case 0  : return PrintResults(g,pf,page);
					 case 1  :
					 default :  return PrintConsolidated(g,pf,page);
					}
	        
		     }


	private void Centre(String s, int width, int XPos, int YPos,Graphics g2d)
	  { int stringLen = (int)  g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();  
		int start = width/2 - stringLen/2; g2d.drawString(s, start + XPos, YPos);  
	   }

	
	
	
	public int PrintResults(Graphics g, PageFormat pf, int pageno)
			throws PrinterException
	{ 
		/* half A4 size
    Paper paper = pf.getPaper();
    pf.setOrientation(PageFormat.PORTRAIT);
    paper.setSize(8*72,5*72);
    pf.setPaper(paper);
		  */
		
		           ///strArray.size()-2
		 if (pageno>3)    // MultipageDoc 'page no' is zero-based
		    {  return NO_SUCH_PAGE;  // After NO_SUCH_PAGE, printer will stop printing.
	        }
		 
		// OneStudent=strArray.get(pageno+1);
		 // show(OneStudent);
	     FillMatrix(pageno);
	
		 int w[]={59,38,38,38,38,38,38,38,38,38,38,59};
		 int ws[]={79,38,38};
		 
		 Font MyFont = new Font("Liberation Serif", Font.PLAIN,8);
		 
		 int tlxstatic=60;
		 int tlx=leftmargin,tly=topmargin;
	
	     /// 72 dots per inch, leave left & top margin 1 inch each 
		 /// on actual paper, printer also leaves some margin
	     int h=20,downshift=13;
	     g.setFont(MyFont);
     
	     g.drawString("Roll : "+Roll,tlxstatic,tly-20);
	     
	     //// Other routine table parts here
	     
	     
	     for(int i=0;i<7;i++)
	       { tlxstatic=60;
	    	 for(int j=0;j<3;j++)
	         {   g.drawRect(tlxstatic,tly+i*h,ws[j],h);
	    		 Centre(StaticMatrix[i][j],ws[j],tlxstatic,tly+i*h+downshift,g);
	    		 tlxstatic=tlxstatic+ws[j];
	         }
	     
	         }
	     
	    
         tlx=leftmargin+w[0]+w[1]+w[2]; ////shift to prime part
	     int width=w[2]; ///same width for all 6 subjects

         for(int j=0;j<9;j++)
        	 for(int i=0;i<8;i++)
        	   { g.drawRect(tlx+j*width,tly+i*h, width, h);
	             Centre(Matrix[i][j],width,tlx+j*width,tly+i*h+downshift,g);
	           }
	  
       
       // Now print Remark
      
       g.drawRect(tlx,tly+8*h,2*width, h);
       Centre("Remarks",2*width,tlx,tly+8*h+downshift,g);
       tlx=tlx+2*width;
       g.drawRect(tlx,tly+8*h,2*width, h);
       Centre(Remark,2*width,tlx,tly+8*h+downshift,g);
      
		return PAGE_EXISTS;
	 }
///// Here the JAVA Printing Mechanism Ends
/////////////////////////////////////////////////
///////////////////////////////////////////////

	

	public int PrintConsolidated(Graphics g, PageFormat pf, int page)
    {  if (page > 0)
                { /* We have only one page, and 'page' is zero-based */
    	              
                    return NO_SUCH_PAGE;
                  }

	
	return PAGE_EXISTS;
    }	


    
    
    
    
///////////////END OF PRINT FUNCTIONS //////////////////////////////////    
    
    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
             //   System.out.println(info.getName());
                if ("Nimbus".equals(info.getName())) 
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {

            public void run() {
                ResultView frame = new ResultView();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}