package ViewPackages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Objects.Mentor;
import Objects.Stajyer;
import Managers.MentorManager;
import Managers.StajyerManager;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;

public class Login extends JFrame {
    private MentorManager mentorManager;
    private StajyerManager stajyerManager;
    private static final long serialVersionUID = 1L;
    private JLabel lblTcStajyer;
    private JLabel lblpasswordStajyer;
    private PlaceholderTextField TcFieldStajyer;
    private PlaceholderPasswordField passwordFieldStajyer;
    private JButton btnLoginStajyer;
    private JButton btnSigninStajyer;
    private JLabel lblTcMentor_1;
    private JLabel lblpasswordMentor;
    private PlaceholderTextField AdFieldMentor;
    private PlaceholderPasswordField passwordFieldMentor;
    private JButton btnLoginMentor;
    private JTabbedPane w_pane;
    private JPanel panel;
    private JPanel panel_1;
    private JLabel lblMergentechStajyerGiriine;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> { 
            try {
                MentorManager mentorManager = new MentorManager();
                StajyerManager stajyerManager = new StajyerManager();
                Login frame = new Login(stajyerManager, mentorManager);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }); 
    }

    public Login(StajyerManager stajyerManager, MentorManager mentorManager) {
    	getContentPane().setForeground(new Color(20, 61, 105));
    	setResizable(false);
        this.stajyerManager = stajyerManager;
        this.mentorManager = mentorManager;
        setTitle("Stajyer Görev Sistemi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        Font tabFont = new Font("Arial", Font.BOLD, 16);
 
        w_pane = new JTabbedPane(JTabbedPane.TOP);
        w_pane.setFont(tabFont);
        w_pane.setBounds(10, 124, 576, 379);
        getContentPane().add(w_pane);

        // Stajyer Girişi Paneli
        panel = new JPanel();
        panel.setBackground(new Color(20, 61, 105));
        w_pane.addTab("Stajyer Girişi", null, panel, null);
        
        panel.setLayout(null);

        lblTcStajyer = new JLabel("T.C. Numarası:");
        lblTcStajyer.setForeground(Color.WHITE);
        lblTcStajyer.setBounds(10, 105, 160, 43);
        lblTcStajyer.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
        panel.add(lblTcStajyer);

        lblpasswordStajyer = new JLabel("Şifre:");
        lblpasswordStajyer.setForeground(Color.WHITE);
        lblpasswordStajyer.setBounds(10, 177, 143, 47);
        lblpasswordStajyer.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
        panel.add(lblpasswordStajyer);

        passwordFieldStajyer = new PlaceholderPasswordField("sifre");
        passwordFieldStajyer.setBounds(219, 183, 303, 43);
        panel.add(passwordFieldStajyer);

        TcFieldStajyer = new PlaceholderTextField("T.C. Numarası");
        TcFieldStajyer.setBounds(219, 108, 303, 43);
        panel.add(TcFieldStajyer);

        btnLoginStajyer = new JButton("Giriş Yap");
        btnLoginStajyer.setForeground(new Color(20, 61, 105));
        btnLoginStajyer.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
        btnLoginStajyer.setBounds(281, 254, 240, 54);
        panel.add(btnLoginStajyer);

        btnSigninStajyer = new JButton("Kayıt Ol");
        btnSigninStajyer.setForeground(new Color(20, 61, 105));
        btnSigninStajyer.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
        btnSigninStajyer.setBounds(10, 254, 223, 54);
        panel.add(btnSigninStajyer);
        
        lblMergentechStajyerGiriine = new JLabel("Mergentech Stajyer Girişine Hoş Geldiniz.\r\n");
        lblMergentechStajyerGiriine.setForeground(Color.WHITE);
        lblMergentechStajyerGiriine.setBounds(91, 21, 342, 53);
        panel.add(lblMergentechStajyerGiriine);
        lblMergentechStajyerGiriine.setFont(new Font("Microsoft PhagsPa", Font.PLAIN, 17));

        // Mentor Girişi Paneli
        panel_1 = new JPanel();
        panel_1.setForeground(new Color(20, 61, 105));
        panel_1.setBackground(new Color(20, 61, 105));
        w_pane.addTab("Yönetici Girişi", null, panel_1, null);
        panel_1.setLayout(null);

        lblTcMentor_1 = new JLabel("Yönetici Adı:");
        lblTcMentor_1.setForeground(new Color(255, 255, 255));
        lblTcMentor_1.setBounds(10, 105, 160, 43);
        lblTcMentor_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
        panel_1.add(lblTcMentor_1);

        AdFieldMentor = new PlaceholderTextField("Yönetici Adı");
        AdFieldMentor.setBounds(219, 108, 303, 43);
        panel_1.add(AdFieldMentor);

        lblpasswordMentor = new JLabel("Şifre:");
        lblpasswordMentor.setForeground(new Color(255, 255, 255));
        lblpasswordMentor.setBounds(10, 177, 143, 47); 
        lblpasswordMentor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
        panel_1.add(lblpasswordMentor);

        passwordFieldMentor = new PlaceholderPasswordField("sifre");
        passwordFieldMentor.setBounds(216, 183, 305, 43);
        panel_1.add(passwordFieldMentor);

        btnLoginMentor = new JButton("Giriş Yap");
        btnLoginMentor.setForeground(new Color(20, 61, 105));
        btnLoginMentor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
        btnLoginMentor.setBounds(216, 257, 303, 55);
        panel_1.add(btnLoginMentor);
        
        JLabel lblMergentechYneticiGiriine = new JLabel("Mergentech Yönetici Girişine Hoş Geldiniz.\r\n");
        lblMergentechYneticiGiriine.setForeground(new Color(255, 255, 255));
        lblMergentechYneticiGiriine.setBackground(new Color(255, 255, 255));
        lblMergentechYneticiGiriine.setFont(new Font("Microsoft PhagsPa", Font.PLAIN, 17));
        lblMergentechYneticiGiriine.setBounds(91, 21, 342, 53);
        panel_1.add(lblMergentechYneticiGiriine);

        JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("mersin.png")));
        lbl_logo.setBounds(232, 10, 123, 93);
        getContentPane().add(lbl_logo);

        // Action Listeners
        btnLoginMentor.addActionListener(e -> {
            String mentorAdi = AdFieldMentor.getText();
            String mentorSifre = new String(passwordFieldMentor.getPassword());
            try {
                Mentor mentor = mentorManager.login(mentorAdi, mentorSifre);
                if (mentor != null) {
                    dispose();
                    MentorPage mentorPage = new MentorPage(mentor);
                    mentorPage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Giriş başarısız! Mentor ismi veya şifre hatalı.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
            }
        });
 
        btnSigninStajyer.addMouseListener(new MouseAdapter() { 
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                Signin signin = new Signin(stajyerManager, mentorManager);
                signin.setVisible(true);
            }
        });

        btnLoginStajyer.addActionListener(e -> {
            String sifreStajyer = new String(passwordFieldStajyer.getPassword());
            String tcStajyer = TcFieldStajyer.getText();
            try {
                Stajyer stajyer = stajyerManager.login(tcStajyer, sifreStajyer);
                if (stajyer != null) {
                    dispose();
                    StajyerPage stajyerPage = new StajyerPage(stajyer);
                    stajyerPage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Giriş başarısız! T.C. No veya şifre hatalı.");
                } 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
            }
        });
    }
}
