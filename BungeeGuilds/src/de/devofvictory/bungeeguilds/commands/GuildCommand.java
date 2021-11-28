package de.devofvictory.bungeeguilds.commands;

import de.devofvictory.bungeeguilds.objects.Guild;
import de.devofvictory.bungeeguilds.utils.GuildManager;
import de.devofvictory.bungeeguilds.utils.GuildMessageUtil;
import de.devofvictory.bungeeguilds.utils.OtherUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class GuildCommand extends Command {

	public GuildCommand(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (sender instanceof ProxiedPlayer) {

			ProxiedPlayer pp = (ProxiedPlayer) sender;

			if (args.length == 0) {
				if (GuildManager.isInGuild(pp.getUniqueId())) {

					GuildMessageUtil.sendMessage(pp, "GuildHelp");

				} else {
					GuildMessageUtil.sendMessage(pp, "NotInGuild");
				}
			} else if (args.length == 1) {

			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("create")) {
					if (OtherUtils.hasPermissions(pp, "create")) {
						if (OtherUtils.isGuildNameValid(args[1])) {
							if (GuildManager.getGuildByName(args[1]) == null) {
								Guild guild = new Guild(args[1], pp.getUniqueId());
								GuildManager.registerGuild(guild);
								GuildMessageUtil.sendMessage(pp, "GuildCreated");
							}
						} else {
							GuildMessageUtil.sendMessage(pp, "GuildNameNotValid");
						}
					} else {
						GuildMessageUtil.sendMessage(pp, "NoPermission");
					}
				} else if (args[0].equalsIgnoreCase("join")) {
					Guild guild = GuildManager.getGuildByName(args[1]);
					if (guild != null) {
						ProxiedPlayer owner = BungeeCord.getInstance().getPlayer(guild.getOwner());
						if (owner != null) {
							GuildMessageUtil.sendMessage(owner,
									"§a" + pp + " wants to join your guild. You can send him a invation.");
							GuildMessageUtil.sendFormattedMessage(pp, "JoinRequestSent");
						} else {
							GuildMessageUtil.sendMessage(sender, "GuildOwnerNotOnline");
						}
					} else {
						GuildMessageUtil.sendMessage(sender, "GuildNotExists");
					}
				} else if (args[0].equalsIgnoreCase("discord")) {
					if (GuildManager.isInGuild(pp.getUniqueId())) {
						Guild guild = GuildManager.getGuild(pp.getUniqueId());
						if (OtherUtils.hasPermissions(pp, "discord")) {
							if (args[1].startsWith("https://discord.gg/")) {
								guild.setDiscordInvition(args[1]);
								GuildMessageUtil.sendMessage(pp, "DiscordSet");
							} else {
								GuildMessageUtil.sendMessage(pp, "NotValidDiscordInvition");
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("invite")) {
					if (GuildManager.isInGuild(pp.getUniqueId())) {
						Guild guild = GuildManager.getGuild(pp.getUniqueId());
						if (OtherUtils.hasPermissions(pp, "invite")) {
							ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
							if (target != null) {
								GuildManager.invations.put(target, guild);
								GuildMessageUtil.sendMessage(target, "§a" + pp + "invited you to the §2"+guild.getName()+" §aguild. To accept to /guild accept.");
								GuildMessageUtil.sendFormattedMessage(pp, "InvitionSent");
							} else {
								GuildMessageUtil.sendFormattedMessage(pp, "PlayerNotOnline");
							}
						}
					}else {
						GuildMessageUtil.sendFormattedMessage(pp, "NotInGuild");
					}
				}else if (args[0].equalsIgnoreCase("kick")) {
					if (GuildManager.isInGuild(pp.getUniqueId())) {
						Guild guild = GuildManager.getGuild(pp.getUniqueId());
						if (OtherUtils.hasPermissions(pp, "kick")) {
							ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
							if (target != null) {
								GuildManager.invations.put(target, guild);
								GuildMessageUtil.sendMessage(target, "§a" + pp + "invited you to the §2"+guild.getName()+" §aguild. To accept to /guild accept.");
								GuildMessageUtil.sendFormattedMessage(pp, "InvitionSent");
							} else {
								GuildMessageUtil.sendFormattedMessage(pp, "PlayerNotOnline");
							}
						}
					}else {
						GuildMessageUtil.sendFormattedMessage(pp, "NotInGuild");
					}
				}
			}

		} else {
			GuildMessageUtil.sendMessage(sender, "NotPlayer");
		}

	}

}
