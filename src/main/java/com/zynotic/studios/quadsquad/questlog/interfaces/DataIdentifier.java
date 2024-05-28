package com.zynotic.studios.quadsquad.questlog.interfaces;

/**
 * Interface defining methods for objects with varying unique identifiers.
 * Classes implementing this interface must provide methods to get and set the identifier.
 */
public interface DataIdentifier {

    /**
     * Retrieves the unique identifier of the object.
     *
     * @return The unique identifier of the object.
     */
    int getId();

    /**
     * Sets the unique identifier of the object.
     *
     * @param id The unique identifier to set.
     */
    void setId(int id);
}
