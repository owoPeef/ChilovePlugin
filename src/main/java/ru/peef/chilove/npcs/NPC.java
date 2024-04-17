package ru.peef.chilove.npcs;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

// TODO
public class NPC {
    public NPC(Player player, String name) {
        Location location = player.getLocation();
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        packet.getIntegers().write(0, 9);
        packet.getUUIDs().write(0, player.getUniqueId());

        packet.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        packet.getBytes()
                .write(0, (byte) (location.getYaw() * 256.0F / 360.0F))
                .write(1, (byte) (location.getPitch() * 256.0F / 360.0F));

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
    }
}
