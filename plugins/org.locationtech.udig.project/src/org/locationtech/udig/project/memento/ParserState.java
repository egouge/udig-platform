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
package org.locationtech.udig.project.memento;

import static org.locationtech.udig.project.memento.Tokens.__null__;
import static org.locationtech.udig.project.memento.Tokens._children_;
import static org.locationtech.udig.project.memento.Tokens._data_;
import static org.locationtech.udig.project.memento.Tokens._memento_;
import static org.locationtech.udig.project.memento.Tokens._none_;
import static org.locationtech.udig.project.memento.Tokens._text_;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum ParserState {
    OUT(_none_) {
        @Override
        void parse( UdigMemento memento, BufferedReader reader, String context ) throws IOException {
            String line = removeWhite(reader.readLine());
            
            if( line.equals(_memento_+"{") ){ //$NON-NLS-1$
                MEMENTO.parse(memento, reader, null);
            }
        }
    },
    MEMENTO(_memento_) {
        @Override
        void parse( UdigMemento memento, BufferedReader reader, String context ) throws IOException {
            String line = removeWhite(reader.readLine());
            while( !line.equals("}") ){ //$NON-NLS-1$
                for( ParserState state : ParserState.values() ) {
                    if( line.startsWith(state.token.name()) ){
                        state.parse(memento,reader, null);
                        break;
                    }

                }
                line = removeWhite(reader.readLine());
            }
        }

    },
    DATA(_data_) {
        final Pattern PATTERN = Pattern.compile("\\s*\\|(.*)\\|.*\\{");  //$NON-NLS-1$
        @Override
        void parse( UdigMemento memento, BufferedReader reader, String context ) throws IOException {
            String line = reader.readLine();
            while( !removeWhite(line).equals("}") ){ //$NON-NLS-1$
                Matcher matcher = PATTERN.matcher(line);
                if(matcher.find()){
                    String key = checkNullToken(matcher.group(1));
                    String value = checkNullToken(parseValue(reader));
                    memento.putString(key,value);
                }
                line = reader.readLine();
            }
        }
    },
    CHILDREN(_children_) {
        @Override
        void parse( UdigMemento memento, BufferedReader reader, String context ) throws IOException {
            String line = removeWhite(reader.readLine());
            while( !line.equals("}") ){ //$NON-NLS-1$
                TYPE.parse(memento, reader, line);

                line = removeWhite(reader.readLine());
            }
        }
    },
    TEXT(_text_) {
        @Override
        void parse( UdigMemento memento, BufferedReader reader, String context ) throws IOException {
            memento.putTextData(parseValue(reader));
        }
    },
    TYPE(_none_) {
        @Override
        void parse( UdigMemento memento, BufferedReader reader, String context ) throws IOException {
            String typeName =checkNullToken(context.substring(0, context.indexOf("{"))); //$NON-NLS-1$
            String line = removeWhite(reader.readLine());
            while( !line.equals("}") ){ //$NON-NLS-1$
                if(line.equals(_memento_+"{")){ //$NON-NLS-1$
                    UdigMemento newMem = memento.createChild(typeName);
                    MEMENTO.parse(newMem, reader, null);
                }
                line = removeWhite(reader.readLine());
            }
        }
    };
    
    private Tokens token;

    private ParserState(Tokens token){
        this.token = token;
    }

    protected String parseValue( BufferedReader reader ) throws IOException {
        String line = reader.readLine();
        StringBuilder val = new StringBuilder();
        boolean isFirst = true;
        while( !removeWhite(line).equals("}") ){ //$NON-NLS-1$
            if(isFirst){
                isFirst=false;
            }else{
                val.append("\n"); //$NON-NLS-1$
            }
            val.append(line);
            line = reader.readLine();
        }

        return val.toString();
    }

    abstract void parse( UdigMemento memento, BufferedReader reader, String context ) throws IOException;
    
    private static String removeWhite( String line ) {
        if( line==null ){
            return ""; //$NON-NLS-1$
        }
        return line.replaceAll("\\s*",""); //$NON-NLS-1$ //$NON-NLS-2$
    }
    private static String checkNullToken( String value ) {
        if( value.equals(__null__.name()) ){
            value=null;
        }
        return value;
    }

}
