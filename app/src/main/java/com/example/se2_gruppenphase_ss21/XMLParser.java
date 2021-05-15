package com.example.se2_gruppenphase_ss21;

import androidx.annotation.IntDef;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class XMLParser {
    public static Boolean[][] parsexml(String value, String cardnumber, InputStream is){
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            Boolean[][] result = processParsing(parser, value);
            return result;

        } catch (XmlPullParserException e) {


        } catch (IOException e) {

        }
    return null;
    }
    protected static Boolean[][] processParsing(XmlPullParser parser, String value) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        boolean card = false;
        boolean dicetype = false;
        Boolean[][] map = new Boolean[5][5];


        while(eventType != XmlPullParser.END_DOCUMENT){


            String eltName = null;
            switch(eventType){
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if("two".equals(eltName)) {
                        System.out.println(eltName);
                        card = true;
                        eventType = parser.next();
                        eltName = parser.getName();
                    }

                    if("firstr".equals(eltName)&&card) {
                        card = false;

                        for(int j =0; j<5; j++) {

                            for (int i = 0; i < 5; i++) {
                                if ("0".equals(parser.getAttributeValue(i))) {
                                    map[j][i]=false;
                                }
                                else {
                                    map[j][i]=true;
                                }
                            }
                            eventType = parser.next();
                        }
                        return map;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return null;

    }


}
