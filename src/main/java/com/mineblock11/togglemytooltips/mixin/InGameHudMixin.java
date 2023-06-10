package com.mineblock11.togglemytooltips.mixin;

import com.mineblock11.togglemytooltips.ScaleModifier;
import com.mineblock11.togglemytooltips.ToggleMyTooltips;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.ArrayList;
import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow @Nullable private Text overlayMessage;

    @Shadow private int scaledWidth;
    @Unique private DrawContext tempContext;

    @Inject(method = "renderHeldItemTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I", shift = At.Shift.BEFORE))
    public void preScale(DrawContext context, CallbackInfo ci) {
        context.getMatrices().push();
        tempContext = context;
    }

    @Inject(method = "renderHeldItemTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I", shift = At.Shift.AFTER))
    public void postScale(DrawContext context, CallbackInfo ci) {
        context.getMatrices().pop();
    }

    @ModifyArgs(method = "renderHeldItemTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"))
    public void modifyArgsHudOverlayScale(Args args) {
        args.set(2, (int) (this.scaledWidth / 2f));

        ScaleModifier modifier = ToggleMyTooltips.CONFIG.scaleModifier();
        boolean enabled = ToggleMyTooltips.CONFIG.showHeldItemTooltip();
        boolean shouldApplyFormatting = ToggleMyTooltips.CONFIG.shouldApplyFormattingExtras();
        List<String> formattingsPre = ToggleMyTooltips.CONFIG.formattingExtras();

        modifier.getMixinEditFunction().accept(args, tempContext.getMatrices());

        if(!enabled) {
            args.set(1, Text.empty());
        }

        if(shouldApplyFormatting) {
            Text text = args.get(1);

            List<Formatting> formattings = new ArrayList<>();

            for (String format : formattingsPre) {
                try {
                    Formatting formatting = Formatting.byName(format);
                    assert formatting != null;
                    formattings.add(formatting);
                } catch (Exception ignored) {
                    LoggerFactory.getLogger(InGameHudMixin.class).info("Invalid format code: " + format);
                }
            }

            MutableText newText = text.copy().formatted(formattings.toArray(new Formatting[0]));
            args.set(1, newText);
        }
    }
}
