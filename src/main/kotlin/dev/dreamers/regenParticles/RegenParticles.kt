package dev.dreamers.regenParticles

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

class RegenParticles : JavaPlugin(), Listener {

    lateinit var particleAPI: ParticleNativeAPI

    override fun onEnable() {
        particleAPI = ParticleNativeCore.loadAPI(this)


        Bukkit.getPluginManager().registerEvents(this, this)
    }

    override fun onDisable() {}

    @EventHandler
    private fun onRegen(event: EntityRegainHealthEvent) {
        if (event.entity !is Player) return
        val player = event.entity as Player
        val location = player.location

        val random = Random
        repeat(10) {
            val xOffset = random.nextDouble(-0.5, 0.5)
            val yOffset = random.nextDouble(0.0, 2.0)
            val zOffset = random.nextDouble(-0.5, 0.5)

            val particleFeetLocation = location.clone().add(xOffset, yOffset, zOffset)

            particleAPI.LIST_1_8.VILLAGER_HAPPY
                .packet(true, particleFeetLocation)
                .sendTo(Bukkit.getServer().onlinePlayers)
        }
    }
}
