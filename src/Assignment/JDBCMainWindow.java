package Assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.*;

public class JDBCMainWindow extends JFrame implements ActionListener
	{
		private JMenuItem exitItem;
		private JMenuItem table1Item;
	    private JMenuItem table2Item;
//		JRadioButton jRadioButton1;
//		JRadioButton jRadioButton2;
//		JButton jButton;
//		ButtonGroup G1;
//		JLabel L1;
//		JLabel L2;

		public JDBCMainWindow()
		{
			// Sets the Window Title
			super( "MSc JDBC Assignment" ); 
			
			//Setup fileMenu and its elements
			JMenuBar menuBar=new JMenuBar();
			JMenu fileMenu=new JMenu("File");
			exitItem =new JMenuItem("Exit");
			table1Item = new JMenuItem("Table 1");
	        table2Item = new JMenuItem("Table 2");
			
//			jRadioButton1 = new JRadioButton();
//			jRadioButton2 = new JRadioButton();
//			jButton = new JButton("Select");
//			G1 = new ButtonGroup();
//			L1 = new JLabel("Table 1");
//			L2 = new JLabel("Table 2");
//			
//			jRadioButton1.setBounds(60,30,60,50);
//			jRadioButton2.setBounds(120,30,40,50);
//			jButton.setBounds(125, 90, 80, 30);
//			L1.setBounds(20, 30,60,30);
//			L1.setBounds(50, 30, 55,30);
//			
//			this.add(jRadioButton1);
//			this.add(jRadioButton2);
//			this.add(jButton);
//			this.add(L1);
//			this.add(L2);
//			
//			G1.add(jRadioButton1);
//			G1.add(jRadioButton2);
			
	        fileMenu.add(exitItem);
	        fileMenu.add(table1Item);
	        fileMenu.add(table2Item);
	        
	        
//			menuBar.add(fileMenu);
//			setJMenuBar(menuBar);
			
			fileMenu.add(exitItem);
			menuBar.add(fileMenu );
			setJMenuBar(menuBar);
			
			// Add a listener to the Exit Menu Item
			exitItem.addActionListener(this);
			table1Item.addActionListener(this);
	        table2Item.addActionListener(this);			
			
//			// Create an instance of our class JDBCMainWindowContent 
//			JDBCMainWindowContent aWindowContent = new JDBCMainWindowContent( "MSc JDBC Assignment");
//			// Add the instance to the main section of the window
//			getContentPane().add( aWindowContent );
			
			// Create an instance of our class JDBCSecondWindowContent 
			
			// Add the instance to the main section of the window
						
			
			setSize( 1200, 600 );
			setVisible( true );

//			jButton.addActionListener(new ActionListener() {
//		// The event handling for the main frame
//				public void actionPerformed(ActionEvent e)
//				{	
////					Object target=e.getSource();
//					
//					if(jRadioButton1.isSelected()) {
//						// Create an instance of our class JDBCMainWindowContent 
//						JDBCMainWindowContent aWindowContent = new JDBCMainWindowContent( "MSc JDBC Assignment");
//						// Add the instance to the main section of the window
//						getContentPane().add( aWindowContent );
//					} else if(jRadioButton2.isSelected()) {
//						JDBCSecondWindowContent bWindowContent = new JDBCSecondWindowContent("MSc JDBC Assignment");
//						getContentPane().add( bWindowContent );	
//					} 
//					JOptionPane jOptionPane = new JOptionPane();
//					jOptionPane.getRootPane();
//				}
//			});
		//			} else if(e.getSource().equals(secondTable)) {
		//				JDBCSecondWindowContent bWindowContent = new JDBCSecondWindowContent("MSc JDBC Assignment");
		//				getContentPane().add( bWindowContent );		
	}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(exitItem)){
				this.dispose();
			}else if (e.getSource().equals(table1Item)) {
			    // Create an instance of your class for Table 1
			    JDBCMainWindowContent aWindowContent = new JDBCMainWindowContent("MSc JDBC Assignment");
			    getContentPane().removeAll();
			    // Add the instance to the main section of the window
			    getContentPane().add(aWindowContent);
			    revalidate();
			    repaint();// Refresh the content
			} else if (e.getSource().equals(table2Item)) {
			    // Create an instance of your class for Table 2
			    JDBCSecondWindowContent bWindowContent = new JDBCSecondWindowContent("MSc JDBC Assignment");
			    // Add the instance to the main section of the window
			    getContentPane().removeAll();
			    getContentPane().add(bWindowContent);
			    revalidate();
			    repaint();// Refresh the content
			}
			
		}
}
