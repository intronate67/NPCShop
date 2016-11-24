/*This file is part of NPCShop, licensed under the MIT License (MIT).
*
* Copyright (c) 2016 Hunter Sharpe
* Copyright (c) contributors

* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/
package net.huntersharpe.NPCShop;

import com.google.inject.Inject;
import net.huntersharpe.NPCShop.commands.*;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//TODO: Load NPC from config after start/stop/restart
@Plugin(id="npcshop", name="NPCShop", version="0.0.1", description="Use NPCs to buy and sell items.")
public class NPCShop {

    private static NPCShop instance;

    public static NPCShop getInstance(){
        return instance;
    }

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File configurationFile = null;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> loader = null;

    private CommentedConfigurationNode node = null;

    @Inject
    private Game game;

    @Listener
    public void onPreInit(GamePreInitializationEvent event){
        instance = this;
        try{
            if(!configurationFile.exists()){
                configurationFile.createNewFile();
                node = loader.load();
                loader.save(node);
            }
            node = loader.load();
            loader.save(node);
        }catch(IOException e){
            e.printStackTrace();
        }
        game.getCommandManager().register(this, shopCommand, "npcshop");
    }

    @Listener
    public void onServerStop(GameStoppingEvent event){
        try {
            loader.save(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum Type{
        BUY,
        SELL,
        DISPLAY;
        private static final Map<String, Type> choices = new HashMap<>();
        static{
            choices.put("buy", BUY);
            choices.put("sell", SELL);
            choices.put("display", DISPLAY);
        }
    }

    //May want to change generic argument type for price.
    //Item - price - playername
    private CommandSpec createCommand = CommandSpec.builder()
            .arguments(
                    GenericArguments.string(Text.of("name")),
                    GenericArguments.string(Text.of("id")),
                    GenericArguments.choices(Text.of("type"), Type.choices),
                    GenericArguments.string(Text.of("playername")),
                    GenericArguments.optional(GenericArguments.string(Text.of("item"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("price")))
            )
            .executor(new CreateCommand())
            .build();
    private CommandSpec deleteCommand = CommandSpec.builder()
            .arguments(GenericArguments.string(Text.of("id")))
            .executor(new DeleteCommand())
            .build();
    private CommandSpec editCommand = CommandSpec.builder()
            .arguments(
                    GenericArguments.string(Text.of("id")),
                    GenericArguments.choices(Text.of("type"), Type.choices),
                    GenericArguments.optional(GenericArguments.string(Text.of("item"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("price")))
            )
            .executor(new EditCommand())
            .build();
    private CommandSpec infoCommand = CommandSpec.builder()
            .arguments(GenericArguments.string(Text.of("id")))
            .executor(new InfoCommand())
            .build();
    //May switch over to .children()
    private CommandSpec shopCommand = CommandSpec.builder()
            .executor(new ShopCommand())
            .child(createCommand, "create")
            .child(deleteCommand, "delete", "del")
            .child(editCommand, "edit")
            .child(infoCommand, "info")
            .build();

    public CommentedConfigurationNode getConfig(){
        return node;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getLoader(){
        return loader;
    }
    /*Command Structure
        /npcshop <create|delete|info?|edit>
            - create <name> <id> <buy|sell|display> [item] [price]
            - delete <id>
            - info <id>
            - edit <id> <buy|sell|display> [item] [price]
        May add ability to change display name.
     */
}
