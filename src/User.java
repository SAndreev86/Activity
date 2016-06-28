import java.util.ArrayList;

public class User {
	
	private String UserName;
		
	private ArrayList<String> AllActivity = new ArrayList<String>();
	
	
 	public User(String Activity) {
 	
 		boolean UserNamebool = false;
 		
 		for(String all : Activity.split("#")) {
 			
 			//если мы не заисали имя пользователя, заисываем первую строку как имя(до первого разделителя # у нас идет имя)
 			if(!UserNamebool) {
 				UserName = all;
 				UserNamebool = true;
 				continue;
 			}
			AllActivity.add(all);
 		}
 	}
 	
 	//метод для получения массива активностей
 		public String [] getAllActivity() {
 			
 			String[] AllActivityMass = new String[AllActivity.size()];
 			
 			for(int n = 0; n < AllActivityMass.length; n++) {
 				AllActivityMass[n] = AllActivity.get(n);
 			}
 			
 			return AllActivityMass;
 		} 
 		
 		//получаем имя пользователя
 		public String getUserName() {
 			return UserName;
 		}
}
