/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.ui.client.clickgui.elements;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ModuleElement extends ButtonElement {

    private final Module module;

    private boolean showSettings;
    private float settingsWidth = 0F;
    private boolean wasPressed;

    public int settingHeight;
    public int slowlyFade;

    public int settingScrollIndex;

    public ModuleElement(final Module module) {
        super(null);

        this.displayName = module.getName();
        this.module = module;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float button) {
        LiquidBounce.clickGui.style.drawModuleElement(mouseX, mouseY, this);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0 && isHovering(mouseX, mouseY) && isVisible()) {
            module.toggle();
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            return true;
        }

        if(mouseButton == 1 && isHovering(mouseX, mouseY) && isVisible()) {
            showSettings = !showSettings;
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            return true;
        }
        return false;
    }

    @Override
    public boolean handleScroll(int mouseX, int mouseY, int wheel) {
        final List<Value<?>> moduleValues = getModule().getValues();

        if(isHoveringSettings(mouseX, mouseY) && isVisible() && showSettings && !moduleValues.isEmpty()) {
            if(wheel < 0 && settingScrollIndex < moduleValues.size() - 15) {
                ++settingScrollIndex;
                if(settingScrollIndex < 0)
                    settingScrollIndex = 0;
            }else if(wheel > 0) {
                --settingScrollIndex;
                if(settingScrollIndex < 0)
                    settingScrollIndex = 0;
            }

            return true;
        }

        return super.handleScroll(mouseX, mouseY, wheel);
    }

    public boolean isHoveringSettings(int mouseX, int mouseY) {
        final int x = getX() + getWidth() + 4;
        final int y = getY() + 6;
        final int xEnd = getX() + getWidth() + (int) getSettingsWidth();
        final int yEnd = settingHeight + 2;

        return mouseX >= x && mouseX <= xEnd && mouseY >= y && mouseY <= yEnd;
    }

    public Module getModule() {
        return module;
    }

    public boolean isShowSettings() {
        return showSettings;
    }

    public void setShowSettings(boolean showSettings) {
        this.showSettings = showSettings;
    }

    public boolean isntPressed() {
        return !wasPressed;
    }

    public void updatePressed() {
        wasPressed = Mouse.isButtonDown(0);
    }

    public float getSettingsWidth() {
        return settingsWidth;
    }

    public void setSettingsWidth(float settingsWidth) {
        this.settingsWidth = settingsWidth;
    }
}