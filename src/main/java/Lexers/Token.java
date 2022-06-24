package Lexers;

public class Token {
    private final String text;  // Текст токена
    private final ITokenType type;  // Тип токена
    private int start;  // Начало токена в исходном тексте

    public Token(String text, ITokenType type, int start) {
        this.text = text;
        this.type = type;
        this.start = start;
    }
    public String getText() {
        return text;
    }
    public ITokenType getType() {
        return type;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
}