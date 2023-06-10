package com.mineblock11.togglemytooltips;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public enum ScaleModifier {
    SMALL((args, matrices) -> {
        Text text = args.get(1);
        int x = args.get(2);
        int y = args.get(3);

        float scaleFactor = 0.52f;

        float scaleTextWidth = (MinecraftClient.getInstance().textRenderer.getWidth(text));
        float scaleTextHeight = (MinecraftClient.getInstance().textRenderer.fontHeight);

        float scaleOffsetX = ((scaleTextWidth / 2) + x) / scaleFactor;
        float scaleOffsetY = (y) / scaleFactor;

        matrices.scale(scaleFactor, scaleFactor, scaleFactor);

//        args.set(2, 1f);
//        args.set(3, 1f);

        args.set(2, (int) (scaleOffsetX - (scaleTextWidth * 1.52f)));
        args.set(3, (int) (scaleOffsetY + (scaleTextHeight * 1.52f)));
    }),
    NORMAL((args, matrices) -> {
        int x = args.get(2);
        Text text = args.get(1);
        float scaleTextWidth = (MinecraftClient.getInstance().textRenderer.getWidth(text));
        x *= 2f;
        x -= scaleTextWidth;
        x /= 2f;
        args.set(2, x);
    }),
    LARGE((args, matrices) -> {
        Text text = args.get(1);
        int x = args.get(2);
        int y = args.get(3);

        float scaleFactor = 2f;

        float scaleTextWidth = (MinecraftClient.getInstance().textRenderer.getWidth(text));

        float scaleOffsetX = (x) / scaleFactor;
        float scaleOffsetY = (y) / scaleFactor;

        matrices.scale(scaleFactor, scaleFactor, scaleFactor);

        args.set(2, (int) (scaleOffsetX - (scaleTextWidth / 2f)));
        args.set(3, (int) scaleOffsetY);
    });

    public BiConsumer<Args, MatrixStack> getMixinEditFunction() {
        return mixinEditFunction;
    }

    private final BiConsumer<Args, MatrixStack> mixinEditFunction;

    ScaleModifier(BiConsumer<Args, MatrixStack> mixinEditFunction) {
        this.mixinEditFunction = mixinEditFunction;
    }
}
