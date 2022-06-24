package Lexers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTokenizer implements Enumeration<Token> {
    private final String content;   // Строка, которую необходимо разбить на лексемы
    private final ITokenType[] tokenTypes;  // Массив типов токенов с регулярными выражениями для анализа
    private final Matcher matcher;
    private int currentPosition = 0;    // Текущая позиция анализа

    /**
     * @param content строка для анализа
     * @param tokenTypes Массив типов токенов с регулярными выражениями для анализа
     */
    public RegexTokenizer(String content, ITokenType[] tokenTypes) {
        this.content = content;
        this.tokenTypes = tokenTypes;

        // Формируем общее регулярное выражение для анализа
        List<String> regexList = new ArrayList<>();
        for (int i = 0; i < tokenTypes.length; i++) {
            ITokenType tokenType = tokenTypes[i];
            regexList.add("(?<g" + i + ">" + tokenType.getRegex() + ")");
        }
        String regex = String.join("|", regexList);

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        // Запускаем первый поиск
        matcher = pattern.matcher(content);
        matcher.find();
    }

    @Override
    public boolean hasMoreElements() {
        return currentPosition < content.length();
    }
    @Override
    public Token nextElement() {
        boolean found = currentPosition > matcher.start() ? matcher.find() : true;

        int start = found ? matcher.start() : content.length();
        int end = found ? matcher.end() : content.length();

        if(found && currentPosition == start) {
            currentPosition = end;
            // Лексема найдена- определяем тип
            for (int i = 0; i < tokenTypes.length; i++) {
                String si = "g" + i;
                if (matcher.start(si) == start && matcher.end(si) == end) {
                    return createToken(content, tokenTypes[i], start, end);
                }
            }
        }
        throw new IllegalStateException("Не удается определить лексему в позиции " + currentPosition);
    }

    /**
     * @param content оригинальная строка для анализа
     * @param tokenType тип токена
     * @param start начало токена в оригинальной строке
     * @param end конец токена в оригинальной строке
     * @return объект токена-лексемы
     */
    protected Token createToken(String content, ITokenType tokenType, int start, int end) {
        return new Token(content.substring(start, end), tokenType, start);
    }
}