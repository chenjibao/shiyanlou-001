
package cjb.compiler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class SyntacticAnalyzer {
	private  Lexer  lexer=new Lexer("src\\yuan1.txt", "src\\symbol.txt", "src\\token.txt", "src\\error.txt");
        public StringBuffer sb=new StringBuffer();
	Iterator<String> it;
	Iterator<String> it1;
	private String currWord;
	private String currWord1;
	{
		
		while (lexer.getReaderState() == false) {
            lexer.anlalyse();  
        }
		
        lexer.saveTokens();    
        lexer.savaSymbols();    
        lexer.savaError();
        it=lexer.list.iterator();
        
	}
	public  Map<String,String> errorsMap=new HashMap<String,String>();
	public void program() throws IOException{
		System.out.println("program¡ª¡ª>block");
		block();
	}
	
	public SyntacticAnalyzer() throws IOException {
		super();
		if(it.hasNext()){
			currWord=it.next();
		}
	}
	private void block() throws IOException {
		if(currWord=="{"){
//			System.out.println("block¡ª¡ª>{stmts}");
                        sb.append("block¡ª¡ª>{stmts}\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			stmts();
			if(currWord=="}"){
				return ;
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
				return;
			}
		}else{
			errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
			return;
		}
	}

	private void stmts() throws IOException {
		if(currWord=="}"){
//			System.out.println("stmts¡ª¡ª>null");
                        sb.append("stmts¡ª¡ª>null\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			return ;
		}
//		System.out.println("stmts¡ª¡ª>stmt stmts");
                sb.append("stmts¡ª¡ª>stmt stmts\n");
		stmt();
		stmts();
		
	}

	
	private void stmt() throws IOException{
		switch(currWord){
		case "id":
//			System.out.println("stmt¡ª¡ª>id=expr;");
                        sb.append("stmt¡ª¡ª>id=expr;\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			if(currWord=="="){
				if(it.hasNext()){
					currWord=it.next();
				}
				expr();
				if(currWord==";"){
					if(it.hasNext()){
						currWord=it.next();
					}
					return ;
				}else{
					errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
					return;
				}
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
				return;
			}
		case "if":
//			System.out.println("stmt¡ª¡ª>if(bool) stmt stmt1;");
                        sb.append("stmt¡ª¡ª>if(bool) stmt stmt1;\n");
			currWord=it.next();
			if(currWord=="("){
				if(it.hasNext()){
					currWord=it.next();
				}
				bool();
				if(currWord==")"){
					if(it.hasNext()){
						currWord=it.next();
					}
					stmt();
					stmt1();
					return ;
				}else{
					errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
					return;
				}
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
				return;
			}
		case "while":
//			System.out.println("stmt¡ª¡ª>while(bool) stmt");
                        sb.append("stmt¡ª¡ª>while(bool) stmt\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			if(currWord=="("){
				if(it.hasNext()){
					currWord=it.next();
				}
				bool();
				if(currWord==")"){
					if(it.hasNext()){
						currWord=it.next();
					}
					stmt();
					return;
				}else{
					errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
					return;
				}
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
				return;
			}
		case "do":
//			System.out.println("stmt¡ª¡ª>do stmt while (bool)");
                        sb.append("stmt¡ª¡ª>do stmt while (bool)\n");
			stmt();
			if(currWord=="while"){
				if(it.hasNext()){
					currWord=it.next();
				}
				if(currWord=="("){
					if(it.hasNext()){
						currWord=it.next();
					}
					bool();
					if(currWord==")"){
						if(it.hasNext()){
							currWord=it.next();
						}
						return;
					}else{
						errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
						return;
					}
				}else{
					errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
					return;
				}
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
				return;
			}
		case "break":
//			System.out.println("stmt¡ª¡ª>break");
                        sb.append("stmt¡ª¡ª>break\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			return ;
		case "{":
			block();
		}
	}

	private void stmt1() throws IOException{
		switch(currWord){
		case "else":
//			System.out.println("stmt1¡ª¡ª>else stmt");
                        sb.append("stmt1¡ª¡ª>else stmt\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			stmt();
			return;
		default:
			System.out.println("stmt1¡ª¡ª>null");
                        sb.append("stmt1¡ª¡ª>null\n");
			return ;
		}
	}

	private void bool() throws IOException{
                sb.append("expr<bool1|expr>bool1\n");
		expr();
		switch(currWord){
		case ">":
			if(it.hasNext()){
				currWord=it.next();
			}
			bool1();
			return;
		case "<":
			if(it.hasNext()){
				currWord=it.next();
			}
			bool1();
			return ;
		case "=":
			if(it.hasNext()){
				currWord=it.next();
			}
			bool1();
			return;
		default:
			errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
			return;
		}
	}

	private void bool1() throws IOException{
		sb.append("bool1¡ª¡ª>expr|=expr\n");
		if(currWord=="="){
			if(it.hasNext()){
				currWord=it.next();
			}
			expr();
			return;
		}
		expr();
	}

	private void expr() throws IOException{
		term();
		exper1();
	}

	private void term() throws IOException{
		factor();
		term1();
	}
	
	private void term1() throws IOException{
            
		switch(currWord){
		case "*":
//			System.out.println("term1¡ª¡ª>*factor term1");
                        sb.append("term1¡ª¡ª>*factor term1\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			factor();
			term1();
			return;
		case "/":
//			System.out.println("term1¡ª¡ª>/factor term1");
                        sb.append("term1¡ª¡ª>/factor term1\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			factor();
			term1();
			return;
		default:
//			System.out.println("term1¡ª¡ª>null");
                        sb.append("term1¡ª¡ª>null\n");
			return ;
		}
		
	}

	private void factor() throws IOException{
		switch(currWord){
		case "(":
//			System.out.println("factor¡ª¡ª>(expr)");
                        sb.append("factor¡ª¡ª>(expr)\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			expr();
			if(currWord==")"){
				if(it.hasNext()){
					currWord=it.next();
				}
				return;
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"è¡?","è¯­æ³•é”™è¯¯");
				return;
			}
		case "id":
//			System.out.println("factor¡ª¡ª>id");
                        sb.append("factor¡ª¡ª>id\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			return;
		case "num":
//			System.out.println("factor¡ª¡ª>num");
                        sb.append("factor¡ª¡ª>num\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			return;
		}
	}

	private void exper1() throws IOException{
		switch(currWord){
		case "+":
//			System.out.println("expr1¡ª¡ª>+term expr1");
                        sb.append("expr1¡ª¡ª>+term expr1\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			term();
			exper1();
			return;
		case "-":
//			System.out.println("expr1¡ª¡ª>-term expr1");
                        sb.append("expr1¡ª¡ª>-term expr1\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			term();
			exper1();
			return;
		default:
//			System.out.println("expr1¡ª¡ª>null");
                        sb.append("expr1¡ª¡ª>null\n");
			return ;
		}
	}
}
