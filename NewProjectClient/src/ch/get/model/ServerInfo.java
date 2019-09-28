package ch.get.model;

public class ServerInfo {

	private String serverName;
	private String serverIp;
	private String serverPort;
	
	public ServerInfo(String serverName, String serverIp, String serverPort) {
		this.serverName = serverName;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
}
