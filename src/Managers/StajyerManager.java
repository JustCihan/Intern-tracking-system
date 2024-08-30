package Managers;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import javax.swing.JOptionPane;
import java.util.Arrays;
import Mainpackage.DbHelper;
import Objects.Stajyer;
import Objects.User;
import Objects.Mission;

public class StajyerManager {
	User user = null;
	static DbHelper dbHelper = new DbHelper();
	static Statement statement = null;
	static boolean isSucsess;

	public Stajyer login(String tc, String sifre) {
		String query = "SELECT * from stajyer where tc= ? and sifre= ? ";
		try (Connection connection = dbHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, tc);
			statement.setString(2, sifre);
			try (ResultSet resultSet = statement.executeQuery()) {
				

				if (resultSet.next()) {
					Stajyer stajyer = new Stajyer();
					stajyer.setAd(resultSet.getString("ad"));
					stajyer.setSoyad(resultSet.getString("soyad"));
					stajyer.setSifre(resultSet.getString("sifre"));
					stajyer.setePosta(resultSet.getString("e_posta"));
					stajyer.setBolum(resultSet.getString("bolum"));
					stajyer.setOkul(resultSet.getString("okul"));
					stajyer.setOkulBolum(resultSet.getString("okul_bolum"));
					stajyer.setSinif(resultSet.getInt("sinif"));
					stajyer.setTelefon(resultSet.getString("telefon"));
					stajyer.setTc(resultSet.getString("tc"));
					return stajyer;
				}

			}
		} catch (SQLException sqlException) {
	        JOptionPane.showMessageDialog(null, "Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
	        System.out.println("SQLException: " + sqlException.getMessage());
	    } catch (Exception exception) {
	        JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
	        System.out.println("Exception: " + exception.getMessage());
	    }
		return null;
	}

	public static void signin(String ad, String soyad, String nickname, String sifre, String tc, String okul,
			String okul_bolum, int sinif, String telefon, String ePosta) throws Exception {
		String update = "INSERT INTO stajyer (ad, soyad, nickname, sifre, tc, okul, okul_bolum, sinif, telefon, e_posta) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		isSucsess = false;
		try (Connection connection = dbHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(update)) {
			statement.setString(1, ad);
			statement.setString(2, soyad);
			statement.setString(3, nickname);
			statement.setString(4, sifre);
			
			System.out.print("Efe"+tc+ "EFe");
			
			statement.setString(5, tc);
			
			statement.setString(6, okul);
			statement.setString(7, okul_bolum);
			statement.setInt(8, sinif);
			statement.setString(9, telefon);
			statement.setString(10, ePosta);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 1 ) {
				isSucsess = true;
			} else {
				JOptionPane.showMessageDialog(null, "Hatalı bir giriş yaptınız");
				
			}
		} catch (SQLException sqlException) {
	        JOptionPane.showMessageDialog(null, "Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
	        System.out.println("SQLException: " + sqlException.getMessage());
	    } catch (Exception exception) {
	        JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
	        System.out.println("Exception: " + exception.getMessage());
	    }
	}

	public static List<String> getBolum() {
		List<String> departments = new ArrayList<>();
		String query = "SELECT bolum_id, name FROM universite_bolum";
		try (Connection connection = dbHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				int id = resultSet.getInt("bolum_id");
				String name = resultSet.getString("name");
				departments.add(name);

			}

		}catch (SQLException sqlException) {
	        JOptionPane.showMessageDialog(null, "Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
	        System.out.println("SQLException: " + sqlException.getMessage());
	    } catch (Exception exception) {
	        JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
	        System.out.println("Exception: " + exception.getMessage());
	    }
		return departments; 
	}

	public static void gorevGonder(String tc, int gorevId, String gorevContent) {
		String gorevIdQuery = "UPDATE stajyer_gorev SET gorev_content = ? WHERE stajyer_id = ? and gorev_id = ?";
		String gorevControl = "SELECT gorev_content FROM stajyer_gorev WHERE stajyer_id = ? and gorev_id = ?";
		String tarihGuncelle = "UPDATE stajyer_gorev SET tarih = CURRENT_TIMESTAMP WHERE gorev_id = ?";
		String zamantKontrol ="Select due_date from gorevler where id = ?";
		try (Connection connection = dbHelper.getConnection(); 
				PreparedStatement statement = connection.prepareStatement(gorevIdQuery);
				PreparedStatement statement2 = connection.prepareStatement(gorevControl);
				PreparedStatement statement3 = connection.prepareStatement(tarihGuncelle);
			PreparedStatement statement4 = connection.prepareStatement(zamantKontrol))
		{
			LocalDate today = LocalDate.now();
			statement4.setInt(1, gorevId);
			statement3.setInt(1, gorevId);
			statement2.setString(1, tc);
			statement2.setInt(2, gorevId);
			Date zamantKontrol1 = null; 
			ResultSet resultSet2 = statement4.executeQuery();

			while(resultSet2.next()) {
				zamantKontrol1 = resultSet2.getDate("due_date");

			}
				LocalDate zamantKontrolLocalDate  = zamantKontrol1.toLocalDate();
				System.out.println(zamantKontrolLocalDate);
				System.out.println(today);
				if (zamantKontrolLocalDate.isAfter(today)) {
					try (ResultSet resultSet = statement2.executeQuery()) {
						if (resultSet.next()) {
							String existingContent = resultSet.getString("gorev_content");
							
							int rowsUpdate = statement3.executeUpdate();
							if(rowsUpdate > 0 ) {
							}else{								
							}

							if (existingContent != null && !existingContent.trim().isEmpty()) {
								JOptionPane.showMessageDialog(null,
										"<html>Gorev Tamamlandı düzenlemek ister misiniz? <br> Düzemlemek için düzenleme butonuna tıklayın. </html>");
							} else {
								statement.setString(1, gorevContent);
								statement.setString(2, tc);
								statement.setInt(3, gorevId);

								int rowsAffected = statement.executeUpdate();
								if (rowsAffected > 0) {
									JOptionPane.showMessageDialog(null, "Görev başarıyla gönderildi.");
								} else {
									JOptionPane.showMessageDialog(null, "Görev gönderilirken bir hata oluştu.");
								} 
							}
						} else {
							JOptionPane.showMessageDialog(null, "Belirtilen ID ile görev bulunamadı.");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
					
				}else {
					JOptionPane.showMessageDialog(null, "Gorevin zamani dolduktan sonra gönderemezsiniz");
				}
			

			
		}catch (SQLException sqlException) {
	        JOptionPane.showMessageDialog(null, "Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
	        System.out.println("SQLException: " + sqlException.getMessage());
	    } catch (Exception exception) {
	        JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
	        System.out.println("Exception: " + exception.getMessage());
	    }
	}

	public static List<String> getOkul() {
		List<String> university = new ArrayList<>();
		String query = "SELECT universite_id, name FROM universite";
		try (Connection connection = dbHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("universite_id");
				String name = resultSet.getString("name");
				university.add(name);
			}
		} catch (SQLException sqlException) {
	        JOptionPane.showMessageDialog(null, "Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
	        System.out.println("SQLException: " + sqlException.getMessage());
	    } catch (Exception exception) {
	        JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
	        System.out.println("Exception: " + exception.getMessage());
	    }
		return university;
	}

	public static List<Integer> getSinif() {
		List<Integer> classes = new ArrayList<>();
		String query = "SELECT sinif_id, sinif FROM sinif";
		try (Connection connection = dbHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				int id = resultSet.getInt("sinif_id");
				int name = resultSet.getInt("sinif");
				classes.add(name);

			}

		} catch (SQLException sqlException) {
	        JOptionPane.showMessageDialog(null, "Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
	        System.out.println("SQLException: " + sqlException.getMessage());
	    } catch (Exception exception) {
	        JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
	        System.out.println("Exception: " + exception.getMessage());
	    }

		return classes;
	}  

	public List<Mission> getStajyerGorev(String tc) {
		List<Mission> gorevListesi = new ArrayList<>();
		Mission mission = new Mission();
		String gorevIdQuery = "SELECT gorev_id, mentor_answer FROM stajyer_gorev WHERE stajyer_id = ?";
		String gorevDetayQuery = "SELECT * FROM gorevler WHERE id = ?";

		try (Connection connection = dbHelper.getConnection();
				PreparedStatement gorevIdStatement = connection.prepareStatement(gorevIdQuery)) {
			gorevIdStatement.setString(1, tc);
			try (ResultSet gorevIdResultSet = gorevIdStatement.executeQuery()) {
				while (gorevIdResultSet.next()) {
					mission = new Mission();
					mission.setMentorAnswer(gorevIdResultSet.getString("mentor_answer"));
					mission.setId(gorevIdResultSet.getInt("gorev_id"));
					try (PreparedStatement gorevDetayStatement = connection.prepareStatement(gorevDetayQuery)) {
						gorevDetayStatement.setInt(1, mission.getId());
						try (ResultSet gorevDetayResultSet = gorevDetayStatement.executeQuery()) {
							if (gorevDetayResultSet.next()) {
								mission.setId(gorevDetayResultSet.getInt("id"));
								mission.setTitle(gorevDetayResultSet.getString("title"));
								mission.setDescription(gorevDetayResultSet.getString("description"));
								mission.setDueDate(gorevDetayResultSet.getDate("due_date"));
								gorevListesi.add(mission);

							}
						}
					}
				}
			}
		} catch (SQLException sqlException) {
	        JOptionPane.showMessageDialog(null, "Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
	        System.out.println("SQLException: " + sqlException.getMessage());
	    } catch (Exception exception) {
	        JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
	        System.out.println("Exception: " + exception.getMessage());
	    }

		return gorevListesi;
	}

	public static void gorevSil(String tc, int gorevId) {
		String gorevSilQuery = "UPDATE stajyer_gorev SET gorev_content = NULL WHERE stajyer_id = ? AND gorev_id = ?";
		try (Connection connection = dbHelper.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(gorevSilQuery)) {
			preparedStatement.setString(1, tc);
			preparedStatement.setInt(2, gorevId);
			preparedStatement.executeUpdate();
		} catch (SQLException sqlException) {
	        JOptionPane.showMessageDialog(null, "Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
	        System.out.println("SQLException: " + sqlException.getMessage());
	    } catch (Exception exception) {
	        JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
	        System.out.println("Exception: " + exception.getMessage());
	    }
	}

	public static String yapilanGoreviGetir(String tc, int gorevId) {
		String gorevQuery = "Select gorev_content from stajyer_gorev where stajyer_id= ? and gorev_id = ?  ";

		try (Connection connection = dbHelper.getConnection();
				PreparedStatement gorevStatement = connection.prepareStatement(gorevQuery)) {
			gorevStatement.setString(1, tc);
			gorevStatement.setInt(2, gorevId);
			try (ResultSet resultSet = gorevStatement.executeQuery();) {
				if (resultSet.next()) {
					String gorev = resultSet.getString("gorev_content");

					return gorev;
				}
			} catch (Exception exception) {

				exception.printStackTrace();
			}
		} catch (SQLException sqlException) {
	        JOptionPane.showMessageDialog(null, "Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
	        System.out.println("SQLException: " + sqlException.getMessage());
	    } catch (Exception exception) {
	        JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
	        System.out.println("Exception: " + exception.getMessage());
	    }
		return null;
	}

	public boolean sifreControl(char[] birinciSifre, char[] ikinciSifre) {
		try {
			return !Arrays.equals(birinciSifre, ikinciSifre);
		} finally {
			Arrays.fill(birinciSifre, '\0');
			Arrays.fill(ikinciSifre, '\0');
		}

	}

}