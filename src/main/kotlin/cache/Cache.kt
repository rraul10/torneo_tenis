package cache

import cache.errors.CacheErrors
import com.github.michaelbull.result.Result

interface Cache<K, T> {
    fun get(key: K): Result<T, CacheErrors>
    fun set(key: K, value: T): Result<T, CacheErrors>
    fun remove(key: K): Result<T, CacheErrors>
    fun clear()
}