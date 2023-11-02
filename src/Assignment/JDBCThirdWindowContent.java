package Assignment;


import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.*;


@SuppressWarnings("serial")
public class JDBCThirdWindowContent extends JInternalFrame implements ActionListener
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
	
	private JLabel countryLabel=new JLabel("Country: ");
	private JLabel salaryLabel=new JLabel("Salary: ");
	private JLabel conversionLabel=new JLabel("Conversion: ");

	
	private JTextField countryTF= new JTextField(10);
	private JTextField salaryTF=new JTextField(10);
	private JTextField conversionTF=new JTextField(10);
	
				
	private static QueryTable3Model Table3Model = new QueryTable3Model();
	
	//Add the models to JTabels
	private JTable Table3ofDBContents=new JTable(Table3Model);
	
	//Buttons for inserting, and updating members
	//also a clear button to clear details panel
	private JButton updateButton = new JButton("Update");
	private JButton insertButton = new JButton("Insert");
	private JButton exportButton  = new JButton("Export");
	private JButton deleteButton  = new JButton("Delete");
	private JButton clearButton  = new JButton("Clear");

	private JButton fullPriceButton  = new JButton("Price of all Smart Home Items in: ");
	private JTextField fullPriceTF  = new JTextField(12);
	private JButton averagePricesButton  = new JButton("Average Prices for each item per country: ");
	private JTextField averagePricesTF  = new JTextField(12);
	private JButton salaryPercButton  = new JButton("Percentage of the salary for all items: ");
	private JTextField salaryPercTF  = new JTextField(12);
	private JButton salariesEuros  = new JButton("Salaries in Euros");
	private JButton percChart  = new JButton("Chart");
	


	public JDBCThirdWindowContent( String aTitle)
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
			
		detailsPanel.add(countryLabel);			
		detailsPanel.add(countryTF);
		detailsPanel.add(salaryLabel);		
		detailsPanel.add(salaryTF);
		detailsPanel.add(conversionLabel);	
		detailsPanel.add(conversionTF);
		
		//setup details panel and add the components to it
		exportButtonPanel=new JPanel();
		exportButtonPanel.setLayout(new GridLayout(4,3));
		exportButtonPanel.setBackground(Color.lightGray);
		exportButtonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Export Data"));
		exportButtonPanel.add(fullPriceButton);
		exportButtonPanel.add(fullPriceTF);
		exportButtonPanel.add(averagePricesButton);
		exportButtonPanel.add(averagePricesTF);
		exportButtonPanel.add(salaryPercButton);
		exportButtonPanel.add(salaryPercTF);
		exportButtonPanel.add(salariesEuros);
		exportButtonPanel.add(percChart);
		exportButtonPanel.setSize(1150, 200);
		exportButtonPanel.setLocation(4, 300);
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
		fullPriceButton.addActionListener(this);
		averagePricesButton.addActionListener(this);
		salaryPercButton.addActionListener(this);
		salariesEuros.addActionListener(this);
		percChart.addActionListener(this);

		
		content.add(insertButton);
		content.add(updateButton);
		content.add(exportButton);
		content.add(deleteButton);
		content.add(clearButton);

				
		Table3ofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));
	
		dbContentsPanel=new JScrollPane(Table3ofDBContents,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
	
		Table3Model.refreshFromDB(stmt);
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
			 countryTF.setText("");
			 salaryTF.setText("");
			 conversionTF.setText("");
		 }
		
		 if (target == insertButton)
		 {		 
	 		try
	 		{
 				String updateTemp ="INSERT INTO AverageSalary VALUES ('"+
 		 				  countryTF.getText()+"','"+salaryTF.getText()+"','"+conversionTF.getText()+"');";
 						
 				stmt.executeUpdate(updateTemp);
 			
	 		}
	 		catch (SQLException sqle)
	 		{
	 			System.err.println("Error with members insert:\n"+sqle.toString());
	 		}
	 		finally
	 		{
	 			Table3Model.refreshFromDB(stmt);
			}
		 }
		 if (target == deleteButton)
		 {
		 	
	 		try
	 		{
 				String updateTemp ="DELETE FROM AverageSalary WHERE country = '"+countryTF.getText()+"';"; 
 				stmt.executeUpdate(updateTemp);
 			
	 		}
	 		catch (SQLException sqle)
	 		{
	 			System.err.println("Error with delete:\n"+sqle.toString());
	 		}
	 		finally
	 		{
	 			Table3Model.refreshFromDB(stmt);
			}
		 }
		 if (target == updateButton)
		 {	 	
	 		try
	 		{ 			
 				String updateTemp ="UPDATE AverageSalary SET salary = '"+salaryTF.getText()+
 									"', conversionToEuro = '"+conversionTF.getText()+
 									"' WHERE  country = '"+countryTF.getText()+"';"; 				
 				
 				System.out.println(updateTemp);
 				stmt.executeUpdate(updateTemp);
 				//these lines do nothing but the table updates when we access the db.
 				rs = stmt.executeQuery("SELECT * from AverageSalary ");
 				rs.next();
 				rs.close();	
 			}
	 		catch (SQLException sqle){
	 			System.err.println("Error with members insert:\n"+sqle.toString());
	 		}
	 		finally{
	 			Table3Model.refreshFromDB(stmt);
			}
		 }		 	
		 if(target == exportButton) {
			 try (Statement statement = con.createStatement()) {
				 String exportFilePath = "averageSalariesTable.csv";
				 
				 try(FileWriter writer = new FileWriter(exportFilePath)){
					 writer.write("Country, Salary, Conversion\n");
					 String selectQuery = "SELECT * FROM AverageSalary;";
					 ResultSet resultSet = statement.executeQuery(selectQuery);
					 
					 while(resultSet.next()) {
						 String country = resultSet.getString("country");
						 double salary = resultSet.getDouble("salary");
						 double conv = resultSet.getDouble("conversionToEuro");
						
						 writer.write(country +", " + salary + ", " + conv + "\n");
					 }					
				 } catch (IOException ioException) {
					 ioException.printStackTrace();
			 }
		 } catch (SQLException sqlException) {
			 sqlException.printStackTrace();
	 			 
		 }
	}
		 if (target == fullPriceButton)
		 {
			    String query = "SELECT SUM(("+fullPriceTF.getText()+"_Min + "+fullPriceTF.getText()+"_Max) / 2) AS Total FROM ItemPrices";

		        try (Statement statement = con.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
		            if (resultSet.next()) {
		                // Get the result from the query
		                double total = resultSet.getDouble("Total");

		                // Specify the export file path
		                String exportFilePath = "exported_total.csv"; // Change this to the desired file path

		                try (FileWriter writer = new FileWriter(exportFilePath)) {
		                    // Write the result to the CSV file
		                    writer.write("Total for " + fullPriceTF.getText()+"\n");
		                    writer.write(String.valueOf(total) + "\n");
		                } catch (IOException ioException) {
		                    ioException.printStackTrace();
		                }
		            }
		        } catch (SQLException sqlException) {
		            sqlException.printStackTrace();
		        }
			  }
		 if(target == averagePricesButton) {
			 String query = "CREATE TABLE "+ averagePricesTF.getText() + "Prices AS SELECT (" + averagePricesTF.getText()+"_Min + " + averagePricesTF.getText()+"_Max)/2 AS " + averagePricesTF.getText()+"Prices FROM ItemPrices;";
			 
			 try(Statement statement = con.createStatement()){
				 int rowCount = statement.executeUpdate(query);
				 
				 if(rowCount>0) {
				 
				 String exportFilePath = "Country_prices.csv";
				 
				 try(FileWriter writer = new FileWriter(exportFilePath)){
					 writer.write("Average prices for " + averagePricesTF.getText()+"\n");
					 String selectQuery = "SELECT * FROM " + averagePricesTF.getText() + "Prices;";
					 ResultSet resultSet = statement.executeQuery(selectQuery);
					 
					 while(resultSet.next()) {
						 double price = resultSet.getDouble(averagePricesTF.getText() + "Prices");
						 writer.write(String.valueOf(price)+"\n");
					 }					
				 } catch (IOException ioException) {
					 ioException.printStackTrace();
				 }
			 }
		 } catch (SQLException sqlException) {
			 sqlException.printStackTrace();
		 	}
		 }
		 
		 if (target == salaryPercButton) {
			    try (Statement statement = con.createStatement()) {
			        // Specify the export file path
			        String exportFilePath = "percentage.csv"; // Change this to the desired file path

			        try (FileWriter writer = new FileWriter(exportFilePath)) {
			            writer.write("Country, Salary, Total_Item_Prices, Percentage\n"); // Header

			            String query = "SELECT DISTINCT country, salary AS " + salaryPercTF.getText() + "_Salary, " +
			                    "ROUND((SELECT SUM((" + salaryPercTF.getText() + "_Min + " + salaryPercTF.getText() + "_Max) / 2) " +
			                    "FROM ItemPrices), 2) AS Total_Item_Prices, " +
			                    "ROUND((SELECT SUM((" + salaryPercTF.getText() + "_Min + " + salaryPercTF.getText() + "_Max) / 2) " +
			                    "FROM ItemPrices) * 100 / salary, 2) AS Percentage FROM AverageSalary " +
			                    "WHERE country = '" + salaryPercTF.getText() + "';";
			            
			            ResultSet resultSet = statement.executeQuery(query);
			            
			            while (resultSet.next()) {
			                String country = resultSet.getString("country");
			                double percentage = resultSet.getDouble("Percentage");
			                double totalItemPrices = resultSet.getDouble("Total_Item_Prices");
			                double salary = resultSet.getDouble(salaryPercTF.getText() + "_Salary");

			                writer.write(country + "," + salary + "," + totalItemPrices + "," + percentage + "\n");
			            }
			        } catch (IOException ioException) {
			            ioException.printStackTrace();
			        }
			    } catch (SQLException sqlException) {
			        sqlException.printStackTrace();
			    }
			}
		 
		 if(target == salariesEuros) {
			 try(Statement statement = con.createStatement()) {
				 String exportFilePath = "salariesInEuros.csv";
				 try (FileWriter writer = new FileWriter(exportFilePath)) {
	                    // Write the result to the CSV file
	                    writer.write("Country, Salary, Conversion to Euros, Salaries in Euros\n");
	                    String query = "SELECT *, Round((salary * conversionToEuro),2) AS 'Salary In Euros' FROM AverageSalary;";
	                    ResultSet resultSet = statement.executeQuery(query);
	                    while (resultSet.next()) {
	                    	String country = resultSet.getString("country");
	                    	double salary = resultSet.getDouble("salary");
	                    	double conversion = resultSet.getDouble("conversionToEuro");
			                double salaries = resultSet.getDouble("Salary In Euros");
			                writer.write(country + "," + salary + "," + conversion + "," + salaries + "\n");
	                    }

	              }catch (IOException ioException) {
			            ioException.printStackTrace();
			        }
			    } catch (SQLException sqlException) {
			        sqlException.printStackTrace();
			    }
			}
		 if (target.equals(percChart)){
			 try(Statement statement = con.createStatement()) {
				String query = "SELECT A.country, ROUND((P.totalPrices * 100 / A.salary), 2) AS Percentage FROM AverageSalary A\r\n"
						+ "JOIN (\r\n"
						+ "    SELECT 'Ireland' AS country, SUM(Ireland) AS totalPrices FROM AveragePrices\r\n"
						+ "    UNION ALL\r\n"
						+ "    SELECT 'Australia' AS country,SUM(Australia) AS totalPrices FROM AveragePrices\r\n"
						+ "    UNION ALL\r\n"
						+ "    SELECT 'Brazil' AS country, SUM(Brazil) AS totalPrices FROM AveragePrices\r\n"
						+ "    UNION ALL\r\n"
						+ "    SELECT 'Egypt' AS country, SUM(Egypt) AS totalPrices FROM AveragePrices\r\n"
						+ "    UNION ALL\r\n"
						+ "    SELECT 'Germany' AS country, SUM(Germany) AS totalPrices FROM AveragePrices\r\n"
						+ "    UNION ALL\r\n"
						+ "    SELECT 'Japan' AS country, SUM(Japan) AS totalPrices FROM AveragePrices\r\n"
						+ "    UNION ALL\r\n"
						+ "    SELECT 'UK' AS country, SUM(UK) AS totalPrices FROM AveragePrices\r\n"
						+ "    UNION ALL\r\n"
						+ "    SELECT 'USA' AS country, SUM(USA) AS totalPrices FROM AveragePrices\r\n"
						+ ") P\r\n"
						+ "ON A.country = P.country;";
				ResultSet resultSet = statement.executeQuery(query);
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	            while (resultSet.next()) {
	                String country = resultSet.getString("country");
	                double percentage = resultSet.getDouble("Percentage");
	                dataset.addValue(percentage, "Percentage", country);
	            }

	            JFreeChart chart = ChartFactory.createBarChart(
	                    "Percentage of Cost of all Items in relation to Salaries by Country",
	                    "Country",
	                    "Percentage ",
	                    dataset,
	                    PlotOrientation.VERTICAL,
	                    true,
	                    true,
	                    false
	            );

	            ChartFrame frame = new ChartFrame("Percentage of Cost of all Items in relation to Salaries by Country", chart);
	            chart.setBackgroundPaint(Color.WHITE);
	            frame.pack();
	            frame.setVisible(true);
	        } catch (SQLException sqlException) {
	            sqlException.printStackTrace();
	        }		
			}

		 }
}

