package in.refort.MarkList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.MenuItem;

public class MarkListActivity extends Activity {
    /** Called when the activity is first created. */
	private ListView lv;
	int froll=151,lroll=160;
    int SmoothDist=58;
    int SmoothDistMinus=-72;
    int TotQns=10;
    int SetNo=1;
    int TotSets=1;
   
    
    int in1,in2,strength,Max,MCQmarks=0;
    String mcqscore="00",sMax="80",tempstr,Examiner="",Exam="",Subject="";
    String Div="",Clas="",FileNameWithPath="",Date;
    String CollegeName1="",CollegeName2="",CollegeName3=" ", fylename,title,Stream=" ";
    String plate1;
    String Email1="",Email2="";
    
    
    Boolean modified=false,end=false,OpenNow=false,NewNow=false,MCQmode=false,autoscroll=true;
    ArrayList<String> Roll = new ArrayList<String>();
    ArrayList<String> Set = new ArrayList<String>();
    ArrayList<String> Mrk = new ArrayList<String>();
    /*  
     ////  Code To Stop Scrolling suddenly if touched (Not Tested)
    @Override 
    public boolean onTouchEvent(MotionEvent ev) 
    { 
    switch (ev.getAction()) 
       {
       case MotionEvent.ACTION_UP: lv.smoothScrollBy(0, 0); 
       break;
       case MotionEvent.ACTION_DOWN: lv.smoothScrollBy(0, 0); 
       break;
       } 
       return super.onTouchEvent(ev);
     }
   */
    public void showtop(String tempstr)
    {	
  ///////////////////  SHIFTED TO TOP
    	
            	Toast toast= Toast.makeText(getBaseContext(), 
    			tempstr, Toast.LENGTH_SHORT);                                      
    			toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0); ///top page,reqd import
    			//toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
    	toast.show();
   
////////////////////////////
   /* 	
    	Toast toast = Toast.makeText(this, tempstr, Toast.LENGTH_SHORT);
    	TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.RED);
        v.setBackgroundColor(Color.YELLOW);
    	toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0); ///top page,reqd import
        v.setTextSize(22);
    	toast.show();
   */
   /////////////////////////////////////////////////////////////////////
    	
    	/*
    	 Toast toast = Toast.makeText(context, R.string.yummyToast, Toast.LENGTH_SHORT);
    	    LinearLayout toastLayout = (LinearLayout) toast.getView();
    	    TextView toastTV = (TextView) toastLayout.getChildAt(0);
    	    toastTV.setTextSize(30);
    	    toast.show();
    	*/
    
    }
    
    public void show(int tempnum)
    {
    	Toast.makeText(getBaseContext(),String.valueOf(tempnum),Toast.LENGTH_SHORT).show();
    }
    
    public void show(String tempstring)
    {
    	Toast.makeText(getBaseContext(),tempstring,Toast.LENGTH_SHORT).show();
    }
    
    
    public void WarnBeforeOpen()
    {    	  
         	 AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
	    	 myAlertDialog.setTitle("Modified !");
	    	 myAlertDialog.setMessage("Save Marklist Before Open ?");
	    	 myAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    	 {
        	 public void onClick(DialogInterface arg0, int arg1) {
	    	  // do something when the OK button is clicked
	    	     OpenNow=true;
        		 SaveMarkList();
      	 
        	 }});
	
	    	 myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	         	 public void onClick(DialogInterface arg0, int arg1) {
                 /////do nothing and continue
	 	    	  }});
	    	 
	   	 myAlertDialog.setNeutralButton("No", new DialogInterface.OnClickListener() {
	    	 public void onClick(DialogInterface arg0, int arg1) {
	    	  OpenFile();
	    		 
	    	  }});

	    	 myAlertDialog.show();
	   
    }
    public void WarnBeforeNew()
    {    	  
	    	 AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
	    	 myAlertDialog.setTitle("Modified !");
	    	 myAlertDialog.setMessage("Save Marklist Before New ?");
	    	 myAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    	 {
        	 public void onClick(DialogInterface arg0, int arg1) {
	    	  // do something when the OK button is clicked
	    	     NewNow=true;
        		 SaveMarkList();
        	 }});
	    	 myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	         	 public void onClick(DialogInterface arg0, int arg1) {
                 /////do nothing and continue
	 	    	  }});
	    	 
	   	 myAlertDialog.setNeutralButton("No", new DialogInterface.OnClickListener() {
	    	 public void onClick(DialogInterface arg0, int arg1) {
	    	  GetNewRoll();
	    		 
	    	  }});

	    	 myAlertDialog.show();
    }
    
    private void CutABS()
    {String stemp;
     int cut=0; 
        for(int i=strength+1;i>1;i--)
        {  stemp=Roll.get(i);
           if(stemp.contains("AB")) 
            { if(strength>5)
                { Roll.remove(i);
                  strength--;		    
		          cut++;
                }
            }
        }
        if(cut>0)
        {modified=true;
		((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
    	lv.setSelection(0);
    	lv.invalidate();
		stemp=Integer.toString(cut);
		String msg=stemp + "  AB's Removed"; 
        showtop(msg);
        
        }
        
    }
    
    private void LongPressDelete(int RollIdx)
    { 	if(RollIdx<2 ||RollIdx>strength+2) {show("Out of Range"); return;}
    	if(strength<6) return;
    	int toproll=lv.getFirstVisiblePosition();
    	Roll.remove(RollIdx);
		modified=true;
		((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
    	lv.setSelection(toproll);
    	lv.invalidate();
		strength--;
	}
    
    private void LongPressInsertAbove(int RollIdx)
    { 	if(RollIdx<2 ||RollIdx>strength+2) {show("Out of Range"); return;}
    	String temp[],stemp;
    	if(RollIdx<2 ||RollIdx>strength+2) {showtop("Out of Range"); return;}
    	
    	int toproll=lv.getFirstVisiblePosition();
    	stemp=Roll.get(RollIdx);
    	temp=stemp.split(":");
    	int temproll=Integer.parseInt(temp[0].replaceAll("[^0-9.]",""));
    	Roll.add(RollIdx,Integer.toString(temproll-1)+ " :    ");
		modified=true;
		((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
		lv.setSelection(RollIdx-2); lv.smoothScrollBy(SmoothDist, 2500);
        lv.invalidate(); 
    	
		strength++;
	}
    
    private void LongPressInsertBelow(int RollIdx)
    { 	if(RollIdx<2 ||RollIdx>strength+2) {show("Out of Range"); return;}
    	String temp[],stemp;
    	if(RollIdx<2 ||RollIdx>strength+2) {showtop("Out of Range"); return;}
    	
    	int toproll=lv.getFirstVisiblePosition();
    	stemp=Roll.get(RollIdx);
    	temp=stemp.split(":");
    	int temproll=Integer.parseInt(temp[0].replaceAll("[^0-9.]",""));
    	Roll.add(RollIdx+1,Integer.toString(temproll+1)+ " :    ");
		modified=true;
		((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
		lv.smoothScrollBy(SmoothDist, 2500);lv.setSelection(RollIdx-1);
        lv.invalidate();
		strength++;
	}
    
    private void ShowPageTotal()
    { int PageTotal=0,len;
      String temp[],stemp;
    for(int i=2;i<strength+2;i++)
    { stemp=Roll.get(i);
      if(stemp.contains("AB")) continue;
      temp=stemp.split(":");
      stemp=temp[1].trim(); ///reuse stemp
      len=stemp.length();
      if(len==0) continue;
     int tempnum = Integer.parseInt(temp[1].replaceAll("[^0-9.]","")); 
     PageTotal=PageTotal+tempnum;
    }
    
    String msg=String.format("Page Total : %d",PageTotal);
    showtop(msg);
    }
    	
    
    
    
    private void ShowInfo(String about)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
        ArrayList<String> listItems=new ArrayList<String>();
        //DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
        ArrayAdapter<String> adapter;
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
              listItems.clear();
              String stemp,temp[],ttemp;
              int i,tempnum=0,marks,len;
               
              if(about=="00")
              {   tempnum=0;
                  
                  for(i=2;i<strength+2;i++)
                  {stemp=Roll.get(i);
                   temp=stemp.split(":");
                        
                  if(temp[1].contains(" 00") || temp[1].contains(" 0 ")) 
     	   	        {listItems.add(stemp);tempnum++;
     	   	        }
                  }
     	   	   stemp=String.valueOf(tempnum);	
     	   	    builder.setTitle(stemp + " Zeroes");
               }              
              
                  if(about=="AB")
                  {   tempnum=0;
                      
                      for(i=2;i<strength+2;i++)
                      {stemp=Roll.get(i);
                      if(stemp.contains("AB")) 
         	   	        {listItems.add(stemp);tempnum++;
         	   	        }
                      }
         	   	   stemp=String.valueOf(tempnum);	
         	   	    builder.setTitle(stemp + " Absentees");
                   }              
                  
	          if(about=="TP")
	          {     	  
	        	    for(i=2;i<strength+2;i++)
	        	    {   stemp=Roll.get(i);
	                   if(stemp.contains("AB")) continue;
	                   temp=stemp.split(":");
	                   stemp=temp[1].trim(); ///reuse stemp
	                   len=stemp.length();
	                   switch(len)
	                   {case 0 : ttemp="      "; break;
	                    case 3 : ttemp="100"; break;
	                    default :ttemp="  "+stemp;break;
	                   }
	                  
            
	        	    	listItems.add("Marks : "+ttemp + "  Roll : " + temp[0]);
	        	    	
	        	    }
	        	      
	        	    adapter.sort(new Comparator<String>() 
	        	        		{
	        	        	public int compare(String object1, String object2) 
	        	        	      {
	        	        		return object2.compareToIgnoreCase(object1);
	        	        	      };
	        	        		});
	        	    
	           builder.setTitle("Merit List");
	          }
        LayoutInflater factory = LayoutInflater.from(this);
        View content = factory.inflate(R.layout.listdialog, null);

        ListView lv = (ListView) content.findViewById(R.id.list);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        builder.setNegativeButton("CLOSE", null);
        builder.setView(content);
        AlertDialog alert = builder.create();
        alert.show();
    }
    
    private boolean Beyond()
    {  String temp[];
    	temp=plate1.split(":");
    	///assert temp!=null
    	
		int marks = Integer.parseInt(temp[1].replaceAll("[^0-9.]",""));
	    if(sMax==null){showtop("Marks Exceed Max Marks : " + sMax); return true;}
	    if(sMax.length()==0) sMax="00";
	    int maximummarks=Integer.parseInt(sMax.replaceAll("[^0-9.]",""));
        if(marks>maximummarks)
        {showtop("Marks Exceed Max Marks : " + sMax); return true;}
        else
         return false;
    }
    
    public void Bye()
    {    	  
	    	 AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
	    	 myAlertDialog.setTitle("Modified !");
	    	 myAlertDialog.setMessage("Save Marklist Before Exit ?");
	    	 myAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    	 {
        	 public void onClick(DialogInterface arg0, int arg1) {
	    	  // do something when the OK button is clicked
	    	     end=true;
        		 SaveMarkList();
	    	         	 
        	 }});
	    	 
	    	 myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	         	 public void onClick(DialogInterface arg0, int arg1) {
                 /////do nothing and continue
	 	    	  }});
	    	 
	   	 myAlertDialog.setNeutralButton("No", new DialogInterface.OnClickListener() {
	    	 public void onClick(DialogInterface arg0, int arg1) {
	    	  // do something when the Cancel button is clicked
	    		 finish();
	    	  }});

	    	 myAlertDialog.show();
    }
    
    private void OpenFile()
    {	OpenNow=false;
    	List<String> listItems = new ArrayList<String>();
    	File mfile=new File("/sdcard");
  	File[] list=mfile.listFiles();
  	  String tempupper;
  	     for(int i=0;i<mfile.listFiles().length;i++)
  	     {
  	      	 tempstr=list[i].getAbsolutePath();
  	      	 tempupper=tempstr.toUpperCase();
  	      	 if(tempupper.endsWith(".MRK") )
  	    	 listItems.add(list[i].getAbsolutePath());
  	     }
           
  	 final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
  	 
  	  AlertDialog.Builder builder = new AlertDialog.Builder(this);
  	 builder.setTitle("Select File To Open...");
  	 builder.setItems(items, new DialogInterface.OnClickListener()
  	 {
  	 public void onClick(DialogInterface dialog, int item)
  	    {String ttt= (String) items[item];
  	      OpenList(ttt);
  	    }
  	 });
  	 AlertDialog alert = builder.create();
  	 alert.show();
  }
    
    private void FlipRoutine()
    		{
    if(TotSets==1) return;
	  SetNo++;
	  if(SetNo>TotSets) SetNo=1;
	  String temp=String.format("%02d", SetNo);
	  final Button buttonflipset = (Button) findViewById(R.id.FlipSet);
	  buttonflipset.setText(temp);
	  final TextView optionview = (TextView) findViewById(R.id.OpsKey);
	  final TextView marksview = (TextView) findViewById(R.id.MarKey);
	  optionview.setText(Set.get(SetNo));
	  marksview.setText(Mrk.get(SetNo));
    		}
    
     
    private void ProcessDigit(char ch,ArrayList Roll)
    {  	modified=true;
    	int start = lv.getFirstVisiblePosition();
        if(start>strength-1) start=strength-1;
     	boolean move=true;
     	int end=lv.getLastVisiblePosition();
     	String original= (String) lv.getItemAtPosition(start+2);
     	String nextroll=(String) lv.getItemAtPosition(start+3);
     	int len=original.length();
        
     	switch(ch)
     	{ case 'A' : { plate1=original.substring(0,len-3)+"AB ";
     	              
     	               if(!nextroll.contains(":")) move=false;
     	               Roll.set(start+2,plate1);
     	              }
     	             break;
     	case 'M' : { plate1=original.substring(0,len-3)+ mcqscore;
     	if(Beyond()) {	plate1=original.substring(0,len-3)+"   "; 
                        Roll.set(start+2,plate1);
                        move=false;break;
                      }        
         if(!nextroll.contains(":")) move=false;
         Roll.set(start+2,plate1);
        }
       break;
     	
     	  case 'H' :{ 
     		          plate1=original.substring(0,len-3)+"100";
     		         if(Beyond()) {	plate1=original.substring(0,len-3)+"   "; 
                                    Roll.set(start+2,plate1);
                                    move=false;break;
                                    }
     		        if(!nextroll.contains(":")) move=false;
                      Roll.set(start+2,plate1);
                      } 
     		        break;
     	 case 'B' :{ plate1=original.substring(0,len-3)+"   ";  ///Back Space
                     Roll.set(start+2,plate1);
                     move=false;
                    }
	        break;

     	  default  : 
     	    	String temp[];
     	    	temp=original.split(":");
     	if((temp[1].charAt(1)!=' ' && temp[1].charAt(2)!=' ') || (temp[1].charAt(1)==' ' && temp[1].charAt(2)==' ') )
     	{move=false;
     	plate1=original.substring(0,len-3)+ch+"  ";
     	if(Beyond()) {	plate1=original.substring(0,len-3)+"   "; 
     					Roll.set(start+2,plate1);
     					move=false;break;
     				  }
     	 Roll.set(start+2,plate1);
     	}
     	else
     	{  
     	   plate1=original.substring(0,len-2)+ch+" ";
     	  if(Beyond()) { plate1=original.substring(0,len-3)+"   "; 
     	  				 Roll.set(start+2,plate1);
     	  				 move=false;break;
     	  				}
     	 if(!nextroll.contains(":")) move=false;
     		Roll.set(start+2,plate1);
     	}

     	break;
     	}  ///////switch statement over
     	
     	for(int i=start;i<end;i++)
     	{   View view = lv.getChildAt(i-start);
     		lv.getAdapter().getView(i, view, lv);
     	}
     	
     	
     	if(move)
     	{
     	lv.smoothScrollBy(SmoothDist, 2500);
     	lv.setSelection(start+1);
     	}
     	
    } 
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.setHeaderTitle("Select The Action"); 
            menu.add(0, v.getId(), 0, "Insert Roll Above"); 
            menu.add(0, v.getId(), 0, "Insert Roll Below");
            menu.add(0, v.getId(), 0, "Delete This Roll");
    } 

    @Override 
   public boolean onContextItemSelected(MenuItem item)
   { 
               AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
              
       //  info.position will give the index of selected item
                  int Index=info.position ;
                  if(Index<2 || Index>strength+2) {showtop("Out of Range"); return true;}
                  String itemtitle=(String) item.getTitle();
                       if(itemtitle=="Insert Roll Above") { LongPressInsertAbove(Index); return false;}
                       if(itemtitle=="Insert Roll Below") { LongPressInsertBelow(Index); return false;}
                       if(itemtitle=="Delete This Roll")  { LongPressDelete(Index); return false;} 
             return true; 
     } 
    
    @Override
    public void onBackPressed() {
        if(modified) Bye();
        else finish();
        return;
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);
       outState.putStringArrayList("temp12", Roll);
        }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        ////load preferences       
        SharedPreferences settings = getSharedPreferences("COLL", 0);
        CollegeName1 = settings.getString("key1", "SIWS College, Wadala");
        CollegeName2 = settings.getString("key2", "Mumbai-400031");
        Email1 = settings.getString("key3", "");
        Email2 = settings.getString("key4", "");
        
        Calendar c = Calendar.getInstance();
        //System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date = df.format(c.getTime());
        
        
       strength=lroll-froll+1;
        if( savedInstanceState != null ) {
            Roll=savedInstanceState .getStringArrayList("temp12");
         }
        else
        {  Roll.removeAll(Roll);
            Roll.add("   ");
            Roll.add("   ");
            for(int i=0;i<strength;i++)
    	   	 {  
            	Roll.add(Integer.toString(froll+i)+ " :    ");
        	   }
            Roll.add("   ");
            Roll.add("   ");
            Roll.add("   ");
            Roll.add("   ");
            Roll.add("   ");
            
        }
        lv = (ListView) findViewById(R.id.RollView);
            
        ////////////First Default Title
        final TextView myTitleText = (TextView) findViewById(R.id.ttext);
		 //if (myTitleText != null)
		     myTitleText.setText("MarkList 2.6 - Untitled");
        
        
        
        ////////////////////////Set and Mrk Array Initialization
        
        for(int i=0;i<100;i++)  { Set.add(""); Mrk.add("");} 
        
        
        // This is the array adapter, it takes the context of the activity as a first // parameter, the type of list view as a second parameter and your array as a third parameter
        ArrayAdapter<String> arrayAdapter =      
        new ArrayAdapter<String>(this,R.layout.simplerow1, Roll);
        lv.setAdapter(arrayAdapter);
      
       // lv.setMinimumHeight(200);
        registerForContextMenu(lv);

        lv.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event)
            {
               autoscroll=false;    
                return false;
            }
        });        
        
        lv.post(new Runnable()
        {
            public void run() {

            	View Vu = lv.getChildAt(0);
     		   if(Vu!=null)
     	    	{SmoothDist=Vu.getHeight();
     		     //show(SmoothDist);
     	    	lv.smoothScrollBy(SmoothDist, 2500);
     	        lv.invalidate();
     	    	}
               }
            });
     //   lv.setOnTouchListener(l)
        lv.setOnScrollListener(new OnScrollListener()
        {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) 
            {
              // TODO Auto-generated method stub
            }
            public void onScrollStateChanged(AbsListView view, int scrollState) 
            {
            	
              // TODO Auto-generated method stub
              if(scrollState == 0)  
            	  
            	  lv.post(new Runnable()
                  {  
                      public void run() 
                      {   if(autoscroll==true) return;
                          
                    	  //int top=lv.getFirstVisiblePosition();
                      
                      
                    	  
                      /*
                      if(top>1 && top<strength+2)
                    	  lv.smoothScrollToPosition(top);
                    	  lv.post(new Runnable()
                          {
                              public void run() 
                              {
                            	  int top=lv.getFirstVisiblePosition();
                            	  lv.setSelection(top);
                              }
                              });
                              */
                    	  autoscroll=true;
                    	  //int pop=lv.getFirstVisiblePosition();
                    	  //lv.smoothScrollToPosition(pop);
                    	
                    	  if (lv.getChildAt(0) != null)
                    	  {
                              Rect r = new Rect();
                              lv.getChildVisibleRect(lv.getChildAt(0), r, null);
                    	  
                    	  
                    	  //View v = lv.getChildAt(0);
                          //int top = (v == null) ? 0 : v.getTop();
                    	  
                    	  lv.smoothScrollBy(SmoothDist+r.top, 2000);
                    	 // show(r.top);
                    	  }
                      }
                      });
               }
          });
        
        final Button buttonHeader = (Button) findViewById(R.id.Header);
		buttonHeader.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ 
				GetHeaderDlg();
			}
			});
		
		 final Button buttonSend = (Button) findViewById(R.id.SEND);
			buttonSend.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{  if(modified) { show("Save Marklist and then send");  return;}
					SendList();
				}
				});
		
		 final Button buttonBlank = (Button) findViewById(R.id.Blanks);
			buttonBlank.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{   //SendList();
					JumpBlank();
				}
				});
			
			final Button buttonUP = (Button) findViewById(R.id.UP);
			buttonUP.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) 
			{
			int start = lv.getFirstVisiblePosition();
			int end = lv.getLastVisiblePosition();
			String original = (String) lv.getItemAtPosition(start + 2);
			boolean move;
			int len = original.length();
			String temp[];
			temp = original.split(":");

			if (temp[1].charAt(1) != ' ' && temp[1].charAt(2) == ' ') {
			/*temp[1]="0"+temp[1].charAt(2);
			plate1=temp[1];
			Roll.set(start +2,plate1);*/
			char ch = temp[1].charAt(1);
			move = false;
			showtop("Enter 2 Digit Marks.");
			plate1 = original.substring(0, len - 3) + ch + " ";
			if (Beyond()) {
			plate1 = original.substring(0, len - 3) + " ";
			Roll.set(start + 2, plate1);

			// move=false;
			}
			} else {move=true;}

			if (start < 1) start = 1;
			if(move){
			// lv.smoothScrollBy(SmoothDistMinus, 2500);
			lv.setSelection(start - 1);
			lv.smoothScrollBy(SmoothDist, 2500);
			lv.invalidate();
			return;}
			}
			});
			
			final Button buttonDN = (Button) findViewById(R.id.DN);
			buttonDN.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ int end=lv.getLastVisiblePosition();
			int start = lv.getFirstVisiblePosition();
			//System.out.print(end);
			String original= (String) lv.getItemAtPosition(start + 2);
			boolean move=true;
			int len=original.length();
			String temp[];
			temp=original.split(":");
			//boolean che=;
			if(temp[1].charAt(2)==' ' && temp[1].charAt(1)!=' ')
			{
			char ch = temp[1].charAt(1);
			move=false;
			showtop("Enter 2 Digit Marks.");
			plate1=original.substring(0,len-3)+ch+" ";
			if(Beyond()) { plate1=original.substring(0,len-3)+" ";
			Roll.set(start+2,plate1);
			move=false;
			}
			}
			else{move=true;}


			if(start>strength-1) start=strength-1;

			if(move) {
			for (int i = start; i < end; i++) {
			View view = lv.getChildAt(i - start);
			lv.getAdapter().getView(i, view, lv);
			}

			lv.smoothScrollBy(SmoothDist, 2500);
			lv.setSelection(start + 1);
			}
			}
			});
			///////Duplicate UP and DN near MCQ
			
	   final Button buttonUP1 = (Button) findViewById(R.id.UP1);
			buttonUP1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{
					int start = lv.getFirstVisiblePosition();
					int end = lv.getLastVisiblePosition();
					String original = (String) lv.getItemAtPosition(start + 2);
					boolean move;
					int len = original.length();
					String temp[];
					temp = original.split(":");

					if (temp[1].charAt(1) != ' ' && temp[1].charAt(2) == ' ') {
					/*temp[1]="0"+temp[1].charAt(2);
					plate1=temp[1];
					Roll.set(start +2,plate1);*/
					char ch = temp[1].charAt(1);
					move = false;
					showtop("Enter 2 Digit Marks.");
					plate1 = original.substring(0, len - 3) + ch + " ";
					if (Beyond()) {
					plate1 = original.substring(0, len - 3) + " ";
					Roll.set(start + 2, plate1);

					// move=false;
					}
					} else {move=true;}

					if (start < 1) start = 1;
					if(move){
					// lv.smoothScrollBy(SmoothDistMinus, 2500);
					lv.setSelection(start - 1);
					lv.smoothScrollBy(SmoothDist, 2500);
					lv.invalidate();
					return;}
					}
					});
			
			final Button buttonDN1 = (Button) findViewById(R.id.DN1);
			buttonDN1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{ int end=lv.getLastVisiblePosition();
				int start = lv.getFirstVisiblePosition();
				//System.out.print(end);
				String original= (String) lv.getItemAtPosition(start + 2);
				boolean move=true;
				int len=original.length();
				String temp[];
				temp=original.split(":");
				//boolean che=;
				if(temp[1].charAt(2)==' ' && temp[1].charAt(1)!=' ')
				{
				char ch = temp[1].charAt(1);
				move=false;
				showtop("Enter 2 Digit Marks.");
				plate1=original.substring(0,len-3)+ch+" ";
				if(Beyond()) { plate1=original.substring(0,len-3)+" ";
				Roll.set(start+2,plate1);
				move=false;
				}
				}
				else{move=true;}


				if(start>strength-1) start=strength-1;

				if(move) {
				for (int i = start; i < end; i++) {
				View view = lv.getChildAt(i - start);
				lv.getAdapter().getView(i, view, lv);
				}

				lv.smoothScrollBy(SmoothDist, 2500);
				lv.setSelection(start + 1);
				}
				}
				});
			
			
			
			/////////////////////////////////END  OF  DUPLICATION OF UP DN BUTTONS 
			
			
			final Button buttonGOTO = (Button) findViewById(R.id.GoTo);
			buttonGOTO.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{ 
					JumpRoll();
				}
				});
		  final Button buttonABS = (Button) findViewById(R.id.ABS);
			buttonABS.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{ 
					ShowInfo("AB");
				}
				});
		
			final Button buttonZeroes = (Button) findViewById(R.id.Zeroes);
			buttonZeroes.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{ 
				ShowInfo("00");
				}
				});
		
			final Button buttonPgTot = (Button) findViewById(R.id.PgTot);
			buttonPgTot.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{ 
				ShowPageTotal();
				
				}
				});
			
			
			final Button buttonMerit = (Button) findViewById(R.id.Merit);
			buttonMerit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{ 
				ShowInfo("TP");
				}
				});
			
			  final Button buttonCutABS = (Button) findViewById(R.id.CutABS);
				buttonCutABS.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v)
					{ 
					CutABS();
					}
					});
    	final Button button0 = (Button) findViewById(R.id.button0);
		button0.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ 
				ProcessDigit('0',Roll);
					}
			});
		
		final Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ProcessDigit('1',Roll);
					}
			});
		
		final Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ProcessDigit('2',Roll);
					}
			});
		
		final Button button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ProcessDigit('3',Roll);
					}
			});
		
		final Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ProcessDigit('4',Roll);
					}
			});
		
		final Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ProcessDigit('5',Roll);
					}
			});
        
		final Button button6 = (Button) findViewById(R.id.button6);
		button6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ProcessDigit('6',Roll);
					}
			});
		
		final Button button7 = (Button) findViewById(R.id.button7);
		button7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ProcessDigit('7',Roll);
			}
			});
		
		final Button button8 = (Button) findViewById(R.id.button8);
		button8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ProcessDigit('8',Roll);
					}
			});
		
		final Button button9 = (Button) findViewById(R.id.button9);
		button9.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ProcessDigit('9',Roll);
			}
			});
		
		final Button buttonAB = (Button) findViewById(R.id.buttonAbsent);
		buttonAB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ ProcessDigit('A',Roll);
				
			}
			});

		final Button buttonH = (Button) findViewById(R.id.buttonHundred);
		buttonH.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ProcessDigit('H',Roll);
			}
			});
		final Button buttonBackspace = (Button) findViewById(R.id.buttonBackspace);
		buttonBackspace.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ 
			ProcessDigit('B',Roll);				
			}
			});
		
		
		final Button buttonNew = (Button) findViewById(R.id.buttonNew);
		buttonNew.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ if(modified) WarnBeforeNew();
			else GetNewRoll();
			 }
			});
		
		
		final Button buttonOpen = (Button) findViewById(R.id.buttonOpen);
		buttonOpen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ 
				if(modified) WarnBeforeOpen(); 
				else
				OpenFile();
			}
			});
		final Button buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{  SaveMarkList();
			}
		});
		
		final Button buttonpost = (Button) findViewById(R.id.buttonPost);
		buttonpost.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ ProcessDigit('M',Roll); 
			  ResetChoices();
			  FlipRoutine();
			}
		});
		
		final Button buttonflipset = (Button) findViewById(R.id.FlipSet);
		buttonflipset.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{ FlipRoutine();
			}
		});
		
		
		
		final Button buttonA = (Button) findViewById(R.id.buttonA);
		buttonA.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{showmarks('A');
			}
		});

		final Button buttonB = (Button) findViewById(R.id.buttonB);
		buttonB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
			showmarks('B');
			}
		});
		final Button buttonC = (Button) findViewById(R.id.buttonC);
		buttonC.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{showmarks('C');
			}
		});
		
		final Button buttonD = (Button) findViewById(R.id.buttonD);
		buttonD.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
			showmarks('D');
			}
		});
		final Button buttonDASH = (Button) findViewById(R.id.buttonDASH);
		buttonDASH.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v)
			{
			showmarks('-');
			}
		});
		final Button buttonMCQAB = (Button) findViewById(R.id.buttonMCQAB);
		buttonMCQAB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{  ProcessDigit('A',Roll);
			}
		});

		final Button buttonMCQReset = (Button) findViewById(R.id.MCQReset);
		buttonMCQReset.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{  ResetChoices();
			}
		});
		
		final Button buttonMCQBackspace = (Button) findViewById(R.id.MCQBackspace);
		buttonMCQBackspace.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{  final TextView studopsview = (TextView) findViewById(R.id.STUDOPS);
	        String studstr=studopsview.getText().toString();
	        int len=studstr.length();
			if(len>0)
			{String temp=studstr.substring(0,len-1);
			 studopsview.setText(temp);
			 showmarks('N'); ///
			}
			}
		});
		
		final TextView Scoreview = (TextView) findViewById(R.id.MCQScore);
		Scoreview.setOnClickListener(new View.OnClickListener() 
    	{
    		public void onClick(View v)
    		{GetMCQkey();
    		}
    });
		
		final TextView optionview = (TextView) findViewById(R.id.OpsKey);
		optionview.setOnClickListener(new View.OnClickListener() 
    	{
    		public void onClick(View v)
    		{GetMCQkey();
    		}
    });
		
		final TextView marksview = (TextView) findViewById(R.id.MarKey);
		marksview.setOnClickListener(new View.OnClickListener() 
    	{
    		public void onClick(View v)
    		{GetMCQkey();
    		}
    });
		
		final TextView studopsview = (TextView) findViewById(R.id.STUDOPS);
		studopsview.setOnClickListener(new View.OnClickListener() 
    	{
    		public void onClick(View v)
    		{GetMCQkey();
    		}
    });
		final Button buttonSaveAs = (Button) findViewById(R.id.SaveAs);
		buttonSaveAs.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{  FileNameWithPath="";
				SaveMarkList();
			}
		});
		
		final Button buttonSave2 = (Button) findViewById(R.id.Save2);
		buttonSave2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{  
				SaveMarkList();
			}
		});
		
		final Button buttoncset = (Button) findViewById(R.id.cSET);
		buttoncset.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{  
				SaveCollege();
			}
		});
	
		final Button buttonMCQ = (Button) findViewById(R.id.MCQ);
		buttonMCQ.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{  
				MCQmode=!MCQmode;
				if(MCQmode)
			   {
			   optionview.setVisibility(View.VISIBLE);
			   Scoreview.setVisibility(View.VISIBLE);
			   marksview.setVisibility(View.VISIBLE);
			   studopsview.setVisibility(View.VISIBLE);
			   
			    LinearLayout h2 = (LinearLayout) findViewById(R.id.linearLayoutH2);
	            h2.setVisibility(View.GONE);
	            LinearLayout h3 = (LinearLayout) findViewById(R.id.linearLayoutH3);
	            h3.setVisibility(View.GONE);
	            LinearLayout h4 = (LinearLayout) findViewById(R.id.linearLayoutH4);
	            h4.setVisibility(View.GONE);
	            LinearLayout h5 = (LinearLayout) findViewById(R.id.linearLayoutH5);
	            h5.setVisibility(View.GONE);
			   
	            LinearLayout ll0 = (LinearLayout) findViewById(R.id.linearLayoutG0);
	            ll0.setVisibility(View.VISIBLE);
	            LinearLayout ll1 = (LinearLayout) findViewById(R.id.linearLayoutG1);
	            ll1.setVisibility(View.VISIBLE);
	            LinearLayout ll2 = (LinearLayout) findViewById(R.id.linearLayoutG2);
	            ll2.setVisibility(View.VISIBLE);
	            LinearLayout ll3 = (LinearLayout) findViewById(R.id.linearLayoutT0);
	            ll3.setVisibility(View.VISIBLE);
	            
	          SetNo=1;  
	  	      final TextView optionview = (TextView) findViewById(R.id.OpsKey);
	  		  final TextView marksview = (TextView) findViewById(R.id.MarKey);
	  		  final Button buttonflipset = (Button) findViewById(R.id.FlipSet);
	  		  optionview.setText(Set.get(SetNo));
	  		  marksview.setText(Mrk.get(SetNo));
	  	      String temp=String.format("%02d", SetNo);
	     	  buttonflipset.setText(temp);
	     	  
	     	 /*
	     	 //  Imp For Scroll Chars In TextView

	     	lv.post(new Runnable()
	     	{
	     	    public void run() {

	     	    	String ttt="AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	     	    	int totalCharstoFit= optionview.getPaint().breakText(ttt,  0, ttt.length(), 
	     	    			 true, optionview.getWidth(), null);

	     	    	show(totalCharstoFit);
	     	       }
	     	    });

	     */
	     	  
	    	   }
			   else
			   {
				   optionview.setVisibility(View.GONE);
				   marksview.setVisibility(View.GONE);
				   studopsview.setVisibility(View.GONE);
				   Scoreview.setVisibility(View.GONE);
				  
				   LinearLayout h2 = (LinearLayout) findViewById(R.id.linearLayoutH2);
		            h2.setVisibility(View.VISIBLE);
		            LinearLayout h3 = (LinearLayout) findViewById(R.id.linearLayoutH3);
		            h3.setVisibility(View.VISIBLE);
		            LinearLayout h4 = (LinearLayout) findViewById(R.id.linearLayoutH4);
		            h4.setVisibility(View.VISIBLE);
		            LinearLayout h5 = (LinearLayout) findViewById(R.id.linearLayoutH5);
		            h5.setVisibility(View.VISIBLE);
				   
		            LinearLayout ll0 = (LinearLayout) findViewById(R.id.linearLayoutG0);
		            ll0.setVisibility(View.GONE);
				   LinearLayout ll1 = (LinearLayout) findViewById(R.id.linearLayoutG1);
	               ll1.setVisibility(View.GONE);
	               LinearLayout ll2 = (LinearLayout) findViewById(R.id.linearLayoutG2);
	               ll2.setVisibility(View.GONE);
	               LinearLayout ll3 = (LinearLayout) findViewById(R.id.linearLayoutT0);
		            ll3.setVisibility(View.GONE);
				   }
			}
		});
        final TextView tv = (TextView) findViewById(R.id.Help);
        tv.setOnClickListener(new View.OnClickListener() 
    	{
    		public void onClick(View v)
    		{showhelp();
    		}
    });
    }
 //////////////////////////////////////////////////////////////Non Event Functions
    
    private void showhelp()
    {   
    	 String string = getString(R.string.ht);
    	 WebView wv = new WebView (getBaseContext());
    	 wv.loadData(string, "text/html", "utf-8");
    	 wv.setBackgroundColor(Color.WHITE);
    	 wv.getSettings().setDefaultTextEncodingName("utf-8");
    	
    	 AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
    	 myAlertDialog.setTitle("Help !");
    	 
    	 myAlertDialog.setView(wv);
    	 myAlertDialog.setPositiveButton("Close", new DialogInterface.OnClickListener()
    	 {
    	 public void onClick(DialogInterface arg0, int arg1) {
    	  // do something when the OK button is clicked
    	 }});
    	 myAlertDialog.show();
    }
    private void JumpRoll()
    {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle("Enter Roll");
       	final EditText input = new EditText(this);
    	input.setSingleLine();
    	input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); 
    	
       	alert.setView(input);
    	alert.setPositiveButton("Jump", new DialogInterface.OnClickListener() 
    	{
    	 public void onClick(DialogInterface dialog, int whichButton)
    	 {
    	     String Rollstr = input.getText().toString();
    	     GOTO(Rollstr);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
 		 	 	imm.hideSoftInputFromWindow(input.getWindowToken(),0);
    	  }
    	});

    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
    	{
    	  public void onClick(DialogInterface dialog, int whichButton)
    	  {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
 		 	 	imm.hideSoftInputFromWindow(input.getWindowToken(),0);
    	  }
    	});
    	alert.show();
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
   	 imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    private void ResetChoices()
    {
    final TextView studopsview = (TextView) findViewById(R.id.STUDOPS);
	studopsview.setText(""); MCQmarks=0;
	mcqscore = String.format("%02d ", MCQmarks);
	String ready="Score : " + mcqscore;
	final TextView scoreview = (TextView) findViewById(R.id.MCQScore);
	scoreview.setText(ready);
	final TextView qnview = (TextView) findViewById(R.id.QN);
	ready = String.format("QN %02d",0);
	qnview.setText(ready);
    }
    
    private void showmarks(char ch)
    {   final TextView studopsview = (TextView) findViewById(R.id.STUDOPS);
        String studstr=studopsview.getText().toString();
    	final TextView rightopsview = (TextView) findViewById(R.id.OpsKey);
	    String rightstr=rightopsview.getText().toString();
	    final TextView marksview = (TextView) findViewById(R.id.MarKey);
	    
	    
	    String marksstr=marksview.getText().toString();
		TotQns=rightstr.length();
	    int len=studstr.length();
		if(len>=TotQns) return;   ////suspend entry on last option
		if(ch!='N') studopsview.setText(studstr+ch);  ///if not BACKSPACE
		studstr=studopsview.getText().toString();
		len=studstr.length();
		MCQmarks=0;
		for(int i=0;i<len;i++)
	       if(studstr.charAt(i)==rightstr.charAt(i)) MCQmarks=MCQmarks+marksstr.charAt(i)-'0';
		mcqscore = String.format("%02d ", MCQmarks);
		String ready="Score : " + mcqscore;
		final TextView scoreview = (TextView) findViewById(R.id.MCQScore);
		scoreview.setText(ready);
		final TextView qnview = (TextView) findViewById(R.id.QN);
		ready = String.format("QN %02d", len);
		qnview.setText(ready);
	 }
    
    
    private void GOTO(String str)
    {  
    	String stemp,ttemp,temp[];
    	int i,len;
    	len=str.length();
    	if(len==0) return;
       	int gotoroll=Integer.parseInt(str.replaceAll("[^0-9.]",""));
	    for(i=2;i<strength+2;i++)
	    {   stemp=Roll.get(i);
	        if(!stemp.contains(":")) continue;
           temp=stemp.split(":");
           stemp=temp[0].trim(); ///reuse stemp
           len=stemp.length();
           if(len==0) continue;
           int temproll=Integer.parseInt(temp[0].replaceAll("[^0-9.]",""));
           if(temproll==gotoroll)
        	   { lv.setSelection(i-2);   
        	     lv.smoothScrollBy(SmoothDist, 2500);
        	        lv.invalidate(); return;
        	    }
	    }
	    showtop("Roll Not Found !");
    }
    
    private void SendList()
    { 
    	String temp=Div.trim()+"-"+Exam.trim()+"-"+Subject.trim()+"-"+Clas.trim()+"-"+Examiner.trim();
    	Intent sendIntent = new Intent(Intent.ACTION_SEND);
    	sendIntent.putExtra(Intent.EXTRA_SUBJECT,temp);
    		sendIntent.putExtra(Intent.EXTRA_TEXT, "Sending Marklist...");
    		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + FileNameWithPath));
    		
    		String E1=Email1.trim();
    		String E2=Email2.trim();
    		if(E1.length()==0 && E2.length()==0) {show("Specify Email(s) by [Set] button"); return; }
    	    String[] emailList={"",""};
    		if(E1.length()!=0) emailList[0]=Email1; 
    		if(E2.length()!=0) emailList[1]=Email2; 
    		sendIntent.putExtra(Intent.EXTRA_EMAIL,emailList);
    		sendIntent.setType("text/plain");
    		startActivity(Intent.createChooser(sendIntent, "Send Mail"));
    	
    }
    
    
    
    private void JumpBlank()
    {   int counter=0;
    	String stemp,ttemp,temp[];
    	int i,len;
       	((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
       boolean jumped=false;
   	  
	    for(i=2;i<strength+2;i++)
	    {   stemp=Roll.get(i);
           //if(stemp.contains("AB")) continue;
           temp=stemp.split(":");
           stemp=temp[1].trim(); ///reuse stemp
           len=stemp.length();
           if(len==0)
        	   {
        	     if(jumped) counter++;
        	     else  { lv.setSelection(i-2); lv.smoothScrollBy(SmoothDist, 2500);
        	              lv.invalidate(); jumped=true;
        	             counter++;
        	            }
        	   }
	    }  
	    stemp=String.valueOf(counter)+" Blanks";	
        showtop(stemp);    	
    }
    
    private void OpenList(String fylenamewithpath)
    { int i;
      String temp2;
    	try
    	{
    	
    	File myFile = new File(fylenamewithpath);
		FileInputStream fIn = new FileInputStream(myFile);
		BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
		String aDataRow = "";
		String[] temp;
		
		aDataRow=myReader.readLine(); /// blank line separator
		CollegeName1 = myReader.readLine();
		CollegeName2 = myReader.readLine();
		CollegeName3 = myReader.readLine();
		aDataRow=myReader.readLine(); /// blank line separator
		
		aDataRow=myReader.readLine(); /// version ========
		aDataRow=myReader.readLine(); /// blank line separator
		
		aDataRow=myReader.readLine(); /// Number of Sets
		temp=aDataRow.split(":");
	
		TotSets = Integer.parseInt(temp[1].replaceAll("[^0-9.]",""));
		
		aDataRow=myReader.readLine(); /// blank line separator
		
		//////Load All Sets Now 
		for(i=1;i<=TotSets;i++)
		{
		aDataRow=myReader.readLine(); /// keystringe
		temp=aDataRow.split(":");
		temp2=temp[1].trim();
		Set.set(i,temp2);
		
		
		aDataRow=myReader.readLine(); /// mrkstring
		temp=aDataRow.split(":");
		temp2=temp[1].trim();
		Mrk.set(i,temp2);
		
		aDataRow=myReader.readLine(); /// blank line separator
		
		}  ///end of for loop for set
		
		
		aDataRow=myReader.readLine(); /// version ========
		aDataRow=myReader.readLine(); /// blank line separator
		
		aDataRow=myReader.readLine(); /// froll
		temp=aDataRow.split(":");
		froll = Integer.parseInt(temp[1].replaceAll("[^0-9.]",""));
		
		aDataRow=myReader.readLine(); /// lroll
		temp=aDataRow.split(":");
		
		lroll= Integer.parseInt(temp[1].replaceAll("[^0-9.]",""));
		
		aDataRow=myReader.readLine(); /// Examiner
		temp=aDataRow.split(":");
		Examiner=temp[1].trim();
			
		aDataRow=myReader.readLine(); /// Class
		temp=aDataRow.split(":");
		Clas=temp[1].trim();
		
		aDataRow=myReader.readLine(); /// Div
		temp=aDataRow.split(":");
		Div=temp[1].trim();
		
		aDataRow=myReader.readLine(); /// Stream
		temp=aDataRow.split(":");
		Stream=temp[1].trim();
		
		aDataRow=myReader.readLine(); /// Subject
		temp=aDataRow.split(":");
		Subject=temp[1].trim();
		
		aDataRow=myReader.readLine(); /// Examiner
		temp=aDataRow.split(":");
		Exam=temp[1].trim();
		
		aDataRow=myReader.readLine(); /// Max Marks
		temp=aDataRow.split(":");
		sMax=temp[1].trim();
		
		aDataRow=myReader.readLine(); /// Date
		temp=aDataRow.split(":");
		Date=temp[1].trim();
		
		aDataRow=myReader.readLine(); /// blank line seperator
		aDataRow=myReader.readLine(); /// version ========
		aDataRow=myReader.readLine(); /// blank line seperator
		
		aDataRow=myReader.readLine(); /// /roll marks heading
		aDataRow=myReader.readLine(); /// blank line seperator
		
		Roll.removeAll(Roll);
		
		i=0;
		while ((aDataRow = myReader.readLine()) != null)
				 
	   	   	 { Roll.add(aDataRow);
	   	   	  i++; 	
	   	   	 }
		  Roll.add("   ");
          Roll.add("   ");
          Roll.add("   ");
          Roll.add("   ");
          Roll.add("   ");
		
		strength=i-2;
		myReader.close();
		FileNameWithPath=fylenamewithpath;
 		int start=fylenamewithpath.lastIndexOf("/");
 		
 		String tempfname=fylenamewithpath.substring(start+1);
 		 		
 		final TextView myTitleText = (TextView) findViewById(R.id.ttext);
 		 if (myTitleText != null)
 		     myTitleText.setText(tempfname);
 		     showtop(tempfname);
 		     showtop("Loaded From SD Card");
 	    	}
		catch (Exception e) 
		{
		Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
		}
 
    	((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
    	lv.setSelection(0);
    	lv.smoothScrollBy(SmoothDist, 2500);
        lv.invalidate();
    	modified=false;
    	SetNo=1;
    }
 
private void SaveMarkList()
{  if(FileNameWithPath!="") { SaveList(); if (OpenNow) OpenFile();
                              if(NewNow) GetNewRoll();return;
                            }

 if(Div.length()==0 || Clas.length()==0 || Exam.length()==0 || Subject.length()==0 || Examiner.length()==0 || Stream.length()==0)
    {show("Cannot Save. Fill All Fields In Header");return;}
	AlertDialog.Builder alert = new AlertDialog.Builder(this);
	alert.setTitle("Enter File Name To Save Mark List");
   	final EditText input = new EditText(this);
	input.setSingleLine();
	input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); 
	if(Clas!=null || Div!=null || Exam!=null || Subject!=null )
	 {String temp=Div.trim()+"-"+Exam.trim()+"-"+Subject.trim()+"-"+Clas.trim()+"-"+Examiner.trim();
	  input.setText(temp);
	 }
   	alert.setView(input);
	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int whichButton) {
	 String fnem = input.getText().toString();
	 fnem+=".mrk";
	 title=fnem;
	 FileNameWithPath="/sdcard/";
	 FileNameWithPath+=fnem;
	 
	
	 InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	 imm.hideSoftInputFromWindow(input.getWindowToken(),0);
	   	if(fnem.length()!=0)    
	           { showtop(fnem);
	             File file = new File(FileNameWithPath);
	             if(file.exists()){showtop("File already exists"); FileNameWithPath="";
	                   end=false;OpenNow=false;NewNow=false;
	                  return; 
	                  } 
 //setTitle(title);
 final TextView myTitleText = (TextView) findViewById(R.id.ttext);
 if (myTitleText != null)
     myTitleText.setText(title);
 
 
 SaveList();
 if (OpenNow)OpenFile();
 if (NewNow) GetNewRoll();
	modified=false;
	
	}
	   	else 
	   	showtop("Blank Name. Not Saved");
	  }
	});

	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	  public void onClick(DialogInterface dialog, int whichButton) {
		  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		 	imm.hideSoftInputFromWindow(input.getWindowToken(),0);
		if (OpenNow)OpenFile();
		if(NewNow) GetNewRoll();
	    return;
	  }
	});
	alert.show();
	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
}


private void SaveCollege()
{		
    final AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setTitle("Preferences");
     
    final LinearLayout layout = new LinearLayout(this);
    
    layout.setOrientation(LinearLayout.VERTICAL);
    
    TextView tv1 = new TextView(this);
    tv1.setText(" School/College Name");
    //tv.setPadding(40, 40, 40, 40);
    //tv.setGravity(Gravity.CENTER);
    //tv.setTextSize(20);
    
    
    
    
    final EditText input1 = new EditText(this);
    input1.setSingleLine();
	input1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
	//input1.setHint("SIWS College, Wadala Rd.");
	input1.setText(CollegeName1);
    
	final EditText input2 = new EditText(this);
    input2.setSingleLine();
	input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//	input2.setHint("Mumbai - 400031, India.");
	input2.setText(CollegeName2);
	
	
	TextView tv2 = new TextView(this);
    tv2.setText(" Exam Email(s)");
	final EditText input3 = new EditText(this);
    input3.setSingleLine();
	input3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
	input3.setText(Email1);
	
	final EditText input4 = new EditText(this);
    input4.setSingleLine();
	input4.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
	input4.setText(Email2);
	
	
	layout.addView(tv1);
    layout.addView(input1);
    layout.addView(input2);
    layout.addView(tv2);
    layout.addView(input3);
    layout.addView(input4);
    

    alert.setView(layout);

    
    alert.setPositiveButton("Set", new DialogInterface.OnClickListener()
	{
	public void onClick(DialogInterface dialog, int whichButton) {
	 CollegeName1 = input1.getText().toString();
	 CollegeName2 = input2.getText().toString();
	 Email1 = input3.getText().toString();
	 Email2 = input4.getText().toString();
	 SharedPreferences settings = getSharedPreferences("COLL", 0);
     SharedPreferences.Editor editor = settings.edit();
     editor.putString("key1", CollegeName1);
     editor.putString("key2", CollegeName2);
     editor.putString("key3", Email1);
     editor.putString("key4", Email2);
     
     editor.commit();
	 InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	 imm.hideSoftInputFromWindow(input1.getWindowToken(),0);
 	  }
	});

	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
	{
	  public void onClick(DialogInterface dialog, int whichButton) {
		  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		 	imm.hideSoftInputFromWindow(input1.getWindowToken(),0);
	    return;
	  }
	});
    alert.show();
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
}



private void SaveList()
{	   int i;
	   modified=false;
	   String tmpStr;
       String txtData = "\n";
       txtData+=CollegeName1;txtData+='\n';
       txtData+=CollegeName2; txtData+='\n'; // collegename2
       txtData+=CollegeName3;txtData+="\n\n"; /// collegename3
       txtData+="======== MarkList Ver 177 =======\n\n";
       
       
       tmpStr=Integer.toString(TotSets);
       txtData+="Total Sets : ";txtData+=tmpStr; txtData+="\n\n";
       
       
       for(i=1;i<=TotSets;i++)
       {tmpStr=String.format("Set %02d : %s\n",i,Set.get(i).toString());
        txtData+=tmpStr;
        tmpStr=String.format("Set %02d : %s\n\n",i,Mrk.get(i).toString());
        txtData+=tmpStr;
       }
       txtData+="======== MarkList Ver 177 =======\n\n";
       tmpStr=Integer.toString(froll);
       txtData+="First Roll  : ";txtData+=tmpStr; txtData+='\n';
       tmpStr=Integer.toString(lroll);
       txtData+="Last  Roll  : ";txtData+=tmpStr; txtData+='\n';
     
       txtData+="Examiner    : ";txtData+=Examiner; txtData+='\n';
       txtData+="Class       : ";txtData+=Clas; txtData+='\n';
       txtData+="Div         : ";txtData+=Div; txtData+='\n';
       txtData+="Stream      : ";txtData+=Stream; txtData+='\n';
       txtData+="Subject     : ";txtData+=Subject; txtData+='\n';
       txtData+="Examination : ";txtData+=Exam; txtData+='\n';
       txtData+="Max Marks   : ";txtData+=sMax; txtData+='\n';
       txtData+="Date        : ";txtData+=Date; txtData+="\n\n";
       txtData+="======== MarkList Ver 177 =======\n\n";
   	   txtData+=" Roll   Marks\n\n";
     
   	   for(i=0;i<strength+2;i++) 
    	   {txtData+=Roll.get(i); txtData+='\n';}
     	
    try {
		File myFile = new File(FileNameWithPath);
		myFile.createNewFile();
		FileOutputStream fOut = new FileOutputStream(myFile);
		OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
		myOutWriter.append(txtData);
		myOutWriter.close();
		fOut.close();
		showtop("Saved on SD card");
				
	} catch (Exception e) {
		Toast.makeText(getBaseContext(), e.getMessage(),
				Toast.LENGTH_SHORT).show();
	}
    if(end) finish();
}

private void GetHeaderDlg()
 	    {
 		 final Dialog myDialog; 
 		 myDialog =  new Dialog(this);
 		 //myDialog.requestWindowFeature(myDialog.getWindow().FEATURE_NO_TITLE);
 		myDialog.setTitle("MarkList Header");
 		 myDialog.setContentView(R.layout.header_dlg); 
 		 myDialog.setCancelable(true); 
 		 myDialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;
 			 	 	
 		final EditText FMax = (EditText) myDialog.findViewById(R.id.EB_MAX);
 		FMax.setText(sMax);
		
 		final EditText FClas = (EditText) myDialog.findViewById(R.id.EB_CLASS);
		FClas.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {            

		    }
		        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		                    int arg3) {             
		    }
		    public void afterTextChanged(Editable arg0) {
		          String s=arg0.toString();
		      if(!s.equals(s.toUpperCase()))
		      {
		         s=s.toUpperCase();
		         FClas.setText(s);
		         FClas.setSelection(FClas.getText().length());
		      }
		    }
		});     
		FClas.setText(Clas); 

		final EditText FSubj = (EditText) myDialog.findViewById(R.id.EB_SUBJECT);
		FSubj.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {            

		    }
		        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		                    int arg3) {             
		    }
		    public void afterTextChanged(Editable arg0) {
		          String s=arg0.toString();
		      if(!s.equals(s.toUpperCase()))
		      {
		         s=s.toUpperCase();
		         FSubj.setText(s);
		         FSubj.setSelection(FSubj.getText().length());
		      }
		    }
		});     
		FSubj.setText(Subject); 

		final EditText FExam = (EditText) myDialog.findViewById(R.id.EB_EXAMINATION);
		FExam.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {            

		    }
		        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		                    int arg3) {             
		    }
		    public void afterTextChanged(Editable arg0) {
		          String s=arg0.toString();
		      if(!s.equals(s.toUpperCase()))
		      {
		         s=s.toUpperCase();
		         FExam.setText(s);
		         FExam.setSelection(FExam.getText().length());
		      }
		    }
		});     
		FExam.setText(Exam);
		
		final EditText FExaminer = (EditText) myDialog.findViewById(R.id.EB_EXAMINER);

		FExaminer.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {            

		    }
		        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		                    int arg3) {             
		    }
		    public void afterTextChanged(Editable arg0) {
		          String s=arg0.toString();
		      if(!s.equals(s.toUpperCase()))
		      {
		         s=s.toUpperCase();
		         FExaminer.setText(s);
		         FExaminer.setSelection(FExaminer.getText().length());
		      }
		    }
		});     
		FExaminer.setText(Examiner);

		final EditText FDiv = (EditText) myDialog.findViewById(R.id.EB_DIV);
		FDiv.addTextChangedListener(new TextWatcher() {
		 public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {            

		    }
		        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		                    int arg3) {             
		    }
		    public void afterTextChanged(Editable arg0) {
		          String s=arg0.toString();
		      if(!s.equals(s.toUpperCase()))
		      {
		         s=s.toUpperCase();
		         FDiv.setText(s);
		         FDiv.setSelection(FDiv.getText().length());
		      }
		    }
		});     
		FDiv.setText(Div);
		
		final EditText FStrim = (EditText) myDialog.findViewById(R.id.EB_STRIM);
		FStrim.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {            

		    }
		        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		                    int arg3) {             
		    }
		    public void afterTextChanged(Editable arg0) {
		          String s=arg0.toString();
		      if(!s.equals(s.toUpperCase()))
		      {
		         s=s.toUpperCase();
		         FStrim.setText(s);
		         FStrim.setSelection(FStrim.getText().length());
		      }
		    }
		});     
		FStrim.setText(Stream);
		
		final EditText FDate = (EditText) myDialog.findViewById(R.id.EB_DET);
		FDate.setText(Date);
		
		Button buttoncancel = (Button) myDialog.findViewById(R.id.BCancel); 
		buttoncancel.setOnClickListener(new OnClickListener() { 
	 
		    public void onClick(View v)
		    {myDialog.dismiss();
		     InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			 imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		    } 
		    });
	Button buttonok = (Button) myDialog.findViewById(R.id.BCreate); 
	buttonok.setOnClickListener(new OnClickListener() { 
	    public void onClick(View v) { 
	    tempstr=FMax.getText().toString();sMax=tempstr;
 	    tempstr=FClas.getText().toString();  Clas=tempstr;
	    tempstr=FSubj.getText().toString();  Subject=tempstr;
	    tempstr=FExam.getText().toString();  Exam=tempstr;
	    tempstr=FExaminer.getText().toString(); Examiner=tempstr;
	    tempstr=FDiv.getText().toString();  Div=tempstr;
	    tempstr=FStrim.getText().toString();  Stream=tempstr;
	    tempstr=FDate.getText().toString();  Date=tempstr;
	    
 	    modified=true;		
 	    
 	  	    
	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
 	imm.hideSoftInputFromWindow(FClas.getWindowToken(),0);
	myDialog.dismiss();
		 } 
	}); 
	
 	 myDialog.show();
 	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
 	imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);  	       	        
 	    
 }


private void GetMCQkey()
{ResetChoices();
SetNo=1;
final Dialog myDialog; 
myDialog =  new Dialog(this);
// myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
myDialog.setTitle("Enter Ans Key and Marks");

myDialog.setContentView(R.layout.set_dlg); 
myDialog.setCancelable(true); 
myDialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;

final TextView setdlgAsview = (TextView) myDialog.findViewById(R.id.SetDlgAKey);
final TextView setdlgMview = (TextView) myDialog.findViewById(R.id.SetDlgMKey);
final TextView setnview = (TextView) myDialog.findViewById(R.id.setnoview);

setdlgAsview.setText(Set.get(SetNo));
setdlgMview.setText(Mrk.get(SetNo));



Button buttonsetdlgA = (Button) myDialog.findViewById(R.id.SetDlgA); 
buttonsetdlgA.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    {	
	    	String studstr=setdlgAsview.getText().toString(); 
	        setdlgAsview.setText(studstr+'A');	        
	    } 
	    });

Button buttonsetdlgB = (Button) myDialog.findViewById(R.id.SetDlgB); 
buttonsetdlgB.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    {	
	       String studstr=setdlgAsview.getText().toString();
	       setdlgAsview.setText(studstr+'B');
	    	
	    } 
	    });

Button buttonsetdlgC = (Button) myDialog.findViewById(R.id.SetDlgC); 
buttonsetdlgC.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    {	
	       String studstr=setdlgAsview.getText().toString();
	       setdlgAsview.setText(studstr+'C');
	    	
	    } 
	    });

Button buttonsetdlgD = (Button) myDialog.findViewById(R.id.SetDlgD); 
buttonsetdlgD.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    {	
	       String studstr=setdlgAsview.getText().toString();
	       setdlgAsview.setText(studstr+'D');
	    	
	    } 
	    });

Button buttonsetdlgLX = (Button) myDialog.findViewById(R.id.SetDlgLX); 
buttonsetdlgLX.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    {  String rightstr=setdlgAsview.getText().toString();
	       int len=rightstr.length();
	       if(len==0 || len==1) setdlgAsview.setText("");
	       else   
	       setdlgAsview.setText(rightstr.substring(0,len-1));
	    	
	    } 
	    });


Button buttonsetdlg1 = (Button) myDialog.findViewById(R.id.SetDlg1); 
buttonsetdlg1.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    {	
	       String rightstr=setdlgMview.getText().toString();
	       setdlgMview.setText(rightstr+'1');	
	    
	    } 
	    });

Button buttonsetdlg2 = (Button) myDialog.findViewById(R.id.SetDlg2); 
buttonsetdlg2.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    {	
	       String rightstr=setdlgMview.getText().toString();
	       setdlgMview.setText(rightstr+'2');
	    	
	    } 
	    });

Button buttonsetdlg3 = (Button) myDialog.findViewById(R.id.SetDlg3); 
buttonsetdlg3.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    {	
	       String rightstr=setdlgMview.getText().toString();
	       setdlgMview.setText(rightstr+'3');
	    	
	    } 
	    });

Button buttonsetdlg4 = (Button) myDialog.findViewById(R.id.SetDlg4); 
buttonsetdlg4.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    {	
	       String rightstr=setdlgMview.getText().toString();
	       setdlgMview.setText(rightstr+'4');
	    	
	    } 
	    });

Button buttonsetdlgNX = (Button) myDialog.findViewById(R.id.SetDlgNX); 
buttonsetdlgNX.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    {  String rightstr=setdlgMview.getText().toString();
	       int len=rightstr.length();
	       if(len==0 || len==1) setdlgMview.setText("");
	       else   
	       setdlgMview.setText(rightstr.substring(0,len-1));
	    } 
	    });

Button buttonnext = (Button) myDialog.findViewById(R.id.Next); 
buttonnext.setOnClickListener(new OnClickListener() { 
   public void onClick(View v) 
   {  String ansstr=setdlgAsview.getText().toString();
      String mrkstr=setdlgMview.getText().toString();
      int len1=ansstr.length();
      int len2=mrkstr.length();
      if(len1==0) {showtop("No More Sets"); return;}
      if(len1!=len2) {showtop("Option-Marks Mismatch"); return;}
      Set.set(SetNo,ansstr);
      Mrk.set(SetNo,mrkstr);
      SetNo++;
	  String SetNoStr = String.format("%02d ",SetNo);
	  String ready="Set No : " + SetNoStr;
	  setnview.setText(ready);
	  setdlgAsview.setText(Set.get(SetNo));
	  setdlgMview.setText(Mrk.get(SetNo));
   } 
   }); 

Button buttonprev = (Button) myDialog.findViewById(R.id.Prev); 
buttonprev.setOnClickListener(new OnClickListener() { 
   public void onClick(View v) 
   {   if(SetNo<2) return;
       String ansstr=setdlgAsview.getText().toString();
       String mrkstr=setdlgMview.getText().toString();
       Set.set(SetNo,ansstr);
       Mrk.set(SetNo,mrkstr);
	   SetNo--;
	  String SetNoStr = String.format("%02d ",SetNo);
	  String ready="Set No : " + SetNoStr;
	  setnview.setText(ready);
	  setdlgAsview.setText(Set.get(SetNo));
	  setdlgMview.setText(Mrk.get(SetNo));
   } 
   }); 

Button buttonfinish = (Button) myDialog.findViewById(R.id.Finish); 
buttonfinish.setOnClickListener(new OnClickListener()
        { 
	    public void onClick(View v)
	    { String ansstr=setdlgAsview.getText().toString();
	      String mrkstr=setdlgMview.getText().toString();
	      int len1=ansstr.length();
	      int len2=mrkstr.length();
	      if(len1!=len2) {showtop("Option-Marks Mismatch"); return;}
	      
	      Set.set(SetNo,ansstr);
	      Mrk.set(SetNo,mrkstr);
	      
	      for(TotSets=2; TotSets<100;TotSets++) 
	    	  { if(Set.get(TotSets).length()==0) break;
	    	    if(Set.get(TotSets).length()!=Mrk.get(TotSets).length()) break;
	    	  }
	      TotSets--;
	      SetNo=1;
	      final TextView optionview = (TextView) findViewById(R.id.OpsKey);
		  final TextView marksview = (TextView) findViewById(R.id.MarKey);
		  optionview.setText(Set.get(SetNo));
		  marksview.setText(Mrk.get(SetNo));
	      myDialog.dismiss();
	     } 
	  });
myDialog.show();

/*
 //  Imp For Scroll Chars In TextView

lv.post(new Runnable()
{
    public void run() {

    	String ttt="AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    	int totalCharstoFit= setdlgAsview.getPaint().breakText(ttt,  0, ttt.length(), 
    			 true, setdlgAsview.getWidth(), null);

    	show(totalCharstoFit);
       }
    });

*/


}


private void GetNewRoll()
{	 NewNow=false; modified=false;	
	
    final AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setTitle("Create New List");
    
    
    final LinearLayout layout = new LinearLayout(this);
    
    layout.setOrientation(LinearLayout.VERTICAL);
    
    final EditText input1 = new EditText(this);
    input1.setSingleLine();
	input1.setInputType(InputType.TYPE_CLASS_PHONE);
	input1.setHint("First Roll Number...");
	
    final EditText input2 = new EditText(this);
    input2.setSingleLine();
	input2.setInputType(InputType.TYPE_CLASS_PHONE);
	input2.setHint("Last Roll Number...");
	
    layout.addView(input1);
    layout.addView(input2);

    alert.setView(layout);

    
    alert.setPositiveButton("Create", new DialogInterface.OnClickListener()
	{
	public void onClick(DialogInterface dialog, int whichButton) 
	{String ins1=input1.getText().toString();
	String ins2=input2.getText().toString();
	if(ins1.length()==0 || ins2.length()==0)
	{   showtop("Invalid Roll");
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	 	imm.hideSoftInputFromWindow(input1.getWindowToken(),0);
    return;
	}
	 in1 = new Integer(input1.getText().toString());
	 in2 = new Integer(input2.getText().toString());
	 
	   froll=in1;lroll=in2;
			strength=lroll-froll+1;
			 	if(in1>in2 || in2-in1+1>10000 || in1==0 || in2==0 || strength<5)
			 	{   showtop("Invalid Roll");
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			 	imm.hideSoftInputFromWindow(input1.getWindowToken(),0);
		         return;
			    }

			 	Roll.removeAll(Roll);
		        Roll.add("   ");
		        Roll.add("   ");
		        for(int i=0;i<strength;i++)
			   	 {  
		        	Roll.add(Integer.toString(froll+i)+ " :    ");
		    	   }
		        Roll.add("   ");
	            Roll.add("   ");
	            Roll.add("   ");
	            Roll.add("   ");
	            Roll.add("   ");
		        
			    FileNameWithPath=""; ///fresh file created
			 
			 	final TextView myTitleText = (TextView) findViewById(R.id.ttext);
			 	myTitleText.setText("MarkList 2.6 - Untitled");
			 	
			 	
			 	
			 	modified=false;
				 		((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
			    	lv.setSelection(0);
			    	lv.smoothScrollBy(SmoothDist, 2500);
			    	lv.invalidate();
	 				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	 		 	 	imm.hideSoftInputFromWindow(input1.getWindowToken(),0);
	 		 	 	
	 		 	 	
	//	!!! variable reset to force header update from teacher
	sMax="00";Examiner="";Exam="";Subject="";Div="";Clas="";FileNameWithPath="";Stream="";
	//  !!! Set Current Date if new file created after old file   
	Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
      Date = df.format(c.getTime());

	   // while(Examiner.length()==0)
		GetHeaderDlg();
		//if(Examiner.length()==0) { showtop("blank"); goto(HD);}
 	 
 	  }
	});

	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
	{
	  public void onClick(DialogInterface dialog, int whichButton)
	  {
		  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		 	imm.hideSoftInputFromWindow(input1.getWindowToken(),0);
	    return;
	  }
	});
    alert.show();
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
}

}
