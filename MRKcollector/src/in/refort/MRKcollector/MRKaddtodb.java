package in.refort.MRKcollector;

import java.awt.Font;
import java.awt.Graphics;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.prefs.Preferences;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.sql.*;

public class MRKaddtodb implements Printable 
{      
////Basic 2 Functions For MVC Design---------------------------------------------------------------
	private static MRKaddtodb instance = null;  /* ===== singleton details ===== */
	protected MRKaddtodb() 
	{/* nothing needed, but this prevents a public no-arg  constructor from being created automatically */}
	public static MRKaddtodb getInstance()
	{ if (instance==null)	{ instance = new MRKaddtodb();} 	return instance;}
      //------------------------------------------------------------------------------------------------

	
	
	
	static int GrandTotal;
	
	static JTable tablePri;
	 public static void SetData(Object obj, int row_index, int col_index)
	    {  tablePri.getModel().setValueAt(obj,row_index,col_index);  }
	 
	 public static Object GetData(JTable table, int row_index, int col_index){
	  	  return table.getModel().getValueAt(row_index, col_index);
	  	  }  

	 

	 private void RightJustify(String s, int xPos, int YPos,Graphics g2d){  
	        int stringLen = (int)  g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();  
	        g2d.drawString(s, xPos-stringLen, YPos);  
	 } 
	public static void show(String msg)
	{JOptionPane.showMessageDialog(null, msg);}
	public static void show(int msg)
	{JOptionPane.showMessageDialog(null, msg);}
        
	    static String subjectcodetriplet;
	    static String pagetotalfromtable;
	  //  boolean printing=false;
	    static int TotalReceived;
	    static ArrayList<String> strArray = new ArrayList<String>();
	    static ArrayList<String> roll = new ArrayList<String>();///temp roll for printing
	    static ArrayList<String> mark = new ArrayList<String>();/// temp mark for printing
	    static ArrayList<String> rollArray = new ArrayList<String>();
	    static String subLine="";
	    static String subcode;
	    static String collegename1;
	   // private String setNObuff="";
		private static final int TL = 20;
		static int CurLis=0;
		int CurSet=0;
		int PAGETOT;
		String PrinterName="Not Set";
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
		public static String FirstRoll;
		public static String LastRoll;
		public static String[][] Set = new String[TL][100];
		public static String[][] Key = new String[TL][100];
		public static int processflag;
        public static int JobNem=0;
	    public static int NumberOfReportPages=0;
	    public static int EntriesPerPage=240;
        
        
    public static String ReadFromDisk(String fnem)
    {  strArray.removeAll(strArray);
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
			 
			 strArray.add(line);
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
    	
    //	show(strArray.size());
    /// leave 3 lines for they are college name lines 
    	collegename1=strArray.get(1);
    	//CollegeName2=strArray.get(2);
    	//CollegeName3=strArray.get(3);
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
    	
    	if(Examination[0]==null || Subject[0]==null || Division[0]==null)
    		{ 
    		 show("Rejected");
    		
    		  return "Rejected"; //?? reject file
    		
    		}
    	
    	subcode=Division[0]+"="+Examination[0]+"="+Subject[0]+"="+Examiner[0];
         return subcode;
       
     }
    
    public static void GetNames()
    {  
		String fnem="";
		
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "txt", "TXT");
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
			 if(line.contains("#")) strArray.add(line);
			
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
        
    //	for(int i=0;i<10;i++)
    	
    	
    	 String temp[],stemp,stemp1;
    	
    	 for(int i=0;i<strArray.size();i++)
         {
             stemp=strArray.get(i);
             temp=stemp.split("#");
            int rrr=Integer.parseInt(temp[0].replaceAll("[^0-9.]",""));
 	        String   sss=String.format("%5d",rrr);  
    		 int index=GetIndex(sss);
		    if(index>0)
			{
		     stemp=rollArray.get(index);
		     int tt=stemp.indexOf('#');
		     stemp1=stemp.substring(tt);
		     
		     String name=String.format("%-60s",temp[1].trim());
		     String ttt=sss+"#"+name+stemp1;
		     
		     rollArray.set(index,ttt);
		     }
          }
          
         
    }
    
    public static void DeleteNames() ///It assumes that if second token is >30 then it is a name and deletes
    {                                /// Name is formatted to length 60 if it is < 60
    	
    	String ready="";
    	String tempstr;
        String temp[];
        int TotalRolls=rollArray.size();
       
        for(int i=0;i<TotalRolls;i++)
        { tempstr=rollArray.get(i);
          temp=tempstr.split("#");
           
        if(temp[1].length()<30) continue; ///no name found
        ready="";
        int len=temp.length;
        if(len<3) continue;  ///Remove the record in special case where only roll and name is present;
          for(int j=0;j<len;j++)
        	   {if(j==1) continue; /// skip name
       	         if(j!=0) ready+="#"; ///separator   
        		  ready+=temp[j];
       	       }
       	
       	       rollArray.set(i,ready);
       	 }
    	
}
    
    
    
    
    public static void CalculatePageTotal()
    { GrandTotal=0;
     int totalrows=tablePri.getRowCount();
     int TotalMarklists=totalrows-20;
     if(TotalMarklists<=0) return;
     for(int i=0;i<TotalMarklists;i++)
    	 {String ss=(String) GetData(tablePri,i,1);
    	  //show(ss);
    	 int pt= PageTotal(ss);
    	 GrandTotal=GrandTotal+pt;
    	 String tt=String.format("%d",pt);
	      SetData(tt,i,0);
      	 }
    	
    }

    
    
    public static int PageTotal(String subjectcode)
    {String tempstr;
     int pagetotal=0;
     String temp[];
     int TotalRolls=rollArray.size();
    
     for(int i=0;i<TotalRolls;i++)
     { tempstr=rollArray.get(i);
       if(!tempstr.contains(subjectcode+":")) continue;
    
       temp=tempstr.split("#");
       int len=temp.length;
       for(int j=0;j<len;j++)
     	   {if(temp[j].contains(subjectcode+":")) 
    	       { 
     		     
     		   String strmarks[]; strmarks=temp[j].split(":");
    	      
    	       if(strmarks[1].contains("A")) continue;
    	       
    		  int ttt=Integer.parseInt(strmarks[1].replaceAll("[^0-9.]",""));
    		   pagetotal=pagetotal+ttt;
    		   
    	        }
    		 
    	   }
        
     }
    return pagetotal;	
    
    
    }
    
    public static void Remove(String scode)
    {String stemp,temp[],modifiedstr;
     int i=0;
     int arrlen=rollArray.size();
    	while(i<arrlen)
    	   {stemp=rollArray.get(i);
    	    if(!stemp.contains(scode)){i++; continue;}
    	    temp=stemp.split("#");
    	    if(temp.length==2) {rollArray.remove(i); i=0;arrlen=rollArray.size(); continue;}
    	    modifiedstr=temp[0];
    	   // rollArray.set(i, element)
    	    for(int j=1;j<temp.length;j++)
    	    {if(temp[j].contains(scode)) continue;
    	     modifiedstr+="#";modifiedstr+=temp[j];
    	    }
    	    rollArray.set(i,modifiedstr);
    	    i++;
    	   }
    	
    	temp=subLine.split("#");
    	arrlen=temp.length;
    	stemp=temp[0];
    	for(i=1;i<arrlen;i++)
    	{if(temp[i].contains(scode)) continue;
    	 stemp+="#";
    	 stemp+=temp[i];
    	 }
    	subLine=stemp;
    }
    
    
    
    
    public static boolean IsMarks_OR_AB(String mm)
    { String[] matches = new String[] {"0", "1","2","3","4","5","6","7","8","9","A"};
      
      for (String s : matches)
         if (mm.contains(s)) return true;
      return false;
  
    }
        
    
    public static void Rectify(int hash)
    {String temp,hashpiece[],colonpiece[];
     int lines=rollArray.size();
     int errorcount=0;
     int vacantcount=0;
     int absentcount=0;
     int correction=0;
     for(int i=1;i<lines;i++) ///start from 1 i.e skip header line 
     {  
    	 temp=rollArray.get(i);
    	 
    	 hashpiece=temp.split("#");
    	 if(hashpiece.length<hash) vacantcount++;
    	 if(hashpiece.length>hash) 
    		 {  errorcount++;////
    		    absentcount=0;
    		    for(int j=1;j<hashpiece.length;j++)
    		    { colonpiece=hashpiece[j].split(":");
    		    if(colonpiece[1].contains("A")) absentcount++;
    		    }
    		    //show(absentcount);
    		    if((hashpiece.length-absentcount)==hash) 
    		    	{ correction++;
    		    	  String setString=hashpiece[0];
    		    	  for(int k=1;k<hashpiece.length;k++)
    		    	  {  	  colonpiece=hashpiece[k].split(":");
    	    		    if(!colonpiece[1].contains("A"))
    	    		    	{setString+="#";
    	    		    	setString+=hashpiece[k];
    	    		    	}
    		    		  
    		    	  }
    		    	
    		    	  rollArray.set(i,setString);
    		    	}
    		    
    		 }
    	 
    	 
     }
      MRKaddtodb.CalculatePageTotal();
      String report=String.format("Total Errors = %d\nCorrected=%d\nPossible Vacants = %d",errorcount+vacantcount,correction,vacantcount);
    	show(report);
    	//show(correction);
    }
    
    
    public static int FillMainList(String correctedsubcode)
    {  String stemp,temp[];
      if(subLine.length()==0)
       { subLine+=correctedsubcode;  ///1=Ok
         for(int i=28+3*TotalSets[CurLis];i<strArray.size();i++) 
		{stemp=strArray.get(i); temp=stemp.split(":");
		//String ll = temp[1].replaceAll("[^a-zA-Z0-9\\s]", "");
		
		//String ll=temp[1].replaceAll("[^A-Za-z0-9 ]", "");
		if (!IsMarks_OR_AB(temp[1])) continue;
		//temp[1].trim();
		//show(ll.length());
		int rrr=Integer.parseInt(temp[0].replaceAll("[^0-9.]",""));
	    String   sss=String.format("%5d",rrr);
		 String ttt=sss+"#"+correctedsubcode+":"+temp[1].trim();
		 rollArray.add(ttt); 
		}
        return 1;
       }
    ///// not first file
	  
 	if(!CodeExists(correctedsubcode))  ///update sub code if not exists
 	 { subLine+="#";subLine+=correctedsubcode;
 	  // subArray.add(Examination[0]+"="+Subject[0]);
 	   for(int i=28+3*TotalSets[CurLis];i<strArray.size();i++) 
   		 {  stemp=strArray.get(i); temp=stemp.split(":");
   		    //String ll=temp[1].replaceAll("[^A-Za-z0-9 ]", "");
   		 
   	//	String ll = temp[1].replaceAll("[^a-zA-Z0-9\\s]", "");
 	    	//if(ll.length()<3) continue;
 	    //	show(ll.length());
   		if (!IsMarks_OR_AB(temp[1])) continue;
   	 	    int rrr=Integer.parseInt(temp[0].replaceAll("[^0-9.]",""));
	        String   sss=String.format("%5d",rrr);  
   		    int index=GetIndex(sss);
   		    if(index<0)
   			{String ttt=sss+"#"+correctedsubcode+":"+temp[1].trim();
   		     rollArray.add(ttt);
   		     }
   		    else
   		   {
   		    String ttt=rollArray.get(index);
   		    ttt+="#";ttt+=correctedsubcode;ttt+=":";ttt+=temp[1].trim();
   		    rollArray.set(index, ttt);
   		   }  
   		 }
 	   return 1;
 	  }  		
     return 0;
    }
	
    public static int GetIndex(String testroll)
    {String temp[],stemp;
    	for (int i=0;i<rollArray.size();i++)
    { stemp=rollArray.get(i);temp=stemp.split("#");
      if(temp[0].equals(testroll))	return i;
    }
    	return -1;
    	
    }
    
    public static boolean CodeExists(String subcode)
    { if(subLine.contains(subcode+"#")) return true; 
    
    	return false;
    }
    

    public static void CreateMysqlDB() throws ClassNotFoundException
    {
    	Collections.sort(rollArray);
    	/**
         * 3306 is the default port for MySQL in XAMPP. Note both the 
         * MySQL server and Apache must be running. 
         */
    	String url = "jdbc:mysql://localhost:3306/?useUnicode=yes&characterEncoding=UTF-8";
        /**
         * The MySQL user.
         */
        String user = "root";
        
        /**
         * Password for the above MySQL user. If no password has been 
         * set (as is the default for the root user in XAMPP's MySQL),
         * an empty string can be used.
         */
        String password = "";
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, user, password);
            
            Statement stt = con.createStatement();
            
            /**
             * Create and select a database for use. 
             */
            stt.execute("CREATE DATABASE IF NOT EXISTS SIWS");
            stt.execute("USE SIWS");
            
            /**
             * Create table marklist
             */
            stt.execute("DROP TABLE IF EXISTS marklist");
            stt.execute("CREATE TABLE marklist (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "RollNo VARCHAR(100),"
                    + "Score VARCHAR(400),"
                    + "PRIMARY KEY(id)"
                    + ")");
            
            /**
             * Add marks entries to the table marklist
             */
                       
            String query = "insert into marklist values (NULL,?,?)";
            java.sql.PreparedStatement pStatement = con.prepareStatement(query);
            int batchSize = 100;
            
            String temp[],stemp,allmarks="",roll="";
            int k;
            for(int count=0;count<rollArray.size();count++)
            {   stemp=rollArray.get(count);
                temp=stemp.split("#");
                k=stemp.indexOf("#");
                allmarks=stemp.substring(k+1);
                roll=temp[0];
            	if(temp[1].length()>30)
                {   k=allmarks.indexOf("#");
            		allmarks=allmarks.substring(k+1);
            		roll=temp[0]+" : "+temp[1];
                }
            	
            	
                pStatement.setString(1,roll);
                pStatement.setString(2,allmarks);
                pStatement.addBatch();
              
                if (count % batchSize == 0)
                {
                    pStatement.executeBatch();
                }
            }
            pStatement.executeBatch() ; //for remaining batch queries if total record is odd no.
            
            ////  free resources 
           
            stt.close();
            pStatement.close();
            con.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

        
    public static void WriteToDisk()
    {File f = new File(System.getProperty("java.class.path"));
	File dir = f.getAbsoluteFile().getParentFile();
	String path = dir.toString();
	String fnem=path+"/Result.rlt";
	 Collections.sort(rollArray);	
     FileWriter f0=null;
	 try {f0 = new FileWriter(fnem);	} catch (IOException e1) {e1.printStackTrace();	}
     String newLine = System.getProperty("line.separator");
     
     
     try { f0.write(subLine);	} catch (IOException e) {e.printStackTrace();	}
     try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}

     
     
for(int i=0;i<rollArray.size();i++)
{   
    try { f0.write(rollArray.get(i));	} catch (IOException e) {e.printStackTrace();	}
    try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}

}

try {f0.close();} catch (IOException e) {e.printStackTrace();}
    

//AutoCloseMsg("File Saved");
   }
    
   public static int Filter(String subcode)
   { Object[] options = {"Yes", "No","Cancel Process"};
    int n = JOptionPane.showOptionDialog(null,"Approve : "+subcode+" ?","Verification Message",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[2]);
    return n;
    }
    
    
   public static void LoadList()
   {String fylename;
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
	
 	
 	 subLine = null;
 	 try {
 		subLine=reader.readLine();
 	} catch (IOException e1) {
 		// TODO Auto-generated catch block
 		e1.printStackTrace();
 	}
 	 //if(subLine==null){ show()		
 	String line=null;
 	try { while ((line = reader.readLine()) != null) 
 		{rollArray.add(line);
 		}
 	} catch (IOException e) 
 	{
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}

   }
   public void setTextComponent(JTable tbl) 
	{
	
		MRKaddtodb.tablePri = tbl;
	}   
   
   
	private void Centre(String s, int width, int XPos, int YPos,Graphics g2d)
	  { int stringLen = (int)  g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();  
		int start = width/2 - stringLen/2; g2d.drawString(s, start + XPos, YPos);  
	   } 
		
	
	 public PrintService findPrintService(String printerName)
	    {
	        for (PrintService service : PrinterJob.lookupPrintServices())
	        {
	            if (service.getName().equalsIgnoreCase(printerName))
	                return service;
	        }

	        return null;
	    }
	 
	  
	 
////////////MAIN JAVA PRINT LOOP COMMON TO ALL PRINTING FUNCTIONS	
	 
	 
//	 @Override
	    public int print(Graphics g, PageFormat pf, int page) throws PrinterException 
	    { 
	    	switch(JobNem)
	    	{case 0  : return PrintML(g,pf,page);
	    	 case 1  : return PickAndPrintLoop(g,pf,page);
	    	 default : return PrintRpt(g,pf,page);
	    	}	    
	    }
	    
////////// END OF MAIN JAVA PRINT LOOP//////////////////
	    
	    
///////////////////////////THREE FUNCTIONS TO PRINT MARKLIST/////////////////    
	    
////////////////////////// Decide Jobnem Identifier
	    
///////////////////////// Get Roll - Mark Pair in roll and mark Array
	    
///////////////////////// Start Print Java Print Loop	    
	    
	    public void PrintMarkSheet()
	    {	JobNem=0;
	    //	printing=true;
	    	GetMarkRollPair();
	        PrintService ps = findPrintService(PrinterName);                                    
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
	        job.setJobName(Division[0]+"-"+Examination[0]+"-"+Subject[0]);
	        //call je print method of the Printable object
	    
	        
	        try {
				job.print();
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    }

	    
	    public void GetMarkRollPair()
		{String temp[],stemp;
		 PAGETOT=0;
		 int ttt=0;
		 roll.removeAll(roll);mark.removeAll(mark);
			
			for(int i=28+3*TotalSets[CurLis];i<strArray.size();i++) 
		   { stemp=strArray.get(i); temp=stemp.split(":");
		     roll.add(temp[0].trim());mark.add(temp[1].trim());
		     if(temp[1].contains("A")) continue;
		     ttt=Integer.parseInt(temp[1].replaceAll("[^0-9.]",""));
		     PAGETOT=PAGETOT+ttt;
			}
		   }

	    
	    public int PrintML(Graphics g, PageFormat pf, int page)
	    {  if (page > 0) { /* We have only one page, and 'page' is zero-based */
	    	              // printing=false;
                        return NO_SUCH_PAGE;
                      }

     g.setFont(new Font("Roman", 0, 8));
     
     int colwidth[]={45,45,45,45,45,45,45,45,45,45}; /// total cols 10 = CollegeIndexNumberColwidArray.length
     int TotalColumns=colwidth.length;
     int height=18;
     int tlx=(int) pf.getImageableX()+15,tly=(int) pf.getImageableY()+10;
     int temptlx=tlx,temptly=tly,shifty=12;
     
        //////////////////MARKLIST HEADER///////////////////////////   
   
     Centre(collegename1,465,tlx,tly,g);
     g.drawString("Class & Div : "+Clas[CurLis]+" "+Division[CurLis],tlx, tly+28);
     g.drawString("Examination : "+Examination[CurLis],tlx, tly+14);
     g.drawString("Subject :"+Subject[CurLis],tlx+200, tly+28);
     g.drawString("Total Marks : "+MaxMarks[CurLis],tlx+200, tly+14);
     g.drawString("Examiner : "+Examiner[CurLis],tlx+360, tly+28);
     g.drawString("Date : "+Date[CurLis],tlx+360, tly+14);
     
     tly=tly+40;//skip headerspace
     
     
  ////////////////TITLE ROW  ////////////////////////////////////
     String columntitle;
     for(int i=0;i<TotalColumns;i++)
        { g.drawRect(tlx,tly,colwidth[i],height);
          if(i%2==0) columntitle="ROLL"; else columntitle="MARKS";
          Centre(columntitle,colwidth[i],tlx,tly+shifty,g);
          tlx=tlx+colwidth[i];
         } ///one grid row complete
     
  tly=tly+height;
     tlx=temptlx;  ///shift to left corner
     temptly=tly;  /// shift to grid height
  ////////////////REMAINING 30 ROWS WITH 5 COLUMNS/////////////////////////////
    
     String cellcontent=" ";
     int rollcounter=0;
     for(int i=0;i<TotalColumns;i++)
     {  for(int j=1;j<31;j++)
        
        { g.drawRect(tlx,tly,colwidth[i],height);
          if(rollcounter<roll.size()) 
         	 {if(i%2==0)
         	   cellcontent=roll.get(rollcounter);
         	 else 
         	   cellcontent=mark.get(rollcounter);
         	 
         	 }
          else cellcontent=" ";
          Centre(cellcontent,colwidth[i],tlx,tly+shifty,g);
          tly=tly+height; 
          rollcounter++;
         } ///one col of grid complete
     tly=temptly;
     tlx=tlx+colwidth[i];//shift to next column
     
     }
    
     /////////MARKLIST FOOTER /////////////////////////////////////
     g.drawString("Page Total : "+String.valueOf(PAGETOT),temptlx, temptly+570);
     RightJustify("Examiner's Sign : ____________",temptlx+450, tly+570,g);
     ////////////////////////////////////////////////////////////////

     return PAGE_EXISTS;
	    }
	    
///////////////////END OF THREE FUNCTIONS TO PRINT MARKLIST ON-THE-FLY ON TICK MARK 	    
/////////////////////////////////////////////////////////////////////////////////	       
	    
	    
	    public void SelectPrinter()
	    {   LoadPreferences();
	    	//show(PrinterName);
	    	ButtonGroup group = new ButtonGroup();
	        ArrayList<String> PrinterNames = new ArrayList<String>(); 
	    	
	    	for (PrintService service : PrinterJob.lookupPrintServices())
	        {
	            PrinterNames.add(service.getName());
	               
	        }	    	
	    	JRadioButton buttons[] = new JRadioButton[PrinterNames.size()];
	    	
	    	for (int i = 0; i < buttons.length; ++i)
	    	{
	    		buttons[i] = new JRadioButton(PrinterNames.get(i));
	    	 //   btn.addActionListener(this);
	    	    group.add(buttons[i]);
	    	    //buttons[i] = btn;
	    	}
	    	
	        int res = JOptionPane.showConfirmDialog(null, buttons, "Select Default Printer", 
                    JOptionPane.OK_CANCEL_OPTION);

	        if (res == JOptionPane.OK_OPTION)
	              { for(int i=0;i<PrinterNames.size();i++) 
	            	  if(buttons[i].isSelected()) { PrinterName=PrinterNames.get(i);
	            	                                SavePreferences();}
	              }

	        
	    }
	    
	
	    public void TableSort(int totalreceived)
	    {	strArray.removeAll(strArray); //reuse strArray for sorting;
			for(int i=0;i<totalreceived;i++)
				{String ss=(String) GetData(tablePri,i,1);
				 strArray.add(ss);
				}
			Collections.sort(strArray.subList(0, totalreceived));//sort subcodes from table
			
			for(int i=0;i<totalreceived;i++) 
			{SetData(strArray.get(i),i,1); } ///put sorted data back in table
	    	CalculatePageTotal();
	    }
	    
	    
	    
      public void SetReceivedCount(int received)
      {TotalReceived=received;
    	  
      }
      
      
      public void SaveReport()
      {
    	  if(TotalReceived==0) { show("No Marklist Compilation Loaded"); return; }
    	  String filter="";
    	  filter = JOptionPane.showInputDialog(null, "Enter Filter Such As U1 :", "List Of Received Mark Lists",
    		        JOptionPane.WARNING_MESSAGE).toUpperCase();
    	  if(filter=="") return;
    	 // filter=filter.toUpperCase()
    	  File f = new File(System.getProperty("java.class.path"));
    		File dir = f.getAbsoluteFile().getParentFile();
    		String path = dir.toString();
    		String fnem=path+"/Received Marklists - " + filter + ".txt";
    		 Collections.sort(rollArray);	
    	     FileWriter f0=null;
    		 try {f0 = new FileWriter(fnem);	} catch (IOException e1) {e1.printStackTrace();	}
    	     String newLine = System.getProperty("line.separator");
    	     
    	     
    	     
    	     
    	     for(int i=0;i<TotalReceived;i++)
    	     {   
    	    	
    	          String tt= (String) GetData(tablePri,i,0);  
    	          
    	          String uu=tt.trim();
    	          String vv=String.format("%1$-4s",uu);
    	        String ss="["+(String) GetData(tablePri,i,1)+"="+vv+"]";
    	        if(!ss.contains(filter)) continue;
    	        try { f0.write(ss);	} catch (IOException e) {e.printStackTrace();	}
    	        try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}  
             }

    	     try {f0.close();} catch (IOException e) {e.printStackTrace();}
    
 }

    
    	    

    

      
      
      
 //////////////////////////START PRINTING REPORT TWO FUNCTIONS
///////////////////////////START PRINT REPORT AND FIND TOTAL PAGES AND SET JOB NAME
////////////////////////// THEN CALL STANDARD JAVA PRINT LOOP WHICH CALLS SECOND FUNCTION
//////////////////////////// PrintRpt(...
      
	    public void PrintReport()
	    {	
	    	if(TotalReceived==0) { show("No Marklists To Prints"); return; }
	    	JobNem=2;
	        NumberOfReportPages=TotalReceived/EntriesPerPage;
	        if(TotalReceived%EntriesPerPage!=0) NumberOfReportPages++;
	    
	        PrintService ps = findPrintService(PrinterName);                                    
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
	        job.setJobName("MarkList Collection Report");
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
	    
	    public int PrintRpt(Graphics g, PageFormat pf, int page)
	    { 
	     	
		 
		  if (page >= NumberOfReportPages) { /* 'page' is zero-based */
                        return NO_SUCH_PAGE;
                      }

     g.setFont(new Font("Courier", 0, 8));
     int height=18;
     int tlx=(int) pf.getImageableX()+80,tly=(int) pf.getImageableY()+10;
     //////////////////START REPORT PAGE///////////////////////////   
     String Line="";
     int counter=0;
     Centre("Mark List Report",465,tlx,tly,g);
     for(int i=page*EntriesPerPage;i<TotalReceived;i++)
     {   if(i%6==0)  ////6 entries per line
    	 { tly=tly+height;g.drawString(Line,tlx, tly); Line="";}
          String tt= (String) GetData(tablePri,i,0);  String uu=tt.trim();
          String vv=String.format("%1$-4s",uu);
        String ss="["+(String) GetData(tablePri,i,1)+"="+vv+"]";
    	  Line=Line+ss;
    	  counter++;
    	  if(counter>EntriesPerPage-1) break;
     }
     tly=tly+height;g.drawString(Line,tlx, tly); Line="";
          
     return PAGE_EXISTS;
 }

/////////////////////////END OF TWO PRINT REPORT FUNCTIONS /////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
	    
	    
/////////////////////START 4 FUNCTIONS FOR PICK MARK LIST FROM TABLE AND PRINT MARKLIST
 //////////////////////////////////////////////////////////////////////////////////////
/////////////////// 1 call SetSubjecCodeTriplet from MRKFrame to pass code triplet to this class
//////////////////  2 Call PickPrintML
/////////////////// 3 Set JobNem and Call RecreateRollMarkArray before print
/////////////////// 4 Call Standard JAVA PRINT LOOP will call PickAndPrint(Grapics g....
	    
	    
	    public void SetSubjectCodeTriplet(String tempscode,String temptriplet) 
		{
			subjectcodetriplet=tempscode;    /////get code triplet from table
			pagetotalfromtable=temptriplet;  /////get pagetotal from table
			
		}
	   
	    public void PickAndPrintML() 
		{	
    	JobNem=1;
    	RecreateRollMarkArray();
        PrintService ps = findPrintService(PrinterName);                                    
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
        job.setJobName(subjectcodetriplet.replace('=', '-'));
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
	
	    public static void RecreateRollMarkArray()
	    {    
	    	/////First Recreate Mark List in roll and mark array
	    String tempstr;
	    roll.removeAll(roll);mark.removeAll(mark);
	    String temp[];
	    int TotalRolls=rollArray.size();
	   
	    for(int i=0;i<TotalRolls;i++)
	    { tempstr=rollArray.get(i);
	      if(!tempstr.contains(subjectcodetriplet)) continue;
	   
	      temp=tempstr.split("#");
	      int len=temp.length;
	      for(int j=0;j<len;j++)
	    	   {if(temp[j].contains(subjectcodetriplet)) 
	   	       { 
	    		  String strmarks[]; strmarks=temp[j].split(":");
	   	          roll.add(temp[0]);mark.add(strmarks[1]);
	   	       
	   	        }
	   		 
	   	   }
	       
	    }
	    //////////Recreation complete. Now Print mark list//////////////////
	    //show(roll.size());
	    
	    }
	  
	    
	    public int PickAndPrintLoop(Graphics g, PageFormat pf, int page)
	    {  if (page > 0) { /* We have only one page, and 'page' is zero-based */
	    	             //  printing=false;
                     return NO_SUCH_PAGE;
                   }

  g.setFont(new Font("Roman", 0, 9));
  
  int colwidth[]={45,45,45,45,45,45,45,45,45,45}; /// total cols 10 = CollegeIndexNumberColwidArray.length
  int TotalColumns=colwidth.length;
  int height=20;
  int tlx=(int) pf.getImageableX()+80,tly=(int) pf.getImageableY()+10;
  int temptlx=tlx,temptly=tly+height,shifty=12;
 
  Centre("Marklist : "+subjectcodetriplet,465,tlx,tly,g); //Heading as subject code triplet
  tly=tly+40;
  ////////////////TITLE ROW  ////////////////////////////////////
  String columntitle;
  for(int i=0;i<TotalColumns;i++)
     { g.drawRect(tlx,tly,colwidth[i],height);
       if(i%2==0) columntitle="ROLL"; else columntitle="MARKS";
       Centre(columntitle,colwidth[i],tlx,tly+shifty,g);
       tlx=tlx+colwidth[i];
      } ///one grid row complete
  
  tly=tly+height;
  tlx=temptlx;  ///shift to left corner
  temptly=tly;  /// shift to grid height
////////////////REMAINING 30 ROWS WITH 5 COLUMNS/////////////////////////////
 
  String cellcontent=" ";
  int rollcounter=0;
  for(int i=0;i<TotalColumns;i++)
  {  for(int j=1;j<31;j++)
     
     { g.drawRect(tlx,tly,colwidth[i],height);
       if(rollcounter<roll.size()) 
      	 {if(i%2==0)
      	   cellcontent=roll.get(rollcounter);
      	 else 
      	   cellcontent=mark.get(rollcounter);
      	 
      	 }
       else cellcontent=" ";
       Centre(cellcontent,colwidth[i],tlx,tly+shifty,g);
       tly=tly+height; 
       rollcounter++;
      } ///one col of grid complete
  tly=temptly;
  tlx=tlx+colwidth[i];//shift to next column
  
  }
 
  /////////MARKLIST FOOTER /////////////////////////////////////
  g.drawString("Page Total : "+pagetotalfromtable,temptlx, temptly+670);
  RightJustify("Examiner's Sign : ____________",temptlx+450, tly+670,g);
  ////////////////////////////////////////////////////////////////
    
  
  return PAGE_EXISTS;
	    }
	    
//////////////////// END OF 4 FUNCTIONS TO PICK MARKLIST AND PRINT
/////////////////////////////////////////////////////////////////////////////////	    
	     
	    
	    public void SavePreferences()
		{Preferences prefs = Preferences.userNodeForPackage(in.refort.MRKcollector.MRKFrame.class);

		// Preference key name
		final String PREF_NAME = "MRKcollectorPref";
		// Set the value of the preference
		prefs.put(PREF_NAME, PrinterName);
			
		}


		public  void LoadPreferences()
		{Preferences prefs = Preferences.userNodeForPackage(in.refort.MRKcollector.MRKFrame.class);

		// Preference key name
		final String PREF_NAME = "MRKcollectorPref";
		PrinterName= prefs.get(PREF_NAME,PrinterName); // "a string"
				
		}
   
}
