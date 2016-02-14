// MRKreportView.h : interface of the CMRKreportView class
//
/////////////////////////////////////////////////////////////////////////////

class CMRKreportView : public CView
{
protected: // create from serialization only
	CMRKreportView();
	DECLARE_DYNCREATE(CMRKreportView)

// Attributes
public:
	CMRKreportDoc* GetDocument();


	void LoadAllFiles();
	void ReadFromDisk(CString fylepath);
	int split();
	void OnDeskJet();
	void PrintRoutine(CDC* pDC,int pageno);
// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CMRKreportView)
	public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CMRKreportView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CMRKreportView)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in MRKreportView.cpp
inline CMRKreportDoc* CMRKreportView::GetDocument()
   { return (CMRKreportDoc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////
