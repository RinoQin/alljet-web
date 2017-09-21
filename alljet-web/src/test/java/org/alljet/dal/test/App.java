package org.alljet.dal.test;

import java.util.List;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ListArrayLi<String> list = new ListArrayLi<String>();
        System.out.println("最开始listarray的长度："+list.size());
        for(int i = 1 ; i<=15;i++){
        	boolean flag = list.add(i+"");
        	if(flag)
        		System.out.println("第"+i+"个add成功");
        	else
        		System.out.println("第"+i+"个add失败");
        	
        }
        System.out.println("最后listarray的长度："+list.size());
        
        
        for(int i = 0 ; i<15;i++){
        	System.out.println("第"+(i+1)+"个str是："+list.get(i));
        }
    }
    
    
    
    
}
