package other;

import java.util.ArrayList;

public class MessageBox {

	private ArrayList<String> messageList = new ArrayList<String>();
	private int lastIndex = 0;
	
	public MessageBox() {
	}
	
	public ArrayList<String> getMessageList() {
		return messageList;
	}


	public void setMessageList(ArrayList<String> messageList) {
		this.messageList = messageList;
	}


	public void addMessage(String message) {
		this.getMessageList().add(message);
	}
	
	@Override
	public String toString() {
		String messages = "";
		for(String message : this.messageList) {
			messages += message;
		}
		return messages;
	}
	
	public String getLastMessage() {
		return this.messageList.get(this.messageList.size() -1);
	}
	
	public ArrayList<String> getLastMessages(){
		ArrayList<String> newMessages = new ArrayList<String>();
		for(int i = this.lastIndex; i<this.messageList.size(); i++) {
			newMessages.add(messageList.get(i));
		}
		lastIndex = messageList.size();
		return newMessages;
	}
}
