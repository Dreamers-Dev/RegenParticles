package dev.dreamers.regenparticles

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

class RegenParticles : JavaPlugin(), Listener {
    lateinit var metrics: Metrics
    lateinit var particleAPI: ParticleNativeAPI
    lateinit var particle: ParticleType

    override fun onEnable() {
        metrics = Metrics(this, 24418)
        particleAPI = ParticleNativeCore.loadAPI(this)
        particle = particleAPI.LIST_1_8.VILLAGER_HAPPY.detachCopy()

        Bukkit.getPluginManager().registerEvents(this, this)
    }

    override fun onDisable() {
        metrics.shutdown()
    }

    @EventHandler
    private fun onRegen(event: EntityRegainHealthEvent) {
        val player = event.entity as? Player ?: return
        if (!player.hasPermission("regenparticles.visualize")) return
        val location = player.location

        repeat(8) {
            val particleLocation = location.clone().apply {
                add(
                    Random.nextDouble(-0.5, 0.5),
                    Random.nextDouble(0.0, 2.0),
                    Random.nextDouble(-0.5, 0.5)
                )
            }
            particle.packet(true, particleLocation).sendTo(Bukkit.getOnlinePlayers())
        }
    }
}
