package com.aymegike.huminekingdom.utils;


import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

import org.bukkit.scoreboard.Scoreboard;



import java.lang.reflect.Array;

import java.lang.reflect.Field;

import java.lang.reflect.Method;

import java.util.ArrayList;

import java.util.Collection;



/**

 * @author Vinetos

 */

public class Reflection

{



    // Get a class

    public static Class<?> getClass(String classname)

    {

        try

        {

            String version = getNmsVersion();

            String path = classname.replace("{nms}", "net.minecraft.server." + version)

                    .replace("{nm}", "net.minecraft." + version)

                    .replace("{cb}", "org.bukkit.craftbukkit." + version);

            return Class.forName(path);

        } catch (Throwable t)

        {

            t.printStackTrace();

            return null;

        }

    }



    // Get a class as Array

    public static Class<?> getArrayClass(String classname, int arraySize)

    {

        try

        {

            return Array.newInstance(getClass(classname), arraySize).getClass();

        } catch (Throwable t)

        {

            t.printStackTrace();

            return null;

        }

    }



    // Get a enum value

    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Enum getEnum(String enumClass, String enumValue)

    {

        return Enum.valueOf((Class<? extends Enum>) Reflection.getClass(enumClass), enumValue);

    }





    // Get a net.minecraft.server version

    public static String getNmsVersion()

    {

        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

    }





    // Get a nms player

    public static Object getNmsPlayer(Player p) throws Exception

    {

        Method getHandle = p.getClass().getMethod("getHandle");

        return getHandle.invoke(p);

    }



    // Get a nms scoreboard

    public static Object getNmsScoreboard(Scoreboard s) throws Exception

    {

        Method getHandle = s.getClass().getMethod("getHandle");

        return getHandle.invoke(s);

    }



    // Get a field value

    public static Object getFieldValue(Object instance, String fieldName) throws Exception

    {

        Field field = instance.getClass().getDeclaredField(fieldName);

        field.setAccessible(true);

        return field.get(instance);

    }



    // Get a field value

    @SuppressWarnings("rawtypes")

    public static Object getFieldValue(Object instance, Class clazz, String fieldName) throws Exception

    {

        Field field = clazz.getDeclaredField(fieldName);

        field.setAccessible(true);

        return field.get(instance);

    }





    // get a field

    public static Field getField(Class<?> clazz, String fieldName)

    {

        Field field = null;

        try

        {

            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

        } catch (NoSuchFieldException e)

        {

            e.printStackTrace();

        }

        return field;

    }



    // Getting fields

    public static ArrayList<Field> getFields(Object instance, Class<?> fieldType) throws Exception

    {

        Field[] fields = instance.getClass().getDeclaredFields();

        ArrayList<Field> fieldArrayList = new ArrayList<Field>();

        for (Field field : fields)

        {

            if (field.getType() == fieldType)

            {

                field.setAccessible(true);

                fieldArrayList.add(field);

            }

        }



        return fieldArrayList;

    }



    // Get a Fields who is an Array

    public static ArrayList<Field> getArraysFields(Object instance, Class<?> fieldType) throws Exception

    {

        String[] values = fieldType.toString().split(" ");

        String fieldName = values[values.length - 1];

        Field[] fields = instance.getClass().getDeclaredFields();

        ArrayList<Field> fieldArrayList = new ArrayList<Field>();

        for (Field field : fields)

        {

            if (field.getType().isArray())

            {

                if (field.getType().toString().contains(fieldName))

                {

                    field.setAccessible(true);

                    fieldArrayList.add(field);

                }

            }

        }

        return fieldArrayList;

    }



    // Get a field value

    @SuppressWarnings("unchecked")

    public static <T> T getFieldValue(Field field, Object obj)

    {

        try

        {

            return (T) field.get(obj);

        } catch (Exception e)

        {

            e.printStackTrace();

            return null;

        }

    }



    // Set a field value

    public static void setFieldValue(Object instance, String field, Object value)

    {

        try

        {

            Field f = instance.getClass().getDeclaredField(field);

            f.setAccessible(true);

            f.set(instance, value);

        } catch (Throwable t)

        {

            t.printStackTrace();

        }

    }



    // Set a field value

    public static void setFieldValue(Object instance, Field field, Object value)

    {

        field.setAccessible(true);

        try

        {

            field.set(instance, value);

        } catch (IllegalAccessException e)

        {

            e.printStackTrace();

        }

    }



    // Get the first field by her type

    public static Field getFirstFieldByType(Class<?> clazz, Class<?> type)

    {

        for (Field field : clazz.getDeclaredFields())

        {

            field.setAccessible(true);

            if (field.getType() == type)

            {

                return field;

            }

        }

        return null;

    }



    // Get player connection

    public static Object getPlayerConnection(Player player) throws Exception

    {

        Object nmsPlayer = getNmsPlayer(player);

        return nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);

    }



    // Send packet to a connection player

    public static void sendPacket(Object connection, Object packet) throws Exception

    {

        connection.getClass().getMethod("sendPacket", Reflection.getClass("{nms}.Packet")).invoke(connection, packet);

    }



    // Send packet to all connected player

    public static void sendPacket(Object packet)

    {

        sendPacket(Bukkit.getOnlinePlayers(), packet);

    }



    // Send packet to a list of player

    public static void sendPacket(Collection<? extends Player> players, Object packet)

    {

        if (packet == null)

            return;

        try

        {

            for (Player p : players)

                sendPacket(getPlayerConnection(p), packet);

        } catch (Throwable t)

        {

            t.printStackTrace();

        }

    }



    // Send a packet to a player

    public static void sendPacket(Player p, Object packet)

    {

        ArrayList<Player> list = new ArrayList<>();

        list.add(p);

        sendPacket(list, packet);

    }



    // Send a message with IChatBaseComponent

    public static void sendMessage(Player p, Object message) throws Exception

    {

        Object nmsPlayer = getNmsPlayer(p);

        nmsPlayer.getClass().getMethod("sendMessage", Reflection.getClass("{nms}.IChatBaseComponent")).invoke(nmsPlayer, message);



    }



    // Get the ping of player

    public static int ping(Player p) throws Exception

    {

        Object nmsPlayer = Reflection.getNmsPlayer(p);

        return Integer.valueOf(getFieldValue(nmsPlayer, "ping").toString());

    }



}
