package service;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//웹소켓 서버 클래스 : 주소는 websocket
@ServerEndpoint("/websocket")
public class WebSocketService {
	//클라이언트를 저장할 List를 생성 
	static List<Session> list = new ArrayList<Session>();
	
	//클라이언트가 접속하면 호출되는 메소드
	@OnOpen
	public void onOpen(Session session) {
		//세션을 리스트에 추가 
		list.add(session);
	}
	//클라이언트가 접속을 해제하면 호출되는 메소드 
	@OnClose
	public void onClose(Session session) {
		//세션을 리스트에서 제거 
		list.remove(session);
	}
	//클라이언트가 메시지를 전송하면 호출되는 메소드 
	@OnMessage
	public void onMessage(String message, Session session) {
		//전송된 메시지를 클라이언트 전체에게 전송
		try {
			for(Session client : list) {
				client.getBasicRemote().sendText(message);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
