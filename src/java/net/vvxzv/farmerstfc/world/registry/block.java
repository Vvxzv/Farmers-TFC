package net.vvxzv.farmerstfc.world.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.SkilletBlock;


import static net.vvxzv.farmerstfc.farmersTFC.MOD_ID;

public class block {
    public static final DeferredRegister<Block> BLOCKS;
    public static final RegistryObject<Block> BROWN_MUSHROOM_BUNCH;
    public static final RegistryObject<Block> RED_MUSHROOM_BUNCH;
    public static final RegistryObject<Block> PAN;

    static {
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
        BROWN_MUSHROOM_BUNCH = BLOCKS.register("brown_mushroom_bunch", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));
        RED_MUSHROOM_BUNCH = BLOCKS.register("red_mushroom_bunch", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));
        PAN = BLOCKS.register("pan", () -> new SkilletBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(0.5F, 6.0F).sound(SoundType.LANTERN)));
    }
}
