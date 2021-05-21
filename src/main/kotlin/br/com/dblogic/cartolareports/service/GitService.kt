package br.com.dblogic.cartolareports.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

@Service
class GitService {

    private val logger = LoggerFactory.getLogger(GitService::class.java)

    @Throws(IOException::class, InterruptedException::class)
    fun gitPull(directory: Path) {
        logger.info("git pull on dir: $directory")
        runCommand(directory, "git", "pull")
    }

    @Throws(IOException::class, InterruptedException::class)
    fun gitClone(directory: Path, originUrl: String) {
        runCommand(directory.parent, "git", "clone", originUrl, directory.fileName.toString())
    }

    @Throws(IOException::class, InterruptedException::class)
    fun runCommand(directory: Path, vararg command: String) {
        Objects.requireNonNull(directory, "directory")
        if (!Files.exists(directory)) {
            throw RuntimeException("can't run command in non-existing directory '$directory'")
        }
        val pb = ProcessBuilder()
                    .command(*command)
                    .directory(directory.toFile())
        val p = pb.start()
        val outputGobbler = StreamGobbler(p.inputStream, "OUTPUT")
        val errorGobbler = StreamGobbler(p.errorStream, "ERROR")
        outputGobbler.start()
        errorGobbler.start()
        val exit = p.waitFor()
        errorGobbler.join()
        outputGobbler.join()
        if (exit != 0) {
            throw AssertionError(String.format("runCommand returned %d", exit))
        }
    }

    private class StreamGobbler(private val inputStream: InputStream, private val type: String) : Thread() {
        private val logger = LoggerFactory.getLogger(StreamGobbler::class.java)
        override fun run() {
            logger.info("type: $type")
            logger.info("inputStream not null: ${inputStream != null}")
            try {
                BufferedReader(InputStreamReader(inputStream)).use { r ->
                    r.lineSequence().forEach {
                        println("$type> $it")
                    }
                }
            } catch (ioe: IOException) {
                ioe.printStackTrace()
            }
        }
    }

}