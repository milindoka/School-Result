// MRKreportDoc.cpp : implementation of the CMRKreportDoc class
//

#include "stdafx.h"
#include "MRKreport.h"

#include "MRKreportDoc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CMRKreportDoc

IMPLEMENT_DYNCREATE(CMRKreportDoc, CDocument)

BEGIN_MESSAGE_MAP(CMRKreportDoc, CDocument)
	//{{AFX_MSG_MAP(CMRKreportDoc)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CMRKreportDoc construction/destruction

CMRKreportDoc::CMRKreportDoc()
{
	// TODO: add one-time construction code here

}

CMRKreportDoc::~CMRKreportDoc()
{
}

BOOL CMRKreportDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////
// CMRKreportDoc serialization

void CMRKreportDoc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
	}
}

/////////////////////////////////////////////////////////////////////////////
// CMRKreportDoc diagnostics

#ifdef _DEBUG
void CMRKreportDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CMRKreportDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CMRKreportDoc commands
