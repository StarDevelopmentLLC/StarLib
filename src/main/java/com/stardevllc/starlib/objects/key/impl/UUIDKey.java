package com.stardevllc.starlib.objects.key.impl;

import com.stardevllc.starlib.helper.StringHelper;
import com.stardevllc.starlib.objects.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class UUIDKey implements Key {
    private final UUID uuid;
    
    public UUIDKey(UUID uuid) {
        this.uuid = uuid;
    }
    
    @Override
    public boolean isEmpty() {
        return uuid == null;
    }
    
    @Override
    public boolean isNotEmpty() {
        return uuid != null;
    }
    
    public UUID getValue() {
        return this.uuid;
    }
    
    @Override
    public boolean contains(String str) {
        //Empty check as an empty key doesn't match anything
        if (this.isEmpty()) {
            return false;
        }
        
        //We don't care about the partial matching stategies, just check for the full uuid
        if (!StringHelper.UUID_PATTERN.matcher(str).matches()) {
            return false;
        }
        
        //Parse the string to a UUID directly
        //Ignore all exceptions and fall-through to the default return of false
        try {
            UUID u = UUID.fromString(str);
            return this.uuid.equals(u);
        } catch (Exception e) {
        }
        
        return false;
    }
    
    @Override
    public boolean contains(Key key) {
        //Empty check as an empty key doesn't match anything
        if (this.isEmpty()) {
            return false;
        }
        
        //If the key is a UUIDKey, then just check the equality of the uuids
        if (key instanceof UUIDKey uuidKey) {
            return Objects.equals(this.uuid, uuidKey.uuid);
        }
        
        //If not, delegate to the contains string method as that handles checking and parsing the string to a UUID
        return contains(key.toString());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.uuid);
    }
    
    @Override
    public boolean equals(Object obj) {
        //First check to see if the object is a UUIDKey, then check the uuids if it is
        if (obj instanceof UUIDKey uuidKey) {
            return Objects.equals(this.uuid, uuidKey.uuid);
        }
        
        //If not, delegate to the contains string method
        return contains(obj.toString());
    }
    
    @Override
    public Object clone() {
        return new UUIDKey(this.uuid);
    }
    
    @Override
    public String toString() {
        //First, check if the uuid is not null, if it is present, then delegate to the UUID#toString method
        if (this.uuid != null) {
            return this.uuid.toString();
        }
        
        //Otherwise, return an empty string
        return "";
    }
    
    @Override
    public int compareTo(@NotNull Key o) {
        //First check to see if this uuid is null, if it is, return -1
        if (this.uuid == null) {
            return -1;
        }
        
        //Next, check to see if the key is a UUIDKey
        if (o instanceof UUIDKey uuidKey) {
            //If it is, check the nullity of the UUID in the other key
            if (uuidKey.uuid == null) {
                //If it is null, return 1
                return 1;
            }
            
            //If null checks passs, then delegate to the UUID#compareTo method
            return this.uuid.compareTo(uuidKey.uuid);
        }
        
        //If the key is not a UUIDKey, then compare the Strings
        return this.toString().compareTo(o.toString());
    }
}