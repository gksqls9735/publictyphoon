package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.TyphoonVO;

public class TyphoonDAO {

	public static boolean checktbl() {
		String sql = "SELECT COUNT(*) FROM TYPH";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if(rs.getInt("COUNT(*)") != 0) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public static void insertTyphoon(ArrayList<TyphoonVO> typSelectList) {
		if (typSelectList.size() < 1) {
			System.out.println("입력할 데이터가 없습니다.");
			return;
		}
		String sql = "INSERT INTO TYPH VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);

			for (TyphoonVO data : typSelectList) {
				pstmt.setInt(1, data.getTyp_seq());
				pstmt.setString(2, data.getTyp_en());
				pstmt.setString(3, data.getTm_st());
				pstmt.setString(4, data.getTm_ed());
				pstmt.setInt(5, data.getTyp_ps());
				pstmt.setInt(6, data.getTyp_ws());
				pstmt.setString(7, data.getTyp_name());
				pstmt.setInt(8, data.getEff());

				int i = pstmt.executeUpdate();

				if (i == 1) {
					System.out.println(data.getTyp_seq() + "번 정보등록 완료");
				} else {
					System.out.println(data.getTyp_seq() + "번 정보등록 실패");
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<TyphoonVO> selectTyphoon() {
		ArrayList<TyphoonVO> list = new ArrayList<TyphoonVO>();
		String sql = "SELECT * FROM TYPH ORDER BY TYP_SEQ ASC";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			System.out.println("=====================================================================================================================================================");
			while (rs.next()) {
				TyphoonVO typ = new TyphoonVO();

				typ.setTyp_seq(rs.getInt("TYP_SEQ"));
				typ.setTyp_en(rs.getString("TYP_EN"));
				typ.setTm_st(rs.getString("TM_ST"));
				typ.setTm_ed(rs.getString("TM_ED"));
				typ.setTyp_ps(rs.getInt("TYP_PS"));
				typ.setTyp_ws(rs.getInt("TYP_WS"));
				typ.setTyp_name(rs.getString("TYP_NAME"));
				typ.setEff(rs.getInt("EFF"));
				System.out.println(typ);
				list.add(typ);
			}
			System.out.println("=====================================================================================================================================================");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static void updateTyphoon(int typ_seq, String typ_en, String typ_name) {
		if (typ_seq == 0) {
			return;
		}

		String sql = "UPDATE TYPH SET TYP_EN = ?, TYP_NAME = ? WHERE TYP_SEQ = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, typ_en);
			pstmt.setString(2, typ_name);
			pstmt.setInt(3, typ_seq);

			int value = pstmt.executeUpdate();

			if (value == 1) {
				System.out.println(typ_seq + "번 수정완료");
			} else {
				System.out.println(typ_seq + "번 수정실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void searchTyphoon(String typ_name) {
		String sql = "SELECT * FROM TYPH WHERE TYP_NAME = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, typ_name);
			rs = pstmt.executeQuery();

			if (!rs.next()) {
				System.out.println(typ_name + "에 해당하는 태풍은 존재하지 않습니다.");
			} else {
				do {
					TyphoonVO typ = new TyphoonVO();

					typ.setTyp_seq(rs.getInt("TYP_SEQ"));
					typ.setTyp_en(rs.getString("TYP_EN"));
					typ.setTm_st(rs.getString("TM_ST"));
					typ.setTm_ed(rs.getString("TM_ED"));
					typ.setTyp_ps(rs.getInt("TYP_PS"));
					typ.setTyp_ws(rs.getInt("TYP_WS"));
					typ.setTyp_name(rs.getString("TYP_NAME"));
					typ.setEff(rs.getInt("EFF"));

					System.out.println(typ);

				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteTyphoon(int typ_seq) {
		String sql = "DELETE FROM TYPH WHERE TYP_SEQ = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, typ_seq);

			int i = pstmt.executeUpdate();
			if (i != 0) {
				System.out.println("정보 삭제완료");
			} else {
				System.out.println("정보 삭제실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void alldeleteTyphoon() {
		String sql = "DELETE FROM TYPH";

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);

			int i = pstmt.executeUpdate();
			if (i > 0) {
				System.out.println("정보 삭제완료");
			} else {
				System.out.println("정보 삭제실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
