package kun.nicko.cristais;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import com.google.common.collect.Lists;

public class Cristais extends JavaPlugin implements CommandExecutor, Listener {
  
  public static Cristais inst;
  public void onEnable() {
    inst = this;
    saveDefaultConfig();
    Bukkit.getPluginManager().registerEvents(this, this);
    getCommand("cristais").setExecutor(this);
    getLogger().severe("oOo - nCristais - Habilitado oOo - Versão: " + getDescription().getVersion());
  }
  
  public ItemStack cristaldslItem() {
    return new ItemBuilder(Material.getMaterial(getConfig().getString("cristal-ds.item.material")), getConfig().getInt("cristal-ds.item.data")).setName(getConfig().getString("cristal-ds.item.nome")).setLore(getConfig().getStringList("cristal-ds.item.lore")).getStack();
  }
  
  public ItemStack getCristal(int nivel) {
    String name = getConfig().getString("cristal-ds.item.nome").replaceAll("%nivel%", nivel + "");
    if ((nivel > 3) || (nivel == 0)) {
      return null;
    }
    ItemStack item = cristaldslItem();
    ItemMeta meta = cristaldslItem().getItemMeta();
    List<String> lore = Lists.newArrayList();
    for (String lore2 : getConfig().getStringList("cristal-ds.item.lore")) {
      lore2 = Utils.toColor(lore2);
      lore2 = lore2.replaceAll("%nivel%", nivel + "");
      lore.add(lore2);
    }
    meta.setLore(lore);
    meta.setDisplayName(Utils.toColor(name));
    item.setItemMeta(meta);
    return item;
  }
  
  public int getCristalNivel(ItemStack cristal) {
    if (cristal.equals(getCristal(1))) {
      return 1;
    }
    if (cristal.equals(getCristal(2))) {
      return 2;
    }
    if (cristal.equals(getCristal(3))) {
      return 3;
    }
    return 0;
  }
  
  public boolean isCristal(ItemStack item) {
    if ((item.getType() == Material.getMaterial(getConfig().getString("cristal-ds.item.material"))) && (item.hasItemMeta())) {
      if ((item.isSimilar(getCristal(1))) || (item.isSimilar(getCristal(2))) || (item.isSimilar(getCristal(3)))) {
        return true;
      }
    }
    else {
      return false;
    }
    return false;
  }
  
  public static boolean isArmour(Material mat) {
    if ((mat == Material.LEATHER_BOOTS) || (mat == Material.IRON_BOOTS) || (mat == Material.DIAMOND_BOOTS) || (mat == Material.CHAINMAIL_BOOTS)) {
      return true;
    }
    return false;
  }
  
  public ItemStack getCurrentItem(ItemStack item, int level) {
    item.addEnchantment(Enchantment.DEPTH_STRIDER, level);
    return item;
  }
  
  @SuppressWarnings("deprecation") @EventHandler
  public void onClick(InventoryClickEvent e) {
    ItemStack cursor = e.getCursor();
    ItemStack item = e.getCurrentItem();
    if ((isCristal(cursor)) && 
      (isArmour(item.getType()))) {
      if ((cursor.getAmount() > 1) || (item.getAmount() > 1)) {
        e.setCancelled(true);
        Utils.sendMessage((Player)e.getWhoClicked(), "iteminvalido");
        return;
      }
      int level = getCristalNivel(cursor);
      if (item.containsEnchantment(Enchantment.DEPTH_STRIDER)) {
        if (getCristalNivel(cursor) > item.getEnchantmentLevel(Enchantment.DEPTH_STRIDER)) {
          e.setCancelled(true);
          e.setCurrentItem(getCurrentItem(item, level));
          e.setCursor(null);
          e.getWhoClicked().sendMessage(Utils.sendMessage("encantadosucesso").replaceAll("%level%", level + ""));
        }
      }
      else {
    	e.setCancelled(true);
        e.setCurrentItem(getCurrentItem(item, level));
        e.setCursor(null);
        e.getWhoClicked().sendMessage(Utils.sendMessage("niveladicionado").replaceAll("%level%", level + ""));
      }
    }
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equalsIgnoreCase("cristais")) {
      switch (args.length) {
      case 0: 
        Utils.sendMessage((Player)sender, "usocorreto");
        break;
      case 4: 
        if (args[0].equalsIgnoreCase("dar")) {
          Player p = Bukkit.getPlayer(args[1]);
          if (p != null) {
            try {
              Integer.parseInt(args[2]);
              Integer.parseInt(args[3]);
            }
            catch (NumberFormatException e) {
              Utils.sendMessage(p, "nivelinvalido");
              return false;
            }
            int id = Integer.parseInt(args[2]);
            int amt = Integer.parseInt(args[3]);
            for (int i = 0; i < amt; i++) {
              p.getInventory().addItem(new ItemStack[] { getCristal(id) });
            }
            sender.sendMessage(Utils.sendMessage("givesucesso").replaceAll("%player%", p.getName()));
            Utils.sendMessage(p, "recebidosucesso");
          }
          else
          {
            Utils.sendMessage(p, "playerinvalido");
          }
        }
        break;
      }
    }
    return false;
  }
  
  public static Cristais getInstance()
  {
    return inst;
  }
}
