package ViewPackages;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import Managers.MentorManager;
import Managers.StajyerManager;
import Objects.Mentor;
import Objects.Mission;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.toedter.calendar.JDateChooser;

public class MentorPage extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout cardLayout;
	private JPanel kartPanel1;
	private JTextField textFieldBaslik;
	private JComboBox<String> stajyerComboBox;
	private DefaultListModel<String> listModel;
	private String selectedTc;
	private Mission selectedMission;
	private Map<Integer, Mission> missionIdMap = new HashMap<>();
	private DefaultListModel<String> selectedStudentListModel;
	private JList<String> selectedStudentList;
	// Yeni eklemeler
	private JComboBox<String> stajyerComboBoxDegerlendir;
	private JList<String> gorevList;
	private DefaultListModel<String> gorevListModel;
	private JComboBox<String> stajyerComboBox_1;

	public MentorPage(Mentor mentor) {
		List<String> sellectedstajyers = new ArrayList<>();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 999, 568);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 255));
		setJMenuBar(menuBar);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cardLayout = new CardLayout();
		kartPanel1 = new JPanel(cardLayout);
		kartPanel1.setBounds(0, 0, 700, 510);
		contentPane.add(kartPanel1);

		ImagePanel card0 = new ImagePanel(getClass().getResource("/deneme.jpg"));

		card0.setBackground(Color.WHITE);
		kartPanel1.add(card0, "Ana sayfa");
		card0.setLayout(null);

		ImagePanel card1 = new ImagePanel(getClass().getResource("/mentorcard1.png"));
		card1.setBackground(new Color(135, 206, 235));
		kartPanel1.add(card1, "Odev Ver");
		card1.setLayout(null);

		textFieldBaslik = new JTextField();
		textFieldBaslik.setBounds(186, 124, 250, 20);
		card1.add(textFieldBaslik);
		textFieldBaslik.setColumns(10);

		JLabel lblNewLabelOdev = new JLabel("Görev Başlığını giriniz:");
		lblNewLabelOdev.setForeground(new Color(240, 248, 255));
		lblNewLabelOdev.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabelOdev.setBounds(10, 123, 154, 18);
		card1.add(lblNewLabelOdev);

		JLabel lblNewLabelAciklama = new JLabel("Görev açıklaması:");
		lblNewLabelAciklama.setForeground(new Color(240, 248, 255));
		lblNewLabelAciklama.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabelAciklama.setBounds(32, 155, 284, 20);
		card1.add(lblNewLabelAciklama);

		JTextArea textAreaAciklama = new JTextArea();
		textAreaAciklama.setBounds(186, 155, 251, 203);
		textAreaAciklama.setLineWrap(true);
		card1.add(textAreaAciklama);

		JScrollPane jScrollPaneforaciklama = new JScrollPane(textAreaAciklama);
		jScrollPaneforaciklama.setBounds(186, 155, 251, 203);
		card1.add(jScrollPaneforaciklama);

		JLabel lblNewLabel_6 = new JLabel("Görev Teslim Tarihi:");
		lblNewLabel_6.setForeground(new Color(240, 248, 255));
		lblNewLabel_6.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_6.setBounds(32, 381, 142, 20);
		card1.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Stajyer seçiniz:");
		lblNewLabel_7.setForeground(new Color(240, 248, 255));
		lblNewLabel_7.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_7.setBounds(32, 36, 154, 18);
		card1.add(lblNewLabel_7);
		MentorManager mentorManager = new MentorManager();

		JPanel panel = new JPanel();
		panel.setBounds(143, 11, 449, 108);
		card1.add(panel);

		selectedStudentListModel = new DefaultListModel<>();
		panel.setLayout(null);
		selectedStudentList = new JList<>(selectedStudentListModel);
		JScrollPane selectedScrollPane = new JScrollPane(selectedStudentList);
		selectedScrollPane.setBounds(0, 22, 449, 86);
		panel.add(selectedScrollPane);

		// ComboBox ve diğer bileşenlerin oluşturulması
		stajyerComboBox = new JComboBox<>();
		stajyerComboBox.setBounds(0, 0, 449, 22);
		panel.add(stajyerComboBox);

		// Stajyer listesini alıp ComboBox'a ekleme
		List<String> stajyers = MentorManager.getIntern();
		for (String stajyer : stajyers) {
			stajyerComboBox.addItem(stajyer);
		}
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(187, 381, 250, 30);
		card1.add(dateChooser);

		// "Kaydet" butonunu oluşturma
		JButton btnNewButtonOdevGönder = new JButton("Kaydet");
		btnNewButtonOdevGönder.setForeground(new Color(20, 61, 105));
		btnNewButtonOdevGönder.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnNewButtonOdevGönder.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < selectedStudentListModel.getSize(); i++) {
					sellectedstajyers.add(selectedStudentListModel.getElementAt(i));
				}
				// Seçilen stajyerin TC numarasını alma
				String selectedItem = (String) stajyerComboBox.getSelectedItem();
				String tc = MentorManager.extractTC(selectedItem);
 
				// Tarih seçimini alma

				java.util.Date selectedDate = dateChooser.getDate();

				try {
					java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

					// Görevi ekleme
					MentorManager.submitTask(textFieldBaslik.getText(), textAreaAciklama.getText(), sqlDate, tc);
					// Başarıyla eklenirse formu sıfırlama
					textFieldBaslik.setText("");
					textAreaAciklama.setText("");
					stajyerComboBox.setSelectedIndex(-1);
					dateChooser.setDate(null); // Tarih seçimini sıfırlama
					JOptionPane.showMessageDialog(null, "Gorev basari ile eklendi");
				} catch (Exception exception) {
					exception.getStackTrace();
					JOptionPane.showMessageDialog(null, "Lutfen bütün bilgileri tam giriniz");

				}
				// Görevi stajyere atama 
				MentorManager.assignTaskToIntern(sellectedstajyers);

				// Görev listesini güncelleme
				updateMissionList(tc);

			}

		});
		btnNewButtonOdevGönder.setBounds(242, 445, 126, 40);
		card1.add(btnNewButtonOdevGönder);

		JButton btnNewButton = new JButton("Ekle");
		btnNewButton.setBounds(598, 11, 92, 23);
		card1.add(btnNewButton);

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String selectedStudent = (String) stajyerComboBox.getSelectedItem();
				if (selectedStudent != null && !selectedStudentListModel.contains(selectedStudent)) {
					selectedStudentListModel.addElement(selectedStudent); 
					sellectedstajyers.add(selectedStudent);
				}
			}
		});
		JButton btnNewButton_1 = new JButton("Kaldır");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent e) {
				String selectedStudent = (String) stajyerComboBox.getSelectedItem();
				selectedStudentListModel.removeElement(selectedStudent);
			}
		});
		btnNewButton_1.setBounds(598, 45, 93, 30);
		card1.add(btnNewButton_1);

		// card3'ü tanımlarken değişiklik yapacağız
		ImagePanel card3BackgroundPanel = new ImagePanel(getClass().getResource("/mentorcard1.png"));
		card3BackgroundPanel.setLayout(new BorderLayout());

		JPanel card3 = new JPanel();
		card3.setOpaque(false); // Arka planı şeffaf yapıyoruz ki altındaki resim görünsün
		card3.setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setOpaque(false); // Viewport'u şeffaf yapıyoruz
		scrollPane.setOpaque(false); // ScrollPane'in kendisini de şeffaf yapıyoruz
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JPanel missionPanel = new JPanel();
		missionPanel.setOpaque(false); // Bu paneli de şeffaf yapıyoruz
		missionPanel.setLayout(new BoxLayout(missionPanel, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(missionPanel);

		card3.add(scrollPane, BorderLayout.CENTER);
		card3BackgroundPanel.add(card3, BorderLayout.CENTER);

		kartPanel1.add(card3BackgroundPanel, "Stajyer Profil");

		JPanel panelSagAlt = new JPanel();
		panelSagAlt.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelSagAlt.setBackground(Color.GRAY);
		panelSagAlt.setBounds(701, 0, 284, 509);
		contentPane.add(panelSagAlt);

		listModel = new DefaultListModel<>();
		int index = 1;

		for (String stajyer : stajyers) {
			listModel.addElement(index + ". " + stajyer);
			index++;
		}

		JList<String> stajyerList = new JList<>(listModel);
		stajyerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stajyerList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int listIndex = stajyerList.locationToIndex(e.getPoint());
					if (listIndex >= 0) {
						String selectedItem = stajyerList.getModel().getElementAt(listIndex);
						String selectedStajyer = selectedItem.substring(selectedItem.indexOf(' ') + 1);
						selectedTc = MentorManager.extractTC(selectedStajyer);

						// Görev başlıklarını listele
						updateMissionList(selectedTc);
						cardLayout.show(kartPanel1, "Stajyer Profil");
					}
				}
			}
		});
		panelSagAlt.setLayout(null);

		JScrollPane listScrollPane = new JScrollPane(stajyerList);
		listScrollPane.setBounds(2, 2, 280, 505);
		panelSagAlt.add(listScrollPane);

		JLabel lblNewLabel_2 = new JLabel("Stajyerler");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBackground(new Color(135, 206, 235));
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		listScrollPane.setColumnHeaderView(lblNewLabel_2);

		JMenu mnAnasayfa = new JMenu("Ana sayfa") {
			@Override
			protected void processMouseEvent(MouseEvent e) {
				// Sadece sol fare tıklamasını kontrol ediyoruz
				if (e.getID() == MouseEvent.MOUSE_CLICKED && SwingUtilities.isLeftMouseButton(e)) {
					cardLayout.show(kartPanel1, "Ana sayfa"); // "Ana sayfa" kartını gösteriyoruz
				} else {
					super.processMouseEvent(e);
				}
			}
		};

		// Menü çubuğuna ekliyoruz
		menuBar.add(mnAnasayfa);
		JMenu mnNewMenu = new JMenu("Görev Ver");
		mnNewMenu.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(kartPanel1, "Odev Ver");
			}
		});

		JMenu mnProfilim = new JMenu("Profilim");
		mnProfilim.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(kartPanel1, "Profilim");
			}
		});

		menuBar.add(mnProfilim);
		menuBar.add(mnNewMenu);

		JMenu mnStajyerProfil = new JMenu("Stajyer Profil");
		mnStajyerProfil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(kartPanel1, "Stajyer Profil");
			}
		});
		menuBar.add(mnStajyerProfil);

		JMenu mnDegerlendir = new JMenu("Değerlendir");
		mnDegerlendir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(kartPanel1, "Degerlendir");
			}
		});
		menuBar.add(mnDegerlendir);
		ImagePanel card5 = new ImagePanel(getClass().getResource("/mentorcard1.png"));
		kartPanel1.add(card5,"Stajyer Gecmisi");
		card5.setLayout(null);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(125, 147, 250, 30);
		card5.add(dateChooser_1);
		
		JDateChooser dateChooser_2 = new JDateChooser();
		dateChooser_2.setBounds(125, 220, 250, 30);
		card5.add(dateChooser_2);
		
		JLabel lblListelenecekBaslangicTarihini = new JLabel("    Listelenecek baslangic tarihini seçiniz");
		lblListelenecekBaslangicTarihini.setForeground(new Color(240, 248, 255));
		lblListelenecekBaslangicTarihini.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblListelenecekBaslangicTarihini.setBounds(125, 116, 284, 20);
		card5.add(lblListelenecekBaslangicTarihini);
		
		JLabel lblListelenecekBitiTarihini = new JLabel("           Listelenecek bitiş tarihini seçiniz");
		lblListelenecekBitiTarihini.setForeground(new Color(240, 248, 255));
		lblListelenecekBitiTarihini.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblListelenecekBitiTarihini.setBounds(105, 189, 304, 20);
		card5.add(lblListelenecekBitiTarihini);
		
		stajyerComboBox_1 = new JComboBox<String>();
		stajyerComboBox_1.setBounds(173, 54, 164, 22);
		card5.add(stajyerComboBox_1);
		List<String> stajyerss = MentorManager.getIntern();
		for(String stajyer : stajyerss ) {
			stajyerComboBox_1.addItem(stajyer);
			
		}

		
		JLabel lblOgrenciyiSeiniz = new JLabel("Stajyeri  Seçiniz");
		lblOgrenciyiSeiniz.setForeground(new Color(240, 248, 255));
		lblOgrenciyiSeiniz.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblOgrenciyiSeiniz.setBounds(29, 53, 175, 20);
		card5.add(lblOgrenciyiSeiniz);

		JPanel card6 = new JPanel();
		kartPanel1.add(card6,"Detaylar");
		card6.setLayout(null);
		JTextArea gorevContentTextArea = new JTextArea();
		gorevContentTextArea.setBounds(0, 0, 690, 510);
		gorevContentTextArea.setText("");
		gorevContentTextArea.setLineWrap(true);
		gorevContentTextArea.setWrapStyleWord(true);
		gorevContentTextArea.setEditable(false);
		gorevContentTextArea.setEnabled(true);
		gorevContentTextArea.setForeground(Color.black);
		card6.add(gorevContentTextArea);
		JScrollPane scrollPaneforgorevContentTextArea = new JScrollPane(gorevContentTextArea);
		scrollPaneforgorevContentTextArea.setBounds(0, 0, 690, 510);
		
		card6.add(scrollPaneforgorevContentTextArea);
		
		JButton btnGorevListele = new JButton("Listele");
		btnGorevListele.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				String selectedItem = (String) stajyerComboBox_1.getSelectedItem();
				String tc = MentorManager.extractTC(selectedItem);
				java.util.Date selectedDate1 = dateChooser_1.getDate(); 
				java.util.Date selectedDate2 = dateChooser_2.getDate();
				try {
					java.sql.Date sqlDate1 = new java.sql.Date(selectedDate1.getTime());
					java.sql.Date sqlDate2 = new java.sql.Date(selectedDate2.getTime());
			
					List<String> gorevliste = mentorManager.getTasksByIdAndDate(sqlDate1, sqlDate2, tc); 
					for(String gorev : gorevliste) {
						gorevContentTextArea.append(gorev);
					}
					
					cardLayout.show(kartPanel1, "Detaylar");
									
									
				}catch(Exception exception) {
					exception.printStackTrace();
					JOptionPane.showMessageDialog(null,"Bilinmeyen bir hata oluştu tekrar deneyiniz");
					
				}
				
			}
		});
		btnGorevListele.setBounds(257, 289, 89, 23);
		card5.add(btnGorevListele);

		
		JMenu mnNewMenu_1 = new JMenu("Stajyer Gecmisi");
		mnNewMenu_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(kartPanel1, "Stajyer Gecmisi");
			}
		});
		menuBar.add(mnNewMenu_1);
		

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
						"Çıkış yapmak istediğinizden emin misiniz? 3 saniye sonra çıkış yapılacaktır.", "Çıkış Onayı",
						JOptionPane.YES_NO_OPTION);

				if (response == JOptionPane.YES_OPTION) {

					Timer timer = new Timer();
					TimerTask task = new TimerTask() {
						@Override
						public void run() {
							SwingUtilities.invokeLater(() -> {
								JOptionPane.showMessageDialog(null, "Çıkış yapılıyor...", "Bilgi",
										JOptionPane.INFORMATION_MESSAGE);

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

		ImagePanel card2 = new ImagePanel(getClass().getResource("/mentorcard1.png"));
		kartPanel1.add(card2, "Profilim");
		card2.setLayout(null);

		JLabel lblAd = new JLabel("Ad:" + " " + mentor.getAd());
		lblAd.setForeground(new Color(240, 248, 255));
		lblAd.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblAd.setBounds(21, 216, 162, 35);
		card2.add(lblAd);

		JLabel lblSoyad = new JLabel("Soyad:" + mentor.getSoyad());
		lblSoyad.setForeground(new Color(240, 248, 255));
		lblSoyad.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblSoyad.setBounds(21, 261, 162, 35);
		card2.add(lblSoyad);

		JLabel lblNewLabel = new JLabel("Hoşgeldiniz Sayın " + mentor.getAd() + " " + mentor.getSoyad());
		lblNewLabel.setForeground(new Color(240, 248, 255));

		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
		lblNewLabel.setBounds(10, 10, 433, 47);
		card2.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Kişisel Bilgilerim:");
		lblNewLabel_1.setForeground(new Color(240, 248, 255));
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(21, 171, 185, 23);
		card2.add(lblNewLabel_1);

		JLabel lblTelefon = new JLabel("Telefon:+9005076665543");
		lblTelefon.setForeground(new Color(240, 248, 255));
		lblTelefon.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblTelefon.setBounds(21, 313, 214, 35);
		card2.add(lblTelefon);

		JLabel lblEmail = new JLabel("Email:" + mentor.getePosta());
		lblEmail.setForeground(new Color(240, 248, 255));
		lblEmail.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblEmail.setBounds(21, 371, 258, 35);
		card2.add(lblEmail);

		ImagePanel card4 = new ImagePanel(getClass().getResource("/mentorcard1.png"));
		card4.setLayout(null);
		kartPanel1.add(card4, "Degerlendir");

		// Stajyer seçimi için ComboBox
		JLabel lblStajyerSec = new JLabel("Stajyer Seç:");
		lblStajyerSec.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblStajyerSec.setForeground(Color.WHITE);
		lblStajyerSec.setBounds(39, 58, 100, 25);
		card4.add(lblStajyerSec);

		stajyerComboBoxDegerlendir = new JComboBox<>();
		stajyerComboBoxDegerlendir.setBounds(189, 55, 200, 36);
		card4.add(stajyerComboBoxDegerlendir);

		for (String stajyer : stajyers) {
			stajyerComboBoxDegerlendir.addItem(stajyer);
		}

		// Görev listesini göstermek için JList
		JLabel lblGorevSec = new JLabel("Görev Seç:");
		lblGorevSec.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblGorevSec.setForeground(Color.WHITE);
		lblGorevSec.setBounds(39, 130, 100, 25);
		card4.add(lblGorevSec);

		gorevListModel = new DefaultListModel<>();
		JScrollPane gorevScrollPane = new JScrollPane();
		gorevScrollPane.setBounds(189, 130, 200, 100);
		card4.add(gorevScrollPane);
		gorevList = new JList<>(gorevListModel);
		gorevScrollPane.setViewportView(gorevList);

		stajyerComboBoxDegerlendir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) stajyerComboBoxDegerlendir.getSelectedItem();
				selectedTc = MentorManager.extractTC(selectedItem);

				// Seçilen stajyerin görevlerini güncelle
				List<Mission> missions = MentorManager.getTaskDetails(selectedTc);
				gorevListModel.clear();
				missionIdMap.clear();
				int index = 0;
				for (Mission mission : missions) {
					gorevListModel.addElement(mission.getTitle());
					missionIdMap.put(index, mission);
					index++;
				}
			}
		});

		// Mentorun yorumunu gireceği metin alanı
		JLabel lblGorevBaslik = new JLabel("Yorum:");
		lblGorevBaslik.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblGorevBaslik.setForeground(Color.WHITE);
		lblGorevBaslik.setBounds(21, 240, 100, 25);
		card4.add(lblGorevBaslik);

		JTextArea textAreaMentorComment = new JTextArea();
		textAreaMentorComment.setBounds(151, 248, 287, 175);
		textAreaMentorComment.setLineWrap(true);
		card4.add(textAreaMentorComment);

		JScrollPane jScrollPaneForMentorComment = new JScrollPane();
		jScrollPaneForMentorComment.setBounds(151, 248, 287, 175);
		card4.add(jScrollPaneForMentorComment);

		JButton btnSaveComment = new JButton("Değerlendirmeyi Kaydet");
		btnSaveComment.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnSaveComment.setForeground(new Color(20, 61, 105));
		btnSaveComment.setBackground(new Color(255, 255, 255));
		btnSaveComment.setBounds(189, 437, 200, 30);
		card4.add(btnSaveComment);
		btnSaveComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mentorComment = textAreaMentorComment.getText();
				selectedMission = missionIdMap.get(gorevList.getSelectedIndex());

				if (selectedTc != null && selectedMission != null) {
					MentorManager.degerlendirmeGir(selectedTc, selectedMission.getId(), mentorComment);
					JOptionPane.showMessageDialog(null, "Değerlendirme başarıyla kaydedildi!");
					textAreaMentorComment.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Lütfen önce bir görev ve stajyer seçin.");
				}
			}
		});
		
		
		
		
	}

	private void updateMissionList(String tc) {
		// `kartPanel1` içindeki üçüncü panel olan `card3BackgroundPanel`'e erişiyoruz.
		JPanel card3BackgroundPanel = (JPanel) kartPanel1.getComponent(2);

		// `card3BackgroundPanel` içindeki `card3` paneline erişiyoruz.
		JPanel card3 = (JPanel) card3BackgroundPanel.getComponent(0);

		// `card3` içinde yer alan `scrollPane`'in viewport içindeki `missionPanel`ine
		// erişiyoruz.
		JScrollPane scrollPane = (JScrollPane) card3.getComponent(0);
		JPanel missionPanel = (JPanel) scrollPane.getViewport().getView();

		missionPanel.removeAll();
		missionIdMap.clear();

		List<Mission> gorevDetails = MentorManager.getTaskDetails(tc);

		if (gorevDetails.isEmpty()) {
			JLabel noTaskLabel = new JLabel("Görev yok.");
			noTaskLabel.setForeground(Color.white);
			missionPanel.add(noTaskLabel);
		} else {
			DefaultListModel<String> taskListModel = new DefaultListModel<>();
			int index = 1;

			for (Mission mission : gorevDetails) {
				taskListModel.addElement(index + ". " + mission.getTitle());
				missionIdMap.put(index - 1, mission);
				index++;
			}

			JList<String> taskList = new JList<>(taskListModel);
			taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			// Sağ tıklama menüsü oluştur
			JPopupMenu popupMenu = new JPopupMenu();
			JMenuItem deleteItem = new JMenuItem("Sil");
			deleteItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedIndex = taskList.getSelectedIndex();
					if (selectedIndex != -1) {
						Mission missionToDelete = missionIdMap.get(selectedIndex);
						boolean success = MentorManager.deleteMission(missionToDelete.getId());
						if (success) {
							JOptionPane.showMessageDialog(null, "Görev başarıyla silindi!");
							updateMissionList(tc); // Görev listesini güncelle
						} else {
							JOptionPane.showMessageDialog(null, "Görev silinirken bir hata oluştu.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Silinecek görev seçilmedi.");
					}
				}
			});
			popupMenu.add(deleteItem);

			// Sağ tıklama menüsünü JList'e ekle
			taskList.setComponentPopupMenu(popupMenu);

			// Sağ tıklama menüsünü açmadan önce seçili öğeyi vurgulama
			taskList.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						int listIndex = taskList.locationToIndex(e.getPoint());
						if (listIndex != -1) {
							taskList.setSelectedIndex(listIndex);
						}
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)) {
						int listIndex = taskList.locationToIndex(e.getPoint());
						selectedMission = missionIdMap.get(listIndex);

						if (selectedMission != null) {
							JTextArea textArea = new JTextArea(selectedMission.getFormattedDetails());
							textArea.setEditable(false);
							textArea.setLineWrap(true);
							textArea.setMargin(new Insets(10, 10, 10, 10));
							textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
							textArea.setPreferredSize(new Dimension(600, 100));

							JTextArea contentArea = new JTextArea("Yanıt: "
									+ (selectedMission.getGorev_content() != null ? selectedMission.getGorev_content()
											: "Henüz yanıtlanmamış"));
							contentArea.setEditable(false);
							contentArea.setMargin(new Insets(10, 10, 10, 10));
							contentArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
							contentArea.setPreferredSize(new Dimension(600, 100));
							contentArea.setLineWrap(true);

							JPanel missionDetailsPanel = new JPanel();
							missionDetailsPanel.setLayout(new BoxLayout(missionDetailsPanel, BoxLayout.Y_AXIS));
							missionDetailsPanel.add(textArea);
							missionDetailsPanel.add(contentArea);

							JScrollPane detailsScrollPane = new JScrollPane(missionDetailsPanel);
							detailsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
							detailsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							detailsScrollPane.setPreferredSize(new Dimension(600, 300)); // Yüksekliği artırdık

							card3.removeAll();
							card3.add(detailsScrollPane, BorderLayout.CENTER);
							card3.revalidate();
							card3.repaint();
						}
					}
				}
			});

			JScrollPane listScrollPane = new JScrollPane(taskList);
			listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			listScrollPane.setPreferredSize(new Dimension(600, 400)); // Yüksekliği belirledik

			missionPanel.add(listScrollPane);
		}

		missionPanel.revalidate();
		missionPanel.repaint();
	}
}
