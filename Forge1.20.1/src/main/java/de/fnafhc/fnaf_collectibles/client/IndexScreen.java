package de.fnafhc.fnaf_collectibles.client;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import de.fnafhc.fnaf_collectibles.items.CardItem;
import de.fnafhc.fnaf_collectibles.utils.PacketManager;
import de.fnafhc.fnaf_collectibles.utils.packets.DepositCardPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class IndexScreen extends Screen {

    private static final ResourceLocation bg =
            new ResourceLocation(Fnaf_collectibles.MODID, "textures/gui/indexbg2.png");

    private final int imageWidth = 128;
    private final int imageHeight = 128;

    private int leftPos;
    private int topPos;
    private int guiScale;

    private final int containerX = 70;
    private final int containerY = 35;
    private final int containerWidth = 48;
    private final int containerHeight = 58;

    private float scrollAmount = 0f;
    private final int entrySize = 15;
    private final int entriesPerRow = 3;
    private int contentHeight = 0;

    private ItemStack hoveredItem = null;

    private final int scrollbarWidth = 3;
    private final int scrollbarGap = 1;
    private boolean draggingScrollbar = false;

    private final List<Item> items = new ArrayList<>();
    private List<Item> filteredItems = new ArrayList<>();

    private String oldStr = "Opened";
    public static boolean oldSelected = false;
    public static boolean changed = false;

    private long openTimeMs;
    private static final int ANIM_DURATION_MS = 300;

    private ItemStack stack;
    private Map<String, Integer> collectedCards = new HashMap<>();

    private EditBox searchField;
    private Checkbox collectedOnlybox;

    private static final float WIDGET_SCALE = 0.4F;
    private static final float WIDGET_SCALE2 = 0.3F;

    private static int totalWithDuplicates = 0;

    protected IndexScreen(Component title, ItemStack stack) {
        super(title);

        IndexScreen.oldSelected = false;
        this.stack = stack;

        items.clear();

        items.addAll(Arrays.asList(Fnaf_collectibles.common));
        items.addAll(Arrays.asList(Fnaf_collectibles.uncommon));
        items.addAll(Arrays.asList(Fnaf_collectibles.rare));
        items.addAll(Arrays.asList(Fnaf_collectibles.epic));
        items.addAll(Arrays.asList(Fnaf_collectibles.legendary));

        CompoundTag tag = this.stack.getTag().getCompound("cards");

        int total = 0;
        if (!tag.isEmpty()) {
            for (String key : tag.getAllKeys()) {
                this.collectedCards.put(key, tag.getInt(key));
                int amount = tag.getInt(key);
                total = total + amount;
            }
        }
        totalWithDuplicates = total;
    }

    @Override
    protected void init() {
        super.init();

        int scaleByWidth = (this.width * 8 / 10) / imageWidth;
        int scaleByHeight = (this.height * 8 / 10) / imageHeight;

        this.guiScale = Math.max(1, Math.min(scaleByWidth, scaleByHeight));
        this.guiScale = Math.min(this.guiScale, 3);

        int virtualWidth = this.width / guiScale;
        int virtualHeight = this.height / guiScale;

        this.leftPos = (virtualWidth - imageWidth) / 2;
        this.topPos = (virtualHeight - imageHeight) / 2;

        this.openTimeMs = System.currentTimeMillis();

        this.searchField = new EditBox(
                this.font,
                leftPos + 8,
                topPos + 38,
                110,
                10,
                Component.literal("Search")
        );

        this.searchField.setMaxLength(20);
        this.searchField.setHint(Component.literal("Search..."));
        this.addRenderableWidget(this.searchField);

        this.collectedOnlybox = new Checkbox(
                leftPos + 8,
                topPos + 55,
                90,
                20,
                Component.literal("§fCollected Only"),
                false
        ) {
            @Override
            public void onPress() {
                super.onPress();
                IndexScreen.oldSelected = this.selected();
                IndexScreen.changed = true;
            }
        };

        this.addRenderableWidget(this.collectedOnlybox);
    }

    @Override
    protected void setInitialFocus(GuiEventListener p_265756_) {
        this.setFocused(this.searchField);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int virtualMouseX = (int) (mouseX / (float) guiScale);
        int virtualMouseY = (int) (mouseY / (float) guiScale);

        String str2 = this.searchField.getValue().toLowerCase();
        if(!oldStr.equalsIgnoreCase(str2) || IndexScreen.oldSelected != this.collectedOnlybox.selected() || IndexScreen.changed) {
            oldStr = str2;
            scrollAmount = 0F;
            IndexScreen.changed = false;
            filteredItems.clear();
            for(Item item : items) {
                String str1 = Fnaf_collectibles.convertToTranslate(item).getString().toLowerCase();
                if(str1.contains(str2)) {
                    if(IndexScreen.oldSelected) {
                        if(collectedCards.containsKey(Fnaf_collectibles.getItemRegistryName(item))) {
                            filteredItems.add(item);
                        }
                    }else {
                        filteredItems.add(item);
                    }
                }
            }
        }

        this.contentHeight = ((this.filteredItems.size() + entriesPerRow - 1) / entriesPerRow) * this.entrySize;
        this.renderBackground(graphics);

        long elapsed = System.currentTimeMillis() - openTimeMs;
        float progress = Mth.clamp(elapsed / (float) ANIM_DURATION_MS, 0f, 1f);
        float eased = 1f - (1f - progress) * (1f - progress);
        int slideOffset = (int) ((1f - eased) * 60);
        int animatedTopPos = topPos + slideOffset;

        this.hoveredItem = null;

        graphics.pose().pushPose();
        graphics.pose().scale(guiScale, guiScale, 1f);

        graphics.blit(bg, leftPos, animatedTopPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);

        double prozent = (double) collectedCards.size() / items.size() * 100;
        if (Double.isNaN(prozent)) prozent = 0.0;

        renderStringWithCustomSize(graphics, getMinecraft().font,
                "§lFNaF Collectibles Index", leftPos + 27, animatedTopPos + 25, 0xFFFFFF, 0.5F);

        renderStringWithCustomSize(graphics, font,
                "Not finished system!", leftPos + 10, animatedTopPos + 56, 0xFFFFFF, 0.4F);

        renderStringWithCustomSize(graphics, getMinecraft().font,
                "Overall: " + String.format("%.1f%%", prozent) +
                        " (" + collectedCards.size() + "/" + items.size() + ")",
                leftPos + 7, animatedTopPos + 88, 0xFFFFFF, 0.4F);

        renderStringWithCustomSize(graphics, getMinecraft().font,
                "Collected Cards with duplicates: " + totalWithDuplicates,
                leftPos+7, animatedTopPos+84, 0xFFFFFF, 0.27F);

        renderScrollContainer(graphics, leftPos, animatedTopPos, virtualMouseX, virtualMouseY);

        graphics.pose().pushPose();

        float widgetX = leftPos + 8;
        float widgetY = animatedTopPos + 38;

        graphics.pose().translate(widgetX, widgetY, 0);
        graphics.pose().scale(WIDGET_SCALE, WIDGET_SCALE, 1.0F);
        graphics.pose().translate(-widgetX, -widgetY, 0);

        double transformedRenderX = transformMouseX(mouseX, WIDGET_SCALE);
        double transformedRenderY = transformMouseY(mouseY, WIDGET_SCALE);

        this.searchField.render(graphics, (int) transformedRenderX, (int) transformedRenderY, partialTicks);
        //this.collectedOnlybox.render(graphics, (int) transformedRenderX, (int) transformedRenderY, partialTicks);

        graphics.pose().popPose();

        graphics.pose().pushPose();

        float widgetX2 = leftPos + 8;
        float widgetY2 = animatedTopPos + 38;

        graphics.pose().translate(widgetX2, widgetY2, 0);
        graphics.pose().scale(WIDGET_SCALE2, WIDGET_SCALE2, 1.0F);
        graphics.pose().translate(-widgetX2, -widgetY2, 0);

        double transformedRenderX2 = transformMouseX(mouseX, WIDGET_SCALE2);
        double transformedRenderY2 = transformMouseY(mouseY, WIDGET_SCALE2);

        this.collectedOnlybox.render(graphics, (int) transformedRenderX2, (int) transformedRenderY2, partialTicks);

        graphics.pose().popPose();

        graphics.pose().popPose();

        if (this.hoveredItem != null) {
            boolean collected = collectedCards.containsKey(Fnaf_collectibles.getItemRegistryName(hoveredItem.getItem()));
            String str = collected ? collectedCards.get(Fnaf_collectibles.getItemRegistryName(hoveredItem.getItem())) + "" : "§cNone";

            List<Component> list = Screen.getTooltipFromItem(Minecraft.getInstance(), this.hoveredItem);
            list.add(Component.literal(""));
            list.add(Component.literal("Collected§7: §a" + str));
            list.add(Component.literal("Right click to put in inventory"));

            graphics.renderComponentTooltip(font, list, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double transformedMouseX = transformMouseX(mouseX, WIDGET_SCALE);
        double transformedMouseY = transformMouseY(mouseY, WIDGET_SCALE);

        double transformedMouseX2 = transformMouseX(mouseX, WIDGET_SCALE2);
        double transformedMouseY2 = transformMouseY(mouseY, WIDGET_SCALE2);

        if (this.searchField.mouseClicked(transformedMouseX, transformedMouseY, button)) {
            this.searchField.setFocused(true);
            return true;
        }else {
            if(this.searchField.isFocused()) {
                this.searchField.setFocused(false);
            }
        }
        if (this.collectedOnlybox.mouseClicked(transformedMouseX2, transformedMouseY2, button)) {
            return true;
        }

        double virtualX = mouseX / guiScale;
        double virtualY = mouseY / guiScale;

        if (button == 0 && isMouseOverScrollbar(virtualX, virtualY)) {
            draggingScrollbar = true;
            setScrollFromThumbDrag(virtualY);
            return true;
        }else {
            draggingScrollbar = false;
        }
        if (button == 1 && this.hoveredItem != null) {
            if (collectedCards.containsKey(Fnaf_collectibles.getItemRegistryName(hoveredItem.getItem()))) {
                int count = collectedCards.get(Fnaf_collectibles.getItemRegistryName(hoveredItem.getItem()));
                if (count > 1) {
                    collectedCards.put(Fnaf_collectibles.getItemRegistryName(hoveredItem.getItem()), count - 1);
                } else {
                    collectedCards.remove(Fnaf_collectibles.getItemRegistryName(hoveredItem.getItem()));
                }
                int total = 0;
                for (String key : this.collectedCards.keySet()) {
                    int amount = this.collectedCards.get(key);
                    total = total + amount;
                }
                totalWithDuplicates = total;
                PacketManager.sendToServer(new DepositCardPacket(Fnaf_collectibles.getItemRegistryName(hoveredItem.getItem())));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        double transformedMouseX = transformMouseX(mouseX, WIDGET_SCALE);
        double transformedMouseY = transformMouseY(mouseY, WIDGET_SCALE);

        double transformedMouseX2 = transformMouseX(mouseX, WIDGET_SCALE2);
        double transformedMouseY2 = transformMouseY(mouseY, WIDGET_SCALE2);

        if (this.searchField.mouseReleased(transformedMouseX, transformedMouseY, button)) {
            return true;
        }
        if (this.collectedOnlybox.mouseReleased(transformedMouseX2, transformedMouseY2, button)) {
            return true;
        }

        if (button == 0 && draggingScrollbar) {
            draggingScrollbar = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (this.searchField.charTyped(codePoint, modifiers)) {
            return true;
        }
        return super.charTyped(codePoint, modifiers);
    }

    private double transformMouseX(double rawMouseX, float WIDGET_SCALE) {
        double virtualX = rawMouseX / guiScale;
        float widgetX = leftPos + 8;
        return widgetX + (virtualX - widgetX) / WIDGET_SCALE;
    }

    private double transformMouseY(double rawMouseY, float WIDGET_SCALE) {
        double virtualY = rawMouseY / guiScale;
        float widgetY = topPos + 38;
        return widgetY + (virtualY - widgetY) / WIDGET_SCALE;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void renderStringWithCustomSize(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color, float scale) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x, y, 0);
        guiGraphics.pose().scale(scale, scale, 1.0F);
        guiGraphics.drawString(font, text, 0, 0, color, false);
        guiGraphics.pose().popPose();
    }

    private void renderScrollContainer(GuiGraphics graphics, int virtLeftPos, int virtTopPos, int virtualMouseX, int virtualMouseY) {
        int scissorX1 = (virtLeftPos + containerX) * guiScale;
        int scissorY1 = (virtTopPos + containerY) * guiScale;
        int scissorX2 = scissorX1 + containerWidth * guiScale;
        int scissorY2 = scissorY1 + containerHeight * guiScale;

        graphics.enableScissor(scissorX1, scissorY1, scissorX2, scissorY2);

        graphics.pose().pushPose();
        graphics.pose().translate(0, -scrollAmount, 0);

        for (int i = 0; i < filteredItems.size(); i++) {
            ItemStack item = new ItemStack(filteredItems.get(i));
            boolean collected = collectedCards.containsKey(Fnaf_collectibles.getItemRegistryName(item.getItem()));
            int col = i % entriesPerRow;
            int row = i / entriesPerRow;

            int entryX = virtLeftPos + containerX + col * entrySize;
            int entryY = virtTopPos + containerY + row * entrySize;

            int boxX = entryX + 2;
            int boxY = entryY + 2;
            int boxSize = entrySize - 2;

            boolean insideContainer =
                    virtualMouseX >= (virtLeftPos + containerX) && virtualMouseX <= (virtLeftPos + containerX + containerWidth) &&
                            virtualMouseY >= (virtTopPos + containerY) && virtualMouseY <= (virtTopPos + containerY + containerHeight);

            boolean hovered = insideContainer &&
                    virtualMouseX >= boxX && virtualMouseX <= boxX + boxSize &&
                    virtualMouseY >= (boxY - scrollAmount) && virtualMouseY <= (boxY - scrollAmount) + boxSize;

            if (hovered) {
                this.hoveredItem = item;
            }

            graphics.pose().pushPose();
            graphics.pose().translate(entryX + 2.8, entryY + 2.8, 0);
            graphics.pose().scale(0.72F, 0.72F, 0.72F);
            graphics.pose().translate(-entryX, -entryY, 0);

            if(!collected) {
                graphics.setColor(0.5F, 0.5F, 0.5F, 1.0F);
            }
            graphics.renderItem(item, entryX, entryY, 5);
            graphics.setColor(1F, 1F, 1F, 1.0F);
            graphics.pose().popPose();
        }

        graphics.pose().popPose();
        graphics.disableScissor();

        renderScrollbar(graphics, virtLeftPos, virtTopPos);
    }

    private void renderScrollbar(GuiGraphics graphics, int virtLeftPos, int virtTopPos) {
        if (contentHeight <= containerHeight) return;

        int barX = virtLeftPos + containerX + containerWidth + scrollbarGap;
        int barY = virtTopPos + containerY;

        graphics.fill(barX, barY, barX + scrollbarWidth, barY + containerHeight, 0xFF1E1E1E);

        float visibleRatio = (float) containerHeight / contentHeight;
        int thumbHeight = Math.max(4, (int) (containerHeight * visibleRatio));

        float maxScroll = contentHeight - containerHeight;
        float scrollRatio = maxScroll > 0 ? scrollAmount / maxScroll : 0;
        int thumbY = barY + (int) ((containerHeight - thumbHeight) * scrollRatio);

        graphics.fill(barX, thumbY, barX + scrollbarWidth, thumbY + thumbHeight, 0xFFAAAAAA);
    }

    private int getScrollbarVirtualX() {
        return leftPos + containerX + containerWidth + scrollbarGap;
    }

    private int getScrollbarVirtualY() {
        return topPos + containerY;
    }

    private boolean isMouseOverScrollbar(double virtualMouseX, double virtualMouseY) {
        int x = getScrollbarVirtualX();
        int y = getScrollbarVirtualY();
        return virtualMouseX >= x && virtualMouseX <= x + scrollbarWidth &&
                virtualMouseY >= y && virtualMouseY <= y + containerHeight;
    }

    private boolean isMouseOverContainer(double virtualMouseX, double virtualMouseY) {
        int x = leftPos + containerX;
        int y = topPos + containerY;
        return virtualMouseX >= x && virtualMouseX <= x + containerWidth &&
                virtualMouseY >= y && virtualMouseY <= y + containerHeight;
    }

    private void setScrollFromThumbDrag(double virtualMouseY) {
        float maxScroll = Math.max(0, contentHeight - containerHeight);
        if (maxScroll <= 0) return;

        int barY = getScrollbarVirtualY();
        float visibleRatio = (float) containerHeight / contentHeight;
        int thumbHeight = Math.max(4, (int) (containerHeight * visibleRatio));

        double relativeY = virtualMouseY - barY - (thumbHeight / 2.0);
        double trackRange = containerHeight - thumbHeight;

        float ratio = (float) Mth.clamp(relativeY / trackRange, 0.0, 1.0);
        scrollAmount = ratio * maxScroll;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        double virtualX = mouseX / guiScale;
        double virtualY = mouseY / guiScale;

        if (isMouseOverContainer(virtualX, virtualY) || isMouseOverScrollbar(virtualX, virtualY)) {
            float maxScroll = Math.max(0, contentHeight - containerHeight);
            scrollAmount = Mth.clamp((float) (scrollAmount - delta * 8), 0, maxScroll);
            return true;
        }
        return super.mouseScrolled(virtualX, virtualY, delta);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        double virtualX = mouseX / guiScale;
        double virtualY = mouseY / guiScale;

        if (draggingScrollbar) {
            setScrollFromThumbDrag(virtualY);
            return true;
        }
        return super.mouseDragged(virtualX, virtualY, button, dragX / guiScale, dragY / guiScale);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX / guiScale, mouseY / guiScale);
    }
}