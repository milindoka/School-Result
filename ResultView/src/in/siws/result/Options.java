package in.siws.result;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Options 
{
   ////Basic 2 Functions For MVC Design---------------------------------------------------------------
	private static Options instance = null;  /* ===== singleton details ===== */
	protected Options() 
	{/* nothing needed, but this prevents a public no-arg  constructor from being created automatically */}
	public static Options getInstance()
	{ if (instance==null)	{ instance = new Options();} 	return instance;}
      //------------------------------------------------------------------------------------------------

	
	
	
	public int SelectOption()
    { 
		int mc = JOptionPane.QUESTION_MESSAGE;

		String[] opts = {"Cancel","Print Consolidated","Empty Names", "Merit", "Mod", "Stat","FList","Sub-Merit"};
		
		int ch = JOptionPane.showOptionDialog (null, "Choose Option", "More Options", 0, mc, null, opts, opts[0]); 
		return ch;
    }


}
