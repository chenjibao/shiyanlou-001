package cjb.compiler;
import java.io.IOException;
import java.util.Set;
public class Main {
	public static void main(String[] args) {
		try {
			SyntacticAnalyzer sa=new SyntacticAnalyzer();
			sa.program();
			if(sa.errorsMap.isEmpty()){
                            
				System.out.println("·ÖÎö³É¹¦");
			}else{
				Set set=sa.errorsMap.keySet();
				for (Object obj : set) {
					String key=(String)obj;
					System.out.println(key+sa.errorsMap.get(key));
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
