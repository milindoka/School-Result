// MRKDoc.cpp : implementation of the CMRKDoc class
//

#include "stdafx.h"
#include "MRK.h"

#include "MRKDoc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CMRKDoc

IMPLEMENT_DYNCREATE(CMRKDoc, CDocument)

BEGIN_MESSAGE_MAP(CMRKDoc, CDocument)
	//{{AFX_MSG_MAP(CMRKDoc)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CMRKDoc construction/destruction

CMRKDoc::CMRKDoc()
{
	// TODO: add one-time construction code here

}

CMRKDoc::~CMRKDoc()
{
}

BOOL CMRKDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////
// CMRKDoc serialization

void CMRKDoc::Serialize(CArchive& ar)
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
// CMRKDoc diagnostics

#ifdef _DEBUG
void CMRKDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CMRKDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CMRKDoc commands
