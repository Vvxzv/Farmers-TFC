package net.vvxzv.farmerstfc.world.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.vvxzv.farmerstfc.farmersTFC.MOD_ID;

public class item {
    public static final DeferredRegister<Item> ITEMS;
    public static final RegistryObject<Item> BROWN_MUSHROOM_BUNCH;
    public static final RegistryObject<Item> RED_MUSHROOM_BUNCH;
    public static final RegistryObject<Item> PAN;

    public static Item.Properties basicItem() {
        return new Item.Properties();
    }

    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
        BROWN_MUSHROOM_BUNCH = ITEMS.register("brown_mushroom_bunch", () -> new BlockItem(block.BROWN_MUSHROOM_BUNCH.get(), basicItem()));
        RED_MUSHROOM_BUNCH = ITEMS.register("red_mushroom_bunch", () -> new BlockItem(block.RED_MUSHROOM_BUNCH.get(), basicItem()));
        PAN = ITEMS.register("pan", () -> new BlockItem(block.PAN.get(), basicItem()));
    }
}
