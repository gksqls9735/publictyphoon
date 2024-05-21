package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.TyphoonVO;

public class Web {

	public static Scanner sc = new Scanner(System.in);

	public static ArrayList<TyphoonVO> webConnect() {
		ArrayList<TyphoonVO> list = new ArrayList<TyphoonVO>();
		String year = null;
	
		do {
			System.out.print("연도를 입력해주세요(2015~2022): ");
			year = sc.nextLine();
			if (Integer.parseInt(year) >= 2015 && Integer.parseInt(year) <= 2022) {
				break;
			} else {
				System.out.println("연도를 다시 입력해주세요.");
			}
			
		} while (true);
		// 요청 url 생성
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1360000/SfcYearlyInfoService/getTyphoonList");

		try {
			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
					+ "=tHGuXKnUrfjRXdJNLC%2BNJRlnd1DOMY%2B4lIapNAuiJa17%2BESaOVn38TYiW0XqpLrcDANlMhkXZl2iw%2Fdr7fftxA%3D%3D");
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("year", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8"));
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

			String header = String.format(
				    "%-5s %-15s %-9s %-12s %-10s %-10s %-10s %-12s",
				    "번호", "영어이름", "생성일", "소멸일", "최저 기압", "최대 풍속", "태풍이름", "한반도 영향"
				);
			System.out.println(header);
			// item 태그객체 목록으로 가져오기
			NodeList descNodes = doc.getElementsByTagName("info");
			// 각 item 태그의 자식 태그에서 정보 가져오기
			for (int i = 0; i < descNodes.getLength(); i++) {
				// item
				Node item = descNodes.item(i);
				TyphoonVO data = new TyphoonVO();

				// item 태그에 순차적으로 접근
				for (Node node = item.getFirstChild(); node != null; node = node.getNextSibling()) {
//					System.out.println(node.getNodeName() + " : " + node.getTextContent());

					switch (node.getNodeName()) {
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
						if (node.getTextContent().equalsIgnoreCase("null")) {
							data.setTyp_ps(0);
						} else {
							data.setTyp_ps(Integer.parseInt(node.getTextContent()));
						}
						break;
					case "typ_ws":
						if (node.getTextContent().equalsIgnoreCase("null")) {
							data.setTyp_ws(0);
						}else {
							data.setTyp_ws(Integer.parseInt(node.getTextContent()));
						}
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
			System.out.println("=====================================================================================================================================================");
			for (TyphoonVO d : list) {
				System.out.println(d);
			}
			System.out.println("=====================================================================================================================================================");
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
