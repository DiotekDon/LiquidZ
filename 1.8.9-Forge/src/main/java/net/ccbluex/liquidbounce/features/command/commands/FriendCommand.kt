package net.ccbluex.liquidbounce.features.command.commands

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.command.Command
import net.ccbluex.liquidbounce.utils.misc.StringUtils

/**
 * LiquidBounce Hacked Client
 * A minecraft forge injection client using Mixin
 *
 * @game Minecraft
 * @author CCBlueX
 */
class FriendCommand : Command("friend", arrayOf("friends")) {

    /**
     * Execute commands with provided [args]
     */
    override fun execute(args: Array<String>) {
        if (args.size > 1) {
            val friendsConfig = LiquidBounce.fileManager.friendsConfig

            when {
                args[1].equals("add", ignoreCase = true) -> {
                    if (args.size > 2) {
                        val name = args[2]

                        if (name.isEmpty()) {
                            chat("The name is empty.")
                            return
                        }

                        if (if (args.size > 3) friendsConfig.addFriend(name, StringUtils.toCompleteString(args, 3)) else friendsConfig.addFriend(name)) {
                            LiquidBounce.fileManager.saveConfig(friendsConfig)
                            chat("§a§l$name§3 was added to your friend list.")
                            playEdit()
                        } else
                            chat("The name is already in the list.")
                        return
                    }
                    chatSyntax("friend add <name> [alias]")
                    return
                }

                args[1].equals("remove", ignoreCase = true) -> {
                    if (args.size > 2) {
                        val name = args[2]

                        if (friendsConfig.removeFriend(name)) {
                            LiquidBounce.fileManager.saveConfig(friendsConfig)
                            chat("§a§l$name§3 was removed from your friend list.")
                            playEdit()
                        } else
                            chat("This name is not in the list.")
                        return
                    }
                    chatSyntax("friend remove <name>")
                    return
                }

                args[1].equals("clear", ignoreCase = true) -> {
                    val friends = friendsConfig.friends.size
                    friendsConfig.clearFriends()
                    LiquidBounce.fileManager.saveConfig(friendsConfig)
                    chat("Removed $friends friend(s).")
                    return
                }

                args[1].equals("list", ignoreCase = true) -> {
                    chat("Your Friends:")

                    for (friend in friendsConfig.friends)
                        chat("§7> §a§l${friend.playerName} §c(§7§l${friend.alias}§c)")

                    chat("You have §c${friendsConfig.friends.size}§3 friends.")
                    return
                }
            }
        }

        chatSyntax("friend <add/remove/list/clear>")
    }

}