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
package net.huntersharpe.NPCShop.commands;

import net.huntersharpe.NPCShop.NPCShop;
import net.huntersharpe.NPCShop.util.EntityHandler;
import net.huntersharpe.NPCShop.util.JSONHandler;
import net.huntersharpe.NPCShop.util.MessageHandler;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;

//TODO: Remove debug numbers
public class CreateCommand implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)){
            MessageHandler.getInstance().sendWrongSourceMessage(src);
            return CommandResult.success();
        }
        Player player = (Player)src;
        CommentedConfigurationNode config = NPCShop.getInstance().getConfig();
        String name = args.<String>getOne("name").get();
        String id = args.<String>getOne("id").get();
        String type = args.<NPCShop.Type>getOne("type").get().toString();
        //This about to get ugly
        if(!config.getNode(id).isVirtual()){
            MessageHandler.getInstance().sendErrorMessage(src, "A NPC with that ID already exists!");
            return CommandResult.success();
        }
        //If skin is specified
        if(!args.<String>getOne("playername").get().equalsIgnoreCase("none")){
            String skinPlayer = args.<String>getOne("playername").get();
            //BUY|SELL
            if(type.equalsIgnoreCase("buy") || type.equalsIgnoreCase("sell")){
                if(args.<String>getOne("item").isPresent()){
                    //Probably not how it is done.
                    if(Sponge.getRegistry().getType(ItemType.class, args.<String>getOne("item").get()).isPresent()){
                        if(args.<String>getOne("price").isPresent()){
                            if(JSONHandler.getInstance().isValidAccount(skinPlayer)){
                                EntityHandler.getInstance().createEntity(player.getName(), name, id, player.getLocation(),
                                        type, args.<String>getOne("item").get(), args.<String>getOne("price").get(), skinPlayer);
                                MessageHandler.getInstance().sendSuccessMessage(player, "1Created NPC Successfully!");
                                return CommandResult.success();
                            }
                            MessageHandler.getInstance().sendErrorMessage(player, skinPlayer + " is not a valid MC character.");
                            return CommandResult.success();
                        }
                        MessageHandler.getInstance().sendErrorMessage(src, "1If you're going to make the NPC a Buy or Sell type, " +
                                "then you must specify a item type AND a price.");
                        return CommandResult.success();
                    }
                    MessageHandler.getInstance().sendErrorMessage(player, "1Item is not a valid item.");
                    return CommandResult.success();
                    //DISPLAY
                }
                MessageHandler.getInstance().sendErrorMessage(src, "2If you're going to make the NPC a Buy or Sell type, " +
                        "then you must specify a item type AND a price.");
                return CommandResult.success();
            }else if(JSONHandler.getInstance().isValidAccount(skinPlayer)){
                EntityHandler.getInstance().createEntity(player.getName(), name, id, player.getLocation(), skinPlayer);
                MessageHandler.getInstance().sendSuccessMessage(player, "2Created NPC Successfully!");
                return CommandResult.success();
            }
            MessageHandler.getInstance().sendErrorMessage(player, skinPlayer + " is not a valid MC character.");
            return CommandResult.success();
        }
        //If no skin is specified
        if(type.equalsIgnoreCase("buy") || type.equalsIgnoreCase("sell")){
            if(args.<String>getOne("item").isPresent()){
                //Probably not how it is done.
                if(Sponge.getRegistry().getType(ItemType.class, args.<String>getOne("item").get()).isPresent()){
                    if(args.<String>getOne("price").isPresent()){
                        EntityHandler.getInstance().createEntity(player.getName(), name, id, player.getLocation(), type,
                                args.<String>getOne("item").get(), args.<String>getOne("price").get());
                        MessageHandler.getInstance().sendSuccessMessage(player, "3Created NPC Successfully!");
                        return CommandResult.success();
                    }
                    MessageHandler.getInstance().sendErrorMessage(src, "3If you're going to make the NPC a Buy or Sell type, " +
                            "then you must specify a item type and price.");
                    return CommandResult.success();
                }
                MessageHandler.getInstance().sendErrorMessage(player, "2Item is not a valid item.");
                return CommandResult.success();
            }
            MessageHandler.getInstance().sendErrorMessage(src, "4If you're going to make the NPC a Buy or Sell type, " +
                    "then you must specify a item type and price.");
            return CommandResult.success();
        }
        EntityHandler.getInstance().createEntity(player.getName(), name, id, player.getLocation());
        System.out.println(type);
        MessageHandler.getInstance().sendSuccessMessage(player, "4Created NPC Successfully!");
        return CommandResult.success();
    }
}