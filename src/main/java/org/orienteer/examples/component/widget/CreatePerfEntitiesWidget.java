package org.orienteer.examples.component.widget;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.orienteer.core.component.BootstrapType;
import org.orienteer.core.component.FAIcon;
import org.orienteer.core.component.FAIconType;
import org.orienteer.core.component.command.AjaxFormCommand;
import org.orienteer.core.component.widget.AbstractHtmlJsPaneWidget;
import org.orienteer.core.widget.AbstractWidget;
import org.orienteer.core.widget.Widget;

import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.intent.OIntent;
import com.orientechnologies.orient.core.intent.OIntentMassiveInsert;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

import ru.ydn.wicket.wicketorientdb.utils.DBClosure;

@Widget(id="create-perf-entities", domain="browse", selector=CreatePerfEntitiesWidget.OCLASS_NAME, order=-10, autoEnable=true, tab="create")
public class CreatePerfEntitiesWidget extends AbstractWidget<OClass> {
	
	public static final String OCLASS_NAME = "PerfEntity";
	
	private TextField<Integer> number;
	private TextField<Integer> maxCategory;
	
	public CreatePerfEntitiesWidget(String id, IModel<OClass> model, IModel<ODocument> widgetDocumentModel) {
		super(id, model, widgetDocumentModel);
		Form<Object> form = new Form<>("form");
		number = new TextField<Integer>("number", Model.of(1000), Integer.class);
		number.setRequired(true).add(new RangeValidator<Integer>(1, null));
		maxCategory = new TextField<Integer>("maxCategory", Model.of(400), Integer.class);
		maxCategory.setRequired(true).add(new RangeValidator<Integer>(1, 1000));
		form.add(number, maxCategory);
		form.add(new AjaxFormCommand<Object>("create", "command.create"){
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				long elapsed = createEntities()/1000;
				target.appendJavaScript("alert('Elapsed "+elapsed+"secs')");
			}
		}.setBootstrapType(BootstrapType.PRIMARY));
		
		add(form);
	}
	
	private long createEntities() {
		final int num = number.getModelObject();
		final int maxCat = maxCategory.getModelObject();
		return new DBClosure<Long>() {

			@Override
			protected Long execute(ODatabaseDocument db) {
				long start = System.currentTimeMillis();
				db.declareIntent(new OIntentMassiveInsert());
				for(int i=0; i<num; i++) {
					ODocument doc = new ODocument(OCLASS_NAME);
					doc.field("name", RandomStringUtils.randomAlphabetic(10));
					doc.field("category", "Category-"+RandomUtils.nextInt(0, maxCat));
					for(int j=0; j<10;j++) {
						doc.field("random"+j, RandomStringUtils.randomAlphanumeric(10));
					}
					doc.save();
					if(i%1000==0) {
						db.commit();
						db.begin();
					}
				}
				db.commit();
				db.declareIntent(null);
				return System.currentTimeMillis() - start;
			}
		}.execute();
	}

	@Override
	protected FAIcon newIcon(String id) {
		return new FAIcon(id, FAIconType.list);
	}

	@Override
	protected IModel<String> getDefaultTitleModel() {
		return new ResourceModel("widget.createperfentities");
	}

}
