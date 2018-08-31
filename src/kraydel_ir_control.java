
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.util.prefs.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import org.harctoolbox.lircclient.LircClient;
import org.harctoolbox.lircclient.TcpLircClient;

import com.beust.jcommander.internal.Console;



import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JMenuItem;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.DropMode;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


public class kraydel_ir_control  {

	private JFrame frmIrdroidAccessibility;
	LircClient lirc;
    String lirc_remote ;
    Boolean voice_enable ;
	private List<String> remotes;
	JTextArea txtrKraydelIrRemote;
	/**
	 * Launch the application.
	 */
	 public List<String> get_remotes()
	  {
	    return remotes;
	  }

	public static void main(String[] args) {
		
				
				
				//	someTask();
		
					kraydel_ir_control window = new kraydel_ir_control();
				
					window.frmIrdroidAccessibility.setVisible(true);
					
					
				
			
		
	}
	
	
	 
	public void read_remote(){
		
		// Retrieve the user preference node for the package com.mycompany
		Preferences prefs = Preferences.userNodeForPackage(kraydel_ir_control.class);

		// Preference key name
		final String PREF_NAME = "remote";
		final String PREF_NAME1 = "voice_enable";
		// Set the value of the preference
	//	String newValue = "a string";
	//	prefs.put(PREF_NAME, newValue);

		// Get the value of the preference;
		// default value is returned if the preference does not exist
		String defaultValue = "Samsung_TV";
		String propertyValue = prefs.get(PREF_NAME, defaultValue); // "a string"
		lirc_remote= propertyValue;
		Boolean default1= false;
		Boolean propertyValue1 = prefs.getBoolean(PREF_NAME1, default1); // "a string"
	    voice_enable =propertyValue1;
			
	}
	
	public void store_remote(String remote){
		// Retrieve the user preference node for the package com.mycompany
				Preferences prefs1 = Preferences.userNodeForPackage(kraydel_ir_control.class);

				// Preference key name
				final String PREF_NAME1 = "remote";

				// Set the value of the preference
			//	String newValue = "a string";
				prefs1.put(PREF_NAME1, remote);
	}
	
	public void store_chkbox(Boolean value){
		// Retrieve the user preference node for the package com.mycompany
				Preferences prefs1 = Preferences.userNodeForPackage(kraydel_ir_control.class);

				// Preference key name
				final String PREF_NAME1 = "voice_enable";

				// Set the value of the preference
			//	String newValue = "a string";
				prefs1.putBoolean(PREF_NAME1,value);
	}

	/**
	 * Create the application.
	 */
	public kraydel_ir_control() {
		execute_winlirc();
		read_remote();
		initialize();
		
	}
	private void elements(){
		List <String> remos= kraydel_ir_control.this.get_remotes();
        JPanel panel = new JPanel();
        panel.add(new JLabel("Please select device:"));
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
     int selected=0;  
    for (int i=0;i<remotes.size();i++){
    	if(lirc_remote.equals(remotes.get(i).toString())){
    		selected=i;
    		System.out.println(selected);
    	}
    	  model.addElement(remotes.get(i));
    }
    
    
      
        JComboBox<String> comboBox = new JComboBox<String>(model);
      
        comboBox.setSelectedIndex(selected);
        panel.add(comboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Flavor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (result) {
            case JOptionPane.OK_OPTION:
                System.out.println("You selected " + comboBox.getSelectedItem());
                txtrKraydelIrRemote.append("\r\n> Device/Remote Selected: "+comboBox.getSelectedItem());
               lirc_remote=comboBox.getSelectedItem().toString();
                store_remote(comboBox.getSelectedItem().toString());
                break;
        }
	}
	private void execute_winlirc(){
		String OSNAME=null;
		 java.io.Console console = System.console();
	        if(console == null && !GraphicsEnvironment.isHeadless()){
	            String filename = kraydel_ir_control.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
	            OSNAME = System.getProperty("os.name");
	            String dot = "IRToy.dll";
	            if(OSNAME.contains("Windows")){
	            	try {
	            		Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","startlirc.bat " +" " });
	            	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            if(OSNAME.contains("Linux")){
	            	try {
						Runtime.getRuntime().exec(new String[]{"irrecord","/c","start","cmd","/k","java -jar \"" + filename + "\""});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            
	        }else{
	            kraydel_ir_control.main(new String[0]);
	            System.out.println("Program has ended, please type 'exit' to close the console");
	        }
	}
	private void execute_irrecord(String remote_name){
	
		String OSNAME=null;
		/* String command = "ping -n 3 www.google.com";

	        Process proc = null;
			try {
				proc = Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Read the output

	        BufferedReader reader =  
	              new BufferedReader(new InputStreamReader(proc.getInputStream()));

	        String line = "";
	        try {
				while((line = reader.readLine()) != null) {
				    System.out.print(line + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        try {
				proc.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   */

		 java.io.Console console = System.console();
	        if(console == null && !GraphicsEnvironment.isHeadless()){
	            String filename = kraydel_ir_control.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
	            OSNAME = System.getProperty("os.name");
	            String dot = "IRToy.dll";
	            if(OSNAME.contains("Windows")){
	            	try {
	            		Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","record.bat " +" "+remote_name });
	            	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            if(OSNAME.contains("Linux")){
	            	try {
						Runtime.getRuntime().exec(new String[]{"irrecord","/c","start","cmd","/k","java -jar \"" + filename + "\""});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            
	        }else{
	            kraydel_ir_control.main(new String[0]);
	            System.out.println("Program has ended, please type 'exit' to close the console");
	        }
	}
	
	 public void thread(){

		 
	 }

	/**
	 * Initialize the contents of the frame.
	 */
	
	 public static void infoBox(String infoMessage, String titleBar)
	    {
	        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }
	private void initialize() {
	
		
	lirc = null;
		try {
			lirc = new TcpLircClient("localhost", 8765, true, 5000);
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			infoBox("No connection with Lirc exiting!", "Connectivity error");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			infoBox("No connection with Lirc exiting!", "Connectivity error");
			e2.printStackTrace();
		}
        String version = null;
		try {
			version = lirc.getVersion();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		remotes = null;
		try {
			remotes = lirc.getRemotes();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
         for (int i = 0; i < remotes.size(); i++)
             System.out.println(i + ":\t" + remotes.get(i));
        System.out.println(version);
		
		frmIrdroidAccessibility = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		  int x = (int) ((dim.getWidth() - 1000) / 2);
		    int y = (int) ((dim.getHeight() - 700) / 2);
		 
		//frmIrdroidAccessibility.setLocation(dim.width/2-frmIrdroidAccessibility.getSize().width/2, dim.height/2-frmIrdroidAccessibility.getSize().height/2);
		frmIrdroidAccessibility.setTitle("Kraydel IR Control");
		frmIrdroidAccessibility.setResizable(false);
		frmIrdroidAccessibility.setBounds(0, 0, 1000, 700);
		frmIrdroidAccessibility.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIrdroidAccessibility.getContentPane().setLayout(null);
		
		frmIrdroidAccessibility.setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
		
		JButton btnNewButton = new JButton("ON");
		btnNewButton.setMnemonic('a');
		String pathToImage = "poweron.png";
		ImageIcon myIcon = new ImageIcon(getClass().getClassLoader().getResource(pathToImage));
		btnNewButton.setIcon(myIcon);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new Thread(new Runnable() {
				    public void run() {
				        //Do whatever
				    	try {
				    		lirc.sendIrCommand(lirc_remote, "POWERON", 0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							infoBox("No connection with Lirc!", "Connectivity error");
							e.printStackTrace();
						}
				    }
				}).start();
				
			}
		});
		btnNewButton.setBounds(57, 63, 131, 117);
		frmIrdroidAccessibility.getContentPane().add(btnNewButton);
		String pathToImage1 = "poweroff.png";
		ImageIcon myIcon1 = new ImageIcon(getClass().getClassLoader().getResource(pathToImage1));
		JButton button = new JButton("OFF");
		button.setMnemonic('b');
		button.setIcon(myIcon1);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
				    public void run() {
				        //Do whatever
				    	try {
				    		
				    		lirc.sendIrCommand(lirc_remote, "POWEROFF", 0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							infoBox("No connection with Lirc!", "Connectivity error");
							e.printStackTrace();
						}
				    }
				}).start();
			}
		});
		button.setBounds(245, 63, 131, 117);
		frmIrdroidAccessibility.getContentPane().add(button);
		String pathToImage2 = "hdmi1.png";
		ImageIcon myIcon2 = new ImageIcon(getClass().getClassLoader().getResource(pathToImage2));
		JButton button_1 = new JButton("HDMI1");
		button_1.setMnemonic('c');
		button_1.setIcon(myIcon2);
		button_1.setBounds(57, 264, 138, 117);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
				    public void run() {
				        //Do whatever
				    	try {
				    		
				    		lirc.sendIrCommand(lirc_remote, "INPUTHDMI1", 0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							infoBox("No connection with Lirc!", "Connectivity error");
							e.printStackTrace();
						}
				    }
				}).start();
			}
		});
		frmIrdroidAccessibility.getContentPane().add(button_1);
		String pathToImage3 = "hdmi1.png";
		ImageIcon myIcon4 = new ImageIcon(getClass().getClassLoader().getResource(pathToImage3));
		JButton button_2 = new JButton("HDMI2");
		button_2.setMnemonic('d');
		button_2.setIcon(myIcon4);
		button_2.setBounds(245, 264, 131, 117);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
				    public void run() {
				        //Do whatever
				    	try {
				    		lirc.sendIrCommand(lirc_remote, "INPUTHDMI2", 0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							infoBox("No connection with Lirc!", "Connectivity error");
							e.printStackTrace();
						}
				    }
				}).start();
			}
		});
		frmIrdroidAccessibility.getContentPane().add(button_2);
		String pathToImage4 = "hdmi1.png";
		ImageIcon myIcon5 = new ImageIcon(getClass().getClassLoader().getResource(pathToImage4));
		JButton button_3 = new JButton("HDMI3");
		button_3.setMnemonic('e');
		button_3.setIcon(myIcon5);
		button_3.setBounds(433, 264, 131, 117);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
				    public void run() {
				        //Do whatever
				    	try {
				    		lirc.sendIrCommand(lirc_remote, "INPUTHDMI3", 0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							infoBox("No connection with Lirc!", "Connectivity error");
							e.printStackTrace();
						}
				    }
				}).start();
			}
		});
		frmIrdroidAccessibility.getContentPane().add(button_3);
		String pathToImage6 = "hdmi1.png";
		ImageIcon myIcon6 = new ImageIcon(getClass().getClassLoader().getResource(pathToImage6));
		JButton btnMute = new JButton("HDMI4");
		btnMute.setMnemonic('f');
		btnMute.setIcon(myIcon6);
		btnMute.setBounds(621, 264, 138, 117);
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
				    public void run() {
				        //Do whatever
				    	try {
				    		lirc.sendIrCommand(lirc_remote, "INPUTHDMI4", 0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							infoBox("No connection with Lirc!", "Connectivity error");
							e.printStackTrace();
						}
				    }
				}).start();
			}
		});
		frmIrdroidAccessibility.getContentPane().add(btnMute);
		
		JLabel lblAllRightsReserved = new JLabel("All Rights Reserved. Kraydel IR Control is a Registered Trademark of Kraydel.");
		lblAllRightsReserved.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAllRightsReserved.setBounds(350, 626, 359, 14);
		frmIrdroidAccessibility.getContentPane().add(lblAllRightsReserved);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(339, 626, 370, 14);
		frmIrdroidAccessibility.getContentPane().add(separator);
		
		JButton btnComponent = new JButton("Comp.1");
		btnComponent.setIcon(new ImageIcon("C:\\Users\\bakalski\\Documents\\GitHub\\IR-Java-Application\\res\\component.png"));
		btnComponent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnComponent.setMnemonic('b');
		btnComponent.setBounds(433, 63, 131, 117);
		frmIrdroidAccessibility.getContentPane().add(btnComponent);
		
		JButton btnComp = new JButton("Comp.2");
		btnComp.setIcon(new ImageIcon("C:\\Users\\bakalski\\Documents\\GitHub\\IR-Java-Application\\res\\component.png"));
		btnComp.setMnemonic('b');
		btnComp.setBounds(621, 63, 131, 117);
		frmIrdroidAccessibility.getContentPane().add(btnComp);
		
		JButton btnComp_1 = new JButton("Comp.3");
		btnComp_1.setIcon(new ImageIcon("C:\\Users\\bakalski\\Documents\\GitHub\\IR-Java-Application\\res\\component.png"));
		btnComp_1.setMnemonic('b');
		btnComp_1.setBounds(808, 63, 131, 117);
		frmIrdroidAccessibility.getContentPane().add(btnComp_1);
		
		JButton btnTv = new JButton("TV");
		btnTv.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnTv.setMnemonic('f');
		btnTv.setBounds(808, 264, 138, 117);
		frmIrdroidAccessibility.getContentPane().add(btnTv);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(45, 442, 901, 127);
		frmIrdroidAccessibility.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 881, 93);
		panel.add(scrollPane);
		
		txtrKraydelIrRemote = new JTextArea();
		scrollPane.setViewportView(txtrKraydelIrRemote);
		txtrKraydelIrRemote.setWrapStyleWord(true);
		txtrKraydelIrRemote.setTabSize(1);
		txtrKraydelIrRemote.setColumns(1);
		txtrKraydelIrRemote.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtrKraydelIrRemote.setText("> Kraydel IR Remote Log");
		txtrKraydelIrRemote.setToolTipText("> Kraydel IR Remote Log");
		txtrKraydelIrRemote.setEditable(false);
		txtrKraydelIrRemote.setBackground(Color.WHITE);
		panel.setVisible(true);
		for (int i =0;i<remotes.size();i++){
		//	comboBox.addItem(arg0)(remotes.get(i));
		}
		
		JMenuBar menuBar = new JMenuBar();
		frmIrdroidAccessibility.setJMenuBar(menuBar);
		
		JMenu mnInfo = new JMenu("Settings");
		menuBar.add(mnInfo);
		
	//	JMenu mnVoiceControl = new JMenu("Voice control");
	//	mnVoiceControl.setEnabled(false);
	//	mnInfo.add(mnVoiceControl);
		
		JMenuItem menuItem = new JMenuItem("Device");
		mnInfo.add(menuItem);
		menuItem.addActionListener(
	            new ActionListener(){
	                public void actionPerformed(ActionEvent e)
	                {
	                	
	        		      remotes = null;
	        				try {
	        					remotes = lirc.getRemotes();
	        				} catch (IOException e2) {
	        					// TODO Auto-generated catch block
	        					e2.printStackTrace();
	        				}
	        				
	                elements();	
	                 
	                }
	            }
	        );
		
		JMenuItem menuItem1 = new JMenuItem("Record Remote");
		mnInfo.add(menuItem1);
		menuItem1.addActionListener(
	            new ActionListener(){
	                public void actionPerformed(ActionEvent e)
	                {
	                //	execute_irrecord();
	                 ShowDialog();
	                }
	            }
	        );
		
		final JCheckBox chckbxEnabledisable = new JCheckBox("Enable / Disable");
		
	//	mnVoiceControl.add(chckbxEnabledisable);
		
		
			//chckbxEnabledisable.setSelected(true);
		
		
		chckbxEnabledisable.addActionListener( new ActionListener(){
			
			
			  public void actionPerformed(ActionEvent e)
              {
		     boolean selected= chckbxEnabledisable.getModel().isSelected();
             System.out.println(selected);
           //  voice_enable= selected;
          //   System.out.println(voice_enable);
          //   store_chkbox(voice_enable);
          //   thread();
               
              }
		}
		
				
				
				
				
				
				);
		//if(voice_enable){
		//chckbxEnabledisable.setSelected(true);
		//thread();
		//}
		
		
		
		
		
		
		

	
		
	}
	private void ShowDialog(){
		 String inStr = JOptionPane.showInputDialog(null, "Please enter the remote name",
		            "Remote Name", JOptionPane.PLAIN_MESSAGE);
		      System.out.println("You have entered " + inStr);
		      
		      txtrKraydelIrRemote.append("\r\n> Remote control selected: "+inStr);
		     
		      execute_irrecord(inStr);
		  
			
		      
		   
	}
}

