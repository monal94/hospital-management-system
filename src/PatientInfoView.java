import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class PatientInfoView extends JFrame
{

	static Connection cn=null;
	Statement st=null;
	ResultSet rs=null;

	JLabel lmain,lpi,lname,ladd,ltel,lmi,lbg,ldob,lhis,lcur,lpno,lroom,ldateadd,lgender,lrtype,ldtip,ldtip2,ldocname;
	JTextField tfname,tftel,tfpno,tfbg,tfroom,tfdateadd,tfmf,tfrtype,tfdocname,tfdob;
	TextArea taadd,tahis,tacur;
	JButton bsub,bclr,bback;

	PatientInfoView()
	{
		super("View Patient Information");
	
		setSize(1100, 750);
		setLocationRelativeTo(null);
		setLayout(null);

		// PERSONAL INFORMATION

		lmain=new JLabel("View Patient Information");
		lmain.setFont(new Font("Arial", Font.BOLD, 15));
		lmain.setBounds(425,35,200,15);
		add(lmain);

		lpi=new JLabel("Personal Information");
		lpi.setBounds(40,70,120,15);
		add(lpi);

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
		ltel.setBounds(575,138,50,25);
		add(ltel);

		lpno=new JLabel("Patient No.:");
		lpno.setBounds(575,97,70,25);
		add(lpno);

		tftel=new JTextField(30);
		tftel.setBounds(720,138,250,20);
		add(tftel);

		tfpno=new JTextField(30);
		tfpno.setBounds(720,97,50,20);
		add(tfpno);

		lmi=new JLabel("Medical Information");
		lmi.setBounds(40,268,120,15);
		add(lmi);

		lbg=new JLabel("Blood Group :");
		lbg.setBounds(104,306,79,15);
		add(lbg);

		tfbg=new JTextField(30);
		tfbg.setBounds(270,306,53,20);
		add(tfbg);

		ldob=new JLabel("Date of Birth :");
		ldob.setBounds(575,306,135,15);
		add(ldob);

		lhis=new JLabel("History :");
		lhis.setBounds(104,365,50,15);
		add(lhis);

		tahis=new TextArea();
		tahis.setBounds(270,365,250,100);
		add(tahis);

		lcur=new JLabel("Current Problem :");
		lcur.setBounds(575,365,100,15);
		add(lcur);

		tacur=new TextArea();
		tacur.setBounds(720,365,250,100);
		add(tacur);		

		lroom=new JLabel("Room No.:");
		lroom.setBounds(790,97,60,20);
		add(lroom);

		tfroom=new JTextField(30);
		tfroom.setBounds(850,97,60,20);
		add(tfroom);

		ldateadd=new JLabel("Date Of Admission :");
		ldateadd.setBounds(575,180,120,25);
		add(ldateadd);

		tfdateadd=new JTextField(40);
		tfdateadd.setBounds(720,180,80,20);
		add(tfdateadd);

		lgender=new JLabel("Gender :");
		lgender.setBounds(575,223,50,15);
		add(lgender);

		tfmf=new JTextField(50);
		tfmf.setBounds(720,223,80,20);
		add(tfmf);

		lrtype=new JLabel("Type Of Room : ");
		lrtype.setBounds(104,510,120,25);
		add(lrtype);

		tfrtype=new JTextField();
		tfrtype.setBounds(270,510,80,20);
		add(tfrtype);

		bsub=new JButton("SEARCH",new ImageIcon("images/search.png"));
		bsub.setBounds(360,643,100,30);
		add(bsub);	

		bclr=new JButton("CLEAR",new ImageIcon("images/LOGGOFF.PNG"));
		bclr.setBounds(470,643,100,30);
		add(bclr);

		bback=new JButton("BACK",new ImageIcon("images/restore.png"));
		bback.setBounds(580,643,100,30);
		add(bback);

		ldtip2=new JLabel("(dd-mm-yyyy)");
		ldtip2.setBounds(820,180,100,20);
		add(ldtip2);

		tfdocname=new JTextField(100);
		tfdocname.setBounds(720,510,250,20);
		add(tfdocname);

		ldocname=new JLabel("Attending Doctor :");
		ldocname.setBounds(575,510,130,15);
		add(ldocname);

		ldtip=new JLabel("(dd-mm-yyyy)");
		ldtip.setBounds(820,305,100,20);
		add(ldtip);

		ldob=new JLabel("Date of Birth :");
		ldob.setBounds(575,306,120,15);
		add(ldob);

		tfdob=new JTextField(15);
		tfdob.setBounds(720,305,80,20);
		add(tfdob);

		try
		{

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			cn=DriverManager.getConnection("Jdbc:Odbc:pat");
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
			tfdob.setText("");
			taadd.setText("");
			tahis.setText("");
			tacur.setText("");
			tfpno.setText("");
			tfroom.setText("");
			tfdateadd.setText("");
			tfrtype.setText("");
			tfmf.setText("");
			tfbg.setText("");
		}
	}

	class back implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			new patStart();
			setVisible(false);
		}
	}


	class submit extends Frame implements ActionListener
	{
		Integer num,no=0;
		String name,addr,contact,blgr,hist,dob,current,room,dateadd,rtype,mf,docname;

		public void actionPerformed(ActionEvent ae)
		{			
			try
			{
				num=Integer.parseInt(tfpno.getText());

				Statement st=cn.createStatement();
				ResultSet rs=st.executeQuery("SELECT * FROM PAT WHERE patientno="+num);
				System.out.println(num);

				if(rs.next())
				{

					no=rs.getInt("patientno");
					name=rs.getString("name");
					addr=rs.getString("address");
					contact=rs.getString("contact");
					hist=rs.getString("history");
					dob=rs.getString("dob");
					current=rs.getString("current");
					blgr=rs.getString("bloodgroup");
					room=rs.getString("roomno");
					dateadd=rs.getString("dateadd");
					rtype=rs.getString("rtype");
					mf=rs.getString("gender");
					docname=rs.getString("docname");

					tfname.setText(name);
					tftel.setText(contact);
					tfdob.setText(dob);
					taadd.setText(addr);
					tahis.setText(hist);
					tacur.setText(current);
					tfbg.setText(blgr);
					tfroom.setText(room);
					tfdateadd.setText(dateadd);
					tfrtype.setText(rtype);
					tfmf.setText(mf);
					tfdocname.setText(docname);
				}

			}
			catch(SQLException sq)
			{
				System.out.println(sq);
			}


		}
	}

}
