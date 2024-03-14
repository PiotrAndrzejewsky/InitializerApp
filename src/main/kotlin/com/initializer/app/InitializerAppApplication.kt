package com.initializer.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InitializerAppApplication

fun main(args: Array<String>) {
	runApplication<InitializerAppApplication>(*args)
}
