/*
 * Copyright 2005 Red Hat, Inc. and/or its affiliates.
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

package org.drools.eclipse.flow.ruleflow.view.property.constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.drools.compiler.lang.descr.GlobalDescr;
import org.drools.eclipse.DroolsEclipsePlugin;
import org.drools.eclipse.editors.completion.CompletionUtil;
import org.drools.eclipse.editors.completion.DefaultCompletionProcessor;
import org.drools.eclipse.editors.completion.RuleCompletionProcessor;
import org.drools.eclipse.editors.completion.RuleCompletionProposal;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Completion for ruleflow constraints. 
 */
public class ImportCompletionProcessor extends DefaultCompletionProcessor {

    public ImportCompletionProcessor() {
        super(null);
    }

    public IEditorPart getEditor() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench != null) {
            IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
            if (workbenchWindow != null) {
                IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
                if (workbenchPage != null) {
                    return workbenchPage.getActiveEditor();
                }
            }
        }
        return null;
    }

    protected List<ICompletionProposal> getCompletionProposals(ITextViewer viewer,
            int documentOffset) {
        try {
            IDocument doc = viewer.getDocument();
            String backText = readBackwards(documentOffset, doc);

            String prefix = CompletionUtil.stripLastWord(backText);

            List<ICompletionProposal> props = null;
            Matcher matcher = IMPORT_PATTERN.matcher(backText);
            if (matcher.matches()) {
                String classNameStart = backText.substring(backText
                        .lastIndexOf("import") + 7);
                props = getAllClassProposals(classNameStart, documentOffset,
                        prefix);
            } else {
                props = getPossibleProposals(viewer, documentOffset, backText, prefix);
            }
            return props;
        } catch (Throwable t) {
            DroolsEclipsePlugin.log(t);
        }
        return null;
    }

    
    public List<String> getImports() {
        return Collections.emptyList();
    }
    
    public List<GlobalDescr> getGlobals() {
        return Collections.emptyList();
    }
    
    protected IJavaProject getCurrentJavaProject() {
        IEditorPart editor = getEditor();
        if (editor != null && editor.getEditorInput() instanceof IFileEditorInput) {
            IFile file = ((IFileEditorInput) editor.getEditorInput()).getFile();
            try {
                if (file.getProject().getNature("org.eclipse.jdt.core.javanature") != null) {
                    return JavaCore.create(file.getProject());
                }
            } catch (CoreException e) {
                // do nothing
            }
        }
        return null;
    }
    
    protected List<ICompletionProposal> getPossibleProposals(ITextViewer viewer,
            int documentOffset,
            String backText,
            final String prefix) {
        List<ICompletionProposal> list = new ArrayList<ICompletionProposal>();
        list.add(new RuleCompletionProposal(documentOffset - prefix.length(), prefix.length(), "import", "import "));
        DefaultCompletionProcessor.filterProposalsOnPrefix(prefix, list);
        return list;
    }

    /**
     * @return a list of "MVELified" RuleCompletionProposal. That list contains only unique proposal based on
     * the overrriden equals in {@link RuleCompletionProposal} to avoid the situation when several
     * accessors can exist for one property. for that case we want to keep only one proposal.
     */
    protected Collection<ICompletionProposal> getJavaMvelCompletionProposals(final int documentOffset,
                                                     final String javaText,
                                                     final String prefix,
                                                     Map<String, String> params) {
                                                        final List<ICompletionProposal> list = new ArrayList<ICompletionProposal>();
                                                        requestJavaCompletionProposals( javaText,
                                                                                        prefix,
                                                                                        documentOffset,
                                                                                        params,
                                                                                        list );
                                                    
                                                        Collection<ICompletionProposal> mvelList = RuleCompletionProcessor.mvelifyProposals( list, false );
                                                        return mvelList;
                                                    }
}
