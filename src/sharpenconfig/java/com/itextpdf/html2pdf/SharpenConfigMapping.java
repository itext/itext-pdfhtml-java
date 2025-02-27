/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.html2pdf;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import sharpen.config.MappingConfiguration;
import sharpen.config.MappingConfigurator;
import sharpen.config.ModuleOption;
import sharpen.config.ModulesConfigurator;
import sharpen.config.OptionsConfigurator;

public class SharpenConfigMapping implements MappingConfiguration {

    @Override
    public int getMappingPriority() {
        return 2;
    }

    @Override
    public String getModuleName() {
        return "html2pdf";
    }

    @Override
    public void applyMappingConfiguration(MappingConfigurator configurator) {
        configurator.mapNamespace("html2pdf.exception", "Html2pdf.Exceptions");

        configurator.mapStringLiteral("com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactoryTest.cannotGetTagWorkerForCustomTagViaReflection.className", "iText.Html2pdf.Attach.Impl.TestClass");
        configurator.mapStringLiteral("com.itextpdf.html2pdf.css.apply.impl.DefaultCssApplierFactoryTest.cannotGetCssApplierForCustomTagViaReflection.className", "iText.Html2pdf.Css.Apply.Impl.TestClass");

        configurator.addFullName("iText.Html2pdf.Html.Node.IElement");

        configurator.mapStringLiteral("com.itextpdf.html2pdf.css.CssStyleSheetParserTest.DEFAULT_CSS_PATH", "iText.Html2Pdf.default.css");
        configurator.mapStringLiteral("com.itextpdf.html2pdf.css.resolve.UserAgentCss.DEFAULT_CSS_PATH", "iText.Html2Pdf.default.css");
        configurator.mapStringLiteral("com.itextpdf.html2pdf.resolver.font.DefaultFontProvider.SHIPPED_FONT_RESOURCE_PATH", "iText.Html2Pdf.font.");
        configurator.mapStringLiteral("com.itextpdf.html2pdf.resolver.font.DefaultFontProvider.addCalligraphFonts.methodName", "LoadShippedFonts");

        configurator.mapFunctionalInterfaceToDelegate("com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerMapping$ITagWorkerCreator");
        configurator.mapFunctionalInterfaceToDelegate("com.itextpdf.html2pdf.css.apply.impl.DefaultTagCssApplierMapping$ICssApplierCreator");
    }

    @Override
    public void applySharpenOptions(OptionsConfigurator configurator) {

    }

    @Override
    public void applyConfigModuleSettings(ModulesConfigurator configurator) {

    }

    @Override
    public Collection<ModuleOption> getAvailableModuleSettings() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Collection<String> getDependencies() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<String> getIgnoredSourceFiles() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<String> getIgnoredResources() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<SimpleImmutableEntry<String, String>> getOverwrittenResources() {
        return Collections.EMPTY_LIST;
    }
}