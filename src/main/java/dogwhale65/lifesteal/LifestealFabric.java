package dogwhale65.lifesteal;

import dogwhale65.lifesteal.command.ModCommandBrigadier;
import dogwhale65.lifesteal.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LifestealFabric implements ModInitializer {
	public static final String MOD_ID = "lifestealfabric";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
			ModCommandBrigadier.register(dispatcher);
		});
		DeathEventHandler.register();
		LOGGER.info("Loaded LifeStealFabric.");
	}
}