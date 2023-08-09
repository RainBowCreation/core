package net.rainbowcreation.rainbowcreationx.gui;

import com.earth2me.essentials.User;
import net.essentialsx.api.v2.services.mail.MailMessage;
import net.rainbowcreation.rainbowcreationx.RainBowCreationX;
import net.rainbowcreation.rainbowcreationx.chat.Console;
import net.rainbowcreation.rainbowcreationx.eventmanager.GuiClick;
import net.rainbowcreation.rainbowcreationx.utils.item.Item;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MailGui {
    private final RainBowCreationX plugin = RainBowCreationX.getInstance();
    public Item runSelf(Gui gui, Item item) {
        User usr = plugin.ess.getUser(gui.getPlayer());
        int i = usr.getUnreadMailAmount();
        String tmp = "";
        if (i > 0) {
            if (i > 1) {
                item.amount(i);
                tmp = "s";
            }
            item.lore("<gray> you have <white>" + i + " <gray>unread email"+tmp+"!");
            List<MailMessage> mailLst = usr.getMailMessages();
            int j = 0;
            for (MailMessage mail:mailLst) {
                if (mail.isRead())
                    continue;
                if (j > 2)
                    break;
                item.lore("<gray>From <yellow>"+mail.getSenderUsername());
                String message = mail.getMessage();
                if (message.length() > 25)
                    message = message.substring(0, 23) + "...";
                item.lore("<white>  -"+message);
                j++;
            }
            if (i > 3)
                item.lore("<gray>...");
        }
        return item;
    }
    public Inventory run(Gui gui) {
        Inventory inv = gui.getInv();
        return inv;
    }

    /**
     * Handel Click event of it own page.
     * redirect event to other page if it not.
     *
     * @param event {@link InventoryClickEvent}
     *
     */
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        ClickType clickType = event.getClick();
        ItemStack check = Gui.guiPart(4);

        if (item.equals(check)) { //click self icon
            if (clickType == ClickType.LEFT) //leftclick open that gui
                return;
            else {
                //if right-click or some else
                //todo
                Console.info("not left at self icon");
            }
            return;
        }
        GuiClick.openGui(event); //redirect to other class if not in this handler
    }
}
