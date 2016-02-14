
// MRKView.cpp : implementation of the CMRKView class
//

#include "stdafx.h"
#include "MRK.h"

#include "MRKDoc.h"
#include "MRKView.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

CStringArray Flist;
CString installpath;
CString msg= "Printing Marklist ...."; 
int tlx=100,tly=-150,width=65,height=25;
int index;
int totalroll;

CFont fon;
CFont* oldfont;
CFont foot;
CFont LargeFont;

CString CollegeName1;
CString CollegeName2;
CString CollegeName3;
BOOL bFirstDraw=TRUE;
int TotalRecords=150;
int PageTotal=0;
int TotalSets;
CString FRoll;
CString LRoll;
CString Examiner;
CString Clas;
CString Division;
CString Stream;
CString Subject;
CString Examination;
CString MaxMark;
CString Date;

CStringArray arr; 
CStringArray Roll;
CStringArray Mark;

//int m_pagetotal,totalrecords=120;
CString keystring,mrkstring;

CString curfilepath;
CString Left,Right;
CString templine;




/////////////////////////////////////////////////////////////////////////////
// CMRKView

IMPLEMENT_DYNCREATE(CMRKView, CView)

BEGIN_MESSAGE_MAP(CMRKView, CView)
	//{{AFX_MSG_MAP(CMRKView)
	ON_WM_TIMER()
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CMRKView construction/destruction



void showno(int no)
{CString temp;
 temp.Format("%d",no);
 AfxMessageBox(temp);
}

CMenu *pMenu ;


CMRKView::CMRKView()
{
	// TODO: add construction code here

}

CMRKView::~CMRKView()
{
}

BOOL CMRKView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

     


	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CMRKView drawing

void CMRKView::OnDraw(CDC* pDC)
{
	CMRKDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);



	CFrameWnd* pfr=GetParentFrame();
    pfr->SetWindowText("Quick MarkList Printer");


	// TODO: add draw code for native data here
	CFont * oldf;

   fon.CreateFont(15,6,0,0,FW_NORMAL,0,0,0,ANSI_CHARSET,OUT_DEFAULT_PRECIS,
          CLIP_DEFAULT_PRECIS,DEFAULT_QUALITY,DEFAULT_PITCH | FF_SWISS,"Times New Roman");
    // oldf=pDC->SelectObject(&fon);

   LargeFont.CreateFont(30,10,0,0,FW_NORMAL,0,0,0,ANSI_CHARSET,OUT_DEFAULT_PRECIS,
          CLIP_DEFAULT_PRECIS,DEFAULT_QUALITY,DEFAULT_PITCH | FF_SWISS,"Times New Roman");



		extern CString CMDPath;
    if(!CMDPath.IsEmpty())
	 { int len=CMDPath.GetLength();
	   curfilepath=CMDPath.Mid(1,len-2);
     // AfxMessageBox(curfilepath);
	   //Flist.Add(curfilepath);
	   
       if(bFirstDraw) { OnDeskJet2();bFirstDraw=FALSE;}
	}
	else
	{
	
		if(GetModuleFileName(AfxGetInstanceHandle(),installpath.GetBuffer(MAX_PATH),MAX_PATH))
         {installpath.ReleaseBuffer();
          int iBackslash=installpath.ReverseFind('\\');
   
		 if(iBackslash!=-1)
		    { installpath=installpath.Left(iBackslash);
		      LoadAllFiles();
              
			  if(Flist.GetSize()==0)  msg="No Files Found In MRK.Exe Folder !";

			 
               if(Flist.GetSize()!=0) 
			   { 
				if(bFirstDraw)   { OnDeskJet(); bFirstDraw=FALSE;}
			   }

		/*	  for(int i=0;i<TotalMrkFiles;i++)
			  {CString tempath=installpath; tempath+="\\";
               tempath+=Flist[i];
			   ReadFromDisk(tempath);
			   OnDeskJet();
               AfxMessageBox("Next");
			  }
          
			*/
			  
				//  showno(TotalMrkFiles);
		     
		     }
	       else
		  {	AfxMessageBox("Error in Folder Path Detection");		
		  }
       	
	}

		
    
	}

    RECT rc;
	GetClientRect(&rc);
    oldf=pDC->SelectObject(&LargeFont);
    pDC->DrawText(msg, msg.GetLength(),&rc, DT_CENTER | DT_VCENTER | DT_SINGLELINE );
	pDC->SelectObject(oldf);

  SetTimer(1,6000,NULL);
    

}



void CMRKView::LoadAllFiles()
{   Flist.RemoveAll();
	WIN32_FIND_DATA FindFileData;
	HANDLE hFind;
	CString temp=installpath;
	temp+="\\*.mrk";
	//get the first file	
	hFind = FindFirstFile(temp, &FindFileData);
	//while we still have files
	if (hFind != INVALID_HANDLE_VALUE) 
	{
		do
		{
		//add file to list
		Flist.Add(FindFileData.cFileName);
		//add file to list
		
		}
		while( FindNextFile(hFind, &FindFileData));
	}	
	FindClose(hFind);
}





void CMRKView::ReadFromDisk(CString fylepath)
{

 int i;
 CStdioFile fff;
  
 fff.Open(fylepath,CFile::modeRead);
 CString tempstr;

while(fff.ReadString(tempstr))  arr.Add(tempstr);
fff.Close();

CollegeName1=arr[1];
CollegeName2=arr[2];
CollegeName3=arr[3];

templine=arr[7];split(); TotalSets=atoi(Right);
templine=arr[9]; split(); keystring=Right;
templine=arr[10]; split(); mrkstring=Right;

int HS=11+3*TotalSets; ///HEADER START INDEX

templine=arr[HS]; split(); FRoll=Right;
templine=arr[HS+1]; split(); LRoll=Right;
templine=arr[HS+2]; split(); Examiner=Right;
templine=arr[HS+3]; split(); Clas=Right;
templine=arr[HS+4]; split(); Division=Right;
templine=arr[HS+5]; split(); Stream=Right;
templine=arr[HS+6]; split(); Subject=Right;
templine=arr[HS+7]; split(); Examination=Right;
templine=arr[HS+8]; split(); MaxMark=Right;
templine=arr[HS+9]; split(); Date=Right;


int LineCount=arr.GetSize();
for(i=HS+17;i<LineCount;i++)
{ templine=arr[i];split();Roll.Add(Left);Mark.Add(Right);}
 

TotalRecords=Roll.GetSize();

}

int CMRKView::split()
{
	 int cutindex;
     if(templine.IsEmpty()) return -1;
	  cutindex=templine.Find(':');
      if(cutindex==-1) return -1;
      Left=templine.Left(cutindex);
	  Right=templine.Mid(cutindex+1);
	  Left.TrimLeft();Left.TrimRight();
	  Right.TrimLeft;Right.TrimRight();
	  return 1;
}

/////////////////////////////////////////////////////////////////////////////
// CMRKView printing

BOOL CMRKView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CMRKView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CMRKView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CMRKView diagnostics

#ifdef _DEBUG
void CMRKView::AssertValid() const
{
	CView::AssertValid();
}

void CMRKView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CMRKDoc* CMRKView::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CMRKDoc)));
	return (CMRKDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CMRKView message handlers


void CMRKView::CentreText(int y,CString str,CDC* pDC)
{CSize tt;
 tt=pDC->GetTextExtent(str);
 int xx=100+(650-tt.cx)/2;
 pDC->TextOut(xx,y,str);
}




void CMRKView::PrintHeader(CDC* pDC)
{  int px=100,py=-25,linespacing=-19;

   CString temp,temp1,temp2;
   
   // Adjust left corner heading
   CSize rightspot,tt;int xx;
   temp1.Format("Examiner : %s",Examiner.Left(8));
   tt=pDC->GetTextExtent(temp1);
   temp2.Format("Date : %s",Date);
   rightspot=pDC->GetTextExtent(temp2);
   if(rightspot.cx<tt.cx) xx=tt.cx; else xx=rightspot.cx;
   //// right spot calculation complete

   CentreText(py,CollegeName1,pDC);
   py=py+linespacing;
   CentreText(py,CollegeName2,pDC);
   //py=py+linespacing;
   //CentreText(py,CollegeName3,pDC); CollegeName3 Unused Reserved
   py=py+linespacing;
   temp.Format("Class & Div : %s-%s",Clas,Division);
   pDC->TextOut(px,py,temp);
   temp.Format("Subject : %s",Subject);
   pDC->TextOut(px+260,py,temp);
   pDC->TextOut(750-xx,py,temp1);  ///Corner No 1
   
   
   py=py+linespacing;
   temp.Format("Examination : %s",Examination);
   pDC->TextOut(px,py,temp);
   temp.Format("Total Marks : %s",MaxMark);
   pDC->TextOut(px+260,py,temp);
   pDC->TextOut(750-xx,py,temp2); ///Corner No 2


}

void CMRKView::TextInBox(int x1,int y1,int width, int height,CString str,CDC* pDC)
{   int shiftx=0,shifty=0;
	pDC->Rectangle(x1,y1,x1+width,y1+height);
	CSize dim=pDC->GetTextExtent(str);
	if(dim.cx<width) shiftx=(width-dim.cx)/2;
	if(dim.cy<height) shifty=(height-dim.cy)/2;
	pDC->TextOut(x1+shiftx,y1+dim.cy+shifty,str);
   

}


void CMRKView::PrintOneColumn(CDC* pDC,int x, int y)
{   
	TextInBox(x,y,width,height,"Roll",pDC);
	TextInBox(x+width,y,width,height,"Marks",pDC);
	
    y=y-height;; 
	
	
	CString temproll,tempmark;
	for(int i=0;i<30;i++)
	 {  if(index<totalroll) { temproll=Roll[index];tempmark=Mark[index]; PageTotal+=atoi(tempmark);  }
		else {temproll=""; tempmark="";}
	   TextInBox(x,y,width,height,temproll,pDC);
       TextInBox(x+width,y,width,height,tempmark,pDC);
	   y=y-height;index++;
	 }

}



void CMRKView::PrintRoutine(CDC* pDC,int pageno) 
{  
    
	PrintHeader(pDC);
    PageTotal=0;
    
    totalroll=Roll.GetSize();
    for(int col=0;col<5;col++)
	{
    PrintOneColumn(pDC,tlx,tly);	
    tlx=tlx+width+width;
	}
   

	CString tmp;	
    tmp.Format("Page Total : %d",PageTotal);
   pDC->TextOut(98,-1050,tmp);
   pDC->TextOut(580,-1050,"Signature   -----------------");
   int counter=pageno*150;
   
   
  
   int lenn=keystring.GetLength();

   if(lenn>1)
   {//pDC->SelectObject(&foot); 
	for(int i=0;i<lenn;i++)	   
     pDC->TextOut(136+i*9,-990,keystring[i]);
      lenn=mrkstring.GetLength();
      for(i=0;i<lenn;i++)	   
        pDC->TextOut(137+i*9,-1001,mrkstring[i]);	  
    
	  pDC->TextOut(98,-990,"ANS");
      pDC->TextOut(98,-1001,"MRK");
      pDC->TextOut(130,-990,":"); 
      pDC->TextOut(130,-1001,":");
   }

 //  pDC->SelectObject(oldfont);
}





void CMRKView::OnTimer(UINT nIDEvent) 
{
	// TODO: Add your message handler code here and/or call default
	
	KillTimer(1);
    AfxGetMainWnd()->PostMessage(WM_CLOSE);

	CView::OnTimer(nIDEvent);
}

void CMRKView::OnDeskJet() 
{   
CTime ct = CTime::GetCurrentTime();
CString det;
det.Format("%02d-%02d-%d",ct.GetDay(),ct.GetMonth(),ct.GetYear());
CString docnem="Marklists-"+det;
    HDC    hdcPrn ;
    
    // Instantiate a CPrintDialog.
    CPrintDialog *printDlg =
    new CPrintDialog(FALSE, PD_ALLPAGES | PD_RETURNDC, NULL);
    // Initialize some of the fields in PRINTDLG structure.
    //printDlg->m_pd.nMinPage = printDlg->m_pd.nMaxPage = 1;
    //printDlg->m_pd.nFromPage = printDlg->m_pd.nToPage = 1;
    // Display Windows print dialog box.
    ///printDlg->DoModal(); To display Print Dialog
    printDlg->GetDefaults();
    // Obtain a handle to the device context.
    hdcPrn = printDlg->GetPrinterDC();
    if (hdcPrn != NULL)
        { CDC *pDC = new CDC;
    	     pDC->Attach (hdcPrn);      // attach a printer DC
	         if(oldfont=NULL) MessageBox("FF");
          pDC->StartDoc(docnem);     // begin a new print job
          pDC->StartPage();          // begin a new page
         	pDC->SetMapMode(MM_LOENGLISH);
          pDC->SetBkMode(TRANSPARENT);
         // fon.CreateFont(15,6,0,0,FW_NORMAL,0,0,0,ANSI_CHARSET,OUT_DEFAULT_PRECIS,
         // CLIP_DEFAULT_PRECIS,DEFAULT_QUALITY,DEFAULT_PITCH | FF_SWISS,"Times New Roman");
          oldfont=pDC->SelectObject(&fon);
          
		  
		  
          CString tempath;

             int TotalMrkFiles = Flist.GetSize();
             int pages;
       for(int j=0;j<TotalMrkFiles;j++)
			  { arr.RemoveAll();
	            Roll.RemoveAll();
				Mark.RemoveAll();
		        
		       tempath=installpath; tempath+="\\";
               tempath+=Flist[j];
			   ReadFromDisk(tempath);
			   pages=TotalRecords/150;
		       if(TotalRecords%150!=0) pages++;
			   index=0;  
		       for(int i=0;i<pages;i++)
		        {
				 tlx=100,tly=-150,width=65,height=25;  
				 PrintRoutine(pDC,i);    ///Main Printing Routine
                 pDC->EndPage();            // end a page
		        }
	           }
		  
		  pDC->EndDoc();             // end a print job
       		 pDC->SelectObject(oldfont);
          pDC->Detach();             // detach the printer DC
          delete pDC;
        }
    delete printDlg;

}



void CMRKView::OnDeskJet2() 
{    
	arr.RemoveAll();
	Roll.RemoveAll();
	Mark.RemoveAll();
    
	ReadFromDisk(curfilepath);   
    CString docnem;
	Division.TrimLeft();Division.TrimRight;
	Examination.TrimLeft();Examination.TrimRight;
	Subject.TrimLeft();Subject.TrimRight;
	docnem.Format("%s-%s-%s",Division,Examination,Subject);
    HDC    hdcPrn ;
    
    // Instantiate a CPrintDialog.
    CPrintDialog *printDlg =
    new CPrintDialog(FALSE, PD_ALLPAGES | PD_RETURNDC, NULL);
    // Initialize some of the fields in PRINTDLG structure.
    //printDlg->m_pd.nMinPage = printDlg->m_pd.nMaxPage = 1;
    //printDlg->m_pd.nFromPage = printDlg->m_pd.nToPage = 1;
    // Display Windows print dialog box.
    ///printDlg->DoModal(); To display Print Dialog
    printDlg->GetDefaults();
    // Obtain a handle to the device context.
    hdcPrn = printDlg->GetPrinterDC();
    if (hdcPrn != NULL)
        { CDC *pDC = new CDC;
    	     pDC->Attach (hdcPrn);      // attach a printer DC
	         if(oldfont=NULL) MessageBox("FF");
          pDC->StartDoc(docnem);     // begin a new print job
          pDC->StartPage();          // begin a new page
         	pDC->SetMapMode(MM_LOENGLISH);
          pDC->SetBkMode(TRANSPARENT);
          fon.CreateFont(15,6,0,0,FW_NORMAL,0,0,0,ANSI_CHARSET,OUT_DEFAULT_PRECIS,
          CLIP_DEFAULT_PRECIS,DEFAULT_QUALITY,DEFAULT_PITCH | FF_SWISS,"Times New Roman");
          oldfont=pDC->SelectObject(&fon);
          
		         int pages;
               
			   pages=TotalRecords/150;
		       if(TotalRecords%150!=0) pages++;
			   index=0;  
		       for(int i=0;i<pages;i++)
		        {
				 tlx=100,tly=-150,width=65,height=25;  
				 PrintRoutine(pDC,i);    ///Main Printing Routine
                 pDC->EndPage();            // end a page
		        }
	           
		  pDC->EndDoc();             // end a print job

       		
		  pDC->SelectObject(oldfont);
          pDC->Detach();             // detach the printer DC
          delete pDC;
        }
    delete printDlg;

}



