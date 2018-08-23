package com.cloudbank.mapper;

/**
 * Mapper interface
 *
 * @param <T> the type of the object which need to be mapped.
 * @param <E> the type of the object into which the object is mapped.
 */
public interface Mapper<T, E> {

    /**
     * Map method
     *
     * @param t the type of the object which need to be mapped
     * @param e instance of object
     * @return  mapped object
     */
    E map(T t, E e);
}
