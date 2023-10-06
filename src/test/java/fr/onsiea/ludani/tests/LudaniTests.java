/*
 * Copyright 2021-2023 Onsiea Studio some rights reserved.
 *
 * This file is part of Ludart Game Framework project developed by Onsiea Studio.
 * (https://github.com/OnsieaStudio/Ludart)
 *
 * Ludart is [licensed]
 * (https://github.com/OnsieaStudio/Ludart/blob/main/LICENSE) under the terms of
 * the "GNU General Public License v3.0" (GPL-3.0).
 * https://github.com/OnsieaStudio/Ludart/wiki/License#license-and-copyright
 *
 * Ludart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * Ludart is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ludart. If not, see <https://www.gnu.org/licenses/>.
 *
 * Any reproduction or alteration of this project may reference it and utilize its name and derivatives, provided it clearly states its modification status and includes a link to the original repository. Usage of all names belonging to authors, developers, and contributors remains subject to copyright.
 * in other cases prior written authorization is required for using names such as "Onsiea," "Ludart," or any names derived from authors, developers, or contributors for product endorsements or promotional purposes.
 *
 *
 * @Author : Seynax (https://github.com/seynax)
 * @Organization : Onsiea Studio (https://github.com/OnsieaStudio)
 */

package fr.onsiea.ludani.tests;

import fr.onsiea.ludart.common.Framework;
import fr.onsiea.ludart.common.modules.settings.ModuleSettings;
import fr.onsiea.tools.logger.Loggers;

import java.lang.reflect.InvocationTargetException;

public class LudaniTests
{

	// Settings
	private final static ModuleSettings SETTINGS = ModuleSettings.defaults(); // Without modification, configured on DEFAULTS SETTINGS from ModuleSettingsClient
	public final static  Loggers        LOGGERS  = LudaniTests.SETTINGS.loggers();

	/**
	 * @author Seynax
	 */
	public static void main(final String[] args)
	{
		LudaniTests.LOGGERS.logLn("ENGINE_TESTS : HELLO WORLD !");

		// Framework building phase with modules schemas building
		Framework framework;
		{
			Framework.Builder frameworkBuilder = null;
			try
			{
				frameworkBuilder = new Framework.Builder();
				frameworkBuilder.each((schemaIn) ->
				{
					System.out.println(schemaIn.sourceModuleClass().getSimpleName());
				});
				framework = frameworkBuilder.build();
			}
			catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException eIn)
			{
				throw new RuntimeException(eIn);
			}
		}

		// Modules replacements phase [WORK IN PROGRESS]
		{

		}

		// Framework initialization phase with modules creation
		{
			framework.startAll();
		}

		// Settings phase
		{
		}

		// Modules runtime phase
		{
			framework.startAll(); // Start all modules
			while (framework.isWorking()) // As long as the client module is running, the loop continues. The client module can stop, for example, if the user requests the window to stop from the window module
			{
				framework.iterateAll(); // Iterate all modules
			}
			framework.stopAll(); // Stop all modules
		}
	}
}