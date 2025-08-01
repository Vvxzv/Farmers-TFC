package net.vvxzv.farmerstfc.world.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.vvxzv.farmerstfc.farmersTFC.MOD_ID;

public class creativeTAB {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public  static final RegistryObject<CreativeModeTab> farmerstfc = CREATIVE_MODE_TAB.register("farmerstfc", () -> CreativeModeTab.builder()
            .title(Component.translatable("farmerstfc.tab.name"))
            .icon(() -> new ItemStack(block.BROWN_MUSHROOM_BUNCH.get()))
            .displayItems((parm, output) -> {
                output.accept(block.BROWN_MUSHROOM_BUNCH.get());
                output.accept(block.RED_MUSHROOM_BUNCH.get());
                output.accept(block.PAN.get());
            })
            .build());
}
