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
package net.huntersharpe.NPCShop.util;

import net.huntersharpe.NPCShop.NPCShop;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class EntityHandler {

    //Might initialized configurationloader<> instead.
    private static EntityHandler instance = new EntityHandler();

    public static EntityHandler getInstance(){
        return instance;
    }

    private CommentedConfigurationNode config = NPCShop.getInstance().getConfig();

    //Entity with no skin, type=display
    public void createEntity(String sender, String name, String id, Location<World> location){
        Human human = (Human) Sponge.getServer().getWorld(location.getExtent().getName()).get().createEntity(
                EntityTypes.HUMAN,
                location.getBlockPosition()
        );
        human.offer(Keys.DISPLAY_NAME, Text.of(name));
        config.getNode(id, "name").setValue(name);
        config.getNode(id, "uuid").setValue(human.getUniqueId().toString());
        config.getNode(id, "type").setValue("Display");
        //Setting to string of null on purpose.
        config.getNode(id, "item").setValue("null");
        config.getNode(id, "price").setValue("null");
        config.getNode(id, "skin").setValue("null");
        config.getNode(id, "location", "world").setValue(location.getExtent().getName());
        config.getNode(id, "location", "x").setValue(location.getBlockPosition().getX());
        config.getNode(id, "location", "y").setValue(location.getBlockPosition().getY());
        config.getNode(id, "location", "Z").setValue(location.getBlockPosition().getZ());
        try {
            NPCShop.getInstance().getLoader().save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        location.getExtent().spawnEntity(human, Cause.of(NamedCause.simulated(Sponge.getServer().getPlayer(sender)
                .get())));
    }
    //Entity with skin, type=display
    public void createEntity(String sender, String name, String id, Location<World> location, String skinName){
        Human human = (Human) Sponge.getServer().getWorld(location.getExtent().getName()).get().createEntity(
                EntityTypes.HUMAN,
                location.getBlockPosition()
        );
        human.offer(Keys.DISPLAY_NAME, Text.of(name));
        human.offer(Keys.SKIN_UNIQUE_ID, UUID.fromString(JSONHandler.getInstance().getUUID(skinName).toString()));
        config.getNode(id, "name").setValue(name);
        config.getNode(id, "uuid").setValue(human.getUniqueId().toString());
        config.getNode(id, "type").setValue("Display");
        //Setting to string of null on purpose.
        config.getNode(id, "item").setValue("null");
        config.getNode(id, "price").setValue("null");
        config.getNode(id, "skin").setValue(JSONHandler.getInstance().getUUID(skinName).toString());
        config.getNode(id, "location", "world").setValue(location.getExtent().getName());
        config.getNode(id, "location", "x").setValue(location.getBlockPosition().getX());
        config.getNode(id, "location", "y").setValue(location.getBlockPosition().getY());
        config.getNode(id, "location", "Z").setValue(location.getBlockPosition().getZ());
        try {
            NPCShop.getInstance().getLoader().save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        location.getExtent().spawnEntity(human, Cause.of(NamedCause.simulated(Sponge.getServer().getPlayer(sender)
                .get())));
    }
    //Entity with no skin, type=buy|sell
    public void createEntity(String sender, String name, String id, Location<World> location, String type, String item,
                             String price){
        Human human = (Human) Sponge.getServer().getWorld(location.getExtent().getName()).get().createEntity(
                EntityTypes.HUMAN,
                location.getBlockPosition()
        );
        human.offer(Keys.DISPLAY_NAME, Text.of(name));
        config.getNode(id, "name").setValue(name);
        config.getNode(id, "uuid").setValue(human.getUniqueId().toString());
        config.getNode(id, "type").setValue(type);
        config.getNode(id, "item").setValue(item);
        config.getNode(id, "price").setValue(price);
        config.getNode(id, "location", "world").setValue(location.getExtent().getName());
        config.getNode(id, "location", "x").setValue(location.getBlockPosition().getX());
        config.getNode(id, "location", "y").setValue(location.getBlockPosition().getY());
        config.getNode(id, "location", "Z").setValue(location.getBlockPosition().getZ());
        try {
            NPCShop.getInstance().getLoader().save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        location.getExtent().spawnEntity(human, Cause.of(NamedCause.simulated(Sponge.getServer().getPlayer(sender)
                .get())));
    }
    //Entity with skin, type=buy|sell
    public void createEntity(String sender, String name, String id, Location<World> location, String type, String item,
                             String price, String skinName){
        Human human = (Human) Sponge.getServer().getWorld(location.getExtent().getName()).get().createEntity(
                EntityTypes.HUMAN,
                location.getBlockPosition()
        );
        human.offer(Keys.DISPLAY_NAME, Text.of(name));
        human.offer(Keys.SKIN_UNIQUE_ID, JSONHandler.getInstance().getUUID(skinName));
        config.getNode(id, "name").setValue(name);
        config.getNode(id, "uuid").setValue(human.getUniqueId().toString());
        config.getNode(id, "type").setValue(type);
        config.getNode(id, "item").setValue(item);
        config.getNode(id, "price").setValue(price);
        config.getNode(id, "location", "world").setValue(location.getExtent().getName());
        config.getNode(id, "location", "x").setValue(location.getBlockPosition().getX());
        config.getNode(id, "location", "y").setValue(location.getBlockPosition().getY());
        config.getNode(id, "location", "Z").setValue(location.getBlockPosition().getZ());
        config.getNode(id, "skin").setValue(JSONHandler.getInstance().getUUID(skinName).toString());
        try {
            NPCShop.getInstance().getLoader().save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        location.getExtent().spawnEntity(human, Cause.of(NamedCause.simulated(Sponge.getServer().getPlayer(sender)
                .get())));
    }

    public void deleteEntity(String id){
        Sponge.getServer().getWorld(config.getNode(id, "location", "world").getString()).get()
                .getEntity(UUID.fromString(config.getNode(id, "uuid").getString())).get().remove();
        config.removeChild(id);
    }

    //May add ability to edit skin.
    public void editEntity(String id, String data, Optional<String> item, Optional<String> price){

    }

}
