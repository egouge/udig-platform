/*
 *    uDig - User Friendly Desktop Internet GIS client
 *    http://udig.refractions.net
 *    (C) 2012, Refractions Research Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * (http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD
 * License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).
 */
package org.locationtech.udig.project.ui.wizard.export.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.draw2d.geometry.Insets;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

/**
 * @author Andrea Antonello - www.hydrologis.com
 * @author Frank Gasdorf
 */
public class Image2Pdf {

    /**
     * writes a buffered image to pdf at a given resolution
     *
     * @param image the image to write
     * @param pdfPath the path to the pdf document to create
     * @param paper the paper type
     * @param marginBorder margins for left, right, top and bottom
     * @param lanscape true if the document should be in landscape mode
     */
    public static void write(BufferedImage image, String pdfPath, Paper paper,
            Insets marginBorder, boolean landscape) {
        Rectangle documentPageSize = calculateSize(landscape, paper, null);
        Rectangle imageSizeInPixel = calculateSize(landscape, paper, marginBorder);

        float imgHeightInPixel = imageSizeInPixel.getHeight();
        float imgWidthInPixel = imageSizeInPixel.getWidth();

        
        
        try(PdfDocument doc = new PdfDocument(new PdfWriter(new FileOutputStream(pdfPath)))) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos); //$NON-NLS-1$
            
            
            ImageData iTextImage = ImageDataFactory.create(baos.toByteArray());


            doc.addNewPage(new PageSize(documentPageSize)); // not needed for page 1, needed for >1

            // high in itext is measured from lower left
            int absoluteX = (marginBorder != null ? marginBorder.left : 0);
            int absoluteY = (marginBorder != null ? marginBorder.bottom : 0);

            Image pdfImg = new Image(iTextImage).setFixedPosition(absoluteX, absoluteY)
            		.scaleToFit(imgWidthInPixel, imgHeightInPixel);
            
            try(Document temp = new Document(doc)){
            	temp.add(pdfImg);
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

    }

    /**
     * @param landscape if the image is in landscape format
     * @param paper paper definition
     * @param marginBorder margins for left, right, top and bottom
     * @return final image size to render
     */
    private static Rectangle calculateSize(boolean landscape, Paper paper, Insets marginBorder) {
        Rectangle rectangle = null;
        switch (paper) {
        case LETTER:
            rectangle = PageSize.LETTER;
            break;
        case LEGAL:
            rectangle = PageSize.LEGAL;
            break;
        case A4:
            rectangle = PageSize.A4;
            break;
        case A3:
            rectangle = PageSize.A3;
            break;
        case A2:
            rectangle = PageSize.A2;
            break;
        case A1:
            rectangle = PageSize.A1;
            break;
        case A0:
            rectangle = PageSize.A0;
            break;
        default:
            System.err.println("Cannot handle Paper to PageSize"); //$NON-NLS-1$
        }

        if (landscape) {
            rectangle = new Rectangle(rectangle.getHeight(), rectangle.getWidth());
        }

        // apply margins for the border
        int marginWidth = (marginBorder != null ? marginBorder.left + marginBorder.right : 0);
        int marginHeigth = (marginBorder != null ? marginBorder.top + marginBorder.bottom : 0);
        return new Rectangle(rectangle.getWidth() - marginWidth,
                rectangle.getHeight() - marginHeigth);
    }

    public static void main(String[] args) {

        String path = "/home/moovida/Desktop/screens/austrocontrol_2.png"; //$NON-NLS-1$
        BufferedImage b = null;
        try {
            b = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        write(b, path + ".pdf", Paper.A1, new Insets(10, 10, 10, 10), false); //$NON-NLS-1$
        System.out.println("finished"); //$NON-NLS-1$
    }

}
