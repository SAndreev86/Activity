import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ReadWriteActivity {
	
	private ArrayList<User> Users = new ArrayList<User>();
	
	public ReadWriteActivity() {
			
				File dictFile = new File("UserActivity.txt");
				
				if(dictFile.canRead()) {
					
					String Activity = "";

					//открываем файл для чтения файл должен быть в кодировке UTF-8
					try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dictFile), "UTF-8"))) {
	
			        
						int temp;
						
						while((temp=br.read()) != -1){
							Activity += (char)temp;			        
						}
							
					} catch(Exception ex){         
						System.out.println(ex.getMessage());
					}  
					
					//% разделитель пользователей(пытался сделать \n но были какие то проблемы) записываем их все в список
					String[] SplitUsers = Activity.split("%");
					
					for(int n = 0; n < SplitUsers.length; n++) {
						if(SplitUsers[n].contains("%")) {
							continue;
						}
						Users.add(new User(SplitUsers[n]));
					}
					
					
				}	else {
				       System.out.println("файла нет или не доступен для чтения, создаем новый...");
				       //тут буду создавать новый файл если старого нет и инициализировать активностями о умолчанию
				}
				
				
		}
	
	
	//метод для записи новой активности, получаем строку и имя пользователя, находим нужного пользователя и добавляем ему новую активность и записываем
	//и перезаисываем файл
	public void setNewActivity(String name , String activity) {
		
		File dictFile = new File("UserActivity.txt");
		
		if(dictFile.canRead()) {
	
			try(BufferedWriter addActivity = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dictFile), "UTF-8"))) {
				
				String out = "";
				//для каждого пользователя
				for(int n = 0; n < Users.size(); n++) {
					
					//получаем массив всех активностей пользователя
					String[] tempAllActy = Users.get(n).getAllActivity();
					
					//первой строкой пишем имя
					out += Users.get(n).getUserName()+"#";
					
					for(String t : tempAllActy) {
					
						out += t+"#";	
					
					}
					//важная метода, если нужный пользователь, записываем ему новую активность 
					if(Users.get(n).getUserName().contains(name)) {
						out += activity+"#";
						
					}
					
					out += "%";
				}
				//записываем все данные в файл
				addActivity.write(out);

			} catch(Exception ex){         
				System.out.println(ex.getMessage());
			}  
						
		}	else {
		       System.out.println("файла нет или не доступен для чтения");
		}
	}
}
