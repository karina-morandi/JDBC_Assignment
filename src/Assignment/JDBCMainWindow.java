package Assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.event.*;

public class JDBCMainWindow extends JFrame implements ActionListener
	{
		private JMenuItem exitItem;
		JRadioButton jRadioButton1;
		JRadioButton jRadioButton2;
		JButton jButton;
		ButtonGroup G1;
		JLabel L1;
		JLabel L2;
//		private JMenuItem secondTable;

		public JDBCMainWindow()
		{
			// Sets the Window Title
			super( "MSc JDBC Assignment" ); 
			
			//Setup fileMenu and its elements
			JMenuBar menuBar=new JMenuBar();
			JMenu fileMenu=new JMenu("Table");
//			secondTable=new JMenu("Second Table");
			exitItem =new JMenuItem("Exit");
			
			jRadioButton1 = new JRadioButton();
			jRadioButton2 = new JRadioButton();
			jButton = new JButton("Select");
			G1 = new ButtonGroup();
			L1 = new JLabel("Table 1");
			L2 = new JLabel("Table 2");
			
			jRadioButton1.setBounds(120,30,120,50);
			jRadioButton2.setBounds(250,30,80,50);
			jButton.setBounds(125, 90, 80, 30);
			L1.setBounds(20, 30,150,30);
			L1.setBounds(50, 30,110,30);
			
			this.add(jRadioButton1);
			this.add(jRadioButton2);
			this.add(jButton);
			this.add(L1);
			this.add(L2);
			
			G1.add(jRadioButton1);
			G1.add(jRadioButton2);
			
//			fileMenu.add(secondTable);
//			menuBar.add(fileMenu);
//			setJMenuBar(menuBar);
			
			fileMenu.add(exitItem);
			menuBar.add(fileMenu );
			setJMenuBar(menuBar);
			
			// Add a listener to the Exit Menu Item
			exitItem.addActionListener(this);

//			// Create an instance of our class JDBCMainWindowContent 
//			JDBCMainWindowContent aWindowContent = new JDBCMainWindowContent( "MSc JDBC Assignment");
//			// Add the instance to the main section of the window
//			getContentPane().add( aWindowContent );
			
			// Create an instance of our class JDBCSecondWindowContent 
			
			// Add the instance to the main section of the window
						
			
			setSize( 1200, 600 );
			setVisible( true );

			jButton.addActionListener(new ActionListener() {
		// The event handling for the main frame
				public void actionPerformed(ActionEvent e)
				{	
//					Object target=e.getSource();
					
					if(jRadioButton1.isSelected()) {
						// Create an instance of our class JDBCMainWindowContent 
						JDBCMainWindowContent aWindowContent = new JDBCMainWindowContent( "MSc JDBC Assignment");
						// Add the instance to the main section of the window
						getContentPane().add( aWindowContent );
					} else if(jRadioButton2.isSelected()) {
						JDBCSecondWindowContent bWindowContent = new JDBCSecondWindowContent("MSc JDBC Assignment");
						getContentPane().add( bWindowContent );	
					} 
					JOptionPane jOptionPane = new JOptionPane();
					jOptionPane.getRootPane();
				}
			});
		//			} else if(e.getSource().equals(secondTable)) {
		//				JDBCSecondWindowContent bWindowContent = new JDBCSecondWindowContent("MSc JDBC Assignment");
		//				getContentPane().add( bWindowContent );		
	}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(exitItem)){
				this.dispose();
			}
			
		}
}

