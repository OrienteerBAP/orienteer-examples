package org.orienteer.examples;

import org.apache.wicket.util.lang.Objects;

import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.hook.ODocumentHookAbstract;
import com.orientechnologies.orient.core.metadata.security.ORole;
import com.orientechnologies.orient.core.metadata.security.ORule;
import com.orientechnologies.orient.core.metadata.security.OSecurityUser;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class PerformanceTestHook extends ODocumentHookAbstract{

	public PerformanceTestHook(ODatabaseDocument database) {
		super(database);
		setIncludeClasses(PerformanceDataModel.PERF_ENTITY_CLASS);
	}
	
	@Override
	public RESULT onRecordBeforeRead(ODocument iDocument) {
		return isAllowed(iDocument) ? RESULT.RECORD_NOT_CHANGED : RESULT.SKIP;
	}
	
	private boolean isAllowed(ODocument doc) {
		OSecurityUser user = database.getUser();
		if (user.isRuleDefined(ORule.ResourceGeneric.BYPASS_RESTRICTED, null) 
				&& user.checkIfAllowed(ORule.ResourceGeneric.BYPASS_RESTRICTED, null, ORole.PERMISSION_READ) != null) {
			return true;
		}
		Object category = user.getDocument().field("restrictToCategory");
		if(category==null) return true;
		else return Objects.equal(doc.field("category"), "Category-"+category);
	}
	
	@Override
	public DISTRIBUTED_EXECUTION_MODE getDistributedExecutionMode() {
		return DISTRIBUTED_EXECUTION_MODE.BOTH;
	}

}
