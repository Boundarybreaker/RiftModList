package io.github.gbui.riftmodlist.mixin;

import io.github.gbui.riftmodlist.gui.GuiButtonFunction;
import io.github.gbui.riftmodlist.gui.GuiModList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {
    @Shadow protected abstract void switchToRealms();

    @ModifyArg(
            method = "addSingleplayerMultiplayerButtons",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiMainMenu;addButton(Lnet/minecraft/client/gui/GuiButton;)Lnet/minecraft/client/gui/GuiButton;",
                    ordinal = 2
            )
    )
    private GuiButton getRealmsButton(GuiButton original) {
        GuiButtonFunction button = new GuiButtonFunction(original.id, width / 2 + 2, original.y, 98, 20, I18n.format("menu.riftmodlist.realms"));
        button.setMousePressed((mx, my) -> switchToRealms());
        return button;
    }

    @Inject(method = "addSingleplayerMultiplayerButtons", at = @At("RETURN"))
    private void onAddSingleplayerMultiplayerButtons(int y, int dy, CallbackInfo ci) {
        GuiButtonFunction button = new GuiButtonFunction(100, width / 2 - 100, y + dy * 2, 98, 20, I18n.format("menu.riftmodlist.mods"));
        button.setMousePressed((mx, my) -> mc.displayGuiScreen(new GuiModList(this)));
        addButton(button);
    }

    @Redirect(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiMainMenu;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V",
                    ordinal = 0
            )
    )
    private void onDrawMinecraftVersion(GuiMainMenu gui, FontRenderer fontRenderer, String s, int x, int y, int color) {
        drawString(fontRenderer, s, x, y - 10 * 2, color);
        drawString(fontRenderer, I18n.format("menu.riftmodlist.powered_by"), x, y - 10 * 1, color);
        drawString(fontRenderer, I18n.format("menu.riftmodlist.mods_loaded", RiftLoader.instance.getMods().size()), x, y - 10 * 0, color);
    }
}
