// MainFrm.cpp : implementation of the CMainFrame class
//

#include "stdafx.h"
#include "MRK.h"

#include "MainFrm.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CMainFrame

IMPLEMENT_DYNCREATE(CMainFrame, CFrameWnd)

BEGIN_MESSAGE_MAP(CMainFrame, CFrameWnd)
	//{{AFX_MSG_MAP(CMainFrame)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	ON_WM_CREATE()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

static UINT indicators[] =
{
	ID_SEPARATOR,           // status line indicator
	ID_INDICATOR_CAPS,
	ID_INDICATOR_NUM,
	ID_INDICATOR_SCRL,
};

/////////////////////////////////////////////////////////////////////////////
// CMainFrame construction/destruction







CMainFrame::CMainFrame()
{
	// TODO: add member initialization code here
	

}

CMainFrame::~CMainFrame()
{
}

int CMainFrame::OnCreate(LPCREATESTRUCT lpCreateStruct)
{
	if (CFrameWnd::OnCreate(lpCreateStruct) == -1)
		return -1;
	
	if (!m_wndToolBar.Create(this) ||
		!m_wndToolBar.LoadToolBar(IDR_MAINFRAME))
	{
		TRACE0("Failed to create toolbar\n");
		return -1;      // fail to create
	}

	if (!m_wndStatusBar.Create(this) ||
		!m_wndStatusBar.SetIndicators(indicators,
		  sizeof(indicators)/sizeof(UINT)))
	{
		TRACE0("Failed to create status bar\n");
		return -1;      // fail to create
	}

	// TODO: Remove this if you don't want tool tips or a resizeable toolbar
	m_wndToolBar.SetBarStyle(m_wndToolBar.GetBarStyle() |
		CBRS_TOOLTIPS | CBRS_FLYBY | CBRS_SIZE_DYNAMIC);

	// TODO: Delete these three lines if you don't want the toolbar to
	//  be dockable
	m_wndToolBar.EnableDocking(CBRS_ALIGN_ANY);
	EnableDocking(CBRS_ALIGN_ANY);
	DockControlBar(&m_wndToolBar);



CControlBar* pStatusBar = GetControlBar(AFX_IDW_STATUS_BAR);
ASSERT_VALID(pStatusBar);

CControlBar* pToolBar = GetControlBar(AFX_IDW_TOOLBAR);
ASSERT_VALID(pToolBar);


ShowControlBar(pToolBar, FALSE, FALSE); 
ShowControlBar(pStatusBar, FALSE, FALSE); 


	
	return 0;
}

BOOL CMainFrame::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	 // Call the base-class version 
   if( !CFrameWnd::PreCreateWindow(cs) )
      return FALSE;

    // Create a window without min/max buttons or sizable border 
    cs.style = WS_OVERLAPPED | WS_BORDER;

    // Size the window to 1/3 screen size and center it 
    cs.cy = ::GetSystemMetrics(SM_CYSCREEN) / 6; 
    cs.cx = ::GetSystemMetrics(SM_CXSCREEN) / 3; 
    cs.y = ((cs.cy * 6) - cs.cy) / 2; 
    cs.x = ((cs.cx * 3) - cs.cx) / 2;




     if(cs.hMenu!=NULL)
         {
             ::DestroyMenu(cs.hMenu);      // delete menu if loaded
             cs.hMenu = NULL;              // no menu for this window
         }

        

//   return TRUE;


	return CFrameWnd::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CMainFrame diagnostics

#ifdef _DEBUG
void CMainFrame::AssertValid() const
{
	CFrameWnd::AssertValid();
}

void CMainFrame::Dump(CDumpContext& dc) const
{
	CFrameWnd::Dump(dc);
}

#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CMainFrame message handlers
