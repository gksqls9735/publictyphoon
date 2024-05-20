package main;

import java.util.ArrayList;
import java.util.Scanner;

import controller.TyphoonRegisterManager;
import controller.Web;
import model.TyphoonVO;
import view.MENU_CHOICE;
import view.MenuViewer;

public class TyphoonMain {

	public static ArrayList<TyphoonVO> typList = null;
	public static ArrayList<TyphoonVO> typSelectList = null;
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		mainMenu();
	}

	private static void mainMenu() {
		int choice = 0;
		TyphoonRegisterManager trm = new TyphoonRegisterManager();
		while (true) {
			try {
				MenuViewer.mainMenuView();
				choice = Integer.parseInt(MenuViewer.choice.nextLine());

				switch (choice) {
				case MENU_CHOICE.WEBCONNECT:
					typList = Web.webConnect();
					break;
				case MENU_CHOICE.SAVE:
					if (typList.size() < 1) {
						System.out.println("공공데이터로부터 가져온 자료가 없습니다.");
					}
					if (trm.checkTblInfo() == true) {
						trm.allDeleteTyphoonInfo();
					}
					trm.insertTyphoonInfo(typList);
					break;
				case MENU_CHOICE.GETTABLE:
					typSelectList = trm.selectTyphoonInfo();
					if (typSelectList.size() > 0) {
						System.out.println("테이블 가져오기 성공");
					}
					break;
				case MENU_CHOICE.SEARCH:
					trm.searchTyphoonInfo();
					break;
				case MENU_CHOICE.UPDATE:
					trm.updateTyphoonInfo(typSelectList);
					typSelectList = trm.selectTyphoonInfo();
					break;
				case MENU_CHOICE.DELETE:
					trm.deleteTyphoonInfo(typSelectList);
					typSelectList = trm.selectTyphoonInfo();
					break;
				case MENU_CHOICE.ALLDELETE:
					trm.allDeleteTyphoonInfo();
					if (typSelectList != null) {
						typSelectList.clear();
					}
					break;
				case MENU_CHOICE.EXIT:
					System.out.println("종료");
					return;
				default:
					System.out.println("올바른 메뉴를 선택해주세요.");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
