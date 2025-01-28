package com.stardevllc.hierarchy.spigot;

import com.stardevllc.hierarchy.base.Hierarchy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Set;

@SuppressWarnings("DuplicatedCode")
public class GroupHierarchy extends Hierarchy<String> {
    
    private final ConfigurationSection section;

    public GroupHierarchy(ConfigurationSection section) {
        this.section = section;
    }

    public GroupHierarchy() {
        this(null);
    }

    @Override
    public void init() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        
        if (section == null) {
            return;
        }

        Set<String> keys = section.getKeys(false);
        if (keys == null || keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            int weight = Integer.parseInt(key);
            String group = section.getString(key);
            set(weight, group);
        }
    }
}
