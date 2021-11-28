package de.devofvictory.biomicploz.utils;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

import de.devofvictory.biomicploz.main.Main;

public class PlotSaver {
	
	static HashMap<Integer, Integer> mergedPlots = new HashMap<Integer, Integer>();

	public static void importPlots() {
		
		FileManager.create("plots");
		
		for (String key : FileManager.plotsCfg.getKeys(false)) {
			
			Integer id = Integer.parseInt(key);
			int corner1X = FileManager.plotsCfg.getInt(id+".corner1.X");
			int corner1Z = FileManager.plotsCfg.getInt(id+".corner1.Z");
			int corner2X = FileManager.plotsCfg.getInt(id+".corner2.X");
			int corner2Z = FileManager.plotsCfg.getInt(id+".corner2.Z");
			UUID owner = UUID.fromString(FileManager.plotsCfg.getString(id+".owner"));
			double homeX = FileManager.plotsCfg.getDouble(id+".home.X");
			double homeY = FileManager.plotsCfg.getDouble(id+".home.Y");
			double homeZ = FileManager.plotsCfg.getDouble(id+".home.Z");
			float homeYaw = FileManager.plotsCfg.getLong(id+".home.X");
			float homePitch = FileManager.plotsCfg.getLong(id+".home.X");
			List<UUID> trusted = Utils.getUUIDList(FileManager.plotsCfg.getStringList(id+".trusted"));
			List<UUID> denied = Utils.getUUIDList(FileManager.plotsCfg.getStringList(id+".denied"));
			int mergedWith = FileManager.plotsCfg.getInt(id+".mergedWith");
			HashMap<String, String> flags = new HashMap<>();
			
			try {
			for (String s : FileManager.plotsCfg.getConfigurationSection(id+".flag").getKeys(false)) {
				flags.put(s, FileManager.plotsCfg.getString(id+".flag."+s));
			}
			}catch (NullPointerException ex) {}
			
			Plot plot = new Plot(id, new Point2D(corner1X, corner1Z), new Point2D(corner2X, corner2Z));
			plot.setOwner(owner);
			plot.setHome(new Location(Main.plotworld, homeX, homeY, homeZ, homeYaw, homePitch));
			plot.setTrusted(trusted);
			plot.setDenied(denied);
			plot.setFlags(flags);
			mergedPlots.put(plot.getID(), mergedWith);
			
			PlotManager.allPlots.add(plot);
			
		}
		
		reMergePlots();
		
		

	}
	
	public static void reMergePlots() {
		for (int id : mergedPlots.keySet()) {
			if (mergedPlots.get(id) != -1) {
				PlotManager.getPlot(id).setPlotMergedOn(PlotManager.getPlot(mergedPlots.get(id)));
			}else {
				PlotManager.getPlot(id).setPlotMergedOn(null);
			}
		}
	}

	public static void exportPlots() {

		for (String key : FileManager.plotsCfg.getKeys(false)) {
			FileManager.plotsCfg.set(key, null);
		}

		for (Plot plot : PlotManager.allPlots) {
			int id = plot.getID();
			
			FileManager.plotsCfg.set(id+".owner", plot.getOwner().toString());
			FileManager.plotsCfg.set(id+".corner1.X", plot.getCorner1().getX());
			FileManager.plotsCfg.set(id+".corner1.Z", plot.getCorner1().getY());
			FileManager.plotsCfg.set(id+".corner2.X", plot.getCorner2().getX());
			FileManager.plotsCfg.set(id+".corner2.Z", plot.getCorner2().getY());
			FileManager.plotsCfg.set(id+".home.X", plot.getHome().getX());
			FileManager.plotsCfg.set(id+".home.Y", plot.getHome().getY());
			FileManager.plotsCfg.set(id+".home.Z", plot.getHome().getZ());
			FileManager.plotsCfg.set(id+".home.YAW", plot.getHome().getYaw());
			FileManager.plotsCfg.set(id+".home.PITCH", plot.getHome().getPitch());		
			FileManager.plotsCfg.set(id+".trusted", Utils.getStringList(plot.getTrusted()));
			FileManager.plotsCfg.set(id+".denied", Utils.getStringList(plot.getDenied()));
			if (plot.isMergedOnOther()) {
				FileManager.plotsCfg.set(id+".mergedWith", plot.getPlotMergedOn().getID());
			}else {
				FileManager.plotsCfg.set(id+".mergedWith", -1);
			}
			
			for (String flagKey : plot.getFlags().keySet()) {
				FileManager.plotsCfg.set(id+".flag."+flagKey, plot.getFlagValue(flagKey));
			}
			
		}

		FileManager.save("plots");
	}

}
