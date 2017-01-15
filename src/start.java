import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class start extends JFrame implements ActionListener
{
	JButton bpat,bdoc,bbill,breport,bback,bexit;
	JLabel linfo,linfo1,linfo2,linfo3,linfo4;
	JMenu m;
	JMenuBar mb;
	JMenuItem mi;

	start()
	{

		super("Hospital Management System");

		setSize(950,660);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		mb=new JMenuBar();
		m=new JMenu("File");
		mi=new JMenuItem("Exit");

		m.add(mi);
		mb.add(m);
		add(mb, BorderLayout.NORTH);
		
		JLabel backimage=new JLabel(new ImageIcon("images/11.jpg"));
		add(backimage);
		backimage.setLayout(null);	

		linfo=new JLabel("SELECT THE APPROPRIATE OPTION");
		linfo.setForeground(Color.white );
		linfo.setFont(new Font("Arial", Font.BOLD, 40));
		linfo.setBounds(45,100,1000,100);
		backimage.add(linfo);

		linfo1=new JLabel("1. For Inserting, Modifying, Retrieving Patients related Data");
		linfo1.setForeground(Color.white);
		linfo1.setFont(new Font("Arial", Font.BOLD, 20));
		linfo1.setBounds(50,325,800,20);
		backimage.add(linfo1);

		linfo2=new JLabel("2. For Inserting, Modifying, Retrieving Doctors related Data");
		linfo2.setForeground(Color.white);
		linfo2.setFont(new Font("Arial", Font.BOLD, 20));
		linfo2.setBounds(50,395,600,20);
		backimage.add(linfo2);

		linfo3=new JLabel("3. Billing Details");
		linfo3.setForeground(Color.white);
		linfo3.setFont(new Font("Arial", Font.BOLD, 20));
		linfo3.setBounds(50,465,250,20);
		backimage.add(linfo3);

		linfo4=new JLabel("4. Patient and Doctor related Data");
		linfo4.setForeground(Color.white);
		linfo4.setFont(new Font("Arial",  Font.BOLD, 20));
		linfo4.setBounds(50,533,350,20);
		backimage.add(linfo4);

		bpat=new JButton("Patient", new ImageIcon("images/Advances.png"));
		bpat.setBounds(720,320,180,30);
		backimage.add(bpat);

		bdoc=new JButton("Doctor",new ImageIcon("images/Advances.png"));
		bdoc.setBounds(720,390,180,30);
		backimage.add(bdoc);

		bbill=new JButton("Billing",new ImageIcon("images/Attendance.png"));
		bbill.setBounds(720,460,180,30);
		backimage.add(bbill);

		breport=new JButton("Reports",new ImageIcon("images/edit.png"));
		breport.setBounds(720,528,180,30);
		backimage.add(breport);

		bback=new JButton("BACK" ,new ImageIcon("images/preview_Hover.png"));
		bback.setBounds(255,635,100,30);
		backimage.add(bback);

		bexit=new JButton("EXIT" ,new ImageIcon("images/exits.png"));
		bexit.setBounds(375,635,100,30);
		backimage.add(bexit);

		bpat.addActionListener(new patient());
		bdoc.addActionListener(new doctor());
		bbill.addActionListener(new billing());
		bexit.addActionListener(new exit());
		bback.addActionListener(new back());
		breport.addActionListener(new report());
		mi.addActionListener(this);

		setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==mi)
		{
			System.exit(0);
		}	
	}


	class report implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			new  Report();
			setVisible(false);
		}
	}

	class back implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			new  main();
			setVisible(false);
		}
	}

	class patient implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			new patStart();
			setVisible(false);
		}
	}

	class doctor implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			new docStart();
			setVisible(false);
		}
	}

	class billing implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			new Billing();
			setVisible(false);
		}
	}

	class exit implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			System.exit(0);
		}
	}

}