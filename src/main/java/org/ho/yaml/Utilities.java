/*
 * Copyright (c) 2007, Yu Cheung Ho
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 *
 *    * Redistributions of source code must retain the above copyright notice, this list of 
 *        conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright notice, this list 
 *        of conditions and the following disclaimer in the documentation and/or other materials 
 *        provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF 
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ho.yaml;

import org.ho.yaml.exception.YamlException;

public class Utilities {

    public static Object decodeSimpleType(String content) {
        if ("~".equals(content)) {
            return null;
        } else {
            try {
                return new Integer(content); // return integer
            } catch (NumberFormatException ignored) {
            }

            try {
                return new Double(content);
            } catch (NumberFormatException | NullPointerException ignored) {
            }
            // UMM... this surprised me, but the double parser can throw NullPointerExceptions
            // on non-null input, this this is buggy

            if ("true".equalsIgnoreCase(content) || "false".equalsIgnoreCase(content))
                return Boolean.valueOf(content); // return boolean
            else return content; // return String
        }
    }

    public static String quote(Object value) {
        return "\"" + value + "\"";
    }

    public static String stringify(Object value) {
        return stringify(value, "");
    }

    public static String escape(String text) {
        text = text.replace("\\", "\\\\");
        text = text.replace("\b", "\\b");
        text = text.replace("\0", "\\0");
        text = text.replace("\t", "\\t");
        text = text.replace("\"", "\\\"");
        return text;
    }

    public static String unescape(String text) {
        if (text == null) return null;
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\\' && i != text.length() - 1) {
                char d = text.charAt(i + 1);
                switch (d) {
                    case 'b':
                        sb.append('\b');
                        break;
                    case '0':
                        sb.append('\0');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case 'n':
                        sb.append('\n');
                        break;
                    case '"':
                        sb.append('"');
                        break;
                    case '\\':
                        sb.append('\\');
                        break;
                    default:
                        sb.append("").append(c).append(d);
                }
                i++;
            } else
                sb.append(c);
        }
        return sb.toString();
    }

    public static String stringify(Object value, String indent) {
        String text = value.toString();

        // special handling for multiple lines
        if (text.indexOf('\n') != -1) {
            if (text.length() == 1)
                return quote("\\n");
            StringBuilder sb = new StringBuilder();
            sb.append("|");
            String lines[] = text.split("\n");
            for (String line : lines) {
                sb.append("\n").append(indent).append(line);
            }
            if (text.charAt(text.length() - 1) == '\n')
                sb.append("\n").append(indent);
            return sb.toString();
        } else if ("".equals(text)) {
            return quote(text);
        } else {
            String indicators = ":[]{},\"'|*&";
            boolean quoteIt = false;
            for (char c : indicators.toCharArray())
                if (text.indexOf(c) != -1) {
                    quoteIt = true;
                    break;
                }
            if (text.trim().length() != text.length())
                quoteIt = true;
            if (isNumeric(text))
                quoteIt = true;
            if (quoteIt) {
                text = escape(text);
                text = quote(text);
            }
            return text;
        }
    }

    // TODO: to be replaced with StringUtils
    static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (Exception ignored) {
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    static String quote(String value) {
        return "\"" + value + "\"";
    }

    public static Object convertType(String value, Class type) {
        if ("~".equals(value))
            return null;
        else if (type == Integer.class || type == Integer.TYPE)
            return new Integer(value);
        else if (type == String.class) {
            return value;
        } else if (type == Long.class || type == Long.TYPE)
            return new Long(value);
        else if (type == Short.class || type == Short.TYPE)
            return new Short(value);
        else if (type == Double.class || type == Double.TYPE)
            return new Double(value);
        else if (type == Boolean.class || type == Boolean.TYPE)
            return Boolean.valueOf(value);
        else if (type == Character.class || type == Character.TYPE) {
            return value.charAt(0);
        } else
            return decodeSimpleType(value);
    }

    public static Class getWrapperClass(Class type) {
        if (Integer.TYPE == type)
            return Integer.class;
        else if (Double.TYPE == type)
            return Double.class;
        else if (Float.TYPE == type)
            return Float.class;
        else if (Boolean.TYPE == type)
            return Boolean.class;
        else if (Character.TYPE == type)
            return Character.class;
        else if (Byte.TYPE == type)
            return Byte.class;
        else if (Long.TYPE == type)
            return Long.class;
        else if (Short.TYPE == type)
            return Short.class;
        else if (Character.TYPE == type)
            return Character.class;
        else
            throw new YamlException(type + " is not a primitive type.");
    }

    public static boolean classEquals(Class one, Class other) {
        if (one == other)
            return true;
        if (one != null && other != null)
            if (one.isPrimitive() || other.isPrimitive())
                if (one.isPrimitive()) {
                    return getWrapperClass(one) == other;
                } else
                    return one == getWrapperClass(other);
        return false;

    }

    public static boolean same(Object one, Object other) {
        return one == null || one.equals(other);
    }


}
