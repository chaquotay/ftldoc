<#---
  -- This ftl macro library provides macros used to display nodes
  -- of the AST (=Abstract Syntax Tree) of a FreeMarker template. The
  -- data displayed by the macro <tt>print</tt> has to be of type
  -- <code>TemplateNodeModel</code>. In most cases one should use
  -- <tt>freemarker.tools.ftldoc.TemplateElementNode</tt>,
  -- which is part of the <tt>ftldoc</tt> distro.
  --
  -- @author Stephan Mueller
  -- @version 0.1
  -->

<#---@begin main macro -->

<#---
  -- The macro <code>print(root)</code> is the entry point for highlighted
  -- syntax output in html. Any wrapped descendant of
  -- <code>freemarker.core.TemplateElement</code> can be root of the display
  -- process. A suitable model that could wrap <code>TemplateElement</code>-s
  -- is <code>org.visigoths.freemarker.tools.ftldoc.TemplateElementNode</code>.
  --
  -- @param root the root (of type node) of the template (fragment) to be displayed
  -->
<#macro print root>
<#visit root>
</#macro>

<#---@end -->
<#---@begin fm4xml-style macros for TemplateElements-s -->

<#---
  -- handler macro for ftl interpolations. This macro doesn't require any arguments
  -- since the FM4XML mechanism provides the current directive as <code>.node</code>
  -- variable.
  --
  -->
<#macro interpolation>
<@nobr>
<span class="interpolation">
${format(.node.start?html)}
</span>
</@nobr>
</#macro>

<#---
  -- handler macro for ftl directives. This macro doesn't require any arguments
  -- since the FM4XML mechanism provides the current directive as <code>.node</code>
  -- variable.
  --
  -->
<#macro directive>
<@nobr>
<span class="directive">
${format(.node.start?html)}
</span>
<#recurse>
<span class="directive">
${format(.node.end?html)}
</span>
</@nobr>
</#macro>

<#---
  -- handler macro for ftl text blocks. This macro doesn't require any arguments
  -- since the FM4XML mechanism provides the current directive as <code>.node</code>
  -- variable.
  --
  -->
<#macro textblock>
<@nobr>
<span class="textblock">
${format(.node.start?html)}
</span>
</@nobr>
</#macro>

<#---
  -- handler macro for ftl user-defined directives. This macro doesn't require any arguments
  -- since the FM4XML mechanism provides the current directive as <code>.node</code>
  -- variable.
  --
  -->
<#macro userdirective>
<@nobr>
<span class="userdirective">
${format(.node.start?html)}
</span>
<#recurse>
<span class="userdirective">
${format(.node.end?html)}
</span>
</@nobr>
</#macro>

<#---
  -- handler macro for ftl comments. This macro doesn't require any arguments
  -- since the FM4XML mechanism provides the current directive as <code>.node</code>
  -- variable.
  --
  -->
<#macro comment>
<@nobr>
<span class="comment">
${format(.node.start?html)}
</span>
</@nobr>
</#macro>

<#---@end -->
<#---@begin helper macros -->

<#function format text>
<#return text?replace(" ","&nbsp;")?replace("\n","<br>")>
</#function>

<#---
  -- Removes disturbing newlines which produces a (unwanted) blank when
  -- rendered by a browser
  --
  -->
<#macro nobr>
<#assign text><#nested></#assign>
${text?replace("\n","")}
</#macro>

<#---@end -->

