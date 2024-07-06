package xyz.stellar.felicity

import io.github.cdimascio.dotenv.Dotenv

val dotenv = Dotenv.configure().load()

val TOKEN = dotenv["TOKEN"]