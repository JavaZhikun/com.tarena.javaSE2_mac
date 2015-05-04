package day02_2;

public class Contact {

	private int id;
	private String name;
	private String phone;
	private String address;
	private long qq;
	
	public Contact(){
		
	}
	
	public Contact(int id, String name , String phone, String address, long qq){
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.qq = qq;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name){     
    	this.name = name;              
	} 
	
	public void setPhone(String phone){     
		this.phone = phone;              
	}                              
	
	public void setAddress(String address){     
		this.address = address;                                             
	}                              
	
	public void setQq(int qq){     
		this.qq = qq;                                             
	}

	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public long getQQ(){
		return qq;
	}
	
	public String toString(){
		return "Contact[id:"+id+",name:"+name+",phone:"+phone+",address:"+address+",qq:"+qq;
	}

}  


                                   




                                   