package net.vvxzv.farmerstfc.compact.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.dries007.tfc.common.recipes.inventory.ItemStackInventory;
import net.dries007.tfc.compat.jei.JEIIntegration;
import net.dries007.tfc.compat.jei.category.BaseRecipeCategory;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.Arrays;
import java.util.List;

public class SkilletCategory extends BaseRecipeCategory<HeatingRecipe> {
    public SkilletCategory(RecipeType<HeatingRecipe> type, IGuiHelper helper) {
        super(type, helper, helper.createBlankDrawable(120, 38), new ItemStack(ModBlocks.SKILLET.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, HeatingRecipe recipe, IFocusGroup focuses) {
        IRecipeSlotBuilder inputSlot = builder.addSlot(RecipeIngredientRole.INPUT, 21, 17);
        IRecipeSlotBuilder outputSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 17);

        inputSlot.addIngredients(recipe.getIngredient())
                .setBackground(this.slot, -1, -1);

        List<ItemStack> outputItems = Arrays.stream(recipe.getIngredient().getItems())
                .map(stack -> recipe.assemble(new ItemStackInventory(stack),
                        Minecraft.getInstance().level.registryAccess()))
                .toList();

        if (!outputItems.isEmpty() && !outputItems.stream().allMatch(ItemStack::isEmpty)) {
            outputSlot.addItemStacks(outputItems);

            // 添加概率提示（如果有）
            if (recipe.getChance() < 1.0F) {
                outputSlot.addTooltipCallback((slot, tooltip) ->
                        tooltip.add(1, Component.translatable("tfc.tooltip.chance",
                                        String.format("%.0f", recipe.getChance() * 100.0F))
                                .withStyle(ChatFormatting.ITALIC)));
            }
        }

        // 处理流体输出（如果有）
        FluidStack resultFluid = recipe.getDisplayOutputFluid();
        if (!resultFluid.isEmpty()) {
            outputSlot.addIngredient(JEIIntegration.FLUID_STACK, resultFluid)
                    .setFluidRenderer(1L, false, 16, 16);
        }

        outputSlot.setBackground(this.slot, -1, -1);
    }

    @Override
    public void draw(HeatingRecipe recipe, IRecipeSlotsView recipeSlots, GuiGraphics graphics, double mouseX, double mouseY) {
        this.fire.draw(graphics, 54, 16);

        MutableComponent temperatureText = TFCConfig.CLIENT.heatTooltipStyle.get()
                .formatColored(recipe.getTemperature());

        if (temperatureText != null) {
            Minecraft mc = Minecraft.getInstance();
            Font font = mc.font;
            int x = 60 - font.width(temperatureText) / 2;
            graphics.drawString(font, temperatureText, x, 4, 0xFFFFFF, true);
        }

        for (IRecipeSlotView view : recipeSlots.getSlotViews()) {
            view.getDisplayedItemStack().ifPresent(stack ->
                    HeatCapability.setTemperature(stack, recipe.getTemperature()));
        }
    }
}