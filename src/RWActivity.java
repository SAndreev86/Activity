import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class RWActivity {
	
	private ArrayList<String> activy = new ArrayList<String>();

	private File dictFile;
	
	//конструктор считывает файл активностей и добавляет их в список
	public RWActivity() {
			
				dictFile = new File("UserActivity.txt");
				
				if(dictFile.canRead()) {
					
					String Activity = "";

					//открываем файл для чтения файл должен быть в кодировке UTF-8
					try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dictFile), "UTF-8"))) {
	
			        
						String temp;
						
						while((temp=br.readLine()) != null){
							Activity += temp;			        
						}
							
					} catch(Exception ex){         
						System.out.println(ex.getMessage());
					}  
					
					String[] Splitactivy = Activity.split("#");
					
					for(int n = 0; n < Splitactivy.length; n++) {
						if(Splitactivy[n].contains("#")) {
							continue;
						}
						activy.add(Splitactivy[n]);
					}
					
					
				}	else {
				       System.out.println("файла нет или не доступен для чтения, создаем новый...");
				       //тут буду создавать новый файл если старого нет и инициализировать активностями о умолчанию
				}
				
				
		}
	
	//метод для получения массива активностей
		public String [] getAllActivity() {
			
			String[] AllActivityMass = new String[activy.size()];
			
			for(int n = 0; n < AllActivityMass.length; n++) {
				AllActivityMass[n] = activy.get(n);
			}
			
			return AllActivityMass;
		} 
		
		//метод для записи новой активности, получаем строку, добавляем в список новую активность
		//и перезаисываем в файл
		public void AddItem(String activity) {
			
			activy.add(activity);

						
			if(dictFile.canRead()) {
		
				try(BufferedWriter addActivity = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dictFile), "UTF-8"))) {
					
					String out = "";
					
						//получаем массив всех активностей пользователя
						String[] tempAllActy = getAllActivity();
												
						for(String t : tempAllActy) {
							
							System.out.println(t);
							out += t+"#";	
						
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
