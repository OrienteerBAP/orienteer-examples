package org.orienteer.examples.component.widget;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.orienteer.core.component.FAIcon;
import org.orienteer.core.component.FAIconType;
import org.orienteer.core.widget.AbstractWidget;
import org.orienteer.core.widget.Widget;

import com.orientechnologies.orient.core.record.impl.ODocument;

@Widget(domain="schema", tab="hello", id="hello-world", autoEnable=true)
public class HelloWorldWidget extends AbstractWidget<Void> {

	public HelloWorldWidget(String id, IModel<Void> model, IModel<ODocument> widgetDocumentModel) {
		super(id, model, widgetDocumentModel);
	}

	@Override
	protected FAIcon newIcon(String id) {
		return new FAIcon(id, FAIconType.exclamation);
	}

	@Override
	protected IModel<String> getDefaultTitleModel() {
		return Model.of("Hi!");
	}

}
