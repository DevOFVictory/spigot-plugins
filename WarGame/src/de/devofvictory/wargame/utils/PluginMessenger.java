package de.devofvictory.wargame.utils;

public class PluginMessenger /*implements PluginMessageListener*/{

//	@Override
//	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
//		
//		if(!channel.equals("BungeeCord")) {
//			return;
//		}
//		
//		ByteArrayDataInput in = ByteStreams.newDataInput(message);
//		String subchannel = in.readUTF();
//		
//		if (subchannel.equals("WarGameLobbyConnector")) {
//			
//			
//			if(in.readUTF().equals("status")) {
//				if (Main.isGameRunning) {
//					sendCustomPluginMessage("running_"+Bukkit.getServer().getPort());
//				}else {
//					sendCustomPluginMessage("waiting_"+Bukkit.getServer().getPort());
//				}
//				
//			}
//			
//		}
//		
//	}
//	
//	public static void sendCustomPluginMessage(String message) {
//		
//		if (!Bukkit.getOnlinePlayers().isEmpty()) {
//			
//			ByteArrayDataOutput out = ByteStreams.newDataOutput();
//			out.writeUTF("Forward");
//			out.writeUTF("lobby");
//			out.writeUTF("WarGameLobbyConnector");
//			out.writeUTF(message);
//			
//			ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
//			DataOutputStream msgout = new DataOutputStream(msgbytes);
//			try {
//				msgout.writeUTF(message); // You can do anything you want with msgout
//			} catch (IOException exception){
//			exception.printStackTrace();
//			}
//
//			out.writeShort(msgbytes.toByteArray().length);
//			out.write(msgbytes.toByteArray());
//
//			Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
//		
//			player.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
//			
//		}
//	}
	

}
