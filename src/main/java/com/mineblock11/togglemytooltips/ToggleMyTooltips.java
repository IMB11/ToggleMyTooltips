package com.mineblock11.togglemytooltips;

import com.mineblock11.togglemytooltips.TMTConfig;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

public class ToggleMyTooltips implements ClientModInitializer {
    public static final TMTConfig CONFIG = TMTConfig.createAndLoad();
    @Override
    public void onInitializeClient() {

    }
}
