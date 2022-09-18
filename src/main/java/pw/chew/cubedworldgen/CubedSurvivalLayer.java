package pw.chew.cubedworldgen;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public record CubedSurvivalLayer(int minY, int maxY, List<LayerMaterial> data) {
    public void generateLayer(ChunkGenerator.ChunkData data) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = minY; y <= maxY; y++) {
                    // Get a random material from the

                    data.setBlock(x, y, z, randomMaterial());
                }
            }
        }
    }

    public Material randomMaterial() {
        // Total chance is always 0-100, so generate a number between 0 and 100, but allow decimals
        double chance = Math.random() * 100;

        // Now, loop through the materials and see if the chance is in the range of the material
        float currentChance = 0;
        for (LayerMaterial material : data) {
            currentChance += material.chance();

            if (chance <= currentChance) {
                return material.material();
            }
        }

        // If we get here, something went wrong. Return air.
        return Material.AIR;
    }

    public static List<LayerMaterial> massMaterial(List<Material> material, List<Float> chance) {
        List<LayerMaterial> materials = new ArrayList<>();
        for (int i = 0; i < material.size(); i++) {
            materials.add(new LayerMaterial(material.get(i), chance.get(i)));
        }
        return materials;
    }

    /**
     * Generates from a string of MATERIAL:CHANCE,MATERIAL:CHANCE, etc.
     * @param materials String of materials
     * @return
     */
    public static List<LayerMaterial> materialFromString(String... materials) {
        List<LayerMaterial> layerMaterials = new ArrayList<>();
        for (String material : materials) {
            String[] split = material.split(":");
            layerMaterials.add(new LayerMaterial(Material.getMaterial(split[0].toUpperCase(Locale.ROOT)), Float.parseFloat(split[1])));
        }
        return layerMaterials;
    }

    public record LayerMaterial(Material material, float chance) {
    }
}
