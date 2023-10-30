package Assignment;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;


@SuppressWarnings("serial")
public class JDBCSecondWindowContent extends JInternalFrame implements ActionListener
{
	// DB Connectivity Attributes
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	private Container content;
	
	private JPanel detailsPanel;
	private JPanel exportButtonPanel;
	private JPanel exportConceptDataPanel;
	private JScrollPane dbContentsPanel;
	
	private Border lineBorder;
	
	private JLabel itemIdLabel=new JLabel("Item ID:                 ");
	private JLabel itemNameLabel=new JLabel("Item Name:               ");
	private JLabel manufacturerLabel=new JLabel("Manufacturer:      ");
	private JLabel connectivityTypeLabel=new JLabel("Connectivity Type:        ");
	private JLabel roomLabel=new JLabel("Room:                 ");
	
	private JTextField itemIdTF= new JTextField(10);
	private JTextField itemNameTF=new JTextField(10);
	private JTextField manufacturerTF=new JTextField(10);
	private JTextField connectivityTF=new JTextField(10);
	private JTextField roomTF=new JTextField(10);
				
	private static QueryTable2Model Table2Model = new QueryTable2Model();
	
	//Add the models to JTabels
	private JTable Table2ofDBContents=new JTable(Table2Model);
	
	//Buttons for inserting, and updating members
	//also a clear button to clear details panel
	private JButton updateButton = new JButton("Update");
	private JButton insertButton = new JButton("Insert");
	private JButton exportButton  = new JButton("Export");
	private JButton deleteButton  = new JButton("Delete");
	private JButton clearButton  = new JButton("Clear");

	private JButton last3LossRates  = new JButton("3 Previous Loss Rates per AP");
	private JTextField last3LossRatesTF  = new JTextField(12);
	private JButton avgofRSS  = new JButton("Avg Loss for last 3 Rec per AP");
	private JTextField avgofRSSTF  = new JTextField(12);
	private JButton overLappingAP  = new JButton("AP Location");
	private JButton overLappingChannels  = new JButton("AP Channel");
	


	public JDBCSecondWindowContent( String aTitle)
	{	
		//setting up the GUI
		super(aTitle, false,false,false,false);
		setEnabled(true);
		
		initiate_db_conn();
		//add the 'main' panel to the Internal Frame
		content=getContentPane();
		content.setLayout(null);
		content.setBackground(Color.lightGray);
		lineBorder = BorderFactory.createEtchedBorder(15, Color.red, Color.black);
	
		//setup details panel and add the components to it
		detailsPanel=new JPanel();
		detailsPanel.setLayout(new GridLayout(11,2));
		detailsPanel.setBackground(Color.lightGray);
		detailsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "AP Details"));
			
		detailsPanel.add(itemIdLabel);			
		detailsPanel.add(itemIdTF);
		detailsPanel.add(itemNameLabel);		
		detailsPanel.add(itemNameTF);
		detailsPanel.add(manufacturerLabel);	
		detailsPanel.add(manufacturerTF);
		detailsPanel.add(connectivityTypeLabel);		
		detailsPanel.add(connectivityTF);
		detailsPanel.add(roomLabel);
		detailsPanel.add(roomTF);
		
		//setup details panel and add the components to it
		exportButtonPanel=new JPanel();
		exportButtonPanel.setLayout(new GridLayout(3,2));
		exportButtonPanel.setBackground(Color.lightGray);
		exportButtonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Export Data"));
		exportButtonPanel.add(last3LossRates);
		exportButtonPanel.add(last3LossRatesTF);
		exportButtonPanel.add(avgofRSS);
		exportButtonPanel.add(avgofRSSTF);
		exportButtonPanel.add(overLappingAP);
		exportButtonPanel.add(overLappingChannels);
		exportButtonPanel.setSize(500, 200);
		exportButtonPanel.setLocation(3, 300);
		content.add(exportButtonPanel);
		
		
	
		insertButton.setSize(100, 30);
		updateButton.setSize(100, 30);
		exportButton.setSize (100, 30);
		deleteButton.setSize (100, 30);
		clearButton.setSize (100, 30);
		
		insertButton.setLocation(370, 10);
		updateButton.setLocation(370, 110);
		exportButton.setLocation (370, 160);
		deleteButton.setLocation (370, 60);
		clearButton.setLocation (370, 210);
		
		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		exportButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);

		
		content.add(insertButton);
		content.add(updateButton);
		content.add(exportButton);
		content.add(deleteButton);
		content.add(clearButton);

				
		Table2ofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));
	
		dbContentsPanel=new JScrollPane(Table2ofDBContents,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel.setBackground(Color.lightGray);
		dbContentsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder,"Database Content"));
		
		detailsPanel.setSize(360, 300);
		detailsPanel.setLocation(3,0);
		dbContentsPanel.setSize(700, 300);
		dbContentsPanel.setLocation(477, 0);
		
		content.add(detailsPanel);
		content.add(dbContentsPanel);
		
		setSize(982,645);
		setVisible(true);
	
		Table2Model.refreshFromDB(stmt);
	}
	
	public void initiate_db_conn()
	{
		try
		{
			// Load the JConnector Driver
			Class.forName("com.mysql.jdbc.Driver");
			// Specify the DB Name
			String url="jdbc:mysql://localhost:3306/Assignment";
			// Connect to DB using DB URL, Username and password
			con = DriverManager.getConnection(url, "root", "@SQLTeemo25");
			//Create a generic statement which is passed to the TestInternalFrame1
			stmt = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error: Failed to connect to database\n"+e.getMessage());
		}
	}
	
	//event handling for members desktop
	public void actionPerformed(ActionEvent e)
	{
		 Object target=e.getSource();
		 if (target == clearButton)
		 {
			 itemIdTF.setText("");
			 itemNameTF.setText("");
			 manufacturerTF.setText("");
			 connectivityTF.setText("");
			 roomTF.setText("");
		 }
		
		 if (target == insertButton)
		 {		 
	 		try
	 		{
 				String updateTemp ="INSERT INTO SmartHomeItems VALUES ('"+
 		 				  itemIdTF.getText()+"','"+itemNameTF.getText()+"','"+manufacturerTF.getText()+"','"+connectivityTF.getText()+"','"+roomTF.getText()+"');";
 						
 				stmt.executeUpdate(updateTemp);
 			
	 		}
	 		catch (SQLException sqle)
	 		{
	 			System.err.println("Error with members insert:\n"+sqle.toString());
	 		}
	 		finally
	 		{
	 			Table2Model.refreshFromDB(stmt);
			}
		 }
		 if (target == deleteButton)
		 {
		 	
	 		try
	 		{
 				String updateTemp ="DELETE FROM SmartHomeItems WHERE item_id = "+itemIdTF.getText()+";"; 
 				stmt.executeUpdate(updateTemp);
 			
	 		}
	 		catch (SQLException sqle)
	 		{
	 			System.err.println("Error with delete:\n"+sqle.toString());
	 		}
	 		finally
	 		{
	 			Table2Model.refreshFromDB(stmt);
			}
		 }
		 if (target == updateButton)
		 {	 	
	 		try
	 		{ 			
 				String updateTemp ="UPDATE SmartHomeItems SET item_name = '"+itemNameTF.getText()+
 									"', manufacturer = '"+manufacturerTF.getText()+
 									"', connectivity_type = '"+connectivityTF.getText()+
 									"', room_location = '"+roomTF.getText() +
 									"' WHERE  item_id = '"+itemIdTF.getText()+"';"; 				
 				
 				System.out.println(updateTemp);
 				stmt.executeUpdate(updateTemp);
 				//these lines do nothing but the table updates when we access the db.
 				rs = stmt.executeQuery("SELECT * from SmartHomeItems ");
 				rs.next();
 				rs.close();	
 			}
	 		catch (SQLException sqle){
	 			System.err.println("Error with members insert:\n"+sqle.toString());
	 		}
	 		finally{
	 			Table2Model.refreshFromDB(stmt);
			}
		 }		 	
	}
	

}
