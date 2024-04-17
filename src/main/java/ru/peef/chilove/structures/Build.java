package ru.peef.chilove.structures;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.type.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ru.peef.chilove.ChiloveMain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Build {
    Plugin plugin;
    List<String> blocks = new ArrayList<>();

    public Build(Plugin plugin, int startX, int startY, int startZ, int endX, int endY, int endZ) {
        plugin.getLogger().info(startX + " " + startY + " " + startZ + " | " + endX + " " + endY + " " + endZ);

        this.plugin = plugin;
        int startingX = startX;
        int startingY = startY;
        int startingZ = startZ;
        int endingX = endX;
        int endingY = endY;
        int endingZ = endZ;

        // TODO => -15 больше -30
        if (String.valueOf(startingX).startsWith("-")) {
            startingX = endX;
            endingX = startX;
        }
        if (startingX > endingX) {
            startingX = endX;
            endingX = startX;
        }

        if (startingY > endingY) {
            startingY = endY;
            endingY = startY;
        }

        if (startingZ > endingZ) {
            startingZ = endZ;
            endingZ = startZ;
        }

        for (int x = startingX; x <= endingX; x++) {
            for (int y = startingY; y <= endingY; y++) {
                for (int z = startingZ; z <= endingZ; z++) {
                    World world = plugin.getServer().getWorlds().get(0);
                    Material mat = world.getType(x, y, z);
                    plugin.getLogger().info("Scan (" + x + " " + y + " " + z + ") => " + mat);
                    String addon = "";
                    plugin.getLogger().info("State=" + world.getBlockAt(x,y,z).getState().getData());
                    if (world.getBlockAt(x,y,z).getState().getData() instanceof TrapDoor trapdoor) {
                        addon = "[facing=" + trapdoor.getFacing() + ",half=" + trapdoor.getHalf() + ",open=" + trapdoor.isOpen() + "]";
                    }
                    if (world.getBlockAt(x,y,z).getState().getData() instanceof Door door) {
                        addon = "[facing=" + door.getFacing() + ",half=" + door.getHalf() + ",open=" + door.isOpen() + "]";
                    }
                    if (world.getBlockAt(x,y,z).getState().getData() instanceof Stairs stairs) {
                        addon = "[facing=" + stairs.getFacing() + ",half=" + stairs.getHalf() + ",shape=" + stairs.getShape() + "]";
                    }
                    if (world.getBlockAt(x,y,z).getState().getData() instanceof Slab slab) {
                        addon = "[type=" + slab.getType() + "]";
                    }
                    if (world.getBlockAt(x,y,z).getState().getData() instanceof Gate gate) {
                        addon = "[facing=" + gate.getFacing() + ",open" + gate.isOpen() + ",in_wall=" + gate.isInWall() + "]";
                    }
                    if (world.getBlockAt(x,y,z).getState().getData() instanceof Switch btn) {
                        addon = "[facing=" + btn.getFacing() + "]";
                    }
                    if (world.getBlockAt(x,y,z).getState().getData() instanceof Chain chain) {
                        addon = "[axis=" + chain.getAxis() + "]";
                    }
                    if (mat != Material.AIR) {
                        blocks.add(
                                x + "," + y + "," + z + ":" + mat + addon
                        );
                    }
                }
            }
        }
    }

    public Build(Plugin plugin, String name) {
        String build = get(name, false);
        blocks.addAll(Arrays.asList(build.split("\\|")));
    }

    // Building format
    // [x1,y1,z1:block1|x2,y2,z2:block2|xN,yN,zN:blockN]

    public String save(String name) {
        File build_file = new File(plugin.getDataFolder() + "/" + name + ".bld");
        try {
            if (build_file.createNewFile()) {
                FileWriter writer = new FileWriter(plugin.getDataFolder() + "/" + name + ".bld");
                if (!blocks.isEmpty()) {
                    StringBuilder writer_string = new StringBuilder();
                    plugin.getLogger().info(blocks.toString());
                    for (String block : blocks) {
                        if (!block.toLowerCase().contains("build") && !block.toLowerCase().contains("failed") && !block.toLowerCase().contains("not material")) {
                            String[] bl_split = block.split(",");
                            String[] z_split = bl_split[2].split(":");
                            writer_string
                                    .append(bl_split[0])
                                    .append(",")
                                    .append(bl_split[1])
                                    .append(",")
                                    .append(z_split[0])
                                    .append(":")
                                    .append(z_split[1])
                                    .append("|");
                        } else {
                            try {
                                blocks.remove(block);
                                plugin.getLogger().info("INVALID FORMAT STRING DELETED (" + block + ")");
                            } catch (Exception exc) {
                                plugin.getLogger().warning("Can't delete string with invalid format => (" + block + ") | " + exc.getMessage());
                            }
                        }
                    }
                    String final_string = writer_string.toString();
                    final_string = final_string.substring(0, final_string.length() - 1);

                    writer.write("{" + final_string + "}");
                } else {
                    writer.write("{}");
                }
                writer.close();
                return "Build saved";
            } else {
                return "Build with name " + name + " already exists";
            }
        } catch (IOException exc) {
            return "Failed to save " + name + ".bld => " + exc.getMessage();
        }
    }

    public String remove(String name) {
        File build_file = new File(plugin.getDataFolder() + "/" + name + ".bld");
        try {
            if (build_file.exists()) {
                if (build_file.delete()) {
                    return "Build remove successfully";
                } else {
                    return "Failed to remove build";
                }
            } else {
                return "Build not found";
            }
        } catch (Exception exc) {
            return "Failed to save " + name + ".bld => " + exc.getMessage();
        }
    }

    public String get(String name, boolean extendFields) {
        String file = plugin.getDataFolder() + "/" + name + ".bld";
        File build_file = new File(file);
        try {
            if (build_file.exists()) {
                if (!extendFields) {
                    return "Build exists";
                } else {
                    String content = new String(Files.readAllBytes(Paths.get(file))).replace("{", "").replace("}", "");

                    for (String str : content.split("\\|")) {
                        for (String cord : str.split(",")) {
                            String block2 = cord;
                            if (cord.split(":").length == 2) {
                                block2 = cord.split(":")[0];
                                try {
                                    Material.getMaterial(cord.split(":")[1]);
                                } catch (Exception exc) {
                                    return cord.split(":")[1] + " is not material (at " + cord + ")";
                                }
                            }

                            try {
                                Integer.parseInt(block2);
                            } catch (NumberFormatException exception) {
                                return "Failed to get coordinate " + cord + " (NaN)";
                            }
                        }
                    }

                    return content;
                }
            } else {
                return "Build not found";
            }
        } catch (Exception exc) {
            return "Failed to get " + name + ".bld => " + exc.getMessage();
        }
    }
}
