package astrotweaks.world.biome;

import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.biome.Biome;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;



public class BiomeCavern {
	public BiomeCavern() {}

	public static final Biome CAVERN = new Cavern();

	static class Cavern extends Biome {
	    public Cavern() {
	        super(new Biome.BiomeProperties("Cavern")
	            .setRainfall(0F)
	            .setBaseHeight(0.1F)
	            .setHeightVariation(0.2F)
	            .setTemperature(1.1F) // 0.0 - 2.0
	            .setWaterColor(0x305080));
	        setRegistryName("cavern");

	        // Remove all plants
	        decorator.treesPerChunk = 0;
	        decorator.flowersPerChunk = 0;
	        decorator.grassPerChunk = 0;
	        decorator.mushroomsPerChunk = 0;
	        decorator.bigMushroomsPerChunk = 0;
	        decorator.reedsPerChunk = 0;
	        decorator.cactiPerChunk = 0;
	        decorator.sandPatchesPerChunk = 0;
	        decorator.gravelPatchesPerChunk = 0;

	        // Clear mob spawn
	        //this.spawnableMonsterList.clear();
	        this.spawnableCreatureList.clear();
	        this.spawnableWaterCreatureList.clear();
	        //this.spawnableCaveCreatureList.clear();

	        this.getSpawnableList(EnumCreatureType.MONSTER).removeIf(entry -> entry.entityClass == EntitySpider.class); // Удалить пауков т.к. они будут просто тупить под потолком
	    }
	    @Override public WorldGenAbstractTree getRandomTreeFeature(Random rand) {return null; /*No trees*/}
	    @Override public int getSkyColorByTemp(float currentTemperature) {return -1;}
	    @Override public void decorate(World worldIn, Random rand, BlockPos pos) {/* Redefining decorate to avoid NPE */}
	}
}
