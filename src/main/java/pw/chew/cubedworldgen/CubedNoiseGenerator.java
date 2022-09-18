package pw.chew.cubedworldgen;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CubedNoiseGenerator extends ChunkGenerator {
    private final int MAX_CHUNKS = 32;
    private final List<CubedSurvivalLayer> layers = new ArrayList<>();

    public CubedNoiseGenerator() {
        // Layer 0 (Bedrock)
        layers.add(new CubedSurvivalLayer(0, 1, Collections.singletonList(new CubedSurvivalLayer.LayerMaterial(Material.BEDROCK, 100))));

        // Layer 1
        layers.add(new CubedSurvivalLayer(1, 16, CubedSurvivalLayer.materialFromString(
            "diamond_ore:3",
            "obsidian:0.1",
            "lava:0.7",
            "gold_ore:9",
            "emerald_ore:2",
            "emerald_block:0.1",
            "stone:73.2",
            "iron_ore:2",
            "redstone_ore:1.4",
            "lapis_ore:1.4",
            "coal_ore:2",
            "gravel:5",
            "ancient_debris:0.1"
        )));

        // Layer 2
        layers.add(new CubedSurvivalLayer(17, 80, CubedSurvivalLayer.materialFromString(
            "gold_ore:10",
            "emerald_ore:0.5",
            "stone:71.5",
            "iron_ore:8",
            "coal_ore:8",
            "gravel:2"
        )));

        // Layer 3
        layers.add(new CubedSurvivalLayer(81, 190, CubedSurvivalLayer.materialFromString(
            "stone:71.75",
            "iron_ore:12",
            "coal_ore:12",
            "gravel:1",
            "emerald_ore:0.25"
        )));

        // Layer 4
        layers.add(new CubedSurvivalLayer(191, 200, CubedSurvivalLayer.materialFromString(
            "OAK_LOG:16",
            "BIRCH_LOG:16",
            "SPRUCE_LOG:16",
            "JUNGLE_LOG:16",
            "ACACIA_LOG:16",
            "DARK_OAK_LOG:16",
            "DIRT:4"
        )));


        // Ceiling and breathing room
        layers.add(new CubedSurvivalLayer(201, 220, CubedSurvivalLayer.materialFromString(
            "AIR:100"
        )));

        layers.add(new CubedSurvivalLayer(221, 230, CubedSurvivalLayer.materialFromString(
            "BARRIER:100"
        )));
    }

    @Override
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        // We only want 32x32 chunks to be generated, so we'll just return if it's not in that range.
        if (chunkX < -MAX_CHUNKS || chunkX > MAX_CHUNKS || chunkZ < -MAX_CHUNKS || chunkZ > MAX_CHUNKS) {
            fillWithAir(chunkData);
            return;
        }
        // in max_chunk +1, we need to have a bedrock wall
        // TODO: lol

        // Now for the layers!
        for (CubedSurvivalLayer layer : layers) {
            layer.generateLayer(chunkData);
        }

        // From minHeight to 0, and from 231 to max height, do air
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 231; y++) {
                    if (y < worldInfo.getMinHeight() || y > worldInfo.getMaxHeight()) {
                        chunkData.setBlock(x, y, z, Material.AIR);
                    }
                }
            }
        }
    }

    public void fillWithAir(ChunkData chunkData) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = chunkData.getMinHeight(); y < chunkData.getMaxHeight(); y++) {
                    chunkData.setBlock(x, y, z, Material.AIR);
                }
            }
        }
    }
}
