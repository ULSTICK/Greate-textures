package electrolyte.greate.compat.jei.category;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import electrolyte.greate.compat.jei.category.animations.TieredAnimatedMillstone;
import electrolyte.greate.content.kinetics.crusher.TieredAbstractCrushingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingOutput;
import electrolyte.greate.registry.Millstones;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class TieredMillingCategory extends GreateRecipeCategory<TieredAbstractCrushingRecipe> {

    public TieredMillingCategory(Info<TieredAbstractCrushingRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TieredAbstractCrushingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 9)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.getIngredients().get(0));

        List<ProcessingOutput> results = recipe.getRollableResults();
        boolean single = results.size() == 1;
        int i = 0;
        for(ProcessingOutput output : results) {
            int xOffset = i % 2 == 0 ? 0 : 19;
            int yOffset = (i / 2)  * -19;
            IRecipeSlotBuilder baseBuilder = builder.addSlot(RecipeIngredientRole.OUTPUT, single ? 139 : 133 + xOffset, 27 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack());
            if(output instanceof TieredProcessingOutput tieredProcessingOutput) {
                baseBuilder.addTooltipCallback(addStochasticTooltipWithExtraPercent(tieredProcessingOutput, tieredProcessingOutput.getExtraTierChance()));
            } else {
                baseBuilder.addTooltipCallback(addStochasticTooltip(output));
            }
            i++;
        }
    }

    @Override
    public void draw(TieredAbstractCrushingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double x, double y) {
        super.draw(recipe, recipeSlotsView, graphics, 1, 57);
        AllGuiTextures.JEI_ARROW.render(graphics, 85, 32);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 43, 4);
        new TieredAnimatedMillstone(Millstones.MILLSTONES[recipe.getRecipeTier()].get()).draw(graphics, 48, 27);
    }
}
