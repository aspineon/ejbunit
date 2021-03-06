package com.github.andersonfonseka;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.ejb.EJB;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.xml.ws.WebServiceRef;

/**
 * @author anderson.fonseca
 *
 */
public class EJBUnit {
	
	private Object object;
	
	public EJBUnit() {
		super();
	}
	
	/**
	 * @param pClazz Receives a class to be instantiated and his dependents fields injected
	 * @return
	 */
	public Object getBean(Class<?> pClazz){
		
		try {

			ClassLoader loader = ClassLoader.getSystemClassLoader();
			Class<?> clazz = loader.loadClass(pClazz.getName());
			this.object = clazz.newInstance();;
			
			populate(clazz);
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
		return this.object;
		
	}

	
	/**
	 * @param clazz Injects dependent field recursively
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public void populate(Class<?> clazz) throws IllegalAccessException, InstantiationException {
		
		for(Field field: clazz.getDeclaredFields()){
			 for(Annotation annotation: field.getAnnotations()){
				 if (annotation.annotationType().isAssignableFrom(PersistenceContext.class)){
					 
					 EntityManagerFactory factory = Persistence.createEntityManagerFactory(((PersistenceContext) annotation).unitName()+"Test");
					 
					 field.setAccessible(true);
					 field.set(object, factory.createEntityManager());
				 }
			 }
		}
		 

		for(Field field: clazz.getSuperclass().getDeclaredFields()){
			 for(Annotation annotation: field.getAnnotations()){
				 if (annotation.annotationType().isAssignableFrom(PersistenceContext.class)){
					 
					 EntityManagerFactory factory = Persistence.createEntityManagerFactory(((PersistenceContext) annotation).unitName()+"Test");
					 
					 field.setAccessible(true);
					 field.set(object, factory.createEntityManager());
				 }
			 }
		}

		for(Field field: clazz.getSuperclass().getDeclaredFields()){
			 for(Annotation annotation: field.getAnnotations()){
				 if (annotation.annotationType().isAssignableFrom(EJB.class)){
					 field.setAccessible(true);
					 
					 Object tmp = field.getType().newInstance();
					 
					 field.set(object, tmp);
					 populate(field.get(object), field.get(object).getClass());
				 }
			 }
		}

		
		for(Field field: clazz.getDeclaredFields()){
			 for(Annotation annotation: field.getAnnotations()){
				 if (annotation.annotationType().isAssignableFrom(EJB.class)){
					 field.setAccessible(true);
					 
					 Object tmp = field.getType().newInstance();
					 
					 field.set(object, tmp);
					 populate(field.get(object), field.get(object).getClass());
				 }
			 }
		}


		for(Field field: clazz.getSuperclass().getDeclaredFields()){
			 for(Annotation annotation: field.getAnnotations()){
				 if (annotation.annotationType().isAssignableFrom(WebServiceRef.class)){
					 field.setAccessible(true);
					 Object tmp = field.getType().newInstance();
					 field.set(object, tmp);
				 }
			 }
		}

		
		for(Field field: clazz.getDeclaredFields()){
			 for(Annotation annotation: field.getAnnotations()){
				 if (annotation.annotationType().isAssignableFrom(WebServiceRef.class)){
					 field.setAccessible(true);
					 Object tmp = field.getType().newInstance();
					 field.set(object, tmp);
				 }
			 }
		}

		
	}
	
	
	/**
	 * @param object Object to be assigned the propreties
	 * @param clazz Injects dependent field recursively
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void populate(Object object, Class<?> clazz) throws IllegalAccessException, InstantiationException {
		
		for(Field field: clazz.getDeclaredFields()){
			 for(Annotation annotation: field.getAnnotations()){
				 if (annotation.annotationType().isAssignableFrom(PersistenceContext.class)){
					 
					 EntityManagerFactory factory = Persistence.createEntityManagerFactory(((PersistenceContext) annotation).unitName()+"Test");
					 
					 field.setAccessible(true);
					 field.set(object, factory.createEntityManager());
				 }
			 }
		}
		 

		for(Field field: clazz.getSuperclass().getDeclaredFields()){
			 for(Annotation annotation: field.getAnnotations()){
				 if (annotation.annotationType().isAssignableFrom(PersistenceContext.class)){
					 
					 EntityManagerFactory factory = Persistence.createEntityManagerFactory(((PersistenceContext) annotation).unitName()+"Test");
					 
					 field.setAccessible(true);
					 field.set(object, factory.createEntityManager());
				 }
			 }
		}
		
		for(Field field: clazz.getSuperclass().getDeclaredFields()){
			 for(Annotation annotation: field.getAnnotations()){
				 if (annotation.annotationType().isAssignableFrom(EJB.class)){
					 field.setAccessible(true);
					 field.set(object, field.getType().newInstance());
					 populate(field.get(object), field.get(object).getClass());
				 }
			 }
		}
		
		for(Field field: clazz.getDeclaredFields()){
			 for(Annotation annotation: field.getAnnotations()){
				 if (annotation.annotationType().isAssignableFrom(EJB.class)){
					 field.setAccessible(true);
					 field.set(object, field.getType().newInstance());
					 populate(field.get(object), field.get(object).getClass());
				 }
			 }
		}
	}

}
