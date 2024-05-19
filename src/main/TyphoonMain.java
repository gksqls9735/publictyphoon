package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import controller.DBUtil;
import controller.TyphoonDAO;
import controller.TyphoonRegisterManager;
import model.TyphoonVO;
import view.MENU_CHOICE;
import view.MenuViewer;

public class TyphoonMain {

	public static ArrayList<TyphoonVO> typList = null;
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
					typList = webConnect();
					break;
				case MENU_CHOICE.SAVE:
					if (typList.size() < 1) {
						System.out.println("공공데이터로부터 가져온 자료가 없습니다.");
					}
					trm.insertTyphoonInfo(typList);
					break;
				case MENU_CHOICE.GETTABLE:
					typList = trm.selectTyphoonInfo();
					System.out.println("테이블 가져오기 성공");
					break;
				case MENU_CHOICE.SEARCH:
					trm.searchTyphoon();
					break;
				case MENU_CHOICE.UPDATE:
					trm.updateTyphoonInfo();
					typList = trm.selectTyphoonInfo();
					break;
				case MENU_CHOICE.DELETE:
					trm.deleteTyphoon();
					typList = trm.selectTyphoonInfo();
					break;
				case MENU_CHOICE.EXIT:
					return;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public static ArrayList<TyphoonVO> webConnect() {
		ArrayList<TyphoonVO> list = new ArrayList<TyphoonVO>();

		// 요청 url 생성
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1360000/SfcYearlyInfoService/getTyphoonList");

		try {
			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
					+ "=tHGuXKnUrfjRXdJNLC%2BNJRlnd1DOMY%2B4lIapNAuiJa17%2BESaOVn38TYiW0XqpLrcDANlMhkXZl2iw%2Fdr7fftxA%3D%3D");
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("year", "UTF-8") + "=" + URLEncoder.encode("2016", "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 커넥션 생성
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(urlBuilder.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 요청전송 및 응답 처리

		BufferedReader br = null;

		try {
			int StatusCode = conn.getResponseCode();
			System.out.println(StatusCode);
			if (StatusCode >= 200 && StatusCode <= 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			Document doc = parseXML(conn.getInputStream());
			
			//item 태그객체 목록으로 가져오기
			NodeList descNodes = doc.getElementsByTagName("info");
			// 각 item 태그의 자식 태그에서 정보 가져오기
			for(int i = 0; i < descNodes.getLength(); i++) {
				//item
				Node item = descNodes.item(i);
				TyphoonVO data = new TyphoonVO();
				
				//item 태그에 순차적으로 접근
				for(Node node = item.getFirstChild(); node!= null; node = node.getNextSibling()) {
//					System.out.println(node.getNodeName() + " : " + node.getTextContent());
					
					switch(node.getNodeName()) {
					 case "typ_seq":
						 data.setTyp_seq(Integer.parseInt(node.getTextContent()));                
                         break;
                     case "typ_en":
                    	 data.setTyp_en(node.getTextContent());
                         break;
                     case "tm_st":
                    	 data.setTm_st(node.getTextContent());
                         break;
                     case "tm_ed":
                    	 data.setTm_ed(node.getTextContent());
                         break;
                     case "typ_ps":
                    	 data.setTyp_ps(Integer.parseInt(node.getTextContent()));
                         break;
                     case "typ_ws":
                    	 data.setTyp_ws(Integer.parseInt(node.getTextContent()));
                         break;
                     case "typ_name":
                    	 data.setTyp_name(node.getTextContent());
                         break;
                     case "eff":
                    	 data.setEff(Integer.parseInt(node.getTextContent()));
                         break;
					}
				}
				list.add(data);
			}
			
			for(TyphoonVO d : list) {
				System.out.println(d);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		conn.disconnect();
		return list;

	}

	public static Document parseXML(InputStream inputStream) {
		DocumentBuilderFactory objDocumentBuliderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder objDocumentBulider = null;
		Document doc = null;
		
		try {
			objDocumentBulider = objDocumentBuliderFactory.newDocumentBuilder();
			doc = objDocumentBulider.parse(inputStream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
