package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.layout.tagging.IAccessibleElement;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupTextNode;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.element.SvgImage;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class for resolving the alternate description of an {@link IAccessibleElement}.
 * The alternate description is resolved in the following order:
 * 1) alt attribute
 * 2) title attribute
 * 3) aria-label attribute
 * 4) aria-labelledby attribute
 * <p>
 * If none of the above attributes are present, the alternate description is not set.
 */
public class AlternateDescriptionResolver {

    private static final List<String> ALTERNATIVE_DESCRIPTION_RESOLUTION_ORDER = Arrays.asList(
            AttributeConstants.ALT,
            AttributeConstants.ARIA_LABEL,
            AttributeConstants.TITLE
    );

    /**
     * Creates a new {@link AlternateDescriptionResolver} instance.
     */
    public AlternateDescriptionResolver() {
        // Empty constructor
    }

    /**
     * Resolves the alternate description of the {@link IAccessibleElement} based on the attributes of the
     * {@link IElementNode}.
     *
     * @param accessibleElement the {@link IAccessibleElement} to which the alternate description should be applied.
     * @param element           the {@link IElementNode} from which the alternate description should be resolved.
     */
    public void resolve(IAccessibleElement accessibleElement, IElementNode element) {
        boolean hasManagedToResolveSpecificImplementation = false;
        if (TagConstants.SVG.equalsIgnoreCase(element.name())) {
            hasManagedToResolveSpecificImplementation = resolveSvg((SvgImage) accessibleElement, element);
        } else if (TagConstants.A.equalsIgnoreCase(element.name())) {
            hasManagedToResolveSpecificImplementation = resolveLink(accessibleElement, element);
        }
        if (hasManagedToResolveSpecificImplementation) {
            return;
        }

        resolveFallback(accessibleElement, element);
    }

    /**
     * Resolves the alternate description of the {@link IAccessibleElement} based on the attributes of the
     * {@link IElementNode}. If the link has an img tag as a child, the alt attribute of the img tag is used as the
     * alternate description.
     *
     * @param accessibleElement the {@link IAccessibleElement} to which the alternate description should be applied.
     * @param element           the {@link IElementNode} from which the alternate description should be resolved.
     * @return {@code true} if the alternate description was resolved, {@code false} otherwise.
     */
    protected boolean resolveLink(IAccessibleElement accessibleElement, IElementNode element) {
        List<INode> children = element.childNodes();
        // If there is an img tag under the link then prefer the alt attribute as a link description.
        if (children.size() == 1
                && children.get(0).childNodes().isEmpty()
                && children.get(0) instanceof JsoupElementNode
                && ((JsoupElementNode) children.get(0)).getAttribute(AttributeConstants.ALT) != null) {
            String result = ((JsoupElementNode) children.get(0)).getAttribute(AttributeConstants.ALT);
            accessibleElement.getAccessibilityProperties().setAlternateDescription(result);
            return true;
        }
        return false;
    }

    /**
     * Resolves the alternate description of the {@link IAccessibleElement} based on the attributes of the
     * {@link IElementNode} in a fallback manner.
     *
     * @param accessibleElement the {@link IAccessibleElement} to which the alternate description should be applied.
     * @param element          the {@link IElementNode} from which the alternate description should be resolved.
     */
    protected void resolveFallback(IAccessibleElement accessibleElement, IElementNode element) {
        for (String s : ALTERNATIVE_DESCRIPTION_RESOLUTION_ORDER) {
            final String alt = element.getAttribute(s);
            if (alt != null && !alt.isEmpty()) {
                accessibleElement.getAccessibilityProperties().setAlternateDescription(alt);
                break;
            }
        }

    }

    /**
     * Resolves the alternate description of the {@link SvgImage} based on the attributes of the {@link IElementNode}.
     * <p>
     * If the alternate description is not found in the attributes, it is searched for the {@code <descr>} tag the
     * child nodes.
     *
     * @param accessibleElement the {@link SvgImage} to which the alternate description should be applied.
     * @param element           the {@link IElementNode} from which the alternate description should be resolved.
     * @return {@code true} if the alternate description was resolved, {@code false} otherwise.
     */
    protected boolean resolveSvg(SvgImage accessibleElement, IElementNode element) {
        for (INode childNode : element.childNodes()) {
            if (!(childNode instanceof IElementNode)) {
                continue;
            }
            IElementNode childElement = (IElementNode) childNode;
            if (SvgConstants.Tags.DESC.equalsIgnoreCase(childElement.name())) {
                if (childElement.childNodes().isEmpty()) {
                    break;
                }
                INode firstChild = childElement.childNodes().get(0);
                if (firstChild instanceof JsoupTextNode) {
                    JsoupTextNode textNode = (JsoupTextNode) firstChild;
                    accessibleElement
                            .getAccessibilityProperties()
                            .setAlternateDescription(textNode.wholeText());
                }
                return true;
            }
        }
        return false;
    }
}
