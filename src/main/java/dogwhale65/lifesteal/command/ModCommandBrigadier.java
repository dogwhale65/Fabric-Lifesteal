package dogwhale65.lifesteal.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommandBrigadier {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> withdraw = CommandManager
                .literal("withdraw")
                .build();

        CommandNode<ServerCommandSource> amount = CommandManager
                .argument("amount", IntegerArgumentType.integer(1))
                .executes(ModCommandFunctions::withdraw)
                .build();

        LiteralCommandNode<ServerCommandSource> revive = CommandManager
                .literal("revive")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(ModCommandFunctions::revive)
                .build();


        dispatcher.getRoot().addChild(withdraw);
        dispatcher.getRoot().addChild(revive);
        withdraw.addChild(amount);
    }






}
