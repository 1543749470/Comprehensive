package me.hgj.jetpackmvvm.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * @author heminghao
 * @description:
 * @date :2020/11/12 3:22 PM
 */
fun launch(context: CoroutineContext = Dispatchers.IO, block: suspend () -> Unit) {
    CoroutineScope(context).launch {
        block()
    }
}
