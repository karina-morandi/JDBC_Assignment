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
	    private JMenuItem table3Item;

		public JDBCMainWindow()
		{
			super( "MSc JDBC Assignment" ); 
			
			JMenuBar menuBar=new JMenuBar();
			JMenu fileMenu=new JMenu("File");
			exitItem =new JMenuItem("Exit");
			table1Item = new JMenuItem("Item Prices");
	        table2Item = new JMenuItem("Smart Home Items");
	        table3Item = new JMenuItem("Average Salaries");
			

	        fileMenu.add(exitItem);
	        fileMenu.add(table1Item);
	        fileMenu.add(table2Item);
	        fileMenu.add(table3Item);
	        
			
			fileMenu.add(exitItem);
			menuBar.add(fileMenu );
			setJMenuBar(menuBar);
			

			exitItem.addActionListener(this);
			table1Item.addActionListener(this);
	        table2Item.addActionListener(this);	
	        table3Item.addActionListener(this);	
			
			setSize( 1200, 600 );
			setVisible( true );

	}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(exitItem)){
				this.dispose();
			}else if (e.getSource().equals(table1Item)) {
			    JDBCMainWindowContent aWindowContent = new JDBCMainWindowContent("MSc JDBC Assignment");
			    getContentPane().removeAll();
			    getContentPane().add(aWindowContent);
			    revalidate();
			    repaint();
			} else if (e.getSource().equals(table2Item)) {
			    JDBCSecondWindowContent bWindowContent = new JDBCSecondWindowContent("MSc JDBC Assignment");
			    getContentPane().removeAll();
			    getContentPane().add(bWindowContent);
			    revalidate();
			    repaint();
			}else if (e.getSource().equals(table3Item)) {
			    JDBCThirdWindowContent cWindowContent = new JDBCThirdWindowContent("MSc JDBC Assignment");
			    getContentPane().removeAll();
			    getContentPane().add(cWindowContent);
			    revalidate();
			    repaint();
			}
			
		}
}
