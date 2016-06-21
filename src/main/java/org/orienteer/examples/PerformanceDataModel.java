package org.orienteer.examples;

import org.orienteer.core.CustomAttributes;
import org.orienteer.core.OrienteerWebApplication;
import org.orienteer.core.module.AbstractOrienteerModule;
import org.orienteer.core.util.OSchemaHelper;

import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.metadata.schema.OClass.INDEX_TYPE;
import com.orientechnologies.orient.core.metadata.schema.OType;

public class PerformanceDataModel extends AbstractOrienteerModule{
	
	public static final String PERF_ENTITY_CLASS = "PerfEntity";

	protected PerformanceDataModel() {
		super("examples-performance", 2);
	}
	
	@Override
	public ODocument onInstall(OrienteerWebApplication app, ODatabaseDocument db) {
		super.onInstall(app, db);
		OSchemaHelper helper = OSchemaHelper.bind(db);
		helper.oClass(PERF_ENTITY_CLASS)
				.oProperty("name", OType.STRING, 10)
				.oProperty("category", OType.STRING, 20).oIndex(INDEX_TYPE.NOTUNIQUE)
				.oProperty("random0", OType.STRING, 30)
				.oProperty("random1", OType.STRING, 31)
				.oProperty("random2", OType.STRING, 32)
				.oProperty("random3", OType.STRING, 33)
				.oProperty("random4", OType.STRING, 34)
				.oProperty("random5", OType.STRING, 35)
				.oProperty("random6", OType.STRING, 36)
				.oProperty("random7", OType.STRING, 37)
				.oProperty("random8", OType.STRING, 38)
				.oProperty("random9", OType.STRING, 39);
		helper.oClass("OUser")
				.oProperty("restrictToCategory", OType.INTEGER, 20);
		return null;
	}
	
	@Override
	public void onUpdate(OrienteerWebApplication app, ODatabaseDocument db, int oldVersion, int newVersion) {
		super.onUpdate(app, db, oldVersion, newVersion);
		onInstall(app, db);
	}
	
	@Override
	public void onInitialize(OrienteerWebApplication app, ODatabaseDocument db) {
		super.onInitialize(app, db);
		app.getOrientDbSettings().getORecordHooks().add(PerformanceTestHook.class);
	}
	
	@Override
	public void onDestroy(OrienteerWebApplication app, ODatabaseDocument db) {
		super.onDestroy(app, db);
		app.getOrientDbSettings().getORecordHooks().remove(PerformanceTestHook.class);
	}
	
}
