package org.orienteer.examples;

import org.orienteer.core.OrienteerWebApplication;
import org.orienteer.core.module.PerspectivesModule;

public class MyWebApplication extends OrienteerWebApplication
{
	@Override
	public void init()
	{
		super.init();
		mountPages("org.orienteer.examples.web");
		registerWidgets("org.orienteer.examples.component.widget");
		registerModule(PerformanceDataModel.class);
	}
	
}
