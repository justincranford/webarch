package org.justin.orm.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("unused")
public class CollectionUtil {
	private CollectionUtil() {
		// do nothing, hide constructor
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	public static Collection<? extends Object> create(final Class clazz, final Object object) throws InstantiationException, IllegalAccessException {
		if (null == clazz) {
			throw new InstantiationException("Class cannot be null");
		} else if (null == object) {
			throw new InstantiationException("Object cannot be null");
		} else if (!(Collection.class.isAssignableFrom(clazz))) {
			throw new InstantiationException("Class " + clazz.getCanonicalName() + " is not a Collection");
		}
		Collection<Object> collection = (Collection<Object>) clazz.newInstance();
		collection.add(object);
		return collection;
	}
//
//	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
////		Object e = objectToCollection(Set.class, "a");
//		Object a = objectToCollection(ArrayList.class, "a");
//		Object b = objectToCollection(HashSet.class, Integer.valueOf(1));
////		Object c = objectToCollection(ConcurrentLinkedQueue.class, null);
//		Object d = objectToCollection(null, null);
//		int z = 1;
//	}
}