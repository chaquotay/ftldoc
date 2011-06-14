/*
 * Copyright (c) 2003 The Visigoth Software Society. All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Visigoth Software Society (http://www.visigoths.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. Neither the name "FreeMarker", "Visigoth", nor any of the names of the 
 *    project contributors may be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact visigoths@visigoths.org.
 *
 * 5. Products derived from this software may not be called "FreeMarker" or "Visigoth"
 *    nor may "FreeMarker" or "Visigoth" appear in their names
 *    without prior written permission of the Visigoth Software Society.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE VISIGOTH SOFTWARE SOCIETY OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Visigoth Software Society. For more
 * information on the Visigoth Software Society, please see
 * http://www.visigoths.org/
 */ 

package freemarker.tools.ftldoc;

import freemarker.template.*;
import freemarker.core.Comment;
import freemarker.core.Macro;
import freemarker.core.TextBlock;
import freemarker.core.TemplateElement;

/**
 * An implementation of {@link TemplateNodeModel}.
 * It's able to wrap {@link TemplateElement}-s. 
 *
 * @author Stephan Mueller <stephan at chaquotay dot net>
 * @version $Id: TemplateElementModel.java,v 1.1 2003/08/27 12:49:20 stephanmueller Exp $
 */

public class TemplateElementModel  implements TemplateNodeModel, TemplateHashModel, TemplateSequenceModel {
    
    private TemplateElement templateElement = null;
    private TemplateModel startTag = null;
    private TemplateModel endTag = null;
    
    /**
     * The constructor. Takes the TemplateElement which should be
     * wrapped into a TemplateNodeModel
     *
     * @param te the TemplateElement
     */
    TemplateElementModel(TemplateElement te) {
        templateElement = te;
        calculateStartTag();
        calculateEndTag();
    }
    
    /**
     * Method calculateStartTag
     *
     */
    private void calculateStartTag() {
        // if someone knows a better way to do this, don't hesitate to change it
        String starttag = templateElement.getSource();
        if(templateElement.getChildCount()>0) {
            // starttag = text between start of myTE and start of first child
            TemplateElement firstChild = (TemplateElement)templateElement.getChildAt(0);
            starttag = templateElement.getTemplate().getSource(templateElement.getBeginColumn(),templateElement.getBeginLine(),firstChild.getBeginColumn(), firstChild.getBeginLine());
            // there's a character too much -> remove it
            if(starttag.length()>1) {
                starttag = starttag.substring(0,starttag.length()-1);
            } else {
                starttag = ""; // an empty starttag? really? well, it cannot harm
            }
        }
        startTag = new SimpleScalar(starttag);
    }
    
    /**
     * Method calculateEndTag
     *
     */
    private void calculateEndTag() {
        // if someone knows a better way to do this, don't hesitate to change it
        String endtag = "";
        TemplateElement lastChild = null;
        try {
            int count = templateElement.getChildCount();
            if(count>0) {
                // endtag = test between end of last child and end of myTE
                lastChild = (TemplateElement)(templateElement.getChildAt(count-1));
                //System.err.println(lastChild.getDescription());
                //System.err.println("pos:"  + lastChild.getEndLine() + "," + lastChild.getEndColumn() + "-" + myTE.getEndLine() + "," + myTE.getEndColumn());
                //System.err.println(myTE.getTemplate().getSource(myTE.getBeginColumn(),myTE.getBeginLine(),myTE.getEndColumn(),myTE.getEndLine()));
                
                endtag = templateElement.getTemplate().getSource(lastChild.getEndColumn(),lastChild.getEndLine(),templateElement.getEndColumn(),templateElement.getEndLine());
                
                String test = templateElement.getTemplate().getSource(templateElement.getBeginColumn(),templateElement.getBeginLine(),templateElement.getEndColumn(),templateElement.getEndLine());
                
                // there's a character too much -> remove it
                endtag = endtag.substring(1);
            }
        } catch (Exception e) { }
        endTag = new SimpleScalar(endtag);
    }
    
    
    /**
     * @return   a boolean
     *
     * @throws   TemplateModelException
     *
     */
    public boolean isEmpty() throws TemplateModelException
    {
        return false;
    }
    
    
    /**
     * @param    p0                  a  String
     *
     * @return   a TemplateModel
     *
     * @throws   TemplateModelException
     *
     */
    public TemplateModel get(String p0) throws TemplateModelException
    {
        // we only have start and end tag
        if(p0.equals("end")) {
            return endTag;
        } else if (p0.equals("start")) {
            return startTag;
        } else {
            return TemplateModel.NOTHING;
        }
    }
    
    /**
     * @param    p0                  an int
     *
     * @return   a TemplateModel
     *
     * @throws   TemplateModelException
     *
     */
    public TemplateModel get(int p0) throws TemplateModelException
    {
        return new TemplateElementModel((TemplateElement)templateElement.getChildAt(p0));
    }
    
    /**
     * @return   an int
     *
     * @throws   TemplateModelException
     *
     */
    public int size() throws TemplateModelException
    {
        return templateElement.getChildCount();
    }
    
    /**
     * return   a TemplateNodeModel
     *
     * @throws   TemplateModelException
     *
     */
    public TemplateNodeModel getParentNode() throws TemplateModelException
    {
        return new TemplateElementModel((TemplateElement)templateElement.getParent());
    }
    
    /**
     * @return   a String
     *
     * @throws   TemplateModelException
     *
     */
    public String getNodeName() throws TemplateModelException
    {
        // ATM we have to do it String-based, since all subclasses of
        // TemplateElement (in freemarker.core) are not visible outside
        // their package, but this will change (at least I hope so)
        String name = null;
        TemplateElement anElement = templateElement;
        if (anElement.getClass().getName().equals("freemarker.core.DollarVariable") ||
            anElement.getClass().getName().equals("freemarker.core.NumericalOutput")) {
            name = "interpolation";
        } else if (
            anElement instanceof Comment) {
            name = "comment";
        } else if (
            anElement instanceof Macro) {
            name = "directive";
        } else if (
            anElement instanceof TextBlock) {
            name = "textblock";
        } else if (
            anElement.getClass().getName().equals("freemarker.core.UnifiedCall")) {
            name = "userdirective";
        } else {
            name = "directive";
        }
        return name;
    }
    
    /**
     * @return   a String
     *
     * @throws   TemplateModelException
     *
     */
    public String getNodeType() throws TemplateModelException
    {
        return templateElement.getClass().getName();
    }
    
    /**
     * Returns the node's namespace. Since we do <em>not</em> use namespaces
     * at all, we just return <tt>null</tt>
     * 
     * @return   null
     *
     * @throws   TemplateModelException
     *
     */
    public String getNodeNamespace() throws TemplateModelException
    {
        return null;
    }
    
    /**
     * @return   a TemplateSequenceModel
     *
     * @throws   TemplateModelException
     *
     */
    public TemplateSequenceModel getChildNodes() throws TemplateModelException
    {
        // Oops, we are parent and children at the same time
        return this;
    }
    
}
