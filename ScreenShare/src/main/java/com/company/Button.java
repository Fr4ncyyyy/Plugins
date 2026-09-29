package com.company;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Button {

    private String path;
    private TextComponent message;

    public Button(String path, String nameUser,boolean prepareCommands) {
        this.path = path;
        createButton(nameUser,prepareCommands);
    }

    private void createButton(String nameUser,boolean prepareCommands) {

        ConfigManager config = ConfigManager.getIstance();

        message = new TextComponent(config.getNameButton(path));

        String color = config.getColorButton(path);

        message.setColor(ChatColor.valueOf(color));

        ClickEvent.Action action;
        if(prepareCommands){
            action = ClickEvent.Action.SUGGEST_COMMAND;
        }else {
            action = ClickEvent.Action.RUN_COMMAND;
        }

            message.setClickEvent(new ClickEvent(
                    action,
                    config.getCommandButton(path)
                            .replace("%player%", nameUser)
            ));

        message.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new TextComponent[]{
                        new TextComponent(
                                config.getHoverButton(path)
                                        .replace("%player%", nameUser)
                        )
                }
        ));
    }

    public TextComponent getButton(){
        return message;
    }

}
