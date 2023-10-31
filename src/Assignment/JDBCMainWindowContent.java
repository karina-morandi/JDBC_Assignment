package Assignment;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;


@SuppressWarnings("serial")
public class JDBCMainWindowContent extends JInternalFrame implements ActionListener
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
	
	private JLabel itemIdLabel=new JLabel("Item ID: ");
	private JLabel IrelandMinPriceLabel=new JLabel("Ireland Min:");
	private JLabel IrelandMaxPriceLabel=new JLabel("Ireland Max:");
	private JLabel USAMinPriceLabel=new JLabel("USA Min:");
	private JLabel USAMaxPriceLabel=new JLabel("USA Max");
	private JLabel UKMinPriceLabel=new JLabel("UK Min");
	private JLabel UKMaxPriceLabel=new JLabel("UK Max");
	private JLabel GermanyMinPriceLabel=new JLabel("Germany Min");
	private JLabel GermanyMaxPriceLabel=new JLabel("Germany Max");
	private JLabel JapanMinPriceLabel=new JLabel("Japan Min");
	private JLabel JapanMaxPriceLabel=new JLabel("Japan Max");
	private JLabel BrazilMinPriceLabel=new JLabel("Brazil Min");
	private JLabel BrazilMaxPriceLabel=new JLabel("Brazil Max");
	private JLabel EgyptMinPriceLabel=new JLabel("Egypt Min");
	private JLabel EgyptMaxPriceLabel=new JLabel("Egypt Max");
	private JLabel AustraliaMinPriceLabel=new JLabel("Australia Min");
	private JLabel AustraliaMaxPriceLabel=new JLabel("Australia Max");
	
	private JTextField itemIDTF= new JTextField(10);
	private JTextField IrelandMinPriceTF=new JTextField(10);
	private JTextField IrelandMaxPriceTF=new JTextField(10);
	private JTextField USAMinPriceTF=new JTextField(10);
	private JTextField USAMaxPriceTF=new JTextField(10);
	private JTextField UKMinPriceTF=new JTextField(10);
	private JTextField UKMaxPriceTF=new JTextField(10);
	private JTextField GermanyMinPriceTF=new JTextField(10);
	private JTextField GermanyMaxPriceTF=new JTextField(10);
	private JTextField JapanMinPriceTF=new JTextField(10);
	private JTextField JapanMaxPriceTF=new JTextField(10);
	private JTextField BrazilMinPriceTF=new JTextField(10);
	private JTextField BrazilMaxPriceTF=new JTextField(10);
	private JTextField EgyptMinPriceTF=new JTextField(10);
	private JTextField EgyptMaxPriceTF=new JTextField(10);
	private JTextField AustraliaMinPriceTF=new JTextField(10);
	private JTextField AustraliaMaxPriceTF=new JTextField(10);
			
	private static QueryTableModel TableModel = new QueryTableModel();
	
	//Add the models to JTabels
	private JTable TableofDBContents=new JTable(TableModel);
	private JTable Table2ofDBContents=new JTable(TableModel);
	
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
	


	public JDBCMainWindowContent( String aTitle)
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
			
		detailsPanel.add(IrelandMinPriceLabel);		
		detailsPanel.add(IrelandMinPriceTF);
		detailsPanel.add(IrelandMaxPriceLabel);	
		detailsPanel.add(IrelandMaxPriceTF);
		detailsPanel.add(USAMinPriceLabel);		
		detailsPanel.add(USAMinPriceTF);
		detailsPanel.add(USAMaxPriceLabel);	
		detailsPanel.add(USAMaxPriceTF);
		detailsPanel.add(UKMinPriceLabel);		
		detailsPanel.add(UKMinPriceTF);
		detailsPanel.add(UKMaxPriceLabel);	
		detailsPanel.add(UKMaxPriceTF);
		detailsPanel.add(GermanyMinPriceLabel);		
		detailsPanel.add(GermanyMinPriceTF);
		detailsPanel.add(GermanyMaxPriceLabel);	
		detailsPanel.add(GermanyMaxPriceTF);
		detailsPanel.add(JapanMinPriceLabel);		
		detailsPanel.add(JapanMinPriceTF);
		detailsPanel.add(JapanMaxPriceLabel);	
		detailsPanel.add(JapanMaxPriceTF);
		detailsPanel.add(BrazilMinPriceLabel);		
		detailsPanel.add(BrazilMinPriceTF);
		detailsPanel.add(BrazilMaxPriceLabel);	
		detailsPanel.add(BrazilMaxPriceTF);
		detailsPanel.add(EgyptMinPriceLabel);		
		detailsPanel.add(EgyptMinPriceTF);
		detailsPanel.add(EgyptMaxPriceLabel);	
		detailsPanel.add(EgyptMaxPriceTF);
		detailsPanel.add(AustraliaMinPriceLabel);		
		detailsPanel.add(AustraliaMinPriceTF);
		detailsPanel.add(AustraliaMaxPriceLabel);	
		detailsPanel.add(AustraliaMaxPriceTF);
		detailsPanel.add(itemIdLabel);			
		detailsPanel.add(itemIDTF);
		
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

				
		TableofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));
		Table2ofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));

	
		dbContentsPanel=new JScrollPane(TableofDBContents,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
	
		TableModel.refreshFromDB(stmt);
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
			 itemIDTF.setText("");
			 IrelandMinPriceTF.setText("");
			 IrelandMaxPriceTF.setText("");
			 USAMinPriceTF.setText("");
			 USAMaxPriceTF.setText("");
			 UKMinPriceTF.setText("");
			 UKMaxPriceTF.setText("");
			 GermanyMinPriceTF.setText("");
			 GermanyMaxPriceTF.setText("");
			 JapanMinPriceTF.setText("");
			 JapanMaxPriceTF.setText("");
			 BrazilMinPriceTF.setText("");
			 BrazilMaxPriceTF.setText("");
			 EgyptMinPriceTF.setText("");
			 EgyptMaxPriceTF.setText("");
			 AustraliaMinPriceTF.setText("");
			 AustraliaMaxPriceTF.setText("");
			 	 
		 }
		
		 if (target == insertButton)
		 {		 
	 		try
	 		{
 				String updateTemp ="INSERT INTO ItemPrices VALUES ('"+
 		 				  itemIDTF.getText()+"','"+IrelandMinPriceTF.getText()+"','"+IrelandMaxPriceTF.getText()+"','"+USAMinPriceTF.getText()+"','"+USAMaxPriceTF.getText()+"','"+UKMinPriceTF.getText()+"','"+UKMaxPriceTF.getText()+"','"+GermanyMinPriceTF.getText()+"','"+GermanyMaxPriceTF.getText()+"','"+JapanMinPriceTF.getText()+"','"+JapanMaxPriceTF.getText()+"','"+BrazilMinPriceTF.getText()+"','"+BrazilMaxPriceTF.getText()+"','"+EgyptMinPriceTF.getText()+"','"+EgyptMaxPriceTF.getText()+"','"+AustraliaMinPriceTF.getText()+"','"+AustraliaMaxPriceTF.getText()+"');";
 				
 				stmt.executeUpdate(updateTemp);
 			
	 		}
	 		catch (SQLException sqle)
	 		{
	 			System.err.println("Error with members insert:\n"+sqle.toString());
	 		}
	 		finally
	 		{
	 			TableModel.refreshFromDB(stmt);
			}
		 }
		 if (target == deleteButton)
		 {
		 	
	 		try
	 		{
 				String updateTemp ="DELETE FROM ItemPrices WHERE Item_id = "+itemIDTF.getText()+";"; 
 				stmt.executeUpdate(updateTemp);
 			
	 		}
	 		catch (SQLException sqle)
	 		{
	 			System.err.println("Error with delete:\n"+sqle.toString());
	 		}
	 		finally
	 		{
	 			TableModel.refreshFromDB(stmt);
			}
		 }
		 if (target == updateButton)
		 {	 	
	 		try
	 		{ 			
 				String updateTemp ="UPDATE ItemPrices SET Ireland_Min = '"+IrelandMinPriceTF.getText()+"', Ireland_Max = '"+IrelandMaxPriceTF.getText()+"', USA_Min = '"+USAMinPriceTF.getText()+"', USA_Max = '"+USAMaxPriceTF.getText()+"', UK_Min = '"+UKMinPriceTF.getText()+"', UK_Max = '"+UKMaxPriceTF.getText()+"', Germany_Min = '"+GermanyMinPriceTF.getText()+"', Germany_Max = '"+GermanyMaxPriceTF.getText()+"', Japan_Min = '"+JapanMinPriceTF.getText()+"', Japan_Max = '"+JapanMaxPriceTF.getText()+"', Brazil_Min = '"+BrazilMinPriceTF.getText()+"', Brazil_Max = '"+BrazilMaxPriceTF.getText()+"', Egypt_Min = '"+EgyptMinPriceTF.getText()+"', Egypt_Max = '"+EgyptMaxPriceTF.getText()+"', Australia_Min = '"+AustraliaMinPriceTF.getText()+"', Australia_Max = '"+AustraliaMaxPriceTF.getText()+
 									"' WHERE item_id = '"+itemIDTF.getText()+"';";
 				
 				System.out.println(updateTemp);
 				stmt.executeUpdate(updateTemp);
 				//these lines do nothing but the table updates when we access the db.
 				rs = stmt.executeQuery("SELECT * from ItemPrices ");
 				rs.next();
 				rs.close();	
 			}
	 		catch (SQLException sqle){
	 			System.err.println("Error with members insert:\n"+sqle.toString());
	 		}
	 		finally{
	 			TableModel.refreshFromDB(stmt);
			}
		 }		 	
	}
	

}
