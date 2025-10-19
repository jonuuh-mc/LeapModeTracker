package io.jonuuh.leapmodetracker.event.render;

import io.jonuuh.leapmodetracker.LeapMode;
import io.jonuuh.leapmodetracker.LeapModeTracker;
import io.jonuuh.leapmodetracker.config.Settings;
import io.jonuuh.leapmodetracker.util.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class ModeHUDRenderer
{
    private static final Minecraft mc = LeapModeTracker.mc;
    private final Map<LeapMode, ResourceLocation> modeTextures;
    private final Settings settingsInstance;

    public ModeHUDRenderer()
    {
        this.settingsInstance = Settings.getInstance();

        this.modeTextures = new HashMap<>();
        modeTextures.put(LeapMode.ARROW, new ResourceLocation("leapmodetracker:textures/arrow.png"));
        modeTextures.put(LeapMode.ARCED, new ResourceLocation("leapmodetracker:textures/arced.png"));
    }

    @SubscribeEvent
    public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
    {
        if (mc.thePlayer != null && !isSpider(mc.thePlayer))
        {
            return;
        }

        ScaledResolution sr = new ScaledResolution(mc);
        Color color = settingsInstance.getHudColor();
        float texSize = 20;
//        float scale = 1F;

        GL11.glPushMatrix();
        GL11.glTranslatef(sr.getScaledWidth() / 2F, sr.getScaledHeight() - (22 * 4), 0F);
        GL11.glTranslatef(-texSize / 2F, -texSize / 2F, 0F);

//        // Scale in place
//        GL11.glTranslatef(texSize * scale, texSize * scale, 0);
//        GL11.glScalef(scale, scale, 0);
//        GL11.glTranslatef(-texSize * scale, -texSize * scale, 0);

        if (LeapModeTracker.getCurrentMode().equals(LeapMode.ARROW))
        {
            GL11.glTranslatef(0, texSize / 4F, 0F);

            // Rotate in place
            GL11.glTranslatef(texSize / 2F, texSize / 2F, 0);
            GL11.glRotatef(-45, 0, 0, 1);
            GL11.glTranslatef(-texSize / 2F, -texSize / 2F, 0);
        }

        GL11.glColor4ub(color.r, color.g, color.b, color.a);

        mc.getTextureManager().bindTexture(modeTextures.get(LeapModeTracker.getCurrentMode()));

        Gui.drawModalRectWithCustomSizedTexture(settingsInstance.getHudXPosOffset(), settingsInstance.getHudYPosOffset(),
                0,0, 20, 20, 20, 20);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private boolean isSpider(EntityPlayerSP player)
    {
        // TODO: doesn't work in mw duels?
        String cleanName = EnumChatFormatting.getTextWithoutFormattingCodes(player.getDisplayName().getUnformattedText());
        return cleanName.contains("[SPI]");
//        return true;
    }
}
