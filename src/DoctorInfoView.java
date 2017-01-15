import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class DoctorInfoView extends JFrame
{

	static Connection cn=null;
	static Connection cn2=null;
	Statement st=null;
	Statement st2=null;
	ResultSet rs=null;
	ResultSet rs2=null;

	JLabel lmain,lname,ladd,ltel,lspecial,ldid,ldspec,lwork,lworkfrom,lworkto,lpatlist;
	JTextField tfname,tftel,tfdid,tfworkf,tfworkt;
	TextArea taadd,taspecial,tapatlist;
	JButton bsub,bclr,bback;

	DoctorInfoView()
	{
		super("Doctor Information");

		setSize(1000, 750);
		setLocationRelativeTo(null);
		setLayout(null);

		lmain=new JLabel("Doctor Information");
		lmain.setFont(new Font("Arial", Font.BOLD, 20));
		lmain.setBounds(370,35,200,20);
		add(lmain);

		lname=new JLabel("Name :");
		lname.setBounds(104,97,70,25);
		add(lname);

		tfname=new JTextField(30);
		tfname.setBounds(270,97,250,20);
		add(tfname);

		ladd=new JLabel("Address :");
		ladd.setBounds(104,138,70,15);
		add(ladd);

		taadd=new TextArea();
		taadd.setBounds(270,138,250,100);
		add(taadd);

		ltel=new JLabel("Contact :");
		ltel.setBounds(570,138,50,25);
		add(ltel);

		ldid=new JLabel("Doctor ID:");
		ldid.setBounds(570,97,70,25);
		add(ldid);

		tfdid=new JTextField(30);
		tfdid.setBounds(643,97,50,20);
		add(tfdid);

		tftel=new JTextField(30);
		tftel.setBounds(640,138,200,20);
		add(tftel);

		ldspec=new JLabel("Specialization :");
		ldspec.setBounds(104,310,100,25);
		add(ldspec);

		taspecial=new TextArea();
		taspecial.setBounds(270,310,250,100);
		add(taspecial);

		lwork=new JLabel("Working hours :");
		lwork.setBounds(570,200,100,15);
		add(lwork);

		lworkfrom=new JLabel("From :");
		lworkfrom.setBounds(670,200,37,25);
		add(lworkfrom);

		tfworkf=new JTextField(30);
		tfworkf.setBounds(710,200,30,20);
		add(tfworkf);

		lworkto=new JLabel("To :");
		lworkto.setBounds(747,200,20,25);
		add(lworkto);

		tfworkt=new JTextField(30);
		tfworkt.setBounds(775,200,30,20);
		add(tfworkt);

		lpatlist=new JLabel("Patient List");
		lpatlist.setBounds(570,285,80,25);
		add(lpatlist);

		tapatlist=new TextArea();
		tapatlist.setBounds(570,310,250,100);
		add(tapatlist);

		bsub=new JButton("SEARCH",new ImageIcon("images/search.png"));
		bsub.setBounds(290,643,100,30);
		add(bsub);	

		bclr=new JButton("CLEAR",new ImageIcon("images/LOGGOFF.PNG"));
		bclr.setBounds(400,643,100,30);
		add(bclr);

		bback=new JButton("BACK",new ImageIcon("images/restore.png"));
		bback.setBounds(510,643,100,30);
		add(bback);

		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			cn=DriverManager.getConnection("Jdbc:Odbc:doc");

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			cn2=DriverManager.getConnection("Jdbc:Odbc:pat");
		}

		catch(Exception e)
		{
			System.out.println(e);
		}	


		bclr.addActionListener(new clear());
		bsub.addActionListener(new submit());
		bback.addActionListener(new back());

		setVisible(true);
	}

	class clear implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{

			tfname.setText("");
			tftel.setText("");
			tfdid.setText("");
			tfworkf.setText("");
			tfworkt.setText("");
			taadd.setText("");
			taspecial.setText("");
			tapatlist.setText("");
		}
	}


	class back implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			new docStart();
			setVisible(false);
		}
	}

	class submit implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{			
			try
			{
				tapatlist.setText("");			
				Integer num=Integer.parseInt(tfdid.getText());
				String name;
				String addr;
				String contact;
				String spec;
				String workf;
				String workt;

				Statement st=cn.createStatement();
				ResultSet rs=st.executeQuery("SELECT * FROM DOC WHERE did="+num);

				if(rs.next())
				{
					num=rs.getInt("did");
					name=rs.getString("name");
					addr=rs.getString("address");
					contact=rs.getString("contact");
					spec=rs.getString("specialization");
					workf=rs.getString("workfrom");
					workt=rs.getString("workto");

					tfname.setText(name);
					taadd.setText(addr);
					tftel.setText(contact);
					taspecial.setText(spec);
					tfworkf.setText(workf);
					tfworkt.setText(workt);
				}
			}

			catch(SQLException sq)
			{
				System.out.println(sq);
			}

			try{

				String docname=tfname.getText();
				System.out.println(docname);
				Statement st2=cn2.createStatement();
				ResultSet rs2=st2.executeQuery("SELECT patientno,name FROM PAT WHERE docname='"+docname+"'");
				ResultSetMetaData rsmt=rs2.getMetaData();
				int ctr=rsmt.getColumnCount();
				while(rs2.next())
				{
					for(int i=1;i<=ctr;i++)
					{
						tapatlist.append(rs2.getString(i)+"  ");
					}
					tapatlist.append("\n");
				}
			}
			catch(SQLException sq)
			{
				System.out.println(sq);
			}

		}

	}
}