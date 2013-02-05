package pack_connect;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;
import com.google.appengine.repackaged.org.json.*;

import pack_utils.ExceptFailTest;
import pack_utils.HM;
import pack_utils.Proper;
import pack_utils.RamdomData;

public class ConnectMethod extends Connect_Request_Abstract
{
	private URIBuilder builder;; 
	private URI uri;
	private JSONObject jsonObject;
	
	// Создание профиля АвтоТест
	public void CreateProfileReqeust(String sHost) throws URISyntaxException, IOException, ExceptFailTest, JSONException
	{
		print("------------------------------------------------------------------------------------------------------------");
		print("Создание профиля - Тест".toUpperCase());
		print("\r\nСоздание профиля".toUpperCase());
		print("Параметры для запроса");
		print("Генерируем Еmail и пароль");
		String sEmail = RamdomData.GetRamdomString(7)+"@yopmail.com";
		String sPassword = RamdomData.GetRamdomString(7);
		print("email = "+ sEmail);
		print("password = "+ sPassword);
		builder = new URIBuilder();
		
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account")
    		.setParameter("email", sEmail)
    		.setParameter("password", sPassword);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:\r\n" + jsonObject.toString(10) + "\r\nПрофиль пользователя создан\r\n");
    	else
    	{
    		print("Не удалось создать профилль пользователя\r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString(10)+"\r\n");
    		print("Тест провален".toUpperCase());
    		throw new ExceptFailTest("Тест провален");
    	}
    	
    	print("------------------------------------------------------------------------------------------------------------");
    	print("Тест завершен успешно".toUpperCase());
	}
	// Авторизация АвтоТест
	public void Authorization(String sHost) throws URISyntaxException, IOException, ExceptFailTest, JSONException
	{
		JSONObject jTemp;
		print("------------------------------------------------------------------------------------------------------------");
		print("Авторизация - Тест".toUpperCase());
		print("\r\nАвторизация - Обычный пользователь".toUpperCase());
		print("Параметры для запроса");
		print("email = "+ Proper.GetProperty("login_authOP"));
		print("password = "+ Proper.GetProperty("password"));
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account/login")
    		.setParameter("username", Proper.GetProperty("login_authOP"))
    		.setParameter("password", Proper.GetProperty("password"));
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	jsonObject = ParseResponse(sResponse);
    	
    	
    	if(jsonObject.isNull("error"))
    	{
	    	String sAuth_token = (String) jsonObject.get("auth_token");
	    	if(sAuth_token != null)
	    	{
	    	         print("Auth_token получен = "+ sAuth_token);
	    	         print("Ответ сервера:\r\n"+ jsonObject.toString(10) + "\r\nПользователь авторизован");
	    	}
	    	else
	    	{
	    		print("Не удалось получить ключ Auth_token");
	    		print("Тест провален".toUpperCase());
	    		throw new ExceptFailTest("Тест провален");
	    	}
    	}
    	else 
    	{
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10) + "\r\n");
    		print("Тест провален".toUpperCase());
    		throw new ExceptFailTest("Тест провален");
    	}
    	
    	/////////////////////////////////////////////////////////////////////////////////////////////
    	print("\r\nАвторизация - Интернет партнер".toUpperCase());
		print("Параметры для запроса");
		print("email = "+ Proper.GetProperty("login_authIP"));
		print("password = "+ Proper.GetProperty("password"));
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account/login")
    		.setParameter("username", Proper.GetProperty("login_authIP"))
    		.setParameter("password", Proper.GetProperty("password"));
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	jsonObject = ParseResponse(sResponse);
    	
    	
    	if(jsonObject.isNull("error"))
    	{
	    	String sAuth_token = (String) jsonObject.get("auth_token");
	    	if(sAuth_token != null)
	    	{
	    	         print("Auth_token получен = "+ sAuth_token);  
	    	         print("Ответ сервера:\r\n"+ jsonObject.toString(10) + "\r\nПользователь авторизован");
	    	}
	    	else
	    	{
	    		print("Не удалось получить ключ Auth_token");
	    		print("Тест провален".toUpperCase());
	    		throw new ExceptFailTest("Тест провален");
	    	}
    	}
    	else 
    	{
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10) + "\r\n");
    		print("Тест провален".toUpperCase());
    		throw new ExceptFailTest("Тест провален");
    	}
    	///////////////////////////////////////////////////////////////////////////////////////////////
 
		print("\r\nАвторизация - Несуществующий пользователь".toUpperCase());
		print("Параметры для запроса");
		print("email = " + Proper.GetProperty("login_authNotExist"));
		print("password = " + Proper.GetProperty("password"));
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account/login")
    		.setParameter("username", Proper.GetProperty("login_authNotExist"))
    		.setParameter("password", Proper.GetProperty("password"));
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	jsonObject = ParseResponse(sResponse);
    	
    	jTemp = jsonObject.getJSONObject("error");
    	String sResult = jTemp.getString("description");
    	
    	if(sResult.equals("Пользователя с такими данными не существует"))
    		print("Ответ сервера:\r\n" + jsonObject.toString(10) + "\r\nПользователя не существует");
    	else 
    	{
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10) + "\r\n");
    		print("Тест провален".toUpperCase());
    		throw new ExceptFailTest("Тест провален");
    	}
    	
    	///////////////////////////////////////////////////////////////////////////////////////////////
    	
    	print("\r\nАвторизация - Забаненный пользователь".toUpperCase());
		print("Параметры для запроса");
		print("email = " + Proper.GetProperty("login_authBan"));
		print("password = " + Proper.GetProperty("password"));
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account/login")
    		.setParameter("username", Proper.GetProperty("login_authBan"))
    		.setParameter("password", Proper.GetProperty("password"));
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	jsonObject = ParseResponse(sResponse);
    	
    	jTemp = jsonObject.getJSONObject("error");
    	sResult = jTemp.getString("description");
    	
    	if(sResult.equals("Пользователь не активный"))
    		print("Ответ сервера:\r\n" + jsonObject.toString(10) + "\r\nПользователя не активен");
    	else 
    	{
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10) + "\r\n");
    		print("Тест провален".toUpperCase());
    		throw new ExceptFailTest("Тест провален");
    	}
    	
		///////////////////////////////////////////////////////////////////////////////////////////////
    	
		print("\r\nАвторизация - Неактивный(не подтвердивший регистрацию) пользователь".toUpperCase());
		print("Параметры для запроса");
		print("email = " + Proper.GetProperty("login_authNotActive"));
		print("password = " + Proper.GetProperty("password"));
		builder = new URIBuilder();
		builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account/login")
		.setParameter("username", Proper.GetProperty("login_authNotActive"))
		.setParameter("password", Proper.GetProperty("password"));
		uri = builder.build();
		if(uri.toString().indexOf("%25") != -1)
		{
		String sTempUri = uri.toString().replace("%25", "%");
		uri = new URI(sTempUri);			
		}
		print("Отправляем запрос. Uri Запроса: "+uri.toString());
		sResponse = HttpPostRequest(uri);
		print("Парсим ответ....");
		jsonObject = ParseResponse(sResponse);
		
		jTemp = jsonObject.getJSONObject("error");
		sResult = jTemp.getString("description");
		
		if(sResult.equals("Пользователя с такими данными не существует"))
		print("Ответ сервера:\r\n" + jsonObject.toString(10) + "\r\nПользователя не существует");
		else 
		{
		print("Ответ сервера:\r\n"+ jsonObject.toString(10) + "\r\n");
		print("Тест провален".toUpperCase());
		throw new ExceptFailTest("Тест провален");
		}
		
		print("------------------------------------------------------------------------------------------------------------");
    	print("Тест завершен успешно".toUpperCase());
    	
	}
	// Получение/Редактирование профиля Автотест
	public void GetAndEditProfile(String sHost) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		JSONObject jTemp, jData;
		String jLogin="", jEmail="";
		
		String sLogin = Proper.GetProperty("login_authOP");
		String sPassword = Proper.GetProperty("password");
		String sAuth_token = "";
		print("------------------------------------------------------------------------------------------------------------");
		print("Авторизация, получение, редактирование профиля - Тест".toUpperCase()+"\r\n");
		sAuth_token = Authorization_1_1(sHost, sLogin, sPassword);
		
		print("\r\nПолучение профиля".toUpperCase());
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account")
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10)+"\r\nПрофиль получен.");
    		print("Проверяем совпадение логина и email");
    		jTemp = jsonObject.getJSONObject("user_info"); 
    		jData = jTemp; // для проверки и сравнения данных
    		jLogin = jTemp.getString("login"); // используем при сравнени после редактирования профиля
    		jEmail = jTemp.getString("email"); // используем при сравнени после редактирования профиля
    		
    		if(jTemp.getString("login").equals(sLogin) && jTemp.getString("email").equals(sLogin))
    		{
    			print("Логин пользователя: "+ sLogin + " для которого запрашивается профиль, совпал с логином: "+ jTemp.getString("login") + " полученным в профиле");
    			print("Email пользователя: "+ sLogin + " для которого запрашивается профиль, совпал с логином: "+ jTemp.getString("email") + " полученным в профиле");
    		}
    		else
    		{
    			print("Тест провален. Логин: " + sLogin +" или Email: " + sLogin + " пользователя для котрого запрашивалсяя профиль," +
    					" не совпали с полученным логином: "+ jTemp.getString("login") + " или Email: " + jTemp.getString("email"));	
    			print("Тест провален".toUpperCase());
    			throw new ExceptFailTest("Тест провален");
    		}
    	}
    	else
    	{
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10)+"");
    		print("Тест провален");
    		throw new ExceptFailTest("Тест провален");
    	}
    	
    	print("\r\nРедактирование профиля".toUpperCase());
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("Генерируем данные");
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		HM<String, String> hObj = new HM<String, String>(); 
		String mas[] = {"site", "zip", "building", "phone", "other_email", "fax", "street", "icq", "contact", "dont_subscribe", "city",
				"title", "address", "mobile", "email", "login"};
		
		for(int i=0; i<mas.length; i++)
		{
			hObj.SetValue(mas[i], RamdomData.GetRandomData(Proper.GetProperty(mas[i]), jData.getString(mas[i])));
		}
		
		String sQuery = CreateArrayRequest("user_info", hObj.GetStringFromAllHashMap());
		print("user_info = "+ hObj.GetStringFromAllHashMap());
		
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account")
    		.setQuery(sQuery)
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	sResponse = HttpPutRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	
    	if(jsonObject.isNull("error"))
    	{	
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10));
    		print("Проверяем изменения данных для профиля");
    		jTemp = jsonObject.getJSONObject("user_info"); 
    		jData = jTemp; // для проверки и сравнения данных
    		for(int i=0; i<mas.length; i++)
    		{
    			if(mas[i].equals("login") || mas[i].equals("email"))
    			{
	    			// проверяем не изменился ли login
	    			if(mas[i].equals("login"))
	    			{
	    				if(jLogin.equals(jData.getString(mas[i])))
	    					print("Значение login = " + jLogin + 
		    						" не изменилось после редактирования профиля " + mas[i] + " = " + jData.getString(mas[i]));
		    			else
		    			{
		    				print("Значение login = " + jLogin + 
		    						" изменилось после редактирования профиля " + mas[i] + " = " + jData.getString(mas[i]));
		    				print("Тест провален".toUpperCase());
		    				throw new ExceptFailTest("Тест провален");
		    			}
	    			}
	    			// проверяем не изменился ли email
	    			if(mas[i].equals("email"))
	    			{
	    				if(jEmail.equals(jData.getString(mas[i])))
		    				print("Значение email = " + jEmail + 
		    						" не изменилось после редактирования профиля " + mas[i] + " = " + jData.getString(mas[i]));
		    			else
		    			{
		    				print("Значение профиля email = " + jEmail + 
		    						" изменилось после редактирования профиля " + mas[i] + " = " + jData.getString(mas[i]));
		    				print("Тест провален".toUpperCase());
		    				throw new ExceptFailTest("Тест провален");
		    			}
	    			}
    			}
    			else
    			{
					// проверяем изменились ли другие данные
					if(hObj.GetValue(mas[i]).equals(jData.getString(mas[i])))
						print("Значение " + mas[i] +" = " + hObj.GetValue(mas[i]) + " указанное для запроса редактирования профиля," +
								" совпало с полученным значение в профиле после редактирования" + mas[i] + " = " + jData.getString(mas[i]));
					else
					{
						print("Значение " + mas[i] +" = " + hObj.GetValue(mas[i]) + " указанное для запроса редактирования профиля," +
								" не совпало с полученным значение в профиле после редактирования " + mas[i] + " = " + jData.getString(mas[i]));
						print("Тест провален".toUpperCase());
						throw new ExceptFailTest("Тест провален");
					}
    			}
    			
    		}
    	}
    	else
    	{
    		print("Тест провален".toUpperCase());
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10));
    		throw new ExceptFailTest("Тест провален");
    	} 
    	print("------------------------------------------------------------------------------------------------------------");
    	print("Тест завершен успешно".toUpperCase());
	}
	// Восстановления пароля Автотест
	public void RestorePassword(String sHost) throws URISyntaxException, IOException, ExceptFailTest, JSONException
	{
		print("------------------------------------------------------------------------------------------------------------");
		print("Восстановление пароля - Тест".toUpperCase()+"\r\n");
		print("Восстановление пароля".toUpperCase());
		print("Параметры для запроса");
		print("email = "+ Proper.GetProperty("login_authOP"));
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account/restore")
    		.setParameter("email", Proper.GetProperty("login_authOP"));
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString(10) + "\r\n На указанный email отправлено письмо восстановления с инструкцией по восстановлению пароля");
    	else
    	{
    		print("Не удалось восстановить пароль\r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		print("Тест провален".toUpperCase());
    		throw new ExceptFailTest("Тест провален");
    	}
    	print("------------------------------------------------------------------------------------------------------------");
    	print("Тест завершен успешно".toUpperCase());
	}
	// Подача/Получение/Редактирование объявление Автотест
	public void AddGetEditAdvert(String sHost) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String sLogin = Proper.GetProperty("login_authOP");
		String sPassword = Proper.GetProperty("password");
		String sAuth_token = "";
		print("------------------------------------------------------------------------------------------------------------");
		print("Подача, получение, редактирование объявления - Тест".toUpperCase()+"\r\n");
		sAuth_token = Authorization_1_1(sHost, sLogin, sPassword);
		
		//////////////////
		print("\r\nПодача объявления в рубрику Авто с пробегом".toUpperCase());
		print("Параметры для запроса");
		print("sAuth_token = "+ sAuth_token);
		print("sCatRegAdv = "+ Proper.GetProperty("category_auto"));
		print("Генерируем данные");
		
		String sVideo = "&advertisement[video]="+Proper.GetProperty("video");
		print(sVideo+"\r\n");
		String sRequest = CreateSimpleRequest(Proper.GetProperty("category_auto"));
		print(sRequest+"\r\n");
		
		
		//генерим advertisement 
		HM<String, String> hObj = new HM<String, String>(); 
		String mas[] = {"email", "phone", "phone_add", "contact", "phone2", "phone_add2", "altermative_contact", "web"};
		for(int i=0; i<mas.length; i++)
		{
			hObj.SetValue(mas[i], RamdomData.GetRandomData(Proper.GetProperty(mas[i]), ""));
		}
		
		String sRequest1 = CreateArrayRequest("advertisement",  hObj.GetStringFromAllHashMap());
		print(sRequest1+"\r\n");
		
		// генерим advertisement [custom_fields]
		HM<String, String> hObj2 = new HM<String, String>(); 
		String mas2[] = {"make", "model", "mileage", "engine-power", "condition", "car-year", "transmittion", "currency",
				"modification", "price", "bodytype", "electromirror", "cruiscontrol", "color"};
		for(int i=0; i<mas2.length; i++)
		{
			hObj2.SetValue(mas2[i], RamdomData.GetRandomData(Proper.GetProperty(mas2[i]), ""));
		}
		
		hObj2.PrintKeyAndValue();
		
		String sRequest2 = CreateDoubleArrayRequest("advertisement", "custom_fields",  hObj2.GetStringFromAllHashMap());
		print(sRequest2);
		
		
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert")
    		.setQuery(sRequest+sRequest1+sRequest2+sVideo)
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpPostRequestImage(uri, Proper.GetProperty("image"));
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("\r\nОтвет сервера:\r\n" + jsonObject.toString(10) + "\r\nОбъявление создано");
    		
    	else
    	{
    		print("Не удалось создать объявление\r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// Создание профиля	
	public void CreateProfileRequest_1(String sHost, String sEmail, String sPassword) throws URISyntaxException, IOException, ExceptFailTest
	{
		print("1.	Создание профиля");
		print("Параметры для запроса");
		print("email = "+ sEmail);
		print("password = "+ sPassword);
		builder = new URIBuilder();
		
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account")
    		.setParameter("email", sEmail)
    		.setParameter("password", sPassword);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	// Проверка что получили
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString() + "Профиль пользователя создан");
    	else
    	{
    		print("Не удалось создать профилль пользователя\r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}
	}	
	// Авторизация
	public String Authorization_1_1(String sHost, String sUsername, String sPassword) throws URISyntaxException, IOException, ExceptFailTest, JSONException
	{
		print("1.1.	Авторизация".toUpperCase());
		print("Параметры для запроса");
		print("email = "+ sUsername);
		print("password = "+ sPassword);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account/login")
    		.setParameter("username", sUsername)
    		.setParameter("password", sPassword);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	
    	jsonObject = ParseResponse(sResponse);
    	String sTempResponse = jsonObject.toString();
    	
    	if(sTempResponse.equals("{\"error\":{\"description\":\"Не указан логин или пароль\",\"code\":1}}"))
    	{
    		print("Не указан логин или пароль");
    		print("Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}
    	if(sTempResponse.equals("{\"error\":{\"description\":\"Пользователя с такими данными не существует\",\"code\":3}}"))
    	{
    		print("Пользователя с такими данными не существует");
    		print("Ответ сервера:\r\n"+ jsonObject.toString() + "\r\n");
    		throw new ExceptFailTest("Тест провален");
    	}
    	if(sTempResponse.equals("{\"error\":{\"description\":\"Пользователь не активный\",\"code\":6}}"))
    	{
    		print("Пользовател неактивен или забанен");
    		print("Ответ сервера:\r\n"+ jsonObject.toString() + "\r\n");
    		throw new ExceptFailTest("Тест провален");
    	}
    	
    	String sAuth_token = (String) jsonObject.get("auth_token");
    	if(sAuth_token != null)
    	{
    	         print("Auth_token = "+ sAuth_token);
    	         print("Ответ сервера:\r\n"+ jsonObject.toString(10) + "\r\nПользователь авторизован");
    	         return sAuth_token;
    	}
    	else 
    	{
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10) + "\r\n");
    		throw new ExceptFailTest("Тест провален");
    	}
	}
	// Получение профиля
	public String GetProfile_1_2(String sHost,String sUsername, String sPassword, boolean bAuthFlag) throws URISyntaxException, IOException, ExceptFailTest, JSONException
	{
		String  sAuth_token= "";
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		print("1.2.	Получение профиля".toUpperCase());
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account")
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	
    	
    	print("Ответ сервера:\r\n"+ jsonObject.toString(10));
    	
    	if(jsonObject.isNull("error"))
    	{
    		return sAuth_token;
    	}
    	else
    	{
    		print("Тест провален");
    		throw new ExceptFailTest("Тест провален");
    	}
    	
	}
	// Редактирование профиля
	public void EditProfile_1_3(String sHost,String sUsername, String sPassword, String sUser_info, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		sAuth_token = GetProfile_1_2(sHost, sUsername, sPassword, bAuthFlag);
		print("1.3.	Редактирование профиля");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("user_info = "+ sUser_info);
		String sQuery = CreateArrayRequest("user_info", sUser_info);
		
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account")
    		.setQuery(sQuery)
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpPutRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10));
    	else
    	{
    		print("Тест провален");
    		print("Ответ сервера:\r\n"+ jsonObject.toString(10));
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Восстановление пароля
	public void RestorePassword1_4(String sHost, String sEmail) throws URISyntaxException, IOException, ExceptFailTest
	{
		print("1.4.	Восстановление пароля");
		print("Параметры для запроса");
		print("email = "+ sEmail);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/account/restore")
    		.setParameter("email", sEmail);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString() + " На указанный email отправлено письмо восстановления с инструкцией по восстановлению пароля");
    	else
    	{
    		print("Не удалось восстановить пароль\r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}
    	
	}
	
	// Подача объявления
	public void PostAdvert_2_1(String sHost, String sUsername, String sPassword, String sCatRegAdv,  String sAdvertisement, String sCustom_fields, String sPathImage, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		print("");
		print("2.1.	Подача объявления");
		print("Параметры для запроса");
		print("sCatRegAdv = "+ sCatRegAdv);
		print("sAdvertisement = "+ sAdvertisement);
		print("sCustom_fields = "+ sCustom_fields);
		print("sAuth_token = "+ sAuth_token);
		
		String sRequest = CreateSimpleRequest(sCatRegAdv);
		String sRequest1 = CreateArrayRequest("advertisement" ,sAdvertisement);
		String sRequest2 = CreateDoubleArrayRequest("advertisement", "custom_fields", sCustom_fields);
		
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert")
    		.setQuery(sRequest+sRequest1+sRequest2)
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpPostRequestImage(uri, sPathImage);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("\r\nОтвет сервера:\r\n" + jsonObject.toString(10) + "\r\nОбъявление создано");
    		
    	else
    	{
    		print("Не удалось создать объявление\r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}
    	
	}
	// Получение объявления
	public String GetAdvert_2_2(String sHost, String sIdAdvert) throws URISyntaxException, IOException, ExceptFailTest, JSONException
	{
		print("2.2.	Получение объявления");
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/"+ sIdAdvert);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	
    	if(jsonObject.isNull("error"))
    	{	
    		print("Ответ сервера:" + jsonObject.toString(2) + " Объявление получено");
    		
    		print("Ищем ссылку на изображение в объявлении");
    		JSONObject jTemp = jsonObject.getJSONObject("advertisement");
    		String s = (String) jTemp.get("images").toString();
    		if(s.equals("[]"))
    			return "false";
    		else
    		{
    			JSONArray ar = (JSONArray) jTemp.get("images");
    			jTemp = (JSONObject) ar.get(0);
    			print(jTemp.get("orig").toString());
    			return jTemp.get("orig").toString();
    		}
    	}
    	else
    	{
    		print("Объявление не получено\r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}
	}
	// Редактирование объявления
	public void EditAdvert_2_3(String sHost, String sUsername, String sPassword, String sIdAdvert, String sCustom_fields, String sPathImageNew, boolean bAuthFlag) throws URISyntaxException, IOException, ExceptFailTest, JSONException
	{
		String  sAuth_token= "";
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующие запросы уйдет пустой ключ auth_token");
		String sUrlImage = GetAdvert_2_2(sHost, sIdAdvert);
		print("");
		print("2.3.	Редактирование объявления");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("ADVERTISEMENT_ID = "+ sIdAdvert);
		if(sUrlImage.equals("false"))
		{
			print("В объявлении нет изображений ");
		}
		else
		{
			print("UrlImage = "+ sUrlImage +"\r\n");
		}
		
		String sRequest = CreateDoubleArrayRequest("advertisement", "custom_fields", sCustom_fields);
		String sRequest1 = CreateArrayRequest("advertisement" ,"{contact=Димон}");
		
		builder = new URIBuilder();
		
		if(!sUrlImage.equals("false"))
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/"+ sIdAdvert)
    		.setQuery(sRequest1 + sRequest + "&deleted_images[0]=" + sUrlImage).setParameter("auth_token", sAuth_token);

    	else
    		builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/"+ sIdAdvert)
    		.setQuery(sRequest).setParameter("auth_token", sAuth_token);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpPutRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("\r\nОтвет сервера:\r\n" + jsonObject.toString(10) + "\r\n Объявление отредактировано");
    		
    	else
    	{
    		print("Не удалось отредактировать объявление\r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}
		
	}
	// Удаление объявления
	public void DeleteAdvert_2_4(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующие запросы уйдет пустой ключ auth_token");
		
		print("2.4.	Удаление объявления");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert)
    			.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpDeleteRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString() + " Объявление удалено проверьте ЛК");
    	else
    	{
    		print("Не удалось удалить объявление \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Добавление объявления в избранное
	public void AddAdvertToFavourite_2_5(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		
		String  sAuth_token= "";
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		print("2.5.	Добавление объявления в «Избранное»");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert +"/favorite")
    			.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString() + " Объявление добавлено в избранное, проверьте вкладку избранные для данного пользователя");
    	else
    	{
    		print("Не удалось добавить объявление \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Удаления объявления из избранного
	public void DeleteAdvertFromFavourite_2_6(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		print("2.4.	Удаление объявления из «Избранное»");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("ADVERTISEMENT_ID = "+ sIdAdvert);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert +"/favorite")
    			.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	String sResponse = HttpDeleteRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString() + " Объявление удалено из избранного, проверьте вкладку");
    	else
    	{
    		print("Не удалось удалить объявление из избранного \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Получение списка платных продуктов для объявления доступных на этапе подачи объявления
	public void GetPaidProductsToStepToAdd_2_7(String sHost, String sIdAdvert) throws URISyntaxException, IOException, ExceptFailTest, JSONException
	{
		print("2.7.	Получение списка платных продуктов для объявления доступных на этапе подачи объявления");
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/products");
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString(10) + "\r\nСписок получен");
    	else
    	{
    		print("Не удалось получить список продуктов \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Получение списка платных продуктов для объявления доступных в личном кабинете пользователя
	public void GetPaidProductsFromLK_2_8(String sHost, String sIdAdvert) throws ExceptFailTest, URISyntaxException, IOException, JSONException
	{
		print("2.8.	Получение списка платных продуктов для объявления доступных в личном кабинете пользователя");
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/products/pers_acc");
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString(10) + "\r\nСписок получен");
    	else
    	{
    		print("Не удалось получить список продуктов \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Получение списка бесплатных действий над объявлением
	public void GetFreeProductsForAdvert_2_9(String sHost, String sIdAdvert) throws ExceptFailTest, URISyntaxException, IOException
	{
		print("2.9.	Получение списка бесплатных действий над объявлением");
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/actions");
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString() + " Список получен");
    	else
    	{
    		print("Не удалось получить список продуктов \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Активация объявлений
	public void ActivationAdvert_2_10(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bApp_token, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		String  sApp_token="";
		
		if(bApp_token)
			sApp_token = "true";
		else print("Передан параметр не передавать ключ оплаты App_token. В следующий запрос уйдет пустой ключ app_token");
			
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		print("2.10.	Активация объявлений".toUpperCase());
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("ADVERTISEMENT_ID = "+ sIdAdvert);
		print("sApp_token = "+ sApp_token);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/activate")
    		.setParameter("auth_token", sAuth_token)
    		.setParameter("app_token", sApp_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString() + " Объявление активировано");
    	else
    	{
    		print("Не удалось активировать объявление \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
		
	}
	// Деактивация объявлений
	public void DeactivateAdvert_2_11(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		print("2.11.	Деактивация объявления");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("ADVERTISEMENT_ID = "+ sIdAdvert);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/deactivate")
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	

    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString() + " Объявление деактивировано");
    	else
    	{
    		print("Не удалось деактивировать объявление \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
    	
	}
	// Продление объявления
	public void Prolongadvert_2_12(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bApp_token, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		String  sApp_token="";
		
		if(bApp_token)
			sApp_token = "true";
		else print("Передан параметр не передавать ключ оплаты App_token. В следующий запрос уйдет пустой ключ app_token");
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		print("2.12.	Продление объявления");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("ADVERTISEMENT_ID = "+ sIdAdvert);
		print("sApp_token = "+ sApp_token);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/prolong")
    		.setParameter("auth_token", sAuth_token)
    		.setParameter("app_token", sApp_token);;
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	

    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		if(jsonObject.toString().equals("{\"error\":null,\"actions\":false}"))
    			print("Ответ сервера:" + jsonObject.toString() + " Объявление не продлено (возможно оно неактивно, неоплачено)");
    		else 
    			print("Ответ сервера:" + jsonObject.toString() + " Объявление продлено");
    	}
    	else
    	{
    		print("Не удалось продлить объявление \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Поднятие объявления
	public void PushUpAdvert_2_13(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bApp_token,  boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		String  sApp_token="";
		
		if(bApp_token)
			sApp_token = "true";
		else print("Передан параметр не передавать ключ оплаты App_token. В следующий запрос уйдет пустой ключ app_token");
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		print("2.13.	Поднятие объявления в списке");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("ADVERTISEMENT_ID = "+ sIdAdvert);
		print("sApp_token = "+ sApp_token);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/pushup")
    		.setParameter("auth_token", sAuth_token)
    		.setParameter("app_token", sApp_token);;
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	

    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		if(jsonObject.toString().equals("{\"error\":null,\"actions\":false}"))
    			print("Ответ сервера:" + jsonObject.toString() + " Объявление не поднято (возможно оно неактивно, неоплачено)");
    		else 
    			print("Ответ сервера:" + jsonObject.toString() + " Объявление поднято");
    	}
    	else
    	{
    		print("Не удалось поднять объявление \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Выделение объявления 
	public void HighLightAdvert_2_14(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bApp_token, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		String  sApp_token="";
		
		if(bApp_token)
			sApp_token = "true";
		else print("Передан параметр не передавать ключ оплаты App_token. В следующий запрос уйдет пустой ключ app_token");
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		print("2.14. Выделения объявления в списке");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("ADVERTISEMENT_ID = "+ sIdAdvert);
		print("sApp_token = "+ sApp_token);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/highlight")
    		.setParameter("auth_token", sAuth_token)
    		.setParameter("app_token", sApp_token);;
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		if(jsonObject.toString().equals("{\"error\":null,\"actions\":false}"))
    			print("Ответ сервера:" + jsonObject.toString() + " Объявление не выделено (возможно оно неактивно, неоплачено)");
    		else 
    			print("Ответ сервера:" + jsonObject.toString() + " Объявление выделено");
    	}
    	else
    	{
    		print("Не удалось выделить объявление \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Назначение «Премиум» объявлению
	public void SetPremiumForAdvert_2_15(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bApp_token, String sNumberDays, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		String  sApp_token="";
		
		if(bApp_token)
			sApp_token = "true";
		else print("Передан параметр не передавать ключ оплаты App_token. В следующий запрос уйдет пустой ключ app_token");
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		print("2.15. Назначение «Премиум» объявлению");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("ADVERTISEMENT_ID = "+ sIdAdvert);
		print("sApp_token = "+ sApp_token);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/premium")
    		.setParameter("auth_token", sAuth_token)
    		.setParameter("app_token", sApp_token)
    		.setParameter("number", sNumberDays);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		if(jsonObject.toString().equals("{\"error\":null,\"actions\":false}"))
    			print("Ответ сервера:" + jsonObject.toString() + " Объявление не назначен премиум (возможно оно неактивно, неоплачено)");
    		else 
    			print("Ответ сервера:" + jsonObject.toString() + " Объявление назначен премиум");
    	}
    	else
    	{
    		print("Не удалось назначить премиум объявление \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Проголосовать за объявление (повысить рейтинг объявления)
	public void VoteForAdvertHigh_2_16(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		print("2.16.	Проголосовать за объявление (повысить рейтинг объявления)");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("ADVERTISEMENT_ID = "+ sIdAdvert);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/vote")
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpPostRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString() + " Объявлению +1 голос");
    	else
    	{
    		print("Не удалось добавить голос объявлению \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Проголосовать за объявление (снизить рейтинг объявления)
	public void VoteForAdvertLower_2_17(String sHost, String sUsername, String sPassword, String sIdAdvert, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		print("2.17.	Проголосовать за объявление (снизить рейтинг объявления)");
		print("Параметры для запроса");
		print("auth_token = "+ sAuth_token);
		print("ADVERTISEMENT_ID = "+ sIdAdvert);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/advert/" + sIdAdvert + "/vote")
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpDeleteRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    		print("Ответ сервера:" + jsonObject.toString() + " Объявлению -1 голос");
    	else
    	{
    		print("Не удалось отнять голос у объявления \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Получение листинга объявлений категории
	public void GetListForCategory_2_18(String sHost, String sDataForListing) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		print("2.18.	Получение листинга объявлений категории");
		print("Параметры для запроса");
		print("DataForListing = "+ sDataForListing);
		
		String sQuery = CreateSimpleRequest(sDataForListing);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/category")
    		.setQuery(sQuery);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера: Листинг объявлений получен");
    		print("");
    		JSONArray ar = jsonObject.getJSONArray("advertisements");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("Объявление №" + i);
    			jsonObject = (JSONObject) ar.get(i);
    			print(jsonObject.toString(10));
    		
    		}
    	}
    	else
    	{
    		print("Не удалось получить листинг категории \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	// Фильтрация/поиск объявлений по критериям 
	public void GetListSearchCategory_2_19(String sHost, String sDataForListing, String sDataForSearch) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		print("2.19.	Фильтрация/поиск объявлений по критериям");
		print("Параметры для запроса");
		print("DataForListing = "+ sDataForListing);
		
		String sQuery = CreateSimpleRequest(sDataForListing);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/search")
    		.setQuery(sQuery);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
   
    	String ss =	"/&filters=/search/"+sDataForSearch;
    	String s1 = uri.toString()+ss;
    	uri = new URI(s1);
    	
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера: Листинг объявлений получен");
    		print("");
    		JSONArray ar = jsonObject.getJSONArray("advertisements");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("Объявление №" + i);
    			jsonObject = (JSONObject) ar.get(i);
    			print(jsonObject.toString(10));
    		
    		}
    	}
    	else
    	{
    		print("Не удалось получить фильтр-листинг по критериям \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	//2.20.	Получение листинга объявлений, добавленных в «Избранное»
	public void GetListFavourite_2_20(String sHost, String sUsername, String sPassword, String sDataForSearchFavourite, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		
		print("2.20.	Получение листинга объявлений, добавленных в «Избранное»");
		print("Параметры для запроса");
		print("DataForSearchFavourite = "+ sDataForSearchFavourite);
		
		
		String sQuery = CreateSimpleRequest(sDataForSearchFavourite);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/favorites")
    		.setQuery(sQuery)
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера: Листинг объявлений, добавленных в «Избранное» получен");
    		JSONArray ar = jsonObject.getJSONArray("advertisements");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("Объявление №" + i);
    			jsonObject = (JSONObject) ar.get(i);
    			print(jsonObject.toString(10));
    		
    		}
    	}
    	else
    	{
    		print("Не удалось получить листинг объявлений, добавленных в «Избранное» \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	//2.21.	Получение листинга «своих» объявлений
	public void GetListOwnAdvert_2_21(String sHost, String sUsername, String sPassword, String sDataForSearchOwnAdvert, boolean bAuthFlag) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{
		String  sAuth_token= "";
		if(bAuthFlag)
		{
			sAuth_token = Authorization_1_1(sHost, sUsername, sPassword);
		}
		else print("Передан параметр не авторизовывать пользователя. В следующий запрос уйдет пустой ключ auth_token");
		
		
		print("2.21.	Получение листинга «своих» объявлений".toUpperCase());
		print("Параметры для запроса");
		print("DataForSearchOwnAdvert = "+ sDataForSearchOwnAdvert);
		
		
		String sQuery = CreateSimpleRequest(sDataForSearchOwnAdvert);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/personal")
    		.setQuery(sQuery)
    		.setParameter("auth_token", sAuth_token);
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера: Листинг «своих» объявлений получен");
    		print("");
    		JSONArray ar = jsonObject.getJSONArray("advertisements");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("Объявление №" + i);
    			jsonObject = (JSONObject) ar.get(i);
    			print(jsonObject.toString(10));
    		
    		}
    	}
    	else
    	{
    		print("Не удалось получить листинг «своих» объявлений \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	//2.22.	Получение листинга объявлений пользователя
	public void GetListUserAdvert_2_22(String sHost, String sDataForSearchUserAdvert) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("2.22.	Получение листинга объявлений пользователя");
		print("Параметры для запроса");
		print("DataForSearchUserAdvert = "+ sDataForSearchUserAdvert);
		
		
		String sQuery = CreateSimpleRequest(sDataForSearchUserAdvert);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/advertisements/user")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера: Листинг объявлений пользователя получен");
    		JSONArray ar = jsonObject.getJSONArray("advertisements");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("Объявление №" + i);
    			jsonObject = (JSONObject) ar.get(i);
    			print(jsonObject.toString(10));
    		
    		}
    	}
    	else
    	{
    		print("Не удалось получить листинг объявлений пользователя \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	
	//3.1.	Получение рубрикатора сайта
	public void GetRubricator_3_1(String sHost, String sCategory) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("3.1.	Получение рубрикатора сайта");
		print("Параметры для запроса");
		print("category = "+ sCategory);
		
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/categories")
    		.setParameter("category", sCategory);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:" + jsonObject.toString() + "рубрикатора сайта получен");
    		print("");
    		JSONArray ar = jsonObject.getJSONArray("categories");
    		for(int i=0; i<ar.length(); i++)
    			print(ar.get(i));
    	}
    	else
    	{
    		print("Не удалось получить рубрикатора сайта \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}	
	//3.2.	Получение списка полей рубрики для подачи объявления
	public void GetCastomfieldsForAddAdvert_3_2(String sHost, String sDataCustomfieldsAdvert) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("3.2.	Получение списка полей рубрики для подачи объявления");
		print("Параметры для запроса");
		print("DataCustomfieldsAdvert = "+ sDataCustomfieldsAdvert);
		String sQuery = CreateSimpleRequest(sDataCustomfieldsAdvert);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/categories/fields/post")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	JSONObject jsonTemp;
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:" + jsonObject.toString() + "список полей рубрики для подачи объявления получен");
    		print("--------------------------------------------------------------------------------------------------------------");
			print("group_custom_fields");
			jsonTemp = jsonObject.getJSONObject("group_custom_fields");
			print(jsonTemp.toString(10));
			
			
			JSONArray ar = jsonObject.getJSONArray("video");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("video");
    			jsonTemp = (JSONObject) ar.get(i);
    			print(jsonTemp.toString(10));
    		
    		}
			
    		ar = jsonObject.getJSONArray("contacts");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("contacts");
    			jsonTemp = (JSONObject) ar.get(i);
    			print(jsonTemp.toString(10));
    		
    		}
    	}
    	else
    	{
    		print("Не удалось получить список полей рубрики для подачи объявления \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	//3.3.	Получение списка полей рубрики для редактирования объявления
	public void GetCastomfieldsForEditAdvert_3_3(String sHost, String sDataCustomfieldsEditAdvert) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("3.3.	Получение списка полей рубрики для редактирования объявления");
		print("Параметры для запроса");
		print("DataCustomfieldsEditAdvert = "+ sDataCustomfieldsEditAdvert);
		String sQuery = CreateSimpleRequest(sDataCustomfieldsEditAdvert);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/categories/fields/edit")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	JSONObject jsonTemp;
    	
    	if(jsonObject.isNull("error"))
    	{
	    	print("Ответ сервера. Cписок полей рубрики для редактирования объявлений получен \r\n");
	    	
	    	print("--------------------------------------------------------------------------------------------------------------");
			print("group_custom_fields");
			jsonTemp = jsonObject.getJSONObject("group_custom_fields");
			print(jsonTemp.toString(10));
			
			
			JSONArray ar = jsonObject.getJSONArray("video");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("video");
    			jsonTemp = (JSONObject) ar.get(i);
    			print(jsonTemp.toString(10));
    		
    		}
			
    		ar = jsonObject.getJSONArray("contacts");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("contacts");
    			jsonTemp = (JSONObject) ar.get(i);
    			print(jsonTemp.toString(10));
    		
    		}
			
    	}
    	else
    	{
    		print("Не удалось получить список полей рубрики для редактирования объявления получен \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	//3.4.	Получение списка полей рубрики для фильтрации объявлений
	public void GetCastomfieldsForSearchAdvert_3_4(String sHost, String sDataCustomfieldsEditAdvert) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("3.4.	Получение списка полей рубрики для фильтрации объявлений");
		print("Параметры для запроса");
		print("DataCustomfieldsEditAdvert = "+ sDataCustomfieldsEditAdvert);
		String sQuery = CreateSimpleRequest(sDataCustomfieldsEditAdvert);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/categories/fields/search")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	JSONObject jsonTemp;
    	jsonObject = ParseResponse(sResponse);
    	jsonTemp = jsonObject;
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера. Cписок полей рубрики для фильтрации объявлений получен");
    		print("");
    		JSONArray ar = jsonTemp.getJSONArray("default");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("default");
    			jsonObject = (JSONObject) ar.get(i);
    			print(jsonObject.toString(10));
    		
    		}
    		ar = jsonTemp.getJSONArray("extended");
    		for(int i=0; i<ar.length(); i++)
    		{
    			print("--------------------------------------------------------------------------------------------------------------");
    			print("extended");
    			jsonObject = (JSONObject) ar.get(i);
    			print(jsonObject.toString(10));
    		
    		}
    	}
    	else
    	{
    		print("Не удалось получить список полей рубрики для фильтрации объявлений \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	
	//4.1.	Получение списка субъектов РФ
	public void GetRegions_4_1(String sHost) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("4.1.	Получение списка субъектов РФ");
		
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions");
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:" + jsonObject.toString() + "список субъектов РФ получен");
    		JSONArray ar = jsonObject.getJSONArray("regions");
    		for(int i=0; i<ar.length(); i++)
    			print(ar.get(i));
    	}
    	else
    	{
    		print("Не удалось получить список субъектов РФ \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	//4.2.	Получение списка популярных городов
	public void GetPopularCities_4_2(String sHost, String sRegion) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("4.3. Получение списка городов, принадлежащих определенному субъекту РФ".toUpperCase());
		print("Параметры для запроса");
		print("region = "+ sRegion);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/cities")
    	.setParameter("region", sRegion);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера: \r\n" + jsonObject.toString(10) + "\r\nсписок популярных городов получен");
    	}
    	else
    	{
    		print("Не удалось получить популярных городов \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	//4.2.1.	Получение списка городов, для которых заведены поддомены
	public void GetCitiesWithDomen_4_2_1(String sHost) throws URISyntaxException, IOException, JSONException, ExceptFailTest
		{

			print("4.2.	Получение списка городов, для которых заведены поддомены".toUpperCase());
			print("Параметры для запроса");
			builder = new URIBuilder();
	    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/popular_cities");
	    	
	    	uri = builder.build();
	    	if(uri.toString().indexOf("%25") != -1)
	    	{
	    		String sTempUri = uri.toString().replace("%25", "%");
	    		uri = new URI(sTempUri);			
	    	}
	    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
	    	
	    	String sResponse = HttpGetRequest(uri);
	    	print("Парсим ответ....");
	    	
	    	jsonObject = ParseResponse(sResponse);
	    	if(jsonObject.isNull("error"))
	    	{
	    		print("Ответ сервера: \r\n" + jsonObject.toString(10) + "\r\nсписок городов, для которых заведены поддомены");
	    	}
	    	else
	    	{
	    		print("Не удалось получить список городов, для которых заведены поддомены \r\n"+
	    				"Ответ сервера:\r\n"+ jsonObject.toString());
	    		throw new ExceptFailTest("Тест провален");
	    	}	
		}
	// 4.3.	Поиск городов и населенных пунктов по названию (саджест)
	public void GetCitiesSuggest_4_3(String sHost, String sDataCitiesSuggest) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("4.4. Поиск городов и населенных пунктов по названию (саджест)".toUpperCase());
		print("Параметры для запроса");
		print("DataCitiesSuggest = "+ sDataCitiesSuggest);
	
		String sQuery = CreateSimpleRequest(sDataCitiesSuggest);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/search")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера: \r\n" + jsonObject.toString(10) + "\r\n список городов и населенных пунктов по названию (саджест) получен");
    	}
    	else
    	{
    		print("Не удалось получить городов и населенных пунктов по названию (саджест) \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	//4.4.	Получение списка улиц (саджест)	
	public void GetStreetsSuggest_4_4(String sHost, String sDataStreetsSuggest) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("4.5. Получение списка улиц (саджест)".toUpperCase());
		print("Параметры для запроса");
		print("DataStreetsSuggest = "+ sDataStreetsSuggest);
	
		String sQuery = CreateSimpleRequest(sDataStreetsSuggest);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/streets")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:\r\n" + jsonObject.toString(10) + "\r\nсписок улиц (саджест) получен");
    	}
    	else
    	{
    		print("Не удалось получить список улиц (саджест) \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}	
	//4.5.	Получение списка домов улицы (саджест)
	public void GetHousesSuggest_4_5(String sHost, String sDataHousesSuggest) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("4.6. Получение списка домов улицы (саджест)".toUpperCase());
		print("Параметры для запроса");
		print("DataHousesSuggest = "+ sDataHousesSuggest);
	
		String sQuery = CreateSimpleRequest(sDataHousesSuggest);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/houses")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:" + jsonObject.toString() + "\r\n список домов улицы (саджест) получен");
    		print("");
    		JSONArray ar = jsonObject.getJSONArray("houses");
    		for(int i=0; i<ar.length(); i++)
    			print(ar.get(i));
    	}
    	else
    	{
    		print("Не удалось получить список домов улицы (саджест) \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}	
	//4.6.	Получение списка районов (саджест)
	public void GetDistrictSuggest_4_6(String sHost, String sDataDistrictSuggest) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("4.7.	Получение списка районов (саджест)".toUpperCase());
		print("Параметры для запроса");
		print("DataDistrictSuggest = "+ sDataDistrictSuggest);
	
		String sQuery = CreateSimpleRequest(sDataDistrictSuggest);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/districts")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:" + jsonObject.toString() + "\r\nсписок районов (саджест) получен \r\n");
    		JSONArray ar = jsonObject.getJSONArray("districts");
    		for(int i=0; i<ar.length(); i++)
    			print(ar.get(i));
    	}
    	else
    	{
    		print("Не удалось получить список районов (саджест) \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}	
	//4.8	Получение списка районов (саджест)
	public void GetMicroDistrictSuggest_4_8(String sHost, String sDataMicroDistrictSuggest) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("4.8. Получение списка микрорайонов (саджест)".toUpperCase());
		print("Параметры для запроса");
		print("DataDistrictSuggest = "+ sDataMicroDistrictSuggest);
	
		String sQuery = CreateSimpleRequest(sDataMicroDistrictSuggest);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/microdistricts")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:\r\n" + jsonObject.toString(10) + "\r\nсписок микрорайонов (саджест) получен \r\n");
    	}
    	else
    	{
    		print("Не удалось получить список микрорайонов (саджест) \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	//4.9	Получение списка АО (саджест)
	public void GetAOSuggest_4_9(String sHost, String sAOSuggest) throws URISyntaxException, IOException, JSONException, ExceptFailTest
		{

			print("4.9. Получение списка административных округов (саджест)".toUpperCase());
			print("Параметры для запроса");
			print("DataDistrictSuggest = "+ sAOSuggest);
		
			String sQuery = CreateSimpleRequest(sAOSuggest);
			builder = new URIBuilder();
	    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/ao")
	    		.setQuery(sQuery);
	    	
	    	uri = builder.build();
	    	if(uri.toString().indexOf("%25") != -1)
	    	{
	    		String sTempUri = uri.toString().replace("%25", "%");
	    		uri = new URI(sTempUri);			
	    	}
	    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
	    	
	    	String sResponse = HttpGetRequest(uri);
	    	print("Парсим ответ....");
	    	
	    	jsonObject = ParseResponse(sResponse);
	    	if(jsonObject.isNull("error"))
	    	{
	    		print("Ответ сервера:\r\n" + jsonObject.toString(10) + "\r\nсписок административных округов (саджест) получен \r\n");
	    	}
	    	else
	    	{
	    		print("Не удалось получить список административных округов (саджест) \r\n"+
	    				"Ответ сервера:\r\n"+ jsonObject.toString());
	    		throw new ExceptFailTest("Тест провален");
	    	}	
		}
		//4.10.	Получение списка направлений (саджест)
public void GetDirectionSuggest_4_10(String sHost, String sDataDirectionSuggest) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("4.10. Получение списка направлений (саджест)".toUpperCase());
		print("Параметры для запроса");
		print("DataDirectionSuggest = "+ sDataDirectionSuggest);
	
		String sQuery = CreateSimpleRequest(sDataDirectionSuggest);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/directions")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:" + jsonObject.toString() + "\r\nсписок направлений (саджест) получен \r\n");
    		JSONArray ar = jsonObject.getJSONArray("directions");
    		for(int i=0; i<ar.length(); i++)
    			print(ar.get(i));
    	}
    	else
    	{
    		print("Не удалось получить список направлений (саджест) \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}	
	//4.11.	Получение списка шоссе (саджест)
	public void GetHighwaySuggest_4_11(String sHost, String sDataHighwaySuggest) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("4.11. Получение списка шоссе (саджест)".toUpperCase());
		print("Параметры для запроса");
		print("DataHighwaySuggest = "+ sDataHighwaySuggest);
	
		String sQuery = CreateSimpleRequest(sDataHighwaySuggest);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/highway")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:" + jsonObject.toString() + "\r\nсписок шоссе (саджест) получен\r\n");
    		JSONArray ar = jsonObject.getJSONArray("highways");
    		for(int i=0; i<ar.length(); i++)
    			print(ar.get(i));
    	}
    	else
    	{
    		print("Не удалось получить список шоссе (саджест) \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	//4.12.	Получение списка станций метро (саджест)
	public void GetMetroSuggest_4_12(String sHost, String sDataMetroSuggest) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("4.12.	Получение списка станций метро (саджест)".toUpperCase());
		print("Параметры для запроса");
		print("DataMetroSuggest = "+ sDataMetroSuggest);
	
		String sQuery = CreateSimpleRequest(sDataMetroSuggest);
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/regions/metro")
    		.setQuery(sQuery);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:" + jsonObject.toString() + "\r\nсписок станций метро (саджест) получен\r\n");
    		JSONArray ar = jsonObject.getJSONArray("metro");
    		for(int i=0; i<ar.length(); i++)
    			print(ar.get(i));
    	}
    	else
    	{
    		print("Не удалось получить список станций метро (саджест) \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
		
	//5.1.	Получение списка валют
	public void GetCurrencies_5_1(String sHost) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("5.1.	Получение списка валют");
	
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/currencies");
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:" + jsonObject.toString() + "\r\nсписок валют получен");
    	}
    	else
    	{
    		print("Не удалось получить список валют \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
		
	//6.1.	Получение значений словаря
	public void GetDictinary_6_1(String sHost, String sNameDict) throws URISyntaxException, IOException, JSONException, ExceptFailTest
	{

		print("6.1.	Получение значений словаря");
		print("Параметры для запроса");
		print("NameDictinary = "+ sNameDict);
		
		builder = new URIBuilder();
    	builder.setScheme("http").setHost(sHost).setPath("/mobile_api/1.0/dictionary/" + sNameDict);
    	
    	uri = builder.build();
    	if(uri.toString().indexOf("%25") != -1)
    	{
    		String sTempUri = uri.toString().replace("%25", "%");
    		uri = new URI(sTempUri);			
    	}
    	print("Отправляем запрос. Uri Запроса: "+uri.toString());
    	
    	String sResponse = HttpGetRequest(uri);
    	print("Парсим ответ....");
    	
    	jsonObject = ParseResponse(sResponse);
    	if(jsonObject.isNull("error"))
    	{
    		print("Ответ сервера:" + jsonObject.toString() + "\r\nзначения словаря получены\r\n");
    		JSONArray ar = jsonObject.getJSONArray("currencies");
    		for(int i=0; i<ar.length(); i++)
    			print(ar.get(i));
    	}
    	else
    	{
    		print("Не удалось получить значения словаря \r\n"+
    				"Ответ сервера:\r\n"+ jsonObject.toString());
    		throw new ExceptFailTest("Тест провален");
    	}	
	}
	
	private JSONObject ParseResponse(String sResponse) throws ExceptFailTest
	   {
		   JSONObject tempJsonObject = null;
		   try
	    	{
			   tempJsonObject = new JSONObject(sResponse);
	    	}
	    	catch(JSONException exc)
	    	{
	    		print("Не удалось распарсить ответ");
	    		print("Ответ на запрос:");
	    		print(sResponse+"\r\n");
	    		exc.printStackTrace();
	    		throw new ExceptFailTest("Не удалось распарсить ответ");
	    	}
		   	return tempJsonObject;
	   }
	
	
}

