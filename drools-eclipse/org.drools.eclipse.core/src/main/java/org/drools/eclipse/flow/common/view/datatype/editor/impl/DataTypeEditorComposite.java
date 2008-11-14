package org.drools.eclipse.flow.common.view.datatype.editor.impl;
/*
 * Copyright 2005 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.drools.eclipse.DroolsEclipsePlugin;
import org.drools.eclipse.flow.common.datatype.DataTypeRegistry;
import org.drools.eclipse.flow.common.view.datatype.editor.DataTypeEditor;
import org.drools.process.core.datatype.DataType;
import org.drools.process.core.datatype.impl.type.UndefinedDataType;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

 * Default editor for a datatype. 
 * 
 * @author <a href="mailto:kris_verlaenen@hotmail.com">Kris Verlaenen</a>
 */
public class DataTypeEditorComposite extends Composite implements DataTypeEditor.DataTypeListener {
    private DataTypeRegistry registry;
    public DataTypeEditorComposite(Composite parent, int style, DataTypeRegistry registry) {
    public void setDataType(DataType dataType) {
            	// "Could not find data type info for type " + dataType.getClass()
            } catch (NoSuchMethodException e) {
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e) {
            }
   public void reset() {
        	DataTypeEditor.DataTypeListener listener = (DataTypeEditor.DataTypeListener) it.next();