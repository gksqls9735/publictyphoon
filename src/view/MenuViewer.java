package view;

import java.util.Scanner;

public class MenuViewer {
	public static Scanner choice = new Scanner(System.in);

	public static void mainMenuView() {
		System.out.println();
		System.out.println("태풍 조회 프로그램");
		System.out.println("해당 번호를 입력하세요.");
		System.out.println("1. 웹 정보 가져오기");
		System.out.println("2. 정보 저장하기");
		System.out.println("3. 테이블 읽어오기");
		System.out.println("4. 조회하기");
		System.out.println("5. 수정하기");
		System.out.println("6. 개별 삭제하기");
		System.out.println("7. 전체 삭제하기");
		System.out.println("8. 종료하기");
		System.out.print("번호 선택: ");
	}
	
}
