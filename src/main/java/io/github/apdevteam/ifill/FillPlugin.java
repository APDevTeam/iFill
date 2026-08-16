package io.github.apdevteam.ifill;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.apdevteam.ifill.commands.FillCommand;
import io.github.apdevteam.ifill.listeners.FillListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FillPlugin extends JavaPlugin {
	private static FillPlugin instance = null;

	@Nullable
	public static FillPlugin getInstance() {
		return instance;
	}

	public boolean MESSAGE;
	public boolean SOUND;
	public List<String> WORLDS;

	@Override
	public void onEnable() {
		instance = this;

		// Register command
		getCommand("fill").setExecutor(new FillCommand());

		// Register listener
		getServer().getPluginManager().registerEvents(new FillListener(), this);

		// Save default config and load values
		saveDefaultConfig();
		MESSAGE = getConfig().getBoolean("fill-message", true);
		SOUND = getConfig().getBoolean("fill-sound", true);
		WORLDS = getConfig().getStringList("worlds");
	}

	public void fill(@NotNull Block block, @NotNull ItemStack stack) {
		// Strip wand name
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("");
		stack.setItemMeta(meta);

		// Fill all slots
		Inventory inv = ((InventoryHolder) block.getState()).getInventory();
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, stack);
		}
	}
}
