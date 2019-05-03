package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.svg.processors.impl.SvgConverterProperties;

public class ContextMappingHelper {

    public static SvgConverterProperties mapToSvgConverterProperties(ProcessorContext context){
        SvgConverterProperties svgConverterProperties = new SvgConverterProperties();
        svgConverterProperties.setFontProvider(context.getFontProvider())
                              .setBaseUri(context.getBaseUri())
                              .setMediaDeviceDescription(context.getDeviceDescription());
        return svgConverterProperties;
    }
}
