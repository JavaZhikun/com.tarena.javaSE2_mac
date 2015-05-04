package day02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.junit.Test;

public class TestCase {
	/**
	 * 批量写数据
	 * 准备一批数据byte[]，将一批数据写入到文件中
	 * @throws Exception 
	 */
	
	//@Test
	public void  testWriteFile() throws Exception{
		byte[] bytes = {1,2,3,4,5,127};
		//在读写打开模式中，如果硬盘中没有目标文件"test.txt"，则自动在磁盘中创建文件
		RandomAccessFile raf = new RandomAccessFile("test.txt","rw");
		//打开以后raf的读取指针位置为0
		//将方法数组中全部的byte数据，依次写入到打开的文件中。文件指针自动移动n次
		//写完之后，指针在刚刚写完数据后面的地方
		raf.write(bytes);
		//write成批写入，将数据byte[]从0位置开始，连续写入6个数据，文件指针连续移动6次
		raf.write(bytes,0,6);//更加方便，更加灵活
		raf.close();
		
	}
	
	/**
	 * 成批读取文件中的数据
	 * 
	 */
	//@Test
	public void testReadFile() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.txt","r");
		//文件打开的时候，文件指针在0位置
		//成批读取数据，需要读到byte数组中，所以要事先创建此byte数组。
		byte[] buf = new byte[12];//比文件长度短，使用读取方法
		//read(buf) 从文件中尽可能多的读取数据到数组中。
		//文件是12个byte，数组是6个byte，读取6个byte填充到数组，文件指针移动6次
		// 返回值是读取数据的个数6
		int n = raf.read(buf);
		System.out.println(n);
		System.out.println(Arrays.toString(buf));
		
	}
	
	
	/**
	 * read 经典用法：一次性读取文件的全部内容到数组
	 * key point:创建一个和文件长度一致的数组，将内容读到数组中
	 * 小心，文件长度不能过大
	 */
	
	//@Test
	public void testFileContent() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.txt","r");
		//raf.length() 获取文件的长度long类型
		//创建数组的长度必须是int
		//buf数组的长度与文件长度一致，但是这个语句要小心，文件长度不能过大
		byte[] buf = new byte[(int)raf.length()];
		//将文件的内容一次读取到buf中
		raf.read(buf);
		
		raf.close();
		System.out.println(Arrays.toString(buf));//文件中全部内容
		
	}
	
	//批次读取文件
	
	//@Test
	public void testRead() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.txt","r");
		//1 2 3 4 5 | 127 1 2 3 4 | 5 127
		byte[] buf = new byte[5];
		
		int n = raf.read(buf);//返回数据指针的位置
		System.out.println(n + ":" + Arrays.toString(buf));//5:[1, 2, 3, 4, 5]
		
		n = raf.read(buf);
		System.out.println(n + ":"+ Arrays.toString(buf));//5:[127, 1, 2, 3, 4]
		
		n =  raf.read(buf);
		System.out.println(n + ":" + Arrays.toString(buf));
		//2:[5, 127, 2, 3, 4] 后面三个属无效数据，用前面的2来区别
		
	   //已经是文件末尾的情况下，再读取，就返回-1，数组数据没有变化,使用上面的数据。
		
		n =  raf.read(buf);
		System.out.println(n + ":" + Arrays.toString(buf));//-1:[5, 127, 2, 3, 4]
	}
	
	/**
	 * 成批读取文件内容的经典写法
	 * @throws Exception 
	 */
	//@Test
	public void testRead2() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.txt","r");
		//首先定义缓存区
		byte[] buf = new byte[5];
		int n;
		//容易错误的是忘记写read的参数buf
		while((n=raf.read(buf)) != -1){
			System.out.println(n +":" + Arrays.toString(buf));
		}
		raf.close();
	}
	
	/**
	 *   文件指针的操作
	 * @throws Exception 
	 */
	//@Test
	public void testFilePointer() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.txt","rw");
		//打开文件时候，文件指针在0
		//获取raf的当前文件指针位置
		//point 指向  pointer 指针
		long pointer = raf.getFilePointer();
		int b = raf.read();//文件指针移动一次
		pointer = raf.getFilePointer();//获取文件的指针
		System.out.println(pointer);
		
		//有两种办法移动文件指针
		//A， 绝对移动，seek（0），移动到0位置,可以移动
		raf.seek(89);
		pointer = raf.getFilePointer();
		System.out.println(pointer);
		
		//B， 相对移动，skipBytes（1） 向后移动一个位置，只能向后移动，不能前移
		//      如果参数是负数，则不移动，如果移动到最后，也不移动
		raf.skipBytes(9);
		pointer = raf.getFilePointer();
		System.out.println(pointer);
	}
	
	//@Test
	public void testFilePointer2() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.txt","rw");
		long pointer = raf.getFilePointer();
		int b = raf.read();
		pointer = raf.getFilePointer();
		System.out.println(pointer);
		
		raf.seek(6);
		pointer = raf.getFilePointer();
		System.out.println(pointer);
		
		raf.skipBytes(7);
		pointer = raf.getFilePointer();
		System.out.println(pointer);
		
	}
	
	/**
	 * 文件指针的工作
	 * 
	 *  byte  byte byte  byte byte  byte
	 *    0     1   2     3    4     5
	 *    
	 * 1.打开文件时候文件 指针在开始位置
	 * 2.每次读写，读写了几个byte，文件指针就移动几个byte
	 * 3.如果连续读写，是不需需要干预指针的位置的。
	 * 4，如果不是连续读写，就需要调整指针的位置了。
	 * 
	 */
	
	/**
	 * 将一个整数写入到文件
	 *   int是32位数:
	 *    1  : 00000000 00000000 00000000 00000001
	 *   max ： 01111111 11111111 11111111 11111111
	 *   文件：  byte  byte byte  byte byte  byte
	 *   		 0     1   2     3    4     5
	 *   
	 *   raf.write(max);//写入低8位
	 *   
	 *   写入整数的算法：我的第一想法
	 *   max>>8;
	 *   写完之后全部进行转换
	 *   
	 *   第二想法：
	 *   首先移动3个8位，然后移动2个，然后一个，最后不移动
	 */
	//@Test
	public void testWriteInteger() throws Exception{
		int n = Integer.MAX_VALUE;
		RandomAccessFile raf = new RandomAccessFile("test.data","rw");
		byte[] bytes = new byte[4]; 
		for(int i = 3; i>=0; i--){
			bytes[3-i] = (byte) (n >> i*8);	
		}
		raf.write(bytes);
	    raf.close();
	    
	   raf = new RandomAccessFile("test.data","r");
	    int b;
	    while(( b = raf.read(bytes))!=-1){
	    	System.out.println(b + ":" + Arrays.toString(bytes));
	    }
		
	
	
	}
	
	/**
	 * num.dat   文件中的数据
	 * 1.从0开始连续4个byte是int数据
	 * 2.从4开始连续8个byte是long数据
	 *   ...
	 * 
	 */
	
	//@Test
	public void testWriteInt() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("num.dat","rw");
		System.out.println(raf.getFilePointer());//0
		
		//将整数写入
		raf.writeInt(Integer.MAX_VALUE);
		System.out.println(raf.getFilePointer());//4
		raf.writeLong(10L);
		System.out.println(raf.getFilePointer());//12
		raf.writeShort((short) 10);
		System.out.println(raf.getFilePointer());//14
		raf.writeByte((byte) 1);
		System.out.println(raf.getFilePointer());//15
		raf.writeChar((char) 1);
		System.out.println(raf.getFilePointer());//17
		raf.writeBoolean(true);
		System.out.println(raf.getFilePointer());//18
		raf.writeDouble(1d);
		System.out.println(raf.getFilePointer());//26
		raf.writeFloat(2F);
		System.out.println(raf.getFilePointer());//30
	}
	
	/**
	 * num.dat   文件中的数据
	 * 1.从0开始连续4个byte是int数据
	 * 2.从4开始连续8个byte是long数据
	 *   ...
	 * 读取数据，从当前指针开始，位置极其重要
	 */
	//@Test  
	public void testReadLong() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("num.dat","r");
		raf.seek(4);//调到long类型数据的起始位置
		long l = raf.readLong();//10
		System.out.println(l);
		raf.seek(0);
		int i = raf.readInt();
		System.out.println(i);
		
		
		
	}
	
	
	/**
	 *   1.文件的基本访问规则，每次读写1个byte
	 *   2.int, long, double 等数据需要拆分为byte写到文件中
	 *   3.读取时候，需要读取多个byte合并为int long double。
	 *   4.Java 提供了int long等数据的读写API
	 *   5.对象需要拆分为byte序列写到文件中
	 *   6.字符串需要拆分为byte写入文件中
	 *     如何将字符串拆分为byte序列
	 *       "abc中国".getBytes()  结果为字符串数组
	 *       编码
	 *       
	 *       utf-8中每个汉字占3个byte
	 * @throws Exception 
	 */  
	//@Test
	public void testString() throws Exception{
		String str = "你好达内！";
		//将str中的字符数据按照UTF-8编码标准
		//编码为byte序列
		byte[] bytes  = str.getBytes("UTF-8");
		RandomAccessFile raf = new RandomAccessFile("str.dat","rw");
		//写数组到文件中，是写字符串的byte数据到文件中。
		//本案例写了15个byte
		//文件中从0开始连续15个byte存储的是String
		raf.write(bytes);
		//将字符串读取回来
		raf.seek(0);
		byte[] buf = new byte[15];
		raf.read(buf);//读取了字符串byte数据
		//解码 utf-8
		String s = new String(buf, "utf-8");
		System.out.println(s);
		
	}
	
	/**
	 * 业务：将电话本存储在一个文件中
	 * 编写算法：
	 *    1.存储电话到一个文件中
	 *    2.读取电话本文件打印联系人
	 *    
	 * 电话本中的联系人是对象//凡是"号"最好是字符串
	 * 联系人：编号(int)，姓名(String)，电话(String),email(String),QQ(long)
	 * 
	 * 方法1： 存储联系人
	 *    文件中每372个byte为一个单元（记录）
	 *    存储一条联系人（存储一个对象）
	 * 
	 * 
	 * 方法2： 读取联系人
	 * 
	 */
	
	/**
	 *   添加一个将联系人写到文件的方法
	 *   raf是已经打开的文件
	 *   obj联系人信息
	 *   rowId 是行号位置
	 *   
	 *   定长文件的标准输出方式
	 * @author luanzhikun
	 *
	 */
	
	public static void writeContact(RandomAccessFile raf, Contact obj,int rowId) throws Exception{
		long start = rowId *372;
		//写id
		raf.seek(start) ;
		raf.writeInt(obj.getId());//4
		//写name 
		raf.seek(start + 4);//30
		raf.write(obj.getName().getBytes("utf-8"));
		//写phone到文件
		raf.seek(start+34);//20
		raf.write(obj.getPhone().getBytes("utf-8"));
		//写address到文件
		raf.seek(start+54);//150
		raf.write(obj.getAddress().getBytes("utf-8"));
		//写qq到文件
		raf.seek(start+204);//
		raf.writeLong(obj.getQQ());
		
		
		//检查是否追加记录
		if(raf.getFilePointer() == raf.length()){
			//追加记录需要文件增长372的整数倍
			raf.setLength(start + 372);
		}		
	}
	
	/**
	 *  测试联系人读写
	 * @throws Exception 
	 * 
	 *
	 */
	//@Test
	public void testContant() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("content.dat","rw");
		Contact tom = new Contact(1,"Tom","110","北京",12910);
		Contact Jerry = new Contact(2,"Jerry","110","海南",12910);
		//...
		
		//将联系人写入到文件
		writeContact(raf,tom,0);
		writeContact(raf,Jerry,1);
		
		//从文件raf读取联系人信息到Contact对象
		Contact c1 = readContact(raf, 1);
		System.out.println(c1);
		raf.close();
		
	}
	
	/**
	 * 从文件中读取联系人信息
	 * @param  raf 保存联系人信息的文件
	 * @param  i   行号  0 1 2 3 。。。  n
	 * @return  联系人信息对象
	 * @throws Exception 
	 */
	
	public static Contact readContact(RandomAccessFile raf,int rowId) throws Exception{
		long start = rowId * 372L;
		raf.seek(start + 0);//找到id的起始位置
		
		int id = raf.readInt();//不能调用错了！
		
		//读取name
		raf.seek(start + 4);//
		byte[] buf = new byte[30];
		raf.read(buf);
		//进行解码
		//从4开始连续读取30个bytes
		String name = new String(buf, "utf-8").trim(); //将用来凑够30的空白去掉
		
		
		raf.seek(start + 34);
		buf =  new byte[20];
		raf.read(buf);
		String phone = new String(buf,"utf-8").trim();
		
		raf.seek(start + 54);
		buf = new byte[150];
		raf.read(buf);
		String address = new String(buf,"utf-8").trim();
		
		raf.seek(start + 204);
		long QQ = raf.readLong();
		
		Contact contact = new Contact(id, name,phone,address, QQ);
		return contact;
	}
	
	
	
	
	class Contact{
		int Id;
		String name;
		String phone;
		String address;
		long QQ;
		
		public Contact(){
			
		}
		
		public Contact(int Id,String name,String phone, String address, long QQ ){
			super();
			this.Id =Id;
			this.name = name;
			this.phone = phone;
			this.address = address;
			this.QQ =QQ;
		}
		
		public void setId(int Id){
			this.Id = Id;
		}
		
		public int getId(){
			return Id;
		}
		public void setName(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		public void setPhone(String phone){
			this.phone = phone;
		}
		
		public String getPhone(){
			return phone;
		}
		public void setAddress(String address){
			this.address = address;
		}
		
		public String getAddress(){
			return address;
		}
		public void setQQ(long QQ){
			this.QQ = QQ;
		}
		
		public long getQQ(){
			return QQ;
		}
		
		
		public String toString(){
			return "Contant [id=" +Id +"name"+ name + "phone" +phone + "address"+ address+"QQ"+ QQ+"]";
		}
		
	}

//文件的复制
//总结
//流文件的读写的经典操作

	/**
	 * 实现文件的复制功能
	 * @throws Exception 
	 */

   @Test
   public void testCopy() throws Exception{
	   //输入流经常的缩写是in
	   FileInputStream in = new FileInputStream("/Users/luanzhikun/desktop/file/Read Me.html");
	   //输出流经常缩写为out
	   FileOutputStream out = new FileOutputStream("/Users/luanzhikun/desktop/file/my.html");
	   int b;//b是byte的缩写。
	   //将输入流读取的每个byte写到输出流中
	   //简单的说，就是读一个byte，写一个byte
	   while((b = in.read()) != -1){
		   out.write(b);
	   }
	   out.close();
	   in.close();
	   System.out.println("ok");
   }

}









