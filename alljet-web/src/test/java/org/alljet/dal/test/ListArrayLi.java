package org.alljet.dal.test;

import java.util.Arrays;
import java.util.Collection;

public class ListArrayLi<E> {
	    	
	private int size;
	private Object[] elementData ;
	
    public ListArrayLi() {
        this(10);
    }
	
    public ListArrayLi(int initLength) {
        super();
        if (initLength < 0)
            throw new RuntimeException("init array length: "+ initLength);
        this.elementData = new Object[initLength];
    }
    
//	public ListArrayLi(Collection<? extends E> c){
//		elementData = c.toArray();
//		size = elementData.length;
//		if (elementData.getClass() != Object[].class)
//			elementData = Arrays.copyOf(elementData, size, Object[].class);
//	}
	
	public boolean add(E e){
		try{
			elementData = Arrays.copyOf(elementData, size+1, Object[].class);
			elementData[size] = e;
			size = elementData.length;
			return true;
    		
		}catch(Exception x){
			return false;
		}
		
	}

    public E get(int index){
    	if(index<size){
    		return (E)elementData[index];
    	}else{
    		throw new RuntimeException("index out");
    	}
    }

	public int size() {
		return size;
	}
	
	    

}
