package com.stardevllc.starlib.serialization;

import java.util.Map;

/**
 * This class marks another class as serializable, but as a Map. This is extremely similar to the config serializable from bukkit/spigot <br>
 * Also similar to the serializable stuff in the StarDevLLC config library <br>
 * In addition to needing to implement the serialize method, all classes must include at least one of: A static method "deserialize" that accepts a single Map<String, Object> and returns and instance of this class, or a constructor that accepts a Map<String, Object>
 */
@FunctionalInterface
public interface StarSerializable {
    Map<String, Object> serialize();
}