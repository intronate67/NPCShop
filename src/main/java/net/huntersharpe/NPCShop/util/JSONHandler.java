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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.UUID;

public class JSONHandler {

    private static JSONHandler instance = new JSONHandler();

    public static JSONHandler getInstance(){
        return instance;
    }

    //8, 4 , 4, 4, 12
    //If this works...
    public UUID getUUID(String name){
        try{
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name + "?at=" + Instant.now()
                    .getEpochSecond());
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JsonElement element = new JsonParser().parse(rd.readLine());
            String sUUID = element.getAsJsonObject().get("id").getAsString();
            String uuid =
                    sUUID.substring(0, Math.min(sUUID.length(), 8))
                    + "-"
                    + sUUID.substring(8, Math.min(sUUID.length(), 12))
                    + "-"
                    + sUUID.substring(12, Math.min(sUUID.length(), 16))
                    + "-"
                    + sUUID.substring(16, Math.min(sUUID.length(), 20))
                    + "-"
                    + sUUID.substring(20, Math.min(sUUID.length(), 32));
            if(sUUID !=null){
                return UUID.fromString(uuid);
            }
            rd.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValidAccount(String name){
        try{
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name + "?at=" + Instant.now()
                    .getEpochSecond());
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while(rd.readLine() != null){
                return true;
            }
            rd.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
