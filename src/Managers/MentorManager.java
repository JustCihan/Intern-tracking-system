package Managers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Mainpackage.DbHelper;
import Objects.Mentor;
import Objects.Mission;

public class MentorManager {
	private static DbHelper dbHelper = new DbHelper();
	static String stajyerTc1;
	static boolean isSuccess;
	private static Mission mission;

	public Mentor login(String adi, String sifre) {
		String query = "SELECT * FROM mentor WHERE adi = ? AND sifre = ?";

		try (Connection connection = dbHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, adi);
			statement.setString(2, sifre);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Mentor mentor = new Mentor();
					mentor.setAd(resultSet.getString("adi"));
					mentor.setSoyad(resultSet.getString("soyadi"));
					mentor.setBolum(resultSet.getString("bolum"));
					mentor.setePosta(resultSet.getString("e_posta"));
					mentor.setSifre(resultSet.getString("sifre"));
					return mentor;
				}
			}
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(null,
					"Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
			System.out.println("SQLException: " + sqlException.getMessage());
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
			System.out.println("Exception: " + exception.getMessage());
		}
		return null;
	}

	public static boolean submitTask(String title, String description, Date dueDate, String stajyerTc) {
		String insertMissionSql = "INSERT INTO gorevler (title, description, due_date) VALUES (?, ?, ?)";
		String selectLastIdSql = "SELECT currval(pg_get_serial_sequence('gorevler', 'id'))";
		String checkExistenceSql = "SELECT 1 FROM stajyer_gorev WHERE stajyer_id = ? AND gorev_id = ?";
		String assignTaskSql = "INSERT INTO stajyer_gorev (stajyer_id, gorev_id) VALUES (?, ?)";

		try (Connection conn = dbHelper.getConnection();
				PreparedStatement insertMissionStmt = conn.prepareStatement(insertMissionSql);
				PreparedStatement selectLastIdStmt = conn.prepareStatement(selectLastIdSql);
				PreparedStatement checkExistenceStmt = conn.prepareStatement(checkExistenceSql);
				PreparedStatement assignTaskStmt = conn.prepareStatement(assignTaskSql)) {

			// Görevi ekle
			insertMissionStmt.setString(1, title);
			insertMissionStmt.setString(2, description);
			insertMissionStmt.setDate(3, dueDate);
			int affectedRows = insertMissionStmt.executeUpdate();

			if (affectedRows > 0) {
				// Son eklenen görevin ID'sini al
				int missionId;
				try (ResultSet rs = selectLastIdStmt.executeQuery()) {
					if (rs.next()) {
						missionId = rs.getInt(1);

						// Önceden kayıt olup olmadığını kontrol et
						checkExistenceStmt.setString(1, stajyerTc);
						checkExistenceStmt.setInt(2, missionId);
						try (ResultSet rsCheck = checkExistenceStmt.executeQuery()) {
							if (rsCheck.next()) {
								// Görev zaten atanmış
								System.out.println("Bu görev zaten atandı.");
								return false;
							} else {
								// Görevi stajyere ata
								assignTaskStmt.setString(1, stajyerTc);
								assignTaskStmt.setInt(2, missionId);
								int assignAffectedRows = assignTaskStmt.executeUpdate();

								// Görev başarıyla atandı mı kontrol et
								return assignAffectedRows > 0;
							}
						}
					} else {
						return false;
					}
				}
			}
			return false;
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(null,
					"Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
			System.out.println("SQLException: " + sqlException.getMessage());
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
			System.out.println("Exception: " + exception.getMessage());
		}
		return isSuccess;
	}

	public static void assignTaskToIntern(List<String> stajyerTc) {
		String selectLastGorevSQL = "SELECT id FROM gorevler ORDER BY id DESC LIMIT 1";
		String checkExistenceSQL = "SELECT 1 FROM stajyer_gorev WHERE stajyer_id = ? AND gorev_id = ?";
		String insertSQL = "INSERT INTO stajyer_gorev (gorev_id, stajyer_id) VALUES (?, ?)";

		for (String bilgiler : stajyerTc) {
			String tc = MentorManager.extractTC(bilgiler);

			try (Connection connection = dbHelper.getConnection();
					PreparedStatement selectStmt = connection.prepareStatement(selectLastGorevSQL);
					ResultSet rs = selectStmt.executeQuery()) {

				if (rs.next()) {
					int lastGorevId = rs.getInt("id");

					// Önce görevin zaten atanıp atanmadığını kontrol et
					try (PreparedStatement checkStmt = connection.prepareStatement(checkExistenceSQL)) {
						checkStmt.setString(1, tc);
						checkStmt.setInt(2, lastGorevId);
						try (ResultSet checkRs = checkStmt.executeQuery()) {
							if (checkRs.next()) {

								return;
							}
						}
					}

					// Görevi stajyere ata
					try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
						insertStmt.setInt(1, lastGorevId);
						insertStmt.setString(2, tc);
						insertStmt.executeUpdate();
					}
				}
			} catch (SQLException sqlException) {
				JOptionPane.showMessageDialog(null,
						"Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
				System.out.println("SQLException: " + sqlException.getMessage());
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
				System.out.println("Exception: " + exception.getMessage());
			}
		}
	}

	public static List<String> getTasksByIdAndDate(Date baslangic, Date bitis, String StajyerTc) {
		List<String> gorevContnetList = new ArrayList<>();
		String GorevGetirQuery = "Select gorev_content,tarih from stajyer_gorev where tarih BETWEEN ? and ? and stajyer_id = ?";
		try (Connection connection = dbHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(GorevGetirQuery);) {
			statement.setDate(1, baslangic);
			statement.setDate(2, bitis);
			statement.setString(3, StajyerTc);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				mission = new Mission();
				mission.setDueDate(resultSet.getDate("tarih"));
				mission.setGorev_content(resultSet.getString("gorev_content"));
				gorevContnetList.add("Tarih :" + mission.getDueDate() +"\n"+ "Gorev İçerigi : \n"
						+ mission.getGorev_content() + "\n");
			}

		} catch (SQLException exception) {
			JOptionPane.showMessageDialog(null,
					"Bilinmeyen bir hata olustu lütfen gerekli birime iletin veya tekrar deneyin");
			exception.printStackTrace();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Tüm bilgileri tam girdiginizden emin olunuz");
			exception.printStackTrace();
		}
		return gorevContnetList;

	}

	public static void degerlendirmeGir(String tc, int gorevId, String mentorAnswer) {
		String query = "UPDATE stajyer_gorev SET mentor_answer = ? WHERE stajyer_id = ? AND gorev_id = ?";

		try (Connection connection = dbHelper.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, mentorAnswer);
			statement.setString(2, tc);
			statement.setInt(3, gorevId);

			statement.executeUpdate(); // Sonuçları kontrol etmiyoruz
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(null,
					"Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
			System.out.println("SQLException: " + sqlException.getMessage());
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
			System.out.println("Exception: " + exception.getMessage());
		}
	}

	public static List<String> getIntern() {
		List<String> stajyerList = new ArrayList<>();
		String sql = "SELECT * FROM stajyer";
		try (Connection conn = dbHelper.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				stajyerList.add(rs.getString("tc") + " - " + rs.getString("ad") + " " + rs.getString("soyad"));
			}
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(null,
					"Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
			System.out.println("SQLException: " + sqlException.getMessage());
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
			System.out.println("Exception: " + exception.getMessage());
		}
		return stajyerList;
	}

	public static List<Mission> getTaskDetails(String tc) {
		List<Mission> missions = new ArrayList<>();
		String sql = "SELECT g.id, g.title, g.description, g.due_date, g.completed, sg.gorev_content "
				+ "FROM gorevler g " + "JOIN stajyer_gorev sg ON g.id = sg.gorev_id " + "WHERE sg.stajyer_id = ?";

		if (tc == null || tc.trim().isEmpty()) {
			System.err.println("‘tc’ parametresi null veya boş.");
			return missions;
		}

		try (Connection conn = dbHelper.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, tc);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Mission mission = new Mission();
					mission.setId(rs.getInt("id"));
					mission.setTitle(rs.getString("title"));
					mission.setDescription(rs.getString("description"));
					mission.setDueDate(rs.getDate("due_date"));
					mission.setCompleted(rs.getBoolean("completed"));
					mission.setGorev_content(rs.getString("gorev_content"));

					boolean exists = missions.stream().anyMatch(m -> m.getTitle().equals(mission.getTitle()));
					if (!exists) {
						missions.add(mission);
					}
				}
			}
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(null,
					"Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
			System.out.println("SQLException: " + sqlException.getMessage());
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
			System.out.println("Exception: " + exception.getMessage());
		}

		return missions;
	}
	public static String getTaskForMentor(int missionId) {
		String sql = "SELECT gorev_content FROM stajyer_gorev WHERE gorev_id = ?";
		try (Connection conn = dbHelper.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, missionId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("gorev_content");
				}
			}
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(null,
					"Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
			System.out.println("SQLException: " + sqlException.getMessage());
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
			System.out.println("Exception: " + exception.getMessage());
		}
		return null;
	}

	public static String extractTC(String data) {
		return data.split(" - ")[0];
	}

	public static boolean deleteMission(int missionId) {
		String deleteFromStajyerGorevSql = "DELETE FROM stajyer_gorev WHERE gorev_id = ?";
		String deleteFromGorevlerSql = "DELETE FROM gorevler WHERE id = ?";

		try (Connection conn = dbHelper.getConnection();
				PreparedStatement deleteFromStajyerGorevStmt = conn.prepareStatement(deleteFromStajyerGorevSql);
				PreparedStatement deleteFromGorevlerStmt = conn.prepareStatement(deleteFromGorevlerSql)) {

			// Görev ilişkilendirmelerini sil
			deleteFromStajyerGorevStmt.setInt(1, missionId);
			int affectedRows1 = deleteFromStajyerGorevStmt.executeUpdate();

			// Görevi sil
			deleteFromGorevlerStmt.setInt(1, missionId);
			int affectedRows2 = deleteFromGorevlerStmt.executeUpdate();

			return affectedRows1 > 0 && affectedRows2 > 0;
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(null,
					"Şu anda sunucu kapalı veya erişilemiyor. Lütfen daha sonra tekrar deneyin.");
			System.out.println("SQLException: " + sqlException.getMessage());
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu.");
			System.out.println("Exception: " + exception.getMessage());
		}
		return false;
	}

}
