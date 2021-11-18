/*
 * Copyright (c) 2021, zuoyu (zuoyuip@foxmil.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.zuoyu.mybatis.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.lang.NonNull;

import top.zuoyu.mybatis.exception.JsonException;

/**
 * 转换工具 .
 *
 * @author: zuoyu
 * @create: 2021-11-04 10:00
 */
class Json {

    private static final int BIG_LENGTH = 65535;


    static double checkDouble(double d) throws JsonException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new JsonException("Forbidden numeric value: " + d);
        }
        return d;
    }

    static Boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof String) {
            String stringValue = (String) value;
            if (Boolean.TRUE.toString().equalsIgnoreCase(stringValue)) {
                return true;
            }
            if (Boolean.FALSE.toString().equalsIgnoreCase(stringValue)) {
                return false;
            }
        }
        return null;
    }

    static Byte toByte(Object value) {
        if (value instanceof Byte) {
            return (Byte) value;
        }
        if (value instanceof Number) {
            return ((Number) value).byteValue();
        }
        if (value instanceof String) {
            try {
                return (byte) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    static Short toShort(Object value) {
        if (value instanceof Short) {
            return (Short) value;
        }
        if (value instanceof Number) {
            return ((Number) value).shortValue();
        }
        if (value instanceof String) {
            try {
                return (short) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    static Integer toInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return (int) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    static Long toLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String) {
            try {
                return (long) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    static Float toFloat(Object value) {
        if (value instanceof Float) {
            return (Float) value;
        }
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        if (value instanceof String) {
            try {
                return (float) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    static Double toDouble(Object value) {
        if (value instanceof Double) {
            return (Double) value;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            try {
                return Double.valueOf((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }


    static Character toCharacter(Object value) {
        if (value instanceof Character) {
            return (Character) value;
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() < 1) {
                throw new JsonException("can not cast to char, value : " + value);
            }
            return strVal.charAt(0);
        }
        return null;
    }

    static String toString(@NonNull Object value) {
        if (value instanceof String) {
            return (String) value;
        }
        return String.valueOf(value);
    }

    static BigDecimal toBigDecimal(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Float) {
            if (Float.isNaN((Float) value) || Float.isInfinite((Float) value)) {
                return null;
            }
        } else if (value instanceof Double) {
            if (Double.isNaN((Double) value) || Double.isInfinite((Double) value)) {
                return null;
            }
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }

        String strVal = value.toString();

        if (strVal.length() == 0
                || "null".equalsIgnoreCase(strVal)) {
            return null;
        }

        if (strVal.length() > BIG_LENGTH) {
            throw new JsonException("decimal overflow");
        }
        return new BigDecimal(strVal);
    }

    static BigInteger toBigInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Float) {
            if (Float.isNaN((Float) value) || Float.isInfinite((Float) value)) {
                return null;
            }
            return BigInteger.valueOf(((Float) value).longValue());
        } else if (value instanceof Double) {
            if (Double.isNaN((Double) value) || Double.isInfinite((Double) value)) {
                return null;
            }
            return BigInteger.valueOf(((Double) value).longValue());
        } else if (value instanceof BigInteger) {
            return (BigInteger) value;
        } else if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            if (scale > -1000 && scale < 1000) {
                return ((BigDecimal) value).toBigInteger();
            }
        }

        String strVal = value.toString();

        if (strVal.length() == 0
                || "null".equalsIgnoreCase(strVal)) {
            return null;
        }

        if (strVal.length() > BIG_LENGTH) {
            throw new JsonException("decimal overflow");
        }
        return new BigInteger(strVal);
    }

    static Date toDate(Object value) {
        return (Date) value;
    }


    public static JsonException typeMismatch(Object indexOrName, Object actual, String requiredType)
            throws JsonException {
        if (actual == null) {
            throw new JsonException("Value at " + indexOrName + " is null.");
        }
        throw new JsonException("Value " + actual + " at " + indexOrName + " of type " + actual.getClass().getName()
                + " cannot be converted to " + requiredType);
    }

    public static JsonException typeMismatch(Object actual, String requiredType) throws JsonException {
        if (actual == null) {
            throw new JsonException("Value is null.");
        }
        throw new JsonException("Value " + actual + " of type " + actual.getClass().getName()
                + " cannot be converted to " + requiredType);
    }

}
