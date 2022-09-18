package pw.chew.cubedworldgen;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class CubedWorldGen extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "CubedWorldGen was enabled successfully.");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "CubedWorldGen was disabled successfully.");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        getLogger().log(Level.WARNING, "CubedWorldGen is used!");
        return new CubedNoiseGenerator(); // Return an instance of the chunk generator we want to use.
    }
}
