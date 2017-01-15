import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

class DoctorTableFromDatabase extends JFrame
{
	static Connection cn=null;

	DoctorTableFromDatabase()
	{
		Vector columnNames = new Vector();
		Vector data = new Vector();

		try
		{
			String driver = "sun.jdbc.odbc.JdbcOdbcDriver";

			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				cn=DriverManager.getConnection("Jdbc:Odbc:doc");
			}

			catch(Exception e)
			{
				System.out.println(e);
			}	


			//  Read data from a table

			String sql = "Select * from DOC";
			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			//  Get column names

			for (int i = 1; i <= columns; i++)
			{
				columnNames.addElement(md.getColumnName(i));
			}

			//  Get row data

			while (rs.next())
			{
				Vector row = new Vector(columns);

				for (int i = 1; i <= columns; i++)
				{
					row.addElement(rs.getObject(i)); 

				}

				data.addElement( row );
			}

		}
		catch(Exception e)
		{
			System.out.println( e );
		}

		//  Create table with database data

		JTable table = new JTable(data, columnNames);

		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add( scrollPane );

		JPanel buttonPanel = new JPanel();
		getContentPane().add( buttonPanel, BorderLayout.SOUTH );
	}
}

