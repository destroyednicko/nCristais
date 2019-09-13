package kun.nicko.cristais;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils
{
  public static String toColor(String s)
  {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
  
  public static void playSound(Player p, String path)
  {
    float volume = (float)Cristais.getInstance().getConfig().getDouble("sounds." + String.valueOf(path) + ".volume");
    float pitch = (float)Cristais.getInstance().getConfig().getDouble("sounds." + String.valueOf(path) + ".pitch");
    p.playSound(p.getLocation(), Sons.valueOf(Cristais.getInstance().getConfig().getString("sounds." + String.valueOf(path) + ".name").toUpperCase()).bukkitSound(), volume, pitch);
  }
  
  public static String sendMessage(String path)
  {
    return toColor(Cristais.getInstance().getConfig().getString("mensagens.prefixo") + Cristais.getInstance().getConfig().getString(new StringBuilder().append("mensagens.").append(path).toString()));
  }
  
  public static void sendMessage(Player p, String path)
  {
    p.sendMessage(sendMessage(path));
  }
}
