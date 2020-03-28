package com.navercorp.pinpoint.web.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tankilo
 * https://github.com/apache/shiro/blob/master/core/src/main/java/org/apache/shiro/util/ThreadContext.java
 */
public final class ThreadContext {
    private ThreadContext() {

    }

    public static final String REVERSE = "REVERSE";
    public static final String TABLE_NAME = "TABLE_NAME";
    private static ThreadLocal<Map<String, Object>> resources = new InheritableThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>(8);
        }
    };

    public static void put(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        if (value == null) {
            remove(key);
            return;
        }
        ensureResourcesInitialized();
        resources.get().put(key, value);
    }

    private static void ensureResourcesInitialized() {
        if (resources.get() == null) {
            resources.set(new HashMap<>(8));
        }
    }

    public static void remove(String key) {
        Map<String, Object> map = resources.get();
        if (map != null) {
            map.remove(key);
        }
    }

    public static Object get(String key) {
        Map<String, Object> map = resources.get();
        if (map != null) {
            return map.get(key);
        } else {
            return null;
        }
    }

    private static Object getValue(Object key) {
        Map<String, Object> perThreadResources = resources.get();
        return perThreadResources != null ? perThreadResources.get(key) : null;
    }

    private static Boolean getBoolean(String key, Boolean defaultValue) {
        Object value = getValue(key);
        if (null != value) {
            return Boolean.valueOf(value.toString());
        }
        return defaultValue;
    }

    private static String getString(String key) {
        Object value = getValue(key);
        if (null != value) {
            return value.toString();
        } else {
            return null;
        }
    }

    public static void setReverse(boolean reverse) {
        put(REVERSE, reverse);

    }

    public static boolean isReverse() {
        return getBoolean(REVERSE, false);
    }

    public static void setTableName(String tableName) {
        put(TABLE_NAME, tableName);
    }

    public static String getTableName() {
        return getString(TABLE_NAME);
    }

    public static void remove() {
        resources.remove();
    }
}