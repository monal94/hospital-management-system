import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.text.*;

class NameEx extends Exception{}

class BlankException extends Exception{}

class PatientInfo extends JFrame
{

	static Connection cn=null;
	Statement st=null;
	ResultSet rs=null;

	JLabel lmain,lpi,lname,ladd,ltel,lmi,lbg,ldob,lhis,lcur,lpno,lroom,ldateadd,lgender,lrtype,ldtip,ldtip2,ldocname;
	JTextField tfname,tftel,tfdob,tfpno,tfroom,tfdateadd,tfdocname;
	TextArea taadd,tahis,tacur;
	Choice chbg,chrt;
	CheckboxGroup cbmf;
	Checkbox cbm,cbf;
	JButton bsub,bclr,bback;

	String dialogmessage;
	String dialogs;
	int dialogtype = JOptionPane.PLAIN_MESSAGE;
	clsSettings settings = new clsSettings();

	PatientInfo()
	{
		super("Add Patient Information");
		setSize(1100, 750);		
		setLocationRelativeTo(null);
		setLayout(null);

		// PERSONAL INFORMATION

		lmain=new JLabel("Add Patient Information");
		lmain.setFont(new Font("Arial", Font.BOLD, 15));
		lmain.setBounds(425,35,200,15);
		add(lmain);

		lpi=new JLabel("Add Personal Information");
		lpi.setBounds(40,70,150,15);
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
		settings.Numvalidator(tftel);

		tfpno=new JTextField(30);
		tfpno.setBounds(720,97,50,20);
		add(tfpno);

		lroom=new JLabel("Room No.:");
		lroom.setBounds(790,97,60,20);
		add(lroom);

		tfroom=new JTextField(30);
		tfroom.setBounds(850,97,60,20);
		add(tfroom);

		lmi=new JLabel("Medical Information");
		lmi.setBounds(40,268,120,15);
		add(lmi);

		lbg=new JLabel("Blood Group :");
		lbg.setBounds(104,306,79,15);
		add(lbg);

		chbg=new Choice();
		chbg.setBounds(270,306,53,15);
		chbg.addItem("A -ve");
		chbg.addItem("A +ve");
		chbg.addItem("B -ve");
		chbg.addItem("B +ve");
		chbg.addItem("AB -ve");
		chbg.addItem("AB +ve");
		chbg.addItem("O +ve");
		chbg.addItem("O -ve");
		add(chbg);

		ldob=new JLabel("Date of Birth :");
		ldob.setBounds(575,306,120,15);
		add(ldob);

		tfdob=new JTextField(15);
		tfdob.setBounds(720,305,80,20);
		add(tfdob);

		ldtip=new JLabel("(dd-mm-yyyy)");
		ldtip.setBounds(820,305,100,20);
		add(ldtip);

		lhis=new JLabel("History :");
		lhis.setBounds(104,365,50,15);
		add(lhis);

		tahis=new TextArea();
		tahis.setBounds(270,365,250,100);
		add(tahis);

		lcur=new JLabel("Current Problem :");
		lcur.setBounds(575,365,100,15);
		add(lcur);

		ldocname=new JLabel("Attending Doctor :");
		ldocname.setBounds(575,510,130,15);
		add(ldocname);

		tacur=new TextArea();
		tacur.setBounds(720,365,250,100);
		add(tacur);		

		ldateadd=new JLabel("Date Of Admission :");
		ldateadd.setBounds(575,180,120,25);
		add(ldateadd);

		tfdateadd=new JTextField(40);
		tfdateadd.setBounds(720,180,80,20);
		add(tfdateadd);

		tfdocname=new JTextField(100);
		tfdocname.setBounds(720,510,250,20);
		add(tfdocname);

		ldtip2=new JLabel("(dd-mm-yyyy)");
		ldtip2.setBounds(820,180,100,20);
		add(ldtip2);

		bsub=new JButton("ADD",new ImageIcon("images/add.gif"));
		bsub.setBounds(362,643,100,30);
		add(bsub);	

		bclr=new JButton("CLEAR",new ImageIcon("images/LOGGOFF.PNG"));
		bclr.setBounds(470,643,100,30);
		add(bclr);

		bback=new JButton("BACK",new ImageIcon("images/restore.png"));
		bback.setBounds(580,643,100,30);
		add(bback);

		lgender=new JLabel("Gender :");
		lgender.setBounds(575,223,50,15);
		add(lgender);

		cbmf=new CheckboxGroup();
		cbm=new Checkbox("Male",cbmf,true);
		cbf=new Checkbox("Female",cbmf,false);
		cbm.setBounds(720,223,50,15);
		add(cbm);
		cbf.setBounds(780,223,60,15);
		add(cbf);

		lrtype=new JLabel("Type Of Room : ");
		lrtype.setBounds(104,510,120,25);
		add(lrtype);

		chrt=new Choice();
		chrt.setBounds(270,510,80,15);
		chrt.addItem("Deluxe");
		chrt.addItem("Private");
		chrt.addItem("Semi-Private");
		chrt.addItem("General");
		add(chrt);

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

		Calendar cal=Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println(df.format(cal.getTime()));

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
			tfdocname.setText("");
			tfdateadd.setText("");
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


	class submit implements ActionListener, ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
		}
		public void actionPerformed(ActionEvent ae)
		{			
			try{

				Integer count=0;


				Integer num=Integer.parseInt(tfpno.getText());
				if(num.equals(null))
				{
					System.out.println("num");
					throw new BlankException();
				}


				String name=tfname.getText();
				int a;
				a=name.charAt(0);
				if(name.equals("") || a==32)
				{
					System.out.println("name=="+name);
					throw new BlankException();
				}
				else
				{
					for(int i=0; i<name.length(); i++)
					{
						boolean check = Character.isLetter(name.charAt(i));
						a=name.charAt(i);
						System.out.print("  "+a);
						if(!((a>=65 && a<=90) || (a>=97 && a<=122) || (a==32) ||(a==46)))
						{
							throw new NameEx();
						}

					}
				}

				String addr=taadd.getText();
				if(addr.equals(null))
				{
					System.out.println("addr");
					throw new BlankException();
				}

				Long contact=Long.parseLong(tftel.getText());

				String blgr=chbg.getSelectedItem();

				String hist=tahis.getText();

				String dob=tfdob.getText();
				if(dob.equals(null))
				{
					System.out.println("dob");
					throw new BlankException();
				}

				String current=tacur.getText();
				if(current.equals(null))
				{
					System.out.println("current");
					throw new BlankException();
				}

				String room=tfroom.getText();

				String dateadd=tfdateadd.getText();
				if(dateadd.equals(null))
				{
					System.out.println("dateadd");
					throw new BlankException();
				}

				String docname=tfdocname.getText();
				if(docname.equals(null))
				{
					System.out.println("docname");
					throw new BlankException();
				}

				Calendar cal=Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				df.setLenient(false);  

				java.util.Date myDate = df.parse(dateadd);

				System.out.println(dateadd);

				String rtype=chrt.getSelectedItem();

				String gender="";

				if(cbm.getState()==true)
				{
					gender="male";
				}
				if(cbf.getState()==true)
				{
					gender="female";
				}

				Statement st=cn.createStatement();	

				st.executeUpdate("INSERT INTO PAT VALUES('"+num+"','"+name+"','"+addr+"','"+contact+"','"+blgr+"','"+hist+"','"+dob+"','"+current+"','"+room+"','"+dateadd+"','"+rtype+"','"+gender+"','"+docname+"');");

				JOptionPane.showMessageDialog(new JFrame(), "Data added successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);


			}
			catch (ParseException e)
			{
				JOptionPane.showMessageDialog(new JFrame(), "Enter Valid Date", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(BlankException be)
			{
				JOptionPane.showMessageDialog(new JFrame(), "Please Enter All The Fields", "ERROR!", JOptionPane.ERROR_MESSAGE);
			}
			catch(NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(new JFrame(), "Patient Number and Contact Number Must Contain Digits.", "ERROR!", JOptionPane.ERROR_MESSAGE);
			}
			catch(SQLException sq)
			{
				System.out.println(sq);
			}
			catch(NameEx ne)
			{
				JOptionPane.showMessageDialog(new JFrame(), "Invalid Name", "ERROR!", JOptionPane.ERROR_MESSAGE);
			}
			catch(Exception e)
			{
				System.out.println(e);
				JOptionPane.showMessageDialog(new JFrame(), "Enter Valid Date", "Error", JOptionPane.ERROR_MESSAGE);
			}


		}
	}

}