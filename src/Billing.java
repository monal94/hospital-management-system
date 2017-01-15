import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.text.*;


class Billing extends JFrame
{	
	static Connection cn=null;
	Statement st=null;
	ResultSet rs=null;

	JLabel lmain,lpname,lpno,ldad,lddis,lrt,ltamt,temp;
	JTextField tfname,tfno,tfdateadd,tfrtype,tftamt;
	JButton bsub,bclr,bback;

	Billing()
	{
		super("Billing Information");

		setSize(800,640);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		lmain=new JLabel("Billing Information");
		lmain.setFont(new Font("Arial", Font.BOLD, 40));
		lmain.setBounds(215,10,640,80);
		add(lmain);

		lpname=new JLabel("Patient Name :");
		lpname.setFont(new Font("Arial", Font.PLAIN, 15));
		lpname.setBounds(95,97,100,35);
		add(lpname);

		tfname=new JTextField(30);
		tfname.setBounds(230,100,225,20);
		add(tfname);

		lpno=new JLabel("Patient No. :");
		lpno.setFont(new Font("Arial", Font.PLAIN, 15));
		lpno.setBounds(560,97,100,35);
		add(lpno);

		tfno=new JTextField(50);
		tfno.setBounds(670,100,100,20);
		add(tfno);

		ldad=new JLabel("Date of Admission :");
		ldad.setFont(new Font("Arial", Font.PLAIN, 15));
		ldad.setBounds(95,175,130,25);
		add(ldad);

		tfdateadd=new JTextField(20);
		tfdateadd.setBounds(230,178,80,20);
		add(tfdateadd);

		lddis=new JLabel("Date of Discharge :");
		lddis.setFont(new Font("Arial", Font.PLAIN, 15));
		lddis.setBounds(560,175,130,25);
		add(lddis);

		lrt=new JLabel("Room Type :");
		lrt.setFont(new Font("Arial", Font.PLAIN, 15));
		lrt.setBounds(95,242,100,25);
		add(lrt);

		tfrtype=new JTextField(20);
		tfrtype.setBounds(230,242,80,20);
		add(tfrtype);

		ltamt=new JLabel("Total Amount :");
		ltamt.setFont(new Font("Arial", Font.PLAIN, 15));
		ltamt.setBounds(95,380,100,25);
		add(ltamt);

		tftamt=new JTextField(20);
		tftamt.setBounds(230,380,120,20);
		add(tftamt);

		bsub=new JButton("SEARCH" ,new ImageIcon("images/setting.png"));
		bsub.setBounds(95,443,110,30);
		add(bsub);	

		bclr=new JButton("CLEAR",new ImageIcon("images/LOGGOFF.PNG"));
		bclr.setBounds(300,443,100,30);
		add(bclr);

		bback=new JButton("BACK",new ImageIcon("images/restore.png"));
		bback.setBounds(410,443,100,30);
		add(bback);

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


		try
		{
			Calendar cal=Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			df.setLenient(false);  
			System.out.println(df.format(cal.getTime()));
			String dd1=df.format(cal.getTime());


			temp=new JLabel(dd1);
			temp.setBounds(694,178,80,20);
			add(temp);

		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame(), "Please Enter All The Fields", "ERROR!", JOptionPane.ERROR_MESSAGE);
		}

		setVisible(true);

	}

	class clear implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			tfname.setText("");
			tfno.setText("");
			tfdateadd.setText("");
			tfrtype.setText("");
			tftamt.setText("");
		}
	}

	class back implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			new start();
			setVisible(false);
		}
	}


	class submit extends Frame implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{	
			try
			{
				Integer no,num=Integer.parseInt(tfno.getText());
				String name,room,dateadd,rtype;

				Statement st=cn.createStatement();
				ResultSet rs=st.executeQuery("SELECT * FROM PAT WHERE patientno="+num);

				if(rs.next())
				{

					no=rs.getInt("patientno");
					name=rs.getString("name");
					dateadd=rs.getString("dateadd");
					System.out.println(dateadd);

					rtype=rs.getString("rtype");

					tfname.setText(name);
					tfdateadd.setText(dateadd);
					tfrtype.setText(rtype);					

				}

				try
				{
					Calendar cal=Calendar.getInstance();
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					df.setLenient(false);  

					int dnow=cal.get(Calendar.DAY_OF_MONTH);
					int mnow=cal.get(Calendar.MONTH);
					int ynow=cal.get(Calendar.YEAR);
					int mnowF=mnow+1;


					Calendar cal1=Calendar.getInstance();
					cal1.setTime(df.parse(tfdateadd.getText()));


					SimpleDateFormat sf2=new SimpleDateFormat("yyyy,MM,dd");
					sf2.setLenient(false);  


					int daddd=cal1.get(Calendar.DAY_OF_MONTH);
					int daddMM=cal1.get(Calendar.MONTH);
					int daddYY=cal1.get(Calendar.YEAR);
					int daddMMF=daddMM+1;


					long from = new java.util.GregorianCalendar(ynow,mnowF,dnow).getTime().getTime(); 
					long to = new java.util.GregorianCalendar(daddYY,daddMMF,daddd).getTime().getTime();
					double difference = from-to;


					long days = Math.round((difference/(1000*60*60*24)));

					long bill=0;

					String rt=tfrtype.getText();


					if(rt.equals("Deluxe"))
					{
						System.out.println(tfrtype.getText());
						int m=2000;
						System.out.println(m);			
						bill=days*m;
						System.out.println("total  bill = "+bill);
					}
					if(rt.equals("Private"))
					{
						int m=800;
						System.out.println(m);			
						bill=days*m;
						System.out.println("total  bill = "+bill);
					}
					if(rt.equals("Semi-Private"))
					{
						int m=600;
						System.out.println(m);			
						bill=days*m;
						System.out.println("total  bill = "+bill);
					}
					if(rt.equals("General"))
					{
						int m=400;
						System.out.println(m);			
						bill=days*m;
						System.out.println("total  bill = "+bill);
					}


					//Final Bill
					String FinalBill=(new Long(bill)).toString();
					tftamt.setText(FinalBill);

				}

				catch (Exception e)
				{
					System.out.println(e);
				}


			}
			catch (SQLException sq)
			{
				System.out.println(sq);
			}

		}
	}
}