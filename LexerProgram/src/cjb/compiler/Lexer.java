package cjb.compiler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Lexer {
	public List<String> list=new LinkedList<String>();
	
	public  int row=1;
	
	public  int col=0;
	
	public char currChar=' ';
	
	public Map<String,String> words=new HashMap<String,String>();
	
	public Map<String,Error> errors=new HashMap<String, Error>();
	
	List<String> tokens=new LinkedList<String>();
	
	private String SOURCE_PATH;
	
	private String SYMBOL_PATH;
	
	private String TOKEN_PATH;
	
	private String ERROR_PATH;
	
	BufferedReader reader=null;
	
	private boolean isEnd=false;
	
	public boolean getReaderState(){
		return this.isEnd;
	}
	
	public Lexer(){}
	
	public Lexer(String sOURCE_PATH, String sYMBOL_PATH, String tOKEN_PATH,
			String eRROR_PATH) {
		super();
		SOURCE_PATH = sOURCE_PATH;
		SYMBOL_PATH = sYMBOL_PATH;
		TOKEN_PATH = tOKEN_PATH;
		ERROR_PATH = eRROR_PATH;
		try {
			reader=new BufferedReader(new FileReader(SOURCE_PATH));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		initWords();
	}
	
	public void reserve(String key,String value){
		words.put(key, value);
	}
	
	public void initWords(){
		reserve(Word.and, "�����");
		reserve(Word.Boolean, "��������");
		reserve(Word.Char, "��������");
		reserve(Word.Double,"��������" );
//		reserve(Word.doubleEqual, "�����?");
		reserve(Word.equal, "�����");
		reserve(Word.False, "�ؼ���");
		reserve(Word.Float, "��������");
		reserve(Word.greater, "�����");
//		reserve(Word.greaterEqual, "�����?");
		reserve(Word.less, "�����");
		reserve(Word.lessEqual, "�����");
		reserve(Word.minus, "�����");
		reserve(Word.minusEqual, "�����");
		reserve(Word.minusMinus, "�����");
		reserve(Word.notEqual, "�����");
		reserve(Word.or, "�����");
		reserve(Word.plus, "�����");
		reserve(Word.plusEqual, "�����");
		reserve(Word.plusPlus, "�����");
		reserve(Word.Private, "�ؼ���");
		reserve(Word.Public, "�ؼ���");
		reserve(Word.String, "��������");
		reserve(Word.True, "�ؼ���");
		reserve(Word.leftBracket, "�綨��");
		reserve(Word.rightBracket, "�綨��");
		reserve(Word.LBracket, "�綨��");
		reserve(Word.RBracket, "�綨��");
		reserve(Word.BRBracket, "�綨��");
		reserve(Word.BLBracket, "�綨��");
		reserve(Word.comma, "�綨��");
		reserve(Word.doubleMark, "�綨��");
		reserve(Word.singleMark, "�綨��");
		reserve(Word.semicolon, "�綨��");
		reserve(Word.dot, "�綨��");
		
		
		
		
	}
	
	public void readch()throws IOException{
		currChar=(char)reader.read ();
		col++;
		if((int)currChar==0xffff){
			this.isEnd=true;
		}
	}
	
	public boolean readNext(char ch) throws IOException{
		readch();
		if(this.currChar!=ch){
			return false;
		}
	
//		this.currChar=' ';
		return true;
	}
	
	public String anlalyse() throws IOException{
		
		clearBlank();
		
		clearNote();
		
		String operator=checkOperator();
		if(operator!=""){
			return operator;
		}
       
        String bound= checkBounds();
        if(bound!=""){
            return bound;
        }
		
		String figure=checkFigure();
		if(figure!=""){
			return figure;
		}
		
		
		String keyWord=checkKeyWords();
		if(keyWord!=""){
			return keyWord;
		}
		
		
		String ss=""+currChar;
//		if ((int)currChar != 0xffff){
		if (getReaderState()==false){
            tokens.add(ss);  
		}
		currChar=' ';
		return ss;
	}

    public String checkBounds() throws IOException {
       
        switch(currChar){
            case '[':
                tokens.add("[");
                return "[";
            case ']':
                tokens.add("]");
                return "]";
            case '(':
                tokens.add("(");
                return "(";
            case ')':
                tokens.add(")");
                return ")";
            case '{':
                tokens.add("{");
                return "{";
            case '}':
                tokens.add("}");
                return "}";
            case ',':
    			tokens.add(",");
    			return ",";
    		case ';':
    			tokens.add(";");
    			return ";";
    		case '\"':
    			tokens.add("\"");
    			return "\"";
    		case '\'':
    			tokens.add("\'");
    			return "\'";    
            default:return "";
        }
    }
	
	public void clearNote() throws IOException {
		if(currChar=='/'){
				if(readNext('*')){
					while(true){
						readch();
						if(currChar=='/'){
							col++;
							break;
						}else if(currChar=='\n'){
							row++;
							col=0;
						}else{
							col++;
						}
					}
					col++;
				}else if(currChar=='/'){
					while(true){
						readch();
						if(currChar=='\n'){
							row++;
							col=0;
							break;
						}else{
							col++;
						}
					}
					col++;
				}
			}
	}
	
	public void clearBlank() throws IOException {
		while(true){
			readch();
			if(currChar==' ' || currChar=='\t'){
				col++;
				continue;
			}else if(currChar=='\n'){
				row++;
				col=0;
				continue;
			}else{
				break;
			}
		}
		col++;
	}
	
	public String checkKeyWords() throws IOException {
		if(Character.isLetter(currChar) || currChar=='_'){
			StringBuffer sb=new StringBuffer();
			do{
				sb.append(currChar);
				reader.mark(1);
				readch();
			}while(Character.isLetterOrDigit(currChar) || currChar=='_');
			
			String key=sb.toString();
			String value=words.get(key);
			if(value!=null){
				tokens.add(key);
				reader.reset();
				return key;
			}
			
			tokens.add(key);
			words.put(key, "��ʶ��");
			reader.reset();
			return key;
		}
		return "";
	}
	
	public String checkFigure() throws IOException {
		if(Character.isDigit(currChar)){
			int value=0;
			StringBuffer sb=new StringBuffer();
			do {
				
//				value = 10 * value + Character.digit(currChar, 10); 
				value =Character.digit(currChar, 10);
				sb.append(""+value);
				reader.mark(1);
				readch();
			} while (Character.isDigit(currChar));
			if(Character.isLetter(currChar)){
				sb.append(currChar);
				checkLexer(sb.toString());
				tokens.add(sb.toString()); 
//				br.mark(1);
//		        System.out.println((char)br.read());
//		        br.reset();
//		        System.out.println((char)br.read());
				reader.reset();
				return sb.toString();
			}else{
				tokens.add(sb.toString());
				words.put(sb.toString(), "����");
				reader.reset();
				return sb.toString();
			}
		}
		return "";
	}
	
	public boolean checkLexer(String word) {
		char firstChar=word.charAt(0);
		switch(firstChar){
		case '0':  
        case '1':  
        case '2':  
        case '3':  
        case '4':  
        case '5':  
        case '6':  
        case '7':  
        case '8':  
        case '9':  
        	errors.put(word,new Error(row,col, "不能用数字开�?"));
        	return false;
        case '~':  
        case '!':  
        case '@':  
        case '$':  
        case '%':  
        case '^':  
        case '&':  
        case '*':  
        case '(':  
        case ')':  
        	errors.put(word,new Error(row,col, "不能用特殊符号开�?"));
        	return false;
        default:
        	return true;
		}
		
	}
	
	public String checkOperator() throws IOException {
		switch(currChar){
		case '=':
//			if(readNext('=')){
//				tokens.add("==");
//				return "==";
//			}else{
//				tokens.add("=");
//				return "=";
//			}
			tokens.add("=");
			return "=";
		case '>':
//			if(readNext('=')){
//				tokens.add(">=");
//				return ">=";
//			}else{
//				tokens.add("=");
//				return "=";
//			}
			tokens.add(">");
			return ">";
		case '<':
//			if(readNext('=')){
//				tokens.add("<=");
//				return "<=";
//			}else{
//				tokens.add("<");
//				return "<";
//			}
			tokens.add("<");
			return "<";
		case '!':
			if(readNext('=')){
				tokens.add("!=");
				return "!=";
			}else{
				tokens.add("!");
				return "!";
			}
		case '+':
			if(readNext('=')){
				tokens.add("+=");
				return "+=";
			}else if(this.currChar=='+'){
				tokens.add("++");
				return "++";
			}else{
				tokens.add("+");
				return "+";
			}
		case '-':
			if(readNext('=')){
				tokens.add("-=");
				return "-=";
			}else if(this.currChar=='-'){
				tokens.add("--");
				return "--";
			}else{
				tokens.add("-");
				return "-";
			}
		case '|':
			if(readNext('|')){
				tokens.add("||");
				return "||";
			}else{
				tokens.add("|");
				return "|";
			}
		
		case '~':  
		case '@':  
		case '$':  
		case '%':  
		case '^':  
		case '&':
			
			String errorString="";
			do{
				errorString+=currChar;//
				readch();
				if(currChar==' ' || currChar=='\t'){
					break;
				}
			}while(true);
			errors.put(errorString, new Error(row, col, "符号命名错误"));
			return errorString;
		default:
			return "";
		}
	}
	
	public String savaError()throws IOException{
                StringBuffer sb=new StringBuffer();
               sb.append("[����]\t[��������]\t\t[����λ��]\n");
		Set keys=errors.keySet();
		for (Object object : keys) {
			String key=(String)object;
			Error err=errors.get(key);
                        sb.append(key + "\t" + err.getMsg() + "\t\t" + err.getHh() + "�?" + err.getLh() + "�?" + "\r\n");
		}
                return sb.toString();
	}
	
	public String saveTokens()throws IOException{
		BufferedWriter br=new BufferedWriter(new FileWriter(TOKEN_PATH));
                StringBuffer sb=new StringBuffer();
                sb.append("[����]  \n");
		for (int i = 0; i < tokens.size(); ++i) {  
            String tok = tokens.get(i);  
           
            sb.append(tok + "\r\n");
        }
                br.flush();
                return sb.toString();
		
		
	}
	
	public String savaSymbols()throws IOException{
            StringBuffer sb=new StringBuffer();
        sb.append("[����]          			 [����������Ϣ]\n");
        for (int i = 0; i <tokens.size(); i++) {
			String key=tokens.get(i);
			String desc=words.get(key);
			if(desc!=null){
								list.add(key);
                                sb.append(key + "\t\t\t" + desc + "\r\n");
			}
		}	
        return sb.toString();
	}
}

