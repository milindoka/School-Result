import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class PrintResult implements Printable, ActionListener
{	

	public static void show(String msg)
	{JOptionPane.showMessageDialog(null, msg);}
	public static void show(int msg)
	{JOptionPane.showMessageDialog(null, msg);}
   
	static ArrayList<String> strArray = new ArrayList<String>();
	
	
	public static void ReadFromDisk()
    {  
		String fnem="";
		
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "RLT", "rlt");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	fnem=chooser.getSelectedFile().getPath();
	    //   show("You chose to open this file: " + fnem);
	       
	    }
		
		
		if(fnem=="") return;
		
		
		strArray.removeAll(strArray);
    	BufferedReader reader=null;
		try {
			reader = new BufferedReader(new FileReader(fnem));
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
	private void Centre(String s, int width, int XPos, int YPos,Graphics g2d)
	  { int stringLen = (int)  g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();  
		int start = width/2 - stringLen/2; g2d.drawString(s, start + XPos, YPos);  
	   }
	
	/// This Function Assigns the printer to our program
	/// See your printer names from control panel and select the pdf printer name
	/// enter a part of the name to identify it uniquely.
	/// The function will give you the pointer to that printer
	/// I am giving the name  "pdf" since my pdf printer name is PDF
	/// when I call it in my printing mechanism
	 public PrintService findPrintService(String printerName)
	    {
	        for (PrintService service : PrinterJob.lookupPrintServices())
	        {
	            if (service.getName().toLowerCase().contains(printerName.toLowerCase()))
	                return service;
	        }

	        return null;
	    }
	 String Roll="0000";
    int subtotal[]={0,0,0,0,0,0,0,0};
    int grace[]   ={0,0,0,0,0,0,0,0};
    int average[] ={0,0,0,0,0,0,0,0};
    int grand[]=   {0,0,0,0,0,0,0,0};
    int gracecount=0;
    int gracetotal=0;
    String Remark;
    String[][] Matrix = new String[8][9];
    String StaticMatrix[][]={ {"Examination","Max","Min"},
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
	 String OneStudent;
	 
	 String Order[]={"ENG","MAR","TAM","HIN","ITE","MAT","PHY","CHE","BIO","SEP","ECO","BKE","OCM","EVS","PTE"};
	 String Row[]={"T1","T2","U1","U2"};
	 int leftmargin=80,topmargin=72;
	 boolean found;
	 public void FillMatrix()
	 { 
		 
		 for(int i=0;i<8;i++)
			 for(int j=0;j<9;j++) Matrix[i][j]="";
		 
		 for(int i=0;i<8;i++) subtotal[i]=grace[i]=average[i]=grand[i]=0;
					 
		 
	     String temp[];
         String sub[];
         temp=OneStudent.split("#");  ///Make pieces of the record cutting at #
         Roll=temp[0]; ///Get Roll Number
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
	    	 {
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
      int GT1200=0,GT650=0;
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
	 
	 
	///// Here the whole  JAVA Printing Mechanism Starts 
	/////  Note 'implements Printable above', It includes the Print Mechanism to our Program
	/////
	  public void actionPerformed(ActionEvent e) 
              {
		  ReadFromDisk(); 
		  
		  //OneStudent=strArray.get(1);
		  
		//  FillMatrix();
		 
		  PrintService ps = findPrintService("pdf"); // put ypur virtual printer name here                                 
	        //create a printerJob
	        PrinterJob job = PrinterJob.getPrinterJob();            
	        //set the printService found (should be tested)
	        try {
				job.setPrintService(ps);
			} catch (PrinterException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}    
		     job.setPrintable(this);
	        
	         ////Widening the print AREA.
	         //Java Keeps preset Margins of about 1 inch on all corners
             //Top Left Corner is  (0,0), then width and height
	         HashPrintRequestAttributeSet pattribs=new HashPrintRequestAttributeSet();
	         pattribs.add(new MediaPrintableArea(0, 0, 210, 297, MediaPrintableArea.MM));
	         // 210 x 297  A4 size paper in Millimiters.
	         
	         
	   //    boolean ok = job.printDialog();  //Suppress print dialog
	         boolean ok=true;                // assume printer is selected without dialog
	         
	         if (ok) {
	             try {
	                  job.print(pattribs);
	             } catch (PrinterException ex) {
	             }
	         }
	     }	
	  
	public int print(Graphics g, PageFormat pf, int pageno)
			throws PrinterException
	{
		  
		 if (pageno>strArray.size()-2)    // MultipageDoc 'page no' is zero-based
		    {  return NO_SUCH_PAGE;  // After NO_SUCH_PAGE, printer will stop printing.
	        }
		 
		 OneStudent=strArray.get(pageno+1);
		 // show(OneStudent);
	     FillMatrix();
		 
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
	     
	     
	     for(int i=0;i<8;i++)
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
	
   static JButton PrinterButton;
	
   public static void main(String[] args) 
   {
    JFrame frame = new JFrame("Print Result");
    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Container c=frame.getContentPane();
    c.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    
    PrinterButton = new JButton("Print");
   
    
    PrinterButton.addActionListener(new PrintResult());
    c.add(PrinterButton, gbc);

    frame.setLocationRelativeTo(null); ////display frame in Center
    frame.setVisible(true);            //// Show the frame
    	
   }
	
}