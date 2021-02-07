package com.tolkiana.liquibasedemo.extensions

/**
 * Finds the items that are contained in the this collection but not in the given one.
 * @param b collection to compare
 * @return only the items that are in this collection but not in `b`
 */
fun <T> Iterable<T>.findDiff(b: Iterable<T>): Set<T> {
    val itemsInBoth = this.intersect(b)
    return this.subtract(itemsInBoth).toSet()
}