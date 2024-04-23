package com.stardevllc.starlib;

public enum Operator {
    ADD("+") {
        public Number calculate(Number number1, Number number2) {
            if (number1 instanceof Integer && number2 instanceof Integer) {
                return number1.intValue() + number2.intValue();
            } else if (number1 instanceof Integer && number2 instanceof Double) {
                return number1.intValue() + number2.doubleValue();
            } else if (number1 instanceof Double && number2 instanceof Integer) {
                return number1.doubleValue() + number2.intValue();
            } else if (number1 instanceof Double && number2 instanceof Double) {
                return number1.doubleValue() + number2.doubleValue();
            }

            return 0;
        }
    }, SUBTRACT("-") {
        public Number calculate(Number number1, Number number2) {
            if (number1 instanceof Integer && number2 instanceof Integer) {
                return number1.intValue() - number2.intValue();
            } else if (number1 instanceof Integer && number2 instanceof Double) {
                return number1.intValue() - number2.doubleValue();
            } else if (number1 instanceof Double && number2 instanceof Integer) {
                return number1.doubleValue() - number2.intValue();
            } else if (number1 instanceof Double && number2 instanceof Double) {
                return number1.doubleValue() - number2.doubleValue();
            }

            return 0;
        }
    }, MULTIPLY("*") {
        public Number calculate(Number number1, Number number2) {
            if (number1 instanceof Integer && number2 instanceof Integer) {
                return number1.intValue() * number2.intValue();
            } else if (number1 instanceof Integer && number2 instanceof Double) {
                return number1.intValue() * number2.doubleValue();
            } else if (number1 instanceof Double && number2 instanceof Integer) {
                return number1.doubleValue() * number2.intValue();
            } else if (number1 instanceof Double && number2 instanceof Double) {
                return number1.doubleValue() * number2.doubleValue();
            }

            return 0;
        }
    }, DIVIDE("/") {
        public Number calculate(Number number1, Number number2) {
            if (number1 instanceof Integer && number2 instanceof Integer) {
                return number1.intValue() / number2.intValue();
            } else if (number1 instanceof Integer && number2 instanceof Double) {
                return number1.intValue() / number2.doubleValue();
            } else if (number1 instanceof Double && number2 instanceof Integer) {
                return number1.doubleValue() / number2.intValue();
            } else if (number1 instanceof Double && number2 instanceof Double) {
                return number1.doubleValue() / number2.doubleValue();
            }
            return 0;
        }
    };

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public Number calculate(Number number1, Number number2) {
        return 0;
    }

    public static Operator getOperator(String input) {
        if (input.startsWith("+")) {
            return Operator.ADD;
        } else if (input.startsWith("-")) {
            return Operator.SUBTRACT;
        } else if (input.startsWith("*")) {
            return Operator.MULTIPLY;
        } else if (input.startsWith("/")) {
            return Operator.DIVIDE;
        }
        return null;
    }
}