package ViewPackages;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import Managers.MentorManager;
import Managers.StajyerManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Signin extends JFrame {
    public boolean kayitOl = false;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField nicknameField;
    private JTextField ePostaField;
    private JPasswordField passwordFieldStajyer;
    private JPasswordField passwordFieldStajyerTekrar;
    private JFormattedTextField phoneFormattedTextField;
    private JFormattedTextField tcFormattedTextField;
    private JComboBox<String> schoolComboBox;
    private JComboBox<String> departmentComboBox;
    private JComboBox<Integer> classComboBox;

    private MentorManager mentorManager;
    private StajyerManager stajyerManager;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MentorManager mentorManager = new MentorManager();
                StajyerManager stajyerManager = new StajyerManager();
                Signin frame = new Signin(stajyerManager, mentorManager);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }); 
    }

    public Signin(StajyerManager stajyerManager, MentorManager mentorManager) {
    	setResizable(false);
        setBackground(Color.LIGHT_GRAY);
        this.stajyerManager = stajyerManager;
        this.mentorManager = mentorManager;

        List<String> departments = StajyerManager.getBolum();
        List<String> universities = StajyerManager.getOkul();
        List<Integer> classes = StajyerManager.getSinif();

        setTitle("Kayıt Sayfası");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 876, 639);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(20, 61, 105));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblName = new JLabel("Ad:");
        lblName.setForeground(Color.WHITE);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblName.setBounds(17, 63, 100, 30);
        contentPane.add(lblName);

        nameField = new JTextField();
        nameField.setForeground(new Color(20, 61, 105));
        nameField.setBackground(new Color(255, 250, 250));
        nameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        nameField.setBounds(127, 63, 190, 30);
        contentPane.add(nameField);

        JLabel lblSurname = new JLabel("Soyad:");
        lblSurname.setForeground(Color.WHITE);
        lblSurname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblSurname.setBounds(17, 103, 100, 30);
        contentPane.add(lblSurname);

        surnameField = new JTextField();
        surnameField.setForeground(new Color(20, 61, 105));
        surnameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        surnameField.setBounds(127, 103, 190, 30);
        contentPane.add(surnameField);

        JLabel lblNickname = new JLabel("Kullanıcı Adı:");
        lblNickname.setForeground(Color.WHITE);
        lblNickname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNickname.setBounds(549, 147, 121, 30);
        contentPane.add(lblNickname);

        nicknameField = new JTextField();
        nicknameField.setForeground(new Color(20, 61, 105));
        nicknameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        nicknameField.setBounds(656, 148, 200, 30);
        contentPane.add(nicknameField);

        JLabel lblPassword = new JLabel("Şifre:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblPassword.setBounds(17, 147, 100, 30);
        contentPane.add(lblPassword);

        passwordFieldStajyer = new JPasswordField();
        passwordFieldStajyer.setForeground(new Color(20, 61, 105));
        passwordFieldStajyer.setFont(new Font("Tahoma", Font.PLAIN, 16));
        passwordFieldStajyer.setBounds(128, 146, 190, 30);
        contentPane.add(passwordFieldStajyer);

        JLabel lblPasswordAgain = new JLabel("Şifre Tekrar:");
        lblPasswordAgain.setForeground(Color.WHITE);
        lblPasswordAgain.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblPasswordAgain.setBounds(17, 187, 120, 30);
        contentPane.add(lblPasswordAgain);

        passwordFieldStajyerTekrar = new JPasswordField();
        passwordFieldStajyerTekrar.setForeground(new Color(20, 61, 105));
        passwordFieldStajyerTekrar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        passwordFieldStajyerTekrar.setBounds(128, 184, 190, 30);
        contentPane.add(passwordFieldStajyerTekrar);

        JLabel lblTc = new JLabel("TC:");
        lblTc.setForeground(Color.WHITE);
        lblTc.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblTc.setBounds(8, 224, 100, 30);
        contentPane.add(lblTc);

        JLabel lblAddress = new JLabel("E-posta");
        lblAddress.setForeground(Color.WHITE);
        lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblAddress.setBounds(549, 187, 100, 30);
        contentPane.add(lblAddress);

        ePostaField = new JTextField();
        ePostaField.setForeground(new Color(20, 61, 105));
        ePostaField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ePostaField.setBounds(656, 188, 200, 30);
        contentPane.add(ePostaField);

        JLabel lblPhone = new JLabel("Telefon:");
        lblPhone.setForeground(Color.WHITE);
        lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblPhone.setBounds(8, 264, 100, 30);
        contentPane.add(lblPhone);

        try {
            MaskFormatter maskFormatterTel = new MaskFormatter("(05##) ### ## ##");
            phoneFormattedTextField = new JFormattedTextField(maskFormatterTel);
            phoneFormattedTextField.setForeground(new Color(20, 61, 105));
            phoneFormattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
            phoneFormattedTextField.setBounds(127, 264, 190, 30);
            contentPane.add(phoneFormattedTextField);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel lblSchool = new JLabel("Okul:");
        lblSchool.setForeground(Color.WHITE);
        lblSchool.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblSchool.setBounds(549, 63, 100, 30);
        contentPane.add(lblSchool);

        schoolComboBox = new JComboBox<>();
        schoolComboBox.setForeground(new Color(20, 61, 105));
        schoolComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        schoolComboBox.setBounds(656, 63, 200, 30);
        contentPane.add(schoolComboBox);
        for (String university : universities) {
            schoolComboBox.addItem(university);
        }
        AutoCompleteDecorator.decorate(schoolComboBox);

        JLabel lblDepartment = new JLabel("Bölüm:");
        lblDepartment.setForeground(Color.WHITE);
        lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblDepartment.setBounds(549, 103, 97, 30);
        contentPane.add(lblDepartment);

        departmentComboBox = new JComboBox<>();
        departmentComboBox.setForeground(new Color(20, 61, 105));
        departmentComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        departmentComboBox.setBounds(656, 104, 200, 30);
        contentPane.add(departmentComboBox);
        for (String department : departments) {
            departmentComboBox.addItem(department);
        }
        AutoCompleteDecorator.decorate(departmentComboBox);

        JLabel lblClass = new JLabel("Sınıf:");
        lblClass.setForeground(Color.WHITE);
        lblClass.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblClass.setBounds(549, 224, 100, 30);
        contentPane.add(lblClass);

        classComboBox = new JComboBox<>();
        classComboBox.setForeground(new Color(20, 61, 105));
        classComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        classComboBox.setBounds(656, 225, 200, 30);
        contentPane.add(classComboBox);
        for (Integer classYear : classes) {
            classComboBox.addItem(classYear);
        }
        AutoCompleteDecorator.decorate(classComboBox);

        JButton btnSignin = new JButton("Kayıt Ol");
        btnSignin.setForeground(new Color(20, 61, 105));
        btnSignin.setBackground(new Color(255, 255, 255));
        btnSignin.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnSignin.setBounds(517, 482, 300, 40);
        contentPane.add(btnSignin);

        try { 
            MaskFormatter tcMaskFormatter = new MaskFormatter("###########");
            tcFormattedTextField = new JFormattedTextField(tcMaskFormatter);
            tcFormattedTextField.setForeground(new Color(20, 61, 105));
            tcFormattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
            tcFormattedTextField.setBounds(127, 224, 190, 30);
            contentPane.add(tcFormattedTextField);
            
            JButton btnNewButton = new JButton("Geri Dön");
            btnNewButton.setForeground(new Color(20, 61, 105));
            btnNewButton.setBackground(new Color(255, 255, 255));
            btnNewButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dispose();
                    Login login = new Login(stajyerManager, mentorManager);
                    login.setVisible(true);
                }
            });
            btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
            btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });
            btnNewButton.setBounds(28, 482, 300, 40);
            contentPane.add(btnNewButton);
            
            JLabel lblNewLabel = new JLabel("Stajyer Kayıt Formu");
            lblNewLabel.setForeground(Color.WHITE);
            lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 23));
            lblNewLabel.setBounds(284, 10, 336, 44);
            contentPane.add(lblNewLabel);
            
            JLabel lblCinsiyet = new JLabel("Cinsiyet:");
            lblCinsiyet.setForeground(Color.WHITE);
            lblCinsiyet.setFont(new Font("Tahoma", Font.PLAIN, 18));
            lblCinsiyet.setBounds(549, 264, 100, 30);
            contentPane.add(lblCinsiyet);
            
            JRadioButton rdbtnNewRadioButton = new JRadioButton("Erkek");
            rdbtnNewRadioButton.setForeground(Color.WHITE);
            rdbtnNewRadioButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
            rdbtnNewRadioButton.setBackground(new Color(20, 61, 105));
            rdbtnNewRadioButton.setBounds(656, 272, 81, 21);
            contentPane.add(rdbtnNewRadioButton);
            
            JRadioButton rdbtnKadn = new JRadioButton("Kadın");
            rdbtnKadn.setForeground(Color.WHITE);
            rdbtnKadn.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
            rdbtnKadn.setBackground(new Color(20, 61, 105));
            rdbtnKadn.setBounds(775, 272, 81, 21);
            contentPane.add(rdbtnKadn);
            
            JLabel lblNewLabel_2 = new JLabel("");
            lblNewLabel_2.setBackground(new Color(135, 206, 235));
            lblNewLabel_2.setIcon(new ImageIcon(Signin.class.getResource("/ViewPackages/Adsız tasarım.png")));
            lblNewLabel_2.setBounds(306, 172, 225, 225);
            contentPane.add(lblNewLabel_2);
        } catch (ParseException e) { 
            e.printStackTrace();
        }

        btnSignin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SigninToLogin signinToLogin = new SigninToLogin();
                signinToLogin.run();
            }
        });
    }

    private class SigninToLogin implements Runnable {
        @Override
        public void run() {
  
            	if(nameField.getText().isEmpty()) {
    				JOptionPane.showMessageDialog(null,"İsim alanı boş brakılamaz" );
    			}else if(surnameField.getText().isEmpty()) {
    				JOptionPane.showMessageDialog(null,"soyisim alanı boş brakılamaz" );
    			}else if( nicknameField.getText().isEmpty()) {
    				JOptionPane.showMessageDialog(null,"Takma isim  alanı boş brakılamaz" );
    			}else if(passwordFieldStajyer.getPassword() == null) {
    				JOptionPane.showMessageDialog(null,"şifre alanları  boş brakılamaz" );
    			}else if(tcFormattedTextField.getText().isEmpty()){
    				JOptionPane.showMessageDialog(null,"Tc alanı boş brakılamaz" );
    			}else if( schoolComboBox.getSelectedItem().toString() == null) {
    				JOptionPane.showMessageDialog(null,"Okul alanı  boş brakılamaz" );
    			}else if(classComboBox.getSelectedItem() == null) {
    				JOptionPane.showMessageDialog(null,"bölüm  alanı boş brakılamaz" );
    			}else if(phoneFormattedTextField.getText().isEmpty()) {
    				JOptionPane.showMessageDialog(null,"telefon alanı boş brakılamaz" );
    			}else if(ePostaField.getText().isEmpty()) {
    				JOptionPane.showMessageDialog(null,"e-posta alanı boş brakılamaz" );
    			}else if(stajyerManager.sifreControl(passwordFieldStajyer.getPassword(), passwordFieldStajyerTekrar.getPassword())) {
    				JOptionPane.showMessageDialog(null,"Şifreler uyuşmuyor" );

    			}
    			else {
    			
                        try { 
							StajyerManager.signin(
							    nameField.getText(),
							    surnameField.getText(),
							    nicknameField.getText(),
							    new String(passwordFieldStajyer.getPassword()),
							    tcFormattedTextField.getText(),
							    schoolComboBox.getSelectedItem().toString(),
							    departmentComboBox.getSelectedItem().toString(),
							    (int) classComboBox.getSelectedItem(),
							    phoneFormattedTextField.getText(),
							    ePostaField.getText()
							    
							);
							  JOptionPane.showMessageDialog(null, "Kayıt başarıyla tamamlandı!");
		                        dispose();
		                        Login login = new Login(stajyerManager, mentorManager);
		                        login.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
                      
                
    				
    			}
            	
            
        }
    }
}