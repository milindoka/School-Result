// MRKView.h : interface of the CMRKView class
//
/////////////////////////////////////////////////////////////////////////////

class CMRKView : public CView
{
protected: // create from serialization only
	CMRKView();
	DECLARE_DYNCREATE(CMRKView)

// Attributes
public:
	CMRKDoc* GetDocument();

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CMRKView)
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
    
    void LoadAllFiles();
	void PrintRoutine(CDC* pDC,int pageno);
	//void PrintRoutine2(CDC* pDC,int pageno);
	void OnDeskJet();
	void OnDeskJet2();
	void CentreText(int y,CString str,CDC* pDC);
    void ReadFromDisk(CString fyle);
	int split();
	void PrintHeader(CDC* pDC);
	void TextInBox(int x1,int y1,int width, int height,CString str,CDC* pDC);
    void PrintOneColumn(CDC* pdc,int x, int y);

	virtual ~CMRKView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CMRKView)
	afx_msg void OnTimer(UINT nIDEvent);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in MRKView.cpp
inline CMRKDoc* CMRKView::GetDocument()
   { return (CMRKDoc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////
