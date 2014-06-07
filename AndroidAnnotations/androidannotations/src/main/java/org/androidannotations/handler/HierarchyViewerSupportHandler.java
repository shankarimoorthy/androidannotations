/**
 * Copyright (C) 2010-2015 eBusiness Information, Excilys Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.androidannotations.handler;

import com.sun.codemodel.JInvocation;
import org.androidannotations.annotations.HierarchyViewerSupport;
import org.androidannotations.holder.EActivityHolder;
import org.androidannotations.model.AnnotationElements;
import org.androidannotations.process.ElementValidation;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

import static com.sun.codemodel.JExpr._this;

public class HierarchyViewerSupportHandler extends BaseAnnotationHandler<EActivityHolder> {

	public HierarchyViewerSupportHandler(ProcessingEnvironment processingEnvironment) {
		super(HierarchyViewerSupport.class, processingEnvironment);
	}

	@Override
	public void validate(Element element, AnnotationElements validatedElements, ElementValidation validation) {
		validatorHelper.hasEActivity(element, validation);

		validatorHelper.isDebuggable(androidManifest, validation);

		validatorHelper.hasInternetPermission(androidManifest, validation);
	}

	@Override
	public void process(Element element, EActivityHolder holder) throws Exception {
		JInvocation viewServerInvocation = classes().VIEW_SERVER.staticInvoke("get").arg(_this());

		holder.getOnViewChangedBody().invoke(viewServerInvocation, "addWindow").arg(_this());
		holder.getOnDestroyAfterSuperBlock().invoke(viewServerInvocation, "removeWindow").arg(_this());
		holder.getOnResumeAfterSuperBlock().invoke(viewServerInvocation, "setFocusedWindow").arg(_this());
	}
}
