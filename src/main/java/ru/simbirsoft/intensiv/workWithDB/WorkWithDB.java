package ru.simbirsoft.intensiv.workWithDB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkWithDB {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("entityManager");

	private static List<String> list = new ArrayList<>();

	static {
		list.add("чай");
		list.add("завтрак");
		list.add("обед");
		list.add("ужин");
		list.add("соц.сеть");
		list.add("пробежка");
		list.add("работа");
		list.add("отдых");
		list.add("курение");
	}

	/*
	 * метод записывает активность в базу. в него поступают имя пользователя
	 * который создал эту новую активность и саму активность.
	 */
	public static void writeActivity(String activity, String name) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		ActivityDB str1 = new ActivityDB();
		str1.setName(name);
		str1.setActivity(activity);
		str1.setId(getMaxIdActivity() + 1);
		entityManager.merge(str1);
		transaction.commit();
		entityManager.close();

	}

	/*
	 * внутренний метод. не для вас
	 */
	private static int getMaxIdActivity() {
		EntityManager entity = entityManagerFactory.createEntityManager();
		int as = entity.createNamedQuery("findMaxIdActivity", Integer.class).getSingleResult();
		entity.close();
		return as;
	}

	/*
	 * внутренний метод. не для вас
	 */
	private static int getMaxIdUser() {
		EntityManager entity = entityManagerFactory.createEntityManager();
		int as = entity.createNamedQuery("findMaxIdUser", Integer.class).getSingleResult();
		entity.close();
		return as;
	}

	/*
	 * внутренний метод. не для вас
	 */
	private static int getMaxIdStatistic() {
		EntityManager entity = entityManagerFactory.createEntityManager();
		int as = entity.createNamedQuery("findMaxIdStatistic", Integer.class).getSingleResult();
		entity.close();
		return as;
	}

	/*
	 * возвращает список активностей определенного пользователя те которые есть
	 * у всех плюс те которые он создал сам
	 */
	public static String[] getListActivity(String name) {
		List<String> list1 = new ArrayList<>();
		list1.addAll(list);
		List<ActivityDB> list2 = new ArrayList<>();
		EntityManager entity = entityManagerFactory.createEntityManager();
		list2 = entity.createNamedQuery("findActivityByName", ActivityDB.class).setParameter("name", name)
				.getResultList();

		for (ActivityDB i : list2) {
			list1.add(i.getActivity());
		}
		entity.close();
		// return list1;

		String[] AllActivityMass = new String[list1.size()];

		for (int n = 0; n < AllActivityMass.length; n++) {
			AllActivityMass[n] = list1.get(n);
		}

		return AllActivityMass;

	}

	/*
	 * записывает нового юзера
	 */
	public static void writeNewUser(String name, String password) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setId(getMaxIdUser() + 1);
		entityManager.merge(user);
		transaction.commit();
		entityManager.close();

	}

	/*
	 * получает пароль по имени юзера
	 */
	public static User getPassword(String name) {
		User userDB = null;
		EntityManager entity = entityManagerFactory.createEntityManager();
		userDB = entity.createNamedQuery("findUserByName", User.class).setParameter("name", name).getSingleResult();
		entity.close();
		return userDB;
	}

	/*
	 * записывает статистику в базу. поступающие данные активность время
	 * активности коммент имя пользователя и дата
	 */
	public static void wtiteStatistic(String activity, long time, String comment, String name, Date date) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		DataStatisticDB statisticDB = new DataStatisticDB(activity, time, comment, name, date);
		statisticDB.setId(getMaxIdStatistic() + 1);
		entityManager.merge(statisticDB);
		transaction.commit();
		entityManager.close();

	}

	/*
	 * вот этот метод Серег для тебя!!!! передаешь имя пользователя и дату и он
	 * возвращает список статистики из которого ты потом легко достанешь все
	 * данные для себя. чтобы установить дату: Date date = new Date();
	 * date.setDate(текущая дата минус какое-то число чтобы получилась нужная
	 * тебе дата); например тебе нужно позавчера: сегодня 3 - 2 получаем 1. у
	 * нас всего три дня это будет легко настроить.
	 */
	public static List<DataStatisticDB> getStatistic(String name, Date date) {
		List<DataStatisticDB> statisticDBs = new ArrayList<>();
		EntityManager entity = entityManagerFactory.createEntityManager();
		statisticDBs = entity.createNamedQuery("findStatisticByNameAndDate", DataStatisticDB.class)
				.setParameter("name", name).setParameter("date", date).getResultList();
		entity.close();
		return statisticDBs;
	}

	public static void close() {
		entityManagerFactory.close();
	}

	public static boolean isExistUser(String name) {
		User userDB = null;
		EntityManager entity = entityManagerFactory.createEntityManager();
		boolean is = true;
		try {
			userDB = entity.createNamedQuery("findUserByName", User.class).setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			is = false;
		}
		entity.close();
		return is;
	}

}
