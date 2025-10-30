package com.stardevllc.starlib.injector;

import com.stardevllc.starlib.helper.ObjectProvider;
import com.stardevllc.starlib.helper.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

public class SimpleFieldInjector implements FieldInjector {
    private final Map<Class<?>, ObjectProvider<?>> instances = new TreeMap<>((o1, o2) -> {
        //Classes are the same
        if (Objects.equals(o1, o2)) {
            return 0;
        }
        
        //If o1 is an interface but o2 is not, then we want it lower in the hierarchy
        if (o1.isInterface() && !o2.isInterface()) {
            return -1;
        }
        
        //If o1 is not an interface but o2 is, then we want it higher in the hierarchy
        if (!o1.isInterface() && o2.isInterface()) {
            return 1;
        }
        
        //o1 is a parent of o2
        //  We want it to be further down in the map
        if (o1.isAssignableFrom(o2)) {
            return -11;
        }
        
        //Otherwise just return 1 as it doesn't matter at that point
        return 1;
    });
    
    private final Set<FieldInjector> parentInjectors = new HashSet<>();
    
    @Override
    public <I> I inject(I object) {
        boolean fullClassInject = object.getClass().isAnnotationPresent(Inject.class);
        //Get all fields of the class
        Map<String, Field> fields = ReflectionHelper.getClassFields(object.getClass());
        //Loop through the fields
        fieldLoop:
        for (Field field : fields.values()) {
            //Only search fields that have the @Inject annotation
            if (!field.isAnnotationPresent(Inject.class) && !fullClassInject) {
                continue;
            }
            
            //Check to see if the field's type is in the map of instances
            //This gives direct class types the priority if multiple of the same inheritance tree exists
            if (instances.containsKey(field.getType())) {
                setFieldValue(object, field, instances.get(field.getType()).get());
                continue;
            }
            
            //Search through the tree map to find the nearest class type to the field type
            for (Entry<Class<?>, ObjectProvider<?>> entry : this.instances.entrySet()) {
                Class<?> clazz = entry.getKey();
                Object instance = entry.getValue().get();
                //If the clazz of the map is a parent of the field's type, set the instance
                //Due to the sorting of the TreeMap, this should be the closest in the inheritance tree to the field type
                if (field.getType().isAssignableFrom(clazz)) {
                    setFieldValue(object, field, instance);
                    continue fieldLoop;
                }
            }
        }
        
        //Call parent injectors
        this.parentInjectors.forEach(injector -> injector.inject(object));
        return object;
    }
    
    private void setFieldValue(Object object, Field field, Object value) {
        //Set the field to the value and catch exceptions
        try {
            //Set the field to be accessible
            field.setAccessible(true);
            
            //Ignore fields that have values (To allow overridding parent injector's instances)
            if (field.get(object) != null) {
                return;
            }
            
            //Set the value
            field.set(object, value);
        } catch (Exception e) {
            System.err.println("Could not set " + field.getName() + " in class " + object.getClass());
            e.printStackTrace();
        }
    }
    
    public <I> ObjectProvider<I> getProvider(Class<? super I> clazz) {
        return (ObjectProvider<I>) this.instances.computeIfAbsent(clazz, c -> new ObjectProvider<>());
    }
    
    @Override
    public Set<FieldInjector> getParentInjectors() {
        return new HashSet<>(this.parentInjectors);
    }
    
    @Override
    public void addParentInjector(FieldInjector injector) {
        this.parentInjectors.add(injector);
    }
}