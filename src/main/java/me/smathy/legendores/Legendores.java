package me.smathy.legendores;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Legendores extends JavaPlugin implements Listener {

    private static final String FIRST_JOIN_METADATA_KEY = "LegendOresFirstJoin";

    public void onEnable(Legendores plugin) {
        FileConfiguration config = plugin.getConfig();
        String onLoadMessage = config.getString("options.onLoadMessage");
        System.out.println(onLoadMessage);
        getCommand("ench").setExecutor(this);
        getCommand("ench").setTabCompleter(this);
        getCommand("updatepack").setExecutor(this);
        getCommand("pack").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        System.out.println("|-----------------------------------|");
        System.out.println("| LegendOres plugin just shut down! |");
        System.out.println("|-----------------------------------|");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        event.getPlayer().sendMessage("1");
        if (!event.getPlayer().hasPlayedBefore()) {
            event.getPlayer().sendMessage("2");
            event.setJoinMessage(null);
            event.getPlayer().sendMessage("3");
            event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Welcome! This is your first time joining!");
            event.getPlayer().sendMessage("4");
            event.getPlayer().setMetadata(FIRST_JOIN_METADATA_KEY, new FixedMetadataValue(this, true));
            event.getPlayer().sendMessage("5");
        } else {
            event.getPlayer().sendMessage("6");
            event.setJoinMessage(null);
            event.getPlayer().sendMessage("7");
            getServer().broadcastMessage(ChatColor.GREEN + "+ " + playerName);
            event.getPlayer().sendMessage("8");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        getServer().broadcastMessage(ChatColor.RED + "- " + event.getPlayer().getName());
    }

    public boolean isArmor(ItemStack itemStack) {
        Material material = itemStack.getType();
        return material == Material.LEATHER_HELMET ||
                material == Material.LEATHER_CHESTPLATE ||
                material == Material.LEATHER_LEGGINGS ||
                material == Material.LEATHER_BOOTS ||
                material == Material.CHAINMAIL_HELMET ||
                material == Material.CHAINMAIL_CHESTPLATE ||
                material == Material.CHAINMAIL_LEGGINGS ||
                material == Material.CHAINMAIL_BOOTS ||
                material == Material.IRON_HELMET ||
                material == Material.IRON_CHESTPLATE ||
                material == Material.IRON_LEGGINGS ||
                material == Material.IRON_BOOTS ||
                material == Material.GOLDEN_HELMET ||
                material == Material.GOLDEN_CHESTPLATE ||
                material == Material.GOLDEN_LEGGINGS ||
                material == Material.GOLDEN_BOOTS ||
                material == Material.DIAMOND_HELMET ||
                material == Material.DIAMOND_CHESTPLATE ||
                material == Material.DIAMOND_LEGGINGS ||
                material == Material.DIAMOND_BOOTS ||
                material == Material.NETHERITE_HELMET ||
                material == Material.NETHERITE_CHESTPLATE ||
                material == Material.NETHERITE_LEGGINGS ||
                material == Material.NETHERITE_BOOTS ||
                material == Material.CARVED_PUMPKIN;
    }

    public boolean isTool(ItemStack itemStack) {
        Material material = itemStack.getType();
        return material == Material.WOODEN_SWORD ||
                material == Material.WOODEN_PICKAXE ||
                material == Material.WOODEN_SHOVEL ||
                material == Material.WOODEN_AXE ||
                material == Material.WOODEN_HOE ||
                material == Material.STONE_SWORD ||
                material == Material.STONE_PICKAXE ||
                material == Material.STONE_SHOVEL ||
                material == Material.STONE_AXE ||
                material == Material.STONE_HOE ||
                material == Material.IRON_SWORD ||
                material == Material.IRON_PICKAXE ||
                material == Material.IRON_SHOVEL ||
                material == Material.IRON_AXE ||
                material == Material.IRON_HOE ||
                material == Material.GOLDEN_SWORD ||
                material == Material.GOLDEN_PICKAXE ||
                material == Material.GOLDEN_SHOVEL ||
                material == Material.GOLDEN_AXE ||
                material == Material.GOLDEN_HOE ||
                material == Material.DIAMOND_SWORD ||
                material == Material.DIAMOND_PICKAXE ||
                material == Material.DIAMOND_SHOVEL ||
                material == Material.DIAMOND_AXE ||
                material == Material.DIAMOND_HOE ||
                material == Material.NETHERITE_SWORD ||
                material == Material.NETHERITE_PICKAXE ||
                material == Material.NETHERITE_SHOVEL ||
                material == Material.NETHERITE_AXE ||
                material == Material.NETHERITE_HOE;
    }

    public boolean isPickaxe(ItemStack itemStack) {
        Material material = itemStack.getType();
        return material == Material.WOODEN_PICKAXE ||
                material == Material.STONE_PICKAXE ||
                material == Material.IRON_PICKAXE ||
                material == Material.GOLDEN_PICKAXE ||
                material == Material.DIAMOND_PICKAXE ||
                material == Material.NETHERITE_PICKAXE;
    }

    public boolean isEnchanted(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isAir()) {
            return false;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        // Check if the item has metadata
        if (itemMeta == null) {
            return false;
        }

        // Check if the item has enchantments
        return itemMeta.hasEnchants();
    }

    private String link = null;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if ("ench".equalsIgnoreCase(cmd.getName())) {
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (!itemInHand.getType().isAir()) {
                if (args.length == 2) { // Checks if there's an argument.
                    if (!isEnchanted(itemInHand)) {
                        if ("blastprotect".equals(args[0])) {
                            if (isArmor(itemInHand)) {
                                if ("1".equals(args[1]) || "2".equals(args[1]) || "3".equals(args[1]) || "4".equals(args[1])) {
                                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft("blast_protection"));
                                    if ("1".equals(args[1])) {
                                        if (player.getLevel() > 9) {
                                            player.giveExpLevels(-10);
                                            itemInHand.addUnsafeEnchantment(enchantment, 1);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 10.");
                                            return true;
                                        }
                                    } else if ("2".equals(args[1])) {
                                        if (player.getLevel() > 19) {
                                            player.giveExpLevels(-20);
                                            itemInHand.addUnsafeEnchantment(enchantment, 2);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 20.");
                                            return true;
                                        }
                                    } else if ("3".equals(args[1])) {
                                        if (player.getLevel() > 49) {
                                            player.giveExpLevels(-50);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 50.");
                                            return true;
                                        }
                                    } else if ("4".equals(args[1])) {
                                        if (player.getLevel() > 99) {
                                            player.giveExpLevels(-100);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 100.");
                                            return true;
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "There's only 3 levels available. More are coming soon!");
                                        return true;
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You can only enchant this on armor.");
                                return true;
                            }
                        } else if ("durability".equalsIgnoreCase(args[0])) {
                            if (isArmor(itemInHand) || isTool(itemInHand)) {
                                if ("1".equals(args[1]) || "2".equals(args[1]) || "3".equals(args[1]) || "4".equals(args[1])) {
                                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft("durability"));
                                    if ("1".equals(args[1])) {
                                        if (player.getLevel() > 9) {
                                            player.giveExpLevels(-10);
                                            itemInHand.addUnsafeEnchantment(enchantment, 1);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 10.");
                                            return true;
                                        }
                                    } else if ("2".equals(args[1])) {
                                        if (player.getLevel() > 19) {
                                            player.giveExpLevels(-20);
                                            itemInHand.addUnsafeEnchantment(enchantment, 2);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 20.");
                                            return true;
                                        }
                                    } else if ("3".equals(args[1])) {
                                        if (player.getLevel() > 49) {
                                            player.giveExpLevels(-50);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 50.");
                                            return true;
                                        }
                                    } else if ("4".equals(args[1])) {
                                        if (player.getLevel() > 99) {
                                            player.giveExpLevels(-100);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 100.");
                                            return true;
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "There's only 3 levels available. More are coming soon!");
                                        return true;
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You can't enchant this item if it isn't armor or a tool.");
                                return true;
                            }
                        } else if ("efficiency".equalsIgnoreCase(args[0])) {
                            if (isTool(itemInHand)) {
                                if ("1".equals(args[1]) || "2".equals(args[1]) || "3".equals(args[1]) || "4".equals(args[1])) {
                                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft("efficiency"));
                                    if ("1".equals(args[1])) {
                                        if (player.getLevel() > 9) {
                                            player.giveExpLevels(-10);
                                            itemInHand.addUnsafeEnchantment(enchantment, 1);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 10.");
                                            return true;
                                        }
                                    } else if ("2".equals(args[1])) {
                                        if (player.getLevel() > 19) {
                                            player.giveExpLevels(-20);
                                            itemInHand.addUnsafeEnchantment(enchantment, 2);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 20.");
                                            return true;
                                        }
                                    } else if ("3".equals(args[1])) {
                                        if (player.getLevel() > 49) {
                                            player.giveExpLevels(-50);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 50.");
                                            return true;
                                        }
                                    } else if ("4".equals(args[1])) {
                                        if (player.getLevel() > 99) {
                                            player.giveExpLevels(-100);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 100.");
                                            return true;
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "There's only 3 levels available. More are coming soon!");
                                        return true;
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You can only enchant this on tools.");
                                return true;
                            }
                        } else if ("flame".equalsIgnoreCase(args[0])) {
                            Material material = itemInHand.getType();
                            if (material == Material.BOW) {
                                if ("1".equals(args[1]) || "2".equals(args[1]) || "3".equals(args[1]) || "4".equals(args[1])) {
                                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft("flame"));
                                    if ("1".equals(args[1])) {
                                        if (player.getLevel() > 9) {
                                            player.giveExpLevels(-10);
                                            itemInHand.addUnsafeEnchantment(enchantment, 1);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 10.");
                                            return true;
                                        }
                                    } else if ("2".equals(args[1])) {
                                        if (player.getLevel() > 19) {
                                            player.giveExpLevels(-20);
                                            itemInHand.addUnsafeEnchantment(enchantment, 2);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 20.");
                                            return true;
                                        }
                                    } else if ("3".equals(args[1])) {
                                        if (player.getLevel() > 49) {
                                            player.giveExpLevels(-50);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 50.");
                                            return true;
                                        }
                                    } else if ("4".equals(args[1])) {
                                        if (player.getLevel() > 99) {
                                            player.giveExpLevels(-100);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 100.");
                                            return true;
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "There's only 3 levels available. More are coming soon!");
                                        return true;
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You can only enchant this on a bow.");
                                return true;
                            }
                        } else if ("fortune".equalsIgnoreCase(args[0])) {
                            if (isPickaxe(itemInHand)) {
                                if ("1".equals(args[1]) || "2".equals(args[1]) || "3".equals(args[1]) || "4".equals(args[1])) {
                                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft("fortune"));
                                    if ("1".equals(args[1])) {
                                        if (player.getLevel() > 9) {
                                            player.giveExpLevels(-10);
                                            itemInHand.addUnsafeEnchantment(enchantment, 1);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 10.");
                                            return true;
                                        }
                                    } else if ("2".equals(args[1])) {
                                        if (player.getLevel() > 19) {
                                            player.giveExpLevels(-20);
                                            itemInHand.addUnsafeEnchantment(enchantment, 2);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 20.");
                                            return true;
                                        }
                                    } else if ("3".equals(args[1])) {
                                        if (player.getLevel() > 49) {
                                            player.giveExpLevels(-50);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 50.");
                                            return true;
                                        }
                                    } else if ("4".equals(args[1])) {
                                        if (player.getLevel() > 99) {
                                            player.giveExpLevels(-100);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 100.");
                                            return true;
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "There's only 3 levels available. More are coming soon!");
                                        return true;
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You can only enchant this on a bow.");
                                return true;
                            }
                        } else if ("protect".equalsIgnoreCase(args[0])) {
                            if (isArmor(itemInHand)) {
                                if ("1".equals(args[1]) || "2".equals(args[1]) || "3".equals(args[1]) || "4".equals(args[1])) {
                                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft("protect"));
                                    if ("1".equals(args[1])) {
                                        if (player.getLevel() > 9) {
                                            player.giveExpLevels(-10);
                                            itemInHand.addUnsafeEnchantment(enchantment, 1);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 10.");
                                            return true;
                                        }
                                    } else if ("2".equals(args[1])) {
                                        if (player.getLevel() > 19) {
                                            player.giveExpLevels(-20);
                                            itemInHand.addUnsafeEnchantment(enchantment, 2);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 20.");
                                            return true;
                                        }
                                    } else if ("3".equals(args[1])) {
                                        if (player.getLevel() > 49) {
                                            player.giveExpLevels(-50);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 50.");
                                            return true;
                                        }
                                    } else if ("4".equals(args[1])) {
                                        if (player.getLevel() > 99) {
                                            player.giveExpLevels(-100);
                                            itemInHand.addUnsafeEnchantment(enchantment, 3);
                                            player.sendMessage(ChatColor.GREEN + "You successfully enchanted your item.");
                                            return false;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You need to be at least level 100.");
                                            return true;
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "There's only 3 levels available. More are coming soon!");
                                        return true;
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You can only enchant this on a bow.");
                                return true;
                            }
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You can't enchant something that is already enchanted.");
                        return true;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Need a enchantment name and a enchantment level.");
                    return true;
                }
            } else {
                player.sendMessage(ChatColor.RED + "You can't enchant air!");
            }
            return false;
        } else if ("updatepack".equalsIgnoreCase(cmd.getName())) {
            if (player.isOp()) {
                if (args.length > 0) {
                    link = args[0];
                    player.sendMessage(ChatColor.GREEN + "Pack link set to: " + ChatColor.BLUE + link);
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /updatepack <pack_link>");
                }
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You can't use this command!");
                return true;
            }
        } else if ("pack".equalsIgnoreCase(cmd.getName())) {
            if (link != null) {
                String message = ChatColor.GREEN + "Here is the link of the pack: " + ChatColor.BLUE + link;
                player.sendMessage(message);
                return true;
            } else {
                if (player.isOp()) {
                    player.sendMessage(ChatColor.RED + "You should set a link for the pack using /updatepack <link>.");
                    return false;
                } else {
                    player.sendMessage(ChatColor.RED + "Error: Please ask a staff member to update the pack link.");
                    return false;
                }
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("ench") && args.length == 1) {
            completions.add("blastprotect");
            completions.add("durability");
            completions.add("efficiency");
            completions.add("flame");
            completions.add("fortune");
            completions.add("protect");
        }
        if (command.getName().equalsIgnoreCase("ench") && args.length == 2) {
            completions.add("1");
            completions.add("2");
            completions.add("3");
            completions.add("4");
        }
        return completions;
    }
}
