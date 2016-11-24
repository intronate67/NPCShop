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

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class MessageHandler {

    private Text prefix = Text.of(
            TextColors.DARK_GRAY,
            "[",
            TextColors.BLUE,
            "NPCShop",
            TextColors.DARK_GRAY,
            "] "
    );

    private static MessageHandler instance = new MessageHandler();

    public static MessageHandler getInstance() {
        return instance;
    }

    public void sendWrongSourceMessage(CommandSource src){
        src.sendMessage(Text.of(prefix, TextColors.RED, "Only players can use that command!"));
    }

    public void sendErrorMessage(CommandSource src, String message){
        src.sendMessage(Text.of(prefix, TextColors.RED, message));
    }

    public void sendSuccessMessage(CommandSource src, String message){
        src.sendMessage(Text.of(prefix, TextColors.GREEN, message));
    }
}
