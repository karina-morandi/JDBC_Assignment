package Assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class JDBCMainWindow extends JFrame implements ActionListener
	{
		private JMenuItem exitItem;
		private JMenuItem secondTable;

		public JDBCMainWindow()
		{
			// Sets the Window Title
			super( "MSc JDBC Assignment" ); 
			
			//Setup fileMenu and its elements
			JMenuBar menuBar=new JMenuBar();
			JMenu fileMenu=new JMenu("First Table");
			secondTable=new JMenu("Second Table");
			exitItem =new JMenuItem("Exit");
	
			fileMenu.add(secondTable);
			menuBar.add(fileMenu);
			setJMenuBar(menuBar);
			
			fileMenu.add(exitItem);
			menuBar.add(fileMenu );
			setJMenuBar(menuBar);
			
			// Add a listener to the Exit Menu Item
			exitItem.addActionListener(this);

			// Create an instance of our class JDBCMainWindowContent 
			JDBCMainWindowContent aWindowContent = new JDBCMainWindowContent( "MSc JDBC Assignment");
			// Add the instance to the main section of the window
			getContentPane().add( aWindowContent );
			
			// Create an instance of our class JDBCSecondWindowContent 
			
			// Add the instance to the main section of the window
						
			
			setSize( 1200, 600 );
			setVisible( true );
		}
		
		// The event handling for the main frame
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource().equals(exitItem)){
				this.dispose();
			} else if(e.getSource().equals(secondTable)) {
				JDBCSecondWindowContent bWindowContent = new JDBCSecondWindowContent("MSc JDBC Assignment");
				getContentPane().add( bWindowContent );
			}
		}
		
		
		
	}


