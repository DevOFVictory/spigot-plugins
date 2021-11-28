package de.devofvictory.biomicploz.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.chilipro.eco.utils.MoneyManager;
import de.devofvictory.biomicploz.main.Main;

public class Plot {

	// Properties

	private Point2D corner1;
	private Point2D corner2;

	private int xMin;
	private int zMin;
	private int xMax;
	private int zMax;

	private UUID owner;
	private List<UUID> trusted;
	private List<UUID> denied;
	private HashMap<String, String> flags;
	private int id;
	private Location home;

	private Plot plotMergedOn;

	// Initializer

	public Plot(int id, Point2D corner1, Point2D corner2) {

		this.id = id;
		this.corner1 = corner1;
		this.corner2 = corner2;

		if (corner1.getX() > corner2.getX()) {
			xMax = corner1.getX();
			xMin = corner2.getX();
		} else {
			xMax = corner2.getX();
			xMin = corner1.getX();
		}

		if (corner1.getY() > corner2.getY()) {
			zMax = corner1.getY();
			zMin = corner2.getY();
		} else {
			zMax = corner2.getY();
			zMin = corner1.getY();
		}

		denied = new ArrayList<>();
		trusted = new ArrayList<>();

		plotMergedOn = null;
	}

	// Getters (Direct)

	public int getID() {
		return id;
	}

	public UUID getOwner() {
		return owner;
	}

	public List<UUID> getTrusted() {
		return trusted;
	}

	public List<UUID> getDenied() {
		return denied;
	}

	public HashMap<String, String> getFlags() {
		return flags;
	}

	public Point2D getCorner1() {
		return corner1;
	}

	public Point2D getCorner2() {
		return corner2;
	}

	// Getters (Non Direct)

	public Point2D getCorner3() {
		return new Point2D(corner2.getX(), corner1.getY());
	}

	public Point2D getCorner4() {
		return new Point2D(corner1.getX(), corner2.getY());
	}

	public Plot getPlotMergedOn() {
		return plotMergedOn;
	}

	public String getFlagValue(String s) {
		if (getFlags().containsKey(s)) {
			return getFlags().get(s);
		} else {
			return null;
		}
	}

	public ArrayList<Player> getPlayersOnPlot() {
		ArrayList<Player> players = new ArrayList<Player>();

		for (Player all : Bukkit.getOnlinePlayers()) {
			if (this.isLocOnPlot(all.getLocation().getBlock().getLocation())) {
				players.add(all);
			}
		}
		return players;
	}

	public ArrayList<Plot> getSurroundingPlots() {
		ArrayList<Plot> surroundings = new ArrayList<Plot>();

		for (Point2D wallPoint : getAllWallLocations()) {
			Location wallLoc = wallPoint.toLocation();

			Plot one = PlotManager.getPlot(wallLoc.clone().add(1, 0, 0));
			Plot two = PlotManager.getPlot(wallLoc.clone().add(-1, 0, 0));
			Plot three = PlotManager.getPlot(wallLoc.clone().add(0, 0, 1));
			Plot four = PlotManager.getPlot(wallLoc.clone().add(0, 0, -1));

			if (one != null && one != this && !surroundings.contains(one)) {
				surroundings.add(one);
			}

			if (two != null && two != this && !surroundings.contains(two)) {
				surroundings.add(two);
			}

			if (three != null && three != this && !surroundings.contains(three)) {
				surroundings.add(three);
			}

			if (four != null && four != this && !surroundings.contains(four)) {
				surroundings.add(four);
			}

		}

		return surroundings;
	}

	public Location getHome() {
		return new Location(Bukkit.getWorld("plotworld"), home.getX(), home.getY(), home.getZ(), home.getYaw(),
				home.getPitch());
	}

	public ArrayList<Plot> getMergedOnThisPlots() {
		ArrayList<Plot> merged = new ArrayList<Plot>();

		for (Plot plot : PlotManager.allPlots) {
			if (plot.isMergedOnOther() && plot.getPlotMergedOn() == this) {
				merged.add(plot);
			}
		}
		return merged;
	}

	public ArrayList<Plot> getMergeablePlots(UUID uuid) {
		ArrayList<Plot> sur = new ArrayList<Plot>();

		for (Plot plot : getSurroundingPlots()) {

			if (plot.getOwner().equals(uuid) && !plot.isMergedOnOther() && !plot.hasMergedPlots()) {
				sur.add(plot);
			}
		}

		return sur;
	}

	public void sell() {

		Player p = Bukkit.getPlayer(this.getOwner());

		MoneyManager.setMoney(p, MoneyManager.getMoney(p) + this.getPrice());

		Utils.sendNoSpamMsg(p,
				Main.Prefix + "§aGrundstück wurde für §e" + this.getPrice() / 2 + "$ §aan die Bank verkauft!");
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);

		delete();
	}

	public void delete() {

		unlink();

		PlotManager.allPlots.remove(this);
	}

	public void unlink() {
		this.setPlotMergedOn(null);
	}

	public ArrayList<Plot> getPlotsForSameChange() {
		ArrayList<Plot> sameSetting = new ArrayList<Plot>();
		if (this.isMergedOnOther()) {
			for (Plot merged : this.getPlotMergedOn().getMergedOnThisPlots()) {
				sameSetting.add(merged);
			}
		} else if (this.hasMergedPlots()) {
			sameSetting.add(this);
			sameSetting.addAll(this.getMergedOnThisPlots());
		} else {
			sameSetting.add(this);
			return sameSetting;
		}
		return sameSetting;
		
	}

	public ArrayList<Point2D> getWallLocations(int index) {
		ArrayList<Point2D> wallLocations = new ArrayList<Point2D>();

		switch (index) {
		case 0:

			for (int i = Math.min(getCorner1().getX(), getCorner3().getX()); i < Math.max(getCorner1().getX(),
					getCorner3().getX()) + 1; i++) {
				wallLocations.add(new Point2D(i, getCorner1().getY()));
			}

			break;
		case 1:

			for (int i = Math.min(getCorner3().getY(), getCorner2().getY()); i < Math.max(getCorner3().getY(),
					getCorner2().getY()); i++) {
				wallLocations.add(new Point2D(getCorner2().getX(), i));
			}

			break;
		case 2:

			for (int i = Math.min(getCorner2().getX(), getCorner4().getX()); i < Math.max(getCorner2().getX(),
					getCorner4().getX()) + 1; i++) {
				wallLocations.add(new Point2D(i, getCorner2().getY()));
			}

			break;
		case 3:

			for (int i = Math.min(getCorner4().getY(), getCorner1().getY()); i < Math.max(getCorner4().getY(),
					getCorner1().getY()); i++) {
				wallLocations.add(new Point2D(getCorner1().getX(), i));
			}

			break;
		default:
			throw new IndexOutOfBoundsException("Please use index 0-3");
		}

		return wallLocations;
	}

	public ArrayList<Point2D> getAllWallLocations() {
		ArrayList<Point2D> wallLocations = new ArrayList<Point2D>();

		for (int i = 0; i < 4; i++) {
			wallLocations.addAll(getWallLocations(i));
		}

		return wallLocations;
	}

	public Plot getOverlapping() {
		for (Plot b : PlotManager.allPlots) {
			if (xMin < b.xMax && xMax > b.xMin && zMin < b.zMax && zMax > b.zMin) {
				return b;
			}
		}
		return null;
	}

	public int getPrice() {
		return getSquareBlocksCount() * 10 + 50;
	}

	public int getSquareBlocksCount() {

		Point2D corner1 = getCorner1();
		Point2D corner2 = getCorner2();

		int side1 = Utils.getDifference(corner1.getX(), corner2.getX());
		int side2 = Utils.getDifference(corner1.getY(), corner2.getY());

		if (side1 * side2 != 0) {
			return side1 * side2;
		} else {
			Location loc1 = new Location(Bukkit.getWorld("plotworld"), corner1.getX(), 0, corner1.getY());
			Location loc2 = new Location(Bukkit.getWorld("plotworld"), corner2.getX(), 0, corner2.getY());

			return (int) Math.round(loc1.distance(loc2));
		}
	}

	public Location getMiddle() {

		int x1 = getCorner1().getX();
		int x2 = getCorner2().getX();

		int y1 = getCorner1().getY();
		int y2 = getCorner2().getY();

		double midX = (x1 + x2) / 2;
		double midZ = (y1 + y2) / 2;

		Location midLocation = new Location(Main.plotworld, midX,
				Main.plotworld.getHighestBlockYAt(Math.round((long) midX), Math.round((long) midZ)), midZ);

		return midLocation;

	}

	// Booleans

	public boolean hasMergedPlots() {
		return !getMergedOnThisPlots().isEmpty();
	}

	public boolean isMergedOnOther() {
		return plotMergedOn != null;
	}

	public boolean isWallLocation(Location loc) {

		int p1x = corner1.getX();
		int p1y = corner1.getY();
		int p2x = corner2.getX();
		int p2y = corner2.getY();

		int p3x = loc.getBlockX();
		int p3y = loc.getBlockZ();

		if ((p3x == p1x || p3x == p2x) || (p3y == p1y || p3y == p2y)) {

			return true;
		} else {
			return false;
		}
	}

	public boolean isCornerLocation(Location loc) {

		int p1x = corner1.getX();
		int p1y = corner1.getY();
		int p2x = corner2.getX();
		int p2y = corner2.getY();

		int p3x = loc.getBlockX();
		int p3y = loc.getBlockZ();

		if ((p3x == p1x || p3x == p2x) && (p3y == p1y || p3y == p2y)) {

			return true;
		} else {
			return false;
		}

	}

	public boolean isLocOnPlot(Location loc) {
		return (PlotManager.isBetween(loc.getBlock().getLocation(), corner1, corner2));
	}

	public void setPlotMergedOn(Plot plotMergedOn) {
		this.plotMergedOn = plotMergedOn;
	}

	// Setters

	public void setCorner1(Point2D corner1) {
		this.corner1 = corner1;
	}

	public void setCorner2(Point2D corner2) {
		this.corner2 = corner2;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public void setTrusted(List<UUID> trusted) {
		this.trusted = trusted;
	}

	public void setDenied(List<UUID> denied) {
		this.denied = denied;
	}

	public void setFlags(HashMap<String, String> flags) {
		this.flags = flags;
	}

	// Methods

	public void addFlag(String key, String value) {
		getFlags().put(key, value);
	}

	public boolean removeFlag(String key) {
		if (getFlags().containsKey(key)) {
			getFlags().remove(key);
			return true;
		} else {
			return false;
		}
	}

	public void setFlagValue(String key, String value) {
		flags.put(key, value);
	}

	public ArrayList<Location> getAllCorners() {
		ArrayList<Location> list = new ArrayList<>();

		for (int i = 1; i < 255; i++) {
			list.add(Main.plotworld.getBlockAt(getCorner1().getX(), i, getCorner1().getY()).getLocation());
			list.add(Main.plotworld.getBlockAt(getCorner2().getX(), i, getCorner2().getY()).getLocation());
			list.add(Main.plotworld.getBlockAt(getCorner3().getX(), i, getCorner3().getY()).getLocation());
			list.add(Main.plotworld.getBlockAt(getCorner4().getX(), i, getCorner4().getY()).getLocation());
		}

		return list;
	}

	public void setHome(Location home) {
		this.home = home;
	}

	public void mergeWith(Plot plot) {
		plot.setPlotMergedOn(this);
	}

}
