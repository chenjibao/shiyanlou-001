
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
		System.out.println("program����>block");
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
//			System.out.println("block����>{stmts}");
                        sb.append("block����>{stmts}\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			stmts();
			if(currWord=="}"){
				return ;
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
				return;
			}
		}else{
			errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
			return;
		}
	}

	private void stmts() throws IOException {
		if(currWord=="}"){
//			System.out.println("stmts����>null");
                        sb.append("stmts����>null\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			return ;
		}
//		System.out.println("stmts����>stmt stmts");
                sb.append("stmts����>stmt stmts\n");
		stmt();
		stmts();
		
	}

	
	private void stmt() throws IOException{
		switch(currWord){
		case "id":
//			System.out.println("stmt����>id=expr;");
                        sb.append("stmt����>id=expr;\n");
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
					errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
					return;
				}
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
				return;
			}
		case "if":
//			System.out.println("stmt����>if(bool) stmt stmt1;");
                        sb.append("stmt����>if(bool) stmt stmt1;\n");
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
					errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
					return;
				}
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
				return;
			}
		case "while":
//			System.out.println("stmt����>while(bool) stmt");
                        sb.append("stmt����>while(bool) stmt\n");
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
					errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
					return;
				}
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
				return;
			}
		case "do":
//			System.out.println("stmt����>do stmt while (bool)");
                        sb.append("stmt����>do stmt while (bool)\n");
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
						errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
						return;
					}
				}else{
					errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
					return;
				}
			}else{
				errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
				return;
			}
		case "break":
//			System.out.println("stmt����>break");
                        sb.append("stmt����>break\n");
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
//			System.out.println("stmt1����>else stmt");
                        sb.append("stmt1����>else stmt\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			stmt();
			return;
		default:
			System.out.println("stmt1����>null");
                        sb.append("stmt1����>null\n");
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
			errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
			return;
		}
	}

	private void bool1() throws IOException{
		sb.append("bool1����>expr|=expr\n");
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
//			System.out.println("term1����>*factor term1");
                        sb.append("term1����>*factor term1\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			factor();
			term1();
			return;
		case "/":
//			System.out.println("term1����>/factor term1");
                        sb.append("term1����>/factor term1\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			factor();
			term1();
			return;
		default:
//			System.out.println("term1����>null");
                        sb.append("term1����>null\n");
			return ;
		}
		
	}

	private void factor() throws IOException{
		switch(currWord){
		case "(":
//			System.out.println("factor����>(expr)");
                        sb.append("factor����>(expr)\n");
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
				errorsMap.put(String.valueOf(lexer.row)+"�?","语法错误");
				return;
			}
		case "id":
//			System.out.println("factor����>id");
                        sb.append("factor����>id\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			return;
		case "num":
//			System.out.println("factor����>num");
                        sb.append("factor����>num\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			return;
		}
	}

	private void exper1() throws IOException{
		switch(currWord){
		case "+":
//			System.out.println("expr1����>+term expr1");
                        sb.append("expr1����>+term expr1\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			term();
			exper1();
			return;
		case "-":
//			System.out.println("expr1����>-term expr1");
                        sb.append("expr1����>-term expr1\n");
			if(it.hasNext()){
				currWord=it.next();
			}
			term();
			exper1();
			return;
		default:
//			System.out.println("expr1����>null");
                        sb.append("expr1����>null\n");
			return ;
		}
	}
}
