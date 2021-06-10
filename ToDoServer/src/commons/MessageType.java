package commons;

public enum MessageType {
	Hello,
	FindUser,
	Password,
	UserNotFound,
	LoginSuccess,
	SendContent,
	Error;
	
	public static MessageType parseType(String typeName) {
		MessageType type = MessageType.Error;
		for (MessageType value : MessageType.values()) {
			if (value.toString().equals(typeName)) type = value;
		}
		return type;
	}
	
	public static MessageType getType (Message msg) {
		MessageType type = MessageType.Error;
		if (msg instanceof Message_Hello) type = Hello;
		else if (msg instanceof Message_FindUser) type = FindUser;
		else if (msg instanceof Message_Password) type = Password;
		else if (msg instanceof Message_UserNotFound) type = UserNotFound;
		else if (msg instanceof Message_LoginSuccess) type = LoginSuccess;
		else if (msg instanceof Message_SendContent) type = SendContent;
		return type;
	}
}