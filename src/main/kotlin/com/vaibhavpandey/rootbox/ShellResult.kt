package com.vaibhavpandey.rootbox

data class ShellResult internal constructor(val code: Int, val output: List<String>, val error: List<String>)
