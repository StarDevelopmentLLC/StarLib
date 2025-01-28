package com.stardevllc.hierarchy.spigot;

import com.stardevllc.hierarchy.base.Hierarchy;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Set;

@SuppressWarnings("DuplicatedCode")
public class PermissionHierarchy extends Hierarchy<String> {

    private final ConfigurationSection section;

    public PermissionHierarchy(ConfigurationSection section) {
        this.section = section;
    }
    
    public PermissionHierarchy() {
        this(null);
    }
    
    @Override
    public void init() {
        if (section == null) {
            return;
        }
        
        Set<String> keys = section.getKeys(false);
        if (keys == null || keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            int weight = Integer.parseInt(key);
            String permission = section.getString(key);
            set(weight, permission);
        }
    }
}
