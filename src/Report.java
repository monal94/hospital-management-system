import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class Report extends JFrame implements ActionListener
{
	static Connection cn=null;
	static Connection cn2=null;
	Statement st=null;
	Statement st2=null;
	ResultSet rs=null;
	ResultSet rs2=null;

	JButton bpat,bdoc,bback;
	JLabel lpat,ldoc;

	Report()
	{
		super("Reports");
		
		setSize(800,640);
		setLocationRelativeTo(null);	
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		lpat=new JLabel("For Patients Information, Click Here :");
		lpat.setFont(new Font("Arial", Font.PLAIN, 15));
		lpat.setBounds(100,200,400,30);
		add(lpat);

		ldoc=new JLabel("For Doctors Information, Click Here :");
		ldoc.setFont(new Font("Arial", Font.PLAIN, 15));
		ldoc.setBounds(100,350,400,30);
		add(ldoc);

		bpat=new JButton("Display Patient's Report",new ImageIcon("images/emp.png"));
		bpat.setBounds(400,200,250,30);
		add(bpat);

		bdoc=new JButton("Display Doctor's Report",new ImageIcon("images/users.png"));
		bdoc.setBounds(400,350,250,30);
		add(bdoc);

		bback=new JButton("BACK",new ImageIcon("images/restore.png"));
		bback.setBounds(340,450,100,30);
		add(bback);

		setVisible(true);

		bpat.addActionListener(new patreport());
		bdoc.addActionListener(new docreport());
		bback.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource()==bback)
		{
			new start();
			setVisible(false);
		}

	}

	class patreport implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			PatientTableFromDatabase frame=new PatientTableFromDatabase();
			frame.setSize(1024,320);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}

	}

	class docreport implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			DoctorTableFromDatabase frame=new DoctorTableFromDatabase();
			frame.setSize(1024,320);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}

}