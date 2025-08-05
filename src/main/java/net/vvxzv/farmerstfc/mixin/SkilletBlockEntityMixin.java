package net.vvxzv.farmerstfc.mixin;

import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.dries007.tfc.common.recipes.TFCRecipeTypes;
import net.dries007.tfc.common.recipes.inventory.ItemStackInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import net.minecraft.world.item.Item;

import java.util.Optional;

@Mixin(SkilletBlockEntity.class)
public abstract class SkilletBlockEntityMixin extends SyncedBlockEntity {

    private ResourceLocation lastHeatingRecipeID;

    public SkilletBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    private static final TagKey<Item> CANT_COOK_TAG = TagKey.create(
            net.minecraft.core.registries.Registries.ITEM,
            new ResourceLocation("farmerstfc", "cant_cook")
    );

    @Inject(
            method = "getMatchingRecipe",
            at = @At("RETURN"),
            remap = false,
            cancellable = true
    )
    private void injectHeatingRecipe(Container recipeWrapper, CallbackInfoReturnable<Optional<CampfireCookingRecipe>> cir) {
        if (cir.getReturnValue().isPresent()) {
            return;
        }

        ItemStack inputStack = recipeWrapper.getItem(0);
        if (inputStack.is(CANT_COOK_TAG)) {
            return;
        }
        Optional<HeatingRecipe> heatingRecipe = this.level.getRecipeManager()
                .getRecipeFor(TFCRecipeTypes.HEATING.get(), new ItemStackInventory(inputStack), this.level);

        if (heatingRecipe.isPresent()) {
            HeatingRecipe recipe = heatingRecipe.get();
            if (recipe.getTemperature() < 201) {
                this.lastHeatingRecipeID = recipe.getId();
                CampfireCookingRecipe fakeCampfireRecipe = new CampfireCookingRecipe(
                        recipe.getId(),
                        "",
                        CookingBookCategory.FOOD,
                        recipe.getIngredient(),
                        recipe.assemble(new ItemStackInventory(inputStack), this.level.registryAccess()),
                        0,
                        600
                );
                cir.setReturnValue(Optional.of(fakeCampfireRecipe));
            }
        }
    }

    public ResourceLocation getLastHeatingRecipeID() {
        return lastHeatingRecipeID;
    }
}