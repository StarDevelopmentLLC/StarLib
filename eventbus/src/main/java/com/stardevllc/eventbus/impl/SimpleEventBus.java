package com.stardevllc.eventbus.impl;

import com.stardevllc.eventbus.EventBus;
import com.stardevllc.eventbus.SubscribeEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class SimpleEventBus<E> implements EventBus<E> {

    private Set<EventHandler<E>> handlers = new HashSet<>();

    @Override
    public void post(E event) {
        for (EventHandler<E> handler : handlers) {
            handler.handle(event);
        }
    }

    @Override
    public void subscribe(Object listener) {
        for (EventHandler<E> handler : handlers) {
            if (handler.getListener().equals(listener)) {
                return;
            }
        }
        handlers.add(new EventHandler<>(listener));
    }

    @Override
    public void unsubscribe(Object object) {
        this.handlers.removeIf(handler -> handler.getListener().equals(object));
    }

    static class EventHandler<E> {
        private Object listener;
        private Map<Class<? extends E>, Method> handlerMethods = new HashMap<>();

        public EventHandler(Object listener) {
            this.listener = listener;

            Class<?> listenerClass = listener.getClass();
            boolean fullClassListener = listenerClass.isAnnotationPresent(SubscribeEvent.class);
            if (!fullClassListener) {
                Class<?> superclass = listenerClass.getSuperclass();
                if (superclass != null && superclass.isAnnotationPresent(SubscribeEvent.class)) {
                    fullClassListener = true;
                }

                for (Class<?> superInterface : listenerClass.getInterfaces()) {
                    if (superInterface.isAnnotationPresent(SubscribeEvent.class)) {
                        fullClassListener = true;
                        break;
                    }
                }
            }

            for (Method method : listenerClass.getDeclaredMethods()) {
                if (fullClassListener || method.isAnnotationPresent(SubscribeEvent.class)) {
                    Parameter[] parameters = method.getParameters();
                    if (parameters.length == 1) {
                        method.setAccessible(true);
                        try {
                            handlerMethods.put((Class<? extends E>) parameters[0].getType(), method);
                        } catch (Exception e) {}
                    }
                }
            }
        }

        public Object getListener() {
            return listener;
        }

        public void handle(Object event) {
            for (Class<?> parameterClazz : handlerMethods.keySet()) {
                if (parameterClazz.isAssignableFrom(event.getClass())) {
                    Method method = handlerMethods.get(parameterClazz);
                    try {
                        method.invoke(listener, event);
                    } catch (Exception e) {
                        throw new RuntimeException("Could not pass " + event.getClass().getName() + " to " + listener.getClass().getName() + "." + method.getName(), e);
                    }
                }
            }
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;

            EventHandler that = (EventHandler) object;
            return Objects.equals(listener, that.listener);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(listener);
        }
    }
}
