// MRKreportView.cpp : implementation of the CMRKreportView class
//

#include "stdafx.h"
#include "MRKreport.h"

#include "MRKreportDoc.h"
#include "MRKreportView.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif


CFont fon;
CFont* oldfont;
CString det;
CFont LargeFont;
int pages;
CString installpath,plate;
int curline=0;
CStringArray Flist;

CString CollegeName1;
CString CollegeName2;
CString CollegeName3;

int TotLines;
int TotalMrkFiles;
int LinesPerPage=45;
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


CStringArray asub,adiv,aexam,aminer,apgtot;
//int m_pagetotal,totalrecords=120;
CString keystring,mrkstring;

CString curfilepath;
CString Left,Right;
CString templine;


void showno(int no)
{CString temp;
 temp.Format("%d",no);
 AfxMessageBox(temp);
}






/////////////////////////////////////////////////////////////////////////////
// CMRKreportView

IMPLEMENT_DYNCREATE(CMRKreportView, CView)

BEGIN_MESSAGE_MAP(CMRKreportView, CView)
	//{{AFX_MSG_MAP(CMRKreportView)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CMRKreportView construction/destruction

CMRKreportView::CMRKreportView()
{
	// TODO: add construction code here

}

CMRKreportView::~CMRKreportView()
{
}

BOOL CMRKreportView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CMRKreportView drawing

void CMRKreportView::OnDraw(CDC* pDC)
{
	CMRKreportDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	// TODO: add draw code for native data here

 fon.CreateFont(15,6,0,0,FW_NORMAL,0,0,0,ANSI_CHARSET,OUT_DEFAULT_PRECIS,
          CLIP_DEFAULT_PRECIS,DEFAULT_QUALITY,DEFAULT_PITCH | FF_SWISS,"Times New Roman");
    // oldf=pDC->SelectObject(&fon);

 LargeFont.CreateFont(30,10,0,0,FW_NORMAL,0,0,0,ANSI_CHARSET,OUT_DEFAULT_PRECIS,
          CLIP_DEFAULT_PRECIS,DEFAULT_QUALITY,DEFAULT_PITCH | FF_SWISS,"Times New Roman");


	
		if(GetModuleFileName(AfxGetInstanceHandle(),installpath.GetBuffer(MAX_PATH),MAX_PATH))
         {  installpath.ReleaseBuffer();
             int iBackslash=installpath.ReverseFind('\\');
   
		 if(iBackslash!=-1)
		    { installpath=installpath.Left(iBackslash);
		       
		 
		       LoadAllFiles();
               TotalMrkFiles=Flist.GetSize();
			  //if(Flist.GetSize()==0) msg="No Files Found In MRK.Exe Folder !";

              if(TotalMrkFiles==0) return;

			
		    for(int i=0;i<TotalMrkFiles;i++)
			
			  {CString tempath=installpath; tempath+="\\";
               tempath+=Flist[i];
			   ReadFromDisk(tempath);
			   asub.Add(Subject);adiv.Add(Division);aexam.Add(Examination);aminer.Add(Examiner);
			   
			   plate.Format("%d",PageTotal);    
			   apgtot.Add(plate);
               			   
			  }
             OnDeskJet();
			
		 }
				//  showno(TotalMrkFiles);
		 }


}

/////////////////////////////////////////////////////////////////////////////
// CMRKreportView printing

BOOL CMRKreportView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CMRKreportView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CMRKreportView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CMRKreportView diagnostics

#ifdef _DEBUG
void CMRKreportView::AssertValid() const
{
	CView::AssertValid();
}

void CMRKreportView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CMRKreportDoc* CMRKreportView::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CMRKreportDoc)));
	return (CMRKreportDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CMRKreportView message handlers




void CMRKreportView::LoadAllFiles()
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


void CMRKreportView::ReadFromDisk(CString fylepath)
{
	arr.RemoveAll();
    Roll.RemoveAll();
	Mark.RemoveAll();
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

templine=arr[HS];   split(); FRoll=Right;
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

PageTotal=0;
for(i=0;i<TotalRecords;i++)
     PageTotal+=atoi(Mark[i]);



}

int CMRKreportView::split()
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


void CMRKreportView::OnDeskJet() 
{ 
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
          pDC->StartDoc("Marklist Report");     // begin a new print job
          pDC->StartPage();          // begin a new page
         	pDC->SetMapMode(MM_LOENGLISH);
          pDC->SetBkMode(TRANSPARENT);
        // fon.CreateFont(15,6,0,0,FW_NORMAL,0,0,0,ANSI_CHARSET,OUT_DEFAULT_PRECIS,
        // CLIP_DEFAULT_PRECIS,DEFAULT_QUALITY,DEFAULT_PITCH | FF_SWISS,"Times New Roman");
          oldfont=pDC->SelectObject(&fon);
          
       CTime theTime = CTime::GetCurrentTime();
       det = theTime.Format("%d-%b-%y,   %H : %M  PM");

		  

       TotLines = asub.GetSize();
       curline=0;
	   pages=TotLines/LinesPerPage;   /// total full pages
	   int remainder=TotLines%LinesPerPage; /// if any line is remianing ?
		   if(remainder!=0) pages++;        ///  then add pagecount
			 
	  
      //  PrintRoutine(pDC,0);    ///Main Printing Routine
      
		for(int j=0;j<pages;j++)
			  { 
		        PrintRoutine(pDC,j);    ///Main Printing Routine
                pDC->EndPage();         // end a page
		        
	           }
		 
		  pDC->EndDoc();             // end a print job
       		 pDC->SelectObject(oldfont);
          pDC->Detach();             // detach the printer DC
          delete pDC;
        }
    delete printDlg;

}


void CMRKreportView::PrintRoutine(CDC* pDC,int pageno) 
{  //int col[]={100,290,350,430,530,650 };
   int col[]={120,270,370,470,570,700};

   

//	MessageBox("TT");
   int tlx=100,tly=-50,linespacing=20;
   CString strpgn;strpgn.Format("Page No.: %d/%d",pageno+1,pages); 


 //  pDC->TextOut(370,tly,"SIWS College,Wadala");
   pDC->TextOut(710,tly,strpgn);
   tly=tly-linespacing-10;
   
   
   pDC->TextOut(col[0]-20,tly,"Exam Dept : Marklists Received Up To  :  "+det);
   tly=tly-linespacing-10;

   
                             pDC->TextOut(col[0],tly,"Subject");
                             pDC->TextOut(col[1],tly,"Division");
							 pDC->TextOut(col[2],tly,"Examination");
							 pDC->TextOut(col[3],tly,"Page Total");
							 pDC->TextOut(col[4]+20,tly,"Examiner");
							 //pDC->TextOut(col[5],tly,"Signature");

   tly=tly-linespacing-10;

   for(int i=0;i<LinesPerPage;i++)
     { if(curline<TotLines){ pDC->TextOut(col[0]-20,tly,asub[curline]);
                             pDC->TextOut(col[1]+10,tly,adiv[curline]);
							 pDC->TextOut(col[2]+10,tly,aexam[curline]);
							 pDC->TextOut(col[3]+10,tly,apgtot[curline]);
							 pDC->TextOut(col[4]+10,tly,aminer[curline]);
							 pDC->MoveTo(col[0]-20,tly-17);
							 pDC->LineTo(800,tly-17);

                            }
//       MessageBox(reportLine[curline]);
       curline++;
       tly=tly-linespacing;
      }

   pDC->TextOut(col[0]-20,-1080,"Principal/Vice Principal :");

}


