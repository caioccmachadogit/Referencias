package com.htcom.padrao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface AnnotationField {
	String  tipo();
	double  quantidadeCaracteres();	
	boolean isPrimaryKey() ;
	boolean isForeignKey();
	String  tableReferencesFK();
	boolean isNull();
}


