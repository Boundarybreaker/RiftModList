package io.github.gbui.riftmodlist.gui;

import net.minecraft.client.gui.GuiButton;

import java.util.function.BiConsumer;

public class GuiButtonFunction extends GuiButton {
    private BiConsumer<Double, Double> mousePressed = null;
    private BiConsumer<Double, Double> mouseReleased = null;

    public GuiButtonFunction(int id, int x, int y, String displayString) {
        super(id, x, y, displayString);
    }

    public GuiButtonFunction(int id, int x, int y, int width, int height, String displayString) {
        super(id, x, y, width, height, displayString);
    }

    public void setMousePressed(BiConsumer<Double, Double> mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseReleased(BiConsumer<Double, Double> mouseReleased) {
        this.mouseReleased = mouseReleased;
    }

    @Override
    public void mousePressed(double p_mousePressed_1_, double p_mousePressed_3_) {
        if (mousePressed != null) {
            mousePressed.accept(p_mousePressed_1_, p_mousePressed_3_);
        }
    }

    @Override
    public void mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_) {
        if (mouseReleased != null) {
            mouseReleased.accept(p_mouseReleased_1_, p_mouseReleased_3_);
        }
    }
}
