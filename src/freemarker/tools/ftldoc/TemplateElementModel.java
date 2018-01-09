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
