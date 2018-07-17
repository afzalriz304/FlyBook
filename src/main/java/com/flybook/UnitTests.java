package com.flybook;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.flybook.serviceImpl.UsefulServicesImpl;

public class UnitTests {

	//load class
	public static UsefulServicesImpl usefulServicesImpl;
	
	@BeforeClass
	public static void before(){
		usefulServicesImpl	=	new UsefulServicesImpl();
	}
	
	@Test
	public void testCityValidation(){
		assertTrue(usefulServicesImpl.cityValidation("BLR", "blr"));
		assertFalse(usefulServicesImpl.cityValidation("BLR", "BPL"));
	}
	
	@Test
	public void testDateValidation(){
		String DepDate="12-10-2018";
		String ArrDate="13-10-2018";
	    try {
			Date departDate=new SimpleDateFormat("dd-MM-yyyy").parse(DepDate);
			Date returnDate=new SimpleDateFormat("dd-MM-yyyy").parse(ArrDate);
			assertFalse(usefulServicesImpl.dateValidation(departDate, returnDate));
		} catch (ParseException e) {
			
		}  
	}
	
	@Test
	public void testSetDateFormatYYYY_MM_DD(){
		Calendar calendar	=	Calendar.getInstance();
		Date current	=	calendar.getTime();
		Date newDate	=	usefulServicesImpl.setDateFormatYYYY_MM_DD(current);
		assertFalse(current.equals(newDate));
	}
	
}
