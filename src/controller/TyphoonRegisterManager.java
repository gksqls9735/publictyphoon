package controller;

import java.util.ArrayList;
import java.util.Scanner;

import model.TyphoonVO;

public class TyphoonRegisterManager {

	public static Scanner sc = new Scanner(System.in);

	public static boolean checkTblInfo() {
		TyphoonDAO td = new TyphoonDAO();
		return td.checktbl();
	}
	
	public static void insertTyphoonInfo(ArrayList<TyphoonVO> typList) {
		TyphoonDAO td = new TyphoonDAO();
		td.insertTyphoon(typList);
	}

	public static ArrayList<TyphoonVO> selectTyphoonInfo() {
		TyphoonDAO td = new TyphoonDAO();
		return td.selectTyphoon();
	}

	public static void updateTyphoonInfo(ArrayList<TyphoonVO> typSelectList) {
		TyphoonDAO td = new TyphoonDAO();
		String header = String.format(
			    "%-5s %-15s %-12s %-12s %-10s %-10s %-10s %-12s",
			    "번호", "영어이름", "생성일", "소멸일", "최저 기압", "최대 풍속", "태풍이름", "한반도 영향"
			);
		System.out.println(header);
		System.out.println("=====================================================================================================================================================");
		for(TyphoonVO data : typSelectList) {
			System.out.println(data);
		}
		System.out.println("=====================================================================================================================================================");
		System.out.println();
		System.out.print("업데이트할 항목의 번호 입력: ");
		int typ_seq = Integer.parseInt(sc.nextLine());
		System.out.print("변경할 정보 입력");
		System.out.print("태풍의 영어 이름: ");
		String typ_en = sc.nextLine();
		System.out.print("태풍의 이름(한글): ");
		String typ_name = sc.nextLine();
		td.updateTyphoon(typ_seq, typ_en, typ_name);
	}

	public static void searchTyphoonInfo() {
		TyphoonDAO td = new TyphoonDAO();
		System.out.print("검색할 태풍의 이름을 입력해주세요: ");
		String typ_name = sc.nextLine();
		td.searchTyphoon(typ_name);
	}

	public static void deleteTyphoonInfo(ArrayList<TyphoonVO> typSelectList) {
		TyphoonDAO td = new TyphoonDAO();
		System.out.println("=====================================================================================================================================================");
		for(TyphoonVO data : typSelectList) {
			System.out.println(data);
		}
		System.out.println("=====================================================================================================================================================");
		System.out.println();
		System.out.print("삭제할 태풍의 번호를 입력해주세요: ");
		int typ_seq = Integer.parseInt(sc.nextLine());
		td.deleteTyphoon(typ_seq);
	}

	public static void allDeleteTyphoonInfo() {
		TyphoonDAO td = new TyphoonDAO();
		td.alldeleteTyphoon();
	}

}