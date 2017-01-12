package in.siws.result;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


//import sun.font.FontFamily;


public class ResultView extends JFrame implements Printable 
{ 
	JLabel label,GTlabel;
	static String fylename="";
    int grandtotal=0;	
    int GT1200=0;
    int GT650=0;
    int TotalPrintPages=0;
    int startpageindex=0,endpageindex=0;
    JTextField NameField;
    JTextField DiviField;
    String FirstLine="";  
    String Strim;
    String AY;
   
    
    
    Font TNR16=new Font("Times New Roman", Font.PLAIN, 16);
    
    
    // boolean EVSfail;
    
    
    
    boolean printpass=false;
    Toast toast;
    DocumentFilter filter = new UppercaseDocumentFilter();    
    
    
    
    
	public static Object GetData(int row_index, int col_index)
	{ return table2.getModel().getValueAt(row_index, col_index); }  

	public static void show(String msg)
	{JOptionPane.showMessageDialog(null, msg);}
	public static void show(int msg)
	{JOptionPane.showMessageDialog(null, msg);}
	
	public static void show(long msg)
	{JOptionPane.showMessageDialog(null, msg);}
	
///////////////////START OF VARIABLES//////////////////////////////////////
	int leftmargin=80,topmargin=195;
	
	static ArrayList<String> strArray = new ArrayList<String>();
	static ArrayList<String> strModSci = new ArrayList<String>();
	static ArrayList<String> strModCom = new ArrayList<String>();
	static ArrayList<Integer> intPasses = new ArrayList<Integer>();
	
	int ROWS=10;
    int COLS=12;
    int dist=0; ////moderation-distance : marks for passing the student
    String[][] Matrix = new String[ROWS][COLS];
String Stream="Commerce"; ///default stream. If found PHY || CHE || BIO change to Siicience
String Roll="0000";
int subtotal[]={0,0,0,0,0,0,0,0};
int grace[]   ={0,0,0,0,0,0,0,0};
int average[] ={0,0,0,0,0,0,0,0};
int grand[]=   {0,0,0,0,0,0,0,0};
int gracecount=0;
int gracetotal=0;
String Remark;

String StaticMatrix[][]={ 
		        {"Examination","Max","Min"},
		        {"Unit Test-I (A)","25",""},
                {"Terminal-I  (B)","50",""},
                {"Unit Test-II(C)","25",""},
                {"Terminal-II (D)","100",""},                         
                {"Aggregate (A+B+C)","200","70"},
                {"Average (A+B+C)/2","100","35"},
                {"Grace","15",""},
                {"Oral/Practs/Proj","",""}
               }; ////3 rows 8 columns

int subindex=0;



String Order[]={"ENG","MAR","TAM","HIN","ITE","MAT","PHY","CHE","BIO","SEP","ECO","BKE","OCM","CS1","CS2","EL1","EL2","EVS","PTE"};
String Row[]={"U1","T1","U2","T2"};
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
    	toast=Toast.getInstance();
    	setLocationRelativeTo(null);/* Prevents constructor from being created automatically.*/
    	
    	
    	sp=SetPrinter.getInstance();
    	
    	int year = Calendar.getInstance().get(Calendar.YEAR);
    	AY=String.format("%d-%d",year-1,year%100);
    	
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
    	
    	   
    	  
    	   table2.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    	   
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
				
				CalculateGT();
				currentindex=0;
				FillMatrix(currentindex);
				FillDistance();
				ShowMatrix();
				toast.AutoCloseMsg("Result Loaded");
			}
        });
        
        JButton buttonNext = new JButton("Next");
        //buttonNext.setMnemonic(KeyEvent.VK_PAGE_DOWN); // Shortcut: PgDN
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
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(new KeyEventDispatcher(){
           public boolean dispatchKeyEvent(KeyEvent e){
             if(e.getID() == KeyEvent.KEY_PRESSED)
             {
               if(e.getKeyCode() == KeyEvent.VK_PAGE_DOWN ) 
               {
            	   if(currentindex<strArray.size()-1)
                   {currentindex++;
               
                   FillMatrix(currentindex);
                   FillDistance();
                   ShowMatrix();
                   }
               }
               
               
               if(e.getKeyCode() == KeyEvent.VK_PAGE_UP )
               {
            	   
               
               if(currentindex>0) 	   
               { currentindex--;  
          	    FillMatrix(currentindex);
          	    FillDistance();
          	    ShowMatrix();
          	   }
               
               }
               
               if(e.getKeyCode() == KeyEvent.VK_DELETE )
               {  int DELETE=0;
               int result = JOptionPane.showConfirmDialog(null, 
      				   "Delete This Roll ?",null, JOptionPane.YES_NO_OPTION);
           	   
           	   /////Confirm Delete
      			
           	  if(result==DELETE)
           	  {
           	   grandtotal=grandtotal-GT1200;
                  String ttt;
                  ttt=String.format("%d",grandtotal);
                  GTlabel.setText(ttt);
           	   
                  strArray.remove(currentindex);
                  int totalrecords=strArray.size();
                  if(currentindex>totalrecords-1) currentindex=totalrecords-1;
                  FillMatrix(currentindex);
      			   FillDistance();
      			   ShowMatrix();
           	  }
           	    
           	 
           }
           
               
               
               
             }
             return false;}});
     
        

        JButton buttonPrev = new JButton("Prev");
      //  buttonNext.setMnemonic(KeyEvent.VK_PAGE_UP); // Shortcut: PgUP
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
            {   int GT1200old=GT1200;
            	JTableHeader th = table2.getTableHeader();
            	TableColumnModel tcm = th.getColumnModel();
            	TableColumn tc;
            	String subttile;
            	String marks="";
            	String T2marks="";
            	
            	
            	String one="",temp="";////=strArray.get(currentindex);
                one+=Roll+"#";
                temp=String.format("%-60s",NameField.getText().trim());
                one+=temp;
                for(int i=0;i<6;i++)
                	for(int j=0;j<3;j++) //Except T2 because T2 includes project mark
                 {
                 tc=tcm.getColumn(3+i);
                 subttile=(String) tc.getHeaderValue();
                 if(subttile.length()==0) continue;
                 marks=(String) GetData(j,3+i);
                 if(marks.length()==0) continue;
                 one+="#";
                 one+=DiviField.getText()+"="+Row[j]+"="+subttile+":"+marks;
                 }
                
                tc=tcm.getColumn(9);                     ////EVS
                subttile=(String) tc.getHeaderValue();
                if(subttile.length()!=0) 
                { marks=(String) GetData(5,9);
                  if(marks.length()==0) ; 
                  else 
                	  one+="#"+DiviField.getText()+"=T2="+subttile+":"+marks;
                }
                  
                tc=tcm.getColumn(10);                   ///PTE
                subttile=(String) tc.getHeaderValue();
                if(subttile.length()!=0) 
                { marks=(String) GetData(5,9);
                  if(marks.length()==0) ; 
                  else 
                	  one+="#"+DiviField.getText()+"=T2="+subttile+":"+marks;
                }   
               ///for OPP  (Oral, Practical, Project) 
               /// Also T2 marks updated in the same loop
                int promarks=0;int t2marks=0;
                for(int i=0;i<6;i++)
                	//for(int j=0;j<4;j++)
                 {promarks=0;t2marks=0;
                 tc=tcm.getColumn(3+i);
                 subttile=(String) tc.getHeaderValue();
                 if(subttile.length()==0) continue;
                 
                 marks=(String) GetData(7,3+i);      ////get 7th row
                 if(marks.length()!=0)
                 {
                 one+="#";
                 one+=DiviField.getText()+"="+"T2"+"="+subttile+"OPP"+":"+marks;
                 }
                 
              
                 T2marks=(String) GetData(3,3+i); ///3rd row for T2 marks
                 
                 
                 
                 
                 if(marks.length()==0 || marks.contains("AB")) ;
                 else
 	                
 	                promarks=Integer.parseInt(marks.replaceAll("[^0-9.]",""));
                 
                 if(T2marks.length()==0 || T2marks.contains("AB")) ;
                 else
 	                
 	                t2marks=Integer.parseInt(T2marks.replaceAll("[^0-9.]",""));
                 
                 T2marks=String.format("%02d",t2marks-promarks);
                 one+="#";
                 one+=DiviField.getText()+"="+"T2"+"="+subttile+":"+T2marks;
                 
              
                
                 }
             //   show("<html><body><p style='width: 600px;'>"+one+"</p></body></html>");
               // show(one);
                strArray.set(currentindex, one);
                FillMatrix(currentindex);
                FillDistance();
                ShowMatrix();
                grandtotal=grandtotal+GT1200-GT1200old;
                
                
                String ttt;
                ttt=String.format("%d",grandtotal);
                GTlabel.setText(ttt);
           //     CalculateGT();
            //    tc=tcm.getColumn(9);
           //     String empty=(String) tc.getHeaderValue();
             
                
             	
            }
        });


        JButton buttonPrint = new JButton("Print This");
        buttonPrint.addActionListener(new ActionListener() 
        {

            public void actionPerformed(ActionEvent arg0) 
            {
            	startpageindex=endpageindex=currentindex;
               TotalPrintPages=0;
              PrintResultCard(0);  ///  Jobnem 0 = Print Current Result 
            }
        });

        
        JButton buttonPrintAll = new JButton("Print All");
        buttonPrintAll.addActionListener(new ActionListener() 
        {

            public void actionPerformed(ActionEvent arg0) 
            { 
            String temp[],temp1[];
              intPasses.removeAll(intPasses);
            //JTextField yField = new JTextField(10);

            JPanel myPanel = new JPanel();
            JCheckBox checkbox = new JCheckBox("Only Non-Failures");
            //String message = "Are you sure you want to disconnect the selected products?";
          //  boolean dontShow = checkbox.isSelected();
            
            myPanel.add(checkbox);
        	  String Range = JOptionPane.showInputDialog(null,myPanel);
        	  temp1=Range.split("-");
        	  printpass=checkbox.isSelected();
        	//  if(checkbox.isSelected()) show("selected"); else show("non-selected");
        	  
        	  startpageindex=endpageindex=TotalPrintPages=0;
        	 for(int i=0;i<strArray.size();i++)
        	  { temp=strArray.get(i).split("#");
        		if(temp[0].contains(temp1[0])) {startpageindex=i;break;} 
              }
        	 
        	 for(int i=0;i<strArray.size();i++)
       	      { temp=strArray.get(i).split("#");
       		    if(temp[0].contains(temp1[1])) {endpageindex=i;break;} 
              }
       	
        	 
        //  show(startpageindex);show(endpageindex);	 
        	 
        	if(startpageindex<=endpageindex) TotalPrintPages=endpageindex-startpageindex;
        	else {show("Error In Range");}
              // if(TotalPrintPages>50) TotalPrintPages=50;
               show(TotalPrintPages);
               
               
               for(int i=startpageindex;i<=endpageindex;i++)
               {FillMatrix(i);
                if(!Remark.contains("FAIL")) intPasses.add(i);
            	   
               }
               if(printpass) {  TotalPrintPages=intPasses.size()-1;
            	                PrintResultCard(2);
                            }
               else
               PrintResultCard(0);  ///  Jobnem 0 = Print Current Result
            }
        });
        
        JButton buttonJump = new JButton("Jump");
        buttonJump.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {  String temp[];
            	String name = JOptionPane.showInputDialog(null, "Roll Number To Jump");
  
                if(name == null || name.isEmpty()) return;
                
            	for(int i=0;i<strArray.size();i++)
            	{
            		temp=strArray.get(i).split("#");
            		if(temp[0].contains(name)) 
            			{ currentindex=i; FillMatrix(currentindex);FillDistance();ShowMatrix(); return;}
            	}
               
            }
        });

       
        JButton buttonDelThis = new JButton("Delete This");
        buttonDelThis.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {  int DELETE=0;
                int result = JOptionPane.showConfirmDialog(null, 
       				   "Delete This Roll ?",null, JOptionPane.YES_NO_OPTION);
            	   
            	   /////Confirm Delete
       			
            	  if(result==DELETE)
            	  {
            	   grandtotal=grandtotal-GT1200;
                   String ttt;
                   ttt=String.format("%d",grandtotal);
                   GTlabel.setText(ttt);
            	   
                   strArray.remove(currentindex);
                   int totalrecords=strArray.size();
                   if(currentindex>totalrecords-1) currentindex=totalrecords-1;
                   FillMatrix(currentindex);
       			   FillDistance();
       			   ShowMatrix();
            	  }
            	    
            	 
            }
            
        });

        JButton buttonFail = new JButton("Fail");
        buttonFail.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {             
              int totalrecords=strArray.size();

              for(int i=1;i<=totalrecords;i++)
              {
            	  
            	FillMatrix((currentindex+i)%totalrecords); ///circular search for fail
            	if(Remark.contains("FAIL")) {currentindex=(currentindex+i)%totalrecords; ShowMatrix(); FillDistance();break;} 
              }
            	
            	
            }
            
        });

        JButton buttonxSub = new JButton("xSub");
        buttonxSub.addActionListener(new ActionListener() 
        { 
          
          
            public void actionPerformed(ActionEvent arg0) 
            {   String temp,hashpiece[];
            
          	  String subn = JOptionPane.showInputDialog(null, "Enter code of the Subject to be Removed");
          	  if(subn==null) return;
          	  subn=subn.trim();
          	  if(subn.length()==0) return;
          	    String sub="="+subn.toUpperCase();
            	//show(sub);
            	temp=strArray.get(currentindex);
       	        hashpiece=temp.split("#");
       	        String setString=hashpiece[0];
       	 	    for(int j=1;j<hashpiece.length;j++)
       		    { if(hashpiece[j].contains(sub)) continue;
       		      setString+="#";
       		      setString+=hashpiece[j];
       		    }
       		    	
       		   strArray.set(currentindex,setString);
       		   CalculateGT();
            }
            
        });

        JButton buttonOptions = new JButton("Options");
        buttonOptions.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent arg0) 
            {
            	
            	 OptionDialog myDialog = new OptionDialog(null, true, "Select Option ?");
                 
            	 String option=myDialog.getAnswer();
            	 
                 if(option == null) return;
                  
                 if(option.contains("ModList"))      {Mod();return;}
                 if(option.contains("Statistics"))   {Stat();return;}
                 if(option.contains("MeritList"))    {Merit();return;}
                 if(option.contains("SubMerit"))     {SubMerit("MAT");return;}
                 if(option.contains("Consolidated")) {PrintConsolidatedResult();return;}
                 if(option.contains("MTnames")) {SaveEmptyNames();return;}
                 if(option.contains("DelVac")) {DeleteVacants();return;}
                 if(option.contains("FList")) {FList();return;}
                 if(option.contains("Saral")) {Saral();return;}
                 if(option.contains("CardsPdf")) 
                 
                 {try {
					CardsPdf();
				      } catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}return;}
            	/*              
            	int option=op.SelectOption();
            	
            	switch (option)
            	{case 0 : break;
            	 case 1 : PrintConsolidatedResult();break;
            	 case 2 : SaveEmptyNames(); break;
            	 case 3 : Merit();   break;
            	 case 4 : Mod();  break;
            	 case 5 : Stat(); break;
            	 case 6 : FList(); break;
            	 case 7 : SubMerit("MAT");
            	 default : break;
            	}
            
            */
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

        
        JButton buttonSaveResult = new JButton("Save Result");
        buttonSaveResult.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {File f = new File(System.getProperty("java.class.path"));
        	File dir = f.getAbsoluteFile().getParentFile();
        	String path = dir.toString();
        	String fnem=path+"/Result.rlt";
        		
             FileWriter f0=null;
        	 try {f0 = new FileWriter(fnem);	} catch (IOException e1) {e1.printStackTrace();	}
             String newLine = System.getProperty("line.separator");
             
             
             try { f0.write(FirstLine);	} catch (IOException e) {e.printStackTrace();	}
             try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}

             
             
        for(int i=0;i<strArray.size();i++)
        {   
            try { f0.write(strArray.get(i));	} catch (IOException e) {e.printStackTrace();	}
            try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}

        }

        try {f0.close();} catch (IOException e) {e.printStackTrace();}
       toast.AutoCloseMsg("Result Saved");
            }
        });

        
                      
        NameField = new JTextField(60);
        ((AbstractDocument) NameField.getDocument()).setDocumentFilter(filter);

        DiviField = new JTextField(2);
        ((AbstractDocument) DiviField.getDocument()).setDocumentFilter(filter);

        
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
        northPanel.add(buttonSaveResult);
        northPanel.add(NameField);
        northPanel.add(DiviField);
        northPanel.add(GTlabel);
        northPanel.add(buttonSetPrinter);
        northPanel.add(label);
        add(northPanel,BorderLayout.NORTH);
        JPanel centrePanel=new JPanel();
        centrePanel.add(scrollPane2);
        add(centrePanel,BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(buttonLoad);
        southPanel.add(buttonPrev);
        southPanel.add(buttonNext);
        southPanel.add(buttonUpdate);
        southPanel.add(buttonPrint);
        southPanel.add(buttonPrintAll);
        southPanel.add(buttonJump);
        southPanel.add(buttonDelThis);
        southPanel.add(buttonFail);
        southPanel.add(buttonxSub);
        southPanel.add(buttonOptions);
        //southPanel.add(buttonSetPrinter);
        
        add(southPanel, BorderLayout.SOUTH);
        
       // FillMatrix(CurrentIndex);
       // ShowMatrix();
        
        for(int i=1;i<9;i++)
 		   for(int j=0;j<3;j++)
 		   {
 			   SetData(StaticMatrix[i][j],i-1,j);
 			   
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
    FillMatrix(currentindex);
    FillDistance();
    ShowMatrix();
    
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
         if(OneStudent.contains("PHY:")) Strim="Science"; else Strim="Commerce";
         temp=OneStudent.split("#");  ///Make pieces of the record cutting at #
         if(temp.length>=3) { sub=temp[2].split("="); //Cut further at "="
                              DiviField.setText(sub[0]);
                             }
         
         Roll=temp[0]; ///Get Roll Number
         if(temp[1].length()>30) NameField.setText(temp[1].trim()); else NameField.setText(" ");
         
         this.setTitle("Roll No : "+Roll);
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
	                
	                if(subindex>7){show(Roll); return;}
	                subtotal[subindex]=subtotal[subindex]+strtointeger;
	                
	                //show(subtotal[subindex]);
	                if(temp[j].contains("U1")) { Matrix[1][subindex]=marks[1]; grand[1]=grand[1]+strtointeger;}
	                
	                if(temp[j].contains("T1")) { Matrix[2][subindex]=marks[1]; grand[2]=grand[2]+strtointeger;} 
	                if(temp[j].contains("U2")) { Matrix[3][subindex]=marks[1]; grand[3]=grand[3]+strtointeger;}
	                if(temp[j].contains("T2")) { 
	                	                        int original=0;
	                	                        if(Matrix[4][subindex]!="")
	                	                        original=Integer.parseInt(Matrix[4][subindex].replaceAll("[^0-9.]",""));
	                                            original=original+strtointeger;
	                                            if(!temp[j].contains("EVS") && !temp[j].contains("PTE"))
	                                            grand[4]=grand[4]+strtointeger;
	                                            if(temp[j].contains("EVS")) evs=strtointeger;     
	                                            String plate00=String.format("%02d",original);
	                	                        Matrix[4][subindex]=plate00;
	                	                        if(marks[0].length()>3)///if project-pract-oral
	                	                        {  String plateopp=String.format("%02d",strtointeger);
	                	                        	Matrix[8][subindex]=plateopp;
	                	                        }
	                	              
	                                            }
	               
	                
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
      GT650=0;
      String plate;
      Matrix[0][8]="Total";Matrix[0][7]="PTE";
      for (int i=1; i<5;i++) { GT1200=GT1200+grand[i];
    	                       plate=String.format("%d",grand[i]);
                               Matrix[i][8]=plate; 
    	                      }
      
      plate=String.format("%d/1200",GT1200);
      Matrix[5][8]=plate;
      int avgtotal=0; 
      for (int i=0; i<6;i++)  avgtotal=avgtotal+average[i]; 
      GT650=avgtotal+evs;
      plate=String.format("%d/650",avgtotal+evs);
      Matrix[6][8]=plate;
      
      Matrix[6][6]=Matrix[4][6];
      if(Matrix[4][7].contains("03")) Matrix[6][7]="A";
      else
    	  {if(Matrix[4][7].contains("02")) Matrix[6][7]="B"; else  Matrix[6][7]="C"; }
        
     Matrix[2][6]=Matrix[2][7]=Matrix[5][6]=Matrix[5][7]=Matrix[7][6]=Matrix[7][7]="";
     Matrix[4][6]=Matrix[4][7]=""; ///Remove EVS and PTE From 4th Row  
      
      
      if (gracecount>3 || gracetotal>15) { Remark="FAIL"; return; }
      
      for (int i=0; i<6;i++) if(grace[i]>5) { Remark="FAIL"; return; }
      
      if(evs<18) { Remark="FAIL"; return;}
      
      plate=String.format("%d/15",gracetotal);
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
     
     for(int i=0;i<3;i++) if(subtotal[sort[i]]<59)        
      {  int gap=59-subtotal[sort[i]];
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

    
    public  void ReadFromDisk()
    {   //String fnme="";
		
    	File f = new File(System.getProperty("java.class.path"));
    	File dir = f.getAbsoluteFile().getParentFile();
    	String path = dir.toString();
    	///to be removed for exported jar
    	String temp[];
    	temp=path.split(":");
    	path=temp[0];
    	////to be removed for exported jar
    	
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
		try {
			FirstLine=reader.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
    	
    
    
    }
 //////Save Moderation Report//////////////////////////////////////////
    public void SaveReport(final String ReportName)
    {
//    	SwingUtilities.invokeLater(new Runnable() {
  //  	    public void run() {
    	    
    	////////////////////////worker thread to wait before message
    	
    	File f = new File(System.getProperty("java.class.path"));
    	File dir = f.getAbsoluteFile().getParentFile();
    	String path = dir.toString();
    	
    	fylename=path+"/"+ReportName+"-Science.txt";
  	   //show(fylename);
  		//String fnem="/home/milind/Result-Moderation-Report.txt";
  			
  	     FileWriter f0=null;
  		 try {f0 = new FileWriter(fylename);	} catch (IOException e1) {e1.printStackTrace();	}
  	     String newLine = System.getProperty("line.separator");
  	       	     
  	     for(int i=0;i<strModSci.size();i++)
  	     {   
  	    	
  	         try { f0.write(strModSci.get(i));	} catch (IOException e) {e.printStackTrace();	}
  	        try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}  
           }

  	     try {f0.close();} catch (IOException e) {e.printStackTrace();}
  	     
         
  	    fylename=path+"/"+ReportName+"-Commerce.txt";
  	    FileWriter f1=null;
		 try {f1 = new FileWriter(fylename);	} catch (IOException e1) {e1.printStackTrace();	}
	     for(int i=0;i<strModCom.size();i++)
	     {    try { f1.write(strModCom.get(i));	} catch (IOException e) {e.printStackTrace();	}
	        try { f1.write(newLine);	} catch (IOException e) {e.printStackTrace();	}  
         }

	     try {f1.close();} catch (IOException e) {e.printStackTrace();}


	     
    	
    	
	     toast.AutoCloseMsg("Saved "+ReportName+" Report");
  	 
}

    
///////////////////END OF SAVE REPORT////////////////////////////////////
    
    
	
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

		 
	
    public void PrintResultCard(int PrnType)
    {	
    	
    	
    	//if(TotalReceived==0) { show("No Marklists To Prints"); return; }
    	JobNem=PrnType;
        //NumberOfReportPages=TotalReceived/EntriesPerPage;
        //if(TotalReceived%EntriesPerPage!=0) NumberOfReportPages++;
    
        PrintService ps = findPrintService(sp.PrinterName);                                    
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
					{case 0  : 
					 case 1  :  return PrintResults(g,pf,page);
					 case 2  :  return PrintPasses(g,pf,page);
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
	
		 if (pageno>TotalPrintPages)    // MultipageDoc 'page no' is zero-based
		    {  return NO_SUCH_PAGE;  // After NO_SUCH_PAGE, printer will stop printing.
	        }
		 
		// OneStudent=strArray.get(pageno+1);
		 // show(OneStudent);
	    
		 FillMatrix(pageno+startpageindex);
	    
		 int w[]={63,38,38,38,38,38,38,38,38,38,38,68};
		 int ws[]={83,38,38};
		 
		 Font MyFont = new Font("Liberation Serif", Font.PLAIN,9);
		 
		 int tlxstatic=60;
		 int tlx=leftmargin,tly=topmargin;
	
	     /// 72 dots per inch, leave left & top margin 1 inch each 
		 /// on actual paper, printer also leaves some margin
	     int h=20,downshift=13;
	     g.setFont(MyFont);
     
	     g.drawString("Mark Sheet showing the number of marks Obtained by  ",tlxstatic,tly-60);
	     g.drawString(NameField.getText().trim()+"    Roll : "+Roll.trim()+"   Div : "+DiviField.getText(),tlxstatic,tly-40);
	     g.drawString(" in each head of passing at FYJC ("+Strim+") class in the examination conducted during the academic Year "+ AY,tlxstatic,tly-20);
	    
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
        	   { if(Remark!="PROMOTED" && i==7) Matrix[i][j]="";
        		 g.drawRect(tlx+j*width,tly+i*h, width, h);
	             Centre(Matrix[i][j],width,tlx+j*width,tly+i*h+downshift,g);
	           }
	  
       
       // Now print Remark
      
       g.drawRect(tlx,tly+8*h,2*width, h);
       Centre("Remarks",2*width,tlx,tly+8*h+downshift,g);
       tlx=tlx+2*width;
       g.drawRect(tlx,tly+8*h,2*width, h);
       Centre(Remark,2*width,tlx,tly+8*h+downshift,g);
       
       tly=tly+230;
       g.drawString("NOTE  :  This marksheet has been prepared as per the instruction circular No 6987, dated 04/11/2009 issued by",leftmargin,tly);
       tly=tly+15;
       g.drawString("               Secretary, Maharashtra State Board of Secondary and Higher Secondary Education, Pune 411004",leftmargin,tly);
	
       return PAGE_EXISTS;
	 }
	
	
	
	public int PrintPasses(Graphics g, PageFormat pf, int pageno)
			throws PrinterException
	{ 
		/* half A4 size
    Paper paper = pf.getPaper();
    pf.setOrientation(PageFormat.PORTRAIT);
    paper.setSize(8*72,5*72);
    pf.setPaper(paper);
		  */
		
		           ///strArray.size()-2
		 if (pageno>TotalPrintPages)    // MultipageDoc 'page no' is zero-based
		    {  return NO_SUCH_PAGE;  // After NO_SUCH_PAGE, printer will stop printing.
	        }
		 
		// OneStudent=strArray.get(pageno+1);
		 // show(OneStudent);
	    
		 FillMatrix(intPasses.get(pageno));
	    
		 int w[]={63,38,38,38,38,38,38,38,38,38,38,68};
		 int ws[]={83,38,38};
		 
		 Font MyFont = new Font("Liberation Serif", Font.PLAIN,9);
		 
		 int tlxstatic=60;
		 int tlx=leftmargin,tly=topmargin;
	
	     /// 72 dots per inch, leave left & top margin 1 inch each 
		 /// on actual paper, printer also leaves some margin
	     int h=20,downshift=13;
	     g.setFont(MyFont);
     
	     
	     g.drawString("Mark Sheet showing the number of marks Obtained by  ",tlxstatic,tly-60);
	     g.drawString(NameField.getText().trim()+"    Roll : "+Roll.trim()+"   Div : "+DiviField.getText(),tlxstatic,tly-40);
	     g.drawString(" in each head of passing at FYJC ("+Strim+") class in the examination conducted during the academic Year "+ AY,tlxstatic,tly-20);
	    
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
        	   { if(Remark!="PROMOTED" && i==7) Matrix[i][j]="";
        		 g.drawRect(tlx+j*width,tly+i*h, width, h);
	             Centre(Matrix[i][j],width,tlx+j*width,tly+i*h+downshift,g);
	           }
	  
       
       // Now print Remark
      
       g.drawRect(tlx,tly+8*h,2*width, h);
       Centre("Remarks",2*width,tlx,tly+8*h+downshift,g);
       tlx=tlx+2*width;
       g.drawRect(tlx,tly+8*h,2*width, h);
       Centre(Remark,2*width,tlx,tly+8*h+downshift,g);
      
       
       tly=tly+230;
       g.drawString("NOTE  :  This marksheet has been prepared as per the instruction circular No 6987, dated 04/11/2009 issued by",leftmargin,tly);
       tly=tly+15;
       g.drawString("               Secretary, Maharashtra State Board of Secondary and Higher Secondary Education, Pune 411004",leftmargin,tly);
       
		return PAGE_EXISTS;
	 }

	
		

	public int PrintConsolidated(Graphics g, PageFormat pf, int pageno)
    {
		
		 if (pageno>=TotalPrintPages)    // MultipageDoc 'page no' is zero-based
		    {  return NO_SUCH_PAGE;  // After NO_SUCH_PAGE, printer will stop printing.
	        }
	
		 int w[]={63,38,38,38,38,38,38,38,38,38,38,68};
		 int ws[]={83,38,38};
		 int width=w[2]; ///same width for all 6 subjects
		 int tlxstatic=60;
		 int tlx=leftmargin,tly=35;
		 int h=15,downshift=10;
		 Font MyFont = new Font("Liberation Serif", Font.PLAIN,9);
		 g.setFont(MyFont);
	
		 Centre("S.I.W.S.N.R Swamy College of Commerce & Econocmics",500,tlxstatic,tly,g);
		 tly=tly+h;
		 Centre("Junior College",500,tlxstatic,tly,g);
		 tly=tly+h;
		 // Four Students Per Page
	    for (int k=0;k<4;k++)
	    {  tlx=leftmargin;tlxstatic=60;
	       if(startpageindex+pageno*4+k>endpageindex) continue;
		   FillMatrix(startpageindex+pageno*4+k); ///////
	     /// 72 dots per inch, leave left & top margin 1 inch each 
		 /// on actual paper, printer also leaves some margin
	     g.drawString(NameField.getText().trim(),tlxstatic,tly);
	     tly=tly+h;
	     g.drawString("Roll : "+Roll.trim()+"   Div : "+DiviField.getText()+" ("+Strim+") Year : "+AY,tlxstatic,tly);
	     tly=tly+h-5;
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
	      for(int j=0;j<9;j++)
     	 for(int i=0;i<8;i++)
     	   { if(Remark!="PROMOTED" && i==7) Matrix[i][j]="";
     		 g.drawRect(tlx+j*width,tly+i*h, width, h);
	             Centre(Matrix[i][j],width,tlx+j*width,tly+i*h+downshift,g);
	           }
	  
    // Now print Remark
   
    g.drawRect(tlx,tly+8*h,2*width, h);
    Centre("Remarks",2*width,tlx,tly+8*h+downshift,g);
    tlx=tlx+2*width;
    g.drawRect(tlx,tly+8*h,2*width, h);
    Centre(Remark,2*width,tlx,tly+8*h+downshift,g);
    
    ///////////Shift Down For Second Student
    
    tly=tly+150;
    
   }/////End of for loop k=0
    ///Print Footer
	    g.drawString("Exam Dept",60,810);
	    g.drawString("Principal/Vice-Principal",450,810);
	    
    return PAGE_EXISTS;

  }	


    
///// End of JAVA Printing Functions
/////////////////////////////////////////////////
///////////////////////////////////////////////
    
    
    
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
    
    
    
    
    class UppercaseDocumentFilter extends DocumentFilter
    {
        public void insertString(DocumentFilter.FilterBypass fb, int offset,
                String text, javax.swing.text.AttributeSet attr) throws BadLocationException 
                {

            fb.insertString(offset, text.toUpperCase(), attr);
                }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                String text, javax.swing.text.AttributeSet attrs) throws BadLocationException
                {

            fb.replace(offset, length, text.toUpperCase(), attrs);
        }
    }
    
    void Mod()
    {if(strArray.size()==0) { show("No Data"); return;}
    strModSci.removeAll(strModSci);
    strModCom.removeAll(strModCom);
    String plate;
    int totalrecords=strArray.size();
    for(int i=0;i<totalrecords;i++) 
    { FillMatrix(i);
      if(!Remark.contains("FAIL")) continue;
      FillDistance();
      ShowMatrix();
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
   //if(i>1400) show(plate);
      if(plate.contains("PHY")) strModSci.add(plate);
      if(plate.contains("OCM")) strModCom.add(plate);
    
    }
    
   Collections.sort(strModSci);
   Collections.sort(strModCom);
   SaveReport("Moderation");

    	
    }
 
    void PrintConsolidatedResult()
    {String temp[],temp1[];
   // intPasses.removeAll(intPasses);
  //JTextField yField = new JTextField(10);

 // JPanel myPanel = new JPanel();
//  JCheckBox checkbox = new JCheckBox("Only Non-Failures");
  //String message = "Are you sure you want to disconnect the selected products?";
//  boolean dontShow = checkbox.isSelected();
  
  //myPanel.add(checkbox);
	  String Range = JOptionPane.showInputDialog(null,"Enter Range Separated by -");
	  
	  if(Range == null || (Range != null && ("".equals(Range))))   
	  {
	      return;
	  }
	  
	  
	  if(!Range.contains("-")) return;
	  
	  temp1=Range.split("-");
	//  printpass=checkbox.isSelected();
	//  if(checkbox.isSelected()) show("selected"); else show("non-selected");
	  
	  startpageindex=endpageindex=TotalPrintPages=0;
	 for(int i=0;i<strArray.size();i++)
	  { temp=strArray.get(i).split("#");
		if(temp[0].contains(temp1[0])) {startpageindex=i;break;} 
    }
	 
	 for(int i=0;i<strArray.size();i++)
	      { temp=strArray.get(i).split("#");
		    if(temp[0].contains(temp1[1])) {endpageindex=i;break;} 
    }
	
	 
//  show(startpageindex);show(endpageindex);	 
	 
	if(startpageindex<=endpageindex) TotalPrintPages=(endpageindex-startpageindex+1)/4;
	else {show("Error In Range"); return;}
    if((endpageindex-startpageindex+1)%4!=0) TotalPrintPages++;
     show(TotalPrintPages);
     PrintResultCard(3);
    	
    }
    
    void Saral()
    {  if(strArray.size()==0) { show("No Data"); return;}
    
       strModSci.removeAll(strModSci);
       strModCom.removeAll(strModCom);
       String plate,subtitles;
       float percent;
       String passfail;
       int totalrecords=strArray.size();
       for(int i=0;i<totalrecords;i++) 
         { FillMatrix(i);
          if(Remark.contains("FAIL"))passfail="FAIL"; else passfail="PROMOTED";
           ShowMatrix();
           percent=(float)((GT1200*100.0f)/1200);
           plate=String.format("Div : %s Roll : %-4s  Name : %s Result : %s Percentage : %.2f",DiviField.getText(),Roll.trim(),NameField.getText().trim(),passfail,percent);
     subtitles="";
     for(int j=3;j<9;j++)
     {
       subtitles=subtitles+table2.getColumnModel().getColumn(j).getHeaderValue();         ///subject name
       subtitles+="=";
      
     }
     if(subtitles.contains("PHY")) strModSci.add(plate);
     if(subtitles.contains("OCM")) strModCom.add(plate);
     
         }
     //Collections.sort(strModSci);
     //Collections.sort(strModCom);
     SaveReport("Saral List");
  	
      
   }
    
    
    void FList()
    {  if(strArray.size()==0) { show("No Data"); return;}
       strModSci.removeAll(strModSci);
       strModCom.removeAll(strModCom);
       String plate,subtitles;
       int totalrecords=strArray.size();
       for(int i=0;i<totalrecords;i++) 
         { FillMatrix(i);
           if(!Remark.contains("FAIL")) continue; ///
           ShowMatrix();
           plate=String.format("Roll : %-4s  Div : %s  Name : %s",Roll.trim(),DiviField.getText(),NameField.getText().trim());
     subtitles="";
     for(int j=3;j<9;j++)
     {
       subtitles=subtitles+table2.getColumnModel().getColumn(j).getHeaderValue();         ///subject name
       subtitles+="=";
      
     }
     
      if(subtitles.contains("PHY")) strModSci.add(plate);
      if(subtitles.contains("OCM")) strModCom.add(plate);
    
    }
    
   //Collections.sort(strModSci);
   //Collections.sort(strModCom);
   SaveReport("Failure List");
	
    }
    
    
    void Stat()
    {
    	if(strArray.size()==0) { show("No Data"); return;}
        int sci=0,sciFA=0,sciPR=0,sciPA=0,sciG3=0,sciG2=0,sciG1=0;
        int com=0,comFA=0,comPR=0,comPA=0,comG3=0,comG2=0,comG1=0;
        
       	
        String temp;
        for(int i=0;i<strArray.size();i++)
        {  
       	temp=strArray.get(i);
       	FillMatrix(i);
       	if(temp.contains("PHY:"))
       	{   sci++;
       	    if(Remark=="FAIL") sciFA++;
       	    if(Remark=="PROMOTED") sciPR++;
       	    if(Remark=="Grade Pass") sciPA++;
       	    if(Remark=="Grade III") sciG3++;
       	    if(Remark=="Grade II") sciG2++;
       	    if(Remark=="Grade I") sciG1++;
       	}
       	else
       	{  com++;
       	   if(Remark=="FAIL") comFA++;
   	       if(Remark=="PROMOTED") comPR++;
   	       if(Remark=="Grade Pass") comPA++;
   	       if(Remark=="Grade III") comG3++;
   	       if(Remark=="Grade II") comG2++;
   	       if(Remark=="Grade I") comG1++;
       		
       	}
       	
        }
        
       String plate;
        plate=String.format("Commerce  :  \n\nGrade I : %d\nGrade II : %d\nGrade III : %d\nGrade PASS : %d\nPROMOTED : %d\nPASS : %d\nFAIL : %d\nTotal : %d\n\n"+
       		 "Science  :  \n\nGrade I : %d\nGrade II : %d\nGrade III : %d\nGrade PASS : %d\nPROMOTED : %d\nPASS : %d\nFAIL : %d\nTotal : %d\n\n"
        					 ,comG1,comG2,comG3,comPA,comPR,com-comFA,comFA,com,sciG1,sciG2,sciG3,sciPA,sciPR,sci-sciFA,sciFA,sci);
        show(plate);

    }
    
    void DeleteVacants()
    {int SKIP=1,DELETE=2;
	String onestudent,temp[],errorMessage = "";
    int hashcount;

do { // Show input dialog with current error message, if any
    String stringInput = JOptionPane.showInputDialog
    		(errorMessage + "Enter Hash Count");
    try { hashcount = Integer.parseInt(stringInput);
        if (hashcount < 0 || hashcount > 100) 
         {  errorMessage = "That number is not within the \n" + "allowed range!\n";
            
         }
    } catch (NumberFormatException e) 
    {
        // The typed text was not an integer
        errorMessage = "The text you typed is not a number.\n";
        return;
    }
} while (!errorMessage.isEmpty());

if(!errorMessage.isEmpty()) return;
    int totalrecords=strArray.size(); 

	for (int j=0;j<totalrecords;j++)
	 { onestudent=strArray.get(j);
	   temp=onestudent.split("#");
	   if(temp.length==hashcount) continue;
	
	   currentindex=j; 
	   FillMatrix(currentindex);
		   FillDistance();
		   ShowMatrix();
	   
		   
	   /////This is possibly vacant, so pop confirmation
		Object[] options = {"Cancel Process","Skip This Roll","Delete"};
   int result = JOptionPane.showOptionDialog(
		   ///////////// first argument of JOptionPane for corner location
		   new javax.swing.JFrame()
		     { 
	     
			private static final long serialVersionUID = 1L;
		public boolean isShowing(){return true;}
	      public java.awt.Rectangle getBounds(){return new java.awt.Rectangle(1000,100,0,0);}
	    },  /////////////end of first argument of JOptionPane
	    
    "Delete This Roll Permanently ?",
    "Alert Dialog",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,
     null,        //do not use a custom Icon
     options,     //the titles of buttons
     options[0]); //default button title
		   
		  
	   ////else result=0 i.e YES
	   
	  if(result==DELETE)
	  {
	   grandtotal=grandtotal-GT1200;
       String ttt;
       ttt=String.format("%d",grandtotal);
       GTlabel.setText(ttt);
	   
       strArray.remove(currentindex);
       totalrecords=strArray.size();
       if(j>0) j--;
       continue;
	  }
	  if(result==SKIP) continue;
	  return;
	 }  	
    	
    }
    
    
    void CardsPdf() throws DocumentException, IOException
    {
    	
    	String filename="hello.pdf";
    		
    	Document document = new Document(PageSize.A4);
    	PdfWriter.getInstance(document, new FileOutputStream(filename));
    	document.open();
    	
    	
    	
         com.itextpdf.text.Font NORMAL = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 12);
        	 
    	 
     //    Font TNR=new Font(TNR16)
     // Phrase phr=new Phrase("Test",TNR16);
      
    	PdfPTable table = new PdfPTable(12);
    	Phrase phr=new Phrase("Test",NORMAL);
    	
    	
    	PdfPCell cell = new PdfPCell(phr);
    	 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	 //cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
    	
    	 
    	 
    	 //Phrase content = new Phrase("Blah blah blah", Font);

    	// Float fontSize = NORMAL.getSize();
        //    	 Float capHeight = 

    	 Float padding = 5f;    

    	// PdfPCell cell = new PdfPCell(content);
    	 //cell.setPadding(padding);
    	 cell.setPaddingBottom(padding);
    	 
    	 
    	 for(int i=0;i<120;i++)
    	{
    	table.addCell(cell);
    	}
    	    	
    	document.add(table);
    
    	document.close();
    	
    }
    
    
    
    
    
    void Merit()
    {	
    	  if(strArray.size()==0) { show("No Data"); return;}
          strModSci.removeAll(strModSci);
          strModCom.removeAll(strModCom);
          String plate,subtitles;
          int totalrecords=strArray.size();
          for(int i=0;i<totalrecords;i++) 
          { FillMatrix(i);
            if(Remark.contains("FAIL")) continue;
           ShowMatrix();
           plate=String.format("GT : %4d  ",GT650);
           plate=plate+"Roll :"+Roll+" - " + NameField.getText();
           subtitles="";
           for(int j=3;j<9;j++)
           {
             subtitles=subtitles+table2.getColumnModel().getColumn(j).getHeaderValue();         ///subject name
             subtitles+="=";
            
           }
           
            if(subtitles.contains("PHY")) strModSci.add(plate);
            if(subtitles.contains("OCM")) strModCom.add(plate);
          
          }
          
         Collections.sort(strModSci);
         Collections.sort(strModCom);
         SaveReport("Merit");
      
    }
    
    
    void SubMerit(String subname)
    {	
    	  if(strArray.size()==0) { show("No Data"); return;}
          strModSci.removeAll(strModSci);
          strModCom.removeAll(strModCom);
          String plate,subtitles;
          int totalrecords=strArray.size();
          for(int i=0;i<totalrecords;i++) 
          { FillMatrix(i);
            if(Remark.contains("FAIL")) continue;
           ShowMatrix();
           subtitles="";
           int index=-1; /// assume sub not found
           String tempsub;
           for(int j=3;j<9;j++)
           { tempsub=(String) table2.getColumnModel().getColumn(j).getHeaderValue();
             subtitles=subtitles+ tempsub;   ///subject name
             subtitles=subtitles+"=";
             if(tempsub.contains(subname)) index=j-3;           
           }
           if(index==-1) continue;
           plate=String.format("%s : %4d  ",subname,subtotal[index]);
           plate=plate+"Roll :"+Roll+" - " + NameField.getText();
           
            if(subtitles.contains("PHY")) strModSci.add(plate);
            if(subtitles.contains("OCM")) strModCom.add(plate);
          
          
        }    
          
         Collections.sort(strModSci);
         Collections.sort(strModCom);
         SaveReport("Sub - Merit - "+subname);
      
    }
    
    
  void SaveEmptyNames()
   {
	   if(strArray.size()==0) { show("No Data"); return;}
       strModSci.removeAll(strModSci); ///reuse mod
     
       
      
   	String tempstr;
       String temp[];
       int TotalRolls=strArray.size();
      
       for(int i=0;i<TotalRolls;i++)
       { tempstr=strArray.get(i);
         temp=tempstr.split("#");
          
        if(temp[1].length()<30) strModSci.add(temp[0]); ///no name found
      	
      
      	 }
       
       File f = new File(System.getProperty("java.class.path"));
   	File dir = f.getAbsoluteFile().getParentFile();
   	String path = dir.toString();
   	
   	fylename=path+"/Empty Names - List.txt";
 	  	
 	     FileWriter f0=null;
 		 try {f0 = new FileWriter(fylename);	} catch (IOException e1) {e1.printStackTrace();	}
 	     String newLine = System.getProperty("line.separator");
 	       	     
 	     for(int i=0;i<strModSci.size();i++)
 	     {   
 	         try { f0.write(strModSci.get(i));	} catch (IOException e) {e.printStackTrace();	}
 	        try { f0.write(newLine);	} catch (IOException e) {e.printStackTrace();	}  
          }
 	     try {f0.close();} catch (IOException e) {e.printStackTrace();}
    
 	    toast.AutoCloseMsg("Saved Empty Names");
   }
    
  
    
}