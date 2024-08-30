package ViewPackages;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JList;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Managers.MentorManager;
import Managers.StajyerManager;
import Objects.Stajyer;
import Objects.Mission;
import java.awt.Component;


public class StajyerPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Stajyer stajyer;
	private CardLayout cl_panelSoltAlt;
	private JPanel panelSoltAlt;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private List<Mission> missions;
	private JLabel odevBasligi, OdevBaslik2, OdevBaslik3;
	private int gorevId;
	private JTextArea odevAciklamasi, MentorCevap;
	private StajyerManager stajyerManager;
	private MentorManager mentorManager;
 
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Stajyer stajyer = new Stajyer();
				stajyer.setAd("Örnek Ad");
				stajyer.setSoyad("Örnek Soyad");
				StajyerPage frame = new StajyerPage(stajyer);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public StajyerPage(Stajyer stajyer) {
		if (stajyer == null) {
			throw new IllegalArgumentException("Stajyer nesnesi null olamaz");
		}
		this.stajyer = stajyer;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 993, 586);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mdProfilim = new JMenu("Profilim");
		mdProfilim.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl_panelSoltAlt.show(panelSoltAlt, "Profilim");
			}
		});
		
				JMenu mnStajyerIslemleri = new JMenu("Ana Sayfa");
				mnStajyerIslemleri.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cl_panelSoltAlt.show(panelSoltAlt, "Giriş Sayfası");

					}
				});
				menuBar.add(mnStajyerIslemleri);
		menuBar.add(mdProfilim);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelSoltAlt = new JPanel();
		panelSoltAlt.setBounds(0, 0, 979, 531);
		contentPane.add(panelSoltAlt);
		cl_panelSoltAlt = new CardLayout();
		panelSoltAlt.setLayout(cl_panelSoltAlt);

		ImagePanel card0 = new ImagePanel(getClass().getResource("/mentorcard1.png"));
		card0.setBackground(new Color(255, 255, 255));
		card0.setLayout(null);
		panelSoltAlt.add(card0, "Giriş Sayfası");

		JLabel lblNewLabel_7 = new JLabel("Hoş Geldiniz Sayın "+stajyer.getAd()+" "+stajyer.getSoyad());
		lblNewLabel_7.setForeground(new Color(255, 255, 255));
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 25));
		lblNewLabel_7.setBounds(64, 10, 827, 37);
		card0.add(lblNewLabel_7);

		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/paggepagge.png"));
		JLabel imageLabel = new JLabel(imageIcon);
		imageLabel.setBounds(21, 138, 503, 339);
		card0.add(imageLabel);
		
		JLabel lblNewLabel_7_1 = new JLabel("Yapmak istediğiniz işleme menüden ulaşabilirsiniz");
		lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7_1.setForeground(Color.WHITE);
		lblNewLabel_7_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_7_1.setBounds(85, 57, 827, 37);
		card0.add(lblNewLabel_7_1);

		 ImagePanel card1 = new ImagePanel(getClass().getResource("/mentorcard1.png"));

		card1.setLayout(null);
		panelSoltAlt.add(card1, "Ödevlerim");

		JLabel lblNewLabel_11 = new JLabel("Görevlerim");
		lblNewLabel_11.setForeground(new Color(255, 255, 255));
		lblNewLabel_11.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_11.setBounds(38, 0, 105, 29);
		card1.add(lblNewLabel_11);

		listModel = new DefaultListModel<>(); 
	
		list = new JList<>(listModel);
		list.setBounds(10, 27, 969, 493);
		card1.add(list);

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = list.locationToIndex(e.getPoint());
					if (index >= 0) {
						Mission selectedMission = missions.get(index);
						displayMissionDetails(selectedMission);
						cl_panelSoltAlt.show(panelSoltAlt, "Odevleri Goruntule");
					}
				}
			}
		});
		JScrollPane jscrollpaneforlist = new JScrollPane(list);
		jscrollpaneforlist.setBounds(10, 27, 959, 493); // `JScrollPane`'in konumu ve boyutu
		card1.add(jscrollpaneforlist);

		 ImagePanel card2 = new ImagePanel(getClass().getResource("/mentorcard1.png"));

		card2.setBackground(new Color(255, 255, 255));
		panelSoltAlt.add(card2, "Odevleri Goruntule");
		card2.setLayout(null);

		odevBasligi = new JLabel("Görev başlığı:");
		odevBasligi.setForeground(new Color(255, 255, 255));
		odevBasligi.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		odevBasligi.setBounds(49, 44, 318, 33);
		card2.add(odevBasligi);

		odevAciklamasi = new JTextArea();
		odevAciklamasi.setLineWrap(true);
		odevAciklamasi.setWrapStyleWord(true);
		odevAciklamasi.setEditable(false);
		odevAciklamasi.setEnabled(true);
		odevAciklamasi.setForeground(Color.black);
		odevAciklamasi.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		card2.add(odevAciklamasi);
		odevAciklamasi.setSelectionColor(Color.white);
		odevAciklamasi.setSelectedTextColor(Color.black);
		odevAciklamasi.setCaretColor(Color.white);

		JScrollPane jScrollPaneForOdevDetay = new JScrollPane(odevAciklamasi);
		card2.add(jScrollPaneForOdevDetay);
		jScrollPaneForOdevDetay.setBounds(27, 88, 518, 385);
		jScrollPaneForOdevDetay.setBorder(new MatteBorder(0, 0, 0, 2, Color.black));

		JButton btnOdevGonder1 = new JButton("Ödev Göndermek için tıkla");
		btnOdevGonder1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btnOdevGonder1.setBounds(378, 487, 250, 33);
		card2.add(btnOdevGonder1);
		btnOdevGonder1.addActionListener(e -> cl_panelSoltAlt.show(panelSoltAlt, "Card 3"));
		btnOdevGonder1.setHorizontalAlignment(SwingConstants.RIGHT);

		MentorCevap = new JTextArea("Mentor Dönüş");
		MentorCevap.setLineWrap(true);
		MentorCevap.setWrapStyleWord(true);
		MentorCevap.setEditable(false);
		MentorCevap.setEnabled(true);
		MentorCevap.setForeground(Color.black);
		MentorCevap.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		MentorCevap.setBounds(565, 88, 404, 383);
		MentorCevap.setSelectionColor(Color.white);
		MentorCevap.setSelectedTextColor(Color.black);
		MentorCevap.setCaretColor(Color.white);
		card2.add(MentorCevap);

		JScrollPane jScrollPaneForMentorCevap = new JScrollPane();
		jScrollPaneForMentorCevap.setBounds(565, 88, 404, 383);
		jScrollPaneForMentorCevap.setBorder(null);
		card2.add(jScrollPaneForMentorCevap);

		 ImagePanel card3 = new ImagePanel(getClass().getResource("/mentorcard1.png"));

		card3.setBackground(new Color(255, 255, 255));
		panelSoltAlt.add(card3, "Card 3");
		card3.setLayout(null);

		OdevBaslik2 = new JLabel("Gorev Basligi");
		OdevBaslik2.setForeground(new Color(255, 255, 255));
		OdevBaslik2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		OdevBaslik2.setBounds(10, 11, 450, 24);
		card3.add(OdevBaslik2);

		JTextArea textAreaOdev = new JTextArea();
		textAreaOdev.setBackground(new Color(240, 240, 240));
		textAreaOdev.setLineWrap(true);
		textAreaOdev.setBounds(0, 37, 701, 458);
		card3.add(textAreaOdev);

		JScrollPane jScrollPaneforOdev = new JScrollPane(textAreaOdev);
		jScrollPaneforOdev.setBounds(0, 37, 969, 458);
		jScrollPaneforOdev.setBorder(null);
		card3.add(jScrollPaneforOdev);

		 ImagePanel card4 = new ImagePanel(getClass().getResource("/mentorcard1.png"));

		card4.setBackground(new Color(255, 255, 255));
		card4.setLayout(null);
		panelSoltAlt.add(card4, "Odev Guncelle");

		OdevBaslik3 = new JLabel("Odev Basligi");
		OdevBaslik3.setForeground(new Color(255, 255, 255));
		OdevBaslik3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		OdevBaslik3.setBounds(10, 3, 681, 24);
		card4.add(OdevBaslik3);

		JTextArea textAreaOdev2 = new JTextArea();
		textAreaOdev2.setLineWrap(true);
		textAreaOdev2.setBackground(new Color(240, 240, 240));
		textAreaOdev2.setBounds(0, 37, 701, 458);
		card4.add(textAreaOdev2);
		JScrollPane jScrollPaneforTextAreaOdev2 = new JScrollPane(textAreaOdev2);
		jScrollPaneforTextAreaOdev2.setBounds(0, 37, 969, 458);
		jScrollPaneforTextAreaOdev2.setBorder(null);
		card4.add(jScrollPaneforTextAreaOdev2);

		ImagePanel card5 = new ImagePanel(getClass().getResource("/mentorcard1.png"));

		panelSoltAlt.add(card5, "Profilim");
		card5.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Ad: " + stajyer.getAd() );
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
		lblNewLabel_1.setBounds(30, 104, 171, 28);
		card5.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Soyad: " + stajyer.getSoyad());
		lblNewLabel_1_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
		lblNewLabel_1_1.setBounds(30, 143, 225, 28);
		card5.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Okul :"  + stajyer.getOkul());
		lblNewLabel_1_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
		lblNewLabel_1_2.setBounds(30, 296, 630, 28);
		card5.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Okul-bölümü:" + stajyer.getOkulBolum());
		lblNewLabel_1_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_1_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
		lblNewLabel_1_3.setBounds(30, 334, 630, 28);
		card5.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_5 = new JLabel("Tc:" +stajyer.getTc() );
		lblNewLabel_1_5.setForeground(new Color(255, 255, 255));
		lblNewLabel_1_5.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
		lblNewLabel_1_5.setBounds(30, 182, 361, 28);
		card5.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("E-Posta:" +stajyer.getePosta() );
		lblNewLabel_1_6.setForeground(new Color(255, 255, 255));
		lblNewLabel_1_6.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
		lblNewLabel_1_6.setBounds(30, 258, 361, 28);
		card5.add(lblNewLabel_1_6);
		
		JLabel lblNewLabel_1_8 = new JLabel("Telefon:" +stajyer.getTelefon());
		lblNewLabel_1_8.setForeground(new Color(255, 255, 255));
		lblNewLabel_1_8.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
		lblNewLabel_1_8.setBounds(30, 220, 361, 28);
		card5.add(lblNewLabel_1_8);
		
		JLabel lblNewLabel_1_7 = new JLabel("Kişisel Bilgilerim");
		lblNewLabel_1_7.setForeground(new Color(255, 255, 255));
		lblNewLabel_1_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_7.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 23));
		lblNewLabel_1_7.setBounds(171, 10, 512, 60);
		card5.add(lblNewLabel_1_7);

		JButton btnOdevGonder2 = new JButton("Gönder");
		btnOdevGonder2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StajyerManager.gorevGonder(stajyer.getTc(), gorevId, textAreaOdev.getText());
				textAreaOdev.setText("");
			}
		});
		btnOdevGonder2.setBounds(880, 497, 89, 23);
		card3.add(btnOdevGonder2);

		JButton btnNewButton = new JButton("Güncelle");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textAreaOdev2.setText(StajyerManager.yapilanGoreviGetir(stajyer.getTc(), gorevId));
				cl_panelSoltAlt.show(panelSoltAlt, "Odev Guncelle");

			}

		});

		btnNewButton.setBounds(781, 497, 89, 23);
		card3.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Güncelle");
		btnNewButton_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StajyerManager.gorevSil(stajyer.getTc(), gorevId);

				StajyerManager.gorevGonder(stajyer.getTc(), gorevId, textAreaOdev2.getText());
				textAreaOdev.setText("");
				cl_panelSoltAlt.show(panelSoltAlt, "Ödevlerim");


			}
		});
		btnNewButton_1.setBounds(856, 497, 113, 23);
		card4.add(btnNewButton_1);

		JMenu mnOdevlerim = new JMenu("Ödevlerim");
		mnOdevlerim.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl_panelSoltAlt.show(panelSoltAlt, "Ödevlerim");
				updateMissionList();
			}
		});
		menuBar.add(mnOdevlerim);
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBackground(new Color(255, 255, 255));
		horizontalGlue.setFont(null);
		horizontalGlue.setForeground(new Color(255, 255, 255));
		menuBar.add(horizontalGlue);

		JMenu settingsMenu = new JMenu();
		ImageIcon settingsIcon = new ImageIcon(getClass().getResource("/settings.png"));
		settingsMenu.setIcon(settingsIcon);
		menuBar.add(settingsMenu);

   
        JMenu mnÇıkışYap = new JMenu("Çıkış yap");
        mnÇıkışYap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Confirm dialog to ask for exit confirmation
                int response = JOptionPane.showConfirmDialog(null,
                         "Çıkış yapmak istediğinizden emin misiniz? 3 saniye sonra çıkış yapılacaktır.",
                        "Çıkış Onayı", JOptionPane.YES_NO_OPTION);

                if (response == JOptionPane.YES_OPTION) {

                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(() -> {
                            	JOptionPane.showMessageDialog(null,"Çıkış yapılıyor...","Bilgi",JOptionPane.INFORMATION_MESSAGE);
                      
                               dispose();

                                MentorManager mentorManager = new MentorManager();
                                StajyerManager stajyerManager = new StajyerManager();
                                Login login = new Login(stajyerManager, mentorManager);
                                login.setVisible(true);
                            });
                        }
                    };
                    timer.schedule(task, 1000); 
                }
            }
        });
        settingsMenu.add(mnÇıkışYap);

        JMenu mnProgramıKapat = new JMenu("Programı Kapat");
        mnProgramıKapat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        settingsMenu.add(mnProgramıKapat);
	}

  
	private void updateMissionList() {
		listModel.clear();
		StajyerManager stajyerManager = new StajyerManager();
		try {
			missions = stajyerManager.getStajyerGorev(stajyer.getTc());
			if (missions.isEmpty()) {
				listModel.addElement("Görev bulunamadı.");
			} else {
				int i =1;
				for (Mission mission : missions) {
					
					String kisaltilmisAciklama = mission.getShortenedDescription(100);
					listModel.addElement( i+"-)"+mission.getTitle() + "      -      " +kisaltilmisAciklama);
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Görevler alınırken bir hata oluştu: " + e.getMessage());
		}
	}

	private void displayMissionDetails(Mission mission) {
		gorevId = mission.getId();
		odevBasligi.setText("Başlık: " + mission.getTitle());
		OdevBaslik2.setText("Başlık: " + mission.getTitle());
		OdevBaslik3.setText("Başlık: " + mission.getTitle());
		MentorCevap.setText("Mentor Dönüş: \n" + mission.getMentorAnswer());
		odevAciklamasi.setText("Son Teslim Tarihi :"+ mission.getDueDate()  + " \n Açıklama: \n" + mission.getDescription());
	}
}
