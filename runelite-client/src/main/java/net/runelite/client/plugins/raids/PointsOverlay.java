package net.runelite.client.plugins.raids;

import com.sun.jna.platform.win32.OaIdl;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.awt.*;
import java.text.DecimalFormat;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

public class PointsOverlay extends Overlay {
    private static final int OLM_PLANE = 0;

    private Client client;
    private RaidsPlugin plugin;
    private RaidsConfig config;
    private final PanelComponent panelComponent = new PanelComponent();
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.##");

    @Setter
    private boolean raidOverlayShown = false;

    @Inject
    private PointsOverlay(Client client, RaidsPlugin plugin, RaidsConfig config)
    {
        super(plugin);
        setPosition(OverlayPosition.TOP_LEFT);
        setPriority(OverlayPriority.LOW);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Points overlay"));
    }


    @Override
    public Dimension render(Graphics2D graphics)
    {
        if(!config.pointCounter() || !raidOverlayShown || client.getVar(Varbits.RAID_PARTY_SIZE)==1){
            return null;
        }
        panelComponent.getChildren().clear();
        int totalPoints = client.getVar(Varbits.TOTAL_POINTS);
        int personalPoints = client.getVar(Varbits.PERSONAL_POINTS);
        int teamPoints = totalPoints-personalPoints;
        Color color = Color.WHITE;

        if( totalPoints == 0){
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Percentage")
                    .right("0")
                    .rightColor(color)
                    .build());
            return panelComponent.render(graphics);
        }
        int partySize = client.getVar(Varbits.RAID_PARTY_SIZE)-1;
        int avgpoints = totalPoints/partySize;
        double personalPercentage = personalPoints / (totalPoints / 100.0);
        double avgPercentage = ((teamPoints/(totalPoints/1.0))*100)/partySize; //totalPoints/1.0;
        /*avgPercentage = avgPercentage/partySize;
        avgPercentage = (avgPercentage/totalPoints)*100.0;*/

        panelComponent.getChildren().add(LineComponent.builder()
            .left("Average")
            .right(DECIMAL_FORMAT.format(avgPercentage)+"%")
            .rightColor(color)
            .build());

        if(personalPercentage>=avgPercentage){
            color = Color.GREEN;
        }else {
            color = Color.RED;
        }
        panelComponent.getChildren().add(LineComponent.builder()
            .left("Percentage")
            .right(DECIMAL_FORMAT.format(personalPercentage)+"%")
            .rightColor(color)
            .build());

        return panelComponent.render(graphics);
    }

}
