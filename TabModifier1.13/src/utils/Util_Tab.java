package utils;




import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_13_R2.PlayerConnection;

public class Util_Tab {
	
	public static void sendTab(Player p, String h, String f) {
		
		if (h == null) h = "";
		if (f == null) f = "";
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		
		//IChatBaseComponent header = ChatSerializer.a("{\"text\": \""+ h + "\"}");
		IChatBaseComponent footer = ChatSerializer.a("{\"text\": \""+ f + "\"}");
		
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter();
		
		try {
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, footer);
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			connection.sendPacket(headerPacket);
		}
		
	}

}
