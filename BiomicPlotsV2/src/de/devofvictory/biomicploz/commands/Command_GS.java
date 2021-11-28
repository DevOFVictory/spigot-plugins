package de.devofvictory.biomicploz.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.biomicploz.inventorys.INV_Menu;
import de.devofvictory.biomicploz.listeners.Listener_OnMark;
import de.devofvictory.biomicploz.main.Main;
import de.devofvictory.biomicploz.utils.MojangAPIAccess;
import de.devofvictory.biomicploz.utils.Plot;
import de.devofvictory.biomicploz.utils.PlotManager;
import de.devofvictory.biomicploz.utils.Point2D;
import de.devofvictory.biomicploz.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Command_GS implements CommandExecutor {

	public static HashMap<Player, Plot> confirm = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			Plot plot = PlotManager.getPlot(p.getLocation());

			if (args.length == 0) {

				INV_Menu.callInv(p);

			} else if (args.length == 1) {

				if (args[0].equalsIgnoreCase("home") || args[0].equalsIgnoreCase("h")) {

					if (PlotManager.getPlayerPlots(p.getUniqueId()).size() > 0) {

						ArrayList<Plot> playerPlots = PlotManager.getPlayerPlots(p.getUniqueId());

						p.closeInventory();
						p.teleport(playerPlots.get(0).getHome());
						Utils.sendNoSpamMsg(p, Main.Prefix + "§aDu wurdest zu deinem 1. Grundstück teleportiert!");
						p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);

					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu hast noch keine Grundstücke!");
					}

				} else if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("sell")
						|| args[0].equalsIgnoreCase("reset")) {

					if (plot != null) {

						if (plot.getOwner().equals(p.getUniqueId()) || Command_Build.powermode.contains(p)) {

							plot.sell();

						} else {
							Utils.sendNoSpamMsg(p,
									Main.Prefix + "§cDas Plot mit der ID §e" + plot.getID() + " §cgehört nicht dir!");
						}
					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu stehst auf keinem Grundstück!");
					}

				} else if (args[0].equalsIgnoreCase("claim") || args[0].equalsIgnoreCase("buy")
						|| args[0].equalsIgnoreCase("get")) {

					if (Listener_OnMark.loc1.containsKey(p) && Listener_OnMark.loc2.containsKey(p)) {

						if (Listener_OnMark.loc1.get(p).distance(Listener_OnMark.loc2.get(p)) < 150) {

							Point2D corner1 = new Point2D(Listener_OnMark.loc1.get(p).getBlockX(),
									Listener_OnMark.loc1.get(p).getBlockZ());
							Point2D corner2 = new Point2D(Listener_OnMark.loc2.get(p).getBlockX(),
									Listener_OnMark.loc2.get(p).getBlockZ());

							Plot plot2 = new Plot(PlotManager.getHighestPlotId() + 1, corner1, corner2);
							Utils.sendNoSpamMsg(p, Main.Prefix + "§eGrundstücksmaße werden berechnet...");

							if (plot2.getOverlapping() == null) {
								if (plot2.isLocOnPlot(p.getLocation())) {

									if (!Command_Build.powermode.contains(p)) {

										TextComponent tp = new TextComponent();
										tp.setText("§8§l[§2§lKaufen§8§l]");
										tp.setHoverEvent(
												new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT,
														new ComponentBuilder("§cZahlungspflichtig erwerben").create()));
										tp.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/gs confirm"));

										TextComponent text = new TextComponent();
										text.setText(Main.Prefix + "§a§l§nPreis: "
												+ (plot2.getSquareBlocksCount() * 10 + 50) + "$§r ");

										text.addExtra(tp);

										Utils.sendNoSpamMsg(p,
												Main.Prefix + "§f§l§m=====================================");
										Utils.sendNoSpamMsg(p,
												Main.Prefix + "§eEcke 1: " + plot2.getCorner1().toString());
										Utils.sendNoSpamMsg(p,
												Main.Prefix + "§eEcke 2: " + plot2.getCorner2().toString());
										Utils.sendNoSpamMsg(p,
												Main.Prefix + "§eEcke 3: " + plot2.getCorner3().toString());
										Utils.sendNoSpamMsg(p,
												Main.Prefix + "§eEcke 4: " + plot2.getCorner4().toString());
										Utils.sendNoSpamMsg(p,
												Main.Prefix + "§eQuadrat-Blöcke: " + plot2.getSquareBlocksCount());
										Utils.sendNoSpamMsg(p,
												Main.Prefix + "§eBlöcke: " + plot2.getSquareBlocksCount() * 256);
										Utils.sendNoSpamMsg(p, Main.Prefix + "");
										p.spigot().sendMessage(text);
										Utils.sendNoSpamMsg(p,
												Main.Prefix + "§f§l§m=====================================");

										confirm.put(p, plot2);
										Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
												new Runnable() {

													@Override
													public void run() {
														if (confirm.containsKey(p))
															confirm.remove(p);

													}
												}, 15 * 20);
									} else {
										PlotManager.confirmPlot(plot2, p);
										p.closeInventory();
									}

								} else {
									Utils.sendNoSpamMsg(p,
											Main.Prefix + "§cDu stehst nicht auf dem gewünschtem Grundstück!");
									p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
									p.closeInventory();
								}
							} else {
								Utils.sendNoSpamMsg(p,
										Main.Prefix + "§cDein Grundstück überschneidet sich mit §6"
												+ Bukkit.getOfflinePlayer(plot2.getOverlapping().getOwner()).getName()
												+ "s§c!");
								p.closeInventory();
								p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);

							}

						} else {
							Utils.sendNoSpamMsg(p, Main.Prefix + "§cDas markierte Grundstück ist zu groß!"); // error:
																												// plot
																												// to
																												// big
							p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
							p.closeInventory();
						}

					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu hast noch kein Grundstück vollständig markiert!"); // both
																														// locations
																														// aren't
																														// set
																														// yet

						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
						p.closeInventory();
					}

				} else if (args[0].equalsIgnoreCase("i") || args[0].equalsIgnoreCase("info")
						|| args[0].equalsIgnoreCase("information")) {

					if (plot != null) {

						p.sendMessage("§8§l----------§2GS§aInfo§8§l----------");
						p.sendMessage("§aID: §7" + plot.getID());
						p.sendMessage("§aBesitzer: §7" + Utils.getCashedName(plot.getOwner()));
						p.sendMessage("§aBiom: §7" + plot.getHome().getBlock().getBiome().name());
						p.sendMessage("§aVerbunden mit: §7" + Utils.getMergedWithString(plot));
						p.sendMessage("§aVertraut: §7" + Utils.getBeautifulList(Utils.getNamesList(plot.getTrusted())));
						p.sendMessage("§aVerboten: §7" + Utils.getBeautifulList(Utils.getNamesList(plot.getDenied())));
						p.sendMessage("§aFlags: §7" + plot.getFlags());
						p.sendMessage("§8§l----------§2GS§aInfo§8§l----------");

						p.closeInventory();
						p.playSound(p.getLocation(), Sound.CLICK, 1, 1);

					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu stehst auf keinem Grundstück!");
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 10, 10);
					}

				} else if (args[0].equalsIgnoreCase("confirm")) {
					if (confirm.containsKey(p)) {

						Plot claim = confirm.get(p);
						PlotManager.confirmPlot(claim, p);
						confirm.remove(p);

					} else {
						Utils.sendNoSpamMsg(p,
								Main.Prefix + "§cHier gibt es nichts zu bestätigen aus den letzten 15 Sekunden!");
					}

				} else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("hilfe")) {

					p.sendMessage("§8§l----------§2GS§aHilfe§8§l----------");
					p.sendMessage("§a/gs §7» §fRufe ein Übersichtsfenster auf");
					p.sendMessage("§a/gs help §7» §fRufe diese Hilfe auf");
					p.sendMessage("§a/gs info §7» §fSehe informationen über das Grundstück");
					p.sendMessage("§a/gs buy §7» §fKaufe das markierte Grundstück");
					p.sendMessage("§a/gs sell §7» §fVerkaufe dein Grundstück an die Bank");
					p.sendMessage("§a/gs setowner <Name> §7» §fGebe dein Grundstück an einen anderen");
					p.sendMessage("§a/gs home [<Name/Nummer>] §7» §fTeleportiere dich zu Grundstücken");
					p.sendMessage("§a/gs tp <ID> §7» §fTeleportiere dich zu einem Grundstück ahand der ID");
					p.sendMessage("§a/gs inpect §7» §fSehe die Ecken von deinem Grundstück");
					p.sendMessage("§a/gs ban <Name> §7» §fBanne einen Spieler von deinem Grundstück");
					p.sendMessage("§a/gs trust <Name> §7» §fVertraue einem Spieler auf deinem Grundstück");
					p.sendMessage("§a/gs remove <Name/*> §7» §fEntferne gebannte und vertraute Spieler");
					p.sendMessage("§a/gs kick <Name/*> §7» §fWerfe einen Spieler von deinem Grundstück");
					p.sendMessage("§a/gs center §7» §fTeleportiere dich in die Mitte deines Grundstückes");
					p.sendMessage("§a/gs setflag <Flag> <Wert> §7» §fSetzte eine Einstellung für dein Plot");
					p.sendMessage("§a/gs link §7» §fFüge dein Plot mit deinen Anderen zusammen");
					p.sendMessage("§a/gs unlink §7» §fLöse die Verbindung mit deinen Plots wieder auf");
					p.sendMessage("§a/gs confirm §7» §fBestätige Aktionen vor dem Ausführen");
					p.sendMessage("§8§l----------§2GS§aHilfe§8§l----------");

				} else if (args[0].equalsIgnoreCase("center") || args[0].equalsIgnoreCase("middle")) {

					if (plot != null) {

						p.teleport(plot.getMiddle());
						Utils.sendNoSpamMsg(p,
								Main.Prefix + "§aDu wurdest in die Mitte des Grundstückes teleportiert!");

					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu stehst auf keinem Grundstück!");
					}

				} else if (args[0].equalsIgnoreCase("inspect") || args[0].equalsIgnoreCase("untersuchen")) {

					if (plot != null) {

						if (plot.getOwner().equals(p.getUniqueId()) || Command_Build.powermode.contains(p)) {

							if (!PlotManager.inspect.containsKey(p)) {

								PlotManager.showCorners(p, plot);
								PlotManager.hideCorners(p);
								PlotManager.showCorners(p, plot);

								Utils.sendNoSpamMsg(p, Main.Prefix + "§aDu bist nun im Untersuchungsmodus!");
							} else {

								PlotManager.hideCorners(p);
								Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu bist nun nicht mehr im Untersuchungsmodus!");
							}

						} else {
							Utils.sendNoSpamMsg(p, Main.Prefix + "§cDies kann nur der Besitzer des Grundstückes!");
						}

					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu stehst auf keinem Grundstück!");
					}

				} else if (args[0].equalsIgnoreCase("merge") || args[0].equalsIgnoreCase("zusammenfügen")
						|| args[0].equalsIgnoreCase("link")) {

					if (plot != null) {

						if (plot.getOwner().equals(p.getUniqueId()) || Command_Build.powermode.contains(p)) {

							ArrayList<Plot> surroundings = plot.getMergeablePlots(plot.getOwner());

							if (!surroundings.isEmpty()) {

								String s = "";

								for (Plot sur : surroundings) {
									s += ", " + sur.getID();
									plot.mergeWith(sur);
								}

								s = s.replaceFirst(", ", "");

								p.sendMessage(Main.Prefix + "§a" + surroundings.size()
										+ " Grundstücke wurden zusammengefügt!");

								p.sendMessage(Main.Prefix + "§7Betroffene Grundstücke: " + s);
								p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 10);

							} else {
								p.sendMessage(Main.Prefix
										+ "§cEs konnten keine Grundstücke zum Zusammenfügen gefunden werden! §7(Sind das deine Grundstücke? Sind die Grundstücke noch nicht mit anderen verlinkt?)");
								p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
							}

						} else {
							Utils.sendNoSpamMsg(p, Main.Prefix + "§cDies kann nur der Besitzer des Grundstückes!");
						}

					} else {
						p.sendMessage(Main.Prefix + "§cDu stehst auf keinem Plot!");
					}

				} else if (args[0].equalsIgnoreCase("unmerge") || args[0].equalsIgnoreCase("trennen")
						|| args[0].equalsIgnoreCase("unlink")) {

					if (plot != null) {

						if (plot.getOwner().equals(p.getUniqueId()) || Command_Build.powermode.contains(p)) {

							if (plot.hasMergedPlots()) {

								Utils.sendNoSpamMsg(p, "§aDie Grundstücke §e" + Utils.getMergedWithString(plot)
										+ " §awurden von diesem Grundstück getrennt!");

								for (Plot mergedOn : plot.getMergedOnThisPlots()) {
									mergedOn.unlink();
								}
								p.playSound(p.getLocation(), Sound.BAT_DEATH, 1, 1);

							} else if (plot.isMergedOnOther()) {
								Utils.sendNoSpamMsg(p, "§aDas Grundstück wurde von dem Grundstück §e"
										+ Utils.getMergedWithString(plot) + " §agetrennt!");
								p.playSound(p.getLocation(), Sound.BAT_DEATH, 1, 1);
								plot.unlink();
							} else {
								Utils.sendNoSpamMsg(p, "§cDieses Grundstück ist mit keinem Anderen verbunden!");
								p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
							}

						} else {
							Utils.sendNoSpamMsg(p, Main.Prefix + "§cDies kann nur der Besitzer des Grundstückes!");
						}

					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu stehst auf keinem Grundstück!");
					}

				} else {
					Utils.sendNoSpamMsg(p, Main.Prefix + "§cUnbekannter Befehl! Mach' §6/gs hilfe §cfür Hilfe");
				}

			} else if (args.length == 2) {

				UUID targetUUID = Utils.getCashedUUID(args[1]);
				if (targetUUID == null && Command_Build.powermode.contains(p)) {
					targetUUID = MojangAPIAccess.getUUID(args[1]);
				}

				if (args[0].equalsIgnoreCase("so") || args[0].equalsIgnoreCase("setowner")) {

					if (plot != null) {

						if (plot.getOwner().equals(p.getUniqueId()) || Command_Build.powermode.contains(p)) {

							if (targetUUID != null) {
								
								for (Plot gs : plot.getPlotsForSameChange()) {
									gs.setOwner(targetUUID);
								}
								
								Utils.sendNoSpamMsg(p, Main.Prefix + "§aDer neue Besitzer ist nun " + args[1]);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);

								Player target = Bukkit.getPlayer(args[1]);

								if (target != null) {
									Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());

									TextComponent tp = new TextComponent();
									tp.setText("§8§l[§7§lT§l§lP§8§l]");
									tp.setHoverEvent(
											new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT,
													new ComponentBuilder("§eKlick hier").create()));
									tp.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/gs tp " + plot.getID()));

									TextComponent text = new TextComponent();
									text.setText(Main.Prefix + "§2" + p.getName() + " §ahat dir das Grundstück §2"
											+ plot.getID() + " §aüberschrieben! ");

									text.addExtra(tp);

									onlineTarget.spigot().sendMessage(text);
									onlineTarget.playSound(onlineTarget.getLocation(), Sound.LEVEL_UP, 1, 1);
								}
							} else {
								p.sendMessage(Main.Prefix + "§cDieser Spieler war noch nie online!");
							}

						} else {
							Utils.sendNoSpamMsg(p,
									Main.Prefix + "§cDas Plot mit der ID §e" + plot.getID() + " §cgehört nicht dir!");
						}
					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu stehst auf keinem Grundstück!");
					}

				} else if (args[0].equalsIgnoreCase("tp")) {

					try {

						int id = Integer.parseInt(args[1]);

						Plot targetPlot = PlotManager.getPlot(id);

						if (targetPlot != null) {

							p.teleport(targetPlot.getHome());
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
							Utils.sendNoSpamMsg(p,
									Main.Prefix + "§aDu wurdest zu §2" + args[1] + "s §aGrundstück teleportiert!");

						} else {
							Utils.sendNoSpamMsg(p, Main.Prefix + "§cDieses Grundstück gibt es nicht!");
						}

					} catch (NumberFormatException ex) {
						Utils.sendNoSpamMsg(p,
								Main.Prefix + "§cBitte gebe eine gültige Zahl an! \n §eBeispiel: /gs tp 13");
					}

				} else if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("home")) {

					try {
						try {
							int number = Integer.parseInt(args[1]);

							p.teleport(PlotManager.getPlayerPlots(p.getUniqueId()).get(number - 1).getHome());
							Utils.sendNoSpamMsg(p,
									Main.Prefix + "§aDu wurdest zu deinem " + number + ". Grundstück teleportiert!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);

						} catch (IndexOutOfBoundsException ex2) {
							Utils.sendNoSpamMsg(p, Main.Prefix + "§cGrundstück konnte nicht gefunden werden!");
						}

					} catch (NumberFormatException ex) {

						if (targetUUID != null) {

							ArrayList<Plot> playerplots = PlotManager.getPlayerPlots(targetUUID);

							try {

								Plot tplot = playerplots.get(0);
								p.teleport(tplot.getHome());

								Utils.sendNoSpamMsg(p, Main.Prefix + "§aDu wurdest zu §2" + args[1]
										+ "s §21. §aGrundstück teleportiert!");
								p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);

							} catch (NumberFormatException | IndexOutOfBoundsException ex2) {
								Utils.sendNoSpamMsg(p, Main.Prefix + "§cGrundstück konnte nicht gefunden werden!");
							}

						} else {
							p.sendMessage(Main.Prefix + "Dieser Spieler war noch nie online!");
						}

					}
				} else if (args[0].equalsIgnoreCase("trust") || args[0].equalsIgnoreCase("vertrauen")
						|| args[0].equalsIgnoreCase("add")) {

					if (plot != null) {

						if (plot.getOwner().equals(p.getUniqueId()) || Command_Build.powermode.contains(p)) {

							if (targetUUID != null) {

								if (!targetUUID.equals(p.getUniqueId())) {

									if (!plot.getTrusted().contains(targetUUID)) {
										for (Plot gs : plot.getPlotsForSameChange()) {
											gs.getTrusted().add(targetUUID);
										}

										Utils.sendNoSpamMsg(p, Main.Prefix + "§2" + args[1] + " §aist nun vertraut!");

									} else {
										Utils.sendNoSpamMsg(p,
												Main.Prefix + "§6" + args[1] + " §cist ist betreits vertraut!");
									}
								} else {
									Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu kannst dir nicht selber vertaruen!");
								}

							} else {
								p.sendMessage(Main.Prefix + "Dieser Spieler war noch nie online!");
							}

						} else {
							p.sendMessage(Main.Prefix + "§cDu bist nicht der Besitzer dieses Grundstückes!");
						}

					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu stehst auf keinem Grundstück!");
					}

				} else if (args[0].equalsIgnoreCase("deny") || args[0].equalsIgnoreCase("ban")) {
					if (plot != null) {

						if (plot.getOwner().equals(p.getUniqueId()) || Command_Build.powermode.contains(p)) {

							if (targetUUID != null) {

								if (!targetUUID.equals(p.getUniqueId())) {

									if (!plot.getDenied().contains(targetUUID)) {

										for (Plot gs : plot.getPlotsForSameChange()) {
											gs.getDenied().add(targetUUID);

										}

										Player target = Bukkit.getPlayer(targetUUID);

										if (target != null) {
											for (Plot gs : plot.getPlotsForSameChange()) {
												
												if (gs.isLocOnPlot(target.getLocation().getBlock().getLocation())) {
													target.teleport(
															new Location(Bukkit.getWorld("plotworld"), 0.5, 75, 0.5));
													Utils.sendNoSpamMsg(target, Main.Prefix
															+ "§4Du wurdest vom Grundstück gebannt und deswegen an den Spawn teleportiert!");
												}
											}
											}

										p.sendMessage(Main.Prefix + "§2" + args[1]
												+ " §adarf dieses Grundstück nun nicht mehr betreten!");

									} else {
										Utils.sendNoSpamMsg(p,
												Main.Prefix + "§6" + args[1] + " §cist bereits gebannt!");
									}
								} else {
									Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu kannst dich nicht selber bannen!");
								}

							} else {
								p.sendMessage(Main.Prefix + "Dieser Spieler war noch nie online!");
							}

						} else {
							p.sendMessage(Main.Prefix + "§cDu bist nicht der Besitzer dieses Grundstückes!");
						}

					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu stehst auf keinem Grundstück!");
					}

				} else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
					if (plot != null) {

						if (plot.getOwner().equals(p.getUniqueId()) || Command_Build.powermode.contains(p)) {

							if (args[1].equals("*")) {
								
								for (Plot gs : plot.getPlotsForSameChange()) {
									gs.getDenied().clear();
									gs.getTrusted().clear();
									
								}


								p.sendMessage(Main.Prefix + "§aAlle Spieler wurden entfernt!");

								return true;
							}

							if (targetUUID != null) {

								if (plot.getDenied().contains(targetUUID) || plot.getTrusted().contains(targetUUID)) {

									if (plot.getDenied().contains(targetUUID)) {
										for (Plot gs : plot.getPlotsForSameChange()) {
											gs.getDenied().remove(targetUUID);
											
										}
									}

									if (plot.getTrusted().contains(targetUUID)) {
										for (Plot gs : plot.getPlotsForSameChange()) {
											gs.getTrusted().remove(targetUUID);
											
										}
									}

									p.sendMessage(Main.Prefix + "§2" + args[1] + " §awurde entfernt!");

								} else {
									Utils.sendNoSpamMsg(p,
											Main.Prefix + "§6" + args[1] + " §cist weder gebannt noch vertraut!");
								}

							} else {
								p.sendMessage(Main.Prefix + "§cDieser Spieler war noch nie online!");
							}

						} else {
							p.sendMessage(Main.Prefix + "§cDu bist nicht der Besitzer dieses Grundstückes!");
						}

					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu stehst auf keinem Grundstück!");
					}

				} else if (args[0].equalsIgnoreCase("kick")) {

					if (plot.getOwner().equals(p.getUniqueId()) || Command_Build.powermode.contains(p)) {

						if (args[1].equals("*")) {
							for (Plot gs : plot.getPlotsForSameChange()) {
								
								for (Player onPlot : gs.getPlayersOnPlot()) {
									if (onPlot != p) {
										onPlot.teleport(new Location(Bukkit.getWorld("plotworld"), 0.5, 75, 0.5));
										Utils.sendNoSpamMsg(onPlot, Main.Prefix
												+ "§4Du wurdest vom Grundstück gekickt und deswegen an den Spawn teleportiert!");
									}
								}
								Utils.sendNoSpamMsg(p, Main.Prefix + "§aAlle Spieler wurden vom Grundstück gekickt!");
								return true;
							}
							}

						if (Bukkit.getPlayer(args[1]) != null) {

							Player target = Bukkit.getPlayer(args[1]);
							
							for (Plot gs : plot.getPlotsForSameChange()) {
								if (gs.isLocOnPlot(target.getLocation().getBlock().getLocation())) {
									
									Utils.sendNoSpamMsg(target, "§4Du wurdest vom Grundstück gekickt!");
									target.teleport(new Location(Bukkit.getWorld("plotworld"), 0.5, 75, 0.5));
									Utils.sendNoSpamMsg(p, Main.Prefix + "§a" + target.getName()
									+ " wurde von deinem Grundstück geworfen!");
									
								} else {
									Utils.sendNoSpamMsg(p, Main.Prefix + "§cDer Spieler " + target.getName()
									+ " steht nicht auf deinem Grundstück!");
								}
							}


						} else {
							Utils.sendNoSpamMsg(p, Main.Prefix + "§cDieser Spieler ist nicht online!");
						}

					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu bist nicht der Besitzer dieses Grundstückes");
					}

				} else {
					Utils.sendNoSpamMsg(p, Main.Prefix + "§cUnbekannter Befehl! Mach' §6/gs hilfe §cfür Hilfe");
				}

			} else if (args.length == 3) {

				if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("home")
						|| args[0].equalsIgnoreCase("tp")) {

					UUID targetUUID = Utils.getCashedUUID(args[1]);
					if (targetUUID == null && Command_Build.powermode.contains(p)) {
						targetUUID = MojangAPIAccess.getUUID(args[1]);
					}

					if (targetUUID != null) {

						ArrayList<Plot> playerplots = PlotManager.getPlayerPlots(targetUUID);

						try {
							int id = Integer.parseInt(args[2]);

							Plot tplot = playerplots.get(id - 1);
							p.teleport(tplot.getHome());

							Utils.sendNoSpamMsg(p, Main.Prefix + "§aDu wurdest zu §2" + args[1] + "s " + id
									+ ". §aGrundstück teleportiert!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);

						} catch (NumberFormatException | IndexOutOfBoundsException ex) {
							Utils.sendNoSpamMsg(p, Main.Prefix + "§cGrundstück konnte nicht gefunden werden!");
						}

					} else {
						p.sendMessage(Main.Prefix + "§cDieser Spieler war noch nie online!");
					}
				} else if (args[0].equalsIgnoreCase("setflag") || args[0].equalsIgnoreCase("flagset")
						|| args[0].equalsIgnoreCase("sf")) {

					if (plot != null) {
						if (plot.getOwner().equals(p.getUniqueId()) || Command_Build.powermode.contains(p)) {

							switch (args[1]) {
							case "use":

								if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
									for (Plot gs : plot.getPlotsForSameChange()) {
										gs.setFlagValue(args[1].toLowerCase(), args[2].toLowerCase());
										
									}
									Utils.sendNoSpamMsg(p, Main.Prefix + "§aDie Einstellung §2" + args[1].toUpperCase()
											+ " §awurde auf §2" + args[2].toUpperCase() + " §agestellt!");
								} else {
									Utils.sendNoSpamMsg(p,
											Main.Prefix + "§cBitte gebe als Wert §4false §coder §2true §can!");
								}

								break;
							case "pvp":
								if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
									for (Plot gs : plot.getPlotsForSameChange()) {
										gs.setFlagValue(args[1].toLowerCase(), args[2].toLowerCase());
										
									}
									Utils.sendNoSpamMsg(p, Main.Prefix + "§aDie Einstellung §2" + args[1].toUpperCase()
											+ " §awurde auf §2" + args[2].toUpperCase() + " §agestellt!");
								} else {
									Utils.sendNoSpamMsg(p,
											Main.Prefix + "§cBitte gebe als Wert §4false §coder §2true §can!");
								}
								break;
							case "pve":
								if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
									for (Plot gs : plot.getPlotsForSameChange()) {
										gs.setFlagValue(args[1].toLowerCase(), args[2].toLowerCase());
										
									}
									Utils.sendNoSpamMsg(p, Main.Prefix + "§aDie Einstellung §2" + args[1].toUpperCase()
											+ " §awurde auf §2" + args[2].toUpperCase() + " §agestellt!");
								} else {
									Utils.sendNoSpamMsg(p,
											Main.Prefix + "§cBitte gebe als Wert §4false §coder §2true §can!");
								}
								break;
							case "explosions":
								if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
									for (Plot gs : plot.getPlotsForSameChange()) {
										gs.setFlagValue(args[1].toLowerCase(), args[2].toLowerCase());
										
									}
									Utils.sendNoSpamMsg(p, Main.Prefix + "§aDie Einstellung §2" + args[1].toUpperCase()
											+ " §awurde auf §2" + args[2].toUpperCase() + " §agestellt!");
								} else {
									Utils.sendNoSpamMsg(p,
											Main.Prefix + "§cBitte gebe als Wert §4false §coder §2true §can!");
								}
								break;
							default:
								Utils.sendNoSpamMsg(p, Main.Prefix
										+ "§cMögliche Flags heißen: §6use§c, §6pvp§c, §6pve§c, §6explosions§c!");
								break;
							}

						} else {
							Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu bist nicht der Besitzer dieses Grundstückes");
						}
					} else {
						Utils.sendNoSpamMsg(p, Main.Prefix + "§cDu stehst auf keinem Grundstück!");
					}

				} else {
					Utils.sendNoSpamMsg(p, Main.Prefix + "§cUnbekannter Befehl! Mach' §6/gs hilfe §cfür Hilfe");
				}

			} else {
				Utils.sendNoSpamMsg(p, Main.Prefix + "§cUnbekannter Befehl! Mach' §6/gs hilfe §cfür Hilfe");
			}
		}

		return true;
	}

}
