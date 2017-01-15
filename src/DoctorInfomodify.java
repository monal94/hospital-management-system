import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class DoctorInfomodify extends JFrame
{

	static Connection cn=null;
	Statement st=null;
	ResultSet rs=null;

	JLabel lmain,lname,ladd,ltel,lspecial,ldid,ldspec,lwork,lworkfrom,lworkto;
	JTextField tfname,tftel,tfdid,tfworkf,tfworkt;
	TextArea taadd,taspecial;
	JButton bsub,bclr,bmod,bback;

	int x,y;
	String str;
	clsSettings settings = new clsSettings();

	DoctorInfomodify()
	{
		super("Doctor Information");

		setSize(1000, 750);
		setLocationRelativeTo(null);
		setLayout(null);

		lmain=new JLabel("Doctor Information");
		lmain.setBounds(380,35,200,20);
		lmain.setFont(new Font("Arial", Font.BOLD, 20));
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
		settings.Numvalidator(tftel);

		ldspec=new JLabel("Specialization :");
		ldspec.setBounds(104,310,100,25);
		add(ldspec);

		taspecial=new TextArea();
		taspecial.setBounds(270,310,250,100);
		add(taspecial);

		lwork=new JLabel("Working hours :");
		lwork.setBounds(570,310,100,15);
		add(lwork);

		lworkfrom=new JLabel("From :");
		lworkfrom.setBounds(670,305,37,25);
		add(lworkfrom);

		tfworkf=new JTextField(30);
		tfworkf.setBounds(710,310,30,20);
		add(tfworkf);
		settings.Numvalidator(tfworkf);

		lworkto=new JLabel("To :");
		lworkto.setBounds(747,305,20,25);
		add(lworkto);

		tfworkt=new JTextField(30);
		tfworkt.setBounds(775,310,30,20);
		add(tfworkt);
		settings.Numvalidator(tfworkt);

		bsub=new JButton("SEARCH",new ImageIcon("images/search.png"));
		bsub.setBounds(230,643,100,30);
		add(bsub);	

		bclr=new JButton("CLEAR",new ImageIcon("images/LOGGOFF.PNG"));
		bclr.setBounds(360,643,100,30);
		add(bclr);

		bmod=new JButton("MODIFY",new ImageIcon("images/modify.png"));
		bmod.setBounds(490,643,100,30);
		add(bmod);

		bback=new JButton("BACK",new ImageIcon("images/restore.png"));
		bback.setBounds(620,643,100,30);
		add(bback);


		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			cn=DriverManager.getConnection("Jdbc:Odbc:doc");
		}

		catch(Exception e)
		{
			System.out.println(e);
		}	

		bclr.addActionListener(new clear());
		bsub.addActionListener(new submit());
		bmod.addActionListener(new modify());
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

		}

	}


	class modify implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			try{

				Integer num1=Integer.parseInt(tfdid.getText());
				if(num1.equals(null))
				{
					System.out.println("num");
					throw new BlankException();
				}


				String name1=tfname.getText();
				int a;
				a=name1.charAt(0);
				if(name1.equals("") || a==32)
				{
					throw new BlankException();
				}
				else
				{
					for(int i=0; i<name1.length(); i++)
					{
						boolean check = Character.isLetter(name1.charAt(i));
						a=name1.charAt(i);
						System.out.print("  "+a);
						if(!((a>=65 && a<=90) || (a>=97 && a<=122) || (a==32) ||(a==46)))
						{
							throw new NameEx();
						}

					}
				}


				String addr1=taadd.getText();
				if(addr1.equals(null))
				{
					System.out.println("addr");
					throw new BlankException();
				}

				String contact1=tftel.getText();


				String spec1=taspecial.getText();
				String workf1=tfworkf.getText();
				String workt1=tfworkt.getText();

				String str="UPDATE DOC SET name=?,address=?,contact=?,specialization=?,workfrom=?,workto=? WHERE did=?";

				Statement st1= cn.createStatement();

				PreparedStatement psmt=cn.prepareStatement(str);
				psmt.setString(1,name1);
				psmt.setString(2,addr1);
				psmt.setString(3,contact1);
				psmt.setString(4,spec1);
				psmt.setString(5,workf1);
				psmt.setString(6,workt1);
				psmt.setInt(7,num1);

				psmt.executeUpdate();

				JOptionPane.showMessageDialog(new JFrame(), "Data Modified successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);


			}catch(SQLException sq)
			{
				String message = "Enter Valid Doctor ID and Contact.";
				JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!",
						JOptionPane.ERROR_MESSAGE);
				System.out.println(sq);
			}
			catch(BlankException be)
			{
				JOptionPane.showMessageDialog(new JFrame(), "Please Enter All The Fields", "ERROR!", JOptionPane.ERROR_MESSAGE);
			}
			catch(NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(new JFrame(), "Patient Number and Contact Number Must Contain Digits.", "ERROR!", JOptionPane.ERROR_MESSAGE);
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