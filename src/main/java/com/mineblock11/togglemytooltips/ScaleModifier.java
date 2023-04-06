package com.mineblock11.togglemytooltips;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Consumer;

public enum ScaleModifier {
    SMALL((args) -> {
        MatrixStack matrices = args.get(0);
        Text text = args.get(1);
        float x = args.get(2);
        float y = args.get(3);

        float scaleFactor = 0.52f;

        float scaleTextWidth = (MinecraftClient.getInstance().textRenderer.getWidth(text));
        float scaleTextHeight = (MinecraftClient.getInstance().textRenderer.fontHeight);

        float scaleOffsetX = ((scaleTextWidth / 2) + x) / scaleFactor;
        float scaleOffsetY = (y) / scaleFactor;

        matrices.scale(scaleFactor, scaleFactor, scaleFactor);

//        args.set(2, 1f);
//        args.set(3, 1f);

        args.set(2, scaleOffsetX - (scaleTextWidth * 1.52f));
        args.set(3, scaleOffsetY + (scaleTextHeight * 1.52f));
    }),
    NORMAL((args) -> {
        float x = args.get(2);
        Text text = args.get(1);
        float scaleTextWidth = (MinecraftClient.getInstance().textRenderer.getWidth(text));
        x *= 2f;
        x -= scaleTextWidth;
        x /= 2f;
        args.set(2, x);
    }),
    LARGE((args) -> {
        MatrixStack matrices = args.get(0);
        Text text = args.get(1);
        float x = args.get(2);
        float y = args.get(3);

        float scaleFactor = 2f;

        float scaleTextWidth = (MinecraftClient.getInstance().textRenderer.getWidth(text));
        float scaleTextHeight = (MinecraftClient.getInstance().textRenderer.fontHeight);

        float scaleOffsetX = (x) / scaleFactor;
        float scaleOffsetY = (y) / scaleFactor;

        matrices.scale(scaleFactor, scaleFactor, scaleFactor);

//        args.set(2, 1f);
//        args.set(3, 1f);

        args.set(2, scaleOffsetX - (scaleTextWidth / 2f));
        args.set(3, scaleOffsetY);
    });

    public Consumer<Args> getMixinEditFunction() {
        return mixinEditFunction;
    }

    private final Consumer<Args> mixinEditFunction;

    ScaleModifier(Consumer<Args> mixinEditFunction) {
        this.mixinEditFunction = mixinEditFunction;
    }
}
