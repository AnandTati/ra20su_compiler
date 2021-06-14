package com.ra20su.syntax.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class Instra_Table {
	private Map<Integer,List<String>> instruct_table=new HashMap<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Instra_Table IT=new Instra_Table();
		IT.insert_table("Push","5000");
	}
	public boolean insert_table(String opration,String Operand )
	{
		int i=1;
		while(instruct_table.containsKey(i))
			i++;
		List<String> AL=new ArrayList<>(Arrays.asList(opration,Operand)); 
		instruct_table.put(i, AL);
		System.out.print(instruct_table);
		return false;
	}
	
}
