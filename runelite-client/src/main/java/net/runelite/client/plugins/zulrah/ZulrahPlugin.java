package net.runelite.client.plugins.zulrah;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.*;

@PluginDescriptor(
        name = "Zulrah helper",
        description = "helps",
        tags = {"zulrah"}
)
public class ZulrahPlugin extends Plugin {

    @Getter(AccessLevel.PACKAGE)
    private NPC zulrah;

    @Inject
    private ZulrahOverlay overlay;

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Subscribe
    public void onNpcSpawned(NpcSpawned npcSpawned)
    {

        NPC npc = npcSpawned.getNpc();
        switch (npc.getId())
        {
            case NpcID.ZULRAH:
                zulrah = npc;
                overlay.setCurrentPhase(0);
                overlay.setCurrentRotation(0);
                overlay.one();
                System.out.println("raaang");
                break;
            }

    }

    @Subscribe
    public void onNpcDespawned(NpcDespawned npcDespawned) {
        NPC npc = npcDespawned.getNpc();
        if (npc == zulrah) {
            System.out.println("deaaad");
            overlay.setCurrentPhase(0);
            overlay.setCurrentRotation(0);
            zulrah = null;
            overlay.getList().clear();
        }
    }

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);

    }
}
