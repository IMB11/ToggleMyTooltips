package com.mineblock11.togglemytooltips;

import io.wispforest.owo.config.annotation.*;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

@Modmenu(modId = "togglemytooltips")
@Config(name = "toggle-my-tooltips-config", wrapperName = "TMTConfig")
public class ConfigModal {
    @SectionHeader("heldItemSection")
    public boolean showHeldItemTooltip = true;
    public ScaleModifier scaleModifier = ScaleModifier.NORMAL;
    public boolean shouldApplyFormattingExtras = false;

    @Expanded
    public List<String> formattingExtras = List.of("BOLD", "DARK_AQUA");
}
