package com.gilshapira.imagesss;


import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A variant of {@code HashMap} that lets clients specify a size for every entry
 * that's added and impose a limit on the total size of entries that the map can hold.
 * The least-recently-accessed entries are removed whenever necessary to keep the total
 * size under the limit.
 *
 * @author Gil Shapira
 */
class LimitedStorageHashMap<K, V> implements Map<K, V> {
    
    public static final String LOGTAG = "LimitedStorageHashMap";

    /** The default size given to an entry if no size is specified */
    public static final int DEFAULT_ENTRY_SIZE = 1;
    
    /** The internal hashmap's initial capacity */
    private static final int INITIAL_CAPACITY = 16;
    
    /** The internal hashmap's load factor */
    private static final float LOAD_FACTOR = 0.75f;

    /** The internal container for entries. */
    private final LinkedHashMap<K, InternalEntry> mEntries;
    
    /** A cached reference to the values collection */
    private Collection<V> mValuesCollection;

    /** The maximum size the entries in the map can take. */
    private long mMaxSize;
    
    /** The current size of the entries in the map. */
    private long mSize;
    
    /**
     * Creates a new {@code LimitedStorageHashMap} object.
     * @param maxSize the maximum size the entries in the map can take.
     */
    public LimitedStorageHashMap(long maxSize) {
        if (maxSize < 0) {
            throw new IllegalArgumentException("Attempt to initialize a LimitedCapacityHashMap with invalid maxSize: " + maxSize);
        }
        mMaxSize = maxSize;
        mEntries = new LinkedHashMap<K, InternalEntry>(INITIAL_CAPACITY, LOAD_FACTOR, true);
        mSize = 0;
    }
    
    /**
     * Validates that the {@code LimitedStorageHashMap} isn't exceeding its
     * maximum size and removes least recently accessed entries until it does.
     */
    public void validateSize() {
        Collection<InternalEntry> values = mEntries.values();
        if (values != null) {
            for (Iterator<InternalEntry> i = values.iterator(); i.hasNext() && (mSize > mMaxSize);) {
                InternalEntry entry = i.next();
                mSize -= entry.size;
                i.remove();
            }
        }
    }
    
    /**
     * Sets the maximum size the entries in the map can take.
     * @param maxSize the maximum size the entries in the map can take.
     */
    public void setMaxSize(long maxSize) {
        mMaxSize = maxSize;
        validateSize();
    }
    
    /**
     * @return the maximum size the entries in the map can take.
     */
    public long getMaxSize() {
        return mMaxSize;
    }

    /**
     * @return the total size of all the entries in the {@code LimitedStorageHashMap}.
     */
    public long getTotalSize() {
        return mSize;
    }

    public V get(Object key) {
        InternalEntry entry = mEntries.get(key);
        if (entry != null) {
            return entry.value;
        }
        return null;
    }

    public V put(K key, V value) {
        return put(key, value, DEFAULT_ENTRY_SIZE);
    }

    /**
     * Maps the specified key to the specified value and attaches a size value to this entry.
     * @param key the key.
     * @param value the value.
     * @param size the size of the entry.
     * @return the value of any previous mapping with the specified key or
     * {@code null} if there was no such mapping.
     */
    public V put(K key, V value, int size) {
        mSize += size;
        validateSize();
        InternalEntry prev = mEntries.put(key, new InternalEntry(value, size));
        if (prev != null) {
            return prev.value;
        }
        return null;
    }

    public V remove(Object key) {
        InternalEntry entry = mEntries.remove(key);
        if (entry != null) {
            mSize -= entry.size;
            return entry.value;
        }
        return null;
    }
    
    public void clear() {
        mEntries.clear();
        mSize = 0;
    }

    public boolean isEmpty() {
        return mEntries.isEmpty();
    }

    public int size() {
        return mEntries.size();
    }

    public boolean containsKey(Object key) {
        return mEntries.containsKey(key);
    }

    public Set<K> keySet() {
        return mEntries.keySet();
    }

    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    public Collection<V> values() {
        if (mValuesCollection == null) {
            mValuesCollection = new AbstractCollection<V>() {
                @Override
                public boolean contains(Object object) {
                    Iterator<V> iter = iterator();
                    while (iter.hasNext()) {
                        V value = iter.next();
                        if (value.equals(object)) return true;
                    }
                    return false;
                }

                @Override
                public int size() {
                    return LimitedStorageHashMap.this.size();
                }

                @Override
                public void clear() {
                    LimitedStorageHashMap.this.clear();
                }

                @Override
                public Iterator<V> iterator() {
                    // we return a wrapper around an internal iterator for the map entries
                    // that simply returns an entry's value instead of the entry itself
                    return new Iterator<V>() {
                        
                        private Iterator<InternalEntry> mIter = mEntries.values().iterator();
                        
                        @Override
                        public boolean hasNext() {
                            return mIter.hasNext();
                        }

                        @Override
                        public V next() {
                            return mIter.next().value;
                        }

                        @Override
                        public void remove() {
                            mIter.remove();
                        }
                        
                    };
                }
            };
        }
        return mValuesCollection;
    }
    
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    /**
     * A wrapper for map entries that holds the size in addition to the entry itself. 
     */
    private class InternalEntry {
        
        /** The size of the entry. */
        public int size;
        
        /** The size of the value.  */
        public V value;
        
        /**
         * Creates a new {@code InternalEntry} object.
         * @param v the value.
         * @param s the size of the value.
         */
        public InternalEntry(V v, int s) {
            value = v;
            size = s;
        }
    }

}
