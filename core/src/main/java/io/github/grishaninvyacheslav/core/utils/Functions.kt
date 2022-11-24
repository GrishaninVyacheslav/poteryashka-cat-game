package io.github.grishaninvyacheslav.core.utils

import kotlinx.coroutines.delay

suspend inline fun <T> executeWithDelayedRetry(
    predicate: (cause: Throwable) -> Boolean = { true },
    retries: Int,
    delay: Long,
    call: () -> T
): T {
     for (i in 0..retries) {
        return try {
            call()
        } catch (e: Exception) {
            if (predicate(e) && i < retries) {
                delay(delay)
                continue
            } else throw e
        }
    }
    throw Exception("io.github.grishaninvyacheslav.human_playground.utils.Functions executeWithDelayedRetry: Unreachable code reached")
}