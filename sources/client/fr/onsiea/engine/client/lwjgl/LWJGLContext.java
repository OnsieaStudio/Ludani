package fr.onsiea.engine.client.lwjgl;

import org.lwjgl.Version;
import org.lwjgl.system.Configuration;

public final class LWJGLContext
{
	// Verification des problèmes liés à la LWJGL/GLFW ou OpenGL (performance, nullpointer, fonction facultative) peut être
	// désactiver pour la version release mais doit être conserver pour la version de developpement
	// Paramètre :
	// Dorg.lwjgl.util.NoChecks=true
	// Fonction :
	// Configuration.DISABLE_CHECKS.set(true)

	// Debug mode
	// Paramètre
	// Dorg.lwjgl.util.Debug=true
	// Fonction
	// Configuration.DEBUG.set(true)

	// Shared library loader debug
	// Paramètre
	// -Dorg.lwjgl.util.DebugLoader=true
	// Fonction
	// Configuration.DEBUG_LOADER.set(true)

	// Memory allocator debug mode
	// Paramètre
	// -Dorg.lwjgl.util.DebugAllocator=true
	// Fonction
	// Configuration.DEBUG_MEMORY_ALLOCATOR.set(true)

	// Memory Stack allocator debug mode
	// Paramètre
	// -Dorg.lwjgl.util.DebugStack=true
	// Fonction
	//	Configuration.DEBUG_STACK.set(true);

	/**public static String version()
	{
		return Version.getVersion();
	}
	
	public static int major()
	{
		return Version.VERSION_MAJOR;
	}
	
	public static int minor()
	{
		return Version.VERSION_MINOR;
	}
	
	public static int revision()
	{
		return Version.VERSION_REVISION;
	}
	
	public static BuildType buildType()
	{
		return Version.BUILD_TYPE;
	}
	
	public static String buildTypeName()
	{
		return Version.BUILD_TYPE.name();
	}
	
	public static String buildTypePostFix()
	{
		return Version.BUILD_TYPE.postfix;
	}**/

	public static void enableDebugging()
	{
		Configuration.DISABLE_CHECKS.set(false);
		Configuration.DISABLE_FUNCTION_CHECKS.set(false);
		Configuration.GLFW_CHECK_THREAD0.set(true);
		Configuration.DEBUG.set(true);
		Configuration.DEBUG_FUNCTIONS.set(true);
		Configuration.DEBUG_LOADER.set(true);
		Configuration.DEBUG_MEMORY_ALLOCATOR.set(true);
		Configuration.DEBUG_MEMORY_ALLOCATOR_INTERNAL.set(true);
		Configuration.DEBUG_STREAM.set(true);

		System.out.println("");
		System.out.println("LWJGL Informations : ");
		System.out.println("{");
		System.out.println("	 Debugging is enabled !");
		System.out.println("	 Version " + Version.getVersion());
		System.out.println("}");
		System.out.println("");
	}

	public static void enableDebugStack()
	{
		Configuration.DEBUG_STACK.set(true);
	}

	public static void disableDebugStack()
	{
		Configuration.DEBUG_STACK.set(false);
	}

	public static void disableDebugging()
	{
		Configuration.DISABLE_CHECKS.set(true);
		Configuration.DISABLE_FUNCTION_CHECKS.set(true);
		Configuration.GLFW_CHECK_THREAD0.set(false);
		Configuration.DEBUG.set(false);
		Configuration.DEBUG_FUNCTIONS.set(false);
		Configuration.DEBUG_LOADER.set(false);
		Configuration.DEBUG_MEMORY_ALLOCATOR.set(false);
		Configuration.DEBUG_MEMORY_ALLOCATOR_INTERNAL.set(false);
		Configuration.DEBUG_STACK.set(false);
		Configuration.DEBUG_STREAM.set(false);
	}
}