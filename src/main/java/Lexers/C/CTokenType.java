package Lexers.C;

import Lexers.ITokenType;

public enum CTokenType implements ITokenType {
    KEYWORD("\\b(?:alignas|alignof|and|and_eq|asm|bitand|bitor|break|case|catch|class|compl|concept|const|const_cast|consteval|constexpr|constinit|" +
            "co_await|continue|co_return|co_yield|decltype|default|delete|do|dynamic_cast|else|enum|explicit|export|extern|" +
            "for|friend|goto|if|inline|mutable|namespace|new|noexcept|not|not_eq|nullptr|operator|or|or_eq|private|protected|public|register|" +
            "reinterpret_cast|requires|return|signed|sizeof|static|static_assert|static_cast|struct|switch|template|this|thread_local|throw|try|typedef|typeid|typename|union|" +
            "unsigned|using|using|virtual|void|volatile|while|xor|xor_e)\\b"),    //0

    TYPE("\\b(?:bool|char|char8_t|char16_t|char32_t|double|float|int|long|short|wchar_t|string|auto)\\b"),

    ID("[_A-Za-z][_A-Za-z0-9]*"),

    //Целые 16е, 8е и 10е числа, с разделителем <'> для версий С++ выше 14, с суффиксами неподписанного <u|U> и длинного типов <l|L>
    HEXADECIMAL_INTEGER_LITERAL("0[xX]['0-9a-fA-F]*[uU]?[lL]?[lL]?[uU]?"),  //3-6
    BINARY_INTEGER_LITERAL("0[bB]['01]+[uU]?[lL]?[lL]?[uU]?"),
    OCTAL_INTEGER_LITERAL("0['0-7]*[uU]?[lL]?[lL]?[uU]?"),
    DECIMAL_INTEGER_LITERAL("0|[1-9]['0-9]*[uU]?[lL]?[lL]?[uU]?"),
    //Вещественные, с экспонентой и суффиксами
    FLOAT1_LITERAL("[0-9]+\\.[0-9]*([eE][+\\-]?[0-9]+)?([fF]|[dD])?"),  //7-10
    FLOAT2_LITERAL("\\.[0-9]+([eE][+\\-]?[0-9]+)?([fF]|[dD])?"),
    FLOAT3_LITERAL("[0-9]+([eE][+\\-]?[0-9]+)([fF]|[dD])?"),
    FLOAT4_LITERAL("[0-9]+([eE][+\\-]?[0-9]+)?([fF]|[dD])?"),
    //Логические
    LOGIC_LITERAL("\\b(?:true|false)"),     //11
    //Строки и символы
    STRING("(u8|L|u|U)?R?\"[^\"]*\"s?"),    //12
    CHAR("(u8|L|u|U)?'[^']+'"),             //13

    //коментарии
    COMMENT("//[^\\n\\r]*"),
    COMMENT1("(?s)/\\*(.)*?\\*/"),

    //Операторы
    OPERATOR_THREE("(->\\*)|(<<=)|(>>=)"),   //16-18
    OPERATOR_TWO("(::)|(->)|(\\+\\+)|(--)|(\\.\\*)|(<<)|(>>)|(<=)|(>=)|(==)|(!=)|(&&)|(\\|\\|)|(\\?:)|(\\*=)|(\\/=)|(%=)|(\\+=)|(-=)|(&=)|(\\|=)|(\\^=)"),
    OPERATOR_ONE("(\\.)|(~)|(!)|(-)|(\\+)|(&)|(\\*)|(\\/)|(%)|(<)|(>)|(\\^)|(\\|)|(=)"),
    //Разделители
    SEPARATOR("[(){}\\[\\];,:]"),

    SPACE("\\r|\\t|\\n|\\f|\\s"),
    INCLUDES("#[^\\n\\r]*"),
    ERRORS(".");

    private final String regex;

    CTokenType(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRegex() {
        return regex;
    }
}