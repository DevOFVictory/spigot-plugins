package de.devofvictory.survivalproject.custom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.devofvictory.survivalproject.utils.TeleporterUtils;

public class CustomRecipes {

	@SuppressWarnings("deprecation")
	public static void load() {

		ItemStack stone = new ItemStack(Material.COBBLESTONE);
		ShapelessRecipe stoneRecipe = new ShapelessRecipe(stone);
		stoneRecipe.addIngredient(Material.DIRT);
		Bukkit.addRecipe(stoneRecipe);

		ItemStack dirt = new ItemStack(Material.DIRT);
		ShapelessRecipe dirtRecipe = new ShapelessRecipe(dirt);
		dirtRecipe.addIngredient(Material.COBBLESTONE);
		Bukkit.addRecipe(dirtRecipe);

		ItemStack nametag = new ItemStack(Material.NAME_TAG);
		ShapedRecipe nametagRecipe = new ShapedRecipe(nametag);
		nametagRecipe.shape("AAA", "SIP", "AAA");
		nametagRecipe.setIngredient('A', Material.AIR);
		nametagRecipe.setIngredient('I', Material.IRON_INGOT);
		nametagRecipe.setIngredient('P', Material.PAPER);
		nametagRecipe.setIngredient('S', Material.STRING);

		ItemStack sabberkanone = new ItemStack(Material.STICK);
		ItemMeta sabberkanoneMeta = sabberkanone.getItemMeta();
		sabberkanoneMeta.setDisplayName("§6§lSabberKanone");
		sabberkanone.setItemMeta(sabberkanoneMeta);

		ShapedRecipe sabberkanoneRecipe = new ShapedRecipe(sabberkanone);
		sabberkanoneRecipe.shape("AAA", "SIZ", "AAA");
		sabberkanoneRecipe.setIngredient('A', Material.AIR);
		sabberkanoneRecipe.setIngredient('I', Material.IRON_NUGGET);
		sabberkanoneRecipe.setIngredient('S', Material.STICK);
		sabberkanoneRecipe.setIngredient('Z', Material.SUGAR);
		Bukkit.addRecipe(sabberkanoneRecipe);

		ItemStack jetpack = new ItemStack(Material.GOLDEN_CHESTPLATE);
		ItemMeta jetpackMeta = sabberkanone.getItemMeta();
		jetpackMeta.setDisplayName("§6§lJetpack");
		jetpack.setItemMeta(jetpackMeta);

		ShapedRecipe jetpackRecipe = new ShapedRecipe(jetpack);
		jetpackRecipe.shape("RRR", "RSR", "RRR");
		jetpackRecipe.setIngredient('R', Material.REDSTONE);
		jetpackRecipe.setIngredient('S', Material.GOLDEN_CHESTPLATE);
		Bukkit.addRecipe(jetpackRecipe);

		ItemStack fuel = new ItemStack(Material.COAL);
		ItemMeta fuelMeta = fuel.getItemMeta();
		fuelMeta.setDisplayName("§6§lFuel");
		fuel.setItemMeta(fuelMeta);
		ShapelessRecipe fuelRecipe = new ShapelessRecipe(fuel);
		fuelRecipe.addIngredient(Material.COAL);
		Bukkit.addRecipe(fuelRecipe);
		
		ItemStack tpblock = new ItemStack(Material.WHITE_WOOL, 2);
		ItemMeta tpblockMeta = tpblock.getItemMeta();
		tpblockMeta.setDisplayName(TeleporterUtils.teleporterName);
		tpblock.setItemMeta(tpblockMeta);
		ShapelessRecipe tpblockRecipe = new ShapelessRecipe(tpblock);
		tpblockRecipe.addIngredient(Material.WHITE_WOOL);
		tpblockRecipe.addIngredient(Material.WHITE_WOOL);
		tpblockRecipe.addIngredient(Material.ENDER_PEARL);
		Bukkit.addRecipe(tpblockRecipe);

	}

}
