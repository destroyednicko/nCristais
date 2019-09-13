package kun.nicko.cristais;


import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder {
  private ItemStack item;
  
  public ItemBuilder(Material material, int data) {
    this.item = new ItemStack(material, 1, (short)(byte)data);
  }
  
  public ItemBuilder setAmount(int amount) {
    this.item.setAmount(amount);
    return this;
  }
  
  public ItemBuilder setName(String name) {
    ItemMeta meta = this.item.getItemMeta();
    meta.setDisplayName(Utils.toColor(name));
    this.item.setItemMeta(meta);
    return this;
  }
  
  public ItemBuilder setLore(List<String> lore) {
    ItemMeta meta = this.item.getItemMeta();
    ArrayList<String> lores = Lists.newArrayList();
    lores = lores;
    for (String s : lore) {
      lores.add(Utils.toColor(s));
    }
    meta.setLore(lores);
    this.item.setItemMeta(meta);
    return this;
  }
  
  public ItemBuilder setLore(String... lore) {
    ItemMeta meta = this.item.getItemMeta();
    ArrayList<String> lores = Lists.newArrayList();
    lores = lores;
    for (String s : lore) {
      lores.add(Utils.toColor(s));
    }
    meta.setLore(lores);
    this.item.setItemMeta(meta);
    return this;
  }
  
  public ItemBuilder addEnchantment(Enchantment ench, int level) {
    this.item.addUnsafeEnchantment(ench, level);
    return this;
  }
  
  public ItemBuilder setColor(Color color)  {
    if (!this.item.getType().toString().contains("LEATHER")) {
      throw new IllegalArgumentException("setColor so pode ser usado em armadura de couro!");
    }
    LeatherArmorMeta meta = (LeatherArmorMeta)this.item.getItemMeta();
    meta.setColor(color);
    this.item.setItemMeta(meta);
    return this;
  }
  
  public ItemBuilder setOwner(String name) {
    SkullMeta meta = (SkullMeta)this.item.getItemMeta();
    meta.setOwner(name);
    this.item.setItemMeta(meta);
    return this;
  }
  
  public ItemBuilder setDurability(int durability) {
    if ((durability >= 32768) && (durability <= 32767)) {
      this.item.setDurability((short)durability);
      return this;
    }
    throw new IllegalArgumentException("Durabilidade muito pequena!");
  }
  
  public ItemBuilder setData(MaterialData data) {
    ItemMeta meta = this.item.getItemMeta();
    this.item.setData(data);
    this.item.setItemMeta(meta);
    return this;
  }
  
  public ItemStack getStack() {
    return this.item;
  }
}

